package com.mojang.datafixers;

import com.google.common.collect.Maps;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Func;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.constant.EmptyPart;
import com.mojang.datafixers.types.constant.EmptyPartPassthrough;
import com.mojang.datafixers.types.templates.Check;
import com.mojang.datafixers.types.templates.CompoundList;
import com.mojang.datafixers.types.templates.Const;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.types.templates.Named;
import com.mojang.datafixers.types.templates.Product;
import com.mojang.datafixers.types.templates.RecursivePoint;
import com.mojang.datafixers.types.templates.Sum;
import com.mojang.datafixers.types.templates.Tag;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.CompoundList.CompoundListType;
import com.mojang.datafixers.types.templates.Const.PrimitiveType;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.datafixers.types.templates.Hook.HookType;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.types.templates.Named.NamedType;
import com.mojang.datafixers.types.templates.Product.ProductType;
import com.mojang.datafixers.types.templates.Sum.SumType;
import com.mojang.datafixers.types.templates.Tag.TagType;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Triple;

public interface DSL {
	static Type<Boolean> bool() {
		return DSL.Instances.BOOL_TYPE;
	}

	static Type<Integer> intType() {
		return DSL.Instances.INT_TYPE;
	}

	static Type<Long> longType() {
		return DSL.Instances.LONG_TYPE;
	}

	static Type<Byte> byteType() {
		return DSL.Instances.BYTE_TYPE;
	}

	static Type<Short> shortType() {
		return DSL.Instances.SHORT_TYPE;
	}

	static Type<Float> floatType() {
		return DSL.Instances.FLOAT_TYPE;
	}

	static Type<Double> doubleType() {
		return DSL.Instances.DOUBLE_TYPE;
	}

	static Type<String> string() {
		return DSL.Instances.STRING_TYPE;
	}

	static TypeTemplate emptyPart() {
		return constType(DSL.Instances.EMPTY_PART);
	}

	static Type<Unit> emptyPartType() {
		return DSL.Instances.EMPTY_PART;
	}

	static TypeTemplate remainder() {
		return constType(DSL.Instances.EMPTY_PASSTHROUGH);
	}

	static Type<Dynamic<?>> remainderType() {
		return DSL.Instances.EMPTY_PASSTHROUGH;
	}

	static TypeTemplate check(String name, int index, TypeTemplate element) {
		return new Check(name, index, element);
	}

	static TypeTemplate compoundList(TypeTemplate element) {
		return compoundList(constType(string()), element);
	}

	static <V> CompoundListType<String, V> compoundList(Type<V> value) {
		return compoundList(string(), value);
	}

	static TypeTemplate compoundList(TypeTemplate key, TypeTemplate element) {
		return and(new CompoundList(key, element), remainder());
	}

	static <K, V> CompoundListType<K, V> compoundList(Type<K> key, Type<V> value) {
		return new CompoundListType<>(key, value);
	}

	static TypeTemplate constType(Type<?> type) {
		return new Const(type);
	}

	static TypeTemplate hook(TypeTemplate template, HookFunction preRead, HookFunction postWrite) {
		return new Hook(template, preRead, postWrite);
	}

	static <A> Type<A> hook(Type<A> type, HookFunction preRead, HookFunction postWrite) {
		return new HookType<>(type, preRead, postWrite);
	}

	static TypeTemplate list(TypeTemplate element) {
		return new List(element);
	}

	static <A> ListType<A> list(Type<A> first) {
		return new ListType<>(first);
	}

	static TypeTemplate named(String name, TypeTemplate element) {
		return new Named(name, element);
	}

	static <A> Type<Pair<String, A>> named(String name, Type<A> element) {
		return new NamedType<>(name, element);
	}

	static TypeTemplate and(TypeTemplate first, TypeTemplate second) {
		return new Product(first, second);
	}

	static TypeTemplate and(TypeTemplate first, TypeTemplate... rest) {
		if (rest.length == 0) {
			return first;
		} else {
			TypeTemplate result = rest[rest.length - 1];

			for (int i = rest.length - 2; i >= 0; i--) {
				result = and(rest[i], result);
			}

			return and(first, result);
		}
	}

	static TypeTemplate allWithRemainder(TypeTemplate first, TypeTemplate... rest) {
		return and(first, ArrayUtils.add(rest, remainder()));
	}

	static <F, G> Type<Pair<F, G>> and(Type<F> first, Type<G> second) {
		return new ProductType<>(first, second);
	}

