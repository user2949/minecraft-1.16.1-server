import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class abz {
	private final ByteArrayOutputStream a;
	private final DataOutputStream b;

	public abz(int integer) {
		this.a = new ByteArrayOutputStream(integer);
		this.b = new DataOutputStream(this.a);
	}

	public void a(byte[] arr) throws IOException {
		this.b.write(arr, 0, arr.length);
	}

	public void a(String string) throws IOException {
		this.b.writeBytes(string);
		this.b.write(0);
	}

	public void a(int integer) throws IOException {
		this.b.write(integer);
	}

	public void a(short short1) throws IOException {
		this.b.writeShort(Short.reverseBytes(short1));
	}

	public byte[] a() {
		return this.a.toByteArray();
	}

	public void b() {
		this.a.reset();
	}
}
