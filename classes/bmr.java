import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

public final class bmr implements Predicate<bki> {
	public static final bmr a = new bmr(Stream.empty());
	private final bmr.c[] b;
	private bki[] c;
	private IntList d;

	private bmr(Stream<? extends bmr.c> stream) {
		this.b = (bmr.c[])stream.toArray(bmr.c[]::new);
	}

	private void f() {
		if (this.c == null) {
			this.c = (bki[])Arrays.stream(this.b).flatMap(c -> c.a().stream()).distinct().toArray(bki[]::new);
		}
	}

	public boolean test(@Nullable bki bki) {
		if (bki == null) {
			return false;
		} else {
			this.f();
			if (this.c.length == 0) {
				return bki.a();
			} else {
				for (bki bki6 : this.c) {
					if (bki6.b() == bki.b()) {
						return true;
					}
				}

				return false;
			}
		}
	}

	public IntList b() {
		if (this.d == null) {
			this.f();
			this.d = new IntArrayList(this.c.length);

			for (bki bki5 : this.c) {
				this.d.add(bee.c(bki5));
			}

			this.d.sort(IntComparators.NATURAL_COMPARATOR);
		}

		return this.d;
	}

	public void a(mg mg) {
		this.f();
		mg.d(this.c.length);

		for (int integer3 = 0; integer3 < this.c.length; integer3++) {
			mg.a(this.c[integer3]);
		}
	}

	public JsonElement c() {
		if (this.b.length == 1) {
			return this.b[0].b();
		} else {
			JsonArray jsonArray2 = new JsonArray();

			for (bmr.c c6 : this.b) {
				jsonArray2.add(c6.b());
			}

			return jsonArray2;
		}
	}

	public boolean d() {
		return this.b.length == 0 && (this.c == null || this.c.length == 0) && (this.d == null || this.d.isEmpty());
	}

	private static bmr b(Stream<? extends bmr.c> stream) {
		bmr bmr2 = new bmr(stream);
		return bmr2.b.length == 0 ? a : bmr2;
	}

	public static bmr a(bqa... arr) {
		return a(Arrays.stream(arr).map(bki::new));
	}

	public static bmr a(Stream<bki> stream) {
		return b(stream.filter(bki -> !bki.a()).map(bki -> new bmr.a(bki)));
	}

	public static bmr a(adf<bke> adf) {
		return b(Stream.of(new bmr.b(adf)));
	}

	public static bmr b(mg mg) {
		int integer2 = mg.i();
		return b(Stream.generate(() -> new bmr.a(mg.m())).limit((long)integer2));
	}

	public static bmr a(@Nullable JsonElement jsonElement) {
		if (jsonElement == null || jsonElement.isJsonNull()) {
			throw new JsonSyntaxException("Item cannot be null");
		} else if (jsonElement.isJsonObject()) {
			return b(Stream.of(a(jsonElement.getAsJsonObject())));
		} else if (jsonElement.isJsonArray()) {
			JsonArray jsonArray2 = jsonElement.getAsJsonArray();
			if (jsonArray2.size() == 0) {
				throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
			} else {
				return b(StreamSupport.stream(jsonArray2.spliterator(), false).map(jsonElementx -> a(adt.m(jsonElementx, "item"))));
			}
		} else {
			throw new JsonSyntaxException("Expected item to be object or array of objects");
		}
	}

	private static bmr.c a(JsonObject jsonObject) {
		if (jsonObject.has("item") && jsonObject.has("tag")) {
			throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
		} else if (jsonObject.has("item")) {
			uh uh2 = new uh(adt.h(jsonObject, "item"));
			bke bke3 = (bke)gl.am.b(uh2).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + uh2 + "'"));
			return new bmr.a(new bki(bke3));
		} else if (jsonObject.has("tag")) {
			uh uh2 = new uh(adt.h(jsonObject, "tag"));
			adf<bke> adf3 = adb.e().b().a(uh2);
			if (adf3 == null) {
				throw new JsonSyntaxException("Unknown item tag '" + uh2 + "'");
			} else {
				return new bmr.b(adf3);
			}
		} else {
			throw new JsonParseException("An ingredient entry needs either a tag or an item");
		}
	}

	static class a implements bmr.c {
		private final bki a;

		private a(bki bki) {
			this.a = bki;
		}

		@Override
		public Collection<bki> a() {
			return Collections.singleton(this.a);
		}

		@Override
		public JsonObject b() {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("item", gl.am.b(this.a.b()).toString());
			return jsonObject2;
		}
	}

	static class b implements bmr.c {
		private final adf<bke> a;

		private b(adf<bke> adf) {
			this.a = adf;
		}

		@Override
		public Collection<bki> a() {
			List<bki> list2 = Lists.<bki>newArrayList();

			for (bke bke4 : this.a.b()) {
				list2.add(new bki(bke4));
			}

			return list2;
		}

		@Override
		public JsonObject b() {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("tag", adb.e().b().b(this.a).toString());
			return jsonObject2;
		}
	}

	interface c {
		Collection<bki> a();

		JsonObject b();
	}
}
