import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.io.IOUtils;

public class aac extends aab {
	public static final Splitter b = Splitter.on('/').omitEmptyStrings().limit(3);
	private ZipFile c;

	public aac(File file) {
		super(file);
	}

	private ZipFile b() throws IOException {
		if (this.c == null) {
			this.c = new ZipFile(this.a);
		}

		return this.c;
	}

	@Override
	protected InputStream a(String string) throws IOException {
		ZipFile zipFile3 = this.b();
		ZipEntry zipEntry4 = zipFile3.getEntry(string);
		if (zipEntry4 == null) {
			throw new aag(this.a, string);
		} else {
			return zipFile3.getInputStream(zipEntry4);
		}
	}

	@Override
	public boolean c(String string) {
		try {
			return this.b().getEntry(string) != null;
		} catch (IOException var3) {
			return false;
		}
	}

	@Override
	public Set<String> a(aaf aaf) {
		ZipFile zipFile3;
		try {
			zipFile3 = this.b();
		} catch (IOException var9) {
			return Collections.emptySet();
		}

		Enumeration<? extends ZipEntry> enumeration4 = zipFile3.entries();
		Set<String> set5 = Sets.<String>newHashSet();

		while (enumeration4.hasMoreElements()) {
			ZipEntry zipEntry6 = (ZipEntry)enumeration4.nextElement();
			String string7 = zipEntry6.getName();
			if (string7.startsWith(aaf.a() + "/")) {
				List<String> list8 = Lists.<String>newArrayList(b.split(string7));
				if (list8.size() > 1) {
					String string9 = (String)list8.get(1);
					if (string9.equals(string9.toLowerCase(Locale.ROOT))) {
						set5.add(string9);
					} else {
						this.d(string9);
					}
				}
			}
		}

		return set5;
	}

	protected void finalize() throws Throwable {
		this.close();
		super.finalize();
	}

	@Override
	public void close() {
		if (this.c != null) {
			IOUtils.closeQuietly(this.c);
			this.c = null;
		}
	}

	@Override
	public Collection<uh> a(aaf aaf, String string2, String string3, int integer, Predicate<String> predicate) {
		ZipFile zipFile7;
		try {
			zipFile7 = this.b();
		} catch (IOException var15) {
			return Collections.emptySet();
		}

		Enumeration<? extends ZipEntry> enumeration8 = zipFile7.entries();
		List<uh> list9 = Lists.<uh>newArrayList();
		String string10 = aaf.a() + "/" + string2 + "/";
		String string11 = string10 + string3 + "/";

		while (enumeration8.hasMoreElements()) {
			ZipEntry zipEntry12 = (ZipEntry)enumeration8.nextElement();
			if (!zipEntry12.isDirectory()) {
				String string13 = zipEntry12.getName();
				if (!string13.endsWith(".mcmeta") && string13.startsWith(string11)) {
					String string14 = string13.substring(string10.length());
					String[] arr15 = string14.split("/");
					if (arr15.length >= integer + 1 && predicate.test(arr15[arr15.length - 1])) {
						list9.add(new uh(string2, string14));
					}
				}
			}
		}

		return list9;
	}
}
