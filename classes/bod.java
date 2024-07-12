public class bod extends bnw {
	public bod(bnw.a a, aor... arr) {
		super(a, bnx.ARMOR_FEET, arr);
	}

	@Override
	public int a(int integer) {
		return integer * 10;
	}

	@Override
	public int b(int integer) {
		return this.a(integer) + 15;
	}

	@Override
	public boolean b() {
		return true;
	}

	@Override
	public int a() {
		return 2;
	}

	public static void a(aoy aoy, bqb bqb, fu fu, int integer) {
		if (aoy.aj()) {
			cfj cfj5 = bvs.iI.n();
			float float6 = (float)Math.min(16, 2 + integer);
			fu.a a7 = new fu.a();

			for (fu fu9 : fu.a(fu.a((double)(-float6), -1.0, (double)(-float6)), fu.a((double)float6, -1.0, (double)float6))) {
				if (fu9.a(aoy.cz(), (double)float6)) {
					a7.d(fu9.u(), fu9.v() + 1, fu9.w());
					cfj cfj10 = bqb.d_(a7);
					if (cfj10.g()) {
						cfj cfj11 = bqb.d_(fu9);
						if (cfj11.c() == cxd.i && (Integer)cfj11.c(bze.a) == 0 && cfj5.a((bqd)bqb, fu9) && bqb.a(cfj5, fu9, der.a())) {
							bqb.a(fu9, cfj5);
							bqb.G().a(fu9, bvs.iI, aec.a(aoy.cX(), 60, 120));
						}
					}
				}
			}
		}
	}

	@Override
	public boolean a(bnw bnw) {
		return super.a(bnw) && bnw != boa.i;
	}
}
