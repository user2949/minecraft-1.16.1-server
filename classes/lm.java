import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class lm extends lr {
	public static final lw<lm> a = new lw<lm>() {
		public lm b(DataInput dataInput, int integer, ln ln) throws IOException {
			ln.a(128L);
			return lm.a(dataInput.readLong());
		}

		@Override
		public String a() {
			return "LONG";
		}

		@Override
		public String b() {
			return "TAG_Long";
		}

		@Override
		public boolean c() {
			return true;
		}
	};
	private final long b;

	private lm(long long1) {
		this.b = long1;
	}

	public static lm a(long long1) {
		return long1 >= -128L && long1 <= 1024L ? lm.a.a[(int)long1 + 128] : new lm(long1);
	}

	@Override
	public void a(DataOutput dataOutput) throws IOException {
		dataOutput.writeLong(this.b);
	}

	@Override
	public byte a() {
		return 4;
	}

	@Override
	public lw<lm> b() {
		return a;
	}

	@Override
	public String toString() {
		return this.b + "L";
	}

	public lm c() {
		return this;
	}

	public boolean equals(Object object) {
		return this == object ? true : object instanceof lm && this.b == ((lm)object).b;
	}

	public int hashCode() {
		return (int)(this.b ^ this.b >>> 32);
	}

	@Override
	public mr a(String string, int integer) {
		mr mr4 = new nd("L").a(g);
		return new nd(String.valueOf(this.b)).a(mr4).a(f);
	}

	@Override
	public long e() {
		return this.b;
	}

	@Override
	public int f() {
		return (int)(this.b & -1L);
	}

	@Override
	public short g() {
		return (short)((int)(this.b & 65535L));
	}

	@Override
	public byte h() {
		return (byte)((int)(this.b & 255L));
	}

	@Override
	public double i() {
		return (double)this.b;
	}

	@Override
	public float j() {
		return (float)this.b;
	}

	@Override
	public Number k() {
		return this.b;
	}

	static class a {
		static final lm[] a = new lm[1153];

		static {
			for (int integer1 = 0; integer1 < a.length; integer1++) {
				a[integer1] = new lm((long)(-128 + integer1));
			}
		}
	}
}
