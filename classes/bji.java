import java.util.List;

public interface bji {
	default boolean a(bki bki) {
		le le3 = bki.b("display");
		return le3 != null && le3.c("color", 99);
	}

	default int b(bki bki) {
		le le3 = bki.b("display");
		return le3 != null && le3.c("color", 99) ? le3.h("color") : 10511680;
	}

	default void c(bki bki) {
		le le3 = bki.b("display");
		if (le3 != null && le3.e("color")) {
			le3.r("color");
		}
	}

	default void a(bki bki, int integer) {
		bki.a("display").b("color", integer);
	}

	static bki a(bki bki, List<bjf> list) {
		bki bki3 = bki.b;
		int[] arr4 = new int[3];
		int integer5 = 0;
		int integer6 = 0;
		bji bji7 = null;
		bke bke8 = bki.b();
		if (bke8 instanceof bji) {
			bji7 = (bji)bke8;
			bki3 = bki.i();
			bki3.e(1);
			if (bji7.a(bki)) {
				int integer9 = bji7.b(bki3);
				float float10 = (float)(integer9 >> 16 & 0xFF) / 255.0F;
				float float11 = (float)(integer9 >> 8 & 0xFF) / 255.0F;
				float float12 = (float)(integer9 & 0xFF) / 255.0F;
				integer5 = (int)((float)integer5 + Math.max(float10, Math.max(float11, float12)) * 255.0F);
				arr4[0] = (int)((float)arr4[0] + float10 * 255.0F);
				arr4[1] = (int)((float)arr4[1] + float11 * 255.0F);
				arr4[2] = (int)((float)arr4[2] + float12 * 255.0F);
				integer6++;
			}

			for (bjf bjf10 : list) {
				float[] arr11 = bjf10.d().e();
				int integer12 = (int)(arr11[0] * 255.0F);
				int integer13 = (int)(arr11[1] * 255.0F);
				int integer14 = (int)(arr11[2] * 255.0F);
				integer5 += Math.max(integer12, Math.max(integer13, integer14));
				arr4[0] += integer12;
				arr4[1] += integer13;
				arr4[2] += integer14;
				integer6++;
			}
		}

		if (bji7 == null) {
			return bki.b;
		} else {
			int integer9 = arr4[0] / integer6;
			int integer10 = arr4[1] / integer6;
			int integer11 = arr4[2] / integer6;
			float float12 = (float)integer5 / (float)integer6;
			float float13 = (float)Math.max(integer9, Math.max(integer10, integer11));
			integer9 = (int)((float)integer9 * float12 / float13);
			integer10 = (int)((float)integer10 * float12 / float13);
			integer11 = (int)((float)integer11 * float12 / float13);
			int var26 = (integer9 << 8) + integer10;
			var26 = (var26 << 8) + integer11;
			bji7.a(bki3, var26);
			return bki3;
		}
	}
}
