package driver;

import flightinfo.Flight;
import flightinfo.Itinerary;
import managers.FlightManager;
import managers.UserManager;
import users.Client;
import users.User;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/** A Driver used for autotesting the project backend. */
public class Driver {

  /**
   * Uploads client information to the application from the file at the given
   * path.
   * 
   * @param path
   *          the path to an input csv file of client information with lines in
   *          the format:
   *          LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate (the
   *          ExpiryDate is stored in the format YYYY-MM-DD)
   */
  public static void uploadClientInfo(String path) {
    try {
      // read the csv file and store the data into a list
      ArrayList<String[]> linesList = managers.FileManager.readCsv(path);
      // create a list of users using the data in the list
      ArrayList<Client> userList = managers.FileManager.createUserList(linesList);
      // loop through the list of user
      for (User user : userList) {
        // add each user into the user manager
        managers.UserManager.addUser(user);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Uploads flight information to the application from the file at the given
   * path.
   * 
   * @param path
   *          the path to an input csv file of flight information with lines in
   *          the format:
   *          Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,
   *          Destination,Price (the dates are in the format YYYY-MM-DD; the
   *          price has exactly two decimal places)
   */
  public static void uploadFlightInfo(String path) {
    try {
      // read the csv file and store the data into a list
      ArrayList<String[]> linesList = managers.FileManager.readCsv(path);
      // create a list of flights using the data in the list
      ArrayList<Flight> flightList = managers.FileManager.createFlightList(linesList);
      // loop through the list of flights
      for (Flight flight : flightList) {
        // add each flight into the flight manager
        managers.FlightManager.addFlight(flight);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the information stored for the client with the given email.
   * 
   * @param email
   *          the email address of a client
   * @return the information stored for the client with the given email in this
   *         format:
   *         LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate (the
   *         ExpiryDate is stored in the format YYYY-MM-DD)
   */
  public static String getClient(String email) {
    // get the user with that email from the user manager, and convert it into a String
    return UserManager.getUser(email).toString();
  }

  /**
   * Returns all flights that depart from origin and arrive at destination on
   * the given date.
   * 
   * @param date
   *          a departure date (in the format YYYY-MM-DD)
   * @param origin
   *          a flight origin
   * @param destination
   *          a flight destination
   * @return the flights that depart from origin and arrive at destination on
   *         the given date formatted with one flight per line in exactly this
   *         format:
   *         Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *         ,Price (the dates are in the format YYYY-MM-DD; the price has
   *         exactly two decimal places)
   */
  public static String getFlights(String date, String origin, String destination) {
    // initialize an empty String to store the information that is being returned
    String result = "";
    // search for all flights that satisfy the searching criteria
    ArrayList<Flight> searchedFlightsList = FlightManager.getFlights(date, origin, destination);
    // loop through the list of search result
    for (Flight nextFlight : searchedFlightsList) {
      // convert each entry into a String representation
      result += nextFlight.toString();
      result += "\n";
    }
    // if the search returned at least one entry, remove the extra empty line at the end
    if (result.length() != 0) {
      result = result.substring(0, result.length() - 1);
    }
    return result;
  }

  /**
   * Returns all itineraries that depart from origin and arrive at destination
   * on the given date. If an itinerary contains two consecutive flights F1 and
   * F2, then the destination of F1 should match the origin of F2. To simplify
   * our task, if there are more than 6 hours between the arrival of F1 and the
   * departure of F2, then we do not consider this sequence for a possible
   * itinerary (we judge that the stopover is too long).
   * 
   * @param date
   *          a departure date (in the format YYYY-MM-DD)
   * @param origin
   *          a flight original
   * @param destination
   *          a flight destination
   * @return itineraries that depart from origin and arrive at destination on
   *         the given date with stopovers at or under 6 hours. Each itinerary
   *         in the output should contain one line per flight, in the format:
   *         Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *         followed by total price (on its own line, exactly two decimal
   *         places), followed by total duration (on its own line, in format
   *         HH:MM).
   */
  public static String getItineraries(String date, String origin, String destination) {
    // search for the itineraries that satisfy the searching criteria
    ArrayList<Itinerary> itineraries = FlightManager.getItineraries(date, origin, destination);
    // initialize an empty String to store the information that is being returned
    String result = "";
    // loop through the list of search result
    for (Itinerary nextItinerary : itineraries) {
      // convert each entry into a String representation
      result += nextItinerary.toString();
      result += "\n";
    }
    // if the search returned at least one entry, remove the extra empty line at the end
    if (result.length() != 0) {
      result = result.substring(0, result.length() - 1);
    }
    return result;
  }

  /**
   * Returns the same itineraries as getItineraries produces, but sorted
   * according to total itinerary cost, in non-decreasing order.
   * 
   * @param date
   *          a departure date (in the format YYYY-MM-DD)
   * @param origin
   *          a flight original
   * @param destination
   *          a flight destination
   * @return itineraries (sorted in non-decreasing order of total itinerary
   *         cost) that depart from origin and arrive at destination on the
   *         given date with stopovers at or under 6 hours. Each itinerary in
   *         the output should contain one line per flight, in the format:
   *         Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *         followed by total price (on its own line, exactly two decimal
   *         places), followed by total duration (on its own line, in format
   *         HH:MM).
   */
  public static String getItinerariesSortedByCost(String date, String origin, String destination) {
    // search for the itineraries that satisfy the searching criteria
    ArrayList<Itinerary> itineraries = FlightManager.getItineraries(date, origin, destination);
    // sort the search result by cost, in non-decreasing order
    FlightManager.sortItineraryByCost(itineraries);
    // initialize an empty String to store the information that is being returned
    String result = "";
    // loop through the list of search result
    for (Itinerary nextItinerary : itineraries) {
      // convert each entry into a String representation
      result += nextItinerary.toString();
      result += "\n";
    }
    // if the search returned at least one entry, remove the extra empty line at the end
    if (result.length() != 0) {
      result = result.substring(0, result.length() - 1);
    }
    return result;
  }

  /**
   * Returns the same itineraries as getItineraries produces, but sorted
   * according to total itinerary travel time, in non-decreasing order.
   * 
   * @param date
   *          a departure date (in the format YYYY-MM-DD)
   * @param origin
   *          a flight original
   * @param destination
   *          a flight destination
   * @return itineraries (sorted in non-decreasing order of travel itinerary
   *         travel time) that depart from origin and arrive at destination on
   *         the given date with stopovers at or under 6 hours. Each itinerary
   *         in the output should contain one line per flight, in the format:
   *         Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *         followed by total price (on its own line, exactly two decimal
   *         places), followed by total duration (on its own line, in format
   *         HH:MM).
   */
  public static String getItinerariesSortedByTime(String date, String origin, String destination) {
    // search for the itineraries that satisfy the searching criteria
    ArrayList<Itinerary> itineraries = FlightManager.getItineraries(date, origin, destination);
    // sort the search result by toral travel time, in non-decreasing order
    FlightManager.sortItineraryByTime(itineraries);
    // initialize an empty String to store the information that is being returned
    String result = "";
    // loop through the list of search result
    for (Itinerary nextItinerary : itineraries) {
      // convert each entry into a String representation
      result += nextItinerary.toString();
      result += "\n";
    }
    // if the search returned at least one entry, remove the extra empty line at the end
    if (result.length() != 0) {
      result = result.substring(0, result.length() - 1);
    }
    return result;
  }

}
