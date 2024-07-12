import java.io.IOException;
import org.apache.commons.lang3.Validate;

public class qm implements ni<nl> {
	private ack a;
	private acm b;
	private int c;
	private int d;
	private int e;
	private float f;
	private float g;

	public qm() {
	}

	public qm(ack ack, acm acm, double double3, double double4, double double5, float float6, float float7) {
		Validate.notNull(ack, "sound");
		this.a = ack;
		this.b = acm;
		this.c = (int)(double3 * 8.0);
		this.d = (int)(double4 * 8.0);
		this.e = (int)(double5 * 8.0);
		this.f = float6;
		this.g = float7;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = gl.ag.a(mg.i());
		this.b = mg.a(acm.class);
		this.c = mg.readInt();
		this.d = mg.readInt();
		this.e = mg.readInt();
		this.f = mg.readFloat();
		this.g = mg.readFloat();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(gl.ag.a(this.a));
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
