import com.google.common.collect.Lists;
import java.util.List;

public class axm {
	private final aoz a;
	private final List<aom> b = Lists.<aom>newArrayList();
	private final List<aom> c = Lists.<aom>newArrayList();

	public axm(aoz aoz) {
		this.a = aoz;
	}

	public void a() {
		this.b.clear();
		this.c.clear();
	}

	public boolean a(aom aom) {
		if (this.b.contains(aom)) {
			return true;
		} else if (this.c.contains(aom)) {
			return false;
		} else {
			this.a.l.X().a("canSee");
			boolean boolean3 = this.a.D(aom);
			this.a.l.X().c();
			if (boolean3) {
				this.b.add(aom);
			} else {
				this.c.add(aom);
			}

			return boolean3;
		}
	}
}
