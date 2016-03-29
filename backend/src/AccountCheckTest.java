import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

import java.io.*;
public class AccountCheckTest{
  TransactionProcessor transProc = new TransactionProcessor("MasterAccount.txt", "transactions.trf");

  // Test account check when it's used correctly
  @Test
  public void accountCheckSuccess(){
    boolean test = transProc.accountCheck("Billy-Bob Thornton",10001);
    assertEquals(true,test);
  }

  // Account check when account doesn't exist
  @Test
  public void AccountCheckNoId(){
    boolean test = transProc.accountCheck("Billy-Bob Thornton",42);
    assertEquals(false,test);
  }

  // Account check when account holder name is invalid
  @Test
  public void AccountCheckNoName(){
    boolean test = transProc.accountCheck("Slick Rick",10001);
    assertEquals(false, test);
  }

  // account check when account is disabled
  @Test
  public void AccountCheckNotEnabled(){
    boolean test = transProc.accountCheck("Billy-Bob Thornton",10003);
    assertEquals(false,test);
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(TransactionProcessorTest.class);
  }
}
