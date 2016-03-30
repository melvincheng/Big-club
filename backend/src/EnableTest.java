import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class EnableTest{
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

  // enable a disabled account
  @Test
  public void enableSuccess(){
    transProc.process();
    byte code = 9;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10003,0,"");
    boolean test = transProc.enable(true,trans);
    assertEquals(true,test);
  }

  // disable an enabled account
  @Test
  public void disableSuccess(){
    transProc.process();
    byte code = 7;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10001,0,"");
    boolean test = transProc.enable(false,trans);
    assertEquals(true,test);
  }


  // disable a disabled account
  @Test
  public void disableDisabled(){
    transProc.process();
    byte code = 7;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10003,0,"");
    boolean test = transProc.enable(false,trans);
    assertEquals(false,test);
    assertEquals("ERROR: Account is already disabled: ", outContent.toString());
  }

  // enable an enabled account
  @Test
  public void enableEnabled(){
    transProc.process();
    byte code = 9;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10001,0,"");
    boolean test = transProc.enable(true,trans);
    assertEquals(false,test);
    assertEquals("ERROR: Account is already enabled: ", outContent.toString());
  }

  // attempt to enable/disable when not an admin
  @Test
  public void enableNotAdmin(){
    byte code = 9;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10001,0,"");
    boolean test = transProc.enable(true,trans);
    assertEquals(false,test);
    assertEquals("ERROR: User was not an admin: ", outContent.toString());
  }

  // attempt to enable/disable an account with an invalid Id 
  @Test
  public void enableInvalidId(){
    transProc.process();
    byte code = 9;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10011,0,"");
    boolean test = transProc.enable(true,trans);
    assertEquals(false,test);
    assertEquals("ERROR: Account does not exist: ", outContent.toString());
  }

  // attempt to enable/disable an account with an invalid account holder name
  @Test
  public void enableInvalidName(){
    transProc.process();
    byte code = 9;
    Transaction trans = new Transaction(code,"Rick",10001,0,"");
    boolean test = transProc.enable(true,trans);
    assertEquals(false,test);
    assertEquals("ERROR: Account holder name is invalid: ", outContent.toString());
  }
  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(EnableTest.class);
  }
}
