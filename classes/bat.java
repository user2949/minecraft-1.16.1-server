import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bat {
	private static final Logger a = LogManager.getLogger();
	private final bac b;
	private final bam[] c = new bam[bas.c()];
	private bam d;

	public bat(bac bac) {
		this.b = bac;
		this.a(bas.k);
	}

	public void a(bas<?> bas) {
		if (this.d == null || bas != this.d.i()) {
			if (this.d != null) {
				this.d.e();
			}

			this.d = this.b((bas<bam>)bas);
			if (!this.b.l.v) {
				this.b.Y().b(bac.b, bas.b());
			}

			a.debug("Dragon is now in phase {} on the {}", bas, this.b.l.v ? "client" : "server");
			this.d.d();
		}
	}

	public bam a() {
		return this.d;
	}

	public <T extends bam> T b(bas<T> bas) {
		int integer3 = bas.b();
		if (this.c[integer3] == null) {
			this.c[integer3] = bas.a(this.b);
		}

		return (T)this.c[integer3];
	}
}
