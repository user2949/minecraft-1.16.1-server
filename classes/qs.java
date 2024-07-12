import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class qs implements ni<nl> {
	private boolean a;
	private Map<uh, w.a> b;
	private Set<uh> c;
	private Map<uh, y> d;

	public qs() {
	}

	public qs(boolean boolean1, Collection<w> collection, Set<uh> set, Map<uh, y> map) {
		this.a = boolean1;
		this.b = Maps.<uh, w.a>newHashMap();

		for (w w7 : collection) {
			this.b.put(w7.h(), w7.a());
		}

		this.c = set;
		this.d = Maps.<uh, y>newHashMap(map);
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readBoolean();
		this.b = Maps.<uh, w.a>newHashMap();
		this.c = Sets.<uh>newLinkedHashSet();
		this.d = Maps.<uh, y>newHashMap();
		int integer3 = mg.i();

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			uh uh5 = mg.o();
			w.a a6 = w.a.b(mg);
			this.b.put(uh5, a6);
		}

		integer3 = mg.i();

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			uh uh5 = mg.o();
			this.c.add(uh5);
		}

		integer3 = mg.i();

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			uh uh5 = mg.o();
			this.d.put(uh5, y.b(mg));
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeBoolean(this.a);
		mg.d(this.b.size());

		for (Entry<uh, w.a> entry4 : this.b.entrySet()) {
			uh uh5 = (uh)entry4.getKey();
			w.a a6 = (w.a)entry4.getValue();
			mg.a(uh5);
			a6.a(mg);
		}

		mg.d(this.c.size());

		for (uh uh4 : this.c) {
			mg.a(uh4);
		}

		mg.d(this.d.size());

		for (Entry<uh, y> entry4 : this.d.entrySet()) {
			mg.a((uh)entry4.getKey());
			((y)entry4.getValue()).a(mg);
		}
	}
}
