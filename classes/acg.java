import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class acg extends acd {
	private static final Logger d = LogManager.getLogger();
	private final int e;
	private String f;
	private ServerSocket g;
	private final String h;
	private final List<acf> i = Lists.<acf>newArrayList();
	private final uv j;

	public acg(uv uv) {
		super("RCON Listener");
		this.j = uv;
		ye ye3 = uv.g_();
		this.e = ye3.u;
		this.h = ye3.v;
		this.f = uv.h_();
		if (this.f.isEmpty()) {
			this.f = "0.0.0.0";
		}
	}

	private void d() {
		this.i.removeIf(acf -> !acf.c());
	}

	public void run() {
		d.info("RCON running on {}:{}", this.f, this.e);

		try {
			while (this.a) {
				try {
					Socket socket2 = this.g.accept();
					acf acf3 = new acf(this.j, this.h, socket2);
					acf3.a();
					this.i.add(acf3);
					this.d();
				} catch (SocketTimeoutException var7) {
					this.d();
				} catch (IOException var8) {
					if (this.a) {
						d.info("IO exception: ", (Throwable)var8);
					}
				}
			}
		} finally {
			this.a(this.g);
		}
	}

	@Override
	public void a() {
		if (this.h.isEmpty()) {
			d.warn("No rcon password set in server.properties, rcon disabled!");
		} else if (0 >= this.e || 65535 < this.e) {
			d.warn("Invalid rcon port {} found in server.properties, rcon disabled!", this.e);
		} else if (!this.a) {
			try {
				this.g = new ServerSocket(this.e, 0, InetAddress.getByName(this.f));
				this.g.setSoTimeout(500);
				super.a();
			} catch (IOException var2) {
				d.warn("Unable to initialise rcon on {}:{}", this.f, this.e, var2);
			}
		}
	}

	@Override
	public void b() {
		this.a = false;
		this.a(this.g);
		super.b();

		for (acf acf3 : this.i) {
			if (acf3.c()) {
				acf3.b();
			}
		}

		this.i.clear();
	}

	private void a(ServerSocket serverSocket) {
		d.debug("closeSocket: {}", serverSocket);

		try {
			serverSocket.close();
		} catch (IOException var3) {
			d.warn("Failed to close socket", (Throwable)var3);
		}
	}
}
