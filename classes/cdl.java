import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class cdl {
	private static final Logger a = LogManager.getLogger();
	private final cdm<?> b;
	@Nullable
	protected bqb d;
	protected fu e = fu.b;
	protected boolean f;
	@Nullable
	private cfj c;
	private boolean g;

	public cdl(cdm<?> cdm) {
		this.b = cdm;
	}

	@Nullable
	public bqb v() {
		return this.d;
	}

	public void a(bqb bqb, fu fu) {
		this.d = bqb;
		this.e = fu.h();
	}

	public boolean n() {
		return this.d != null;
	}

	public void a(cfj cfj, le le) {
		this.e = new fu(le.h("x"), le.h("y"), le.h("z"));
	}

	public le a(le le) {
		return this.b(le);
	}

	private le b(le le) {
		uh uh3 = cdm.a(this.u());
		if (uh3 == null) {
			throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
		} else {
			le.a("id", uh3.toString());
			le.b("x", this.e.u());
			le.b("y", this.e.v());
			le.b("z", this.e.w());
			return le;
		}
	}

	@Nullable
	public static cdl b(cfj cfj, le le) {
		String string3 = le.l("id");
		return (cdl)gl.aC.b(new uh(string3)).map(cdm -> {
			try {
				return cdm.a();
			} catch (Throwable var3) {
				a.error("Failed to create block entity {}", string3, var3);
				return null;
			}
		}).map(cdl -> {
			try {
				cdl.a(cfj, le);
				return cdl;
			} catch (Throwable var5) {
				a.error("Failed to load data for block entity {}", string3, var5);
				return null;
			}
		}).orElseGet(() -> {
			a.warn("Skipping BlockEntity with id {}", string3);
			return null;
		});
	}

	public void Z_() {
		if (this.d != null) {
			this.c = this.d.d_(this.e);
			this.d.b(this.e, this);
			if (!this.c.g()) {
				this.d.c(this.e, this.c.b());
			}
		}
	}

	public fu o() {
		return this.e;
	}

	public cfj p() {
		if (this.c == null) {
			this.c = this.d.d_(this.e);
		}

		return this.c;
	}

	@Nullable
	public nv a() {
		return null;
	}

	public le b() {
		return this.b(new le());
	}

	public boolean q() {
		return this.f;
	}

	public void an_() {
		this.f = true;
	}

	public void r() {
		this.f = false;
	}

	public boolean a_(int integer1, int integer2) {
		return false;
	}

	public void s() {
		this.c = null;
	}

	public void a(k k) {
		k.a("Name", (l<String>)(() -> gl.aC.b(this.u()) + " // " + this.getClass().getCanonicalName()));
		if (this.d != null) {
			k.a(k, this.e, this.p());
			k.a(k, this.e, this.d.d_(this.e));
		}
	}

	public void a(fu fu) {
		this.e = fu.h();
	}

	public boolean t() {
		return false;
	}

	public void a(cap cap) {
	}

	public void a(bzj bzj) {
	}

	public cdm<?> u() {
		return this.b;
	}

	public void w() {
		if (!this.g) {
			this.g = true;
			a.warn("Block entity invalid: {} @ {}", () -> gl.aC.b(this.u()), this::o);
		}
	}
}
