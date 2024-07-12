import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import javax.annotation.Nullable;

public class cic {
	private static final Int2ObjectMap<cic> d = new Int2ObjectOpenHashMap<>();
	public static final cic a = a(new cic(1, GZIPInputStream::new, GZIPOutputStream::new));
	public static final cic b = a(new cic(2, InflaterInputStream::new, DeflaterOutputStream::new));
	public static final cic c = a(new cic(3, inputStream -> inputStream, outputStream -> outputStream));
	private final int e;
	private final cic.a<InputStream> f;
	private final cic.a<OutputStream> g;

	private cic(int integer, cic.a<InputStream> a2, cic.a<OutputStream> a3) {
		this.e = integer;
		this.f = a2;
		this.g = a3;
	}

	private static cic a(cic cic) {
		d.put(cic.e, cic);
		return cic;
	}

	@Nullable
	public static cic a(int integer) {
		return d.get(integer);
	}

	public static boolean b(int integer) {
		return d.containsKey(integer);
	}

	public int a() {
		return this.e;
	}

	public OutputStream a(OutputStream outputStream) throws IOException {
		return this.g.wrap(outputStream);
	}

	public InputStream a(InputStream inputStream) throws IOException {
		return this.f.wrap(inputStream);
	}

	@FunctionalInterface
	interface a<O> {
		O wrap(O object) throws IOException;
	}
}
