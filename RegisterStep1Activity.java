package cs.b07.flightmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import users.User;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterStep1Activity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register_step1);
    setTitle(R.string.register);
  }

  /**
   * Checks whether the input is valid, if so, launches RegisterStep2Activity.
   *
   * @param view the view
   */
  public void nextPage(View view) {
    RadioButton clientButton = (RadioButton) findViewById(R.id.Client_button);
    RadioButton adminButton = (RadioButton) findViewById(R.id.Admin_button);

    final EditText usernameText = (EditText) findViewById(R.id.username_field);
    final String username = usernameText.getText().toString();

    if (managers.UserManager.getUserByUsername(username) != null) {
      // create a dialog box showing the username already exists
      AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
      builder.setTitle(R.string.username_exists);
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
      if (clientButton.isChecked()) {
        // next page is RegisterStep2Activity
        final Intent nextPage = new Intent(this, RegisterStep2Activity.class);

        EditText passwordText = (EditText) findViewById(R.id.password_field);
        final String password = passwordText.getText().toString();
        EditText password2Text = (EditText) findViewById(R.id.password2_field);
        final String password2 = password2Text.getText().toString();

        // make sure there is no empty field
        if (!(username.isEmpty() || password.isEmpty() || password2.isEmpty())) {
          if (password.equals(password2)) {
            // Passes the User object to RegisterStep2Activity.
            User client = new User(username, password);
            nextPage.putExtra("userKey", client);
            startActivity(nextPage);
          } else {
            // create a dialog box showing a successful update
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle(R.string.passwords_do_not_match);
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

      if (adminButton.isChecked()) {
        final Intent nextPage = new Intent(this, AdminRegistrationActivity.class);

        EditText passwordText = (EditText) findViewById(R.id.password_field);
        final String password = passwordText.getText().toString();
        EditText password2Text = (EditText) findViewById(R.id.password2_field);
        final String password2 = password2Text.getText().toString();

        if (password.equals(password2)) {
          // Passes the User object to RegisterStep2Activity.
          User user = new User(username, password);
          nextPage.putExtra("userKey", user);
          startActivity(nextPage);
        } else {
          // create a dialog box showing passwords do not match
          AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
          builder.setTitle(R.string.passwords_do_not_match);
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
}

