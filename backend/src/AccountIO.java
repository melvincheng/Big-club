import java.util.Vector;
import java.util.Map;

public class AccountIO{
  private String filename;

  /**
  * Constructor for the IO
  * sets the file to read
  */
  public AccountIO(String filename) {
      this.filename = filename;
  }

  public Map<Int,Account> readFile(){
      Map<Int,Account> accounts = new HashMap<Int,Account>();

  // read in account file
  // put individual accounts into account objects
  // put account to its corresponding account ID
  // return vector at EOF

      return accounts;
  }
}
