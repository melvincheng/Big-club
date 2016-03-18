import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

public class AccountIO{
  private String filename;

  /**
  * @brief Constructor for the IO
  * sets the accounts file to read
  * @param filename the name of the accounts file to read
  */
  public AccountIO(String filename) {
      this.filename = filename;
  }

  /**
  * @brief reads the accounts file and returns a map of the accounts
  * map has keys relating to account id, values are accounts themselves
  * @return accounts a map of the accounts from the file
  */
  public Map<Integer,Account> readFile(){
    try{
      // initialize our map object to hold our accounts
      Map<Integer,Account> accounts = new HashMap<Integer,Account>();

      // helper variables for file parsing
      String input;
      String token;

      // initialize our file reader
      BufferedReader br = new BufferedReader(new FileReader(this.filename));

      /*
       * loop through each entry within our master accounts file
       * parse variables from the entries and add them to the map
       */
      while((input = br.readLine()) != null){
        int id;
        String name;
        boolean enabled;
        float balance;
        boolean student;
        int trans;

        // parse the account id
        token = input.substring(0,5);
        id = Integer.parseInt(token);

        // parse the account holder's name
        name = input.substring(6,26);

        // parse the enabled flag
        token = input.substring(27,27);
        if((token.compareTo("A")) == 0){
          enabled = true;
        }else{
          enabled = false;
        }

        // parse the account balance
        token = input.substring(29,36);
        balance = Float.parseFloat(token);

        // parse the plan flag
        token = input.substring(38,38);
        if((token.compareTo("N")) == 0){
          student = false;
        }else{
          student = true;
        }

        // parse the total amount of transactions
        token = input.substring(41,44);
        trans = Integer.parseInt(token);

        // create an account object storing parsed information
        // add it to the map
        Account newAccount = new Account(id,name,balance,enabled,student,trans);
        accounts.put(id,newAccount);
      }

      // return the map
      return accounts;
    }catch(Exception e){
      System.err.println("Error in opening file to read");
      return null;
    }
  }

  public void writeFile(){
    /* TODO: implement write file
     * retrieve changed accounts
     * write to a new master accounts file
     * write to a new current accounts file
     */
    try{
      // declare file variables
      // TODO: remove the temp when implemented
      File master = new File("MasterAccountTemp.txt");
      File current = new File("CurrentAccountsTemp.txt");

      // create writer objects
      PrintWriter masterpw = new PrintWriter(master);
      PrintWriter currentpw = new PrintWriter(current);

      // dummy output
      masterpw.println("this is master account file, please implement me");
      currentpw.println("this is current accounts file, please implement me");
    }catch (Exception e){
      System.err.println("Could not open file");
    }
  }
}
