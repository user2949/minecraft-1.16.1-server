import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

public class ef {
	public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("argument.block.tag.disallowed"));
	public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("argument.block.id.invalid", object));
	public static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType(
		(object1, object2) -> new ne("argument.block.property.unknown", object1, object2)
	);
	public static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType(
		(object1, object2) -> new ne("argument.block.property.duplicate", object2, object1)
	);
	public static final Dynamic3CommandExceptionType e = new Dynamic3CommandExceptionType(
		(object1, object2, object3) -> new ne("argument.block.property.invalid", object1, object3, object2)
	);
	public static final Dynamic2CommandExceptionType f = new Dynamic2CommandExceptionType(
		(object1, object2) -> new ne("argument.block.property.novalue", object1, object2)
	);
	public static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(new ne("argument.block.property.unclosed"));
	private static final BiFunction<SuggestionsBuilder, adg<bvr>, CompletableFuture<Suggestions>> h = (suggestionsBuilder, adg) -> suggestionsBuilder.buildFuture();
	private final StringReader i;
	private final boolean j;
	private final Map<cgl<?>, Comparable<?>> k = Maps.<cgl<?>, Comparable<?>>newHashMap();
	private final Map<String, String> l = Maps.<String, String>newHashMap();
	private uh m = new uh("");
	private cfk<bvr, cfj> n;
	private cfj o;
	@Nullable
	private le p;
	private uh q = new uh("");
	private int r;
	private BiFunction<SuggestionsBuilder, adg<bvr>, CompletableFuture<Suggestions>> s = h;

	public ef(StringReader stringReader, boolean boolean2) {
		this.i = stringReader;
		this.j = boolean2;
	}

	public Map<cgl<?>, Comparable<?>> a() {
		return this.k;
	}

	@Nullable
	public cfj b() {
		return this.o;
	}

	@Nullable
	public le c() {
		return this.p;
	}

	@Nullable
	public uh d() {
		return this.q;
	}

	public ef a(boolean boolean1) throws CommandSyntaxException {
		this.s = this::l;
		if (this.i.canRead() && this.i.peek() == '#') {
			this.f();
			this.s = this::i;
			if (this.i.canRead() && this.i.peek() == '[') {
				this.h();
				this.s = this::f;
			}
		} else {
			this.e();
			this.s = this::j;
			if (this.i.canRead() && this.i.peek() == '[') {
				this.g();
				this.s = this::f;
			}
		}

		if (boolean1 && this.i.canRead() && this.i.peek() == '{') {
			this.s = h;
			this.i();
		}

		return this;
	}

	private CompletableFuture<Suggestions> b(SuggestionsBuilder suggestionsBuilder, adg<bvr> adg) {
		if (suggestionsBuilder.getRemaining().isEmpty()) {
			suggestionsBuilder.suggest(String.valueOf(']'));
		}

		return this.d(suggestionsBuilder, adg);
	}

	private CompletableFuture<Suggestions> c(SuggestionsBuilder suggestionsBuilder, adg<bvr> adg) {
		if (suggestionsBuilder.getRemaining().isEmpty()) {
			suggestionsBuilder.suggest(String.valueOf(']'));
		}

		return this.e(suggestionsBuilder, adg);
	}

	private CompletableFuture<Suggestions> d(SuggestionsBuilder suggestionsBuilder, adg<bvr> adg) {
		String string4 = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);

		for (cgl<?> cgl6 : this.o.r()) {
			if (!this.k.containsKey(cgl6) && cgl6.f().startsWith(string4)) {
				suggestionsBuilder.suggest(cgl6.f() + '=');
			}
		}

		return suggestionsBuilder.buildFuture();
	}

	private CompletableFuture<Suggestions> e(SuggestionsBuilder suggestionsBuilder, adg<bvr> adg) {
		String string4 = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
		if (this.q != null && !this.q.a().isEmpty()) {
			adf<bvr> adf5 = adg.a(this.q);
			if (adf5 != null) {
				for (bvr bvr7 : adf5.b()) {
					for (cgl<?> cgl9 : bvr7.m().d()) {
						if (!this.l.containsKey(cgl9.f()) && cgl9.f().startsWith(string4)) {
							suggestionsBuilder.suggest(cgl9.f() + '=');
						}
					}
				}
			}
		}

		return suggestionsBuilder.buildFuture();
	}

	private CompletableFuture<Suggestions> f(SuggestionsBuilder suggestionsBuilder, adg<bvr> adg) {
		if (suggestionsBuilder.getRemaining().isEmpty() && this.a(adg)) {
			suggestionsBuilder.suggest(String.valueOf('{'));
		}

		return suggestionsBuilder.buildFuture();
	}

	private boolean a(adg<bvr> adg) {
		if (this.o != null) {
			return this.o.b().q();
		} else {
			if (this.q != null) {
				adf<bvr> adf3 = adg.a(this.q);
				if (adf3 != null) {
					for (bvr bvr5 : adf3.b()) {
						if (bvr5.q()) {
							return true;
						}
					}
				}
			}

			return false;
		}
	}

	private CompletableFuture<Suggestions> g(SuggestionsBuilder suggestionsBuilder, adg<bvr> adg) {
		if (suggestionsBuilder.getRemaining().isEmpty()) {
			suggestionsBuilder.suggest(String.valueOf('='));
		}

		return suggestionsBuilder.buildFuture();
	}

	private CompletableFuture<Suggestions> h(SuggestionsBuilder suggestionsBuilder, adg<bvr> adg) {
		if (suggestionsBuilder.getRemaining().isEmpty()) {
			suggestionsBuilder.suggest(String.valueOf(']'));
		}

		if (suggestionsBuilder.getRemaining().isEmpty() && this.k.size() < this.o.r().size()) {
			suggestionsBuilder.suggest(String.valueOf(','));
		}

		return suggestionsBuilder.buildFuture();
	}

	private static <T extends Comparable<T>> SuggestionsBuilder a(SuggestionsBuilder suggestionsBuilder, cgl<T> cgl) {
		for (T comparable4 : cgl.a()) {
			if (comparable4 instanceof Integer) {
				suggestionsBuilder.suggest((Integer)comparable4);
			} else {
				suggestionsBuilder.suggest(cgl.a(comparable4));
			}
		}

		return suggestionsBuilder;
	}

	private CompletableFuture<Suggestions> a(SuggestionsBuilder suggestionsBuilder, adg<bvr> adg, String string) {
		boolean boolean5 = false;
		if (this.q != null && !this.q.a().isEmpty()) {
			adf<bvr> adf6 = adg.a(this.q);
			if (adf6 != null) {
				for (bvr bvr8 : adf6.b()) {
					cgl<?> cgl9 = bvr8.m().a(string);
					if (cgl9 != null) {
						a(suggestionsBuilder, cgl9);
					}

					if (!boolean5) {
						for (cgl<?> cgl11 : bvr8.m().d()) {
							if (!this.l.containsKey(cgl11.f())) {
								boolean5 = true;
								break;
							}
						}
					}
				}
			}
		}

		if (boolean5) {
			suggestionsBuilder.suggest(String.valueOf(','));
		}

		suggestionsBuilder.suggest(String.valueOf(']'));
		return suggestionsBuilder.buildFuture();
	}

	private CompletableFuture<Suggestions> i(SuggestionsBuilder suggestionsBuilder, adg<bvr> adg) {
		if (suggestionsBuilder.getRemaining().isEmpty()) {
			adf<bvr> adf4 = adg.a(this.q);
			if (adf4 != null) {
				boolean boolean5 = false;
				boolean boolean6 = false;

				for (bvr bvr8 : adf4.b()) {
					boolean5 |= !bvr8.m().d().isEmpty();
					boolean6 |= bvr8.q();
					if (boolean5 && boolean6) {
						break;
					}
				}

				if (boolean5) {
					suggestionsBuilder.suggest(String.valueOf('['));
				}

				if (boolean6) {
					suggestionsBuilder.suggest(String.valueOf('{'));
				}
			}
		}

		return this.k(suggestionsBuilder, adg);
	}

	private CompletableFuture<Suggestions> j(SuggestionsBuilder suggestionsBuilder, adg<bvr> adg) {
		if (suggestionsBuilder.getRemaining().isEmpty()) {
			if (!this.o.b().m().d().isEmpty()) {
				suggestionsBuilder.suggest(String.valueOf('['));
			}

			if (this.o.b().q()) {
				suggestionsBuilder.suggest(String.valueOf('{'));
			}
		}

		return suggestionsBuilder.buildFuture();
	}

	private CompletableFuture<Suggestions> k(SuggestionsBuilder suggestionsBuilder, adg<bvr> adg) {
		return db.a(adg.a(), suggestionsBuilder.createOffset(this.r).add(suggestionsBuilder));
	}

	private CompletableFuture<Suggestions> l(SuggestionsBuilder suggestionsBuilder, adg<bvr> adg) {
		if (this.j) {
			db.a(adg.a(), suggestionsBuilder, String.valueOf('#'));
		}

		db.a(gl.aj.b(), suggestionsBuilder);
		return suggestionsBuilder.buildFuture();
	}

	public void e() throws CommandSyntaxException {
		int integer2 = this.i.getCursor();
		this.m = uh.a(this.i);
		bvr bvr3 = (bvr)gl.aj.b(this.m).orElseThrow(() -> {
			this.i.setCursor(integer2);
			return b.createWithContext(this.i, this.m.toString());
		});
		this.n = bvr3.m();
		this.o = bvr3.n();
	}

	public void f() throws CommandSyntaxException {
		if (!this.j) {
			throw a.create();
		} else {
			this.s = this::k;
			this.i.expect('#');
			this.r = this.i.getCursor();
			this.q = uh.a(this.i);
		}
	}

	public void g() throws CommandSyntaxException {
		this.i.skip();
		this.s = this::b;
		this.i.skipWhitespace();

		while (this.i.canRead() && this.i.peek() != ']') {
			this.i.skipWhitespace();
			int integer2 = this.i.getCursor();
			String string3 = this.i.readString();
			cgl<?> cgl4 = this.n.a(string3);
			if (cgl4 == null) {
				this.i.setCursor(integer2);
				throw c.createWithContext(this.i, this.m.toString(), string3);
			}

			if (this.k.containsKey(cgl4)) {
				this.i.setCursor(integer2);
				throw d.createWithContext(this.i, this.m.toString(), string3);
			}

			this.i.skipWhitespace();
			this.s = this::g;
			if (!this.i.canRead() || this.i.peek() != '=') {
				throw f.createWithContext(this.i, this.m.toString(), string3);
			}

			this.i.skip();
			this.i.skipWhitespace();
			this.s = (suggestionsBuilder, adg) -> a(suggestionsBuilder, cgl4).buildFuture();
			int integer5 = this.i.getCursor();
			this.a(cgl4, this.i.readString(), integer5);
			this.s = this::h;
			this.i.skipWhitespace();
			if (this.i.canRead()) {
				if (this.i.peek() != ',') {
					if (this.i.peek() != ']') {
						throw g.createWithContext(this.i);
					}
					break;
				}

				this.i.skip();
				this.s = this::d;
			}
		}

		if (this.i.canRead()) {
			this.i.skip();
		} else {
			throw g.createWithContext(this.i);
		}
	}

	public void h() throws CommandSyntaxException {
		this.i.skip();
		this.s = this::c;
		int integer2 = -1;
		this.i.skipWhitespace();

		while (this.i.canRead() && this.i.peek() != ']') {
			this.i.skipWhitespace();
			int integer3 = this.i.getCursor();
			String string4 = this.i.readString();
			if (this.l.containsKey(string4)) {
				this.i.setCursor(integer3);
				throw d.createWithContext(this.i, this.m.toString(), string4);
			}

			this.i.skipWhitespace();
			if (!this.i.canRead() || this.i.peek() != '=') {
				this.i.setCursor(integer3);
				throw f.createWithContext(this.i, this.m.toString(), string4);
			}

			this.i.skip();
			this.i.skipWhitespace();
			this.s = (suggestionsBuilder, adg) -> this.a(suggestionsBuilder, adg, string4);
			integer2 = this.i.getCursor();
			String string5 = this.i.readString();
			this.l.put(string4, string5);
			this.i.skipWhitespace();
			if (this.i.canRead()) {
				integer2 = -1;
				if (this.i.peek() != ',') {
					if (this.i.peek() != ']') {
						throw g.createWithContext(this.i);
					}
					break;
				}

				this.i.skip();
				this.s = this::e;
			}
		}

		if (this.i.canRead()) {
			this.i.skip();
		} else {
			if (integer2 >= 0) {
				this.i.setCursor(integer2);
			}

			throw g.createWithContext(this.i);
		}
	}

	public void i() throws CommandSyntaxException {
		this.p = new lv(this.i).f();
	}

	private <T extends Comparable<T>> void a(cgl<T> cgl, String string, int integer) throws CommandSyntaxException {
		Optional<T> optional5 = cgl.b(string);
		if (optional5.isPresent()) {
			this.o = this.o.a(cgl, (Comparable)optional5.get());
			this.k.put(cgl, optional5.get());
		} else {
			this.i.setCursor(integer);
			throw e.createWithContext(this.i, this.m.toString(), cgl.f(), string);
		}
	}

	public static String a(cfj cfj) {
		StringBuilder stringBuilder2 = new StringBuilder(gl.aj.b(cfj.b()).toString());
		if (!cfj.r().isEmpty()) {
			stringBuilder2.append('[');
			boolean boolean3 = false;

			for (Entry<cgl<?>, Comparable<?>> entry5 : cfj.s().entrySet()) {
				if (boolean3) {
					stringBuilder2.append(',');
				}

				a(stringBuilder2, (cgl)entry5.getKey(), (Comparable<?>)entry5.getValue());
				boolean3 = true;
			}

			stringBuilder2.append(']');
		}

		return stringBuilder2.toString();
	}

	private static <T extends Comparable<T>> void a(StringBuilder stringBuilder, cgl<T> cgl, Comparable<?> comparable) {
		stringBuilder.append(cgl.f());
		stringBuilder.append('=');
		stringBuilder.append(cgl.a((T)comparable));
	}

	public CompletableFuture<Suggestions> a(SuggestionsBuilder suggestionsBuilder, adg<bvr> adg) {
		return (CompletableFuture<Suggestions>)this.s.apply(suggestionsBuilder.createOffset(this.i.getCursor()), adg);
	}

	public Map<String, String> j() {
		return this.l;
	}
}
