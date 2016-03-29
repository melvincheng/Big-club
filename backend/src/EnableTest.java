import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

public class EnableTest{
  TransactionProcessor transProc = new TransactionProcessor("MasterAccount.txt", "adminTransactions.trf");

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
  }

  // enable an enabled account
  @Test
  public void enableEnabled(){
    transProc.process();
    byte code = 9;
    Transaction trans = new Transaction(code,"Billy-Bob Thornton",10001,0,"");
    boolean test = transProc.enable(true,trans);
    assertEquals(false,test);
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(EnableTest.class);
  }
}
