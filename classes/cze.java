import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public abstract class cze {
	protected bql a;
	protected aoz b;
	protected final Int2ObjectMap<czd> c = new Int2ObjectOpenHashMap<>();
	protected int d;
	protected int e;
	protected int f;
	protected boolean g;
	protected boolean h;
	protected boolean i;

	public void a(bql bql, aoz aoz) {
		this.a = bql;
		this.b = aoz;
		this.c.clear();
		this.d = aec.d(aoz.cx() + 1.0F);
		this.e = aec.d(aoz.cy() + 1.0F);
		this.f = aec.d(aoz.cx() + 1.0F);
	}

	public void a() {
		this.a = null;
		this.b = null;
	}

	protected czd a(fu fu) {
		return this.a(fu.u(), fu.v(), fu.w());
	}

	protected czd a(int integer1, int integer2, int integer3) {
		return this.c.computeIfAbsent(czd.b(integer1, integer2, integer3), integer4 -> new czd(integer1, integer2, integer3));
	}

	public abstract czd b();

	public abstract czj a(double double1, double double2, double double3);

	public abstract int a(czd[] arr, czd czd);

	public abstract czb a(
		bpg bpg, int integer2, int integer3, int integer4, aoz aoz, int integer6, int integer7, int integer8, boolean boolean9, boolean boolean10
	);

	public abstract czb a(bpg bpg, int integer2, int integer3, int integer4);

	public void a(boolean boolean1) {
		this.g = boolean1;
	}

	public void b(boolean boolean1) {
		this.h = boolean1;
	}

	public void c(boolean boolean1) {
		this.i = boolean1;
	}

	public boolean c() {
		return this.g;
	}

	public boolean d() {
		return this.h;
	}

	public boolean e() {
		return this.i;
	}
}
