package Program.Main.Guis;

import Program.Main.CustomClass.CButton;
import Program.Main.CustomClass.CText;
import Program.Main.CustomClass.CTextField;
import Program.Main.Utilities.ConnectCSS;
import Program.Main.Utilities.FilePaths;
import Program.Main.Utilities.ScreenBounds;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public abstract class Gui implements ScreenBounds, ConnectCSS, FilePaths {
	public static CTextField carsSearch_Field = new CTextField();
	public static CTextField employeesSearch_Field = new CTextField();
	public static CTextField clientsSearch_Field = new CTextField();
	public static CTextField addCarToClient_Field = new CTextField();
	public static Text searchField_Text = new Text("Search Box : ");
	public static Text searchFieldAddCar2Client_Text = new Text("Search Box : ");

	// Error Text
	public CText errorText = new CText("Email already exist!", "error-text");
	// borderpane containing all needed elements
	public BorderPane border = new BorderPane();
	// vertical box for options on left side
	public VBox leftOptions_vbox = new VBox();
	// // Texts displaying number of entities in database - END // //
	// center Panes
	public StackPane firstViewedScene_stack = new StackPane();
	// // Whitish background of scenes -END // //
	// // Texts displaying number of entities in database // //
	public Text firstViewedScene_text = new Text();
	public Text firstViewedScene_text2 = new Text();
	// // Whitish background of scenes // //
	public Rectangle firstViewedScene_rect = new Rectangle(0, 0, ScreenBounds.getScreenWidth() / 1.1, ScreenBounds.getScreenHeight() / 1.2);
	// // Display Table search feature // //
	public VBox displayTableContainer_vbox = new VBox();
	// // Display Table search feature - END // //
	// // Top bar in the scenes // //
	public BorderPane hBorder = new BorderPane();
	public Label sceneTitle = new Label();
	public CButton currentProfile_btn = new CButton("profilePic_button", true);

	// // Top bar in the scenes - END // //

	// // Scene of guis that is returned to main stage // //
	public Scene scene = new Scene(border, ScreenBounds.getScreenWidth(), ScreenBounds.getScreenHeight());
	private double mousePos_x = 0;
	private double mousePos_y = 0;
	// // Scene of guis that is returned to main stage - END// //

	// // Abstract Methods // //
	// This method combines checking if a string is: empty / blank / null
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
	public abstract void initialiseCss();
	public abstract void initDisplayTable();
	public abstract void initInitialPane();
	public abstract void switchOption(Object obj, int option);
	public abstract void resetBoxes();
	public abstract void initListeners();
	public abstract boolean checkEmptyBoxes();
	public abstract void initializeComponents();
	// This adds all table elements to a vbox container
	public abstract void fillTableContainer();
	// // Abstract Methods - END // //

	// // Declared Methods // //
	// This initiates the listener that handles right click of mouse on any
	// table element
	public abstract void contextMenuListener();
	public void init() {
		initialiseCss();
		initializeComponents();
		// These are here so that we don't have to include them in each class
		leftOptions_vbox.setId("optionsLeft_box");
		hBorder.setId("topBar_box");
		sceneTitle.setId("sceneTitleID");
		firstViewedScene_stack.setId("centerBorder_STACK");

		firstViewedScene_rect.setFill(Color.WHITE);
		firstViewedScene_rect.setId("firstViewedScene-rec-id");
		Font fontBottom = Font.font("Times Roman", // font name
				FontWeight.BOLD, // font weight
				FontPosture.ITALIC, // font posture
				40 // font size
		);
		Font fontTop = Font.font("Times Roman", // font name
				FontWeight.BOLD, // font weight
				FontPosture.ITALIC, // font posture
				150 // font size
		);
		firstViewedScene_text.setFont(fontTop);
		firstViewedScene_text2.setFont(fontBottom);

		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.GHOSTWHITE);

		firstViewedScene_rect.setEffect(dropShadow);

		firstViewedScene_text.setTranslateY(-(ScreenBounds.getScreenHeight() / 6));

		carsSearch_Field.setMaxWidth(250);
		employeesSearch_Field.setMaxWidth(250);
		clientsSearch_Field.setMaxWidth(250);
		addCarToClient_Field.setMaxWidth(250);
		// use to track mouse position
		// mousePosListener();
	}
	// This records mouse position in the window for later use when displaying
	// right clicked box
	private void mousePosListener() {
		scene.setOnMouseMoved(e2 -> {
			mousePos_x = e2.getX();
			mousePos_y = e2.getY();

			// for debugging
			/*System.out.println(mousePos_x);
			System.out.println(mousePos_y);*/
		});
	}
	/*This method takes in the textfield to highlight and if needs to highlight it
	to the warning color or back to normal*/
	public void boxHighlighter(TextField text, boolean highlight) {
		String colorHighlight = "red";
		String colorDefault = "#0b2d39";

		if (highlight) {
			text.setStyle("-fx-background-color:" + colorHighlight + ";");
		} else {
			text.setStyle("-fx-background-color:" + colorDefault + ";");
		}
	}

	public void boxHighlighter(ComboBox<?> combo, boolean highlight) {
		String colorHighlight = "red";
		String colorDefault = "#0b2d39";

		if (highlight) {
			combo.setStyle("-fx-background-color:" + colorHighlight + ";");
		} else {
			combo.setStyle("-fx-background-color:" + colorDefault + ";");
		}
	}

	// This is used to clear the center stack whenever we click a button so that
	// the stack can change children in switchOption function
	public void clearCenterDisplay_Options() {
		firstViewedScene_stack.getChildren().clear();

	}

	public void fillCenterDisplay_Options() {

		firstViewedScene_stack.getChildren().addAll(firstViewedScene_rect, firstViewedScene_text, firstViewedScene_text2);

	}

	public Scene getGuiScene() {
		// scene.
		return scene;
	}

	// // Declared Methods - END // //

}
