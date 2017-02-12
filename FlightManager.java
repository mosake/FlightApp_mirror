package managers;

import cs.b07.flightmanager.Constants;
import flightinfo.Flight;
import flightinfo.Itinerary;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * A flight manager class.
 * Stores and manipulates the flights stored in memory to be passes to other classes.
 *
 * @author Andrew Wang
 */
public class FlightManager {

  /**
   * An ArrayList of all the Flights.
   */
  private static ArrayList<Flight> flightsList;

  /**
   * An ArrayList storing the search result when searching for itineraries.
   */
  private static ArrayList<ArrayList<Flight>> searchResult;

  /**
   * Search a flight by flight number.
   *
   * @param inputnumber the Flight Number.
   */
  public static Flight searchFlightByFlightNumber(String inputnumber) {
    for (Flight flight : flightsList) {
      String flightNumber = flight.getFlightNum();
      if (inputnumber.equals(flightNumber)) {
        return (flight);
      }
    }
    return null;
  }

  /**
   * Adds a flight into the system.
   *
   * @param flight the Flight being added.
   */
  public static void addFlight(Flight flight) {
    flightsList.add(flight);
  }

  /**
   * Returns an ArrayList of Flights that departs at origin on date, and arrive at destination.
   * If null is passed in for destination,
   * all flights that departs at origin on date will be returned.
   *
   * @param date        the date of departure
   * @param origin      the origin location
   * @param destination the destination location
   * @return an ArrayList of Flights that departs at origin on date, and arrive at destination
   */

  public static ArrayList<Flight> getFlights(String date, String origin, String destination) {
    // initialize an empty ArrayList to store the search result
    ArrayList<Flight> result = new ArrayList<>();
    // loop through the list of all flights stored in the system
    for (Flight nextFlight : flightsList) {
      // get the departure time for the next flight, and store it in a Date object for comparing
      String nextDate = managers.FileManager.convertDateToString(nextFlight.getDepartureTime());
      String nextOrigin = nextFlight.getOrigin();
      String nextDestination = nextFlight.getDestination();
      // if the destination is not specified
      if (destination == null) {
        // if the origin of next flight matches the searching criteria
        if (origin.equals(nextOrigin)) {
          // add this flight into the list
          result.add(nextFlight);
        }
        // otherwise, there is a specified destination
      } else {
        // if the date, origin, and destination match the searching criteria
        if ((date.equals(nextDate)) && (origin.equals(nextOrigin))
                && (destination.equals(nextDestination))) {
          // add this flight into the list
          result.add(nextFlight);
        }
      }
    }
    return result;
  }

  /**
   * Returns an ArrayList of Itineraries starts from origin, ends at destination,
   * and departs on date.
   *
   * @param date        the date of departure
   * @param origin      the origin location
   * @param destination the destination location
   * @return an ArrayList of Itineraries starts from origin, ends at destination,
   *     and departs on date.
   */
  public static ArrayList<Itinerary> getItineraries(String date, String origin,
                                                    String destination) {
    // initialize an empty ArrayList to store the search result
    searchResult = new ArrayList<>();
    // get all the flights departs from origin on date
    ArrayList<Flight> flightSearchResult = getFlights(date, origin, null);
    // loop through all flights departs from origin on date
    for (Flight nextFlight : flightSearchResult) {
      // initialize an empty ArrayList, and put the first flight into this list,
      // for later recursive operation
      ArrayList<Flight> nextList = new ArrayList<>();
      nextList.add(nextFlight);
      // call the recursive helper function to get all itineraries matching the searching criteria
      getItinerariesHelper(date, destination, nextList);
    }
    // convert the ArrayList of Flights in searchResult into an ArrayList of Itinerary
    ArrayList<Itinerary> result = new ArrayList<>();
    for (ArrayList<Flight> nextListOfFlights : searchResult) {
      double price = getTotalCost(nextListOfFlights);
      int travelTime = getTravelTime(nextListOfFlights);
      Itinerary nextItinerary = new Itinerary(nextListOfFlights, travelTime, price);
      result.add(nextItinerary);
    }
    return result;
  }


  /**
   * Returns an ArrayList of Flights stored in the system.
   *
   * @return an ArrayList of Flights stored in the system
   */
  public static ArrayList<Flight> getFlightsList() {
    return flightsList;
  }

  /**
   * Sets the ArrayList of Flight stored in the system.
   *
   * @param flightsList an ArrayList of Flights
   */
  public static void setFlightsList(ArrayList<Flight> flightsList) {
    FlightManager.flightsList = flightsList;
  }

  /**
   * Returns the sum of price for all Flights in flightList.
   *
   * @param flightList an ArrayList of Flights
   * @return the sum of price for all Flights in flightList.
   */
  public static double getTotalCost(ArrayList<Flight> flightList) {
    double total = 0;
    for (Flight nextFlight : flightList) {
      total += nextFlight.getCost();
    }
    return total;
  }

  /**
   * Returns the total travel time for all Flights in flightList.
   * Total travel time is calculated by the difference between the arrival time of the last flight
   * and the departure time of the first flight.
   *
   * @param flightList an ArrayList of Flights
   * @return the total travel time for all Flights in flightList.
   */
  public static int getTravelTime(ArrayList<Flight> flightList) {
    // get the arrival time for the last flight in the itinerary
    long arrivalTime = flightList.get(flightList.size() - 1).getArrivalTime().getTime();
    // get the departure time for the first flight in the itinerary
    long departureTime = flightList.get(0).getDepartureTime().getTime();
    // calculate the time difference, in milliseconds
    int diff = (int) (arrivalTime - departureTime);
    // convert the time difference to minutes 
    diff = diff / (60 * 1000);
    return diff;
  }


