/**
 * main.cpp
 * @author Santiago Bonada
 * @author Melvin Cheng
 * @author Adam Beckett May
 * @version 0.1
 * The main.cpp contains the main function, the entry point of the banking
 * application. From here commands are inputted and the functions are ran
 *
 */

#include <iostream>
#include <string>
#include "session.h"

int main(int argc, char* argv[]) {
  std::string accounts_file;
  std::string transaction_file;

  /*
   * gets the argument for the current accounts file and transaction file
   * if there are no arguments, program defaults to
   * "CurrentAccount.txt" and "transactions.atf"
  */
  if(argc >= 2){
    accounts_file = argv[1];
    transaction_file = argv[2];
  }else{
    accounts_file = "CurrentAccount.txt";
    transaction_file = "transactions.atf";
  }

  std::string input;  // holds input
  // create session object
  Session sesh = Session(accounts_file,transaction_file);

  // say welcome to user here
  std::cout << "Welcome" << std::endl;
  // create input acceptance loop
  while (1) {
    // get input
    std::cout << "Please enter a transaction:" << std::endl;
    getline(std::cin,input);
    // check input, if it's valid, call respective function
    if(!input.compare("login")){
      sesh.login();
    }else if(!input.compare("logout")){
      sesh.logout();
    }else if(!input.compare("withdrawal")||!input.compare("withdraw")){
      sesh.withdrawal();
    }else if(!input.compare("deposit")){
      sesh.deposit();
    }else if(!input.compare("changeplan")){
      sesh.changeplan();
    }else if(!input.compare("transfer")){
      sesh.transfer();
    }else if(!input.compare("paybill")){
      sesh.paybill();
    }else if(!input.compare("create")){
      sesh.create();
    }else if(!input.compare("delete")){
      sesh.remove();
    }else if(!input.compare("disable")){
      sesh.enable(false);
    }else if(!input.compare("enable")){
      sesh.enable(true);
    }else if(!input.compare("quit")){
      return 0;
    }else{
    // otherwise, gracefully inform user that they entered invalid command
      std::cout << "Invalid transaction" << std::endl;
    }
  }
}
