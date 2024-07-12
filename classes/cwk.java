import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

public class cwk extends cwq<cwk.a> {
	protected cwk(chl chl) {
		super(bqi.BLOCK, chl, new cwk.a(new Long2ObjectOpenHashMap<>()));
	}

	@Override
	protected int d(long long1) {
		long long4 = go.e(long1);
		chd chd6 = this.a(long4, false);
		return chd6 == null ? 0 : chd6.a(go.b(fu.b(long1)), go.b(fu.c(long1)), go.b(fu.d(long1)));
	}

	public static final class a extends cwl<cwk.a> {
		public a(Long2ObjectOpenHashMap<chd> long2ObjectOpenHashMap) {
			super(long2ObjectOpenHashMap);
		}

		public cwk.a b() {
			return new cwk.a(this.a.clone());
		}
	}
}
