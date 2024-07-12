import com.mojang.serialization.Codec;
import java.util.Random;

public class ckp extends cml<coa> {
	public ckp(Codec<coa> codec) {
		super(codec);
	}

	@Override
	protected boolean b() {
		return false;
	}

	protected boolean a(cha cha, brh brh, long long3, ciy ciy, int integer5, int integer6, bre bre, bph bph, coa coa) {
		return b(integer5, integer6, cha) >= 60;
	}

	@Override
	public cml.a<coa> a() {
		return ckp.a::new;
	}

	private static int b(int integer1, int integer2, cha cha) {
		Random random4 = new Random((long)(integer1 + integer2 * 10387313));
		cap cap5 = cap.a(random4);
		int integer6 = 5;
		int integer7 = 5;
		if (cap5 == cap.CLOCKWISE_90) {
			integer6 = -5;
		} else if (cap5 == cap.CLOCKWISE_180) {
			integer6 = -5;
			integer7 = -5;
		} else if (cap5 == cap.COUNTERCLOCKWISE_90) {
			integer7 = -5;
		}

		int integer8 = (integer1 << 4) + 7;
		int integer9 = (integer2 << 4) + 7;
		int integer10 = cha.c(integer8, integer9, cio.a.WORLD_SURFACE_WG);
		int integer11 = cha.c(integer8, integer9 + integer7, cio.a.WORLD_SURFACE_WG);
		int integer12 = cha.c(integer8 + integer6, integer9, cio.a.WORLD_SURFACE_WG);
		int integer13 = cha.c(integer8 + integer6, integer9 + integer7, cio.a.WORLD_SURFACE_WG);
		return Math.min(Math.min(integer10, integer11), Math.min(integer12, integer13));
	}

	public static class a extends ctz<coa> {
		public a(cml<coa> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, coa coa) {
			cap cap8 = cap.a(this.d);
			int integer9 = ckp.b(integer3, integer4, cha);
			if (integer9 >= 60) {
				fu fu10 = new fu(integer3 * 16 + 8, integer9, integer4 * 16 + 8);
				ctg.a(cva, fu10, cap8, this.b, this.d);
				this.b();
			}
		}
	}
}
