import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public class cfb extends bvg {
	public static final cgd a = cfd.a;
	public static final cgg<cgk> b = cfd.b;

	public cfb(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, cgk.DEFAULT));
	}

	@Nullable
	@Override
	public cdl a(bpg bpg) {
		return null;
	}

	public static cdl a(cfj cfj, fz fz, boolean boolean3, boolean boolean4) {
		return new cff(cfj, fz, boolean3, boolean4);
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cff) {
				((cff)cdl7).l();
			}
		}
	}

	@Override
	public void a(bqc bqc, fu fu, cfj cfj) {
		fu fu5 = fu.a(((fz)cfj.c(a)).f());
		cfj cfj6 = bqc.d_(fu5);
		if (cfj6.b() instanceof cfc && (Boolean)cfj6.c(cfc.b)) {
			bqc.a(fu5, false);
		}
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (!bqb.v && bqb.c(fu) == null) {
			bqb.a(fu, false);
			return ang.CONSUME;
		} else {
			return ang.PASS;
		}
	}

	@Override
	public List<bki> a(cfj cfj, dat.a a) {
		cff cff4 = this.a(a.a(), a.a(dda.f));
		return cff4 == null ? Collections.emptyList() : cff4.k().a(a);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return dfd.a();
	}

	@Override
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		cff cff6 = this.a(bpg, fu);
		return cff6 != null ? cff6.a(bpg, fu) : dfd.a();
	}

	@Nullable
	private cff a(bpg bpg, fu fu) {
		cdl cdl4 = bpg.c(fu);
		return cdl4 instanceof cff ? (cff)cdl4 : null;
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, cap.a(cfj.c(a)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(bzj.a(cfj.c(a)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cfb.a, b);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
