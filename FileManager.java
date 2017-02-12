package managers;

import cs.b07.flightmanager.Constants;
import flightinfo.Flight;
import flightinfo.Itinerary;
import information.BillingInformation;
import information.PersonalInformation;
import users.Administrator;
import users.Client;
import users.User;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * A manager class working with files.
 * 
 * @author Andrew Wang, Kelly Mo
 */

public class FileManager {

  /**
   * Reads a csv file stored at filePath, and returns an ArrayList of parsed entries,
   * each element in the ArrayList is a String array, which store elements which are
   * separated by commas in the csv file.
   *
   * @param filePath the path of input file
   * @return an ArrayList of String arrays of data stored in the file
   * @throws FileNotFoundException on input/output errors
   */

  public static Map<String,Client> clients;
  private static Map<String,Administrator> admins;
  private static Map<String, Flight> flight;

  /**
   * Returns a Map of all Clients stored in the system.
   *
   * @return a Map of all Clients stored in the system
   */
  public static Map<String, Client> getClients() {
    return clients;
  }

  /**
   * Returns a Map of all Administrators stored in the system.
   *
   * @return a Map of all Administrators stored in the system
   */
  public static Map<String, Administrator> getAdmins() {
    return admins;
  }

  /**
   * Returns a Map of all Flights stored in the system.
   *
   * @return a Map of all Flights stored in the system
   */
  public static Map<String, Flight> getFlight() {
    return flight;
  }

  /**
   * Sets a Map of Clients stored in the system.
   *
   * @param clients a Map of Clients
   */
  public static void setClients(Map<String, Client> clients) {
    FileManager.clients = clients;
  }

  /**
   * Sets a Map of Administrators stored in the system.
   *
   * @param admins a Map of Administrators
   */
  public static void setAdmins(Map<String, Administrator> admins) {
    FileManager.admins = admins;
  }

  /**
   * Sets a Map of Flights stored in the system.
   *
   * @param flight a Map of Flights
   */
  public static void setFlight(Map<String, Flight> flight) {
    FileManager.flight = flight;
  }

  /**
   * Object whose information is stored in file filePath. Taken from a serialized file.
   * @param filePath the file path for our file
   * @param object The object with the object type Administrator, Client or Itinerary
   * @throws IOException if the file is not found
   */
  public static void getObj(String filePath, Object object) throws IOException {
    // Populates the record list using stored data, if it exists.
    File file = new File(filePath);
    if (file.exists()) {
      readFromFile(file.getPath(), object);
    } else {
      // noinspection ResultOfMethodCallIgnored
      file.createNewFile();
    }
  }

  /**
   * Returns an ArrayList populated by data in the csv file saved at filaPath.
   *
   * @param filePath the path of the csv file
   * @return an ArrayList populated by data in the csv file
   * @throws FileNotFoundException the file not found exception
   */
  public static ArrayList<String[]> readCsv(String filePath) throws FileNotFoundException {
    File file = new File(filePath);
    // initialize a list to store each line in the csv file
    ArrayList<String[]> linesList = new ArrayList<String[]>();

    // initialize a scanner to scan through a csv file located at filePath
    Scanner sc = new Scanner(file);
    // initialize a list to store each line in the csv file
    linesList = new ArrayList<String[]>();
    // loop through the csv file
    while (sc.hasNextLine()) {
      // get the next line in the scv file
      String nextLine = sc.nextLine();
      // split the line by commas
      String[] nextLineArray = nextLine.split(",");
      // put the splitted line into the list
      linesList.add(nextLineArray);
    }
    sc.close();
    return linesList;
  }

