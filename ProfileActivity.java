package cs.b07.flightmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import information.BillingInformation;
import information.PersonalInformation;
import managers.FileManager;
import managers.UserManager;
import users.Client;

import java.io.File;
import java.util.Map;

/**
 * The activity showing a client's profile.
 */
public class ProfileActivity extends AppCompatActivity {

  /**
   * The number of digits displayed on the profile screen.
   */
  public static final int NUM_DIGITS_OF_CARD_NUMBER_DISPLAYED = 4;

  private Client client;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    setTitle(R.string.my_profile);

    // catch the passed intent
    Intent intent = getIntent();

    // Uses key "client" to get Client object.
    client = (Client) intent.getSerializableExtra("client");

    // sets all fields to the info of the logged in client
    updateFields(client);
  }

  /**
   * Launches EditNameActivity.
   *
   * @param view the View that is being clicked
   */
  public void editName(View view) {
    // specifies the next Activity to move to: DisplayActivity
    Intent intent = new Intent(this, EditNameActivity.class);
    // passes the String data to DisplayActivity
    intent.putExtra("clientKey", client);
    // starts EditNameActivity, and wait for a result of whether data has been changed
    startActivityForResult(intent, 0);
  }

  /**
   * Launches EditAddressActivity.
   *
   * @param view the View that is being clicked
   */
  public void editAddress(View view) {
    // specifies the next Activity to move to: DisplayActivity
    Intent intent = new Intent(this, EditAddressActivity.class);
    // passes the data to DisplayActivity
    intent.putExtra("clientKey", client);
    // starts EditAddressActivity, and wait for a result of whether data has been changed
    startActivityForResult(intent, 0);
  }

  /**
   * Launches EditBillingActivity.
   *
   * @param view the View that is being clicked
   */
  public void editBilling(View view) {
    // specifies the next Activity to move to: DisplayActivity
    Intent intent = new Intent(this, EditBillingActivity.class);
    // passes the data to DisplayActivity
    intent.putExtra("clientKey", client);
    // starts EditBillingActivity, and wait for a result of whether data has been changed
    startActivityForResult(intent, 0);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    // if the data has been changed
    if (resultCode == RESULT_OK) {
      // get the changed Client object from the Intent
      client = (Client) data.getSerializableExtra("clientKey");
      // update all fields on the screen
      updateFields(client);

      // update client in client array
      UserManager.updateClient(client);
      Map<String, Client> mapped =  FileManager.getClients();
      mapped.put(client.getUser(), client);
      FileManager.setClients(mapped);
      // save file
      File filepathText = this.getApplicationContext().getDir(Constants.FILE_PATH_TEXT,
          MODE_PRIVATE);
      File userfile = new File(filepathText, Constants.FILE_NAME_USERS);

      File filepathSer = this.getApplicationContext().getDir(
          Constants.FILE_PATH_SERIALIZED, MODE_PRIVATE);
      File serfile = new File(filepathSer, Constants.FILE_NAME_SERIALIZED_CLIENTS);

      // create an Intent to bring the updated Client back to previous activity
      Intent output = new Intent();
      // pass the client data to the previous activity
      output.putExtra("clientKey", client);
      setResult(RESULT_OK, output);
    }
  }

  /**
   * Sets text for all views in this activity with information of client.
   *
   * @param client the Client being displayed on the screen
   */
  private void updateFields(Client client) {
    // gets all String representations from client
    final PersonalInformation personalInfo = client.getPersonalInfo();
    final BillingInformation billingInfo = client.getBillingInfo();
    final String firstName = personalInfo.getFirstName();
    final String lastName = personalInfo.getLastName();
    final String fullName = firstName + " " + lastName;
    final String email = personalInfo.getEmail();
    final String address = personalInfo.getAddress();
    final String cardNum = billingInfo.getCreditCardNo();
    String cardDisplay;
    if (cardNum.length() > 4) {
      cardDisplay = getResources().getString(R.string.ends_in) + " "
              + cardNum.substring(cardNum.length() - NUM_DIGITS_OF_CARD_NUMBER_DISPLAYED);
    } else {
      cardDisplay = getResources().getString(R.string.ends_in) + " " + cardNum;
    }
    // gets all fields in this activity
    final TextView fullNameField = (TextView) findViewById(R.id.fullName);
    final TextView emailField = (TextView) findViewById(R.id.email);
    final TextView addressField = (TextView) findViewById(R.id.homeAddress);
    final TextView paymentField = (TextView) findViewById(R.id.paymentMethod);

    // updates all fields with updated data
    fullNameField.setText(fullName);
    emailField.setText(email);
    addressField.setText(address);
    paymentField.setText(cardDisplay);
  }
}
