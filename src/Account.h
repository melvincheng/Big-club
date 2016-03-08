/* Account.h

*/

#ifndef ACCOUNT_H
#define ACCOUNT_H
#include <string>

class Account {
 private:
  int account_id_; // account id of the account
  std::string name_; // name of the account holder
  float balance_; // balance within the account
  bool enabled_; // flag for if the account is enabled
  bool student_; // flag for if the account has a student plan

 public:
  /**
   * Primary constructor to use when initializing accounts.
   */
  Account(int,std::string,float,bool,bool);
   /**
    * default constructor
    */
  Account();
   /**
    * get the account id of the account
    */
  int get_account_id();
   /**
    * get the name of the account holder
    */
  std::string get_name();
   /**
    * get the balance of the account
    */
  float get_balance();
   /**
    * check if the account is enabled
    */
  bool is_enabled();
   /**
    * check if the account has a student plan
    */
  bool is_student();
   /**
    * set the balance of the account
    */
  void set_balance(float);
   /**
    * enable the account
    */
  void enable(bool);
   /**
    * change the plan on the account
    */
  void change_plan();
};
#endif  // ACCOUNT_H
