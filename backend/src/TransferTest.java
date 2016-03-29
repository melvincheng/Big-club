import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

public class TransferTest{
  TransactionProcessor transProc = new TransactionProcessor("MasterAccount.txt", "transactions.trf");

  // if transfer goes through succesfully
  @Test
  public void TransferSuccess(){
    byte code1 = 2;
    byte code2 = 2;
    Transaction trans1 = new Transaction(code1,"Billy-Bob Thornton",10001,275.1f,"");
    Transaction trans2 = new Transaction(code2,"Billy-Bob Thornton",10002,275.0f,"");
    boolean test = transProc.transfer(trans1,trans2);
    assertEquals(true,test);
  }

  // if transfer goes through unsuccesfully
  @Test
  public void TransferFail(){
    byte code1 = 2;
    byte code2 = 2;
    Transaction trans1 = new Transaction(code1,"Billy-Bob Thornton",10001,600.1f,"");
    Transaction trans2 = new Transaction(code2,"Billy-Bob Thornton",10002,275.0f,"");
    boolean test = transProc.transfer(trans1,trans2);
    assertEquals(false,test);
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(TransferTest.class);
  }
}
