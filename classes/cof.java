import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class cof implements cnr {
	public static final Codec<cof> a = RecordCodecBuilder.create(
		instance -> instance.apply2(cof::new, cmw.a.listOf().fieldOf("features").forGetter(cof -> cof.b), ckb.b.fieldOf("default").forGetter(cof -> cof.c))
	);
	public final List<cmw<?>> b;
	public final ckb<?, ?> c;

	public cof(List<cmw<?>> list, ckb<?, ?> ckb) {
		this.b = list;
		this.c = ckb;
	}
}
