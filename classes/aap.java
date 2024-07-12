import com.mojang.brigadier.arguments.StringArgumentType;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aap implements AutoCloseable {
	private static final Logger a = LogManager.getLogger();
	private static final aaj b = new aaj(new ne("resourcePack.broken_assets").a(new i[]{i.RED, i.ITALIC}), u.a().getPackVersion());
	private final String c;
	private final Supplier<aae> d;
	private final mr e;
	private final mr f;
	private final aaq g;
	private final aap.b h;
	private final boolean i;
	private final boolean j;
	private final aas k;

	@Nullable
	public static <T extends aap> T a(String string, boolean boolean2, Supplier<aae> supplier, aap.a<T> a, aap.b b, aas aas) {
		try (aae aae7 = (aae)supplier.get()) {
			aaj aaj9 = aae7.a(aaj.a);
			if (boolean2 && aaj9 == null) {
				aap.a
					.error("Broken/missing pack.mcmeta detected, fudging it into existance. Please check that your launcher has downloaded all assets for the game correctly!");
				aaj9 = aap.b;
			}

			if (aaj9 != null) {
				return a.create(string, boolean2, supplier, aae7, aaj9, b, aas);
			}

			aap.a.warn("Couldn't find pack meta for pack {}", string);
		} catch (IOException var22) {
			aap.a.warn("Couldn't get pack info for: {}", var22.toString());
		}

		return null;
	}

	public aap(String string, boolean boolean2, Supplier<aae> supplier, mr mr4, mr mr5, aaq aaq, aap.b b, boolean boolean8, aas aas) {
		this.c = string;
		this.d = supplier;
		this.e = mr4;
		this.f = mr5;
		this.g = aaq;
		this.i = boolean2;
		this.h = b;
		this.j = boolean8;
		this.k = aas;
	}

	public aap(String string, boolean boolean2, Supplier<aae> supplier, aae aae, aaj aaj, aap.b b, aas aas) {
		this(string, boolean2, supplier, new nd(aae.a()), aaj.a(), aaq.a(aaj.b()), b, false, aas);
	}

	public mr a(boolean boolean1) {
		return ms.a(this.k.decorate(new nd(this.c)))
			.a(nb -> nb.a(boolean1 ? i.GREEN : i.RED).a(StringArgumentType.escapeIfRequired(this.c)).a(new mv(mv.a.a, new nd("").a(this.e).c("\n").a(this.f))));
	}

	public aaq c() {
		return this.g;
	}

	public aae d() {
		return (aae)this.d.get();
	}

	public String e() {
		return this.c;
	}

	public boolean f() {
		return this.i;
	}

	public boolean g() {
		return this.j;
	}

	public aap.b h() {
		return this.h;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof aap)) {
			return false;
		} else {
			aap aap3 = (aap)object;
			return this.c.equals(aap3.c);
		}
	}

	public int hashCode() {
		return this.c.hashCode();
	}

	public void close() {
	}

	@FunctionalInterface
	public interface a<T extends aap> {
		@Nullable
		T create(String string, boolean boolean2, Supplier<aae> supplier, aae aae, aaj aaj, aap.b b, aas aas);
	}

	public static enum b {
		TOP,
		BOTTOM;

		public <T, P extends aap> int a(List<T> list, T object, Function<T, P> function, boolean boolean4) {
			aap.b b6 = boolean4 ? this.a() : this;
			if (b6 == BOTTOM) {
				int integer7;
				for (integer7 = 0; integer7 < list.size(); integer7++) {
					P aap8 = (P)function.apply(list.get(integer7));
					if (!aap8.g() || aap8.h() != this) {
						break;
					}
				}

				list.add(integer7, object);
				return integer7;
			} else {
				int integer7;
				for (integer7 = list.size() - 1; integer7 >= 0; integer7--) {
					P aap8 = (P)function.apply(list.get(integer7));
					if (!aap8.g() || aap8.h() != this) {
						break;
					}
				}

				list.add(integer7 + 1, object);
				return integer7 + 1;
			}
		}

		public aap.b a() {
			return this == TOP ? BOTTOM : TOP;
		}
	}
}
