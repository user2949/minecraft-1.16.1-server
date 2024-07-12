import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;

public class dfk extends dfo {
	private final dfm a;
	private final String b;
	private final Set<String> c = Sets.<String>newHashSet();
	private mr d;
	private mr e;
	private mr f;
	private boolean g;
	private boolean h;
	private dfo.b i;
	private dfo.b j;
	private i k;
	private dfo.a l;
	private final nb m;

	public dfk(dfm dfm, String string) {
		this.e = nd.d;
		this.f = nd.d;
		this.g = true;
		this.h = true;
		this.i = dfo.b.ALWAYS;
		this.j = dfo.b.ALWAYS;
		this.k = i.RESET;
		this.l = dfo.a.ALWAYS;
		this.a = dfm;
		this.b = string;
		this.d = new nd(string);
		this.m = nb.b.a(string).a(new mv(mv.a.a, new nd(string)));
	}

	@Override
	public String b() {
		return this.b;
	}

	public mr c() {
		return this.d;
	}

	public mx d() {
		mx mx2 = ms.a((mr)this.d.e().c(this.m));
		i i3 = this.n();
		if (i3 != i.RESET) {
			mx2.a(i3);
		}

		return mx2;
	}

	public void a(mr mr) {
		if (mr == null) {
			throw new IllegalArgumentException("Name cannot be null");
		} else {
			this.d = mr;
			this.a.b(this);
		}
	}

	public void b(@Nullable mr mr) {
		this.e = mr == null ? nd.d : mr;
		this.a.b(this);
	}

	public mr e() {
		return this.e;
	}

	public void c(@Nullable mr mr) {
		this.f = mr == null ? nd.d : mr;
		this.a.b(this);
	}

	public mr f() {
		return this.f;
	}

	@Override
	public Collection<String> g() {
		return this.c;
	}

	@Override
	public mx d(mr mr) {
		mx mx3 = new nd("").a(this.e).a(mr).a(this.f);
		i i4 = this.n();
		if (i4 != i.RESET) {
			mx3.a(i4);
		}

		return mx3;
	}

	public static mx a(@Nullable dfo dfo, mr mr) {
		return dfo == null ? mr.e() : dfo.d(mr);
	}

	@Override
	public boolean h() {
		return this.g;
	}

	public void a(boolean boolean1) {
		this.g = boolean1;
		this.a.b(this);
	}

	public boolean i() {
		return this.h;
	}

	public void b(boolean boolean1) {
		this.h = boolean1;
		this.a.b(this);
	}

	public dfo.b j() {
		return this.i;
	}

	@Override
	public dfo.b k() {
		return this.j;
	}

	public void a(dfo.b b) {
		this.i = b;
		this.a.b(this);
	}

	public void b(dfo.b b) {
		this.j = b;
		this.a.b(this);
	}

	@Override
	public dfo.a l() {
		return this.l;
	}

	public void a(dfo.a a) {
		this.l = a;
		this.a.b(this);
	}

	public int m() {
		int integer2 = 0;
		if (this.h()) {
			integer2 |= 1;
		}

		if (this.i()) {
			integer2 |= 2;
		}

		return integer2;
	}

	public void a(i i) {
		this.k = i;
		this.a.b(this);
	}

	public i n() {
		return this.k;
	}
}
