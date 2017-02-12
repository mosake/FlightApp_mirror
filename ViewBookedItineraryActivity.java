package cs.b07.flightmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import flightinfo.Itinerary;
import information.PersonalInformation;
import users.Client;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The activity displaying all booked itinerary of a Client.
 */
public class ViewBookedItineraryActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_booked_itinerary);
    setTitle(R.string.view_booked_itineraries);

    // catch the passed intent
    Intent intent = getIntent();

    // Uses key "client" to get Client object.
    Client client = (Client) intent.getSerializableExtra("client");

    // gets all String representations from client
    PersonalInformation personalInfo = client.getPersonalInfo();
    String firstName = personalInfo.getFirstName();
    String lastName = personalInfo.getLastName();
    String fullName = firstName + " " + lastName;
    String email = personalInfo.getEmail();

    // gets all fields in this activity
    TextView fullNameField = (TextView) findViewById(R.id.fullName);
    TextView emailField = (TextView) findViewById(R.id.email);

    // updates all fields with updated data
    fullNameField.setText(fullName);
    emailField.setText(email);

    // get a list of booked itineraries of this client
    ArrayList<Itinerary> itineraries = client.getBookedItn();

    // make latest booked itinerary stay on top
    Collections.reverse(itineraries);

    // Create the adapter to convert the array to views
    BookedItinerariesAdapter adapter = new BookedItinerariesAdapter(this, itineraries);
    // Attach the adapter to a ListView
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
        Itinerary itinerary = (Itinerary) object;
        // specifies the next Activity to move to: ItineraryDetailActivity
        Intent intent = new Intent(ViewBookedItineraryActivity.this, ItineraryDetailActivity.class);
        // passes the data to ItineraryDetailActivity
        intent.putExtra("itineraryKey", itinerary);
        // starts ItineraryDetailActivity
        startActivity(intent);
      }
    });
  }
}
