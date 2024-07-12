import java.util.EnumSet;
import javax.annotation.Nullable;

public class avx extends aug {
	private final aug a;
	private final int b;
	private boolean c;

	public avx(int integer, aug aug) {
		this.b = integer;
		this.a = aug;
	}

	public boolean a(avx avx) {
		return this.D_() && avx.h() < this.h();
	}

	@Override
	public boolean a() {
		return this.a.a();
	}

	@Override
	public boolean b() {
		return this.a.b();
	}

	@Override
	public boolean D_() {
		return this.a.D_();
	}

	@Override
	public void c() {
		if (!this.c) {
			this.c = true;
			this.a.c();
		}
	}

	@Override
	public void d() {
		if (this.c) {
			this.c = false;
			this.a.d();
		}
	}

	@Override
	public void e() {
		this.a.e();
	}

	@Override
	public void a(EnumSet<aug.a> enumSet) {
		this.a.a(enumSet);
	}

	@Override
	public EnumSet<aug.a> i() {
		return this.a.i();
	}

	public boolean g() {
		return this.c;
	}

	public int h() {
		return this.b;
	}

	public aug j() {
		return this.a;
	}

	public boolean equals(@Nullable Object object) {
		if (this == object) {
			return true;
		} else {
			return object != null && this.getClass() == object.getClass() ? this.a.equals(((avx)object).a) : false;
		}
	}

	public int hashCode() {
		return this.a.hashCode();
	}
}
