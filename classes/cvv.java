import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

public class cvv extends cvq {
	private static final cfj a = bvs.cM.n();
	private static final cfj b = bvs.cN.n();
	private static final cfj c = bvs.E.n();
	private static final ImmutableList<cfj> d = ImmutableList.of(a, b);

	public cvv(Codec<cvx> codec) {
		super(codec);
	}

	@Override
	protected ImmutableList<cfj> a() {
		return d;
	}

	@Override
	protected ImmutableList<cfj> b() {
		return d;
	}

	@Override
	protected cfj c() {
		return c;
	}
}