  /**
   * Appends fileText to a file located at filePath.
   *
   * @param filePath the file path
   * @param fileText the new text
   * @return a true if completed
   */
  public static Boolean writeFile(String filePath, String fileText) {
    try {
      File file = new File(filePath);
      // create new file if if it doesn't already exist
      if (!file.exists()) {
        // noinspection ResultOfMethodCallIgnored
        file.createNewFile();
      }
      FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(fileText + "\n");
      bw.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Returns a Date object converted from dateString.
   * dateString has to be in the format "YYYY-MM-DD HH:MM" or "YYYY-MM-DD".
   *
   * @param dateString a String representation of a date
   * @return a Date object parsed from dateString
   */
  public static Date convertStringToDate(String dateString) {
    // HH converts hour in 24 hours format (0-23), day calculation
    SimpleDateFormat formatWithTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat formatWithoutTime = new SimpleDateFormat("yyyy-MM-dd");
    // initialize a Date object to store the date that is being converted
    Date date = null;
    // attempt to convert the date with a time
    try {
      date = formatWithTime.parse(dateString);
    } catch (Exception e) {
      // attempt to convert the date without a time
      try {
        date = formatWithoutTime.parse(dateString);
      } catch (Exception f) {
        e.printStackTrace();
      }
    }
    return date;
  }

  /**
   * Returns a String representation converted from date, which is a Date object,
   * in the format "YYYY-MM-DD".
   *
   * @param date the Date object being converted
   * @return a String representation of date in the format "YYYY-MM-DD"
   */
  public static String convertDateToString(Date date) {
    // set the format to a date format without a time
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    // convert the Date object into a String representation
    String expectedDate = format.format(date);
    return expectedDate;
  }

  /**
   * Returns a String representation converted from date, which is a Date object,
   * in the format "YYYY-MM-DD HH:MM".
   *
   * @param date the Date object being converted
   * @return a String representation of date in the format "YYYY-MM-DD HH:MM"
   */
  public static String convertDateToStringWithTime(Date date) {
    // set the format to a date format with a time
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    // convert the Date object into a String representation
    String expectedDate = format.format(date);
    return expectedDate;
  }

  /**
   * Returns a String representation converted from date, which is a Date object,
   * in the format "HH:MM".
   *
   * @param date the Date object being converted
   * @return a String representation of date in the format "HH:MM"
   */
  public static String convertDateToStringOnlyTime(Date date) {
    // set the format to a date format with a time
    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    // convert the Date object into a String representation
    String expectedDate = format.format(date);
    return expectedDate;
  }

  /**
   * Returns an ArrayList of Clients with the data in linesList.
   *
   * @param linesList data read from a csv file
   * @return an ArrayList of Clients with the data in linesList
   */
  public static ArrayList<Client> createUserList(ArrayList<String[]> linesList) {
    // initialize a new ArrayList to store the list of Clients which are being returned
    ArrayList<Client> userList = new ArrayList<Client>();
    Client newUser = null;
    // loop through the list of lines in the csv file
    for (String[] nextLine : linesList) {

      // placeholder username and passwords
      User fillUser = new User("username", "password");

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
      newUser = new Client(fillUser.getUser(), fillUser.getPass(), billingInfo,
              personalInfo);
      // add the client we just created into the list
      userList.add(newUser);
    }
    return userList;
  }

  // <--- Serializing Objects -->

  /**
   * Serialization method for reading a file.
   * @param path path to cvs file
   * @param myobject is the type of object to be deserialized
   */
  @SuppressWarnings("unchecked")
  private static void readFromFile(String path, Object myobject) {
    try (
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
    ) {
      //deserialize the array based on what object type we want
      if (myobject.getClass().equals(Client.class)) {
        clients = (Map<String,Client>)input.readObject();
        // add to UserManager List
        UserManager.userList.addAll(clients.values());
      } else if (myobject.getClass().equals(Administrator.class)) {
        admins = (Map<String,Administrator>)input.readObject();
        // add to UserManager List
        UserManager.userList.addAll(admins.values());
      } else if (myobject.getClass().equals(Flight.class)) {
        FlightManager.setFlightsList((ArrayList<Flight>) flight.values());
      }
      input.close();
    } catch (ClassNotFoundException ex) {
      Constants.FLOGGER.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
    } catch (IOException ex) {
      Constants.FLOGGER.log(Level.SEVERE, "Cannot perform input.", ex);
    }
  }

  /**
   * Serializes and writes the User to file outputStream.
   * @param filePath the file to write the records to
   * @param myobject is the type of object to be serialized
   */
  public static void saveToFile(String filePath, Object myobject) {
    try (
            OutputStream file = new FileOutputStream(filePath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
    ) {
      //deserialize the array based on what object type we want
      if (myobject.getClass().equals(Client.class)) {
        output.writeObject(clients);
      } else if (myobject.getClass().equals(Administrator.class)) {
        output.writeObject(admins);
      } else if (myobject.getClass().equals(Itinerary.class)) {
        output.writeObject(flight);
      }
    } catch (IOException ex) {
      Constants.FLOGGER.log(Level.SEVERE, "Cannot perform output.", ex);
    }
  }

  /**
   * Returns an ArrayList of Flights with the data from linesList.
   *
   * @param linesList a list of lines read from a csv file
   * @return an ArrayList of Flights with the data from linesList
   */
  public static ArrayList<Flight> createFlightList(ArrayList<String[]> linesList) {
    // Initialize an ArrayList to store the list of Flights which are being returned
    ArrayList<Flight> flightsList = new ArrayList<Flight>();
    // loop through the list of lines in the csv file
    for (String[] nextLine : linesList) {
      String flightNum = nextLine[0];
      // convert the date and time text into a Date object
      Date depTime = convertStringToDate(nextLine[1]);
      Date arrTime = convertStringToDate(nextLine[2]);
      String airline = nextLine[3];
      String origin = nextLine[4];
      String destination = nextLine[5];
      // convert the price into a double
      double cost = Double.parseDouble(nextLine[6]);
      int numSeats = Integer.parseInt(nextLine[7]);
      // create a new Flight object to store the data fetched from the csv file
      Flight newFlight = new Flight(flightNum, depTime, arrTime, airline,
              origin, destination, cost, numSeats);
      // add the flight we just created into the list
      flightsList.add(newFlight);
    }
    return flightsList;
  }

  /**
   * Read from folder.
   */
  public static ArrayList<String[]> flightFolder(File folder, ArrayList<String[]> allFlights) {
    if (allFlights == null) {
      allFlights = new ArrayList<>();
    }
    // create new file if if it doesn't already exist
    if (!folder.exists()) {
      try {
        folder.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      for (File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
          flightFolder(fileEntry, allFlights);
        } else {
          try {
            allFlights.addAll(FileManager.readCsv(fileEntry.getPath()));
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return allFlights;
  }
}
