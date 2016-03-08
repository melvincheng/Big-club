#include "session.h"
#include <iostream>
#include <iomanip>
#include <fstream>
#include <string>
#include <sstream>
#include <math.h>

Session::Session(std::string account_file, std::string transaction_file){
  this->account_file = account_file;
  this->transaction_file = transaction_file;
  admin_ = false;
  logged_ = false;
  daily_withdraw = MAX_WITHDRAW;
  daily_transfer = MAX_TRANSFER;
  daily_paybill = MAX_PAYBILL;
  read_accounts();
}

void Session::login() {
  //check if logged in
  std::string name;
  std::map<int,Account> account_map;
  if(logged_){
    printf("Transaction denied. Already logged in\n");
  }else{
    std::string input = "";
    printf("Please select the following:\nadmin\nuser\n");
    getline(std::cin, input);
    // check for admin, user, or invalid
    if(input == "admin"){
      admin_ = true;
      write_file(10,"",0,0.0,"A");
    }else if(input == "user"){
      printf("Please enter your name:\n");
      getline(std::cin,name);
      try{
        account_map = accounts_.at(name);
      }catch(const std::out_of_range& err){
        printf("The account holder's name is invalid\n");
        return;
      }
      name_ = name;
      admin_ = false;
      write_file(10,name_,0,0.0,"S");
    }else{
      printf("Login failed, you must specify either admin or user\n");
      return;
    }
    // check for account name
    logged_ = true;
  }
}

void Session::read_accounts() {
  std::ifstream cafile(account_file);

  std::string line;
  std::string token;
  int id;
  std::string name;
  float balance;
  bool enabled;
  bool student;

  while (!cafile.eof()) {
    getline(cafile, line);
    token = line.substr(0, 5);  // extract account id
    id = atoi(token.c_str());   // convert account id to integer

    // check if we read in the final account file
    if(id == 0){
      return;
    }

    token = line.substr(6, 20);  // extract account name

    name = trim(token);

    token = line.substr(27, 1);  // extract account enabled flag
    if (token == "A") {
      enabled = true;
    } else {
      enabled = false;
    }

    token = line.substr(29, 8);     // extract account balance
    balance = atof(token.c_str())*100.0;  // convert account balance to float

    token = line.substr(38, 1);  // extract plan status
    if (token == "N") {
      student = false;
    } else {
      student = true;
    }

    // printf("%d %s %f\n", id, name.c_str(), balance);

    Account curr = Account(id, name, balance, enabled, student);
    if (!accounts_.count(name)) {
      std::map<int, Account> temp;
      accounts_[name] = temp;
    }
    accounts_[name][id] = curr;
  }
}

void Session::write_file(int trans_num, std::string name, int account_id, float value, std::string misc){
  // write transaction file
  char out_file [40];
  value = value / 100;
  sprintf(out_file, "%02d %-20s %05d %08.2f %s", trans_num, name.c_str(), account_id, value, misc.c_str());
  std::string current_transaction(out_file);
  if(trans_num == 0){
    std::ofstream trans_file(transaction_file);
    for(uint i = 0; i < transactions_.size();i++){
      trans_file << transactions_[i] << std::endl;
    }
    trans_file << current_transaction;
  }else{
    transactions_.push_back(current_transaction);
  }
}

void Session::logout() {
  // check if logged out
  if(!logged_){
    printf("Transaction denied. Not logged in\n");
  }else{
  // set logged flag on session
    printf("You have successfully logged out");
    logged_ = false;
    if(admin_){
      write_file(0,"",0,0.0,"A");
    }else{
      write_file(0,name_,0,0.0,"S");
    }
    exit(0);
  }
  // logout
}

