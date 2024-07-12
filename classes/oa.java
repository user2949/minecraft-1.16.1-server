import java.io.IOException;
import java.util.UUID;

public class oa implements ni<nl> {
	private mr a;
	private mo b;
	private UUID c;

	public oa() {
	}

	public oa(mr mr, mo mo, UUID uUID) {
		this.a = mr;
		this.b = mo;
		this.c = uUID;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.h();
		this.b = mo.a(mg.readByte());
		this.c = mg.k();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.writeByte(this.b.a());
		mg.a(this.c);
	}

	public void a(nl nl) {
		nl.a(this);
	}

	public boolean c() {
		return this.b == mo.SYSTEM || this.b == mo.GAME_INFO;
	}

	public mo d() {
		return this.b;
	}

	@Override
	public boolean a() {
		return true;
	}
}
