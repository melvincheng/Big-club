import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

public class DeleteTest{
  TransactionProcessor transProc = new TransactionProcessor("MasterAccount.txt", "adminTransactions.trf");

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
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(DeleteTest.class);
  }
}
