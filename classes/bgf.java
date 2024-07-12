import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;

public class bgf {
	private final int a;
	private final float b;
	private final boolean c;
	private final boolean d;
	private final boolean e;
	private final List<Pair<aog, Float>> f;

	private bgf(int integer, float float2, boolean boolean3, boolean boolean4, boolean boolean5, List<Pair<aog, Float>> list) {
		this.a = integer;
		this.b = float2;
		this.c = boolean3;
		this.d = boolean4;
		this.e = boolean5;
		this.f = list;
	}

	public int a() {
		return this.a;
	}

	public float b() {
		return this.b;
	}

	public boolean c() {
		return this.c;
	}

	public boolean d() {
		return this.d;
	}

	public boolean e() {
		return this.e;
	}

	public List<Pair<aog, Float>> f() {
		return this.f;
	}

	public static class a {
		private int a;
		private float b;
		private boolean c;
		private boolean d;
		private boolean e;
		private final List<Pair<aog, Float>> f = Lists.<Pair<aog, Float>>newArrayList();

		public bgf.a a(int integer) {
			this.a = integer;
			return this;
		}

		public bgf.a a(float float1) {
			this.b = float1;
			return this;
		}

		public bgf.a a() {
			this.c = true;
			return this;
		}

		public bgf.a b() {
			this.d = true;
			return this;
		}

		public bgf.a c() {
			this.e = true;
			return this;
		}

		public bgf.a a(aog aog, float float2) {
			this.f.add(Pair.of(aog, float2));
			return this;
		}

		public bgf d() {
			return new bgf(this.a, this.b, this.c, this.d, this.e, this.f);
		}
	}
}
