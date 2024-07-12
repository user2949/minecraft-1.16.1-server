import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aad extends aab {
	private static final Logger b = LogManager.getLogger();
	private static final boolean c = v.i() == v.b.WINDOWS;
	private static final CharMatcher d = CharMatcher.is('\\');

	public aad(File file) {
		super(file);
	}

	public static boolean a(File file, String string) throws IOException {
		String string3 = file.getCanonicalPath();
		if (c) {
			string3 = d.replaceFrom(string3, '/');
		}

		return string3.endsWith(string);
	}

	@Override
	protected InputStream a(String string) throws IOException {
		File file3 = this.e(string);
		if (file3 == null) {
			throw new aag(this.a, string);
		} else {
			return new FileInputStream(file3);
		}
	}

	@Override
	protected boolean c(String string) {
		return this.e(string) != null;
	}

	@Nullable
	private File e(String string) {
		try {
			File file3 = new File(this.a, string);
			if (file3.isFile() && a(file3, string)) {
				return file3;
			}
		} catch (IOException var3) {
		}

		return null;
	}

	@Override
	public Set<String> a(aaf aaf) {
		Set<String> set3 = Sets.<String>newHashSet();
		File file4 = new File(this.a, aaf.a());
		File[] arr5 = file4.listFiles(DirectoryFileFilter.DIRECTORY);
		if (arr5 != null) {
			for (File file9 : arr5) {
				String string10 = a(file4, file9);
				if (string10.equals(string10.toLowerCase(Locale.ROOT))) {
					set3.add(string10.substring(0, string10.length() - 1));
				} else {
					this.d(string10);
				}
			}
		}

		return set3;
	}

	@Override
	public void close() {
	}

	@Override
	public Collection<uh> a(aaf aaf, String string2, String string3, int integer, Predicate<String> predicate) {
		File file7 = new File(this.a, aaf.a());
		List<uh> list8 = Lists.<uh>newArrayList();
		this.a(new File(new File(file7, string2), string3), integer, string2, list8, string3 + "/", predicate);
		return list8;
	}

	private void a(File file, int integer, String string3, List<uh> list, String string5, Predicate<String> predicate) {
		File[] arr8 = file.listFiles();
		if (arr8 != null) {
			for (File file12 : arr8) {
				if (file12.isDirectory()) {
					if (integer > 0) {
						this.a(file12, integer - 1, string3, list, string5 + file12.getName() + "/", predicate);
					}
				} else if (!file12.getName().endsWith(".mcmeta") && predicate.test(file12.getName())) {
					try {
						list.add(new uh(string3, string5 + file12.getName()));
					} catch (t var13) {
						b.error(var13.getMessage());
					}
				}
			}
		}
	}
}
