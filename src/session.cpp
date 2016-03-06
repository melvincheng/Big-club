#include "session.h"
#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <math.h>

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
    std::cout << "Please select the following:\nadmin\nuser" << std::endl;
    std::getline(std::cin, input);
    // check for admin, user, or invalid
    std::cout<<input;
    if(input == "admin"){
      admin_ = true;
      write_file(10,"",0,0.0,"A");
    }else if(input == "user"){
      admin_ = false;
      std::cout << "Please enter account holder's name: " << std::endl;
      std::getline(std::cin,input);
      std::cout<<input; 
      name_ = input;
      write_file(10,input,0,0.0,"S");
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
    if(first.compare("END")){
      return;
    }
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

void Session::write_file(int trans_num, std::string name, int account_id, float value, std::string misc){
  //TODO: implement write filz
  char out_file [40];
  sprintf(out_file, "%02d %20s %05d %00008.2f %s", trans_num, name.c_str(), account_id, value, misc.c_str());
  std::string current_transaction(out_file);
  if(trans_num == 00){
    //TODO: output file
  }else{
    transactions_.push_back(current_transaction);
  }
}

void Session::logout() {
  // check if logged out
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
  }else{
  // set logged flag on session
    std::cout << "Transaction successful. You've successfully logged out" << std::endl;
    logged_ = false;
    if(admin_){
      write_file(00,"",0,0.0,"A");
    }else{
      write_file(00,name_,0,0.0,"S");
    }
    exit(0);
  }
  // logout
}

void Session::withdrawal() {
  // withdrawal
  std::string name = "";
  std::string input;
  int account_id = 0;
  float value = 0.0;
  std::map<int,Account> account_map;
  Account account;
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(admin_){
    std::cout << "Please enter the account holder's name:" << std::endl;
    std::getline(std::cin, name);
  }
  try{
    account_map = accounts_[name_];
  }catch(const std::out_of_range& err){
    std::cout << "The account holder's name is invalid" << std::endl;
    return;
  }

  std::cout << "Please enter the account number to withdraw from:" << std::endl;
  std::getline(std::cin, input);
  try{
    account_id = std::stoi(input);
    account = account_map[account_id];
  }catch(const std::out_of_range& err){
    std::cout << "Withdrawal failed, account does not exist" << std::endl;
    return;
  }catch(const std::invalid_argument& err){
    std::cout << "Withdrawal failed, account does not exist" << std::endl;
    return;
  } 
  std::cout << "Please enter the amount to withdraw:" << std::endl;
  std::getline(std::cin, input);
  try{
    value = std::stof(input);
  }catch(const std::invalid_argument& err){
    std::cout << "Withdrawal failed, value must be a numeric value between $0-$500" << std::endl;
    return;
  }
  if(value > 500.00 ){ // TODO: Check for current day maximum Need to chang the code
    std::cout << "Withdrawal of " << value << " failed; cannot withdraw more than $500.00 in a single day" << std::endl;
  }else if(value > account.get_balance()){
    std::cout << "Cannot withdraw " << value <<", you have insufficient funds" << std::endl;
  }else if(!account.is_enabled()){
    std::cout << "Withdraw failed, " << account_id << " is disabled" << std::endl;
  }else if(!admin_){
    if(account.is_student() && value + 0.05 > account.get_balance()){
      std::cout << "Withdrawal failed, you must have at least $0.00 after transaction fees in your account" << std::endl;
    }else if(!(account.is_student()) && value + 0.10 > account.get_balance()){
      std::cout << "Withdrawal failed, you must have at least $0.00 after transaction fees in your account" << std::endl;
    }
  }else if(fmod(value,5) != 0){
    std::cout << "Withdrawal failed, you must enter a number divisible by 5" << std::endl;
  }else{
    std::cout << "Withdrawal of " << value << " was successful" << std::endl;
    write_file(01,name,account_id,value,"");
  }
}

