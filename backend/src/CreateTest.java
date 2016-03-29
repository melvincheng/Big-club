import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

public class CreateTest{
  TransactionProcessor transProc = new TransactionProcessor("MasterAccount.txt", "transactions.trf");

  // test a valid account
  @Test
  public void createSuccess(){
    byte code = 5;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",0,300.20f,"");
    boolean test = transProc.create(trans);
    assertEquals(true,test);
  }

  // test an invalid name
  @Test
  public void createBadName(){
    byte code = 5;
    Transaction trans = new Transaction(code,"11111",10001,300.20f,"");
    boolean test = transProc.create(trans);
    assertEquals(false,test);
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(CreateTest.class);
  }
}
