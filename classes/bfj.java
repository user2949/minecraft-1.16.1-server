import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class bfj extends czq {
	private final Map<Integer, bfh> a = Maps.<Integer, bfh>newHashMap();
	private final zd b;
	private int c;
	private int d;

	public bfj(zd zd) {
		super(a(zd.m()));
		this.b = zd;
		this.c = 1;
		this.b();
	}

	public bfh a(int integer) {
		return (bfh)this.a.get(integer);
	}

	public void a() {
		this.d++;
		Iterator<bfh> iterator2 = this.a.values().iterator();

		while (iterator2.hasNext()) {
			bfh bfh3 = (bfh)iterator2.next();
			if (this.b.S().b(bpx.x)) {
				bfh3.n();
			}

			if (bfh3.d()) {
				iterator2.remove();
				this.b();
			} else {
				bfh3.o();
			}
		}

		if (this.d % 200 == 0) {
			this.b();
		}

		qy.a(this.b, this.a.values());
	}

	public static boolean a(bfi bfi, bfh bfh) {
		return bfi != null && bfh != null && bfh.i() != null ? bfi.aU() && bfi.fa() && bfi.dc() <= 2400 && bfi.l.m() == bfh.i().m() : false;
	}

	@Nullable
	public bfh a(ze ze) {
		if (ze.a_()) {
			return null;
		} else if (this.b.S().b(bpx.x)) {
			return null;
		} else {
			cif cif3 = ze.l.m();
			if (!cif3.l()) {
				return null;
			} else {
				fu fu4 = ze.cA();
				List<aya> list6 = (List<aya>)this.b.x().c(ayc.b, fu4, 64, axz.b.IS_OCCUPIED).collect(Collectors.toList());
				int integer7 = 0;
				dem dem8 = dem.a;

				for (aya aya10 : list6) {
					fu fu11 = aya10.f();
					dem8 = dem8.b((double)fu11.u(), (double)fu11.v(), (double)fu11.w());
					integer7++;
				}

				fu fu5;
				if (integer7 > 0) {
					dem8 = dem8.a(1.0 / (double)integer7);
					fu5 = new fu(dem8);
				} else {
					fu5 = fu4;
				}

				bfh bfh9 = this.a(ze.u(), fu5);
				boolean boolean10 = false;
				if (!bfh9.j()) {
					if (!this.a.containsKey(bfh9.u())) {
						this.a.put(bfh9.u(), bfh9);
					}

					boolean10 = true;
				} else if (bfh9.m() < bfh9.l()) {
					boolean10 = true;
				} else {
					ze.d(aoi.E);
					ze.b.a(new on(ze, (byte)43));
				}

				if (boolean10) {
					bfh9.a((bec)ze);
					ze.b.a(new on(ze, (byte)43));
					if (!bfh9.c()) {
						ze.a(acu.az);
						aa.I.a(ze);
					}
				}

				this.b();
				return bfh9;
			}
		}
	}

	private bfh a(zd zd, fu fu) {
		bfh bfh4 = zd.c_(fu);
		return bfh4 != null ? bfh4 : new bfh(this.e(), zd, fu);
	}

	@Override
	public void a(le le) {
		this.c = le.h("NextAvailableID");
		this.d = le.h("Tick");
		lk lk3 = le.d("Raids", 10);

		for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
			le le5 = lk3.a(integer4);
			bfh bfh6 = new bfh(this.b, le5);
			this.a.put(bfh6.u(), bfh6);
		}
	}

	@Override
	public le b(le le) {
		le.b("NextAvailableID", this.c);
		le.b("Tick", this.d);
		lk lk3 = new lk();

		for (bfh bfh5 : this.a.values()) {
			le le6 = new le();
			bfh5.a(le6);
			lk3.add(le6);
		}

		le.a("Raids", lk3);
		return le;
	}

	public static String a(cif cif) {
		return "raids" + cif.c();
	}

	private int e() {
		return ++this.c;
	}

	@Nullable
	public bfh a(fu fu, int integer) {
		bfh bfh4 = null;
		double double5 = (double)integer;

		for (bfh bfh8 : this.a.values()) {
			double double9 = bfh8.t().j(fu);
			if (bfh8.v() && double9 < double5) {
				bfh4 = bfh8;
				double5 = double9;
			}
		}

		return bfh4;
	}
}
