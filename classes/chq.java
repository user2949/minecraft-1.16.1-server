import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class chq<T> implements chp<T> {
	private final cho<T> b;
	private final chp<T> c = (integer, objectx) -> 0;
	private final ge<T> d;
	private final Function<le, T> e;
	private final Function<T, le> f;
	private final T g;
	protected adj a;
	private cho<T> h;
	private int i;
	private final ReentrantLock j = new ReentrantLock();

	public void a() {
		if (this.j.isLocked() && !this.j.isHeldByCurrentThread()) {
			String string2 = (String)Thread.getAllStackTraces()
				.keySet()
				.stream()
				.filter(Objects::nonNull)
				.map(thread -> thread.getName() + ": \n\tat " + (String)Arrays.stream(thread.getStackTrace()).map(Object::toString).collect(Collectors.joining("\n\tat ")))
				.collect(Collectors.joining("\n"));
			j j3 = new j("Writing into PalettedContainer from multiple threads", new IllegalStateException());
			k k4 = j3.a("Thread dumps");
			k4.a("Thread dumps", string2);
			throw new s(j3);
		} else {
			this.j.lock();
		}
	}

	public void b() {
		this.j.unlock();
	}

	public chq(cho<T> cho, ge<T> ge, Function<le, T> function3, Function<T, le> function4, T object) {
		this.b = cho;
		this.d = ge;
		this.e = function3;
		this.f = function4;
		this.g = object;
		this.b(4);
	}

	private static int b(int integer1, int integer2, int integer3) {
		return integer2 << 8 | integer3 << 4 | integer1;
	}

	private void b(int integer) {
		if (integer != this.i) {
			this.i = integer;
			if (this.i <= 4) {
				this.i = 4;
				this.h = new chm<>(this.d, this.i, this, this.e);
			} else if (this.i < 9) {
				this.h = new chh<>(this.d, this.i, this, this.e, this.f);
			} else {
				this.h = this.b;
				this.i = aec.e(this.d.a());
			}

			this.h.a(this.g);
			this.a = new adj(this.i, 4096);
		}
	}

	@Override
	public int onResize(int integer, T object) {
		this.a();
		adj adj4 = this.a;
		cho<T> cho5 = this.h;
		this.b(integer);

		for (int integer6 = 0; integer6 < adj4.b(); integer6++) {
			T object7 = cho5.a(adj4.a(integer6));
			if (object7 != null) {
				this.b(integer6, object7);
			}
		}

		int integer6x = this.h.a(object);
		this.b();
		return integer6x;
	}

	public T a(int integer1, int integer2, int integer3, T object) {
		this.a();
		T object6 = this.a(b(integer1, integer2, integer3), object);
		this.b();
		return object6;
	}

	public T b(int integer1, int integer2, int integer3, T object) {
		return this.a(b(integer1, integer2, integer3), object);
	}

	protected T a(int integer, T object) {
		int integer4 = this.h.a(object);
		int integer5 = this.a.a(integer, integer4);
		T object6 = this.h.a(integer5);
		return object6 == null ? this.g : object6;
	}

	protected void b(int integer, T object) {
		int integer4 = this.h.a(object);
		this.a.b(integer, integer4);
	}

	public T a(int integer1, int integer2, int integer3) {
		return this.a(b(integer1, integer2, integer3));
	}

	protected T a(int integer) {
		T object3 = this.h.a(this.a.a(integer));
		return object3 == null ? this.g : object3;
	}

	public void b(mg mg) {
		this.a();
		mg.writeByte(this.i);
		this.h.b(mg);
		mg.a(this.a.a());
		this.b();
	}

	public void a(lk lk, long[] arr) {
		this.a();
		int integer4 = Math.max(4, aec.e(lk.size()));
		if (integer4 != this.i) {
			this.b(integer4);
		}

		this.h.a(lk);
		int integer5 = arr.length * 64 / 4096;
		if (this.h == this.b) {
			cho<T> cho6 = new chh<>(this.d, integer4, this.c, this.e, this.f);
			cho6.a(lk);
			adj adj7 = new adj(integer4, 4096, arr);

			for (int integer8 = 0; integer8 < 4096; integer8++) {
				this.a.b(integer8, this.b.a(cho6.a(adj7.a(integer8))));
			}
		} else if (integer5 == this.i) {
			System.arraycopy(arr, 0, this.a.a(), 0, arr.length);
		} else {
			adj adj6 = new adj(integer5, 4096, arr);

			for (int integer7 = 0; integer7 < 4096; integer7++) {
				this.a.b(integer7, adj6.a(integer7));
			}
		}

		this.b();
	}

	public void a(le le, String string2, String string3) {
		this.a();
		chh<T> chh5 = new chh<>(this.d, this.i, this.c, this.e, this.f);
		T object6 = this.g;
		int integer7 = chh5.a(this.g);
		int[] arr8 = new int[4096];

		for (int integer9 = 0; integer9 < 4096; integer9++) {
			T object10 = this.a(integer9);
			if (object10 != object6) {
				object6 = object10;
				integer7 = chh5.a(object10);
			}

			arr8[integer9] = integer7;
		}

		lk lk9 = new lk();
		chh5.b(lk9);
		le.a(string2, lk9);
		int integer10 = Math.max(4, aec.e(lk9.size()));
		adj adj11 = new adj(integer10, 4096);

		for (int integer12 = 0; integer12 < arr8.length; integer12++) {
			adj11.b(integer12, arr8[integer12]);
		}

		le.a(string3, adj11.a());
		this.b();
	}

	public int c() {
		return 1 + this.h.a() + mg.a(this.a.b()) + this.a.a().length * 8;
	}

	public boolean a(Predicate<T> predicate) {
		return this.h.a(predicate);
	}

	public void a(chq.a<T> a) {
		Int2IntMap int2IntMap3 = new Int2IntOpenHashMap();
		this.a.a(integer -> int2IntMap3.put(integer, int2IntMap3.get(integer) + 1));
		int2IntMap3.int2IntEntrySet().forEach(entry -> a.accept(this.h.a(entry.getIntKey()), entry.getIntValue()));
	}

	@FunctionalInterface
	public interface a<T> {
		void accept(T object, int integer);
	}
}
