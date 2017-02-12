package users;

import java.io.Serializable;

/**
 * Represents a User, with a username and password.
 * Username field is never changed per User 
 * so we do not need a username setter outside of implementation.
 * 
 * @author Kelly Mo
 */
public class User implements Serializable {

  /**
   * Serialization.
   */
  private static final long serialVersionUID = -8100427899043264619L;
  
  private String user;
  private String pass; // case sensitive

  /**
   * Creates a new User with the given username and password.
   * @param username The username of this User.
   * @param password The password of this User.
   */
  public User(String username, String password) {
    this.user = username;
    this.pass = password;
  }

  /**
   * Returns the username.
   * @return the user
   */
  public String getUser() {
    return user;
  }

  /**
   * Returns the password.
   * @return the pass
   */
  public String getPass() {
    return pass;
  }

  /**
   * Sets the password.
   * @param pass the pass to set
   */
  public void setPass(String pass) {
    this.pass = pass;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((pass == null) ? 0 : pass.hashCode());
    result = prime * result + ((user == null) ? 0 : user.hashCode());
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
    if (!(obj instanceof User)) {
      return false;
    }
    User other = (User) obj;
    if (pass == null) {
      if (other.pass != null) {
        return false;
      }
    } else if (!pass.equals(other.pass)) {
      return false;
    }
    if (user == null) {
      if (other.user != null) {
        return false;
      }
    } else if (!user.equals(other.user)) {
      return false;
    }
    return true;
  }

}
