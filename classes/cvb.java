import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class cvb {
	private bzj a = bzj.NONE;
	private cap b = cap.NONE;
	private fu c = fu.b;
	private boolean d;
	@Nullable
	private bph e;
	@Nullable
	private ctd f;
	private boolean g = true;
	@Nullable
	private Random h;
	@Nullable
	private int i;
	private final List<cvc> j = Lists.<cvc>newArrayList();
	private boolean k;
	private boolean l;

	public cvb a() {
		cvb cvb2 = new cvb();
		cvb2.a = this.a;
		cvb2.b = this.b;
		cvb2.c = this.c;
		cvb2.d = this.d;
		cvb2.e = this.e;
		cvb2.f = this.f;
		cvb2.g = this.g;
		cvb2.h = this.h;
		cvb2.i = this.i;
		cvb2.j.addAll(this.j);
		cvb2.k = this.k;
		cvb2.l = this.l;
		return cvb2;
	}

	public cvb a(bzj bzj) {
		this.a = bzj;
		return this;
	}

	public cvb a(cap cap) {
		this.b = cap;
		return this;
	}

	public cvb a(fu fu) {
		this.c = fu;
		return this;
	}

	public cvb a(boolean boolean1) {
		this.d = boolean1;
		return this;
	}

	public cvb a(bph bph) {
		this.e = bph;
		return this;
	}

	public cvb a(ctd ctd) {
		this.f = ctd;
		return this;
	}

	public cvb a(@Nullable Random random) {
		this.h = random;
		return this;
	}

	public cvb c(boolean boolean1) {
		this.k = boolean1;
		return this;
	}

	public cvb b() {
		this.j.clear();
		return this;
	}

	public cvb a(cvc cvc) {
		this.j.add(cvc);
		return this;
	}

	public cvb b(cvc cvc) {
		this.j.remove(cvc);
		return this;
	}

	public bzj c() {
		return this.a;
	}

	public cap d() {
		return this.b;
	}

	public fu e() {
		return this.c;
	}

	public Random b(@Nullable fu fu) {
		if (this.h != null) {
			return this.h;
		} else {
			return fu == null ? new Random(v.b()) : new Random(aec.a(fu));
		}
	}

	public boolean g() {
		return this.d;
	}

	@Nullable
	public ctd h() {
		if (this.f == null && this.e != null) {
			this.k();
		}

		return this.f;
	}

	public boolean i() {
		return this.k;
	}

	public List<cvc> j() {
		return this.j;
	}

	void k() {
		if (this.e != null) {
			this.f = this.b(this.e);
		}
	}

	public boolean l() {
		return this.g;
	}

	public cve.a a(List<cve.a> list, @Nullable fu fu) {
		int integer4 = list.size();
		if (integer4 == 0) {
			throw new IllegalStateException("No palettes");
		} else {
			return (cve.a)list.get(this.b(fu).nextInt(integer4));
		}
	}

	@Nullable
	private ctd b(@Nullable bph bph) {
		if (bph == null) {
			return this.f;
		} else {
			int integer3 = bph.b * 16;
			int integer4 = bph.c * 16;
			return new ctd(integer3, 0, integer4, integer3 + 16 - 1, 255, integer4 + 16 - 1);
		}
	}

	public cvb d(boolean boolean1) {
		this.l = boolean1;
		return this;
	}

	public boolean m() {
		return this.l;
	}
}
