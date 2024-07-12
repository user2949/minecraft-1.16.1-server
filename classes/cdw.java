import java.util.Random;
import javax.annotation.Nullable;

public class cdw extends cdl implements ank, ceo {
	public int a;
	public float b;
	public float c;
	public float g;
	public float h;
	public float i;
	public float j;
	public float k;
	public float l;
	public float m;
	private static final Random n = new Random();
	private mr o;

	public cdw() {
		super(cdm.l);
	}

	@Override
	public le a(le le) {
		super.a(le);
		if (this.Q()) {
			le.a("CustomName", mr.a.a(this.o));
		}

		return le;
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		if (le.c("CustomName", 8)) {
			this.o = mr.a.a(le.l("CustomName"));
		}
	}

	@Override
	public void al_() {
		this.j = this.i;
		this.l = this.k;
		bec bec2 = this.d.a((double)this.e.u() + 0.5, (double)this.e.v() + 0.5, (double)this.e.w() + 0.5, 3.0, false);
		if (bec2 != null) {
			double double3 = bec2.cC() - ((double)this.e.u() + 0.5);
			double double5 = bec2.cG() - ((double)this.e.w() + 0.5);
			this.m = (float)aec.d(double5, double3);
			this.i += 0.1F;
			if (this.i < 0.5F || n.nextInt(40) == 0) {
				float float7 = this.g;

				do {
					this.g = this.g + (float)(n.nextInt(4) - n.nextInt(4));
				} while (float7 == this.g);
			}
		} else {
			this.m += 0.02F;
			this.i -= 0.1F;
		}

		while (this.k >= (float) Math.PI) {
			this.k -= (float) (Math.PI * 2);
		}

		while (this.k < (float) -Math.PI) {
			this.k += (float) (Math.PI * 2);
		}

		while (this.m >= (float) Math.PI) {
			this.m -= (float) (Math.PI * 2);
		}

		while (this.m < (float) -Math.PI) {
			this.m += (float) (Math.PI * 2);
		}

		float float3 = this.m - this.k;

		while (float3 >= (float) Math.PI) {
			float3 -= (float) (Math.PI * 2);
		}

		while (float3 < (float) -Math.PI) {
			float3 += (float) (Math.PI * 2);
		}

		this.k += float3 * 0.4F;
		this.i = aec.a(this.i, 0.0F, 1.0F);
		this.a++;
		this.c = this.b;
		float float4 = (this.g - this.b) * 0.4F;
		float float5 = 0.2F;
		float4 = aec.a(float4, -0.2F, 0.2F);
		this.h = this.h + (float4 - this.h) * 0.9F;
		this.b = this.b + this.h;
	}

	@Override
	public mr P() {
		return (mr)(this.o != null ? this.o : new ne("container.enchant"));
	}

	public void a(@Nullable mr mr) {
		this.o = mr;
	}

	@Nullable
	@Override
	public mr R() {
		return this.o;
	}
}
