import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Set;

public class cor implements cnr {
	public static final Codec<cor> a = RecordCodecBuilder.create(
		instance -> instance.group(
					cxa.a.fieldOf("state").forGetter(cor -> cor.b),
					Codec.BOOL.fieldOf("requires_block_below").withDefault(true).forGetter(cor -> cor.c),
					Codec.INT.fieldOf("rock_count").withDefault(4).forGetter(cor -> cor.d),
					Codec.INT.fieldOf("hole_count").withDefault(1).forGetter(cor -> cor.e),
					gl.aj.listOf().fieldOf("valid_blocks").xmap(ImmutableSet::copyOf, ImmutableList::copyOf).forGetter(cor -> cor.f)
				)
				.apply(instance, cor::new)
	);
	public final cxa b;
	public final boolean c;
	public final int d;
	public final int e;
	public final Set<bvr> f;

	public cor(cxa cxa, boolean boolean2, int integer3, int integer4, Set<bvr> set) {
		this.b = cxa;
		this.c = boolean2;
		this.d = integer3;
		this.e = integer4;
		this.f = set;
	}
}
