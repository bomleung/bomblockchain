
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import util.StringUtil;

/**
 * @author bom
 * @date 2023/11/07
 **/
public class BomChain {

    public static float minimumTransaction = 0.1f;
    public static int difficulty = 5;

    public static ArrayList<Block> blockChain = new ArrayList<Block>();

    public static Wallet walletA;

    public static Wallet walletB;

    public static HashMap<String, TransactionOutPut> UTXOS = new HashMap<String, TransactionOutPut>();


    public static void main(String[] args) {

        Security.addProvider(new BouncyCastleProvider());
        Wallet walletA = new Wallet();
        Wallet walletB = new Wallet();
        System.out.println("Private and public keys:");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));

        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
        transaction.generateSignature(walletA.privateKey);

        System.out.println("Is signature verified");
        System.out.println(transaction.verifySignature());

//        blockChain.add(new Block("I am first block", "0"));
//        System.out.println("Trying to mine block 1...");
//        blockChain.get(0).mineBlock(difficulty);
//
//        blockChain.add(new Block("I am second block", blockChain.get(blockChain.size() - 1).hash));
//        System.out.println("Trying to mine block 2...");
//        blockChain.get(1).mineBlock(difficulty);
//
//        blockChain.add(new Block("I am third block", blockChain.get(blockChain.size() - 1).hash));
//        System.out.println("Trying to mine block 3...");
//        blockChain.get(2).mineBlock(difficulty);
//
//        System.out.println("\bBlockChain is Valid: " + isChainValid());
//
//
//        String blockChainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
//        System.out.println("\nThe block chain: ");
//        System.out.println(blockChainJson);

    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blockChain.size(); i++) {
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i - 1);
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hash not equal!");
                return false;
            }
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hash not equal!");
                return false;
            }
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This Block hasn't mined");
                return false;
            }
        }
        return true;
    }
}
