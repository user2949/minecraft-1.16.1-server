import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Arrays;

public class cwu extends cwq<cwu.a> {
	private static final fz[] k = new fz[]{fz.NORTH, fz.SOUTH, fz.WEST, fz.EAST};
	private final LongSet l = new LongOpenHashSet();
	private final LongSet m = new LongOpenHashSet();
	private final LongSet n = new LongOpenHashSet();
	private final LongSet o = new LongOpenHashSet();
	private volatile boolean p;

	protected cwu(chl chl) {
		super(bqi.SKY, chl, new cwu.a(new Long2ObjectOpenHashMap<>(), new Long2IntOpenHashMap(), Integer.MAX_VALUE));
	}

	@Override
	protected int d(long long1) {
		long long4 = go.e(long1);
		int integer6 = go.c(long4);
		cwu.a a7 = this.e;
		int integer8 = a7.c.get(go.f(long4));
		if (integer8 != a7.b && integer6 < integer8) {
			chd chd9 = this.a(a7, long4);
			if (chd9 == null) {
				for (long1 = fu.f(long1); chd9 == null; chd9 = this.a(a7, long4)) {
					long4 = go.a(long4, fz.UP);
					if (++integer6 >= integer8) {
						return 15;
					}

					long1 = fu.a(long1, 0, 16, 0);
				}
			}

			return chd9.a(go.b(fu.b(long1)), go.b(fu.c(long1)), go.b(fu.d(long1)));
		} else {
			return 15;
		}
	}

	@Override
	protected void k(long long1) {
		int integer4 = go.c(long1);
		if (this.f.b > integer4) {
			this.f.b = integer4;
			this.f.c.defaultReturnValue(this.f.b);
		}

		long long5 = go.f(long1);
		int integer7 = this.f.c.get(long5);
		if (integer7 < integer4 + 1) {
			this.f.c.put(long5, integer4 + 1);
			if (this.o.contains(long5)) {
				this.q(long1);
				if (integer7 > this.f.b) {
					long long8 = go.b(go.b(long1), integer7 - 1, go.d(long1));
					this.p(long8);
				}

				this.f();
			}
		}
	}

	private void p(long long1) {
		this.n.add(long1);
		this.m.remove(long1);
	}

	private void q(long long1) {
		this.m.add(long1);
		this.n.remove(long1);
	}

	private void f() {
		this.p = !this.m.isEmpty() || !this.n.isEmpty();
	}

	@Override
	protected void l(long long1) {
		long long4 = go.f(long1);
		boolean boolean6 = this.o.contains(long4);
		if (boolean6) {
			this.p(long1);
		}

		int integer7 = go.c(long1);
		if (this.f.c.get(long4) == integer7 + 1) {
			long long8;
			for (long8 = long1; !this.g(long8) && this.a(integer7); long8 = go.a(long8, fz.DOWN)) {
				integer7--;
			}

			if (this.g(long8)) {
				this.f.c.put(long4, integer7 + 1);
				if (boolean6) {
					this.q(long8);
				}
			} else {
				this.f.c.remove(long4);
			}
		}

		if (boolean6) {
			this.f();
		}
	}

	@Override
	protected void b(long long1, boolean boolean2) {
		this.d();
		if (boolean2 && this.o.add(long1)) {
			int integer5 = this.f.c.get(long1);
			if (integer5 != this.f.b) {
				long long6 = go.b(go.b(long1), integer5 - 1, go.d(long1));
				this.q(long6);
				this.f();
			}
		} else if (!boolean2) {
			this.o.remove(long1);
		}
	}

	@Override
	protected boolean a() {
		return super.a() || this.p;
	}

	@Override
	protected chd j(long long1) {
		chd chd4 = this.i.get(long1);
		if (chd4 != null) {
			return chd4;
		} else {
			long long5 = go.a(long1, fz.UP);
			int integer7 = this.f.c.get(go.f(long1));
			if (integer7 != this.f.b && go.c(long5) < integer7) {
				chd chd8;
				while ((chd8 = this.a(long5, true)) == null) {
					long5 = go.a(long5, fz.UP);
				}

				return new chd(new cwn(chd8, 0).a());
			} else {
				return new chd();
			}
		}
	}

