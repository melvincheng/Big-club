import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class AdminCheckTest{
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

  // Test if the user is an admin when the user is logged in as an admin
  @Test
  public void adminCheckPass(){
    transProc.process();
    assertEquals(true,transProc.adminCheck());
  }

  // Test if the user is not an admin whent he user is not logged in as an admin
  @Test
  public void adminCheckFail(){
    assertEquals(false,transProc.adminCheck());
    assertEquals("ERROR: User was not an admin: ", outContent.toString());
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(AdminCheckTest.class);
  }
}
