import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class ai {
	private final w a;
	private final ai b;
	private final ai c;
	private final int d;
	private final List<ai> e = Lists.<ai>newArrayList();
	private ai f;
	private ai g;
	private int h;
	private float i;
	private float j;
	private float k;
	private float l;

	public ai(w w, @Nullable ai ai2, @Nullable ai ai3, int integer4, int integer5) {
		if (w.c() == null) {
			throw new IllegalArgumentException("Can't position an invisible advancement!");
		} else {
			this.a = w;
			this.b = ai2;
			this.c = ai3;
			this.d = integer4;
			this.f = this;
			this.h = integer5;
			this.i = -1.0F;
			ai ai7 = null;

			for (w w9 : w.e()) {
				ai7 = this.a(w9, ai7);
			}
		}
	}

	@Nullable
	private ai a(w w, @Nullable ai ai) {
		if (w.c() != null) {
			ai = new ai(w, this, ai, this.e.size() + 1, this.h + 1);
			this.e.add(ai);
		} else {
			for (w w5 : w.e()) {
				ai = this.a(w5, ai);
			}
		}

		return ai;
	}

	private void a() {
		if (this.e.isEmpty()) {
			if (this.c != null) {
				this.i = this.c.i + 1.0F;
			} else {
				this.i = 0.0F;
			}
		} else {
			ai ai2 = null;

			for (ai ai4 : this.e) {
				ai4.a();
				ai2 = ai4.a(ai2 == null ? ai4 : ai2);
			}

			this.b();
			float float3 = (((ai)this.e.get(0)).i + ((ai)this.e.get(this.e.size() - 1)).i) / 2.0F;
			if (this.c != null) {
				this.i = this.c.i + 1.0F;
				this.j = this.i - float3;
			} else {
				this.i = float3;
			}
		}
	}

	private float a(float float1, int integer, float float3) {
		this.i += float1;
		this.h = integer;
		if (this.i < float3) {
			float3 = this.i;
		}

		for (ai ai6 : this.e) {
			float3 = ai6.a(float1 + this.j, integer + 1, float3);
		}

		return float3;
	}

	private void a(float float1) {
		this.i += float1;

		for (ai ai4 : this.e) {
			ai4.a(float1);
		}
	}

	private void b() {
		float float2 = 0.0F;
		float float3 = 0.0F;

		for (int integer4 = this.e.size() - 1; integer4 >= 0; integer4--) {
			ai ai5 = (ai)this.e.get(integer4);
			ai5.i += float2;
			ai5.j += float2;
			float3 += ai5.k;
			float2 += ai5.l + float3;
		}
	}

	@Nullable
	private ai c() {
		if (this.g != null) {
			return this.g;
		} else {
			return !this.e.isEmpty() ? (ai)this.e.get(0) : null;
		}
	}

	@Nullable
	private ai d() {
		if (this.g != null) {
			return this.g;
		} else {
			return !this.e.isEmpty() ? (ai)this.e.get(this.e.size() - 1) : null;
		}
	}

	private ai a(ai ai) {
		if (this.c == null) {
			return ai;
		} else {
			ai ai3 = this;
			ai ai4 = this;
			ai ai5 = this.c;
			ai ai6 = (ai)this.b.e.get(0);
			float float7 = this.j;
			float float8 = this.j;
			float float9 = ai5.j;

			float float10;
			for (float10 = ai6.j; ai5.d() != null && ai3.c() != null; float8 += ai4.j) {
				ai5 = ai5.d();
				ai3 = ai3.c();
				ai6 = ai6.c();
				ai4 = ai4.d();
				ai4.f = this;
				float float11 = ai5.i + float9 - (ai3.i + float7) + 1.0F;
				if (float11 > 0.0F) {
					ai5.a(this, ai).a(this, float11);
					float7 += float11;
					float8 += float11;
				}

				float9 += ai5.j;
				float7 += ai3.j;
				float10 += ai6.j;
			}

			if (ai5.d() != null && ai4.d() == null) {
				ai4.g = ai5.d();
				ai4.j += float9 - float8;
			} else {
				if (ai3.c() != null && ai6.c() == null) {
					ai6.g = ai3.c();
					ai6.j += float7 - float10;
				}

				ai = this;
			}

			return ai;
		}
	}

	private void a(ai ai, float float2) {
		float float4 = (float)(ai.d - this.d);
		if (float4 != 0.0F) {
			ai.k -= float2 / float4;
			this.k += float2 / float4;
		}

		ai.l += float2;
		ai.i += float2;
		ai.j += float2;
	}

	private ai a(ai ai1, ai ai2) {
		return this.f != null && ai1.b.e.contains(this.f) ? this.f : ai2;
	}

	private void e() {
		if (this.a.c() != null) {
			this.a.c().a((float)this.h, this.i);
		}

		if (!this.e.isEmpty()) {
			for (ai ai3 : this.e) {
				ai3.e();
			}
		}
	}

	public static void a(w w) {
		if (w.c() == null) {
			throw new IllegalArgumentException("Can't position children of an invisible root!");
		} else {
			ai ai2 = new ai(w, null, null, 1, 0);
			ai2.a();
			float float3 = ai2.a(0.0F, 0, ai2.i);
			if (float3 < 0.0F) {
				ai2.a(-float3);
			}

			ai2.e();
		}
	}
}
