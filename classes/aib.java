import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class aib extends DataFix {
	private static final int[][] a = new int[][]{{-1, 0, 0}, {1, 0, 0}, {0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}};
	private static final Object2IntMap<String> b = DataFixUtils.make(new Object2IntOpenHashMap<>(), object2IntOpenHashMap -> {
		object2IntOpenHashMap.put("minecraft:acacia_leaves", 0);
		object2IntOpenHashMap.put("minecraft:birch_leaves", 1);
		object2IntOpenHashMap.put("minecraft:dark_oak_leaves", 2);
		object2IntOpenHashMap.put("minecraft:jungle_leaves", 3);
		object2IntOpenHashMap.put("minecraft:oak_leaves", 4);
		object2IntOpenHashMap.put("minecraft:spruce_leaves", 5);
	});
	private static final Set<String> c = ImmutableSet.of(
		"minecraft:acacia_bark",
		"minecraft:birch_bark",
		"minecraft:dark_oak_bark",
		"minecraft:jungle_bark",
		"minecraft:oak_bark",
		"minecraft:spruce_bark",
		"minecraft:acacia_log",
		"minecraft:birch_log",
		"minecraft:dark_oak_log",
		"minecraft:jungle_log",
		"minecraft:oak_log",
		"minecraft:spruce_log",
		"minecraft:stripped_acacia_log",
		"minecraft:stripped_birch_log",
		"minecraft:stripped_dark_oak_log",
		"minecraft:stripped_jungle_log",
		"minecraft:stripped_oak_log",
		"minecraft:stripped_spruce_log"
	);

	public aib(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.c);
		OpticFinder<?> opticFinder3 = type2.findField("Level");
		OpticFinder<?> opticFinder4 = opticFinder3.type().findField("Sections");
		Type<?> type5 = opticFinder4.type();
		if (!(type5 instanceof ListType)) {
			throw new IllegalStateException("Expecting sections to be a list.");
		} else {
			Type<?> type6 = ((ListType)type5).getElement();
			OpticFinder<?> opticFinder7 = DSL.typeFinder(type6);
			return this.fixTypeEverywhereTyped(
				"Leaves fix",
				type2,
				typed -> typed.updateTyped(
						opticFinder3,
						typedx -> {
							int[] arr5 = new int[]{0};
							Typed<?> typed6 = typedx.updateTyped(
								opticFinder4,
								typedxx -> {
									Int2ObjectMap<aib.a> int2ObjectMap5 = new Int2ObjectOpenHashMap<>(
										(Map<? extends Integer, ? extends aib.a>)typedxx.getAllTyped(opticFinder7)
											.stream()
											.map(typedxxx -> new aib.a(typedxxx, this.getInputSchema()))
											.collect(Collectors.toMap(aib.b::c, a -> a))
									);
									if (int2ObjectMap5.values().stream().allMatch(aib.b::b)) {
										return typedxx;
									} else {
										List<IntSet> list6 = Lists.<IntSet>newArrayList();
			
										for (int integer7 = 0; integer7 < 7; integer7++) {
											list6.add(new IntOpenHashSet());
										}
			
										for (aib.a a8 : int2ObjectMap5.values()) {
											if (!a8.b()) {
												for (int integer9 = 0; integer9 < 4096; integer9++) {
													int integer10 = a8.c(integer9);
													if (a8.a(integer10)) {
														((IntSet)list6.get(0)).add(a8.c() << 12 | integer9);
													} else if (a8.b(integer10)) {
														int integer11 = this.a(integer9);
														int integer12 = this.c(integer9);
														arr5[0] |= a(integer11 == 0, integer11 == 15, integer12 == 0, integer12 == 15);
													}
												}
											}
										}
			
										for (int integer7 = 1; integer7 < 7; integer7++) {
											IntSet intSet8 = (IntSet)list6.get(integer7 - 1);
											IntSet intSet9 = (IntSet)list6.get(integer7);
											IntIterator intIterator10 = intSet8.iterator();
			
											while (intIterator10.hasNext()) {
												int integer11 = intIterator10.nextInt();
												int integer12 = this.a(integer11);
												int integer13 = this.b(integer11);
												int integer14 = this.c(integer11);
			
												for (int[] arr18 : a) {
													int integer19 = integer12 + arr18[0];
													int integer20 = integer13 + arr18[1];
													int integer21 = integer14 + arr18[2];
													if (integer19 >= 0 && integer19 <= 15 && integer21 >= 0 && integer21 <= 15 && integer20 >= 0 && integer20 <= 255) {
														aib.a a22 = int2ObjectMap5.get(integer20 >> 4);
														if (a22 != null && !a22.b()) {
															int integer23 = a(integer19, integer20 & 15, integer21);
															int integer24 = a22.c(integer23);
															if (a22.b(integer24)) {
																int integer25 = a22.d(integer24);
																if (integer25 > integer7) {
																	a22.a(integer23, integer24, integer7);
																	intSet9.add(a(integer19, integer20, integer21));
																}
															}
														}
													}
												}
											}
										}
			
										return typedxx.updateTyped(opticFinder7, typedxxx -> int2ObjectMap5.get(typedxxx.get(DSL.remainderFinder()).get("Y").asInt(0)).a(typedxxx));
									}
								}
							);
							if (arr5[0] != 0) {
								typed6 = typed6.update(DSL.remainderFinder(), dynamic -> {
									Dynamic<?> dynamic3 = DataFixUtils.orElse(dynamic.get("UpgradeData").result(), dynamic.emptyMap());
									return dynamic.set("UpgradeData", dynamic3.set("Sides", dynamic.createByte((byte)(dynamic3.get("Sides").asByte((byte)0) | arr5[0]))));
								});
							}
		
							return typed6;
						}
					)
			);
		}
	}

	public static int a(int integer1, int integer2, int integer3) {
		return integer2 << 8 | integer3 << 4 | integer1;
	}

	private int a(int integer) {
		return integer & 15;
	}

	private int b(int integer) {
		return integer >> 8 & 0xFF;
	}

	private int c(int integer) {
		return integer >> 4 & 15;
	}

	public static int a(boolean boolean1, boolean boolean2, boolean boolean3, boolean boolean4) {
		int integer5 = 0;
		if (boolean3) {
			if (boolean2) {
				integer5 |= 2;
			} else if (boolean1) {
				integer5 |= 128;
			} else {
				integer5 |= 1;
			}
		} else if (boolean4) {
			if (boolean1) {
				integer5 |= 32;
			} else if (boolean2) {
				integer5 |= 8;
			} else {
				integer5 |= 16;
			}
		} else if (boolean2) {
			integer5 |= 4;
		} else if (boolean1) {
			integer5 |= 64;
		}

		return integer5;
	}

	public static final class a extends aib.b {
		@Nullable
		private IntSet e;
		@Nullable
		private IntSet f;
		@Nullable
		private Int2IntMap g;

		public a(Typed<?> typed, Schema schema) {
			super(typed, schema);
		}

		@Override
		protected boolean a() {
			this.e = new IntOpenHashSet();
			this.f = new IntOpenHashSet();
			this.g = new Int2IntOpenHashMap();

			for (int integer2 = 0; integer2 < this.b.size(); integer2++) {
				Dynamic<?> dynamic3 = (Dynamic<?>)this.b.get(integer2);
				String string4 = dynamic3.get("Name").asString("");
				if (aib.b.containsKey(string4)) {
					boolean boolean5 = Objects.equals(dynamic3.get("Properties").get("decayable").asString(""), "false");
					this.e.add(integer2);
					this.g.put(this.a(string4, boolean5, 7), integer2);
					this.b.set(integer2, this.a(dynamic3, string4, boolean5, 7));
				}

				if (aib.c.contains(string4)) {
					this.f.add(integer2);
				}
			}

			return this.e.isEmpty() && this.f.isEmpty();
		}

		private Dynamic<?> a(Dynamic<?> dynamic, String string, boolean boolean3, int integer) {
			Dynamic<?> dynamic6 = dynamic.emptyMap();
			dynamic6 = dynamic6.set("persistent", dynamic6.createString(boolean3 ? "true" : "false"));
			dynamic6 = dynamic6.set("distance", dynamic6.createString(Integer.toString(integer)));
			Dynamic<?> dynamic7 = dynamic.emptyMap();
			dynamic7 = dynamic7.set("Properties", dynamic6);
			return dynamic7.set("Name", dynamic7.createString(string));
		}

		public boolean a(int integer) {
			return this.f.contains(integer);
		}

		public boolean b(int integer) {
			return this.e.contains(integer);
		}

		private int d(int integer) {
			return this.a(integer) ? 0 : Integer.parseInt(((Dynamic)this.b.get(integer)).get("Properties").get("distance").asString(""));
		}

		private void a(int integer1, int integer2, int integer3) {
			Dynamic<?> dynamic5 = (Dynamic<?>)this.b.get(integer2);
			String string6 = dynamic5.get("Name").asString("");
			boolean boolean7 = Objects.equals(dynamic5.get("Properties").get("persistent").asString(""), "true");
			int integer8 = this.a(string6, boolean7, integer3);
			if (!this.g.containsKey(integer8)) {
				int integer9 = this.b.size();
				this.e.add(integer9);
				this.g.put(integer8, integer9);
				this.b.add(this.a(dynamic5, string6, boolean7, integer3));
			}

			int integer9 = this.g.get(integer8);
			if (1 << this.d.b() <= integer9) {
				aeq aeq10 = new aeq(this.d.b() + 1, 4096);

				for (int integer11 = 0; integer11 < 4096; integer11++) {
					aeq10.a(integer11, this.d.a(integer11));
				}

				this.d = aeq10;
			}

			this.d.a(integer1, integer9);
		}
	}

	public abstract static class b {
		private final Type<Pair<String, Dynamic<?>>> e = DSL.named(ajb.m.typeName(), DSL.remainderType());
		protected final OpticFinder<List<Pair<String, Dynamic<?>>>> a = DSL.fieldFinder("Palette", DSL.list(this.e));
		protected final List<Dynamic<?>> b;
		protected final int c;
		@Nullable
		protected aeq d;

		public b(Typed<?> typed, Schema schema) {
			if (!Objects.equals(schema.getType(ajb.m), this.e)) {
				throw new IllegalStateException("Block state type is not what was expected.");
			} else {
				Optional<List<Pair<String, Dynamic<?>>>> optional4 = typed.getOptional(this.a);
				this.b = (List<Dynamic<?>>)optional4.map(list -> (List)list.stream().map(Pair::getSecond).collect(Collectors.toList())).orElse(ImmutableList.of());
				Dynamic<?> dynamic5 = typed.get(DSL.remainderFinder());
				this.c = dynamic5.get("Y").asInt(0);
				this.a(dynamic5);
			}
		}

		protected void a(Dynamic<?> dynamic) {
			if (this.a()) {
				this.d = null;
			} else {
				long[] arr3 = dynamic.get("BlockStates").asLongStream().toArray();
				int integer4 = Math.max(4, DataFixUtils.ceillog2(this.b.size()));
				this.d = new aeq(integer4, 4096, arr3);
			}
		}

		public Typed<?> a(Typed<?> typed) {
			return this.b()
				? typed
				: typed.update(DSL.remainderFinder(), dynamic -> dynamic.set("BlockStates", dynamic.createLongList(Arrays.stream(this.d.a()))))
					.set(this.a, (List<Pair<String, Dynamic<?>>>)this.b.stream().map(dynamic -> Pair.of(ajb.m.typeName(), dynamic)).collect(Collectors.toList()));
		}

		public boolean b() {
			return this.d == null;
		}

		public int c(int integer) {
			return this.d.a(integer);
		}

		protected int a(String string, boolean boolean2, int integer) {
			return aib.b.get(string) << 5 | (boolean2 ? 16 : 0) | integer;
		}

		int c() {
			return this.c;
		}

		protected abstract boolean a();
	}
}
