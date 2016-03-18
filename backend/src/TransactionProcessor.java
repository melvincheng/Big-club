/**
 * This class is used to process the data that was parsed by the transactionIO and accountIO
 */

import java.util.Vector;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class TransactionProcessor{
  Map<Integer,Account> accounts;          // all the accounts in the bank
  Vector<Transaction> transactions;   // all the transactions that happen during the day
  boolean admin;                      // if an admin logs in, this is false,
                                      // and used to stop transaction count increment if it's true


  /**
   * @brief Constructor for the processor, reads the master file and transaction file
   *        then puts the accounts into a map and puts the transactions into a vector
   * @param masterFile        the name of the master file
   * @param transactionFile   the name of the transaction file
   */
  public TransactionProcessor(String masterFile, String transactionFile){
    TransactionIO trfio = new TransactionIO(transactionFile);
    AccountIO actio = new AccountIO(masterFile);
    this.transactions = trfio.readFile();
    this.accounts = actio.readFile();
    this.admin = false;
  }

  /**
   * @brief Processes the transaction files and then updates accounts
   */
  public void process(){
    byte code;
    Transaction trans;
    int transId;
    // loops through all the transactions
    // once all the transactions are processed, the function return
    for(int i = 0;i < transactions.size();i++){
      trans = transactions.elementAt(i);
      transId = trans.getTransId();
      code = trans.getTransCode();
      if(code == 1){
        changeBalance(true, transId, trans.getValue());
      }else if(code == 2){
        transfer(transId, transactions.elementAt(i++).getTransId(), trans.getValue());
      }else if(code == 3){
        paybill(transId, trans.getMisc(), trans.getValue());
      }else if(code == 4){
        changeBalance(false, transId, trans.getValue());
      }else if(code == 5){
        create(trans);
      }else if(code == 6){
        delete(transId);
      }else if(code == 7){
        enable(false, transId);
      }else if(code == 8){
        changeplan(transId);
      }else if(code == 9){
        enable(true, transId);
      }else{
        System.out.println("Transaction "+i+" failed");
      }
    }
  }

  /**
   * @brief   changes the balance of the account
   *          this is used for withdraw, deposit, transfer, and paybill
   *
   * @param increase    if this is false, the balance is decreased
   *                    if this is true, the balance is increased
   * @param accountId   the balance of this account is being changed
   */
  private void changeBalance(boolean increase, int accountId, float value){
    Account account = accounts.get(accountId);
    if(increase){
      account.setBalance(account.getBalance() + value); 
    }else{
      account.setBalance(account.getBalance() - value); 
    }
  }

  /**
   * @brief transfer money from one account to another
   * 
   * @param accountId1    the account where the money is being transfered from
   * @param accountId2    the account where the money is being transfered to
   */
  private void transfer(int accountId1, int accountId2, float value){
    Account account1 = accounts.get(accountId1);
    Account account2 = accounts.get(accountId2);
    changeBalance(false, accountId1, value);
    changeBalance(true, accountId2, value);
  }

  /**
   * @brief   pays bill to a company
   *          checks if the company being paided to exists
   * 
   * @param accountId   the account that is being used to pay bills
   *
   * @param company     the company that is being paid
   */
  private void paybill(int accountId, String company, float value){
    if(company == "TV" || company == "EC" || company == "CQ"){
      changeBalance(false, accountId, value);
    }
  }

  /**
   * @brief creates an account
   * 
   * @param trans   the transaction that is being used to create the account
   *                since the transaction contains the account holder name
   *                and the initial balance
   */
  private void create(Transaction trans){
    Set<Integer> accountIds = new HashSet<Integer>();
    int max = 0;
    //finds the next Id to use for the new account
    for(int key:accountIds){
      if(max < key){
        max = key;
      }
    }
    Account account = new Account(max++, trans.getTransName(), trans.getValue(), true, false, 0);
    accounts.put(max++,account);
  }

  /**
   * @brief deletes an account
   * 
   * @param trans   the transaction that is being used to delete the account
   *                since the transaction contains the accountId of the account 
   *                that is to be deleted
   */
  private void delete(int accountId){
    accounts.remove(accountId);
  }

  /**
   * @brief enables or disables account depending on the boolean
   * 
   * @param willEnable  if this is true, the function attempts to enable the account
   *                if this is false, the function attempts to disable the account
   * @param trans   the transaction that is being used to enable or disable the account
   *                since the transaction contains the accountId to be enable/disable and
   *                also contains whether the account is already enable/disable
   */
  private void enable(boolean willEnable, int accountId){
    Account account = accounts.get(accountId);
    if(!willEnable && account.isEnabled()){
      account.setEnabled(true);
    }else if(willEnable && !account.isEnabled()){
      account.setEnabled(false);
    }
  }

  /**
   * @brief   changes the plan of the account
   *          whenever this is called, the plan changes
   * 
   * @param trans   the transaction that is being used to change the plan of the account
   *                since the transaction contains the accountId of the account that is
   *                being changed. also used to check is the account is a student or not
   */
  private void changeplan(int accountId){
    Account account = accounts.get(accountId);
    account.setStudent(!account.isStudent());
  }
}