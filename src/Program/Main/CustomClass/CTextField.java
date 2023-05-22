package Program.Main.CustomClass;

import Program.Main.Utilities.Fonts;
import javafx.scene.control.TextField;

public class CTextField extends TextField {

	public CTextField(String promptText, String id) {
		super();
		this.setId(id);
		this.setPromptText(promptText);
		this.setFont(Fonts.getRomanTimes());
	}

	public CTextField() {
		super();
		this.setFont(Fonts.getRomanTimes());
	}

}
