import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ajp extends DataFix {
	private static final Logger a = LogManager.getLogger();

	public ajp(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getOutputSchema().getType(ajb.c);
		Type<?> type3 = type2.findFieldType("Level");
		Type<?> type4 = type3.findFieldType("TileEntities");
		if (!(type4 instanceof ListType)) {
			throw new IllegalStateException("Tile entity type is not a list type.");
		} else {
			ListType<?> listType5 = (ListType<?>)type4;
			OpticFinder<? extends List<?>> opticFinder6 = DSL.fieldFinder("TileEntities", (Type<? extends List<?>>)listType5);
			Type<?> type7 = this.getInputSchema().getType(ajb.c);
			OpticFinder<?> opticFinder8 = type7.findField("Level");
			OpticFinder<?> opticFinder9 = opticFinder8.type().findField("Sections");
			Type<?> type10 = opticFinder9.type();
			if (!(type10 instanceof ListType)) {
				throw new IllegalStateException("Expecting sections to be a list.");
			} else {
				Type<?> type11 = ((ListType)type10).getElement();
				OpticFinder<?> opticFinder12 = DSL.typeFinder(type11);
				return TypeRewriteRule.seq(
					new aes(this.getOutputSchema(), "AddTrappedChestFix", ajb.k).makeRule(),
					this.fixTypeEverywhereTyped("Trapped Chest fix", type7, typed -> typed.updateTyped(opticFinder8, typedx -> {
							Optional<? extends Typed<?>> optional6 = typedx.getOptionalTyped(opticFinder9);
							if (!optional6.isPresent()) {
								return typedx;
							} else {
								List<? extends Typed<?>> list7 = ((Typed)optional6.get()).getAllTyped(opticFinder12);
								IntSet intSet8 = new IntOpenHashSet();
	
								for (Typed<?> typed10 : list7) {
									ajp.a a11 = new ajp.a(typed10, this.getInputSchema());
									if (!a11.b()) {
										for (int integer12 = 0; integer12 < 4096; integer12++) {
											int integer13 = a11.c(integer12);
											if (a11.a(integer13)) {
												intSet8.add(a11.c() << 12 | integer12);
											}
										}
									}
								}
	
								Dynamic<?> dynamic9 = typedx.get(DSL.remainderFinder());
								int integer10 = dynamic9.get("xPos").asInt(0);
								int integer11 = dynamic9.get("zPos").asInt(0);
								TaggedChoiceType<String> taggedChoiceType12 = (TaggedChoiceType<String>)this.getInputSchema().findChoiceType(ajb.k);
								return typedx.updateTyped(opticFinder6, typedxx -> typedxx.updateTyped(taggedChoiceType12.finder(), typedxxx -> {
										Dynamic<?> dynamic6 = typedxxx.getOrCreate(DSL.remainderFinder());
										int integer7 = dynamic6.get("x").asInt(0) - (integer10 << 4);
										int integer8 = dynamic6.get("y").asInt(0);
										int integer9 = dynamic6.get("z").asInt(0) - (integer11 << 4);
										return intSet8.contains(aib.a(integer7, integer8, integer9)) ? typedxxx.update(taggedChoiceType12.finder(), pair -> pair.mapFirst(string -> {
												if (!Objects.equals(string, "minecraft:chest")) {
													a.warn("Block Entity was expected to be a chest");
												}
	
												return "minecraft:trapped_chest";
											})) : typedxxx;
									}));
							}
						}))
				);
			}
		}
	}

	public static final class a extends aib.b {
		@Nullable
		private IntSet e;

		public a(Typed<?> typed, Schema schema) {
			super(typed, schema);
		}

		@Override
		protected boolean a() {
			this.e = new IntOpenHashSet();

			for (int integer2 = 0; integer2 < this.b.size(); integer2++) {
				Dynamic<?> dynamic3 = (Dynamic<?>)this.b.get(integer2);
				String string4 = dynamic3.get("Name").asString("");
				if (Objects.equals(string4, "minecraft:trapped_chest")) {
					this.e.add(integer2);
				}
			}

			return this.e.isEmpty();
		}

		public boolean a(int integer) {
			return this.e.contains(integer);
		}
	}
}
