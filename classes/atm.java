public class atm {
	protected final aoz a;
	protected double b;
	protected double c;
	protected double d;
	protected double e;
	protected float f;
	protected float g;
	protected atm.a h = atm.a.WAIT;

	public atm(aoz aoz) {
		this.a = aoz;
	}

	public boolean b() {
		return this.h == atm.a.MOVE_TO;
	}

	public double c() {
		return this.e;
	}

	public void a(double double1, double double2, double double3, double double4) {
		this.b = double1;
		this.c = double2;
		this.d = double3;
		this.e = double4;
		if (this.h != atm.a.JUMPING) {
			this.h = atm.a.MOVE_TO;
		}
	}

	public void a(float float1, float float2) {
		this.h = atm.a.STRAFE;
		this.f = float1;
		this.g = float2;
		this.e = 0.25;
	}

	public void a() {
		if (this.h == atm.a.STRAFE) {
			float float2 = (float)this.a.b(apx.d);
			float float3 = (float)this.e * float2;
			float float4 = this.f;
			float float5 = this.g;
			float float6 = aec.c(float4 * float4 + float5 * float5);
			if (float6 < 1.0F) {
				float6 = 1.0F;
			}

			float6 = float3 / float6;
			float4 *= float6;
			float5 *= float6;
			float float7 = aec.a(this.a.p * (float) (Math.PI / 180.0));
			float float8 = aec.b(this.a.p * (float) (Math.PI / 180.0));
			float float9 = float4 * float8 - float5 * float7;
			float float10 = float5 * float8 + float4 * float7;
			if (!this.b(float9, float10)) {
				this.f = 1.0F;
				this.g = 0.0F;
			}

			this.a.n(float3);
			this.a.q(this.f);
			this.a.s(this.g);
			this.h = atm.a.WAIT;
		} else if (this.h == atm.a.MOVE_TO) {
			this.h = atm.a.WAIT;
			double double2 = this.b - this.a.cC();
			double double4 = this.d - this.a.cG();
			double double6 = this.c - this.a.cD();
			double double8 = double2 * double2 + double6 * double6 + double4 * double4;
			if (double8 < 2.5000003E-7F) {
				this.a.q(0.0F);
				return;
			}

			float float10 = (float)(aec.d(double4, double2) * 180.0F / (float)Math.PI) - 90.0F;
			this.a.p = this.a(this.a.p, float10, 90.0F);
			this.a.n((float)(this.e * this.a.b(apx.d)));
			fu fu11 = this.a.cA();
			cfj cfj12 = this.a.l.d_(fu11);
			bvr bvr13 = cfj12.b();
			dfg dfg14 = cfj12.k(this.a.l, fu11);
			if (double6 > (double)this.a.G && double2 * double2 + double4 * double4 < (double)Math.max(1.0F, this.a.cx())
				|| !dfg14.b() && this.a.cD() < dfg14.c(fz.a.Y) + (double)fu11.v() && !bvr13.a(acx.o) && !bvr13.a(acx.L)) {
				this.a.v().a();
				this.h = atm.a.JUMPING;
			}
		} else if (this.h == atm.a.JUMPING) {
			this.a.n((float)(this.e * this.a.b(apx.d)));
			if (this.a.aj()) {
				this.h = atm.a.WAIT;
			}
		} else {
			this.a.q(0.0F);
		}
	}

	private boolean b(float float1, float float2) {
		awv awv4 = this.a.x();
		if (awv4 != null) {
			cze cze5 = awv4.q();
			if (cze5 != null && cze5.a(this.a.l, aec.c(this.a.cC() + (double)float1), aec.c(this.a.cD()), aec.c(this.a.cG() + (double)float2)) != czb.WALKABLE) {
				return false;
			}
		}

		return true;
	}

	protected float a(float float1, float float2, float float3) {
		float float5 = aec.g(float2 - float1);
		if (float5 > float3) {
			float5 = float3;
		}

		if (float5 < -float3) {
			float5 = -float3;
		}

		float float6 = float1 + float5;
		if (float6 < 0.0F) {
			float6 += 360.0F;
		} else if (float6 > 360.0F) {
			float6 -= 360.0F;
		}

		return float6;
	}

	public double d() {
		return this.b;
	}

	public double e() {
		return this.c;
	}

	public double f() {
		return this.d;
	}

	public static enum a {
		WAIT,
		MOVE_TO,
		STRAFE,
		JUMPING;
	}
}
