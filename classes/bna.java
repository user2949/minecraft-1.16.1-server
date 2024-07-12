import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class bna implements bmm {
	private final uh a;
	private final String b;
	private final bki c;
	private final gi<bmr> d;

	public bna(uh uh, String string, bki bki, gi<bmr> gi) {
		this.a = uh;
		this.b = string;
		this.c = bki;
		this.d = gi;
	}

	@Override
	public uh f() {
		return this.a;
	}

	@Override
	public bmw<?> ai_() {
		return bmw.b;
	}

	@Override
	public bki c() {
		return this.c;
	}

	@Override
	public gi<bmr> a() {
		return this.d;
	}

	public boolean a(bgu bgu, bqb bqb) {
		bee bee4 = new bee();
		int integer5 = 0;

		for (int integer6 = 0; integer6 < bgu.ab_(); integer6++) {
			bki bki7 = bgu.a(integer6);
			if (!bki7.a()) {
				integer5++;
				bee4.a(bki7, 1);
			}
		}

		return integer5 == this.d.size() && bee4.a(this, null);
	}

	public bki a(bgu bgu) {
		return this.c.i();
	}

	public static class a implements bmw<bna> {
		public bna a(uh uh, JsonObject jsonObject) {
			String string4 = adt.a(jsonObject, "group", "");
			gi<bmr> gi5 = a(adt.u(jsonObject, "ingredients"));
			if (gi5.isEmpty()) {
				throw new JsonParseException("No ingredients for shapeless recipe");
			} else if (gi5.size() > 9) {
				throw new JsonParseException("Too many ingredients for shapeless recipe");
			} else {
				bki bki6 = bmz.a(adt.t(jsonObject, "result"));
				return new bna(uh, string4, bki6, gi5);
			}
		}

		private static gi<bmr> a(JsonArray jsonArray) {
			gi<bmr> gi2 = gi.a();

			for (int integer3 = 0; integer3 < jsonArray.size(); integer3++) {
				bmr bmr4 = bmr.a(jsonArray.get(integer3));
				if (!bmr4.d()) {
					gi2.add(bmr4);
				}
			}

			return gi2;
		}

		public bna a(uh uh, mg mg) {
			String string4 = mg.e(32767);
			int integer5 = mg.i();
			gi<bmr> gi6 = gi.a(integer5, bmr.a);

			for (int integer7 = 0; integer7 < gi6.size(); integer7++) {
				gi6.set(integer7, bmr.b(mg));
			}

			bki bki7 = mg.m();
			return new bna(uh, string4, bki7, gi6);
		}

		public void a(mg mg, bna bna) {
			mg.a(bna.b);
			mg.d(bna.d.size());

			for (bmr bmr5 : bna.d) {
				bmr5.a(mg);
			}

			mg.a(bna.c);
		}
	}
}
