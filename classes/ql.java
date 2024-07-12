import java.io.IOException;
import org.apache.commons.lang3.Validate;

public class ql implements ni<nl> {
	private ack a;
	private acm b;
	private int c;
	private float d;
	private float e;

	public ql() {
	}

	public ql(ack ack, acm acm, aom aom, float float4, float float5) {
		Validate.notNull(ack, "sound");
		this.a = ack;
		this.b = acm;
		this.c = aom.V();
		this.d = float4;
		this.e = float5;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = gl.ag.a(mg.i());
		this.b = mg.a(acm.class);
		this.c = mg.i();
		this.d = mg.readFloat();
		this.e = mg.readFloat();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(gl.ag.a(this.a));
		mg.a(this.b);
		mg.d(this.c);
		mg.writeFloat(this.d);
		mg.writeFloat(this.e);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
