import com.google.common.collect.Iterators;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public enum fz implements aeh {
	DOWN(0, 1, -1, "down", fz.b.NEGATIVE, fz.a.Y, new gr(0, -1, 0)),
	UP(1, 0, -1, "up", fz.b.POSITIVE, fz.a.Y, new gr(0, 1, 0)),
	NORTH(2, 3, 2, "north", fz.b.NEGATIVE, fz.a.Z, new gr(0, 0, -1)),
	SOUTH(3, 2, 0, "south", fz.b.POSITIVE, fz.a.Z, new gr(0, 0, 1)),
	WEST(4, 5, 1, "west", fz.b.NEGATIVE, fz.a.X, new gr(-1, 0, 0)),
	EAST(5, 4, 3, "east", fz.b.POSITIVE, fz.a.X, new gr(1, 0, 0));

	private final int g;
	private final int h;
	private final int i;
	private final String j;
	private final fz.a k;
	private final fz.b l;
	private final gr m;
	private static final fz[] n = values();
	private static final Map<String, fz> o = (Map<String, fz>)Arrays.stream(n).collect(Collectors.toMap(fz::m, fz -> fz));
	private static final fz[] p = (fz[])Arrays.stream(n).sorted(Comparator.comparingInt(fz -> fz.g)).toArray(fz[]::new);
	private static final fz[] q = (fz[])Arrays.stream(n).filter(fz -> fz.n().d()).sorted(Comparator.comparingInt(fz -> fz.i)).toArray(fz[]::new);
	private static final Long2ObjectMap<fz> r = (Long2ObjectMap<fz>)Arrays.stream(n).collect(Collectors.toMap(fz -> new fu(fz.p()).a(), fz -> fz, (fz1, fz2) -> {
		throw new IllegalArgumentException("Duplicate keys");
	}, Long2ObjectOpenHashMap::new));

	private fz(int integer3, int integer4, int integer5, String string6, fz.b b, fz.a a, gr gr) {
		this.g = integer3;
		this.i = integer5;
		this.h = integer4;
		this.j = string6;
		this.k = a;
		this.l = b;
		this.m = gr;
	}

	public static fz[] a(aom aom) {
		float float2 = aom.g(1.0F) * (float) (Math.PI / 180.0);
		float float3 = -aom.h(1.0F) * (float) (Math.PI / 180.0);
		float float4 = aec.a(float2);
		float float5 = aec.b(float2);
		float float6 = aec.a(float3);
		float float7 = aec.b(float3);
		boolean boolean8 = float6 > 0.0F;
		boolean boolean9 = float4 < 0.0F;
		boolean boolean10 = float7 > 0.0F;
		float float11 = boolean8 ? float6 : -float6;
		float float12 = boolean9 ? -float4 : float4;
		float float13 = boolean10 ? float7 : -float7;
		float float14 = float11 * float5;
		float float15 = float13 * float5;
		fz fz16 = boolean8 ? EAST : WEST;
		fz fz17 = boolean9 ? UP : DOWN;
		fz fz18 = boolean10 ? SOUTH : NORTH;
		if (float11 > float13) {
			if (float12 > float14) {
				return a(fz17, fz16, fz18);
			} else {
				return float15 > float12 ? a(fz16, fz18, fz17) : a(fz16, fz17, fz18);
			}
		} else if (float12 > float15) {
			return a(fz17, fz18, fz16);
		} else {
			return float14 > float12 ? a(fz18, fz16, fz17) : a(fz18, fz17, fz16);
		}
	}

	private static fz[] a(fz fz1, fz fz2, fz fz3) {
		return new fz[]{fz1, fz2, fz3, fz3.f(), fz2.f(), fz1.f()};
	}

	public int c() {
		return this.g;
	}

	public int d() {
		return this.i;
	}

	public fz.b e() {
		return this.l;
	}

	public fz f() {
		return a(this.h);
	}

	public fz g() {
		switch (this) {
			case NORTH:
				return EAST;
			case SOUTH:
				return WEST;
			case WEST:
				return NORTH;
			case EAST:
				return SOUTH;
			default:
				throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
		}
	}

	public fz h() {
		switch (this) {
			case NORTH:
				return WEST;
			case SOUTH:
				return EAST;
			case WEST:
				return SOUTH;
			case EAST:
				return NORTH;
			default:
				throw new IllegalStateException("Unable to get CCW facing of " + this);
		}
	}

	public int i() {
		return this.m.u();
	}

	public int j() {
		return this.m.v();
	}

	public int k() {
		return this.m.w();
	}

	public String m() {
		return this.j;
	}

	public fz.a n() {
		return this.k;
	}

	public static fz a(int integer) {
		return p[aec.a(integer % p.length)];
	}

	public static fz b(int integer) {
		return q[aec.a(integer % q.length)];
	}

	@Nullable
	public static fz a(int integer1, int integer2, int integer3) {
		return r.get(fu.a(integer1, integer2, integer3));
	}

	public static fz a(double double1) {
		return b(aec.c(double1 / 90.0 + 0.5) & 3);
	}

	public static fz a(fz.a a, fz.b b) {
		switch (a) {
			case X:
				return b == fz.b.POSITIVE ? EAST : WEST;
			case Y:
				return b == fz.b.POSITIVE ? UP : DOWN;
			case Z:
			default:
				return b == fz.b.POSITIVE ? SOUTH : NORTH;
		}
	}

	public float o() {
		return (float)((this.i & 3) * 90);
	}

	public static fz a(Random random) {
		return v.a(n, random);
	}

	public static fz a(double double1, double double2, double double3) {
		return a((float)double1, (float)double2, (float)double3);
	}

	public static fz a(float float1, float float2, float float3) {
		fz fz4 = NORTH;
		float float5 = Float.MIN_VALUE;

		for (fz fz9 : n) {
			float float10 = float1 * (float)fz9.m.u() + float2 * (float)fz9.m.v() + float3 * (float)fz9.m.w();
			if (float10 > float5) {
				float5 = float10;
				fz4 = fz9;
			}
		}

		return fz4;
	}

	public String toString() {
		return this.j;
	}

	@Override
	public String a() {
		return this.j;
	}

	public static fz a(fz.b b, fz.a a) {
		for (fz fz6 : n) {
			if (fz6.e() == b && fz6.n() == a) {
				return fz6;
			}
		}

		throw new IllegalArgumentException("No such direction: " + b + " " + a);
	}

	public gr p() {
		return this.m;
	}

	public static enum a implements aeh, Predicate<fz> {
		X("x") {
			@Override
			public int a(int integer1, int integer2, int integer3) {
				return integer1;
			}

			@Override
			public double a(double double1, double double2, double double3) {
				return double1;
			}
		},
		Y("y") {
			@Override
			public int a(int integer1, int integer2, int integer3) {
				return integer2;
			}

			@Override
			public double a(double double1, double double2, double double3) {
				return double2;
			}
		},
		Z("z") {
			@Override
			public int a(int integer1, int integer2, int integer3) {
				return integer3;
			}

			@Override
			public double a(double double1, double double2, double double3) {
				return double3;
			}
		};

		private static final fz.a[] e = values();
		public static final Codec<fz.a> d = aeh.a(fz.a::values, fz.a::a);
		private static final Map<String, fz.a> f = (Map<String, fz.a>)Arrays.stream(e).collect(Collectors.toMap(fz.a::b, a -> a));
		private final String g;

		private a(String string3) {
			this.g = string3;
		}

		@Nullable
		public static fz.a a(String string) {
			return (fz.a)f.get(string.toLowerCase(Locale.ROOT));
		}

		public String b() {
			return this.g;
		}

		public boolean c() {
			return this == Y;
		}

		public boolean d() {
			return this == X || this == Z;
		}

		public String toString() {
			return this.g;
		}

		public static fz.a a(Random random) {
			return v.a(e, random);
		}

		public boolean test(@Nullable fz fz) {
			return fz != null && fz.n() == this;
		}

		public fz.c e() {
			switch (this) {
				case X:
				case Z:
					return fz.c.HORIZONTAL;
				case Y:
					return fz.c.VERTICAL;
				default:
					throw new Error("Someone's been tampering with the universe!");
			}
		}

		@Override
		public String a() {
			return this.g;
		}

		public abstract int a(int integer1, int integer2, int integer3);

		public abstract double a(double double1, double double2, double double3);
	}

	public static enum b {
		POSITIVE(1, "Towards positive"),
		NEGATIVE(-1, "Towards negative");

		private final int c;
		private final String d;

		private b(int integer3, String string4) {
			this.c = integer3;
			this.d = string4;
		}

		public int a() {
			return this.c;
		}

		public String toString() {
			return this.d;
		}

		public fz.b c() {
			return this == POSITIVE ? NEGATIVE : POSITIVE;
		}
	}

	public static enum c implements Iterable<fz>, Predicate<fz> {
		HORIZONTAL(new fz[]{fz.NORTH, fz.EAST, fz.SOUTH, fz.WEST}, new fz.a[]{fz.a.X, fz.a.Z}),
		VERTICAL(new fz[]{fz.UP, fz.DOWN}, new fz.a[]{fz.a.Y});

		private final fz[] c;
		private final fz.a[] d;

		private c(fz[] arr, fz.a[] arr) {
			this.c = arr;
			this.d = arr;
		}

		public fz a(Random random) {
			return v.a(this.c, random);
		}

		public boolean test(@Nullable fz fz) {
			return fz != null && fz.n().e() == this;
		}

		public Iterator<fz> iterator() {
			return Iterators.forArray(this.c);
		}

		public Stream<fz> a() {
			return Arrays.stream(this.c);
		}
	}
}
