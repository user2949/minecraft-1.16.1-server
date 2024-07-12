import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class ctz<C extends cnr> {
	public static final ctz<?> a = new ctz<cnw>(cml.c, 0, 0, ctd.a(), 0, 0L) {
		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, cnw cnw) {
		}
	};
	private final cml<C> e;
	protected final List<cty> b = Lists.<cty>newArrayList();
	protected ctd c;
	private final int f;
	private final int g;
	private int h;
	protected final ciy d;

	public ctz(cml<C> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
		this.e = cml;
		this.f = integer2;
		this.g = integer3;
		this.h = integer5;
		this.d = new ciy();
		this.d.c(long6, integer2, integer3);
		this.c = ctd;
	}

	public abstract void a(cha cha, cva cva, int integer3, int integer4, bre bre, C cnr);

	public ctd c() {
		return this.c;
	}

	public List<cty> d() {
		return this.b;
	}

	public void a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph) {
		synchronized (this.b) {
			if (!this.b.isEmpty()) {
				ctd ctd9 = ((cty)this.b.get(0)).n;
				gr gr10 = ctd9.g();
				fu fu11 = new fu(gr10.u(), ctd9.b, gr10.w());
				Iterator<cty> iterator12 = this.b.iterator();

				while (iterator12.hasNext()) {
					cty cty13 = (cty)iterator12.next();
					if (cty13.g().b(ctd) && !cty13.a(bqu, bqq, cha, random, ctd, bph, fu11)) {
						iterator12.remove();
					}
				}

				this.b();
			}
		}
	}

	protected void b() {
		this.c = ctd.a();

		for (cty cty3 : this.b) {
			this.c.c(cty3.g());
		}
	}

	public le a(int integer1, int integer2) {
		le le4 = new le();
		if (this.e()) {
			le4.a("id", gl.aG.b(this.l()).toString());
			le4.b("ChunkX", integer1);
			le4.b("ChunkZ", integer2);
			le4.b("references", this.h);
			le4.a("BB", this.c.h());
			lk lk5 = new lk();
			synchronized (this.b) {
				for (cty cty8 : this.b) {
					lk5.add(cty8.f());
				}
			}

			le4.a("Children", lk5);
			return le4;
		} else {
			le4.a("id", "INVALID");
			return le4;
		}
	}

	protected void a(int integer1, Random random, int integer3) {
		int integer5 = integer1 - integer3;
		int integer6 = this.c.e() + 1;
		if (integer6 < integer5) {
			integer6 += random.nextInt(integer5 - integer6);
		}

		int integer7 = integer6 - this.c.e;
		this.c.a(0, integer7, 0);

		for (cty cty9 : this.b) {
			cty9.a(0, integer7, 0);
		}
	}

	protected void a(Random random, int integer2, int integer3) {
		int integer5 = integer3 - integer2 + 1 - this.c.e();
		int integer6;
		if (integer5 > 1) {
			integer6 = integer2 + random.nextInt(integer5);
		} else {
			integer6 = integer2;
		}

		int integer7 = integer6 - this.c.b;
		this.c.a(0, integer7, 0);

		for (cty cty9 : this.b) {
			cty9.a(0, integer7, 0);
		}
	}

	public boolean e() {
		return !this.b.isEmpty();
	}

	public int f() {
		return this.f;
	}

	public int g() {
		return this.g;
	}

	public fu a() {
		return new fu(this.f << 4, 0, this.g << 4);
	}

	public boolean h() {
		return this.h < this.k();
	}

	public void i() {
		this.h++;
	}

	public int j() {
		return this.h;
	}

	protected int k() {
		return 1;
	}

	public cml<?> l() {
		return this.e;
	}
}
