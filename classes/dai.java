import com.mojang.datafixers.DataFixer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dai {
	private static final Logger b = LogManager.getLogger();
	private final File c;
	protected final DataFixer a;

	public dai(dae.a a, DataFixer dataFixer) {
		this.a = dataFixer;
		this.c = a.a(dac.c).toFile();
		this.c.mkdirs();
	}

	public void a(bec bec) {
		try {
			le le3 = bec.e(new le());
			File file4 = File.createTempFile(bec.bS() + "-", ".dat", this.c);
			lo.a(le3, new FileOutputStream(file4));
			File file5 = new File(this.c, bec.bS() + ".dat");
			File file6 = new File(this.c, bec.bS() + ".dat_old");
			v.a(file5, file4, file6);
		} catch (Exception var6) {
			b.warn("Failed to save player data for {}", bec.P().getString());
		}
	}

	@Nullable
	public le b(bec bec) {
		le le3 = null;

		try {
			File file4 = new File(this.c, bec.bS() + ".dat");
			if (file4.exists() && file4.isFile()) {
				le3 = lo.a(new FileInputStream(file4));
			}
		} catch (Exception var4) {
			b.warn("Failed to load player data for {}", bec.P().getString());
		}

		if (le3 != null) {
			int integer4 = le3.c("DataVersion", 3) ? le3.h("DataVersion") : -1;
			bec.f(lq.a(this.a, aeo.PLAYER, le3, integer4));
		}

		return le3;
	}

	public String[] a() {
		String[] arr2 = this.c.list();
		if (arr2 == null) {
			arr2 = new String[0];
		}

		for (int integer3 = 0; integer3 < arr2.length; integer3++) {
			if (arr2[integer3].endsWith(".dat")) {
				arr2[integer3] = arr2[integer3].substring(0, arr2[integer3].length() - 4);
			}
		}

		return arr2;
	}
}