void Session::withdrawal() {
  // withdrawal
  std::string name, input;
  std::string::size_type no_convert;
  int account_id;
  float value, actual_value;
  std::map<int,Account> account_map;
  Account account;
  if(!logged_){
    printf("Transaction denied. Not logged in\n");
    return;
  }else if(admin_){
    printf("Please enter the account holder's name:\n");
    getline(std::cin, name);
  }else{
    name = name_;
  }
  try{
    account_map = accounts_.at(name);
  }catch(const std::out_of_range& err){
    printf("The account holder's name is invalid\n");
    return;
  }
  printf("Please enter the account number to withdraw from:\n");
  getline(std::cin, input);
  try{
    account_id = std::stoi(input);
    account = account_map.at(account_id);
  }catch(const std::out_of_range& err){
    printf("Withdrawal failed, account does not exist\n");
    return;
  }catch(const std::invalid_argument& err){
    printf("Withdrawal failed, account does not exist\n");
    return;
  }
  if(!account.is_enabled()){
    printf("Withdraw failed, %5d is disabled\n",account_id);
    return;
  }

  printf("Please enter the amount to withdraw:\n");
  getline(std::cin, input);
  try{
    value = std::stof(input, &no_convert);
    value = value * 100.0;
    if(no_convert!=input.length()){
        throw std::invalid_argument("non numerical characters");
    }
  }catch(const std::invalid_argument& err){
    printf("Withdrawal failed, value must be a numerical value.\n");
    return;
  }catch(const std::out_of_range& err){
    printf("Withdrawal failed, value must be a numerical value.\n");
    return;
  }
  if(value > (daily_withdraw*100) ){ // TODO: Check for current day maximum Need to chang the code
    printf("Withdrawal of $%0.2f failed; cannot withdraw more than $500.00 in a single day\n",(value/100.0));
    return;
  }else if(value > account.get_balance()){
    printf("Withdrawal failed, you must have at least $0.00 after transaction fees in your account\n");
    return;
  }else if(fmod(value/100.0,5) != 0){
    printf("Withdrawal failed, you must enter a number divisible by 5\n");
    return;
  }else if(!admin_){
    if(account.is_student()){
      actual_value = value + 5.0;
    }else{
      actual_value = value + 10.0;
    }
    if(actual_value > account.get_balance()){
      printf("Withdrawal failed, you must have at least $0.00 after transaction fees in your account\n");
      return;
    }
  }else{
    actual_value = value;
  }
  daily_withdraw = daily_withdraw - (value/100.0);
  printf("Withdrawal of $%0.2f was successful\n",value/100.0);
  write_file(1,name,account_id,actual_value,"");
}

void Session::deposit() {
  // deposit
  std::string name, input;
  std::string::size_type no_convert;
  int account_id;
  float value, actual_value;
  std::map<int,Account> account_map;
  Account account;
  if(!logged_){
    printf("Transaction denied. Not logged in\n");
    return;
  }else if(admin_){
    printf("Please enter account holder's name:\n");
    getline(std::cin, name);
  }else{
    name = name_;
  }
  try{
      account_map = accounts_.at(name);
    }catch(const std::out_of_range& err){
      printf("The account holder's name is invalid\n");
      return;
    }
  printf("Enter the account number:\n");
  getline(std::cin, input);
  try{
    account_id = stoi(input);
    account = account_map.at(account_id);
  }catch(const std::out_of_range& err){
    printf("Deposit failed, you entered an invalid account number\n");
    return;
  }catch(const std::invalid_argument& err){
    printf("Deposit failed, you entered an invalid account number\n");
    return;
  }
  if(!account.is_enabled()){
    printf("Deposit failed, %d is disabled\n", account_id);
    return;
  }
  printf("Enter the amount in dollars to deposit:\n");
  getline(std::cin, input);
  try{
    value = std::stof(input, &no_convert);
    value = value * 100.0;
    if(no_convert!=input.length()){
        throw std::invalid_argument("non numerical characters");
    }
  }catch(const std::invalid_argument& err){
    printf("Deposit failed, you must enter a numerical value.\n");
    return;
  }catch(const std::out_of_range& err){
    printf("Deposit failed, you must enter a numerical value.\n");
    return;
  }
  if(!admin_){
    if(account.is_student()){
      actual_value = value + 5.0;
    }else{
      actual_value = value + 10.0;
    }
    if(account.get_balance() - actual_value < 0.0){
      printf("Deposit failed, you must have at least $0.00 after transaction fees\n");
      return;
    }
  }else{
    actual_value = value;
  }
  printf("Deposit successful, your deposit will be on hold until the next day\n");
  write_file(4,name,account_id,actual_value,"");
}

