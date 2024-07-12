import java.io.IOException;
import java.util.UUID;

public class ny implements ni<nl> {
	private UUID a;
	private ny.a b;
	private mr c;
	private float d;
	private amw.a e;
	private amw.b f;
	private boolean g;
	private boolean h;
	private boolean i;

	public ny() {
	}

	public ny(ny.a a, amw amw) {
		this.b = a;
		this.a = amw.i();
		this.c = amw.j();
		this.d = amw.k();
		this.e = amw.l();
		this.f = amw.m();
		this.g = amw.n();
		this.h = amw.o();
		this.i = amw.p();
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.k();
		this.b = mg.a(ny.a.class);
		switch (this.b) {
			case ADD:
				this.c = mg.h();
				this.d = mg.readFloat();
				this.e = mg.a(amw.a.class);
				this.f = mg.a(amw.b.class);
				this.a(mg.readUnsignedByte());
			case REMOVE:
			default:
				break;
			case UPDATE_PCT:
				this.d = mg.readFloat();
				break;
			case UPDATE_NAME:
				this.c = mg.h();
				break;
			case UPDATE_STYLE:
				this.e = mg.a(amw.a.class);
				this.f = mg.a(amw.b.class);
				break;
			case UPDATE_PROPERTIES:
				this.a(mg.readUnsignedByte());
		}
	}

	private void a(int integer) {
		this.g = (integer & 1) > 0;
		this.h = (integer & 2) > 0;
		this.i = (integer & 4) > 0;
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.a(this.b);
		switch (this.b) {
			case ADD:
				mg.a(this.c);
				mg.writeFloat(this.d);
				mg.a(this.e);
				mg.a(this.f);
				mg.writeByte(this.k());
			case REMOVE:
			default:
				break;
			case UPDATE_PCT:
				mg.writeFloat(this.d);
				break;
			case UPDATE_NAME:
				mg.a(this.c);
				break;
			case UPDATE_STYLE:
				mg.a(this.e);
				mg.a(this.f);
				break;
			case UPDATE_PROPERTIES:
				mg.writeByte(this.k());
		}
	}

	private int k() {
		int integer2 = 0;
		if (this.g) {
			integer2 |= 1;
		}

		if (this.h) {
			integer2 |= 2;
		}

		if (this.i) {
			integer2 |= 4;
		}

		return integer2;
	}

	public void a(nl nl) {
		nl.a(this);
	}

	public static enum a {
		ADD,
		REMOVE,
		UPDATE_PCT,
		UPDATE_NAME,
		UPDATE_STYLE,
		UPDATE_PROPERTIES;
	}
}