  /**
   * Initialize searchResult, and adds all results of an itinerary search into searchResult.
   * A helper method for getItineraries.
   *
   * @param date        the date of departure
   * @param destination the destination location
   * @param flightList  an ArrayList of only one Flight, which is the first flight in an itinerary.
   */
  private static void getItinerariesHelper(String date, String destination,
                                           ArrayList<Flight> flightList) {
    // get the last flight in the not-yet-completed itinerary
    Flight lastFlight = flightList.get(flightList.size() - 1);
    // get the destination of that flight
    String lastDestination = lastFlight.getDestination();
    // if the itinerary being process is already complete,
    // i.e. the destination of the last flight in the list matches the destination of 
    // the desired destination in the searching criteria
    if (lastDestination.equals(destination)) {
      // store this list of Flights into the list of search result
      searchResult.add(flightList);
    }
    // get all the flights departs from the destination of previous flight
    ArrayList<Flight> flightSearchResult = getFlights(date, lastDestination, null);
    // loop through that list of Flights
    for (Flight nextFlight : flightSearchResult) {
      // get the time difference between the arrival time of previous flight
      // and departure time of next flight, in milliseconds
      long diff = (nextFlight.getDepartureTime().getTime())
              - (lastFlight.getArrivalTime().getTime());
      // convert the time difference into minutes
      diff = diff / (60 * 1000);
      // if the next flight departs 30min later than the arrival time of previous flight,
      // the time difference is no greater than 6 hours, and is not circular
      if ((diff >= 30) && (diff <= 360) && !(flightList.contains(nextFlight))) {
        // add this flight into the itinerary that is being processed
        ArrayList<Flight> nextList = new ArrayList<>(flightList);
        nextList.add(nextFlight);
        // recurse on next flight
        getItinerariesHelper(date, destination, nextList);
      }
    }
  }

  /**
   * Sorts an ArrayList of Itineraries by total cost in non-deceasing order, in place.
   *
   * @param itineraryList an ArrayList of Itineraries
   */
  public static void sortItineraryByCost(ArrayList<Itinerary> itineraryList) {
    for (int i = 0; i < itineraryList.size() - 1; i++) {
      for (int j = i + 1; j < itineraryList.size(); j++) {
        if (itineraryList.get(i).getTotalPrice() > itineraryList.get(j).getTotalPrice()) {
          Collections.swap(itineraryList, i, j);
        }
      }
    }
  }

  /**
   * Sorts an ArrayList of Itineraries by total travel time in non-deceasing order, in place.
   *
   * @param itineraryList an ArrayList of Itineraries
   */
  public static void sortItineraryByTime(ArrayList<Itinerary> itineraryList) {
    for (int i = 0; i < itineraryList.size() - 1; i++) {
      for (int j = i + 1; j < itineraryList.size(); j++) {
        if (itineraryList.get(i).getTotalTime() > itineraryList.get(j).getTotalTime()) {
          Collections.swap(itineraryList, i, j);
        }
      }
    }
  }

  /**
   * Sorts an ArrayList of Flights by total cost in non-deceasing order, in place.
   *
   * @param flightList an ArrayList of Flights
   */
  public static void sortFlightByCost(ArrayList<Flight> flightList) {
    for (int i = 0; i < flightList.size() - 1; i++) {
      for (int j = i + 1; j < flightList.size(); j++) {
        if (flightList.get(i).getCost() > flightList.get(j).getCost()) {
          Collections.swap(flightList, i, j);
        }
      }
    }
  }

  /**
   * Sorts an ArrayList of Flights by total travel time in non-deceasing order, in place.
   *
   * @param flightList an ArrayList of Flights
   */
  public static void sortFlightByTime(ArrayList<Flight> flightList) {
    for (int i = 0; i < flightList.size() - 1; i++) {
      for (int j = i + 1; j < flightList.size(); j++) {
        if (flightList.get(i).getTravelTime() > flightList.get(j).getTravelTime()) {
          Collections.swap(flightList, i, j);
        }
      }
    }
  }

  /**
   * Writes the flights to file outputStream.
   *
   * @param filePath the file to write the records to
   */
  public static void saveToFile(String filePath) {
    try (
            OutputStream file = new FileOutputStream(filePath,true);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer)
    ) {
      output.writeObject(flightsList);
    } catch (IOException ex) {
      Constants.FLOGGER.log(Level.SEVERE, "Cannot perform output.", ex);
    }
  }

  /**
   * Reads the flights from file inputStream.
   *
   * @param path the file to read the records to
   */
  @SuppressWarnings("unchecked")
  public static void readFromFile(String path) {
    try (
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer)
    ) {
      // deserialize the ArrayList
      flightsList = (ArrayList<Flight>) input.readObject();
    } catch (ClassNotFoundException ex) {
      Constants.FLOGGER.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
    } catch (IOException ex) {
      Constants.FLOGGER.log(Level.SEVERE, "Cannot perform input.", ex);
    }
  }

  /**
   * Takes all the flights in the current array and returns the map of flight numbers
   * mapping the flights.
   *
   * @return userMap map of flight numbers mapping the flights
   */
  public static Map<String, Flight> getFlightMap() {
    Map<String, Flight> flightMap = new HashMap<>();
    for (Flight nextflight : flightsList) {
      flightMap.put(nextflight.getFlightNum(), nextflight);
    }
    return flightMap;
  }
}
