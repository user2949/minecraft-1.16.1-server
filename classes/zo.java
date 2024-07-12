import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zo implements zm {
	private static final Logger a = LogManager.getLogger();
	private final int b;
	private int c;
	private long d;
	private long e = Long.MAX_VALUE;

	public zo(int integer) {
		int integer3 = integer * 2 + 1;
		this.b = integer3 * integer3;
	}

	@Override
	public void a(bph bph) {
		this.e = v.b();
		this.d = this.e;
	}

	@Override
	public void a(bph bph, @Nullable chc chc) {
		if (chc == chc.m) {
			this.c++;
		}

		int integer4 = this.c();
		if (v.b() > this.e) {
			this.e += 500L;
			a.info(new ne("menu.preparingSpawn", aec.a(integer4, 0, 100)).getString());
		}
	}

	@Override
	public void b() {
		a.info("Time elapsed: {} ms", v.b() - this.d);
		this.e = Long.MAX_VALUE;
	}

	public int c() {
		return aec.d((float)this.c * 100.0F / (float)this.b);
	}
}
