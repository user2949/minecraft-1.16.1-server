import com.google.gson.JsonObject;
import java.io.File;
import java.net.SocketAddress;

public class abm extends abt<String, abn> {
	public abm(File file) {
		super(file);
	}

	@Override
	protected abs<String> a(JsonObject jsonObject) {
		return new abn(jsonObject);
	}

	public boolean a(SocketAddress socketAddress) {
		String string3 = this.c(socketAddress);
		return this.d(string3);
	}

	public boolean a(String string) {
		return this.d(string);
	}

	public abn b(SocketAddress socketAddress) {
		String string3 = this.c(socketAddress);
		return this.b(string3);
	}

	private String c(SocketAddress socketAddress) {
		String string3 = socketAddress.toString();
		if (string3.contains("/")) {
			string3 = string3.substring(string3.indexOf(47) + 1);
		}

		if (string3.contains(":")) {
			string3 = string3.substring(0, string3.indexOf(58));
		}

		return string3;
	}
}
