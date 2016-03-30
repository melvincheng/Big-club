import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class CreateTest{
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
  
  // test a valid account
  @Test
  public void createSuccess(){
    transProc.process();
    byte code = 5;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",0,300.20f,"");
    boolean test = transProc.create(trans);
    assertEquals(true,test);
  }

  // test an invalid name
  @Test
  public void createBadName(){
    transProc.process();
    byte code = 5;
    Transaction trans = new Transaction(code,"11111",10001,300.20f,"");
    boolean test = transProc.create(trans);
    assertEquals(false,test);
    assertEquals("ERROR: Name contains invalid characters: ", outContent.toString());
  }

  // attempt to create when not logged in as an admin
  @Test
  public void createNotAdmin(){
    byte code = 5;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",0,300.20f,"");
    boolean test = transProc.create(trans);
    assertEquals(false,test);
    assertEquals("ERROR: User was not an admin: ", outContent.toString());
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(CreateTest.class);
  }
}
