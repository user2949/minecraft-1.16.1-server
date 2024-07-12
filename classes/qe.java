import java.io.IOException;

public class qe implements ni<nl> {
	private float a;
	private int b;
	private float c;

	public qe() {
	}

	public qe(float float1, int integer, float float3) {
		this.a = float1;
		this.b = integer;
		this.c = float3;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readFloat();
		this.b = mg.i();
		this.c = mg.readFloat();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeFloat(this.a);
		mg.d(this.b);
		mg.writeFloat(this.c);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
