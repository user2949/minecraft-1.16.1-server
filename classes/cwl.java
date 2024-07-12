import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;

public abstract class cwl<M extends cwl<M>> {
	private final long[] b = new long[2];
	private final chd[] c = new chd[2];
	private boolean d;
	protected final Long2ObjectOpenHashMap<chd> a;

	protected cwl(Long2ObjectOpenHashMap<chd> long2ObjectOpenHashMap) {
		this.a = long2ObjectOpenHashMap;
		this.c();
		this.d = true;
	}

	public abstract M b();

	public void a(long long1) {
		this.a.put(long1, this.a.get(long1).b());
		this.c();
	}

	public boolean b(long long1) {
		return this.a.containsKey(long1);
	}

	@Nullable
	public chd c(long long1) {
		if (this.d) {
			for (int integer4 = 0; integer4 < 2; integer4++) {
				if (long1 == this.b[integer4]) {
					return this.c[integer4];
				}
			}
		}

		chd chd4 = this.a.get(long1);
		if (chd4 == null) {
			return null;
		} else {
			if (this.d) {
				for (int integer5 = 1; integer5 > 0; integer5--) {
					this.b[integer5] = this.b[integer5 - 1];
					this.c[integer5] = this.c[integer5 - 1];
				}

				this.b[0] = long1;
				this.c[0] = chd4;
			}

			return chd4;
		}
	}

	@Nullable
	public chd d(long long1) {
		return this.a.remove(long1);
	}

	public void a(long long1, chd chd) {
		this.a.put(long1, chd);
	}

	public void c() {
		for (int integer2 = 0; integer2 < 2; integer2++) {
			this.b[integer2] = Long.MAX_VALUE;
			this.c[integer2] = null;
		}
	}

	public void d() {
		this.d = false;
	}
}
