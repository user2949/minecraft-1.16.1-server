package com.mojang.datafixers.functions;

import com.google.common.collect.Maps;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.types.families.Algebra;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.types.templates.RecursivePoint.RecursivePointType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

final class Fold<A, B> extends PointFree<Function<A, B>> {
	private static final Map<Pair<RecursiveTypeFamily, Algebra>, IntFunction<RewriteResult<?, ?>>> HMAP_CACHE = Maps.<Pair<RecursiveTypeFamily, Algebra>, IntFunction<RewriteResult<?, ?>>>newConcurrentMap(
		
	);
	private static final Map<Pair<IntFunction<RewriteResult<?, ?>>, Integer>, RewriteResult<?, ?>> HMAP_APPLY_CACHE = Maps.<Pair<IntFunction<RewriteResult<?, ?>>, Integer>, RewriteResult<?, ?>>newConcurrentMap(
		
	);
	protected final RecursivePointType<A> aType;
	protected final RewriteResult<?, B> function;
	protected final Algebra algebra;
	protected final int index;

	public Fold(RecursivePointType<A> aType, RewriteResult<?, B> function, Algebra algebra, int index) {
		this.aType = aType;
		this.function = function;
		this.algebra = algebra;
		this.index = index;
	}

	private <FB> PointFree<Function<A, B>> cap(RewriteResult<?, B> op, RewriteResult<?, FB> resResult) {
		return Functions.comp(resResult.view().newType(), (PointFree<Function<FB, B>>)op.view().function(), (PointFree<Function<A, FB>>)resResult.view().function());
	}

	@Override
	public Function<DynamicOps<?>, Function<A, B>> eval() {
		return ops -> a -> {
				RecursiveTypeFamily family = this.aType.family();
				IntFunction<RewriteResult<?, ?>> hmapped = (IntFunction<RewriteResult<?, ?>>)HMAP_CACHE.computeIfAbsent(
					Pair.of(family, this.algebra),
					key -> ((RecursiveTypeFamily)key.getFirst())
							.template()
							.hmap((TypeFamily)key.getFirst(), ((RecursiveTypeFamily)key.getFirst()).fold((Algebra)key.getSecond()))
				);
				RewriteResult<?, ?> result = (RewriteResult<?, ?>)HMAP_APPLY_CACHE.computeIfAbsent(
					Pair.of(hmapped, this.index), key -> (RewriteResult)((IntFunction)key.getFirst()).apply((Integer)key.getSecond())
				);
				PointFree<Function<A, B>> eval = this.cap(this.function, result);
				return ((Function)eval.evalCached().apply(ops)).apply(a);
			};
	}

	@Override
	public String toString(int level) {
		return "fold(" + this.aType + ", " + this.index + ", \n" + indent(level + 1) + this.algebra.toString(level + 1) + "\n" + indent(level) + ")";
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			Fold<?, ?> fold = (Fold<?, ?>)o;
			return Objects.equals(this.aType, fold.aType) && Objects.equals(this.algebra, fold.algebra);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.aType, this.algebra});
	}
}
