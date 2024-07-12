import com.google.common.collect.Maps;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ace extends acd {
	private static final Logger d = LogManager.getLogger();
	private long e;
	private final int f;
	private final int g;
	private final int h;
	private final String i;
	private final String j;
	private DatagramSocket k;
	private final byte[] l = new byte[1460];
	private String m;
	private String n;
	private final Map<SocketAddress, ace.a> o;
	private final abz p;
	private long q;
	private final uv r;

	public ace(uv uv) {
		super("Query Listener");
		this.r = uv;
		this.f = uv.g_().s;
		this.n = uv.h_();
		this.g = uv.o();
		this.i = uv.i_();
		this.h = uv.I();
		this.j = uv.k_();
		this.q = 0L;
		this.m = "0.0.0.0";
		if (!this.n.isEmpty() && !this.m.equals(this.n)) {
			this.m = this.n;
		} else {
			this.n = "0.0.0.0";

			try {
				InetAddress inetAddress3 = InetAddress.getLocalHost();
				this.m = inetAddress3.getHostAddress();
			} catch (UnknownHostException var3) {
				d.warn("Unable to determine local host IP, please set server-ip in server.properties", (Throwable)var3);
			}
		}

		this.p = new abz(1460);
		this.o = Maps.<SocketAddress, ace.a>newHashMap();
	}

	private void a(byte[] arr, DatagramPacket datagramPacket) throws IOException {
		this.k.send(new DatagramPacket(arr, arr.length, datagramPacket.getSocketAddress()));
	}

	private boolean a(DatagramPacket datagramPacket) throws IOException {
		byte[] arr3 = datagramPacket.getData();
		int integer4 = datagramPacket.getLength();
		SocketAddress socketAddress5 = datagramPacket.getSocketAddress();
		d.debug("Packet len {} [{}]", integer4, socketAddress5);
		if (3 <= integer4 && -2 == arr3[0] && -3 == arr3[1]) {
			d.debug("Packet '{}' [{}]", aca.a(arr3[2]), socketAddress5);
			switch (arr3[2]) {
				case 0:
					if (!this.c(datagramPacket)) {
						d.debug("Invalid challenge [{}]", socketAddress5);
						return false;
					} else if (15 == integer4) {
						this.a(this.b(datagramPacket), datagramPacket);
						d.debug("Rules [{}]", socketAddress5);
					} else {
						abz abz6 = new abz(1460);
						abz6.a(0);
						abz6.a(this.a(datagramPacket.getSocketAddress()));
						abz6.a(this.i);
						abz6.a("SMP");
						abz6.a(this.j);
						abz6.a(Integer.toString(this.r.H()));
						abz6.a(Integer.toString(this.h));
						abz6.a((short)this.g);
						abz6.a(this.m);
						this.a(abz6.a(), datagramPacket);
						d.debug("Status [{}]", socketAddress5);
					}
				default:
					return true;
				case 9:
					this.d(datagramPacket);
					d.debug("Challenge [{}]", socketAddress5);
					return true;
			}
		} else {
			d.debug("Invalid packet [{}]", socketAddress5);
			return false;
		}
	}

	private byte[] b(DatagramPacket datagramPacket) throws IOException {
		long long3 = v.b();
		if (long3 < this.q + 5000L) {
			byte[] arr5 = this.p.a();
			byte[] arr6 = this.a(datagramPacket.getSocketAddress());
			arr5[1] = arr6[0];
			arr5[2] = arr6[1];
			arr5[3] = arr6[2];
			arr5[4] = arr6[3];
			return arr5;
		} else {
			this.q = long3;
			this.p.b();
			this.p.a(0);
			this.p.a(this.a(datagramPacket.getSocketAddress()));
			this.p.a("splitnum");
			this.p.a(128);
			this.p.a(0);
			this.p.a("hostname");
			this.p.a(this.i);
			this.p.a("gametype");
			this.p.a("SMP");
			this.p.a("game_id");
			this.p.a("MINECRAFT");
			this.p.a("version");
			this.p.a(this.r.G());
			this.p.a("plugins");
			this.p.a(this.r.j_());
			this.p.a("map");
			this.p.a(this.j);
			this.p.a("numplayers");
			this.p.a("" + this.r.H());
			this.p.a("maxplayers");
			this.p.a("" + this.h);
			this.p.a("hostport");
			this.p.a("" + this.g);
			this.p.a("hostip");
			this.p.a(this.m);
			this.p.a(0);
			this.p.a(1);
			this.p.a("player_");
			this.p.a(0);
			String[] arr5 = this.r.J();

			for (String string9 : arr5) {
				this.p.a(string9);
			}

			this.p.a(0);
			return this.p.a();
		}
	}

	private byte[] a(SocketAddress socketAddress) {
		return ((ace.a)this.o.get(socketAddress)).c();
	}

	private Boolean c(DatagramPacket datagramPacket) {
		SocketAddress socketAddress3 = datagramPacket.getSocketAddress();
		if (!this.o.containsKey(socketAddress3)) {
			return false;
		} else {
			byte[] arr4 = datagramPacket.getData();
			return ((ace.a)this.o.get(socketAddress3)).a() == aca.c(arr4, 7, datagramPacket.getLength());
		}
	}

	private void d(DatagramPacket datagramPacket) throws IOException {
		ace.a a3 = new ace.a(datagramPacket);
		this.o.put(datagramPacket.getSocketAddress(), a3);
		this.a(a3.b(), datagramPacket);
	}

	private void d() {
		if (this.a) {
			long long2 = v.b();
			if (long2 >= this.e + 30000L) {
				this.e = long2;
				this.o.values().removeIf(a -> a.a(long2));
			}
		}
	}

	public void run() {
		d.info("Query running on {}:{}", this.n, this.f);
		this.e = v.b();
		DatagramPacket datagramPacket2 = new DatagramPacket(this.l, this.l.length);

		try {
			while (this.a) {
				try {
					this.k.receive(datagramPacket2);
					this.d();
					this.a(datagramPacket2);
				} catch (SocketTimeoutException var8) {
					this.d();
				} catch (PortUnreachableException var9) {
				} catch (IOException var10) {
					this.a(var10);
				}
			}
		} finally {
			d.debug("closeSocket: {}:{}", this.n, this.f);
			this.k.close();
		}
	}

	@Override
	public void a() {
		if (!this.a) {
			if (0 < this.f && 65535 >= this.f) {
				if (this.e()) {
					super.a();
				}
			} else {
				d.warn("Invalid query port {} found in server.properties (queries disabled)", this.f);
			}
		}
	}

	private void a(Exception exception) {
		if (this.a) {
			d.warn("Unexpected exception", (Throwable)exception);
			if (!this.e()) {
				d.error("Failed to recover from exception, shutting down!");
				this.a = false;
			}
		}
	}

	private boolean e() {
		try {
			this.k = new DatagramSocket(this.f, InetAddress.getByName(this.n));
			this.k.setSoTimeout(500);
			return true;
		} catch (Exception var2) {
			d.warn("Unable to initialise query system on {}:{}", this.n, this.f, var2);
			return false;
		}
	}

	static class a {
		private final long a = new Date().getTime();
		private final int b;
		private final byte[] c;
		private final byte[] d;
		private final String e;

		public a(DatagramPacket datagramPacket) {
			byte[] arr3 = datagramPacket.getData();
			this.c = new byte[4];
			this.c[0] = arr3[3];
			this.c[1] = arr3[4];
			this.c[2] = arr3[5];
			this.c[3] = arr3[6];
			this.e = new String(this.c, StandardCharsets.UTF_8);
			this.b = new Random().nextInt(16777216);
			this.d = String.format("\t%s%d\u0000", this.e, this.b).getBytes(StandardCharsets.UTF_8);
		}

		public Boolean a(long long1) {
			return this.a < long1;
		}

		public int a() {
			return this.b;
		}

		public byte[] b() {
			return this.d;
		}

		public byte[] c() {
			return this.c;
		}
	}
}
