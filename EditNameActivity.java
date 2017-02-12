package cs.b07.flightmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import information.PersonalInformation;
import users.Client;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The activity for a user to edit name and e-mail information.
 */
public class EditNameActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_name);
    setTitle(R.string.edit_name);

    // get the Intent that launched me
    Intent intent = getIntent();

    // get the Client that was put into the Intent with key clientKey
    final Client client = (Client) intent.getSerializableExtra("clientKey");
    // get PersonalInformation object stored in this Client
    final PersonalInformation info = client.getPersonalInfo();

    // get the name stored in that PersonalInformation object
    String firstName = info.getFirstName();
    String lastName = info.getLastName();
    String email = info.getEmail();

    // find the TextView object for TextView with id display_id
    final EditText firstNameField = (EditText) findViewById(R.id.editFirstName);
    final EditText lastNameField = (EditText) findViewById(R.id.editLastName);
    final EditText emailField = (EditText) findViewById(R.id.editEmail);

    // specify text to be displayed in the TextView
    firstNameField.setText(firstName);
    lastNameField.setText(lastName);
    emailField.setText(email);

    // get the button which creates a dialog box
    Button btnShow = (Button) findViewById(R.id.confirmButton);
    // add a on click listener on this button
    btnShow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // get the new name entered in the EditText
        String newFirstName = firstNameField.getText().toString();
        String newLastName = lastNameField.getText().toString();
        String newEmail = emailField.getText().toString();

        // update info to the Client
        info.setFirstName(newFirstName);
        info.setLastName(newLastName);
        info.setEmail(newEmail);
        client.setPersonalInfo(info);

        // create an Intent to bring the updated Client back to previous activity
        Intent output = new Intent();
        output.putExtra("clientKey", client);
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
    });
  }

}
