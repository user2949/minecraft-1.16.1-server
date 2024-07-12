import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

public class ctx extends czq {
	private LongSet a = new LongOpenHashSet();
	private LongSet b = new LongOpenHashSet();

	public ctx(String string) {
		super(string);
	}

	@Override
	public void a(le le) {
		this.a = new LongOpenHashSet(le.o("All"));
		this.b = new LongOpenHashSet(le.o("Remaining"));
	}

	@Override
	public le b(le le) {
		le.a("All", this.a.toLongArray());
		le.a("Remaining", this.b.toLongArray());
		return le;
	}

	public void a(long long1) {
		this.a.add(long1);
		this.b.add(long1);
	}

	public boolean b(long long1) {
		return this.a.contains(long1);
	}

	public boolean c(long long1) {
		return this.b.contains(long1);
	}

	public void d(long long1) {
		this.b.remove(long1);
	}

	public LongSet a() {
		return this.a;
	}
}
