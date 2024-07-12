import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class yv {
	private static final Logger a = LogManager.getLogger();
	private static final int b = 33 + chc.a(chc.m) - 2;
	private final Long2ObjectMap<ObjectSet<ze>> c = new Long2ObjectOpenHashMap<>();
	private final Long2ObjectOpenHashMap<aeg<zh<?>>> d = new Long2ObjectOpenHashMap<>();
	private final yv.a e = new yv.a();
	private final yv.b f = new yv.b(8);
	private final yv.c g = new yv.c(33);
	private final Set<yo> h = Sets.<yo>newHashSet();
	private final yr i;
	private final amp<yr.a<Runnable>> j;
	private final amp<yr.b> k;
	private final LongSet l = new LongOpenHashSet();
	private final Executor m;
	private long n;

	protected yv(Executor executor1, Executor executor2) {
		amp<Runnable> amp4 = amp.a("player ticket throttler", executor2::execute);
		yr yr5 = new yr(ImmutableList.of(amp4), executor1, 4);
		this.i = yr5;
		this.j = yr5.a(amp4, true);
		this.k = yr5.a(amp4);
		this.m = executor2;
	}

	protected void a() {
		this.n++;
		ObjectIterator<Entry<aeg<zh<?>>>> objectIterator2 = this.d.long2ObjectEntrySet().fastIterator();

		while (objectIterator2.hasNext()) {
			Entry<aeg<zh<?>>> entry3 = (Entry<aeg<zh<?>>>)objectIterator2.next();
			if (((aeg)entry3.getValue()).removeIf(zh -> zh.b(this.n))) {
				this.e.b(entry3.getLongKey(), a((aeg<zh<?>>)entry3.getValue()), false);
			}

			if (((aeg)entry3.getValue()).isEmpty()) {
				objectIterator2.remove();
			}
		}
	}

	private static int a(aeg<zh<?>> aeg) {
		return !aeg.isEmpty() ? aeg.b().b() : yp.a + 1;
	}

	protected abstract boolean a(long long1);

	@Nullable
	protected abstract yo b(long long1);

	@Nullable
	protected abstract yo a(long long1, int integer2, @Nullable yo yo, int integer4);

	public boolean a(yp yp) {
		this.f.a();
		this.g.a();
		int integer3 = Integer.MAX_VALUE - this.e.a(Integer.MAX_VALUE);
		boolean boolean4 = integer3 != 0;
		if (boolean4) {
		}

		if (!this.h.isEmpty()) {
			this.h.forEach(yo -> yo.a(yp));
			this.h.clear();
			return true;
		} else {
			if (!this.l.isEmpty()) {
				LongIterator longIterator5 = this.l.iterator();

				while (longIterator5.hasNext()) {
					long long6 = longIterator5.nextLong();
					if (this.e(long6).stream().anyMatch(zh -> zh.a() == zi.c)) {
						yo yo8 = yp.a(long6);
						if (yo8 == null) {
							throw new IllegalStateException();
						}

						CompletableFuture<Either<chj, yo.a>> completableFuture9 = yo8.b();
						completableFuture9.thenAccept(either -> this.m.execute(() -> this.k.a(yr.a(() -> {
								}, long6, false))));
					}
				}

				this.l.clear();
			}

			return boolean4;
		}
	}

	private void a(long long1, zh<?> zh) {
		aeg<zh<?>> aeg5 = this.e(long1);
		int integer6 = a(aeg5);
		zh<?> zh7 = aeg5.a(zh);
		zh7.a(this.n);
		if (zh.b() < integer6) {
			this.e.b(long1, zh.b(), true);
		}
	}

	private void b(long long1, zh<?> zh) {
		aeg<zh<?>> aeg5 = this.e(long1);
		if (aeg5.remove(zh)) {
		}

		if (aeg5.isEmpty()) {
			this.d.remove(long1);
		}

		this.e.b(long1, a(aeg5), false);
	}

	public <T> void a(zi<T> zi, bph bph, int integer, T object) {
		this.a(bph.a(), new zh<>(zi, integer, object));
	}

	public <T> void b(zi<T> zi, bph bph, int integer, T object) {
		zh<T> zh6 = new zh<>(zi, integer, object);
		this.b(bph.a(), zh6);
	}

	public <T> void c(zi<T> zi, bph bph, int integer, T object) {
		this.a(bph.a(), new zh<>(zi, 33 - integer, object));
	}

	public <T> void d(zi<T> zi, bph bph, int integer, T object) {
		zh<T> zh6 = new zh<>(zi, 33 - integer, object);
		this.b(bph.a(), zh6);
	}

	private aeg<zh<?>> e(long long1) {
		return this.d.computeIfAbsent(long1, long1x -> aeg.a(4));
	}

	protected void a(bph bph, boolean boolean2) {
		zh<bph> zh4 = new zh<>(zi.d, 31, bph);
		if (boolean2) {
			this.a(bph.a(), zh4);
		} else {
			this.b(bph.a(), zh4);
		}
	}

	public void a(go go, ze ze) {
		long long4 = go.r().a();
		this.c.computeIfAbsent(long4, long1 -> new ObjectOpenHashSet()).add(ze);
		this.f.b(long4, 0, true);
		this.g.b(long4, 0, true);
	}

	public void b(go go, ze ze) {
		long long4 = go.r().a();
		ObjectSet<ze> objectSet6 = this.c.get(long4);
		objectSet6.remove(ze);
		if (objectSet6.isEmpty()) {
			this.c.remove(long4);
			this.f.b(long4, Integer.MAX_VALUE, false);
			this.g.b(long4, Integer.MAX_VALUE, false);
		}
	}

	protected String c(long long1) {
		aeg<zh<?>> aeg4 = this.d.get(long1);
		String string5;
		if (aeg4 != null && !aeg4.isEmpty()) {
			string5 = aeg4.b().toString();
		} else {
			string5 = "no_ticket";
		}

		return string5;
	}

	protected void a(int integer) {
		this.g.a(integer);
	}

	public int b() {
		this.f.a();
		return this.f.a.size();
	}

	public boolean d(long long1) {
		this.f.a();
		return this.f.a.containsKey(long1);
	}

	public String c() {
		return this.i.a();
	}

	class a extends ys {
		public a() {
			super(yp.a + 2, 16, 256);
		}

		@Override
		protected int b(long long1) {
			aeg<zh<?>> aeg4 = yv.this.d.get(long1);
			if (aeg4 == null) {
				return Integer.MAX_VALUE;
			} else {
				return aeg4.isEmpty() ? Integer.MAX_VALUE : aeg4.b().b();
			}
		}

		@Override
		protected int c(long long1) {
			if (!yv.this.a(long1)) {
				yo yo4 = yv.this.b(long1);
				if (yo4 != null) {
					return yo4.j();
				}
			}

			return yp.a + 1;
		}

		@Override
		protected void a(long long1, int integer) {
			yo yo5 = yv.this.b(long1);
			int integer6 = yo5 == null ? yp.a + 1 : yo5.j();
			if (integer6 != integer) {
				yo5 = yv.this.a(long1, integer, yo5, integer6);
				if (yo5 != null) {
					yv.this.h.add(yo5);
				}
			}
		}

		public int a(int integer) {
			return this.b(integer);
		}
	}

	class b extends ys {
		protected final Long2ByteMap a = new Long2ByteOpenHashMap();
		protected final int b;

		protected b(int integer) {
			super(integer + 2, 16, 256);
			this.b = integer;
			this.a.defaultReturnValue((byte)(integer + 2));
		}

		@Override
		protected int c(long long1) {
			return this.a.get(long1);
		}

		@Override
		protected void a(long long1, int integer) {
			byte byte5;
			if (integer > this.b) {
				byte5 = this.a.remove(long1);
			} else {
				byte5 = this.a.put(long1, (byte)integer);
			}

			this.a(long1, byte5, integer);
		}

		@Override
		protected void a(long long1, int integer2, int integer3) {
		}

		@Override
		protected int b(long long1) {
			return this.d(long1) ? 0 : Integer.MAX_VALUE;
		}

		private boolean d(long long1) {
			ObjectSet<ze> objectSet4 = yv.this.c.get(long1);
			return objectSet4 != null && !objectSet4.isEmpty();
		}

		public void a() {
			this.b(Integer.MAX_VALUE);
		}
	}

	class c extends yv.b {
		private int e;
		private final Long2IntMap f = Long2IntMaps.synchronize(new Long2IntOpenHashMap());
		private final LongSet g = new LongOpenHashSet();

		protected c(int integer) {
			super(integer);
			this.e = 0;
			this.f.defaultReturnValue(integer + 2);
		}

		@Override
		protected void a(long long1, int integer2, int integer3) {
			this.g.add(long1);
		}

		@Override
		public void a(int integer) {
			for (it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry entry4 : this.a.long2ByteEntrySet()) {
				byte byte5 = entry4.getByteValue();
				long long6 = entry4.getLongKey();
				this.a(long6, byte5, this.c(byte5), byte5 <= integer - 2);
			}

			this.e = integer;
		}

		private void a(long long1, int integer, boolean boolean3, boolean boolean4) {
			if (boolean3 != boolean4) {
				zh<?> zh7 = new zh<>(zi.c, yv.b, new bph(long1));
				if (boolean4) {
					yv.this.j.a(yr.a(() -> yv.this.m.execute(() -> {
							if (this.c(this.c(long1))) {
								yv.this.a(long1, zh7);
								yv.this.l.add(long1);
							} else {
								yv.this.k.a(yr.a(() -> {
								}, long1, false));
							}
						}), long1, () -> integer));
				} else {
					yv.this.k.a(yr.a(() -> yv.this.m.execute(() -> yv.this.b(long1, zh7)), long1, true));
				}
			}
		}

		@Override
		public void a() {
			super.a();
			if (!this.g.isEmpty()) {
				LongIterator longIterator2 = this.g.iterator();

				while (longIterator2.hasNext()) {
					long long3 = longIterator2.nextLong();
					int integer5 = this.f.get(long3);
					int integer6 = this.c(long3);
					if (integer5 != integer6) {
						yv.this.i.a(new bph(long3), () -> this.f.get(long3), integer6, integer -> {
							if (integer >= this.f.defaultReturnValue()) {
								this.f.remove(long3);
							} else {
								this.f.put(long3, integer);
							}
						});
						this.a(long3, integer6, this.c(integer5), this.c(integer6));
					}
				}

				this.g.clear();
			}
		}

		private boolean c(int integer) {
			return integer <= this.e - 2;
		}
	}
}
