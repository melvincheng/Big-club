/* Account.h

*/

#ifndef ACCOUNT_H
#define ACCOUNT_H
#include <string>

class Account {
 private:
  int account_id_;
  std::string name_;
  float balance_;
  bool enabled_;
  bool student_;

 public:
  Account(int,std::string,float,bool,bool);
  int get_account_id();
  std::string get_name();
  float get_balance();
  bool is_enabled();
  bool is_student();
  void set_balance(float);
  void enable();
  void disable();
  void change_plan();
};
#endif  // ACCOUNT_H
