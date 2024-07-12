import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Objects;

public interface hl {
	HashFunction a = Hashing.sha1();

	void a(hm hm) throws IOException;

	String a();

	static void a(Gson gson, hm hm, JsonElement jsonElement, Path path) throws IOException {
		String string5 = gson.toJson(jsonElement);
		String string6 = a.hashUnencodedChars(string5).toString();
		if (!Objects.equals(hm.a(path), string6) || !Files.exists(path, new LinkOption[0])) {
			Files.createDirectories(path.getParent());
			BufferedWriter bufferedWriter7 = Files.newBufferedWriter(path);
			Throwable var7 = null;

			try {
				bufferedWriter7.write(string5);
			} catch (Throwable var16) {
				var7 = var16;
				throw var16;
			} finally {
				if (bufferedWriter7 != null) {
					if (var7 != null) {
						try {
							bufferedWriter7.close();
						} catch (Throwable var15) {
							var7.addSuppressed(var15);
						}
					} else {
						bufferedWriter7.close();
					}
				}
			}
		}

		hm.a(path, string6);
	}
}
