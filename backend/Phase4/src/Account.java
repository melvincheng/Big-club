/**
 * This class holds data for individual accounts
 */
public class Account{
  private int id; 			// account number
  private String name; 		// account holder's full name
  private float balance; 	// account balance
  private boolean enabled; 	// flag of account if it is enabled
  private boolean student; 	// flag of account if it has a student plan
  private int count; 		// number of transactions

  /**
  * Constructor
  * all fields are set at construction of object
  */
  public Account(int id, String name, float balance, boolean enabled, boolean student, int count){
    this.id = id;
    this.name = name;
    this.balance = balance;
    this.enabled = enabled;
    this.student = student;
    this.count = count;
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
	public float getBalance() {
		return balance;
	}

	/**
	* Sets new value of balance
	* @param
	*/
	public void setBalance(float balance) {
		this.balance = balance;
	}

	/**
	* Returns value of enabled
	* @return
	*/
	public boolean isEnabled() {
		return enabled;
	}

	/**
	* Sets new value of enabled
	* @param
	*/
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	* Returns value of student
	* @return
	*/
	public boolean isStudent() {
		return student;
	}

	/**
	* Sets new value of student
	* @param
	*/
	public void setStudent(boolean student) {
		this.student = student;
	}

	/**
	* Returns number of transaction account has done
	*/
	public int getCount(){
		return this.count;
	}
	/**
	* increment transaction count
	*
	*/
	public void incCount(){
		count++;
	}
}
