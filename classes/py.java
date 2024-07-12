import java.io.IOException;
import javax.annotation.Nullable;

public class py implements ni<nl> {
	private int a;
	private String b;

	public py() {
	}

	public py(int integer, @Nullable dfj dfj) {
		this.a = integer;
		if (dfj == null) {
			this.b = "";
		} else {
			this.b = dfj.b();
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readByte();
		this.b = mg.e(16);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a);
		mg.a(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
