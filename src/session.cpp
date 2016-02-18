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
  double balance;
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
    if(!accounts_.count(full)){
      std::map<int,Account> temp;
      accounts_[full] = temp;
    }
    accounts_[full][id] = curr;
  }
}

void Session::logout() {
  // check if logged out
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
  }else{
  // set logged flag on session
  std::cout << "Transaction denied.you've successfully logged out" << std::endl;
  }
  // logout
}

//Broken
void Session::withdrawal() {
  // withdrawal
  std::string name = "";
  int account_id = 0;
  double value = 0.0;
  std::map<int,Account> account_map;
  Account account;
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
   return;
  }else if(admin_){
   std::cout << "Please enter the account holder's name:" << std::endl;
   std::cin >> name;
   try{
     account_map = accounts_.at(name);
     account = account_map.at(account_id);
   }catch(int err){
     std::cout << "The account holder's name is invalid" << std::endl;
     return;
   }
  }
 std::cout << "Please enter the account number to withdraw from:" << std::endl;
 if(!std::cin >> account_id){
   std::cout << "The account number is invalid" << std::endl;
   return;
 }
 std::cout << "Please enter the amount to withdraw:" << std::endl;
 if(!(std::cin >> value)){
   //std::cout << "T"
 }else if(value > 500.00 ){

 }
 //Withdrawal of $530.00 failed; withdrawal amount must be within the range of $0-$500

 std::cin >> value;
 std::cout << "Withdrawal of " << value << " was successful" << std::endl;
}

void Session::deposit() {
  // deposit
  std::string name = "";
  int account = 0;
  double value = 0.0;
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
    return;
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
  double value = 0.0;
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
  double value = 0.0;
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(admin_){
    std::cout << "Please enter the account holder's name:" << std::endl;
    std::cin >> name;
  }
  std::cout << "Enter the account number:" << std::endl;
  std::cin >> account;
  std::cout << "Enter the company to whom you wish the pay the bill to:" << std::endl;
  std::cin >> company;
  std::cout << "Enter the amount you wish to pay:" << std::endl;
  std::cin >> value;
  std::cout << "Payment to " << company << " of" << value << " was succesful" << std::endl;
}

void Session::create() {
  // create"";
  std::string name = "";
  std::string alphabet = "abcdefghijklmnopqrstuvwxyz ";
  double balance;

  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }
  else if(!admin_){
    std::cout <<  "Transaction denied. User is not an admin" << std::endl;
    return;
  }

  std::cout << "What is the name of the new account?" << std::endl;
  std::cin >> name;

  std::size_t nonAlpha = name.find_first_not_of(alphabet);
  if(name.length() > 20) //more characters than allowed
  {
    std::cout <<"Transaction denied. Name is too long"<< std::endl;
    return;
  }
  else if(name == ""){
    std::cout << "Transaction denied. No name detected"<< std::endl;
    return;
  }
  else if (nonAlpha!=std::string::npos)
  {
    std::cout << "Transaction denied. Non-Alphabetic chracters have been found.";
    return;
  }

  std::cout << "How much money will you deposit initially?" << std::endl;
  if(std::cin >> balance){
    if (balance > 99999.99)
    {
      std::cout << "You have too much moneyz, stop being so rich" << std::endl;
      return;
    }
    else if(balance == NULL)
    {
      std::cout << "An initial balance of $00000.00 has been administered" << std::endl;
    }
  }
  else {
    std::cout << "The balance recieved is not a recoginized form of currency in Canada" << std::endl;
    return;
  }

  std::cout << "A new account has been successfully created." << std::endl;
  return;

}

void Session::remove() {
  // remove
  accounts_;
  std::string name = "";
  int accountID;

  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(!admin_){
    std::cout << "Transaction denied. User is not an admin" << std::endl;
    return;
  }

  std::cout << "Please enter the name tied to the account" << std::endl;
  std::cin >> name;

  std::cout << "Please enter the account ID" << std::endl;
  std::cin >> accountID;

  std::cout << "Transaction complete, the account is now deleted"

  return;
}

void Session::disable() {
  // disable
  std::string name = "";
  int account_id = 0;
  std::map<int,Account> account_map;
  Account account;
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(!admin_){
    std::cout << "Transaction denied. User is not an admin" << std::endl;
  }else{
    std::cout << "Please enter account holder's name:" << std::endl;
    std::cin >> name;
    try{
      account_map = accounts_.at(name);
    }catch(int err){
      std::cout << "The account holder's name is invalid" << std::endl;
      return;
    }
    std::cout << "Please enter account number:" << std::endl;
    if(std::cin >> account_id){
      try{
        account = account_map.at(account_id);
      }catch(int err){
        std::cout << "The account number is invalid" << std::endl;
        return;
      }
      std::cout << "Account has been successfully disabled" << std::endl;
    }else{
      std::cout << "The account number is invalid" << std::endl;
    }
  }
}

void Session::enable() {
  // enable
  std::string name = "";
  int account_id = 0;
  std::map<int,Account> account_map;
  Account account;
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(!admin_){
    std::cout << "Transaction denied. User is not an admin" << std::endl;
  }else{
    std::cout << "Please enter account holder's name:" << std::endl;
    std::cin >> name;
    try{
      account_map = accounts_.at(name);
    }catch(int err){
      std::cout << "The account holder's name is invalid" << std::endl;
      return;
    }
    std::cout << "Please enter account number:" << std::endl;
    if(std::cin >> account_id){
      try{
        account = account_map.at(account_id);
      }catch(int err){
        std::cout << "Account has been successfully enable" << std::endl;
      }
    }else{
        std::cout << "The account number is invalid" << std::endl;
    }
  }
}

std::string Session::get_name(){

}
