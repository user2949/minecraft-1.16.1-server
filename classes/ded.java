import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.primitives.UnsignedLong;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ded<T> {
	private static final Logger a = LogManager.getLogger();
	private final dec<T> b;
	private final Queue<ded.a<T>> c = new PriorityQueue(c());
	private UnsignedLong d = UnsignedLong.ZERO;
	private final Table<String, Long, ded.a<T>> e = HashBasedTable.create();

	private static <T> Comparator<ded.a<T>> c() {
		return Comparator.comparingLong(a -> a.a).thenComparing(a -> a.b);
	}

	public ded(dec<T> dec, Stream<Dynamic<lu>> stream) {
		this(dec);
		this.c.clear();
		this.e.clear();
		this.d = UnsignedLong.ZERO;
		stream.forEach(dynamic -> {
			if (!(dynamic.getValue() instanceof le)) {
				a.warn("Invalid format of events: {}", dynamic);
			} else {
				this.a((le)dynamic.getValue());
			}
		});
	}

	public ded(dec<T> dec) {
		this.b = dec;
	}

	public void a(T object, long long2) {
		while (true) {
			ded.a<T> a5 = (ded.a<T>)this.c.peek();
			if (a5 == null || a5.a > long2) {
				return;
			}

			this.c.remove();
			this.e.remove(a5.c, long2);
			a5.d.a(object, this, long2);
		}
	}

	public void a(String string, long long2, deb<T> deb) {
		if (!this.e.contains(string, long2)) {
			this.d = this.d.plus(UnsignedLong.ONE);
			ded.a<T> a6 = new ded.a<>(long2, this.d, string, deb);
			this.e.put(string, long2, a6);
			this.c.add(a6);
		}
	}

	public int a(String string) {
		Collection<ded.a<T>> collection3 = this.e.row(string).values();
		collection3.forEach(this.c::remove);
		int integer4 = collection3.size();
		collection3.clear();
		return integer4;
	}

	public Set<String> a() {
		return Collections.unmodifiableSet(this.e.rowKeySet());
	}

	private void a(le le) {
		le le3 = le.p("Callback");
		deb<T> deb4 = this.b.a(le3);
		if (deb4 != null) {
			String string5 = le.l("Name");
			long long6 = le.i("TriggerTime");
			this.a(string5, long6, deb4);
		}
	}

	private le a(ded.a<T> a) {
		le le3 = new le();
		le3.a("Name", a.c);
		le3.a("TriggerTime", a.a);
		le3.a("Callback", this.b.a(a.d));
		return le3;
	}

	public lk b() {
		lk lk2 = new lk();
		this.c.stream().sorted(c()).map(this::a).forEach(lk2::add);
		return lk2;
	}

	public static class a<T> {
		public final long a;
		public final UnsignedLong b;
		public final String c;
		public final deb<T> d;

		private a(long long1, UnsignedLong unsignedLong, String string, deb<T> deb) {
			this.a = long1;
			this.b = unsignedLong;
			this.c = string;
			this.d = deb;
		}
	}
}
