import com.google.common.collect.Lists;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class aee<T> {
	private final List<T> a = Lists.<T>newArrayList();
	private final Spliterator<T> b;

	public aee(Stream<T> stream) {
		this.b = stream.spliterator();
	}

	public Stream<T> a() {
		return StreamSupport.stream(new AbstractSpliterator<T>(Long.MAX_VALUE, 0) {
			private int b;

			public boolean tryAdvance(Consumer<? super T> consumer) {
				while (this.b >= aee.this.a.size()) {
					if (!aee.this.b.tryAdvance(aee.this.a::add)) {
						return false;
					}
				}

				consumer.accept(aee.this.a.get(this.b++));
				return true;
			}
		}, false);
	}
}
