package Program.Main.Utilities;
import javafx.scene.Scene;
public interface ConnectCSS extends FilePaths {

	default void initConnectCSS(Scene scene, String exePathToCss, boolean usesGlobalCss) {
		if (usesGlobalCss) {
			scene.getStylesheets().clear();
			scene.getStylesheets().addAll(globalCSS_ExePath, exePathToCss);
		} else {
			scene.getStylesheets().clear();
			scene.getStylesheets().addAll(exePathToCss);
		}
	}

	/*default void initConnectCSS(Scene scene, String pathToCss, boolean usesGlobalCss) {
		if (usesGlobalCss) {
			// Gets current working directory of project (Used for exe case)
			String userDir = System.getProperty("user.dir");
	
			String pathToGlobal_css = userDir + globalCSS_Path;
			String pathToCSS = userDir + pathToCss;
			scene.getStylesheets().clear();
	
			String encodedGlobalCss = null, encodedCarsCss = null;
			try {
				// Convert all path white spaces to %20 so it works on any path.
				// Prog Uni folder reads prog%20Uni
				URL url = new File(pathToCSS).toURI().toURL();
				encodedCarsCss = url.toString();
	
				URL url2 = new File(pathToGlobal_css).toURI().toURL();
				encodedGlobalCss = url2.toString();
	
			} catch (MalformedURLException ignored) {
			}
	
			scene.getStylesheets().addAll(encodedGlobalCss, encodedCarsCss);
		} else {
			// Gets current working directory of project (Used for exe case)
			String userDir = System.getProperty("user.dir");
	
			String pathToCarsGuiCSS = userDir + pathToCss;
			scene.getStylesheets().clear();
	
			String encodedCarsCss = null;
			try {
				// Convert all path white spaces to %20 so it works on any path.
				// Prog Uni folder reads prog%20Uni
				URL urlCars = new File(pathToCarsGuiCSS).toURI().toURL();
				encodedCarsCss = urlCars.toString();
	
			} catch (MalformedURLException ignored) {
			}
	
			scene.getStylesheets().add(encodedCarsCss);
		}
	}*/

}
