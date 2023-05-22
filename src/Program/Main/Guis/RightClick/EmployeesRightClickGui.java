package Program.Main.Guis.RightClick;
import Program.Main.CustomClass.CComboBox;
import Program.Main.CustomClass.CText;
import Program.Main.CustomClass.CTextField;
import Program.Main.App;
import Program.Main.Objects.EmployeesObject;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
public class EmployeesRightClickGui extends RightClickGui {
	public static CText empSalary_Text = new CText("Salary", "salary-text");
	public static CText empPosition_Text = new CText("Position", "position-text");

	public static CComboBox empPosition_ComboBox = new CComboBox("Position", "position-combo"); // same here

	public static CTextField empSalary_Field = new CTextField("Salary", "salary-textfield"); // here i kept emp cuze its only for emp

	public static List<String> empPositionsRightClick_List = new ArrayList<>();

	public EmployeesRightClickGui() {
		initRightClick();
	}

	@Override
	public void deleteObject(int ID) {
		rightClickStage.setScene(delete_Scene);
		confirmation_text.setText("Are you sure you want to delete this Employee ?");
		rightClickStage.show();
		yes_btn.setOnAction(e -> {

			try {
				App.sql.deleteEmployee(ID);
				App.sql.refreshEmployees();
				rightClickStage.close();
			} catch (Exception ignored) {

			}
		});

		no_btn.setOnAction(e -> {
			rightClickStage.close();
		});
	}

	public void initEmployeeEdit(EmployeesObject emp) {

		GridPane edit_GridPane = new GridPane();

		FName_Field.setText(emp.getFname().get());
		LName_Field.setText(emp.getLname().get());
		Email_Field.setText(emp.getEmail().get());
		Telephone_Field.setText(emp.getUnformattedTelephone().get());
		empSalary_Field.setText(emp.getUnformattedSalary().get());
		empSalary_Field.setEffect(dropShadow);
		Address_Field.setText(emp.getAddress().get());
		Gender_ComboBox.setValue(emp.getGender().get() == "M" ? "Female" : "Male");
		empPosition_ComboBox.setValue(emp.getPosition().get());
		empPosition_ComboBox.setEffect(dropShadow);
		empPosition_ComboBox.setPrefWidth(200);
		DatePicker_Calendar.setValue(emp.getDateOfBirthLocalDate());

		edit_GridPane.add(FName_Text, 0, 0);
		edit_GridPane.add(LName_Text, 0, 1);
		edit_GridPane.add(Email_Text, 0, 2);
		edit_GridPane.add(Telephone_Text, 0, 3);
		edit_GridPane.add(empSalary_Text, 0, 4);
		edit_GridPane.add(Address_Text, 0, 5);
		edit_GridPane.add(Gender_Text, 0, 6);
		edit_GridPane.add(empPosition_Text, 0, 7);
		edit_GridPane.add(DatePicker_Text, 0, 8);

		edit_GridPane.add(FName_Field, 1, 0);
		edit_GridPane.add(LName_Field, 1, 1);
		edit_GridPane.add(Email_Field, 1, 2);
		edit_GridPane.add(Telephone_Field, 1, 3);
		edit_GridPane.add(empSalary_Field, 1, 4);
		edit_GridPane.add(Address_Field, 1, 5);
		edit_GridPane.add(Gender_ComboBox, 1, 6);
		edit_GridPane.add(empPosition_ComboBox, 1, 7);
		edit_GridPane.add(DatePicker_Calendar, 1, 8);

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
				App.sql.updateEmployee(emp.getNormalizedId(), isEmptyOrBlank(FName_Field.getText()) ? emp.getFname().get() : FName_Field.getText(), // is it empty? yes?
						isEmptyOrBlank(LName_Field.getText()) ? emp.getLname().get() : LName_Field.getText(), Gender_ComboBox.getValue().toString() == "Male" ? "M" : "F",
						empPosition_ComboBox.getValue().toString(),
						isEmptyOrBlank(empSalary_Field.getText()) ? Float.valueOf(emp.getUnformattedSalary().get()) : Float.valueOf(empSalary_Field.getText().toString()),
						isEmptyOrBlank(Address_Field.getText()) ? emp.getAddress().get() : Address_Field.getText(),
						isEmptyOrBlank(Telephone_Field.getText()) ? emp.getUnformattedTelephone().get() : Telephone_Field.getText(),
						(isEmptyOrBlank(Email_Field.getText())
								? emp.getEmail().get()
								: emailVerifier(Email_Field.getText()) ? Email_Field.getText() : emp.getEmail().get()), // same as
						Date.valueOf((DatePicker_Calendar.getValue().toString())));
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
				App.sql.refreshEmployees();
				rightClickStage.close();
			} catch (Exception ignored) { // as a wise man once said "we ignore
				// all our problems, until we can't
				// anymore"
				// this is one the cases you can't ignoted
				edit_GridPane.add(errorText, 0, 9);
			}
		});

		empSalary_Field.addEventFilter(KeyEvent.KEY_TYPED, e -> {
			if (!"1234567890".contains(e.getCharacter()) || empSalary_Field.getText().length() >= 8) {
				e.consume();
			}
		});

	}
}
