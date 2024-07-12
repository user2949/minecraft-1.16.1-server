import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class cjb extends cjh<cod> {
	public cjb(Codec<cod> codec, int integer) {
		super(codec, integer);
	}

	public boolean a(Random random, int integer2, int integer3, cod cod) {
		return random.nextFloat() <= cod.c;
	}

	public boolean a(
		cgy cgy, Function<fu, bre> function, Random random, int integer4, int integer5, int integer6, int integer7, int integer8, BitSet bitSet, cod cod
	) {
		int integer12 = (this.d() * 2 - 1) * 16;
		int integer13 = random.nextInt(random.nextInt(random.nextInt(this.a()) + 1) + 1);

		for (int integer14 = 0; integer14 < integer13; integer14++) {
			double double15 = (double)(integer5 * 16 + random.nextInt(16));
			double double17 = (double)this.b(random);
			double double19 = (double)(integer6 * 16 + random.nextInt(16));
			int integer21 = 1;
			if (random.nextInt(4) == 0) {
				double double22 = 0.5;
				float float24 = 1.0F + random.nextFloat() * 6.0F;
				this.a(cgy, function, random.nextLong(), integer4, integer7, integer8, double15, double17, double19, float24, 0.5, bitSet);
				integer21 += random.nextInt(4);
			}

			for (int integer22 = 0; integer22 < integer21; integer22++) {
				float float23 = random.nextFloat() * (float) (Math.PI * 2);
				float float24 = (random.nextFloat() - 0.5F) / 4.0F;
				float float25 = this.a(random);
				int integer26 = integer12 - random.nextInt(integer12 / 4);
				int integer27 = 0;
				this.a(
					cgy, function, random.nextLong(), integer4, integer7, integer8, double15, double17, double19, float25, float23, float24, 0, integer26, this.b(), bitSet
				);
			}
		}

		return true;
	}

	protected int a() {
		return 15;
	}

	protected float a(Random random) {
		float float3 = random.nextFloat() * 2.0F + random.nextFloat();
		if (random.nextInt(10) == 0) {
			float3 *= random.nextFloat() * random.nextFloat() * 3.0F + 1.0F;
		}

		return float3;
	}

	protected double b() {
		return 1.0;
	}

	protected int b(Random random) {
		return random.nextInt(random.nextInt(120) + 8);
	}

	protected void a(
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
		double double11,
		BitSet bitSet
	) {
		double double19 = 1.5 + (double)(aec.a((float) (Math.PI / 2)) * float10);
		double double21 = double19 * double11;
		this.a(cgy, function, long3, integer4, integer5, integer6, double7 + 1.0, double8, double9, double19, double21, bitSet);
	}

	protected void a(
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
		int integer24 = random23.nextInt(integer14 / 2) + integer14 / 4;
		boolean boolean25 = random23.nextInt(6) == 0;
		float float26 = 0.0F;
		float float27 = 0.0F;

		for (int integer28 = integer13; integer28 < integer14; integer28++) {
			double double29 = 1.5 + (double)(aec.a((float) Math.PI * (float)integer28 / (float)integer14) * float10);
			double double31 = double29 * double15;
			float float33 = aec.b(float12);
			double7 += (double)(aec.b(float11) * float33);
			double8 += (double)aec.a(float12);
			double9 += (double)(aec.a(float11) * float33);
			float12 *= boolean25 ? 0.92F : 0.7F;
			float12 += float27 * 0.1F;
			float11 += float26 * 0.1F;
			float27 *= 0.9F;
			float26 *= 0.75F;
			float27 += (random23.nextFloat() - random23.nextFloat()) * random23.nextFloat() * 2.0F;
			float26 += (random23.nextFloat() - random23.nextFloat()) * random23.nextFloat() * 4.0F;
			if (integer28 == integer24 && float10 > 1.0F) {
				this.a(
					cgy,
					function,
					random23.nextLong(),
					integer4,
					integer5,
					integer6,
					double7,
					double8,
					double9,
					random23.nextFloat() * 0.5F + 0.5F,
					float11 - (float) (Math.PI / 2),
					float12 / 3.0F,
					integer28,
					integer14,
					1.0,
					bitSet
				);
				this.a(
					cgy,
					function,
					random23.nextLong(),
					integer4,
					integer5,
					integer6,
					double7,
					double8,
					double9,
					random23.nextFloat() * 0.5F + 0.5F,
					float11 + (float) (Math.PI / 2),
					float12 / 3.0F,
					integer28,
					integer14,
					1.0,
					bitSet
				);
				return;
			}

			if (random23.nextInt(4) != 0) {
				if (!this.a(integer5, integer6, double7, double9, integer28, integer14, float10)) {
					return;
				}

				this.a(cgy, function, long3, integer4, integer5, integer6, double7, double8, double9, double29, double31, bitSet);
			}
		}
	}

	@Override
	protected boolean a(double double1, double double2, double double3, int integer) {
		return double2 <= -0.7 || double1 * double1 + double2 * double2 + double3 * double3 >= 1.0;
	}
}
