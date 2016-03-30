import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import java.io.*;
public class AccountIOTest{
  final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  @Before
  public void setupStream(){
    System.setErr(new PrintStream(outContent));
  }
  @After
  public void cleanStream(){
    System.setErr(null);
  }
  // read accounts file correctly
  @Test
  public void readFileTestSuccess(){
    Map<Integer,Account> expectAccounts = new HashMap<Integer,Account>();
    Account acc1 = new Account(10001, "Billy-Bob Thornton", 501.0f, true, false,0);
    Account acc2 = new Account(10002, "Billy-Bob Thornton", 300.0f, true,false,0);
    Account acc3 = new Account(10003, "Billy-Bob Thornton", 0.0f, false,false,0);
    Account acc4 = new Account(99999, "Billy-Bob Thornton", 0.0f, true,false,0);
    Account acc5 = new Account(0, "END OF FILE", 0.0f, false,false,0);

    expectAccounts.put(acc1.getId(),acc1);
    expectAccounts.put(acc2.getId(),acc2);
    expectAccounts.put(acc3.getId(),acc3);
    expectAccounts.put(acc4.getId(),acc4);
    expectAccounts.put(acc5.getId(),acc5);

    AccountIO accIO = new AccountIO("MasterAccountTest.txt");
    Map<Integer,Account> actualAccounts = accIO.readFile();
    Set<Integer> accountIds = expectAccounts.keySet();

    for(int key:accountIds){
      assertEquals(expectAccounts.get(key).getId(),actualAccounts.get(key).getId());
      assertEquals(expectAccounts.get(key).getName(),actualAccounts.get(key).getName());
      assertEquals(expectAccounts.get(key).getBalance(),actualAccounts.get(key).getBalance(),0.0f);
      assertEquals(expectAccounts.get(key).isEnabled(),actualAccounts.get(key).isEnabled());
      assertEquals(expectAccounts.get(key).isStudent(),actualAccounts.get(key).isStudent());
      assertEquals(expectAccounts.get(key).getCount(),actualAccounts.get(key).getCount());
    }
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(AccountIOTest.class);
  }
}
