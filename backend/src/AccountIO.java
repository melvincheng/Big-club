import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

public class AccountIO{
  private String filename;

  /**
  * Constructor for the IO
  * sets the file to read
  */
  public AccountIO(String filename) {
      this.filename = filename;
  }

  public Map<Integer,Account> readFile(){
    try{
      Map<Integer,Account> accounts = new HashMap<Integer,Account>();
      String input;
      String token;

      // read in account file
      BufferedReader br = new BufferedReader(new FileReader(this.filename));

      while((input = br.readLine()) != null){
        int id;
        String name;
        boolean enabled;
        float balance;
        boolean student;
        int trans;

        token = input.substring(0,5);
        id = Integer.parseInt(token);

        name = input.substring(6,26);

        token = input.substring(27,27);
        if((token.compareTo("A")) == 0){
          enabled = true;
        }else{
          enabled = false;
        }

        token = input.substring(29,36);
        balance = Float.parseFloat(token);

        token = input.substring(38,38);
        if((token.compareTo("N")) == 0){
          student = false;
        }else{
          student = true;
        }

        token = input.substring(41,44);
        trans = Integer.parseInt(token);
        Account newAccount = new Account(id,name,balance,enabled,student,trans);
        accounts.put(id,newAccount);
      }

      // put individual accounts into account objects
      // put account to its corresponding account ID
      // return vector at EOF

      return accounts;
    }catch(Exception e){
      return null;
    }
  }
}