	@Override
	protected void a(cwo<cwu.a, ?> cwo, boolean boolean2, boolean boolean3) {
		super.a(cwo, boolean2, boolean3);
		if (boolean2) {
			if (!this.m.isEmpty()) {
				LongIterator var4 = this.m.iterator();

				while (var4.hasNext()) {
					long long6 = (Long)var4.next();
					int integer8 = this.c(long6);
					if (integer8 != 2 && !this.n.contains(long6) && this.l.add(long6)) {
						if (integer8 == 1) {
							this.a(cwo, long6);
							if (this.g.add(long6)) {
								this.f.a(long6);
							}

							Arrays.fill(this.a(long6, true).a(), (byte)-1);
							int integer9 = go.c(go.b(long6));
							int integer10 = go.c(go.c(long6));
							int integer11 = go.c(go.d(long6));

							for (fz fz15 : k) {
								long long16 = go.a(long6, fz15);
								if ((this.n.contains(long16) || !this.l.contains(long16) && !this.m.contains(long16)) && this.g(long16)) {
									for (int integer18 = 0; integer18 < 16; integer18++) {
										for (int integer19 = 0; integer19 < 16; integer19++) {
											long long20;
											long long22;
											switch (fz15) {
												case NORTH:
													long20 = fu.a(integer9 + integer18, integer10 + integer19, integer11);
													long22 = fu.a(integer9 + integer18, integer10 + integer19, integer11 - 1);
													break;
												case SOUTH:
													long20 = fu.a(integer9 + integer18, integer10 + integer19, integer11 + 16 - 1);
													long22 = fu.a(integer9 + integer18, integer10 + integer19, integer11 + 16);
													break;
												case WEST:
													long20 = fu.a(integer9, integer10 + integer18, integer11 + integer19);
													long22 = fu.a(integer9 - 1, integer10 + integer18, integer11 + integer19);
													break;
												default:
													long20 = fu.a(integer9 + 16 - 1, integer10 + integer18, integer11 + integer19);
													long22 = fu.a(integer9 + 16, integer10 + integer18, integer11 + integer19);
											}

											cwo.a(long20, long22, cwo.b(long20, long22, 0), true);
										}
									}
								}
							}

							for (int integer12 = 0; integer12 < 16; integer12++) {
								for (int integer13 = 0; integer13 < 16; integer13++) {
									long long14 = fu.a(go.c(go.b(long6)) + integer12, go.c(go.c(long6)), go.c(go.d(long6)) + integer13);
									long long16 = fu.a(go.c(go.b(long6)) + integer12, go.c(go.c(long6)) - 1, go.c(go.d(long6)) + integer13);
									cwo.a(long14, long16, cwo.b(long14, long16, 0), true);
								}
							}
						} else {
							for (int integer9 = 0; integer9 < 16; integer9++) {
								for (int integer10 = 0; integer10 < 16; integer10++) {
									long long11 = fu.a(go.c(go.b(long6)) + integer9, go.c(go.c(long6)) + 16 - 1, go.c(go.d(long6)) + integer10);
									cwo.a(Long.MAX_VALUE, long11, 0, true);
								}
							}
						}
					}
				}
			}

			this.m.clear();
			if (!this.n.isEmpty()) {
				LongIterator var23 = this.n.iterator();

				while (var23.hasNext()) {
					long long6 = (Long)var23.next();
					if (this.l.remove(long6) && this.g(long6)) {
						for (int integer8 = 0; integer8 < 16; integer8++) {
							for (int integer9 = 0; integer9 < 16; integer9++) {
								long long10 = fu.a(go.c(go.b(long6)) + integer8, go.c(go.c(long6)) + 16 - 1, go.c(go.d(long6)) + integer9);
								cwo.a(Long.MAX_VALUE, long10, 15, false);
							}
						}
					}
				}
			}

			this.n.clear();
			this.p = false;
		}
	}

	protected boolean a(int integer) {
		return integer >= this.f.b;
	}

	protected boolean m(long long1) {
		int integer4 = fu.c(long1);
		if ((integer4 & 15) != 15) {
			return false;
		} else {
			long long5 = go.e(long1);
			long long7 = go.f(long5);
			if (!this.o.contains(long7)) {
				return false;
			} else {
				int integer9 = this.f.c.get(long7);
				return go.c(integer9) == integer4 + 16;
			}
		}
	}

	protected boolean n(long long1) {
		long long4 = go.f(long1);
		int integer6 = this.f.c.get(long4);
		return integer6 == this.f.b || go.c(long1) >= integer6;
	}

	protected boolean o(long long1) {
		long long4 = go.f(long1);
		return this.o.contains(long4);
	}

	public static final class a extends cwl<cwu.a> {
		private int b;
		private final Long2IntOpenHashMap c;

		public a(Long2ObjectOpenHashMap<chd> long2ObjectOpenHashMap, Long2IntOpenHashMap long2IntOpenHashMap, int integer) {
			super(long2ObjectOpenHashMap);
			this.c = long2IntOpenHashMap;
			long2IntOpenHashMap.defaultReturnValue(integer);
			this.b = integer;
		}

		public cwu.a b() {
			return new cwu.a(this.a.clone(), this.c.clone(), this.b);
		}
	}
}
