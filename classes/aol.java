import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aol extends aom {
	private static final Logger b = LogManager.getLogger();
	private static final tq<Float> c = tt.a(aol.class, ts.c);
	private static final tq<Integer> d = tt.a(aol.class, ts.b);
	private static final tq<Boolean> e = tt.a(aol.class, ts.i);
	private static final tq<hf> f = tt.a(aol.class, ts.j);
	private bmb g = bme.a;
	private final List<aog> an = Lists.<aog>newArrayList();
	private final Map<aom, Integer> ao = Maps.<aom, Integer>newHashMap();
	private int ap = 600;
	private int aq = 20;
	private int ar = 20;
	private boolean as;
	private int at;
	private float au;
	private float av;
	private aoy aw;
	private UUID ax;

	public aol(aoq<? extends aol> aoq, bqb bqb) {
		super(aoq, bqb);
		this.H = true;
		this.a(3.0F);
	}

	public aol(bqb bqb, double double2, double double3, double double4) {
		this(aoq.a, bqb);
		this.d(double2, double3, double4);
	}

	@Override
	protected void e() {
		this.Y().a(d, 0);
		this.Y().a(c, 0.5F);
		this.Y().a(e, false);
		this.Y().a(f, hh.u);
	}

	public void a(float float1) {
		if (!this.l.v) {
			this.Y().b(c, float1);
		}
	}

	@Override
	public void y_() {
		double double2 = this.cC();
		double double4 = this.cD();
		double double6 = this.cG();
		super.y_();
		this.d(double2, double4, double6);
	}

	public float g() {
		return this.Y().a(c);
	}

	public void a(bmb bmb) {
		this.g = bmb;
		if (!this.as) {
			this.x();
		}
	}

	private void x() {
		if (this.g == bme.a && this.an.isEmpty()) {
			this.Y().b(d, 0);
		} else {
			this.Y().b(d, bmd.a(bmd.a(this.g, this.an)));
		}
	}

	public void a(aog aog) {
		this.an.add(aog);
		if (!this.as) {
			this.x();
		}
	}

	public int h() {
		return this.Y().a(d);
	}

	public void a(int integer) {
		this.as = true;
		this.Y().b(d, integer);
	}

	public hf i() {
		return this.Y().a(f);
	}

	public void a(hf hf) {
		this.Y().b(f, hf);
	}

	protected void a(boolean boolean1) {
		this.Y().b(e, boolean1);
	}

	@Override
	public boolean k() {
		return this.Y().a(e);
	}

	public int m() {
		return this.ap;
	}

	public void b(int integer) {
		this.ap = integer;
	}

	@Override
	public void j() {
		super.j();
		boolean boolean2 = this.k();
		float float3 = this.g();
		if (this.l.v) {
			hf hf4 = this.i();
			if (boolean2) {
				if (this.J.nextBoolean()) {
					for (int integer5 = 0; integer5 < 2; integer5++) {
						float float6 = this.J.nextFloat() * (float) (Math.PI * 2);
						float float7 = aec.c(this.J.nextFloat()) * 0.2F;
						float float8 = aec.b(float6) * float7;
						float float9 = aec.a(float6) * float7;
						if (hf4.b() == hh.u) {
							int integer10 = this.J.nextBoolean() ? 16777215 : this.h();
							int integer11 = integer10 >> 16 & 0xFF;
							int integer12 = integer10 >> 8 & 0xFF;
							int integer13 = integer10 & 0xFF;
							this.l
								.b(
									hf4,
									this.cC() + (double)float8,
									this.cD(),
									this.cG() + (double)float9,
									(double)((float)integer11 / 255.0F),
									(double)((float)integer12 / 255.0F),
									(double)((float)integer13 / 255.0F)
								);
						} else {
							this.l.b(hf4, this.cC() + (double)float8, this.cD(), this.cG() + (double)float9, 0.0, 0.0, 0.0);
						}
					}
				}
			} else {
				float float5 = (float) Math.PI * float3 * float3;

				for (int integer6 = 0; (float)integer6 < float5; integer6++) {
					float float7 = this.J.nextFloat() * (float) (Math.PI * 2);
					float float8 = aec.c(this.J.nextFloat()) * float3;
					float float9 = aec.b(float7) * float8;
					float float10 = aec.a(float7) * float8;
					if (hf4.b() == hh.u) {
						int integer11 = this.h();
						int integer12 = integer11 >> 16 & 0xFF;
						int integer13 = integer11 >> 8 & 0xFF;
						int integer14 = integer11 & 0xFF;
						this.l
							.b(
								hf4,
								this.cC() + (double)float9,
								this.cD(),
								this.cG() + (double)float10,
								(double)((float)integer12 / 255.0F),
								(double)((float)integer13 / 255.0F),
								(double)((float)integer14 / 255.0F)
							);
					} else {
						this.l
							.b(
								hf4, this.cC() + (double)float9, this.cD(), this.cG() + (double)float10, (0.5 - this.J.nextDouble()) * 0.15, 0.01F, (0.5 - this.J.nextDouble()) * 0.15
							);
					}
				}
			}
		} else {
			if (this.K >= this.aq + this.ap) {
				this.aa();
				return;
			}

			boolean boolean4 = this.K < this.aq;
			if (boolean2 != boolean4) {
				this.a(boolean4);
			}

			if (boolean4) {
				return;
			}

			if (this.av != 0.0F) {
				float3 += this.av;
				if (float3 < 0.5F) {
					this.aa();
					return;
				}

				this.a(float3);
			}

			if (this.K % 5 == 0) {
				Iterator<Entry<aom, Integer>> iterator5 = this.ao.entrySet().iterator();

				while (iterator5.hasNext()) {
					Entry<aom, Integer> entry6 = (Entry<aom, Integer>)iterator5.next();
					if (this.K >= (Integer)entry6.getValue()) {
						iterator5.remove();
					}
				}

				List<aog> list5 = Lists.<aog>newArrayList();

				for (aog aog7 : this.g.a()) {
					list5.add(new aog(aog7.a(), aog7.b() / 4, aog7.c(), aog7.d(), aog7.e()));
				}

				list5.addAll(this.an);
				if (list5.isEmpty()) {
					this.ao.clear();
				} else {
					List<aoy> list6 = this.l.a(aoy.class, this.cb());
					if (!list6.isEmpty()) {
						for (aoy aoy8 : list6) {
							if (!this.ao.containsKey(aoy8) && aoy8.eg()) {
								double double9 = aoy8.cC() - this.cC();
								double double11 = aoy8.cG() - this.cG();
								double double13 = double9 * double9 + double11 * double11;
								if (double13 <= (double)(float3 * float3)) {
									this.ao.put(aoy8, this.K + this.ar);

									for (aog aog16 : list5) {
										if (aog16.a().a()) {
											aog16.a().a(this, this.t(), aoy8, aog16.c(), 0.5);
										} else {
											aoy8.c(new aog(aog16));
										}
									}

									if (this.au != 0.0F) {
										float3 += this.au;
										if (float3 < 0.5F) {
											this.aa();
											return;
										}

										this.a(float3);
									}

									if (this.at != 0) {
										this.ap = this.ap + this.at;
										if (this.ap <= 0) {
											this.aa();
											return;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void b(float float1) {
		this.au = float1;
	}

	public void c(float float1) {
		this.av = float1;
	}

	public void d(int integer) {
		this.aq = integer;
	}

	public void a(@Nullable aoy aoy) {
		this.aw = aoy;
		this.ax = aoy == null ? null : aoy.bR();
	}

	@Nullable
	public aoy t() {
		if (this.aw == null && this.ax != null && this.l instanceof zd) {
			aom aom2 = ((zd)this.l).a(this.ax);
			if (aom2 instanceof aoy) {
				this.aw = (aoy)aom2;
			}
		}

		return this.aw;
	}

	@Override
	protected void a(le le) {
		this.K = le.h("Age");
		this.ap = le.h("Duration");
		this.aq = le.h("WaitTime");
		this.ar = le.h("ReapplicationDelay");
		this.at = le.h("DurationOnUse");
		this.au = le.j("RadiusOnUse");
		this.av = le.j("RadiusPerTick");
		this.a(le.j("Radius"));
		if (le.b("Owner")) {
			this.ax = le.a("Owner");
		}

		if (le.c("Particle", 8)) {
			try {
				this.a(dt.b(new StringReader(le.l("Particle"))));
			} catch (CommandSyntaxException var5) {
				b.warn("Couldn't load custom particle {}", le.l("Particle"), var5);
			}
		}

		if (le.c("Color", 99)) {
			this.a(le.h("Color"));
		}

		if (le.c("Potion", 8)) {
			this.a(bmd.c(le));
		}

		if (le.c("Effects", 9)) {
			lk lk3 = le.d("Effects", 10);
			this.an.clear();

			for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
				aog aog5 = aog.b(lk3.a(integer4));
				if (aog5 != null) {
					this.a(aog5);
				}
			}
		}
	}

	@Override
	protected void b(le le) {
		le.b("Age", this.K);
		le.b("Duration", this.ap);
		le.b("WaitTime", this.aq);
		le.b("ReapplicationDelay", this.ar);
		le.b("DurationOnUse", this.at);
		le.a("RadiusOnUse", this.au);
		le.a("RadiusPerTick", this.av);
		le.a("Radius", this.g());
		le.a("Particle", this.i().a());
		if (this.ax != null) {
			le.a("Owner", this.ax);
		}

		if (this.as) {
			le.b("Color", this.h());
		}

		if (this.g != bme.a && this.g != null) {
			le.a("Potion", gl.an.b(this.g).toString());
		}

		if (!this.an.isEmpty()) {
			lk lk3 = new lk();

			for (aog aog5 : this.an) {
				lk3.add(aog5.a(new le()));
			}

			le.a("Effects", lk3);
		}
	}

	@Override
	public void a(tq<?> tq) {
		if (c.equals(tq)) {
			this.y_();
		}

		super.a(tq);
	}

	@Override
	public cxf z_() {
		return cxf.IGNORE;
	}

	@Override
	public ni<?> O() {
		return new nm(this);
	}

	@Override
	public aon a(apj apj) {
		return aon.b(this.g() * 2.0F, 0.5F);
	}
}
