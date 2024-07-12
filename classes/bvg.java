import javax.annotation.Nullable;

public abstract class bvg extends bvr implements bxp {
	protected bvg(cfi.c c) {
		super(c);
	}

	@Override
	public cak b(cfj cfj) {
		return cak.INVISIBLE;
	}

	@Override
	public boolean a(cfj cfj, bqb bqb, fu fu, int integer4, int integer5) {
		super.a(cfj, bqb, fu, integer4, integer5);
		cdl cdl7 = bqb.c(fu);
		return cdl7 == null ? false : cdl7.a_(integer4, integer5);
	}

	@Nullable
	@Override
	public anj b(cfj cfj, bqb bqb, fu fu) {
		cdl cdl5 = bqb.c(fu);
		return cdl5 instanceof anj ? (anj)cdl5 : null;
	}
}
