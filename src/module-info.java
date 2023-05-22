module CarDealerShip {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires java.sql;
	requires javafx.media;

	opens Program.Main to javafx.graphics, javafx.fxml;
	opens Program.Main.Guis to javafx.fxml, javafx.graphics;
	opens Program.Main.Utilities to javafx.fxml, javafx.graphics;
	opens Program.Main.Guis.RightClick to javafx.fxml, javafx.graphics;
}
