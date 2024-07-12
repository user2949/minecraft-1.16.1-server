public interface cxm<R extends cxi> extends cxn {
	void a(long long1, long long2);

	R a(cyx cyx);

	default R a(cyx cyx, R cxi) {
		return this.a(cyx);
	}

	default R a(cyx cyx, R cxi2, R cxi3) {
		return this.a(cyx);
	}

	default int a(int integer1, int integer2) {
		return this.a(2) == 0 ? integer1 : integer2;
	}

	default int a(int integer1, int integer2, int integer3, int integer4) {
		int integer6 = this.a(4);
		if (integer6 == 0) {
			return integer1;
		} else if (integer6 == 1) {
			return integer2;
		} else {
			return integer6 == 2 ? integer3 : integer4;
		}
	}
}
