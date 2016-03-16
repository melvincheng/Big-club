package sagyou.jikan;
import java.util.Vector;
/*
 * This class reads in the transaction file outputted from the front end
 */
public class TransactionIO{
  private String filename; // file containing the frontend's transactions

  public TransactionIO(String filename){
    this.filename = filename;
  }

  public Vector<Transaction> readFile(){
    Vector<Transaction> transactions = new Vector<Transaction>();

    // read in transaction file
    // put individual transactions into transaction objects
    // push back transactions onto the vector
    // return vector at EOF

    return transactions;
  }

}
