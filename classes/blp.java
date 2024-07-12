public class blp extends bke {
	private final blo a;

	public blp(blo blo, bke.a a) {
		super(a.b(blo.a()));
		this.a = blo;
	}

	public blo g() {
		return this.a;
	}

	@Override
	public int c() {
		return this.a.e();
	}

	@Override
	public boolean a(bki bki1, bki bki2) {
		return this.a.f().a(bki2) || super.a(bki1, bki2);
	}
}
