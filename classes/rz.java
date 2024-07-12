import java.io.IOException;

public class rz implements ni<qz> {
	private int a;
	private rz.a b;
	private int c;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.a(rz.a.class);
		this.c = mg.i();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.a(this.b);
		mg.d(this.c);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public rz.a c() {
		return this.b;
	}

	public int d() {
		return this.c;
	}

	public static enum a {
		PRESS_SHIFT_KEY,
		RELEASE_SHIFT_KEY,
		STOP_SLEEPING,
		START_SPRINTING,
		STOP_SPRINTING,
		START_RIDING_JUMP,
		STOP_RIDING_JUMP,
		OPEN_INVENTORY,
		START_FALL_FLYING;
	}
}
