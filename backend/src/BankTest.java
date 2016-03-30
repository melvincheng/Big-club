import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class BankTest{
  final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void setupStream(){
    System.setOut(new PrintStream(outContent));
  }
  @After
  public void cleanStream(){
    System.setOut(null);
  }
  // Testing successful main
  @Test
  public void bankSuccess(){
    Bank.main(new String[] {"MasterAccount.txt", "transactions.trf"});
    assertEquals("Backend start\nExecution complete\n",outContent.toString());
  }

  // Testing a main without sufficient arguments
  @Test
  public void bankFail(){
    Bank.main(new String[]{});
    assertEquals("Backend start\nERROR: Invalid number of files\n", outContent.toString());
  }

  
  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(BankTest.class);
  }
}
