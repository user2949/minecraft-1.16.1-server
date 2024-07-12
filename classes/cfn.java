import java.util.function.Predicate;
import javax.annotation.Nullable;

public class cfn {
	private final bqd a;
	private final fu b;
	private final boolean c;
	private cfj d;
	private cdl e;
	private boolean f;

	public cfn(bqd bqd, fu fu, boolean boolean3) {
		this.a = bqd;
		this.b = fu.h();
		this.c = boolean3;
	}

	public cfj a() {
		if (this.d == null && (this.c || this.a.C(this.b))) {
			this.d = this.a.d_(this.b);
		}

		return this.d;
	}

	@Nullable
	public cdl b() {
		if (this.e == null && !this.f) {
			this.e = this.a.c(this.b);
			this.f = true;
		}

		return this.e;
	}

	public bqd c() {
		return this.a;
	}

	public fu d() {
		return this.b;
	}

	public static Predicate<cfn> a(Predicate<cfj> predicate) {
		return cfn -> cfn != null && predicate.test(cfn.a());
	}
}
