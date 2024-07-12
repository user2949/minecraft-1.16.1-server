import javax.annotation.Nullable;

public interface bqh {
	boolean a(fu fu, cfj cfj, int integer3, int integer4);

	default boolean a(fu fu, cfj cfj, int integer) {
		return this.a(fu, cfj, integer, 512);
	}

	boolean a(fu fu, boolean boolean2);

	default boolean b(fu fu, boolean boolean2) {
		return this.a(fu, boolean2, null);
	}

	default boolean a(fu fu, boolean boolean2, @Nullable aom aom) {
		return this.a(fu, boolean2, aom, 512);
	}

	boolean a(fu fu, boolean boolean2, @Nullable aom aom, int integer);

	default boolean c(aom aom) {
		return false;
	}
}
