import java.io.IOException;

public class sb implements ni<qz> {
	private sb.a a;
	private uh b;
	private boolean c;
	private boolean d;
	private boolean e;
	private boolean f;
	private boolean g;
	private boolean h;
	private boolean i;
	private boolean j;

	public sb() {
	}

	public sb(bmu<?> bmu) {
		this.a = sb.a.SHOWN;
		this.b = bmu.f();
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.a(sb.a.class);
		if (this.a == sb.a.SHOWN) {
			this.b = mg.o();
		} else if (this.a == sb.a.SETTINGS) {
			this.c = mg.readBoolean();
			this.d = mg.readBoolean();
			this.e = mg.readBoolean();
			this.f = mg.readBoolean();
			this.g = mg.readBoolean();
			this.h = mg.readBoolean();
			this.i = mg.readBoolean();
			this.j = mg.readBoolean();
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		if (this.a == sb.a.SHOWN) {
			mg.a(this.b);
		} else if (this.a == sb.a.SETTINGS) {
			mg.writeBoolean(this.c);
			mg.writeBoolean(this.d);
			mg.writeBoolean(this.e);
			mg.writeBoolean(this.f);
			mg.writeBoolean(this.g);
			mg.writeBoolean(this.h);
			mg.writeBoolean(this.i);
			mg.writeBoolean(this.j);
		}
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public sb.a b() {
		return this.a;
	}

	public uh c() {
		return this.b;
	}

	public boolean d() {
		return this.c;
	}

	public boolean e() {
		return this.d;
	}

	public boolean f() {
		return this.e;
	}

	public boolean g() {
		return this.f;
	}

	public boolean h() {
		return this.g;
	}

	public boolean i() {
		return this.h;
	}

	public boolean j() {
		return this.i;
	}

	public boolean k() {
		return this.j;
	}

	public static enum a {
		SHOWN,
		SETTINGS;
	}
}
