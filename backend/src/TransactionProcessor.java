import java.util.Vector;

public class TransactionProcessor{
  Map<Int,Account> accounts;
  Vector<Transaction> transactions;

  /**
   * Constructor
   */
  public TransactionProcessor(){
    TransactionIO trfio = new TransactionIO("test.trf");
    AccountIO actio = new AccountIO("Master_accounts.txt");
    this.transactions = trfio.readFile();
    this.accounts = actio.readFile();


  }

  private 
}
