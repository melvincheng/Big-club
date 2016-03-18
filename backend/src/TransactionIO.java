/**
 * This class reads a transaction file and parses each transaction in the transaction file
 */
import java.util.Vector;

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
    Vector<Transaction> transactions = new Vector<Transaction>();

    // read in transaction file
    // put individual transactions into transaction objects
    // push back transactions onto the vector
    // return vector at EOF

    return transactions;
  }
}
