import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cky implements cnr {
	public static final Codec<cky> a = RecordCodecBuilder.create(
		instance -> instance.group(
					cfj.b.fieldOf("valid_base_block").forGetter(cky -> cky.f),
					cfj.b.fieldOf("stem_state").forGetter(cky -> cky.g),
					cfj.b.fieldOf("hat_state").forGetter(cky -> cky.h),
					cfj.b.fieldOf("decor_state").forGetter(cky -> cky.i),
					Codec.BOOL.fieldOf("planted").withDefault(false).forGetter(cky -> cky.j)
				)
				.apply(instance, cky::new)
	);
	public static final cky b = new cky(bvs.mu.n(), bvs.mq.n(), bvs.iK.n(), bvs.mw.n(), true);
	public static final cky c;
	public static final cky d = new cky(bvs.ml.n(), bvs.mh.n(), bvs.mn.n(), bvs.mw.n(), true);
	public static final cky e;
	public final cfj f;
	public final cfj g;
	public final cfj h;
	public final cfj i;
	public final boolean j;

	public cky(cfj cfj1, cfj cfj2, cfj cfj3, cfj cfj4, boolean boolean5) {
		this.f = cfj1;
		this.g = cfj2;
		this.h = cfj3;
		this.i = cfj4;
		this.j = boolean5;
	}

	static {
		c = new cky(b.f, b.g, b.h, b.i, false);
		e = new cky(d.f, d.g, d.h, d.i, false);
	}
}
