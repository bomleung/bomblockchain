import util.StringUtil;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

/**
 * @author bom
 * @date 2023/11/08
 **/
public class Transaction {


    public String transactionId;
    public PublicKey sender;
    public PublicKey receiver;
    public float value;
    public byte[] signature;

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutPut> outputs = new ArrayList<TransactionOutPut>();

    public static int sequence = 0;

    public Transaction(PublicKey sender, PublicKey receiver, float value, ArrayList<TransactionInput> inputs) {
        this.sender = sender;
        this.receiver = receiver;
        this.value = value;
        this.inputs = inputs;
    }

    private String calculateHash() {
        sequence++;
        return StringUtil.sha256Encrypt(
                StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(receiver) +
                        Float.toString(value) + sequence
        );
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver) + Float.toString(value);
        signature = StringUtil.applyECDSASig(privateKey, data);
    }

    public boolean verifySignature() {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(receiver);
        return StringUtil.verifyECDSASig(sender, data, signature);
    }

    public boolean processTransaction() {
        if (verifySignature() == false) {
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }

        for (TransactionInput i : inputs) {
            i.UTXO = BomChain.UTXOS.get(i.transactionOutPutId);
        }

        if (getInputsValue() < BomChain.minimumTransaction) {
            System.out.println("#Transaction Inputs too small:" + getInputsValue());
            return false;
        }

        float leftOver = getInputsValue();
        transactionId = calculateHash();
        outputs.add(new TransactionOutPut(this.receiver, value, transactionId));
        outputs.add(new TransactionOutPut(this.sender, leftOver, transactionId));

        for (TransactionOutPut o : outputs) {
            BomChain.UTXOS.put(o.id, o);
        }

        for (TransactionInput i : inputs) {
            if (i.UTXO == null) {
                continue;
            }
            BomChain.UTXOS.remove(i.UTXO.id);
        }
        return true;

    }

    public float getInputsValue() {
        float total = 0;
        for (TransactionInput i : inputs) {
            if (i.UTXO == null) {
                continue;
            }
            total += i.UTXO.value;
        }
        return total;
    }

    public float getOutPutsValue() {
        float total = 0;
        for (TransactionOutPut o : outputs) {
            total += o.value;
        }
        return total;
    }
}
