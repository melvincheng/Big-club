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
 * Handles session functionalities for the current user of the banking
 * application
 * Should be used like: Session curr_sess;
 */
class Session {
 private:
  bool admin_;        // flag if the current session is in admin mode
  bool logged_;       // flag if the current session is logged in
  std::string name_;  // name of session user
  std::map<std::string,Account> accounts_;  // acounts from current bank accounts file

  void read_accounts();

 public:
  Session(); // default constructor

  void login();

  void logout();

  void withdrawal();

  void deposit();

  void changeplan();

  void transfer();

  void paybill();

  void create();

  void remove();

  void disable();

  void enable();
};

#endif  // SESSION_H
