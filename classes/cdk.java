public class cdk extends cdb {
	public cdk() {
		super(cdm.B, bmx.c);
	}

	@Override
	protected mr g() {
		return new ne("container.blast_furnace");
	}

	@Override
	protected int a(bki bki) {
		return super.a(bki) / 2;
	}

	@Override
	protected bgi a(int integer, beb beb) {
		return new bgm(integer, beb, this, this.b);
	}
}
