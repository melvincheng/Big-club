import static org.junit.Asser.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
public class AccountTest{

  // test method to get the account id
  @Test
  public void getIdTest(){
    Account AccTest = new Account(10002,"Rick",200.20,true,true,0);
    assertEquals(10002,AccTest.getId());
  }

  // test method to get the account holder's name
  @Test
  public void getNameTest(){
    Account AccTest = new Account(10002,"Rick",200.20,true,true,0);
    assertEquals("Rick",AccTest.getName());
  }

  // test method to get the account balance
  @Test
  public void getBalanceTest(){
    Account AccTest = new Account(10002,"Rick",200.20,true,true,0);
    assertEquals(200.20,AccTest.getBalance());
  }

  // test method to set the account balance
  @Test
  public void setBalanceTest(){
    Account AccTest = new Account(10002,"Rick",200.20,true,true,0);
    AccTest.setBalance(105.50);
    assertEquals(100.5,AccTest.getBalance());
  }

  // test method for testing if account is enabled
  @Test
  public void isEnabledTest(){
    Account AccTest = new Account(10002,"Rick",200.20,true,true,0);
    assertEquals(true,AccTest.isEnabled());
  }

  // test method to test if account can be set to disabled
  @Test
  public void setEnabledTest(){
    Account AccTest = new Account(10002,"Rick",200.20,true,true,0);
    AccTest.setEnabled(false);
    assertEquals(false,AccTest.isEnabled());
  }


  // test method to test if account is a student account
  @Test
  public void IsStudentTest(){
    Account AccTest = new Account(10002,"Rick",200.20,true,true,0);
    assertEquals(true,AccTest.isStudent());
  }


  // test method to test if account can be set to student
  @Test
  public void setStudentTest(){
    Account AccTest = new Account(10002,"Rick",200.20,true,true,0);
    AccTest.setStudent(false);
    assertEquals(false,AccTest.isStudent());
  }


  // test method to get total transactions on account
  @Test
  public void setEnabledTest(){
    Account AccTest = new Account(10002,"Rick",200.20,true,true,0);
    assertEquals(0,AccTest.getCount());
  }

  // test method to increment total transactions
  @Test
  public void setEnabledTest(){
    Account AccTest = new Account(10002,"Rick",200.20,true,true,0);
    AccTest.incCount();
    assertEquals(1,AccTest.getCount());
    AccTest.incCount();
    assertEquals(2,AccTest.getCount());
  }


  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(AccountTest.class);
  }
}
