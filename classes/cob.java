import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cob implements cnr {
	public static final Codec<cob> a = RecordCodecBuilder.create(
		instance -> instance.group(
					ctp.b.c.fieldOf("biome_temp").forGetter(cob -> cob.b),
					Codec.FLOAT.fieldOf("large_probability").forGetter(cob -> cob.c),
					Codec.FLOAT.fieldOf("cluster_probability").forGetter(cob -> cob.d)
				)
				.apply(instance, cob::new)
	);
	public final ctp.b b;
	public final float c;
	public final float d;

	public cob(ctp.b b, float float2, float float3) {
		this.b = b;
		this.c = float2;
		this.d = float3;
	}
}
