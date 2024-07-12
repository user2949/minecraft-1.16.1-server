import java.io.IOException;
import javax.annotation.Nullable;

public class ro implements ni<qz> {
	private int a;
	private ro.a b;
	private dem c;
	private anf d;
	private boolean e;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.a(ro.a.class);
		if (this.b == ro.a.INTERACT_AT) {
			this.c = new dem((double)mg.readFloat(), (double)mg.readFloat(), (double)mg.readFloat());
		}

		if (this.b == ro.a.INTERACT || this.b == ro.a.INTERACT_AT) {
			this.d = mg.a(anf.class);
		}

		this.e = mg.readBoolean();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.a(this.b);
		if (this.b == ro.a.INTERACT_AT) {
			mg.writeFloat((float)this.c.b);
			mg.writeFloat((float)this.c.c);
			mg.writeFloat((float)this.c.d);
		}

		if (this.b == ro.a.INTERACT || this.b == ro.a.INTERACT_AT) {
			mg.a(this.d);
		}

		mg.writeBoolean(this.e);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	@Nullable
	public aom a(bqb bqb) {
		return bqb.a(this.a);
	}

	public ro.a b() {
		return this.b;
	}

	public anf c() {
		return this.d;
	}

	public dem d() {
		return this.c;
	}

	public boolean e() {
		return this.e;
	}

	public static enum a {
		INTERACT,
		ATTACK,
		INTERACT_AT;
	}
}
