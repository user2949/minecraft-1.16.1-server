import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class abe extends abf<Map<uh, JsonElement>> {
	private static final Logger a = LogManager.getLogger();
	private static final int b = ".json".length();
	private final Gson c;
	private final String d;

	public abe(Gson gson, String string) {
		this.c = gson;
		this.d = string;
	}

	protected Map<uh, JsonElement> b(abc abc, ami ami) {
		Map<uh, JsonElement> map4 = Maps.<uh, JsonElement>newHashMap();
		int integer5 = this.d.length() + 1;

		for (uh uh7 : abc.a(this.d, string -> string.endsWith(".json"))) {
			String string8 = uh7.a();
			uh uh9 = new uh(uh7.b(), string8.substring(integer5, string8.length() - b));

			try {
				abb abb10 = abc.a(uh7);
				Throwable var10 = null;

				try {
					InputStream inputStream12 = abb10.b();
					Throwable var12 = null;

					try {
						Reader reader14 = new BufferedReader(new InputStreamReader(inputStream12, StandardCharsets.UTF_8));
						Throwable var14 = null;

						try {
							JsonElement jsonElement16 = adt.a(this.c, reader14, JsonElement.class);
							if (jsonElement16 != null) {
								JsonElement jsonElement17 = (JsonElement)map4.put(uh9, jsonElement16);
								if (jsonElement17 != null) {
									throw new IllegalStateException("Duplicate data file ignored with ID " + uh9);
								}
							} else {
								a.error("Couldn't load data file {} from {} as it's null or empty", uh9, uh7);
							}
						} catch (Throwable var62) {
							var14 = var62;
							throw var62;
						} finally {
							if (reader14 != null) {
								if (var14 != null) {
									try {
										reader14.close();
									} catch (Throwable var61) {
										var14.addSuppressed(var61);
									}
								} else {
									reader14.close();
								}
							}
						}
					} catch (Throwable var64) {
						var12 = var64;
						throw var64;
					} finally {
						if (inputStream12 != null) {
							if (var12 != null) {
								try {
									inputStream12.close();
								} catch (Throwable var60) {
									var12.addSuppressed(var60);
								}
							} else {
								inputStream12.close();
							}
						}
					}
				} catch (Throwable var66) {
					var10 = var66;
					throw var66;
				} finally {
					if (abb10 != null) {
						if (var10 != null) {
							try {
								abb10.close();
							} catch (Throwable var59) {
								var10.addSuppressed(var59);
							}
						} else {
							abb10.close();
						}
					}
				}
			} catch (IllegalArgumentException | IOException | JsonParseException var68) {
				a.error("Couldn't parse data file {} from {}", uh9, uh7, var68);
			}
		}

		return map4;
	}
}