void Session::changeplan() {
  // changeplan
  std::string name, input, new_plan;
  int account_id = 0;
  std::map<int,Account> account_map;
  Account account;
  if(!logged_){
    printf("Transaction denied. Not logged in\n");
    return;
  }else if(!admin_){
    printf("Transaction denied. User is not an admin\n");
    return;
  }
  printf("Please enter the account holder's name:\n");
  getline(std::cin, name);
  try{
    account_map = accounts_.at(name);
  }catch(const std::out_of_range& err){
    printf("The account holder's name is invalid\n");
    return;
  }
  printf("Please enter the account number:\n");
  getline(std::cin, input);
  try{
    account_id = std::stoi(input);
    account = account_map.at(account_id);
  }catch(const std::out_of_range& err){
    printf("The account number is invalid\n");
    return;
  }
  if(account.is_student()){
    write_file(8,name,account_id,0.0,"NP");
  }else{
    write_file(8,name,account_id,0.0,"SP");
  }
  printf("Account's plan has been changed\n");
}

void Session::transfer() {
  // transfer
  std::string name, input;
  std::string::size_type no_convert;
  int account_id_1, account_id_2;
  float value, actual_value;
  std::map<int,Account> account_map;
  Account account_1, account_2;
  if(!logged_){
    printf("Transaction denied. Not logged in\n");
    return;
  }else if(admin_){
    printf("Please enter the account holder's name:\n");
    getline(std::cin, name);
  }else{
    name = name_;
  }
  try{
      account_map = accounts_.at(name);
    }catch(const std::out_of_range& err){
      printf("The account holder's name is invalid\n");
      return;
    }
  printf("Please enter the account number to transfer from:\n");
  getline(std::cin, input);
  try{
    account_id_1 = stoi(input);
    account_1 = account_map.at(account_id_1);
  }catch(const std::out_of_range& err){
    printf("Transfer failed, account does not exist\n");
    return;
  }catch(const std::invalid_argument& err){
    printf("Transfer failed, account does not exist\n");
    return;
  }
  if(!account_1.is_enabled()){
    printf("Transfer unsuccessful, %d is disabled\n", account_id_1);
    return;
  }
  printf("Please enter the account number to transfer to:\n");
  getline(std::cin, input);
  try{
    account_id_2 = std::stoi(input);
    account_2 = account_map.at(account_id_2);
  }catch(const std::out_of_range& err){
    printf("Transfer failed, account does not exist\n");
    return;
  }catch(const std::invalid_argument& err){
    printf("Transfer failed, account does not exist\n");
    return;
  }
  if(!account_2.is_enabled()){
    printf("Transfer unsuccessful, %d is disabled\n", account_id_2);
    return;
  }
  printf("Please enter the amount you wish to transfer:\n");
  getline(std::cin, input);
  try{
    value = std::stof(input, &no_convert);
    value = value * 100.0;
    if(no_convert!=input.length()){
        throw std::invalid_argument("non numerical characters");
    }
  }catch(const std::out_of_range& err){
    printf("Invalid amount, you can only transfer numerical values\n");
    return;
  }catch(const std::invalid_argument& err){
    printf("Invalid amount, you can only transfer numerical values\n");
    return;
  }
  if(value > (daily_transfer * 100) && !admin_){ //TODO: Check for current day maximum Need to chang the code
    printf("Transfer unsuccessful, can not transfer more than $1000.00 in a single day\n");
    return;
  }else if(value > account_1.get_balance()){
    printf("Transfer failed, your account must have at least $0.00 after transaction fees\n");
    return;
  }else if(!admin_){
    if(account_1.is_student()){
      actual_value = value + 5.0;
    }else{
      actual_value = value + 10.0;
    }
    if(actual_value > account_1.get_balance()){
      printf("Payment failed, you must have at least $0.00 after transaction fees\n");
      return;
    }
  }else{
    actual_value = value;
  }
  daily_transfer = daily_transfer - (value / 100.0);
  printf("Transfer from %d to %d of $%0.2f was successful\n", account_id_1, account_id_2, value/100.0);
  write_file(2,name,account_id_1,actual_value,"");
  write_file(2,name,account_id_2,value,"");
}


