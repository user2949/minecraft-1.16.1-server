import java.util.function.BiFunction;
import java.util.function.Consumer;

public interface dch extends dau, BiFunction<bki, dat, bki> {
	dci b();

	static Consumer<bki> a(BiFunction<bki, dat, bki> biFunction, Consumer<bki> consumer, dat dat) {
		return bki -> consumer.accept(biFunction.apply(bki, dat));
	}

	public interface a {
		dch b();
	}
}
