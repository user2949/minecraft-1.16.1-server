import java.util.function.Predicate;
import javax.annotation.Nullable;

public class chk {
	private static final cho<cfj> a = new chg<>(bvr.m, bvs.a.n());
	private final int b;
	private short c;
	private short d;
	private short e;
	private final chq<cfj> f;

	public chk(int integer) {
		this(integer, (short)0, (short)0, (short)0);
	}

	public chk(int integer, short short2, short short3, short short4) {
		this.b = integer;
		this.c = short2;
		this.d = short3;
		this.e = short4;
		this.f = new chq<>(a, bvr.m, lq::c, lq::a, bvs.a.n());
	}

	public cfj a(int integer1, int integer2, int integer3) {
		return this.f.a(integer1, integer2, integer3);
	}

	public cxa b(int integer1, int integer2, int integer3) {
		return this.f.a(integer1, integer2, integer3).m();
	}

	public void a() {
		this.f.a();
	}

	public void b() {
		this.f.b();
	}

	public cfj a(int integer1, int integer2, int integer3, cfj cfj) {
		return this.a(integer1, integer2, integer3, cfj, true);
	}

	public cfj a(int integer1, int integer2, int integer3, cfj cfj, boolean boolean5) {
		cfj cfj7;
		if (boolean5) {
			cfj7 = this.f.a(integer1, integer2, integer3, cfj);
		} else {
			cfj7 = this.f.b(integer1, integer2, integer3, cfj);
		}

		cxa cxa8 = cfj7.m();
		cxa cxa9 = cfj.m();
		if (!cfj7.g()) {
			this.c--;
			if (cfj7.n()) {
				this.d--;
			}
		}

		if (!cxa8.c()) {
			this.e--;
		}

		if (!cfj.g()) {
			this.c++;
			if (cfj.n()) {
				this.d++;
			}
		}

		if (!cxa9.c()) {
			this.e++;
		}

		return cfj7;
	}

	public boolean c() {
		return this.c == 0;
	}

	public static boolean a(@Nullable chk chk) {
		return chk == chj.a || chk.c();
	}

	public boolean d() {
		return this.e() || this.f();
	}

	public boolean e() {
		return this.d > 0;
	}

	public boolean f() {
		return this.e > 0;
	}

	public int g() {
		return this.b;
	}

	public void h() {
		this.c = 0;
		this.d = 0;
		this.e = 0;
		this.f.a((chq.a<cfj>)((cfj, integer) -> {
			cxa cxa4 = cfj.m();
			if (!cfj.g()) {
				this.c = (short)(this.c + integer);
				if (cfj.n()) {
					this.d = (short)(this.d + integer);
				}
			}

			if (!cxa4.c()) {
				this.c = (short)(this.c + integer);
				if (cxa4.f()) {
					this.e = (short)(this.e + integer);
				}
			}
		}));
	}

	public chq<cfj> i() {
		return this.f;
	}

	public void b(mg mg) {
		mg.writeShort(this.c);
		this.f.b(mg);
	}

	public int j() {
		return 2 + this.f.c();
	}

	public boolean a(Predicate<cfj> predicate) {
		return this.f.a(predicate);
	}
}
