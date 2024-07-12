import java.io.IOException;

public class pa implements ni<nl> {
	protected int a;
	protected short b;
	protected short c;
	protected short d;
	protected byte e;
	protected byte f;
	protected boolean g;
	protected boolean h;
	protected boolean i;

	public static long a(double double1) {
		return aec.d(double1 * 4096.0);
	}

	public static dem a(long long1, long long2, long long3) {
		return new dem((double)long1, (double)long2, (double)long3).a(2.4414062E-4F);
	}

	public pa() {
	}

	public pa(int integer) {
		this.a = integer;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
	}

	public void a(nl nl) {
		nl.a(this);
	}

	public String toString() {
		return "Entity_" + super.toString();
	}

	public static class a extends pa {
		public a() {
			this.i = true;
		}

		public a(int integer, short short2, short short3, short short4, boolean boolean5) {
			super(integer);
			this.b = short2;
			this.c = short3;
			this.d = short4;
			this.g = boolean5;
			this.i = true;
		}

		@Override
		public void a(mg mg) throws IOException {
			super.a(mg);
			this.b = mg.readShort();
			this.c = mg.readShort();
			this.d = mg.readShort();
			this.g = mg.readBoolean();
		}

		@Override
		public void b(mg mg) throws IOException {
			super.b(mg);
			mg.writeShort(this.b);
			mg.writeShort(this.c);
			mg.writeShort(this.d);
			mg.writeBoolean(this.g);
		}
	}

	public static class b extends pa {
		public b() {
			this.h = true;
			this.i = true;
		}

		public b(int integer, short short2, short short3, short short4, byte byte5, byte byte6, boolean boolean7) {
			super(integer);
			this.b = short2;
			this.c = short3;
			this.d = short4;
			this.e = byte5;
			this.f = byte6;
			this.g = boolean7;
			this.h = true;
			this.i = true;
		}

		@Override
		public void a(mg mg) throws IOException {
			super.a(mg);
			this.b = mg.readShort();
			this.c = mg.readShort();
			this.d = mg.readShort();
			this.e = mg.readByte();
			this.f = mg.readByte();
			this.g = mg.readBoolean();
		}

		@Override
		public void b(mg mg) throws IOException {
			super.b(mg);
			mg.writeShort(this.b);
			mg.writeShort(this.c);
			mg.writeShort(this.d);
			mg.writeByte(this.e);
			mg.writeByte(this.f);
			mg.writeBoolean(this.g);
		}
	}

	public static class c extends pa {
		public c() {
			this.h = true;
		}

		public c(int integer, byte byte2, byte byte3, boolean boolean4) {
			super(integer);
			this.e = byte2;
			this.f = byte3;
			this.h = true;
			this.g = boolean4;
		}

		@Override
		public void a(mg mg) throws IOException {
			super.a(mg);
			this.e = mg.readByte();
			this.f = mg.readByte();
			this.g = mg.readBoolean();
		}

		@Override
		public void b(mg mg) throws IOException {
			super.b(mg);
			mg.writeByte(this.e);
			mg.writeByte(this.f);
			mg.writeBoolean(this.g);
		}
	}
}
