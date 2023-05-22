package Program.Main;

import LoginLogout.LoginLogout;
import Program.Main.DBManagement.SQLExecuters;
import Program.Main.Guis.CarsGui;
import Program.Main.Guis.ClientGui;
import Program.Main.Guis.EmployeeGui;
import Program.Main.Guis.MainGui;
import Program.Main.Guis.RightClick.RightClickGui;
import Program.Main.Objects.CarsObject;
import Program.Main.Objects.ClientsObject;
import Program.Main.Objects.EmployeesObject;
import Program.Main.Resources.Audio.BackgroundMusic;
import Program.Main.Utilities.Email;
import Program.Main.Utilities.ScreenBounds;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
public class App extends Application implements ScreenBounds, Email {
	static Rectangle2D screenBounds = Screen.getPrimary().getBounds();

	// Used to store data from databases
	public static final ObservableList<CarsObject> dataListCars = FXCollections.observableArrayList();
	public static final ObservableList<EmployeesObject> dataListEmployees = FXCollections.observableArrayList();
	public static final ObservableList<ClientsObject> dataListClients = FXCollections.observableArrayList();
	public static SQLExecuters sql = new SQLExecuters();
	public static BackgroundMusic bgMusic = new BackgroundMusic();
	// TO get screen width and height
	// Getting user's screen size from property in Gui class
	public MainGui mainGui = new MainGui();
	public CarsGui carsGui = new CarsGui();
	public EmployeeGui employeeGui = new EmployeeGui();
	public ClientGui clientGui = new ClientGui();
	public Scene mainScene;
	public Scene carsScene;
	public Scene employeeScene;
	public Scene clientScene;
	public Stage tempStage_global = new Stage();
	public LoginLogout log = new LoginLogout();


	public static double screenWidth;
	public static double screenHeight;



	public static double getScreenWidth() {
		return screenBounds.getWidth();
	}

	public static double getScreenHeight() {
		return screenBounds.getHeight();
	}

	public static void main(String[] args){
		launch(args);


			// Place the code causing the exception here
			// It will be executed on the JavaFX application thread

	}

	@Override
	public void start(Stage primaryStage) {
		// IMPORTANT LISTENER // TO CLOSE ALL STAGES ONCE YOU EXIT MAIN STAGE

		primaryStage.setOnCloseRequest(we -> {

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Exit confirmation");
			alert.setHeaderText("Are you absolutely sure that you want to exit this holy program ?");
			alert.setContentText("Steps to exit:\n1- Click Yes");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				if (RightClickGui.rightClickStage.isShowing()) {
					RightClickGui.rightClickStage.close();
				}
			}

			if (result.get() == ButtonType.CANCEL) {
				we.consume();
			}

		});

		// IMPORTANT LISTENER - END //


		tempStage_global = primaryStage;
		// Initiates all the scenes
		initScenes();
		updateScreenSize();

