package Program.Main.Resources.Audio;

import Program.Main.CustomClass.CButton;
import Program.Main.CustomClass.CText;
import Program.Main.CustomClass.CTextField;
import Program.Main.Utilities.ConnectCSS;
import Program.Main.Utilities.FilePaths;
import Program.Main.Utilities.IsNull;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Objects;
public class BackgroundMusic implements IsNull, ConnectCSS, FilePaths {
	private final HBox path_HBox = new HBox();
	private final Scene path_Scene = new Scene(path_HBox, 490, 100);
	private final CText path_Text = new CText("Song path: ", "path-text");
	private final CTextField path_TextField = new CTextField("Insert Path", "path-textfield");
	private final CButton fileChooser_btn = new CButton("Browse", "file-chooser-btn");
	private String mediaPath = "bg_music.mp3";
	public Media media = new Media(Objects.requireNonNull(this.getClass().getResource(mediaPath)).toString());
	public MediaPlayer mediaPlayer = new MediaPlayer(media);

	public BackgroundMusic() {
		mediaPlayer.setVolume(0.05);
		mediaPlayer.setCycleCount(Timeline.INDEFINITE);
	}

	public void setMediaPath(CButton audio_btn) {

		Stage path_Stage = new Stage();

		initializeComponents();
		initialiseCss();

		fileChooser_btn.setOnAction(e -> {
			// this is the window that shows up and lets the user insert files
			// from his pc
			// Failed try at fixing when closing dialog program crashes
			FileChooser fileChooser = new FileChooser();
			File selectedFile = fileChooser.showOpenDialog(path_Stage);
			path_TextField.setText(selectedFile.getPath());

			if (!isEmptyOrBlank(path_TextField.getText())) {

				mediaPath = path_TextField.getText();
				mediaPlayer.stop();
				try {
					URL url = new File(mediaPath).toURI().toURL();
					mediaPath = url.toString();
					/*Media newMedia = new Media(mediaPath);
					MediaPlayer newMediaPlayer = new MediaPlayer(newMedia);*/

					// Test if this works
					media = new Media(mediaPath);
					mediaPlayer = new MediaPlayer(media);
				} catch (Exception ignored) {

				}
				playAndPause(audio_btn);

			}
			path_Stage.close();
		});

		path_Stage.setResizable(false);
		path_Stage.setScene(path_Scene);
		path_Stage.showAndWait();

	}

	private void initializeComponents() {
		try {
			path_HBox.getChildren().clear();
		} catch (Exception ignored) {

		}
		// drop shadow effect
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.GHOSTWHITE);

		path_Text.setEffect(dropShadow);
		path_TextField.setEffect(dropShadow);
		fileChooser_btn.setEffect(dropShadow);
		path_Text.setFill(Color.WHITE);
		path_TextField.setText(media.getSource());

		path_HBox.getChildren().addAll(path_Text, path_TextField, fileChooser_btn);

		path_HBox.setId("path-hbox");
		path_HBox.setSpacing(20);
		path_HBox.setPadding(new Insets(30));
	}

	private void initialiseCss() {
		// // CSS // //
		// initConnectCSS(path_Scene, bGMusicCSS_Path, false);
		initConnectCSS(path_Scene, bGMusicCSS_ExePath, false);
		// // CSS - END // //
	}

	public void playAndPause(Button audio_btn_player) {
		Status status = mediaPlayer.getStatus();
		if (status == Status.PLAYING) {
			mediaPlayer.pause();
			// status=status.STOPPED;
			try {
				audio_btn_player.setText("▶");
			} catch (Exception ignored) {

			}

		} else {
			mediaPlayer.play();
			try {
				audio_btn_player.setText("||");
			} catch (Exception ignored) {

			}

		}
	}

	public MediaPlayer getMediaPlayer() {
		return this.mediaPlayer;
	}
}
