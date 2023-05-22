package LoginLogout;

import Program.Main.CustomClass.CButton;
import Program.Main.DBManagement.InitConnection;
import Program.Main.App;
import Program.Main.Utilities.FilePaths;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LoginLogout implements FilePaths {
	public static String username = "";
	public static String password = "";
	public static int port = 0;
	public static String dataBaseName = "";
	public static Stage loginLogoutStage = new Stage();
	public static Scene loginLogoutScene;
	public static VBox mainVBox = new VBox();
	public static boolean loggedIn = false;
	public static String pfp = profilePic_ExePath;
	public static CButton profile = new CButton("profile", true);
	public static CButton logout = new CButton("Logout", "log-out-button");

	public LoginLogout() {
		initScene();
		try (Scanner scanner = new Scanner(new File(credentials_ExePath));) {
			while (scanner.hasNextLine()) {
				username = scanner.nextLine();
				password = scanner.nextLine();
				port = Integer.parseInt(scanner.nextLine());
				dataBaseName = scanner.nextLine();
				loggedIn = true;
			}
		} catch (FileNotFoundException ignored) {

		}
		try (Scanner scanner = new Scanner(new File(profilePicText_ExePath));) {
			while (scanner.hasNextLine()) {
				pfp = scanner.nextLine();
			}
		} catch (FileNotFoundException e) {
		}
		if (loggedIn) {
			// System.out.print("LOGGED IN"); // just for debug
			InitConnection.setUSERNAME(username);
			InitConnection.setPASS(password);
			InitConnection.setPORT(port);
			InitConnection.setDbName(dataBaseName);
		}
	}
	public static void saveCredentials(String UserName, String Password, int Port, String dbName) {
		username = UserName;
		password = Password;
		port = Port;
		dataBaseName = dbName;

		try (PrintWriter fileWriter = new PrintWriter(credentials_ExePath);) {
			fileWriter.println(username);
			fileWriter.println(password);
			fileWriter.println(port);
			fileWriter.println(dataBaseName);
		} catch (IOException e1) {
		}
	}

	public static void resetObjectLists() {
		App.dataListCars.clear();
		App.dataListClients.clear();
		App.dataListEmployees.clear();
	}

	public static void savePfp(String pfpNew) {
		try (PrintWriter printWriter = new PrintWriter(profilePicText_ExePath);) {
			printWriter.println(pfpNew);
		} catch (FileNotFoundException e) {
		}
	}
	private void initScene() {
		loginLogoutScene = new Scene(mainVBox, 200, 200);
		loginLogoutStage = new Stage();
		loginLogoutStage.setScene(loginLogoutScene);
		mainVBox.getChildren().addAll(profile, logout);

		mainVBox.setId("vbox");
		loginLogoutScene.getStylesheets().add(dBOptionsCSS_ExePath);
	}
	public void LoginLogoutScreen() {
		// System.out.println("accessing loginlogoutscreen");
		if (loggedIn) {
			profile.setStyle("-fx-background-size: cover;-fx-min-width: 100;-fx-min-height: 100;");

			profile.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				File selectedFile = fileChooser.showOpenDialog(loginLogoutStage);
				try {
					pfp = selectedFile.getPath();
				} catch (Exception ignored) {

				}

				savePfp(pfp);
			});

			logout.setOnAction(e -> {
				InitConnection.connectionStarted = false;
				resetObjectLists();
				loggedIn = false;
				loginLogoutStage.close();
				InitConnection.setUSERNAME("");
				InitConnection.setPASS("");
				InitConnection.setPORT(0);
				InitConnection.setDbName("");
				try (PrintWriter fileWriter = new PrintWriter(credentials_ExePath);) {
					fileWriter.print("");
				} catch (IOException e1) {
				}
			});
			mainVBox.setSpacing(10);
			mainVBox.setAlignment(Pos.CENTER);
			loginLogoutStage.showAndWait();
		} else {
			App.sql.tryConnection();
			if (InitConnection.isConnectionStarted()) {
				loggedIn = true;
				App.sql.refreshCars();
				App.sql.refreshEmployees();
				App.sql.refreshClients();
				App.sql.insertDefaultVehicleBrands();
			}

		}
	}

}
