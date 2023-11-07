import util.StringUtil;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author bom
 * @date 2023/11/07
 **/
public class Block {
    public String hash;

    public String previousHash;

    private String data;

    private Long timeStamp;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();
        this.hash = calculateHash();

    }

    public String calculateHash() {
        String calculatedHash = StringUtil.sha256Encrypt(previousHash + data + Long.toString(timeStamp) + data);
        return calculatedHash;

    }
}
