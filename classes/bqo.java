import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bqo<T> implements bqr<T> {
	protected final Predicate<T> a;
	private final Function<T, uh> b;
	private final Set<bqs<T>> c = Sets.<bqs<T>>newHashSet();
	private final TreeSet<bqs<T>> d = Sets.newTreeSet(bqs.a());
	private final zd e;
	private final Queue<bqs<T>> f = Queues.<bqs<T>>newArrayDeque();
	private final List<bqs<T>> g = Lists.<bqs<T>>newArrayList();
	private final Consumer<bqs<T>> h;

	public bqo(zd zd, Predicate<T> predicate, Function<T, uh> function, Consumer<bqs<T>> consumer) {
		this.a = predicate;
		this.b = function;
		this.e = zd;
		this.h = consumer;
	}

	public void b() {
		int integer2 = this.d.size();
		if (integer2 != this.c.size()) {
			throw new IllegalStateException("TickNextTick list out of synch");
		} else {
			if (integer2 > 65536) {
				integer2 = 65536;
			}

			zb zb3 = this.e.i();
			Iterator<bqs<T>> iterator4 = this.d.iterator();
			this.e.X().a("cleaning");

			while (integer2 > 0 && iterator4.hasNext()) {
				bqs<T> bqs5 = (bqs<T>)iterator4.next();
				if (bqs5.b > this.e.Q()) {
					break;
				}

				if (zb3.a(bqs5.a)) {
					iterator4.remove();
					this.c.remove(bqs5);
					this.f.add(bqs5);
					integer2--;
				}
			}

			this.e.X().b("ticking");

			bqs<T> bqs5x;
			while ((bqs5x = (bqs<T>)this.f.poll()) != null) {
				if (zb3.a(bqs5x.a)) {
					try {
						this.g.add(bqs5x);
						this.h.accept(bqs5x);
					} catch (Throwable var8) {
						j j7 = j.a(var8, "Exception while ticking");
						k k8 = j7.a("Block being ticked");
						k.a(k8, bqs5x.a, null);
						throw new s(j7);
					}
				} else {
					this.a(bqs5x.a, bqs5x.b(), 0);
				}
			}

			this.e.X().c();
			this.g.clear();
			this.f.clear();
		}
	}

	@Override
	public boolean b(fu fu, T object) {
		return this.f.contains(new bqs(fu, object));
	}

	public List<bqs<T>> a(bph bph, boolean boolean2, boolean boolean3) {
		int integer5 = (bph.b << 4) - 2;
		int integer6 = integer5 + 16 + 2;
		int integer7 = (bph.c << 4) - 2;
		int integer8 = integer7 + 16 + 2;
		return this.a(new ctd(integer5, 0, integer7, integer6, 256, integer8), boolean2, boolean3);
	}

	public List<bqs<T>> a(ctd ctd, boolean boolean2, boolean boolean3) {
		List<bqs<T>> list5 = this.a(null, this.d, ctd, boolean2);
		if (boolean2 && list5 != null) {
			this.c.removeAll(list5);
		}

		list5 = this.a(list5, this.f, ctd, boolean2);
		if (!boolean3) {
			list5 = this.a(list5, this.g, ctd, boolean2);
		}

		return list5 == null ? Collections.emptyList() : list5;
	}

	@Nullable
	private List<bqs<T>> a(@Nullable List<bqs<T>> list, Collection<bqs<T>> collection, ctd ctd, boolean boolean4) {
		Iterator<bqs<T>> iterator6 = collection.iterator();

		while (iterator6.hasNext()) {
			bqs<T> bqs7 = (bqs<T>)iterator6.next();
			fu fu8 = bqs7.a;
			if (fu8.u() >= ctd.a && fu8.u() < ctd.d && fu8.w() >= ctd.c && fu8.w() < ctd.f) {
				if (boolean4) {
					iterator6.remove();
				}

				if (list == null) {
					list = Lists.<bqs<T>>newArrayList();
				}

				list.add(bqs7);
			}
		}

		return list;
	}

	public void a(ctd ctd, fu fu) {
		for (bqs<T> bqs6 : this.a(ctd, false, false)) {
			if (ctd.b(bqs6.a)) {
				fu fu7 = bqs6.a.a(fu);
				T object8 = bqs6.b();
				this.a(new bqs<>(fu7, object8, bqs6.b, bqs6.c));
			}
		}
	}

	public lk a(bph bph) {
		List<bqs<T>> list3 = this.a(bph, false, true);
		return a(this.b, list3, this.e.Q());
	}

	private static <T> lk a(Function<T, uh> function, Iterable<bqs<T>> iterable, long long3) {
		lk lk5 = new lk();

		for (bqs<T> bqs7 : iterable) {
			le le8 = new le();
			le8.a("i", ((uh)function.apply(bqs7.b())).toString());
			le8.b("x", bqs7.a.u());
			le8.b("y", bqs7.a.v());
			le8.b("z", bqs7.a.w());
			le8.b("t", (int)(bqs7.b - long3));
			le8.b("p", bqs7.c.a());
			lk5.add(le8);
		}

		return lk5;
	}

	@Override
	public boolean a(fu fu, T object) {
		return this.c.contains(new bqs(fu, object));
	}

	@Override
	public void a(fu fu, T object, int integer, bqt bqt) {
		if (!this.a.test(object)) {
			this.a(new bqs<>(fu, object, (long)integer + this.e.Q(), bqt));
		}
	}

	private void a(bqs<T> bqs) {
		if (!this.c.contains(bqs)) {
			this.c.add(bqs);
			this.d.add(bqs);
		}
	}

	public int a() {
		return this.c.size();
	}
}
