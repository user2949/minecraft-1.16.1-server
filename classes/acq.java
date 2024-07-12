import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class acq extends acv {
	private static final Logger b = LogManager.getLogger();
	private final MinecraftServer c;
	private final File d;
	private final Set<acr<?>> e = Sets.<acr<?>>newHashSet();
	private int f = -300;

	public acq(MinecraftServer minecraftServer, File file) {
		this.c = minecraftServer;
		this.d = file;
		if (file.isFile()) {
			try {
				this.a(minecraftServer.ax(), FileUtils.readFileToString(file));
			} catch (IOException var4) {
				b.error("Couldn't read statistics file {}", file, var4);
			} catch (JsonParseException var5) {
				b.error("Couldn't parse statistics file {}", file, var5);
			}
		}
	}

	public void a() {
		try {
			FileUtils.writeStringToFile(this.d, this.b());
		} catch (IOException var2) {
			b.error("Couldn't save stats", (Throwable)var2);
		}
	}

	@Override
	public void a(bec bec, acr<?> acr, int integer) {
		super.a(bec, acr, integer);
		this.e.add(acr);
	}

	private Set<acr<?>> d() {
		Set<acr<?>> set2 = Sets.<acr<?>>newHashSet(this.e);
		this.e.clear();
		return set2;
	}

	public void a(DataFixer dataFixer, String string) {
		try {
			JsonReader jsonReader4 = new JsonReader(new StringReader(string));
			Throwable var4 = null;

			try {
				jsonReader4.setLenient(false);
				JsonElement jsonElement6 = Streams.parse(jsonReader4);
				if (!jsonElement6.isJsonNull()) {
					le le7 = a(jsonElement6.getAsJsonObject());
					if (!le7.c("DataVersion", 99)) {
						le7.b("DataVersion", 1343);
					}

					le7 = lq.a(dataFixer, aeo.STATS, le7, le7.h("DataVersion"));
					if (le7.c("stats", 10)) {
						le le8 = le7.p("stats");

						for (String string10 : le8.d()) {
							if (le8.c(string10, 10)) {
								v.a(gl.aQ.b(new uh(string10)), act -> {
									le le5 = le8.p(string10);

									for (String string7 : le5.d()) {
										if (le5.c(string7, 99)) {
											v.a(this.a(act, string7), acr -> this.a.put(acr, le5.h(string7)), () -> b.warn("Invalid statistic in {}: Don't know what {} is", this.d, string7));
										} else {
											b.warn("Invalid statistic value in {}: Don't know what {} is for key {}", this.d, le5.c(string7), string7);
										}
									}
								}, () -> b.warn("Invalid statistic type in {}: Don't know what {} is", this.d, string10));
							}
						}
					}

					return;
				}

				b.error("Unable to parse Stat data from {}", this.d);
			} catch (Throwable var19) {
				var4 = var19;
				throw var19;
			} finally {
				if (jsonReader4 != null) {
					if (var4 != null) {
						try {
							jsonReader4.close();
						} catch (Throwable var18) {
							var4.addSuppressed(var18);
						}
					} else {
						jsonReader4.close();
					}
				}
			}
		} catch (IOException | JsonParseException var21) {
			b.error("Unable to parse Stat data from {}", this.d, var21);
		}
	}

	private <T> Optional<acr<T>> a(act<T> act, String string) {
		return Optional.ofNullable(uh.a(string)).flatMap(act.a()::b).map(act::b);
	}

	private static le a(JsonObject jsonObject) {
		le le2 = new le();

		for (Entry<String, JsonElement> entry4 : jsonObject.entrySet()) {
			JsonElement jsonElement5 = (JsonElement)entry4.getValue();
			if (jsonElement5.isJsonObject()) {
				le2.a((String)entry4.getKey(), a(jsonElement5.getAsJsonObject()));
			} else if (jsonElement5.isJsonPrimitive()) {
				JsonPrimitive jsonPrimitive6 = jsonElement5.getAsJsonPrimitive();
				if (jsonPrimitive6.isNumber()) {
					le2.b((String)entry4.getKey(), jsonPrimitive6.getAsInt());
				}
			}
		}

		return le2;
	}

	protected String b() {
		Map<act<?>, JsonObject> map2 = Maps.<act<?>, JsonObject>newHashMap();

		for (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<acr<?>> entry4 : this.a.object2IntEntrySet()) {
			acr<?> acr5 = (acr<?>)entry4.getKey();
			((JsonObject)map2.computeIfAbsent(acr5.a(), act -> new JsonObject())).addProperty(b(acr5).toString(), entry4.getIntValue());
		}

		JsonObject jsonObject3 = new JsonObject();

		for (Entry<act<?>, JsonObject> entry5 : map2.entrySet()) {
			jsonObject3.add(gl.aQ.b((act<?>)entry5.getKey()).toString(), (JsonElement)entry5.getValue());
		}

		JsonObject jsonObject4 = new JsonObject();
		jsonObject4.add("stats", jsonObject3);
		jsonObject4.addProperty("DataVersion", u.a().getWorldVersion());
		return jsonObject4.toString();
	}

	private static <T> uh b(acr<T> acr) {
		return acr.a().a().b(acr.b());
	}

	public void c() {
		this.e.addAll(this.a.keySet());
	}

	public void a(ze ze) {
		int integer3 = this.c.ag();
		Object2IntMap<acr<?>> object2IntMap4 = new Object2IntOpenHashMap<>();
		if (integer3 - this.f > 300) {
			this.f = integer3;

			for (acr<?> acr6 : this.d()) {
				object2IntMap4.put(acr6, this.a(acr6));
			}
		}

		ze.b.a(new ns(object2IntMap4));
	}
}
