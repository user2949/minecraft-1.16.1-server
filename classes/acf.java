import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class acf extends acd {
	private static final Logger d = LogManager.getLogger();
	private boolean e;
	private Socket f;
	private final byte[] g = new byte[1460];
	private final String h;
	private final uv i;

	acf(uv uv, String string, Socket socket) {
		super("RCON Client " + socket.getInetAddress());
		this.i = uv;
		this.f = socket;

		try {
			this.f.setSoTimeout(0);
		} catch (Exception var5) {
			this.a = false;
		}

		this.h = string;
	}

	public void run() {
		try {
			try {
				while (this.a) {
					BufferedInputStream bufferedInputStream2 = new BufferedInputStream(this.f.getInputStream());
					int integer3 = bufferedInputStream2.read(this.g, 0, 1460);
					if (10 > integer3) {
						return;
					}

					int integer4 = 0;
					int integer5 = aca.b(this.g, 0, integer3);
					if (integer5 != integer3 - 4) {
						return;
					}

					integer4 += 4;
					int integer6 = aca.b(this.g, integer4, integer3);
					integer4 += 4;
					int integer7 = aca.a(this.g, integer4);
					integer4 += 4;
					switch (integer7) {
						case 2:
							if (this.e) {
								String string9 = aca.a(this.g, integer4, integer3);

								try {
									this.a(integer6, this.i.a(string9));
								} catch (Exception var16) {
									this.a(integer6, "Error executing: " + string9 + " (" + var16.getMessage() + ")");
								}
								break;
							}

							this.d();
							break;
						case 3:
							String string8 = aca.a(this.g, integer4, integer3);
							integer4 += string8.length();
							if (!string8.isEmpty() && string8.equals(this.h)) {
								this.e = true;
								this.a(integer6, 2, "");
								break;
							}

							this.e = false;
							this.d();
							break;
						default:
							this.a(integer6, String.format("Unknown request %s", Integer.toHexString(integer7)));
					}
				}

				return;
			} catch (SocketTimeoutException var17) {
			} catch (IOException var18) {
			} catch (Exception var19) {
				d.error("Exception whilst parsing RCON input", (Throwable)var19);
			}
		} finally {
			this.e();
			d.info("Thread {} shutting down", this.b);
			this.a = false;
		}
	}

	private void a(int integer1, int integer2, String string) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream5 = new ByteArrayOutputStream(1248);
		DataOutputStream dataOutputStream6 = new DataOutputStream(byteArrayOutputStream5);
		byte[] arr7 = string.getBytes("UTF-8");
		dataOutputStream6.writeInt(Integer.reverseBytes(arr7.length + 10));
		dataOutputStream6.writeInt(Integer.reverseBytes(integer1));
		dataOutputStream6.writeInt(Integer.reverseBytes(integer2));
		dataOutputStream6.write(arr7);
		dataOutputStream6.write(0);
		dataOutputStream6.write(0);
		this.f.getOutputStream().write(byteArrayOutputStream5.toByteArray());
	}

	private void d() throws IOException {
		this.a(-1, 2, "");
	}

	private void a(int integer, String string) throws IOException {
		int integer4 = string.length();

		do {
			int integer5 = 4096 <= integer4 ? 4096 : integer4;
			this.a(integer, 0, string.substring(0, integer5));
			string = string.substring(integer5);
			integer4 = string.length();
		} while (0 != integer4);
	}

	@Override
	public void b() {
		this.a = false;
		this.e();
		super.b();
	}

	private void e() {
		if (null != this.f) {
			try {
				this.f.close();
			} catch (IOException var2) {
				d.warn("Failed to close socket", (Throwable)var2);
			}

			this.f = null;
		}
	}
}
