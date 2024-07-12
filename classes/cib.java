import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;

public final class cib implements AutoCloseable {
	private final Long2ObjectLinkedOpenHashMap<cia> a = new Long2ObjectLinkedOpenHashMap<>();
	private final File b;
	private final boolean c;

	cib(File file, boolean boolean2) {
		this.b = file;
		this.c = boolean2;
	}

	private cia b(bph bph) throws IOException {
		long long3 = bph.a(bph.h(), bph.i());
		cia cia5 = this.a.getAndMoveToFirst(long3);
		if (cia5 != null) {
			return cia5;
		} else {
			if (this.a.size() >= 256) {
				this.a.removeLast().close();
			}

			if (!this.b.exists()) {
				this.b.mkdirs();
			}

			File file6 = new File(this.b, "r." + bph.h() + "." + bph.i() + ".mca");
			cia cia7 = new cia(file6, this.b, this.c);
			this.a.putAndMoveToFirst(long3, cia7);
			return cia7;
		}
	}

	@Nullable
	public le a(bph bph) throws IOException {
		cia cia3 = this.b(bph);
		DataInputStream dataInputStream4 = cia3.a(bph);
		Throwable var4 = null;

		Object var5;
		try {
			if (dataInputStream4 != null) {
				return lo.a(dataInputStream4);
			}

			var5 = null;
		} catch (Throwable var15) {
			var4 = var15;
			throw var15;
		} finally {
			if (dataInputStream4 != null) {
				if (var4 != null) {
					try {
						dataInputStream4.close();
					} catch (Throwable var14) {
						var4.addSuppressed(var14);
					}
				} else {
					dataInputStream4.close();
				}
			}
		}

		return (le)var5;
	}

	protected void a(bph bph, le le) throws IOException {
		cia cia4 = this.b(bph);
		DataOutputStream dataOutputStream5 = cia4.c(bph);
		Throwable var5 = null;

		try {
			lo.a(le, dataOutputStream5);
		} catch (Throwable var14) {
			var5 = var14;
			throw var14;
		} finally {
			if (dataOutputStream5 != null) {
				if (var5 != null) {
					try {
						dataOutputStream5.close();
					} catch (Throwable var13) {
						var5.addSuppressed(var13);
					}
				} else {
					dataOutputStream5.close();
				}
			}
		}
	}

	public void close() throws IOException {
		adq<IOException> adq2 = new adq();

		for (cia cia4 : this.a.values()) {
			try {
				cia4.close();
			} catch (IOException var5) {
				adq2.a(var5);
			}
		}

		adq2.a();
	}

	public void a() throws IOException {
		for (cia cia3 : this.a.values()) {
			cia3.a();
		}
	}
}
