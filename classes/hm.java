import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class hm {
	private static final Logger a = LogManager.getLogger();
	private final Path b;
	private final Path c;
	private int d;
	private final Map<Path, String> e = Maps.<Path, String>newHashMap();
	private final Map<Path, String> f = Maps.<Path, String>newHashMap();
	private final Set<Path> g = Sets.<Path>newHashSet();

	public hm(Path path, String string) throws IOException {
		this.b = path;
		Path path4 = path.resolve(".cache");
		Files.createDirectories(path4);
		this.c = path4.resolve(string);
		this.c().forEach(pathx -> {
			String var10000 = (String)this.e.put(pathx, "");
		});
		if (Files.isReadable(this.c)) {
			IOUtils.readLines(Files.newInputStream(this.c), Charsets.UTF_8).forEach(stringx -> {
				int integer4 = stringx.indexOf(32);
				this.e.put(path.resolve(stringx.substring(integer4 + 1)), stringx.substring(0, integer4));
			});
		}
	}

	public void a() throws IOException {
		this.b();

		Writer writer2;
		try {
			writer2 = Files.newBufferedWriter(this.c);
		} catch (IOException var3) {
			a.warn("Unable write cachefile {}: {}", this.c, var3.toString());
			return;
		}

		IOUtils.writeLines(
			(Collection<?>)this.f
				.entrySet()
				.stream()
				.map(entry -> (String)entry.getValue() + ' ' + this.b.relativize((Path)entry.getKey()))
				.collect(Collectors.toList()),
			System.lineSeparator(),
			writer2
		);
		writer2.close();
		a.debug("Caching: cache hits: {}, created: {} removed: {}", this.d, this.f.size() - this.d, this.e.size());
	}

	@Nullable
	public String a(Path path) {
		return (String)this.e.get(path);
	}

	public void a(Path path, String string) {
		this.f.put(path, string);
		if (Objects.equals(this.e.remove(path), string)) {
			this.d++;
		}
	}

	public boolean b(Path path) {
		return this.e.containsKey(path);
	}

	public void c(Path path) {
		this.g.add(path);
	}

	private void b() throws IOException {
		this.c().forEach(path -> {
			if (this.b(path) && !this.g.contains(path)) {
				try {
					Files.delete(path);
				} catch (IOException var3) {
					a.debug("Unable to delete: {} ({})", path, var3.toString());
				}
			}
		});
	}

	private Stream<Path> c() throws IOException {
		return Files.walk(this.b).filter(path -> !Objects.equals(this.c, path) && !Files.isDirectory(path, new LinkOption[0]));
	}
}
