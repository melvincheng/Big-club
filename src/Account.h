/* Account.h

*/

#ifndef ACCOUNT_H
#define ACCOUNT_H
#include <string>

class Account {
 private:
  int account_id_;
  std::string name_;
  double balance_;
  bool enabled_;
  bool student_;

 public:
  Account(int,std::string,double,bool,bool);
  Account();
  int get_account_id();
  std::string get_name();
  double get_balance();
  bool is_enabled();
  bool is_student();
  void set_balance(double);
  void enable();
  void disable();
  void change_plan();
};
#endif  // ACCOUNT_H
