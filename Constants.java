package cs.b07.flightmanager;

import managers.FileManager;

import java.util.logging.Logger;

/**
 * A class storing constants needed by the app.
 */
public class Constants {

  // ----- constants for file names and paths  -----

  public static final String FILE_PATH_SERIALIZED = "serialized";
  public static final String FILE_PATH_TEXT = "text";
  public static final String FILE_PATH_FLIGHTS = "text/flights";

  public static final String FILE_NAME_USERS = "clients.txt";
  public static final String FILE_NAME_LOGIN = "passwords.txt";
  public static final Logger FLOGGER = Logger.getLogger(FileManager.class.getPackage().getName());

  public static final String FILE_NAME_SERIALIZED_CLIENTS = "clients.ser";
  public static final String FILE_NAME_SERIALIZED_ADMINS = "administrators.ser";
  public static final String FILE_NAME_SERIALIZED_FLIGHTS = "flights.ser";

  public static final String SECRET_CODE = "AnyaRocks!";
}
