public class cdt extends cdl implements ceo {
	public cdt() {
		super(cdm.p);
	}

	@Override
	public void al_() {
		if (this.d != null && !this.d.v && this.d.Q() % 20L == 0L) {
			cfj cfj2 = this.p();
			bvr bvr3 = cfj2.b();
			if (bvr3 instanceof bwy) {
				bwy.d(cfj2, this.d, this.e);
			}
		}
	}
}
