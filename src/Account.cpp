#include "Account.h"
#include <iostream>

Account::Account(int account_id, std::string name,double balance, bool enabled, bool student){
  account_id_ = account_id;
  name_ = name;
  balance_ = balance;
  enabled_ = enabled;
  student_ = student;
}

Account::Account(){}

std::string Account::get_name() {
  return name_;
}

int Account::get_account_id() {
  return account_id_;
}

double Account::get_balance() {
  return balance_;
}

bool Account::is_enabled() {
  return enabled_;
}

bool Account::is_student() {
  return student_;
}

void Account::set_balance(double input) {
  balance_ = input;
}

void Account::enable() {
  if (enabled_ == false){
    enabled_ = true;
  }else{
    std::cout << "Transaction denied. Account is already enabled" << std::endl;
  }
}

void Account::disable() {
  if(enabled_ == true){
    enabled_ = false;
  }else{
    std::cout << "Transaction denied. Account is already disabled" << std::endl;
  }
}

void Account::change_plan() {
  if (student_) {
    student_ = false;
  } else {
    student_ = true;
  }
}
