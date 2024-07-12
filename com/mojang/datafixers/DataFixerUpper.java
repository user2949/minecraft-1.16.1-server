package com.mojang.datafixers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.functions.PointFreeRule;
import com.mojang.datafixers.functions.PointFreeRule.AppNest;
import com.mojang.datafixers.functions.PointFreeRule.CataFuseDifferent;
import com.mojang.datafixers.functions.PointFreeRule.CataFuseSame;
import com.mojang.datafixers.functions.PointFreeRule.CompAssocLeft;
import com.mojang.datafixers.functions.PointFreeRule.CompAssocRight;
import com.mojang.datafixers.functions.PointFreeRule.LensAppId;
import com.mojang.datafixers.functions.PointFreeRule.LensComp;
import com.mojang.datafixers.functions.PointFreeRule.LensCompFunc;
import com.mojang.datafixers.functions.PointFreeRule.SortInj;
import com.mojang.datafixers.functions.PointFreeRule.SortProj;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataFixerUpper implements DataFixer {
	public static boolean ERRORS_ARE_FATAL = false;
	private static final Logger LOGGER = LogManager.getLogger();
	protected static final PointFreeRule OPTIMIZATION_RULE = DataFixUtils.make(
		() -> {
			PointFreeRule opSimple = PointFreeRule.orElse(
				PointFreeRule.orElse(CataFuseSame.INSTANCE, PointFreeRule.orElse(CataFuseDifferent.INSTANCE, LensAppId.INSTANCE)),
				PointFreeRule.orElse(LensComp.INSTANCE, PointFreeRule.orElse(AppNest.INSTANCE, LensCompFunc.INSTANCE))
			);
			PointFreeRule opLeft = PointFreeRule.many(PointFreeRule.once(PointFreeRule.orElse(opSimple, CompAssocLeft.INSTANCE)));
			PointFreeRule opComp = PointFreeRule.many(PointFreeRule.once(PointFreeRule.orElse(SortInj.INSTANCE, SortProj.INSTANCE)));
			PointFreeRule opRight = PointFreeRule.many(PointFreeRule.once(PointFreeRule.orElse(opSimple, CompAssocRight.INSTANCE)));
			return PointFreeRule.seq(ImmutableList.of(() -> opLeft, () -> opComp, () -> opRight, () -> opLeft, () -> opRight));
		}
	);
	private final Int2ObjectSortedMap<Schema> schemas;
	private final List<DataFix> globalList;
	private final IntSortedSet fixerVersions;
	private final Long2ObjectMap<TypeRewriteRule> rules = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap<>());

	protected DataFixerUpper(Int2ObjectSortedMap<Schema> schemas, List<DataFix> globalList, IntSortedSet fixerVersions) {
		this.schemas = schemas;
		this.globalList = globalList;
		this.fixerVersions = fixerVersions;
	}

	@Override
	public <T> Dynamic<T> update(TypeReference type, Dynamic<T> input, int version, int newVersion) {
		if (version < newVersion) {
			Type<?> dataType = this.getType(type, version);
			DataResult<T> read = dataType.readAndWrite(
				input.getOps(), this.getType(type, newVersion), this.getRule(version, newVersion), OPTIMIZATION_RULE, input.getValue()
			);
			T result = (T)read.resultOrPartial(LOGGER::error).orElse(input.getValue());
			return new Dynamic<>(input.getOps(), result);
		} else {
			return input;
		}
	}

	@Override
	public Schema getSchema(int key) {
		return this.schemas.get(getLowestSchemaSameVersion(this.schemas, key));
	}

	protected Type<?> getType(TypeReference type, int version) {
		return this.getSchema(DataFixUtils.makeKey(version)).getType(type);
	}

	protected static int getLowestSchemaSameVersion(Int2ObjectSortedMap<Schema> schemas, int versionKey) {
		return versionKey < schemas.firstIntKey() ? schemas.firstIntKey() : schemas.subMap(0, versionKey + 1).lastIntKey();
	}

	private int getLowestFixSameVersion(int versionKey) {
		return versionKey < this.fixerVersions.firstInt() ? this.fixerVersions.firstInt() - 1 : this.fixerVersions.subSet(0, versionKey + 1).lastInt();
	}

	protected TypeRewriteRule getRule(int version, int dataVersion) {
		if (version >= dataVersion) {
			return TypeRewriteRule.nop();
		} else {
			int expandedVersion = this.getLowestFixSameVersion(DataFixUtils.makeKey(version));
			int expandedDataVersion = DataFixUtils.makeKey(dataVersion);
			long key = (long)expandedVersion << 32 | (long)expandedDataVersion;
			return (TypeRewriteRule)this.rules.computeIfAbsent((Object)Long.valueOf(key), k -> {
				List<TypeRewriteRule> rules = Lists.<TypeRewriteRule>newArrayList();

				for (DataFix fix : this.globalList) {
					int fixVersion = fix.getVersionKey();
					if (fixVersion > expandedVersion && fixVersion <= expandedDataVersion) {
						TypeRewriteRule fixRule = fix.getRule();
						if (fixRule != TypeRewriteRule.nop()) {
							rules.add(fixRule);
						}
					}
				}

				return TypeRewriteRule.seq(rules);
			});
		}
	}

	protected IntSortedSet fixerVersions() {
		return this.fixerVersions;
	}
}
