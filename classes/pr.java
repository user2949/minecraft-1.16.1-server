import java.io.IOException;
import javax.annotation.Nullable;

public class pr implements ni<nl> {
	@Nullable
	private uh a;

	public pr() {
	}

	public pr(@Nullable uh uh) {
		this.a = uh;
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		if (mg.readBoolean()) {
			this.a = mg.o();
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeBoolean(this.a != null);
		if (this.a != null) {
			mg.a(this.a);
		}
	}
}
