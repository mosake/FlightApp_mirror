package cs.b07.flightmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import information.BillingInformation;
import managers.FileManager;
import users.Client;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The activity for a user to edit billing information.
 */
public class EditBillingActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_billing);
    setTitle(R.string.edit_billing_information);

    // get the Intent that launched me
    Intent intent = getIntent();

    // get the Client that was put into the Intent with key clientKey
    final Client client = (Client) intent.getSerializableExtra("clientKey");
    // get PersonalInformation object stored in this Client
    final BillingInformation info = client.getBillingInfo();

    // find the TextView object for TextView with their ids
    final EditText cardNumberField = (EditText) findViewById(R.id.editCardNumber);
    final DatePicker expiryDateField = (DatePicker) findViewById(R.id.editExpiryDate);

    // get the button which creates a dialog box
    Button btnShow = (Button) findViewById(R.id.confirmButton);
    // add a on click listener on this button
    btnShow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // get the new credit card number entered in the EditText
        String newCardNumber = cardNumberField.getText().toString();
        // get the new expiry date entered in the DatePicker
        Integer year = expiryDateField.getYear();
        Integer month = expiryDateField.getMonth();
        Integer day = expiryDateField.getDayOfMonth();
        // create a String representation of that date
        String newExpiryDateStr = year.toString() + "-" + month.toString() + "-" + day.toString();
        // convert the String representation to a Date object
        Date newExpiryDate = FileManager.convertStringToDate(newExpiryDateStr);
        // update info to the Client
        info.setCreditCardNo(newCardNumber);
        info.setExpiryDate(newExpiryDate);
        client.setBillingInfo(info);

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
