package com.mojang.datafixers;

import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.functions.PointFree;
import com.mojang.datafixers.functions.PointFreeRule;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DynamicOps;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class View<A, B> implements App2<View.Mu, A, B> {
	private final Type<A> type;
	protected final Type<B> newType;
	private final PointFree<Function<A, B>> function;

	static <A, B> View<A, B> unbox(App2<View.Mu, A, B> box) {
		return (View<A, B>)box;
	}

	public static <A> View<A, A> nopView(Type<A> type) {
		return create(type, type, Functions.id());
	}

	public View(Type<A> type, Type<B> newType, PointFree<Function<A, B>> function) {
		this.type = type;
		this.newType = newType;
		this.function = function;
	}

	public Type<A> type() {
		return this.type;
	}

	public Type<B> newType() {
		return this.newType;
	}

	public PointFree<Function<A, B>> function() {
		return this.function;
	}

	public Type<Function<A, B>> getFuncType() {
		return DSL.func(this.type, this.newType);
	}

	public String toString() {
		return "View[" + this.function + "," + this.newType + "]";
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			View<?, ?> view = (View<?, ?>)o;
			return Objects.equals(this.type, view.type) && Objects.equals(this.newType, view.newType) && Objects.equals(this.function, view.function);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.type, this.newType, this.function});
	}

	public Optional<? extends View<A, B>> rewrite(PointFreeRule rule) {
		return rule.rewrite(DSL.func(this.type, this.newType), this.function()).map(f -> create(this.type, this.newType, f));
	}

	public View<A, B> rewriteOrNop(PointFreeRule rule) {
		return DataFixUtils.orElse(this.rewrite(rule), this);
	}

	public <C> View<A, C> flatMap(Function<Type<B>, View<B, C>> function) {
		View<B, C> instance = (View<B, C>)function.apply(this.newType);
		return new View<>(this.type, instance.newType, Functions.comp(this.newType, instance.function(), this.function()));
	}

	public static <A, B> View<A, B> create(Type<A> type, Type<B> newType, PointFree<Function<A, B>> function) {
		return new View<>(type, newType, function);
	}

	public static <A, B> View<A, B> create(String name, Type<A> type, Type<B> newType, Function<DynamicOps<?>, Function<A, B>> function) {
		return new View<>(type, newType, Functions.fun(name, function));
	}

	public <C> View<C, B> compose(View<C, A> that) {
		if (Objects.equals(this.function(), Functions.id())) {
			return new View<>(that.type(), this.newType(), (PointFree<Function<C, B>>)that.function());
		} else {
			return Objects.equals(that.function(), Functions.id())
				? new View<>(that.type(), this.newType(), (PointFree<Function<C, B>>)this.function())
				: create(that.type, this.newType, Functions.comp(that.newType, this.function(), that.function()));
		}
	}

	static final class Mu implements K2 {
	}
}
