import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class eq implements ej {
	private final ep a;
	private final ep b;
	private final ep c;

	public eq(ep ep1, ep ep2, ep ep3) {
		this.a = ep1;
		this.b = ep2;
		this.c = ep3;
	}

	@Override
	public dem a(cz cz) {
		dem dem3 = cz.d();
		return new dem(this.a.a(dem3.b), this.b.a(dem3.c), this.c.a(dem3.d));
	}

	@Override
	public del b(cz cz) {
		del del3 = cz.i();
		return new del((float)this.a.a((double)del3.i), (float)this.b.a((double)del3.j));
	}

	@Override
	public boolean a() {
		return this.a.a();
	}

	@Override
	public boolean b() {
		return this.b.a();
	}

	@Override
	public boolean c() {
		return this.c.a();
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof eq)) {
			return false;
		} else {
			eq eq3 = (eq)object;
			if (!this.a.equals(eq3.a)) {
				return false;
			} else {
				return !this.b.equals(eq3.b) ? false : this.c.equals(eq3.c);
			}
		}
	}

	public static eq a(StringReader stringReader) throws CommandSyntaxException {
		int integer2 = stringReader.getCursor();
		ep ep3 = ep.a(stringReader);
		if (stringReader.canRead() && stringReader.peek() == ' ') {
			stringReader.skip();
			ep ep4 = ep.a(stringReader);
			if (stringReader.canRead() && stringReader.peek() == ' ') {
				stringReader.skip();
				ep ep5 = ep.a(stringReader);
				return new eq(ep3, ep4, ep5);
			} else {
				stringReader.setCursor(integer2);
				throw eo.a.createWithContext(stringReader);
			}
		} else {
			stringReader.setCursor(integer2);
			throw eo.a.createWithContext(stringReader);
		}
	}

	public static eq a(StringReader stringReader, boolean boolean2) throws CommandSyntaxException {
		int integer3 = stringReader.getCursor();
		ep ep4 = ep.a(stringReader, boolean2);
		if (stringReader.canRead() && stringReader.peek() == ' ') {
			stringReader.skip();
			ep ep5 = ep.a(stringReader, false);
			if (stringReader.canRead() && stringReader.peek() == ' ') {
				stringReader.skip();
				ep ep6 = ep.a(stringReader, boolean2);
				return new eq(ep4, ep5, ep6);
			} else {
				stringReader.setCursor(integer3);
				throw eo.a.createWithContext(stringReader);
			}
		} else {
			stringReader.setCursor(integer3);
			throw eo.a.createWithContext(stringReader);
		}
	}

	public static eq d() {
		return new eq(new ep(true, 0.0), new ep(true, 0.0), new ep(true, 0.0));
	}

	public int hashCode() {
		int integer2 = this.a.hashCode();
		integer2 = 31 * integer2 + this.b.hashCode();
		return 31 * integer2 + this.c.hashCode();
	}
}
