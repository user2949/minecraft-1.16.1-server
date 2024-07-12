import com.google.gson.JsonObject;

public abstract class bnf implements bmu<amz> {
	protected final bmr a;
	protected final bki b;
	private final bmx<?> e;
	private final bmw<?> f;
	protected final uh c;
	protected final String d;

	public bnf(bmx<?> bmx, bmw<?> bmw, uh uh, String string, bmr bmr, bki bki) {
		this.e = bmx;
		this.f = bmw;
		this.c = uh;
		this.d = string;
		this.a = bmr;
		this.b = bki;
	}

	@Override
	public bmx<?> g() {
		return this.e;
	}

	@Override
	public bmw<?> ai_() {
		return this.f;
	}

	@Override
	public uh f() {
		return this.c;
	}

	@Override
	public bki c() {
		return this.b;
	}

	@Override
	public gi<bmr> a() {
		gi<bmr> gi2 = gi.a();
		gi2.add(this.a);
		return gi2;
	}

	@Override
	public bki a(amz amz) {
		return this.b.i();
	}

	public static class a<T extends bnf> implements bmw<T> {
		final bnf.a.a<T> v;

		protected a(bnf.a.a<T> a) {
			this.v = a;
		}

		public T a(uh uh, JsonObject jsonObject) {
			String string4 = adt.a(jsonObject, "group", "");
			bmr bmr5;
			if (adt.d(jsonObject, "ingredient")) {
				bmr5 = bmr.a(adt.u(jsonObject, "ingredient"));
			} else {
				bmr5 = bmr.a(adt.t(jsonObject, "ingredient"));
			}

			String string6 = adt.h(jsonObject, "result");
			int integer7 = adt.n(jsonObject, "count");
			bki bki8 = new bki(gl.am.a(new uh(string6)), integer7);
			return this.v.create(uh, string4, bmr5, bki8);
		}

		public T a(uh uh, mg mg) {
			String string4 = mg.e(32767);
			bmr bmr5 = bmr.b(mg);
			bki bki6 = mg.m();
			return this.v.create(uh, string4, bmr5, bki6);
		}

		public void a(mg mg, T bnf) {
			mg.a(bnf.d);
			bnf.a.a(mg);
			mg.a(bnf.b);
		}

		interface a<T extends bnf> {
			T create(uh uh, String string, bmr bmr, bki bki);
		}
	}
}
