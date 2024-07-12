import javax.annotation.Nullable;

public abstract class bup extends bvg {
	private final bje a;

	protected bup(bje bje, cfi.c c) {
		super(c);
		this.a = bje;
	}

	@Override
	public boolean ak_() {
		return true;
	}

	@Override
	public cdl a(bpg bpg) {
		return new cdc(this.a);
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, @Nullable aoy aoy, bki bki) {
		if (bki.t()) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cdc) {
				((cdc)cdl7).a(bki.r());
			}
		}
	}

	public bje b() {
		return this.a;
	}
}
