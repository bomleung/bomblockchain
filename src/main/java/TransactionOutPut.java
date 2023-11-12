import util.StringUtil;

import java.security.PublicKey;

/**
 * @author bom
 * @date 2023/11/12
 **/
public class TransactionOutPut {
    public String id;
    public PublicKey receiver;
    public float value;
    public String parentTransactionId;

    public TransactionOutPut(PublicKey receiver, float value, String parentTransactionId) {
        this.id = StringUtil.sha256Encrypt(StringUtil.getStringFromKey(receiver) + Float.toString(value) + parentTransactionId);
        this.receiver = receiver;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
    }

    public boolean isMine(PublicKey publicKey) {
        return (publicKey == receiver);
    }
}
