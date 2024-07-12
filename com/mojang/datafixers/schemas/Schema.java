package com.mojang.datafixers.schemas;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.RecursivePoint.RecursivePointType;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class Schema {
	protected final Object2IntMap<String> RECURSIVE_TYPES = new Object2IntOpenHashMap<>();
	private final Map<String, Supplier<TypeTemplate>> TYPE_TEMPLATES = Maps.<String, Supplier<TypeTemplate>>newHashMap();
	private final Map<String, Type<?>> TYPES;
	private final int versionKey;
	private final String name;
	protected final Schema parent;

	public Schema(int versionKey, Schema parent) {
		this.versionKey = versionKey;
		int subVersion = DataFixUtils.getSubVersion(versionKey);
		this.name = "V" + DataFixUtils.getVersion(versionKey) + (subVersion == 0 ? "" : "." + subVersion);
		this.parent = parent;
		this.registerTypes(this, this.registerEntities(this), this.registerBlockEntities(this));
		this.TYPES = this.buildTypes();
	}

	protected Map<String, Type<?>> buildTypes() {
		Map<String, Type<?>> types = Maps.<String, Type<?>>newHashMap();
		List<TypeTemplate> templates = Lists.<TypeTemplate>newArrayList();

		for (Entry<String> entry : this.RECURSIVE_TYPES.object2IntEntrySet()) {
			templates.add(DSL.check((String)entry.getKey(), entry.getIntValue(), this.getTemplate((String)entry.getKey())));
		}

		TypeTemplate choice = (TypeTemplate)templates.stream().reduce(DSL::or).get();
		TypeFamily family = new RecursiveTypeFamily(this.name, choice);

		for (String name : this.TYPE_TEMPLATES.keySet()) {
			int recurseId = (Integer)this.RECURSIVE_TYPES.getOrDefault(name, (Object)Integer.valueOf(-1));
			Type<?> type;
			if (recurseId != -1) {
				type = family.apply(recurseId);
			} else {
				type = this.getTemplate(name).apply(family).apply(-1);
			}

			types.put(name, type);
		}

		return types;
	}

	public Set<String> types() {
		return this.TYPES.keySet();
	}

	public Type<?> getTypeRaw(TypeReference type) {
		String name = type.typeName();
		return (Type<?>)this.TYPES.computeIfAbsent(name, key -> {
			throw new IllegalArgumentException("Unknown type: " + name);
		});
	}

	public Type<?> getType(TypeReference type) {
		String name = type.typeName();
		Type<?> type1 = (Type<?>)this.TYPES.computeIfAbsent(name, key -> {
			throw new IllegalArgumentException("Unknown type: " + name);
		});
		return type1 instanceof RecursivePointType
			? (Type)type1.findCheckedType(-1).orElseThrow(() -> new IllegalStateException("Could not find choice type in the recursive type"))
			: type1;
	}

	public TypeTemplate resolveTemplate(String name) {
		return (TypeTemplate)((Supplier)this.TYPE_TEMPLATES.getOrDefault(name, (Supplier)() -> {
			throw new IllegalArgumentException("Unknown type: " + name);
		})).get();
	}

	public TypeTemplate id(String name) {
		int id = (Integer)this.RECURSIVE_TYPES.getOrDefault(name, (Object)Integer.valueOf(-1));
		return id != -1 ? DSL.id(id) : this.getTemplate(name);
	}

	protected TypeTemplate getTemplate(String name) {
		return DSL.named(name, this.resolveTemplate(name));
	}

	public Type<?> getChoiceType(TypeReference type, String choiceName) {
		TaggedChoiceType<?> choiceType = this.findChoiceType(type);
		if (!choiceType.types().containsKey(choiceName)) {
			throw new IllegalArgumentException("Data fixer not registered for: " + choiceName + " in " + type.typeName());
		} else {
			return (Type<?>)choiceType.types().get(choiceName);
		}
	}

	public TaggedChoiceType<?> findChoiceType(TypeReference type) {
		return (TaggedChoiceType<?>)this.getType(type).findChoiceType("id", -1).orElseThrow(() -> new IllegalArgumentException("Not a choice type"));
	}

	public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
		this.parent.registerTypes(schema, entityTypes, blockEntityTypes);
	}

	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		return this.parent.registerEntities(schema);
	}

	public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
		return this.parent.registerBlockEntities(schema);
	}

	public void registerSimple(Map<String, Supplier<TypeTemplate>> map, String name) {
		this.register(map, name, DSL::remainder);
	}

	public void register(Map<String, Supplier<TypeTemplate>> map, String name, Function<String, TypeTemplate> template) {
		this.register(map, name, (Supplier<TypeTemplate>)(() -> (TypeTemplate)template.apply(name)));
	}

	public void register(Map<String, Supplier<TypeTemplate>> map, String name, Supplier<TypeTemplate> template) {
		map.put(name, template);
	}

	public void registerType(boolean recursive, TypeReference type, Supplier<TypeTemplate> template) {
		this.TYPE_TEMPLATES.put(type.typeName(), template);
		if (recursive && !this.RECURSIVE_TYPES.containsKey(type.typeName())) {
			this.RECURSIVE_TYPES.put(type.typeName(), this.RECURSIVE_TYPES.size());
		}
	}

	public int getVersionKey() {
		return this.versionKey;
	}

	public Schema getParent() {
		return this.parent;
	}
}
