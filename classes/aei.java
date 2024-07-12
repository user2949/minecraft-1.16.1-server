import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class aei {
	private static final Pattern a = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

	public static boolean b(@Nullable String string) {
		return StringUtils.isEmpty(string);
	}
}
