import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

public class cfj extends cfi.a {
	public static final Codec<cfj> b = a(gl.aj, bvr::n).stable();

	public cfj(bvr bvr, ImmutableMap<cgl<?>, Comparable<?>> immutableMap, MapCodec<cfj> mapCodec) {
		super(bvr, immutableMap, mapCodec);
	}

	@Override
	protected cfj p() {
		return this;
	}
}
