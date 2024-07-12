import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class acd implements Runnable {
	private static final Logger d = LogManager.getLogger();
	private static final AtomicInteger e = new AtomicInteger(0);
	protected volatile boolean a;
	protected final String b;
	protected Thread c;

	protected acd(String string) {
		this.b = string;
	}

	public synchronized void a() {
		this.a = true;
		this.c = new Thread(this, this.b + " #" + e.incrementAndGet());
		this.c.setUncaughtExceptionHandler(new n(d));
		this.c.start();
		d.info("Thread {} started", this.b);
	}

	public synchronized void b() {
		this.a = false;
		if (null != this.c) {
			int integer2 = 0;

			while (this.c.isAlive()) {
				try {
					this.c.join(1000L);
					if (++integer2 >= 5) {
						d.warn("Waited {} seconds attempting force stop!", integer2);
					} else if (this.c.isAlive()) {
						d.warn("Thread {} ({}) failed to exit after {} second(s)", this, this.c.getState(), integer2, new Exception("Stack:"));
						this.c.interrupt();
					}
				} catch (InterruptedException var3) {
				}
			}

			d.info("Thread {} stopped", this.b);
			this.c = null;
		}
	}

	public boolean c() {
		return this.a;
	}
}
