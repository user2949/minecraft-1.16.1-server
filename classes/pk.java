import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

public class pk implements ni<nl> {
	private double a;
	private double b;
	private double c;
	private float d;
	private float e;
	private Set<pk.a> f;
	private int g;

	public pk() {
	}

	public pk(double double1, double double2, double double3, float float4, float float5, Set<pk.a> set, int integer) {
		this.a = double1;
		this.b = double2;
		this.c = double3;
		this.d = float4;
		this.e = float5;
		this.f = set;
		this.g = integer;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readDouble();
		this.b = mg.readDouble();
		this.c = mg.readDouble();
		this.d = mg.readFloat();
		this.e = mg.readFloat();
		this.f = pk.a.a(mg.readUnsignedByte());
		this.g = mg.i();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeDouble(this.a);
		mg.writeDouble(this.b);
		mg.writeDouble(this.c);
		mg.writeFloat(this.d);
		mg.writeFloat(this.e);
		mg.writeByte(pk.a.a(this.f));
		mg.d(this.g);
	}

	public void a(nl nl) {
		nl.a(this);
	}

	public static enum a {
		X(0),
		Y(1),
		Z(2),
		Y_ROT(3),
		X_ROT(4);

		private final int f;

		private a(int integer3) {
			this.f = integer3;
		}

		private int a() {
			return 1 << this.f;
		}

		private boolean b(int integer) {
			return (integer & this.a()) == this.a();
		}

		public static Set<pk.a> a(int integer) {
			Set<pk.a> set2 = EnumSet.noneOf(pk.a.class);

			for (pk.a a6 : values()) {
				if (a6.b(integer)) {
					set2.add(a6);
				}
			}

			return set2;
		}

		public static int a(Set<pk.a> set) {
			int integer2 = 0;

			for (pk.a a4 : set) {
				integer2 |= a4.a();
			}

			return integer2;
		}
	}
}
