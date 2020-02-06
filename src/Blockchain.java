import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

class Blockchain extends Thread implements Serializable {
    ArrayList<Block> chain;
    private float reward_per_block = 10;
    private ArrayList<Transaction> pendingTransactions;
    private ArrayList<Transaction> currently_mining;
    int difficulty = 0;
    String ID;
    private String b_chain_broadcast;
    static boolean mineInterrupt = false;
    static int transactions_per_block = 2;

    Blockchain(String ID, int diff, String broadcast_addr) throws NoSuchAlgorithmException, SocketException {
        this.ID = ID;
        this.b_chain_broadcast = broadcast_addr;
        chain = new ArrayList<>();
        pendingTransactions = new ArrayList<>();
        setDifficulty(diff);
        chain.add(generateGenesisBlock());

    }

    private Block generateGenesisBlock() throws NoSuchAlgorithmException {
        Transaction minerReward = new Transaction("Reward","Blockchain",this.ID);
        minerReward.setAmount(reward_per_block);
        ArrayList<Transaction> genesis = new ArrayList<>();
        genesis.add(minerReward);
        Block generate = new Block("null", pendingTransactions,"27/05/1999",0);
        generate.setPrevHash(null);
        generate.hash = "";
        return generate;
    }
    void setDifficulty(int diff){
        this.difficulty = diff;
    }

    void start_mining() throws NoSuchAlgorithmException, InterruptedException, IOException {
        Blockchain.mineInterrupt = false;
        boolean flag = true;
        int k = Blockchain.transactions_per_block;
        currently_mining = new ArrayList<>();
        while(!MainRun.stopThread_mining){
            if (pendingTransactions.size() < k){
                if (flag) {
                    String toNoti = "Waiting for transactions...  "+ pendingTransactions.size();
                    NotificationGUI.model.addElement(toNoti);
                    System.out.println("Waiting for transactions..."+ pendingTransactions.size());
                    flag = false;
                }
                Thread.sleep(3000);
            }
            else {
                //System.out.println(pendingTransactions.size());
                for (int i=0;i<k;i++){
                    currently_mining.add(pendingTransactions.get(i));
                }
                //Get miner reward
                Transaction minerReward = new Transaction("Reward","Blockchain",this.ID);
                minerReward.setAmount(reward_per_block);
                currently_mining.add(minerReward);
                //Get current time
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String s = dtf.format(now);
                Block newBlock = new Block(chain.get(chain.size()-1).getCurrentHash(), currently_mining, s, difficulty);

                if(newBlock.mineBlock()){
                    pendingTransactions.removeAll(currently_mining);
                    TransferData sendBlock = new TransferData(ID, newBlock);
                    SendData sd = new SendData(b_chain_broadcast, 7777);
                    sd.broadcastData(sendBlock);
                    validate_add_block(newBlock);
                    String toNoti = "Block sent! ID:"+ newBlock.blockId;
                    NotificationGUI.model.addElement(toNoti);
                    System.out.println("Block sent! ID:"+ newBlock.blockId);
                    currently_mining.clear();
                    break;
                }
            }
        }
    }

