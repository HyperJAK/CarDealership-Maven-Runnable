package Program.Main.Guis.RightClick;
import Program.Main.CustomClass.CButton;
import Program.Main.Guis.CarsGui;
import Program.Main.Guis.Gui;
import Program.Main.App;
import Program.Main.Objects.CarsObject;
import Program.Main.Objects.ClientsObject;
import Program.Main.Utilities.ScreenBounds;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.util.ArrayList;
public class ClientsRightClickGui extends RightClickGui {
	public static ObservableList<CarsObject> dataListCarsOwned = FXCollections.observableArrayList();
	private static TableView<CarsObject> tempDisplayTable = new TableView<>();

	private static TableColumn<CarsObject, String> id_col = new TableColumn<>("ID");
	private static TableColumn<CarsObject, String> brand_col = new TableColumn<>("Brand");
	// // Left Bar Options Buttons - END // //
	private static TableColumn<CarsObject, String> model_col = new TableColumn<>("Model");
	private static TableColumn<CarsObject, String> color_col = new TableColumn<>("Color");
	private static TableColumn<CarsObject, String> category_col = new TableColumn<>("Category");
	private static TableColumn<CarsObject, String> nbDoors_col = new TableColumn<>("nb Doors");
	private static TableColumn<CarsObject, String> price_col = new TableColumn<>("Price");
	private static TableColumn<CarsObject, String> discount_col = new TableColumn<>("Discount");
	private static TableColumn<CarsObject, String> date_col = new TableColumn<>("Date Of Sale");
	private TableView<CarsObject> tempTable;
	private CButton Confirm_CButton = new CButton("Confirm", "confirm-button");
	private CButton goBack_CButton = new CButton("Go Back", "go-back-button");

	public ClientsRightClickGui() {

	}
	public static void initViewSearchFilter() {

		SortedList<CarsObject> sortedDataCars = new SortedList<>(dataListCarsOwned);

		sortedDataCars.comparatorProperty().bind(tempDisplayTable.comparatorProperty());

		tempDisplayTable.setItems(sortedDataCars);
	}
	@Override
	public void deleteObject(int ID) {
		rightClickStage.setScene(delete_Scene);
		confirmation_text.setText("Are you sure you want to delete this Client ?");
		rightClickStage.show();
		yes_btn.setOnAction(e -> {

			try {
				App.sql.deleteClient(ID);
				App.sql.refreshClients();
				rightClickStage.close();
			} catch (Exception ignored) {
				System.out.println("Error");
			}
		});

		no_btn.setOnAction(e -> {
			rightClickStage.close();
		});
	}
	public void initCarsOfClientTable(int clientID) {
		VBox vBox = new VBox();

		vBox.setId("vBox");

		initViewSearchFilter();

		id_col.setCellValueFactory(cellData -> cellData.getValue().getID());
		brand_col.setCellValueFactory(cellData -> cellData.getValue().getBrand());
		model_col.setCellValueFactory(cellData -> cellData.getValue().getModel());
		color_col.setCellValueFactory(cellData -> cellData.getValue().getColor());
		category_col.setCellValueFactory(cellData -> cellData.getValue().getCategory());
		nbDoors_col.setCellValueFactory(cellData -> cellData.getValue().getNbOfDoors());
		price_col.setCellValueFactory(cellData -> cellData.getValue().getPrice());
		discount_col.setCellValueFactory(cellData -> cellData.getValue().getDiscount());
		date_col.setCellValueFactory(cellData -> cellData.getValue().getDate());

		brand_col.setResizable(true);
		model_col.setResizable(true);
		color_col.setResizable(true);
		category_col.setResizable(true);
		nbDoors_col.setResizable(true);
		price_col.setResizable(true);
		discount_col.setResizable(true);
		date_col.setResizable(true);

		id_col.setPrefWidth(ScreenBounds.getScreenWidth() / 24);
		brand_col.setPrefWidth(ScreenBounds.getScreenWidth() / 14); // x6 of the main
		// width of the
		// whole table
		model_col.setPrefWidth(ScreenBounds.getScreenWidth() / 11);
		color_col.setPrefWidth(ScreenBounds.getScreenWidth() / 14);
		category_col.setPrefWidth(ScreenBounds.getScreenWidth() / 11.5);
		nbDoors_col.setPrefWidth(ScreenBounds.getScreenWidth() / 18);
		price_col.setPrefWidth(ScreenBounds.getScreenWidth() / 12);
		discount_col.setPrefWidth(ScreenBounds.getScreenWidth() / 20);
		date_col.setPrefWidth(ScreenBounds.getScreenWidth() / 14);

		// !(PREVENT REORDERING THE COLUMNS INSIDE THE TABLE)
		tempDisplayTable.getColumns().forEach(e -> e.setReorderable(true));

		tempDisplayTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		tempDisplayTable.setId("vehicle-display-table");

		tempDisplayTable.getColumns().addAll(id_col, brand_col, model_col, color_col, category_col, nbDoors_col, price_col, discount_col, date_col);

		tempDisplayTable.setMaxWidth(ScreenBounds.getScreenWidth() / 1.6);
		tempDisplayTable.setMinHeight(ScreenBounds.getScreenHeight() / 1.705);

		App.sql.refreshSpecificClientCars(clientID);

		vBox.getChildren().addAll(tempDisplayTable, goBack_CButton);
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(20);

		showClientCars_Scene = new Scene(vBox, 1000, 700);
		initViewCarsCSS();

		goBack_CButton.setOnAction(e -> {
			tempDisplayTable.getColumns().clear();
			rightClickStage.close();
		});

		rightClickStage.setResizable(true);
		rightClickStage.setScene(showClientCars_Scene);
		rightClickStage.show();
	}
	public void initAddCar(ClientsObject client) {

		VBox vBox = new VBox();
		HBox hBox = new HBox();

		vBox.setId("vBox");

		tempTable = CarsGui.displayVehicle_Table;
		tempTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		initSearchFilter();

		hBox.getChildren().addAll(Confirm_CButton, goBack_CButton);
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(10);

		vBox.getChildren().addAll(Gui.searchFieldAddCar2Client_Text, CarsGui.addCarToClient_Field, tempTable, hBox);
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);

