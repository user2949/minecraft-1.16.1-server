import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class lc extends lr {
	public static final lw<lc> a = new lw<lc>() {
		public lc b(DataInput dataInput, int integer, ln ln) throws IOException {
			ln.a(72L);
			return lc.a(dataInput.readByte());
		}

		@Override
		public String a() {
			return "BYTE";
		}

		@Override
		public String b() {
			return "TAG_Byte";
		}

		@Override
		public boolean c() {
			return true;
		}
	};
	public static final lc b = a((byte)0);
	public static final lc c = a((byte)1);
	private final byte h;

	private lc(byte byte1) {
		this.h = byte1;
	}

	public static lc a(byte byte1) {
		return lc.a.a[128 + byte1];
	}

	public static lc a(boolean boolean1) {
		return boolean1 ? c : b;
	}

	@Override
	public void a(DataOutput dataOutput) throws IOException {
		dataOutput.writeByte(this.h);
	}

	@Override
	public byte a() {
		return 1;
	}

	@Override
	public lw<lc> b() {
		return a;
	}

	@Override
	public String toString() {
		return this.h + "b";
	}

	public lc c() {
		return this;
	}

	public boolean equals(Object object) {
		return this == object ? true : object instanceof lc && this.h == ((lc)object).h;
	}

	public int hashCode() {
		return this.h;
	}

	@Override
	public mr a(String string, int integer) {
		mr mr4 = new nd("b").a(g);
		return new nd(String.valueOf(this.h)).a(mr4).a(f);
	}

	@Override
	public long e() {
		return (long)this.h;
	}

	@Override
	public int f() {
		return this.h;
	}

	@Override
	public short g() {
		return (short)this.h;
	}

	@Override
	public byte h() {
		return this.h;
	}

	@Override
	public double i() {
		return (double)this.h;
	}

	@Override
	public float j() {
		return (float)this.h;
	}

	@Override
	public Number k() {
		return this.h;
	}

	static class a {
		private static final lc[] a = new lc[256];

		static {
			for (int integer1 = 0; integer1 < a.length; integer1++) {
				a[integer1] = new lc((byte)(integer1 - 128));
			}
		}
	}
}
