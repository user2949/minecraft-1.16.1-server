import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.IOException;

public class ns implements ni<nl> {
	private Object2IntMap<acr<?>> a;

	public ns() {
	}

	public ns(Object2IntMap<acr<?>> object2IntMap) {
		this.a = object2IntMap;
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		int integer3 = mg.i();
		this.a = new Object2IntOpenHashMap<>(integer3);

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			this.a(gl.aQ.a(mg.i()), mg);
		}
	}

	private <T> void a(act<T> act, mg mg) {
		int integer4 = mg.i();
		int integer5 = mg.i();
		this.a.put(act.b(act.a().a(integer4)), integer5);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a.size());

		for (Entry<acr<?>> entry4 : this.a.object2IntEntrySet()) {
			acr<?> acr5 = (acr<?>)entry4.getKey();
			mg.d(gl.aQ.a(acr5.a()));
			mg.d(this.a(acr5));
			mg.d(entry4.getIntValue());
		}
	}

	private <T> int a(acr<T> acr) {
		return acr.a().a().a(acr.b());
	}
}
