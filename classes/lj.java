import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class lj extends lr {
	public static final lw<lj> a = new lw<lj>() {
		public lj b(DataInput dataInput, int integer, ln ln) throws IOException {
			ln.a(96L);
			return lj.a(dataInput.readInt());
		}

		@Override
		public String a() {
			return "INT";
		}

		@Override
		public String b() {
			return "TAG_Int";
		}

		@Override
		public boolean c() {
			return true;
		}
	};
	private final int b;

	private lj(int integer) {
		this.b = integer;
	}

	public static lj a(int integer) {
		return integer >= -128 && integer <= 1024 ? lj.a.a[integer + 128] : new lj(integer);
	}

	@Override
	public void a(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(this.b);
	}

	@Override
	public byte a() {
		return 3;
	}

	@Override
	public lw<lj> b() {
		return a;
	}

	@Override
	public String toString() {
		return String.valueOf(this.b);
	}

	public lj c() {
		return this;
	}

	public boolean equals(Object object) {
		return this == object ? true : object instanceof lj && this.b == ((lj)object).b;
	}

	public int hashCode() {
		return this.b;
	}

	@Override
	public mr a(String string, int integer) {
		return new nd(String.valueOf(this.b)).a(f);
	}

	@Override
	public long e() {
		return (long)this.b;
	}

	@Override
	public int f() {
		return this.b;
	}

	@Override
	public short g() {
		return (short)(this.b & 65535);
	}

	@Override
	public byte h() {
		return (byte)(this.b & 0xFF);
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
		static final lj[] a = new lj[1153];

		static {
			for (int integer1 = 0; integer1 < a.length; integer1++) {
				a[integer1] = new lj(-128 + integer1);
			}
		}
	}
}
