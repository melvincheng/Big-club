/**
 * session.h
 * @author Santiago Bonada
 * @author Melvin Cheng
 * @author Adam Beckett May
 * @version 0.1
 * This header defines the Session class which handles the functionalites
 * of a session within the banking application
 */
#ifndef SESSION_H
#define SESSION_H

#include <string>
#include <map>
#include <vector>
#include "Account.h"

/**
 * Contains, keeps track, and manipulates information of the current session
 */

class Session {

/**
 * Handles session functionalities for the current user of the banking
 * application
 * Should be used like: Session curr_sess;
 */
 private:
  bool admin_;        // flag if the current session is in admin mode
  bool logged_;       // flag if the current session is logged in
  std::string name_;  // name of session user
  std::map<std::string,std::map<int,Account>> accounts_;  // acounts from current bank accounts file
  std::vector<std::string> transactions_;
  std::string account_file;
  std::string transaction_file;
  const float MAX_WITHDRAW = 500.0;
  const float MAX_TRANSFER = 1000.0;
  const float MAX_PAYBILL = 2000.0;
  float daily_withdraw;
  float daily_transfer;
  float daily_paybill;

/**
 * trims off leading and trailing whitespace from a string
 */
  std::string trim(const std::string& str, const std::string& whitespace = " \n");

/**
 * Reads the current account file
 */
  void read_accounts();

/**
 * Writes into a transaction file
 */
  void write_file(int trans_num, std::string name = "", int account_id = 0, float value = 0, std::string misc = "");

 public:
  Session(std::string account_file, std::string transaction_file); // default constructor

/**
 * Logs into a session
 */
  void login();

/**
 * Logs out of a session
 */
  void logout();

/**
 * Withdraws money from an account
 */
  void withdrawal();

/**
 * Deposits money into an account
 */
  void deposit();

/**
 * Changes the plan of an account
 */
  void changeplan();

/**
 * Transfers money from one account to another
 */
  void transfer();

/**
 * Pays money to a company
 */
  void paybill();

/**
 * Creates an account
 */
  void create();

/**
 * Removes an account
 */
  void remove();

/**
 * Enables or disable an account depending on argument
 * if argument is true, attempt to enable
 * if argument is false, attempt to disable
 */
  void enable(bool enable);
};

#endif  // SESSION_H