		try {
			// // // // // Listeners // // // // //
			initMainElementListeners();
			initGuiElementListeners();
			// // // // // Listeners - END // // // // //

			tempStage_global.setMinWidth(1280);
			tempStage_global.setMinHeight(720);
			tempStage_global.setWidth(screenWidth);
			tempStage_global.setHeight(screenHeight - (screenHeight / 6));
			tempStage_global.setMaximized(true);

			tempStage_global.setScene(mainScene);

			tempStage_global.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initMainElementListeners() {

		mainGui.currentProfile_btn.setOnAction(e -> {
			log.LoginLogoutScreen();
		});
		// // Main Button Listeners // //
		mainGui.carsGui_btn.setOnAction(e -> {
			mainGui.disableMainButtons(true);

			sceneSwitchAnimation(mainGui.border, tempStage_global, carsScene);

		});

		mainGui.employeeGui_btn.setOnAction(e -> {
			mainGui.disableMainButtons(true);

			sceneSwitchAnimation(mainGui.border, tempStage_global, employeeScene);
			employeeGui.initInitialPane();

		});

		mainGui.clientGui_btn.setOnAction(e -> {
			mainGui.disableMainButtons(true);

			sceneSwitchAnimation(mainGui.border, tempStage_global, clientScene);

		});

		// // Main Button Listeners - END // //
	}

	private void initGuiElementListeners() {
		// // CARS -> OPTIONS // //

		carsGui.addCar_btn.setOnAction(e -> {
			carsGui.clearCenterDisplay_Options();
			carsGui.switchOption(carsGui, 1);
			// To add the method to switch scene
		});

		carsGui.displayCars_btn.setOnAction(e -> {
			carsGui.clearCenterDisplay_Options();
			carsGui.switchOption(carsGui, 2);
			// To add the method to switch scene
		});

		carsGui.carOptions_btn.setOnAction(e -> {
			carsGui.clearCenterDisplay_Options();
			carsGui.switchOption(carsGui, 3);
			// To add the method to switch scene
		});

		carsGui.removecars_btn.setOnAction(e -> {
			carsGui.clearCenterDisplay_Options();
			carsGui.switchOption(carsGui, 4);

			// To add the method to switch scene
		});

		carsGui.goBack_btn.setOnAction(e -> {
			carsGui.disableCarButtons(true);
			sceneSwitchAnimation(carsGui.border, tempStage_global, mainScene);
		});

		// Same with client and employees but create new functions for them
		// in SQLExecutes
		carsGui.saveCar_btn.setOnAction(e -> {
			sql.refreshCars();
			if ((!carsGui.checkEmptyBoxes())) {
				try {

					String brand = carsGui.vehicleBrands_ComboBox.getValue().toString();
					String model = carsGui.vehicleModels_ComboBox.getValue().toString();
					String color = carsGui.vehicleColors_ComboBox.getValue().toString();
					String category = carsGui.vehicleCategories_ComboBox.getValue().toString();
					int nbdoors = Integer.parseInt(carsGui.vehicleDoors_ComboBox.getValue().toString());
					float price = Float.parseFloat(carsGui.vehiclePrice_Field.getText());

					sql.insertCar(brand, model, color, category, nbdoors, price);
					sql.refreshCars();

				} catch (SQLException e1) {
					e1.printStackTrace();

				} catch (Exception ignored) {
					ignored.printStackTrace();
				}
				carsGui.resetBoxes();
			}

		});

		// // CARS -> OPTIONS - END // //

		// // EMPLOYEE -> OPTIONS // //

		employeeGui.addEmployee_btn.setOnAction(e -> {
			employeeGui.clearCenterDisplay_Options();
			employeeGui.switchOption(employeeGui, 1);
			// To add the method to switch scene
		});
		employeeGui.displayEmployees_btn.setOnAction(e -> {
			employeeGui.clearCenterDisplay_Options();
			employeeGui.switchOption(employeeGui, 2);
			// To add the method to switch scene
		});
		employeeGui.updateEmployees_btn.setOnAction(e -> {
			employeeGui.clearCenterDisplay_Options();
			employeeGui.switchOption(employeeGui, 3);
			// To add the method to switch scene
		});
		employeeGui.deleteEmployee_btn.setOnAction(e -> {
			employeeGui.clearCenterDisplay_Options();
			employeeGui.switchOption(employeeGui, 4);
			// To add the method to switch scene
		});

		employeeGui.goBack_btn.setOnAction(e -> {
			// disable employee buttons
			employeeGui.disableEmployeeButtons(true);
			sceneSwitchAnimation(employeeGui.border, tempStage_global, mainScene);

		});

		employeeGui.empSave_btn.setOnAction(e -> {
			if (!(employeeGui.checkEmptyBoxes())) {
				try {
					boolean correctEmail = emailVerifier(employeeGui.empEmail_Field.getText());
					if (correctEmail) {
						/* setting style here in case user entered wrong and
						 color was red
						 so it resets when he enters correct*/
						employeeGui.empEmail_Field.setStyle("-fx-background-color:#0b2d39");

						try {
							String fName = employeeGui.empFName_Field.getText();

							String lName = employeeGui.empLName_Field.getText();
							String gender = "";
							if (employeeGui.empGender_ComboBox.getValue().toString() == "Male") {
								gender = "M";
							}
							if (employeeGui.empGender_ComboBox.getValue().toString() == "Female") {
								gender = "F";
							}

							String position = employeeGui.empPosition_ComboBox.getValue().toString();

							String address = employeeGui.empAddress_Field.getText();
							String phone = employeeGui.empTelephone_Field.getText();
							String email = employeeGui.empEmail_Field.getText();
							float salary = Float.parseFloat(employeeGui.empSalary_Field.getText());

							Date dateOfBirth = Date.valueOf((employeeGui.empDatePicker_Calendar.getValue().toString()));

							sql.insertEmployee(fName, lName, gender, position, salary, address, phone, email, dateOfBirth);
							sql.refreshEmployees();
							employeeGui.resetBoxes();
						} catch (Exception ignored) {
							employeeGui.displayDuplicateError();
						}

					} else {
						employeeGui.empEmail_Field.setStyle("-fx-background-color:red");
					}
				} catch (Exception exep) {
					employeeGui.empEmail_Field.setStyle("-fx-background-color:red");
				}

			}
		});

		// // EMPLOYEE -> OPTIONS - END // //

		// // CLIENT -> OPTIONS // //

		clientGui.addClient_btn.setOnAction(e -> {
			clientGui.clearCenterDisplay_Options();
			clientGui.switchOption(clientGui, 1);
			// To add the method to switch scene
		});
		clientGui.displayClients_btn.setOnAction(e -> {
			clientGui.clearCenterDisplay_Options();
			clientGui.switchOption(clientGui, 2);
			// To add the method to switch scene
		});
		clientGui.updateClients_btn.setOnAction(e -> {
			clientGui.clearCenterDisplay_Options();
			clientGui.switchOption(clientGui, 3);
			// To add the method to switch scene
		});
		clientGui.deleteAllClients_btn.setOnAction(e -> {
			clientGui.clearCenterDisplay_Options();
			clientGui.switchOption(clientGui, 4);
			// To add the method to switch scene
		});
		clientGui.goBack_btn.setOnAction(e -> {
			// disable client buttons
			clientGui.disableClientButtons(true);
			sceneSwitchAnimation(clientGui.border, tempStage_global, mainScene);

		});

		clientGui.clientSave_btn.setOnAction(e -> {
			if (!(clientGui.checkEmptyBoxes())) {
				try {
					boolean correctEmail = emailVerifier(clientGui.clientEmail_Field.getText());
					if (correctEmail) {
						/* setting style here in case user entered wrong and
						 color was red
						 so it resets when he enters correct*/
						clientGui.clientEmail_Field.setStyle("-fx-background-color:#0b2d39");

						try {
							String fName = clientGui.clientFName_Field.getText();

							String lName = clientGui.clientLName_Field.getText();
							String gender = "";
							if (clientGui.clientGender_ComboBox.getValue().toString() == "Male") {
								gender = "M";
							}
							if (clientGui.clientGender_ComboBox.getValue().toString() == "Female") {
								gender = "F";
							}

							String address = clientGui.clientAddress_Field.getText();
							String phone = clientGui.clientTelephone_Field.getText();
							String email = clientGui.clientEmail_Field.getText();
							Date dateOfBirth = Date.valueOf((clientGui.clientDatePicker.getValue().toString()));

							sql.insertClient(fName, lName, gender, address, phone, email, dateOfBirth);
							sql.refreshClients();
							clientGui.resetBoxes();
						} catch (Exception ignored) {
							clientGui.displayDuplicateError();
						}

					} else {
						clientGui.clientEmail_Field.setStyle("-fx-background-color:red");
					}
				} catch (Exception exep) {
					clientGui.clientEmail_Field.setStyle("-fx-background-color:red");
				}

			}
		});

		// // CLIENT -> OPTIONS - END // //

	}

	// If screen works finally and all proportions work then keep this
	public void updateScreenSize() {
		screenWidth = getScreenWidth();
		screenHeight = getScreenHeight();
	}

	private void initScenes() {
		mainGui.init();
		mainScene = mainGui.getGuiScene();

		carsGui.init();
		carsScene = carsGui.getGuiScene();

		employeeGui.init();
		employeeScene = employeeGui.getGuiScene();

		clientGui.init();
		clientScene = clientGui.getGuiScene();
	}

	private void resetButtonsStatus() {
		mainGui.disableMainButtons(false);
		carsGui.disableCarButtons(false);
		employeeGui.disableEmployeeButtons(false);
		clientGui.disableClientButtons(false);
	}

	// Method used to switch from main buttons to different sectors and back to
	// main
	public void sceneSwitchAnimation(Node primaryMainNode, Stage stage, Scene newScene) {
		FadeTransition fadeScene = new FadeTransition(Duration.millis(300));

		fadeScene.setNode(primaryMainNode);
		fadeScene.setFromValue(1);
		fadeScene.setToValue(0);
		fadeScene.play();
		fadeScene.setOnFinished(e -> {

			// Sets a new scene on stage
			stage.setScene(newScene);
			// Added this here to try and fix the screen size problem
			updateScreenSize();
			primaryMainNode.setOpacity(1);
			// Reenables all buttons that were disabled before
			resetButtonsStatus();

			if (newScene == carsScene) {
				carsGui.fillCenterDisplay_Options();
				carsGui.initInitialPane();
				// Resets TextBoxes if anything was placed before in them
				carsGui.resetBoxes();

			} else if (newScene == employeeScene) {
				employeeGui.fillCenterDisplay_Options();
				employeeGui.initInitialPane();
				// Resets TextBoxes if anything was placed before in them
				employeeGui.resetBoxes();

			} else if (newScene == clientScene) {
				clientGui.fillCenterDisplay_Options();
				clientGui.initInitialPane();
				// Resets TextBoxes if anything was placed before in them
				clientGui.resetBoxes();

			} else if (newScene == mainScene) {
				// Clears Elements in stackPane of gui's so that on next entry
				// they update
				carsGui.clearCenterDisplay_Options();
				employeeGui.clearCenterDisplay_Options();
				clientGui.clearCenterDisplay_Options();
			}

		});
	}

}
