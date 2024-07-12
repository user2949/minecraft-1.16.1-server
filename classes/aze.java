public abstract class aze extends apq {
	private int bx;

	protected aze(aoq<? extends aze> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public boolean d(ze ze) {
		le le3 = new le();
		le3.a("id", this.aT());
		this.e(le3);
		if (ze.g(le3)) {
			this.aa();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void j() {
		this.bx++;
		super.j();
	}

	public boolean eZ() {
		return this.bx > 100;
	}
}
