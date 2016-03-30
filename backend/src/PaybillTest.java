import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class PaybillTest{
  TransactionProcessor transProc = new TransactionProcessor("MasterAccount.txt", "transactions.trf");
  final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void setupStream(){
      System.setOut(new PrintStream(outContent));
    }
  @After
  public void cleanStream(){
    System.setOut(null);
  }

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
    assertEquals("ERROR: Invalid company: ",outContent.toString());
  }

  // test with an account with insufficient funds
  @Test
  public void paybillNoFunds(){
    byte code = 3;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10001,1000.00f,"TV");
    boolean test = transProc.paybill(trans);
    assertEquals(false,test);
    assertEquals("ERROR: Account has insufficient funds: ",outContent.toString());
  }


  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(PaybillTest.class);
  }
}
