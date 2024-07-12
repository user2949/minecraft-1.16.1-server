import com.google.common.collect.Lists;
import java.util.List;

public class cfg {
	private final bqb a;
	private final fu b;
	private final boolean c;
	private final fu d;
	private final fz e;
	private final List<fu> f = Lists.<fu>newArrayList();
	private final List<fu> g = Lists.<fu>newArrayList();
	private final fz h;

	public cfg(bqb bqb, fu fu, fz fz, boolean boolean4) {
		this.a = bqb;
		this.b = fu;
		this.h = fz;
		this.c = boolean4;
		if (boolean4) {
			this.e = fz;
			this.d = fu.a(fz);
		} else {
			this.e = fz.f();
			this.d = fu.a(fz, 2);
		}
	}

	public boolean a() {
		this.f.clear();
		this.g.clear();
		cfj cfj2 = this.a.d_(this.d);
		if (!cfc.a(cfj2, this.a, this.d, this.e, false, this.h)) {
			if (this.c && cfj2.k() == cxf.DESTROY) {
				this.g.add(this.d);
				return true;
			} else {
				return false;
			}
		} else if (!this.a(this.d, this.e)) {
			return false;
		} else {
			for (int integer3 = 0; integer3 < this.f.size(); integer3++) {
				fu fu4 = (fu)this.f.get(integer3);
				if (a(this.a.d_(fu4).b()) && !this.a(fu4)) {
					return false;
				}
			}

			return true;
		}
	}

	private static boolean a(bvr bvr) {
		return bvr == bvs.gn || bvr == bvs.ne;
	}

	private static boolean a(bvr bvr1, bvr bvr2) {
		if (bvr1 == bvs.ne && bvr2 == bvs.gn) {
			return false;
		} else {
			return bvr1 == bvs.gn && bvr2 == bvs.ne ? false : a(bvr1) || a(bvr2);
		}
	}

	private boolean a(fu fu, fz fz) {
		cfj cfj4 = this.a.d_(fu);
		bvr bvr5 = cfj4.b();
		if (cfj4.g()) {
			return true;
		} else if (!cfc.a(cfj4, this.a, fu, this.e, false, fz)) {
			return true;
		} else if (fu.equals(this.b)) {
			return true;
		} else if (this.f.contains(fu)) {
			return true;
		} else {
			int integer6 = 1;
			if (integer6 + this.f.size() > 12) {
				return false;
			} else {
				while (a(bvr5)) {
					fu fu7 = fu.a(this.e.f(), integer6);
					bvr bvr8 = bvr5;
					cfj4 = this.a.d_(fu7);
					bvr5 = cfj4.b();
					if (cfj4.g() || !a(bvr8, bvr5) || !cfc.a(cfj4, this.a, fu7, this.e, false, this.e.f()) || fu7.equals(this.b)) {
						break;
					}

					if (++integer6 + this.f.size() > 12) {
						return false;
					}
				}

				int integer7 = 0;

				for (int integer8 = integer6 - 1; integer8 >= 0; integer8--) {
					this.f.add(fu.a(this.e.f(), integer8));
					integer7++;
				}

				int integer8 = 1;

				while (true) {
					fu fu9 = fu.a(this.e, integer8);
					int integer10 = this.f.indexOf(fu9);
					if (integer10 > -1) {
						this.a(integer7, integer10);

						for (int integer11 = 0; integer11 <= integer10 + integer7; integer11++) {
							fu fu12 = (fu)this.f.get(integer11);
							if (a(this.a.d_(fu12).b()) && !this.a(fu12)) {
								return false;
							}
						}

						return true;
					}

					cfj4 = this.a.d_(fu9);
					if (cfj4.g()) {
						return true;
					}

					if (!cfc.a(cfj4, this.a, fu9, this.e, true, this.e) || fu9.equals(this.b)) {
						return false;
					}

					if (cfj4.k() == cxf.DESTROY) {
						this.g.add(fu9);
						return true;
					}

					if (this.f.size() >= 12) {
						return false;
					}

					this.f.add(fu9);
					integer7++;
					integer8++;
				}
			}
		}
	}

	private void a(int integer1, int integer2) {
		List<fu> list4 = Lists.<fu>newArrayList();
		List<fu> list5 = Lists.<fu>newArrayList();
		List<fu> list6 = Lists.<fu>newArrayList();
		list4.addAll(this.f.subList(0, integer2));
		list5.addAll(this.f.subList(this.f.size() - integer1, this.f.size()));
		list6.addAll(this.f.subList(integer2, this.f.size() - integer1));
		this.f.clear();
		this.f.addAll(list4);
		this.f.addAll(list5);
		this.f.addAll(list6);
	}

	private boolean a(fu fu) {
		cfj cfj3 = this.a.d_(fu);

		for (fz fz7 : fz.values()) {
			if (fz7.n() != this.e.n()) {
				fu fu8 = fu.a(fz7);
				cfj cfj9 = this.a.d_(fu8);
				if (a(cfj9.b(), cfj3.b()) && !this.a(fu8, fz7)) {
					return false;
				}
			}
		}

		return true;
	}

	public List<fu> c() {
		return this.f;
	}

	public List<fu> d() {
		return this.g;
	}
}
