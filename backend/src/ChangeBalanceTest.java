import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class ChangeBalanceTest{
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

  // Correct usage of changeBalance deposit
  @Test
  public void changeBalanceDepositSuccess(){
    byte code = 4;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10001,20,"");
    boolean test = transProc.changeBalance(true,false,trans);
    assertEquals(true,test);
  }

  // Correct usage of changeBalance withdraw
  @Test
  public void changeBalanceWithdrawSuccess(){
    byte code = 1;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10001,20,"");
    boolean test = transProc.changeBalance(false,false,trans);
    assertEquals(true,test);
  }

  // Trying to withdraw too many funds
  @Test
  public void changeBalanceOverdraw(){
    byte code = 1;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10002,305,"");
    boolean test = transProc.changeBalance(false,false,trans);
    assertEquals(false,test);
    assertEquals("ERROR: Account has insufficient funds: ",outContent.toString());
  }

  // trying to deposit too little funds
  @Test
  public void changeBalanceUnderfund(){
    byte code = 4;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10004,0.01f,"");
    boolean test = transProc.changeBalance(true,false,trans);
    assertEquals(false,test);
    assertEquals("ERROR: Account has insufficient funds: ",outContent.toString());
  }

  // trying withdraw/deposit with a student document
  @Test
  public void changeBalanceStudent(){
    byte code = 4;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10100,20,"");
    boolean test = transProc.changeBalance(true,false,trans);
    assertEquals(true,test);
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(ChangeBalanceTest.class);
  }
}
