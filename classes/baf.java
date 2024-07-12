public abstract class baf extends bae {
	public baf(bac bac) {
		super(bac);
	}

	@Override
	public boolean a() {
		return true;
	}

	@Override
	public float a(anw anw, float float2) {
		if (anw.j() instanceof beg) {
			anw.j().f(1);
			return 0.0F;
		} else {
			return super.a(anw, float2);
		}
	}
}
