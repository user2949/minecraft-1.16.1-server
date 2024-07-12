import com.google.gson.JsonObject;

public class bnl implements bmu<amz> {
	private final bmr a;
	private final bmr b;
	private final bki c;
	private final uh d;

	public bnl(uh uh, bmr bmr2, bmr bmr3, bki bki) {
		this.d = uh;
		this.a = bmr2;
		this.b = bmr3;
		this.c = bki;
	}

	@Override
	public boolean a(amz amz, bqb bqb) {
		return this.a.a(amz.a(0)) && this.b.a(amz.a(1));
	}

	@Override
	public bki a(amz amz) {
		bki bki3 = this.c.i();
		le le4 = amz.a(0).o();
		if (le4 != null) {
			bki3.c(le4.g());
		}

		return bki3;
	}

	@Override
	public bki c() {
		return this.c;
	}

	public boolean a(bki bki) {
		return this.b.a(bki);
	}

	@Override
	public uh f() {
		return this.d;
	}

	@Override
	public bmw<?> ai_() {
		return bmw.u;
	}

	@Override
	public bmx<?> g() {
		return bmx.g;
	}

	public static class a implements bmw<bnl> {
		public bnl a(uh uh, JsonObject jsonObject) {
			bmr bmr4 = bmr.a(adt.t(jsonObject, "base"));
			bmr bmr5 = bmr.a(adt.t(jsonObject, "addition"));
			bki bki6 = bmz.a(adt.t(jsonObject, "result"));
			return new bnl(uh, bmr4, bmr5, bki6);
		}

		public bnl a(uh uh, mg mg) {
			bmr bmr4 = bmr.b(mg);
			bmr bmr5 = bmr.b(mg);
			bki bki6 = mg.m();
			return new bnl(uh, bmr4, bmr5, bki6);
		}

		public void a(mg mg, bnl bnl) {
			bnl.a.a(mg);
			bnl.b.a(mg);
			mg.a(bnl.c);
		}
	}
}
