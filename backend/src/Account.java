package sagyou.jikan;

/*
 * This class holds data for individual accounts
 */
public class Account{
  private int id; // account number
  private String name; // account holder's full name
  private double balance; // account balance
  private boolean enabled; // flag of account if it is enabled
  private boolean student; // flag of account if it has a student plan

  /**
   * Constructor
   * all fields are set at construction of object
   */
  public Account(int id, String name, double balance, boolean enabled, boolean student){
    this.id = id;
    this.name = name;
    this.balance = balance;
    this.enabled = enabled;
    this.student = student;
  }

	/**
	* Returns value of id
	* @return
	*/
	public int getId() {
		return id;
	}

  /**
	* Returns value of name
	* @return
	*/
	public String getName() {
		return name;
	}

  /**
  * Returns value of balance
  * @return
  */
  public double getBalance(){
    return balance;
  }


	/**
	* Returns value of enabled
	* @return
	*/
	public boolean isEnabled() {
		return enabled;
	}

	/**
	* Returns value of student
	* @return
	*/
	public boolean isStudent() {
		return student;
	}

}
