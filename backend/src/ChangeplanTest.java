import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class ChangeplanTest{
  TransactionProcessor transProc = new TransactionProcessor("MasterAccount.txt", "adminTransactions.trf");
  final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void setupStream(){
      System.setOut(new PrintStream(outContent));
    }
  @After
  public void cleanStream(){
    System.setOut(null);
  }

  // Changeplan function successfully changes the plan
  @Test
  public void changeplanTestPass(){
    transProc.process();
    byte code = 8;
    Transaction tranTest = new Transaction(code,"Billy-Bob Thornton",10002,200f,"");
    assertEquals(true, transProc.changeplan(tranTest));
  }

  // Trying to change plan of an invalid account
  @Test
  public void changeplanInvalidAcc(){
    transProc.process();
    byte code = 8;
    Transaction tranTest = new Transaction(code,"Rick",10009,200f,"");
    assertEquals(false, transProc.changeplan(tranTest));
    assertEquals("ERROR: Account does not exist: ", outContent.toString());
  }

  // Trying to change plan when user is not an adminn
  @Test
  public void changeplanNotAdmin(){
    byte code = 8;
    Transaction tranTest = new Transaction(code,"Billy-Bob Thornton",10002,200f,"");
    assertEquals(false, transProc.changeplan(tranTest));
    assertEquals("ERROR: User was not an admin: ", outContent.toString());
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(ChangeplanTest.class);
  }
}