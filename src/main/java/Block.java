import util.StringUtil;


/**
 * @author bom
 * @date 2023/11/07
 **/
public class Block {
    public String hash;

    public String previousHash;

    private String data;

    private Long timeStamp;

    private int nonce;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();
        this.hash = calculateHash();

    }

    public String calculateHash() {
        String calculatedHash = StringUtil.sha256Encrypt(
                previousHash +
                        Integer.toString(nonce) +
                        data +
                        Long.toString(timeStamp));
        return calculatedHash;

    }

    public void mineBlock(int difficulty) {
        //target --> 0000
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined!: " + hash);
    }
}
