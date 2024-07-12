import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;

public class aco {
	protected final Set<uh> a = Sets.<uh>newHashSet();
	protected final Set<uh> b = Sets.<uh>newHashSet();
	protected boolean c;
	protected boolean d;
	protected boolean e;
	protected boolean f;
	protected boolean g;
	protected boolean h;
	protected boolean i;
	protected boolean j;

	public void a(aco aco) {
		this.a.clear();
		this.b.clear();
		this.c = aco.c;
		this.d = aco.d;
		this.e = aco.e;
		this.f = aco.f;
		this.g = aco.g;
		this.h = aco.h;
		this.i = aco.i;
		this.j = aco.j;
		this.a.addAll(aco.a);
		this.b.addAll(aco.b);
	}

	public void a(bmu<?> bmu) {
		if (!bmu.ah_()) {
			this.a(bmu.f());
		}
	}

	protected void a(uh uh) {
		this.a.add(uh);
	}

	public boolean b(@Nullable bmu<?> bmu) {
		return bmu == null ? false : this.a.contains(bmu.f());
	}

	public boolean b(uh uh) {
		return this.a.contains(uh);
	}

	protected void c(uh uh) {
		this.a.remove(uh);
		this.b.remove(uh);
	}

	public void e(bmu<?> bmu) {
		this.b.remove(bmu.f());
	}

	public void f(bmu<?> bmu) {
		this.d(bmu.f());
	}

	protected void d(uh uh) {
		this.b.add(uh);
	}

	public void a(boolean boolean1) {
		this.c = boolean1;
	}

	public void b(boolean boolean1) {
		this.d = boolean1;
	}

	public void c(boolean boolean1) {
		this.e = boolean1;
	}

	public void d(boolean boolean1) {
		this.f = boolean1;
	}

	public void e(boolean boolean1) {
		this.g = boolean1;
	}

	public void f(boolean boolean1) {
		this.h = boolean1;
	}

	public void g(boolean boolean1) {
		this.i = boolean1;
	}

	public void h(boolean boolean1) {
		this.j = boolean1;
	}
}
