public enum fs {
	NONE {
		@Override
		public int a(int integer1, int integer2, int integer3, fz.a a) {
			return a.a(integer1, integer2, integer3);
		}

		@Override
		public fz.a a(fz.a a) {
			return a;
		}

		@Override
		public fs a() {
			return this;
		}
	},
	FORWARD {
		@Override
		public int a(int integer1, int integer2, int integer3, fz.a a) {
			return a.a(integer3, integer1, integer2);
		}

		@Override
		public fz.a a(fz.a a) {
			return d[Math.floorMod(a.ordinal() + 1, 3)];
		}

		@Override
		public fs a() {
			return BACKWARD;
		}
	},
	BACKWARD {
		@Override
		public int a(int integer1, int integer2, int integer3, fz.a a) {
			return a.a(integer2, integer3, integer1);
		}

		@Override
		public fz.a a(fz.a a) {
			return d[Math.floorMod(a.ordinal() - 1, 3)];
		}

		@Override
		public fs a() {
			return FORWARD;
		}
	};

	public static final fz.a[] d = fz.a.values();
	public static final fs[] e = values();

	private fs() {
	}

	public abstract int a(int integer1, int integer2, int integer3, fz.a a);

	public abstract fz.a a(fz.a a);

	public abstract fs a();

	public static fs a(fz.a a1, fz.a a2) {
		return e[Math.floorMod(a2.ordinal() - a1.ordinal(), 3)];
	}
}
