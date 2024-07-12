import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public enum awm {
	MAJOR_NEGATIVE("major_negative", -5, 100, 10, 10),
	MINOR_NEGATIVE("minor_negative", -1, 200, 20, 20),
	MINOR_POSITIVE("minor_positive", 1, 200, 1, 5),
	MAJOR_POSITIVE("major_positive", 5, 100, 0, 100),
	TRADING("trading", 1, 25, 2, 20);

	public final String f;
	public final int g;
	public final int h;
	public final int i;
	public final int j;
	private static final Map<String, awm> k = (Map<String, awm>)Stream.of(values()).collect(ImmutableMap.toImmutableMap(awm -> awm.f, Function.identity()));

	private awm(String string3, int integer4, int integer5, int integer6, int integer7) {
		this.f = string3;
		this.g = integer4;
		this.h = integer5;
		this.i = integer6;
		this.j = integer7;
	}

	@Nullable
	public static awm a(String string) {
		return (awm)k.get(string);
	}
}
