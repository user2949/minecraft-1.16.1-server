import java.io.IOException;

public class ob implements ni<nl> {
	private bph a;
	private ob.a[] b;

	public ob() {
	}

	public ob(int integer, short[] arr, chj chj) {
		this.a = chj.g();
		this.b = new ob.a[integer];

		for (int integer5 = 0; integer5 < this.b.length; integer5++) {
			this.b[integer5] = new ob.a(arr[integer5], chj);
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = new bph(mg.readInt(), mg.readInt());
		this.b = new ob.a[mg.i()];

		for (int integer3 = 0; integer3 < this.b.length; integer3++) {
			this.b[integer3] = new ob.a(mg.readShort(), bvr.m.a(mg.i()));
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeInt(this.a.b);
		mg.writeInt(this.a.c);
		mg.d(this.b.length);

		for (ob.a a6 : this.b) {
			mg.writeShort(a6.b());
			mg.d(bvr.i(a6.c()));
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}

	public class a {
		private final short b;
		private final cfj c;

		public a(short short2, cfj cfj) {
			this.b = short2;
			this.c = cfj;
		}

		public a(short short2, chj chj) {
			this.b = short2;
			this.c = chj.d_(this.a());
		}

		public fu a() {
			return new fu(ob.this.a.a(this.b >> 12 & 15, this.b & 255, this.b >> 8 & 15));
		}

		public short b() {
			return this.b;
		}

		public cfj c() {
			return this.c;
		}
	}
}
