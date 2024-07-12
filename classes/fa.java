import com.google.common.primitives.Doubles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;

public class fa {
	public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("argument.entity.invalid"));
	public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("argument.entity.selector.unknown", object));
	public static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("argument.entity.selector.not_allowed"));
	public static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ne("argument.entity.selector.missing"));
	public static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new ne("argument.entity.options.unterminated"));
	public static final DynamicCommandExceptionType f = new DynamicCommandExceptionType(object -> new ne("argument.entity.options.valueless", object));
	public static final BiConsumer<dem, List<? extends aom>> g = (dem, list) -> {
	};
	public static final BiConsumer<dem, List<? extends aom>> h = (dem, list) -> list.sort((aom2, aom3) -> Doubles.compare(aom2.d(dem), aom3.d(dem)));
	public static final BiConsumer<dem, List<? extends aom>> i = (dem, list) -> list.sort((aom2, aom3) -> Doubles.compare(aom3.d(dem), aom2.d(dem)));
	public static final BiConsumer<dem, List<? extends aom>> j = (dem, list) -> Collections.shuffle(list);
	public static final BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> k = (suggestionsBuilder, consumer) -> suggestionsBuilder.buildFuture(
			
		);
	private final StringReader l;
	private final boolean m;
	private int n;
	private boolean o;
	private boolean p;
	private bx.c q = bx.c.e;
	private bx.d r = bx.d.e;
	@Nullable
	private Double s;
	@Nullable
	private Double t;
	@Nullable
	private Double u;
	@Nullable
	private Double v;
	@Nullable
	private Double w;
	@Nullable
	private Double x;
	private cs y = cs.a;
	private cs z = cs.a;
	private Predicate<aom> A = aom -> true;
	private BiConsumer<dem, List<? extends aom>> B = g;
	private boolean C;
	@Nullable
	private String D;
	private int E;
	@Nullable
	private UUID F;
	private BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> G = k;
	private boolean H;
	private boolean I;
	private boolean J;
	private boolean K;
	private boolean L;
	private boolean M;
	private boolean N;
	private boolean O;
	@Nullable
	private aoq<?> P;
	private boolean Q;
	private boolean R;
	private boolean S;
	private boolean T;

	public fa(StringReader stringReader) {
		this(stringReader, true);
	}

	public fa(StringReader stringReader, boolean boolean2) {
		this.l = stringReader;
		this.m = boolean2;
	}

	public ez a() {
		deg deg2;
		if (this.v == null && this.w == null && this.x == null) {
			if (this.q.b() != null) {
				float float3 = this.q.b();
				deg2 = new deg((double)(-float3), (double)(-float3), (double)(-float3), (double)(float3 + 1.0F), (double)(float3 + 1.0F), (double)(float3 + 1.0F));
			} else {
				deg2 = null;
			}
		} else {
			deg2 = this.a(this.v == null ? 0.0 : this.v, this.w == null ? 0.0 : this.w, this.x == null ? 0.0 : this.x);
		}

		Function<dem, dem> function3;
		if (this.s == null && this.t == null && this.u == null) {
			function3 = dem -> dem;
		} else {
			function3 = dem -> new dem(this.s == null ? dem.b : this.s, this.t == null ? dem.c : this.t, this.u == null ? dem.d : this.u);
		}

		return new ez(this.n, this.o, this.p, this.A, this.q, function3, deg2, this.B, this.C, this.D, this.F, this.P, this.T);
	}

	private deg a(double double1, double double2, double double3) {
		boolean boolean8 = double1 < 0.0;
		boolean boolean9 = double2 < 0.0;
		boolean boolean10 = double3 < 0.0;
		double double11 = boolean8 ? double1 : 0.0;
		double double13 = boolean9 ? double2 : 0.0;
		double double15 = boolean10 ? double3 : 0.0;
		double double17 = (boolean8 ? 0.0 : double1) + 1.0;
		double double19 = (boolean9 ? 0.0 : double2) + 1.0;
		double double21 = (boolean10 ? 0.0 : double3) + 1.0;
		return new deg(double11, double13, double15, double17, double19, double21);
	}

	private void I() {
		if (this.y != cs.a) {
			this.A = this.A.and(this.a(this.y, aom -> (double)aom.q));
		}

		if (this.z != cs.a) {
			this.A = this.A.and(this.a(this.z, aom -> (double)aom.p));
		}

		if (!this.r.c()) {
			this.A = this.A.and(aom -> !(aom instanceof ze) ? false : this.r.d(((ze)aom).bK));
		}
	}

	private Predicate<aom> a(cs cs, ToDoubleFunction<aom> toDoubleFunction) {
		double double4 = (double)aec.g(cs.a() == null ? 0.0F : cs.a());
		double double6 = (double)aec.g(cs.b() == null ? 359.0F : cs.b());
		return aom -> {
			double double7 = aec.g(toDoubleFunction.applyAsDouble(aom));
			return double4 > double6 ? double7 >= double4 || double7 <= double6 : double7 >= double4 && double7 <= double6;
		};
	}

	protected void b() throws CommandSyntaxException {
		this.T = true;
		this.G = this::d;
		if (!this.l.canRead()) {
			throw d.createWithContext(this.l);
		} else {
			int integer2 = this.l.getCursor();
			char character3 = this.l.read();
			if (character3 == 'p') {
				this.n = 1;
				this.o = false;
				this.B = h;
				this.a(aoq.bb);
			} else if (character3 == 'a') {
				this.n = Integer.MAX_VALUE;
				this.o = false;
				this.B = g;
				this.a(aoq.bb);
			} else if (character3 == 'r') {
				this.n = 1;
				this.o = false;
				this.B = j;
				this.a(aoq.bb);
			} else if (character3 == 's') {
				this.n = 1;
				this.o = true;
				this.C = true;
			} else {
				if (character3 != 'e') {
					this.l.setCursor(integer2);
					throw b.createWithContext(this.l, '@' + String.valueOf(character3));
				}

				this.n = Integer.MAX_VALUE;
				this.o = true;
				this.B = g;
				this.A = aom::aU;
			}

			this.G = this::e;
			if (this.l.canRead() && this.l.peek() == '[') {
				this.l.skip();
				this.G = this::f;
				this.d();
			}
		}
	}

	protected void c() throws CommandSyntaxException {
		if (this.l.canRead()) {
			this.G = this::c;
		}

		int integer2 = this.l.getCursor();
		String string3 = this.l.readString();

		try {
			this.F = UUID.fromString(string3);
			this.o = true;
		} catch (IllegalArgumentException var4) {
			if (string3.isEmpty() || string3.length() > 16) {
				this.l.setCursor(integer2);
				throw a.createWithContext(this.l);
			}

			this.o = false;
			this.D = string3;
		}

		this.n = 1;
	}

	protected void d() throws CommandSyntaxException {
		this.G = this::g;
		this.l.skipWhitespace();

		while (this.l.canRead() && this.l.peek() != ']') {
			this.l.skipWhitespace();
			int integer2 = this.l.getCursor();
			String string3 = this.l.readString();
			fb.a a4 = fb.a(this, string3, integer2);
			this.l.skipWhitespace();
			if (!this.l.canRead() || this.l.peek() != '=') {
				this.l.setCursor(integer2);
				throw f.createWithContext(this.l, string3);
			}

			this.l.skip();
			this.l.skipWhitespace();
			this.G = k;
			a4.handle(this);
			this.l.skipWhitespace();
			this.G = this::h;
			if (this.l.canRead()) {
				if (this.l.peek() != ',') {
					if (this.l.peek() != ']') {
						throw e.createWithContext(this.l);
					}
					break;
				}

				this.l.skip();
				this.G = this::g;
			}
		}

		if (this.l.canRead()) {
			this.l.skip();
			this.G = k;
		} else {
			throw e.createWithContext(this.l);
		}
	}

	public boolean e() {
		this.l.skipWhitespace();
		if (this.l.canRead() && this.l.peek() == '!') {
			this.l.skip();
			this.l.skipWhitespace();
			return true;
		} else {
			return false;
		}
	}

	public boolean f() {
		this.l.skipWhitespace();
		if (this.l.canRead() && this.l.peek() == '#') {
			this.l.skip();
			this.l.skipWhitespace();
			return true;
		} else {
			return false;
		}
	}

	public StringReader g() {
		return this.l;
	}

	public void a(Predicate<aom> predicate) {
		this.A = this.A.and(predicate);
	}

	public void h() {
		this.p = true;
	}

	public bx.c i() {
		return this.q;
	}

	public void a(bx.c c) {
		this.q = c;
	}

	public bx.d j() {
		return this.r;
	}

	public void a(bx.d d) {
		this.r = d;
	}

	public cs k() {
		return this.y;
	}

	public void a(cs cs) {
		this.y = cs;
	}

	public cs l() {
		return this.z;
	}

	public void b(cs cs) {
		this.z = cs;
	}

	@Nullable
	public Double m() {
		return this.s;
	}

	@Nullable
	public Double n() {
		return this.t;
	}

	@Nullable
	public Double o() {
		return this.u;
	}

	public void a(double double1) {
		this.s = double1;
	}

	public void b(double double1) {
		this.t = double1;
	}

	public void c(double double1) {
		this.u = double1;
	}

	public void d(double double1) {
		this.v = double1;
	}

	public void e(double double1) {
		this.w = double1;
	}

	public void f(double double1) {
		this.x = double1;
	}

	@Nullable
	public Double p() {
		return this.v;
	}

	@Nullable
	public Double q() {
		return this.w;
	}

	@Nullable
	public Double r() {
		return this.x;
	}

	public void a(int integer) {
		this.n = integer;
	}

	public void a(boolean boolean1) {
		this.o = boolean1;
	}

	public void a(BiConsumer<dem, List<? extends aom>> biConsumer) {
		this.B = biConsumer;
	}

	public ez t() throws CommandSyntaxException {
		this.E = this.l.getCursor();
		this.G = this::b;
		if (this.l.canRead() && this.l.peek() == '@') {
			if (!this.m) {
				throw c.createWithContext(this.l);
			}

			this.l.skip();
			this.b();
		} else {
			this.c();
		}

		this.I();
		return this.a();
	}

	private static void a(SuggestionsBuilder suggestionsBuilder) {
		suggestionsBuilder.suggest("@p", new ne("argument.entity.selector.nearestPlayer"));
		suggestionsBuilder.suggest("@a", new ne("argument.entity.selector.allPlayers"));
		suggestionsBuilder.suggest("@r", new ne("argument.entity.selector.randomPlayer"));
		suggestionsBuilder.suggest("@s", new ne("argument.entity.selector.self"));
		suggestionsBuilder.suggest("@e", new ne("argument.entity.selector.allEntities"));
	}

	private CompletableFuture<Suggestions> b(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
		consumer.accept(suggestionsBuilder);
		if (this.m) {
			a(suggestionsBuilder);
		}

		return suggestionsBuilder.buildFuture();
	}

	private CompletableFuture<Suggestions> c(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
		SuggestionsBuilder suggestionsBuilder4 = suggestionsBuilder.createOffset(this.E);
		consumer.accept(suggestionsBuilder4);
		return suggestionsBuilder.add(suggestionsBuilder4).buildFuture();
	}

	private CompletableFuture<Suggestions> d(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
		SuggestionsBuilder suggestionsBuilder4 = suggestionsBuilder.createOffset(suggestionsBuilder.getStart() - 1);
		a(suggestionsBuilder4);
		suggestionsBuilder.add(suggestionsBuilder4);
		return suggestionsBuilder.buildFuture();
	}

	private CompletableFuture<Suggestions> e(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
		suggestionsBuilder.suggest(String.valueOf('['));
		return suggestionsBuilder.buildFuture();
	}

	private CompletableFuture<Suggestions> f(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
		suggestionsBuilder.suggest(String.valueOf(']'));
		fb.a(this, suggestionsBuilder);
		return suggestionsBuilder.buildFuture();
	}

	private CompletableFuture<Suggestions> g(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
		fb.a(this, suggestionsBuilder);
		return suggestionsBuilder.buildFuture();
	}

	private CompletableFuture<Suggestions> h(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
		suggestionsBuilder.suggest(String.valueOf(','));
		suggestionsBuilder.suggest(String.valueOf(']'));
		return suggestionsBuilder.buildFuture();
	}

	public boolean u() {
		return this.C;
	}

	public void a(BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> biFunction) {
		this.G = biFunction;
	}

	public CompletableFuture<Suggestions> a(SuggestionsBuilder suggestionsBuilder, Consumer<SuggestionsBuilder> consumer) {
		return (CompletableFuture<Suggestions>)this.G.apply(suggestionsBuilder.createOffset(this.l.getCursor()), consumer);
	}

	public boolean v() {
		return this.H;
	}

	public void b(boolean boolean1) {
		this.H = boolean1;
	}

	public boolean w() {
		return this.I;
	}

	public void c(boolean boolean1) {
		this.I = boolean1;
	}

	public boolean x() {
		return this.J;
	}

	public void d(boolean boolean1) {
		this.J = boolean1;
	}

	public boolean y() {
		return this.K;
	}

	public void e(boolean boolean1) {
		this.K = boolean1;
	}

	public boolean z() {
		return this.L;
	}

	public void f(boolean boolean1) {
		this.L = boolean1;
	}

	public boolean A() {
		return this.M;
	}

	public void g(boolean boolean1) {
		this.M = boolean1;
	}

	public boolean B() {
		return this.N;
	}

	public void h(boolean boolean1) {
		this.N = boolean1;
	}

	public void i(boolean boolean1) {
		this.O = boolean1;
	}

	public void a(aoq<?> aoq) {
		this.P = aoq;
	}

	public void D() {
		this.Q = true;
	}

	public boolean E() {
		return this.P != null;
	}

	public boolean F() {
		return this.Q;
	}

	public boolean G() {
		return this.R;
	}

	public void j(boolean boolean1) {
		this.R = boolean1;
	}

	public boolean H() {
		return this.S;
	}

	public void k(boolean boolean1) {
		this.S = boolean1;
	}
}
