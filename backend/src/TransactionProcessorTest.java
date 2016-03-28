import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

import java.io.*;
public class TransactionProcessorTest{
  TransactionProcessor transProc = new TransactionProcessor("MasterAccount.txt", "transactions.trf");
  @Test
  public void accountCheck1(){
  }

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter(TransactionProcessorTest.class);
  }
}