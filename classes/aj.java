import com.google.gson.JsonObject;

public abstract class aj implements ae {
	private final uh a;
	private final be.b b;

	public aj(uh uh, be.b b) {
		this.a = uh;
		this.b = b;
	}

	@Override
	public uh a() {
		return this.a;
	}

	protected be.b b() {
		return this.b;
	}

	@Override
	public JsonObject a(cg cg) {
		JsonObject jsonObject3 = new JsonObject();
		jsonObject3.add("player", this.b.a(cg));
		return jsonObject3;
	}

	public String toString() {
		return "AbstractCriterionInstance{criterion=" + this.a + '}';
	}
}
