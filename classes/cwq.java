import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import javax.annotation.Nullable;

public abstract class cwq<M extends cwl<M>> extends yz {
	protected static final chd a = new chd();
	private static final fz[] k = fz.values();
	private final bqi l;
	private final chl m;
	protected final LongSet b = new LongOpenHashSet();
	protected final LongSet c = new LongOpenHashSet();
	protected final LongSet d = new LongOpenHashSet();
	protected volatile M e;
	protected final M f;
	protected final LongSet g = new LongOpenHashSet();
	protected final LongSet h = new LongOpenHashSet();
	protected final Long2ObjectMap<chd> i = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap<>());
	private final LongSet n = new LongOpenHashSet();
	private final LongSet o = new LongOpenHashSet();
	private final LongSet p = new LongOpenHashSet();
	protected volatile boolean j;

	protected cwq(bqi bqi, chl chl, M cwl) {
		super(3, 16, 256);
		this.l = bqi;
		this.m = chl;
		this.f = cwl;
		this.e = cwl.b();
		this.e.d();
	}

	protected boolean g(long long1) {
		return this.a(long1, true) != null;
	}

	@Nullable
	protected chd a(long long1, boolean boolean2) {
		return this.a(boolean2 ? this.f : this.e, long1);
	}

	@Nullable
	protected chd a(M cwl, long long2) {
		return cwl.c(long2);
	}

	@Nullable
	public chd h(long long1) {
		chd chd4 = this.i.get(long1);
		return chd4 != null ? chd4 : this.a(long1, false);
	}

	protected abstract int d(long long1);

	protected int i(long long1) {
		long long4 = go.e(long1);
		chd chd6 = this.a(long4, true);
		return chd6.a(go.b(fu.b(long1)), go.b(fu.c(long1)), go.b(fu.d(long1)));
	}

	protected void b(long long1, int integer) {
		long long5 = go.e(long1);
		if (this.g.add(long5)) {
			this.f.a(long5);
		}

		chd chd7 = this.a(long5, true);
		chd7.a(go.b(fu.b(long1)), go.b(fu.c(long1)), go.b(fu.d(long1)), integer);

		for (int integer8 = -1; integer8 <= 1; integer8++) {
			for (int integer9 = -1; integer9 <= 1; integer9++) {
				for (int integer10 = -1; integer10 <= 1; integer10++) {
					this.h.add(go.e(fu.a(long1, integer9, integer10, integer8)));
				}
			}
		}
	}

	@Override
	protected int c(long long1) {
		if (long1 == Long.MAX_VALUE) {
			return 2;
		} else if (this.b.contains(long1)) {
			return 0;
		} else {
			return !this.p.contains(long1) && this.f.b(long1) ? 1 : 2;
		}
	}

	@Override
	protected int b(long long1) {
		if (this.c.contains(long1)) {
			return 2;
		} else {
			return !this.b.contains(long1) && !this.d.contains(long1) ? 2 : 0;
		}
	}

	@Override
	protected void a(long long1, int integer) {
		int integer5 = this.c(long1);
		if (integer5 != 0 && integer == 0) {
			this.b.add(long1);
			this.d.remove(long1);
		}

		if (integer5 == 0 && integer != 0) {
			this.b.remove(long1);
			this.c.remove(long1);
		}

		if (integer5 >= 2 && integer != 2) {
			if (this.p.contains(long1)) {
				this.p.remove(long1);
			} else {
				this.f.a(long1, this.j(long1));
				this.g.add(long1);
				this.k(long1);

				for (int integer6 = -1; integer6 <= 1; integer6++) {
					for (int integer7 = -1; integer7 <= 1; integer7++) {
						for (int integer8 = -1; integer8 <= 1; integer8++) {
							this.h.add(go.e(fu.a(long1, integer7, integer8, integer6)));
						}
					}
				}
			}
		}

		if (integer5 != 2 && integer >= 2) {
			this.p.add(long1);
		}

		this.j = !this.p.isEmpty();
	}

	protected chd j(long long1) {
		chd chd4 = this.i.get(long1);
		return chd4 != null ? chd4 : new chd();
	}

	protected void a(cwo<?, ?> cwo, long long2) {
		if (cwo.c() < 8192) {
			cwo.a(long2x -> go.e(long2x) == long2);
		} else {
			int integer5 = go.c(go.b(long2));
			int integer6 = go.c(go.c(long2));
			int integer7 = go.c(go.d(long2));

			for (int integer8 = 0; integer8 < 16; integer8++) {
				for (int integer9 = 0; integer9 < 16; integer9++) {
					for (int integer10 = 0; integer10 < 16; integer10++) {
						long long11 = fu.a(integer5 + integer8, integer6 + integer9, integer7 + integer10);
						cwo.e(long11);
					}
				}
			}
		}
	}

	protected boolean a() {
		return this.j;
	}

	protected void a(cwo<M, ?> cwo, boolean boolean2, boolean boolean3) {
		if (this.a() || !this.i.isEmpty()) {
			LongIterator objectIterator5 = this.p.iterator();

			while (objectIterator5.hasNext()) {
				long long6 = (Long)objectIterator5.next();
				this.a(cwo, long6);
				chd chd8 = this.i.remove(long6);
				chd chd9 = this.f.d(long6);
				if (this.o.contains(go.f(long6))) {
					if (chd8 != null) {
						this.i.put(long6, chd8);
					} else if (chd9 != null) {
						this.i.put(long6, chd9);
					}
				}
			}

			this.f.c();
			objectIterator5 = this.p.iterator();

			while (objectIterator5.hasNext()) {
				long long6 = (Long)objectIterator5.next();
				this.l(long6);
			}

			this.p.clear();
			this.j = false;

			for (Entry<chd> entry6 : this.i.long2ObjectEntrySet()) {
				long long7 = entry6.getLongKey();
				if (this.g(long7)) {
					chd chd9 = (chd)entry6.getValue();
					if (this.f.c(long7) != chd9) {
						this.a(cwo, long7);
						this.f.a(long7, chd9);
						this.g.add(long7);
					}
				}
			}

			this.f.c();
			if (!boolean3) {
				objectIterator5 = this.i.keySet().iterator();

				while (objectIterator5.hasNext()) {
					long long6 = (Long)objectIterator5.next();
					this.b(cwo, long6);
				}
			} else {
				objectIterator5 = this.n.iterator();

				while (objectIterator5.hasNext()) {
					long long6 = (Long)objectIterator5.next();
					this.b(cwo, long6);
				}
			}

			this.n.clear();
			ObjectIterator<Entry<chd>> objectIterator5x = this.i.long2ObjectEntrySet().iterator();

			while (objectIterator5x.hasNext()) {
				Entry<chd> entry6x = (Entry<chd>)objectIterator5x.next();
				long long7 = entry6x.getLongKey();
				if (this.g(long7)) {
					objectIterator5x.remove();
				}
			}
		}
	}

	private void b(cwo<M, ?> cwo, long long2) {
		if (this.g(long2)) {
			int integer5 = go.c(go.b(long2));
			int integer6 = go.c(go.c(long2));
			int integer7 = go.c(go.d(long2));

			for (fz fz11 : k) {
				long long12 = go.a(long2, fz11);
				if (!this.i.containsKey(long12) && this.g(long12)) {
					for (int integer14 = 0; integer14 < 16; integer14++) {
						for (int integer15 = 0; integer15 < 16; integer15++) {
							long long16;
							long long18;
							switch (fz11) {
								case DOWN:
									long16 = fu.a(integer5 + integer15, integer6, integer7 + integer14);
									long18 = fu.a(integer5 + integer15, integer6 - 1, integer7 + integer14);
									break;
								case UP:
									long16 = fu.a(integer5 + integer15, integer6 + 16 - 1, integer7 + integer14);
									long18 = fu.a(integer5 + integer15, integer6 + 16, integer7 + integer14);
									break;
								case NORTH:
									long16 = fu.a(integer5 + integer14, integer6 + integer15, integer7);
									long18 = fu.a(integer5 + integer14, integer6 + integer15, integer7 - 1);
									break;
								case SOUTH:
									long16 = fu.a(integer5 + integer14, integer6 + integer15, integer7 + 16 - 1);
									long18 = fu.a(integer5 + integer14, integer6 + integer15, integer7 + 16);
									break;
								case WEST:
									long16 = fu.a(integer5, integer6 + integer14, integer7 + integer15);
									long18 = fu.a(integer5 - 1, integer6 + integer14, integer7 + integer15);
									break;
								default:
									long16 = fu.a(integer5 + 16 - 1, integer6 + integer14, integer7 + integer15);
									long18 = fu.a(integer5 + 16, integer6 + integer14, integer7 + integer15);
							}

							cwo.a(long16, long18, cwo.b(long16, long18, cwo.c(long16)), false);
							cwo.a(long18, long16, cwo.b(long18, long16, cwo.c(long18)), false);
						}
					}
				}
			}
		}
	}

	protected void k(long long1) {
	}

	protected void l(long long1) {
	}

	protected void b(long long1, boolean boolean2) {
	}

	public void c(long long1, boolean boolean2) {
		if (boolean2) {
			this.o.add(long1);
		} else {
			this.o.remove(long1);
		}
	}

	protected void a(long long1, @Nullable chd chd, boolean boolean3) {
		if (chd != null) {
			this.i.put(long1, chd);
			if (!boolean3) {
				this.n.add(long1);
			}
		} else {
			this.i.remove(long1);
		}
	}

	protected void d(long long1, boolean boolean2) {
		boolean boolean5 = this.b.contains(long1);
		if (!boolean5 && !boolean2) {
			this.d.add(long1);
			this.a(Long.MAX_VALUE, long1, 0, true);
		}

		if (boolean5 && boolean2) {
			this.c.add(long1);
			this.a(Long.MAX_VALUE, long1, 2, false);
		}
	}

	protected void d() {
		if (this.b()) {
			this.b(Integer.MAX_VALUE);
		}
	}

	protected void e() {
		if (!this.g.isEmpty()) {
			M cwl2 = this.f.b();
			cwl2.d();
			this.e = cwl2;
			this.g.clear();
		}

		if (!this.h.isEmpty()) {
			LongIterator longIterator2 = this.h.iterator();

			while (longIterator2.hasNext()) {
				long long3 = longIterator2.nextLong();
				this.m.a(this.l, go.a(long3));
			}

			this.h.clear();
		}
	}
}
