import com.google.common.collect.AbstractIterator;
import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.concurrent.Immutable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Immutable
public class fu extends gr {
	public static final Codec<fu> a = Codec.INT_STREAM
		.<fu>comapFlatMap(intStream -> v.a(intStream, 3).map(arr -> new fu(arr[0], arr[1], arr[2])), fu -> IntStream.of(new int[]{fu.u(), fu.v(), fu.w()}))
		.stable();
	private static final Logger e = LogManager.getLogger();
	public static final fu b = new fu(0, 0, 0);
	private static final int f = 1 + aec.f(aec.c(30000000));
	private static final int g = f;
	private static final int h = 64 - f - g;
	private static final long i = (1L << f) - 1L;
	private static final long j = (1L << h) - 1L;
	private static final long k = (1L << g) - 1L;
	private static final int l = h;
	private static final int m = h + g;

	public fu(int integer1, int integer2, int integer3) {
		super(integer1, integer2, integer3);
	}

	public fu(double double1, double double2, double double3) {
		super(double1, double2, double3);
	}

	public fu(dem dem) {
		this(dem.b, dem.c, dem.d);
	}

	public fu(gj gj) {
		this(gj.a(), gj.b(), gj.c());
	}

	public fu(gr gr) {
		this(gr.u(), gr.v(), gr.w());
	}

	public static long a(long long1, fz fz) {
		return a(long1, fz.i(), fz.j(), fz.k());
	}

	public static long a(long long1, int integer2, int integer3, int integer4) {
		return a(b(long1) + integer2, c(long1) + integer3, d(long1) + integer4);
	}

	public static int b(long long1) {
		return (int)(long1 << 64 - m - f >> 64 - f);
	}

	public static int c(long long1) {
		return (int)(long1 << 64 - h >> 64 - h);
	}

	public static int d(long long1) {
		return (int)(long1 << 64 - l - g >> 64 - g);
	}

	public static fu e(long long1) {
		return new fu(b(long1), c(long1), d(long1));
	}

	public long a() {
		return a(this.u(), this.v(), this.w());
	}

	public static long a(int integer1, int integer2, int integer3) {
		long long4 = 0L;
		long4 |= ((long)integer1 & i) << m;
		long4 |= ((long)integer2 & j) << 0;
		return long4 | ((long)integer3 & k) << l;
	}

	public static long f(long long1) {
		return long1 & -16L;
	}

	public fu a(double double1, double double2, double double3) {
		return double1 == 0.0 && double2 == 0.0 && double3 == 0.0 ? this : new fu((double)this.u() + double1, (double)this.v() + double2, (double)this.w() + double3);
	}

	public fu b(int integer1, int integer2, int integer3) {
		return integer1 == 0 && integer2 == 0 && integer3 == 0 ? this : new fu(this.u() + integer1, this.v() + integer2, this.w() + integer3);
	}

	public fu a(gr gr) {
		return this.b(gr.u(), gr.v(), gr.w());
	}

	public fu b(gr gr) {
		return this.b(-gr.u(), -gr.v(), -gr.w());
	}

	public fu b() {
		return this.a(fz.UP);
	}

	public fu b(int integer) {
		return this.a(fz.UP, integer);
	}

	public fu n() {
		return this.a(fz.DOWN);
	}

	public fu l(int integer) {
		return this.a(fz.DOWN, integer);
	}

	public fu d() {
		return this.a(fz.NORTH);
	}

	public fu d(int integer) {
		return this.a(fz.NORTH, integer);
	}

	public fu e() {
		return this.a(fz.SOUTH);
	}

	public fu e(int integer) {
		return this.a(fz.SOUTH, integer);
	}

	public fu f() {
		return this.a(fz.WEST);
	}

	public fu f(int integer) {
		return this.a(fz.WEST, integer);
	}

	public fu g() {
		return this.a(fz.EAST);
	}

	public fu g(int integer) {
		return this.a(fz.EAST, integer);
	}

	public fu a(fz fz) {
		return new fu(this.u() + fz.i(), this.v() + fz.j(), this.w() + fz.k());
	}

	public fu b(fz fz, int integer) {
		return integer == 0 ? this : new fu(this.u() + fz.i() * integer, this.v() + fz.j() * integer, this.w() + fz.k() * integer);
	}

