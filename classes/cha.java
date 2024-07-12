import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.function.Function;
import javax.annotation.Nullable;

public abstract class cha {
	public static final Codec<cha> a = gl.aB.dispatchStable(cha::a, Function.identity());
	protected final brh b;
	protected final brh c;
	private final ciw d;
	private final long e;
	private final List<bph> f = Lists.<bph>newArrayList();

	public cha(brh brh, ciw ciw) {
		this(brh, brh, ciw, 0L);
	}

	public cha(brh brh1, brh brh2, ciw ciw, long long4) {
		this.b = brh1;
		this.c = brh2;
		this.d = ciw;
		this.e = long4;
	}

	private void g() {
		if (this.f.isEmpty()) {
			cos cos2 = this.d.b();
			if (cos2 != null && cos2.c() != 0) {
				List<bre> list3 = Lists.<bre>newArrayList();

				for (bre bre5 : this.b.c()) {
					if (bre5.a(cml.k)) {
						list3.add(bre5);
					}
				}

				int integer4 = cos2.a();
				int integer5 = cos2.c();
				int integer6 = cos2.b();
				Random random7 = new Random();
				random7.setSeed(this.e);
				double double8 = random7.nextDouble() * Math.PI * 2.0;
				int integer10 = 0;
				int integer11 = 0;

				for (int integer12 = 0; integer12 < integer5; integer12++) {
					double double13 = (double)(4 * integer4 + integer4 * integer11 * 6) + (random7.nextDouble() - 0.5) * (double)integer4 * 2.5;
					int integer15 = (int)Math.round(Math.cos(double8) * double13);
					int integer16 = (int)Math.round(Math.sin(double8) * double13);
					fu fu17 = this.b.a((integer15 << 4) + 8, 0, (integer16 << 4) + 8, 112, list3, random7);
					if (fu17 != null) {
						integer15 = fu17.u() >> 4;
						integer16 = fu17.w() >> 4;
					}

					this.f.add(new bph(integer15, integer16));
					double8 += (Math.PI * 2) / (double)integer6;
					if (++integer10 == integer6) {
						integer11++;
						integer10 = 0;
						integer6 += 2 * integer6 / (integer11 + 1);
						integer6 = Math.min(integer6, integer5 - integer12);
						double8 += random7.nextDouble() * Math.PI * 2.0;
					}
				}
			}
		}
	}

	protected abstract Codec<? extends cha> a();

	public void a(cgy cgy) {
		bph bph3 = cgy.g();
		((chr)cgy).a(new cgz(bph3, this.c));
	}

	public void a(long long1, brg brg, cgy cgy, cin.a a) {
		brg brg7 = brg.a(this.b);
		ciy ciy8 = new ciy();
		int integer9 = 8;
		bph bph10 = cgy.g();
		int integer11 = bph10.b;
		int integer12 = bph10.c;
		bre bre13 = this.b.b(bph10.b << 2, 0, bph10.c << 2);
		BitSet bitSet14 = ((chr)cgy).b(a);

		for (int integer15 = integer11 - 8; integer15 <= integer11 + 8; integer15++) {
			for (int integer16 = integer12 - 8; integer16 <= integer12 + 8; integer16++) {
				List<cjc<?>> list17 = bre13.a(a);
				ListIterator<cjc<?>> listIterator18 = list17.listIterator();

				while (listIterator18.hasNext()) {
					int integer19 = listIterator18.nextIndex();
					cjc<?> cjc20 = (cjc<?>)listIterator18.next();
					ciy8.c(long1 + (long)integer19, integer15, integer16);
					if (cjc20.a(ciy8, integer15, integer16)) {
						cjc20.a(cgy, brg7::a, ciy8, this.f(), integer15, integer16, integer11, integer12, bitSet14);
					}
				}
			}
		}
	}

	@Nullable
	public fu a(zd zd, cml<?> cml, fu fu, int integer, boolean boolean5) {
		if (!this.b.a(cml)) {
			return null;
		} else if (cml == cml.k) {
			this.g();
			fu fu7 = null;
			double double8 = Double.MAX_VALUE;
			fu.a a10 = new fu.a();

			for (bph bph12 : this.f) {
				a10.d((bph12.b << 4) + 8, 32, (bph12.c << 4) + 8);
				double double13 = a10.j(fu);
				if (fu7 == null) {
					fu7 = new fu(a10);
					double8 = double13;
				} else if (double13 < double8) {
					fu7 = new fu(a10);
					double8 = double13;
				}
			}

			return fu7;
		} else {
			return cml.a(zd, zd.a(), fu, integer, boolean5, zd.B(), this.d.a(cml));
		}
	}

