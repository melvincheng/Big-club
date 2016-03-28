import static org.junit.Asser.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
public class TransactionTest{

  // test the method for getting the transaction code
  @Test
  public void getTransCodeTest(){
    Transaction tranTest = new Transaction(2,"Rick",10002,200,"");
    assertEquals(2,tranTest.getTransCode());
  }

  // test the method for getting the name on the transaction
  @Test
  public void getTransNameTest(){
    Transaction tranTest = new Transaction(2,"Rick",10002,200,"");
    assertEquals("Rick",tranTest.getTransName());
  }

  // test the method for getting the account Id on the transaction
  @Test
  public void getTransIdTest(){
    Transaction tranTest = new Transaction(2,"Rick",10002,200,"");
    assertEquals(10002,tranTest.getTransId());
  }

  // test the method for getting the funds involved in the transaction
  @Test
  public void getValueTest(){
    Transaction tranTest = new Transaction(2,"Rick",10002,200,"");
    assertEquals(200,tranTest.getValue());
  }

  // test the method for getting the misc information
  @Test
  public void getMiscTest(){
    Transaction tranTest = new Transaction(2,"Rick",10002,200,"TV");
    assertEquals("TV",tranTest.getMisc());
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(TransactionTest.class);
  }
}
