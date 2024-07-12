import com.google.common.collect.ImmutableSet;
import java.util.Set;

public interface dau {
	default Set<dcx<?>> a() {
		return ImmutableSet.of();
	}

	default void a(dbe dbe) {
		dbe.a(this);
	}
}
