package information;

import java.io.Serializable;
import java.util.Date;

/**
 * A representation of the billing information of a client.
 */
public class BillingInformation implements Serializable {

  /**
   * Serialization.
   */
  private static final long serialVersionUID = 4979978546368977516L;
  
  private String creditCardNo;
  private Date expiryDate;

  /**
   * Creates a BillingInformation object given cardNumber and expDate.
   * @param cardNumber the credit card number of the user
   * @param expDate the expiryDate of the user.
   */
  public BillingInformation(String cardNumber, Date expDate) {
    super();
    this.creditCardNo = cardNumber;
    this.expiryDate = expDate;
  }

  /**.
   * Returns the creditCardNo of the user
   * 
   * @return the creditCardNo
   */
  public String getCreditCardNo() {
    return creditCardNo;
  }

  /**.
   * Sets the new creditCardNo of the user given a new creditCardNo
   * 
   * @param creditCardNo
   *          the creditCardNo to set
   */
  public void setCreditCardNo(String creditCardNo) {
    this.creditCardNo = creditCardNo;
  }

  /**
   * Returns the expiryDate for the Credit Card of the user.
   * 
   * @return the expiryDate for the Credit Card
   */
  public String getExpiryDate() {
    return managers.FileManager.convertDateToString(expiryDate);
  }

  /**
   * Sets the new expireyDate for the creditCard given a new expiryDate.
   * 
   * @param expiryDate
   *          the expiryDate to set
   */
  public void setExpiryDate(Date expiryDate) {
    this.expiryDate = expiryDate;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((creditCardNo == null) ? 0 : creditCardNo.hashCode());
    result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof BillingInformation)) {
      return false;
    }
    BillingInformation other = (BillingInformation) obj;
    if (creditCardNo == null) {
      if (other.creditCardNo != null) {
        return false;
      }
    } else if (!creditCardNo.equals(other.creditCardNo)) {
      return false;
    }
    if (expiryDate == null) {
      if (other.expiryDate != null) {
        return false;
      }
    } else if (!expiryDate.equals(other.expiryDate)) {
      return false;
    }
    return true;
  }

}
