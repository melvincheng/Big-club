/**
 * This class reads a transaction file and parses each transaction in the transaction file
 */
import java.util.Vector;
import java.io.*;

/*
 * This class reads in the transaction file outputted from the front end
 */
public class TransactionIO{
  private String filename; // file containing the frontend's transactions

  /**
   * Constructor takes in a string and sets it as the file name
   */
  public TransactionIO(String filename){
    this.filename = filename;
  }

  /**
   * readFile reads the file and parses the data
   */
  public Vector<Transaction> readFile(){
    try{
      Vector<Transaction> transactions = new Vector<Transaction>();
      String input;
      String token;

      // read in transaction file
      BufferedReader br = new BufferedReader(new FileReader(this.filename));

      while((input = br.readLine()) != null){
        byte code;
        String name;
        int id;
        float value;
        String misc;

        token = input.substring(0,1);
        code = Byte.parseByte(token);

        name = input.substring(3,23);

        token = input.substring(24,29);
        id = Integer.parseInt(token);

        token = input.substring(30,38);
        value = Float.parseFloat(token);

        misc = input.substring(39,input.length());

        Transaction newTrans = new Transaction(code,name,id,value,misc);
        transactions.add(newTrans);
      }
      // put individual transactions into transaction objects
      // push back transactions onto the vector
      // return vector at EOF

      return transactions;
    }catch (Exception e){
      return null;
    }
  }
}
