import java.util.function.IntSupplier;
import java.util.function.LongSupplier;

public class amc {
	private final LongSupplier a;
	private final IntSupplier b;
	private amg c = amf.a;

	public amc(LongSupplier longSupplier, IntSupplier intSupplier) {
		this.a = longSupplier;
		this.b = intSupplier;
	}

	public boolean a() {
		return this.c != amf.a;
	}

	public void b() {
		this.c = amf.a;
	}

	public void c() {
		this.c = new amb(this.a, this.b, true);
	}

	public ami d() {
		return this.c;
	}

	public amh e() {
		return this.c.d();
	}
}
