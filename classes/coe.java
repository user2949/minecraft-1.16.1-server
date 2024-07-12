import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class coe implements cnr {
	public static final Codec<coe> a = RecordCodecBuilder.create(
		instance -> instance.group(ckb.b.fieldOf("feature_true").forGetter(coe -> coe.b), ckb.b.fieldOf("feature_false").forGetter(coe -> coe.c))
				.apply(instance, coe::new)
	);
	public final ckb<?, ?> b;
	public final ckb<?, ?> c;

	public coe(ckb<?, ?> ckb1, ckb<?, ?> ckb2) {
		this.b = ckb1;
		this.c = ckb2;
	}
}
