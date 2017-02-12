
package users;

import java.io.Serializable;

/**
 * An Administrator responsibilities class.
 * Note this class is implemented for p3.
 * @author Kelly
 *
 */
public class Administrator extends User implements Serializable {

  /**
   * Serialization.
   */
  private static final long serialVersionUID = -9105618510388536284L;

  /**
   * Creates a new administrator as a child of User.
   *
   * @param username the username set by the admin.
   * @param password the password set by the admin.
   */
  public Administrator(String username, String password) {
    super(username, password);
  }

  @Override
  public String toString() {
    return this.getUser();
  }
}
