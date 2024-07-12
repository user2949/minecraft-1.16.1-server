import com.mojang.serialization.Codec;
import java.util.Random;

public class cvp extends cvw<cvx> {
	public cvp(Codec<cvx> codec) {
		super(codec);
	}

	public void a(
		Random random, cgy cgy, bre bre, int integer4, int integer5, int integer6, double double7, cfj cfj8, cfj cfj9, int integer10, long long11, cvx cvx
	) {
		if (double7 > 1.0) {
			cvw.S.a(random, cgy, bre, integer4, integer5, integer6, double7, cfj8, cfj9, integer10, long11, cvw.F);
		} else {
			cvw.S.a(random, cgy, bre, integer4, integer5, integer6, double7, cfj8, cfj9, integer10, long11, cvw.D);
		}
	}
}
