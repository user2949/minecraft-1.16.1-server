import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
interface dbg {
	dbg a = (dat, consumer) -> false;
	dbg b = (dat, consumer) -> true;

	boolean expand(dat dat, Consumer<dbn> consumer);

	default dbg a(dbg dbg) {
		Objects.requireNonNull(dbg);
		return (dat, consumer) -> this.expand(dat, consumer) && dbg.expand(dat, consumer);
	}

	default dbg b(dbg dbg) {
		Objects.requireNonNull(dbg);
		return (dat, consumer) -> this.expand(dat, consumer) || dbg.expand(dat, consumer);
	}
}
