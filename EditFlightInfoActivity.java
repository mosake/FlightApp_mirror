package cs.b07.flightmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import flightinfo.Flight;
import managers.FileManager;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class EditFlightInfoActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_flight_info);
    setTitle(R.string.edit_flight_information);

    // get the Intent that launched me
    Intent intent = getIntent();

    // get the Flight
    final Flight flight = (Flight) intent.getSerializableExtra("flightKey");

    final Date depTime = flight.getDepartureTime();
    final Date arrTime = flight.getArrivalTime();
    final String dep = FileManager.convertDateToStringWithTime(depTime);
    final String arr = FileManager.convertDateToStringWithTime(arrTime);

    final String flightNum = flight.getFlightNum();
    final String airline = flight.getAirline();
    final String origin = flight.getOrigin();
    final String destination = flight.getDestination();
    final String cost = flight.getCost().toString();

    final String numSeat = flight.getNumSeat().toString();
    final String numPassenger = flight.getNumPassenger().toString();

    final TextView flightNumField = (TextView) findViewById(R.id.FlightNum_EditText);
    final TextView airLineField = (TextView) findViewById(R.id.Airline_EditText);
    final TextView priceField = (TextView) findViewById(R.id.Price_EditText);
    final TextView originField = (TextView) findViewById(R.id.Origin_EditText);
    final TextView desField = (TextView) findViewById(R.id.Destination_EditText);
    final TextView depField = (TextView) findViewById(R.id.Dep_EditText);
    final TextView arrField = (TextView) findViewById(R.id.Arr_EditText);
    final TextView seatField = (TextView) findViewById(R.id.Seat_EditText);
    final TextView passengerField = (TextView) findViewById(R.id.Passenger_EditText);

    flightNumField.setText(flightNum);
    airLineField.setText(airline);
    priceField.setText(cost);
    originField.setText(origin);
    desField.setText(destination);
    depField.setText(dep);
    arrField.setText(arr);
    seatField.setText(numSeat);
    passengerField.setText(numPassenger);

    // get the button
    Button btnShow = (Button) findViewById(R.id.button);
    // add a on click listener on this button
    btnShow.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        String newFlightNum = flightNumField.getText().toString();
        String newAir = airLineField.getText().toString();
        String newPrice = priceField.getText().toString();
        String newOrigin = originField.getText().toString();
        String newDes = desField.getText().toString();
        String newDep = depField.getText().toString();
        String newArr = arrField.getText().toString();
        String newSeat = seatField.getText().toString();
        String newPassenger = passengerField.getText().toString();

        // update info
        flight.setFlightNum(newFlightNum);
        flight.setAirline(newAir);
        flight.setCost(Double.valueOf(newPrice));
        flight.setOrigin(newOrigin);
        flight.setDestination(newDes);
        flight.setDepTime(managers.FileManager.convertStringToDate(newDep));
        flight.setArrTime(managers.FileManager.convertStringToDate(newArr));
        flight.setNumSeat(Integer.valueOf(newSeat));
        flight.setNumPassenger(Integer.valueOf(newPassenger));

        for (Flight f : managers.FlightManager.getFlightsList()) {
          if (flight.getFlightNum().equals(f.getFlightNum())) {
            System.out.println(flight.getFlightNum());
            System.out.println(f.getFlightNum());
            // create a dialog box showing a duplicate flight
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Flight Information already exists");
            builder.setMessage(R.string.disappear);
            builder.setCancelable(false);

            final AlertDialog closeDialog = builder.create();

            closeDialog.show();

            // close the dialog box after 3 seconds
            final Timer timer2 = new Timer();
            timer2.schedule(new TimerTask() {
              public void run() {
                closeDialog.dismiss();
                timer2.cancel(); //this will cancel the timer of the system
              }
            }, 3000); // the timer will count 3 seconds

          } else {
            Intent output = new Intent();
            output.putExtra("flightKey", flight);
            setResult(RESULT_OK, output);


            // create a dialog box showing a successful update
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle(R.string.recorded);
            builder.setMessage(R.string.disappear);
            builder.setCancelable(false);

            final AlertDialog closeDialog = builder.create();

            closeDialog.show();

            // close the dialog box after 3 seconds
            final Timer timer2 = new Timer();
            timer2.schedule(new TimerTask() {
              public void run() {
                closeDialog.dismiss();
                finish();
                timer2.cancel(); //this will cancel the timer of the system
              }
            }, 3000); // the timer will count 3 seconds

          }

        }

      }

    });

  }
}
