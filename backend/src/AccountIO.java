import java.util.Vector;

public class AccountIO{
  private String filename;

  /**
  * Constructor for the IO
  * sets the file to read
  */
  public AccountIO(String filename) {
      this.filename = filename;
  }

  public Vector<Account> readFile(){
      Vector<Account> accounts = new Vector<Account>();

  // read in transaction file
  // put individual transactions into transaction objects
  // push back transactions onto the vector
  // return vector at EOF

      return accounts;
  }
}
