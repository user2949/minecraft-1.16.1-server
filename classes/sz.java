import java.io.IOException;
import java.security.PublicKey;

public class sz implements ni<sw> {
	private String a;
	private PublicKey b;
	private byte[] c;

	public sz() {
	}

	public sz(String string, PublicKey publicKey, byte[] arr) {
		this.a = string;
		this.b = publicKey;
		this.c = arr;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e(20);
		this.b = adn.a(mg.a());
		this.c = mg.a();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.a(this.b.getEncoded());
		mg.a(this.c);
	}

	public void a(sw sw) {
		sw.a(this);
	}
}
