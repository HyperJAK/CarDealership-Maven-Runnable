package Program.Main.CustomClass;

import Program.Main.Utilities.FilePaths;
import Program.Main.Utilities.Fonts;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CButton extends Button implements FilePaths {

	// public CustomButton(String id) {
	// super();
	// this.setId(id);
	// init();
	// }

	public CButton(String text, String id) {
		super();
		this.setId(id);
		this.setText(text);
		init();
	}

	public CButton(String text) {
		super();
		this.setText(text);
		init();
	}

	public CButton() {
		super();
		init();
	}

	@SuppressWarnings("unused")
	public CButton(String id, boolean round) {
		if (round) {
			// to make it round / style it
			double r = 20;
			this.setShape(new Circle(r));
			this.setMinSize(2 * r, 2 * r);
			this.setMaxSize(2 * r, 2 * r);
			this.setId(id);

			// Gets the file from system drive
			InputStream stream = null;
			try {
				stream = new FileInputStream(profilePic_ExePath);

				assert stream != null;
				Image image = new Image(stream);

				BackgroundImage newGameBgr = new BackgroundImage(image, null, null, null, null);

				this.setBackground(new Background(newGameBgr));

			} catch (FileNotFoundException e) {

			}
			// URL url new URL("");

		}
	}

	private void init() {
		this.setAlignment(Pos.CENTER);
		this.setTextFill(Color.WHITE);
		this.setFont(Fonts.getRomanTimes());
		this.setWrapText(true);
	}
}
