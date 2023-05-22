package Program.Main.Utilities;
public interface IsNull {

	default boolean isEmptyOrBlank(String text) {

		try {
			if (text.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			/* An empty string is a string instance of zero length*/
		}
		try {
			if (text.isBlank()) {
				return true;
			}
		} catch (Exception e2) {
			/*If a string only consists of whitespace, then we call it blank.
			 * For Java, whitespaces are characters, like spaces, tabs, and so on*/
		}
		try {
			if (text == null) {
				return true;
			}
		} catch (Exception e3) {
			/*a null string has no value at all*/
		}

		return false;
	}
}
