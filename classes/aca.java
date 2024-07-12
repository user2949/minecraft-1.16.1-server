import java.nio.charset.StandardCharsets;

public class aca {
	public static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	public static String a(byte[] arr, int integer2, int integer3) {
		int integer4 = integer3 - 1;
		int integer5 = integer2 > integer4 ? integer4 : integer2;

		while (0 != arr[integer5] && integer5 < integer4) {
			integer5++;
		}

		return new String(arr, integer2, integer5 - integer2, StandardCharsets.UTF_8);
	}

	public static int a(byte[] arr, int integer) {
		return b(arr, integer, arr.length);
	}

	public static int b(byte[] arr, int integer2, int integer3) {
		return 0 > integer3 - integer2 - 4 ? 0 : arr[integer2 + 3] << 24 | (arr[integer2 + 2] & 0xFF) << 16 | (arr[integer2 + 1] & 0xFF) << 8 | arr[integer2] & 0xFF;
	}

	public static int c(byte[] arr, int integer2, int integer3) {
		return 0 > integer3 - integer2 - 4 ? 0 : arr[integer2] << 24 | (arr[integer2 + 1] & 0xFF) << 16 | (arr[integer2 + 2] & 0xFF) << 8 | arr[integer2 + 3] & 0xFF;
	}

	public static String a(byte byte1) {
		return "" + a[(byte1 & 240) >>> 4] + a[byte1 & 15];
	}
}