void Session::deposit() {
  // deposit
  std::string name = "";
  int account_id = 0;
  float value = 0.0;
  std::map<int,Account> account_map;
  Account account;
  std::string input;
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(admin_){
    std::cout << "Please enter account holder's name:" << std::endl;
    std::getline(std::cin, name);
    try{
      account_map = accounts_.at(name);
    }catch(const std::out_of_range& err){
      std::cout << "The account holder's name is invalid" << std::endl;
      return;
    }
  }
  std::cout << "Enter the account number:" << std::endl;
  std::getline(std::cin, input);
  try{
    account_id = stoi(input);
    account = account_map.at(account_id);
  }catch(const std::out_of_range& err){
    std::cout << "Deposit failed, you entered an invalid account number" << std::endl;
    return;
  }catch(const std::invalid_argument& err){
    std::cout << "Deposit failed, you entered an invalid account number" << std::endl;
    return;
  }
  std::cout << "Enter the amount in dollars to deposit:" << std::endl;
  std::getline(std::cin, input);
  try{
    value = std::stof(input);
  }catch(const std::invalid_argument& err){
    std::cout << "Deposit failed, you must enter a numerical value." << std::endl;
    return;
  }catch(const std::out_of_range& err){
    std::cout << "Deposit failed, you must enter a numerical value." << std::endl;
    return;
  }
  if(value > account.get_balance()){
    std::cout << "Cannot withdraw " << value <<", you have insufficient funds" << std::endl;
  }else if(!account.is_enabled()){
    std::cout << "Deposit failed, " << account_id << " is disabled" << std::endl;
  }else if(!admin_){
    if(account.is_student() && value + 0.05 < account.get_balance()){
      std::cout << "Deposit failed, you must have at least $0.00 after transaction fees" << std::endl;
    }else if(!(account.is_student()) && value + 0.10 < account.get_balance()){
      std::cout << "Deposit failed, you must have at least $0.00 after transaction fees" << std::endl;
    }
  }else{
    std::cout << "Deposit successful, your deposit will be on hold until your next session" << std::endl;
  }
}

void Session::changeplan() {
  // changeplan
  std::string name = "";
  std::string input;
  int account_id = 0;
  std::map<int,Account> account_map;
  Account account;
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(!admin_){
    std::cout << "Transaction denied. User is not an admin" << std::endl;
    return;
  }
  std::cout << "Please enter the account holder's name:" << std::endl;
  std::getline(std::cin, name);
  try{
    account_map = accounts_.at(name);
  }catch(const std::out_of_range& err){
    std::cout << "The account holder's name is invalid" << std::endl;
    return;
  }
  std::cout << "Please enter the account number:" << std::endl;
  std::getline(std::cin, input);
  try{
    account_id = std::stoi(input);
    account = account_map.at(account_id);
  }catch(const std::out_of_range& err){
    std::cout << "The account number is invalid" << std::endl;
    return;
  }
  std::cout << "Account's plan has been changed" << std::endl;
}

void Session::transfer() {
  // transfer
  std::string name = "";
  std::string input;
  int account_id_1,account_id_2 = 0;
  float value = 0.0;
  std::map<int,Account> account_map;
  Account account_1, account_2;
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(admin_){
    std::cout << "Please enter the account holder's name:" << std::endl;
    std::getline(std::cin, name);
    try{
      account_map = accounts_.at(name);
    }catch(const std::out_of_range& err){
      std::cout << "The account holder's name is invalid" << std::endl;
      return;
    }
  }
  std::cout << "Please enter the account number to transfer from:" << std::endl;
  std::getline(std::cin, input);
  try{
    account_id_1 = stoi(input);
    account_1 = account_map.at(account_id_1);
  }catch(const std::out_of_range& err){
    std::cout << "Transfer failed, account does not exist" << std::endl;
    return;
  }catch(const std::invalid_argument& err){
    std::cout << "Transfer failed, account does not exist" << std::endl;
    return;
  }
  std::cout << "Please enter the account number to transfer to:" << std::endl;
  std::getline(std::cin, input);
  try{
    account_id_2 = std::stoi(input);
    account_2 = account_map.at(account_id_2);
  }catch(const std::out_of_range& err){
    std::cout << "Transfer failed, account does not exist" << std::endl;
    return;
  }catch(const std::invalid_argument& err){
    std::cout << "Transfer failed, account does not exist" << std::endl;
    return;
  }
  std::cout << "Please enter the amount you wish to transfer:" << std::endl;
  std::getline(std::cin, input);
  try{
    value = std::stof(input);
  }catch(const std::out_of_range& err){
    std::cout << "Invalid amount, you can only transfer funds between $0-$1000" << std::endl;
  }catch(const std::invalid_argument& err){
    std::cout << "Invalid amount, you can only transfer funds between $0-$1000" << std::endl;
  }
  if(value > account_1.get_balance()){
    std::cout << "Cannot transfer " << value <<", you have insufficient funds" << std::endl;
  }else if(!account_1.is_enabled()){
    std::cout << "Transfer unsuccessful, " << account_id_1 << " is disabled" << std::endl;
  }else if(!account_2.is_enabled()){
    std::cout << "Transfer unsuccessful, " << account_id_2 << " is disabled" << std::endl;
  }else if(value > 1000.00 ){ //TODO: Check for current day maximum Need to chang the code
    std::cout << "Transfer unsuccessful, can not transfer more than $1000.00 in a single day" << std::endl;
  }else if(!admin_){
    if(account_1.is_student() && value + 0.05 < account_1.get_balance()){
      std::cout << "Transfer failed, your account must have at least $0.00 after transaction fees" << std::endl;
    }else if(!(account_1.is_student()) && value + 0.10 < account_1.get_balance()){
      std::cout << "Transfer failed, your account must have at least $0.00 after transaction fees" << std::endl;
    }
  }else{
    std::cout << "Transfer to " << account_id_1 << " from " << account_id_2 << " of " << value << " successful" << std::endl;
  }
}


