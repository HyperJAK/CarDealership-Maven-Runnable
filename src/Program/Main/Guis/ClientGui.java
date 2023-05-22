package Program.Main.Guis;

import Program.Main.CustomClass.CButton;
import Program.Main.CustomClass.CComboBox;
import Program.Main.CustomClass.CTextField;
import Program.Main.Guis.RightClick.ClientsRightClickGui;
import Program.Main.App;
import Program.Main.Objects.ClientsObject;
import Program.Main.Utilities.Fonts;
import Program.Main.Utilities.ScreenBounds;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ClientGui extends Gui {

	// // Table elements // //

	public static TableView<ClientsObject> displayClients_Table = new TableView<>();
	private final TableColumn<ClientsObject, String> id_col = new TableColumn<>("ID");
	private final TableColumn<ClientsObject, String> firstName_col = new TableColumn<>("First Name");
	private final TableColumn<ClientsObject, String> lastName_col = new TableColumn<>("Last Name");
	private final TableColumn<ClientsObject, String> gender_col = new TableColumn<>("Gender");
	private final TableColumn<ClientsObject, String> address_col = new TableColumn<>("Address");
	private final TableColumn<ClientsObject, String> telephone_col = new TableColumn<>("Telephone");
	private final TableColumn<ClientsObject, String> email_col = new TableColumn<>("Email");
	private final TableColumn<ClientsObject, String> dateOfBirth_col = new TableColumn<>("Birth Date");
	private final TableColumn<ClientsObject, String> carsOwned_col = new TableColumn<>("Nb Of Cars");

	// // Table elements - END // //

	// // Left Bar Options Buttons // //
	public CButton addClient_btn = new CButton("Add Clients", "add_clt_button");
	public CButton displayClients_btn = new CButton("Display Clients", "display_clts_button");
	public CButton updateClients_btn = new CButton("Client Options", "clt_options_button");

	public CButton deleteAllClients_btn = new CButton("Remove Clients", "remove_clts_button");
	public CButton goBack_btn = new CButton("Go Back", "go_back_button_clt");

	// // Left Bar Options Buttons - END // //

	// // ADD CLIENT OPTION FIELDS // //
	public GridPane addClient_GridPane = new GridPane();
	public CTextField clientFName_Field = new CTextField("First Name", "client-first-name-textfield");
	public CTextField clientLName_Field = new CTextField("Last Name", "client-last-name-textfield");
	public CTextField clientEmail_Field = new CTextField("Email", "client-email-textfield");
	public CTextField clientTelephone_Field = new CTextField("Phone number", "client-telephone-textfield");
	public CTextField clientAddress_Field = new CTextField("Address", "client-address-textfield");
	public CComboBox clientGender_ComboBox = new CComboBox("Gender", "client-gender-combo");
	public CButton clientSave_btn = new CButton("Save Client", "save-client-button");
	public DatePicker clientDatePicker = new DatePicker();
	// // ADD CLIENT OPTION FIELDS - END // //

	public ClientsRightClickGui ClientsRightClick = new ClientsRightClickGui();
	public static void initSearchFilter() {
		// Search filter

		FilteredList<ClientsObject> filteredDataClients = new FilteredList<>(App.dataListClients, b -> true);

		clientsSearch_Field.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataClients.setPredicate(ClientsObject -> {
				if (isEmptyOrBlank(newValue)) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();
				// Note // get() gets the string from simpleString that
				// getBrand() returns
				if (ClientsObject.getId().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (ClientsObject.getfName().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (ClientsObject.getlName().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (ClientsObject.getGender().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (ClientsObject.getDateOfBirth().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (String.valueOf(ClientsObject.getAddress()).toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (String.valueOf(ClientsObject.getTelephone()).toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (String.valueOf(ClientsObject.getEmail()).toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (String.valueOf(ClientsObject.getCarsOwned()).toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else {
					return false;
				}

			});
		});

		SortedList<ClientsObject> sortedDataClients = new SortedList<>(filteredDataClients);

		sortedDataClients.comparatorProperty().bind(displayClients_Table.comparatorProperty());

		displayClients_Table.setItems(sortedDataClients);
	}
	@Override
	public void initialiseCss() {

		// initConnectCSS(scene, clientCSS_Path, true);
		initConnectCSS(scene, clientCSS_ExePath, true);

	}
	@SuppressWarnings("static-access")
	@Override
	public void initializeComponents() {

		initSearchFilter();
		initListeners();
		initDisplayTable();
		fillTableContainer();
		contextMenuListener();
		// // Top Bar Options // //
		sceneTitle.setText("Client Management");
		sceneTitle.setTextFill(Color.RED);
		sceneTitle.setFont(Fonts.getRomanTimes());

		hBorder.setLeft(sceneTitle);
		hBorder.setCenter(currentProfile_btn);

		hBorder.setAlignment(sceneTitle, Pos.CENTER_LEFT);
		hBorder.setMargin(sceneTitle, new Insets(0, 0, 0, 30));
		hBorder.setMargin(currentProfile_btn, new Insets(10, 0, 10, -(ScreenBounds.getScreenWidth() / 6.5)));

		border.setTop(hBorder);

		// // Top Bar Options - END // //

		clientDatePicker.setId("client-date-of-birth-field");
		clientDatePicker.setPromptText("Date of Birth");

		// // AddEmployee Option // //

		// Putting add employee options in the right position
		double classBox_YValue = (ScreenBounds.getScreenHeight() / 2) - (ScreenBounds.getScreenHeight() / 2.5);

		addClient_GridPane.setTranslateY(classBox_YValue);
		addClient_GridPane.setAlignment(Pos.TOP_CENTER);

		addClient_GridPane.setHgap(10); // horizontal gap in pixels => that's
		// what
		// you are asking for
		addClient_GridPane.setVgap(10); // vertical gap in pixels

		clientGender_ComboBox.getItems().addAll("Male", "Female");

		// Instead add here the table to pick date of birth
		// addClient_grid.add(clientAge, 0, 0);
		addClient_GridPane.add(clientDatePicker, 0, 0);
		addClient_GridPane.add(clientGender_ComboBox, 0, 1);
		addClient_GridPane.add(clientFName_Field, 1, 0);
		addClient_GridPane.add(clientEmail_Field, 1, 1);
		addClient_GridPane.add(clientAddress_Field, 1, 2);
		addClient_GridPane.add(clientLName_Field, 2, 0);
		addClient_GridPane.add(clientTelephone_Field, 2, 1);
		addClient_GridPane.add(clientSave_btn, 2, 2);

		// // AddEmployee Option - END // //

		// // Left Bar Options // //

		leftOptions_vbox.getChildren().addAll(addClient_btn, displayClients_btn, goBack_btn);

		leftOptions_vbox.setAlignment(Pos.CENTER);

		border.setLeft(leftOptions_vbox);

		// // Left Bar Options - END // //

	}
	@Override
	public void fillTableContainer() {
		displayTableContainer_vbox.getChildren().addAll(searchField_Text, clientsSearch_Field, displayClients_Table);
		displayTableContainer_vbox.setSpacing(10);
		displayTableContainer_vbox.setAlignment(Pos.CENTER);
		displayTableContainer_vbox.setTranslateY(-(ScreenBounds.getScreenHeight() / 35));
		displayTableContainer_vbox.setTranslateX(-(ScreenBounds.getScreenWidth() / 50));
	}
	public void initInitialPane() {
		firstViewedScene_text.setText(String.valueOf(App.dataListClients.size())); // change
		// this
		// to
		// the
		// number
		// of
		// clients later ofc
		firstViewedScene_text2.setText("Total Clients served");
		border.setCenter(this.firstViewedScene_stack);
	}
	// To disable/enable CLIENT buttons
	public void disableClientButtons(boolean status) {
		if (status) {
			addClient_btn.setDisable(true);
			displayClients_btn.setDisable(true);
			updateClients_btn.setDisable(true);
			deleteAllClients_btn.setDisable(true);
			goBack_btn.setDisable(true);
		} else {
			addClient_btn.setDisable(false);
			displayClients_btn.setDisable(false);
			updateClients_btn.setDisable(false);
			deleteAllClients_btn.setDisable(false);
			goBack_btn.setDisable(false);
		}
	}
	@Override
	public void switchOption(Object obj, int option) {
		// TODO Auto-generated method stub
		if (obj.getClass() == ClientGui.class) {
			switch (option) {
				case 1 -> {
					this.firstViewedScene_stack.getChildren().addAll(this.firstViewedScene_rect, addClient_GridPane);
				}
				case 2 -> {
					this.firstViewedScene_stack.getChildren().addAll(this.firstViewedScene_rect, displayTableContainer_vbox);
				}
				case 3 -> {
					this.firstViewedScene_stack.getChildren().addAll(this.firstViewedScene_rect, displayTableContainer_vbox);
				}
				case 4 -> {
					this.firstViewedScene_stack.getChildren().addAll(this.firstViewedScene_rect);
				}
			}

		}

	}
	@Override
	public void initListeners() {

		// Simpler
		clientFName_Field.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (clientFName_Field.getLength() >= 20) {
				e.consume();
			}
		});

		clientLName_Field.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (clientLName_Field.getLength() >= 20) {
				e.consume();
			}
		});

		clientEmail_Field.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (clientEmail_Field.getLength() >= 200 || !"1234567890qwertyuioplkjhgfdsazxcvbnm@.-_".contains(e.getCharacter())) {
				e.consume();
			}
		});

		clientTelephone_Field.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (clientTelephone_Field.getLength() >= 15 || !"1234567890".contains(e.getCharacter())) {
				e.consume();
			}
		});

		// consume do8re we dont want him to enter anything
		// here from his keyboard, mmmmm intellij improvement
		clientDatePicker.addEventFilter(KeyEvent.KEY_PRESSED, Event::consume);
		// consume do8re we dont want him to enter anything
		// here from his keyboard, mmmmm intellij improvement
		clientDatePicker.addEventFilter(KeyEvent.KEY_TYPED, Event::consume);

		/*RightClickGui.rightClickStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		      public void handle(WindowEvent we) {
		    	  	displayTableContainer_vbox.getChildren().clear();
		  			displayTableContainer_vbox.getChildren().addAll(searchField_Text, clientsSearch_Field, displayClients_Table);
		      }
		  }); */
	}
	@SuppressWarnings("unchecked")
	@Override
	public void initDisplayTable() {

		id_col.setCellValueFactory(cellData -> cellData.getValue().getId());
		dateOfBirth_col.setCellValueFactory(cellData -> cellData.getValue().getDateOfBirth());
		carsOwned_col.setCellValueFactory(cellData -> cellData.getValue().getCarsOwned());
		firstName_col.setCellValueFactory(cellData -> cellData.getValue().getfName());
		lastName_col.setCellValueFactory(cellData -> cellData.getValue().getlName());
		gender_col.setCellValueFactory(cellData -> cellData.getValue().getGender());
		address_col.setCellValueFactory(cellData -> cellData.getValue().getAddress());
		telephone_col.setCellValueFactory(cellData -> cellData.getValue().getTelephone());
		email_col.setCellValueFactory(cellData -> cellData.getValue().getEmail());

		displayClients_Table.setId("emp-display-table");

		// TESTING

		/* ClientsObject client1 = new ClientsObject(1, "", 2, "ralph", "daher", "f",
		        "betchay", "81358691", "ex@email.com");*/

		// To Add Employees to observable list
		// Main.dataListClients.addAll(client1);

		displayClients_Table.getColumns().addAll(id_col, dateOfBirth_col, carsOwned_col, firstName_col, lastName_col, gender_col, address_col, telephone_col, email_col);

		displayClients_Table.setMaxWidth(ScreenBounds.getScreenWidth() / 1.2);
		displayClients_Table.setMinHeight(ScreenBounds.getScreenHeight() / 1.6);

		// Resizing is important especially when we have long addresses
		id_col.setResizable(true);
		firstName_col.setResizable(true);
		lastName_col.setResizable(true);
		dateOfBirth_col.setResizable(true);
		carsOwned_col.setResizable(true);
		gender_col.setResizable(true);
		address_col.setResizable(true);
		telephone_col.setResizable(true);
		email_col.setResizable(false);

		id_col.setPrefWidth(ScreenBounds.getScreenWidth() / 18.4);
		dateOfBirth_col.setPrefWidth(ScreenBounds.getScreenWidth() / 10.5);
		carsOwned_col.setPrefWidth(ScreenBounds.getScreenWidth() / 10.5);
		firstName_col.setPrefWidth(ScreenBounds.getScreenWidth() / 11); // x6 of the main
		// width of the
		// whole table
		lastName_col.setPrefWidth(ScreenBounds.getScreenWidth() / 10.5);

		gender_col.setPrefWidth(ScreenBounds.getScreenWidth() / 10.5);
		address_col.setPrefWidth(ScreenBounds.getScreenWidth() / 10.5);
		telephone_col.setPrefWidth(ScreenBounds.getScreenWidth() / 10.3);
		email_col.setPrefWidth(ScreenBounds.getScreenWidth() / 9);

		// Reordering doesnt break anything in code or mix things up its just
		// visual
		displayClients_Table.getColumns().forEach(e -> e.setReorderable(true));

		// displayClients_Table.setItems(Main.dataListClients); // no no we dont
		// need do it here because we adding the filter list then this functions
		// is removing the filtered list and adding this list instead leading to
		// a not working search filter

	}

	@Override
	public void resetBoxes() {
		clientFName_Field.setText("");
		clientLName_Field.setText("");
		clientAddress_Field.setText("");
		clientTelephone_Field.setText("");
		clientEmail_Field.setText("");
		clientDatePicker.getEditor().clear();

		clientGender_ComboBox = new CComboBox("Gender", "client-gender-combo");
		clientGender_ComboBox.getItems().addAll("Male", "Female");

		addClient_GridPane.getChildren().clear();
		firstViewedScene_stack.getChildren().remove(errorText);

		addClient_GridPane.add(clientDatePicker, 0, 0);
		addClient_GridPane.add(clientGender_ComboBox, 0, 1);
		addClient_GridPane.add(clientFName_Field, 1, 0);
		addClient_GridPane.add(clientEmail_Field, 1, 1);
		addClient_GridPane.add(clientAddress_Field, 1, 2);
		addClient_GridPane.add(clientLName_Field, 2, 0);
		addClient_GridPane.add(clientTelephone_Field, 2, 1);
		addClient_GridPane.add(clientSave_btn, 2, 2);
	}

	// Duplicate error:
	public void displayDuplicateError() {
		addClient_GridPane.add(errorText, 1, 4);
		// this.firstViewedScene_stack.getChildren().add(errorText);
		// errorText.setTranslateY(-Main.screenHeight/20);
	}

	@Override
	public boolean checkEmptyBoxes() {

		boolean empty = false;

		if (isEmptyOrBlank(clientFName_Field.getText())) {
			boxHighlighter(clientFName_Field, true);
			empty = true;
		} else {
			boxHighlighter(clientFName_Field, false);
		}
		if (isEmptyOrBlank(clientLName_Field.getText())) {
			boxHighlighter(clientLName_Field, true);
			empty = true;
		} else {
			boxHighlighter(clientLName_Field, false);
		}

		if (isEmptyOrBlank(clientTelephone_Field.getText())) {
			boxHighlighter(clientTelephone_Field, true);
			empty = true;
		} else {
			boxHighlighter(clientTelephone_Field, false);
		}

		if (isEmptyOrBlank(clientAddress_Field.getText())) {
			boxHighlighter(clientAddress_Field, true);
			empty = true;
		} else {
			boxHighlighter(clientAddress_Field, false);
		}

		// email tetxfield isnt being changed because the validation in Main
		// class in empSave_btn listener validates it
		if (isEmptyOrBlank(clientEmail_Field.getText())) {
			clientEmail_Field.setStyle("-fx-background-color:red;");
			empty = true;
		}

		if (clientGender_ComboBox.getSelectionModel().isEmpty()) {
			clientGender_ComboBox.setStyle("-fx-background-color:red;");
			empty = true;
		} else {
			clientGender_ComboBox.setStyle("-fx-background-color:#0b2d39;");
		}
		if (clientDatePicker.getValue() == null) { // If datePicker ae not set,
			// datePicker.getValue()
			// returns null and using
			// datePicker.getValue().toString()
			// will throw
			// NullPointerException.
			clientDatePicker.setStyle("-fx-background-color:red;");
			empty = true;
		} else {
			clientDatePicker.setStyle("-fx-background-color:#0b2d39;");
		}
		return empty;

	}

	// If mouse button clicked on any element in table then take its values
	@Override
	public void contextMenuListener() { // "Listener" ok jak

		displayClients_Table.setOnMouseClicked(e -> {

			// If mouse button clicked is right click
			if (e.getButton() == MouseButton.SECONDARY) {
				// Returns object of ClientsObject class
				ClientsObject client = displayClients_Table.getSelectionModel().getSelectedItem();

				ContextMenu context = new ContextMenu();
				MenuItem addCar_MenuItem = new MenuItem("Add Car");
				MenuItem viewCars_MenuItem = new MenuItem("View Cars");
				MenuItem edit_MenuItem = new MenuItem("Edit Client Info");
				MenuItem delete_MenuItem = new MenuItem("Delete Client");

				if (displayClients_Table.getSelectionModel().getSelectedItem() != null) {
					displayClients_Table.setContextMenu(context);
					context.getItems().addAll(addCar_MenuItem, viewCars_MenuItem, edit_MenuItem, delete_MenuItem);

					addCar_MenuItem.setOnAction(add -> {
						ClientsRightClick.initAddCar(client);
					});

					viewCars_MenuItem.setOnAction(view -> {
						ClientsRightClick.initCarsOfClientTable(client.getNormalizedId());
					});

					edit_MenuItem.setOnAction(edit -> {
						ClientsRightClick.initClientEdit(client);
					});

					delete_MenuItem.setOnAction(del -> {
						ClientsRightClick.deleteObject(client.getNormalizedId());
					});
				}

			}

		});

	}

}
