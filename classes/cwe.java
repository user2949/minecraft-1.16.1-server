import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public class cwe implements cwh {
	private final cwc[] a;
	private final double b;
	private final double c;

	public cwe(ciy ciy, IntStream intStream) {
		this(ciy, (List<Integer>)intStream.boxed().collect(ImmutableList.toImmutableList()));
	}

	public cwe(ciy ciy, List<Integer> list) {
		this(ciy, new IntRBTreeSet(list));
	}

	private cwe(ciy ciy, IntSortedSet intSortedSet) {
		if (intSortedSet.isEmpty()) {
			throw new IllegalArgumentException("Need some octaves!");
		} else {
			int integer4 = -intSortedSet.firstInt();
			int integer5 = intSortedSet.lastInt();
			int integer6 = integer4 + integer5 + 1;
			if (integer6 < 1) {
				throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
			} else {
				cwc cwc7 = new cwc(ciy);
				int integer8 = integer5;
				this.a = new cwc[integer6];
				if (integer5 >= 0 && integer5 < integer6 && intSortedSet.contains(0)) {
					this.a[integer5] = cwc7;
				}

				for (int integer9 = integer5 + 1; integer9 < integer6; integer9++) {
					if (integer9 >= 0 && intSortedSet.contains(integer8 - integer9)) {
						this.a[integer9] = new cwc(ciy);
					} else {
						ciy.a(262);
					}
				}

				if (integer5 > 0) {
					long long9 = (long)(cwc7.a(0.0, 0.0, 0.0, 0.0, 0.0) * 9.223372E18F);
					ciy ciy11 = new ciy(long9);

					for (int integer12 = integer8 - 1; integer12 >= 0; integer12--) {
						if (integer12 < integer6 && intSortedSet.contains(integer8 - integer12)) {
							this.a[integer12] = new cwc(ciy11);
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

	public double a(double double1, double double2, double double3) {
		return this.a(double1, double2, double3, 0.0, 0.0, false);
	}

	public double a(double double1, double double2, double double3, double double4, double double5, boolean boolean6) {
		double double13 = 0.0;
		double double15 = this.c;
		double double17 = this.b;

		for (cwc cwc22 : this.a) {
			if (cwc22 != null) {
				double13 += cwc22.a(a(double1 * double15), boolean6 ? -cwc22.b : a(double2 * double15), a(double3 * double15), double4 * double15, double5 * double15)
					* double17;
			}

			double15 /= 2.0;
			double17 *= 2.0;
		}

		return double13;
	}

	@Nullable
	public cwc a(int integer) {
		return this.a[integer];
	}

	public static double a(double double1) {
		return double1 - (double)aec.d(double1 / 3.3554432E7 + 0.5) * 3.3554432E7;
	}

	@Override
	public double a(double double1, double double2, double double3, double double4) {
		return this.a(double1, double2, 0.0, double3, double4, false);
	}
}
