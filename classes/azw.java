import javax.annotation.Nullable;

public class azw extends azm {
	public azw(aoq<? extends azw> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public static apw.a eM() {
		return fj().a(apx.a, 15.0).a(apx.d, 0.2F);
	}

	@Override
	protected void eL() {
		this.a(apx.m).a(this.fr());
	}

	@Override
	public apc dB() {
		return apc.b;
	}

	@Override
	protected ack I() {
		super.I();
		return acl.rH;
	}

	@Override
	protected ack dp() {
		super.dp();
		return acl.rI;
	}

	@Override
	protected ack e(anw anw) {
		super.e(anw);
		return acl.rJ;
	}

	@Nullable
	@Override
	public aok a(aok aok) {
		return aoq.aY.a(this.l);
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (!this.eX()) {
			return ang.PASS;
		} else if (this.x_()) {
			return super.b(bec, anf);
		} else if (bec.ep()) {
			this.f(bec);
			return ang.a(this.l.v);
		} else if (this.bo()) {
			return super.b(bec, anf);
		} else {
			if (!bki4.a()) {
				if (bki4.b() == bkk.lO && !this.N_()) {
					this.f(bec);
					return ang.a(this.l.v);
				}

				ang ang5 = bki4.a(bec, this, anf);
				if (ang5.a()) {
					return ang5;
				}
			}

			this.h(bec);
			return ang.a(this.l.v);
		}
	}

	@Override
	protected void eW() {
	}
}
