import java.util.Set;

public interface amz extends amx {
	int ab_();

	boolean c();

	bki a(int integer);

	bki a(int integer1, int integer2);

	bki b(int integer);

	void a(int integer, bki bki);

	default int X_() {
		return 64;
	}

	void Z_();

	boolean a(bec bec);

	default void c_(bec bec) {
	}

	default void b_(bec bec) {
	}

	default boolean b(int integer, bki bki) {
		return true;
	}

	default int a(bke bke) {
		int integer3 = 0;

		for (int integer4 = 0; integer4 < this.ab_(); integer4++) {
			bki bki5 = this.a(integer4);
			if (bki5.b().equals(bke)) {
				integer3 += bki5.E();
			}
		}

		return integer3;
	}

	default boolean a(Set<bke> set) {
		for (int integer3 = 0; integer3 < this.ab_(); integer3++) {
			bki bki4 = this.a(integer3);
			if (set.contains(bki4.b()) && bki4.E() > 0) {
				return true;
			}
		}

		return false;
	}
}
