import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class daw {
	private static final Logger c = LogManager.getLogger();
	public static final daw a = new daw(dcz.a, new dav[0], new dch[0]);
	public static final dcy b = dcz.k;
	private final dcy d;
	private final dav[] e;
	private final dch[] f;
	private final BiFunction<bki, dat, bki> g;

	private daw(dcy dcy, dav[] arr, dch[] arr) {
		this.d = dcy;
		this.e = arr;
		this.f = arr;
		this.g = dcj.a(arr);
	}

	public static Consumer<bki> a(Consumer<bki> consumer) {
		return bki -> {
			if (bki.E() < bki.c()) {
				consumer.accept(bki);
			} else {
				int integer3 = bki.E();

				while (integer3 > 0) {
					bki bki4 = bki.i();
					bki4.e(Math.min(bki.c(), integer3));
					integer3 -= bki4.E();
					consumer.accept(bki4);
				}
			}
		};
	}

	public void a(dat dat, Consumer<bki> consumer) {
		if (dat.a(this)) {
			Consumer<bki> consumer4 = dch.a(this.g, consumer, dat);

			for (dav dav8 : this.e) {
				dav8.a(consumer4, dat);
			}

			dat.b(this);
		} else {
			c.warn("Detected infinite loop in loot tables");
		}
	}

	public void b(dat dat, Consumer<bki> consumer) {
		this.a(dat, a(consumer));
	}

	public List<bki> a(dat dat) {
		List<bki> list3 = Lists.<bki>newArrayList();
		this.b(dat, list3::add);
		return list3;
	}

	public dcy a() {
		return this.d;
	}

	public void a(dbe dbe) {
		for (int integer3 = 0; integer3 < this.e.length; integer3++) {
			this.e[integer3].a(dbe.b(".pools[" + integer3 + "]"));
		}

		for (int integer3 = 0; integer3 < this.f.length; integer3++) {
			this.f[integer3].a(dbe.b(".functions[" + integer3 + "]"));
		}
	}

	public void a(amz amz, dat dat) {
		List<bki> list4 = this.a(dat);
		Random random5 = dat.a();
		List<Integer> list6 = this.a(amz, random5);
		this.a(list4, list6.size(), random5);

		for (bki bki8 : list4) {
			if (list6.isEmpty()) {
				c.warn("Tried to over-fill a container");
				return;
			}

			if (bki8.a()) {
				amz.a((Integer)list6.remove(list6.size() - 1), bki.b);
			} else {
				amz.a((Integer)list6.remove(list6.size() - 1), bki8);
			}
		}
	}

	private void a(List<bki> list, int integer, Random random) {
		List<bki> list5 = Lists.<bki>newArrayList();
		Iterator<bki> iterator6 = list.iterator();

		while (iterator6.hasNext()) {
			bki bki7 = (bki)iterator6.next();
			if (bki7.a()) {
				iterator6.remove();
			} else if (bki7.E() > 1) {
				list5.add(bki7);
				iterator6.remove();
			}
		}

		while (integer - list.size() - list5.size() > 0 && !list5.isEmpty()) {
			bki bki6 = (bki)list5.remove(aec.a(random, 0, list5.size() - 1));
			int integer7 = aec.a(random, 1, bki6.E() / 2);
			bki bki8 = bki6.a(integer7);
			if (bki6.E() > 1 && random.nextBoolean()) {
				list5.add(bki6);
			} else {
				list.add(bki6);
			}

			if (bki8.E() > 1 && random.nextBoolean()) {
				list5.add(bki8);
			} else {
				list.add(bki8);
			}
		}

		list.addAll(list5);
		Collections.shuffle(list, random);
	}

	private List<Integer> a(amz amz, Random random) {
		List<Integer> list4 = Lists.<Integer>newArrayList();

		for (int integer5 = 0; integer5 < amz.ab_(); integer5++) {
			if (amz.a(integer5).a()) {
				list4.add(integer5);
			}
		}

		Collections.shuffle(list4, random);
		return list4;
	}

	public static daw.a b() {
		return new daw.a();
	}

	public static class a implements dce<daw.a> {
		private final List<dav> a = Lists.<dav>newArrayList();
		private final List<dch> b = Lists.<dch>newArrayList();
		private dcy c = daw.b;

		public daw.a a(dav.a a) {
			this.a.add(a.b());
			return this;
		}

		public daw.a a(dcy dcy) {
			this.c = dcy;
			return this;
		}

		public daw.a b(dch.a a) {
			this.b.add(a.b());
			return this;
		}

		public daw.a c() {
			return this;
		}

		public daw b() {
			return new daw(this.c, (dav[])this.a.toArray(new dav[0]), (dch[])this.b.toArray(new dch[0]));
		}
	}

	public static class b implements JsonDeserializer<daw>, JsonSerializer<daw> {
		public daw deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			JsonObject jsonObject5 = adt.m(jsonElement, "loot table");
			dav[] arr6 = adt.a(jsonObject5, "pools", new dav[0], jsonDeserializationContext, dav[].class);
			dcy dcy7 = null;
			if (jsonObject5.has("type")) {
				String string8 = adt.h(jsonObject5, "type");
				dcy7 = dcz.a(new uh(string8));
			}

			dch[] arr8 = adt.a(jsonObject5, "functions", new dch[0], jsonDeserializationContext, dch[].class);
			return new daw(dcy7 != null ? dcy7 : dcz.k, arr6, arr8);
		}

		public JsonElement serialize(daw daw, Type type, JsonSerializationContext jsonSerializationContext) {
			JsonObject jsonObject5 = new JsonObject();
			if (daw.d != daw.b) {
				uh uh6 = dcz.a(daw.d);
				if (uh6 != null) {
					jsonObject5.addProperty("type", uh6.toString());
				} else {
					daw.c.warn("Failed to find id for param set " + daw.d);
				}
			}

			if (daw.e.length > 0) {
				jsonObject5.add("pools", jsonSerializationContext.serialize(daw.e));
			}

			if (!ArrayUtils.isEmpty((Object[])daw.f)) {
				jsonObject5.add("functions", jsonSerializationContext.serialize(daw.f));
			}

			return jsonObject5;
		}
	}
}
