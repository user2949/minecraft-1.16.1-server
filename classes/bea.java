import java.util.Arrays;
import java.util.Comparator;

public enum bea {
	FULL(0, "options.chat.visibility.full"),
	SYSTEM(1, "options.chat.visibility.system"),
	HIDDEN(2, "options.chat.visibility.hidden");

	private static final bea[] d = (bea[])Arrays.stream(values()).sorted(Comparator.comparingInt(bea::a)).toArray(bea[]::new);
	private final int e;
	private final String f;

	private bea(int integer3, String string4) {
		this.e = integer3;
		this.f = string4;
	}

	public int a() {
		return this.e;
	}
}
