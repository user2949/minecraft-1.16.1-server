public class cec extends cdl implements amx {
	private bki a = bki.b;

	public cec() {
		super(cdm.e);
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		if (le.c("RecordItem", 10)) {
			this.a(bki.a(le.p("RecordItem")));
		}
	}

	@Override
	public le a(le le) {
		super.a(le);
		if (!this.d().a()) {
			le.a("RecordItem", this.d().b(new le()));
		}

		return le;
	}

	public bki d() {
		return this.a;
	}

	public void a(bki bki) {
		this.a = bki;
		this.Z_();
	}

	@Override
	public void aa_() {
		this.a(bki.b);
	}
}
