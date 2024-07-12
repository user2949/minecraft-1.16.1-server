import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import java.util.stream.IntStream;

public class cwf implements cwh {
	private final cwg[] a;
	private final double b;
	private final double c;

	public cwf(ciy ciy, IntStream intStream) {
		this(ciy, (List<Integer>)intStream.boxed().collect(ImmutableList.toImmutableList()));
	}

	public cwf(ciy ciy, List<Integer> list) {
		this(ciy, new IntRBTreeSet(list));
	}

	private cwf(ciy ciy, IntSortedSet intSortedSet) {
		if (intSortedSet.isEmpty()) {
			throw new IllegalArgumentException("Need some octaves!");
		} else {
			int integer4 = -intSortedSet.firstInt();
			int integer5 = intSortedSet.lastInt();
			int integer6 = integer4 + integer5 + 1;
			if (integer6 < 1) {
				throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
			} else {
				cwg cwg7 = new cwg(ciy);
				int integer8 = integer5;
				this.a = new cwg[integer6];
				if (integer5 >= 0 && integer5 < integer6 && intSortedSet.contains(0)) {
					this.a[integer5] = cwg7;
				}

				for (int integer9 = integer5 + 1; integer9 < integer6; integer9++) {
					if (integer9 >= 0 && intSortedSet.contains(integer8 - integer9)) {
						this.a[integer9] = new cwg(ciy);
					} else {
						ciy.a(262);
					}
				}

				if (integer5 > 0) {
					long long9 = (long)(cwg7.a(cwg7.b, cwg7.c, cwg7.d) * 9.223372E18F);
					ciy ciy11 = new ciy(long9);

					for (int integer12 = integer8 - 1; integer12 >= 0; integer12--) {
						if (integer12 < integer6 && intSortedSet.contains(integer8 - integer12)) {
							this.a[integer12] = new cwg(ciy11);
						} else {
							ciy11.a(262);
						}
					}
				}

				this.c = Math.pow(2.0, (double)integer5);
				this.b = 1.0 / (Math.pow(2.0, (double)integer6) - 1.0);
			}
		}
	}

	public double a(double double1, double double2, boolean boolean3) {
		double double7 = 0.0;
		double double9 = this.c;
		double double11 = this.b;

		for (cwg cwg16 : this.a) {
			if (cwg16 != null) {
				double7 += cwg16.a(double1 * double9 + (boolean3 ? cwg16.b : 0.0), double2 * double9 + (boolean3 ? cwg16.c : 0.0)) * double11;
			}

			double9 /= 2.0;
			double11 *= 2.0;
		}

		return double7;
	}

	@Override
	public double a(double double1, double double2, double double3, double double4) {
		return this.a(double1, double2, true) * 0.55;
	}
}
