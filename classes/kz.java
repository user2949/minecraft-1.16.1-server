import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class kz {
	private static final Logger a = LogManager.getLogger();
	private static final Gson b = new Gson();
	private static final Pattern c = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
	private static volatile kz d = c();

	private static kz c() {
		Builder<String, String> builder1 = ImmutableMap.builder();
		BiConsumer<String, String> biConsumer2 = builder1::put;

		try {
			InputStream inputStream3 = kz.class.getResourceAsStream("/assets/minecraft/lang/en_us.json");
			Throwable var3 = null;

			try {
				a(inputStream3, biConsumer2);
			} catch (Throwable var13) {
				var3 = var13;
				throw var13;
			} finally {
				if (inputStream3 != null) {
					if (var3 != null) {
						try {
							inputStream3.close();
						} catch (Throwable var12) {
							var3.addSuppressed(var12);
						}
					} else {
						inputStream3.close();
					}
				}
			}
		} catch (JsonParseException | IOException var15) {
			a.error("Couldn't read strings from /assets/minecraft/lang/en_us.json", (Throwable)var15);
		}

		final Map<String, String> map3 = builder1.build();
		return new kz() {
			@Override
			public String a(String string) {
				return (String)map3.getOrDefault(string, string);
			}

			@Override
			public boolean b(String string) {
				return map3.containsKey(string);
			}

			@Override
			public String a(String string, boolean boolean2) {
				return string;
			}
		};
	}

	public static void a(InputStream inputStream, BiConsumer<String, String> biConsumer) {
		JsonObject jsonObject3 = b.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), JsonObject.class);

		for (Entry<String, JsonElement> entry5 : jsonObject3.entrySet()) {
			String string6 = c.matcher(adt.a((JsonElement)entry5.getValue(), (String)entry5.getKey())).replaceAll("%$1s");
			biConsumer.accept(entry5.getKey(), string6);
		}
	}

	public static kz a() {
		return d;
	}

	public abstract String a(String string);

	public abstract boolean b(String string);

	public abstract String a(String string, boolean boolean2);
}
