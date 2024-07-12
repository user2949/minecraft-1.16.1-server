public interface cyn {
	default <R extends cxi> cxj<R> a(cxm<R> cxm) {
		return () -> cxm.a((integer2, integer3) -> {
				cxm.a((long)integer2, (long)integer3);
				return this.a((cxn)cxm, integer2, integer3);
			});
	}

	int a(cxn cxn, int integer2, int integer3);
}
