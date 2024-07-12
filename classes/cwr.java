import javax.annotation.Nullable;

public class cwr implements cws {
	@Nullable
	private final cwo<?, ?> a;
	@Nullable
	private final cwo<?, ?> b;

	public cwr(chl chl, boolean boolean2, boolean boolean3) {
		this.a = boolean2 ? new cwj(chl) : null;
		this.b = boolean3 ? new cwt(chl) : null;
	}

	public void a(fu fu) {
		if (this.a != null) {
			this.a.a(fu);
		}

		if (this.b != null) {
			this.b.a(fu);
		}
	}

	public void a(fu fu, int integer) {
		if (this.a != null) {
			this.a.a(fu, integer);
		}
	}

	public boolean a() {
		return this.b != null && this.b.a() ? true : this.a != null && this.a.a();
	}

	public int a(int integer, boolean boolean2, boolean boolean3) {
		if (this.a != null && this.b != null) {
			int integer5 = integer / 2;
			int integer6 = this.a.a(integer5, boolean2, boolean3);
			int integer7 = integer - integer5 + integer6;
			int integer8 = this.b.a(integer7, boolean2, boolean3);
			return integer6 == 0 && integer8 > 0 ? this.a.a(integer8, boolean2, boolean3) : integer8;
		} else if (this.a != null) {
			return this.a.a(integer, boolean2, boolean3);
		} else {
			return this.b != null ? this.b.a(integer, boolean2, boolean3) : integer;
		}
	}

	@Override
	public void a(go go, boolean boolean2) {
		if (this.a != null) {
			this.a.a(go, boolean2);
		}

		if (this.b != null) {
			this.b.a(go, boolean2);
		}
	}

	public void a(bph bph, boolean boolean2) {
		if (this.a != null) {
			this.a.a(bph, boolean2);
		}

		if (this.b != null) {
			this.b.a(bph, boolean2);
		}
	}

	public cwp a(bqi bqi) {
		if (bqi == bqi.BLOCK) {
			return (cwp)(this.a == null ? cwp.a.INSTANCE : this.a);
		} else {
			return (cwp)(this.b == null ? cwp.a.INSTANCE : this.b);
		}
	}

	public void a(bqi bqi, go go, @Nullable chd chd, boolean boolean4) {
		if (bqi == bqi.BLOCK) {
			if (this.a != null) {
				this.a.a(go.s(), chd, boolean4);
			}
		} else if (this.b != null) {
			this.b.a(go.s(), chd, boolean4);
		}
	}

	public void b(bph bph, boolean boolean2) {
		if (this.a != null) {
			this.a.b(bph, boolean2);
		}

		if (this.b != null) {
			this.b.b(bph, boolean2);
		}
	}

	public int b(fu fu, int integer) {
		int integer4 = this.b == null ? 0 : this.b.b(fu) - integer;
		int integer5 = this.a == null ? 0 : this.a.b(fu);
		return Math.max(integer5, integer4);
	}
}
