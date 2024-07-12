import java.util.List;

public class aub extends aug {
	private int a;
	private final apg b;
	private bec c;
	private atq d;

	public aub(apg apg) {
		this.b = apg;
	}

	@Override
	public boolean a() {
		List<bft> list2 = this.b.l.a(bft.class, this.b.cb().g(5.0));
		boolean boolean3 = false;

		for (bft bft5 : list2) {
			aom aom6 = bft5.cl();
			if (aom6 instanceof bec && (aec.e(((bec)aom6).aY) > 0.0F || aec.e(((bec)aom6).ba) > 0.0F)) {
				boolean3 = true;
				break;
			}
		}

		return this.c != null && (aec.e(this.c.aY) > 0.0F || aec.e(this.c.ba) > 0.0F) || boolean3;
	}

	@Override
	public boolean D_() {
		return true;
	}

	@Override
	public boolean b() {
		return this.c != null && this.c.bn() && (aec.e(this.c.aY) > 0.0F || aec.e(this.c.ba) > 0.0F);
	}

	@Override
	public void c() {
		for (bft bft4 : this.b.l.a(bft.class, this.b.cb().g(5.0))) {
			if (bft4.cl() != null && bft4.cl() instanceof bec) {
				this.c = (bec)bft4.cl();
				break;
			}
		}

		this.a = 0;
		this.d = atq.GO_TO_BOAT;
	}

	@Override
	public void d() {
		this.c = null;
	}

	@Override
	public void e() {
		boolean boolean2 = aec.e(this.c.aY) > 0.0F || aec.e(this.c.ba) > 0.0F;
		float float3 = this.d == atq.GO_IN_BOAT_DIRECTION ? (boolean2 ? 0.01F : 0.0F) : 0.015F;
		this.b.a(float3, new dem((double)this.b.aY, (double)this.b.aZ, (double)this.b.ba));
		this.b.a(apd.SELF, this.b.cB());
		if (--this.a <= 0) {
			this.a = 10;
			if (this.d == atq.GO_TO_BOAT) {
				fu fu4 = this.c.cA().a(this.c.bY().f());
				fu4 = fu4.b(0, -1, 0);
				this.b.x().a((double)fu4.u(), (double)fu4.v(), (double)fu4.w(), 1.0);
				if (this.b.g(this.c) < 4.0F) {
					this.a = 0;
					this.d = atq.GO_IN_BOAT_DIRECTION;
				}
			} else if (this.d == atq.GO_IN_BOAT_DIRECTION) {
				fz fz4 = this.c.bZ();
				fu fu5 = this.c.cA().a(fz4, 10);
				this.b.x().a((double)fu5.u(), (double)(fu5.v() - 1), (double)fu5.w(), 1.0);
				if (this.b.g(this.c) > 12.0F) {
					this.a = 0;
					this.d = atq.GO_TO_BOAT;
				}
			}
		}
	}
}
