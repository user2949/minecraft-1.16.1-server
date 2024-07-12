import java.util.List;
import java.util.function.Predicate;

public class auc extends aug {
	private final ayj a;
	private int b;
	private int c;

	public auc(ayj ayj) {
		this.a = ayj;
		this.c = this.a(ayj);
	}

	protected int a(ayj ayj) {
		return 200 + ayj.cX().nextInt(200) % 20;
	}

	@Override
	public boolean a() {
		if (this.a.eS()) {
			return false;
		} else if (this.a.eP()) {
			return true;
		} else if (this.c > 0) {
			this.c--;
			return false;
		} else {
			this.c = this.a(this.a);
			Predicate<ayj> predicate2 = ayj -> ayj.eR() || !ayj.eP();
			List<ayj> list3 = this.a.l.a(this.a.getClass(), this.a.cb().c(8.0, 8.0, 8.0), predicate2);
			ayj ayj4 = (ayj)list3.stream().filter(ayj::eR).findAny().orElse(this.a);
			ayj4.a(list3.stream().filter(ayj -> !ayj.eP()));
			return this.a.eP();
		}
	}

	@Override
	public boolean b() {
		return this.a.eP() && this.a.eT();
	}

	@Override
	public void c() {
		this.b = 0;
	}

	@Override
	public void d() {
		this.a.eQ();
	}

	@Override
	public void e() {
		if (--this.b <= 0) {
			this.b = 10;
			this.a.eU();
		}
	}
}
