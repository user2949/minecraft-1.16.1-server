import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public abstract class ci<T extends aj> implements ad<T> {
	private final Map<uq, Set<ad.a<T>>> a = Maps.<uq, Set<ad.a<T>>>newIdentityHashMap();

	@Override
	public final void a(uq uq, ad.a<T> a) {
		((Set)this.a.computeIfAbsent(uq, uqx -> Sets.newHashSet())).add(a);
	}

	@Override
	public final void b(uq uq, ad.a<T> a) {
		Set<ad.a<T>> set4 = (Set<ad.a<T>>)this.a.get(uq);
		if (set4 != null) {
			set4.remove(a);
			if (set4.isEmpty()) {
				this.a.remove(uq);
			}
		}
	}

	@Override
	public final void a(uq uq) {
		this.a.remove(uq);
	}

	protected abstract T b(JsonObject jsonObject, be.b b, av av);

	public final T a(JsonObject jsonObject, av av) {
		be.b b4 = be.b.a(jsonObject, "player", av);
		return this.b(jsonObject, b4, av);
	}

	protected void a(ze ze, Predicate<T> predicate) {
		uq uq4 = ze.J();
		Set<ad.a<T>> set5 = (Set<ad.a<T>>)this.a.get(uq4);
		if (set5 != null && !set5.isEmpty()) {
			dat dat6 = be.b(ze, ze);
			List<ad.a<T>> list7 = null;

			for (ad.a<T> a9 : set5) {
				T aj10 = a9.a();
				if (aj10.b().a(dat6) && predicate.test(aj10)) {
					if (list7 == null) {
						list7 = Lists.<ad.a<T>>newArrayList();
					}

					list7.add(a9);
				}
			}

			if (list7 != null) {
				for (ad.a<T> a9x : list7) {
					a9x.a(uq4);
				}
			}
		}
	}
}
