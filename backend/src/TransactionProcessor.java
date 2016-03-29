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
  AccountIO actio;
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
    actio = new AccountIO(masterFile);
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
    boolean successful = true;
    // loops through all the transactions
    // once all the transactions are processed, the function return
    for(int i = 0;i < transactions.size();i++){
      trans = transactions.elementAt(i);
      transId = trans.getTransId();
      code = trans.getTransCode();
      if(code == 0){
        if(this.logged){
          this.admin = false;
          this.logged = false;
        }else{
          System.out.println("ERROR: User was already logged out: Transaction "+i);
        }
      }else if(code == 10){
        if(this.logged){
          System.out.println("ERROR: User was already logged in: Transaction "+i);
        }else if(trans.getMisc().equals("A")){
          this.admin = true;
        }else if(trans.getMisc().equals("S")){
          this.admin = false;
        }
        this.logged = true;
      }else if(this.logged){
        if(code == 1){
          successful = changeBalance(true, false, trans);
        }else if(code == 2){
          successful = transfer(trans, transactions.elementAt(i++));
        }else if(code == 3){
          successful = paybill(trans);
        }else if(code == 4){
          successful = changeBalance(false, false, trans);
        }else if(code == 5){
          successful = create(trans);
        }else if(code == 6){
          successful = delete(trans);
        }else if(code == 7){
          successful = enable(false, trans);
        }else if(code == 8){
          successful = changeplan(trans);
        }else if(code == 9){
          successful = enable(true, trans);
        }else{
          System.out.println("ERROR: Invalid transaction code: Transaction "+i);
        }
      }else if(!this.logged){
        System.out.print("ERROR: User was not logged out: ");
      }

      if(!successful){
        System.out.print("Transaction "+i+"\n");
        successful = true;
      }
    }
    actio.writeFile(accounts);
  }

  /**
   * @brief Checks if the logged in used is an admin; required for admin functions
   * @return returns true if the user is admin, false if the user is not
   */
  public boolean adminCheck(){
    if(admin){
      return true;
    }else{
      System.out.println("ERROR: User was not an admin: ");
      return false;
    }
  }

  /**
   * @brief   Checks if an account is valid
   * @param   accountName   account holder's name
   * @param   accountId     account Id of the acccount
   * @return  returns true if succes, if not successful, returns falses
   */
  public boolean accountCheck(String accountName, int accountId){
    Account account;
    if(accounts.containsKey(accountId)){
      account = accounts.get(accountId);
    }else{
      System.out.print("ERROR: Account does not exist: ");
      return false;
    }

    if(!account.getName().equals(accountName)){
      System.out.print("ERROR: Account holder name is invalid: ");
      return false;
    }
    if(!account.isEnabled()){
      System.out.print("ERROR: Account is disabled: ");
      return false;
    }
    return true;
  }

  /**
   * @brief   changes the balance of the account
   *          this is used for withdraw, deposit, transfer, and paybill
   *
   * @param increase    if this is false, the balance is decreased
   *                    if this is true, the balance is increased
   * @param transfer    determines if the transaction is processing a transfer transaction
   * @param trans       the corresponding transaction being processed
   * @return returns true if success, if not successful, returns false
   */
  public boolean changeBalance(boolean increase, boolean transfer, Transaction trans){
    int accountId = trans.getTransId();
    float value = trans.getValue();

    if(!accountCheck(trans.getTransName(),accountId)){
      return false;
    }
    Account account = accounts.get(accountId);
    float serviceFee = 0.0f;
    if(!transfer){
      if(!this.admin){
        if(account.isStudent()){
          serviceFee = 0.05f;
        }else{
          serviceFee = 0.1f;
        }
      }
    }
    if(increase){
      if(account.getBalance() + value - serviceFee < 0.0f){
        System.out.print("ERROR: Account has insufficient funds: ");
        return false;
      }
      account.setBalance(account.getBalance() + value - serviceFee);
    }else{
      if(account.getBalance() - value - serviceFee < 0.0f){
        System.out.print("ERROR: Account has insufficient funds: ");
        return false;
      }
      account.setBalance(account.getBalance() - value - serviceFee);
    }
    return true;
  }

  /**
   * @brief transfer money from one account to another
   *
   * @param trans1    the withdraw transaction of transfer
   * @param trans2    the deposit transaction of transfer
   * @return returns true if success, if not successful, returns false
   */
  public boolean transfer(Transaction trans1, Transaction trans2){
    int accountId1 = trans1.getTransId();
    int accountId2 = trans2.getTransId();
    float value = trans1.getValue();
    if(!accountCheck(trans1.getTransName(), accountId1)){
      return false;
    }
    if(!accountCheck(trans2.getTransName(), accountId2)){
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
   * @param trans   the corresponding transaction being processed
   * @return returns true if success, if not successful, returns false
   */
  public boolean paybill(Transaction trans){
    int accountId = trans.getTransId();
    String company = trans.getMisc();
    float value = trans.getValue();
    if(!accountCheck(trans.getTransName(), accountId)){
      return false;
    }
    if(company.equals("TV") || company.equals("EC") || company.equals("CQ")){
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
   * @return returns true if success, if not successful, returns false
   */
  public boolean create(Transaction trans){
    if(!adminCheck()){
      return false;
    }
    Set<Integer> accountIds = accounts.keySet();
    int max = 0;
    //finds the next Id to use for the new account
    for(int key:accountIds){
      if(max < key){
        max = key;
      }
    }
    if(!trans.getTransName().matches("[a-zA-Z\\-\\s]+")){
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
   * @return returns true if success, if not successful, returns false
   */
  public boolean delete(Transaction trans){
    if(!adminCheck()){
      return false;
    }
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
   * @return returns true if success, if not successful, returns false
   */
  public boolean enable(boolean willEnable, Transaction trans){
    if(!adminCheck()){
      return false;
    }

    int accountId = trans.getTransId();
    Account account;
    if(accounts.containsKey(accountId)){
      account = accounts.get(accountId);
    }else{
      System.out.print("ERROR: Account does not exist: ");
      return false;
    }
    if(!account.getName().equals(trans.getTransName())){
      System.out.print("ERROR: Account holder name is invalid: ");
      return false;
    }

    if(!willEnable && account.isEnabled()){
      account.setEnabled(false);
      return true;
    }else if(willEnable && !account.isEnabled()){
      account.setEnabled(true);
      return true;
    }else if(!willEnable && !account.isEnabled()){
      System.out.print("ERROR: Account is already disabled: ");
    }else if(willEnable && account.isEnabled()){
      System.out.print("ERROR: Account is already enabled: ");
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
   * @return returns true if success, if not successful, returns false
   */
  public boolean changeplan(Transaction trans){
    if(!adminCheck()){
      return false;
    }
    int accountId = trans.getTransId();
    if(accountCheck(trans.getTransName(), accountId)){
      Account account = accounts.get(accountId);
      account.setStudent(!account.isStudent());
      return true;
    }
    return false;
  }
}
