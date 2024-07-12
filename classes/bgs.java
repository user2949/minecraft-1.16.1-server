import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public interface bgs {
	bgs a = new bgs() {
		@Override
		public <T> Optional<T> a(BiFunction<bqb, fu, T> biFunction) {
			return Optional.empty();
		}
	};

	static bgs a(bqb bqb, fu fu) {
		return new bgs() {
			@Override
			public <T> Optional<T> a(BiFunction<bqb, fu, T> biFunction) {
				return Optional.of(biFunction.apply(bqb, fu));
			}
		};
	}

	<T> Optional<T> a(BiFunction<bqb, fu, T> biFunction);

	default <T> T a(BiFunction<bqb, fu, T> biFunction, T object) {
		return (T)this.a(biFunction).orElse(object);
	}

	default void a(BiConsumer<bqb, fu> biConsumer) {
		this.a((BiFunction)((bqb, fu) -> {
			biConsumer.accept(bqb, fu);
			return Optional.empty();
		}));
	}
}
