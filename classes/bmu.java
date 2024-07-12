public interface bmu<C extends amz> {
	boolean a(C amz, bqb bqb);

	bki a(C amz);

	bki c();

	default gi<bki> b(C amz) {
		gi<bki> gi3 = gi.a(amz.ab_(), bki.b);

		for (int integer4 = 0; integer4 < gi3.size(); integer4++) {
			bke bke5 = amz.a(integer4).b();
			if (bke5.p()) {
				gi3.set(integer4, new bki(bke5.o()));
			}
		}

		return gi3;
	}

	default gi<bmr> a() {
		return gi.a();
	}

	default boolean ah_() {
		return false;
	}

	uh f();

	bmw<?> ai_();

	bmx<?> g();
}
