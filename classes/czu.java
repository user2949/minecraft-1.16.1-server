import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;

public class czu extends czq {
	private final Object2IntMap<String> a = new Object2IntOpenHashMap<>();

	public czu() {
		super("idcounts");
		this.a.defaultReturnValue(-1);
	}

	@Override
	public void a(le le) {
		this.a.clear();

		for (String string4 : le.d()) {
			if (le.c(string4, 99)) {
				this.a.put(string4, le.h(string4));
			}
		}
	}

	@Override
	public le b(le le) {
		for (Entry<String> entry4 : this.a.object2IntEntrySet()) {
			le.b((String)entry4.getKey(), entry4.getIntValue());
		}

		return le;
	}

	public int a() {
		int integer2 = this.a.getInt("map") + 1;
		this.a.put("map", integer2);
		this.b();
		return integer2;
	}
}
