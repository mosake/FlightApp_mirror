package cs.b07.flightmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import flightinfo.Flight;
import managers.FileManager;

import java.util.ArrayList;
import java.util.Date;

/**
 * An adapter for the ListView in FlightSearchResultActivity.
 */
public class FlightsSearchAdapter extends ArrayAdapter<Flight> {

  /**
   * Instantiates a new Flights search adapter.
   *
   * @param context the context
   * @param flights an ArrayList of Flights being displayed in the ListView
   */
  public FlightsSearchAdapter(Context context, ArrayList<Flight> flights) {
    super(context, 0, flights);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Get the data item for this position
    Flight flight = getItem(position);
    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout
              .itineraries_list_item, parent, false);
    }
    // Lookup view for data population
    final TextView departureField = (TextView) convertView.findViewById(R.id.departureField);
    final TextView arrivalField = (TextView) convertView.findViewById(R.id.arrivalField);
    final TextView secondLineField = (TextView) convertView.findViewById(R.id.secondLineField);
    final TextView totalPriceField = (TextView) convertView.findViewById(R.id.totalPriceField);

    // get the price of the flight
    Double totalPrice = flight.getCost();
    // add a dollar sign in front of the String representation of the price
    String totalPriceString = "$" + totalPrice.toString();
    // make sure the String representation of price has exactly 2 decimal places
    if (totalPriceString.charAt(totalPriceString.length() - 2) == '.') {
      totalPriceString += "0";
    }
    // get the travel time of the flight
    Integer totalTime = flight.getTravelTime();
    // convert travel time to the format of "**hr **min"
    Integer totalTimeMin = totalTime % 60;
    Integer totalTimeHour = totalTime / 60;
    String secondLine = totalTimeHour.toString() + "hr " + totalTimeMin.toString() + "min";

    // Get the information from the Flight object
    Date departureTime = flight.getDepartureTime();
    Date arrivalTime = flight.getArrivalTime();

    // Convert Date object into String representation
    String departureTimeString = FileManager.convertDateToStringOnlyTime(departureTime);
    String arrivalTimeString = FileManager.convertDateToStringOnlyTime(arrivalTime);

    // calculate day difference between arrival date and departure date
    // noinspection deprecation
    Integer dayDifference = arrivalTime.getDay() - departureTime.getDay();
    // if the departure date and arrival date are not on the same day
    if (dayDifference > 0) {
      // add a day difference indicator to the end of the arrival time display
      arrivalTimeString += " (+" + dayDifference.toString() + ")";
    }

    // Populate the data into the template view using the data object
    secondLineField.setText(secondLine);
    departureField.setText(departureTimeString);
    arrivalField.setText(arrivalTimeString);
    totalPriceField.setText(totalPriceString);
    // Return the completed view to render on screen
    return convertView;
  }
}
