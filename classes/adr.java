public class adr {
	public static class a {
		public static int b(int integer) {
			return integer >> 16 & 0xFF;
		}

		public static int c(int integer) {
			return integer >> 8 & 0xFF;
		}

		public static int d(int integer) {
			return integer & 0xFF;
		}
	}
}
