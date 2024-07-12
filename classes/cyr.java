public interface cyr extends cyo, cyu {
	int a(cxn cxn, int integer);

	@Override
	default int a(cxm<?> cxm, cxi cxi, int integer3, int integer4) {
		return this.a(cxm, cxi.a(this.a(integer3), this.b(integer4)));
	}
}
