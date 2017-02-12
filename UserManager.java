package managers;

import static managers.FileManager.convertStringToDate;

import information.BillingInformation;
import information.PersonalInformation;
import users.Administrator;
import users.Client;
import users.User;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A user manager class.
 * Stores and manipulates the users stored in memory to be passes to other classes.
 * @author Kelly Mo
 *
 */
public class UserManager {
  public static ArrayList<User> userList;

  public static void addUser(User user) {
    userList.add(user);
  }


  /**
   * Returns the string representation of a user based on their username field.
   * (i.e email for clients)
   * Returns empty string if user does not exist.
   * @param email The user's email address
   * @return String representation of a user or a blank string.
   */
  public static Client getUser(String email) {
    for (User nextUser: userList) {
      if (nextUser instanceof Client) {
        if (((Client) nextUser).getPersonalInfo().getEmail().equals(email)) {
          return (Client) nextUser;
        }
      }
    }
    return null;
  }

  /**
   * Returns the string representation of a user based on their username field.
   * (i.e email for clients)
   * Returns empty string if user does not exist.
   * @param username The user's username
   * @return String representation of a user or a blank string.
   */
  public static Client getUserByUsername(String username) {
    for (User nextUser: userList) {
      if (nextUser instanceof Client) {
        if (nextUser.getUser().equals(username)) {
          return (Client) nextUser;
        }
      }
    }
    return null;
  }

  /**
   * Takes all the clients in the current array and returns them with username mapping the client.
   * @return userMap username mapping the client
   */
  public static Map<String,Client> getClientMap() {
    Map<String,Client> userMap = new HashMap<>();
    if (userList == null) {
      userList = new ArrayList<>();
    } else {
      for (User nextuser: userList) {
        if (nextuser instanceof Client) {
          userMap.put(nextuser.getUser(), (Client) nextuser);
        }
      }
    }
    return userMap;
  }

  /**
   * Takes all the admins in the current array and returns them with username mapping the admins.
   * @return userMap username mapping the admins
   */
  public static Map<String,Administrator> getAdminMap() {
    Map<String,Administrator> userMap = new HashMap<>();
    if (userList == null) {
      userList = new ArrayList<>();
    } else {
      for (User nextuser : userList) {
        if (nextuser instanceof Administrator) {
          userMap.put(nextuser.getUser(), (Administrator) nextuser);
        }
      }
    }
    return userMap;
  }

  /**
   * Returns an ArrayList of Users with the data from linesList.
   *
   * @param infoList a list of lines read from a csv file
   * @return an ArrayList of Clients and Administrators with the data from linesList
   */
  public static ArrayList<User> createUsersList(ArrayList<String[]> infoList,
                                                ArrayList<String[]> loginlist) {
    // initialize a new ArrayList to store the list of Clients which are being returned
    ArrayList<User> userList = new ArrayList<User>();

    // placeholder username and passwords
    User newUser = new User("username", "password");

    // loop through the list of lines in the csv file
    for (int i = 0; i < infoList.size(); i++) {
      String[] nextLine = infoList.get(i);

      String username = loginlist.get(i)[0];
      String password = loginlist.get(i)[1];

      // read admin
      if (nextLine.length == 1) {
        newUser = new Administrator(username, password);
      } else {
        String lastName = nextLine[0];
        String firstName = nextLine[1];
        String email = nextLine[2];
        String address = nextLine[3];
        String cardNumber = nextLine[4];
        // card expiry date
        Date expDate = convertStringToDate(nextLine[5]);

        BillingInformation billingInfo = new BillingInformation(cardNumber, expDate);
        PersonalInformation personalInfo = new PersonalInformation(lastName, firstName,
                email, address);
        // create a new Client object to store the data fetched from the csv file
        newUser = new Client(username, password, billingInfo,
                personalInfo);
      }
      // add the client we just created into the list
      userList.add(newUser);
    }
    return userList;
  }

  /**
   * Updates client information in the Array.
   *
   * @param client The client with information we want to update
   */
  public static void updateClient(Client client) {
    if (userList == null) {
      userList = new ArrayList<>();
    }
    for (User nextUser: userList) {
      if (nextUser instanceof Client) {
        if (nextUser.getUser().equals(client.getUser())) {
          userList.set(userList.indexOf(nextUser), client);
        }
      }
    }
  }

  /**
   * Returns an Administrator whose username is username.
   *
   * @param username the username of the Administrator
   * @return an Administrator whose username is username
   */
  public static Administrator getAdminByUsername(String username) {
    for (User nextUser: userList) {
      if (nextUser instanceof Administrator) {
        if (nextUser.getUser().equals(username)) {
          return (Administrator) nextUser;
        }
      }
    }
    return null;
  }

  public static ArrayList<User> getList() {
    return userList;
  }


  /**
   * Writes the users to file outputStream.
   * @param filePath the file to write the records to
   */
  public static void saveToFile(String filePath) {
    try (
        OutputStream file = new FileOutputStream(filePath,true);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer)
        ) {
      output.writeObject(userList);
    } catch (IOException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform output.", ex);
    }
  }

  /**
   * Reads the users from file.
   * @param path file path
   */
  @SuppressWarnings({ "unchecked" })
  public static void readFromFile(String path) {
    try (
        InputStream file = new FileInputStream(path);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer)
        ) {
      // deserialize the ArrayList
      userList = (ArrayList<User>)input.readObject();
    } catch (ClassNotFoundException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
    } catch (IOException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
    }
  }

  private static final Logger fLogger =
      Logger.getLogger(FlightManager.class.getPackage().getName());

}
