package users;

import flightinfo.Flight;
import flightinfo.Itinerary;
import information.BillingInformation;
import information.PersonalInformation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A client object class.
 * @author mokelly1
 *
 */
public class Client extends User implements Serializable {

  /**
   * Serialization.
   */
  private static final long serialVersionUID = -8890434359999176522L;

  private ArrayList<Itinerary> bookedItn = new ArrayList<>();
  private BillingInformation billingInfo;
  private PersonalInformation personalInfo;

  /**
   * Initializes a new client object.
   * @param username The client's user name. The field is also saved as the email.
   * @param password The client's password. Default as lastNamefirstName.
   * @param billing The client's billing information.
   * @param personal The client's personal information
   */
  public Client(String username, String password, BillingInformation billing,
                PersonalInformation personal) {
    super(username, password);
    this.billingInfo = billing;
    this.personalInfo = personal;
  }

  /**
   * Returns the client's billing information.
   * @return the billingInfo
   */
  public BillingInformation getBillingInfo() {
    return billingInfo;
  }

  /**
   * Sets the client's billing information.
   * @param billingInfo the billingInfo to set
   */
  public void setBillingInfo(BillingInformation billingInfo) {
    this.billingInfo = billingInfo;
  }

  /**
   * Returns the client's personal info.
   * @return the personalInfo
   */
  public PersonalInformation getPersonalInfo() {
    return personalInfo;
  }

  /**
   * Sets the client's personal info.
   * @param personalInfo the personalInfo to set
   */
  public void setPersonalInfo(PersonalInformation personalInfo) {
    this.personalInfo = personalInfo;
  }

  /**
   * Returns a list of booked itineraries.
   * @return the bookedItn
   */
  public ArrayList<Itinerary> getBookedItn() {
    return bookedItn;
  }

  /**
   * Add an itinerary to the Client's booked itineraries list.
   * @param bookedItn the booked itinerary to be added
   */
  public void addBookedItn(Itinerary bookedItn) {
    this.bookedItn.add(bookedItn);
    ArrayList<Flight> listOfFlights = bookedItn.getListOfFlights();
    for (Flight nextFlight: listOfFlights) {
      nextFlight.addOnePassenger();
    }
  }

  /**
   * Returns a Client object as a string. format:
   * LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate
   * 
   * @return client as a string representation
   */
  @Override
  public String toString() {
    return (this.personalInfo.getLastName() + "," + this.personalInfo.getFirstName() + ","
            + this.personalInfo.getEmail() + "," + this.personalInfo.getAddress() + ","
        + this.billingInfo.getCreditCardNo() + "," + this.billingInfo.getExpiryDate());
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((billingInfo == null) ? 0 : billingInfo.hashCode());
    result = prime * result + ((bookedItn == null) ? 0 : bookedItn.hashCode());
    result = prime * result + ((personalInfo == null) ? 0 : personalInfo.hashCode());
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
    if (!super.equals(obj)) {
      return false;
    }
    if (!(obj instanceof Client)) {
      return false;
    }
    Client other = (Client) obj;
    if (billingInfo == null) {
      if (other.billingInfo != null) {
        return false;
      }
    } else if (!billingInfo.equals(other.billingInfo)) {
      return false;
    }
    if (bookedItn == null) {
      if (other.bookedItn != null) {
        return false;
      }
    } else if (!bookedItn.equals(other.bookedItn)) {
      return false;
    }
    if (personalInfo == null) {
      if (other.personalInfo != null) {
        return false;
      }
    } else if (!personalInfo.equals(other.personalInfo)) {
      return false;
    }
    return true;
  }
  
}
