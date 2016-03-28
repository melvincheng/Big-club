import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExternalResource;
public class TransactionProcessorTest{
  @Rule
  public ResourceFile masterFile = new ResourceFile("/MasterAccount.txt");
  public ResourceFile transactions = new ResourceFile("/transactions.trf");

  @Test
  public void accountCheck1(){
    TransactionProcessor transProc = new TransactionProcessor(masterFile, transactions);
    // accountCheck(tranTest.getTransName(), tranTest.getTransId());
  }
    public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(TransactionProcessorTest.class);
  }
}