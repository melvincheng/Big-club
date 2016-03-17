import java.util.Vector;

public class TransactionProcessor{
  Vector<Account> accounts;
  Vector<Transaction> transactions;

  public TransactionProcessor(){
    TransactionIO trfio = new TransactionIO("test.trf");
    AccountIO actio = new AccountIO("Master_accounts");
    this.transactions = trfio.readFile();
    this.accounts = actio.readFile();
  }
}
