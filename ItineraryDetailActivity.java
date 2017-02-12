package cs.b07.flightmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import flightinfo.Flight;
import flightinfo.Itinerary;
import users.Client;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An activity for displaying the detailed information of an itinerary.
 */
public class ItineraryDetailActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_itinerary_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // get the Intent that launched me
    Intent intentPrevious = getIntent();

    // get the data that was passed to me in the Intent
    final Client client = (Client) intentPrevious.getSerializableExtra("clientKey");
    final Itinerary itinerary = (Itinerary) intentPrevious.getSerializableExtra("itineraryKey");
    Integer status = (Integer) intentPrevious.getSerializableExtra("status");

    // get all Flights stored in this Itinerary object
    ArrayList<Flight> flights = itinerary.getListOfFlights();

    // get the floating action button object for booking this itinerary
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    // if this activity was not started by ItinerarySearchResultActivity
    if (status == null) {
      // make this button invisible
      fab.setVisibility(View.INVISIBLE);
    }

    // set the action when clicking on the floating action button
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ItineraryDetailActivity.this);

        // set the title and message of the dialogue box
        builder.setTitle(R.string.confirm_lower_case);
        builder.setMessage(R.string.would_you_like_to_book);

        // if the user clicked on yes
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

          public void onClick(DialogInterface dialog, int which) {
            // add this itinerary to the user's booking list
            client.addBookedItn(itinerary);
            // close the dialog box
            dialog.dismiss();
            // create a dialog box showing a successful update
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
                    view.getContext());

            // set the title and message of the dialog
            builder.setTitle(R.string.booked);
            builder.setMessage(R.string.disappear);
            builder.setCancelable(false);

            // create the dialog box instance for displaying a successful booking
            final android.app.AlertDialog closeDialog = builder.create();
            closeDialog.show();

            // close the dialog box after 3 seconds
            final Timer timer2 = new Timer();
            timer2.schedule(new TimerTask() {
              public void run() {
                // close the dialog box
                closeDialog.dismiss();
                // create an Intent for going back to the WelcomeActivity after successful booking
                Intent back = new Intent(ItineraryDetailActivity.this, WelcomeActivity.class);
                back.putExtra("client", client);
                back.putExtra("status", 0);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(back);
                timer2.cancel(); // this will cancel the timer of the system
              }
            }, 3000); // the timer will count 3 seconds
          }


        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

          @Override
          public void onClick(DialogInterface dialog, int which) {
            // Do nothing
            dialog.dismiss();
          }
        });

        // make the confirm dialog box appear
        AlertDialog alert = builder.create();
        alert.show();
      }
    });

    // create the adapter to convert the array to views
    FlightsAdapter adapter = new FlightsAdapter(this, flights);

    // attach the adapter to a ListView
    final ListView listView = (ListView) findViewById(R.id.flightsList);
    listView.setAdapter(adapter);

    // set the list items to be clickable
    listView.setClickable(true);
    // set a onclick listener to each item in the list
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        // get the Flight object that is being clicked in the ListView
        Object object = listView.getItemAtPosition(position);
        Flight flight = (Flight) object;
        // specifies the next Activity to move to: FlightProfileActivity
        Intent intent = new Intent(ItineraryDetailActivity.this, FlightProfileActivity.class);
        // passes the data to FlightProfileActivity
        intent.putExtra("flightKey", flight);
        intent.putExtra("permissionKey", 0);
        // starts FlightProfileActivity
        startActivity(intent);
      }
    });
  }

}
