import com.mojang.serialization.Codec;
import java.util.Random;

public class cvz extends cvw<cvx> {
	public cvz(Codec<cvx> codec) {
		super(codec);
	}

	public void a(
		Random random, cgy cgy, bre bre, int integer4, int integer5, int integer6, double double7, cfj cfj8, cfj cfj9, int integer10, long long11, cvx cvx
	) {
		double double16 = bre.f.a((double)integer4 * 0.25, (double)integer5 * 0.25, false);
		if (double16 > 0.0) {
			int integer18 = integer4 & 15;
			int integer19 = integer5 & 15;
			fu.a a20 = new fu.a();

			for (int integer21 = integer6; integer21 >= 0; integer21--) {
				a20.d(integer18, integer21, integer19);
				if (!cgy.d_(a20).g()) {
					if (integer21 == 62 && !cgy.d_(a20).a(cfj9.b())) {
						cgy.a(a20, cfj9, false);
					}
					break;
				}
			}
		}

		cvw.S.a(random, cgy, bre, integer4, integer5, integer6, double7, cfj8, cfj9, integer10, long11, cvx);
	}
}
