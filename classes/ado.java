import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringEscapeUtils;

public class ado {
	private final Writer a;
	private final int b;

	private ado(Writer writer, List<String> list) throws IOException {
		this.a = writer;
		this.b = list.size();
		this.a(list.stream());
	}

	public static ado.a a() {
		return new ado.a();
	}

	public void a(Object... arr) throws IOException {
		if (arr.length != this.b) {
			throw new IllegalArgumentException("Invalid number of columns, expected " + this.b + ", but got " + arr.length);
		} else {
			this.a(Stream.of(arr));
		}
	}

	private void a(Stream<?> stream) throws IOException {
		this.a.write((String)stream.map(ado::a).collect(Collectors.joining(",")) + "\r\n");
	}

	private static String a(@Nullable Object object) {
		return StringEscapeUtils.escapeCsv(object != null ? object.toString() : "[null]");
	}

	public static class a {
		private final List<String> a = Lists.<String>newArrayList();

		public ado.a a(String string) {
			this.a.add(string);
			return this;
		}

		public ado a(Writer writer) throws IOException {
			return new ado(writer, this.a);
		}
	}
}
