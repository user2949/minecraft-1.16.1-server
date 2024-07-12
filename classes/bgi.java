import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class bgi {
	private final gi<bki> c = gi.a();
	public final List<bhw> a = Lists.<bhw>newArrayList();
	private final List<bgw> d = Lists.<bgw>newArrayList();
	@Nullable
	private final bhk<?> e;
	public final int b;
	private int g = -1;
	private int h;
	private final Set<bhw> i = Sets.<bhw>newHashSet();
	private final List<bgt> j = Lists.<bgt>newArrayList();
	private final Set<bec> k = Sets.<bec>newHashSet();

	protected bgi(@Nullable bhk<?> bhk, int integer) {
		this.e = bhk;
		this.b = integer;
	}

	protected static boolean a(bgs bgs, bec bec, bvr bvr) {
		return bgs.a((bqb, fu) -> !bqb.d_(fu).a(bvr) ? false : bec.g((double)fu.u() + 0.5, (double)fu.v() + 0.5, (double)fu.w() + 0.5) <= 64.0, true);
	}

	public bhk<?> a() {
		if (this.e == null) {
			throw new UnsupportedOperationException("Unable to construct this menu by type");
		} else {
			return this.e;
		}
	}

	protected static void a(amz amz, int integer) {
		int integer3 = amz.ab_();
		if (integer3 < integer) {
			throw new IllegalArgumentException("Container size " + integer3 + " is smaller than expected " + integer);
		}
	}

	protected static void a(bgr bgr, int integer) {
		int integer3 = bgr.a();
		if (integer3 < integer) {
			throw new IllegalArgumentException("Container data count " + integer3 + " is smaller than expected " + integer);
		}
	}

	protected bhw a(bhw bhw) {
		bhw.d = this.a.size();
		this.a.add(bhw);
		this.c.add(bki.b);
		return bhw;
	}

	protected bgw a(bgw bgw) {
		this.d.add(bgw);
		return bgw;
	}

	protected void a(bgr bgr) {
		for (int integer3 = 0; integer3 < bgr.a(); integer3++) {
			this.a(bgw.a(bgr, integer3));
		}
	}

	public void a(bgt bgt) {
		if (!this.j.contains(bgt)) {
			this.j.add(bgt);
			bgt.a(this, this.b());
			this.c();
		}
	}

	public gi<bki> b() {
		gi<bki> gi2 = gi.a();

		for (int integer3 = 0; integer3 < this.a.size(); integer3++) {
			gi2.add(((bhw)this.a.get(integer3)).e());
		}

		return gi2;
	}

	public void c() {
		for (int integer2 = 0; integer2 < this.a.size(); integer2++) {
			bki bki3 = ((bhw)this.a.get(integer2)).e();
			bki bki4 = this.c.get(integer2);
			if (!bki.b(bki4, bki3)) {
				bki bki5 = bki3.i();
				this.c.set(integer2, bki5);

				for (bgt bgt7 : this.j) {
					bgt7.a(this, integer2, bki5);
				}
			}
		}

		for (int integer2x = 0; integer2x < this.d.size(); integer2x++) {
			bgw bgw3 = (bgw)this.d.get(integer2x);
			if (bgw3.c()) {
				for (bgt bgt5 : this.j) {
					bgt5.a(this, integer2x, bgw3.b());
				}
			}
		}
	}

	public boolean a(bec bec, int integer) {
		return false;
	}

	public bhw a(int integer) {
		return (bhw)this.a.get(integer);
	}

	public bki b(bec bec, int integer) {
		bhw bhw4 = (bhw)this.a.get(integer);
		return bhw4 != null ? bhw4.e() : bki.b;
	}

	public bki a(int integer1, int integer2, bgq bgq, bec bec) {
		try {
			return this.b(integer1, integer2, bgq, bec);
		} catch (Exception var8) {
			j j7 = j.a(var8, "Container click");
			k k8 = j7.a("Click info");
			k8.a("Menu Type", (l<String>)(() -> this.e != null ? gl.aM.b(this.e).toString() : "<no type>"));
			k8.a("Menu Class", (l<String>)(() -> this.getClass().getCanonicalName()));
			k8.a("Slot Count", this.a.size());
			k8.a("Slot", integer1);
			k8.a("Button", integer2);
			k8.a("Type", bgq);
			throw new s(j7);
		}
	}

	private bki b(int integer1, int integer2, bgq bgq, bec bec) {
		bki bki6 = bki.b;
		beb beb7 = bec.bt;
		if (bgq == bgq.QUICK_CRAFT) {
			int integer8 = this.h;
			this.h = c(integer2);
			if ((integer8 != 1 || this.h != 2) && integer8 != this.h) {
				this.d();
			} else if (beb7.m().a()) {
				this.d();
			} else if (this.h == 0) {
				this.g = b(integer2);
				if (a(this.g, bec)) {
					this.h = 1;
					this.i.clear();
				} else {
					this.d();
				}
			} else if (this.h == 1) {
				bhw bhw9 = (bhw)this.a.get(integer1);
				bki bki10 = beb7.m();
				if (bhw9 != null && a(bhw9, bki10, true) && bhw9.a(bki10) && (this.g == 2 || bki10.E() > this.i.size()) && this.b(bhw9)) {
					this.i.add(bhw9);
				}
			} else if (this.h == 2) {
				if (!this.i.isEmpty()) {
					bki bki9 = beb7.m().i();
					int integer10 = beb7.m().E();

					for (bhw bhw12 : this.i) {
						bki bki13 = beb7.m();
						if (bhw12 != null && a(bhw12, bki13, true) && bhw12.a(bki13) && (this.g == 2 || bki13.E() >= this.i.size()) && this.b(bhw12)) {
							bki bki14 = bki9.i();
							int integer15 = bhw12.f() ? bhw12.e().E() : 0;
							a(this.i, this.g, bki14, integer15);
							int integer16 = Math.min(bki14.c(), bhw12.b(bki14));
							if (bki14.E() > integer16) {
								bki14.e(integer16);
							}

							integer10 -= bki14.E() - integer15;
							bhw12.d(bki14);
						}
					}

					bki9.e(integer10);
					beb7.g(bki9);
				}

				this.d();
			} else {
				this.d();
			}
		} else if (this.h != 0) {
			this.d();
		} else if ((bgq == bgq.PICKUP || bgq == bgq.QUICK_MOVE) && (integer2 == 0 || integer2 == 1)) {
			if (integer1 == -999) {
				if (!beb7.m().a()) {
					if (integer2 == 0) {
						bec.a(beb7.m(), true);
						beb7.g(bki.b);
					}

					if (integer2 == 1) {
						bec.a(beb7.m().a(1), true);
					}
				}
			} else if (bgq == bgq.QUICK_MOVE) {
				if (integer1 < 0) {
					return bki.b;
				}

				bhw bhw8 = (bhw)this.a.get(integer1);
				if (bhw8 == null || !bhw8.a(bec)) {
					return bki.b;
				}

				for (bki bki9 = this.b(bec, integer1); !bki9.a() && bki.c(bhw8.e(), bki9); bki9 = this.b(bec, integer1)) {
					bki6 = bki9.i();
				}
			} else {
				if (integer1 < 0) {
					return bki.b;
				}

				bhw bhw8 = (bhw)this.a.get(integer1);
				if (bhw8 != null) {
					bki bki9 = bhw8.e();
					bki bki10 = beb7.m();
					if (!bki9.a()) {
						bki6 = bki9.i();
					}

					if (bki9.a()) {
						if (!bki10.a() && bhw8.a(bki10)) {
							int integer11 = integer2 == 0 ? bki10.E() : 1;
							if (integer11 > bhw8.b(bki10)) {
								integer11 = bhw8.b(bki10);
							}

							bhw8.d(bki10.a(integer11));
						}
					} else if (bhw8.a(bec)) {
						if (bki10.a()) {
							if (bki9.a()) {
								bhw8.d(bki.b);
								beb7.g(bki.b);
							} else {
								int integer11 = integer2 == 0 ? bki9.E() : (bki9.E() + 1) / 2;
								beb7.g(bhw8.a(integer11));
								if (bki9.a()) {
									bhw8.d(bki.b);
								}

								bhw8.a(bec, beb7.m());
							}
						} else if (bhw8.a(bki10)) {
							if (a(bki9, bki10)) {
								int integer11 = integer2 == 0 ? bki10.E() : 1;
								if (integer11 > bhw8.b(bki10) - bki9.E()) {
									integer11 = bhw8.b(bki10) - bki9.E();
								}

								if (integer11 > bki10.c() - bki9.E()) {
									integer11 = bki10.c() - bki9.E();
								}

								bki10.g(integer11);
								bki9.f(integer11);
							} else if (bki10.E() <= bhw8.b(bki10)) {
								bhw8.d(bki10);
								beb7.g(bki9);
							}
						} else if (bki10.c() > 1 && a(bki9, bki10) && !bki9.a()) {
							int integer11x = bki9.E();
							if (integer11x + bki10.E() <= bki10.c()) {
								bki10.f(integer11x);
								bki9 = bhw8.a(integer11x);
								if (bki9.a()) {
									bhw8.d(bki.b);
								}

								bhw8.a(bec, beb7.m());
							}
						}
					}

					bhw8.d();
				}
			}
		} else if (bgq == bgq.SWAP) {
			bhw bhw8 = (bhw)this.a.get(integer1);
			bki bki9x = beb7.a(integer2);
			bki bki10x = bhw8.e();
			if (!bki9x.a() || !bki10x.a()) {
				if (bki9x.a()) {
					if (bhw8.a(bec)) {
						beb7.a(integer2, bki10x);
						bhw8.b(bki10x.E());
						bhw8.d(bki.b);
						bhw8.a(bec, bki10x);
					}
				} else if (bki10x.a()) {
					if (bhw8.a(bki9x)) {
						int integer11x = bhw8.b(bki9x);
						if (bki9x.E() > integer11x) {
							bhw8.d(bki9x.a(integer11x));
						} else {
							bhw8.d(bki9x);
							beb7.a(integer2, bki.b);
						}
					}
				} else if (bhw8.a(bec) && bhw8.a(bki9x)) {
					int integer11x = bhw8.b(bki9x);
					if (bki9x.E() > integer11x) {
						bhw8.d(bki9x.a(integer11x));
						bhw8.a(bec, bki10x);
						if (!beb7.e(bki10x)) {
							bec.a(bki10x, true);
						}
					} else {
						bhw8.d(bki9x);
						beb7.a(integer2, bki10x);
						bhw8.a(bec, bki10x);
					}
				}
			}
		} else if (bgq == bgq.CLONE && bec.bJ.d && beb7.m().a() && integer1 >= 0) {
			bhw bhw8 = (bhw)this.a.get(integer1);
			if (bhw8 != null && bhw8.f()) {
				bki bki9x = bhw8.e().i();
				bki9x.e(bki9x.c());
				beb7.g(bki9x);
			}
		} else if (bgq == bgq.THROW && beb7.m().a() && integer1 >= 0) {
			bhw bhw8 = (bhw)this.a.get(integer1);
			if (bhw8 != null && bhw8.f() && bhw8.a(bec)) {
				bki bki9x = bhw8.a(integer2 == 0 ? 1 : bhw8.e().E());
				bhw8.a(bec, bki9x);
				bec.a(bki9x, true);
			}
		} else if (bgq == bgq.PICKUP_ALL && integer1 >= 0) {
			bhw bhw8 = (bhw)this.a.get(integer1);
			bki bki9x = beb7.m();
			if (!bki9x.a() && (bhw8 == null || !bhw8.f() || !bhw8.a(bec))) {
				int integer10 = integer2 == 0 ? 0 : this.a.size() - 1;
				int integer11x = integer2 == 0 ? 1 : -1;

				for (int integer12 = 0; integer12 < 2; integer12++) {
					for (int integer13 = integer10; integer13 >= 0 && integer13 < this.a.size() && bki9x.E() < bki9x.c(); integer13 += integer11x) {
						bhw bhw14 = (bhw)this.a.get(integer13);
						if (bhw14.f() && a(bhw14, bki9x, true) && bhw14.a(bec) && this.a(bki9x, bhw14)) {
							bki bki15 = bhw14.e();
							if (integer12 != 0 || bki15.E() != bki15.c()) {
								int integer16 = Math.min(bki9x.c() - bki9x.E(), bki15.E());
								bki bki17 = bhw14.a(integer16);
								bki9x.f(integer16);
								if (bki17.a()) {
									bhw14.d(bki.b);
								}

								bhw14.a(bec, bki17);
							}
						}
					}
				}
			}

			this.c();
		}

		return bki6;
	}

	public static boolean a(bki bki1, bki bki2) {
		return bki1.b() == bki2.b() && bki.a(bki1, bki2);
	}

	public boolean a(bki bki, bhw bhw) {
		return true;
	}

	public void b(bec bec) {
		beb beb3 = bec.bt;
		if (!beb3.m().a()) {
			bec.a(beb3.m(), false);
			beb3.g(bki.b);
		}
	}

	protected void a(bec bec, bqb bqb, amz amz) {
		if (!bec.aU() || bec instanceof ze && ((ze)bec).q()) {
			for (int integer5 = 0; integer5 < amz.ab_(); integer5++) {
				bec.a(amz.b(integer5), false);
			}
		} else {
			for (int integer5 = 0; integer5 < amz.ab_(); integer5++) {
				bec.bt.a(bqb, amz.b(integer5));
			}
		}
	}

	public void a(amz amz) {
		this.c();
	}

	public void a(int integer, bki bki) {
		this.a(integer).d(bki);
	}

	public void a(int integer1, int integer2) {
		((bgw)this.d.get(integer1)).a(integer2);
	}

	public boolean c(bec bec) {
		return !this.k.contains(bec);
	}

	public void a(bec bec, boolean boolean2) {
		if (boolean2) {
			this.k.remove(bec);
		} else {
			this.k.add(bec);
		}
	}

	public abstract boolean a(bec bec);

	protected boolean a(bki bki, int integer2, int integer3, boolean boolean4) {
		boolean boolean6 = false;
		int integer7 = integer2;
		if (boolean4) {
			integer7 = integer3 - 1;
		}

		if (bki.d()) {
			while (!bki.a() && (boolean4 ? integer7 >= integer2 : integer7 < integer3)) {
				bhw bhw8 = (bhw)this.a.get(integer7);
				bki bki9 = bhw8.e();
				if (!bki9.a() && a(bki, bki9)) {
					int integer10 = bki9.E() + bki.E();
					if (integer10 <= bki.c()) {
						bki.e(0);
						bki9.e(integer10);
						bhw8.d();
						boolean6 = true;
					} else if (bki9.E() < bki.c()) {
						bki.g(bki.c() - bki9.E());
						bki9.e(bki.c());
						bhw8.d();
						boolean6 = true;
					}
				}

				if (boolean4) {
					integer7--;
				} else {
					integer7++;
				}
			}
		}

		if (!bki.a()) {
			if (boolean4) {
				integer7 = integer3 - 1;
			} else {
				integer7 = integer2;
			}

			while (boolean4 ? integer7 >= integer2 : integer7 < integer3) {
				bhw bhw8x = (bhw)this.a.get(integer7);
				bki bki9x = bhw8x.e();
				if (bki9x.a() && bhw8x.a(bki)) {
					if (bki.E() > bhw8x.a()) {
						bhw8x.d(bki.a(bhw8x.a()));
					} else {
						bhw8x.d(bki.a(bki.E()));
					}

					bhw8x.d();
					boolean6 = true;
					break;
				}

				if (boolean4) {
					integer7--;
				} else {
					integer7++;
				}
			}
		}

		return boolean6;
	}

	public static int b(int integer) {
		return integer >> 2 & 3;
	}

	public static int c(int integer) {
		return integer & 3;
	}

	public static boolean a(int integer, bec bec) {
		if (integer == 0) {
			return true;
		} else {
			return integer == 1 ? true : integer == 2 && bec.bJ.d;
		}
	}

	protected void d() {
		this.h = 0;
		this.i.clear();
	}

	public static boolean a(@Nullable bhw bhw, bki bki, boolean boolean3) {
		boolean boolean4 = bhw == null || !bhw.f();
		return !boolean4 && bki.a(bhw.e()) && bki.a(bhw.e(), bki) ? bhw.e().E() + (boolean3 ? 0 : bki.E()) <= bki.c() : boolean4;
	}

	public static void a(Set<bhw> set, int integer2, bki bki, int integer4) {
		switch (integer2) {
			case 0:
				bki.e(aec.d((float)bki.E() / (float)set.size()));
				break;
			case 1:
				bki.e(1);
				break;
			case 2:
				bki.e(bki.b().i());
		}

		bki.f(integer4);
	}

	public boolean b(bhw bhw) {
		return true;
	}

	public static int a(@Nullable cdl cdl) {
		return cdl instanceof amz ? b((amz)cdl) : 0;
	}

	public static int b(@Nullable amz amz) {
		if (amz == null) {
			return 0;
		} else {
			int integer2 = 0;
			float float3 = 0.0F;

			for (int integer4 = 0; integer4 < amz.ab_(); integer4++) {
				bki bki5 = amz.a(integer4);
				if (!bki5.a()) {
					float3 += (float)bki5.E() / (float)Math.min(amz.X_(), bki5.c());
					integer2++;
				}
			}

			float3 /= (float)amz.ab_();
			return aec.d(float3 * 14.0F) + (integer2 > 0 ? 1 : 0);
		}
	}
}
