import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;

public final class uc<E> implements Codec<gh<E>> {
	private final Codec<gh<E>> a;
	private final ug<gl<E>> b;
	private final MapCodec<E> c;

	public static <E> uc<E> a(ug<gl<E>> ug, Lifecycle lifecycle, MapCodec<E> mapCodec) {
		return new uc<>(ug, lifecycle, mapCodec);
	}

	private uc(ug<gl<E>> ug, Lifecycle lifecycle, MapCodec<E> mapCodec) {
		this.a = gh.c(ug, lifecycle, mapCodec);
		this.b = ug;
		this.c = mapCodec;
	}

	public <T> DataResult<T> encode(gh<E> gh, DynamicOps<T> dynamicOps, T object) {
		return this.a.encode(gh, dynamicOps, object);
	}

	@Override
	public <T> DataResult<Pair<gh<E>, T>> decode(DynamicOps<T> dynamicOps, T object) {
		DataResult<Pair<gh<E>, T>> dataResult4 = this.a.decode(dynamicOps, object);
		return dynamicOps instanceof ue
			? dataResult4.flatMap(pair -> ((ue)dynamicOps).a((gh<E>)pair.getFirst(), this.b, this.c).map(gh -> Pair.of(gh, pair.getSecond())))
			: dataResult4;
	}

	public String toString() {
		return "RegistryDapaPackCodec[" + this.a + " " + this.b + " " + this.c + "]";
	}
}
