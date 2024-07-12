import java.io.IOException;

public class ph implements ni<nl> {
	public ph.a a;
	public int b;
	public int c;
	public int d;
	public mr e;

	public ph() {
	}

	public ph(anv anv, ph.a a) {
		this(anv, a, nd.d);
	}

	public ph(anv anv, ph.a a, mr mr) {
		this.a = a;
		aoy aoy5 = anv.c();
		switch (a) {
			case END_COMBAT:
				this.d = anv.f();
				this.c = aoy5 == null ? -1 : aoy5.V();
				break;
			case ENTITY_DIED:
				this.b = anv.h().V();
				this.c = aoy5 == null ? -1 : aoy5.V();
				this.e = mr;
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.a(ph.a.class);
		if (this.a == ph.a.END_COMBAT) {
			this.d = mg.i();
			this.c = mg.readInt();
		} else if (this.a == ph.a.ENTITY_DIED) {
			this.b = mg.i();
			this.c = mg.readInt();
			this.e = mg.h();
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		if (this.a == ph.a.END_COMBAT) {
			mg.d(this.d);
			mg.writeInt(this.c);
		} else if (this.a == ph.a.ENTITY_DIED) {
			mg.d(this.b);
			mg.writeInt(this.c);
			mg.a(this.e);
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public boolean a() {
		return this.a == ph.a.ENTITY_DIED;
	}

	public static enum a {
		ENTER_COMBAT,
		END_COMBAT,
		ENTITY_DIED;
	}
}
