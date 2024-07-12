import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class kc {
	private static final Logger a = LogManager.getLogger();
	private final fu b;
	private final zd c;
	private final km d;
	private final int e;
	private final List<kg> f = Lists.<kg>newArrayList();
	private final Map<kg, fu> g = Maps.<kg, fu>newHashMap();
	private final List<Pair<kb, Collection<kg>>> h = Lists.<Pair<kb, Collection<kg>>>newArrayList();
	private kq i;
	private int j = 0;
	private fu.a k;

	public kc(Collection<kb> collection, fu fu, cap cap, zd zd, km km, int integer) {
		this.k = fu.i();
		this.b = fu;
		this.c = zd;
		this.d = km;
		this.e = integer;
		collection.forEach(kb -> {
			Collection<kg> collection5 = Lists.<kg>newArrayList();

			for (kv kv8 : kb.b()) {
				kg kg9 = new kg(kv8, cap, zd);
				collection5.add(kg9);
				this.f.add(kg9);
			}

			this.h.add(Pair.of(kb, collection5));
		});
	}

	public List<kg> a() {
		return this.f;
	}

	public void b() {
		this.a(0);
	}

	private void a(int integer) {
		this.j = integer;
		this.i = new kq();
		if (integer < this.h.size()) {
			Pair<kb, Collection<kg>> pair3 = (Pair<kb, Collection<kg>>)this.h.get(this.j);
			kb kb4 = pair3.getFirst();
			Collection<kg> collection5 = pair3.getSecond();
			this.a(collection5);
			kb4.a(this.c);
			String string6 = kb4.a();
			a.info("Running test batch '" + string6 + "' (" + collection5.size() + " tests)...");
			collection5.forEach(kg -> {
				this.i.a(kg);
				this.i.a(new kh() {
					@Override
					public void a(kg kg) {
					}

					@Override
					public void c(kg kg) {
						kc.this.a(kg);
					}
				});
				fu fu3 = (fu)this.g.get(kg);
				kj.a(kg, fu3, this.d);
			});
		}
	}

	private void a(kg kg) {
		if (this.i.i()) {
			this.a(this.j + 1);
		}
	}

	private void a(Collection<kg> collection) {
		int integer3 = 0;
		deg deg4 = new deg(this.k);

		for (kg kg6 : collection) {
			fu fu7 = new fu(this.k);
			cel cel8 = kr.a(kg6.s(), fu7, kg6.t(), 2, this.c, true);
			deg deg9 = kr.a(cel8);
			kg6.a(cel8.o());
			this.g.put(kg6, new fu(this.k));
			deg4 = deg4.b(deg9);
			this.k.e((int)deg9.b() + 5, 0, 0);
			if (integer3++ % this.e == this.e - 1) {
				this.k.e(0, 0, (int)deg4.d() + 6);
				this.k.o(this.b.u());
				deg4 = new deg(this.k);
			}
		}
	}
}
