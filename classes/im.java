import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Supplier;

public class im implements ik {
	private final bvr a;
	private final List<im.b> b = Lists.<im.b>newArrayList();

	private im(bvr bvr) {
		this.a = bvr;
	}

	@Override
	public bvr a() {
		return this.a;
	}

	public static im a(bvr bvr) {
		return new im(bvr);
	}

	public im a(List<iq> list) {
		this.b.add(new im.b(list));
		return this;
	}

	public im a(iq iq) {
		return this.a(ImmutableList.of(iq));
	}

	public im a(il il, List<iq> list) {
		this.b.add(new im.a(il, list));
		return this;
	}

	public im a(il il, iq... arr) {
		return this.a(il, ImmutableList.copyOf(arr));
	}

	public im a(il il, iq iq) {
		return this.a(il, ImmutableList.of(iq));
	}

	public JsonElement get() {
		cfk<bvr, cfj> cfk2 = this.a.m();
		this.b.forEach(b -> b.a(cfk2));
		JsonArray jsonArray3 = new JsonArray();
		this.b.stream().map(im.b::a).forEach(jsonArray3::add);
		JsonObject jsonObject4 = new JsonObject();
		jsonObject4.add("multipart", jsonArray3);
		return jsonObject4;
	}

	static class a extends im.b {
		private final il a;

		private a(il il, List<iq> list) {
			super(list);
			this.a = il;
		}

		@Override
		public void a(cfk<?, ?> cfk) {
			this.a.a(cfk);
		}

		@Override
		public void a(JsonObject jsonObject) {
			jsonObject.add("when", (JsonElement)this.a.get());
		}
	}

	static class b implements Supplier<JsonElement> {
		private final List<iq> a;

		private b(List<iq> list) {
			this.a = list;
		}

		public void a(cfk<?, ?> cfk) {
		}

		public void a(JsonObject jsonObject) {
		}

		public JsonElement get() {
			JsonObject jsonObject2 = new JsonObject();
			this.a(jsonObject2);
			jsonObject2.add("apply", iq.a(this.a));
			return jsonObject2;
		}
	}
}
