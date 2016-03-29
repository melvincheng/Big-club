import static org.junit.Asser.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import java.io.*
import java.util.Vector;
public class TransactionIOTest{

  // test method to get the account id
  @Test
  public void TransactionIOTest(){
    String copyOver;
    assertEquals("Must be the same", "transactions.trf", copyOver.TransactionIO("transactions.trf"));
  }

  @Test
  public void readFileTest(){
    String readTest;
    Vector<Transaction> real = new Vector<Transaction>();
    Transaction trans1 = new Transaction(10, "Billy-Bob Thorton", 00000, 00000.00, "S");
    Transaction trans2 = new Transaction(02, "Billy-Bob Thorton", 10001, 00275.10, "");
    Transaction trans3 = new Transaction(02, "Billy-Bob Thorton", 00000, 00275.00, "");
    Transaction trans4 = new Transaction(00, "Billy-Bob Thorton", 00000, 00000.00, "S");
    real.add(trans1);
    real.add(trans2);
    real.add(trans3);
    real.add(trans4);
    readTest.TransactionIO("transactions.trf");
    assertEquals(real, transTest.readFile());
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(TransactionTest.class);
  }
}
