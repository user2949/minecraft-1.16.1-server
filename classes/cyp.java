public interface cyp extends cyw {
	default <R extends cxi> cxj<R> a(cxm<R> cxm, cxj<R> cxj2, cxj<R> cxj3) {
		return () -> {
			R cxi5 = cxj2.make();
			R cxi6 = cxj3.make();
			return cxm.a((integer4, integer5) -> {
				cxm.a((long)integer4, (long)integer5);
				return this.a((cxn)cxm, cxi5, cxi6, integer4, integer5);
			}, cxi5, cxi6);
		};
	}

	int a(cxn cxn, cxi cxi2, cxi cxi3, int integer4, int integer5);
}
