import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class hk {
	private static final Logger a = LogManager.getLogger();
	private final Collection<Path> b;
	private final Path c;
	private final List<hl> d = Lists.<hl>newArrayList();

	public hk(Path path, Collection<Path> collection) {
		this.c = path;
		this.b = collection;
	}

	public Collection<Path> a() {
		return this.b;
	}

	public Path b() {
		return this.c;
	}

	public void c() throws IOException {
		hm hm2 = new hm(this.c, "cache");
		hm2.c(this.b().resolve("version.json"));
		Stopwatch stopwatch3 = Stopwatch.createStarted();
		Stopwatch stopwatch4 = Stopwatch.createUnstarted();

		for (hl hl6 : this.d) {
			a.info("Starting provider: {}", hl6.a());
			stopwatch4.start();
			hl6.a(hm2);
			stopwatch4.stop();
			a.info("{} finished after {} ms", hl6.a(), stopwatch4.elapsed(TimeUnit.MILLISECONDS));
			stopwatch4.reset();
		}

		a.info("All providers took: {} ms", stopwatch3.elapsed(TimeUnit.MILLISECONDS));
		hm2.a();
	}

	public void a(hl hl) {
		this.d.add(hl);
	}

	static {
		uj.a();
	}
}