	public fu a(cap cap) {
		switch (cap) {
			case NONE:
			default:
				return this;
			case CLOCKWISE_90:
				return new fu(-this.w(), this.v(), this.u());
			case CLOCKWISE_180:
				return new fu(-this.u(), this.v(), -this.w());
			case COUNTERCLOCKWISE_90:
				return new fu(this.w(), this.v(), -this.u());
		}
	}

	public fu d(gr gr) {
		return new fu(this.v() * gr.w() - this.w() * gr.v(), this.w() * gr.u() - this.u() * gr.w(), this.u() * gr.v() - this.v() * gr.u());
	}

	public fu h() {
		return this;
	}

	public fu.a i() {
		return new fu.a(this.u(), this.v(), this.w());
	}

	public static Iterable<fu> a(Random random, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8) {
		int integer9 = integer6 - integer3 + 1;
		int integer10 = integer7 - integer4 + 1;
		int integer11 = integer8 - integer5 + 1;
		return () -> new AbstractIterator<fu>() {
				final fu.a a = new fu.a();
				int b = integer2;

				protected fu computeNext() {
					if (this.b <= 0) {
						return this.endOfData();
					} else {
						fu fu2 = this.a.d(integer3 + random.nextInt(integer9), integer4 + random.nextInt(integer10), integer5 + random.nextInt(integer11));
						this.b--;
						return fu2;
					}
				}
			};
	}

	public static Iterable<fu> a(fu fu, int integer2, int integer3, int integer4) {
		int integer5 = integer2 + integer3 + integer4;
		int integer6 = fu.u();
		int integer7 = fu.v();
		int integer8 = fu.w();
		return () -> new AbstractIterator<fu>() {
				private final fu.a h = new fu.a();
				private int i;
				private int j;
				private int k;
				private int l;
				private int m;
				private boolean n;

				protected fu computeNext() {
					if (this.n) {
						this.n = false;
						this.h.q(integer8 - (this.h.w() - integer8));
						return this.h;
					} else {
						fu fu2;
						for (fu2 = null; fu2 == null; this.m++) {
							if (this.m > this.k) {
								this.l++;
								if (this.l > this.j) {
									this.i++;
									if (this.i > integer5) {
										return this.endOfData();
									}

									this.j = Math.min(integer2, this.i);
									this.l = -this.j;
								}

								this.k = Math.min(integer3, this.i - Math.abs(this.l));
								this.m = -this.k;
							}

							int integer3 = this.l;
							int integer4 = this.m;
							int integer5 = this.i - Math.abs(integer3) - Math.abs(integer4);
							if (integer5 <= integer4) {
								this.n = integer5 != 0;
								fu2 = this.h.d(integer6 + integer3, integer7 + integer4, integer8 + integer5);
							}
						}

						return fu2;
					}
				}
			};
	}

	public static Optional<fu> a(fu fu, int integer2, int integer3, Predicate<fu> predicate) {
		return b(fu, integer2, integer3, integer2).filter(predicate).findFirst();
	}

	public static Stream<fu> b(fu fu, int integer2, int integer3, int integer4) {
		return StreamSupport.stream(a(fu, integer2, integer3, integer4).spliterator(), false);
	}

	public static Iterable<fu> a(fu fu1, fu fu2) {
		return b(
			Math.min(fu1.u(), fu2.u()),
			Math.min(fu1.v(), fu2.v()),
			Math.min(fu1.w(), fu2.w()),
			Math.max(fu1.u(), fu2.u()),
			Math.max(fu1.v(), fu2.v()),
			Math.max(fu1.w(), fu2.w())
		);
	}

	public static Stream<fu> b(fu fu1, fu fu2) {
		return StreamSupport.stream(a(fu1, fu2).spliterator(), false);
	}

	public static Stream<fu> a(ctd ctd) {
		return a(Math.min(ctd.a, ctd.d), Math.min(ctd.b, ctd.e), Math.min(ctd.c, ctd.f), Math.max(ctd.a, ctd.d), Math.max(ctd.b, ctd.e), Math.max(ctd.c, ctd.f));
	}

