import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class nt implements ni<nl> {
	private static final Logger b = LogManager.getLogger();
	private fu c;
	private cfj d;
	ry.a a;
	private boolean e;

	public nt() {
	}

	public nt(fu fu, cfj cfj, ry.a a, boolean boolean4, String string) {
		this.c = fu.h();
		this.d = cfj;
		this.a = a;
		this.e = boolean4;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.c = mg.e();
		this.d = bvr.m.a(mg.i());
		this.a = mg.a(ry.a.class);
		this.e = mg.readBoolean();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.c);
		mg.d(bvr.i(this.d));
		mg.a(this.a);
		mg.writeBoolean(this.e);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