	public void a(zj zj, bqq bqq) {
		int integer4 = zj.a();
		int integer5 = zj.b();
		int integer6 = integer4 * 16;
		int integer7 = integer5 * 16;
		fu fu8 = new fu(integer6, 0, integer7);
		bre bre9 = this.b.b((integer4 << 2) + 2, 2, (integer5 << 2) + 2);
		ciy ciy10 = new ciy();
		long long11 = ciy10.a(zj.B(), integer6, integer7);

		for (cin.b b16 : cin.b.values()) {
			try {
				bre9.a(b16, bqq, this, zj, long11, ciy10, fu8);
			} catch (Exception var18) {
				j j18 = j.a(var18, "Biome decoration");
				j18.a("Generation").a("CenterX", integer4).a("CenterZ", integer5).a("Step", b16).a("Seed", long11).a("Biome", gl.as.b(bre9));
				throw new s(j18);
			}
		}
	}

	public abstract void a(zj zj, cgy cgy);

	public void a(zj zj) {
	}

	public ciw b() {
		return this.d;
	}

	public int c() {
		return 64;
	}

	public brh d() {
		return this.c;
	}

	public int e() {
		return 256;
	}

	public List<bre.g> a(bre bre, bqq bqq, apa apa, fu fu) {
		return bre.a(apa);
	}

	public void a(bqq bqq, cgy cgy, cva cva, long long4) {
		bph bph7 = cgy.g();
		bre bre8 = this.b.b((bph7.b << 2) + 2, 0, (bph7.c << 2) + 2);
		this.a(brf.k, bqq, cgy, cva, long4, bph7, bre8);

		for (ckc<?, ?> ckc10 : bre8.g()) {
			this.a(ckc10, bqq, cgy, cva, long4, bph7, bre8);
		}
	}

	private void a(ckc<?, ?> ckc, bqq bqq, cgy cgy, cva cva, long long5, bph bph, bre bre) {
		ctz<?> ctz10 = bqq.a(go.a(cgy.g(), 0), ckc.b, cgy);
		int integer11 = ctz10 != null ? ctz10.j() : 0;
		ctz<?> ctz12 = ckc.a(this, this.b, cva, long5, bph, bre, integer11, this.d.a(ckc.b));
		bqq.a(go.a(cgy.g(), 0), ckc.b, ctz12, cgy);
	}

	public void a(bqc bqc, bqq bqq, cgy cgy) {
		int integer5 = 8;
		int integer6 = cgy.g().b;
		int integer7 = cgy.g().c;
		int integer8 = integer6 << 4;
		int integer9 = integer7 << 4;
		go go10 = go.a(cgy.g(), 0);

		for (int integer11 = integer6 - 8; integer11 <= integer6 + 8; integer11++) {
			for (int integer12 = integer7 - 8; integer12 <= integer7 + 8; integer12++) {
				long long13 = bph.a(integer11, integer12);

				for (ctz<?> ctz16 : bqc.a(integer11, integer12).h().values()) {
					try {
						if (ctz16 != ctz.a && ctz16.c().a(integer8, integer9, integer8 + 15, integer9 + 15)) {
							bqq.a(go10, ctz16.l(), long13, cgy);
							qy.a(bqc, ctz16);
						}
					} catch (Exception var19) {
						j j18 = j.a(var19, "Generating structure reference");
						k k19 = j18.a("Structure");
						k19.a("Id", (l<String>)(() -> gl.aG.b(ctz16.l()).toString()));
						k19.a("Name", (l<String>)(() -> ctz16.l().i()));
						k19.a("Class", (l<String>)(() -> ctz16.l().getClass().getCanonicalName()));
						throw new s(j18);
					}
				}
			}
		}
	}

	public abstract void b(bqc bqc, bqq bqq, cgy cgy);

	public int f() {
		return 63;
	}

	public abstract int a(int integer1, int integer2, cio.a a);

	public abstract bpg a(int integer1, int integer2);

	public int b(int integer1, int integer2, cio.a a) {
		return this.a(integer1, integer2, a);
	}

	public int c(int integer1, int integer2, cio.a a) {
		return this.a(integer1, integer2, a) - 1;
	}

	public boolean a(bph bph) {
		this.g();
		return this.f.contains(bph);
	}

	static {
		gl.a(gl.aB, "noise", cip.d);
		gl.a(gl.aB, "flat", cim.d);
		gl.a(gl.aB, "debug", cil.e);
	}
}
