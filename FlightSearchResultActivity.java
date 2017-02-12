package cs.b07.flightmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import flightinfo.Flight;

import java.util.ArrayList;

/**
 * The activity showing search result of flights.
 */
public class FlightSearchResultActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_itinerary_search_result);
    setTitle(R.string.search_result);

    // get the Intent that launched me
    Intent intentPrevious = getIntent();

    // get the data that was passed to me in the Intent
    String[] search = (String[]) intentPrevious.getSerializableExtra("searchKey");
    final int permissionKey = (int) intentPrevious.getSerializableExtra("permissionKey");

    // gets all fields in this activity
    TextView originField = (TextView) findViewById(R.id.originField);
    TextView destinationField = (TextView) findViewById(R.id.destinationField);
    TextView departureDateField = (TextView) findViewById(R.id.departureDateField);

    // put the searching criteria to the fields
    originField.setText(search[0]);
    destinationField.setText(search[1]);
    departureDateField.setText(search[2]);

    // noinspection unchecked
    ArrayList<Flight> flights = (ArrayList<Flight>)
            intentPrevious.getSerializableExtra("flightKey");
    // create the adapter to convert the array to views
    FlightsSearchAdapter adapter = new FlightsSearchAdapter(this, flights);
    // attach the adapter to a ListView
    final ListView listView = (ListView) findViewById(R.id.itinerariesList);
    listView.setAdapter(adapter);

    // set the list items to be clickable
    listView.setClickable(true);
    // set a onclick listener to each item in the list
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        // get the Itinerary object that is being clicked in the ListView
        Object object = listView.getItemAtPosition(position);
        Flight flight = (Flight) object;
        // specifies the next Activity to move to: ItineraryDetailActivity
        Intent intent = new Intent(FlightSearchResultActivity.this, FlightProfileActivity.class);
        // passes the data to ItineraryDetailActivity
        intent.putExtra("flightKey", flight);
        intent.putExtra("permissionKey", permissionKey);
        // starts ItineraryDetailActivity
        startActivity(intent);
      }
    });
  }
}
