import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

public class AdminCheckTest{
  TransactionProcessor transProc = new TransactionProcessor("MasterAccount.txt", "adminTransactions.trf");
  @Test
  public void adminCheckPass(){
    transProc.process();
    assertEquals(true,transProc.adminCheck());
  }

  @Test
  public void adminCheckFail(){
    assertEquals(false,transProc.adminCheck());
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(AdminCheckTest.class);
  }
}
