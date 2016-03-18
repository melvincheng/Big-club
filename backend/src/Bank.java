public class Bank {
  public static void main(String[] args) {
  	TransactionProcessor transProcess = new TransactionProcessor(args[0], args[1]);
  	transProcess.process();
  }
}