	static <F, G, H> Type<Pair<F, Pair<G, H>>> and(Type<F> first, Type<G> second, Type<H> third) {
		return and(first, and(second, third));
	}

	static <F, G, H, I> Type<Pair<F, Pair<G, Pair<H, I>>>> and(Type<F> first, Type<G> second, Type<H> third, Type<I> forth) {
		return and(first, and(second, and(third, forth)));
	}

	static TypeTemplate id(int index) {
		return new RecursivePoint(index);
	}

	static TypeTemplate or(TypeTemplate left, TypeTemplate right) {
		return new Sum(left, right);
	}

	static <F, G> Type<Either<F, G>> or(Type<F> first, Type<G> second) {
		return new SumType<>(first, second);
	}

	static TypeTemplate field(String name, TypeTemplate element) {
		return new Tag(name, element);
	}

	static <A> TagType<A> field(String name, Type<A> element) {
		return new TagType<>(name, element);
	}

	static <K> TaggedChoice<K> taggedChoice(String name, Type<K> keyType, Map<K, TypeTemplate> templates) {
		return new TaggedChoice<>(name, keyType, templates);
	}

	static <K> TaggedChoice<K> taggedChoiceLazy(String name, Type<K> keyType, Map<K, Supplier<TypeTemplate>> templates) {
		return taggedChoice(
			name, keyType, (Map<K, TypeTemplate>)templates.entrySet().stream().map(e -> Pair.of(e.getKey(), ((Supplier)e.getValue()).get())).collect(Pair.toMap())
		);
	}

	static <K> Type<Pair<K, ?>> taggedChoiceType(String name, Type<K> keyType, Map<K, ? extends Type<?>> types) {
		return (Type<Pair<K, ?>>)DSL.Instances.TAGGED_CHOICE_TYPE_CACHE
			.computeIfAbsent(Triple.of(name, keyType, types), k -> new TaggedChoiceType((String)k.getLeft(), (Type<K>)k.getMiddle(), (Map<K, Type<?>>)k.getRight()));
	}

	static <A, B> Type<Function<A, B>> func(Type<A> input, Type<B> output) {
		return new Func<>(input, output);
	}

	static <A> Type<Either<A, Unit>> optional(Type<A> type) {
		return or(type, emptyPartType());
	}

	static TypeTemplate optional(TypeTemplate value) {
		return or(value, emptyPart());
	}

	static TypeTemplate fields(String name1, TypeTemplate element1) {
		return allWithRemainder(field(name1, element1));
	}

	static TypeTemplate fields(String name1, TypeTemplate element1, String name2, TypeTemplate element2) {
		return allWithRemainder(field(name1, element1), field(name2, element2));
	}

	static TypeTemplate fields(String name1, TypeTemplate element1, String name2, TypeTemplate element2, String name3, TypeTemplate element3) {
		return allWithRemainder(field(name1, element1), field(name2, element2), field(name3, element3));
	}

	static TypeTemplate fields(String name, TypeTemplate element, TypeTemplate rest) {
		return and(field(name, element), rest);
	}

	static TypeTemplate fields(String name1, TypeTemplate element1, String name2, TypeTemplate element2, TypeTemplate rest) {
		return and(field(name1, element1), field(name2, element2), rest);
	}

	static TypeTemplate fields(String name1, TypeTemplate element1, String name2, TypeTemplate element2, String name3, TypeTemplate element3, TypeTemplate rest) {
		return and(field(name1, element1), field(name2, element2), field(name3, element3), rest);
	}

	static TypeTemplate optionalFields(String name, TypeTemplate element) {
		return allWithRemainder(optional(field(name, element)));
	}

	static TypeTemplate optionalFields(String name1, TypeTemplate element1, String name2, TypeTemplate element2) {
		return allWithRemainder(optional(field(name1, element1)), optional(field(name2, element2)));
	}

	static TypeTemplate optionalFields(String name1, TypeTemplate element1, String name2, TypeTemplate element2, String name3, TypeTemplate element3) {
		return allWithRemainder(optional(field(name1, element1)), optional(field(name2, element2)), optional(field(name3, element3)));
	}

	static TypeTemplate optionalFields(
		String name1, TypeTemplate element1, String name2, TypeTemplate element2, String name3, TypeTemplate element3, String name4, TypeTemplate element4
	) {
		return allWithRemainder(
			optional(field(name1, element1)), optional(field(name2, element2)), optional(field(name3, element3)), optional(field(name4, element4))
		);
	}