void Session::paybill() {
  // paybill
  std::string name, company, lower_company, company_short, input;
  std::string::size_type no_convert;
  int account_id;
  float value, actual_value;
  std::map<int,Account> account_map;
  Account account;
  if(!logged_){
    printf("Transaction denied. Not logged in\n");
    return;
  }else if(admin_){
    printf("Please enter the account holder's name:\n");
    getline(std::cin, name);
  }else{
    name = name_;
  }
  try{
      account_map = accounts_.at(name);
    }catch(const std::out_of_range& err){
      printf("The account holder's name is invalid\n");
      return;
    }
  printf("Enter the account number:\n");
  getline(std::cin, input);
  try{
    account_id = stoi(input);
    account = account_map.at(account_id);
  }catch(const std::out_of_range& err){
    printf("Invalid account number: %s\n", input.c_str());
    return;
  }catch(const std::invalid_argument& err){
    printf("Invalid account number: %s\n", input.c_str());
    return;
  }
  if(!account.is_enabled()){
    printf("Payment failed, %d is disabled\n", account_id);
    return;
  }
  printf("Enter the company to whom you wish the pay the bill to:\n");
  getline(std::cin, company);
  char *to_lower = new char [company.length()];
  for(uint i = 0; i < company.length(); i++){
    to_lower[i] = std::tolower(company[i]);
  }
  lower_company = to_lower;

  if(lower_company == "the bright light electric company" || lower_company == "ec"){// TODO: find the company name
    company = "The Bright Light Electric Company";
    company_short = "EC";
  }else if(lower_company == "credit card company q" || lower_company == "cq"){
    company = "Credit Card Company Q";
    company_short = "CQ";
  }else if(lower_company == "low definition tv, inc." || lower_company == "tv"){
    company = "Low Definition TV, Inc.";
    company_short = "TV";
  }else{
    printf("%s is not a valid company to pay a bill to:\n", company.c_str());
    return;
  }
  printf("Enter the amount you wish to pay:\n");
  getline(std::cin, input);
  try{
    value = std::stof(input, &no_convert);
    value = value * 100.0;
    if(no_convert!=input.length()){
        throw std::invalid_argument("non numerical characters");
    }
  }catch(const std::invalid_argument& err){
    printf("Payment, you must enter a numerical value.\n");
    return;
  }catch(const std::out_of_range& err){
    printf("Payment, you must enter a numerical value.\n");
    return;
  }
  if(value > (daily_paybill*100) && !admin_){// TODO: Check for current day maximum Need to chang the code
    printf("You may not pay more than $2000.00 to a bill holder in a day.\n");
    return;
  }else if(value > account.get_balance()){
    printf("Payment to %s of $%0.2f failed, you do not have at least $%0.2f in your account.\n", company.c_str(), value/100.0, value/100.0);
    return;
  }else if(!admin_){
    if(account.is_student()){
      actual_value = value + 5.0;
    }else{
      actual_value = value + 10.0;
    }
    if(actual_value > account.get_balance()){
      printf("Payment failed, you must have at least $0.00 after transaction fees\n");
      return;
    }
  }else{
    actual_value = value;
  }
  daily_paybill = daily_paybill - (value/100);
  printf("Payment to %s of $%0.2f was successful\n", company.c_str(), value/100.0);
  write_file(3,name,account_id,actual_value,company_short);
}

