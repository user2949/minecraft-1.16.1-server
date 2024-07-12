import com.google.gson.JsonElement;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ja {
	public static final ja.a a = a(iy::a, ix.c);
	public static final ja.a b = a(iy::a, ix.d);
	public static final ja.a c = a(iy::j, ix.e);
	public static final ja.a d = a(iy::j, ix.f);
	public static final ja.a e = a(iy::m, ix.h);
	public static final ja.a f = a(iy::k, ix.g);
	public static final ja.a g = a(iy::x, ix.i);
	public static final ja.a h = a(iy::w, ix.j);
	public static final ja.a i = a(iy::f, ix.aa);
	public static final ja.a j = a(iy::h, ix.ad);
	public static final ja.a k = a(iy::i, ix.ab);
	public static final ja.a l = a(iy::q, ix.F);
	public static final ja.a m = a(iy::z, ix.am);
	public static final ja.a n = a(iy::a, ix.I);
	public static final ja.a o = a(iy::t, ix.ax);
	public static final ja.a p = a(iy::t, ix.ay);
	public static final ja.a q = a(iy::b, ix.aE);
	public static final ja.a r = a(iy::l, ix.e);
	public static final ja.a s = a(iy::l, ix.f);
	public static final ja.a t = a(iy::n, ix.h);
	public static final ja.a u = a(iy::o, ix.e);
	private final iy v;
	private final iw w;

	private ja(iy iy, iw iw) {
		this.v = iy;
		this.w = iw;
	}

	public iw a() {
		return this.w;
	}

	public iy b() {
		return this.v;
	}

	public ja a(Consumer<iy> consumer) {
		consumer.accept(this.v);
		return this;
	}

	public uh a(bvr bvr, BiConsumer<uh, Supplier<JsonElement>> biConsumer) {
		return this.w.a(bvr, this.v, biConsumer);
	}

	public uh a(bvr bvr, String string, BiConsumer<uh, Supplier<JsonElement>> biConsumer) {
		return this.w.a(bvr, string, this.v, biConsumer);
	}

	private static ja.a a(Function<bvr, iy> function, iw iw) {
		return bvr -> new ja((iy)function.apply(bvr), iw);
	}

	public static ja a(uh uh) {
		return new ja(iy.b(uh), ix.c);
	}

	@FunctionalInterface
	public interface a {
		ja get(bvr bvr);

		default uh a(bvr bvr, BiConsumer<uh, Supplier<JsonElement>> biConsumer) {
			return this.get(bvr).a(bvr, biConsumer);
		}

		default uh a(bvr bvr, String string, BiConsumer<uh, Supplier<JsonElement>> biConsumer) {
			return this.get(bvr).a(bvr, string, biConsumer);
		}

		default ja.a a(Consumer<iy> consumer) {
			return bvr -> this.get(bvr).a(consumer);
		}
	}
}
