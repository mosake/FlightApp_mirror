package cs.b07.flightmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import flightinfo.Flight;

import java.util.ArrayList;

/**
 * An adapter for the ListView in FlightProfileActivity.
 */
public class FlightsAdapter extends ArrayAdapter<Flight> {

  /**
   * Instantiates a new Flights adapter.
   *
   * @param context the context
   * @param flights an ArrayList of Flights being displayed in the ListView
   */
  public FlightsAdapter(Context context, ArrayList<Flight> flights) {
    super(context, 0, flights);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Get the data item for this position
    Flight flight = getItem(position);
    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.flights_list_item,
              parent, false);
    }
    // Lookup view for data population
    final TextView originField = (TextView) convertView.findViewById(R.id.originField);
    final TextView destinationField = (TextView) convertView.findViewById(R.id.destinationField);
    final TextView secondLineField = (TextView) convertView.findViewById(R.id.secondLineField);
    final TextView numberField = (TextView) convertView.findViewById(R.id.number);

    // Get the information from the Flight object
    String origin = flight.getOrigin();
    String destination = flight.getDestination();
    String airline = flight.getAirline();
    String flightNum = flight.getFlightNum();
    Integer numSeatsAvailable = flight.getNumSeat() - flight.getNumPassenger();
    String numSeatsAvailableString = numSeatsAvailable.toString();
    String secondLine = airline + ", " + flightNum + ", " + numSeatsAvailableString + " "
            + R.string.seats_available;

    // Populate the data into the template view using the data object
    originField.setText(origin);
    destinationField.setText(destination);
    secondLineField.setText(secondLine);
    numberField.setText(String.valueOf(position + 1));

    // Return the completed view to render on screen
    return convertView;
  }
}
