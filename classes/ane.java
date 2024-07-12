import javax.annotation.concurrent.Immutable;

@Immutable
public class ane {
	private final and a;
	private final float b;

	public ane(and and, long long2, long long3, float float4) {
		this.a = and;
		this.b = this.a(and, long2, long3, float4);
	}

	public and a() {
		return this.a;
	}

	public float b() {
		return this.b;
	}

	public boolean a(float float1) {
		return this.b > float1;
	}

	public float d() {
		if (this.b < 2.0F) {
			return 0.0F;
		} else {
			return this.b > 4.0F ? 1.0F : (this.b - 2.0F) / 2.0F;
		}
	}

	private float a(and and, long long2, long long3, float float4) {
		if (and == and.PEACEFUL) {
			return 0.0F;
		} else {
			boolean boolean8 = and == and.HARD;
			float float9 = 0.75F;
			float float10 = aec.a(((float)long2 + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
			float9 += float10;
			float float11 = 0.0F;
			float11 += aec.a((float)long3 / 3600000.0F, 0.0F, 1.0F) * (boolean8 ? 1.0F : 0.75F);
			float11 += aec.a(float4 * 0.25F, 0.0F, float10);
			if (and == and.EASY) {
				float11 *= 0.5F;
			}

			float9 += float11;
			return (float)and.a() * float9;
		}
	}
}
