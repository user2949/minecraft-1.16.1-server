import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class anv {
	private final List<ant> a = Lists.<ant>newArrayList();
	private final aoy b;
	private int c;
	private int d;
	private int e;
	private boolean f;
	private boolean g;
	private String h;

	public anv(aoy aoy) {
		this.b = aoy;
	}

	public void a() {
		this.k();
		Optional<fu> optional2 = this.b.dq();
		if (optional2.isPresent()) {
			cfj cfj3 = this.b.l.d_((fu)optional2.get());
			if (cfj3.a(bvs.cg) || cfj3.a(acx.I)) {
				this.h = "ladder";
			} else if (cfj3.a(bvs.dP)) {
				this.h = "vines";
			} else if (cfj3.a(bvs.mx) || cfj3.a(bvs.my)) {
				this.h = "weeping_vines";
			} else if (cfj3.a(bvs.mz) || cfj3.a(bvs.mA)) {
				this.h = "twisting_vines";
			} else if (cfj3.a(bvs.lQ)) {
				this.h = "scaffolding";
			} else {
				this.h = "other_climbable";
			}
		} else if (this.b.aA()) {
			this.h = "water";
		}
	}

	public void a(anw anw, float float2, float float3) {
		this.g();
		this.a();
		ant ant5 = new ant(anw, this.b.K, float2, float3, this.h, this.b.C);
		this.a.add(ant5);
		this.c = this.b.K;
		this.g = true;
		if (ant5.f() && !this.f && this.b.aU()) {
			this.f = true;
			this.d = this.b.K;
			this.e = this.d;
			this.b.g();
		}
	}

	public mr b() {
		if (this.a.isEmpty()) {
			return new ne("death.attack.generic", this.b.d());
		} else {
			ant ant2 = this.j();
			ant ant3 = (ant)this.a.get(this.a.size() - 1);
			mr mr5 = ant3.h();
			aom aom6 = ant3.a().k();
			mr mr4;
			if (ant2 != null && ant3.a() == anw.k) {
				mr mr7 = ant2.h();
				if (ant2.a() == anw.k || ant2.a() == anw.m) {
					mr4 = new ne("death.fell.accident." + this.a(ant2), this.b.d());
				} else if (mr7 != null && (mr5 == null || !mr7.equals(mr5))) {
					aom aom8 = ant2.a().k();
					bki bki9 = aom8 instanceof aoy ? ((aoy)aom8).dC() : bki.b;
					if (!bki9.a() && bki9.t()) {
						mr4 = new ne("death.fell.assist.item", this.b.d(), mr7, bki9.C());
					} else {
						mr4 = new ne("death.fell.assist", this.b.d(), mr7);
					}
				} else if (mr5 != null) {
					bki bki8 = aom6 instanceof aoy ? ((aoy)aom6).dC() : bki.b;
					if (!bki8.a() && bki8.t()) {
						mr4 = new ne("death.fell.finish.item", this.b.d(), mr5, bki8.C());
					} else {
						mr4 = new ne("death.fell.finish", this.b.d(), mr5);
					}
				} else {
					mr4 = new ne("death.fell.killer", this.b.d());
				}
			} else {
				mr4 = ant3.a().a(this.b);
			}

			return mr4;
		}
	}

	@Nullable
	public aoy c() {
		aoy aoy2 = null;
		bec bec3 = null;
		float float4 = 0.0F;
		float float5 = 0.0F;

		for (ant ant7 : this.a) {
			if (ant7.a().k() instanceof bec && (bec3 == null || ant7.c() > float5)) {
				float5 = ant7.c();
				bec3 = (bec)ant7.a().k();
			}

			if (ant7.a().k() instanceof aoy && (aoy2 == null || ant7.c() > float4)) {
				float4 = ant7.c();
				aoy2 = (aoy)ant7.a().k();
			}
		}

		return (aoy)(bec3 != null && float5 >= float4 / 3.0F ? bec3 : aoy2);
	}

	@Nullable
	private ant j() {
		ant ant2 = null;
		ant ant3 = null;
		float float4 = 0.0F;
		float float5 = 0.0F;

		for (int integer6 = 0; integer6 < this.a.size(); integer6++) {
			ant ant7 = (ant)this.a.get(integer6);
			ant ant8 = integer6 > 0 ? (ant)this.a.get(integer6 - 1) : null;
			if ((ant7.a() == anw.k || ant7.a() == anw.m) && ant7.j() > 0.0F && (ant2 == null || ant7.j() > float5)) {
				if (integer6 > 0) {
					ant2 = ant8;
				} else {
					ant2 = ant7;
				}

				float5 = ant7.j();
			}

			if (ant7.g() != null && (ant3 == null || ant7.c() > float4)) {
				ant3 = ant7;
				float4 = ant7.c();
			}
		}

		if (float5 > 5.0F && ant2 != null) {
			return ant2;
		} else {
			return float4 > 5.0F && ant3 != null ? ant3 : null;
		}
	}

	private String a(ant ant) {
		return ant.g() == null ? "generic" : ant.g();
	}

	public int f() {
		return this.f ? this.b.K - this.d : this.e - this.d;
	}

	private void k() {
		this.h = null;
	}

	public void g() {
		int integer2 = this.f ? 300 : 100;
		if (this.g && (!this.b.aU() || this.b.K - this.c > integer2)) {
			boolean boolean3 = this.f;
			this.g = false;
			this.f = false;
			this.e = this.b.K;
			if (boolean3) {
				this.b.h();
			}

			this.a.clear();
		}
	}

	public aoy h() {
		return this.b;
	}
}
