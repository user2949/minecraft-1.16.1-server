import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zc {
	private static final Logger a = LogManager.getLogger();
	private final zd b;
	private final aom c;
	private final int d;
	private final boolean e;
	private final Consumer<ni<?>> f;
	private long g;
	private long h;
	private long i;
	private int j;
	private int k;
	private int l;
	private dem m = dem.a;
	private int n;
	private int o;
	private List<aom> p = Collections.emptyList();
	private boolean q;
	private boolean r;

	public zc(zd zd, aom aom, int integer, boolean boolean4, Consumer<ni<?>> consumer) {
		this.b = zd;
		this.f = consumer;
		this.c = aom;
		this.d = integer;
		this.e = boolean4;
		this.d();
		this.j = aec.d(aom.p * 256.0F / 360.0F);
		this.k = aec.d(aom.q * 256.0F / 360.0F);
		this.l = aec.d(aom.bG() * 256.0F / 360.0F);
		this.r = aom.aj();
	}

	public void a() {
		List<aom> list2 = this.c.cm();
		if (!list2.equals(this.p)) {
			this.p = list2;
			this.f.accept(new qg(this.c));
		}

		if (this.c instanceof bba && this.n % 10 == 0) {
			bba bba3 = (bba)this.c;
			bki bki4 = bba3.o();
			if (bki4.b() instanceof bko) {
				czv czv5 = bko.b(bki4, this.b);

				for (ze ze7 : this.b.w()) {
					czv5.a(ze7, bki4);
					ni<?> ni8 = ((bko)bki4.b()).a(bki4, this.b, ze7);
					if (ni8 != null) {
						ze7.b.a(ni8);
					}
				}
			}

			this.c();
		}

		if (this.n % this.d == 0 || this.c.ad || this.c.Y().a()) {
			if (this.c.bn()) {
				int integer3 = aec.d(this.c.p * 256.0F / 360.0F);
				int integer4 = aec.d(this.c.q * 256.0F / 360.0F);
				boolean boolean5 = Math.abs(integer3 - this.j) >= 1 || Math.abs(integer4 - this.k) >= 1;
				if (boolean5) {
					this.f.accept(new pa.c(this.c.V(), (byte)integer3, (byte)integer4, this.c.aj()));
					this.j = integer3;
					this.k = integer4;
				}

				this.d();
				this.c();
				this.q = true;
			} else {
				this.o++;
				int integer3 = aec.d(this.c.p * 256.0F / 360.0F);
				int integer4 = aec.d(this.c.q * 256.0F / 360.0F);
				dem dem5 = this.c.cz().d(pa.a(this.g, this.h, this.i));
				boolean boolean6 = dem5.g() >= 7.6293945E-6F;
				ni<?> ni7 = null;
				boolean boolean8 = boolean6 || this.n % 60 == 0;
				boolean boolean9 = Math.abs(integer3 - this.j) >= 1 || Math.abs(integer4 - this.k) >= 1;
				if (this.n > 0 || this.c instanceof beg) {
					long long10 = pa.a(dem5.b);
					long long12 = pa.a(dem5.c);
					long long14 = pa.a(dem5.d);
					boolean boolean16 = long10 < -32768L || long10 > 32767L || long12 < -32768L || long12 > 32767L || long14 < -32768L || long14 > 32767L;
					if (boolean16 || this.o > 400 || this.q || this.r != this.c.aj()) {
						this.r = this.c.aj();
						this.o = 0;
						ni7 = new qr(this.c);
					} else if ((!boolean8 || !boolean9) && !(this.c instanceof beg)) {
						if (boolean8) {
							ni7 = new pa.a(this.c.V(), (short)((int)long10), (short)((int)long12), (short)((int)long14), this.c.aj());
						} else if (boolean9) {
							ni7 = new pa.c(this.c.V(), (byte)integer3, (byte)integer4, this.c.aj());
						}
					} else {
						ni7 = new pa.b(this.c.V(), (short)((int)long10), (short)((int)long12), (short)((int)long14), (byte)integer3, (byte)integer4, this.c.aj());
					}
				}

				if ((this.e || this.c.ad || this.c instanceof aoy && ((aoy)this.c).ee()) && this.n > 0) {
					dem dem10 = this.c.cB();
					double double11 = dem10.g(this.m);
					if (double11 > 1.0E-7 || double11 > 0.0 && dem10.g() == 0.0) {
						this.m = dem10;
						this.f.accept(new qb(this.c.V(), this.m));
					}
				}

				if (ni7 != null) {
					this.f.accept(ni7);
				}

				this.c();
				if (boolean8) {
					this.d();
				}

				if (boolean9) {
					this.j = integer3;
					this.k = integer4;
				}

				this.q = false;
			}

			int integer3x = aec.d(this.c.bG() * 256.0F / 360.0F);
			if (Math.abs(integer3x - this.l) >= 1) {
				this.f.accept(new pq(this.c, (byte)integer3x));
				this.l = integer3x;
			}

			this.c.ad = false;
		}

		this.n++;
		if (this.c.w) {
			this.a(new qb(this.c));
			this.c.w = false;
		}
	}

	public void a(ze ze) {
		this.c.c(ze);
		ze.c(this.c);
	}

	public void b(ze ze) {
		this.a(ze.b::a);
		this.c.b(ze);
		ze.d(this.c);
	}

	public void a(Consumer<ni<?>> consumer) {
		if (this.c.y) {
			a.warn("Fetching packet for removed entity " + this.c);
		}

		ni<?> ni3 = this.c.O();
		this.l = aec.d(this.c.bG() * 256.0F / 360.0F);
		consumer.accept(ni3);
		if (!this.c.Y().d()) {
			consumer.accept(new pz(this.c.V(), this.c.Y(), true));
		}

		boolean boolean4 = this.e;
		if (this.c instanceof aoy) {
			Collection<apt> collection5 = ((aoy)this.c).dA().b();
			if (!collection5.isEmpty()) {
				consumer.accept(new qt(this.c.V(), collection5));
			}

			if (((aoy)this.c).ee()) {
				boolean4 = true;
			}
		}

		this.m = this.c.cB();
		if (boolean4 && !(ni3 instanceof no)) {
			consumer.accept(new qb(this.c.V(), this.m));
		}

		if (this.c instanceof aoy) {
			List<Pair<aor, bki>> list5 = Lists.<Pair<aor, bki>>newArrayList();

			for (aor aor9 : aor.values()) {
				bki bki10 = ((aoy)this.c).b(aor9);
				if (!bki10.a()) {
					list5.add(Pair.of(aor9, bki10.i()));
				}
			}

			if (!list5.isEmpty()) {
				consumer.accept(new qc(this.c.V(), list5));
			}
		}

		if (this.c instanceof aoy) {
			aoy aoy5 = (aoy)this.c;

			for (aog aog7 : aoy5.dg()) {
				consumer.accept(new qu(this.c.V(), aog7));
			}
		}

		if (!this.c.cm().isEmpty()) {
			consumer.accept(new qg(this.c));
		}

		if (this.c.bn()) {
			consumer.accept(new qg(this.c.cs()));
		}

		if (this.c instanceof aoz) {
			aoz aoz5 = (aoz)this.c;
			if (aoz5.eC()) {
				consumer.accept(new qa(aoz5, aoz5.eD()));
			}
		}
	}

	private void c() {
		tt tt2 = this.c.Y();
		if (tt2.a()) {
			this.a(new pz(this.c.V(), tt2, false));
		}

		if (this.c instanceof aoy) {
			Set<apt> set3 = ((aoy)this.c).dA().a();
			if (!set3.isEmpty()) {
				this.a(new qt(this.c.V(), set3));
			}

			set3.clear();
		}
	}

	private void d() {
		this.g = pa.a(this.c.cC());
		this.h = pa.a(this.c.cD());
		this.i = pa.a(this.c.cG());
	}

	public dem b() {
		return pa.a(this.g, this.h, this.i);
	}

	private void a(ni<?> ni) {
		this.f.accept(ni);
		if (this.c instanceof ze) {
			((ze)this.c).b.a(ni);
		}
	}
}
