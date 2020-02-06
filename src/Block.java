import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


class Block implements Serializable {
    String blockId;
    String hash;
    private String prevHash;
    private ArrayList<Transaction> transactions;
    private String time;
    private int difficulty;
    private int nonce;
    Block(String preH, ArrayList<Transaction> tr, String time, int difficulty) throws NoSuchAlgorithmException {
        this.blockId = String.valueOf((int) (Math.random() * 100000));
        this.prevHash = preH;
        this.transactions = new ArrayList<>(tr);
        this.time = time;
        this.difficulty = difficulty;
        this.hash = calcHash();
    }

    // Import or implement hash function SHA256 or any other.
    // Returns a Hex Hash value
    String calcHash() throws NoSuchAlgorithmException {
        StringBuilder transaction_string = new StringBuilder();
        for(Transaction t: transactions){
            if ("Money".equals(t.Header)) {
                transaction_string.append(t.getAmount()).append(t.getToAddress());
            }
            else {
                transaction_string.append(t.getAmount()).append(t.Header).append(t.getToAddress());
            }
        }

        String msg = prevHash + blockId + time + transaction_string + nonce;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] bytes = digest.digest(msg.getBytes());
        final StringBuilder hexString = new StringBuilder();
        for(final byte b :bytes){
            String hex = Integer.toHexString(0xff &b);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    boolean mineBlock() throws NoSuchAlgorithmException {
        StringBuilder tar = new StringBuilder();
        tar.append("0".repeat(Math.max(0, difficulty)));
        while(!hash.substring(0,difficulty).equals(tar.toString()) && !Blockchain.mineInterrupt){
            nonce++;
            hash = calcHash();
        }
        //Blockchain.mineInterrupt = false; //Once stopped, removes interrupt.
        return hash.substring(0, difficulty).equals(tar.toString());

    }

    String getCurrentHash(){
        return hash;
    }
    String getPrevHash(){
        return prevHash;
    }
    ArrayList<Transaction> getTransactions(){
        return transactions;
    }
    public String getTime(){
        return time;
    }

    private String getBlockId(){
        return blockId;
    }

    void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        ArrayList<Transaction> a = new ArrayList<>();
        Block b = new Block("", a, "12-09-19", 6);
        System.out.println(b.mineBlock());
        System.out.println(b.getCurrentHash());
        System.out.println(b.getBlockId());
        System.out.println("Nonce:"+b.nonce);
        Block c = new Block(b.getCurrentHash(), a, "12-09-19", 6);
        System.out.println(c.mineBlock());
        System.out.println(c.getCurrentHash());
        System.out.println(c.getBlockId());
        System.out.println("Nonce:"+c.nonce);
        System.out.println("Prev hash = "+ c.getPrevHash());
    }

}