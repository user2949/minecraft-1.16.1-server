import com.mojang.serialization.Codec;
import java.util.Arrays;

public class cim extends cha {
	public static final Codec<cim> d = cra.a.fieldOf("settings").<cim>xmap(cim::new, cim::g).codec();
	private final cra e;

	public cim(cra cra) {
		super(new bse(cra.c()), new bse(cra.e()), cra.d(), 0L);
		this.e = cra;
	}

	@Override
	protected Codec<? extends cha> a() {
		return d;
	}

	public cra g() {
		return this.e;
	}

	@Override
	public void a(zj zj, cgy cgy) {
	}

	@Override
	public int c() {
		cfj[] arr2 = this.e.g();

		for (int integer3 = 0; integer3 < arr2.length; integer3++) {
			cfj cfj4 = arr2[integer3] == null ? bvs.a.n() : arr2[integer3];
			if (!cio.a.MOTION_BLOCKING.e().test(cfj4)) {
				return integer3 - 1;
			}
		}

		return arr2.length;
	}

	@Override
	public void b(bqc bqc, bqq bqq, cgy cgy) {
		cfj[] arr5 = this.e.g();
		fu.a a6 = new fu.a();
		cio cio7 = cgy.a(cio.a.OCEAN_FLOOR_WG);
		cio cio8 = cgy.a(cio.a.WORLD_SURFACE_WG);

		for (int integer9 = 0; integer9 < arr5.length; integer9++) {
			cfj cfj10 = arr5[integer9];
			if (cfj10 != null) {
				for (int integer11 = 0; integer11 < 16; integer11++) {
					for (int integer12 = 0; integer12 < 16; integer12++) {
						cgy.a(a6.d(integer11, integer9, integer12), cfj10, false);
						cio7.a(integer11, integer9, integer12, cfj10);
						cio8.a(integer11, integer9, integer12, cfj10);
					}
				}
			}
		}
	}

	@Override
	public int a(int integer1, int integer2, cio.a a) {
		cfj[] arr5 = this.e.g();

		for (int integer6 = arr5.length - 1; integer6 >= 0; integer6--) {
			cfj cfj7 = arr5[integer6];
			if (cfj7 != null && a.e().test(cfj7)) {
				return integer6 + 1;
			}
		}

		return 0;
	}

	@Override
	public bpg a(int integer1, int integer2) {
		return new bqk((cfj[])Arrays.stream(this.e.g()).map(cfj -> cfj == null ? bvs.a.n() : cfj).toArray(cfj[]::new));
	}
}
