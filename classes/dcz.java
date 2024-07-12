import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class dcz {
	private static final BiMap<uh, dcy> m = HashBiMap.create();
	public static final dcy a = a("empty", a -> {
	});
	public static final dcy b = a("chest", a -> a.a(dda.f).b(dda.a));
	public static final dcy c = a("command", a -> a.a(dda.f).b(dda.a));
	public static final dcy d = a("selector", a -> a.a(dda.f).a(dda.a));
	public static final dcy e = a("fishing", a -> a.a(dda.f).a(dda.j).b(dda.a));
	public static final dcy f = a("entity", a -> a.a(dda.a).a(dda.f).a(dda.c).b(dda.d).b(dda.e).b(dda.b));
	public static final dcy g = a("gift", a -> a.a(dda.f).a(dda.a));
	public static final dcy h = a("barter", a -> a.a(dda.a));
	public static final dcy i = a("advancement_reward", a -> a.a(dda.a).a(dda.f));
	public static final dcy j = a("advancement_entity", a -> a.a(dda.a).a(dda.g).a(dda.f));
	public static final dcy k = a("generic", a -> a.a(dda.a).a(dda.b).a(dda.c).a(dda.d).a(dda.e).a(dda.f).a(dda.h).a(dda.i).a(dda.j).a(dda.k));
	public static final dcy l = a("block", a -> a.a(dda.h).a(dda.f).a(dda.j).b(dda.a).b(dda.i).b(dda.k));

	private static dcy a(String string, Consumer<dcy.a> consumer) {
		dcy.a a3 = new dcy.a();
		consumer.accept(a3);
		dcy dcy4 = a3.a();
		uh uh5 = new uh(string);
		dcy dcy6 = m.put(uh5, dcy4);
		if (dcy6 != null) {
			throw new IllegalStateException("Loot table parameter set " + uh5 + " is already registered");
		} else {
			return dcy4;
		}
	}

	@Nullable
	public static dcy a(uh uh) {
		return (dcy)m.get(uh);
	}

	@Nullable
	public static uh a(dcy dcy) {
		return (uh)m.inverse().get(dcy);
	}
}
