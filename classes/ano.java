import com.google.common.collect.Maps;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;

public class ano {
	private final Map<String, Object> a = Maps.<String, Object>newHashMap();
	private final Map<String, Object> b = Maps.<String, Object>newHashMap();
	private final String c = UUID.randomUUID().toString();
	private final URL d;
	private final anp e;
	private final Timer f = new Timer("Snooper Timer", true);
	private final Object g = new Object();
	private final long h;
	private boolean i;

	public ano(String string, anp anp, long long3) {
		try {
			this.d = new URL("http://snoop.minecraft.net/" + string + "?version=" + 2);
		} catch (MalformedURLException var6) {
			throw new IllegalArgumentException();
		}

		this.e = anp;
		this.h = long3;
	}

	public void a() {
		if (!this.i) {
		}
	}

	public void b() {
		this.b("memory_total", Runtime.getRuntime().totalMemory());
		this.b("memory_max", Runtime.getRuntime().maxMemory());
		this.b("memory_free", Runtime.getRuntime().freeMemory());
		this.b("cpu_cores", Runtime.getRuntime().availableProcessors());
		this.e.a(this);
	}

	public void a(String string, Object object) {
		synchronized (this.g) {
			this.b.put(string, object);
		}
	}

	public void b(String string, Object object) {
		synchronized (this.g) {
			this.a.put(string, object);
		}
	}

	public boolean d() {
		return this.i;
	}

	public void e() {
		this.f.cancel();
	}

	public long g() {
		return this.h;
	}
}
