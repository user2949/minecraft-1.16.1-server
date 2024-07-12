import java.io.IOException;
import javax.annotation.Nullable;

public class qa implements ni<nl> {
	private int a;
	private int b;

	public qa() {
	}

	public qa(aom aom1, @Nullable aom aom2) {
		this.a = aom1.V();
		this.b = aom2 != null ? aom2.V() : 0;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readInt();
		this.b = mg.readInt();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeInt(this.a);
		mg.writeInt(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
