package Program.Main.Utilities;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public interface Fonts {

	// Add more fonts later

	Font RomanTimesFont = Font.font("Times Roman", // font name
			FontWeight.BOLD, // font weight
			FontPosture.ITALIC, // font posture
			14 // font size
			// always follow this order for the code to work
	);

	Font RomanTimesFont10 = Font.font("Times Roman", FontWeight.MEDIUM, FontPosture.REGULAR, 20);

	static Font getRomanTimes() {
		return RomanTimesFont;
	}

	static Font getRomanTimes_20() {
		return RomanTimesFont10;
	}

}
