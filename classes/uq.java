import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class uq {
	private static final Logger a = LogManager.getLogger();
	private static final Gson b = new GsonBuilder().registerTypeAdapter(y.class, new y.a()).registerTypeAdapter(uh.class, new uh.a()).setPrettyPrinting().create();
	private static final TypeToken<Map<uh, y>> c = new TypeToken<Map<uh, y>>() {
	};
	private final DataFixer d;
	private final abp e;
	private final File f;
	private final Map<w, y> g = Maps.<w, y>newLinkedHashMap();
	private final Set<w> h = Sets.<w>newLinkedHashSet();
	private final Set<w> i = Sets.<w>newLinkedHashSet();
	private final Set<w> j = Sets.<w>newLinkedHashSet();
	private ze k;
	@Nullable
	private w l;
	private boolean m = true;

	public uq(DataFixer dataFixer, abp abp, us us, File file, ze ze) {
		this.d = dataFixer;
		this.e = abp;
		this.f = file;
		this.k = ze;
		this.d(us);
	}

	public void a(ze ze) {
		this.k = ze;
	}

	public void a() {
		for (ad<?> ad3 : aa.a()) {
			ad3.a(this);
		}
	}

	public void a(us us) {
		this.a();
		this.g.clear();
		this.h.clear();
		this.i.clear();
		this.j.clear();
		this.m = true;
		this.l = null;
		this.d(us);
	}

	private void b(us us) {
		for (w w4 : us.a()) {
			this.c(w4);
		}
	}

	private void c() {
		List<w> list2 = Lists.<w>newArrayList();

		for (Entry<w, y> entry4 : this.g.entrySet()) {
			if (((y)entry4.getValue()).a()) {
				list2.add(entry4.getKey());
				this.j.add(entry4.getKey());
			}
		}

		for (w w4 : list2) {
			this.e(w4);
		}
	}

	private void c(us us) {
		for (w w4 : us.a()) {
			if (w4.f().isEmpty()) {
				this.a(w4, "");
				w4.d().a(this.k);
			}
		}
	}

	private void d(us us) {
		if (this.f.isFile()) {
			try {
				JsonReader jsonReader3 = new JsonReader(new StringReader(Files.toString(this.f, StandardCharsets.UTF_8)));
				Throwable var3 = null;

				try {
					jsonReader3.setLenient(false);
					Dynamic<JsonElement> dynamic5 = new Dynamic<>(JsonOps.INSTANCE, Streams.parse(jsonReader3));
					if (!dynamic5.get("DataVersion").asNumber().result().isPresent()) {
						dynamic5 = dynamic5.set("DataVersion", dynamic5.createInt(1343));
					}

					dynamic5 = this.d.update(aeo.ADVANCEMENTS.a(), dynamic5, dynamic5.get("DataVersion").asInt(0), u.a().getWorldVersion());
					dynamic5 = dynamic5.remove("DataVersion");
					Map<uh, y> map6 = b.getAdapter(c).fromJsonTree(dynamic5.getValue());
					if (map6 == null) {
						throw new JsonParseException("Found null for advancements");
					}

					Stream<Entry<uh, y>> stream7 = map6.entrySet().stream().sorted(Comparator.comparing(Entry::getValue));

					for (Entry<uh, y> entry9 : (List)stream7.collect(Collectors.toList())) {
						w w10 = us.a((uh)entry9.getKey());
						if (w10 == null) {
							a.warn("Ignored advancement '{}' in progress file {} - it doesn't exist anymore?", entry9.getKey(), this.f);
						} else {
							this.a(w10, (y)entry9.getValue());
						}
					}
				} catch (Throwable var19) {
					var3 = var19;
					throw var19;
				} finally {
					if (jsonReader3 != null) {
						if (var3 != null) {
							try {
								jsonReader3.close();
							} catch (Throwable var18) {
								var3.addSuppressed(var18);
							}
						} else {
							jsonReader3.close();
						}
					}
				}
			} catch (JsonParseException var21) {
				a.error("Couldn't parse player advancements in {}", this.f, var21);
			} catch (IOException var22) {
				a.error("Couldn't access player advancements in {}", this.f, var22);
			}
		}

		this.c(us);
		this.c();
		this.b(us);
	}

	public void b() {
		Map<uh, y> map2 = Maps.<uh, y>newHashMap();

		for (Entry<w, y> entry4 : this.g.entrySet()) {
			y y5 = (y)entry4.getValue();
			if (y5.b()) {
				map2.put(((w)entry4.getKey()).h(), y5);
			}
		}

		if (this.f.getParentFile() != null) {
			this.f.getParentFile().mkdirs();
		}

		JsonElement jsonElement3 = b.toJsonTree(map2);
		jsonElement3.getAsJsonObject().addProperty("DataVersion", u.a().getWorldVersion());

		try {
			OutputStream outputStream4 = new FileOutputStream(this.f);
			Throwable var38 = null;

			try {
				Writer writer6 = new OutputStreamWriter(outputStream4, Charsets.UTF_8.newEncoder());
				Throwable var6 = null;

				try {
					b.toJson(jsonElement3, writer6);
				} catch (Throwable var31) {
					var6 = var31;
					throw var31;
				} finally {
					if (writer6 != null) {
						if (var6 != null) {
							try {
								writer6.close();
							} catch (Throwable var30) {
								var6.addSuppressed(var30);
							}
						} else {
							writer6.close();
						}
					}
				}
			} catch (Throwable var33) {
				var38 = var33;
				throw var33;
			} finally {
				if (outputStream4 != null) {
					if (var38 != null) {
						try {
							outputStream4.close();
						} catch (Throwable var29) {
							var38.addSuppressed(var29);
						}
					} else {
						outputStream4.close();
					}
				}
			}
		} catch (IOException var35) {
			a.error("Couldn't save player advancements to {}", this.f, var35);
		}
	}

	public boolean a(w w, String string) {
		boolean boolean4 = false;
		y y5 = this.b(w);
		boolean boolean6 = y5.a();
		if (y5.a(string)) {
			this.d(w);
			this.j.add(w);
			boolean4 = true;
			if (!boolean6 && y5.a()) {
				w.d().a(this.k);
				if (w.c() != null && w.c().i() && this.k.l.S().b(bpx.w)) {
					this.e.a(new ne("chat.type.advancement." + w.c().e().a(), this.k.d(), w.j()), mo.SYSTEM, v.b);
				}
			}
		}

		if (y5.a()) {
			this.e(w);
		}

		return boolean4;
	}

	public boolean b(w w, String string) {
		boolean boolean4 = false;
		y y5 = this.b(w);
		if (y5.b(string)) {
			this.c(w);
			this.j.add(w);
			boolean4 = true;
		}

		if (!y5.b()) {
			this.e(w);
		}

		return boolean4;
	}

	private void c(w w) {
		y y3 = this.b(w);
		if (!y3.a()) {
			for (Entry<String, ab> entry5 : w.f().entrySet()) {
				ac ac6 = y3.c((String)entry5.getKey());
				if (ac6 != null && !ac6.a()) {
					ae ae7 = ((ab)entry5.getValue()).a();
					if (ae7 != null) {
						ad<ae> ad8 = aa.a(ae7.a());
						if (ad8 != null) {
							ad8.a(this, new ad.a<>(ae7, w, (String)entry5.getKey()));
						}
					}
				}
			}
		}
	}

	private void d(w w) {
		y y3 = this.b(w);

		for (Entry<String, ab> entry5 : w.f().entrySet()) {
			ac ac6 = y3.c((String)entry5.getKey());
			if (ac6 != null && (ac6.a() || y3.a())) {
				ae ae7 = ((ab)entry5.getValue()).a();
				if (ae7 != null) {
					ad<ae> ad8 = aa.a(ae7.a());
					if (ad8 != null) {
						ad8.b(this, new ad.a<>(ae7, w, (String)entry5.getKey()));
					}
				}
			}
		}
	}

	public void b(ze ze) {
		if (this.m || !this.i.isEmpty() || !this.j.isEmpty()) {
			Map<uh, y> map3 = Maps.<uh, y>newHashMap();
			Set<w> set4 = Sets.<w>newLinkedHashSet();
			Set<uh> set5 = Sets.<uh>newLinkedHashSet();

			for (w w7 : this.j) {
				if (this.h.contains(w7)) {
					map3.put(w7.h(), this.g.get(w7));
				}
			}

			for (w w7x : this.i) {
				if (this.h.contains(w7x)) {
					set4.add(w7x);
				} else {
					set5.add(w7x.h());
				}
			}

			if (this.m || !map3.isEmpty() || !set4.isEmpty() || !set5.isEmpty()) {
				ze.b.a(new qs(this.m, set4, set5, map3));
				this.i.clear();
				this.j.clear();
			}
		}

		this.m = false;
	}

	public void a(@Nullable w w) {
		w w3 = this.l;
		if (w != null && w.b() == null && w.c() != null) {
			this.l = w;
		} else {
			this.l = null;
		}

		if (w3 != this.l) {
			this.k.b.a(new pr(this.l == null ? null : this.l.h()));
		}
	}

	public y b(w w) {
		y y3 = (y)this.g.get(w);
		if (y3 == null) {
			y3 = new y();
			this.a(w, y3);
		}

		return y3;
	}

	private void a(w w, y y) {
		y.a(w.f(), w.i());
		this.g.put(w, y);
	}

	private void e(w w) {
		boolean boolean3 = this.f(w);
		boolean boolean4 = this.h.contains(w);
		if (boolean3 && !boolean4) {
			this.h.add(w);
			this.i.add(w);
			if (this.g.containsKey(w)) {
				this.j.add(w);
			}
		} else if (!boolean3 && boolean4) {
			this.h.remove(w);
			this.i.add(w);
		}

		if (boolean3 != boolean4 && w.b() != null) {
			this.e(w.b());
		}

		for (w w6 : w.e()) {
			this.e(w6);
		}
	}

	private boolean f(w w) {
		for (int integer3 = 0; w != null && integer3 <= 2; integer3++) {
			if (integer3 == 0 && this.g(w)) {
				return true;
			}

			if (w.c() == null) {
				return false;
			}

			y y4 = this.b(w);
			if (y4.a()) {
				return true;
			}

			if (w.c().j()) {
				return false;
			}

			w = w.b();
		}

		return false;
	}

	private boolean g(w w) {
		y y3 = this.b(w);
		if (y3.a()) {
			return true;
		} else {
			for (w w5 : w.e()) {
				if (this.g(w5)) {
					return true;
				}
			}

			return false;
		}
	}
}
