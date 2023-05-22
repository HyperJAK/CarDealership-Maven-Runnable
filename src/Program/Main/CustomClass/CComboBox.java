package Program.Main.CustomClass;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class CComboBox extends ComboBox<Object> {

	@SuppressWarnings("unchecked")
	public CComboBox(ObservableList<?> obs, String promptText, String id) {
		super();
		this.setItems((ObservableList<Object>) obs);
		this.setPromptText(promptText);
		this.setId(id);

	}

	public CComboBox(String promptText, String id) {
		super();
		this.setPromptText(promptText);
		this.setId(id);

	}

	public CComboBox() {
		super();
	}
}
