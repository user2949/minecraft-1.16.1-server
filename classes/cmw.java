import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;

public class cmw<FC extends cnr> {
	public static final Codec<cmw<?>> a = RecordCodecBuilder.create(
		instance -> instance.group(ckb.b.fieldOf("feature").forGetter(cmw -> cmw.b), Codec.FLOAT.fieldOf("chance").forGetter(cmw -> cmw.c)).apply(instance, cmw::new)
	);
	public final ckb<FC, ?> b;
	public final float c;

	public cmw(ckb<FC, ?> ckb, float float2) {
		this.b = ckb;
		this.c = float2;
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu) {
		return this.b.a(bqu, bqq, cha, random, fu);
	}
}
