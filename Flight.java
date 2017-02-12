package flightinfo;

import java.io.Serializable;
import java.util.Date;

/**
 * A representation of a flight.
 */
public class Flight implements Serializable {

  /**
   * Serialization.
   */
  private static final long serialVersionUID = 2882209805344509864L;
  
  /**.
  * Local constants.
  */
  private String flightNum;
  private Date depTime;
  private Date arrTime;
  private String airline;
  private String origin;
  private String destination;
  private Double cost;
  private Integer numSeat;
  private Integer numPassenger = 0;

  public void setFlightNum(String flightNum) {
    this.flightNum = flightNum;
  }

  public void setDepTime(Date depTime) {
    this.depTime = depTime;
  }

  public void setArrTime(Date arrTime) {
    this.arrTime = arrTime;
  }

  public void setAirline(String airline) {
    this.airline = airline;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

  /**
   * Creates a Flight Object given flight number, departure time, arrival time,
   * airline, origin, destination,cost.
   * @param flightNum the flight number of the flight.
   * @param depTime the departure time of the flight.
   * @param arrTime the arrival time of the flight.
   * @param airline the airline the flight belongs to.
   * @param origin the origin the flight is leaving from
   * @param destination the destination of the flight .
   * @param cost the cost of the flight
   * @param numSeat the number of avaiable seats of the flight
   */

  public Flight(String flightNum, Date depTime, Date arrTime, 
      String airline, String origin, String destination,
      Double cost, int numSeat) {
    this.flightNum = flightNum;
    this.depTime = depTime;
    this.arrTime = arrTime;
    this.airline = airline;
    this.origin = origin;
    this.destination = destination;
    this.cost = cost;
    this.numSeat = numSeat;
  }

  /**.
   * 
   * @return the travel time of the flight.
   */
  public int getTravelTime() {
    double diff = this.arrTime.getTime() - this.depTime.getTime();
    double diffMinutes = diff / (60 * 1000);
    return (int) diffMinutes;
  }

  /**.
   * 
   * @return the destination of the flight
   */
  public String getDestination() {
    return this.destination;
  }

  /**.
   * 
   * @return the Cost of the flight.
   */
  public Double getCost() {
    return this.cost;
  }

  /**.
   * 
   * @return the airline of the flight.
   */
  public String getAirline() {
    return this.airline;
  }

  /**.
   *
   * @return the origin of the flight.
   */
  public String getOrigin() {
    return this.origin;
  }

  /**.
   * 
   * @return the flightNum the flight number of the flight.
   */
  public String getFlightNum() {
    return this.flightNum;
  }

  /**.
   * 
   * @return the depTime the departure time of the flight.
   */
  public Date getDepartureTime() {
    return this.depTime;
  }

  /**.
   * 
   * @return the arrTime the arrival time of the flight.
   */
  public Date getArrivalTime() {
    return this.arrTime;
  }

  /**
   * Returns the number of seats of the flight.
   *
   * @return the number of seats of the flight
   */
  public Integer getNumSeat() {
    return numSeat;
  }

  /**
   * Sets the number of seats of the flight.
   *
   * @param numSeat the number of seats of the flight
   */
  public void setNumSeat(Integer numSeat) {
    this.numSeat = numSeat;
  }

  /**
   * Returns the number of passengers of the flight.
   *
   * @return the number of passengers of the flight
   */
  public Integer getNumPassenger() {
    return numPassenger;
  }

  /**
   * Sets the number of passengers of the flight.
   *
   * @param numPassenger the number of passengers of the flight
   */
  public void setNumPassenger(Integer numPassenger) {
    this.numPassenger = numPassenger;
  }

  /**
   * Increments one to the number of passengers of the flight.
   */
  public void addOnePassenger() {
    this.numPassenger++;
  }

  @Override
  /**.
   * format:
   * Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination,Price
   * (the dates are in the format YYYY-MM-DD; the price has exactly two decimal
   * places)
   */
  public String toString() {
    String strCost = this.getCost().toString();
    if (strCost.charAt(strCost.length() - 2) == '.') {
      strCost += "0";
    }
    return flightNum + "," + managers.FileManager.convertDateToStringWithTime(depTime) + ','
        + managers.FileManager.convertDateToStringWithTime(arrTime) + "," + airline 
        + "," + origin + "," + destination
        + "," + strCost;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((airline == null) ? 0 : airline.hashCode());
    result = prime * result + ((arrTime == null) ? 0 : arrTime.hashCode());
    result = prime * result + ((cost == null) ? 0 : cost.hashCode());
    result = prime * result + ((depTime == null) ? 0 : depTime.hashCode());
    result = prime * result + ((destination == null) ? 0 : destination.hashCode());
    result = prime * result + ((flightNum == null) ? 0 : flightNum.hashCode());
    result = prime * result + ((numPassenger == null) ? 0 : numPassenger.hashCode());
    result = prime * result + ((numSeat == null) ? 0 : numSeat.hashCode());
    result = prime * result + ((origin == null) ? 0 : origin.hashCode());
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
    if (!(obj instanceof Flight)) {
      return false;
    }
    Flight other = (Flight) obj;
    if (airline == null) {
      if (other.airline != null) {
        return false;
      }
    } else if (!airline.equals(other.airline)) {
      return false;
    }
    if (arrTime == null) {
      if (other.arrTime != null) {
        return false;
      }
    } else if (!arrTime.equals(other.arrTime)) {
      return false;
    }
    if (cost == null) {
      if (other.cost != null) {
        return false;
      }
    } else if (!cost.equals(other.cost)) {
      return false;
    }
    if (depTime == null) {
      if (other.depTime != null) {
        return false;
      }
    } else if (!depTime.equals(other.depTime)) {
      return false;
    }
    if (destination == null) {
      if (other.destination != null) {
        return false;
      }
    } else if (!destination.equals(other.destination)) {
      return false;
    }
    if (flightNum == null) {
      if (other.flightNum != null) {
        return false;
      }
    } else if (!flightNum.equals(other.flightNum)) {
      return false;
    }
    if (numPassenger == null) {
      if (other.numPassenger != null) {
        return false;
      }
    } else if (!numPassenger.equals(other.numPassenger)) {
      return false;
    }
    if (numSeat == null) {
      if (other.numSeat != null) {
        return false;
      }
    } else if (!numSeat.equals(other.numSeat)) {
      return false;
    }
    if (origin == null) {
      if (other.origin != null) {
        return false;
      }
    } else if (!origin.equals(other.origin)) {
      return false;
    }
    return true;
  }

}
