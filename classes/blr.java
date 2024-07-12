public class blr extends bih {
	public blr(bke.a a) {
		super(a);
	}

	@Override
	public bki r() {
		return bmd.a(super.r(), bme.D);
	}

	@Override
	public void a(biy biy, gi<bki> gi) {
		if (this.a(biy)) {
			for (bmb bmb5 : gl.an) {
				if (!bmb5.a().isEmpty()) {
					gi.add(bmd.a(new bki(this), bmb5));
				}
			}
		}
	}

	@Override
	public String f(bki bki) {
		return bmd.d(bki).b(this.a() + ".effect.");
	}
}
