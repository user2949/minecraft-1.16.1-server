import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Set;

public class ddd implements ddm {
	private final bnw a;
	private final float[] b;

	private ddd(bnw bnw, float[] arr) {
		this.a = bnw;
		this.b = arr;
	}

	@Override
	public ddn b() {
		return ddo.j;
	}

	@Override
	public Set<dcx<?>> a() {
		return ImmutableSet.of(dda.j);
	}

	public boolean test(dat dat) {
		bki bki3 = dat.c(dda.j);
		int integer4 = bki3 != null ? bny.a(this.a, bki3) : 0;
		float float5 = this.b[Math.min(integer4, this.b.length - 1)];
		return dat.a().nextFloat() < float5;
	}

	public static ddm.a a(bnw bnw, float... arr) {
		return () -> new ddd(bnw, arr);
	}

	public static class a implements dbc<ddd> {
		public void a(JsonObject jsonObject, ddd ddd, JsonSerializationContext jsonSerializationContext) {
			jsonObject.addProperty("enchantment", gl.ak.b(ddd.a).toString());
			jsonObject.add("chances", jsonSerializationContext.serialize(ddd.b));
		}

		public ddd a(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
			uh uh4 = new uh(adt.h(jsonObject, "enchantment"));
			bnw bnw5 = (bnw)gl.ak.b(uh4).orElseThrow(() -> new JsonParseException("Invalid enchantment id: " + uh4));
			float[] arr6 = adt.a(jsonObject, "chances", jsonDeserializationContext, float[].class);
			return new ddd(bnw5, arr6);
		}
	}
}
