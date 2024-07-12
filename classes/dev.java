public abstract class dev {
	private static final fz.a[] d = fz.a.values();
	protected final int a;
	protected final int b;
	protected final int c;

	protected dev(int integer1, int integer2, int integer3) {
		this.a = integer1;
		this.b = integer2;
		this.c = integer3;
	}

	public boolean a(fs fs, int integer2, int integer3, int integer4) {
		return this.c(fs.a(integer2, integer3, integer4, fz.a.X), fs.a(integer2, integer3, integer4, fz.a.Y), fs.a(integer2, integer3, integer4, fz.a.Z));
	}

	public boolean c(int integer1, int integer2, int integer3) {
		if (integer1 < 0 || integer2 < 0 || integer3 < 0) {
			return false;
		} else {
			return integer1 < this.a && integer2 < this.b && integer3 < this.c ? this.b(integer1, integer2, integer3) : false;
		}
	}

	public boolean b(fs fs, int integer2, int integer3, int integer4) {
		return this.b(fs.a(integer2, integer3, integer4, fz.a.X), fs.a(integer2, integer3, integer4, fz.a.Y), fs.a(integer2, integer3, integer4, fz.a.Z));
	}

	public abstract boolean b(int integer1, int integer2, int integer3);

	public abstract void a(int integer1, int integer2, int integer3, boolean boolean4, boolean boolean5);

	public boolean a() {
		for (fz.a a5 : d) {
			if (this.a(a5) >= this.b(a5)) {
				return true;
			}
		}

		return false;
	}

	public abstract int a(fz.a a);

	public abstract int b(fz.a a);

	public int c(fz.a a) {
		return a.a(this.a, this.b, this.c);
	}

	public int b() {
		return this.c(fz.a.X);
	}

	public int c() {
		return this.c(fz.a.Y);
	}

	public int d() {
		return this.c(fz.a.Z);
	}

	protected boolean a(int integer1, int integer2, int integer3, int integer4) {
		for (int integer6 = integer1; integer6 < integer2; integer6++) {
			if (!this.c(integer3, integer4, integer6)) {
				return false;
			}
		}

		return true;
	}

	protected void a(int integer1, int integer2, int integer3, int integer4, boolean boolean5) {
		for (int integer7 = integer1; integer7 < integer2; integer7++) {
			this.a(integer3, integer4, integer7, false, boolean5);
		}
	}

	protected boolean a(int integer1, int integer2, int integer3, int integer4, int integer5) {
		for (int integer7 = integer1; integer7 < integer2; integer7++) {
			if (!this.a(integer3, integer4, integer7, integer5)) {
				return false;
			}
		}

		return true;
	}

	public void b(dev.b b, boolean boolean2) {
		dev dev4 = new dep(this);

		for (int integer5 = 0; integer5 <= this.a; integer5++) {
			for (int integer6 = 0; integer6 <= this.b; integer6++) {
				int integer7 = -1;

				for (int integer8 = 0; integer8 <= this.c; integer8++) {
					if (dev4.c(integer5, integer6, integer8)) {
						if (boolean2) {
							if (integer7 == -1) {
								integer7 = integer8;
							}
						} else {
							b.consume(integer5, integer6, integer8, integer5 + 1, integer6 + 1, integer8 + 1);
						}
					} else if (integer7 != -1) {
						int integer9 = integer5;
						int integer10 = integer5;
						int integer11 = integer6;
						int integer12 = integer6;
						dev4.a(integer7, integer8, integer5, integer6, false);

						while (dev4.a(integer7, integer8, integer9 - 1, integer11)) {
							dev4.a(integer7, integer8, integer9 - 1, integer11, false);
							integer9--;
						}

						while (dev4.a(integer7, integer8, integer10 + 1, integer11)) {
							dev4.a(integer7, integer8, integer10 + 1, integer11, false);
							integer10++;
						}

						while (dev4.a(integer9, integer10 + 1, integer7, integer8, integer11 - 1)) {
							for (int integer13 = integer9; integer13 <= integer10; integer13++) {
								dev4.a(integer7, integer8, integer13, integer11 - 1, false);
							}

							integer11--;
						}

						while (dev4.a(integer9, integer10 + 1, integer7, integer8, integer12 + 1)) {
							for (int integer13 = integer9; integer13 <= integer10; integer13++) {
								dev4.a(integer7, integer8, integer13, integer12 + 1, false);
							}

							integer12++;
						}

						b.consume(integer9, integer11, integer7, integer10 + 1, integer12 + 1, integer8);
						integer7 = -1;
					}
				}
			}
		}
	}

	public void a(dev.a a) {
		this.a(a, fs.NONE);
		this.a(a, fs.FORWARD);
		this.a(a, fs.BACKWARD);
	}

	private void a(dev.a a, fs fs) {
		fs fs4 = fs.a();
		fz.a a5 = fs4.a(fz.a.Z);
		int integer6 = this.c(fs4.a(fz.a.X));
		int integer7 = this.c(fs4.a(fz.a.Y));
		int integer8 = this.c(a5);
		fz fz9 = fz.a(a5, fz.b.NEGATIVE);
		fz fz10 = fz.a(a5, fz.b.POSITIVE);

		for (int integer11 = 0; integer11 < integer6; integer11++) {
			for (int integer12 = 0; integer12 < integer7; integer12++) {
				boolean boolean13 = false;

				for (int integer14 = 0; integer14 <= integer8; integer14++) {
					boolean boolean15 = integer14 != integer8 && this.b(fs4, integer11, integer12, integer14);
					if (!boolean13 && boolean15) {
						a.consume(
							fz9, fs4.a(integer11, integer12, integer14, fz.a.X), fs4.a(integer11, integer12, integer14, fz.a.Y), fs4.a(integer11, integer12, integer14, fz.a.Z)
						);
					}

					if (boolean13 && !boolean15) {
						a.consume(
							fz10,
							fs4.a(integer11, integer12, integer14 - 1, fz.a.X),
							fs4.a(integer11, integer12, integer14 - 1, fz.a.Y),
							fs4.a(integer11, integer12, integer14 - 1, fz.a.Z)
						);
					}

					boolean13 = boolean15;
				}
			}
		}
	}

	public interface a {
		void consume(fz fz, int integer2, int integer3, int integer4);
	}

	public interface b {
		void consume(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6);
	}
}
