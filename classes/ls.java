import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ls extends lr {
	public static final lw<ls> a = new lw<ls>() {
		public ls b(DataInput dataInput, int integer, ln ln) throws IOException {
			ln.a(80L);
			return ls.a(dataInput.readShort());
		}

		@Override
		public String a() {
			return "SHORT";
		}

		@Override
		public String b() {
			return "TAG_Short";
		}

		@Override
		public boolean c() {
			return true;
		}
	};
	private final short b;

	private ls(short short1) {
		this.b = short1;
	}

	public static ls a(short short1) {
		return short1 >= -128 && short1 <= 1024 ? ls.a.a[short1 + 128] : new ls(short1);
	}

	@Override
	public void a(DataOutput dataOutput) throws IOException {
		dataOutput.writeShort(this.b);
	}

	@Override
	public byte a() {
		return 2;
	}

	@Override
	public lw<ls> b() {
		return a;
	}

	@Override
	public String toString() {
		return this.b + "s";
	}

	public ls c() {
		return this;
	}

	public boolean equals(Object object) {
		return this == object ? true : object instanceof ls && this.b == ((ls)object).b;
	}

	public int hashCode() {
		return this.b;
	}

	@Override
	public mr a(String string, int integer) {
		mr mr4 = new nd("s").a(g);
		return new nd(String.valueOf(this.b)).a(mr4).a(f);
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
		return this.b;
	}

	@Override
	public byte h() {
		return (byte)(this.b & 255);
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
		static final ls[] a = new ls[1153];

		static {
			for (int integer1 = 0; integer1 < a.length; integer1++) {
				a[integer1] = new ls((short)(-128 + integer1));
			}
		}
	}
}
