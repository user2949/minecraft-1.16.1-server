package com.mojang.datafixers.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.View;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.Optic.CompositionOptic;
import com.mojang.datafixers.types.Func;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.constant.EmptyPart;
import com.mojang.datafixers.types.families.Algebra;
import com.mojang.datafixers.types.families.ListAlgebra;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.commons.lang3.ObjectUtils;

public interface PointFreeRule {
	<A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> pointFree);

	default <A, B> Optional<View<A, B>> rewrite(View<A, B> view) {
		return this.rewrite(view.getFuncType(), view.function()).map(pf -> View.create(view.type(), view.newType(), pf));
	}

	default <A> PointFree<A> rewriteOrNop(Type<A> type, PointFree<A> expr) {
		return DataFixUtils.orElse(this.rewrite(type, expr), expr);
	}

	default <A, B> View<A, B> rewriteOrNop(View<A, B> view) {
		return DataFixUtils.orElse(this.rewrite(view), view);
	}

	static PointFreeRule nop() {
		return PointFreeRule.Nop.INSTANCE;
	}

	static PointFreeRule seq(PointFreeRule first, Supplier<PointFreeRule> second) {
		return seq(ImmutableList.of(() -> first, second));
	}

	static PointFreeRule seq(List<Supplier<PointFreeRule>> rules) {
		return new PointFreeRule.Seq(rules);
	}

	static PointFreeRule orElse(PointFreeRule first, PointFreeRule second) {
		return new PointFreeRule.OrElse(first, () -> second);
	}

	static PointFreeRule orElseStrict(PointFreeRule first, Supplier<PointFreeRule> second) {
		return new PointFreeRule.OrElse(first, second);
	}

	static PointFreeRule all(PointFreeRule rule) {
		return new PointFreeRule.All(rule);
	}

	static PointFreeRule one(PointFreeRule rule) {
		return new PointFreeRule.One(rule);
	}

	static PointFreeRule once(PointFreeRule rule) {
		return orElseStrict(rule, () -> one(once(rule)));
	}

	static PointFreeRule many(PointFreeRule rule) {
		return new PointFreeRule.Many(rule);
	}

	static PointFreeRule everywhere(PointFreeRule rule) {
		return seq(orElse(rule, PointFreeRule.Nop.INSTANCE), () -> all(everywhere(rule)));
	}

	public static final class All implements PointFreeRule {
		private final PointFreeRule rule;

		public All(PointFreeRule rule) {
			this.rule = rule;
		}

		@Override
		public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> expr) {
			return expr.all(this.rule, type);
		}

		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			} else if (!(obj instanceof PointFreeRule.All)) {
				return false;
			} else {
				PointFreeRule.All that = (PointFreeRule.All)obj;
				return Objects.equals(this.rule, that.rule);
			}
		}

		public int hashCode() {
			return this.rule.hashCode();
		}
	}

	public static enum AppNest implements PointFreeRule {
		INSTANCE;

		@Override
		public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> expr) {
			if (expr instanceof Apply) {
				Apply<?, ?> applyFirst = (Apply<?, ?>)expr;
				if (applyFirst.arg instanceof Apply) {
					Apply<?, ?> applySecond = (Apply<?, ?>)applyFirst.arg;
					return this.cap(applyFirst, applySecond);
				}
			}

			return Optional.empty();
		}

		private <A, B, C, D, E> Optional<? extends PointFree<A>> cap(Apply<D, E> applyFirst, Apply<B, C> applySecond) {
			PointFree<?> func = applySecond.func;
			return Optional.of(
				Functions.app(
					Functions.comp(applyFirst.argType, applyFirst.func, (PointFree<Function<A, D>>)func), (PointFree<A>)applySecond.arg, (Type<A>)applySecond.argType
				)
			);
		}
	}

	public static enum BangEta implements PointFreeRule {
		INSTANCE;

		@Override
		public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> expr) {
			if (expr instanceof Bang) {
				return Optional.empty();
			} else {
				if (type instanceof Func) {
					Func<?, ?> func = (Func<?, ?>)type;
					if (func.second() instanceof EmptyPart) {
						return Optional.of(Functions.bang());
					}
				}

				return Optional.empty();
			}
		}
	}

	public static enum CataFuseDifferent implements PointFreeRule.CompRewrite {
		INSTANCE;

		@Override
		public <A> Optional<? extends PointFree<?>> doRewrite(
			Type<A> type, Type<?> middleType, PointFree<? extends Function<?, ?>> first, PointFree<? extends Function<?, ?>> second
		) {
			if (first instanceof Fold && second instanceof Fold) {
				Fold<?, ?> firstFold = (Fold<?, ?>)first;
				Fold<?, ?> secondFold = (Fold<?, ?>)second;
				RecursiveTypeFamily family = firstFold.aType.family();
				if (Objects.equals(family, secondFold.aType.family()) && firstFold.index == secondFold.index) {
					List<RewriteResult<?, ?>> newAlgebra = Lists.<RewriteResult<?, ?>>newArrayList();
					BitSet firstModifies = new BitSet(family.size());
					BitSet secondModifies = new BitSet(family.size());

					for (int i = 0; i < family.size(); i++) {
						RewriteResult<?, ?> firstAlgFunc = firstFold.algebra.apply(i);
						RewriteResult<?, ?> secondAlgFunc = secondFold.algebra.apply(i);
						boolean firstId = Objects.equals(PointFreeRule.CompAssocRight.INSTANCE.rewriteOrNop(firstAlgFunc.view()).function(), Functions.id());
						boolean secondId = Objects.equals(secondAlgFunc.view().function(), Functions.id());
						firstModifies.set(i, !firstId);
						secondModifies.set(i, !secondId);
					}

					BitSet newSet = ObjectUtils.clone(firstModifies);
					newSet.or(secondModifies);

					for (int i = 0; i < family.size(); i++) {
						RewriteResult<?, ?> firstAlgFunc = firstFold.algebra.apply(i);
						RewriteResult<?, ?> secondAlgFunc = secondFold.algebra.apply(i);
						PointFree<?> firstF = PointFreeRule.CompAssocRight.INSTANCE.rewriteOrNop(firstAlgFunc.view()).function();
						PointFree<?> secondF = PointFreeRule.CompAssocRight.INSTANCE.rewriteOrNop(secondAlgFunc.view()).function();
						boolean firstId = Objects.equals(firstF, Functions.id());
						boolean secondId = Objects.equals(secondF, Functions.id());
						if (firstAlgFunc.recData().intersects(secondModifies) || secondAlgFunc.recData().intersects(firstModifies)) {
							return Optional.empty();
						}

						if (firstId) {
							newAlgebra.add(secondAlgFunc);
						} else {
							if (!secondId) {
								return Optional.empty();
							}

							newAlgebra.add(firstAlgFunc);
						}
					}

					Algebra algebra = new ListAlgebra("FusedDifferent", newAlgebra);
					return Optional.of(((RewriteResult)family.fold(algebra).apply(firstFold.index)).view().function());
				}
			}

			return Optional.empty();
		}
	}

	public static enum CataFuseSame implements PointFreeRule.CompRewrite {
		INSTANCE;

		@Override
		public <A> Optional<? extends PointFree<?>> doRewrite(
			Type<A> type, Type<?> middleType, PointFree<? extends Function<?, ?>> first, PointFree<? extends Function<?, ?>> second
		) {
			if (first instanceof Fold && second instanceof Fold) {
				Fold<?, ?> firstFold = (Fold<?, ?>)first;
				Fold<?, ?> secondFold = (Fold<?, ?>)second;
				RecursiveTypeFamily family = firstFold.aType.family();
				if (Objects.equals(family, secondFold.aType.family()) && firstFold.index == secondFold.index) {
					List<RewriteResult<?, ?>> newAlgebra = Lists.<RewriteResult<?, ?>>newArrayList();
					boolean foundOne = false;

					for (int i = 0; i < family.size(); i++) {
						RewriteResult<?, ?> firstAlgFunc = firstFold.algebra.apply(i);
						RewriteResult<?, ?> secondAlgFunc = secondFold.algebra.apply(i);
						boolean firstId = Objects.equals(PointFreeRule.CompAssocRight.INSTANCE.rewriteOrNop(firstAlgFunc.view()).function(), Functions.id());
						boolean secondId = Objects.equals(secondAlgFunc.view().function(), Functions.id());
						if (firstId && secondId) {
							newAlgebra.add(firstFold.algebra.apply(i));
						} else {
							if (foundOne || firstId || secondId) {
								return Optional.empty();
							}

							newAlgebra.add(this.getCompose(firstAlgFunc, secondAlgFunc));
							foundOne = true;
						}
					}

					Algebra algebra = new ListAlgebra("FusedSame", newAlgebra);
					return Optional.of(((RewriteResult)family.fold(algebra).apply(firstFold.index)).view().function());
				}
			}

			return Optional.empty();
		}

		private <B> RewriteResult<?, ?> getCompose(RewriteResult<B, ?> firstAlgFunc, RewriteResult<?, ?> secondAlgFunc) {
			return firstAlgFunc.compose((RewriteResult<?, B>)secondAlgFunc);
		}
	}

	public static enum CompAssocLeft implements PointFreeRule {
		INSTANCE;

		@Override
		public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> expr) {
			if (expr instanceof Comp) {
				Comp<?, ?, ?> comp2 = (Comp<?, ?, ?>)expr;
				PointFree<? extends Function<?, ?>> second = comp2.second;
				if (second instanceof Comp) {
					Comp<?, ?, ?> comp1 = (Comp<?, ?, ?>)second;
					return swap(comp1, comp2);
				}
			}

			return Optional.empty();
		}

		private static <A, B, C, D, E> Optional<PointFree<E>> swap(Comp<A, B, C> comp1, Comp<?, ?, D> comp2raw) {
			return Optional.of(new Comp<>(comp1.middleType, new Comp<>(comp2raw.middleType, comp2raw.first, comp1.first), comp1.second));
		}
	}

	public static enum CompAssocRight implements PointFreeRule {
		INSTANCE;

		@Override
		public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> expr) {
			if (expr instanceof Comp) {
				Comp<?, ?, ?> comp1 = (Comp<?, ?, ?>)expr;
				PointFree<? extends Function<?, ?>> first = comp1.first;
				if (first instanceof Comp) {
					Comp<?, ?, ?> comp2 = (Comp<?, ?, ?>)first;
					return swap(comp1, comp2);
				}
			}

			return Optional.empty();
		}

		private static <A, B, C, D, E> Optional<PointFree<E>> swap(Comp<A, B, D> comp1, Comp<?, C, ?> comp2raw) {
			return Optional.of(new Comp<>(comp2raw.middleType, comp2raw.first, new Comp<>(comp1.middleType, (PointFree<Function<B, C>>)comp2raw.second, comp1.second)));
		}
	}

	public interface CompRewrite extends PointFreeRule {
		@Override
		default <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> expr) {
			if (expr instanceof Comp) {
				Comp<?, ?, ?> comp = (Comp<?, ?, ?>)expr;
				PointFree<? extends Function<?, ?>> first = comp.first;
				PointFree<? extends Function<?, ?>> second = comp.second;
				if (first instanceof Comp) {
					Comp<?, ?, ?> firstComp = (Comp<?, ?, ?>)first;
					return this.doRewrite(type, comp.middleType, firstComp.second, comp.second).map(result -> {
						if (result instanceof Comp) {
							Comp<?, ?, ?> resultComp = (Comp<?, ?, ?>)result;
							return buildLeftNested(resultComp, firstComp);
						} else {
							return buildRight(firstComp, result);
						}
					});
				} else if (second instanceof Comp) {
					Comp<?, ?, ?> secondComp = (Comp<?, ?, ?>)second;
					return this.doRewrite(type, comp.middleType, comp.first, secondComp.first).map(result -> {
						if (result instanceof Comp) {
							Comp<?, ?, ?> resultComp = (Comp<?, ?, ?>)result;
							return buildRightNested(secondComp, resultComp);
						} else {
							return buildLeft(result, secondComp);
						}
					});
				} else {
					return (Optional<? extends PointFree<A>>)this.doRewrite(type, comp.middleType, comp.first, comp.second);
				}
			} else {
				return Optional.empty();
			}
		}

		static <A, B, C, D> PointFree<D> buildLeft(PointFree<?> result, Comp<A, B, C> comp) {
			return new Comp<>(comp.middleType, (PointFree<Function<B, C>>)result, comp.second);
		}

		static <A, B, C, D> PointFree<D> buildRight(Comp<A, B, C> comp, PointFree<?> result) {
			return new Comp<>(comp.middleType, comp.first, (PointFree<Function<A, B>>)result);
		}

		static <A, B, C, D, E> PointFree<E> buildLeftNested(Comp<A, B, C> comp1, Comp<?, ?, D> comp2raw) {
			return new Comp<>(comp1.middleType, new Comp<>(comp2raw.middleType, comp2raw.first, comp1.first), comp1.second);
		}

		static <A, B, C, D, E> PointFree<E> buildRightNested(Comp<A, B, D> comp1, Comp<?, C, ?> comp2raw) {
			return new Comp<>(comp2raw.middleType, comp2raw.first, new Comp<>(comp1.middleType, (PointFree<Function<B, C>>)comp2raw.second, comp1.second));
		}

		<A> Optional<? extends PointFree<?>> doRewrite(
			Type<A> type1, Type<?> type2, PointFree<? extends Function<?, ?>> pointFree3, PointFree<? extends Function<?, ?>> pointFree4
		);
	}

	public static enum LensAppId implements PointFreeRule {
		INSTANCE;

		@Override
		public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> expr) {
			if (expr instanceof Apply) {
				Apply<?, A> apply = (Apply<?, A>)expr;
				PointFree<? extends Function<?, A>> func = apply.func;
				if (func instanceof ProfunctorTransformer && Objects.equals(apply.arg, Functions.id())) {
					return Optional.of(Functions.id());
				}
			}

			return Optional.empty();
		}
	}

	public static enum LensComp implements PointFreeRule.CompRewrite {
		INSTANCE;

		@Override
		public <A> Optional<? extends PointFree<?>> doRewrite(
			Type<A> type, Type<?> middleType, PointFree<? extends Function<?, ?>> first, PointFree<? extends Function<?, ?>> second
		) {
			if (first instanceof Apply && second instanceof Apply) {
				Apply<?, ?> applyFirst = (Apply<?, ?>)first;
				Apply<?, ?> applySecond = (Apply<?, ?>)second;
				PointFree<? extends Function<?, ?>> firstFunc = applyFirst.func;
				PointFree<? extends Function<?, ?>> secondFunc = applySecond.func;
				if (firstFunc instanceof ProfunctorTransformer && secondFunc instanceof ProfunctorTransformer) {
					ProfunctorTransformer<?, ?, ?, ?> lensPFFirst = (ProfunctorTransformer<?, ?, ?, ?>)firstFunc;
					ProfunctorTransformer<?, ?, ?, ?> lensPFSecond = (ProfunctorTransformer<?, ?, ?, ?>)secondFunc;
					if (Objects.equals(lensPFFirst.optic, lensPFSecond.optic)) {
						Func<?, ?> firstFuncType = (Func<?, ?>)applyFirst.argType;
						Func<?, ?> secondFuncType = (Func<?, ?>)applySecond.argType;
						return this.cap(lensPFFirst, lensPFSecond, applyFirst.arg, applySecond.arg, firstFuncType, secondFuncType);
					}
				}
			}

			return Optional.empty();
		}

		private <R, A, B, C, S, T, U> Optional<? extends PointFree<R>> cap(
			ProfunctorTransformer<S, T, A, B> l1, ProfunctorTransformer<?, U, ?, C> l2, PointFree<?> f1, PointFree<?> f2, Func<?, ?> firstType, Func<?, ?> secondType
		) {
			return this.cap2(
				l1, (ProfunctorTransformer<T, U, B, C>)l2, (PointFree<Function<B, C>>)f1, (PointFree<Function<A, B>>)f2, (Func<B, C>)firstType, (Func<A, B>)secondType
			);
		}

		private <R, P extends K2, Proof extends K1, A, B, C, S, T, U> Optional<? extends PointFree<R>> cap2(
			ProfunctorTransformer<S, T, A, B> l1,
			ProfunctorTransformer<T, U, B, C> l2,
			PointFree<Function<B, C>> f1,
			PointFree<Function<A, B>> f2,
			Func<B, C> firstType,
			Func<A, B> secondType
		) {
			PointFree<Function<A, C>> arg = Functions.comp(firstType.first(), f1, f2);
			return Optional.of(Functions.app(l1, (PointFree<Function<A, B>>)arg, DSL.func(secondType.first(), (Type<B>)firstType.second())));
		}
	}

	public static enum LensCompFunc implements PointFreeRule {
		INSTANCE;

		@Override
		public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> expr) {
			if (expr instanceof Comp) {
				Comp<?, ?, ?> comp = (Comp<?, ?, ?>)expr;
				PointFree<? extends Function<?, ?>> first = comp.first;
				PointFree<? extends Function<?, ?>> second = comp.second;
				if (first instanceof ProfunctorTransformer && second instanceof ProfunctorTransformer) {
					ProfunctorTransformer<?, ?, ?, ?> firstOptic = (ProfunctorTransformer<?, ?, ?, ?>)first;
					ProfunctorTransformer<?, ?, ?, ?> secondOptic = (ProfunctorTransformer<?, ?, ?, ?>)second;
					return Optional.of(this.cap(firstOptic, secondOptic));
				}
			}

			return Optional.empty();
		}

		private <R, X, Y, S, T, A, B> R cap(ProfunctorTransformer<X, Y, ?, ?> first, ProfunctorTransformer<S, T, A, B> second) {
			return (R)Functions.<X, Y, A, B>profunctorTransformer(first.optic.compose(second.optic));
		}
	}

	public static final class Many implements PointFreeRule {
		private final PointFreeRule rule;

		public Many(PointFreeRule rule) {
			this.rule = rule;
		}

		@Override
		public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> expr) {
			Optional<? extends PointFree<A>> result = Optional.of(expr);

			while (true) {
				Optional<? extends PointFree<A>> newResult = result.flatMap(e -> this.rule.rewrite(type, e).map(r -> r));
				if (!newResult.isPresent()) {
					return result;
				}

				result = newResult;
			}
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			} else if (o != null && this.getClass() == o.getClass()) {
				PointFreeRule.Many many = (PointFreeRule.Many)o;
				return Objects.equals(this.rule, many.rule);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return Objects.hash(new Object[]{this.rule});
		}
	}

	public static enum Nop implements PointFreeRule, Supplier<PointFreeRule> {
		INSTANCE;

		@Override
		public <A> Optional<PointFree<A>> rewrite(Type<A> type, PointFree<A> expr) {
			return Optional.of(expr);
		}

		public PointFreeRule get() {
			return this;
		}
	}

	public static final class One implements PointFreeRule {
		private final PointFreeRule rule;

		public One(PointFreeRule rule) {
			this.rule = rule;
		}

		@Override
		public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> expr) {
			return expr.one(this.rule, type);
		}

		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			} else if (!(obj instanceof PointFreeRule.One)) {
				return false;
			} else {
				PointFreeRule.One that = (PointFreeRule.One)obj;
				return Objects.equals(this.rule, that.rule);
			}
		}

		public int hashCode() {
			return this.rule.hashCode();
		}
	}

	public static final class OrElse implements PointFreeRule {
		protected final PointFreeRule first;
		protected final Supplier<PointFreeRule> second;

		public OrElse(PointFreeRule first, Supplier<PointFreeRule> second) {
			this.first = first;
			this.second = second;
		}

		@Override
		public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> expr) {
			Optional<? extends PointFree<A>> view = this.first.rewrite(type, expr);
			return view.isPresent() ? view : ((PointFreeRule)this.second.get()).rewrite(type, expr);
		}

		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			} else if (!(obj instanceof PointFreeRule.OrElse)) {
				return false;
			} else {
				PointFreeRule.OrElse that = (PointFreeRule.OrElse)obj;
				return Objects.equals(this.first, that.first) && Objects.equals(this.second, that.second);
			}
		}

		public int hashCode() {
			return Objects.hash(new Object[]{this.first, this.second});
		}
	}

	public static final class Seq implements PointFreeRule {
		private final List<Supplier<PointFreeRule>> rules;

		public Seq(List<Supplier<PointFreeRule>> rules) {
			this.rules = ImmutableList.copyOf(rules);
		}

		@Override
		public <A> Optional<? extends PointFree<A>> rewrite(Type<A> type, PointFree<A> expr) {
			Optional<? extends PointFree<A>> result = Optional.of(expr);

			for (Supplier<PointFreeRule> rule : this.rules) {
				result = result.flatMap(pf -> ((PointFreeRule)rule.get()).rewrite(type, pf));
			}

			return result;
		}

		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			} else if (!(obj instanceof PointFreeRule.Seq)) {
				return false;
			} else {
				PointFreeRule.Seq that = (PointFreeRule.Seq)obj;
				return Objects.equals(this.rules, that.rules);
			}
		}

		public int hashCode() {
			return Objects.hash(new Object[]{this.rules});
		}
	}

	public static enum SortInj implements PointFreeRule.CompRewrite {
		INSTANCE;

		@Override
		public <A> Optional<? extends PointFree<?>> doRewrite(
			Type<A> type, Type<?> middleType, PointFree<? extends Function<?, ?>> first, PointFree<? extends Function<?, ?>> second
		) {
			if (first instanceof Apply && second instanceof Apply) {
				Apply<?, ?> applyFirst = (Apply<?, ?>)first;
				Apply<?, ?> applySecond = (Apply<?, ?>)second;
				PointFree<? extends Function<?, ?>> firstFunc = applyFirst.func;
				PointFree<? extends Function<?, ?>> secondFunc = applySecond.func;
				if (firstFunc instanceof ProfunctorTransformer && secondFunc instanceof ProfunctorTransformer) {
					ProfunctorTransformer<?, ?, ?, ?> firstOptic = (ProfunctorTransformer<?, ?, ?, ?>)firstFunc;
					ProfunctorTransformer<?, ?, ?, ?> secondOptic = (ProfunctorTransformer<?, ?, ?, ?>)secondFunc;
					Optic<?, ?, ?, ?, ?> fo = firstOptic.optic;

					while (fo instanceof CompositionOptic) {
						fo = ((CompositionOptic)fo).outer();
					}

					Optic<?, ?, ?, ?, ?> so = secondOptic.optic;

					while (so instanceof CompositionOptic) {
						so = ((CompositionOptic)so).outer();
					}

					if (Objects.equals(fo, Optics.inj2()) && Objects.equals(so, Optics.inj1())) {
						Func<?, ?> firstArg = (Func<?, ?>)applyFirst.argType;
						Func<?, ?> secondArg = (Func<?, ?>)applySecond.argType;
						return Optional.of(this.cap(firstArg, secondArg, applyFirst, applySecond));
					}
				}
			}

			return Optional.empty();
		}

		private <R, A, A2, B, B2> R cap(Func<B, B2> firstArg, Func<A, A2> secondArg, Apply<?, ?> first, Apply<?, ?> second) {
			return (R)Functions.comp(DSL.or(secondArg.first(), firstArg.second()), second, first);
		}
	}

	public static enum SortProj implements PointFreeRule.CompRewrite {
		INSTANCE;

		@Override
		public <A> Optional<? extends PointFree<?>> doRewrite(
			Type<A> type, Type<?> middleType, PointFree<? extends Function<?, ?>> first, PointFree<? extends Function<?, ?>> second
		) {
			if (first instanceof Apply && second instanceof Apply) {
				Apply<?, ?> applyFirst = (Apply<?, ?>)first;
				Apply<?, ?> applySecond = (Apply<?, ?>)second;
				PointFree<? extends Function<?, ?>> firstFunc = applyFirst.func;
				PointFree<? extends Function<?, ?>> secondFunc = applySecond.func;
				if (firstFunc instanceof ProfunctorTransformer && secondFunc instanceof ProfunctorTransformer) {
					ProfunctorTransformer<?, ?, ?, ?> firstOptic = (ProfunctorTransformer<?, ?, ?, ?>)firstFunc;
					ProfunctorTransformer<?, ?, ?, ?> secondOptic = (ProfunctorTransformer<?, ?, ?, ?>)secondFunc;
					Optic<?, ?, ?, ?, ?> fo = firstOptic.optic;

					while (fo instanceof CompositionOptic) {
						fo = ((CompositionOptic)fo).outer();
					}

					Optic<?, ?, ?, ?, ?> so = secondOptic.optic;

					while (so instanceof CompositionOptic) {
						so = ((CompositionOptic)so).outer();
					}

					if (Objects.equals(fo, Optics.proj2()) && Objects.equals(so, Optics.proj1())) {
						Func<?, ?> firstArg = (Func<?, ?>)applyFirst.argType;
						Func<?, ?> secondArg = (Func<?, ?>)applySecond.argType;
						return Optional.of(this.cap(firstArg, secondArg, applyFirst, applySecond));
					}
				}
			}

			return Optional.empty();
		}

		private <R, A, A2, B, B2> R cap(Func<B, B2> firstArg, Func<A, A2> secondArg, Apply<?, ?> first, Apply<?, ?> second) {
			return (R)Functions.comp(DSL.and(secondArg.first(), firstArg.second()), second, first);
		}
	}
}
