import com.google.common.base.Joiner;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dn.h;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class my extends mn implements mt {
	private static final Logger g = LogManager.getLogger();
	protected final boolean d;
	protected final String e;
	@Nullable
	protected final h f;

	@Nullable
	private static h d(String string) {
		try {
			return new dn().a(new StringReader(string));
		} catch (CommandSyntaxException var2) {
			return null;
		}
	}

	public my(String string, boolean boolean2) {
		this(string, d(string), boolean2);
	}

	protected my(String string, @Nullable h h, boolean boolean3) {
		this.e = string;
		this.f = h;
		this.d = boolean3;
	}

	protected abstract Stream<le> a(cz cz) throws CommandSyntaxException;

	public String g() {
		return this.e;
	}

	public boolean h() {
		return this.d;
	}

	@Override
	public mx a(@Nullable cz cz, @Nullable aom aom, int integer) throws CommandSyntaxException {
		if (cz != null && this.f != null) {
			Stream<String> stream5 = this.a(cz).flatMap(le -> {
				try {
					return this.f.a(le).stream();
				} catch (CommandSyntaxException var3) {
					return Stream.empty();
				}
			}).map(lu::f_);
			return (mx)(this.d ? (mx)stream5.flatMap(string -> {
				try {
					mx mx5 = mr.a.a(string);
					return Stream.of(ms.a(cz, mx5, aom, integer));
				} catch (Exception var5) {
					g.warn("Failed to parse component: " + string, (Throwable)var5);
					return Stream.of();
				}
			}).reduce((mx1, mx2) -> mx1.c(", ").a(mx2)).orElse(new nd("")) : new nd(Joiner.on(", ").join(stream5.iterator())));
		} else {
			return new nd("");
		}
	}

	public static class a extends my {
		private final String g;
		@Nullable
		private final ej h;

		public a(String string1, boolean boolean2, String string3) {
			super(string1, boolean2);
			this.g = string3;
			this.h = this.d(this.g);
		}

		@Nullable
		private ej d(String string) {
			try {
				return eh.a().a(new StringReader(string));
			} catch (CommandSyntaxException var3) {
				return null;
			}
		}

		private a(String string1, @Nullable h h, boolean boolean3, String string4, @Nullable ej ej) {
			super(string1, h, boolean3);
			this.g = string4;
			this.h = ej;
		}

		@Nullable
		public String i() {
			return this.g;
		}

		public my.a f() {
			return new my.a(this.e, this.f, this.d, this.g, this.h);
		}

		@Override
		protected Stream<le> a(cz cz) {
			if (this.h != null) {
				zd zd3 = cz.e();
				fu fu4 = this.h.c(cz);
				if (zd3.p(fu4)) {
					cdl cdl5 = zd3.c(fu4);
					if (cdl5 != null) {
						return Stream.of(cdl5.a(new le()));
					}
				}
			}

			return Stream.empty();
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			} else if (!(object instanceof my.a)) {
				return false;
			} else {
				my.a a3 = (my.a)object;
				return Objects.equals(this.g, a3.g) && Objects.equals(this.e, a3.e) && super.equals(object);
			}
		}

		@Override
		public String toString() {
			return "BlockPosArgument{pos='" + this.g + '\'' + "path='" + this.e + '\'' + ", siblings=" + this.a + ", style=" + this.c() + '}';
		}
	}

	public static class b extends my {
		private final String g;
		@Nullable
		private final ez h;

		public b(String string1, boolean boolean2, String string3) {
			super(string1, boolean2);
			this.g = string3;
			this.h = d(string3);
		}

		@Nullable
		private static ez d(String string) {
			try {
				fa fa2 = new fa(new StringReader(string));
				return fa2.t();
			} catch (CommandSyntaxException var2) {
				return null;
			}
		}

		private b(String string1, @Nullable h h, boolean boolean3, String string4, @Nullable ez ez) {
			super(string1, h, boolean3);
			this.g = string4;
			this.h = ez;
		}

		public String i() {
			return this.g;
		}

		public my.b f() {
			return new my.b(this.e, this.f, this.d, this.g, this.h);
		}

		@Override
		protected Stream<le> a(cz cz) throws CommandSyntaxException {
			if (this.h != null) {
				List<? extends aom> list3 = this.h.b(cz);
				return list3.stream().map(bz::b);
			} else {
				return Stream.empty();
			}
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			} else if (!(object instanceof my.b)) {
				return false;
			} else {
				my.b b3 = (my.b)object;
				return Objects.equals(this.g, b3.g) && Objects.equals(this.e, b3.e) && super.equals(object);
			}
		}

		@Override
		public String toString() {
			return "EntityNbtComponent{selector='" + this.g + '\'' + "path='" + this.e + '\'' + ", siblings=" + this.a + ", style=" + this.c() + '}';
		}
	}

	public static class c extends my {
		private final uh g;

		public c(String string, boolean boolean2, uh uh) {
			super(string, boolean2);
			this.g = uh;
		}

		public c(String string, @Nullable h h, boolean boolean3, uh uh) {
			super(string, h, boolean3);
			this.g = uh;
		}

		public uh i() {
			return this.g;
		}

		public my.c f() {
			return new my.c(this.e, this.f, this.d, this.g);
		}

		@Override
		protected Stream<le> a(cz cz) {
			le le3 = cz.j().aG().a(this.g);
			return Stream.of(le3);
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			} else if (!(object instanceof my.c)) {
				return false;
			} else {
				my.c c3 = (my.c)object;
				return Objects.equals(this.g, c3.g) && Objects.equals(this.e, c3.e) && super.equals(object);
			}
		}

		@Override
		public String toString() {
			return "StorageNbtComponent{id='" + this.g + '\'' + "path='" + this.e + '\'' + ", siblings=" + this.a + ", style=" + this.c() + '}';
		}
	}
}