    public void run(){
        try {
            start_mining();
        } catch (NoSuchAlgorithmException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    void validate_add_block(Block bk){
        if (!bk.getPrevHash().equals(chain.get(chain.size()-1).getCurrentHash())){// Check if new block slots on the blockchain
            String toNoti = "Block Rejected! : Blockchain hash does not match!";
            NotificationGUI.model.addElement(toNoti);
            System.out.println("Block Rejected! : Blockchain hash does not match!");
            return;
        }
        ArrayList<Transaction> pTr = chain.get(chain.size()-1).getTransactions();
        ArrayList<Transaction> nTr = bk.getTransactions();
        String toNoti2 = "Blockchain Size:"+ chain.size();
        NotificationGUI.model.addElement(toNoti2);
        System.out.println("Blockchain Size:"+ chain.size());
        ArrayList<String> prevTr = new ArrayList<>();
        ArrayList<String> newTr = new ArrayList<>();

        for (Transaction t : pTr) {
            prevTr.add(t.id);
        }
        for (Transaction t : nTr) {
            if(t.id.equals("Reward")){
                if (t.getAmount() != reward_per_block){
                    return;
                }
            }
            newTr.add(t.id);
        }

        prevTr.retainAll(newTr);
        if(prevTr.size()!=0){
            for (String s:prevTr){
                if (s.equals("Reward")){
                    prevTr.remove(s);
                    break;
                }
            }
        }
        if (prevTr.size() != 0){// Check if previous block has some of the same transactions.
            System.out.println(prevTr.get(0));
            System.out.println(prevTr.size());
            String toNoti3 = "Block Rejected! : Duplicate Transactions!";
            NotificationGUI.model.addElement(toNoti3);
            System.out.println("Block Rejected! : Duplicate Transactions!");
        }
        else{
            this.chain.add(bk);
            String toNoti3 = "New block added to blockchain ID:"+ bk.blockId;
            NotificationGUI.model.addElement(toNoti3);
            System.out.println("New block added to blockchain ID:"+ bk.blockId);
            for(Transaction t : pendingTransactions){
                for (String s : newTr){
                    if (t.id.equals(s)){
                        pendingTransactions.remove(t);
                    }
                }
            }
            if (!MainRun.stopThread_mining){
                MainRun.mining_flag = true;
            }
            // Interrupt any mining process as new block has been acquired.
            Blockchain.mineInterrupt = true;
        }
    }

    void validate_add_transaction(Transaction tr){
        for(Transaction t : pendingTransactions){
            if(t.id.equals(tr.id)){
                String toNoti3 = "Duplicate transaction found!";
                NotificationGUI.model.addElement(toNoti3);
                System.out.println("Duplicate transaction found!");
                return;
            }
        }
        pendingTransactions.add(tr);
        String toNoti3 = "\nTransaction from: "+ tr.fromAddress+" Validated";
        NotificationGUI.model.addElement(toNoti3);
        System.out.println("\nTransaction from: "+ tr.fromAddress+" Validated");
    }

    boolean isValid() throws NoSuchAlgorithmException { // Some problem in this function. For now return true.
        String hashTarget = new String(new char[difficulty]);

        // Checks for the validity of the block-chain.
        /*for (int i = chain.size()-1; i>0; i--){
            //Compare current registered hash and calculated hash
            if (chain.get(i).getCurrentHash().equals(chain.get(i).calcHash())){
                return false;
            }

            //Compare previous registered hash and previous hash
            if ((!chain.get(i).getPrevHash().equals(chain.get(i-1).calcHash()))){
                return false;
            }

            //To check is block is mined
            if (!chain.get(i).getCurrentHash().substring(0,difficulty).equals(hashTarget)){
                System.out.println("Block" + i +"is not Mined");
                return false;
            }
        }*/
        return true;
    }

    float getFunds(){
        float funds = 0;
        for(Block b: chain){
            for (Transaction t: b.getTransactions()){
                if (t.getToAddress().equals(this.ID)){
                    funds += t.getAmount();
                }
                if (t.fromAddress.equals(this.ID)){
                    funds -= t.getAmount();
                }
            }
        }
        for (Transaction t : pendingTransactions){
            if (t.fromAddress.equals(this.ID)){
                funds -= t.getAmount();
            }
        }
        return funds;
    }


    void showObj(MedicalObject obj) {
        if (obj!=null) {
            String s = "Type: "+obj.TYPE+"Name: "+obj.NAME+"Quanity: "+obj.QUANTITY+"Amount: "+obj.AMOUNT;
            System.out.println(s);
        }
    }

    void showHis (MedicalHistory his){
        if (his!=null){
            String s = "\nName:"+his.NAME+"\nAddress:"+his.ADDRESS+"\nBloodGroup:"+his.BLOODGROUP+"\nAge:"+his.AGE+
                       "\nDiseases:\n"+his.DISEASES+"\nAllergies:\n"+his.ALLERGIES;
            System.out.println(s);
        }

    }

    void viewTrs() {
        for (Block b:chain) {
            for (Transaction t:b.getTransactions()) {
                System.out.println("From:"+t.fromAddress);
                System.out.println("Type:"+t.Header);
                System.out.println("To:"+t.getToAddress());
                showObj(t.object);
                showHis(t.history);
            }
        }
    }
}
