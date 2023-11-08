import java.security.*;
import java.security.spec.ECGenParameterSpec;

/**
 * @author bom
 * @date 2023/11/08
 **/
public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;

    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec spec = new ECGenParameterSpec("prime192v1");
            generator.initialize(spec, random);
            KeyPair keyPair = generator.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
