import com.google.common.collect.Lists;
import com.mojang.serialization.DynamicLike;
import java.util.List;

public class cgw {
	private final List<cgu> a = Lists.<cgu>newArrayList();
	private double c = 0.2;
	private double d = 5.0;
	private int e = 15;
	private int f = 5;
	private double g;
	private double h;
	private int i = 29999984;
	private cgw.a j = new cgw.d(6.0E7);
	public static final cgw.c b = new cgw.c(0.0, 0.0, 0.2, 5.0, 5, 15, 6.0E7, 0L, 0.0);

	public boolean a(fu fu) {
		return (double)(fu.u() + 1) > this.e() && (double)fu.u() < this.g() && (double)(fu.w() + 1) > this.f() && (double)fu.w() < this.h();
	}

	public boolean a(bph bph) {
		return (double)bph.f() > this.e() && (double)bph.d() < this.g() && (double)bph.g() > this.f() && (double)bph.e() < this.h();
	}

	public boolean a(deg deg) {
		return deg.d > this.e() && deg.a < this.g() && deg.f > this.f() && deg.c < this.h();
	}

	public double a(aom aom) {
		return this.b(aom.cC(), aom.cG());
	}

	public dfg c() {
		return this.j.m();
	}

	public double b(double double1, double double2) {
		double double6 = double2 - this.f();
		double double8 = this.h() - double2;
		double double10 = double1 - this.e();
		double double12 = this.g() - double1;
		double double14 = Math.min(double10, double12);
		double14 = Math.min(double14, double6);
		return Math.min(double14, double8);
	}

	public double e() {
		return this.j.a();
	}

	public double f() {
		return this.j.c();
	}

	public double g() {
		return this.j.b();
	}

	public double h() {
		return this.j.d();
	}

	public double a() {
		return this.g;
	}

	public double b() {
		return this.h;
	}

	public void c(double double1, double double2) {
		this.g = double1;
		this.h = double2;
		this.j.k();

		for (cgu cgu7 : this.l()) {
			cgu7.a(this, double1, double2);
		}
	}

	public double i() {
		return this.j.e();
	}

	public long j() {
		return this.j.g();
	}

	public double k() {
		return this.j.h();
	}

	public void a(double double1) {
		this.j = new cgw.d(double1);

		for (cgu cgu5 : this.l()) {
			cgu5.a(this, double1);
		}
	}

	public void a(double double1, double double2, long long3) {
		this.j = (cgw.a)(double1 == double2 ? new cgw.d(double2) : new cgw.b(double1, double2, long3));

		for (cgu cgu9 : this.l()) {
			cgu9.a(this, double1, double2, long3);
		}
	}

	protected List<cgu> l() {
		return Lists.<cgu>newArrayList(this.a);
	}

	public void a(cgu cgu) {
		this.a.add(cgu);
	}

	public void a(int integer) {
		this.i = integer;
		this.j.j();
	}

	public int m() {
		return this.i;
	}

	public double n() {
		return this.d;
	}

	public void b(double double1) {
		this.d = double1;

		for (cgu cgu5 : this.l()) {
			cgu5.c(this, double1);
		}
	}

	public double o() {
		return this.c;
	}

	public void c(double double1) {
		this.c = double1;

		for (cgu cgu5 : this.l()) {
			cgu5.b(this, double1);
		}
	}

	public int q() {
		return this.e;
	}

	public void b(int integer) {
		this.e = integer;

		for (cgu cgu4 : this.l()) {
			cgu4.a(this, integer);
		}
	}

	public int r() {
		return this.f;
	}

	public void c(int integer) {
		this.f = integer;

		for (cgu cgu4 : this.l()) {
			cgu4.b(this, integer);
		}
	}

	public void s() {
		this.j = this.j.l();
	}

	public cgw.c t() {
		return new cgw.c(this);
	}

	public void a(cgw.c c) {
		this.c(c.a(), c.b());
		this.c(c.c());
		this.b(c.d());
		this.c(c.e());
		this.b(c.f());
		if (c.h() > 0L) {
			this.a(c.g(), c.i(), c.h());
		} else {
			this.a(c.g());
		}
	}

	interface a {
		double a();

		double b();

		double c();

		double d();

		double e();

		long g();

		double h();

		void j();

		void k();

		cgw.a l();

		dfg m();
	}

	class b implements cgw.a {
		private final double b;
		private final double c;
		private final long d;
		private final long e;
		private final double f;

		private b(double double2, double double3, long long4) {
			this.b = double2;
			this.c = double3;
			this.f = (double)long4;
			this.e = v.b();
			this.d = this.e + long4;
		}

		@Override
		public double a() {
			return Math.max(cgw.this.a() - this.e() / 2.0, (double)(-cgw.this.i));
		}

		@Override
		public double c() {
			return Math.max(cgw.this.b() - this.e() / 2.0, (double)(-cgw.this.i));
		}

		@Override
		public double b() {
			return Math.min(cgw.this.a() + this.e() / 2.0, (double)cgw.this.i);
		}

		@Override
		public double d() {
			return Math.min(cgw.this.b() + this.e() / 2.0, (double)cgw.this.i);
		}

