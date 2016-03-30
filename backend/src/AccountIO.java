/**
* This class is used to reads the master account file and writes a new master account file
*/

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
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
        name = input.substring(6,26).trim();

        // parse the enabled flag
        token = input.substring(27,28);
        if((token.compareTo("A")) == 0){
          enabled = true;
        }else{
          enabled = false;
        }

        // parse the account balance
        token = input.substring(29,36);
        balance = Float.parseFloat(token);

        // parse the plan flag
        token = input.substring(38,39);
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
      System.err.println("ERROR: Cannot open file to read");
      return null;
    }
  }

  public void writeFile(Map<Integer,Account >newAccounts){
    /*
    * retrieve changed accounts
    * write to a new master accounts file
    * write to a new current accounts file
    */
    try{
      // declare file variables
      File master = new File("MasterAccount.txt");
      File current = new File("CurrentAccounts.txt");

      // create writer objects
      PrintWriter currentpw = new PrintWriter(current);
      PrintWriter masterpw = new PrintWriter(master);
      Account currAccount;

      // get set of accounts in system
      Set<Integer> accountIds = newAccounts.keySet();
      String curroutf; // formatted string for current accounts file
      String mastoutf; // formatted string for master accounts file

      String currEOF = "";
      String mastEOF = "";

      for(int key:accountIds){
        currAccount = newAccounts.get(key);
        if(currAccount != null){
          String active;
          String plan;
          if(currAccount.isEnabled()){
            active = "A";
          }else{
            active = "D";
          }

          if(currAccount.isStudent()){
            plan = "S";
          }else{
            plan = "N";
          }

          // create formatted string for current accounts file
          curroutf = String.format("%05d %20s %1s %08.2f %1s",
          currAccount.getId(),
          currAccount.getName(),
          active,
          currAccount.getBalance(),
          plan);

          // create formatted string for master accounts file
          mastoutf = String.format("%05d %20s %1s %08.2f %1s %04d",
          currAccount.getId(),
          currAccount.getName(),
          active,
          currAccount.getBalance(),
          plan,
          currAccount.getCount());

          // print to file if it's NOT end of file account
          if(currAccount.getId() != 0){
          currentpw.println(curroutf);
          masterpw.println(mastoutf);
        }else{
            currEOF = curroutf;
            mastEOF = mastoutf;
          }
        }
      }

      //print the end of file
      currentpw.println(currEOF);
      masterpw.println(mastEOF);

      currentpw.close();
      masterpw.close();
    }catch (Exception e){
      
    }
  }
}
