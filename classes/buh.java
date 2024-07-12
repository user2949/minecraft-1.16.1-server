import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;

public class buh extends brh {
	public static final Codec<buh> e = Codec.LONG.fieldOf("seed").<buh>xmap(buh::new, buh -> buh.h).stable().codec();
	private final cwg f;
	private static final List<bre> g = ImmutableList.of(brk.k, brk.R, brk.Q, brk.P, brk.S);
	private final long h;

	public buh(long long1) {
		super(g);
		this.h = long1;
		ciy ciy4 = new ciy(long1);
		ciy4.a(17292);
		this.f = new cwg(ciy4);
	}

	@Override
	protected Codec<? extends brh> a() {
		return e;
	}

	@Override
	public bre b(int integer1, int integer2, int integer3) {
		int integer5 = integer1 >> 2;
		int integer6 = integer3 >> 2;
		if ((long)integer5 * (long)integer5 + (long)integer6 * (long)integer6 <= 4096L) {
			return brk.k;
		} else {
			float float7 = a(this.f, integer5 * 2 + 1, integer6 * 2 + 1);
			if (float7 > 40.0F) {
				return brk.R;
			} else if (float7 >= 0.0F) {
				return brk.Q;
			} else {
				return float7 < -20.0F ? brk.P : brk.S;
			}
		}
	}

	public boolean b(long long1) {
		return this.h == long1;
	}

	public static float a(cwg cwg, int integer2, int integer3) {
		int integer4 = integer2 / 2;
		int integer5 = integer3 / 2;
		int integer6 = integer2 % 2;
		int integer7 = integer3 % 2;
		float float8 = 100.0F - aec.c((float)(integer2 * integer2 + integer3 * integer3)) * 8.0F;
		float8 = aec.a(float8, -100.0F, 80.0F);

		for (int integer9 = -12; integer9 <= 12; integer9++) {
			for (int integer10 = -12; integer10 <= 12; integer10++) {
				long long11 = (long)(integer4 + integer9);
				long long13 = (long)(integer5 + integer10);
				if (long11 * long11 + long13 * long13 > 4096L && cwg.a((double)long11, (double)long13) < -0.9F) {
					float float15 = (aec.e((float)long11) * 3439.0F + aec.e((float)long13) * 147.0F) % 13.0F + 9.0F;
					float float16 = (float)(integer6 - integer9 * 2);
					float float17 = (float)(integer7 - integer10 * 2);
					float float18 = 100.0F - aec.c(float16 * float16 + float17 * float17) * float15;
					float18 = aec.a(float18, -100.0F, 80.0F);
					float8 = Math.max(float8, float18);
				}
			}
		}

		return float8;
	}
}
