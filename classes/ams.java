import com.google.common.collect.Queues;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public interface ams<T, F> {
	@Nullable
	F a();

	boolean a(T object);

	boolean b();

	public static final class a implements ams<ams.b, Runnable> {
		private final List<Queue<Runnable>> a;

		public a(int integer) {
			this.a = (List<Queue<Runnable>>)IntStream.range(0, integer).mapToObj(integerx -> Queues.newConcurrentLinkedQueue()).collect(Collectors.toList());
		}

		@Nullable
		public Runnable a() {
			for (Queue<Runnable> queue3 : this.a) {
				Runnable runnable4 = (Runnable)queue3.poll();
				if (runnable4 != null) {
					return runnable4;
				}
			}

			return null;
		}

		public boolean a(ams.b b) {
			int integer3 = b.a();
			((Queue)this.a.get(integer3)).add(b);
			return true;
		}

		@Override
		public boolean b() {
			return this.a.stream().allMatch(Collection::isEmpty);
		}
	}

	public static final class b implements Runnable {
		private final int a;
		private final Runnable b;

		public b(int integer, Runnable runnable) {
			this.a = integer;
			this.b = runnable;
		}

		public void run() {
			this.b.run();
		}

		public int a() {
			return this.a;
		}
	}

	public static final class c<T> implements ams<T, T> {
		private final Queue<T> a;

		public c(Queue<T> queue) {
			this.a = queue;
		}

		@Nullable
		@Override
		public T a() {
			return (T)this.a.poll();
		}

		@Override
		public boolean a(T object) {
			return this.a.add(object);
		}

		@Override
		public boolean b() {
			return this.a.isEmpty();
		}
	}
}
