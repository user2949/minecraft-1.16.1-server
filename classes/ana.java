import java.util.List;
import java.util.function.Predicate;

public class ana {
	public static bki a(List<bki> list, int integer2, int integer3) {
		return integer2 >= 0 && integer2 < list.size() && !((bki)list.get(integer2)).a() && integer3 > 0 ? ((bki)list.get(integer2)).a(integer3) : bki.b;
	}

	public static bki a(List<bki> list, int integer) {
		return integer >= 0 && integer < list.size() ? (bki)list.set(integer, bki.b) : bki.b;
	}

	public static le a(le le, gi<bki> gi) {
		return a(le, gi, true);
	}

	public static le a(le le, gi<bki> gi, boolean boolean3) {
		lk lk4 = new lk();

		for (int integer5 = 0; integer5 < gi.size(); integer5++) {
			bki bki6 = gi.get(integer5);
			if (!bki6.a()) {
				le le7 = new le();
				le7.a("Slot", (byte)integer5);
				bki6.b(le7);
				lk4.add(le7);
			}
		}

		if (!lk4.isEmpty() || boolean3) {
			le.a("Items", lk4);
		}

		return le;
	}

	public static void b(le le, gi<bki> gi) {
		lk lk3 = le.d("Items", 10);

		for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
			le le5 = lk3.a(integer4);
			int integer6 = le5.f("Slot") & 255;
			if (integer6 >= 0 && integer6 < gi.size()) {
				gi.set(integer6, bki.a(le5));
			}
		}
	}

	public static int a(amz amz, Predicate<bki> predicate, int integer, boolean boolean4) {
		int integer5 = 0;

		for (int integer6 = 0; integer6 < amz.ab_(); integer6++) {
			bki bki7 = amz.a(integer6);
			int integer8 = a(bki7, predicate, integer - integer5, boolean4);
			if (integer8 > 0 && !boolean4 && bki7.a()) {
				amz.a(integer6, bki.b);
			}

			integer5 += integer8;
		}

		return integer5;
	}

	public static int a(bki bki, Predicate<bki> predicate, int integer, boolean boolean4) {
		if (bki.a() || !predicate.test(bki)) {
			return 0;
		} else if (boolean4) {
			return bki.E();
		} else {
			int integer5 = integer < 0 ? bki.E() : Math.min(integer, bki.E());
			bki.g(integer5);
			return integer5;
		}
	}
}
