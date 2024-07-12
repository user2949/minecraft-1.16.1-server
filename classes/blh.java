import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;

public class blh extends bke {
	private static final Map<aoq<?>, blh> a = Maps.<aoq<?>, blh>newIdentityHashMap();
	private final int b;
	private final int c;
	private final aoq<?> d;

	public blh(aoq<?> aoq, int integer2, int integer3, bke.a a) {
		super(a);
		this.d = aoq;
		this.b = integer2;
		this.c = integer3;
		blh.a.put(aoq, this);
	}

	@Override
	public ang a(blv blv) {
		bqb bqb3 = blv.o();
		if (bqb3.v) {
			return ang.SUCCESS;
		} else {
			bki bki4 = blv.l();
			fu fu5 = blv.a();
			fz fz6 = blv.i();
			cfj cfj7 = bqb3.d_(fu5);
			if (cfj7.a(bvs.bP)) {
				cdl cdl8 = bqb3.c(fu5);
				if (cdl8 instanceof cek) {
					bpd bpd9 = ((cek)cdl8).d();
					aoq<?> aoq10 = this.a(bki4.o());
					bpd9.a(aoq10);
					cdl8.Z_();
					bqb3.a(fu5, cfj7, cfj7, 3);
					bki4.g(1);
					return ang.CONSUME;
				}
			}

			fu fu8;
			if (cfj7.k(bqb3, fu5).b()) {
				fu8 = fu5;
			} else {
				fu8 = fu5.a(fz6);
			}

			aoq<?> aoq9 = this.a(bki4.o());
			if (aoq9.a(bqb3, bki4, blv.m(), fu8, apb.SPAWN_EGG, true, !Objects.equals(fu5, fu8) && fz6 == fz.UP) != null) {
				bki4.g(1);
			}

			return ang.CONSUME;
		}
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		dej dej6 = a(bqb, bec, bpj.b.SOURCE_ONLY);
		if (dej6.c() != dej.a.BLOCK) {
			return anh.c(bki5);
		} else if (bqb.v) {
			return anh.a(bki5);
		} else {
			deh deh7 = (deh)dej6;
			fu fu8 = deh7.a();
			if (!(bqb.d_(fu8).b() instanceof bze)) {
				return anh.c(bki5);
			} else if (bqb.a(bec, fu8) && bec.a(fu8, deh7.b(), bki5)) {
				aoq<?> aoq9 = this.a(bki5.o());
				if (aoq9.a(bqb, bki5, bec, fu8, apb.SPAWN_EGG, false, false) == null) {
					return anh.c(bki5);
				} else {
					if (!bec.bJ.d) {
						bki5.g(1);
					}

					bec.b(acu.c.b(this));
					return anh.b(bki5);
				}
			} else {
				return anh.d(bki5);
			}
		}
	}

	public boolean a(@Nullable le le, aoq<?> aoq) {
		return Objects.equals(this.a(le), aoq);
	}

	public static Iterable<blh> f() {
		return Iterables.unmodifiableIterable(a.values());
	}

	public aoq<?> a(@Nullable le le) {
		if (le != null && le.c("EntityTag", 10)) {
			le le3 = le.p("EntityTag");
			if (le3.c("id", 8)) {
				return (aoq<?>)aoq.a(le3.l("id")).orElse(this.d);
			}
		}

		return this.d;
	}

	public Optional<aoz> a(bec bec, aoz aoz, aoq<? extends aoz> aoq, bqb bqb, dem dem, bki bki) {
		if (!this.a(bki.o(), aoq)) {
			return Optional.empty();
		} else {
			aoz aoz8;
			if (aoz instanceof aok) {
				aoz8 = ((aok)aoz).a((aok)aoz);
			} else {
				aoz8 = aoq.a(bqb);
			}

			if (aoz8 == null) {
				return Optional.empty();
			} else {
				aoz8.a(true);
				if (!aoz8.x_()) {
					return Optional.empty();
				} else {
					aoz8.b(dem.a(), dem.b(), dem.c(), 0.0F, 0.0F);
					bqb.c(aoz8);
					if (bki.t()) {
						aoz8.a(bki.r());
					}

					if (!bec.bJ.d) {
						bki.g(1);
					}

					return Optional.of(aoz8);
				}
			}
		}
	}
}
