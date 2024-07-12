import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class amb implements amg {
	private static final long a = Duration.ofMillis(100L).toNanos();
	private static final Logger b = LogManager.getLogger();
	private final List<String> c = Lists.<String>newArrayList();
	private final LongList d = new LongArrayList();
	private final Map<String, amb.a> e = Maps.<String, amb.a>newHashMap();
	private final IntSupplier f;
	private final LongSupplier g;
	private final long h;
	private final int i;
	private String j = "";
	private boolean k;
	@Nullable
	private amb.a l;
	private final boolean m;

	public amb(LongSupplier longSupplier, IntSupplier intSupplier, boolean boolean3) {
		this.h = longSupplier.getAsLong();
		this.g = longSupplier;
		this.i = intSupplier.getAsInt();
		this.f = intSupplier;
		this.m = boolean3;
	}

	@Override
	public void a() {
		if (this.k) {
			b.error("Profiler tick already started - missing endTick()?");
		} else {
			this.k = true;
			this.j = "";
			this.c.clear();
			this.a("root");
		}
	}

	@Override
	public void b() {
		if (!this.k) {
			b.error("Profiler tick already ended - missing startTick()?");
		} else {
			this.c();
			this.k = false;
			if (!this.j.isEmpty()) {
				b.error("Profiler tick ended before path was fully popped (remainder: '{}'). Mismatched push/pop?", () -> amh.b(this.j));
			}
		}
	}

	@Override
	public void a(String string) {
		if (!this.k) {
			b.error("Cannot push '{}' to profiler if profiler tick hasn't started - missing startTick()?", string);
		} else {
			if (!this.j.isEmpty()) {
				this.j = this.j + '\u001e';
			}

			this.j = this.j + string;
			this.c.add(this.j);
			this.d.add(v.c());
			this.l = null;
		}
	}

	@Override
	public void a(Supplier<String> supplier) {
		this.a((String)supplier.get());
	}

	@Override
	public void c() {
		if (!this.k) {
			b.error("Cannot pop from profiler if profiler tick hasn't started - missing startTick()?");
		} else if (this.d.isEmpty()) {
			b.error("Tried to pop one too many times! Mismatched push() and pop()?");
		} else {
			long long2 = v.c();
			long long4 = this.d.removeLong(this.d.size() - 1);
			this.c.remove(this.c.size() - 1);
			long long6 = long2 - long4;
			amb.a a8 = this.e();
			a8.a = a8.a + long6;
			a8.b = a8.b + 1L;
			if (this.m && long6 > a) {
				b.warn("Something's taking too long! '{}' took aprox {} ms", () -> amh.b(this.j), () -> (double)long6 / 1000000.0);
			}

			this.j = this.c.isEmpty() ? "" : (String)this.c.get(this.c.size() - 1);
			this.l = null;
		}
	}

	@Override
	public void b(String string) {
		this.c();
		this.a(string);
	}

	private amb.a e() {
		if (this.l == null) {
			this.l = (amb.a)this.e.computeIfAbsent(this.j, string -> new amb.a());
		}

		return this.l;
	}

	@Override
	public void c(String string) {
		this.e().c.addTo(string, 1L);
	}

	@Override
	public void c(Supplier<String> supplier) {
		this.e().c.addTo((String)supplier.get(), 1L);
	}

	@Override
	public amh d() {
		return new ame(this.e, this.h, this.i, this.g.getAsLong(), this.f.getAsInt());
	}

	static class a implements amj {
		private long a;
		private long b;
		private Object2LongOpenHashMap<String> c = new Object2LongOpenHashMap<>();

		private a() {
		}

		@Override
		public long a() {
			return this.a;
		}

		@Override
		public long b() {
			return this.b;
		}

		@Override
		public Object2LongMap<String> c() {
			return Object2LongMaps.unmodifiable(this.c);
		}
	}
}
