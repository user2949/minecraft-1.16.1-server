import java.util.Objects;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class bpl extends AbstractSpliterator<dfg> {
	@Nullable
	private final aom a;
	private final deg b;
	private final der c;
	private final fx d;
	private final fu.a e;
	private final dfg f;
	private final bpk g;
	private boolean h;
	private final BiPredicate<cfj, fu> i;

	public bpl(bpk bpk, @Nullable aom aom, deg deg) {
		this(bpk, aom, deg, (cfj, fu) -> true);
	}

	public bpl(bpk bpk, @Nullable aom aom, deg deg, BiPredicate<cfj, fu> biPredicate) {
		super(Long.MAX_VALUE, 1280);
		this.c = aom == null ? der.a() : der.a(aom);
		this.e = new fu.a();
		this.f = dfd.a(deg);
		this.g = bpk;
		this.h = aom != null;
		this.a = aom;
		this.b = deg;
		this.i = biPredicate;
		int integer6 = aec.c(deg.a - 1.0E-7) - 1;
		int integer7 = aec.c(deg.d + 1.0E-7) + 1;
		int integer8 = aec.c(deg.b - 1.0E-7) - 1;
		int integer9 = aec.c(deg.e + 1.0E-7) + 1;
		int integer10 = aec.c(deg.c - 1.0E-7) - 1;
		int integer11 = aec.c(deg.f + 1.0E-7) + 1;
		this.d = new fx(integer6, integer8, integer10, integer7, integer9, integer11);
	}

	public boolean tryAdvance(Consumer<? super dfg> consumer) {
		return this.h && this.b(consumer) || this.a(consumer);
	}

	boolean a(Consumer<? super dfg> consumer) {
		while (this.d.a()) {
			int integer3 = this.d.b();
			int integer4 = this.d.c();
			int integer5 = this.d.d();
			int integer6 = this.d.e();
			if (integer6 != 3) {
				bpg bpg7 = this.a(integer3, integer5);
				if (bpg7 != null) {
					this.e.d(integer3, integer4, integer5);
					cfj cfj8 = bpg7.d_(this.e);
					if (this.i.test(cfj8, this.e) && (integer6 != 1 || cfj8.d()) && (integer6 != 2 || cfj8.a(bvs.bo))) {
						dfg dfg9 = cfj8.b(this.g, this.e, this.c);
						if (dfg9 == dfd.b()) {
							if (this.b.a((double)integer3, (double)integer4, (double)integer5, (double)integer3 + 1.0, (double)integer4 + 1.0, (double)integer5 + 1.0)) {
								consumer.accept(dfg9.a((double)integer3, (double)integer4, (double)integer5));
								return true;
							}
						} else {
							dfg dfg10 = dfg9.a((double)integer3, (double)integer4, (double)integer5);
							if (dfd.c(dfg10, this.f, deq.i)) {
								consumer.accept(dfg10);
								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}

	@Nullable
	private bpg a(int integer1, int integer2) {
		int integer4 = integer1 >> 4;
		int integer5 = integer2 >> 4;
		return this.g.c(integer4, integer5);
	}

	boolean b(Consumer<? super dfg> consumer) {
		Objects.requireNonNull(this.a);
		this.h = false;
		cgw cgw3 = this.g.f();
		deg deg4 = this.a.cb();
		if (!a(cgw3, deg4)) {
			dfg dfg5 = cgw3.c();
			if (!b(dfg5, deg4) && a(dfg5, deg4)) {
				consumer.accept(dfg5);
				return true;
			}
		}

		return false;
	}

	private static boolean a(dfg dfg, deg deg) {
		return dfd.c(dfg, dfd.a(deg.g(1.0E-7)), deq.i);
	}

	private static boolean b(dfg dfg, deg deg) {
		return dfd.c(dfg, dfd.a(deg.h(1.0E-7)), deq.i);
	}

	public static boolean a(cgw cgw, deg deg) {
		double double3 = (double)aec.c(cgw.e());
		double double5 = (double)aec.c(cgw.f());
		double double7 = (double)aec.f(cgw.g());
		double double9 = (double)aec.f(cgw.h());
		return deg.a > double3 && deg.a < double7 && deg.c > double5 && deg.c < double9 && deg.d > double3 && deg.d < double7 && deg.f > double5 && deg.f < double9;
	}
}
