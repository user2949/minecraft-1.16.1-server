public interface cyo extends cyw {
	default <R extends cxi> cxj<R> a(cxm<R> cxm, cxj<R> cxj) {
		return () -> {
			R cxi4 = cxj.make();
			return cxm.a((integer3, integer4) -> {
				cxm.a((long)integer3, (long)integer4);
				return this.a(cxm, cxi4, integer3, integer4);
			}, cxi4);
		};
	}

	int a(cxm<?> cxm, cxi cxi, int integer3, int integer4);
}
