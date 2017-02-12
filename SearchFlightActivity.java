package cs.b07.flightmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import flightinfo.Flight;
import managers.FlightManager;
import users.Client;

import java.util.ArrayList;

/**
 * The activity that searches flights by origin, destination, and departure date.
 */
public class SearchFlightActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_itinerary);
    setTitle(R.string.search_flight);
  }

  /**
   * Launches FlightSearchResultActivity.
   *
   * @param view the View that is being clicked
   */
  public void onClick(View view) {
    // gets all fields in this activity
    EditText originField = (EditText) findViewById(R.id.originEdit);
    EditText destinationField = (EditText) findViewById(R.id.destinationEdit);
    DatePicker departureDatePicker = (DatePicker) findViewById(R.id.departureDatePicker);
    RadioButton totalTravelTimeOption = (RadioButton) findViewById(R.id.totalTravelTimeOption);
    RadioButton totalPriceOption = (RadioButton) findViewById(R.id.totalPriceOption);

    // gets all String entered by the user
    String origin = originField.getText().toString();
    String destination = destinationField.getText().toString();
    String departureDate = departureDatePicker.getYear() + "-"
            + (departureDatePicker.getMonth() + 1) + "-" + departureDatePicker.getDayOfMonth();
    String[] search = {origin, destination, departureDate};

    // gets a list of Flights that matches the searching criteria
    ArrayList<Flight> result = FlightManager.getFlights(departureDate, origin, destination);

    // get the Intent that launched me
    Intent intentPrevious = getIntent();

    // uses key "client" to get Client object
    Client client = (Client) intentPrevious.getSerializableExtra("client");

    // specifies the next Activity to move to: ItinerarySearchResultActivity
    Intent intent = new Intent(this, FlightSearchResultActivity.class);

    // if the user choose to sort by total travel time
    if (totalTravelTimeOption.isChecked()) {
      // call the backend to sort the list of Itineraries by total travel time
      managers.FlightManager.sortFlightByTime(result);
      // if the user choose to sort by total price
    } else if (totalPriceOption.isChecked()) {
      // call the backend to sort the list of Itineraries by total price
      managers.FlightManager.sortFlightByCost(result);
    }

    // get the permissionKey which determines user type from the previous activity
    int permissionKey = (int) intentPrevious.getSerializableExtra("permissionKey");

    // passes the data to ItinerarySearchResultActivity
    intent.putExtra("searchKey", search);
    intent.putExtra("clientKey", client);
    intent.putExtra("flightKey", result);
    intent.putExtra("permissionKey", permissionKey);

    // starts ItinerarySearchResultActivity
    startActivity(intent);
  }

  /**
   * Switches the origin and destination that the user typed in originEdit and destinationEdit.
   *
   * @param view the View that is being clicked
   */
  public void switchOriginAndDestination(View view) {
    // gets all fields in this activity
    EditText originField = (EditText) findViewById(R.id.originEdit);
    EditText destinationField = (EditText) findViewById(R.id.destinationEdit);

    // gets all String entered by the user
    String origin = originField.getText().toString();
    String destination = destinationField.getText().toString();

    // set the input back to those two fields
    originField.setText(destination);
    destinationField.setText(origin);
  }
}
