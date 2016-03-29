/**
 * This class reads a transaction file and parses each transaction in the transaction file
 */
import java.util.Vector;
import java.io.*;

/*
 * This class reads in the transaction file outputted from the front end
 */
public class TransactionIO{
  String filename; // file containing the frontend's transactions

  /**
   * @brief Constructor takes in a string and sets it as the file name
   * for the transaction file
   * @param filename the name of the transaction file to read
   */
  public TransactionIO(String filename){
    this.filename = filename;
  }

  /**
   * @brief reads the transaction file and returns a vector of accounts
   * @return transactions a vector of the transactiosn from the file
   */
  public Vector<Transaction> readFile(){
    try{
      // initialize our vector object to hold our accounts
      Vector<Transaction> transactions = new Vector<Transaction>();

      // helper variables for file parsing
      String input;
      String token;

      // initialize our file reader
      BufferedReader br = new BufferedReader(new FileReader(this.filename));

      /*
       * loop through each entry within our transaction file
       * parse variables from entries and add them to the vector
       */
      while((input = br.readLine()) != null){
        byte code;
        String name;
        int id;
        float value;
        String misc;

        // parse the transaction code
        token = input.substring(0,2);
        code = Byte.parseByte(token);

        // parse the name on the transaction
        name = input.substring(3,23).trim();

        // parse the account id
        token = input.substring(24,29);
        id = Integer.parseInt(token);

        // parse the value of funds involved in the transaction
        token = input.substring(30,38);
        value = Float.parseFloat(token);

        // parse any miscelaneous information
        try{
          misc = input.substring(39,input.length());
        }catch(Exception e){
          misc = "";
        }

        // create a transaction object storing parsed information
        // add it to the vector
        Transaction newTrans = new Transaction(code,name,id,value,misc);
        transactions.add(newTrans);
      }
      // return the vector
      return transactions;
    }catch (Exception e){
      System.err.println("Error: "+e+" with "+this.filename);
      return null;
    }
  }
}
