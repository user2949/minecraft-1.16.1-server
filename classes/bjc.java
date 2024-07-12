public class bjc extends bin {
	private final fz g;

	public bjc(bqb bqb, fu fu, fz fz3, bki bki, fz fz5) {
		super(bqb, null, anf.MAIN_HAND, bki, new deh(dem.c(fu), fz5, fu, false));
		this.g = fz3;
	}

	@Override
	public fu a() {
		return this.d.a();
	}

	@Override
	public boolean b() {
		return this.e.d_(this.d.a()).a(this);
	}

	@Override
	public boolean c() {
		return this.b();
	}

	@Override
	public fz d() {
		return fz.DOWN;
	}

	@Override
	public fz[] e() {
		switch (this.g) {
			case DOWN:
			default:
				return new fz[]{fz.DOWN, fz.NORTH, fz.EAST, fz.SOUTH, fz.WEST, fz.UP};
			case UP:
				return new fz[]{fz.DOWN, fz.UP, fz.NORTH, fz.EAST, fz.SOUTH, fz.WEST};
			case NORTH:
				return new fz[]{fz.DOWN, fz.NORTH, fz.EAST, fz.WEST, fz.UP, fz.SOUTH};
			case SOUTH:
				return new fz[]{fz.DOWN, fz.SOUTH, fz.EAST, fz.WEST, fz.UP, fz.NORTH};
			case WEST:
				return new fz[]{fz.DOWN, fz.WEST, fz.SOUTH, fz.UP, fz.NORTH, fz.EAST};
			case EAST:
				return new fz[]{fz.DOWN, fz.EAST, fz.SOUTH, fz.UP, fz.NORTH, fz.WEST};
		}
	}

	@Override
	public fz f() {
		return this.g.n() == fz.a.Y ? fz.NORTH : this.g;
	}

	@Override
	public boolean g() {
		return false;
	}

	@Override
	public float h() {
		return (float)(this.g.d() * 90);
	}
}
