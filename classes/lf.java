import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class lf extends lr {
	public static final lf a = new lf(0.0);
	public static final lw<lf> b = new lw<lf>() {
		public lf b(DataInput dataInput, int integer, ln ln) throws IOException {
			ln.a(128L);
			return lf.a(dataInput.readDouble());
		}

		@Override
		public String a() {
			return "DOUBLE";
		}

		@Override
		public String b() {
			return "TAG_Double";
		}

		@Override
		public boolean c() {
			return true;
		}
	};
	private final double c;

	private lf(double double1) {
		this.c = double1;
	}

	public static lf a(double double1) {
		return double1 == 0.0 ? a : new lf(double1);
	}

	@Override
	public void a(DataOutput dataOutput) throws IOException {
		dataOutput.writeDouble(this.c);
	}

	@Override
	public byte a() {
		return 6;
	}

	@Override
	public lw<lf> b() {
		return b;
	}

	@Override
	public String toString() {
		return this.c + "d";
	}

	public lf c() {
		return this;
	}

	public boolean equals(Object object) {
		return this == object ? true : object instanceof lf && this.c == ((lf)object).c;
	}

	public int hashCode() {
		long long2 = Double.doubleToLongBits(this.c);
		return (int)(long2 ^ long2 >>> 32);
	}

	@Override
	public mr a(String string, int integer) {
		mr mr4 = new nd("d").a(g);
		return new nd(String.valueOf(this.c)).a(mr4).a(f);
	}

	@Override
	public long e() {
		return (long)Math.floor(this.c);
	}

	@Override
	public int f() {
		return aec.c(this.c);
	}

	@Override
	public short g() {
		return (short)(aec.c(this.c) & 65535);
	}

	@Override
	public byte h() {
		return (byte)(aec.c(this.c) & 0xFF);
	}

	@Override
	public double i() {
		return this.c;
	}

	@Override
	public float j() {
		return (float)this.c;
	}

	@Override
	public Number k() {
		return this.c;
	}
}
