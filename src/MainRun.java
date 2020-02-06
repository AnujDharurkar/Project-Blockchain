
import java.io.*;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;

public class MainRun {
    static String BROADCAST_ADDRESS = "192.168.43.255"; //Set the current broadcast address
    public static boolean stopThread_mining = false;
    public static boolean mining_flag = false;
    int DIFFICULTY = 0;
    public Blockchain b_chain;
    private ReceiveData server;
    private SendData sd;

    private void createNewBChain(){
        String ID = String.valueOf((int) (Math.random() * 100000));
        try {
            b_chain = new Blockchain(ID, DIFFICULTY, BROADCAST_ADDRESS);
        } catch (NoSuchAlgorithmException | SocketException e) {
            e.printStackTrace();
        }
    }

    void setup() throws SocketException, NoSuchAlgorithmException {
        File file = new File("Ledger.txt");
        if (file.exists()){
            System.out.println("Ledger Found, Using That.\n");
            try {
                FileInputStream fi = new FileInputStream(file);
                ObjectInputStream oi = new ObjectInputStream(fi);

                b_chain = (Blockchain) oi.readObject();
                System.out.println("Chain size:" + b_chain.chain.size());
                oi.close();
                fi.close();
                if(!b_chain.isValid()){
                    for (Block b : b_chain.chain){
                        System.out.println(b.getPrevHash() + "->" + b.getCurrentHash());
                    }
                    System.out.println("Invalid Blockchain!, creating a new one.");
                    b_chain = null;
                    createNewBChain();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            createNewBChain();
        }
        server = new ReceiveData(7777, b_chain);
        Thread t1 = new Thread(server);
        t1.start();

    }

    void exit() throws IOException {
        sd = new SendData("localhost", 7777);
        try {
            sd.endBroadcast();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        server.stopRunning();
        try{
            FileOutputStream f = new FileOutputStream(new File("Ledger.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(b_chain);
            o.close();
            f.close();
            System.out.println("Good-Bye!"+ "Chain size = " + b_chain.chain.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MedicalHistory create_medical_history(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name:");
        String _name = sc.nextLine();
        System.out.print("Enter Address:");
        String _addr = sc.nextLine();
        System.out.print("Enter Blood Group:");
        String _bg = sc.nextLine();
        System.out.print("Enter Age:");
        int _age = sc.nextInt();
        MedicalHistory mh = new MedicalHistory(_name, _addr, _bg, _age);

        // Extract data from string and put into array-list.
        System.out.println("Enter diseases (Separated by ','):");
        String dummy = sc.nextLine();
        String _diseases = sc.nextLine();
        String[] _disa = _diseases.split("[,]");
        System.out.println("Enter allergies (Separated by ','):");
        String _allergies = sc.nextLine();
        String[] alga = _allergies.split("[,]");

        ArrayList<String> dis= new ArrayList<>();
        ArrayList<String> all = new ArrayList<>();

        Collections.addAll(dis, _disa);
        Collections.addAll(all, alga);
        mh.setALLERGIES(all);
        mh.setDISEASES(dis);
        try {
            mh.store();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mh;
    }

    private MedicalObject create_medical_object(){
        Scanner sc = new Scanner(System.in);
        String _type, _name;
        int _quantity;
        float _amount;
        System.out.print("Enter type of object (Organ/Drug):");
        _type = sc.nextLine();
        System.out.print("Enter name of the object:");
        _name = sc.nextLine();
        System.out.print("Enter the quantity:");
        _quantity = sc.nextInt();
        System.out.print("Enter the funds to be transferred:");
        _amount = sc.nextFloat();
        System.out.println("Enter blood type:");
        String _bg = sc.nextLine();
        MedicalObject mobj = new MedicalObject(_type, _name, _quantity, _amount);
        try {
            mobj.setBloodType(_bg);
            mobj.store();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mobj;
    }



    String makeTransaction() throws IOException { // Only for console TAG{CONSOLE}
        float cash;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Blockchain ID of recipient:");
        String SendTO = sc.nextLine();
        System.out.print("\n1) Medical Object\n2) Medical History\n3) Funds\n->");
        String switch_str = sc.nextLine();
        Transaction tr = new Transaction(String.valueOf((float) (Math.random() * 1000000)), b_chain.ID, SendTO);
        // Transaction tr = new Transaction("1", b_chain.ID);
        switch(switch_str){
            case "1":
                tr.setObject(create_medical_object());
                break;
            case "2":
                tr.setHistory(create_medical_history());
                break;
            case "3":
                System.out.println("Your wallet:"+ b_chain.getFunds());
                System.out.print("Enter amount:");
                cash = sc.nextFloat();
                if (b_chain.getFunds() >= cash) {tr.setAmount(cash);}
                else{
                    System.out.println("You don't have enough funds!");
                    return "You don't have the required funds!";
                }
                break;
            default:
                return "Cancelled";
        }
        b_chain.validate_add_transaction(tr);
        TransferData td = new TransferData(b_chain.ID, tr);
        SendData sd = new SendData(MainRun.BROADCAST_ADDRESS, 7777);
        sd.broadcastData(td);
        return "Transaction from "+b_chain.ID + " to "+ SendTO + "Type -" + tr.Header;// Here goes Transaction Information.
    }


    void makeTransaction_GUI(String trID) {
        Transaction tr = new Transaction(String.valueOf((float) (Math.random() * 1000000)), b_chain.ID, trID);
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException { //{CONSOLE}
        boolean run = true;
        Scanner sc = new Scanner(System.in);
        MainRun m = new MainRun();
        m.setup();

        while(run){
            if(MainRun.mining_flag){
                Thread miningThread = new Thread(m.b_chain);
                MainRun.mining_flag = false;
                miningThread.start();

            }
            System.out.print("\n1)Create a transaction\n2)Start Mining\n3)Stop Mining\n4)Exit\n->");
            switch (sc.nextLine()){
                case "1":
                    m.makeTransaction();
                    break;
                case "2":
                    Thread miningThread = new Thread(m.b_chain);
                    MainRun.stopThread_mining = false;
                    miningThread.start();
                    break;
                case "3":
                    MainRun.stopThread_mining = true;
                    break;
                default:
                    m.b_chain.viewTrs();
                    m.exit();
                    run = false;
                    break;
            }
        }
    }
}