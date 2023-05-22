package Program.Main.Utilities;

import Program.Main.App;


public interface ScreenBounds {

	// Gets user's screen size to facilitate things


	static double getScreenWidth() {
		return App.getScreenWidth();
	}

	static double getScreenHeight() {
		return App.getScreenHeight();
	}
}
