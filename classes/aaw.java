import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aaw implements abc {
	private static final Logger b = LogManager.getLogger();
	protected final List<aae> a = Lists.<aae>newArrayList();
	private final aaf c;
	private final String d;

	public aaw(aaf aaf, String string) {
		this.c = aaf;
		this.d = string;
	}

	public void a(aae aae) {
		this.a.add(aae);
	}

	@Override
	public abb a(uh uh) throws IOException {
		this.e(uh);
		aae aae3 = null;
		uh uh4 = d(uh);

		for (int integer5 = this.a.size() - 1; integer5 >= 0; integer5--) {
			aae aae6 = (aae)this.a.get(integer5);
			if (aae3 == null && aae6.b(this.c, uh4)) {
				aae3 = aae6;
			}

			if (aae6.b(this.c, uh)) {
				InputStream inputStream7 = null;
				if (aae3 != null) {
					inputStream7 = this.a(uh4, aae3);
				}

				return new abi(aae6.a(), uh, this.a(uh, aae6), inputStream7);
			}
		}

		throw new FileNotFoundException(uh.toString());
	}

	protected InputStream a(uh uh, aae aae) throws IOException {
		InputStream inputStream4 = aae.a(this.c, uh);
		return (InputStream)(b.isDebugEnabled() ? new aaw.a(inputStream4, uh, aae.a()) : inputStream4);
	}

	private void e(uh uh) throws IOException {
		if (!this.f(uh)) {
			throw new IOException("Invalid relative path to resource: " + uh);
		}
	}

	private boolean f(uh uh) {
		return !uh.a().contains("..");
	}

	@Override
	public List<abb> c(uh uh) throws IOException {
		this.e(uh);
		List<abb> list3 = Lists.<abb>newArrayList();
		uh uh4 = d(uh);

		for (aae aae6 : this.a) {
			if (aae6.b(this.c, uh)) {
				InputStream inputStream7 = aae6.b(this.c, uh4) ? this.a(uh4, aae6) : null;
				list3.add(new abi(aae6.a(), uh, this.a(uh, aae6), inputStream7));
			}
		}

		if (list3.isEmpty()) {
			throw new FileNotFoundException(uh.toString());
		} else {
			return list3;
		}
	}

	@Override
	public Collection<uh> a(uh uh, Predicate<String> predicate) {
		return (Collection<uh>)(Objects.equals(uh.b(), this.d) ? this.a(uh.a(), predicate) : ImmutableSet.<uh>of());
	}

	@Override
	public Collection<uh> a(String string, Predicate<String> predicate) {
		List<uh> list4 = Lists.<uh>newArrayList();

		for (aae aae6 : this.a) {
			list4.addAll(aae6.a(this.c, this.d, string, Integer.MAX_VALUE, predicate));
		}

		Collections.sort(list4);
		return list4;
	}

	static uh d(uh uh) {
		return new uh(uh.b(), uh.a() + ".mcmeta");
	}

	static class a extends FilterInputStream {
		private final String a;
		private boolean b;

		public a(InputStream inputStream, uh uh, String string) {
			super(inputStream);
			ByteArrayOutputStream byteArrayOutputStream5 = new ByteArrayOutputStream();
			new Exception().printStackTrace(new PrintStream(byteArrayOutputStream5));
			this.a = "Leaked resource: '" + uh + "' loaded from pack: '" + string + "'\n" + byteArrayOutputStream5;
		}

		public void close() throws IOException {
			super.close();
			this.b = true;
		}

		protected void finalize() throws Throwable {
			if (!this.b) {
				aaw.b.warn(this.a);
			}

			super.finalize();
		}
	}
}
