import com.mojang.bridge.game.GameVersion;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import java.time.Duration;

public class u {
	public static final Level a = Level.DISABLED;
	public static final long b = Duration.ofMillis(300L).toNanos();
	public static boolean c = true;
	public static boolean d;
	public static final char[] e = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':'};
	private static GameVersion f;

	public static boolean a(char character) {
		return character != 167 && character >= ' ' && character != 127;
	}

	public static String a(String string) {
		StringBuilder stringBuilder2 = new StringBuilder();

		for (char character6 : string.toCharArray()) {
			if (a(character6)) {
				stringBuilder2.append(character6);
			}
		}

		return stringBuilder2.toString();
	}

	public static GameVersion a() {
		if (f == null) {
			f = o.a();
		}

		return f;
	}

	static {
		ResourceLeakDetector.setLevel(a);
		CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES = false;
		CommandSyntaxException.BUILT_IN_EXCEPTIONS = new cv();
	}
}
