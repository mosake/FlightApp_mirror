package cs.b07.flightmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import information.BillingInformation;
import information.PersonalInformation;
import managers.FileManager;
import managers.UserManager;
import users.Client;
import users.User;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterStep2Activity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register_step2);
    setTitle(R.string.register);
  }

  /**
   * Checks whether the input is valid, if so, register this user, and go back to LoginActivity.
   *
   * @param view the view
   */
  public void nextPage(View view) {
    // get all the input
    EditText lastName = (EditText) findViewById(R.id.last);
    EditText firstName = (EditText) findViewById(R.id.first);
    EditText emailField = (EditText) findViewById(R.id.email);
    EditText add = (EditText) findViewById(R.id.address);
    EditText cardNum = (EditText) findViewById(R.id.card);
    EditText date = (EditText) findViewById(R.id.date);

    // convert them to string
    String last = lastName.getText().toString();
    String first = firstName.getText().toString();
    String email = emailField.getText().toString();
    String address = add.getText().toString();
    String card = cardNum.getText().toString();
    String expiryDate = date.getText().toString();
    Date expiry = managers.FileManager.convertStringToDate(expiryDate);

    // make sure there is no empty field
    if (!(last.isEmpty() || first.isEmpty() || email.isEmpty() || address.isEmpty()
            || card.isEmpty() || expiryDate.isEmpty())) {
      try {
        expiry = managers.FileManager.convertStringToDate(expiryDate);
        // catch the passed intent
        Intent intent = getIntent();
        // Uses key "userKey" to get User object.
        User user = (User) intent.getSerializableExtra("userKey");
        // get the elements in the list
        String username = user.getUser();
        String pass = user.getPass();

        // create new personal and billing information objects
        PersonalInformation newPersonal = new PersonalInformation(last, first, email, address);
        BillingInformation newBilling = new BillingInformation(card, expiry);

        // create new client object
        Client newClient = new Client(username, pass, newBilling, newPersonal);
        // add the new client object to the list of clients
        UserManager.getList().add(newClient);

        // pass the client object to next activity
        Intent next = new Intent(this, LoginActivity.class);
        next.putExtra("clientKey", newClient);

        // add new client to file and write to "clients.txt" + "passwords.txt"
        managers.UserManager.addUser(newClient);

        // -- file info --
        File filepathText = this.getApplicationContext().getDir(Constants.FILE_PATH_TEXT,
                MODE_PRIVATE);
        File userfile = new File(filepathText, Constants.FILE_NAME_USERS);
        File loginfile = new File(filepathText, Constants.FILE_NAME_LOGIN);

        File filepathSer = this.getApplicationContext().getDir(
                Constants.FILE_PATH_SERIALIZED, MODE_PRIVATE);
        final File serfile = new File(filepathSer, Constants.FILE_NAME_SERIALIZED_CLIENTS);

        FileManager.writeFile(userfile.getPath(), newClient.toString());
        FileManager.writeFile(loginfile.getPath(), newClient.getUser() + "," + newClient.getPass());
        Map<String, Client> file = FileManager.getClients();
        file.putAll(UserManager.getClientMap());
        FileManager.setClients(file);
        FileManager.saveToFile(serfile.getPath(), newClient);
        FileManager.writeFile(userfile.getPath(), newClient.toString());
        FileManager.writeFile(loginfile.getPath(), newClient.getUser() + "," + newClient.getPass());
        FileManager.clients.put(newClient.getUser(), newClient);
        FileManager.saveToFile(serfile.getPath(), newClient);

        // create a dialog box showing a successful update
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(R.string.registration_successful);
        builder.setMessage(R.string.disappear);
        builder.setCancelable(false);

        final AlertDialog closeDialog = builder.create();

        closeDialog.show();

        // close the dialog box after 3 seconds
        final Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
          public void run() {
            closeDialog.dismiss();
            Intent back = new Intent(RegisterStep2Activity.this, LoginActivity.class);
            back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(back);
            timer2.cancel(); //this will cancel the timer of the system
          }
        }, 3000); // the timer will count 3 seconds
      } catch (Exception e) {
        // create a dialog box showing the expiry date is invalid
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(R.string.invalid_expiry_date);
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

    }
  }
}

