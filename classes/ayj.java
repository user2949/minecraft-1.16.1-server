import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public abstract class ayj extends ayh {
	private ayj b;
	private int c = 1;

	public ayj(aoq<? extends ayj> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void o() {
		super.o();
		this.br.a(5, new auc(this));
	}

	@Override
	public int er() {
		return this.eO();
	}

	public int eO() {
		return super.er();
	}

	@Override
	protected boolean eM() {
		return !this.eP();
	}

	public boolean eP() {
		return this.b != null && this.b.aU();
	}

	public ayj a(ayj ayj) {
		this.b = ayj;
		ayj.eV();
		return ayj;
	}

	public void eQ() {
		this.b.eW();
		this.b = null;
	}

	private void eV() {
		this.c++;
	}

	private void eW() {
		this.c--;
	}

	public boolean eR() {
		return this.eS() && this.c < this.eO();
	}

	@Override
	public void j() {
		super.j();
		if (this.eS() && this.l.t.nextInt(200) == 1) {
			List<ayh> list2 = this.l.a(this.getClass(), this.cb().c(8.0, 8.0, 8.0));
			if (list2.size() <= 1) {
				this.c = 1;
			}
		}
	}

	public boolean eS() {
		return this.c > 1;
	}

	public boolean eT() {
		return this.h(this.b) <= 121.0;
	}

	public void eU() {
		if (this.eP()) {
			this.x().a(this.b, 1.0);
		}
	}

	public void a(Stream<ayj> stream) {
		stream.limit((long)(this.eO() - this.c)).filter(ayj -> ayj != this).forEach(ayj -> ayj.a(this));
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		super.a(bqc, ane, apb, apo, le);
		if (apo == null) {
			apo = new ayj.a(this);
		} else {
			this.a(((ayj.a)apo).a);
		}

		return apo;
	}

	public static class a implements apo {
		public final ayj a;

		public a(ayj ayj) {
			this.a = ayj;
		}
	}
}
