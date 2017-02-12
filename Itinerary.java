package flightinfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A representation of an itinerary.
 */
public class Itinerary implements Serializable {

  /**
   * Serialization.
   */
  private static final long serialVersionUID = 1377154768802769354L;
  
  private ArrayList<Flight> listOfFlights;
  private int totalTime;
  private double totalPrice;

  /**
   * Creates an itinerary given list of flights, total time and total price
   * 
   * @param listOfFlights
   *          the list of flights to the given destination.
   * @param totalTime
   *          the total time to reach the destination.
   * @param totalPrice
   *          the total price of the trip.
   */

  public Itinerary(ArrayList<Flight> listOfFlights, int totalTime, double totalPrice) {
    super();
    this.listOfFlights = listOfFlights;
    this.totalTime = totalTime;
    this.totalPrice = totalPrice;
  }

  /**
   * Returns the list of flights.
   * 
   * @return the listOfFlights
   */
  public ArrayList<Flight> getListOfFlights() {
    return listOfFlights;
  }

  /**
   * Sets the new list of flights given the new list of flights.
   * 
   * @param listOfFlights
   *          the listOfFlights to set
   */
  public void setListOfFlights(ArrayList<Flight> listOfFlights) {
    this.listOfFlights = listOfFlights;
  }

  /**
   * Return the total time.
   * 
   * @return the totalTime
   */
  public int getTotalTime() {
    return totalTime;
  }

  /**
   * Sets the new totalTime given the new total cost.
   * 
   * @param totalTime
   *          the totalTime to set
   */
  public void setTotalTime(int totalTime) {
    this.totalTime = totalTime;
  }

  /**
   * Returns the total price.
   * 
   * @return the totalPrice
   */
  public double getTotalPrice() {
    return totalPrice;
  }

  /**
   * Sets the new total price given the new total price.
   * 
   * @param totalPrice
   *          the totalPrice to set
   */
  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  @Override
  /**
   * @return in the format:
   *         Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination
   *         followed by total price (on its own line, exactly two decimal
   *         places), followed by total duration (on its own line, in format
   *         HH:MM).
   */
  public String toString() {
    String result = "";
    for (Flight nextFlight : this.listOfFlights) {
      String nextLine = nextFlight.toString();
      nextLine = nextLine.substring(0, nextLine.lastIndexOf(','));
      result += nextLine;
      result += "\n";
    }
    Double price = this.getTotalPrice();
    String strPrice = price.toString();
    if (strPrice.charAt(strPrice.length() - 2) == '.') {
      strPrice += "0";
    }
    result += strPrice;
    result += "\n";
    int travelTime = this.getTotalTime();
    Integer hours = travelTime / 60 % 24;
    Integer minutes = travelTime % 60;
    String hoursString = hours.toString();
    String minutesString = minutes.toString();
    if (hoursString.length() == 1) {
      hoursString = "0" + hoursString;
    }
    if (minutesString.length() == 1) {
      minutesString = "0" + minutesString;
    }
    result += hoursString + ":" + minutesString;
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((listOfFlights == null) ? 0 : listOfFlights.hashCode());
    long temp;
    temp = Double.doubleToLongBits(totalPrice);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + totalTime;
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
    if (!(obj instanceof Itinerary)) {
      return false;
    }
    Itinerary other = (Itinerary) obj;
    if (listOfFlights == null) {
      if (other.listOfFlights != null) {
        return false;
      }
    } else if (!listOfFlights.equals(other.listOfFlights)) {
      return false;
    }
    if (Double.doubleToLongBits(totalPrice) != Double.doubleToLongBits(other.totalPrice)) {
      return false;
    }
    if (totalTime != other.totalTime) {
      return false;
    }
    return true;
  }

}
