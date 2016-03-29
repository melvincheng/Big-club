import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
public class ChangeplanTest{
  TransactionProcessor transProc = new TransactionProcessor("MasterAccount.txt", "adminTransactions.trf");
  @Test
  public void changeplanTestPass(){
    transProc.process();
    byte code = 8;
    Transaction tranTest = new Transaction(code,"Billy-Bob Thornton",10002,200f,"");
    assertEquals(true, transProc.changeplan(tranTest));
  }

  @Test
  public void changeplanTestFail(){
    transProc.process();
    byte code = 8;
    Transaction tranTest = new Transaction(code,"Rick",10009,200f,"");
    assertEquals(false, transProc.changeplan(tranTest));  
  }

  @Test
  public void changeplanNotAdminTest(){
    byte code = 8;
    Transaction tranTest = new Transaction(code,"Rick",10009,200f,"");
    assertEquals(false, transProc.changeplan(tranTest));
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(ChangeplanTest.class);
  }
}