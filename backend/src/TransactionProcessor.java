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
  boolean logged;                     // determine if a user is logged in


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
    this.logged = false;
  }

  /**
   * @brief Processes the transaction files and then updates accounts
   */
  public void process(){
    byte code;
    Transaction trans;
    int transId;
    boolean successful;
    // loops through all the transactions
    // once all the transactions are processed, the function return
    for(int i = 0;i < transactions.size();i++){
      trans = transactions.elementAt(i);
      transId = trans.getTransId();
      code = trans.getTransCode();
      if(code == 1 || code == 2 || code == 3 || code == 4 || code == 5 || code == 6 || code == 7 || code == 8 || code == 9){
        if(logged){
          if(code == 5 || code == 6 || code == 7 || code == 8 || code == 9){
            if(admin){
              if(code == 5){
                successful = create(trans);
              }else if(code == 6){
                successful = delete(trans);
              }else if(code == 7){
                successful = enable(false, trans);
              }else if(code == 8){
                successful = changeplan(trans);
              }else if(code == 9){
                successful = enable(true, trans);
              }
            }else{
              System.out.println("ERROR: User was not an admin: Transaction "+i);
            }
          }
          if(code == 1){
            successful = changeBalance(true, false, trans);
          }else if(code == 2){
            successful = transfer(trans, transactions.elementAt(i++));
          }else if(code == 3){
            successful = paybill(trans);
          }else if(code == 4){
            successful = changeBalance(false, false, trans);
          }
        }else{
          System.out.println("ERROR: User was not logged in: Transaction "+i);
        }
      }

      if(code == 0){
        this.admin = false;
        this.logged = false;
      }else if(code == 10){
        if(trans.getMisc() == "A"){
          this.admin = true;
          this.logged = true;
        }else if(trans.getMisc() == "S"){
          this.admin = false;
          this.logged = true;
        }
      }else{
        System.out.println("ERROR: Invalid transaction code: Transaction "+i);
      }

      if(!successful){
        System.out.print("Transaction "+i+"\n");
        successful = true;
      }
    }
  }


  private boolean accountCheck(String accountName, int accountId){
    if(accounts.containsKey(accountId)){
      Account account = accounts.get(accountId);
    }else{
      System.out.print("ERROR: Account does not exist: ");
      return false;
    }
    if(account.getName() != accountName){
      System.out.print("ERROR: Account holder name is invalid");
      return false;
    }
    if(account.isEnabled()){
      return true;
    }
    System.out.print("ERROR: Account is disabled: ");
    return false;
  }

  /**
   * @brief   changes the balance of the account
   *          this is used for withdraw, deposit, transfer, and paybill
   *
   * @param increase    if this is false, the balance is decreased
   *                    if this is true, the balance is increased
   * @param accountId   the balance of this account is being changed
   */
  private boolean changeBalance(boolean increase, boolean transfer, Transaction trans){
    int accountId = trans.getTransId();
    float value = trans.getValue();

    if(!accountCheck(trans.getTransName(),accountId)){
      return false;
    }
    Account account = accounts.get(accountId);
    float serviceFee = 0.0;
    if(!transfer){
      if(this.admin = false){
        if(account.isStudent() == true){
          serviceFee = 0.05;
        }else{
          serviceFee = 0.1;
        }
      }
    }
    if(account.getBalance() + value - serviceFee < 0.0 || account.getBalance() - value - serviceFee < 0.0){
      System.out.print("ERROR: Account has insufficient funds: ");
      return false;
    }
    if(increase){
      account.setBalance(account.getBalance() + value - serviceFee); 
    }else{
      account.setBalance(account.getBalance() - value - serviceFee); 
    }
    return true;
  }

  /**
   * @brief transfer money from one account to another
   * 
   * @param accountId1    the account where the money is being transfered from
   * @param accountId2    the account where the money is being transfered to
   */
  private boolean transfer(Transaction trans1, Transaction trans2){
    int accountId1 = trans1.getTransId();
    int accountId2 = trans2.getTransId();
    float value = trans1.getValue();
    if(!accountCheck(trans1.getTransName(), accountId1) && !accountCheck(trans2.getTransName(), accountId2)){
      return false;
    }
    Account account1 = accounts.get(accountId1);
    Account account2 = accounts.get(accountId2);
    if(changeBalance(false, false, trans1)){
      changeBalance(true, true, trans2);
      return true;
    }
    return false;
  }

  /**
   * @brief   pays bill to a company
   *          checks if the company being paided to exists
   * 
   * @param accountId   the account that is being used to pay bills
   *
   * @param company     the company that is being paid
   */
  private boolean paybill(Transaction trans){
    int accountId = trans.getTransId();
    String company = trans.getMisc();
    float value = trans.getValue();
    if(!accountCheck(accountId)){
      return false;
    }
    if(company == "TV" || company == "EC" || company == "CQ"){
      if(changeBalance(false, false, trans)){
        return true;
      }
    }else{
      System.out.print("ERROR: Invalid company: ");
      return false;
    }
    return false;
  }

  /**
   * @brief creates an account
   * 
   * @param trans   the transaction that is being used to create the account
   *                since the transaction contains the account holder name
   *                and the initial balance
   */
  private boolean create(Transaction trans){
    Set<Integer> accountIds = new HashSet<Integer>();
    int max = 0;
    //finds the next Id to use for the new account
    for(int key:accountIds){
      if(max < key){
        max = key;
      }
    }
    if(!trans.getTransName().matches("[a-zA-Z\-]+")){
      System.out.print("ERROR: Name contains invalid characters");
      return false;
    }

    Account account = new Account(max++, trans.getTransName(), trans.getValue(), true, false, 0);
    accounts.put(max++,account);
    return true;
  }

  /**
   * @brief deletes an account
   * 
   * @param trans   the transaction that is being used to delete the account
   *                since the transaction contains the accountId of the account 
   *                that is to be deleted
   */
  private boolean delete(Transaction trans){
    int accountId = trans.getTransId();
    if(accountCheck(trans.getTransName(), accountId)){
      accounts.remove(accountId);
      return true;
    }
    return false;
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
  private boolean enable(boolean willEnable, Transaction trans){
    int accountId = trans.getTransId();
    if(!accountCheck(trans.getTransName(), accountId)){
      return false;
    }
    Account account = accounts.get(accountId);
    if(!willEnable && account.isEnabled()){
      account.setEnabled(false);
      return true;
    }else if(willEnable && !account.isEnabled()){
      account.setEnabled(true);
      return true;
    }else if(!willEnable && !account.isEnabled()){
      System.out.print("ERROR: Account is already disabled: ");
    }else if(willEnable && account.isEnabled()){
      System.out.print("ERROR: Account is already enabled: ")
    }
    return false;
  }

  /**
   * @brief   changes the plan of the account
   *          whenever this is called, the plan changes
   * 
   * @param trans   the transaction that is being used to change the plan of the account
   *                since the transaction contains the accountId of the account that is
   *                being changed. also used to check is the account is a student or not
   */
  private boolean changeplan(Transaction trans){
    int accountId = trans.getTransId();
    if(accountCheck(trans.getTransName(), accountId)){
      Account account = accounts.get(accountId);
      account.setStudent(!account.isStudent());
      return true;
    }
    return false;
  }
}