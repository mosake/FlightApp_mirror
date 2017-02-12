package cs.b07.flightmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import flightinfo.Flight;
import managers.FileManager;

import java.util.Date;

public class FlightProfileActivity extends AppCompatActivity {

  private Flight flight;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_flight_profile);

    // catch the passed intent
    Intent intent = getIntent();

    // Uses key "flightKey" to get Flight object.
    flight = (Flight) intent.getSerializableExtra("flightKey");
    int permission = (int) intent.getSerializableExtra("permissionKey");

    // if it is a Client who initiated this activity
    if (permission == 0) {
      // make the edit button invisible
      Button editButton = (Button) findViewById(R.id.Edit_Button);
      editButton.setVisibility(View.INVISIBLE);
    }

    // sets all fields to the info of the logged in client
    updateFields(flight);
  }

  /**
   * Sets text for all views in this activity with information of flight.
   *
   * @param flight the flight being displayed on the screen
   */
  private void updateFields(Flight flight) {
    // get flight number
    String flightNumber = flight.getFlightNum();
    setTitle(flightNumber);

    // gets all String representations from flight
    final Date depTime = flight.getDepartureTime();
    final Date arrTime = flight.getArrivalTime();
    final String dep = FileManager.convertDateToStringOnlyTime(depTime);
    final String arr = FileManager.convertDateToStringOnlyTime(arrTime);
    final String airline = flight.getAirline();
    final String origin = flight.getOrigin();
    final String destination = flight.getDestination();
    final Double cost = flight.getCost();
    final Integer numSeat = flight.getNumSeat();
    final Integer numPassenger = flight.getNumPassenger();
    final Integer availableSeat = numSeat - numPassenger;

    // gets all fields in this activity
    final TextView airLineField = (TextView) findViewById(R.id.AirLine_TextView);
    final TextView priceField = (TextView) findViewById(R.id.Price_TextView);
    final TextView seatField = (TextView) findViewById(R.id.Seat_TextView);
    final TextView depTimeField = (TextView) findViewById(R.id.Dep_TextClock);
    final TextView arrTimeField = (TextView) findViewById(R.id.Arr_TextClock);
    final TextView originField = (TextView) findViewById(R.id.Or_TextView);
    final TextView destinationField = (TextView) findViewById(R.id.De_TextView);

    // updates all fields with updated data
    airLineField.setText(airline);
    priceField.setText("$" + cost.toString());
    seatField.setText("Available Seats: " + availableSeat.toString());
    depTimeField.setText(dep);
    arrTimeField.setText(arr);
    originField.setText(origin);
    destinationField.setText(destination);
  }

  /**
   * Launches EditFlightInfoActivity.
   *
   * @param view the view
   */
  public void editFlightInfoActivity(View view) {
    // specifies the next Activity to move to
    Intent intent = new Intent(this, EditFlightInfoActivity.class);
    intent.putExtra("flightKey", flight);
    // starts EditNameActivity, and wait for a result of whether data has been changed
    startActivityForResult(intent, 0);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    // if the data has been changed
    if (resultCode == RESULT_OK) {
      // get the changed Client object from the Intent
      flight = (Flight) data.getSerializableExtra("flightKey");
      // update all fields on the screen
      updateFields(flight);
    }
  }

}
