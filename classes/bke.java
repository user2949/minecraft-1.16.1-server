import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public class bke implements bqa {
	public static final Map<bvr, bke> e = Maps.<bvr, bke>newHashMap();
	protected static final UUID f = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
	protected static final UUID g = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
	protected static final Random h = new Random();
	protected final biy i;
	private final bkw a;
	private final int b;
	private final int c;
	private final boolean d;
	private final bke j;
	@Nullable
	private String k;
	@Nullable
	private final bgf l;

	public static int a(bke bke) {
		return bke == null ? 0 : gl.am.a(bke);
	}

	public static bke b(int integer) {
		return gl.am.a(integer);
	}

	@Deprecated
	public static bke a(bvr bvr) {
		return (bke)e.getOrDefault(bvr, bkk.a);
	}

	public bke(bke.a a) {
		this.i = a.d;
		this.a = a.e;
		this.j = a.c;
		this.c = a.b;
		this.b = a.a;
		this.l = a.f;
		this.d = a.g;
	}

	public void a(bqb bqb, aoy aoy, bki bki, int integer) {
	}

	public boolean b(le le) {
		return false;
	}

	public boolean a(cfj cfj, bqb bqb, fu fu, bec bec) {
		return true;
	}

	@Override
	public bke h() {
		return this;
	}

	public ang a(blv blv) {
		return ang.PASS;
	}

	public float a(bki bki, cfj cfj) {
		return 1.0F;
	}

	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		if (this.s()) {
			bki bki5 = bec.b(anf);
			if (bec.q(this.t().d())) {
				bec.c(anf);
				return anh.b(bki5);
			} else {
				return anh.d(bki5);
			}
		} else {
			return anh.c(bec.b(anf));
		}
	}

	public bki a(bki bki, bqb bqb, aoy aoy) {
		return this.s() ? aoy.a(bqb, bki) : bki;
	}

	public final int i() {
		return this.b;
	}

	public final int j() {
		return this.c;
	}

	public boolean k() {
		return this.c > 0;
	}

	public boolean a(bki bki, aoy aoy2, aoy aoy3) {
		return false;
	}

	public boolean a(bki bki, bqb bqb, cfj cfj, fu fu, aoy aoy) {
		return false;
	}

	public boolean b(cfj cfj) {
		return false;
	}

	public ang a(bki bki, bec bec, aoy aoy, anf anf) {
		return ang.PASS;
	}

	public String toString() {
		return gl.am.b(this).a();
	}

	protected String m() {
		if (this.k == null) {
			this.k = v.a("item", gl.am.b(this));
		}

		return this.k;
	}

	public String a() {
		return this.m();
	}

	public String f(bki bki) {
		return this.a();
	}

	public boolean n() {
		return true;
	}

	@Nullable
	public final bke o() {
		return this.j;
	}

	public boolean p() {
		return this.j != null;
	}

	public void a(bki bki, bqb bqb, aom aom, int integer, boolean boolean5) {
	}

	public void b(bki bki, bqb bqb, bec bec) {
	}

	public boolean ae_() {
		return false;
	}

	public blu d_(bki bki) {
		return bki.b().s() ? blu.EAT : blu.NONE;
	}

	public int e_(bki bki) {
		if (bki.b().s()) {
			return this.t().e() ? 16 : 32;
		} else {
			return 0;
		}
	}

	public void a(bki bki, bqb bqb, aoy aoy, int integer) {
	}

	public mr h(bki bki) {
		return new ne(this.f(bki));
	}

	public boolean e(bki bki) {
		return bki.x();
	}

	public bkw i(bki bki) {
		if (!bki.x()) {
			return this.a;
		} else {
			switch (this.a) {
				case COMMON:
				case UNCOMMON:
					return bkw.RARE;
				case RARE:
					return bkw.EPIC;
				case EPIC:
				default:
					return this.a;
			}
		}
	}

	public boolean f_(bki bki) {
		return this.i() == 1 && this.k();
	}

	protected static deh a(bqb bqb, bec bec, bpj.b b) {
		float float4 = bec.q;
		float float5 = bec.p;
		dem dem6 = bec.j(1.0F);
		float float7 = aec.b(-float5 * (float) (Math.PI / 180.0) - (float) Math.PI);
		float float8 = aec.a(-float5 * (float) (Math.PI / 180.0) - (float) Math.PI);
		float float9 = -aec.b(-float4 * (float) (Math.PI / 180.0));
		float float10 = aec.a(-float4 * (float) (Math.PI / 180.0));
		float float11 = float8 * float9;
		float float13 = float7 * float9;
		double double14 = 5.0;
		dem dem16 = dem6.b((double)float11 * 5.0, (double)float10 * 5.0, (double)float13 * 5.0);
		return bqb.a(new bpj(dem6, dem16, bpj.a.OUTLINE, b, bec));
	}

	public int c() {
		return 0;
	}

	public void a(biy biy, gi<bki> gi) {
		if (this.a(biy)) {
			gi.add(new bki(this));
		}
	}

	protected boolean a(biy biy) {
		biy biy3 = this.q();
		return biy3 != null && (biy == biy.g || biy == biy3);
	}

	@Nullable
	public final biy q() {
		return this.i;
	}

	public boolean a(bki bki1, bki bki2) {
		return false;
	}

	public Multimap<aps, apv> a(aor aor) {
		return ImmutableMultimap.of();
	}

	public boolean j(bki bki) {
		return bki.b() == bkk.qP;
	}

	public bki r() {
		return new bki(this);
	}

	public boolean a(adf<bke> adf) {
		return adf.a(this);
	}

	public boolean s() {
		return this.l != null;
	}

	@Nullable
	public bgf t() {
		return this.l;
	}

	public ack ag_() {
		return acl.eJ;
	}

	public ack af_() {
		return acl.eK;
	}

	public boolean u() {
		return this.d;
	}

	public boolean a(anw anw) {
		return !this.d || !anw.p();
	}

	public static class a {
		private int a = 64;
		private int b;
		private bke c;
		private biy d;
		private bkw e = bkw.COMMON;
		private bgf f;
		private boolean g;

		public bke.a a(bgf bgf) {
			this.f = bgf;
			return this;
		}

		public bke.a a(int integer) {
			if (this.b > 0) {
				throw new RuntimeException("Unable to have damage AND stack.");
			} else {
				this.a = integer;
				return this;
			}
		}

		public bke.a b(int integer) {
			return this.b == 0 ? this.c(integer) : this;
		}

		public bke.a c(int integer) {
			this.b = integer;
			this.a = 1;
			return this;
		}

		public bke.a a(bke bke) {
			this.c = bke;
			return this;
		}

		public bke.a a(biy biy) {
			this.d = biy;
			return this;
		}

		public bke.a a(bkw bkw) {
			this.e = bkw;
			return this;
		}

		public bke.a a() {
			this.g = true;
			return this;
		}
	}
}
