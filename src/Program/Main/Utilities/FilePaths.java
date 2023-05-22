package Program.Main.Utilities;

import Program.Main.App;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public interface FilePaths {

	URL globalCSS_URL = App.class.getResource("CSS/GlobalCSS.css");
	URL empCSS_URL = App.class.getResource("CSS/EmployeesGuiCSS.css");
	URL clientCSS_URL = App.class.getResource("CSS/ClientsGuiCSS.css");
	URL carCSS_URL = App.class.getResource("CSS/CarsGuiCSS.css");
	URL mainGuiCSS_URL = App.class.getResource("CSS/MainGuiCSS.css");
	URL rightClickCSS_URL = App.class.getResource("CSS/RightClickCSS.css");
	URL dBOptionsCSS_URL = App.class.getResource("CSS/DBOptionsGuiCSS.css");
	URL bGMusicCSS_URL = App.class.getResource("CSS/BackgroundMusicCSS.css");

	URL credentials_URL = App.class.getResource("Resources/TextFiles/credentials.txt");
	URL empPositions_URL = App.class.getResource("Resources/TextFiles/positions.txt");
	URL carColors_URL = App.class.getResource("Resources/TextFiles/colors.txt");

	URL profilePic_URL = App.class.getResource("Resources/Images/default_pfp.png");

	URL profilePicText_URL = App.class.getResource("Resources/TextFiles/profilePic.txt");

	String profilePicText_ExePath = getRealPath(profilePicText_URL, "/", false);

	String profilePic_ExePath = getRealPath(profilePic_URL, "\\", false);

	String globalCSS_ExePath = getRealPath(globalCSS_URL, "/", true);

	String credentials_ExePath = getRealPath(credentials_URL, "/", false);
	String empPositions_ExePath = getRealPath(empPositions_URL, "/", false);
	String carColors_ExePath = getRealPath(carColors_URL, "/", false);

	String empCSS_ExePath = getRealPath(empCSS_URL, "/", true);

	String clientCSS_ExePath = getRealPath(clientCSS_URL, "/", true);

	String carCSS_ExePath = getRealPath(carCSS_URL, "/", true);

	String mainGuiCSS_ExePath = getRealPath(mainGuiCSS_URL, "/", true);

	String rightClickCSS_ExePath = getRealPath(rightClickCSS_URL, "/", true);

	String dBOptionsCSS_ExePath = getRealPath(dBOptionsCSS_URL, "/", true);

	String bGMusicCSS_ExePath = getRealPath(bGMusicCSS_URL, "/", true);

	static String getRealPath(URL url, String converter, boolean cssPath) {
		String fixed = null;

		switch (converter) {
			case "/" -> {
				// for text files, use this
				if (!cssPath) {
					fixed = url.getPath();
					fixed = fixed.replaceFirst("/", "");
					fixed = fixed.replaceFirst("file:", "");
					fixed = fixed.replace("%20", " ");
				}
				// For css use this
				else {
					try {
						fixed = url.toURI().toURL().toString();
					} catch (MalformedURLException | URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// For pictures maybe use this
			case "\\" -> {
				fixed = url.getPath().replace("/", "\\\\");
				fixed = fixed.replaceFirst("\\\\", "");
				fixed = fixed.replaceFirst("\\\\", "");
				fixed = fixed.replace("%20", " ");
				fixed = fixed.replaceFirst("file:", "");
			}
		}

		return fixed;
	}

}
