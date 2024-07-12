import java.io.IOException;

public class ou implements ni<nl> {
	private int a;
	private fu b;
	private int c;
	private boolean d;

	public ou() {
	}

	public ou(int integer1, fu fu, int integer3, boolean boolean4) {
		this.a = integer1;
		this.b = fu.h();
		this.c = integer3;
		this.d = boolean4;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readInt();
		this.b = mg.e();
		this.c = mg.readInt();
		this.d = mg.readBoolean();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeInt(this.a);
		mg.a(this.b);
		mg.writeInt(this.c);
		mg.writeBoolean(this.d);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