void Session::paybill() {
  // paybill
  std::string name, company = "";
  std::string input;
  float value = 0.0;
  std::map<int,Account> account_map;
  Account account;
  char to_lower [64];
  int account_id;
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(admin_){
    std::cout << "Please enter the account holder's name:" << std::endl;
    std::getline(std::cin, name);
    try{
      account_map = accounts_.at(name);
    }catch(const std::out_of_range& err){
      std::cout << "The account holder's name is invalid" << std::endl;
      return;
    }
  }
  std::cout << "Enter the account number:" << std::endl;
  std::getline(std::cin, input);
  try{
    account_id = stoi(input);
    account = account_map.at(account_id);
  }catch(const std::out_of_range& err){
    std::cout << "Invalid account number: " << account_id << std::endl;
    return;
  }catch(const std::invalid_argument& err){
    std::cout << "Invalid account number: " << account_id << std::endl;
    return;
  }
  std::cout << "Enter the company to whom you wish the pay the bill to:" << std::endl;
  std::getline(std::cin, company);
  for(uint i = 0; i < company.length(); i++){
    to_lower[i] = std::tolower(company[i]);
  }
  company = to_lower;
  if(company == "the bright light electric company" || company == "ec"){// TODO: find the company name
    company = "The Bright Light Electric Company";
  }else if(company == "credit card company q" || company == "cq"){
    company = "Credit Card Company Q";
  }else if(company == "low definition tv, inc." || company == "tv"){
    company = "Low Definition TV, Inc.";
  }else{
    std::cout << company << "is not a valid company to pay a bill to:" << std::endl;
  }
  std::cout << "Enter the amount you wish to pay:" << std::endl;
  std::getline(std::cin, input);
  try{
    value = std::stof(input);
  }catch(const std::invalid_argument& err){
    std::cout << "Payment, you must enter a numerical value." << std::endl;
    return;
  }catch(const std::out_of_range& err){
    std::cout << "Payment, you must enter a numerical value." << std::endl;
    return;
  }
  if(value > account.get_balance()){
    std::cout << "Payment to Credit Card Company Q of " << value << " failed, you do not have at least " << value << " in your account." << std::endl;
  }else if(value > 2000){// TODO: Check for current day maximum Need to chang the code
    std::cout << "You may not pay more than $2000 to a bill holder in a day." << std::endl;
  }else if(!account.is_enabled()){
    std::cout << "Payment failed, " << account_id << " is disabled" << std::endl;
  }else if(!admin_){
    if(account.is_student() && value + 0.05 > account.get_balance()){
      std::cout << "Payment failed, you must have at least $0.00 after transaction fees in your account" << std::endl;
    }else if(!(account.is_student()) && value + 0.10 > account.get_balance()){
      std::cout << "Payment failed, you must have at least $0.00 after transaction fees in your account" << std::endl;
    }
  }else{
    std::cout << "Payment of $" << value << " to " << company << " was successful" << std::endl;
  }
}

