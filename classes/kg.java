import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2LongMap.Entry;
import java.util.Collection;
import javax.annotation.Nullable;

public class kg {
	private final kv a;
	@Nullable
	private fu b;
	private final zd c;
	private final Collection<kh> d = Lists.<kh>newArrayList();
	private final int e;
	private final Collection<kk> f = Lists.<kk>newCopyOnWriteArrayList();
	private Object2LongMap<Runnable> g = new Object2LongOpenHashMap<>();
	private long h;
	private long i;
	private boolean j = false;
	private final Stopwatch k = Stopwatch.createUnstarted();
	private boolean l = false;
	private final cap m;
	@Nullable
	private Throwable n;

	public kg(kv kv, cap cap, zd zd) {
		this.a = kv;
		this.c = zd;
		this.e = kv.c();
		this.m = kv.g().a(cap);
	}

	void a(fu fu) {
		this.b = fu;
	}

	void a() {
		this.h = this.c.Q() + 1L + this.a.f();
		this.k.start();
	}

	public void b() {
		if (!this.k()) {
			this.i = this.c.Q() - this.h;
			if (this.i >= 0L) {
				if (this.i == 0L) {
					this.v();
				}

				ObjectIterator<Entry<Runnable>> objectIterator2 = this.g.object2LongEntrySet().iterator();

				while (objectIterator2.hasNext()) {
					Entry<Runnable> entry3 = (Entry<Runnable>)objectIterator2.next();
					if (entry3.getLongValue() <= this.i) {
						try {
							((Runnable)entry3.getKey()).run();
						} catch (Exception var4) {
							this.a(var4);
						}

						objectIterator2.remove();
					}
				}

				if (this.i > (long)this.e) {
					if (this.f.isEmpty()) {
						this.a(new kn("Didn't succeed or fail within " + this.a.c() + " ticks"));
					} else {
						this.f.forEach(kk -> kk.b(this.i));
						if (this.n == null) {
							this.a(new kn("No sequences finished"));
						}
					}
				} else {
					this.f.forEach(kk -> kk.a(this.i));
				}
			}
		}
	}

	private void v() {
		if (this.j) {
			throw new IllegalStateException("Test already started");
		} else {
			this.j = true;

			try {
				this.a.a(new kf(this));
			} catch (Exception var2) {
				this.a(var2);
			}
		}
	}

	public String c() {
		return this.a.a();
	}

	public fu d() {
		return this.b;
	}

	public zd g() {
		return this.c;
	}

	public boolean h() {
		return this.l && this.n == null;
	}

	public boolean i() {
		return this.n != null;
	}

	public boolean j() {
		return this.j;
	}

	public boolean k() {
		return this.l;
	}

	private void x() {
		if (!this.l) {
			this.l = true;
			this.k.stop();
		}
	}

	public void a(Throwable throwable) {
		this.x();
		this.n = throwable;
		this.d.forEach(kh -> kh.c(this));
	}

	@Nullable
	public Throwable n() {
		return this.n;
	}

	public String toString() {
		return this.c();
	}

	public void a(kh kh) {
		this.d.add(kh);
	}

	public void a(fu fu, int integer) {
		cel cel4 = kr.a(this.s(), fu, this.t(), integer, this.c, false);
		this.a(cel4.o());
		cel4.a(this.c());
		kr.a(this.b, new fu(1, 0, -1), this.t(), this.c);
		this.d.forEach(kh -> kh.a(this));
	}

	public boolean q() {
		return this.a.d();
	}

	public boolean r() {
		return !this.a.d();
	}

	public String s() {
		return this.a.b();
	}

	public cap t() {
		return this.m;
	}

	public kv u() {
		return this.a;
	}
}
