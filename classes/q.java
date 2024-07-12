import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class q {
	private static final Pattern a = Pattern.compile("(<name>.*) \\((<count>\\d*)\\)", 66);
	private static final Pattern b = Pattern.compile(".*\\.|(?:COM|CLOCK\\$|CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])(?:\\..*)?", 2);

	public static boolean a(Path path) {
		Path path2 = path.normalize();
		return path2.equals(path);
	}

	public static boolean b(Path path) {
		for (Path path3 : path) {
			if (b.matcher(path3.toString()).matches()) {
				return false;
			}
		}

		return true;
	}

	public static Path b(Path path, String string2, String string3) {
		String string4 = string2 + string3;
		Path path5 = Paths.get(string4);
		if (path5.endsWith(string3)) {
			throw new InvalidPathException(string4, "empty resource name");
		} else {
			return path.resolve(path5);
		}
	}
}
