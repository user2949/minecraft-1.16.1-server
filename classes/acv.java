import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public class acv {
	protected final Object2IntMap<acr<?>> a = Object2IntMaps.synchronize(new Object2IntOpenHashMap<>());

	public acv() {
		this.a.defaultReturnValue(0);
	}

	public void b(bec bec, acr<?> acr, int integer) {
		int integer5 = (int)Math.min((long)this.a(acr) + (long)integer, 2147483647L);
		this.a(bec, acr, integer5);
	}

	public void a(bec bec, acr<?> acr, int integer) {
		this.a.put(acr, integer);
	}

	public int a(acr<?> acr) {
		return this.a.getInt(acr);
	}
}
