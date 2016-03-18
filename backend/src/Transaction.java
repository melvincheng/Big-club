/**
 * This class holds data for each individual transaction
 */

public class Transaction{
  private byte transCode; // transaction code of the transaction
  private String transName; // account holder's name for the transaction
  private int transId; // account ID on the transaction
  private double value; // amount of funds involved in the transaction
  private String misc; // extra info in the transaction

  /**
  * Default Transactions constructor
  */
  public Transaction(byte transCode, String transName, int transId, double value, String misc) {
    this.transCode = transCode;
    this.transName = transName;
    this.transId = transId;
    this.value = value;
    this.misc = misc;
  }

  /**
  * Returns value of transCode
  * @return
  */
  public byte getTransCode() {
    return transCode;
  }

  /**
  * Returns value of transName
  * @return
  */
  public String getTransName() {
    return transName;
  }

  /**
  * Returns value of transId
  * @return
  */
  public int getTransId() {
    return transId;
  }

  /**
  * Returns value of value
  * @return
  */
  public double getValue() {
    return value;
  }


  /**
  * Returns value of misc
  * @return
  */
  public String getMisc() {
    return misc;
  }
}
