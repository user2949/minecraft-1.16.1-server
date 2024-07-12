import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public final class deu implements dey {
	private final des a;
	private final int b;
	private final int c;
	private final int d;

	deu(int integer1, int integer2) {
		this.a = new des((int)dfd.a(integer1, integer2));
		this.b = integer1;
		this.c = integer2;
		this.d = IntMath.gcd(integer1, integer2);
	}

	@Override
	public boolean a(dey.a a) {
		int integer3 = this.b / this.d;
		int integer4 = this.c / this.d;

		for (int integer5 = 0; integer5 <= this.a.size(); integer5++) {
			if (!a.merge(integer5 / integer4, integer5 / integer3, integer5)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public DoubleList a() {
		return this.a;
	}
}
