import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

public class ProcLoopTest{

  // test 0 iterations of the loop
  @Test
  public void zeroProc(){
    TransactionProcessor transProc = new TransactionProcessor("../../resources/MasterAccount.txt", "../../resources/transactionsNone.trf");
    assertEquals(0,transProc.transactions.size());
  }

  // test only 1 iteration of the loop
  @Test
  public void oneProc(){
    TransactionProcessor transProc = new TransactionProcessor("../../resources/MasterAccount.txt", "../../resources/transactionsOnce.trf");
    assertEquals(1,transProc.transactions.size());
  }

  // test multiple iterations of the loop
  @Test
  public void MultiProc(){
    TransactionProcessor transProc = new TransactionProcessor("../../resources/MasterAccount.txt", "../../resources/transactions.trf");
    assertEquals(4,transProc.transactions.size());
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(ProcLoopTest.class);
  }
}
