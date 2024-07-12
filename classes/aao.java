import java.io.File;
import java.io.FileFilter;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class aao implements aat {
	private static final FileFilter a = file -> {
		boolean boolean2 = file.isFile() && file.getName().endsWith(".zip");
		boolean boolean3 = file.isDirectory() && new File(file, "pack.mcmeta").isFile();
		return boolean2 || boolean3;
	};
	private final File b;
	private final aas c;

	public aao(File file, aas aas) {
		this.b = file;
		this.c = aas;
	}

	@Override
	public <T extends aap> void a(Consumer<T> consumer, aap.a<T> a) {
		if (!this.b.isDirectory()) {
			this.b.mkdirs();
		}

		File[] arr4 = this.b.listFiles(aao.a);
		if (arr4 != null) {
			for (File file8 : arr4) {
				String string9 = "file/" + file8.getName();
				T aap10 = aap.a(string9, false, this.a(file8), a, aap.b.TOP, this.c);
				if (aap10 != null) {
					consumer.accept(aap10);
				}
			}
		}
	}

	private Supplier<aae> a(File file) {
		return file.isDirectory() ? () -> new aad(file) : () -> new aac(file);
	}
}
