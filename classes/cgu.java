public interface cgu {
	void a(cgw cgw, double double2);

	void a(cgw cgw, double double2, double double3, long long4);

	void a(cgw cgw, double double2, double double3);

	void a(cgw cgw, int integer);

	void b(cgw cgw, int integer);

	void b(cgw cgw, double double2);

	void c(cgw cgw, double double2);

	public static class a implements cgu {
		private final cgw a;

		public a(cgw cgw) {
			this.a = cgw;
		}

		@Override
		public void a(cgw cgw, double double2) {
			this.a.a(double2);
		}

		@Override
		public void a(cgw cgw, double double2, double double3, long long4) {
			this.a.a(double2, double3, long4);
		}

		@Override
		public void a(cgw cgw, double double2, double double3) {
			this.a.c(double2, double3);
		}

		@Override
		public void a(cgw cgw, int integer) {
			this.a.b(integer);
		}

		@Override
		public void b(cgw cgw, int integer) {
			this.a.c(integer);
		}

		@Override
		public void b(cgw cgw, double double2) {
			this.a.c(double2);
		}

		@Override
		public void c(cgw cgw, double double2) {
			this.a.b(double2);
		}
	}
}
