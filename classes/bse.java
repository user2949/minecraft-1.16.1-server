import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;

public class bse extends brh {
	public static final Codec<bse> e = gl.as.fieldOf("biome").<bse>xmap(bse::new, bse -> bse.f).stable().codec();
	private final bre f;

	public bse(bre bre) {
		super(ImmutableList.of(bre));
		this.f = bre;
	}

	@Override
	protected Codec<? extends brh> a() {
		return e;
	}

	@Override
	public bre b(int integer1, int integer2, int integer3) {
		return this.f;
	}

	@Nullable
	@Override
	public fu a(int integer1, int integer2, int integer3, int integer4, int integer5, List<bre> list, Random random, boolean boolean8) {
		if (list.contains(this.f)) {
			return boolean8
				? new fu(integer1, integer2, integer3)
				: new fu(integer1 - integer4 + random.nextInt(integer4 * 2 + 1), integer2, integer3 - integer4 + random.nextInt(integer4 * 2 + 1));
		} else {
			return null;
		}
	}

	@Override
	public Set<bre> a(int integer1, int integer2, int integer3, int integer4) {
		return Sets.<bre>newHashSet(this.f);
	}
}
