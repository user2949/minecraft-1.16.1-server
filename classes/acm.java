import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum acm {
	MASTER("master"),
	MUSIC("music"),
	RECORDS("record"),
	WEATHER("weather"),
	BLOCKS("block"),
	HOSTILE("hostile"),
	NEUTRAL("neutral"),
	PLAYERS("player"),
	AMBIENT("ambient"),
	VOICE("voice");

	private static final Map<String, acm> k = (Map<String, acm>)Arrays.stream(values()).collect(Collectors.toMap(acm::a, Function.identity()));
	private final String l;

	private acm(String string3) {
		this.l = string3;
	}

	public String a() {
		return this.l;
	}
}
