/**
 * @author bom
 * @date 2023/11/07
 **/
public class BomChain {
    public static void main(String[] args) {
        Block firstBlock = new Block("I am first block", "0");
        System.out.println("First Block's Hash: " + firstBlock.hash);

        Block secondBlock = new Block("I am second block", firstBlock.hash);
        System.out.println("Second Block's Hash: " + secondBlock.hash);

        Block thirdBlock = new Block("I am third block", secondBlock.hash);
        System.out.println("Third Block's Hash: " + thirdBlock.hash);

    }
}
