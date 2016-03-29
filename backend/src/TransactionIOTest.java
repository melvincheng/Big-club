import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import java.util.Vector;
public class TransactionIOTest{


  // test method that reads the transaction file
  @Test
  public void readFileTestSuccess(){
    Vector<Transaction> expectTrans = new Vector<Transaction>();
    Transaction trans1 = new Transaction((byte)10, "Billy-Bob Thornton", 0, 0.0f, "S");
    Transaction trans2 = new Transaction((byte)2, "Billy-Bob Thornton", 10001, 275.10f, "");
    Transaction trans3 = new Transaction((byte)2, "Billy-Bob Thornton", 10002, 275.00f, "");
    Transaction trans4 = new Transaction((byte)0, "Billy-Bob Thornton", 0, 0.0f, "S");
    expectTrans.add(trans1);
    expectTrans.add(trans2);
    expectTrans.add(trans3);
    expectTrans.add(trans4);
    TransactionIO transIO = new TransactionIO("transactions.trf");
    Vector<Transaction> actualTrans = transIO.readFile();
    for(int i=0;i<expectTrans.size();i++){
      assertEquals(expectTrans.get(i).getTransCode(),actualTrans.get(i).getTransCode());
      assertEquals(expectTrans.get(i).getTransName(),actualTrans.get(i).getTransName());
      assertEquals(expectTrans.get(i).getTransId(),actualTrans.get(i).getTransId());
      assertEquals(expectTrans.get(i).getValue(),actualTrans.get(i).getValue(),0.0f);
      assertEquals(expectTrans.get(i).getMisc(),actualTrans.get(i).getMisc());
    }
  }

  @Test
  public void readFileTestFail(){
    TransactionIO transIO = new TransactionIO("failTransactions.trf");
    assertEquals(null, transIO.readFile());
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(TransactionIOTest.class);
  }
}
