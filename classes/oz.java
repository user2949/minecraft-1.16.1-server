import java.io.IOException;

public class oz implements ni<nl> {
	private int a;
	private bpa b;
	private int c;
	private int d;
	private boolean e;
	private boolean f;

	public oz() {
	}

	public oz(int integer1, bpa bpa, int integer3, int integer4, boolean boolean5, boolean boolean6) {
		this.a = integer1;
		this.b = bpa;
		this.c = integer3;
		this.d = integer4;
		this.e = boolean5;
		this.f = boolean6;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = bpa.b(mg);
		this.c = mg.i();
		this.d = mg.i();
		this.e = mg.readBoolean();
		this.f = mg.readBoolean();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		this.b.a(mg);
		mg.d(this.c);
		mg.d(this.d);
		mg.writeBoolean(this.e);
		mg.writeBoolean(this.f);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
