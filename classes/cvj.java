import com.mojang.serialization.Codec;
import java.util.Random;

public class cvj<SC extends cvy> {
	public static final Codec<cvj<?>> a = gl.ap.dispatch("name", cvj -> cvj.b, cvw::d);
	public final cvw<SC> b;
	public final SC c;

	public cvj(cvw<SC> cvw, SC cvy) {
		this.b = cvw;
		this.c = cvy;
	}

	public void a(Random random, cgy cgy, bre bre, int integer4, int integer5, int integer6, double double7, cfj cfj8, cfj cfj9, int integer10, long long11) {
		this.b.a(random, cgy, bre, integer4, integer5, integer6, double7, cfj8, cfj9, integer10, long11, this.c);
	}

	public void a(long long1) {
		this.b.a(long1);
	}

	public SC a() {
		return this.c;
	}
}
