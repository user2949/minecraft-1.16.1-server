import java.io.IOException;

public class ry implements ni<qz> {
	private fu a;
	private fz b;
	private ry.a c;

	@Override
	public void a(mg mg) throws IOException {
		this.c = mg.a(ry.a.class);
		this.a = mg.e();
		this.b = fz.a(mg.readUnsignedByte());
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.c);
		mg.a(this.a);
		mg.writeByte(this.b.c());
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public fu b() {
		return this.a;
	}

	public fz c() {
		return this.b;
	}

	public ry.a d() {
		return this.c;
	}

	public static enum a {
		START_DESTROY_BLOCK,
		ABORT_DESTROY_BLOCK,
		STOP_DESTROY_BLOCK,
		DROP_ALL_ITEMS,
		DROP_ITEM,
		RELEASE_USE_ITEM,
		SWAP_ITEM_WITH_OFFHAND;
	}
}
