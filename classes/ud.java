import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;

public final class ud<E> implements Codec<Supplier<E>> {
	private final ug<gl<E>> a;
	private final MapCodec<E> b;

	public static <E> ud<E> a(ug<gl<E>> ug, MapCodec<E> mapCodec) {
		return new ud<>(ug, mapCodec);
	}

	private ud(ug<gl<E>> ug, MapCodec<E> mapCodec) {
		this.a = ug;
		this.b = mapCodec;
	}

	public <T> DataResult<T> encode(Supplier<E> supplier, DynamicOps<T> dynamicOps, T object) {
		return dynamicOps instanceof uf ? ((uf)dynamicOps).a(supplier.get(), object, this.a, this.b) : this.b.codec().encode((E)supplier.get(), dynamicOps, object);
	}

	@Override
	public <T> DataResult<Pair<Supplier<E>, T>> decode(DynamicOps<T> dynamicOps, T object) {
		return dynamicOps instanceof ue
			? ((ue)dynamicOps).a(object, this.a, this.b)
			: this.b.codec().decode(dynamicOps, object).map(pair -> pair.mapFirst(objectx -> () -> objectx));
	}

	public String toString() {
		return "RegistryFileCodec[" + this.a + " " + this.b + "]";
	}
}
