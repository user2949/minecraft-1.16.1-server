import java.util.List;
import javax.annotation.Nullable;

public class czf {
	private final List<czd> a;
	private czd[] b = new czd[0];
	private czd[] c = new czd[0];
	private int e;
	private final fu f;
	private final float g;
	private final boolean h;

	public czf(List<czd> list, fu fu, boolean boolean3) {
		this.a = list;
		this.f = fu;
		this.g = list.isEmpty() ? Float.MAX_VALUE : ((czd)this.a.get(this.a.size() - 1)).c(this.f);
		this.h = boolean3;
	}

	public void a() {
		this.e++;
	}

	public boolean b() {
		return this.e >= this.a.size();
	}

	@Nullable
	public czd c() {
		return !this.a.isEmpty() ? (czd)this.a.get(this.a.size() - 1) : null;
	}

	public czd a(int integer) {
		return (czd)this.a.get(integer);
	}

	public List<czd> d() {
		return this.a;
	}

	public void b(int integer) {
		if (this.a.size() > integer) {
			this.a.subList(integer, this.a.size()).clear();
		}
	}

	public void a(int integer, czd czd) {
		this.a.set(integer, czd);
	}

	public int e() {
		return this.a.size();
	}

	public int f() {
		return this.e;
	}

	public void c(int integer) {
		this.e = integer;
	}

	public dem a(aom aom, int integer) {
		czd czd4 = (czd)this.a.get(integer);
		double double5 = (double)czd4.a + (double)((int)(aom.cx() + 1.0F)) * 0.5;
		double double7 = (double)czd4.b;
		double double9 = (double)czd4.c + (double)((int)(aom.cx() + 1.0F)) * 0.5;
		return new dem(double5, double7, double9);
	}

	public dem a(aom aom) {
		return this.a(aom, this.e);
	}

	public gr g() {
		czd czd2 = this.h();
		return new gr(czd2.a, czd2.b, czd2.c);
	}

	public czd h() {
		return (czd)this.a.get(this.e);
	}

	public boolean a(@Nullable czf czf) {
		if (czf == null) {
			return false;
		} else if (czf.a.size() != this.a.size()) {
			return false;
		} else {
			for (int integer3 = 0; integer3 < this.a.size(); integer3++) {
				czd czd4 = (czd)this.a.get(integer3);
				czd czd5 = (czd)czf.a.get(integer3);
				if (czd4.a != czd5.a || czd4.b != czd5.b || czd4.c != czd5.c) {
					return false;
				}
			}

			return true;
		}
	}

	public boolean i() {
		return this.h;
	}

	public String toString() {
		return "Path(length=" + this.a.size() + ")";
	}

	public fu m() {
		return this.f;
	}

	public float n() {
		return this.g;
	}
}
