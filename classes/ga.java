import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Set;

public enum ga {
	NORTH(fz.NORTH),
	NORTH_EAST(fz.NORTH, fz.EAST),
	EAST(fz.EAST),
	SOUTH_EAST(fz.SOUTH, fz.EAST),
	SOUTH(fz.SOUTH),
	SOUTH_WEST(fz.SOUTH, fz.WEST),
	WEST(fz.WEST),
	NORTH_WEST(fz.NORTH, fz.WEST);

	private static final int i = 1 << NORTH_WEST.ordinal();
	private static final int j = 1 << WEST.ordinal();
	private static final int k = 1 << SOUTH_WEST.ordinal();
	private static final int l = 1 << SOUTH.ordinal();
	private static final int m = 1 << SOUTH_EAST.ordinal();
	private static final int n = 1 << EAST.ordinal();
	private static final int o = 1 << NORTH_EAST.ordinal();
	private static final int p = 1 << NORTH.ordinal();
	private final Set<fz> q;

	private ga(fz... arr) {
		this.q = Sets.immutableEnumSet(Arrays.asList(arr));
	}

	public Set<fz> a() {
		return this.q;
	}
}
