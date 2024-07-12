import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public enum i {
	BLACK("BLACK", '0', 0, 0),
	DARK_BLUE("DARK_BLUE", '1', 1, 170),
	DARK_GREEN("DARK_GREEN", '2', 2, 43520),
	DARK_AQUA("DARK_AQUA", '3', 3, 43690),
	DARK_RED("DARK_RED", '4', 4, 11141120),
	DARK_PURPLE("DARK_PURPLE", '5', 5, 11141290),
	GOLD("GOLD", '6', 6, 16755200),
	GRAY("GRAY", '7', 7, 11184810),
	DARK_GRAY("DARK_GRAY", '8', 8, 5592405),
	BLUE("BLUE", '9', 9, 5592575),
	GREEN("GREEN", 'a', 10, 5635925),
	AQUA("AQUA", 'b', 11, 5636095),
	RED("RED", 'c', 12, 16733525),
	LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13, 16733695),
	YELLOW("YELLOW", 'e', 14, 16777045),
	WHITE("WHITE", 'f', 15, 16777215),
	OBFUSCATED("OBFUSCATED", 'k', true),
	BOLD("BOLD", 'l', true),
	STRIKETHROUGH("STRIKETHROUGH", 'm', true),
	UNDERLINE("UNDERLINE", 'n', true),
	ITALIC("ITALIC", 'o', true),
	RESET("RESET", 'r', -1, null);

	private static final Map<String, i> w = (Map<String, i>)Arrays.stream(values()).collect(Collectors.toMap(i -> c(i.y), i -> i));
	private static final Pattern x = Pattern.compile("(?i)§[0-9A-FK-OR]");
	private final String y;
	private final char z;
	private final boolean A;
	private final String B;
	private final int C;
	@Nullable
	private final Integer D;

	private static String c(String string) {
		return string.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
	}

	private i(String string3, char character, int integer5, Integer integer) {
		this(string3, character, false, integer5, integer);
	}

	private i(String string3, char character, boolean boolean5) {
		this(string3, character, boolean5, -1, null);
	}

	private i(String string3, char character, boolean boolean5, int integer6, Integer integer) {
		this.y = string3;
		this.z = character;
		this.A = boolean5;
		this.C = integer6;
		this.D = integer;
		this.B = "§" + character;
	}

	public int b() {
		return this.C;
	}

	public boolean c() {
		return this.A;
	}

	public boolean d() {
		return !this.A && this != RESET;
	}

	@Nullable
	public Integer e() {
		return this.D;
	}

	public String f() {
		return this.name().toLowerCase(Locale.ROOT);
	}

	public String toString() {
		return this.B;
	}

	@Nullable
	public static String a(@Nullable String string) {
		return string == null ? null : x.matcher(string).replaceAll("");
	}

	@Nullable
	public static i b(@Nullable String string) {
		return string == null ? null : (i)w.get(c(string));
	}

	@Nullable
	public static i a(int integer) {
		if (integer < 0) {
			return RESET;
		} else {
			for (i i5 : values()) {
				if (i5.b() == integer) {
					return i5;
				}
			}

			return null;
		}
	}

	public static Collection<String> a(boolean boolean1, boolean boolean2) {
		List<String> list3 = Lists.<String>newArrayList();

		for (i i7 : values()) {
			if ((!i7.d() || boolean1) && (!i7.c() || boolean2)) {
				list3.add(i7.f());
			}
		}

		return list3;
	}
}
