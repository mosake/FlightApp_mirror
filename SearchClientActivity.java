package cs.b07.flightmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import managers.UserManager;
import users.Client;

import java.util.Timer;
import java.util.TimerTask;

public class SearchClientActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_client);
  }

  /**
   * Searches for a Client by username, and shows the profile of that Client.
   *
   * @param view the view
   */
  public void searchClient(View view) {
    EditText clientUsernameField = (EditText) findViewById(R.id.SearchClientUsername_editText);
    final String clientUsername = clientUsernameField.getText().toString();

    Client client = UserManager.getUserByUsername(clientUsername);

    if (client != null) {
      final Intent clientProfile = new Intent(this, ProfileActivity.class);
      clientProfile.putExtra("client", client);
      startActivity(clientProfile);
    } else {
      // create a dialog box showing a successful update
      AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
      builder.setTitle(R.string.client_does_not_exist);
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
