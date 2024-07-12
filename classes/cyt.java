public interface cyt extends cyo, cyv {
	int a(cxn cxn, int integer2, int integer3, int integer4, int integer5, int integer6);

	@Override
	default int a(cxm<?> cxm, cxi cxi, int integer3, int integer4) {
		return this.a(
			cxm,
			cxi.a(this.a(integer3 + 1), this.b(integer4 + 0)),
			cxi.a(this.a(integer3 + 2), this.b(integer4 + 1)),
			cxi.a(this.a(integer3 + 1), this.b(integer4 + 2)),
			cxi.a(this.a(integer3 + 0), this.b(integer4 + 1)),
			cxi.a(this.a(integer3 + 1), this.b(integer4 + 1))
		);
	}
}
