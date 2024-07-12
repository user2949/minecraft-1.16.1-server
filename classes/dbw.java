import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Random;

public class dbw extends dcg {
	private dbw(ddm[] arr) {
		super(arr);
	}

	@Override
	public dci b() {
		return dcj.r;
	}

	@Override
	public bki a(bki bki, dat dat) {
		Float float4 = dat.c(dda.k);
		if (float4 != null) {
			Random random5 = dat.a();
			float float6 = 1.0F / float4;
			int integer7 = bki.E();
			int integer8 = 0;

			for (int integer9 = 0; integer9 < integer7; integer9++) {
				if (random5.nextFloat() <= float6) {
					integer8++;
				}
			}

			bki.e(integer8);
		}

		return bki;
	}

	public static dcg.a<?> c() {
		return a(dbw::new);
	}

	public static class a extends dcg.c<dbw> {
		public dbw b(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ddm[] arr) {
			return new dbw(arr);
		}
	}
}
