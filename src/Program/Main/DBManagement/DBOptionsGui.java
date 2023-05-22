package Program.Main.DBManagement;

import Program.Main.CustomClass.CButton;
import Program.Main.CustomClass.CText;
import Program.Main.CustomClass.CTextField;
import Program.Main.Utilities.FilePaths;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DBOptionsGui implements FilePaths {

	//public static final CheckBox rememberMe = new CheckBox("Remember me");
	private static final GridPane grid = new GridPane();
	// // The elements in the containers // //
	private static final BorderPane border = new BorderPane();
	// private static final CText url_example = new CText("Example:
	// \njdbc:mysql://(HOST):(PORT)/(DB_NAME)", "url-example");
	private static final CText url_text = new CText("URL Field: ", "url-text");
	private static final CText username_text = new CText("Username Field: ", "username-text");
	// // The main containers - END // //
	private static final CText password_text = new CText("Password Field: ", "password-text");
	private static final CText port_text = new CText("Port Field: ", "port-text");
	private static final CText dBNames_text = new CText("DB Name Field: ", "db-name-text");
	private static final CTextField url_field = new CTextField("Enter URL", "url-field");
	private static final CTextField username_field = new CTextField("Enter Username", "username-field");
	/*private static final CTextField password_field = new CTextField(
			"Enter Password", "password-field");*/
	private static final PasswordField password_field = new PasswordField();
	private static final CTextField port_field = new CTextField("Enter Port", "port-field");
	private static final CTextField dBNames_field = new CTextField("Enter DB Name", "db-name-field");
	private static final CButton save_btn = new CButton("Save", "save-button");
	// // The main containers // //
	public static Stage stageOptions = new Stage();
	private static Scene dBScene;

	// // The elements in the containers - END // //

	public static void initialiseCss() {
		// Gets current working directory of project (Used for exe case)

		dBScene.getStylesheets().add(dBOptionsCSS_ExePath);

	}

	public static void init() {

		initScene();
		initFilters();
		initialiseCss();

		stageOptions.setScene(dBScene);
		showStage();
	}

	public static void showStage() {

		stageOptions.showAndWait();

	}

	public static void initFilters() {
		save_btn.setOnAction(e -> {
			try {
				if (!isEmptyOrBlank(url_field.getText())) {
					InitConnection.setURL(url_field.getText());
				}
				if (!isEmptyOrBlank(dBNames_field.getText())) {
					InitConnection.setDbName(dBNames_field.getText());
				}
				if (!isEmptyOrBlank(username_field.getText())) {
					InitConnection.setUSERNAME(username_field.getText());
				}
				if (!isEmptyOrBlank(password_field.getText())) {
					InitConnection.setPASS(password_field.getText());
				}
				if (!isEmptyOrBlank(port_field.getText())) {

					InitConnection.setPORT(Integer.parseInt(port_field.getText()));
				}
				InitConnection.getConnection();
				url_field.setText("");
				dBNames_field.setText("");
				username_field.setText("");
				password_field.setText("");
				port_field.setText("");
			} catch (Exception ignored) {

			}
			stageOptions.close();
		});
	}

	public static boolean isEmptyOrBlank(String text) {

		try {
			if (text.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			/* An empty string is a string instance of zero length*/
		}
		try {
			if (text.isBlank()) {
				return true;
			}
		} catch (Exception e2) {
			/*If a string only consists of whitespace, then we call it blank.
			 * For Java, whitespaces are characters, like spaces, tabs, and so on*/
		}
		try {
			if (text == null) {
				return true;
			}
		} catch (Exception e3) {
			/*a null string has no value at all*/
		}

		return false;
	}

	private static void initScene() {

		grid.setId("grid");
		password_field.setId("password-field");
		password_field.setPromptText("Enter Password");

		// grid.add(url_example, 0, 0);
		grid.add(url_text, 0, 1);
		grid.add(username_text, 0, 2);
		grid.add(password_text, 0, 3);
		grid.add(port_text, 0, 4);
		grid.add(dBNames_text, 0, 5);

		grid.add(url_field, 1, 1);
		grid.add(username_field, 1, 2);
		grid.add(password_field, 1, 3);
		grid.add(port_field, 1, 4);
		grid.add(dBNames_field, 1, 5);

		//grid.add(rememberMe, 0, 6);
		grid.add(save_btn, 1, 6);

		grid.setHalignment(save_btn, HPos.RIGHT);
		//grid.setHalignment(rememberMe, HPos.CENTER);

		grid.setVgap(20);
		grid.setPadding(new Insets(20));

		border.setCenter(grid);

		dBScene = new Scene(border, 350, 350);
		stageOptions.setResizable(false);

		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.GHOSTWHITE);

		url_field.setEffect(dropShadow);
		username_field.setEffect(dropShadow);
		password_field.setEffect(dropShadow);
		port_field.setEffect(dropShadow);
		dBNames_field.setEffect(dropShadow);
	}

}
