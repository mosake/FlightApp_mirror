package cs.b07.flightmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import managers.FileManager;
import managers.UserManager;
import users.Administrator;
import users.User;

import java.io.File;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AdminRegistrationActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_secret_password);
    setTitle(R.string.secret_code);
  }

  /**
   * Launch the next activity.
   *
   * @param view the view
   */
  public void nextPage(View view) {
    EditText secretCodeField = (EditText) findViewById(R.id.secret_code);
    String secretCode = secretCodeField.getText().toString();

    if (secretCode.equals(Constants.SECRET_CODE)) {
      Intent intent = getIntent();
      User admin = (User) intent.getSerializableExtra("userKey");

      String username = admin.getUser();
      String password = admin.getPass();

      Administrator newAdmin = new Administrator(username, password);

      Intent next = new Intent(this, LoginActivity.class);
      next.putExtra("adminKey", newAdmin);

      // add new admin to file and write to "clients.txt" + "passwords.txt"
      managers.UserManager.addUser(newAdmin);

      // -- file info --
      File filepathText = this.getApplicationContext().getDir(Constants.FILE_PATH_TEXT,
              MODE_PRIVATE | MODE_APPEND);
      File userfile = new File(filepathText, Constants.FILE_NAME_USERS);
      File loginfile = new File(filepathText, Constants.FILE_NAME_LOGIN);

      File filepathSer = this.getApplicationContext().getDir(
          Constants.FILE_PATH_SERIALIZED, MODE_PRIVATE | MODE_APPEND);
      final File serfile = new File(filepathSer, Constants.FILE_NAME_SERIALIZED_ADMINS);

      FileManager.writeFile(userfile.getPath(), newAdmin.toString());
      FileManager.writeFile(loginfile.getPath(), newAdmin.getUser() + "," + newAdmin.getPass());
      Map<String, Administrator> file2 = FileManager.getAdmins();
      file2.putAll(UserManager.getAdminMap());
      FileManager.setAdmins(file2);
      FileManager.saveToFile(serfile.getPath(), newAdmin);

      AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
      builder.setTitle(R.string.registered_admin);
      builder.setMessage(R.string.disappear);
      builder.setCancelable(false);

      final AlertDialog closeDialog = builder.create();

      closeDialog.show();

      // close the dialog box after 3 seconds
      final Timer timer2 = new Timer();
      timer2.schedule(new TimerTask() {
        public void run() {
          closeDialog.dismiss();
          Intent back = new Intent(AdminRegistrationActivity.this, LoginActivity.class);
          back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(back);
          timer2.cancel(); //this will cancel the timer of the system
        }
      }, 3000); // the timer will count 3 seconds


    } else {
      AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
      builder.setTitle(R.string.are_you_sure_admin);
      builder.setMessage(R.string.disappear);
      builder.setCancelable(false);

      final AlertDialog closeDialog = builder.create();

      closeDialog.show();

      // close the dialog box after 3 seconds
      final Timer timer2 = new Timer();
      timer2.schedule(new TimerTask() {
        public void run() {
          closeDialog.dismiss();
          Intent back = new Intent(AdminRegistrationActivity.this, LoginActivity.class);
          back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(back);
          timer2.cancel(); //this will cancel the timer of the system
        }
      }, 3000); // the timer will count 3 seconds


    }
  }

}
