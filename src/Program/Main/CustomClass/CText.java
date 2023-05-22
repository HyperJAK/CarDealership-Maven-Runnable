package Program.Main.CustomClass;

import Program.Main.Utilities.Fonts;
import javafx.scene.text.Text;

public class CText extends Text {

	public CText(String text, String id) {
		super();
		this.setText(text);
		this.setId(id);
		init();
	}

	public CText(String id) {
		super();
		this.setId(id);
		init();
	}

	public CText() {
		super();
		init();
	}

	private void init() {
		this.setFont(Fonts.getRomanTimes_20());
	}
}
