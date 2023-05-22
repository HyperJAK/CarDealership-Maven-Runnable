package Program.Main.Guis;

import Program.Main.CustomClass.CButton;
import Program.Main.CustomClass.CComboBox;
import Program.Main.CustomClass.CTextField;
import Program.Main.Guis.RightClick.CarsRightClickGui;
import Program.Main.Guis.RightClick.RightClickGui;
import Program.Main.App;
import Program.Main.Objects.CarsObject;
import Program.Main.Utilities.ScreenBounds;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarsGui extends Gui {

	// // Table elements // //
	public static TableView<CarsObject> displayVehicle_Table = new TableView<>();
	// // ADD VEHICLE OPTION FIELDS // //
	///// Get these from text files in program start
	public static List<String> vehicleBrands_List = new ArrayList<>();
	// Ex: BMW / Ferrari
	public static List<String> vehicleModels_List = new ArrayList<>();
	///
	// Ex: 3 Series 2022
	public static List<String> vehicleColors_List = new ArrayList<>();
	// Ex: Coupe / pickup / sedan / sports
	// Ex: White like snow, yellow like sun, but overall the ride in it is fun
	public static List<String> vehicleCategories_List = new ArrayList<>();
	// Combo Boxes containing the observable lists
	public static CComboBox vehicleBrands_ComboBox = new CComboBox(FXCollections.observableList(vehicleBrands_List), "Vehicle Brand", "vehicle-manufacturers-combo");
	public static CComboBox vehicleModels_ComboBox = new CComboBox(FXCollections.observableList(vehicleModels_List), "Vehicle Models", "vehicle-models-combo");
	public static CComboBox vehicleColors_ComboBox = new CComboBox(FXCollections.observableList(vehicleColors_List), "Vehicle Colors", "vehicle-colors-combo");
	public static CComboBox vehicleCategories_ComboBox = new CComboBox(FXCollections.observableList(vehicleCategories_List), "Vehicle Categories",
			"vehicle-categories-combo");
	// PANES FOR ALL OPTIONS OF CARS
	public static CComboBox vehicleDoors_ComboBox = new CComboBox("Nb Doors", "vehicle-doors-combo");
	// // Table elements - END // //
	// ADD CAR OPTION
	public static HBox addCar_HBox = new HBox();
	public static CTextField vehiclePrice_Field = new CTextField("Price", "car-price-textfield");
	// Display Car Option
	public static CButton saveCar_btn = new CButton("Save Car", "save-car-button");
	private final TableColumn<CarsObject, String> id_col = new TableColumn<>("ID");
	private final TableColumn<CarsObject, String> brand_col = new TableColumn<>("Brand");
	// // Left Bar Options Buttons - END // //
	private final TableColumn<CarsObject, String> model_col = new TableColumn<>("Model");
	private final TableColumn<CarsObject, String> color_col = new TableColumn<>("Color");
	private final TableColumn<CarsObject, String> category_col = new TableColumn<>("Category");
	private final TableColumn<CarsObject, String> nbDoors_col = new TableColumn<>("nb Doors");
	private final TableColumn<CarsObject, String> price_col = new TableColumn<>("Price");
	private final TableColumn<CarsObject, String> discount_col = new TableColumn<>("Discount");
	private final TableColumn<CarsObject, String> availability_col = new TableColumn<>("Sold");

	// // Left Bar Options Buttons // //
	public CButton addCar_btn = new CButton("Add Cars", "add_car_button");
	public CButton displayCars_btn = new CButton("Display Cars", "display_cars_button");
	public CButton carOptions_btn = new CButton("Car Options", "car_options_button");
	public CButton removecars_btn = new CButton("Remove Car", "remove_car_button");
	public CButton goBack_btn = new CButton("Go Back", "go_back_button_car");
	// // Left Bar Options Buttons - END // //

	public CarsRightClickGui CarsRightClick = new CarsRightClickGui();

	//We reset each alone because each time we select different brand or categories its supposed to refresh
	public static void resetComboBoxes(String boxName) {
		switch (boxName) {
			case "brands" -> {
				vehicleBrands_ComboBox = new CComboBox(FXCollections.observableList(vehicleBrands_List), "Vehicle Brand", "vehicle-manufacturers-combo");

				addCar_HBox.getChildren().clear();
				addCar_HBox.getChildren()
						.addAll(vehicleBrands_ComboBox, vehicleDoors_ComboBox, vehicleCategories_ComboBox, vehicleModels_ComboBox, vehicleColors_ComboBox,
								vehiclePrice_Field, saveCar_btn);
			}
			case "models" -> {
				vehicleModels_ComboBox = new CComboBox(FXCollections.observableList(vehicleModels_List), "Vehicle Models", "vehicle-models-combo");

				addCar_HBox.getChildren().clear();
				addCar_HBox.getChildren()
						.addAll(vehicleBrands_ComboBox, vehicleDoors_ComboBox, vehicleCategories_ComboBox, vehicleModels_ComboBox, vehicleColors_ComboBox,
								vehiclePrice_Field, saveCar_btn);
			}
			case "categories" -> {
				vehicleCategories_ComboBox = new CComboBox(FXCollections.observableList(vehicleCategories_List), "Vehicle Categories", "vehicle-categories-combo");

				addCar_HBox.getChildren().clear();
				addCar_HBox.getChildren()
						.addAll(vehicleBrands_ComboBox, vehicleDoors_ComboBox, vehicleCategories_ComboBox, vehicleModels_ComboBox, vehicleColors_ComboBox,
								vehiclePrice_Field, saveCar_btn);
			}
		}

	}
	public static void initSearchFilter() {

		FilteredList<CarsObject> filteredDataCars = new FilteredList<>(App.dataListCars, b -> true);

		carsSearch_Field.textProperty().addListener((observable, oldValue, newValue) -> {
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
				} else if (String.valueOf(CarsObject.getAvailability()).toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else {
					return false;
				}

			});
		});

		SortedList<CarsObject> sortedDataCars = new SortedList<>(filteredDataCars);

		sortedDataCars.comparatorProperty().bind(displayVehicle_Table.comparatorProperty());

		displayVehicle_Table.setItems(sortedDataCars);
	}
	@Override
	public void initialiseCss() {

		// initConnectCSS(scene, carCSS_Path, true);

		initConnectCSS(scene, carCSS_ExePath, true);

	}
	@SuppressWarnings("static-access")
	@Override
	public void initializeComponents() {
		// initialises the combo boxes
		initSearchFilter();
		initVehicleInfo();

		initAddCarComponents();

		initDisplayTable();
		fillTableContainer();

		contextMenuListener();

		initListeners();

		sceneTitle.setText("Cars Management");

		hBorder.setAlignment(sceneTitle, Pos.CENTER);
		hBorder.setMargin(sceneTitle, new Insets(0, 0, 0, 30));
		hBorder.setMargin(currentProfile_btn, new Insets(10, 0, 10, -ScreenBounds.getScreenWidth() / 6.5));

		// Putting add cars options in the right position
		double classBox_YValue = (ScreenBounds.getScreenHeight() / 2) - (ScreenBounds.getScreenHeight() / 2.3);

		addCar_HBox.setTranslateY(classBox_YValue);
		addCar_HBox.setAlignment(Pos.TOP_CENTER);

		addCar_HBox.setSpacing(20);

		leftOptions_vbox.setAlignment(Pos.CENTER);

		initInitialPane();

		border.setTop(hBorder);
		border.setLeft(leftOptions_vbox);
		border.setCenter(this.firstViewedScene_stack);

		hBorder.setLeft(sceneTitle);
		hBorder.setCenter(currentProfile_btn);

		leftOptions_vbox.getChildren().addAll(addCar_btn, displayCars_btn, goBack_btn);

	}
	@Override
	public void fillTableContainer() {
		displayTableContainer_vbox.getChildren().addAll(searchField_Text, carsSearch_Field, displayVehicle_Table);
		displayTableContainer_vbox.setSpacing(10);
		displayTableContainer_vbox.setAlignment(Pos.CENTER);
		displayTableContainer_vbox.setTranslateY(-(ScreenBounds.getScreenHeight() / 20));
		displayTableContainer_vbox.setTranslateX(-(ScreenBounds.getScreenWidth() / 50));
	}
	@Override
	public void contextMenuListener() {

		displayVehicle_Table.setOnMouseClicked(e -> {

			// If mouse button clicked is right click
			if (e.getButton() == MouseButton.SECONDARY) {
				// Returns object of ClientsObject class
				CarsObject vehicle = displayVehicle_Table.getSelectionModel().getSelectedItem();

				ContextMenu context = new ContextMenu();
				MenuItem edit_MenuItem = new MenuItem("Edit Vehicle Info");
				MenuItem delete_MenuItem = new MenuItem("Delete Vehicle");

				if (displayVehicle_Table.getSelectionModel().getSelectedItem() != null) {
					displayVehicle_Table.setContextMenu(context);
					context.getItems().addAll(edit_MenuItem, delete_MenuItem);

					edit_MenuItem.setOnAction(edit -> {
						CarsRightClick.initCarEdit(vehicle);
					});

					delete_MenuItem.setOnAction(del -> {
						CarsRightClick.deleteObject(vehicle.getNormalizedId());
					});
				}

				// Debug
				/*System.out.println(client.getfName());*/
			}

		});

	}
	private void initVehicleInfo() {

		//
		try {

			File colors = new File(carColors_ExePath);

			Scanner colorsReader = new Scanner(colors);
			while (colorsReader.hasNext()) {
				vehicleColors_List.add(colorsReader.nextLine());
			}
			colorsReader.close();
		} catch (FileNotFoundException e) {
			try {
				File colors = new File("TextFiles/colors.txt");
				Scanner colorsReader = new Scanner(colors);
				while (colorsReader.hasNext()) {
					vehicleColors_List.add(colorsReader.nextLine());
				}
				colorsReader.close();
			} catch (Exception ignored) {

			}

		}

		// To add here models from database along with brands and categories
		App.sql.insertDefaultVehicleBrands();

	}
	public void initInitialPane() {

		firstViewedScene_text.setText(String.valueOf(App.dataListCars.size())); // change
		// this
		// to
		// the
		// number
		// of
		// cars
		// later ofc
		firstViewedScene_text2.setText("Total Cars");

	}
	@Override
	public void switchOption(Object obj, int option) {
		// TODO Auto-generated method stub
		if (obj.getClass() == CarsGui.class) {
			switch (option) {
				case 1 -> {
					this.firstViewedScene_stack.getChildren().addAll(firstViewedScene_rect, addCar_HBox);
					resetBoxes();
				}
				case 2 -> {
					this.firstViewedScene_stack.getChildren().addAll(firstViewedScene_rect, displayTableContainer_vbox);
					resetBoxes();
				}
				case 3 -> {
					this.firstViewedScene_stack.getChildren().addAll(firstViewedScene_rect, displayTableContainer_vbox);
				}
				case 4 -> {
					this.firstViewedScene_stack.getChildren().addAll(firstViewedScene_rect, displayTableContainer_vbox);
				}
			}

		}

	}
	public void initAddCarComponents() {

		vehicleDoors_ComboBox.getItems().add("2");
		vehicleDoors_ComboBox.getItems().add("4");
		vehicleDoors_ComboBox.setDisable(true);

		// vehicleManufacturers_List.add("BMW");// Example (delete)
		// vehicleManufacturers_List.add("Ferrari");// Example (delete)

		addCar_HBox.getChildren()
				.addAll(vehicleBrands_ComboBox, vehicleDoors_ComboBox, vehicleModels_ComboBox, vehicleColors_ComboBox, vehicleCategories_ComboBox, vehiclePrice_Field,
						saveCar_btn);

		vehicleModels_ComboBox.setDisable(true);
		vehicleCategories_ComboBox.setDisable(true);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void initDisplayTable() {

		id_col.setCellValueFactory(cellData -> cellData.getValue().getID());
		brand_col.setCellValueFactory(cellData -> cellData.getValue().getBrand());
		model_col.setCellValueFactory(cellData -> cellData.getValue().getModel());
		color_col.setCellValueFactory(cellData -> cellData.getValue().getColor());
		category_col.setCellValueFactory(cellData -> cellData.getValue().getCategory());
		nbDoors_col.setCellValueFactory(cellData -> cellData.getValue().getNbOfDoors());
		price_col.setCellValueFactory(cellData -> cellData.getValue().getPrice());
		discount_col.setCellValueFactory(cellData -> cellData.getValue().getDiscount());
		availability_col.setCellValueFactory(cellData -> cellData.getValue().getAvailability());

		displayVehicle_Table.setId("vehicle-display-table");

		displayVehicle_Table.getColumns().addAll(id_col, brand_col, model_col, color_col, category_col, nbDoors_col, price_col, discount_col, availability_col);

		displayVehicle_Table.setMaxWidth(ScreenBounds.getScreenWidth() / 1.6);
		displayVehicle_Table.setMinHeight(ScreenBounds.getScreenHeight() / 1.705);

		brand_col.setResizable(true);
		model_col.setResizable(true);
		color_col.setResizable(true);
		category_col.setResizable(true);
		nbDoors_col.setResizable(true);
		price_col.setResizable(true);
		availability_col.setResizable(true);
		discount_col.setResizable(true);

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
		availability_col.setPrefWidth(ScreenBounds.getScreenWidth() / 14);

		// !(PREVENT REORDERING THE COLUMNS INSIDE THE TABLE)
		displayVehicle_Table.getColumns().forEach(e -> e.setReorderable(true));

	}
	// To disable/enable CARS buttons
	public void disableCarButtons(boolean status) {
		if (status) {
			addCar_btn.setDisable(true);
			carOptions_btn.setDisable(true);
			displayCars_btn.setDisable(true);
			removecars_btn.setDisable(true);
			goBack_btn.setDisable(true);

		} else {
			addCar_btn.setDisable(false);
			carOptions_btn.setDisable(false);
			displayCars_btn.setDisable(false);
			removecars_btn.setDisable(false);
			goBack_btn.setDisable(false);

		}
	}
	@Override
	public void resetBoxes() {
		vehiclePrice_Field.setText("");

		vehicleCategories_ComboBox = new CComboBox(FXCollections.observableList(vehicleCategories_List), "Vehicle Categories", "vehicle-categories-combo");

		vehicleModels_ComboBox = new CComboBox(FXCollections.observableList(vehicleModels_List), "Vehicle Models", "vehicle-models-combo");

		vehicleBrands_ComboBox = new CComboBox(FXCollections.observableList(vehicleBrands_List), "Vehicle Brand", "vehicle-manufacturers-combo");

		vehicleDoors_ComboBox = new CComboBox("Nb Doors", "vehicle-doors-combo");

		vehicleColors_ComboBox = new CComboBox(FXCollections.observableList(vehicleColors_List), "Vehicle Colors", "vehicle-colors-combo");

		displayTableContainer_vbox.getChildren().clear();
		displayTableContainer_vbox.getChildren().addAll(searchField_Text, carsSearch_Field, displayVehicle_Table);
		displayVehicle_Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		vehicleDoors_ComboBox.getItems().add("2");
		vehicleDoors_ComboBox.getItems().add("4");
		vehicleDoors_ComboBox.setDisable(true);

		addCar_HBox.getChildren().clear();
		addCar_HBox.getChildren()
				.addAll(vehicleBrands_ComboBox, vehicleDoors_ComboBox, vehicleCategories_ComboBox, vehicleModels_ComboBox, vehicleColors_ComboBox, vehiclePrice_Field,
						saveCar_btn);
		initListeners();
	}

	// Search filter
	@Override
	public void initListeners() {

		// Based on this the other combo boxes display something specific to

		vehicleDoors_ComboBox.setOnAction(e2 -> {
			if (vehicleBrands_ComboBox.getSelectionModel().getSelectedItem() != null) {
				try {
					String brand = vehicleBrands_ComboBox.getValue().toString();
					int nbdoors = Integer.parseInt(vehicleDoors_ComboBox.getValue().toString());

					// Based on brand and nb of doors it updates category
					App.sql.updateCategoryWithBrandAndDoors(brand, nbdoors);
				} catch (Exception exp) {
					System.out.println("Exception in refresh2");
				}
			}

			if (vehicleBrands_ComboBox.getSelectionModel().getSelectedItem() != null && vehicleDoors_ComboBox.getSelectionModel().getSelectedItem() != null) {
				try {
					String brand = vehicleBrands_ComboBox.getValue().toString();
					int nbdoors = Integer.parseInt(vehicleDoors_ComboBox.getValue().toString());

					App.sql.updateVehicleModelInfo(brand, nbdoors);
				} catch (Exception exp) {
					System.out.println("Exception in refresh2");
				}
			}

		});

		vehicleBrands_ComboBox.setOnAction(e -> {
			vehicleDoors_ComboBox.setDisable(false);
			if (vehicleDoors_ComboBox.getSelectionModel().getSelectedItem() != null) {
				String brand = vehicleBrands_ComboBox.getValue().toString();
				int nbdoors = Integer.parseInt(vehicleDoors_ComboBox.getValue().toString());

				App.sql.updateVehicleModelInfo(brand, nbdoors);
			}
		});

		vehicleCategories_ComboBox.setOnAction(e3 -> {
			if (vehicleBrands_ComboBox.getSelectionModel().getSelectedItem() != null && vehicleDoors_ComboBox.getSelectionModel().getSelectedItem() != null) {

				String brand = vehicleBrands_ComboBox.getValue().toString();
				int nbdoors = Integer.parseInt(vehicleDoors_ComboBox.getValue().toString());

				App.sql.updateVehicleModelInfo(brand, nbdoors);

			}

		});

		// Price filter
		vehiclePrice_Field.addEventFilter(KeyEvent.KEY_TYPED, e4 -> {
			if (!"1234567890".contains(e4.getCharacter())) {
				e4.consume();
			}
			if (vehiclePrice_Field.getLength() >= 10) { // we got
				// remember
				// to put
				// limiters
				// so the
				// database
				// doesn't
				// explode
				e4.consume();
			}
		});

		RightClickGui.rightClickStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				initSearchFilter();
			}
		});
	}
	@Override
	public boolean checkEmptyBoxes() {

		boolean empty = false;

		if (vehicleBrands_ComboBox.getSelectionModel().isEmpty()) {
			boxHighlighter(vehicleBrands_ComboBox, true);
			empty = true;
		} else {
			boxHighlighter(vehicleBrands_ComboBox, false);
		}
		if (vehicleModels_ComboBox.getSelectionModel().isEmpty()) {
			boxHighlighter(vehicleModels_ComboBox, true);
			empty = true;
		} else {
			boxHighlighter(vehicleModels_ComboBox, false);
		}

		if (vehicleColors_ComboBox.getSelectionModel().isEmpty()) {
			boxHighlighter(vehicleColors_ComboBox, true);
			empty = true;
		} else {
			boxHighlighter(vehicleColors_ComboBox, false);
		}
		if (vehicleCategories_ComboBox.getSelectionModel().isEmpty()) {
			boxHighlighter(vehicleCategories_ComboBox, true);
			empty = true;
		} else {
			boxHighlighter(vehicleCategories_ComboBox, false);
		}
		if (vehicleDoors_ComboBox.getSelectionModel().isEmpty()) {
			boxHighlighter(vehicleDoors_ComboBox, true);
			empty = true;
		} else {
			boxHighlighter(vehicleDoors_ComboBox, false);
		}
		if (isEmptyOrBlank(vehiclePrice_Field.getText())) {
			boxHighlighter(vehiclePrice_Field, true);
			empty = true;
		} else {
			boxHighlighter(vehiclePrice_Field, false);
		}
		return empty;

	}

}
