import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.stream.IntStream;

public class cwd {
	private final double a;
	private final cwe b;
	private final cwe c;

	public cwd(ciy ciy, IntStream intStream) {
		this(ciy, (List<Integer>)intStream.boxed().collect(ImmutableList.toImmutableList()));
	}

	public cwd(ciy ciy, List<Integer> list) {
		this.b = new cwe(ciy, list);
		this.c = new cwe(ciy, list);
		int integer4 = (Integer)list.stream().min(Integer::compareTo).orElse(0);
		int integer5 = (Integer)list.stream().max(Integer::compareTo).orElse(0);
		this.a = 0.16666666666666666 / a(integer5 - integer4);
	}

	private static double a(int integer) {
		return 0.1 * (1.0 + 1.0 / (double)(integer + 1));
	}

	public double a(double double1, double double2, double double3) {
		double double8 = double1 * 1.0181268882175227;
		double double10 = double2 * 1.0181268882175227;
		double double12 = double3 * 1.0181268882175227;
		return (this.b.a(double1, double2, double3) + this.c.a(double8, double10, double12)) * this.a;
	}
}
