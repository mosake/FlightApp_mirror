package information;

import java.io.Serializable;

/**
 * A representation of the personal information of a client.
 */
public class PersonalInformation implements Serializable {
  
  /**
   * Serialization.
   */
  private static final long serialVersionUID = -5457724858278349036L;
  
  private String lastName;
  private String firstName;
  private String email;
  private String address;

  /**
   * Creates a personal information object given lastName, firstName, email and
   * address.
   * 
   * @param lastName
   *          The lastName of the user.
   * @param firstName
   *          The firstName of the user.
   * @param email
   *          The email address of the user.
   * @param address
   *          The address of residence of the user.
   */
  public PersonalInformation(String lastName, String firstName, String email, String address) {
    super();
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.address = address;
  }

  /**
   * Returns the lastName of this user.
   * 
   * @return the lastName of the user
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the new lastName for the user given a new lastName.
   * 
   * @param lastName
   *          the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Returns the firstName of this user.
   * 
   * @return the firstName of the user.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * sets the new firstName for this user given a new firstName.
   * 
   * @param firstName
   *          the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Returns the email for this user.
   * 
   * @return the email of the user.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the new email for this user given a new email.
   * 
   * @param email
   *          the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Returns the address for this user.
   * 
   * @return the address of the user.
   */
  public String getAddress() {
    return address;
  }

  /**
   * Sets the new address for this user given the new email.
   * 
   * @param address
   *          the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
    if (!(obj instanceof PersonalInformation)) {
      return false;
    }
    PersonalInformation other = (PersonalInformation) obj;
    if (address == null) {
      if (other.address != null) {
        return false;
      }
    } else if (!address.equals(other.address)) {
      return false;
    }
    if (email == null) {
      if (other.email != null) {
        return false;
      }
    } else if (!email.equals(other.email)) {
      return false;
    }
    if (firstName == null) {
      if (other.firstName != null) {
        return false;
      }
    } else if (!firstName.equals(other.firstName)) {
      return false;
    }
    if (lastName == null) {
      if (other.lastName != null) {
        return false;
      }
    } else if (!lastName.equals(other.lastName)) {
      return false;
    }
    return true;
  }

}
