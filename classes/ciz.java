import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class ciz extends cjh<cod> {
	private final float[] m = new float[1024];

	public ciz(Codec<cod> codec) {
		super(codec, 256);
	}

	public boolean a(Random random, int integer2, int integer3, cod cod) {
		return random.nextFloat() <= cod.c;
	}

	public boolean a(
		cgy cgy, Function<fu, bre> function, Random random, int integer4, int integer5, int integer6, int integer7, int integer8, BitSet bitSet, cod cod
	) {
		int integer12 = (this.d() * 2 - 1) * 16;
		double double13 = (double)(integer5 * 16 + random.nextInt(16));
		double double15 = (double)(random.nextInt(random.nextInt(40) + 8) + 20);
		double double17 = (double)(integer6 * 16 + random.nextInt(16));
		float float19 = random.nextFloat() * (float) (Math.PI * 2);
		float float20 = (random.nextFloat() - 0.5F) * 2.0F / 8.0F;
		double double21 = 3.0;
		float float23 = (random.nextFloat() * 2.0F + random.nextFloat()) * 2.0F;
		int integer24 = integer12 - random.nextInt(integer12 / 4);
		int integer25 = 0;
		this.a(cgy, function, random.nextLong(), integer4, integer7, integer8, double13, double15, double17, float23, float19, float20, 0, integer24, 3.0, bitSet);
		return true;
	}

	private void a(
		cgy cgy,
		Function<fu, bre> function,
		long long3,
		int integer4,
		int integer5,
		int integer6,
		double double7,
		double double8,
		double double9,
		float float10,
		float float11,
		float float12,
		int integer13,
		int integer14,
		double double15,
		BitSet bitSet
	) {
		Random random23 = new Random(long3);
		float float24 = 1.0F;

		for (int integer25 = 0; integer25 < 256; integer25++) {
			if (integer25 == 0 || random23.nextInt(3) == 0) {
				float24 = 1.0F + random23.nextFloat() * random23.nextFloat();
			}

			this.m[integer25] = float24 * float24;
		}

		float float25 = 0.0F;
		float float26 = 0.0F;

		for (int integer27 = integer13; integer27 < integer14; integer27++) {
			double double28 = 1.5 + (double)(aec.a((float)integer27 * (float) Math.PI / (float)integer14) * float10);
			double double30 = double28 * double15;
			double28 *= (double)random23.nextFloat() * 0.25 + 0.75;
			double30 *= (double)random23.nextFloat() * 0.25 + 0.75;
			float float32 = aec.b(float12);
			float float33 = aec.a(float12);
			double7 += (double)(aec.b(float11) * float32);
			double8 += (double)float33;
			double9 += (double)(aec.a(float11) * float32);
			float12 *= 0.7F;
			float12 += float26 * 0.05F;
			float11 += float25 * 0.05F;
			float26 *= 0.8F;
			float25 *= 0.5F;
			float26 += (random23.nextFloat() - random23.nextFloat()) * random23.nextFloat() * 2.0F;
			float25 += (random23.nextFloat() - random23.nextFloat()) * random23.nextFloat() * 4.0F;
			if (random23.nextInt(4) != 0) {
				if (!this.a(integer5, integer6, double7, double9, integer27, integer14, float10)) {
					return;
				}

				this.a(cgy, function, long3, integer4, integer5, integer6, double7, double8, double9, double28, double30, bitSet);
			}
		}
	}

	@Override
	protected boolean a(double double1, double double2, double double3, int integer) {
		return (double1 * double1 + double3 * double3) * (double)this.m[integer - 1] + double2 * double2 / 6.0 >= 1.0;
	}
}