	static TypeTemplate optionalFields(
		String name1,
		TypeTemplate element1,
		String name2,
		TypeTemplate element2,
		String name3,
		TypeTemplate element3,
		String name4,
		TypeTemplate element4,
		String name5,
		TypeTemplate element5
	) {
		return allWithRemainder(
			optional(field(name1, element1)),
			optional(field(name2, element2)),
			optional(field(name3, element3)),
			optional(field(name4, element4)),
			optional(field(name5, element5))
		);
	}

	static TypeTemplate optionalFields(String name, TypeTemplate element, TypeTemplate rest) {
		return and(optional(field(name, element)), rest);
	}

	static TypeTemplate optionalFields(String name1, TypeTemplate element1, String name2, TypeTemplate element2, TypeTemplate rest) {
		return and(optional(field(name1, element1)), optional(field(name2, element2)), rest);
	}

	static TypeTemplate optionalFields(
		String name1, TypeTemplate element1, String name2, TypeTemplate element2, String name3, TypeTemplate element3, TypeTemplate rest
	) {
		return and(optional(field(name1, element1)), optional(field(name2, element2)), optional(field(name3, element3)), rest);
	}

	static TypeTemplate optionalFields(
		String name1,
		TypeTemplate element1,
		String name2,
		TypeTemplate element2,
		String name3,
		TypeTemplate element3,
		String name4,
		TypeTemplate element4,
		TypeTemplate rest
	) {
		return and(optional(field(name1, element1)), optional(field(name2, element2)), optional(field(name3, element3)), optional(field(name4, element4)), rest);
	}

	static TypeTemplate optionalFields(
		String name1,
		TypeTemplate element1,
		String name2,
		TypeTemplate element2,
		String name3,
		TypeTemplate element3,
		String name4,
		TypeTemplate element4,
		String name5,
		TypeTemplate element5,
		TypeTemplate rest
	) {
		return and(
			optional(field(name1, element1)),
			optional(field(name2, element2)),
			optional(field(name3, element3)),
			optional(field(name4, element4)),
			optional(field(name5, element5)),
			rest
		);
	}

	static OpticFinder<Dynamic<?>> remainderFinder() {
		return DSL.Instances.REMAINDER_FINDER;
	}

	static <FT> OpticFinder<FT> typeFinder(Type<FT> type) {
		return new FieldFinder<>(null, type);
	}

	static <FT> OpticFinder<FT> fieldFinder(String name, Type<FT> type) {
		return new FieldFinder<>(name, type);
	}

	static <FT> OpticFinder<FT> namedChoice(String name, Type<FT> type) {
		return new NamedChoiceFinder<>(name, type);
	}

	static Unit unit() {
		return Unit.INSTANCE;
	}

	public static final class Instances {
		private static final Type<Boolean> BOOL_TYPE = new PrimitiveType<>(Codec.BOOL);
		private static final Type<Integer> INT_TYPE = new PrimitiveType<>(Codec.INT);
		private static final Type<Long> LONG_TYPE = new PrimitiveType<>(Codec.LONG);
		private static final Type<Byte> BYTE_TYPE = new PrimitiveType<>(Codec.BYTE);
		private static final Type<Short> SHORT_TYPE = new PrimitiveType<>(Codec.SHORT);
		private static final Type<Float> FLOAT_TYPE = new PrimitiveType<>(Codec.FLOAT);
		private static final Type<Double> DOUBLE_TYPE = new PrimitiveType<>(Codec.DOUBLE);
		private static final Type<String> STRING_TYPE = new PrimitiveType<>(Codec.STRING);
		private static final Type<Unit> EMPTY_PART = new EmptyPart();
		private static final Type<Dynamic<?>> EMPTY_PASSTHROUGH = new EmptyPartPassthrough();
		private static final OpticFinder<Dynamic<?>> REMAINDER_FINDER = DSL.remainderType().finder();
		private static final Map<Triple<String, Type<?>, Map<?, ? extends Type<?>>>, Type<? extends Pair<?, ?>>> TAGGED_CHOICE_TYPE_CACHE = Maps.<Triple<String, Type<?>, Map<?, ? extends Type<?>>>, Type<? extends Pair<?, ?>>>newConcurrentMap(
			
		);
	}

	public interface TypeReference {
		String typeName();

		default TypeTemplate in(Schema schema) {
			return schema.id(this.typeName());
		}
	}
}
