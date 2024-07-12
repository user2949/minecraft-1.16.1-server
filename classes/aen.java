import java.util.List;
import java.util.Random;

public class aen {
	public static int a(List<? extends aen.a> list) {
		int integer2 = 0;
		int integer3 = 0;

		for (int integer4 = list.size(); integer3 < integer4; integer3++) {
			aen.a a5 = (aen.a)list.get(integer3);
			integer2 += a5.a;
		}

		return integer2;
	}

	public static <T extends aen.a> T a(Random random, List<T> list, int integer) {
		if (integer <= 0) {
			throw (IllegalArgumentException)v.c((T)(new IllegalArgumentException()));
		} else {
			int integer4 = random.nextInt(integer);
			return a(list, integer4);
		}
	}

	public static <T extends aen.a> T a(List<T> list, int integer) {
		int integer3 = 0;

		for (int integer4 = list.size(); integer3 < integer4; integer3++) {
			T a5 = (T)list.get(integer3);
			integer -= a5.a;
			if (integer < 0) {
				return a5;
			}
		}

		return null;
	}

	public static <T extends aen.a> T a(Random random, List<T> list) {
		return a(random, list, a(list));
	}

	public static class a {
		protected final int a;

		public a(int integer) {
			this.a = integer;
		}
	}
}
