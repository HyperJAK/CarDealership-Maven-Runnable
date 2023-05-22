package Program.Main.Guis;

import Program.Main.CustomClass.CButton;
import Program.Main.CustomClass.CComboBox;
import Program.Main.CustomClass.CTextField;
import Program.Main.Guis.RightClick.EmployeesRightClickGui;
import Program.Main.App;
import Program.Main.Objects.EmployeesObject;
import Program.Main.Utilities.Fonts;
import Program.Main.Utilities.ScreenBounds;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeGui extends Gui {

	// // Table elements // //

	public static TableView<EmployeesObject> displayEmployees_Table = new TableView<>();
	public static List<String> empPositions_List = new ArrayList<>();
	private final TableColumn<EmployeesObject, String> id_col = new TableColumn<>("ID");
	private final TableColumn<EmployeesObject, String> dateOfBirth_col = new TableColumn<>("Date Of Birth");
	private final TableColumn<EmployeesObject, String> firstName_col = new TableColumn<>("First Name");
	private final TableColumn<EmployeesObject, String> lastName_col = new TableColumn<>("Last Name");
	private final TableColumn<EmployeesObject, String> gender_col = new TableColumn<>("Gender");
	private final TableColumn<EmployeesObject, String> position_col = new TableColumn<>("Position");
	private final TableColumn<EmployeesObject, String> salary_col = new TableColumn<>("Salary");
	private final TableColumn<EmployeesObject, String> address_col = new TableColumn<>("Address");
	private final TableColumn<EmployeesObject, String> telephone_col = new TableColumn<>("Telephone");
	private final TableColumn<EmployeesObject, String> email_col = new TableColumn<>("Email");

	// // Table elements - END // //

	// // Lists - END // //
	// // Left Bar Options Buttons // //
	public CButton addEmployee_btn = new CButton("Add Employees", "add_emp_button");
	public CButton displayEmployees_btn = new CButton("Display Emps", "display_emps_button");
	public CButton updateEmployees_btn = new CButton("Emp Options", "emp_options_button");
	public CButton deleteEmployee_btn = new CButton("Remove Emp", "remove_emp_button");
	public CButton goBack_btn = new CButton("Go Back", "go_back_button_emp");

	// // Left Bar Options Buttons - END// //

	// // ADD Employee OPTION FIELDS // //

	public GridPane addEmployee_Grid = new GridPane();
	public CTextField empFName_Field = new CTextField("First Name", "emp-first-name-textfield");
	public CTextField empLName_Field = new CTextField("Last Name", "emp-last-name-textfield");
	public CTextField empEmail_Field = new CTextField("Email", "emp-email-textfield");
	public CTextField empTelephone_Field = new CTextField("Phone number", "emp-telephone-textfield");
	public CTextField empSalary_Field = new CTextField("Salary", "emp-salary-textfield");
	public CTextField empAddress_Field = new CTextField("Address", "emp-address-textfield");
	public CButton empSave_btn = new CButton("Save Employee", "save-employee-button");
	public CComboBox empGender_ComboBox = new CComboBox("Gender", "emp-gender-combo");
	// Display Employee Option
	public CComboBox empPosition_ComboBox = new CComboBox("Position", "emp-position-combo");
	public DatePicker empDatePicker_Calendar = new DatePicker();

	// // ADD Employee OPTION FIELDS - END // //

	public EmployeesRightClickGui EmployeesRightClick = new EmployeesRightClickGui();
	public static void initSearchFilter() {
		FilteredList<EmployeesObject> filteredDataEmployees = new FilteredList<>(App.dataListEmployees, b -> true);

		employeesSearch_Field.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataEmployees.setPredicate(EmployeesObject -> {
				if (isEmptyOrBlank(newValue)) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();
				// Note // get() gets the string from simpleString that
				// getBrand() returns
				if (EmployeesObject.getID().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (EmployeesObject.getFname().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (EmployeesObject.getLname().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (EmployeesObject.getGender().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (EmployeesObject.getAddress().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (EmployeesObject.getTelephone().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (EmployeesObject.getEmail().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (EmployeesObject.getPosition().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (EmployeesObject.getDateOfBirth().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else {
					return false;
				}

			});
		});

		SortedList<EmployeesObject> sortedDataEmployees = new SortedList<>(filteredDataEmployees);

		sortedDataEmployees.comparatorProperty().bind(displayEmployees_Table.comparatorProperty());

		displayEmployees_Table.setItems(sortedDataEmployees);
	}
	@Override
	public void initialiseCss() {

		// initConnectCSS(scene, empCSS_Path, true);
		initConnectCSS(scene, empCSS_ExePath, true);

	}
	@SuppressWarnings("static-access")
	@Override
	public void initializeComponents() {

		// initialize the filter
		initSearchFilter();

		// Initialises all listeners
		initListeners();
		// Initialises table elements
		initDisplayTable();
		// Fills the table and its elements in a VBox
		fillTableContainer();
		// Fill the lists
		initEmployeePositions();
		contextMenuListener();
		// // Gender / Position ComboBox // //

		empGender_ComboBox.getItems().addAll("Male", "Female");
		empPosition_ComboBox.getItems().addAll(empPositions_List);

		// // Gender / Position ComboBox - END // //

		empDatePicker_Calendar.setId("emp-date-of-birth-field");
		empDatePicker_Calendar.setPromptText("Date of Birth");

		// // Top Bar Options // //
		sceneTitle.setText("Emp Management");
		sceneTitle.setTextFill(Color.RED);
		sceneTitle.setFont(Fonts.getRomanTimes());

		hBorder.setLeft(sceneTitle);
		hBorder.setCenter(currentProfile_btn);
		hBorder.setAlignment(sceneTitle, Pos.CENTER);
		hBorder.setMargin(sceneTitle, new Insets(0, 0, 0, 30));
		hBorder.setMargin(currentProfile_btn, new Insets(10, 0, 10, -ScreenBounds.getScreenWidth() / 6.5));

		border.setTop(hBorder);
		// // Top Bar Options - END // //

		// // Left Bar Options // //
		leftOptions_vbox.setAlignment(Pos.CENTER);
		leftOptions_vbox.getChildren().addAll(addEmployee_btn, displayEmployees_btn, goBack_btn);

		border.setLeft(leftOptions_vbox);
		// // Left Bar Options - END // //

		// // AddEmployee Option // //

		// Putting add employee options in the right position
		double classBox_YValue = (ScreenBounds.getScreenHeight() / 2) - (ScreenBounds.getScreenHeight() / 2.3);

		addEmployee_Grid.setTranslateY(classBox_YValue);
		addEmployee_Grid.setAlignment(Pos.TOP_CENTER);

		addEmployee_Grid.setHgap(10); // horizontal gap in pixels => that's what
		// you are asking for
		addEmployee_Grid.setVgap(10); // vertical gap in pixels

		// Adding elements to AddEmployee grid option
		// Instead add here the table to pick date of birth
		// addEmployee_Grid.add(empAge, 0, 0);
		addEmployee_Grid.add(empDatePicker_Calendar, 0, 0);
		addEmployee_Grid.add(empGender_ComboBox, 0, 1);
		addEmployee_Grid.add(empPosition_ComboBox, 0, 2);

		addEmployee_Grid.add(empFName_Field, 1, 0);
		addEmployee_Grid.add(empEmail_Field, 1, 1);
		addEmployee_Grid.add(empSalary_Field, 1, 2);
		addEmployee_Grid.add(empLName_Field, 2, 0);
		addEmployee_Grid.add(empTelephone_Field, 2, 1);
		addEmployee_Grid.add(empAddress_Field, 2, 2);
		addEmployee_Grid.add(empSave_btn, 1, 3);

		// // AddEmployee Option - END // //

	}
	private void initEmployeePositions() {

		//
		try {
			File position = new File(empPositions_ExePath);
			Scanner positionReader = new Scanner(position);
			while (positionReader.hasNext()) {
				empPositions_List.add(positionReader.nextLine());
			}
		} catch (FileNotFoundException e) {
			try {
				File position = new File("TextFiles/positions.txt");
				Scanner positionReader = new Scanner(position);
				while (positionReader.hasNext()) {
					empPositions_List.add(positionReader.nextLine());
				}
			} catch (Exception ignored) {
			}

		}
	}
	@Override
	public void fillTableContainer() {
		displayTableContainer_vbox.getChildren().addAll(searchField_Text, employeesSearch_Field, displayEmployees_Table);
		displayTableContainer_vbox.setSpacing(10);
		displayTableContainer_vbox.setAlignment(Pos.CENTER);
		displayTableContainer_vbox.setTranslateY(-(ScreenBounds.getScreenHeight() / 20));
		displayTableContainer_vbox.setTranslateX(-(ScreenBounds.getScreenWidth() / 50));
	}
	@Override
	public void contextMenuListener() {

		displayEmployees_Table.setOnMouseClicked(e -> {

			// If mouse button clicked is right click
			if (e.getButton() == MouseButton.SECONDARY) {
				// Returns object of ClientsObject class
				EmployeesObject emp = displayEmployees_Table.getSelectionModel().getSelectedItem();

				ContextMenu context = new ContextMenu();
				MenuItem edit_MenuItem = new MenuItem("Edit Employee Info");
				MenuItem delete_MenuItem = new MenuItem("Delete Employee");

				if (displayEmployees_Table.getSelectionModel().getSelectedItem() != null) {

					displayEmployees_Table.setContextMenu(context);
					context.getItems().addAll(edit_MenuItem, delete_MenuItem);

					edit_MenuItem.setOnAction(edit -> {
						EmployeesRightClick.initEmployeeEdit(emp);
					});

					delete_MenuItem.setOnAction(del -> {
						EmployeesRightClick.deleteObject(emp.getNormalizedId());
					});
				}

				// Debug
				/*System.out.println(client.getfName());*/
			}

		});
	}
	public void initInitialPane() {
		firstViewedScene_text.setText(String.valueOf(App.dataListEmployees.size())); // change
		// this
		// to
		// the
		// number
		// of
		// cars
		// later ofc
		firstViewedScene_text2.setText("Total Employees");

		border.setCenter(this.firstViewedScene_stack);

	}
	// To disable/enable EMPLOYEE buttons
	public void disableEmployeeButtons(boolean status) {
		if (status) {
			addEmployee_btn.setDisable(true);
			displayEmployees_btn.setDisable(true);
			updateEmployees_btn.setDisable(true);
			deleteEmployee_btn.setDisable(true);
			goBack_btn.setDisable(true);

		} else {
			addEmployee_btn.setDisable(false);
			displayEmployees_btn.setDisable(false);
			updateEmployees_btn.setDisable(false);
			deleteEmployee_btn.setDisable(false);
			goBack_btn.setDisable(false);
		}
	}
	// Method to execute code when switching between the options in this scene
	@Override
	public void switchOption(Object obj, int option) {
		// TODO Auto-generated method stub
		if (obj.getClass() == EmployeeGui.class) {
			switch (option) {
				case 1 -> {
					this.firstViewedScene_stack.getChildren().addAll(this.firstViewedScene_rect, addEmployee_Grid);
				}
				case 2 -> {
					this.firstViewedScene_stack.getChildren().addAll(this.firstViewedScene_rect, displayTableContainer_vbox);
				}
				case 3 -> {
					this.firstViewedScene_stack.getChildren().addAll(this.firstViewedScene_rect, displayTableContainer_vbox);
				}
				case 4 -> {
					this.firstViewedScene_stack.getChildren().addAll(this.firstViewedScene_rect, displayTableContainer_vbox);
				}
			}

		}

	}
	public void resetBoxes() {
		empFName_Field.setText("");
		empLName_Field.setText("");
		empAddress_Field.setText("");
		empTelephone_Field.setText("");
		empSalary_Field.setText("");
		empEmail_Field.setText("");
		empDatePicker_Calendar.getEditor().clear();

		empGender_ComboBox = new CComboBox("Gender", "emp-gender-combo");
		empGender_ComboBox.getItems().addAll("Male", "Female");
		empPosition_ComboBox = new CComboBox(FXCollections.observableList(empPositions_List), "Position", "emp-position-combo");

		addEmployee_Grid.getChildren().clear();
		firstViewedScene_stack.getChildren().remove(errorText);

		addEmployee_Grid.add(empDatePicker_Calendar, 0, 0);
		addEmployee_Grid.add(empGender_ComboBox, 0, 1);
		addEmployee_Grid.add(empPosition_ComboBox, 0, 2);

		addEmployee_Grid.add(empFName_Field, 1, 0);
		addEmployee_Grid.add(empEmail_Field, 1, 1);
		addEmployee_Grid.add(empSalary_Field, 1, 2);
		addEmployee_Grid.add(empLName_Field, 2, 0);
		addEmployee_Grid.add(empTelephone_Field, 2, 1);
		addEmployee_Grid.add(empAddress_Field, 2, 2);
		addEmployee_Grid.add(empSave_btn, 1, 3);

	}
	// Duplicate error:
	public void displayDuplicateError() {
		addEmployee_Grid.add(errorText, 1, 4);
		// this.firstViewedScene_stack.getChildren().add(errorText);
		// errorText.setTranslateY(-Main.screenHeight/20);
	}

	// Search filter
	// Initialises all listeners in this scene
	public void initListeners() {

		// Simpler
		empFName_Field.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (empFName_Field.getLength() >= 20) {
				e.consume();
			}
		});

		empLName_Field.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (empLName_Field.getLength() >= 20) {
				e.consume();
			}
		});

		empEmail_Field.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (empEmail_Field.getLength() >= 200 || !"1234567890qwertyuioplkjhgfdsazxcvbnm@.-_".contains(e.getCharacter())) {
				e.consume();
			}
		});

		empTelephone_Field.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (empTelephone_Field.getLength() >= 15 || !"1234567890".contains(e.getCharacter())) {
				e.consume();
			}
		});

		empDatePicker_Calendar.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			e.consume(); // consume do8re we dont want him to enter anything
			// here from his keyboard
		});
		empDatePicker_Calendar.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			e.consume(); // consume do8re we dont want him to enter anything
			// here from his keyboard
		});

		empSalary_Field.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (!"1234567890".contains(e.getCharacter()) || empSalary_Field.getText().length() >= 8) {
				e.consume();
			}
		});
	}
	@SuppressWarnings("unchecked")
	@Override
	public void initDisplayTable() {

		id_col.setCellValueFactory(cellData -> cellData.getValue().getID());
		dateOfBirth_col.setCellValueFactory(cellData -> cellData.getValue().getDateOfBirth());
		firstName_col.setCellValueFactory(cellData -> cellData.getValue().getFname());
		lastName_col.setCellValueFactory(cellData -> cellData.getValue().getLname());
		gender_col.setCellValueFactory(cellData -> cellData.getValue().getGender());
		position_col.setCellValueFactory(cellData -> cellData.getValue().getPosition());
		salary_col.setCellValueFactory(cellData -> cellData.getValue().getSalary());
		address_col.setCellValueFactory(cellData -> cellData.getValue().getAddress());
		telephone_col.setCellValueFactory(cellData -> cellData.getValue().getTelephone());
		email_col.setCellValueFactory(cellData -> cellData.getValue().getEmail());

		displayEmployees_Table.setId("emp-display-table");

		// To Add Employees to observable list
		// Main.dataListEmployees.addAll(emp1);

		displayEmployees_Table.getColumns()
				.addAll(id_col, dateOfBirth_col, firstName_col, lastName_col, gender_col, position_col, salary_col, address_col, telephone_col, email_col);

		displayEmployees_Table.setMaxWidth(ScreenBounds.getScreenWidth() / 1.2);
		/* displayEmployeesTable.setMinHeight(Main.screenHeight / 1.705);*/
		displayEmployees_Table.setMinHeight(ScreenBounds.getScreenHeight() / 1.6);

		// Resizing is important especially when we have long addresses // is
		// that why email_col was on false? anyways i fixed it...
		id_col.setResizable(true);
		dateOfBirth_col.setResizable(true);
		firstName_col.setResizable(true);
		lastName_col.setResizable(true);
		gender_col.setResizable(true);
		address_col.setResizable(true);
		telephone_col.setResizable(true);
		email_col.setResizable(true);

		id_col.setPrefWidth(ScreenBounds.getScreenWidth() / 18.4);
		dateOfBirth_col.setPrefWidth(ScreenBounds.getScreenWidth() / 18.4);
		firstName_col.setPrefWidth(ScreenBounds.getScreenWidth() / 11);
		lastName_col.setPrefWidth(ScreenBounds.getScreenWidth() / 11);
		gender_col.setPrefWidth(ScreenBounds.getScreenWidth() / 11);
		position_col.setPrefWidth(ScreenBounds.getScreenWidth() / 11);
		salary_col.setPrefWidth(ScreenBounds.getScreenWidth() / 11);
		address_col.setPrefWidth(ScreenBounds.getScreenWidth() / 12.7);
		telephone_col.setPrefWidth(ScreenBounds.getScreenWidth() / 11);
		email_col.setPrefWidth(ScreenBounds.getScreenWidth() / 11);

		// Reordering doesnt break anything in code or mix things up its just
		// visual
		displayEmployees_Table.getColumns().forEach(e -> e.setReorderable(true));

		// displayEmployees_Table.setItems(Main.dataListEmployees); // no no we
		// dont need do it here because we adding the filter list then this
		// functions is removing the filtered list and adding this list instead
		// leading to a not working search filter

	}

	/*This method checks which boxes are empty and highlights them in the referred color
	When they aren't empty it returns them to normal color*/
	@Override
	public boolean checkEmptyBoxes() {
		boolean empty = false;

		if (isEmptyOrBlank(empFName_Field.getText())) {
			boxHighlighter(empFName_Field, true);
			empty = true;
		} else {
			boxHighlighter(empFName_Field, false);
		}
		if (isEmptyOrBlank(empLName_Field.getText())) {
			boxHighlighter(empLName_Field, true);
			empty = true;
		} else {
			boxHighlighter(empLName_Field, false);
		}

		if (isEmptyOrBlank(empTelephone_Field.getText())) {
			boxHighlighter(empTelephone_Field, true);
			empty = true;
		} else {
			boxHighlighter(empTelephone_Field, false);
		}
		if (isEmptyOrBlank(empSalary_Field.getText())) {
			boxHighlighter(empSalary_Field, true);
			empty = true;
		} else {
			boxHighlighter(empSalary_Field, false);
		}
		if (isEmptyOrBlank(empAddress_Field.getText())) {
			boxHighlighter(empAddress_Field, true);
			empty = true;
		} else {
			boxHighlighter(empAddress_Field, false);
		}

		// email tetxfield isnt being changed because the validation in Main
		// class in empSave_btn listener validates it
		if (isEmptyOrBlank(empEmail_Field.getText())) {
			empEmail_Field.setStyle("-fx-background-color:red;");
			empty = true;
		}

		if (empGender_ComboBox.getSelectionModel().isEmpty()) {
			empGender_ComboBox.setStyle("-fx-background-color:red;");
			empty = true;
		} else {
			empGender_ComboBox.setStyle("-fx-background-color:#0b2d39;");
		}

		if (empPosition_ComboBox.getSelectionModel().isEmpty()) {
			empPosition_ComboBox.setStyle("-fx-background-color:red;");
			empty = true;
		} else {
			empPosition_ComboBox.setStyle("-fx-background-color:#0b2d39;");
		}
		if (empDatePicker_Calendar.getValue() == null) { // If datePicker ae not
			// set,
			// datePicker.getValue() returns
			// null and using
			// datePicker.getValue().toString()
			// will throw
			// NullPointerException.
			empDatePicker_Calendar.setStyle("-fx-background-color:red;");
			empty = true;
		} else {
			empDatePicker_Calendar.setStyle("-fx-background-color:#0b2d39;");
		}
		return empty;
	}

}
