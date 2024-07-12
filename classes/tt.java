import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class tt {
	private static final Logger a = LogManager.getLogger();
	private static final Map<Class<? extends aom>, Integer> b = Maps.<Class<? extends aom>, Integer>newHashMap();
	private final aom c;
	private final Map<Integer, tt.a<?>> d = Maps.<Integer, tt.a<?>>newHashMap();
	private final ReadWriteLock e = new ReentrantReadWriteLock();
	private boolean f = true;
	private boolean g;

	public tt(aom aom) {
		this.c = aom;
	}

	public static <T> tq<T> a(Class<? extends aom> class1, tr<T> tr) {
		if (a.isDebugEnabled()) {
			try {
				Class<?> class3 = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
				if (!class3.equals(class1)) {
					a.debug("defineId called for: {} from {}", class1, class3, new RuntimeException());
				}
			} catch (ClassNotFoundException var5) {
			}
		}

		int integer3;
		if (b.containsKey(class1)) {
			integer3 = (Integer)b.get(class1) + 1;
		} else {
			int integer4 = 0;
			Class<?> class5 = class1;

			while (class5 != aom.class) {
				class5 = class5.getSuperclass();
				if (b.containsKey(class5)) {
					integer4 = (Integer)b.get(class5) + 1;
					break;
				}
			}

			integer3 = integer4;
		}

		if (integer3 > 254) {
			throw new IllegalArgumentException("Data value id is too big with " + integer3 + "! (Max is " + 254 + ")");
		} else {
			b.put(class1, integer3);
			return tr.a(integer3);
		}
	}

	public <T> void a(tq<T> tq, T object) {
		int integer4 = tq.a();
		if (integer4 > 254) {
			throw new IllegalArgumentException("Data value id is too big with " + integer4 + "! (Max is " + 254 + ")");
		} else if (this.d.containsKey(integer4)) {
			throw new IllegalArgumentException("Duplicate id value for " + integer4 + "!");
		} else if (ts.b(tq.b()) < 0) {
			throw new IllegalArgumentException("Unregistered serializer " + tq.b() + " for " + integer4 + "!");
		} else {
			this.c(tq, object);
		}
	}

	private <T> void c(tq<T> tq, T object) {
		tt.a<T> a4 = new tt.a<>(tq, object);
		this.e.writeLock().lock();
		this.d.put(tq.a(), a4);
		this.f = false;
		this.e.writeLock().unlock();
	}

	private <T> tt.a<T> b(tq<T> tq) {
		this.e.readLock().lock();

		tt.a<T> a3;
		try {
			a3 = (tt.a<T>)this.d.get(tq.a());
		} catch (Throwable var9) {
			j j5 = j.a(var9, "Getting synched entity data");
			k k6 = j5.a("Synched entity data");
			k6.a("Data ID", tq);
			throw new s(j5);
		} finally {
			this.e.readLock().unlock();
		}

		return a3;
	}

	public <T> T a(tq<T> tq) {
		return this.b(tq).b();
	}

	public <T> void b(tq<T> tq, T object) {
		tt.a<T> a4 = this.b(tq);
		if (ObjectUtils.notEqual(object, a4.b())) {
			a4.a(object);
			this.c.a(tq);
			a4.a(true);
			this.g = true;
		}
	}

	public boolean a() {
		return this.g;
	}

	public static void a(List<tt.a<?>> list, mg mg) throws IOException {
		if (list != null) {
			int integer3 = 0;

			for (int integer4 = list.size(); integer3 < integer4; integer3++) {
				a(mg, (tt.a)list.get(integer3));
			}
		}

		mg.writeByte(255);
	}

	@Nullable
	public List<tt.a<?>> b() {
		List<tt.a<?>> list2 = null;
		if (this.g) {
			this.e.readLock().lock();

			for (tt.a<?> a4 : this.d.values()) {
				if (a4.c()) {
					a4.a(false);
					if (list2 == null) {
						list2 = Lists.<tt.a<?>>newArrayList();
					}

					list2.add(a4.d());
				}
			}

			this.e.readLock().unlock();
		}

		this.g = false;
		return list2;
	}

	@Nullable
	public List<tt.a<?>> c() {
		List<tt.a<?>> list2 = null;
		this.e.readLock().lock();

		for (tt.a<?> a4 : this.d.values()) {
			if (list2 == null) {
				list2 = Lists.<tt.a<?>>newArrayList();
			}

			list2.add(a4.d());
		}

		this.e.readLock().unlock();
		return list2;
	}

	private static <T> void a(mg mg, tt.a<T> a) throws IOException {
		tq<T> tq3 = a.a();
		int integer4 = ts.b(tq3.b());
		if (integer4 < 0) {
			throw new EncoderException("Unknown serializer type " + tq3.b());
		} else {
			mg.writeByte(tq3.a());
			mg.d(integer4);
			tq3.b().a(mg, a.b());
		}
	}

	@Nullable
	public static List<tt.a<?>> a(mg mg) throws IOException {
		List<tt.a<?>> list2 = null;

		int integer3;
		while ((integer3 = mg.readUnsignedByte()) != 255) {
			if (list2 == null) {
				list2 = Lists.<tt.a<?>>newArrayList();
			}

			int integer4 = mg.i();
			tr<?> tr5 = ts.a(integer4);
			if (tr5 == null) {
				throw new DecoderException("Unknown serializer type " + integer4);
			}

			list2.add(a(mg, integer3, tr5));
		}

		return list2;
	}

	private static <T> tt.a<T> a(mg mg, int integer, tr<T> tr) {
		return new tt.a<>(tr.a(integer), tr.a(mg));
	}

	public boolean d() {
		return this.f;
	}

	public void e() {
		this.g = false;
		this.e.readLock().lock();

		for (tt.a<?> a3 : this.d.values()) {
			a3.a(false);
		}

		this.e.readLock().unlock();
	}

	public static class a<T> {
		private final tq<T> a;
		private T b;
		private boolean c;

		public a(tq<T> tq, T object) {
			this.a = tq;
			this.b = object;
			this.c = true;
		}

		public tq<T> a() {
			return this.a;
		}

		public void a(T object) {
			this.b = object;
		}

		public T b() {
			return this.b;
		}

		public boolean c() {
			return this.c;
		}

		public void a(boolean boolean1) {
			this.c = boolean1;
		}

		public tt.a<T> d() {
			return new tt.a<>(this.a, this.a.b().a(this.b));
		}
	}
}
