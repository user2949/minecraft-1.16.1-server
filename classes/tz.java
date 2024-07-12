import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Iterator;

public class tz<C extends amz> extends ty<C> {
	private boolean e;

	public tz(bhp<C> bhp) {
		super(bhp);
	}

	@Override
	protected void a(bmu<C> bmu, boolean boolean2) {
		this.e = this.d.a(bmu);
		int integer4 = this.b.b(bmu, null);
		if (this.e) {
			bki bki5 = this.d.a(0).e();
			if (bki5.a() || integer4 <= bki5.E()) {
				return;
			}
		}

		int integer5 = this.a(boolean2, integer4, this.e);
		IntList intList6 = new IntArrayList();
		if (this.b.a(bmu, intList6, integer5)) {
			if (!this.e) {
				this.a(this.d.f());
				this.a(0);
			}

			this.a(integer5, intList6);
		}
	}

	@Override
	protected void a() {
		this.a(this.d.f());
		super.a();
	}

	protected void a(int integer, IntList intList) {
		Iterator<Integer> iterator4 = intList.iterator();
		bhw bhw5 = this.d.a(0);
		bki bki6 = bee.a((Integer)iterator4.next());
		if (!bki6.a()) {
			int integer7 = Math.min(bki6.c(), integer);
			if (this.e) {
				integer7 -= bhw5.e().E();
			}

			for (int integer8 = 0; integer8 < integer7; integer8++) {
				this.a(bhw5, bki6);
			}
		}
	}
}
