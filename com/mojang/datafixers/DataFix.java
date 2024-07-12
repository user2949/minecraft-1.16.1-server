package com.mojang.datafixers;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.BitSet;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DataFix {
	private static final Logger LOGGER = LogManager.getLogger();
	private final Schema outputSchema;
	private final boolean changesType;
	@Nullable
	private TypeRewriteRule rule;

	public DataFix(Schema outputSchema, boolean changesType) {
		this.outputSchema = outputSchema;
		this.changesType = changesType;
	}

	protected <A> TypeRewriteRule fixTypeEverywhere(String name, Type<A> type, Function<DynamicOps<?>, Function<A, A>> function) {
		return this.fixTypeEverywhere(name, type, type, function, new BitSet());
	}

	protected <A, B> TypeRewriteRule convertUnchecked(String name, Type<A> type, Type<B> newType) {
		return this.fixTypeEverywhere(name, type, newType, ops -> Function.identity(), new BitSet());
	}

	protected TypeRewriteRule writeAndRead(String name, Type<?> type, Type<?> newType) {
		return this.writeFixAndRead(name, type, newType, Function.identity());
	}

	protected <A, B> TypeRewriteRule writeFixAndRead(String name, Type<A> type, Type<B> newType, Function<Dynamic<?>, Dynamic<?>> fix) {
		return this.fixTypeEverywhere(name, type, newType, ops -> input -> {
				Optional<? extends Dynamic<?>> written = type.writeDynamic(ops, (A)input).resultOrPartial(LOGGER::error);
				if (!written.isPresent()) {
					throw new RuntimeException("Could not write the object in " + name);
				} else {
					Optional<? extends Pair<Typed<B>, ?>> read = newType.readTyped((Dynamic)fix.apply(written.get())).resultOrPartial(LOGGER::error);
					if (!read.isPresent()) {
						throw new RuntimeException("Could not read the new object in " + name);
					} else {
						return ((Typed)((Pair)read.get()).getFirst()).getValue();
					}
				}
			});
	}

	protected <A, B> TypeRewriteRule fixTypeEverywhere(String name, Type<A> type, Type<B> newType, Function<DynamicOps<?>, Function<A, B>> function) {
		return this.fixTypeEverywhere(name, type, newType, function, new BitSet());
	}

	protected <A, B> TypeRewriteRule fixTypeEverywhere(String name, Type<A> type, Type<B> newType, Function<DynamicOps<?>, Function<A, B>> function, BitSet bitSet) {
		return this.fixTypeEverywhere(type, RewriteResult.create(View.create(name, type, newType, new DataFix.NamedFunctionWrapper<>(name, function)), bitSet));
	}

	protected <A> TypeRewriteRule fixTypeEverywhereTyped(String name, Type<A> type, Function<Typed<?>, Typed<?>> function) {
		return this.fixTypeEverywhereTyped(name, type, function, new BitSet());
	}

	protected <A> TypeRewriteRule fixTypeEverywhereTyped(String name, Type<A> type, Function<Typed<?>, Typed<?>> function, BitSet bitSet) {
		return this.fixTypeEverywhereTyped(name, type, type, function, bitSet);
	}

	protected <A, B> TypeRewriteRule fixTypeEverywhereTyped(String name, Type<A> type, Type<B> newType, Function<Typed<?>, Typed<?>> function) {
		return this.fixTypeEverywhereTyped(name, type, newType, function, new BitSet());
	}

	protected <A, B> TypeRewriteRule fixTypeEverywhereTyped(String name, Type<A> type, Type<B> newType, Function<Typed<?>, Typed<?>> function, BitSet bitSet) {
		return this.fixTypeEverywhere(type, checked(name, type, newType, function, bitSet));
	}

	public static <A, B> RewriteResult<A, B> checked(String name, Type<A> type, Type<B> newType, Function<Typed<?>, Typed<?>> function, BitSet bitSet) {
		return RewriteResult.create(View.create(name, type, newType, new DataFix.NamedFunctionWrapper<>(name, ops -> a -> {
				Typed<?> result = (Typed<?>)function.apply(new Typed<>(type, ops, (A)a));
				if (!newType.equals(result.type, true, false)) {
					throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", newType, result.type));
				} else {
					return result.value;
				}
			})), bitSet);
	}

	protected <A, B> TypeRewriteRule fixTypeEverywhere(Type<A> type, RewriteResult<A, B> view) {
		return TypeRewriteRule.checkOnce(TypeRewriteRule.everywhere(TypeRewriteRule.ifSame(type, view), DataFixerUpper.OPTIMIZATION_RULE, true, true), this::onFail);
	}

	protected void onFail(Type<?> type) {
		LOGGER.info("Not matched: " + this + " " + type);
	}

	public final int getVersionKey() {
		return this.getOutputSchema().getVersionKey();
	}

	public TypeRewriteRule getRule() {
		if (this.rule == null) {
			this.rule = this.makeRule();
		}

		return this.rule;
	}

	protected abstract TypeRewriteRule makeRule();

	protected Schema getInputSchema() {
		return this.changesType ? this.outputSchema.getParent() : this.getOutputSchema();
	}

	protected Schema getOutputSchema() {
		return this.outputSchema;
	}

	private static final class NamedFunctionWrapper<A, B> implements Function<DynamicOps<?>, Function<A, B>> {
		private final String name;
		private final Function<DynamicOps<?>, Function<A, B>> delegate;

		public NamedFunctionWrapper(String name, Function<DynamicOps<?>, Function<A, B>> delegate) {
			this.name = name;
			this.delegate = delegate;
		}

		public Function<A, B> apply(DynamicOps<?> ops) {
			return (Function<A, B>)this.delegate.apply(ops);
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			} else if (o != null && this.getClass() == o.getClass()) {
				DataFix.NamedFunctionWrapper<?, ?> that = (DataFix.NamedFunctionWrapper<?, ?>)o;
				return Objects.equals(this.name, that.name);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return Objects.hash(new Object[]{this.name});
		}
	}
}
