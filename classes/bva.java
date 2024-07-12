import java.util.Random;
import javax.annotation.Nullable;

public class bva extends bvg {
	public static final cgd a = cfz.M;
	public static final cga b = cfz.u;

	public bva(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, Boolean.valueOf(false)));
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			cdl cdl8 = bqb.c(fu);
			if (cdl8 instanceof cde) {
				bec.a((cde)cdl8);
				bec.a(acu.aq);
				bdd.a(bec, true);
			}

			return ang.CONSUME;
		}
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof amz) {
				anc.a(bqb, fu, (amz)cdl7);
				bqb.c(fu, this);
			}

			super.a(cfj1, bqb, fu, cfj4, boolean5);
		}
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		cdl cdl6 = zd.c(fu);
		if (cdl6 instanceof cde) {
			((cde)cdl6).h();
		}
	}

	@Nullable
	@Override
	public cdl a(bpg bpg) {
		return new cde();
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, @Nullable aoy aoy, bki bki) {
		if (bki.t()) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cde) {
				((cde)cdl7).a(bki.r());
			}
		}
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		return bgi.a(bqb.c(fu));
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
		a.a(bva.a, b);
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, bin.d().f());
	}
}
