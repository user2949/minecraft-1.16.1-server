import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class lh extends lr {
	public static final lh a = new lh(0.0F);
	public static final lw<lh> b = new lw<lh>() {
		public lh b(DataInput dataInput, int integer, ln ln) throws IOException {
			ln.a(96L);
			return lh.a(dataInput.readFloat());
		}

		@Override
		public String a() {
			return "FLOAT";
		}

		@Override
		public String b() {
			return "TAG_Float";
		}

		@Override
		public boolean c() {
			return true;
		}
	};
	private final float c;

	private lh(float float1) {
		this.c = float1;
	}

	public static lh a(float float1) {
		return float1 == 0.0F ? a : new lh(float1);
	}

	@Override
	public void a(DataOutput dataOutput) throws IOException {
		dataOutput.writeFloat(this.c);
	}

	@Override
	public byte a() {
		return 5;
	}

	@Override
	public lw<lh> b() {
		return b;
	}

	@Override
	public String toString() {
		return this.c + "f";
	}

	public lh c() {
		return this;
	}

	public boolean equals(Object object) {
		return this == object ? true : object instanceof lh && this.c == ((lh)object).c;
	}

	public int hashCode() {
		return Float.floatToIntBits(this.c);
	}

	@Override
	public mr a(String string, int integer) {
		mr mr4 = new nd("f").a(g);
		return new nd(String.valueOf(this.c)).a(mr4).a(f);
	}

	@Override
	public long e() {
		return (long)this.c;
	}

	@Override
	public int f() {
		return aec.d(this.c);
	}

	@Override
	public short g() {
		return (short)(aec.d(this.c) & 65535);
	}

	@Override
	public byte h() {
		return (byte)(aec.d(this.c) & 0xFF);
	}

	@Override
	public double i() {
		return (double)this.c;
	}

	@Override
	public float j() {
		return this.c;
	}

	@Override
	public Number k() {
		return this.c;
	}
}
