package cs.b07.flightmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import users.Client;

/**
 * The type Welcome activity.
 */
public class WelcomeActivity extends AppCompatActivity {
  private Client client;
  private Intent intent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome_activity);

    Intent intent = getIntent();
    client = (Client) intent.getSerializableExtra("client");
    Integer status = (Integer) intent.getSerializableExtra("status");
    String username = client.getPersonalInfo().getLastName()
            + "," + client.getPersonalInfo().getFirstName();

    if (status != null) {
      Intent intentProfile = new Intent(this, ViewBookedItineraryActivity.class);
      intentProfile.putExtra("client", client);
      startActivity(intentProfile);
    }

    Button profileButton = (Button) findViewById(R.id.Profile_button);
    profileButton.setText(username);
  }

  /**
   * Launches ProfileActivity.
   *
   * @param view the view
   */
  public void nextProfilePage(View view) {
    final Intent ProfilePage = new Intent(this, ProfileActivity.class);
    ProfilePage.putExtra("client", client);
    // starts ProfilePage, and wait for a result of whether data has been changed
    startActivityForResult(ProfilePage, 0);
  }

  /**
   * Launches SearchFlightActivity.
   *
   * @param view the view
   */
  public void nextSearchFlightPage(View view) {
    final Intent searchItineraryPage = new Intent(this, SearchFlightActivity.class);
    searchItineraryPage.putExtra("client", client);
    searchItineraryPage.putExtra("permissionKey", 0);
    startActivity(searchItineraryPage);
  }

  /**
   * Launches SearchItineraryActivity.
   *
   * @param view the view
   */
  public void nextSearchItineraryPage(View view) {
    final Intent searchItineraryPage = new Intent(this, SearchItineraryActivity.class);
    searchItineraryPage.putExtra("client", client);
    startActivity(searchItineraryPage);
  }

  /**
   * Launches ViewBookedItineraryActivity.
   *
   * @param view the view
   */
  public void nextBookedItineraryPage(View view) {
    final Intent bookedItineraryPage = new Intent(this, ViewBookedItineraryActivity.class);
    bookedItineraryPage.putExtra("client", client);
    startActivity(bookedItineraryPage);
  }

  /**
   * Logs the user out, and go back to LoginActivity.
   *
   * @param view the view
   */
  public void logOut(View view) {
    final Intent loginPage = new Intent(WelcomeActivity.this, LoginActivity.class);
    loginPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(loginPage);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    // if the data has been changed
    if (resultCode == RESULT_OK) {
      // get the changed Client object from the Intent
      client = (Client) data.getSerializableExtra("clientKey");

      // update all display fields
      Button profileButton = (Button) findViewById(R.id.Profile_button);
      String username = client.getPersonalInfo().getLastName() + ","
              + client.getPersonalInfo().getFirstName();
      profileButton.setText(username);
    }
  }
}
