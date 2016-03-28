import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
public class TransactionTest{

  // test the method for getting the transaction code
  @Test
  public void getTransCodeTest(){
    Transaction tranTest = new Transaction((byte)2,"Rick",10002,200f,"");
    assertEquals(2,tranTest.getTransCode());
  }

  // test the method for getting the name on the transaction
  @Test
  public void getTransNameTest(){
    Transaction tranTest = new Transaction((byte)2,"Rick",10002,200f,"");
    assertEquals("Rick",tranTest.getTransName());
  }

  // test the method for getting the account Id on the transaction
  @Test
  public void getTransIdTest(){
    Transaction tranTest = new Transaction((byte)2,"Rick",10002,200f,"");
    assertEquals(10002,tranTest.getTransId());
  }

  // test the method for getting the funds involved in the transaction
  @Test
  public void getValueTest(){
    Transaction tranTest = new Transaction((byte)2,"Rick",10002,200f,"");
    assertEquals(200f,tranTest.getValue(),0.0);
  }

  // test the method for getting the misc information
  @Test
  public void getMiscTest(){
    Transaction tranTest = new Transaction((byte)2,"Rick",10002,200f,"TV");
    assertEquals("TV",tranTest.getMisc());
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(TransactionTest.class);
  }
}
