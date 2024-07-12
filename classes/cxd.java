public final class cxd {
	public static final cxd a = new cxd.a(cxe.b).c().i().b().e().h();
	public static final cxd b = new cxd.a(cxe.b).c().i().b().e().h();
	public static final cxd c = new cxd.a(cxe.b).c().i().b().g().h();
	public static final cxd d = new cxd.a(cxe.e).c().i().b().d().h();
	public static final cxd e = new cxd.a(cxe.i).c().i().b().f().h();
	public static final cxd f = new cxd.a(cxe.n).c().i().b().f().h();
	public static final cxd g = new cxd.a(cxe.i).c().i().b().f().e().d().h();
	public static final cxd h = new cxd.a(cxe.n).c().i().b().f().e().h();
	public static final cxd i = new cxd.a(cxe.n).c().i().b().f().e().a().h();
	public static final cxd j = new cxd.a(cxe.n).c().i().b().f().e().a().h();
	public static final cxd k = new cxd.a(cxe.f).c().i().b().f().e().a().h();
	public static final cxd l = new cxd.a(cxe.j).c().i().b().f().e().h();
	public static final cxd m = new cxd.a(cxe.b).c().i().b().f().e().h();
	public static final cxd n = new cxd.a(cxe.b).c().i().b().f().h();
	public static final cxd o = new cxd.a(cxe.e).c().i().f().h();
	public static final cxd p = new cxd.a(cxe.b).h();
	public static final cxd q = new cxd.a(cxe.k).h();
	public static final cxd r = new cxd.a(cxe.l).h();
	public static final cxd s = new cxd.a(cxe.c).h();
	public static final cxd t = new cxd.a(cxe.g).h();
	public static final cxd u = new cxd.a(cxe.d).h();
	public static final cxd v = new cxd.a(cxe.t).h();
	public static final cxd w = new cxd.a(cxe.z).h();
	public static final cxd x = new cxd.a(cxe.o).d().h();
	public static final cxd y = new cxd.a(cxe.o).h();
	public static final cxd z = new cxd.a(cxe.o).d().f().c().h();
	public static final cxd A = new cxd.a(cxe.o).d().f().h();
	public static final cxd B = new cxd.a(cxe.e).d().h();
	public static final cxd C = new cxd.a(cxe.f).d().i().h();
	public static final cxd D = new cxd.a(cxe.i).d().i().f().h();
	public static final cxd E = new cxd.a(cxe.b).i().h();
	public static final cxd F = new cxd.a(cxe.g).i().h();
	public static final cxd G = new cxd.a(cxe.i).i().f().h();
	public static final cxd H = new cxd.a(cxe.m).h();
	public static final cxd I = new cxd.a(cxe.h).h();
	public static final cxd J = new cxd.a(cxe.j).h();
	public static final cxd K = new cxd.a(cxe.h).g().h();
	public static final cxd L = new cxd.a(cxe.b).g().h();
	public static final cxd M = new cxd.a(cxe.m).g().h();
	public static final cxd N = new cxd.a(cxe.i).f().h();
	public static final cxd O = new cxd.a(cxe.i).f().h();
	public static final cxd P = new cxd.a(cxe.i).f().h();
	public static final cxd Q = new cxd.a(cxe.b).f().h();
	private final cxe R;
	private final cxf S;
	private final boolean T;
	private final boolean U;
	private final boolean V;
	private final boolean W;
	private final boolean X;
	private final boolean Y;

	public cxd(cxe cxe, boolean boolean2, boolean boolean3, boolean boolean4, boolean boolean5, boolean boolean6, boolean boolean7, cxf cxf) {
		this.R = cxe;
		this.V = boolean2;
		this.Y = boolean3;
		this.T = boolean4;
		this.W = boolean5;
		this.U = boolean6;
		this.X = boolean7;
		this.S = cxf;
	}

	public boolean a() {
		return this.V;
	}

	public boolean b() {
		return this.Y;
	}

	public boolean c() {
		return this.T;
	}

	public boolean d() {
		return this.U;
	}

	public boolean e() {
		return this.X;
	}

	public boolean f() {
		return this.W;
	}

	public cxf g() {
		return this.S;
	}

	public cxe h() {
		return this.R;
	}

	public static class a {
		private cxf a = cxf.NORMAL;
		private boolean b = true;
		private boolean c;
		private boolean d;
		private boolean e;
		private boolean f = true;
		private final cxe g;
		private boolean h = true;

		public a(cxe cxe) {
			this.g = cxe;
		}

		public cxd.a a() {
			this.d = true;
			return this;
		}

		public cxd.a b() {
			this.f = false;
			return this;
		}

		public cxd.a c() {
			this.b = false;
			return this;
		}

		private cxd.a i() {
			this.h = false;
			return this;
		}

		protected cxd.a d() {
			this.c = true;
			return this;
		}

		public cxd.a e() {
			this.e = true;
			return this;
		}

		protected cxd.a f() {
			this.a = cxf.DESTROY;
			return this;
		}

		protected cxd.a g() {
			this.a = cxf.BLOCK;
			return this;
		}

		public cxd h() {
			return new cxd(this.g, this.d, this.f, this.b, this.h, this.c, this.e, this.a);
		}
	}
}
