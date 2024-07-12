import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class coj implements cnr {
	public static final Codec<coj> a = RecordCodecBuilder.create(
		instance -> instance.group(
					cfj.b.fieldOf("target").forGetter(coj -> coj.b),
					cfj.b.fieldOf("state").forGetter(coj -> coj.c),
					gr.c.fieldOf("minimum_reach").forGetter(coj -> coj.d),
					gr.c.fieldOf("maximum_reach").forGetter(coj -> coj.e)
				)
				.apply(instance, coj::new)
	);
	public final cfj b;
	public final cfj c;
	public final gr d;
	public final gr e;

	public coj(cfj cfj1, cfj cfj2, gr gr3, gr gr4) {
		this.b = cfj1;
		this.c = cfj2;
		this.d = gr3;
		this.e = gr4;
	}

	public static class a {
		private cfj a = bvs.a.n();
		private cfj b = bvs.a.n();
		private gr c;
		private gr d;

		public a() {
			this.c = gr.d;
			this.d = gr.d;
		}

		public coj.a a(cfj cfj) {
			this.a = cfj;
			return this;
		}

		public coj.a b(cfj cfj) {
			this.b = cfj;
			return this;
		}

		public coj.a a(gr gr) {
			this.c = gr;
			return this;
		}

		public coj.a b(gr gr) {
			this.d = gr;
			return this;
		}

		public coj a() {
			if (this.c.u() >= 0 && this.c.v() >= 0 && this.c.w() >= 0) {
				if (this.c.u() <= this.d.u() && this.c.v() <= this.d.v() && this.c.w() <= this.d.w()) {
					return new coj(this.a, this.b, this.c, this.d);
				} else {
					throw new IllegalArgumentException("Maximum reach must be greater than minimum reach for each axis");
				}
			} else {
				throw new IllegalArgumentException("Minimum reach cannot be less than zero");
			}
		}
	}
}
