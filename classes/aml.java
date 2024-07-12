import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.LongSupplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aml {
	private static final Logger a = LogManager.getLogger();
	private final LongSupplier b;
	private final long c;
	private int d;
	private final File e;
	private amg f;

	public ami a() {
		this.f = new amb(this.b, () -> this.d, false);
		this.d++;
		return this.f;
	}

	public void b() {
		if (this.f != amf.a) {
			amh amh2 = this.f.d();
			this.f = amf.a;
			if (amh2.g() >= this.c) {
				File file3 = new File(this.e, "tick-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
				amh2.a(file3);
				a.info("Recorded long tick -- wrote info to: {}", file3.getAbsolutePath());
			}
		}
	}

	@Nullable
	public static aml a(String string) {
		return null;
	}

	public static ami a(ami ami, @Nullable aml aml) {
		return aml != null ? ami.a(aml.a(), ami) : ami;
	}
}
