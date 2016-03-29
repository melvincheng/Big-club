import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class DeleteTest{

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
  public void deleteSuccess(){
    transProc.process();
    byte code = 6;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10001,0,"");
    boolean test = transProc.delete(trans);
    assertEquals(true,test);
  }

  // try to delete an account that doesn't exist
  @Test
  public void deleteNonexist(){
    transProc.process();
    byte code = 6;
    Transaction trans = new Transaction(code,"11111",20001,0,"");
    boolean test = transProc.delete(trans);
    assertEquals(false,test);
    assertEquals("ERROR: Account does not exist: ", outContent.toString());
  }

  // try to delete an account while not an admin
  @Test
  public void deleteNotAdmin(){
    byte code = 6;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10001,0,"");
    boolean test = transProc.delete(trans);
    assertEquals(false,test);
    assertEquals("ERROR: User was not an admin: ", outContent.toString());
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(DeleteTest.class);
  }
}
