package Program.Main.Guis.RightClick;

import Program.Main.CustomClass.CButton;
import Program.Main.CustomClass.CComboBox;
import Program.Main.CustomClass.CText;
import Program.Main.CustomClass.CTextField;
import Program.Main.Utilities.*;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class RightClickGui implements ConnectCSS, Email, IsNull, FilePaths, ScreenBounds {

	public static final Stage rightClickStage = new Stage();
	public static CText errorText = new CText("Email already exists!", "error-text");

	public static Scene addCar_Scene;
	public static Scene edit_Scene;
	public static Scene delete_Scene;
	public static Scene showClientCars_Scene;
	public static CTextField FName_Field = new CTextField("First Name", "first-name-textfield");
	public static CTextField LName_Field = new CTextField("Last Name", "last-name-textfield");
	public static CTextField Email_Field = new CTextField("Email", "email-textfield");
	public static CTextField Telephone_Field = new CTextField("Phone number", "telephone-textfield");
	public static CTextField Address_Field = new CTextField("Address", "address-textfield");
	public static CComboBox Gender_ComboBox = new CComboBox("Gender", "gender-combo");
	public static DatePicker DatePicker_Calendar = new DatePicker();
	public static CText FName_Text = new CText("First Name", "first-name-text");
	public static CText LName_Text = new CText("Last Name", "last-name-text");
	public static CText Email_Text = new CText("Email", "email-text");
	public static CText Telephone_Text = new CText("Phone number", "telephone-text");
	public static CText Address_Text = new CText("Address", "address-text");
	public static CText Gender_Text = new CText("Gender", "gender-text");
	public static CText DatePicker_Text = new CText("Date Of Birth", "date-of-birth-text");
	public static CText confirmation_text = new CText("confirmation-text");
	public static CButton yes_btn = new CButton("Yes");
	public static CButton no_btn = new CButton("No");
	public static CButton save_btn = new CButton("Save", "save-button");
	public static DropShadow dropShadow = new DropShadow();
	private static HBox hboxText = new HBox();
	private static HBox hboxConfirmation = new HBox();
	private static VBox vbox = new VBox();
	private static StackPane stack = new StackPane();
	public void initRightClick() {
		initDeleteScene();
		initFiltersAndEffects();
		initComboBox();
	}

	private void initComboBox() {
		Gender_ComboBox.getItems().addAll("Male", "Female");

		try {
			File position = new File(empPositions_ExePath);
			Scanner positionReader = new Scanner(position);
			while (positionReader.hasNext()) {
				EmployeesRightClickGui.empPositionsRightClick_List.add(positionReader.nextLine());
			}
		} catch (FileNotFoundException e) {
			try {
				File position = new File("TextFiles/positions.txt");
				Scanner positionReader = new Scanner(position);
				while (positionReader.hasNext()) {
					EmployeesRightClickGui.empPositionsRightClick_List.add(positionReader.nextLine());
				}
				positionReader.close();
			} catch (Exception ignored) {
			}

		}
		EmployeesRightClickGui.empPosition_ComboBox.getItems().addAll(EmployeesRightClickGui.empPositionsRightClick_List);
	}

	private void initDeleteScene() {
		confirmation_text.setFill(Color.BLACK);

		hboxText.getChildren().add(confirmation_text);
		hboxConfirmation.getChildren().addAll(yes_btn, no_btn);
		yes_btn.setId("yes-button");
		no_btn.setId("no-button");

		vbox.setId("delete-scene-vbox");

		hboxConfirmation.setAlignment(Pos.CENTER);

		hboxText.setMargin(confirmation_text, new Insets(10, 20, 10, 20));
		hboxConfirmation.setSpacing(20);

		vbox.getChildren().addAll(hboxText, hboxConfirmation);
		vbox.setSpacing(10);

		stack.getChildren().add(vbox);
		stack.setAlignment(Pos.CENTER);

		delete_Scene = new Scene(stack, 450, 100);
		rightClickStage.setResizable(false);

		//initConnectCSS(deleteScene, rightClickCSS_Path, false);
		initConnectCSS(delete_Scene, rightClickCSS_ExePath, false);
	}

	// // Filters // //
	private void initFiltersAndEffects() {

		FName_Field.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (FName_Field.getLength() >= 20) {
				e.consume();
			}
		});

		LName_Field.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (LName_Field.getLength() >= 20) {
				e.consume();
			}
		});

		Email_Field.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (Email_Field.getLength() >= 200 || !"1234567890qwertyuioplkjhgfdsazxcvbnm@.-_".contains(e.getCharacter())) {
				e.consume();
			}
		});

		Telephone_Field.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (Telephone_Field.getLength() >= 15 || !"1234567890".contains(e.getCharacter())) {
				e.consume();
			}
		});

		// consume do8re we dont want him to enter anything
		// here from his keyboard
		DatePicker_Calendar.addEventFilter(KeyEvent.KEY_PRESSED, Event::consume);
		// consume do8re we dont want him to enter anything
		// here from his keyboard
		DatePicker_Calendar.addEventFilter(KeyEvent.KEY_TYPED, Event::consume);

		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.GHOSTWHITE);

		FName_Field.setEffect(dropShadow);
		LName_Field.setEffect(dropShadow);
		Email_Field.setEffect(dropShadow);
		Telephone_Field.setEffect(dropShadow);

		Address_Field.setEffect(dropShadow);
		Gender_ComboBox.setEffect(dropShadow);

		DatePicker_Calendar.setEffect(dropShadow);

		DatePicker_Calendar.setId("date-of-birth-field");

		Gender_ComboBox.setPrefWidth(200);

		DatePicker_Calendar.setPrefWidth(200);
	}
	// // Filters - END // //

	// // launches the right click menu for cars // //

	// Deletes object from database
	public abstract void deleteObject(int ID);

	public void initCss() {
		//	initConnectCSS(editScene, rightClickCSS_Path, false);
		initConnectCSS(edit_Scene, rightClickCSS_ExePath, false);
	}

	public void initAddCarCSS() {
		initConnectCSS(addCar_Scene, carCSS_ExePath, true);
	}

	public void initViewCarsCSS() {
		initConnectCSS(showClientCars_Scene, carCSS_ExePath, true);
	}

}
