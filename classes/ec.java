import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class ec implements Predicate<cfn> {
	private final cfj a;
	private final Set<cgl<?>> b;
	@Nullable
	private final le c;

	public ec(cfj cfj, Set<cgl<?>> set, @Nullable le le) {
		this.a = cfj;
		this.b = set;
		this.c = le;
	}

	public cfj a() {
		return this.a;
	}

	public boolean test(cfn cfn) {
		cfj cfj3 = cfn.a();
		if (!cfj3.a(this.a.b())) {
			return false;
		} else {
			for (cgl<?> cgl5 : this.b) {
				if (cfj3.c(cgl5) != this.a.c(cgl5)) {
					return false;
				}
			}

			if (this.c == null) {
				return true;
			} else {
				cdl cdl4 = cfn.b();
				return cdl4 != null && lq.a(this.c, cdl4.a(new le()), true);
			}
		}
	}

	public boolean a(zd zd, fu fu, int integer) {
		cfj cfj5 = bvr.b(this.a, (bqc)zd, fu);
		if (cfj5.g()) {
			cfj5 = this.a;
		}

		if (!zd.a(fu, cfj5, integer)) {
			return false;
		} else {
			if (this.c != null) {
				cdl cdl6 = zd.c(fu);
				if (cdl6 != null) {
					le le7 = this.c.g();
					le7.b("x", fu.u());
					le7.b("y", fu.v());
					le7.b("z", fu.w());
					cdl6.a(cfj5, le7);
				}
			}

			return true;
		}
	}
}
