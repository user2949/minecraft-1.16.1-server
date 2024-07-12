import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.UUID;

public final class gp {
	public static final Codec<UUID> a = Codec.INT_STREAM.comapFlatMap(intStream -> v.a(intStream, 4).map(gp::a), uUID -> Arrays.stream(a(uUID)));

	public static UUID a(int[] arr) {
		return new UUID((long)arr[0] << 32 | (long)arr[1] & 4294967295L, (long)arr[2] << 32 | (long)arr[3] & 4294967295L);
	}

	public static int[] a(UUID uUID) {
		long long2 = uUID.getMostSignificantBits();
		long long4 = uUID.getLeastSignificantBits();
		return a(long2, long4);
	}

	private static int[] a(long long1, long long2) {
		return new int[]{(int)(long1 >> 32), (int)long1, (int)(long2 >> 32), (int)long2};
	}
}
