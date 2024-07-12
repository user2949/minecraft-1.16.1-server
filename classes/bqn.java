import com.google.common.collect.Lists;
import java.util.List;

public class bqn {
	private final List<bqn.a> a = Lists.<bqn.a>newArrayList();

	public void a(fu fu, double double2) {
		if (double2 != 0.0) {
			this.a.add(new bqn.a(fu, double2));
		}
	}

	public double b(fu fu, double double2) {
		if (double2 == 0.0) {
			return 0.0;
		} else {
			double double5 = 0.0;

			for (bqn.a a8 : this.a) {
				double5 += a8.a(fu);
			}

			return double5 * double2;
		}
	}

	static class a {
		private final fu a;
		private final double b;

		public a(fu fu, double double2) {
			this.a = fu;
			this.b = double2;
		}

		public double a(fu fu) {
			double double3 = this.a.j(fu);
			return double3 == 0.0 ? Double.POSITIVE_INFINITY : this.b / Math.sqrt(double3);
		}
	}
}