void Session::create() {
  // create"";
  std::string name, input;
  std::string::size_type no_convert;
  std::size_t nonAlpha;
  float balance;

  if(!logged_){
    printf("Transaction denied. Not logged in\n");
    return;
  }
  else if(!admin_){
    printf("Transaction denied. User is not an admin\n");
    return;
  }

  printf("Please input Account Name:\n");
  getline(std::cin, name);
  name = trim(name);
  nonAlpha = name.find_first_not_of("aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ- "); //characters that are allowed for the user to use
  if(name.length() > 20){ //more characters than allowed
    printf("I'm sorry, your name is too long. Please shorten it to 20 characters or less.\n");
    return;
  }
  else if(name.length() == 0){
    printf("Transaction denied. No name detected\n");
    return;
  }
  else if (nonAlpha!=std::string::npos){
    printf("I'm sorry, your name is unrecognizable and cannot be used (get rid of all numbers and symbols)\n");
    return;
  }
  printf("Please input your Initial Balance:\n");
  getline(std::cin, input);
  input = trim(input);
  if(input.length() == 0){
    balance = 0.0;
  }else{
    try{
      balance = std::stof(input,&no_convert);
      balance = balance * 100.0;
      if(no_convert!=input.length()){
        throw std::invalid_argument("Invalid characters");
    }
    }catch(const std::out_of_range& err){
      printf("Transaction denied. Invalid characters\n");
      return;
    }catch(const std::invalid_argument&){
      printf("Transaction denied. Invalid characters\n");
      return;
    }
  }
  if (balance > 9999999.0){
    printf("Transaction denied. Amount entered is too large\n");
    return;
  }
  else if(balance == 0.0){
    printf("An initial balance of 00000.00 has been administered\n");
  }
  printf("A new account was made under the name:\n%s\nwith a current balance of:\n%08.2f\nYour account will be available on the next day\n", name.c_str(), balance/100.0);
  write_file(5,name,0,balance);
}

void Session::remove() {
  // remove
  std::string name, input;
  int account_id = 0;
  std::map<int,Account> account_map;
  std::size_t nonAlpha;
  Account account;
  if(!logged_){
    printf("Transaction denied. Not logged in\n");
    return;
  }else if(!admin_){
    printf("Transaction denied. User is not an admin\n");
    return;
  }

  printf("Please input Account Name:\n");
  getline(std::cin, name);
  name = trim(name);
  nonAlpha = name.find_first_not_of("aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ- "); // Alphabet of usable characters
  if(name.length() > 20) //more characters than allowed
  {
    printf("Transaction denied. Name is too long\n");
    return;
  }
  else if(name.length() == 0){
    printf("Transaction denied. No name detected\n");
    return;
  }
  else if (nonAlpha!=std::string::npos)
  {
    printf("Transaction denied. Non-Alphabetic chracters have been found.");
    return;
  }else{
    try{
      account_map = accounts_.at(name);
    }catch(const std::out_of_range& err){
      printf("I'm sorry, this name is not registered in the bank\n");
      return;
    }
  }
  printf("Please input your Account Number:\n");
  getline(std::cin, input);
  try{
    account_id = std::stoi(input);
    account = account_map.at(account_id);
  }catch(const std::out_of_range& err){
    printf("I'm sorry, the account number given does not match up to the Account Name.\n");
    return;
  }
    printf("The account has been deleted\n");
    write_file(6,name,account_id,0.0);
}

void Session::enable(bool enable) {
  // disable
  std::string name = "";
  std::string input;
  int account_id = 0;
  std::map<int,Account> account_map;
  Account account;
  if(!logged_){
    printf("Transaction denied. Not logged in\n");
    return;
  }else if(!admin_){
    printf("Transaction denied. User is not an admin\n");
  }else{
    printf("Please enter account holder's name:\n");
    getline(std::cin, name);
    try{
      account_map = accounts_.at(name);
    }catch(const std::out_of_range& err){
      printf("The account holder's name is invalid\n");
      return;
    }
    printf("Please enter account number:\n");
    getline(std::cin, input);
    try{
      account_id = std::stoi(input);
      account = account_map.at(account_id);
    }catch(const std::out_of_range& err){
      printf("The account number is invalid\n");
      return;
    }
    if(enable){
      printf("Account has been successfully enabled\n");
      write_file(9,name,account_id,0.0);
    }else{
      printf("Account has been successfully disabled\n");
      write_file(7,name,account_id,0.0);
    }
  }
}

std::string Session::trim(const std::string& str, const std::string& whitespace){
  const auto strBegin = str.find_first_not_of(whitespace);
  if(strBegin == std::string::npos) return ""; // nothing in string

  const auto strEnd = str.find_last_not_of(whitespace);
  const auto strRange = strEnd - strBegin + 1;
  return str.substr(strBegin,strRange);
}
