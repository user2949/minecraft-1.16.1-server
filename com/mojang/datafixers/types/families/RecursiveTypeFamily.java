package com.mojang.datafixers.types.families;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.OpticParts;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.View;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.functions.PointFree;
import com.mojang.datafixers.functions.PointFreeRule;
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.Type.FieldNotFoundException;
import com.mojang.datafixers.types.Type.TypeMatcher;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.RecursivePoint.RecursivePointType;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class RecursiveTypeFamily implements TypeFamily {
	private final String name;
	private final TypeTemplate template;
	private final int size;
	private final Int2ObjectMap<RecursivePointType<?>> types = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());
	private final int hashCode;

	public RecursiveTypeFamily(String name, TypeTemplate template) {
		this.name = name;
		this.template = template;
		this.size = template.size();
		this.hashCode = Objects.hashCode(template);
	}

	public static <A, B> View<A, B> viewUnchecked(Type<?> type, Type<?> resType, PointFree<Function<A, B>> function) {
		return View.create((Type<A>)type, (Type<B>)resType, function);
	}

	public <A> RecursivePointType<A> buildMuType(Type<A> newType, @Nullable RecursiveTypeFamily newFamily) {
		if (newFamily == null) {
			TypeTemplate newTypeTemplate = newType.template();
			if (Objects.equals(this.template, newTypeTemplate)) {
				newFamily = this;
			} else {
				newFamily = new RecursiveTypeFamily("ruled " + this.name, newTypeTemplate);
			}
		}

		RecursivePointType<A> newMuType = null;

		for (int i1 = 0; i1 < newFamily.size; i1++) {
			RecursivePointType<?> type = newFamily.apply(i1);
			Type<?> unfold = type.unfold();
			if (newType.equals(unfold, true, false)) {
				newMuType = (RecursivePointType<A>)type;
				break;
			}
		}

		if (newMuType == null) {
			throw new IllegalStateException("Couldn't determine the new type properly");
		} else {
			return newMuType;
		}
	}

	public String name() {
		return this.name;
	}

	public TypeTemplate template() {
		return this.template;
	}

	public int size() {
		return this.size;
	}

	public IntFunction<RewriteResult<?, ?>> fold(Algebra algebra) {
		return index -> {
			RewriteResult<?, ?> result = algebra.apply(index);
			return RewriteResult.create(
				viewUnchecked(result.view().type(), result.view().newType(), Functions.fold(this.apply(index), result, algebra, index)), result.recData()
			);
		};
	}

	public RecursivePointType<?> apply(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException();
		} else {
			return (RecursivePointType<?>)this.types
				.computeIfAbsent((Object)Integer.valueOf(index), i -> new RecursivePointType(this, i, () -> this.template.apply(this).apply(i)));
		}
	}

	public <A, B> Either<TypedOptic<?, ?, A, B>, FieldNotFoundException> findType(
		int index, Type<A> aType, Type<B> bType, TypeMatcher<A, B> matcher, boolean recurse
	) {
		return this.apply(index).unfold().findType(aType, bType, matcher, false).flatMap(optic -> {
			TypeTemplate nc = optic.tType().template();
			List<FamilyOptic<A, B>> fo = Lists.<FamilyOptic<A, B>>newArrayList();
			RecursiveTypeFamily newFamily = new RecursiveTypeFamily(this.name, nc);
			RecursivePointType<?> sType = this.apply(index);
			RecursivePointType<?> tType = newFamily.apply(index);
			if (recurse) {
				FamilyOptic<A, B> arg = i -> ((FamilyOptic)fo.get(0)).apply(i);
				fo.add(this.template.applyO(arg, aType, bType));
				OpticParts<A, B> parts = ((FamilyOptic)fo.get(0)).apply(index);
				return Either.left(this.mkOptic((Type<A>)sType, (Type<A>)tType, aType, bType, parts));
			} else {
				return this.mkSimpleOptic(sType, tType, aType, bType, matcher);
			}
		});
	}

	private <S, T, A, B> TypedOptic<S, T, A, B> mkOptic(Type<S> sType, Type<T> tType, Type<A> aType, Type<B> bType, OpticParts<A, B> parts) {
		return new TypedOptic<>(parts.bounds(), sType, tType, aType, bType, (Optic<?, S, T, A, B>)parts.optic());
	}

	private <S, T, A, B> Either<TypedOptic<?, ?, A, B>, FieldNotFoundException> mkSimpleOptic(
		RecursivePointType<S> sType, RecursivePointType<T> tType, Type<A> aType, Type<B> bType, TypeMatcher<A, B> matcher
	) {
		return sType.unfold()
			.findType(aType, bType, matcher, false)
			.mapLeft(o -> this.mkOptic(sType, tType, o.aType(), o.bType(), new OpticParts<>(o.bounds(), o.optic())));
	}

	public Optional<RewriteResult<?, ?>> everywhere(int index, TypeRewriteRule rule, PointFreeRule optimizationRule) {
		Type<?> sourceType = this.apply(index).unfold();
		RewriteResult<?, ?> sourceView = DataFixUtils.orElse(sourceType.everywhere(rule, optimizationRule, false, false), RewriteResult.nop(sourceType));
		RecursivePointType<?> newType = this.buildMuType(sourceView.view().newType(), null);
		RecursiveTypeFamily newFamily = newType.family();
		List<RewriteResult<?, ?>> views = Lists.<RewriteResult<?, ?>>newArrayList();
		boolean foundAny = false;

		for (int i = 0; i < this.size; i++) {
			RecursivePointType<?> type = this.apply(i);
			Type<?> unfold = type.unfold();
			boolean nop1 = true;
			RewriteResult<?, ?> view = DataFixUtils.orElse(unfold.everywhere(rule, optimizationRule, false, true), RewriteResult.nop(unfold));
			if (!Objects.equals(view.view().function(), Functions.id())) {
				nop1 = false;
			}

			RecursivePointType<?> newMuType = this.buildMuType(view.view().newType(), newFamily);
			boolean nop = this.cap2(views, type, rule, optimizationRule, nop1, view, newMuType);
			foundAny = foundAny || !nop;
		}

		if (!foundAny) {
			return Optional.empty();
		} else {
			Algebra algebra = new ListAlgebra("everywhere", views);
			RewriteResult<?, ?> fold = (RewriteResult<?, ?>)this.fold(algebra).apply(index);
			return Optional.of(RewriteResult.create(viewUnchecked(this.apply(index), newType, fold.view().function()), fold.recData()));
		}
	}

	private <A, B> boolean cap2(
		List<RewriteResult<?, ?>> views,
		RecursivePointType<A> type,
		TypeRewriteRule rule,
		PointFreeRule optimizationRule,
		boolean nop,
		RewriteResult<?, ?> view,
		RecursivePointType<B> newType
	) {
		RewriteResult<A, B> newView = RewriteResult.create(newType.in(), new BitSet()).compose((RewriteResult<A, B>)view);
		Optional<RewriteResult<B, ?>> rewrite = rule.rewrite(newView.view().newType());
		if (rewrite.isPresent() && !Objects.equals(((RewriteResult)rewrite.get()).view().function(), Functions.id())) {
			nop = false;
			view = ((RewriteResult)rewrite.get()).compose(newView);
		}

		view = RewriteResult.create(view.view().rewriteOrNop(optimizationRule), view.recData());
		views.add(view);
		return nop;
	}

	public String toString() {
		return "Mu[" + this.name + ", " + this.size + ", " + this.template + "]";
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (!(o instanceof RecursiveTypeFamily)) {
			return false;
		} else {
			RecursiveTypeFamily family = (RecursiveTypeFamily)o;
			return Objects.equals(this.template, family.template);
		}
	}

	public int hashCode() {
		return this.hashCode;
	}
}
