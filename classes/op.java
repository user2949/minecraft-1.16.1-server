import java.io.IOException;

public class op implements ni<nl> {
	private int a;
	private int b;

	public op() {
	}

	public op(int integer1, int integer2) {
		this.a = integer1;
		this.b = integer2;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readInt();
		this.b = mg.readInt();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeInt(this.a);
		mg.writeInt(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
