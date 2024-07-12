import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class cel extends cdl {
	private uh a;
	private String b = "";
	private String c = "";
	private fu g = new fu(0, 1, 0);
	private fu h = fu.b;
	private bzj i = bzj.NONE;
	private cap j = cap.NONE;
	private cgq k = cgq.DATA;
	private boolean l = true;
	private boolean m;
	private boolean n;
	private boolean o = true;
	private float p = 1.0F;
	private long q;

	public cel() {
		super(cdm.t);
	}

	@Override
	public le a(le le) {
		super.a(le);
		le.a("name", this.d());
		le.a("author", this.b);
		le.a("metadata", this.c);
		le.b("posX", this.g.u());
		le.b("posY", this.g.v());
		le.b("posZ", this.g.w());
		le.b("sizeX", this.h.u());
		le.b("sizeY", this.h.v());
		le.b("sizeZ", this.h.w());
		le.a("rotation", this.j.toString());
		le.a("mirror", this.i.toString());
		le.a("mode", this.k.toString());
		le.a("ignoreEntities", this.l);
		le.a("powered", this.m);
		le.a("showair", this.n);
		le.a("showboundingbox", this.o);
		le.a("integrity", this.p);
		le.a("seed", this.q);
		return le;
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.a(le.l("name"));
		this.b = le.l("author");
		this.c = le.l("metadata");
		int integer4 = aec.a(le.h("posX"), -48, 48);
		int integer5 = aec.a(le.h("posY"), -48, 48);
		int integer6 = aec.a(le.h("posZ"), -48, 48);
		this.g = new fu(integer4, integer5, integer6);
		int integer7 = aec.a(le.h("sizeX"), 0, 48);
		int integer8 = aec.a(le.h("sizeY"), 0, 48);
		int integer9 = aec.a(le.h("sizeZ"), 0, 48);
		this.h = new fu(integer7, integer8, integer9);

		try {
			this.j = cap.valueOf(le.l("rotation"));
		} catch (IllegalArgumentException var12) {
			this.j = cap.NONE;
		}

		try {
			this.i = bzj.valueOf(le.l("mirror"));
		} catch (IllegalArgumentException var11) {
			this.i = bzj.NONE;
		}

		try {
			this.k = cgq.valueOf(le.l("mode"));
		} catch (IllegalArgumentException var10) {
			this.k = cgq.DATA;
		}

		this.l = le.q("ignoreEntities");
		this.m = le.q("powered");
		this.n = le.q("showair");
		this.o = le.q("showboundingbox");
		if (le.e("integrity")) {
			this.p = le.j("integrity");
		} else {
			this.p = 1.0F;
		}

		this.q = le.i("seed");
		this.L();
	}

	private void L() {
		if (this.d != null) {
			fu fu2 = this.o();
			cfj cfj3 = this.d.d_(fu2);
			if (cfj3.a(bvs.mY)) {
				this.d.a(fu2, cfj3.a(cbt.a, this.k), 2);
			}
		}
	}

	@Nullable
	@Override
	public nv a() {
		return new nv(this.e, 7, this.b());
	}

	@Override
	public le b() {
		return this.a(new le());
	}

	public boolean a(bec bec) {
		if (!bec.eV()) {
			return false;
		} else {
			if (bec.cf().v) {
				bec.a(this);
			}

			return true;
		}
	}

	public String d() {
		return this.a == null ? "" : this.a.toString();
	}

	public String f() {
		return this.a == null ? "" : this.a.a();
	}

	public boolean g() {
		return this.a != null;
	}

	public void a(@Nullable String string) {
		this.a(aei.b(string) ? null : uh.a(string));
	}

	public void a(@Nullable uh uh) {
		this.a = uh;
	}

	public void a(aoy aoy) {
		this.b = aoy.P().getString();
	}

	public void b(fu fu) {
		this.g = fu;
	}

	public fu j() {
		return this.h;
	}

	public void c(fu fu) {
		this.h = fu;
	}

	public void b(bzj bzj) {
		this.i = bzj;
	}

	public cap l() {
		return this.j;
	}

	public void b(cap cap) {
		this.j = cap;
	}

	public void b(String string) {
		this.c = string;
	}

	public cgq x() {
		return this.k;
	}

	public void a(cgq cgq) {
		this.k = cgq;
		cfj cfj3 = this.d.d_(this.o());
		if (cfj3.a(bvs.mY)) {
			this.d.a(this.o(), cfj3.a(cbt.a, cgq), 2);
		}
	}

	public void a(boolean boolean1) {
		this.l = boolean1;
	}

	public void a(float float1) {
		this.p = float1;
	}

	public void a(long long1) {
		this.q = long1;
	}

	public boolean C() {
		if (this.k != cgq.SAVE) {
			return false;
		} else {
			fu fu2 = this.o();
			int integer3 = 80;
			fu fu4 = new fu(fu2.u() - 80, 0, fu2.w() - 80);
			fu fu5 = new fu(fu2.u() + 80, 255, fu2.w() + 80);
			List<cel> list6 = this.a(fu4, fu5);
			List<cel> list7 = this.a(list6);
			if (list7.size() < 1) {
				return false;
			} else {
				ctd ctd8 = this.a(fu2, list7);
				if (ctd8.d - ctd8.a > 1 && ctd8.e - ctd8.b > 1 && ctd8.f - ctd8.c > 1) {
					this.g = new fu(ctd8.a - fu2.u() + 1, ctd8.b - fu2.v() + 1, ctd8.c - fu2.w() + 1);
					this.h = new fu(ctd8.d - ctd8.a - 1, ctd8.e - ctd8.b - 1, ctd8.f - ctd8.c - 1);
					this.Z_();
					cfj cfj9 = this.d.d_(fu2);
					this.d.a(fu2, cfj9, cfj9, 3);
					return true;
				} else {
					return false;
				}
			}
		}
	}

	private List<cel> a(List<cel> list) {
		Predicate<cel> predicate3 = cel -> cel.k == cgq.CORNER && Objects.equals(this.a, cel.a);
		return (List<cel>)list.stream().filter(predicate3).collect(Collectors.toList());
	}

	private List<cel> a(fu fu1, fu fu2) {
		List<cel> list4 = Lists.<cel>newArrayList();

		for (fu fu6 : fu.a(fu1, fu2)) {
			cfj cfj7 = this.d.d_(fu6);
			if (cfj7.a(bvs.mY)) {
				cdl cdl8 = this.d.c(fu6);
				if (cdl8 != null && cdl8 instanceof cel) {
					list4.add((cel)cdl8);
				}
			}
		}

		return list4;
	}

	private ctd a(fu fu, List<cel> list) {
		ctd ctd4;
		if (list.size() > 1) {
			fu fu5 = ((cel)list.get(0)).o();
			ctd4 = new ctd(fu5, fu5);
		} else {
			ctd4 = new ctd(fu, fu);
		}

		for (cel cel6 : list) {
			fu fu7 = cel6.o();
			if (fu7.u() < ctd4.a) {
				ctd4.a = fu7.u();
			} else if (fu7.u() > ctd4.d) {
				ctd4.d = fu7.u();
			}

			if (fu7.v() < ctd4.b) {
				ctd4.b = fu7.v();
			} else if (fu7.v() > ctd4.e) {
				ctd4.e = fu7.v();
			}

			if (fu7.w() < ctd4.c) {
				ctd4.c = fu7.w();
			} else if (fu7.w() > ctd4.f) {
				ctd4.f = fu7.w();
			}
		}

		return ctd4;
	}

	public boolean D() {
		return this.b(true);
	}

	public boolean b(boolean boolean1) {
		if (this.k == cgq.SAVE && !this.d.v && this.a != null) {
			fu fu3 = this.o().a(this.g);
			zd zd4 = (zd)this.d;
			cva cva5 = zd4.r_();

			cve cve6;
			try {
				cve6 = cva5.a(this.a);
			} catch (t var8) {
				return false;
			}

			cve6.a(this.d, fu3, this.h, !this.l, bvs.iN);
			cve6.a(this.b);
			if (boolean1) {
				try {
					return cva5.c(this.a);
				} catch (t var7) {
					return false;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public boolean E() {
		return this.c(true);
	}

	private static Random b(long long1) {
		return long1 == 0L ? new Random(v.b()) : new Random(long1);
	}

	public boolean c(boolean boolean1) {
		if (this.k == cgq.LOAD && !this.d.v && this.a != null) {
			zd zd3 = (zd)this.d;
			cva cva4 = zd3.r_();

			cve cve5;
			try {
				cve5 = cva4.b(this.a);
			} catch (t var6) {
				return false;
			}

			return cve5 == null ? false : this.a(boolean1, cve5);
		} else {
			return false;
		}
	}

	public boolean a(boolean boolean1, cve cve) {
		fu fu4 = this.o();
		if (!aei.b(cve.b())) {
			this.b = cve.b();
		}

		fu fu5 = cve.a();
		boolean boolean6 = this.h.equals(fu5);
		if (!boolean6) {
			this.h = fu5;
			this.Z_();
			cfj cfj7 = this.d.d_(fu4);
			this.d.a(fu4, cfj7, cfj7, 3);
		}

		if (boolean1 && !boolean6) {
			return false;
		} else {
			cvb cvb7 = new cvb().a(this.i).a(this.j).a(this.l).a(null);
			if (this.p < 1.0F) {
				cvb7.b().a(new cuk(aec.a(this.p, 0.0F, 1.0F))).a(b(this.q));
			}

			fu fu8 = fu4.a(this.g);
			cve.a(this.d, fu8, cvb7, b(this.q));
			return true;
		}
	}

	public void F() {
		if (this.a != null) {
			zd zd2 = (zd)this.d;
			cva cva3 = zd2.r_();
			cva3.d(this.a);
		}
	}

	public boolean G() {
		if (this.k == cgq.LOAD && !this.d.v && this.a != null) {
			zd zd2 = (zd)this.d;
			cva cva3 = zd2.r_();

			try {
				return cva3.b(this.a) != null;
			} catch (t var4) {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean H() {
		return this.m;
	}

	public void d(boolean boolean1) {
		this.m = boolean1;
	}

	public void e(boolean boolean1) {
		this.n = boolean1;
	}

	public void f(boolean boolean1) {
		this.o = boolean1;
	}

	public static enum a {
		UPDATE_DATA,
		SAVE_AREA,
		LOAD_AREA,
		SCAN_AREA;
	}
}
