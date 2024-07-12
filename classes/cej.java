public class cej extends cdb {
	public cej() {
		super(cdm.A, bmx.d);
	}

	@Override
	protected mr g() {
		return new ne("container.smoker");
	}

	@Override
	protected int a(bki bki) {
		return super.a(bki) / 2;
	}

	@Override
	protected bgi a(int integer, beb beb) {
		return new bhy(integer, beb, this, this.b);
	}
}
