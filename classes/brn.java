import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class brn extends brh {
	public static final Codec<brn> e = RecordCodecBuilder.create(
		instance -> instance.group(gl.as.listOf().fieldOf("biomes").forGetter(brn -> brn.f), adl.a(0, 62).fieldOf("scale").withDefault(2).forGetter(brn -> brn.h))
				.apply(instance, brn::new)
	);
	private final List<bre> f;
	private final int g;
	private final int h;

	public brn(List<bre> list, int integer) {
		super(ImmutableList.copyOf(list));
		this.f = list;
		this.g = integer + 2;
		this.h = integer;
	}

	@Override
	protected Codec<? extends brh> a() {
		return e;
	}

	@Override
	public bre b(int integer1, int integer2, int integer3) {
		return (bre)this.f.get(Math.floorMod((integer1 >> this.g) + (integer3 >> this.g), this.f.size()));
	}
}
