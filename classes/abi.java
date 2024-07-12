import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;

public class abi implements abb {
	private final String a;
	private final uh b;
	private final InputStream c;
	private final InputStream d;

	public abi(String string, uh uh, InputStream inputStream3, @Nullable InputStream inputStream4) {
		this.a = string;
		this.b = uh;
		this.c = inputStream3;
		this.d = inputStream4;
	}

	@Override
	public InputStream b() {
		return this.c;
	}

	@Override
	public String d() {
		return this.a;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof abi)) {
			return false;
		} else {
			abi abi3 = (abi)object;
			if (this.b != null ? this.b.equals(abi3.b) : abi3.b == null) {
				return this.a != null ? this.a.equals(abi3.a) : abi3.a == null;
			} else {
				return false;
			}
		}
	}

	public int hashCode() {
		int integer2 = this.a != null ? this.a.hashCode() : 0;
		return 31 * integer2 + (this.b != null ? this.b.hashCode() : 0);
	}

	public void close() throws IOException {
		this.c.close();
		if (this.d != null) {
			this.d.close();
		}
	}
}
