import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

public class bpw extends czq {
	private LongSet a = new LongOpenHashSet();

	public bpw() {
		super("chunks");
	}

	@Override
	public void a(le le) {
		this.a = new LongOpenHashSet(le.o("Forced"));
	}

	@Override
	public le b(le le) {
		le.a("Forced", this.a.toLongArray());
		return le;
	}

	public LongSet a() {
		return this.a;
	}
}