		@Override
		public double e() {
			double double2 = (double)(v.b() - this.e) / this.f;
			return double2 < 1.0 ? aec.d(double2, this.b, this.c) : this.c;
		}

		@Override
		public long g() {
			return this.d - v.b();
		}

		@Override
		public double h() {
			return this.c;
		}

		@Override
		public void k() {
		}

		@Override
		public void j() {
		}

		@Override
		public cgw.a l() {
			return (cgw.a)(this.g() <= 0L ? cgw.this.new d(this.c) : this);
		}

		@Override
		public dfg m() {
			return dfd.a(
				dfd.a,
				dfd.a(Math.floor(this.a()), Double.NEGATIVE_INFINITY, Math.floor(this.c()), Math.ceil(this.b()), Double.POSITIVE_INFINITY, Math.ceil(this.d())),
				deq.e
			);
		}
	}

	public static class c {
		private final double a;
		private final double b;
		private final double c;
		private final double d;
		private final int e;
		private final int f;
		private final double g;
		private final long h;
		private final double i;

		private c(double double1, double double2, double double3, double double4, int integer5, int integer6, double double7, long long8, double double9) {
			this.a = double1;
			this.b = double2;
			this.c = double3;
			this.d = double4;
			this.e = integer5;
			this.f = integer6;
			this.g = double7;
			this.h = long8;
			this.i = double9;
		}

		private c(cgw cgw) {
			this.a = cgw.a();
			this.b = cgw.b();
			this.c = cgw.o();
			this.d = cgw.n();
			this.e = cgw.r();
			this.f = cgw.q();
			this.g = cgw.i();
			this.h = cgw.j();
			this.i = cgw.k();
		}

		public double a() {
			return this.a;
		}

		public double b() {
			return this.b;
		}

		public double c() {
			return this.c;
		}

		public double d() {
			return this.d;
		}

		public int e() {
			return this.e;
		}

		public int f() {
			return this.f;
		}

		public double g() {
			return this.g;
		}

		public long h() {
			return this.h;
		}

		public double i() {
			return this.i;
		}

		public static cgw.c a(DynamicLike<?> dynamicLike, cgw.c c) {
			double double3 = dynamicLike.get("BorderCenterX").asDouble(c.a);
			double double5 = dynamicLike.get("BorderCenterZ").asDouble(c.b);
			double double7 = dynamicLike.get("BorderSize").asDouble(c.g);
			long long9 = dynamicLike.get("BorderSizeLerpTime").asLong(c.h);
			double double11 = dynamicLike.get("BorderSizeLerpTarget").asDouble(c.i);
			double double13 = dynamicLike.get("BorderSafeZone").asDouble(c.d);
			double double15 = dynamicLike.get("BorderDamagePerBlock").asDouble(c.c);
			int integer17 = dynamicLike.get("BorderWarningBlocks").asInt(c.e);
			int integer18 = dynamicLike.get("BorderWarningTime").asInt(c.f);
			return new cgw.c(double3, double5, double15, double13, integer17, integer18, double7, long9, double11);
		}

		public void a(le le) {
			le.a("BorderCenterX", this.a);
			le.a("BorderCenterZ", this.b);
			le.a("BorderSize", this.g);
			le.a("BorderSizeLerpTime", this.h);
			le.a("BorderSafeZone", this.d);
			le.a("BorderDamagePerBlock", this.c);
			le.a("BorderSizeLerpTarget", this.i);
			le.a("BorderWarningBlocks", (double)this.e);
			le.a("BorderWarningTime", (double)this.f);
		}
	}

	class d implements cgw.a {
		private final double b;
		private double c;
		private double d;
		private double e;
		private double f;
		private dfg g;

		public d(double double2) {
			this.b = double2;
			this.n();
		}

		@Override
		public double a() {
			return this.c;
		}

		@Override
		public double b() {
			return this.e;
		}

		@Override
		public double c() {
			return this.d;
		}

		@Override
		public double d() {
			return this.f;
		}

		@Override
		public double e() {
			return this.b;
		}

		@Override
		public long g() {
			return 0L;
		}

		@Override
		public double h() {
			return this.b;
		}

		private void n() {
			this.c = Math.max(cgw.this.a() - this.b / 2.0, (double)(-cgw.this.i));
			this.d = Math.max(cgw.this.b() - this.b / 2.0, (double)(-cgw.this.i));
			this.e = Math.min(cgw.this.a() + this.b / 2.0, (double)cgw.this.i);
			this.f = Math.min(cgw.this.b() + this.b / 2.0, (double)cgw.this.i);
			this.g = dfd.a(
				dfd.a,
				dfd.a(Math.floor(this.a()), Double.NEGATIVE_INFINITY, Math.floor(this.c()), Math.ceil(this.b()), Double.POSITIVE_INFINITY, Math.ceil(this.d())),
				deq.e
			);
		}

		@Override
		public void j() {
			this.n();
		}

		@Override
		public void k() {
			this.n();
		}

		@Override
		public cgw.a l() {
			return this;
		}

		@Override
		public dfg m() {
			return this.g;
		}
	}
}
