import java.io.IOException;

public class ol implements ni<nl> {
	private uh a;
	private acm b;
	private int c;
	private int d = Integer.MAX_VALUE;
	private int e;
	private float f;
	private float g;

	public ol() {
	}

	public ol(uh uh, acm acm, dem dem, float float4, float float5) {
		this.a = uh;
		this.b = acm;
		this.c = (int)(dem.b * 8.0);
		this.d = (int)(dem.c * 8.0);
		this.e = (int)(dem.d * 8.0);
		this.f = float4;
		this.g = float5;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.o();
		this.b = mg.a(acm.class);
		this.c = mg.readInt();
		this.d = mg.readInt();
		this.e = mg.readInt();
		this.f = mg.readFloat();
		this.g = mg.readFloat();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.a(this.b);
		mg.writeInt(this.c);
		mg.writeInt(this.d);
		mg.writeInt(this.e);
		mg.writeFloat(this.f);
		mg.writeFloat(this.g);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
