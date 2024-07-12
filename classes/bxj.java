import javax.annotation.Nullable;

public class bxj extends bvg {
	protected static final dfg a = bvr.a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

	protected bxj(cfi.c c) {
		super(c);
	}

	@Override
	public boolean c_(cfj cfj) {
		return true;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Override
	public cdl a(bpg bpg) {
		return new cdw();
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			bec.a(cfj.b(bqb, fu));
			return ang.CONSUME;
		}
	}

	@Nullable
	@Override
	public anj b(cfj cfj, bqb bqb, fu fu) {
		cdl cdl5 = bqb.c(fu);
		if (cdl5 instanceof cdw) {
			mr mr6 = ((ank)cdl5).d();
			return new ann((integer, beb, bec) -> new bgy(integer, beb, bgs.a(bqb, fu)), mr6);
		} else {
			return null;
		}
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, aoy aoy, bki bki) {
		if (bki.t()) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cdw) {
				((cdw)cdl7).a(bki.r());
			}
		}
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