	public static Stream<fu> a(deg deg) {
		return a(aec.c(deg.a), aec.c(deg.b), aec.c(deg.c), aec.c(deg.d), aec.c(deg.e), aec.c(deg.f));
	}

	public static Stream<fu> a(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
		return StreamSupport.stream(b(integer1, integer2, integer3, integer4, integer5, integer6).spliterator(), false);
	}

	public static Iterable<fu> b(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
		int integer7 = integer4 - integer1 + 1;
		int integer8 = integer5 - integer2 + 1;
		int integer9 = integer6 - integer3 + 1;
		int integer10 = integer7 * integer8 * integer9;
		return () -> new AbstractIterator<fu>() {
				private final fu.a g = new fu.a();
				private int h;

				protected fu computeNext() {
					if (this.h == integer10) {
						return this.endOfData();
					} else {
						int integer2 = this.h % integer7;
						int integer3 = this.h / integer7;
						int integer4 = integer3 % integer8;
						int integer5 = integer3 / integer8;
						this.h++;
						return this.g.d(integer1 + integer2, integer2 + integer4, integer3 + integer5);
					}
				}
			};
	}

	public static class a extends fu {
		public a() {
			this(0, 0, 0);
		}

		public a(int integer1, int integer2, int integer3) {
			super(integer1, integer2, integer3);
		}

		public a(double double1, double double2, double double3) {
			this(aec.c(double1), aec.c(double2), aec.c(double3));
		}

		@Override
		public fu a(double double1, double double2, double double3) {
			return super.a(double1, double2, double3).h();
		}

		@Override
		public fu b(int integer1, int integer2, int integer3) {
			return super.b(integer1, integer2, integer3).h();
		}

		@Override
		public fu b(fz fz, int integer) {
			return super.a(fz, integer).h();
		}

		@Override
		public fu a(cap cap) {
			return super.a(cap).h();
		}

		public fu.a d(int integer1, int integer2, int integer3) {
			this.o(integer1);
			this.p(integer2);
			this.q(integer3);
			return this;
		}

		public fu.a c(double double1, double double2, double double3) {
			return this.d(aec.c(double1), aec.c(double2), aec.c(double3));
		}

		public fu.a g(gr gr) {
			return this.d(gr.u(), gr.v(), gr.w());
		}

		public fu.a g(long long1) {
			return this.d(b(long1), c(long1), d(long1));
		}

		public fu.a a(fs fs, int integer2, int integer3, int integer4) {
			return this.d(fs.a(integer2, integer3, integer4, fz.a.X), fs.a(integer2, integer3, integer4, fz.a.Y), fs.a(integer2, integer3, integer4, fz.a.Z));
		}

		public fu.a a(gr gr, fz fz) {
			return this.d(gr.u() + fz.i(), gr.v() + fz.j(), gr.w() + fz.k());
		}

		public fu.a a(gr gr, int integer2, int integer3, int integer4) {
			return this.d(gr.u() + integer2, gr.v() + integer3, gr.w() + integer4);
		}

		public fu.a c(fz fz) {
			return this.c(fz, 1);
		}

		public fu.a c(fz fz, int integer) {
			return this.d(this.u() + fz.i() * integer, this.v() + fz.j() * integer, this.w() + fz.k() * integer);
		}

		public fu.a e(int integer1, int integer2, int integer3) {
			return this.d(this.u() + integer1, this.v() + integer2, this.w() + integer3);
		}

		public fu.a a(fz.a a, int integer2, int integer3) {
			switch (a) {
				case X:
					return this.d(aec.a(this.u(), integer2, integer3), this.v(), this.w());
				case Y:
					return this.d(this.u(), aec.a(this.v(), integer2, integer3), this.w());
				case Z:
					return this.d(this.u(), this.v(), aec.a(this.w(), integer2, integer3));
				default:
					throw new IllegalStateException("Unable to clamp axis " + a);
			}
		}

		@Override
		public void o(int integer) {
			super.o(integer);
		}

		@Override
		public void p(int integer) {
			super.p(integer);
		}

		@Override
		public void q(int integer) {
			super.q(integer);
		}

		@Override
		public fu h() {
			return new fu(this);
		}
	}
}
