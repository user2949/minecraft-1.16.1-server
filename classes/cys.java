public interface cys extends cyo, cyv {
	int a(cxn cxn, int integer);

	@Override
	default int a(cxm<?> cxm, cxi cxi, int integer3, int integer4) {
		int integer6 = cxi.a(this.a(integer3 + 1), this.b(integer4 + 1));
		return this.a(cxm, integer6);
	}
}
