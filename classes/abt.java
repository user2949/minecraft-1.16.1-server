import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class abt<K, V extends abs<K>> {
	protected static final Logger a = LogManager.getLogger();
	private static final Gson b = new GsonBuilder().setPrettyPrinting().create();
	private final File c;
	private final Map<String, V> d = Maps.<String, V>newHashMap();

	public abt(File file) {
		this.c = file;
	}

	public File b() {
		return this.c;
	}

	public void a(V abs) {
		this.d.put(this.a(abs.g()), abs);

		try {
			this.e();
		} catch (IOException var3) {
			a.warn("Could not save the list after adding a user.", (Throwable)var3);
		}
	}

	@Nullable
	public V b(K object) {
		this.g();
		return (V)this.d.get(this.a(object));
	}

	public void c(K object) {
		this.d.remove(this.a(object));

		try {
			this.e();
		} catch (IOException var3) {
			a.warn("Could not save the list after removing a user.", (Throwable)var3);
		}
	}

	public void b(abs<K> abs) {
		this.c(abs.g());
	}

	public String[] a() {
		return (String[])this.d.keySet().toArray(new String[this.d.size()]);
	}

	public boolean c() {
		return this.d.size() < 1;
	}

	protected String a(K object) {
		return object.toString();
	}

	protected boolean d(K object) {
		return this.d.containsKey(this.a(object));
	}

	private void g() {
		List<K> list2 = Lists.<K>newArrayList();

		for (V abs4 : this.d.values()) {
			if (abs4.f()) {
				list2.add(abs4.g());
			}
		}

		for (K object4 : list2) {
			this.d.remove(this.a(object4));
		}
	}

	protected abstract abs<K> a(JsonObject jsonObject);

	public Collection<V> d() {
		return this.d.values();
	}

	public void e() throws IOException {
		JsonArray jsonArray2 = new JsonArray();
		this.d.values().stream().map(abs -> v.a(new JsonObject(), abs::a)).forEach(jsonArray2::add);
		BufferedWriter bufferedWriter3 = Files.newWriter(this.c, StandardCharsets.UTF_8);
		Throwable var3 = null;

		try {
			b.toJson(jsonArray2, bufferedWriter3);
		} catch (Throwable var12) {
			var3 = var12;
			throw var12;
		} finally {
			if (bufferedWriter3 != null) {
				if (var3 != null) {
					try {
						bufferedWriter3.close();
					} catch (Throwable var11) {
						var3.addSuppressed(var11);
					}
				} else {
					bufferedWriter3.close();
				}
			}
		}
	}

	public void f() throws IOException {
		if (this.c.exists()) {
			BufferedReader bufferedReader2 = Files.newReader(this.c, StandardCharsets.UTF_8);
			Throwable var2 = null;

			try {
				JsonArray jsonArray4 = b.fromJson(bufferedReader2, JsonArray.class);
				this.d.clear();

				for (JsonElement jsonElement6 : jsonArray4) {
					JsonObject jsonObject7 = adt.m(jsonElement6, "entry");
					abs<K> abs8 = this.a(jsonObject7);
					if (abs8.g() != null) {
						this.d.put(this.a(abs8.g()), abs8);
					}
				}
			} catch (Throwable var15) {
				var2 = var15;
				throw var15;
			} finally {
				if (bufferedReader2 != null) {
					if (var2 != null) {
						try {
							bufferedReader2.close();
						} catch (Throwable var14) {
							var2.addSuppressed(var14);
						}
					} else {
						bufferedReader2.close();
					}
				}
			}
		}
	}
}
