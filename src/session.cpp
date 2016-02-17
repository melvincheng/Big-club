#include "session.h"
#include <iostream>
#include <fstream>
#include <string>
#include <sstream>

Session::Session(){
    admin_ = false;
    logged_ = false;
    read_accounts();
}

void Session::login() {
  //check if logged in
  if(logged_){
    std::cout << "Transaction denied. Already logged in" << std::endl;
  }else{
    std::string input = "";
    std::cout << "Please select the following:\n admin\nuser" << std::endl;
    std::cin >> input;
    // check for admin, user, or invalid
    if(input == "admin"){
      admin_ = true;
    }else if(input == "user"){
      admin_ = false;
      std::cout << "Please enter account holder's name: ";
      std::cin >> input;
    }else{
      std::cout << "Login failed, you must specify either admin or user" << std::endl;
      return;
    }
    // check for account name
    logged_ = true;
  }
}

void Session::read_accounts(){
  std::string filename = "CurrentAccount.txt";
  std::ifstream cafile(filename);

  std::string token;
  int id;
  std::string first;
  std::string last;
  float balance;
  bool enabled;
  bool student;
  while(!cafile.eof()){
    cafile >> id;
    cafile >> first;
    cafile >> last;
    std::string full = first + " " + last;
    cafile >> token;
    if(token == "A"){
      enabled = true;
    }else{
      enabled = false;
    }
    cafile >> balance;
    cafile >> token;
    if(token == "N"){
      student = false;
    }else{
      student = true;
    }
    Account curr = Account(id,full,balance,enabled,student);
  }
}

void Session::logout() {
  // check if logged out
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
  }else{
  // set logged flag on session
  l
  std::cout << "You hsaction denied.ve successfully logged out" << std::endl;
  }
  // logout
}

void Session::withdrawal() {
  // withdrawal
  std::string name = "";
  int number = 0;
  float value = 0.0;
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
   return;
 }else if(admin_){
   std::cout << "Please enter the account holder's name:" << std::endl;
   std::cin >> name;
 }
 std::cout << "Please enter the account number to withdraw from:" << std::endl;
 std::cin >> number;
 std::cout << "Please enter the amount to withdraw:" << std::endl;
 std::cin >> value;
 std::cout << "Withdrawal of " + value + " was successful" << std::endl;
}

void Session::deposit() {
  // deposit
  std::string name = "";
  int account = 0;
  float value = 0.0;
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(admin_){
    std::cout << "Please enter the account holder's name:" << std::endl;
    std::cin >> name;
  }
  std::cout << "Enter the account number:" << std::endl;
  std::cin >> account;
  std::cout << "Enter the amount in dollars to deposit:" << std::endl;
  std::cin >> value;
  std::cout << "Deposit successful, your deposit will be on hold until your next session" << std::endl;
}

void Session::changeplan() {
  // changeplan
  std::string name = "";
  int account = 0;
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(!admin_){
    std::cout << "Transaction denied. User is not an admin" << std::endl;
    return
  }
  std::cout << "Please enter the account holder's name:" << std::endl;
  std::cin >> name;
  std::cout << "Please enter the account number:" << std::endl;
  std::cin >> account;
  std::cout << "Account's plan has been changed" << std::endl;
}

void Session::transfer() {
  // transfer
  std::string name = "";
  int account_1,account_2 = 0;
  float value = 0.0;
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(admin_){
    std::cout << "Please enter the account holder's name:" << std::endl;
    std::cin >> name;
  }
  std::cout << "Please enter the account number to transfer from:" << std::endl;
  std::cin >> account_1;
  std::cout << "Please enter the account number to transfer to:" << std::endl;
  std::cin >> account_2;
  std::cout << "Please enter the amount you wish to transfer:" << std::endl;
  std::cin >> value;
  std::cout << "Transfer to " << account_1 << " from" << account_2 << "of " << value << " successful" << std::endl;
}

void Session::paybill() {
  // paybill
  std::string name, company = "";
  int account = 0;
  float value = 0.0;
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(admin_){
    std::cout << "Please enter the account holder's name:" << std::endl;
    std::cin >> name;
  }
  std::cout << "Enter the account number:" << endl;
  std::cin << account;
  std::cout << "Enter the company to whom you wish the pay the bill to:" << endl;
  std::cin << company;
  std::cout << "Enter the amount you wish to pay:" <<endl;
  std::cin << value;
  std::cout<< "Payment to " << company << " of" << value << "was succesful" << endl;
}

void Session::create() {
  // create"";
  std::string name = "";
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }
  else if(!admin_){
    std::cout <<  "Transaction denied. User is not an admin" << std::endl;
    return;
  }

  std::cout << "What is the name of the new account?" << std::endl;
  std::cin << name;
  if(strlen(name) > 20)) //more characters than allowed
  {
    std::cout <<"Transaction denied. Name is too long"<< std::endl;
    return;
  }
  else if(name = ""){
    std::cout << ""
  }

}

void Session::remove() {
  // remove
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(!admin_){
    std::cout << "Transaction denied. User is not an admin" << std::endl;
  }
}

void Session::disable() {
  // disable
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(!admin_){
    std::cout << "Transaction denied. User is not an admin" << std::endl;
  }
}

void Session::enable() {
  // enable
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(!admin_){
    std::cout << "Transaction denied. User is not an admin" << std::endl;
  }
}
