public class aea {
	public static long a(long long1, long long2) {
		long1 *= long1 * 6364136223846793005L + 1442695040888963407L;
		return long1 + long2;
	}
}
