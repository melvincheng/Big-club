/*************************\
* @author Santiago Bonada
* @author Adam Beckett May
* @author Melvin Cheng
* @version 1.0
* @date 18/03/2016
* This application is the backend for a banking system
* this backend runs at the end of the day
* the backend processes the transaction file produced by the frontend
* and then applies the transactions to the accounts within the system
* program is invoked with 2 arguments:
* 1) the Master Bank Accounts file
* 2) the day's transaction file
* Output of the program is:
* 1) a new Master Bank Accounts file
* 2) a new Current Accounts File for the frontend
**************************/

public class Bank{
  public static void main(String[] args) {
    System.out.println("Backend start");
    if(args.length!=2){
      System.out.println("ERROR: Invalid number of files");
      return;
    }
    TransactionProcessor transProcess = new TransactionProcessor(args[0], args[1]);
    transProcess.process();
    System.out.println("Execution complete");
  }
}
