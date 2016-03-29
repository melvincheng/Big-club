import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

public class PaybillTest{
  TransactionProcessor transProc = new TransactionProcessor("MasterAccount.txt", "transactions.trf");

  // test a valid company
  @Test
  public void paybillSuccess(){
    byte code = 3;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10001,300.20f,"TV");
    boolean test = transProc.paybill(trans);
    assertEquals(true,test);
  }

  // test an invalid company
  @Test
  public void paybillNoCompany(){
    byte code = 3;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10001,300.20f,"eee");
    boolean test = transProc.paybill(trans);
    assertEquals(false,test);
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(PaybillTest.class);
  }
}
