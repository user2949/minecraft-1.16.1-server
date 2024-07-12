import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class bnw {
	private final aor[] a;
	private final bnw.a d;
	public final bnx b;
	@Nullable
	protected String c;

	protected bnw(bnw.a a, bnx bnx, aor[] arr) {
		this.d = a;
		this.b = bnx;
		this.a = arr;
	}

	public Map<aor, bki> a(aoy aoy) {
		Map<aor, bki> map3 = Maps.newEnumMap(aor.class);

		for (aor aor7 : this.a) {
			bki bki8 = aoy.b(aor7);
			if (!bki8.a()) {
				map3.put(aor7, bki8);
			}
		}

		return map3;
	}

	public bnw.a d() {
		return this.d;
	}

	public int e() {
		return 1;
	}

	public int a() {
		return 1;
	}

	public int a(int integer) {
		return 1 + integer * 10;
	}

	public int b(int integer) {
		return this.a(integer) + 5;
	}

	public int a(int integer, anw anw) {
		return 0;
	}

	public float a(int integer, apc apc) {
		return 0.0F;
	}

	public final boolean b(bnw bnw) {
		return this.a(bnw) && bnw.a(this);
	}

	protected boolean a(bnw bnw) {
		return this != bnw;
	}

	protected String f() {
		if (this.c == null) {
			this.c = v.a("enchantment", gl.ak.b(this));
		}

		return this.c;
	}

	public String g() {
		return this.f();
	}

	public mr d(int integer) {
		mx mx3 = new ne(this.g());
		if (this.c()) {
			mx3.a(i.RED);
		} else {
			mx3.a(i.GRAY);
		}

		if (integer != 1 || this.a() != 1) {
			mx3.c(" ").a(new ne("enchantment.level." + integer));
		}

		return mx3;
	}

	public boolean a(bki bki) {
		return this.b.a(bki.b());
	}

	public void a(aoy aoy, aom aom, int integer) {
	}

	public void b(aoy aoy, aom aom, int integer) {
	}

	public boolean b() {
		return false;
	}

	public boolean c() {
		return false;
	}

	public boolean h() {
		return true;
	}

	public boolean i() {
		return true;
	}

	public static enum a {
		COMMON(10),
		UNCOMMON(5),
		RARE(2),
		VERY_RARE(1);

		private final int e;

		private a(int integer3) {
			this.e = integer3;
		}

		public int a() {
			return this.e;
		}
	}
}
