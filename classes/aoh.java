public final class aoh {
	public static boolean a(aoy aoy) {
		return aoy.a(aoi.c) || aoy.a(aoi.C);
	}

	public static int b(aoy aoy) {
		int integer2 = 0;
		int integer3 = 0;
		if (aoy.a(aoi.c)) {
			integer2 = aoy.b(aoi.c).c();
		}

		if (aoy.a(aoi.C)) {
			integer3 = aoy.b(aoi.C).c();
		}

		return Math.max(integer2, integer3);
	}

	public static boolean c(aoy aoy) {
		return aoy.a(aoi.m) || aoy.a(aoi.C);
	}
}
