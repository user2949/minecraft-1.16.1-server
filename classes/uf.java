import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import java.util.Optional;

public class uf<T> extends ub<T> {
	private final gm b;

	public static <T> uf<T> a(DynamicOps<T> dynamicOps, gm gm) {
		return new uf<>(dynamicOps, gm);
	}

	private uf(DynamicOps<T> dynamicOps, gm gm) {
		super(dynamicOps);
		this.b = gm;
	}

	protected <E> DataResult<T> a(E object1, T object2, ug<gl<E>> ug, MapCodec<E> mapCodec) {
		Optional<gs<E>> optional6 = this.b.a(ug);
		if (optional6.isPresent()) {
			gs<E> gs7 = (gs<E>)optional6.get();
			Optional<ug<E>> optional8 = gs7.c(object1);
			if (optional8.isPresent()) {
				ug<E> ug9 = (ug<E>)optional8.get();
				if (gs7.c(ug9)) {
					return adl.a(ug, mapCodec).codec().encode(Pair.of(ug9, object1), this.a, object2);
				}

				return uh.a.encode(ug9.a(), this.a, object2);
			}
		}

		return mapCodec.codec().encode(object1, this.a, object2);
	}
}
