package cs.b07.flightmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import flightinfo.Flight;
import managers.FileManager;
import managers.FlightManager;
import managers.UserManager;
import users.Administrator;
import users.Client;
import users.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A login screen that offers login via email/password.
 * Main activity
 */
public class LoginActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    // Set up the login form.
    File filepathText = this.getApplicationContext().getDir(Constants.FILE_PATH_TEXT,
        MODE_PRIVATE);
    File userfile = new File(filepathText, Constants.FILE_NAME_USERS);
    File loginfile = new File(filepathText, Constants.FILE_NAME_LOGIN);
    File flightsfile = new File(filepathText, Constants.FILE_PATH_FLIGHTS);

    File filepathSer = this.getApplicationContext().getDir(
        Constants.FILE_PATH_SERIALIZED, MODE_PRIVATE);
    File clientserfile = new File(filepathSer, Constants.FILE_NAME_SERIALIZED_CLIENTS);
    File adminserfile = new File(filepathSer, Constants.FILE_NAME_SERIALIZED_ADMINS);
    File flightserfile = new File(filepathSer, Constants.FILE_NAME_SERIALIZED_FLIGHTS);

    ArrayList<String[]> userListStr;
    ArrayList<User> userList;

    // the login file consists of: username,password
    // which correspond to what line/ array position the client is in
    ArrayList<String[]> loginlist = null;

    try {
      loginlist = FileManager.readCsv(loginfile.getPath());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    try {
      userListStr = FileManager.readCsv(userfile.getPath());
      userList = UserManager.createUsersList(userListStr, loginlist);

      // set the userList for our current device
      UserManager.userList = userList;
      // update serialized files
      Map<String, Client> file = FileManager.getClients();
      if (file == null) {
        file = UserManager.getClientMap();
      } else {
        file.putAll(UserManager.getClientMap());
      }
      FileManager.setClients(file);
      Map<String, Administrator> file2 = FileManager.getAdmins();
      if (file2 == null) {
        file2 = UserManager.getAdminMap();
      } else {
        file2.putAll(UserManager.getAdminMap());
      }
      FileManager.setAdmins(file2);
      FileManager.saveToFile(clientserfile.getPath(), Client.class);
      FileManager.saveToFile(adminserfile.getPath(), Administrator.class);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    try {
      FileManager.getObj(clientserfile.getPath(), Client.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // initialize flight list in FlightManager and map in FileManager
    try {
      // get serialized file
      FileManager.getObj(flightserfile.getPath(), Flight.class);
      // check for flight.txt
      ArrayList<String[]> flightStr = FileManager.flightFolder(flightsfile, null);
      FlightManager.setFlightsList(FileManager.createFlightList(flightStr));
      //update serialized file
      Map<String, Flight> file3 = FileManager.getFlight();
      if (file3 == null) {
        file3 = FlightManager.getFlightMap();
      } else {
        file3.putAll(FlightManager.getFlightMap());
      }
      FileManager.setFlight(file3);
      FileManager.saveToFile(clientserfile.getPath(), Flight.class);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks whether the user is registered and whether the password is correct,
   * if so, launches WelcomeActivity if user is a Client, or launches AdminWelcomeActivity
   * if user is an Admin.
   *
   * @param view the view
   */
  public void loginUser(View view) {

    EditText usernameText = (EditText) findViewById(R.id.login_username);
    String username = usernameText.getText().toString();
    EditText passwordText = (EditText) findViewById(R.id.login_pass);
    String password = passwordText.getText().toString();

    Client client = UserManager.getUserByUsername(username);
    Administrator admin = UserManager.getAdminByUsername(username);
    if (client != null) {
      String actualPassword = client.getPass();

      // next page is the Welcome Page
      Intent nextPage;
      if (password.equals(actualPassword)) {
        nextPage = new Intent(this, WelcomeActivity.class);
        // Passes the User object to RegisterStep2Activity.
        nextPage.putExtra("client", client);
        startActivity(nextPage);
      } else {
        // create a dialog box showing a successful update
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(R.string.incorrect_password);
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
    } else if (admin != null) {
      String actualPassword = admin.getPass();

      Intent nextPage;
      if (password.equals(actualPassword)) {
        nextPage = new Intent(this, AdminWelcomeActivity.class);
        // Passes the User object to RegisterStep2Activity.
        nextPage.putExtra("admin", admin);
        startActivity(nextPage);
      } else {
        // create a dialog box showing a successful update
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(R.string.incorrect_password);
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


    } else {
      // create a dialog box showing a successful update
      AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
      builder.setTitle(R.string.user_does_not_exist);
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

  /**
   * Launches RegisterStep1Activity.
   *
   * @param view the view
   */
  public void registerNewUser(View view) {
    // go directly to the registration form
    Intent nextPage = new Intent(this, RegisterStep1Activity.class);
    startActivity(nextPage);
  }
}

