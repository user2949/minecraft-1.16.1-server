import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class ez {
	private final int a;
	private final boolean b;
	private final boolean c;
	private final Predicate<aom> d;
	private final bx.c e;
	private final Function<dem, dem> f;
	@Nullable
	private final deg g;
	private final BiConsumer<dem, List<? extends aom>> h;
	private final boolean i;
	@Nullable
	private final String j;
	@Nullable
	private final UUID k;
	@Nullable
	private final aoq<?> l;
	private final boolean m;

	public ez(
		int integer,
		boolean boolean2,
		boolean boolean3,
		Predicate<aom> predicate,
		bx.c c,
		Function<dem, dem> function,
		@Nullable deg deg,
		BiConsumer<dem, List<? extends aom>> biConsumer,
		boolean boolean9,
		@Nullable String string,
		@Nullable UUID uUID,
		@Nullable aoq<?> aoq,
		boolean boolean13
	) {
		this.a = integer;
		this.b = boolean2;
		this.c = boolean3;
		this.d = predicate;
		this.e = c;
		this.f = function;
		this.g = deg;
		this.h = biConsumer;
		this.i = boolean9;
		this.j = string;
		this.k = uUID;
		this.l = aoq;
		this.m = boolean13;
	}

	public int a() {
		return this.a;
	}

	public boolean b() {
		return this.b;
	}

	public boolean c() {
		return this.i;
	}

	public boolean d() {
		return this.c;
	}

	private void e(cz cz) throws CommandSyntaxException {
		if (this.m && !cz.c(2)) {
			throw dh.f.create();
		}
	}

	public aom a(cz cz) throws CommandSyntaxException {
		this.e(cz);
		List<? extends aom> list3 = this.b(cz);
		if (list3.isEmpty()) {
			throw dh.d.create();
		} else if (list3.size() > 1) {
			throw dh.a.create();
		} else {
			return (aom)list3.get(0);
		}
	}

	public List<? extends aom> b(cz cz) throws CommandSyntaxException {
		this.e(cz);
		if (!this.b) {
			return this.d(cz);
		} else if (this.j != null) {
			ze ze3 = cz.j().ac().a(this.j);
			return (List<? extends aom>)(ze3 == null ? Collections.emptyList() : Lists.newArrayList(ze3));
		} else if (this.k != null) {
			for (zd zd4 : cz.j().F()) {
				aom aom5 = zd4.a(this.k);
				if (aom5 != null) {
					return Lists.newArrayList(aom5);
				}
			}

			return Collections.emptyList();
		} else {
			dem dem3 = (dem)this.f.apply(cz.d());
			Predicate<aom> predicate4 = this.a(dem3);
			if (this.i) {
				return (List<? extends aom>)(cz.f() != null && predicate4.test(cz.f()) ? Lists.newArrayList(cz.f()) : Collections.emptyList());
			} else {
				List<aom> list5 = Lists.<aom>newArrayList();
				if (this.d()) {
					this.a(list5, cz.e(), dem3, predicate4);
				} else {
					for (zd zd7 : cz.j().F()) {
						this.a(list5, zd7, dem3, predicate4);
					}
				}

				return this.a(dem3, list5);
			}
		}
	}

	private void a(List<aom> list, zd zd, dem dem, Predicate<aom> predicate) {
		if (this.g != null) {
			list.addAll(zd.a(this.l, this.g.c(dem), predicate));
		} else {
			list.addAll(zd.a(this.l, predicate));
		}
	}

	public ze c(cz cz) throws CommandSyntaxException {
		this.e(cz);
		List<ze> list3 = this.d(cz);
		if (list3.size() != 1) {
			throw dh.e.create();
		} else {
			return (ze)list3.get(0);
		}
	}

	public List<ze> d(cz cz) throws CommandSyntaxException {
		this.e(cz);
		if (this.j != null) {
			ze ze3 = cz.j().ac().a(this.j);
			return (List<ze>)(ze3 == null ? Collections.emptyList() : Lists.<ze>newArrayList(ze3));
		} else if (this.k != null) {
			ze ze3 = cz.j().ac().a(this.k);
			return (List<ze>)(ze3 == null ? Collections.emptyList() : Lists.<ze>newArrayList(ze3));
		} else {
			dem dem3 = (dem)this.f.apply(cz.d());
			Predicate<aom> predicate4 = this.a(dem3);
			if (this.i) {
				if (cz.f() instanceof ze) {
					ze ze5 = (ze)cz.f();
					if (predicate4.test(ze5)) {
						return Lists.<ze>newArrayList(ze5);
					}
				}

				return Collections.emptyList();
			} else {
				List<ze> list5;
				if (this.d()) {
					list5 = cz.e().a(predicate4::test);
				} else {
					list5 = Lists.<ze>newArrayList();

					for (ze ze7 : cz.j().ac().s()) {
						if (predicate4.test(ze7)) {
							list5.add(ze7);
						}
					}
				}

				return this.a(dem3, list5);
			}
		}
	}

	private Predicate<aom> a(dem dem) {
		Predicate<aom> predicate3 = this.d;
		if (this.g != null) {
			deg deg4 = this.g.c(dem);
			predicate3 = predicate3.and(aom -> deg4.c(aom.cb()));
		}

		if (!this.e.c()) {
			predicate3 = predicate3.and(aom -> this.e.a(aom.d(dem)));
		}

		return predicate3;
	}

	private <T extends aom> List<T> a(dem dem, List<T> list) {
		if (list.size() > 1) {
			this.h.accept(dem, list);
		}

		return list.subList(0, Math.min(this.a, list.size()));
	}

	public static mx a(List<? extends aom> list) {
		return ms.b(list, aom::d);
	}
}
