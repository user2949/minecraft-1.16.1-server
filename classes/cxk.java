import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;

public final class cxk implements cxi {
	private final cyx a;
	private final Long2IntLinkedOpenHashMap b;
	private final int c;

	public cxk(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap, int integer, cyx cyx) {
		this.b = long2IntLinkedOpenHashMap;
		this.c = integer;
		this.a = cyx;
	}

	@Override
	public int a(int integer1, int integer2) {
		long long4 = bph.a(integer1, integer2);
		synchronized (this.b) {
			int integer7 = this.b.get(long4);
			if (integer7 != Integer.MIN_VALUE) {
				return integer7;
			} else {
				int integer8 = this.a.apply(integer1, integer2);
				this.b.put(long4, integer8);
				if (this.b.size() > this.c) {
					for (int integer9 = 0; integer9 < this.c / 16; integer9++) {
						this.b.removeFirstInt();
					}
				}

				return integer8;
			}
		}
	}

	public int a() {
		return this.c;
	}
}
