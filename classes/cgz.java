import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cgz implements brg.a {
	private static final Logger d = LogManager.getLogger();
	private static final int e = (int)Math.round(Math.log(16.0) / Math.log(2.0)) - 2;
	private static final int f = (int)Math.round(Math.log(256.0) / Math.log(2.0)) - 2;
	public static final int a = 1 << e + e + f;
	public static final int b = (1 << e) - 1;
	public static final int c = (1 << f) - 1;
	private final bre[] g;

	public cgz(bre[] arr) {
		this.g = arr;
	}

	private cgz() {
		this(new bre[a]);
	}

	public cgz(mg mg) {
		this();

		for (int integer3 = 0; integer3 < this.g.length; integer3++) {
			int integer4 = mg.readInt();
			bre bre5 = gl.as.a(integer4);
			if (bre5 == null) {
				d.warn("Received invalid biome id: " + integer4);
				this.g[integer3] = brk.c;
			} else {
				this.g[integer3] = bre5;
			}
		}
	}

	public cgz(bph bph, brh brh) {
		this();
		int integer4 = bph.d() >> 2;
		int integer5 = bph.e() >> 2;

		for (int integer6 = 0; integer6 < this.g.length; integer6++) {
			int integer7 = integer6 & b;
			int integer8 = integer6 >> e + e & c;
			int integer9 = integer6 >> e & b;
			this.g[integer6] = brh.b(integer4 + integer7, integer8, integer5 + integer9);
		}
	}

	public cgz(bph bph, brh brh, @Nullable int[] arr) {
		this();
		int integer5 = bph.d() >> 2;
		int integer6 = bph.e() >> 2;
		if (arr != null) {
			for (int integer7 = 0; integer7 < arr.length; integer7++) {
				this.g[integer7] = gl.as.a(arr[integer7]);
				if (this.g[integer7] == null) {
					int integer8 = integer7 & b;
					int integer9 = integer7 >> e + e & c;
					int integer10 = integer7 >> e & b;
					this.g[integer7] = brh.b(integer5 + integer8, integer9, integer6 + integer10);
				}
			}
		} else {
			for (int integer7x = 0; integer7x < this.g.length; integer7x++) {
				int integer8 = integer7x & b;
				int integer9 = integer7x >> e + e & c;
				int integer10 = integer7x >> e & b;
				this.g[integer7x] = brh.b(integer5 + integer8, integer9, integer6 + integer10);
			}
		}
	}

	public int[] a() {
		int[] arr2 = new int[this.g.length];

		for (int integer3 = 0; integer3 < this.g.length; integer3++) {
			arr2[integer3] = gl.as.a(this.g[integer3]);
		}

		return arr2;
	}

	public void a(mg mg) {
		for (bre bre6 : this.g) {
			mg.writeInt(gl.as.a(bre6));
		}
	}

	public cgz b() {
		return new cgz((bre[])this.g.clone());
	}

	@Override
	public bre b(int integer1, int integer2, int integer3) {
		int integer5 = integer1 & b;
		int integer6 = aec.a(integer2, 0, c);
		int integer7 = integer3 & b;
		return this.g[integer6 << e + e | integer7 << e | integer5];
	}
}
