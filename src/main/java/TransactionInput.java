/**
 * @author bom
 * @date 2023/11/12
 **/
public class TransactionInput {
    public String transactionOutPutId;
    public TransactionOutPut UTXO;

    public TransactionInput(String transactionOutPutId) {
        this.transactionOutPutId = transactionOutPutId;
    }
}
