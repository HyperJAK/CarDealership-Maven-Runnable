package Program.Main.Utilities;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public interface Email {

	default boolean emailVerifier(String email) {
		final String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z.-]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
