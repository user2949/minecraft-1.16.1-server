import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class ep {
	public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("argument.pos.missing.double"));
	public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("argument.pos.missing.int"));
	private final boolean c;
	private final double d;

	public ep(boolean boolean1, double double2) {
		this.c = boolean1;
		this.d = double2;
	}

	public double a(double double1) {
		return this.c ? this.d + double1 : this.d;
	}

	public static ep a(StringReader stringReader, boolean boolean2) throws CommandSyntaxException {
		if (stringReader.canRead() && stringReader.peek() == '^') {
			throw eo.b.createWithContext(stringReader);
		} else if (!stringReader.canRead()) {
			throw a.createWithContext(stringReader);
		} else {
			boolean boolean3 = b(stringReader);
			int integer4 = stringReader.getCursor();
			double double5 = stringReader.canRead() && stringReader.peek() != ' ' ? stringReader.readDouble() : 0.0;
			String string7 = stringReader.getString().substring(integer4, stringReader.getCursor());
			if (boolean3 && string7.isEmpty()) {
				return new ep(true, 0.0);
			} else {
				if (!string7.contains(".") && !boolean3 && boolean2) {
					double5 += 0.5;
				}

				return new ep(boolean3, double5);
			}
		}
	}

	public static ep a(StringReader stringReader) throws CommandSyntaxException {
		if (stringReader.canRead() && stringReader.peek() == '^') {
			throw eo.b.createWithContext(stringReader);
		} else if (!stringReader.canRead()) {
			throw b.createWithContext(stringReader);
		} else {
			boolean boolean2 = b(stringReader);
			double double3;
			if (stringReader.canRead() && stringReader.peek() != ' ') {
				double3 = boolean2 ? stringReader.readDouble() : (double)stringReader.readInt();
			} else {
				double3 = 0.0;
			}

			return new ep(boolean2, double3);
		}
	}

	private static boolean b(StringReader stringReader) {
		boolean boolean2;
		if (stringReader.peek() == '~') {
			boolean2 = true;
			stringReader.skip();
		} else {
			boolean2 = false;
		}

		return boolean2;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof ep)) {
			return false;
		} else {
			ep ep3 = (ep)object;
			return this.c != ep3.c ? false : Double.compare(ep3.d, this.d) == 0;
		}
	}

	public int hashCode() {
		int integer2 = this.c ? 1 : 0;
		long long3 = Double.doubleToLongBits(this.d);
		return 31 * integer2 + (int)(long3 ^ long3 >>> 32);
	}

	public boolean a() {
		return this.c;
	}
}
