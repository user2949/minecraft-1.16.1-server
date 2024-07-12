import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Objects;

public class ek implements ej {
	private final double a;
	private final double b;
	private final double c;

	public ek(double double1, double double2, double double3) {
		this.a = double1;
		this.b = double2;
		this.c = double3;
	}

	@Override
	public dem a(cz cz) {
		del del3 = cz.i();
		dem dem4 = cz.k().a(cz);
		float float5 = aec.b((del3.j + 90.0F) * (float) (Math.PI / 180.0));
		float float6 = aec.a((del3.j + 90.0F) * (float) (Math.PI / 180.0));
		float float7 = aec.b(-del3.i * (float) (Math.PI / 180.0));
		float float8 = aec.a(-del3.i * (float) (Math.PI / 180.0));
		float float9 = aec.b((-del3.i + 90.0F) * (float) (Math.PI / 180.0));
		float float10 = aec.a((-del3.i + 90.0F) * (float) (Math.PI / 180.0));
		dem dem11 = new dem((double)(float5 * float7), (double)float8, (double)(float6 * float7));
		dem dem12 = new dem((double)(float5 * float9), (double)float10, (double)(float6 * float9));
		dem dem13 = dem11.c(dem12).a(-1.0);
		double double14 = dem11.b * this.c + dem12.b * this.b + dem13.b * this.a;
		double double16 = dem11.c * this.c + dem12.c * this.b + dem13.c * this.a;
		double double18 = dem11.d * this.c + dem12.d * this.b + dem13.d * this.a;
		return new dem(dem4.b + double14, dem4.c + double16, dem4.d + double18);
	}

	@Override
	public del b(cz cz) {
		return del.a;
	}

	@Override
	public boolean a() {
		return true;
	}

	@Override
	public boolean b() {
		return true;
	}

	@Override
	public boolean c() {
		return true;
	}

	public static ek a(StringReader stringReader) throws CommandSyntaxException {
		int integer2 = stringReader.getCursor();
		double double3 = a(stringReader, integer2);
		if (stringReader.canRead() && stringReader.peek() == ' ') {
			stringReader.skip();
			double double5 = a(stringReader, integer2);
			if (stringReader.canRead() && stringReader.peek() == ' ') {
				stringReader.skip();
				double double7 = a(stringReader, integer2);
				return new ek(double3, double5, double7);
			} else {
				stringReader.setCursor(integer2);
				throw eo.a.createWithContext(stringReader);
			}
		} else {
			stringReader.setCursor(integer2);
			throw eo.a.createWithContext(stringReader);
		}
	}

	private static double a(StringReader stringReader, int integer) throws CommandSyntaxException {
		if (!stringReader.canRead()) {
			throw ep.a.createWithContext(stringReader);
		} else if (stringReader.peek() != '^') {
			stringReader.setCursor(integer);
			throw eo.b.createWithContext(stringReader);
		} else {
			stringReader.skip();
			return stringReader.canRead() && stringReader.peek() != ' ' ? stringReader.readDouble() : 0.0;
		}
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof ek)) {
			return false;
		} else {
			ek ek3 = (ek)object;
			return this.a == ek3.a && this.b == ek3.b && this.c == ek3.c;
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.a, this.b, this.c});
	}
}
