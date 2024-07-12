import com.google.common.collect.ComparisonChain;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aog implements Comparable<aog> {
	private static final Logger a = LogManager.getLogger();
	private final aoe b;
	private int c;
	private int d;
	private boolean e;
	private boolean f;
	private boolean h;
	private boolean i;
	@Nullable
	private aog j;

	public aog(aoe aoe) {
		this(aoe, 0, 0);
	}

	public aog(aoe aoe, int integer) {
		this(aoe, integer, 0);
	}

	public aog(aoe aoe, int integer2, int integer3) {
		this(aoe, integer2, integer3, false, true);
	}

	public aog(aoe aoe, int integer2, int integer3, boolean boolean4, boolean boolean5) {
		this(aoe, integer2, integer3, boolean4, boolean5, boolean5);
	}

	public aog(aoe aoe, int integer2, int integer3, boolean boolean4, boolean boolean5, boolean boolean6) {
		this(aoe, integer2, integer3, boolean4, boolean5, boolean6, null);
	}

	public aog(aoe aoe, int integer2, int integer3, boolean boolean4, boolean boolean5, boolean boolean6, @Nullable aog aog) {
		this.b = aoe;
		this.c = integer2;
		this.d = integer3;
		this.f = boolean4;
		this.h = boolean5;
		this.i = boolean6;
		this.j = aog;
	}

	public aog(aog aog) {
		this.b = aog.b;
		this.a(aog);
	}

	void a(aog aog) {
		this.c = aog.c;
		this.d = aog.d;
		this.f = aog.f;
		this.h = aog.h;
		this.i = aog.i;
	}

	public boolean b(aog aog) {
		if (this.b != aog.b) {
			a.warn("This method should only be called for matching effects!");
		}

		boolean boolean3 = false;
		if (aog.d > this.d) {
			if (aog.c < this.c) {
				aog aog4 = this.j;
				this.j = new aog(this);
				this.j.j = aog4;
			}

			this.d = aog.d;
			this.c = aog.c;
			boolean3 = true;
		} else if (aog.c > this.c) {
			if (aog.d == this.d) {
				this.c = aog.c;
				boolean3 = true;
			} else if (this.j == null) {
				this.j = new aog(aog);
			} else {
				this.j.b(aog);
			}
		}

		if (!aog.f && this.f || boolean3) {
			this.f = aog.f;
			boolean3 = true;
		}

		if (aog.h != this.h) {
			this.h = aog.h;
			boolean3 = true;
		}

		if (aog.i != this.i) {
			this.i = aog.i;
			boolean3 = true;
		}

		return boolean3;
	}

	public aoe a() {
		return this.b;
	}

	public int b() {
		return this.c;
	}

	public int c() {
		return this.d;
	}

	public boolean d() {
		return this.f;
	}

	public boolean e() {
		return this.h;
	}

	public boolean f() {
		return this.i;
	}

	public boolean a(aoy aoy, Runnable runnable) {
		if (this.c > 0) {
			if (this.b.a(this.c, this.d)) {
				this.a(aoy);
			}

			this.i();
			if (this.c == 0 && this.j != null) {
				this.a(this.j);
				this.j = this.j.j;
				runnable.run();
			}
		}

		return this.c > 0;
	}

	private int i() {
		if (this.j != null) {
			this.j.i();
		}

		return --this.c;
	}

	public void a(aoy aoy) {
		if (this.c > 0) {
			this.b.a(aoy, this.d);
		}
	}

	public String g() {
		return this.b.c();
	}

	public String toString() {
		String string2;
		if (this.d > 0) {
			string2 = this.g() + " x " + (this.d + 1) + ", Duration: " + this.c;
		} else {
			string2 = this.g() + ", Duration: " + this.c;
		}

		if (this.e) {
			string2 = string2 + ", Splash: true";
		}

		if (!this.h) {
			string2 = string2 + ", Particles: false";
		}

		if (!this.i) {
			string2 = string2 + ", Show Icon: false";
		}

		return string2;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof aog)) {
			return false;
		} else {
			aog aog3 = (aog)object;
			return this.c == aog3.c && this.d == aog3.d && this.e == aog3.e && this.f == aog3.f && this.b.equals(aog3.b);
		}
	}

	public int hashCode() {
		int integer2 = this.b.hashCode();
		integer2 = 31 * integer2 + this.c;
		integer2 = 31 * integer2 + this.d;
		integer2 = 31 * integer2 + (this.e ? 1 : 0);
		return 31 * integer2 + (this.f ? 1 : 0);
	}

	public le a(le le) {
		le.a("Id", (byte)aoe.a(this.a()));
		this.c(le);
		return le;
	}

	private void c(le le) {
		le.a("Amplifier", (byte)this.c());
		le.b("Duration", this.b());
		le.a("Ambient", this.d());
		le.a("ShowParticles", this.e());
		le.a("ShowIcon", this.f());
		if (this.j != null) {
			le le3 = new le();
			this.j.a(le3);
			le.a("HiddenEffect", le3);
		}
	}

	public static aog b(le le) {
		int integer2 = le.f("Id");
		aoe aoe3 = aoe.a(integer2);
		return aoe3 == null ? null : a(aoe3, le);
	}

	private static aog a(aoe aoe, le le) {
		int integer3 = le.f("Amplifier");
		int integer4 = le.h("Duration");
		boolean boolean5 = le.q("Ambient");
		boolean boolean6 = true;
		if (le.c("ShowParticles", 1)) {
			boolean6 = le.q("ShowParticles");
		}

		boolean boolean7 = boolean6;
		if (le.c("ShowIcon", 1)) {
			boolean7 = le.q("ShowIcon");
		}

		aog aog8 = null;
		if (le.c("HiddenEffect", 10)) {
			aog8 = a(aoe, le.p("HiddenEffect"));
		}

		return new aog(aoe, integer4, integer3 < 0 ? 0 : integer3, boolean5, boolean6, boolean7, aog8);
	}

	public int compareTo(aog aog) {
		int integer3 = 32147;
		return (this.b() <= 32147 || aog.b() <= 32147) && (!this.d() || !aog.d())
			? ComparisonChain.start().compare(this.d(), aog.d()).compare(this.b(), aog.b()).compare(this.a().f(), aog.a().f()).result()
			: ComparisonChain.start().compare(this.d(), aog.d()).compare(this.a().f(), aog.a().f()).result();
	}
}
