package cs.b07.flightmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import flightinfo.Flight;
import flightinfo.Itinerary;
import managers.FileManager;

import java.util.ArrayList;
import java.util.Date;

/**
 * An adapter for the ListView in FlightSearchResultActivity.
 */
public class ItinerariesAdapter extends ArrayAdapter<Itinerary> {

  /**
   * Instantiates a new Itineraries adapter.
   *
   * @param context     the context
   * @param itineraries an ArrayList of Itineraries being displayed in the ListView
   */
  public ItinerariesAdapter(Context context, ArrayList<Itinerary> itineraries) {
    super(context, 0, itineraries);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Get the data item for this position
    Itinerary itinerary = getItem(position);
    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.itineraries_list_item,
              parent, false);
    }
    // Lookup view for data population
    final TextView departureField = (TextView) convertView.findViewById(R.id.departureField);
    final TextView arrivalField = (TextView) convertView.findViewById(R.id.arrivalField);
    final TextView secondLineField = (TextView) convertView.findViewById(R.id.secondLineField);
    final TextView connectingFlightField = (TextView) convertView.findViewById(R.id
            .connectingFlightNumber);
    final TextView totalPriceField = (TextView) convertView.findViewById(R.id.totalPriceField);

    // get the list of flights from the itinerary
    ArrayList<Flight> flights = itinerary.getListOfFlights();

    // get the departure time and the arrival time of this itinerary, which is the departure time
    // of the first flight and the arrival time for the last flight
    Date departureTime = flights.get(0).getDepartureTime();
    Date arrivalTime = flights.get(flights.size() - 1).getArrivalTime();

    // get the total price of this itinerary
    Double totalPrice = itinerary.getTotalPrice();
    // add a dollar sign in front of the String representation of the price
    String totalPriceString = "$" + totalPrice.toString();
    // make sure the String representation of price has exactly 2 decimal places
    if (totalPriceString.charAt(totalPriceString.length() - 2) == '.') {
      totalPriceString += "0";
    }

    // get the number of connecting flights in this itinerary
    Integer numFlights = flights.size();
    String numFlightsString;
    // if this is a direct flight, display "D" for direct
    if (numFlights == 1) {
      numFlightsString = "D";
    } else {
      // otherwise, display the number of connecting flights
      numFlightsString = numFlights.toString();
    }

    // calculate day difference between arrival date and departure date
    // noinspection deprecation
    Integer dayDifference = arrivalTime.getDay() - departureTime.getDay();

    // Convert Date object into String representation
    String departureTimeString = FileManager.convertDateToStringOnlyTime(departureTime);
    String arrivalTimeString = FileManager.convertDateToStringOnlyTime(arrivalTime);

    // if the departure date and arrival date are not on the same day
    if (!dayDifference.equals(0)) {
      // add a day difference indicator to the end of the arrival time display
      arrivalTimeString += " (+" + dayDifference.toString() + ")";
    }

    // get the travel time of the flight
    Integer totalTime = itinerary.getTotalTime();
    // convert travel time to the format of "**hr **min"
    Integer totalTimeMin = totalTime % 60;
    Integer totalTimeHour = totalTime / 60;
    String secondLine = totalTimeHour.toString() + "hr " + totalTimeMin.toString() + "min";

    // Populate the data into the template view using the data object
    departureField.setText(departureTimeString);
    arrivalField.setText(arrivalTimeString);
    secondLineField.setText(secondLine);
    connectingFlightField.setText(numFlightsString);
    totalPriceField.setText(totalPriceString);
    // Return the completed view to render on screen
    return convertView;
  }
}
