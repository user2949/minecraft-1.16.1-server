import java.io.IOException;
import java.util.UUID;
import javax.annotation.Nullable;

public class sp implements ni<qz> {
	private UUID a;

	public sp() {
	}

	public sp(UUID uUID) {
		this.a = uUID;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.k();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	@Nullable
	public aom a(zd zd) {
		return zd.a(this.a);
	}
}
