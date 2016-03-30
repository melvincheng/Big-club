import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class TransferTest{
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

  // if the first account has insufficient funds
  @Test
  public void TransferNoFunds(){
    byte code1 = 2;
    byte code2 = 2;
    Transaction trans1 = new Transaction(code1,"Billy-Bob Thornton",10001,600.1f,"");
    Transaction trans2 = new Transaction(code2,"Billy-Bob Thornton",10002,275.0f,"");
    boolean test = transProc.transfer(trans1,trans2);
    assertEquals(false,test);
    assertEquals("ERROR: Account has insufficient funds: ",outContent.toString());
  }

  // if the first account is invalid
  @Test
  public void transferInvalidAcc1(){
    byte code1 = 2;
    byte code2 = 2;
    Transaction trans1 = new Transaction(code1,"Billy-Bob Thornton",10010,600.1f,"");
    Transaction trans2 = new Transaction(code2,"Billy-Bob Thornton",10002,275.0f,"");
    boolean test = transProc.transfer(trans1,trans2);
    assertEquals(false,test);
    assertEquals("ERROR: Account does not exist: ", outContent.toString());
  }

  // if the second account is invalid
  @Test
  public void transferInvalidAcc2(){
    byte code1 = 2;
    byte code2 = 2;
    Transaction trans1 = new Transaction(code1,"Billy-Bob Thornton",10001,600.1f,"");
    Transaction trans2 = new Transaction(code2,"Billy-Bob Thornton",10010,275.0f,"");
    boolean test = transProc.transfer(trans1,trans2);
    assertEquals(false,test);
    assertEquals("ERROR: Account does not exist: ", outContent.toString());
  }
  
  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(TransferTest.class);
  }
}
