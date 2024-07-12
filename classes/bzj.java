public enum bzj {
	NONE(c.IDENTITY),
	LEFT_RIGHT(c.INVERT_Z),
	FRONT_BACK(c.INVERT_X);

	private final c d;

	private bzj(c c) {
		this.d = c;
	}

	public int a(int integer1, int integer2) {
		int integer4 = integer2 / 2;
		int integer5 = integer1 > integer4 ? integer1 - integer2 : integer1;
		switch (this) {
			case FRONT_BACK:
				return (integer2 - integer5) % integer2;
			case LEFT_RIGHT:
				return (integer4 - integer5 + integer2) % integer2;
			default:
				return integer1;
		}
	}

	public cap a(fz fz) {
		fz.a a3 = fz.n();
		return (this != LEFT_RIGHT || a3 != fz.a.Z) && (this != FRONT_BACK || a3 != fz.a.X) ? cap.NONE : cap.CLOCKWISE_180;
	}

	public fz b(fz fz) {
		if (this == FRONT_BACK && fz.n() == fz.a.X) {
			return fz.f();
		} else {
			return this == LEFT_RIGHT && fz.n() == fz.a.Z ? fz.f() : fz;
		}
	}

	public c a() {
		return this.d;
	}
}
