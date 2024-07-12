import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Pattern;

public class lv {
	public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("argument.nbt.trailing"));
	public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("argument.nbt.expected.key"));
	public static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("argument.nbt.expected.value"));
	public static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType(
		(object1, object2) -> new ne("argument.nbt.list.mixed", object1, object2)
	);
	public static final Dynamic2CommandExceptionType e = new Dynamic2CommandExceptionType(
		(object1, object2) -> new ne("argument.nbt.array.mixed", object1, object2)
	);
	public static final DynamicCommandExceptionType f = new DynamicCommandExceptionType(object -> new ne("argument.nbt.array.invalid", object));
	private static final Pattern g = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
	private static final Pattern h = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
	private static final Pattern i = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
	private static final Pattern j = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
	private static final Pattern k = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
	private static final Pattern l = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
	private static final Pattern m = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
	private final StringReader n;

	public static le a(String string) throws CommandSyntaxException {
		return new lv(new StringReader(string)).a();
	}

	@VisibleForTesting
	le a() throws CommandSyntaxException {
		le le2 = this.f();
		this.n.skipWhitespace();
		if (this.n.canRead()) {
			throw a.createWithContext(this.n);
		} else {
			return le2;
		}
	}

	public lv(StringReader stringReader) {
		this.n = stringReader;
	}

	protected String b() throws CommandSyntaxException {
		this.n.skipWhitespace();
		if (!this.n.canRead()) {
			throw b.createWithContext(this.n);
		} else {
			return this.n.readString();
		}
	}

	protected lu c() throws CommandSyntaxException {
		this.n.skipWhitespace();
		int integer2 = this.n.getCursor();
		if (StringReader.isQuotedStringStart(this.n.peek())) {
			return lt.a(this.n.readQuotedString());
		} else {
			String string3 = this.n.readUnquotedString();
			if (string3.isEmpty()) {
				this.n.setCursor(integer2);
				throw c.createWithContext(this.n);
			} else {
				return this.b(string3);
			}
		}
	}

	private lu b(String string) {
		try {
			if (i.matcher(string).matches()) {
				return lh.a(Float.parseFloat(string.substring(0, string.length() - 1)));
			}

			if (j.matcher(string).matches()) {
				return lc.a(Byte.parseByte(string.substring(0, string.length() - 1)));
			}

			if (k.matcher(string).matches()) {
				return lm.a(Long.parseLong(string.substring(0, string.length() - 1)));
			}

			if (l.matcher(string).matches()) {
				return ls.a(Short.parseShort(string.substring(0, string.length() - 1)));
			}

			if (m.matcher(string).matches()) {
				return lj.a(Integer.parseInt(string));
			}

			if (h.matcher(string).matches()) {
				return lf.a(Double.parseDouble(string.substring(0, string.length() - 1)));
			}

			if (g.matcher(string).matches()) {
				return lf.a(Double.parseDouble(string));
			}

			if ("true".equalsIgnoreCase(string)) {
				return lc.c;
			}

			if ("false".equalsIgnoreCase(string)) {
				return lc.b;
			}
		} catch (NumberFormatException var3) {
		}

		return lt.a(string);
	}

	public lu d() throws CommandSyntaxException {
		this.n.skipWhitespace();
		if (!this.n.canRead()) {
			throw c.createWithContext(this.n);
		} else {
			char character2 = this.n.peek();
			if (character2 == '{') {
				return this.f();
			} else {
				return character2 == '[' ? this.e() : this.c();
			}
		}
	}

	protected lu e() throws CommandSyntaxException {
		return this.n.canRead(3) && !StringReader.isQuotedStringStart(this.n.peek(1)) && this.n.peek(2) == ';' ? this.h() : this.g();
	}

	public le f() throws CommandSyntaxException {
		this.a('{');
		le le2 = new le();
		this.n.skipWhitespace();

		while (this.n.canRead() && this.n.peek() != '}') {
			int integer3 = this.n.getCursor();
			String string4 = this.b();
			if (string4.isEmpty()) {
				this.n.setCursor(integer3);
				throw b.createWithContext(this.n);
			}

			this.a(':');
			le2.a(string4, this.d());
			if (!this.i()) {
				break;
			}

			if (!this.n.canRead()) {
				throw b.createWithContext(this.n);
			}
		}

		this.a('}');
		return le2;
	}

	private lu g() throws CommandSyntaxException {
		this.a('[');
		this.n.skipWhitespace();
		if (!this.n.canRead()) {
			throw c.createWithContext(this.n);
		} else {
			lk lk2 = new lk();
			lw<?> lw3 = null;

			while (this.n.peek() != ']') {
				int integer4 = this.n.getCursor();
				lu lu5 = this.d();
				lw<?> lw6 = lu5.b();
				if (lw3 == null) {
					lw3 = lw6;
				} else if (lw6 != lw3) {
					this.n.setCursor(integer4);
					throw d.createWithContext(this.n, lw6.b(), lw3.b());
				}

				lk2.add(lu5);
				if (!this.i()) {
					break;
				}

				if (!this.n.canRead()) {
					throw c.createWithContext(this.n);
				}
			}

			this.a(']');
			return lk2;
		}
	}

	private lu h() throws CommandSyntaxException {
		this.a('[');
		int integer2 = this.n.getCursor();
		char character3 = this.n.read();
		this.n.read();
		this.n.skipWhitespace();
		if (!this.n.canRead()) {
			throw c.createWithContext(this.n);
		} else if (character3 == 'B') {
			return new lb(this.a(lb.a, lc.a));
		} else if (character3 == 'L') {
			return new ll(this.a(ll.a, lm.a));
		} else if (character3 == 'I') {
			return new li(this.a(li.a, lj.a));
		} else {
			this.n.setCursor(integer2);
			throw f.createWithContext(this.n, String.valueOf(character3));
		}
	}

	private <T extends Number> List<T> a(lw<?> lw1, lw<?> lw2) throws CommandSyntaxException {
		List<T> list4 = Lists.<T>newArrayList();

		while (this.n.peek() != ']') {
			int integer5 = this.n.getCursor();
			lu lu6 = this.d();
			lw<?> lw7 = lu6.b();
			if (lw7 != lw2) {
				this.n.setCursor(integer5);
				throw e.createWithContext(this.n, lw7.b(), lw1.b());
			}

			if (lw2 == lc.a) {
				list4.add(((lr)lu6).h());
			} else if (lw2 == lm.a) {
				list4.add(((lr)lu6).e());
			} else {
				list4.add(((lr)lu6).f());
			}

			if (!this.i()) {
				break;
			}

			if (!this.n.canRead()) {
				throw c.createWithContext(this.n);
			}
		}

		this.a(']');
		return list4;
	}

	private boolean i() {
		this.n.skipWhitespace();
		if (this.n.canRead() && this.n.peek() == ',') {
			this.n.skip();
			this.n.skipWhitespace();
			return true;
		} else {
			return false;
		}
	}

	private void a(char character) throws CommandSyntaxException {
		this.n.skipWhitespace();
		this.n.expect(character);
	}
}
