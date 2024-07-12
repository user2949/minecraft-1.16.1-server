import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class bpd {
	private static final Logger a = LogManager.getLogger();
	private int b = 20;
	private final List<bqp> c = Lists.<bqp>newArrayList();
	private bqp d = new bqp();
	private double e;
	private double f;
	private int g = 200;
	private int h = 800;
	private int i = 4;
	@Nullable
	private aom j;
	private int k = 6;
	private int l = 16;
	private int m = 4;

	@Nullable
	private uh g() {
		String string2 = this.d.b().l("id");

		try {
			return aei.b(string2) ? null : new uh(string2);
		} catch (t var4) {
			fu fu4 = this.b();
			a.warn("Invalid entity id '{}' at spawner {}:[{},{},{}]", string2, this.a().W().a(), fu4.u(), fu4.v(), fu4.w());
			return null;
		}
	}

	public void a(aoq<?> aoq) {
		this.d.b().a("id", gl.al.b(aoq).toString());
	}

	private boolean h() {
		fu fu2 = this.b();
		return this.a().a((double)fu2.u() + 0.5, (double)fu2.v() + 0.5, (double)fu2.w() + 0.5, (double)this.l);
	}

	public void c() {
		if (!this.h()) {
			this.f = this.e;
		} else {
			bqb bqb2 = this.a();
			fu fu3 = this.b();
			if (bqb2.v) {
				double double4 = (double)fu3.u() + bqb2.t.nextDouble();
				double double6 = (double)fu3.v() + bqb2.t.nextDouble();
				double double8 = (double)fu3.w() + bqb2.t.nextDouble();
				bqb2.a(hh.S, double4, double6, double8, 0.0, 0.0, 0.0);
				bqb2.a(hh.A, double4, double6, double8, 0.0, 0.0, 0.0);
				if (this.b > 0) {
					this.b--;
				}

				this.f = this.e;
				this.e = (this.e + (double)(1000.0F / ((float)this.b + 200.0F))) % 360.0;
			} else {
				if (this.b == -1) {
					this.i();
				}

				if (this.b > 0) {
					this.b--;
					return;
				}

				boolean boolean4 = false;

				for (int integer5 = 0; integer5 < this.i; integer5++) {
					le le6 = this.d.b();
					Optional<aoq<?>> optional7 = aoq.a(le6);
					if (!optional7.isPresent()) {
						this.i();
						return;
					}

					lk lk8 = le6.d("Pos", 6);
					int integer9 = lk8.size();
					double double10 = integer9 >= 1 ? lk8.h(0) : (double)fu3.u() + (bqb2.t.nextDouble() - bqb2.t.nextDouble()) * (double)this.m + 0.5;
					double double12 = integer9 >= 2 ? lk8.h(1) : (double)(fu3.v() + bqb2.t.nextInt(3) - 1);
					double double14 = integer9 >= 3 ? lk8.h(2) : (double)fu3.w() + (bqb2.t.nextDouble() - bqb2.t.nextDouble()) * (double)this.m + 0.5;
					if (bqb2.b(((aoq)optional7.get()).a(double10, double12, double14))
						&& app.a((aoq)optional7.get(), bqb2.n(), apb.SPAWNER, new fu(double10, double12, double14), bqb2.v_())) {
						aom aom16 = aoq.a(le6, bqb2, aom -> {
							aom.b(double10, double12, double14, aom.p, aom.q);
							return aom;
						});
						if (aom16 == null) {
							this.i();
							return;
						}

						int integer17 = bqb2.a(
								aom16.getClass(),
								new deg((double)fu3.u(), (double)fu3.v(), (double)fu3.w(), (double)(fu3.u() + 1), (double)(fu3.v() + 1), (double)(fu3.w() + 1)).g((double)this.m)
							)
							.size();
						if (integer17 >= this.k) {
							this.i();
							return;
						}

						aom16.b(aom16.cC(), aom16.cD(), aom16.cG(), bqb2.t.nextFloat() * 360.0F, 0.0F);
						if (aom16 instanceof aoz) {
							aoz aoz18 = (aoz)aom16;
							if (!aoz18.a(bqb2, apb.SPAWNER) || !aoz18.a(bqb2)) {
								continue;
							}

							if (this.d.b().e() == 1 && this.d.b().c("id", 8)) {
								((aoz)aom16).a(bqb2, bqb2.d(aom16.cA()), apb.SPAWNER, null, null);
							}
						}

						this.a(aom16);
						bqb2.c(2004, fu3, 0);
						if (aom16 instanceof aoz) {
							((aoz)aom16).G();
						}

						boolean4 = true;
					}
				}

				if (boolean4) {
					this.i();
				}
			}
		}
	}

	private void a(aom aom) {
		if (this.a().c(aom)) {
			for (aom aom4 : aom.cm()) {
				this.a(aom4);
			}
		}
	}

	private void i() {
		if (this.h <= this.g) {
			this.b = this.g;
		} else {
			this.b = this.g + this.a().t.nextInt(this.h - this.g);
		}

		if (!this.c.isEmpty()) {
			this.a(aen.a(this.a().t, this.c));
		}

		this.a(1);
	}

	public void a(le le) {
		this.b = le.g("Delay");
		this.c.clear();
		if (le.c("SpawnPotentials", 9)) {
			lk lk3 = le.d("SpawnPotentials", 10);

			for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
				this.c.add(new bqp(lk3.a(integer4)));
			}
		}

		if (le.c("SpawnData", 10)) {
			this.a(new bqp(1, le.p("SpawnData")));
		} else if (!this.c.isEmpty()) {
			this.a(aen.a(this.a().t, this.c));
		}

		if (le.c("MinSpawnDelay", 99)) {
			this.g = le.g("MinSpawnDelay");
			this.h = le.g("MaxSpawnDelay");
			this.i = le.g("SpawnCount");
		}

		if (le.c("MaxNearbyEntities", 99)) {
			this.k = le.g("MaxNearbyEntities");
			this.l = le.g("RequiredPlayerRange");
		}

		if (le.c("SpawnRange", 99)) {
			this.m = le.g("SpawnRange");
		}

		if (this.a() != null) {
			this.j = null;
		}
	}

	public le b(le le) {
		uh uh3 = this.g();
		if (uh3 == null) {
			return le;
		} else {
			le.a("Delay", (short)this.b);
			le.a("MinSpawnDelay", (short)this.g);
			le.a("MaxSpawnDelay", (short)this.h);
			le.a("SpawnCount", (short)this.i);
			le.a("MaxNearbyEntities", (short)this.k);
			le.a("RequiredPlayerRange", (short)this.l);
			le.a("SpawnRange", (short)this.m);
			le.a("SpawnData", this.d.b().g());
			lk lk4 = new lk();
			if (this.c.isEmpty()) {
				lk4.add(this.d.a());
			} else {
				for (bqp bqp6 : this.c) {
					lk4.add(bqp6.a());
				}
			}

			le.a("SpawnPotentials", lk4);
			return le;
		}
	}

	public boolean b(int integer) {
		if (integer == 1 && this.a().v) {
			this.b = this.g;
			return true;
		} else {
			return false;
		}
	}

	public void a(bqp bqp) {
		this.d = bqp;
	}

	public abstract void a(int integer);

	public abstract bqb a();

	public abstract fu b();
}
