import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

public class cvi extends cvq {
	private static final cfj a = bvs.cO.n();
	private static final cfj b = bvs.np.n();
	private static final cfj c = bvs.E.n();
	private static final ImmutableList<cfj> d = ImmutableList.of(a, b);
	private static final ImmutableList<cfj> e = ImmutableList.of(a);

	public cvi(Codec<cvx> codec) {
		super(codec);
	}

	@Override
	protected ImmutableList<cfj> a() {
		return d;
	}

	@Override
	protected ImmutableList<cfj> b() {
		return e;
	}

	@Override
	protected cfj c() {
		return c;
	}
}