void Session::create() {
  // create"";
  std::string name = "";
  std::string input;
  std::string alphabet = "abcdefghijklmnopqrstuvwxyz ";
  std::size_t nonAlpha = name.find_first_not_of(alphabet);
  float balance = 0.0;

  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }
  else if(!admin_){
    std::cout <<  "Transaction denied. User is not an admin" << std::endl;
    return;
  }

  std::cout << "Please input Account Name:" << std::endl;
  std::getline(std::cin, name);

  if(name.length() > 20) //more characters than allowed
  {
    std::cout <<"I'm sorry, your name is too long. Please shorten it to 20 characters or less."<< std::endl;
    return;
  }
  else if(name == ""){
    std::cout << "Transaction denied. No name detected"<< std::endl;
    return;
  }
  else if (nonAlpha!=std::string::npos)
  {
    std::cout << "I'm sorry, your name is unrecognizable and cannot be used (get rid of all numbers and symbols)";
    return;
  }

  std::cout << "Please input your Initial Balance:" << std::endl;
  std::getline(std::cin, input);
  if(input == ""){
    balance = 0.0;
  }else{
    try{
      balance = std::stof(input);
    }catch(const std::out_of_range& err){
      std::cout << "Transaction denied. Invalid characters" << std::endl;
      return;
    }catch(const std::invalid_argument&){
      std::cout << "Transaction denied. Invalid characters" << std::endl;
      return;
    }
  }
  if (balance > 99999.99)
  {
    std::cout << "Transaction denied. Amount entered is too large" << std::endl;
    return;
  }
  else if(balance == 0.0)
  {
    std::cout << "An initial balance of 00000.00 has been administered" << std::endl;
  }
  std::cout << "A new account was made under the name:\n" << name << std::endl << 
  "with a current balance of:\n" << balance << std::endl << 
  "Your account will be available on the next day" << std::endl;
  return;
}

void Session::remove() {
  // remove
  std::string name = "";
  std::string input;
  int account_id = 0;
  std::map<int,Account> account_map;
  std::string alphabet = "abcdefghijklmnopqrstuvwxyz ";
  std::size_t nonAlpha = name.find_first_not_of(alphabet);
  Account account;
  std::string confirm = "";
  if(!logged_){
    std::cout << "Transaction denied. Not logged in" << std::endl;
    return;
  }else if(!admin_){
    std::cout << "Transaction denied. User is not an admin" << std::endl;
    return;
  }

  std::cout << "Please enter the name tied to the account" << std::endl;
  std::getline(std::cin, name);

  if(name.length() > 20) //more characters than allowed
  {
    std::cout <<"Transaction denied. Name is too long" << std::endl;
    return;
  }
  else if(name == ""){
    std::cout << "Transaction denied. No name detected" << std::endl;
    return;
  }
  else if (nonAlpha!=std::string::npos)
  {
    std::cout << "Transaction denied. Non-Alphabetic chracters have been found.";
    return;
  }else{
    try{
      account_map = accounts_.at(name);
    }catch(const std::out_of_range& err){
      std::cout << "I'm sorry, this name is not registered in the bank" << std::endl;
      return;
    }
  }
  std::cout << "Please enter the account ID" << std::endl;
  std::getline(std::cin, input);
  try{
    account_id = std::stoi(input);
    account = account_map.at(account_id);
  }catch(const std::out_of_range& err){
    std::cout << "I'm sorry, the account number given does not match up to the Account Name." << std::endl;
    return;
  }
  std::cout << "This bank Account will now be deleted\n" << name << std::endl << account_id << std::endl << "Confirm?" << std::endl;
  std::getline(std::cin, confirm);
  if(confirm == "y" || confirm == "yes" || confirm == "Y" || confirm == "YES"){
    std::cout << "This account has been deleted" << std::endl;
  }else{
    std::cout << "This account has not been deleted" << std::endl;
    return;
  }
}

void Session::enable(bool enable) {
  // disable
  std::string name = "";
  std::string input;
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
    std::getline(std::cin, name);
    try{
      account_map = accounts_.at(name);
    }catch(const std::out_of_range& err){
      std::cout << "The account holder's name is invalid" << std::endl;
      return;
    }
    std::cout << "Please enter account number:" << std::endl;
    std::getline(std::cin, input);
    try{
      account_id = std::stoi(input);
      account = account_map.at(account_id);
    }catch(const std::out_of_range& err){
      std::cout << "The account number is invalid" << std::endl;
      return;
    }
    if(enable){
      std::cout << "Account has been successfully enable" << std::endl;
    }else{
      std::cout << "Account has been successfully disable" << std::endl;
    }
  }
}