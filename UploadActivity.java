package cs.b07.flightmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import flightinfo.Flight;
import managers.FileManager;
import managers.FlightManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UploadActivity extends AppCompatActivity {

  public static final String FLIGHT_DATA_DIR = "Flights_data";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_upload);
    setTitle("Upload Flight Information");
  }

  /**
   * Uploads the specified csv file to the system.
   *
   * @param view the view
   */
  public void onClick(View view) {
    EditText fileName = (EditText) findViewById(R.id.file_name);
    String fileNameText = fileName.getText().toString();

    File flightsData = this.getApplicationContext()
            .getDir(FLIGHT_DATA_DIR, MODE_PRIVATE);
    File flightsFile = new File(flightsData, fileNameText);
    try {
      FileManager.readCsv(flightsFile.getPath());
    } catch (FileNotFoundException e) {
      e.printStackTrace();

      AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
      builder.setTitle("File name does not exist");
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
    }

    try {
      ArrayList<String[]> textList = FileManager.readCsv(flightsFile.getPath());
      ArrayList<Flight> flightsList = FileManager.createFlightList(textList);

      FlightManager.setFlightsList(flightsList);
    } catch (FileNotFoundException e) {
      e.printStackTrace();

      AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
      builder.setTitle("File name does not exist");
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
    }

    Intent intent = new Intent(this, AdminWelcomeActivity.class);
    startActivity(intent);
  }

  /**
   * Adds a flight into the system.
   *
   * @param view the view
   */
  public void createFlight(View view) {
    System.out.println(0);
    final TextView flightNumField = (TextView) findViewById(R.id.flightnum);
    final TextView airLineField = (TextView) findViewById(R.id.airline);
    final TextView priceField = (TextView) findViewById(R.id.cost);
    final TextView originField = (TextView) findViewById(R.id.origin_field);
    final TextView desField = (TextView) findViewById(R.id.destination_field);
    final TextView depField = (TextView) findViewById(R.id.departrue_field);
    final TextView arrField = (TextView) findViewById(R.id.arrival_field);
    final TextView seatField = (TextView) findViewById(R.id.seats);

    String newFlightNum = flightNumField.getText().toString();
    String newAir = airLineField.getText().toString();
    String newPrice = priceField.getText().toString();
    String newOrigin = originField.getText().toString();
    String newDes = desField.getText().toString();
    String newDep = depField.getText().toString();
    String newArr = arrField.getText().toString();
    String newSeat = seatField.getText().toString();

    Flight flight = new Flight(newFlightNum, managers.FileManager.convertStringToDate(newDep),
            managers.FileManager.convertStringToDate(newArr), newAir, newOrigin, newDes,
            Double.valueOf(newPrice), Integer.valueOf(newSeat));

    boolean flightExist = false;
    for (Flight f : managers.FlightManager.getFlightsList()) {
      if (flight.getFlightNum().equals(f.getFlightNum())) {
        flightExist = true;
      }
    }
    if (flightExist) {
      System.out.println(flight.getFlightNum());
      // create a dialog box showing a duplicate flight
      AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
      builder.setTitle(R.string.flight_info_exists);
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
      }, 3000); // the timer will count 3 seconds//
    }
    if (!flightExist) {
      System.out.println(1);
      managers.FlightManager.getFlightsList().add(flight);
      Intent intent = new Intent(this, FlightProfileActivity.class);
      intent.putExtra("flightKey", flight);
      intent.putExtra("permissionKey", 1);
      startActivity(intent);
    }
  }
}

