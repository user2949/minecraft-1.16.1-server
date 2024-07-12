import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;

public class dag {
	private final int a;
	private final long b;
	private final String c;
	private final int d;
	private final boolean e;

	public dag(int integer1, long long2, String string, int integer4, boolean boolean5) {
		this.a = integer1;
		this.b = long2;
		this.c = string;
		this.d = integer4;
		this.e = boolean5;
	}

	public static dag a(Dynamic<?> dynamic) {
		int integer2 = dynamic.get("version").asInt(0);
		long long3 = dynamic.get("LastPlayed").asLong(0L);
		OptionalDynamic<?> optionalDynamic5 = dynamic.get("Version");
		return optionalDynamic5.result().isPresent()
			? new dag(
				integer2,
				long3,
				optionalDynamic5.get("Name").asString(u.a().getName()),
				optionalDynamic5.get("Id").asInt(u.a().getWorldVersion()),
				optionalDynamic5.get("Snapshot").asBoolean(!u.a().isStable())
			)
			: new dag(integer2, long3, "", 0, false);
	}

	public int a() {
		return this.a;
	}

	public long b() {
		return this.b;
	}
}
