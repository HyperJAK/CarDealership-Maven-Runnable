package Program.Main.Guis;

import Program.Main.CustomClass.CButton;
import Program.Main.App;
import Program.Main.Utilities.ConnectCSS;
import Program.Main.Utilities.FilePaths;
import Program.Main.Utilities.ScreenBounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainGui implements ScreenBounds, ConnectCSS, FilePaths {

	public static Slider audioVolume_Slider = new Slider();
	public static CButton audio_btn = new CButton("▶", "audio_button_main");
	public BorderPane border = new BorderPane();
	public StackPane stack = new StackPane();
	public Scene scene = new Scene(border);
	public VBox vBox = new VBox();
	// 3 main page buttons that lead to different Gui's / scenes
	public CButton carsGui_btn = new CButton("Cars Management", "cars_button_main");
	public CButton clientGui_btn = new CButton("Client Options", "clients_button_main");
	public CButton employeeGui_btn = new CButton("Employee Options", "employees_button_main");
	// Round profile pic button
	public CButton currentProfile_btn = new CButton("profilePic_button_main", true);
	// // AUDIO // //
	public HBox media_HBox1 = new HBox();
	public HBox media_HBox2 = new HBox();
	public VBox media_VBox = new VBox();
	public CButton path_btn = new CButton("⚙", "path_button_main");

	/*public Media note = new Media(this.getClass().getResource("bg_music.mp3").toString());
	public MediaPlayer media = new MediaPlayer(note);*/
	// // AUDIO - END // //
	public void initialiseCss() {
		// initConnectCSS(scene, mainGuiCSS_Path, false);
		initConnectCSS(scene, mainGuiCSS_ExePath, true);

	}

	@SuppressWarnings("static-access")
	public void initializeComponents() {

		initListeners();
		initBackgroundAudio();

		// Set ID for Main menu border pane
		border.setId("mainMenuBorderPane");

		// Profile btn goes up
		currentProfile_btn.setTranslateY(0);

		// styling the vertical box that contains buttons
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(20);

		// adding buttons to a vertical box and addint that to the stack pane

		vBox.getChildren().addAll(carsGui_btn, employeeGui_btn, clientGui_btn);
		stack.getChildren().addAll(vBox);

		// Fixing alignment of profil pic

		border.setTop(currentProfile_btn);
		BorderPane.setAlignment(currentProfile_btn, Pos.CENTER);
		currentProfile_btn.setTranslateY(ScreenBounds.getScreenHeight() / 10);

		// stack pane max width and height
		stack.setMaxWidth(ScreenBounds.getScreenWidth() / 4);
		stack.setMaxHeight(ScreenBounds.getScreenHeight());

		// Fixing alignment of Buttons

		stack.setTranslateX(-(ScreenBounds.getScreenWidth() / 3.65));
		stack.setTranslateY(-(ScreenBounds.getScreenHeight() / 7));

		border.setCenter(stack);

		border.setAlignment(stack, Pos.CENTER);

	}

	private void initBackgroundAudio() {
		// AUDIO BUTTON //
		media_HBox2.setId("media-hbox2");

		audioVolume_Slider.setOrientation(Orientation.HORIZONTAL);
		audioVolume_Slider.setShowTickLabels(false);
		audioVolume_Slider.setShowTickMarks(false);
		audioVolume_Slider.setValue(0.05);

		media_HBox1.getChildren().addAll(path_btn, audio_btn);
		media_HBox1.setAlignment(Pos.BASELINE_RIGHT);
		media_HBox1.setSpacing(25);
		media_HBox1.setPadding(new Insets(-10, 0, 0, 0));

		media_HBox2.getChildren().add(audioVolume_Slider);
		media_HBox2.setAlignment(Pos.CENTER);
		media_HBox2.setMaxWidth(ScreenBounds.getScreenWidth() / 19);

		media_VBox.getChildren().addAll(media_HBox2, media_HBox1);
		media_VBox.setMargin(media_HBox2, new Insets(0, ScreenBounds.getScreenWidth() / 101, 0, 0));

		audioVolume_Slider.setMaxWidth(ScreenBounds.getScreenWidth() / 20);
		media_VBox.setAlignment(Pos.BASELINE_RIGHT);
		border.setBottom(media_VBox);
		border.setAlignment(media_VBox, Pos.BASELINE_RIGHT);
		audio_btn.setTranslateX(-ScreenBounds.getScreenWidth() / 100);

	}

	// To disable/enable MAIN buttons
	public void disableMainButtons(boolean status) {
		if (status) {
			carsGui_btn.setDisable(true);
			clientGui_btn.setDisable(true);
			employeeGui_btn.setDisable(true);
		} else {
			carsGui_btn.setDisable(false);
			clientGui_btn.setDisable(false);
			employeeGui_btn.setDisable(false);
		}
	}

	public void init() {
		initialiseCss();
		initializeComponents();
	}

	private void initListeners() {
		// // // // // AUDIO BUTTON !!!!!!!!!! // // // // //
		audio_btn.setOnMouseClicked(e -> {
			App.bgMusic.playAndPause(audio_btn);
		});

		path_btn.setOnMouseClicked(e -> {
			App.bgMusic.setMediaPath(audio_btn);
		});

		audioVolume_Slider.valueProperty().addListener(ov -> {
			App.bgMusic.getMediaPlayer().setVolume(audioVolume_Slider.getValue() / 100);
		});

		// // // // // AUDIO BUTTON !!!!!!!!!! - END// // // // //
	}

	public Scene getGuiScene() {
		return scene;
	}

}
