#include <iostream>
#include <string>
#include "session.h"

int main() {
  // say welcome to user here

  std::string input;  // holds input
  // create session object
  Session sesh = Session();
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
      std::cout << "Command not recognized" << std::endl;
    }
  }
}
