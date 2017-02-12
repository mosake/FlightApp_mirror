package cs.b07.flightmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AdminWelcomeActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_welcome);
  }

  public void editClientInfoPage(View view) {
    final Intent searchClientInfoPage = new Intent(this, SearchClientActivity.class);
    startActivity(searchClientInfoPage);
  }

  /**.
   * Goes back to the login screen.
   * @param view the vieew you are currently on
   */
  public void logOut(View view) {
    final Intent loginPage = new Intent(AdminWelcomeActivity.this, LoginActivity.class);
    loginPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(loginPage);
  }

  /**
   * Launch SearchClientToBookActivity.
   *
   * @param view the view
   */
  public void bookItinerary(View view) {
    final Intent searchToBookPage = new Intent(this, SearchClientToBookActivity.class);
    startActivity(searchToBookPage);
  }

  /**
   * Launch SearchFlightActivity.
   *
   * @param view the view
   */
  public void updateFlight(View view) {
    final Intent updatePage = new Intent(this, SearchFlightActivity.class);
    updatePage.putExtra("permissionKey", 1);
    startActivity(updatePage);
  }

  /**
   * Launch UploadActivity.
   *
   * @param view the view
   */
  public void uploadFlight(View view) {
    final Intent uploadPage = new Intent(this, UploadActivity.class);
    startActivity(uploadPage);
  }
}