		addCar_Scene = new Scene(vBox, 1000, 700);

		initAddCarCSS();

		Confirm_CButton.setOnAction(e -> {
			ArrayList<CarsObject> selectedCars = new ArrayList<CarsObject>();
			selectedCars.addAll(tempTable.getSelectionModel().getSelectedItems());

			for (int i = 0; i < selectedCars.size(); i++) {
				App.sql.addCarToClient(client, selectedCars.get(i));
				client.setCarsOwned(Integer.valueOf(client.getCarsOwned().get()) + 1);
				System.out.println(i);
			}
			rightClickStage.close();
		});

		goBack_CButton.setOnAction(e -> {
			rightClickStage.close();
		});

		rightClickStage.setResizable(true);
		rightClickStage.setScene(addCar_Scene);
		rightClickStage.show();

	}

	public void initSearchFilter() {

		ObservableList<CarsObject> OnlyAvailable = FXCollections.observableArrayList();

		for (int i = 0; i < App.dataListCars.size(); i++) {
			if (String.valueOf(App.dataListCars.get(i).getAvailability()).toLowerCase().contains("false"))
				OnlyAvailable.add(App.dataListCars.get(i));
		}
		FilteredList<CarsObject> filteredDataCars = new FilteredList<>(OnlyAvailable);

		Gui.addCarToClient_Field.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataCars.setPredicate(CarsObject -> {
				if (isEmptyOrBlank(newValue)) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				// Note // get() gets the string from simpleString that
				// getBrand() returns
				if (CarsObject.getBrand().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (CarsObject.getID().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (CarsObject.getCategory().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (CarsObject.getColor().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (CarsObject.getModel().get().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (String.valueOf(CarsObject.getNbOfDoors()).toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (String.valueOf(CarsObject.getPrice()).toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (String.valueOf(CarsObject.getDiscount()).toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else {
					return false;
				}
			});
		});

		SortedList<CarsObject> sortedDataCars = new SortedList<>(filteredDataCars);

		sortedDataCars.comparatorProperty().bind(tempTable.comparatorProperty());

		tempTable.setItems(sortedDataCars);
	}

	public void initClientEdit(ClientsObject clt) {

		GridPane edit_GridPane = new GridPane();

		FName_Field.setText(clt.getfName().get());
		LName_Field.setText(clt.getlName().get());
		Email_Field.setText(clt.getEmail().get());
		Telephone_Field.setText(clt.getUnformattedTelephone().get());
		Address_Field.setText(clt.getAddress().get());
		Gender_ComboBox.setValue(clt.getGender().get() == "M" ? "Female" : "Male");
		DatePicker_Calendar.setValue(clt.getDateOfBirthLocalDate());

		edit_GridPane.add(FName_Text, 0, 0);
		edit_GridPane.add(LName_Text, 0, 1);
		edit_GridPane.add(Email_Text, 0, 2);
		edit_GridPane.add(Telephone_Text, 0, 3);
		edit_GridPane.add(Address_Text, 0, 4);
		edit_GridPane.add(Gender_Text, 0, 5);
		edit_GridPane.add(DatePicker_Text, 0, 6);

		edit_GridPane.add(FName_Field, 1, 0);
		edit_GridPane.add(LName_Field, 1, 1);
		edit_GridPane.add(Email_Field, 1, 2);
		edit_GridPane.add(Telephone_Field, 1, 3);
		edit_GridPane.add(Address_Field, 1, 4);
		edit_GridPane.add(Gender_ComboBox, 1, 5);
		edit_GridPane.add(DatePicker_Calendar, 1, 6);

		edit_GridPane.add(save_btn, 1, 9);
		edit_GridPane.setAlignment(Pos.CENTER);
		edit_GridPane.setHgap(10);
		edit_GridPane.setVgap(10);
		edit_GridPane.setHalignment(save_btn, HPos.RIGHT);
		edit_GridPane.setId("grid");

		edit_Scene = new Scene(edit_GridPane, 400, 450);

		initCss();

		rightClickStage.setScene(edit_Scene);
		rightClickStage.setResizable(false);
		rightClickStage.show();
		rightClickStage.setAlwaysOnTop(true);

		// // click the save button stuff // //

		save_btn.setOnAction(e -> {
			try {
				// take his original
				// value, otherwise
				// take this values
				// in the text
				// fields
				App.sql.updateClient(clt.getNormalizedId(), isEmptyOrBlank(FName_Field.getText()) ? clt.getfName().get() : FName_Field.getText(), // is it empty? yes?
						isEmptyOrBlank(LName_Field.getText()) ? clt.getlName().get() : LName_Field.getText(), Gender_ComboBox.getValue().toString() == "Male" ? "M" : "F",
						isEmptyOrBlank(Address_Field.getText()) ? clt.getAddress().get() : Address_Field.getText(),
						isEmptyOrBlank(Telephone_Field.getText()) ? clt.getUnformattedTelephone().get() : Telephone_Field.getText(),
						(isEmptyOrBlank(Email_Field.getText())
								? clt.getEmail().get()
								: emailVerifier(Email_Field.getText()) ? Email_Field.getText() : clt.getEmail().get()), // same as
						Date.valueOf((DatePicker_Calendar.getValue().toString())), Integer.parseInt(clt.getCarsOwned().get()));
				// before but
				// now after
				// checking,
				// does it end
				// with
				// @gmail.com?
				// if yes
				// proceed if
				// not take emp
				// current email
				App.sql.refreshClients();
				rightClickStage.close();
			} catch (Exception ignored) { // as a wise man once said "we ignore
				// all our problems, until we can't
				// anymore"
				// this is one the cases you can't ignoted
				edit_GridPane.add(errorText, 0, 9);
			}
		});

	}
}
