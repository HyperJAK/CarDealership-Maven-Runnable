package Program.Main.DBManagement;

import LoginLogout.LoginLogout;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.DriverManager;

public class InitConnection {

	// This class establishes the connection between javafx and database (It is
	// used in SQLExecutes class)

	// Don't modify
	private static final String HOST = "localhost";
	// This is used to redisplay the info entering stage when save button is
	// clicked it is used in line 78 in this class
	public static boolean programStarted = false;
	public static boolean closedLogin = false;
	// Insert your database name here
	private static String DB_NAME = "vehicledealership";
	// Don't modify
	private static String USERNAME = "root";
	// Don't modify
	private static int PORT = 3306;
	private static String PASS = "jak1";
	private static String URL;
	// This is used to check if database is connected and based on that execute
	// refresh functions in start of Main class
	public static boolean connectionStarted = false;
	private static Connection connection;

	// Returns connection status
	public static boolean isConnectionStarted() {
		return connectionStarted;
	}

	public static void refreshURL() {
		URL = ("jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME);
	}

	public static Connection getConnection() {
		refreshURL();

		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASS);
			connectionStarted = true;
			showSuccess();
		} catch (Exception j) {
			try {

				showFail();

			} catch (Exception ignored) {

			}

		}

		return connection;
	}

	public void saveCredentials(){

	}

	// Function to show connection success
	public static void showSuccess() {
		Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
		alert2.setTitle("Connection found");
		alert2.setHeaderText("You have been succesfully connected to the Database: " + DB_NAME);

		LoginLogout.loggedIn = true;
		/*if (DBOptionsGui.rememberMe.isSelected()) {
			LoginLogout.saveCredentials(USERNAME, PASS, PORT, DB_NAME);
		}*/

		alert2.showAndWait();

	}

	// Function to show that connection failed and handle it
	public static void showFail() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Database Not Found");
		alert.setHeaderText("The connection to the database couldn't be established");
		alert.setContentText("Please try to change either one of these info: \n" + "-Url\n" + "-Username\n" + "-Password\n" + "-Port\n" + "-Database name");

		/*alert.setOnCloseRequest(e -> {
			closedLogin = true;
		});*/

		alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {

			try {
				// Here it checks if we have already initiated the Gui
				// so that it doesn't display it twice
				if (programStarted) {
					DBOptionsGui.showStage();

				} else {
					DBOptionsGui.init();
					programStarted = true;
				}

				try {
					connection = DriverManager.getConnection(URL, USERNAME, PASS);
					connectionStarted = true;

				} catch (Exception ignored) {

				}
			} catch (Exception ignored) {

			}

		});

	}

	public static String getDbName() {
		return DB_NAME;
	}

	public static void setDbName(String dbName) {
		DB_NAME = dbName;
	}
	// When you setup password on mysql just add it here, dw i won't hac u ^w^

	public static String getUSERNAME() {
		return USERNAME;
	}

	public static void setUSERNAME(String USERNAME) {
		InitConnection.USERNAME = USERNAME;
	}

	public static int getPORT() {
		return PORT;
	}

	public static void setPORT(int PORT) {
		InitConnection.PORT = PORT;
	}

	public static String getURL() {
		return URL;
	}

	public static void setURL(String URL) {
		InitConnection.URL = URL;
	}

	public static void setPASS(String PASS) {
		InitConnection.PASS = PASS;
	}

}
