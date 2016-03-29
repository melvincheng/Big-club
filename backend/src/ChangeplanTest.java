import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
public class ChangeplanTest{
  @Test
  public void changeplanTestPass(){
    TransactionProcessor transProc = new TransactionProcessor("MasterAccount.txt", "transactions.trf");
    Transaction tranTest = new Transaction((byte)2,"Billy-bob Thornton",10002,200f,"");
    assertEquals(true, transProc.changeplan(tranTest));
  }

  @Test
  public void changeplanTestFail(){
    TransactionProcessor transProc = new TransactionProcessor("MasterAccount.txt", "transactions.trf");
    Transaction tranTest = new Transaction((byte)2,"Rick",10002,200f,"");
    assertEquals(false, transProc.changeplan(tranTest));  
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(ChangeplanTest.class);
  }
}