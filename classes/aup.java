public class aup extends auo {
	private final bdk g;

	public aup(bdk bdk) {
		super(bdk, bec.class, 8.0F);
		this.g = bdk;
	}

	@Override
	public boolean a() {
		if (this.g.eO()) {
			this.b = this.g.eN();
			return true;
		} else {
			return false;
		}
	}
}
