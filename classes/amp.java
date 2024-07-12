import com.mojang.datafixers.util.Either;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public interface amp<Msg> extends AutoCloseable {
	String bh();

	void a(Msg object);

	default void close() {
	}

	default <Source> CompletableFuture<Source> b(Function<? super amp<Source>, ? extends Msg> function) {
		CompletableFuture<Source> completableFuture3 = new CompletableFuture();
		Msg object4 = (Msg)function.apply(a("ask future procesor handle", completableFuture3::complete));
		this.a(object4);
		return completableFuture3;
	}

	default <Source> CompletableFuture<Source> c(Function<? super amp<Either<Source, Exception>>, ? extends Msg> function) {
		CompletableFuture<Source> completableFuture3 = new CompletableFuture();
		Msg object4 = (Msg)function.apply(a("ask future procesor handle", either -> {
			either.ifLeft(completableFuture3::complete);
			either.ifRight(completableFuture3::completeExceptionally);
		}));
		this.a(object4);
		return completableFuture3;
	}

	static <Msg> amp<Msg> a(String string, Consumer<Msg> consumer) {
		return new amp<Msg>() {
			@Override
			public String bh() {
				return string;
			}

			@Override
			public void a(Msg object) {
				consumer.accept(object);
			}

			public String toString() {
				return string;
			}
		};
	}
}
