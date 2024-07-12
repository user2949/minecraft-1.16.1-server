import java.io.IOException;
import java.security.PrivateKey;
import javax.crypto.SecretKey;

public class tf implements ni<tc> {
	private byte[] a = new byte[0];
	private byte[] b = new byte[0];

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.a();
		this.b = mg.a();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.a(this.b);
	}

	public void a(tc tc) {
		tc.a(this);
	}

	public SecretKey a(PrivateKey privateKey) {
		return adn.a(privateKey, this.a);
	}

	public byte[] b(PrivateKey privateKey) {
		return privateKey == null ? this.b : adn.b(privateKey, this.b);
	}
}
