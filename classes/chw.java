import com.mojang.datafixers.DataFixer;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class chw implements AutoCloseable {
	private final chx a;
	protected final DataFixer b;
	@Nullable
	private ctj c;

	public chw(File file, DataFixer dataFixer, boolean boolean3) {
		this.b = dataFixer;
		this.a = new chx(file, boolean3, "chunk");
	}

	public le a(ug<bqb> ug, Supplier<daa> supplier, le le) {
		int integer5 = a(le);
		int integer6 = 1493;
		if (integer5 < 1493) {
			le = lq.a(this.b, aeo.CHUNK, le, integer5, 1493);
			if (le.p("Level").q("hasLegacyStructureData")) {
				if (this.c == null) {
					this.c = ctj.a(ug, (daa)supplier.get());
				}

				le = this.c.a(le);
			}
		}

		le = lq.a(this.b, aeo.CHUNK, le, Math.max(1493, integer5));
		if (integer5 < u.a().getWorldVersion()) {
			le.b("DataVersion", u.a().getWorldVersion());
		}

		return le;
	}

	public static int a(le le) {
		return le.c("DataVersion", 99) ? le.h("DataVersion") : -1;
	}

	@Nullable
	public le e(bph bph) throws IOException {
		return this.a.a(bph);
	}

	public void a(bph bph, le le) {
		this.a.a(bph, le);
		if (this.c != null) {
			this.c.a(bph.a());
		}
	}

	public void i() {
		this.a.a().join();
	}

	public void close() throws IOException {
		this.a.close();
	}
}
