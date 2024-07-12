import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dca extends dcg {
	private static final Logger a = LogManager.getLogger();
	private final List<bnw> b;

	private dca(ddm[] arr, Collection<bnw> collection) {
		super(arr);
		this.b = ImmutableList.copyOf(collection);
	}

	@Override
	public dci b() {
		return dcj.d;
	}

	@Override
	public bki a(bki bki, dat dat) {
		Random random5 = dat.a();
		bnw bnw4;
		if (this.b.isEmpty()) {
			boolean boolean6 = bki.b() == bkk.mc;
			List<bnw> list7 = (List<bnw>)gl.ak.e().filter(bnw::i).filter(bnw -> boolean6 || bnw.a(bki)).collect(Collectors.toList());
			if (list7.isEmpty()) {
				a.warn("Couldn't find a compatible enchantment for {}", bki);
				return bki;
			}

			bnw4 = (bnw)list7.get(random5.nextInt(list7.size()));
		} else {
			bnw4 = (bnw)this.b.get(random5.nextInt(this.b.size()));
		}

		return a(bki, bnw4, random5);
	}

	private static bki a(bki bki, bnw bnw, Random random) {
		int integer4 = aec.a(random, bnw.e(), bnw.a());
		if (bki.b() == bkk.mc) {
			bki = new bki(bkk.pp);
			bjm.a(bki, new bnz(bnw, integer4));
		} else {
			bki.a(bnw, integer4);
		}

		return bki;
	}

	public static dcg.a<?> d() {
		return a(arr -> new dca(arr, ImmutableList.<bnw>of()));
	}

	public static class a extends dcg.a<dca.a> {
		private final Set<bnw> a = Sets.<bnw>newHashSet();

		protected dca.a d() {
			return this;
		}

		public dca.a a(bnw bnw) {
			this.a.add(bnw);
			return this;
		}

		@Override
		public dch b() {
			return new dca(this.g(), this.a);
		}
	}

	public static class b extends dcg.c<dca> {
		public void a(JsonObject jsonObject, dca dca, JsonSerializationContext jsonSerializationContext) {
			super.a(jsonObject, dca, jsonSerializationContext);
			if (!dca.b.isEmpty()) {
				JsonArray jsonArray5 = new JsonArray();

				for (bnw bnw7 : dca.b) {
					uh uh8 = gl.ak.b(bnw7);
					if (uh8 == null) {
						throw new IllegalArgumentException("Don't know how to serialize enchantment " + bnw7);
					}

					jsonArray5.add(new JsonPrimitive(uh8.toString()));
				}

				jsonObject.add("enchantments", jsonArray5);
			}
		}

		public dca b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			List<bnw> list5 = Lists.<bnw>newArrayList();
			if (jsonObject.has("enchantments")) {
				for (JsonElement jsonElement8 : adt.u(jsonObject, "enchantments")) {
					String string9 = adt.a(jsonElement8, "enchantment");
					bnw bnw10 = (bnw)gl.ak.b(new uh(string9)).orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + string9 + "'"));
					list5.add(bnw10);
				}
			}

			return new dca(arr, list5);
		}
	}
}
