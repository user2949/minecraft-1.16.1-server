import java.util.UUID;
import javax.annotation.Nullable;

public abstract class bes extends aom {
	private UUID b;
	private int c;
	private boolean d;

	bes(aoq<? extends bes> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public void b(@Nullable aom aom) {
		if (aom != null) {
			this.b = aom.bR();
			this.c = aom.V();
		}
	}

	@Nullable
	public aom v() {
		if (this.b != null && this.l instanceof zd) {
			return ((zd)this.l).a(this.b);
		} else {
			return this.c != 0 ? this.l.a(this.c) : null;
		}
	}

	@Override
	protected void b(le le) {
		if (this.b != null) {
			le.a("Owner", this.b);
		}

		if (this.d) {
			le.a("LeftOwner", true);
		}
	}

	@Override
	protected void a(le le) {
		if (le.b("Owner")) {
			this.b = le.a("Owner");
		}

		this.d = le.q("LeftOwner");
	}

	@Override
	public void j() {
		if (!this.d) {
			this.d = this.h();
		}

		super.j();
	}

	private boolean h() {
		aom aom2 = this.v();
		if (aom2 != null) {
			for (aom aom4 : this.l.a(this, this.cb().b(this.cB()).g(1.0), aom -> !aom.a_() && aom.aQ())) {
				if (aom4.cq() == aom2.cq()) {
					return false;
				}
			}
		}

		return true;
	}

	public void c(double double1, double double2, double double3, float float4, float float5) {
		dem dem10 = new dem(double1, double2, double3)
			.d()
			.b(this.J.nextGaussian() * 0.0075F * (double)float5, this.J.nextGaussian() * 0.0075F * (double)float5, this.J.nextGaussian() * 0.0075F * (double)float5)
			.a((double)float4);
		this.e(dem10);
		float float11 = aec.a(b(dem10));
		this.p = (float)(aec.d(dem10.b, dem10.d) * 180.0F / (float)Math.PI);
		this.q = (float)(aec.d(dem10.c, (double)float11) * 180.0F / (float)Math.PI);
		this.r = this.p;
		this.s = this.q;
	}

	public void a(aom aom, float float2, float float3, float float4, float float5, float float6) {
		float float8 = -aec.a(float3 * (float) (Math.PI / 180.0)) * aec.b(float2 * (float) (Math.PI / 180.0));
		float float9 = -aec.a((float2 + float4) * (float) (Math.PI / 180.0));
		float float10 = aec.b(float3 * (float) (Math.PI / 180.0)) * aec.b(float2 * (float) (Math.PI / 180.0));
		this.c((double)float8, (double)float9, (double)float10, float5, float6);
		dem dem11 = aom.cB();
		this.e(this.cB().b(dem11.b, aom.aj() ? 0.0 : dem11.c, dem11.d));
	}

	protected void a(dej dej) {
		dej.a a3 = dej.c();
		if (a3 == dej.a.ENTITY) {
			this.a((dei)dej);
		} else if (a3 == dej.a.BLOCK) {
			this.a((deh)dej);
		}
	}

	protected void a(dei dei) {
	}

	protected void a(deh deh) {
		cfj cfj4 = this.l.d_(deh.a());
		cfj4.a(this.l, cfj4, deh, this);
	}

	protected boolean a(aom aom) {
		if (!aom.a_() && aom.aU() && aom.aQ()) {
			aom aom3 = this.v();
			return aom3 == null || this.d || !aom3.x(aom);
		} else {
			return false;
		}
	}

	protected void x() {
		dem dem2 = this.cB();
		float float3 = aec.a(b(dem2));
		this.q = e(this.s, (float)(aec.d(dem2.c, (double)float3) * 180.0F / (float)Math.PI));
		this.p = e(this.r, (float)(aec.d(dem2.b, dem2.d) * 180.0F / (float)Math.PI));
	}

	protected static float e(float float1, float float2) {
		while (float2 - float1 < -180.0F) {
			float1 -= 360.0F;
		}

		while (float2 - float1 >= 180.0F) {
			float1 += 360.0F;
		}

		return aec.g(0.2F, float1, float2);
	}
}
