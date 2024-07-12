import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;

public final class deo extends dfg {
	private final DoubleList b;
	private final DoubleList c;
	private final DoubleList d;

	protected deo(dev dev, double[] arr2, double[] arr3, double[] arr4) {
		this(
			dev,
			DoubleArrayList.wrap(Arrays.copyOf(arr2, dev.b() + 1)),
			DoubleArrayList.wrap(Arrays.copyOf(arr3, dev.c() + 1)),
			DoubleArrayList.wrap(Arrays.copyOf(arr4, dev.d() + 1))
		);
	}

	deo(dev dev, DoubleList doubleList2, DoubleList doubleList3, DoubleList doubleList4) {
		super(dev);
		int integer6 = dev.b() + 1;
		int integer7 = dev.c() + 1;
		int integer8 = dev.d() + 1;
		if (integer6 == doubleList2.size() && integer7 == doubleList3.size() && integer8 == doubleList4.size()) {
			this.b = doubleList2;
			this.c = doubleList3;
			this.d = doubleList4;
		} else {
			throw (IllegalArgumentException)v.c(new IllegalArgumentException("Lengths of point arrays must be consistent with the size of the VoxelShape."));
		}
	}

	@Override
	protected DoubleList a(fz.a a) {
		switch (a) {
			case X:
				return this.b;
			case Y:
				return this.c;
			case Z:
				return this.d;
			default:
				throw new IllegalArgumentException();
		}
	}
}
