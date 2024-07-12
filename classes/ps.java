import java.io.IOException;

public class ps implements ni<nl> {
	private ps.a a;
	private int b;
	private double c;
	private double d;
	private double e;
	private double f;
	private long g;
	private int h;
	private int i;

	public ps() {
	}

	public ps(cgw cgw, ps.a a) {
		this.a = a;
		this.c = cgw.a();
		this.d = cgw.b();
		this.f = cgw.i();
		this.e = cgw.k();
		this.g = cgw.j();
		this.b = cgw.m();
		this.i = cgw.r();
		this.h = cgw.q();
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.a(ps.a.class);
		switch (this.a) {
			case SET_SIZE:
				this.e = mg.readDouble();
				break;
			case LERP_SIZE:
				this.f = mg.readDouble();
				this.e = mg.readDouble();
				this.g = mg.j();
				break;
			case SET_CENTER:
				this.c = mg.readDouble();
				this.d = mg.readDouble();
				break;
			case SET_WARNING_BLOCKS:
				this.i = mg.i();
				break;
			case SET_WARNING_TIME:
				this.h = mg.i();
				break;
			case INITIALIZE:
				this.c = mg.readDouble();
				this.d = mg.readDouble();
				this.f = mg.readDouble();
				this.e = mg.readDouble();
				this.g = mg.j();
				this.b = mg.i();
				this.i = mg.i();
				this.h = mg.i();
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		switch (this.a) {
			case SET_SIZE:
				mg.writeDouble(this.e);
				break;
			case LERP_SIZE:
				mg.writeDouble(this.f);
				mg.writeDouble(this.e);
				mg.b(this.g);
				break;
			case SET_CENTER:
				mg.writeDouble(this.c);
				mg.writeDouble(this.d);
				break;
			case SET_WARNING_BLOCKS:
				mg.d(this.i);
				break;
			case SET_WARNING_TIME:
				mg.d(this.h);
				break;
			case INITIALIZE:
				mg.writeDouble(this.c);
				mg.writeDouble(this.d);
				mg.writeDouble(this.f);
				mg.writeDouble(this.e);
				mg.b(this.g);
				mg.d(this.b);
				mg.d(this.i);
				mg.d(this.h);
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}

	public static enum a {
		SET_SIZE,
		LERP_SIZE,
		SET_CENTER,
		INITIALIZE,
		SET_WARNING_TIME,
		SET_WARNING_BLOCKS;
	}
}
