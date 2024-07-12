public class arv<E extends aoy> extends aqh<E> {
	private boolean b;
	private boolean c;
	private final adx d;
	private final aqh<? super E> e;
	private int f;

	public arv(aqh<? super E> aqh, adx adx) {
		this(aqh, false, adx);
	}

	public arv(aqh<? super E> aqh, boolean boolean2, adx adx) {
		super(aqh.a);
		this.e = aqh;
		this.b = !boolean2;
		this.d = adx;
	}

	@Override
	protected boolean a(zd zd, E aoy) {
		if (!this.e.a(zd, aoy)) {
			return false;
		} else {
			if (this.b) {
				this.a(zd);
				this.b = false;
			}

			if (this.f > 0) {
				this.f--;
			}

			return !this.c && this.f == 0;
		}
	}

	@Override
	protected void a(zd zd, E aoy, long long3) {
		this.e.a(zd, aoy, long3);
	}

	@Override
	protected boolean b(zd zd, E aoy, long long3) {
		return this.e.b(zd, aoy, long3);
	}

	@Override
	protected void d(zd zd, E aoy, long long3) {
		this.e.d(zd, aoy, long3);
		this.c = this.e.a() == aqh.a.RUNNING;
	}

	@Override
	protected void c(zd zd, E aoy, long long3) {
		this.a(zd);
		this.e.c(zd, aoy, long3);
	}

	private void a(zd zd) {
		this.f = this.d.a(zd.t);
	}

	@Override
	protected boolean a(long long1) {
		return false;
	}

	@Override
	public String toString() {
		return "RunSometimes: " + this.e;
	}
}
