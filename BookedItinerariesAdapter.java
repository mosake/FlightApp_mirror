package cs.b07.flightmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import flightinfo.Flight;
import flightinfo.Itinerary;

import java.util.ArrayList;

/**
 * An adapter for the ListView in ViewBookedItineraryActivity.
 */
public class BookedItinerariesAdapter extends ArrayAdapter<Itinerary> {
  /**
   * Instantiates a new Booked itineraries adapter.
   *
   * @param context     the context
   * @param itineraries an ArrayList of Itineraries being displayed in the ListView
   */
  public BookedItinerariesAdapter(Context context, ArrayList<Itinerary> itineraries) {
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

    // get the list of flights from the itinerary
    ArrayList<Flight> flights = itinerary.getListOfFlights();
    // get the origin and destination of this itinerary,
    // i.e. the origin and destination of the first flight in this itinerary
    String origin = flights.get(0).getOrigin();
    String destination = flights.get(flights.size() - 1).getDestination();

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

    // get a String representation of the departure time of this itinerary
    String secondLine = R.string.departs + " "
            + managers.FileManager.convertDateToStringWithTime(flights.get(0).getDepartureTime());

    // Populate the data into the template view using the data object
    departureField.setText(origin);
    arrivalField.setText(destination);
    secondLineField.setText(secondLine);
    connectingFlightField.setText(numFlightsString);
    // Return the completed view to render on screen
    return convertView;
  }
}
