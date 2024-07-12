import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class afs extends DataFix {
	private static final Logger a = LogManager.getLogger();
	private static final BitSet b = new BitSet(256);
	private static final BitSet c = new BitSet(256);
	private static final Dynamic<?> d = afn.b("{Name:'minecraft:pumpkin'}");
	private static final Dynamic<?> e = afn.b("{Name:'minecraft:podzol',Properties:{snowy:'true'}}");
	private static final Dynamic<?> f = afn.b("{Name:'minecraft:grass_block',Properties:{snowy:'true'}}");
	private static final Dynamic<?> g = afn.b("{Name:'minecraft:mycelium',Properties:{snowy:'true'}}");
	private static final Dynamic<?> h = afn.b("{Name:'minecraft:sunflower',Properties:{half:'upper'}}");
	private static final Dynamic<?> i = afn.b("{Name:'minecraft:lilac',Properties:{half:'upper'}}");
	private static final Dynamic<?> j = afn.b("{Name:'minecraft:tall_grass',Properties:{half:'upper'}}");
	private static final Dynamic<?> k = afn.b("{Name:'minecraft:large_fern',Properties:{half:'upper'}}");
	private static final Dynamic<?> l = afn.b("{Name:'minecraft:rose_bush',Properties:{half:'upper'}}");
	private static final Dynamic<?> m = afn.b("{Name:'minecraft:peony',Properties:{half:'upper'}}");
	private static final Map<String, Dynamic<?>> n = DataFixUtils.make(Maps.<String, Dynamic<?>>newHashMap(), hashMap -> {
		hashMap.put("minecraft:air0", afn.b("{Name:'minecraft:flower_pot'}"));
		hashMap.put("minecraft:red_flower0", afn.b("{Name:'minecraft:potted_poppy'}"));
		hashMap.put("minecraft:red_flower1", afn.b("{Name:'minecraft:potted_blue_orchid'}"));
		hashMap.put("minecraft:red_flower2", afn.b("{Name:'minecraft:potted_allium'}"));
		hashMap.put("minecraft:red_flower3", afn.b("{Name:'minecraft:potted_azure_bluet'}"));
		hashMap.put("minecraft:red_flower4", afn.b("{Name:'minecraft:potted_red_tulip'}"));
		hashMap.put("minecraft:red_flower5", afn.b("{Name:'minecraft:potted_orange_tulip'}"));
		hashMap.put("minecraft:red_flower6", afn.b("{Name:'minecraft:potted_white_tulip'}"));
		hashMap.put("minecraft:red_flower7", afn.b("{Name:'minecraft:potted_pink_tulip'}"));
		hashMap.put("minecraft:red_flower8", afn.b("{Name:'minecraft:potted_oxeye_daisy'}"));
		hashMap.put("minecraft:yellow_flower0", afn.b("{Name:'minecraft:potted_dandelion'}"));
		hashMap.put("minecraft:sapling0", afn.b("{Name:'minecraft:potted_oak_sapling'}"));
		hashMap.put("minecraft:sapling1", afn.b("{Name:'minecraft:potted_spruce_sapling'}"));
		hashMap.put("minecraft:sapling2", afn.b("{Name:'minecraft:potted_birch_sapling'}"));
		hashMap.put("minecraft:sapling3", afn.b("{Name:'minecraft:potted_jungle_sapling'}"));
		hashMap.put("minecraft:sapling4", afn.b("{Name:'minecraft:potted_acacia_sapling'}"));
		hashMap.put("minecraft:sapling5", afn.b("{Name:'minecraft:potted_dark_oak_sapling'}"));
		hashMap.put("minecraft:red_mushroom0", afn.b("{Name:'minecraft:potted_red_mushroom'}"));
		hashMap.put("minecraft:brown_mushroom0", afn.b("{Name:'minecraft:potted_brown_mushroom'}"));
		hashMap.put("minecraft:deadbush0", afn.b("{Name:'minecraft:potted_dead_bush'}"));
		hashMap.put("minecraft:tallgrass2", afn.b("{Name:'minecraft:potted_fern'}"));
		hashMap.put("minecraft:cactus0", afn.b(2240));
	});
	private static final Map<String, Dynamic<?>> o = DataFixUtils.make(Maps.<String, Dynamic<?>>newHashMap(), hashMap -> {
		a(hashMap, 0, "skeleton", "skull");
		a(hashMap, 1, "wither_skeleton", "skull");
		a(hashMap, 2, "zombie", "head");
		a(hashMap, 3, "player", "head");
		a(hashMap, 4, "creeper", "head");
		a(hashMap, 5, "dragon", "head");
	});
	private static final Map<String, Dynamic<?>> p = DataFixUtils.make(Maps.<String, Dynamic<?>>newHashMap(), hashMap -> {
		a(hashMap, "oak_door", 1024);
		a(hashMap, "iron_door", 1136);
		a(hashMap, "spruce_door", 3088);
		a(hashMap, "birch_door", 3104);
		a(hashMap, "jungle_door", 3120);
		a(hashMap, "acacia_door", 3136);
		a(hashMap, "dark_oak_door", 3152);
	});
	private static final Map<String, Dynamic<?>> q = DataFixUtils.make(Maps.<String, Dynamic<?>>newHashMap(), hashMap -> {
		for (int integer2 = 0; integer2 < 26; integer2++) {
			hashMap.put("true" + integer2, afn.b("{Name:'minecraft:note_block',Properties:{powered:'true',note:'" + integer2 + "'}}"));
			hashMap.put("false" + integer2, afn.b("{Name:'minecraft:note_block',Properties:{powered:'false',note:'" + integer2 + "'}}"));
		}
	});
	private static final Int2ObjectMap<String> r = DataFixUtils.make(new Int2ObjectOpenHashMap<>(), int2ObjectOpenHashMap -> {
		int2ObjectOpenHashMap.put(0, "white");
		int2ObjectOpenHashMap.put(1, "orange");
		int2ObjectOpenHashMap.put(2, "magenta");
		int2ObjectOpenHashMap.put(3, "light_blue");
		int2ObjectOpenHashMap.put(4, "yellow");
		int2ObjectOpenHashMap.put(5, "lime");
		int2ObjectOpenHashMap.put(6, "pink");
		int2ObjectOpenHashMap.put(7, "gray");
		int2ObjectOpenHashMap.put(8, "light_gray");
		int2ObjectOpenHashMap.put(9, "cyan");
		int2ObjectOpenHashMap.put(10, "purple");
		int2ObjectOpenHashMap.put(11, "blue");
		int2ObjectOpenHashMap.put(12, "brown");
		int2ObjectOpenHashMap.put(13, "green");
		int2ObjectOpenHashMap.put(14, "red");
		int2ObjectOpenHashMap.put(15, "black");
	});
	private static final Map<String, Dynamic<?>> s = DataFixUtils.make(Maps.<String, Dynamic<?>>newHashMap(), hashMap -> {
		for (Entry<String> entry3 : r.int2ObjectEntrySet()) {
			if (!Objects.equals(entry3.getValue(), "red")) {
				a(hashMap, entry3.getIntKey(), (String)entry3.getValue());
			}
		}
	});
	private static final Map<String, Dynamic<?>> t = DataFixUtils.make(Maps.<String, Dynamic<?>>newHashMap(), hashMap -> {
		for (Entry<String> entry3 : r.int2ObjectEntrySet()) {
			if (!Objects.equals(entry3.getValue(), "white")) {
				b(hashMap, 15 - entry3.getIntKey(), (String)entry3.getValue());
			}
		}
	});
	private static final Dynamic<?> u = afn.b(0);

	public afs(Schema schema, boolean boolean2) {
		super(schema, boolean2);
	}

	private static void a(Map<String, Dynamic<?>> map, int integer, String string3, String string4) {
		map.put(integer + "north", afn.b("{Name:'minecraft:" + string3 + "_wall_" + string4 + "',Properties:{facing:'north'}}"));
		map.put(integer + "east", afn.b("{Name:'minecraft:" + string3 + "_wall_" + string4 + "',Properties:{facing:'east'}}"));
		map.put(integer + "south", afn.b("{Name:'minecraft:" + string3 + "_wall_" + string4 + "',Properties:{facing:'south'}}"));
		map.put(integer + "west", afn.b("{Name:'minecraft:" + string3 + "_wall_" + string4 + "',Properties:{facing:'west'}}"));

		for (int integer5 = 0; integer5 < 16; integer5++) {
			map.put(integer + "" + integer5, afn.b("{Name:'minecraft:" + string3 + "_" + string4 + "',Properties:{rotation:'" + integer5 + "'}}"));
		}
	}

	private static void a(Map<String, Dynamic<?>> map, String string, int integer) {
		map.put(
			"minecraft:" + string + "eastlowerleftfalsefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "eastlowerleftfalsetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "eastlowerlefttruefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "eastlowerlefttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}")
		);
		map.put("minecraft:" + string + "eastlowerrightfalsefalse", afn.b(integer));
		map.put(
			"minecraft:" + string + "eastlowerrightfalsetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}")
		);
		map.put("minecraft:" + string + "eastlowerrighttruefalse", afn.b(integer + 4));
		map.put(
			"minecraft:" + string + "eastlowerrighttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}")
		);
		map.put("minecraft:" + string + "eastupperleftfalsefalse", afn.b(integer + 8));
		map.put("minecraft:" + string + "eastupperleftfalsetrue", afn.b(integer + 10));
		map.put(
			"minecraft:" + string + "eastupperlefttruefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "eastupperlefttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}")
		);
		map.put("minecraft:" + string + "eastupperrightfalsefalse", afn.b(integer + 9));
		map.put("minecraft:" + string + "eastupperrightfalsetrue", afn.b(integer + 11));
		map.put(
			"minecraft:" + string + "eastupperrighttruefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "eastupperrighttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "northlowerleftfalsefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "northlowerleftfalsetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "northlowerlefttruefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "northlowerlefttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}")
		);
		map.put("minecraft:" + string + "northlowerrightfalsefalse", afn.b(integer + 3));
		map.put(
			"minecraft:" + string + "northlowerrightfalsetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}")
		);
		map.put("minecraft:" + string + "northlowerrighttruefalse", afn.b(integer + 7));
		map.put(
			"minecraft:" + string + "northlowerrighttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "northupperleftfalsefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "northupperleftfalsetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "northupperlefttruefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "northupperlefttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "northupperrightfalsefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "northupperrightfalsetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "northupperrighttruefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "northupperrighttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "southlowerleftfalsefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "southlowerleftfalsetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "southlowerlefttruefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "southlowerlefttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}")
		);
		map.put("minecraft:" + string + "southlowerrightfalsefalse", afn.b(integer + 1));
		map.put(
			"minecraft:" + string + "southlowerrightfalsetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}")
		);
		map.put("minecraft:" + string + "southlowerrighttruefalse", afn.b(integer + 5));
		map.put(
			"minecraft:" + string + "southlowerrighttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "southupperleftfalsefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "southupperleftfalsetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "southupperlefttruefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "southupperlefttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "southupperrightfalsefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "southupperrightfalsetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "southupperrighttruefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "southupperrighttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "westlowerleftfalsefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "westlowerleftfalsetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "westlowerlefttruefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "westlowerlefttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}")
		);
		map.put("minecraft:" + string + "westlowerrightfalsefalse", afn.b(integer + 2));
		map.put(
			"minecraft:" + string + "westlowerrightfalsetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}")
		);
		map.put("minecraft:" + string + "westlowerrighttruefalse", afn.b(integer + 6));
		map.put(
			"minecraft:" + string + "westlowerrighttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "westupperleftfalsefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "westupperleftfalsetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "westupperlefttruefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "westupperlefttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "westupperrightfalsefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "westupperrightfalsetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}")
		);
		map.put(
			"minecraft:" + string + "westupperrighttruefalse",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}")
		);
		map.put(
			"minecraft:" + string + "westupperrighttruetrue",
			afn.b("{Name:'minecraft:" + string + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}")
		);
	}

	private static void a(Map<String, Dynamic<?>> map, int integer, String string) {
		map.put("southfalsefoot" + integer, afn.b("{Name:'minecraft:" + string + "_bed',Properties:{facing:'south',occupied:'false',part:'foot'}}"));
		map.put("westfalsefoot" + integer, afn.b("{Name:'minecraft:" + string + "_bed',Properties:{facing:'west',occupied:'false',part:'foot'}}"));
		map.put("northfalsefoot" + integer, afn.b("{Name:'minecraft:" + string + "_bed',Properties:{facing:'north',occupied:'false',part:'foot'}}"));
		map.put("eastfalsefoot" + integer, afn.b("{Name:'minecraft:" + string + "_bed',Properties:{facing:'east',occupied:'false',part:'foot'}}"));
		map.put("southfalsehead" + integer, afn.b("{Name:'minecraft:" + string + "_bed',Properties:{facing:'south',occupied:'false',part:'head'}}"));
		map.put("westfalsehead" + integer, afn.b("{Name:'minecraft:" + string + "_bed',Properties:{facing:'west',occupied:'false',part:'head'}}"));
		map.put("northfalsehead" + integer, afn.b("{Name:'minecraft:" + string + "_bed',Properties:{facing:'north',occupied:'false',part:'head'}}"));
		map.put("eastfalsehead" + integer, afn.b("{Name:'minecraft:" + string + "_bed',Properties:{facing:'east',occupied:'false',part:'head'}}"));
		map.put("southtruehead" + integer, afn.b("{Name:'minecraft:" + string + "_bed',Properties:{facing:'south',occupied:'true',part:'head'}}"));
		map.put("westtruehead" + integer, afn.b("{Name:'minecraft:" + string + "_bed',Properties:{facing:'west',occupied:'true',part:'head'}}"));
		map.put("northtruehead" + integer, afn.b("{Name:'minecraft:" + string + "_bed',Properties:{facing:'north',occupied:'true',part:'head'}}"));
		map.put("easttruehead" + integer, afn.b("{Name:'minecraft:" + string + "_bed',Properties:{facing:'east',occupied:'true',part:'head'}}"));
	}

	private static void b(Map<String, Dynamic<?>> map, int integer, String string) {
		for (int integer4 = 0; integer4 < 16; integer4++) {
			map.put("" + integer4 + "_" + integer, afn.b("{Name:'minecraft:" + string + "_banner',Properties:{rotation:'" + integer4 + "'}}"));
		}

		map.put("north_" + integer, afn.b("{Name:'minecraft:" + string + "_wall_banner',Properties:{facing:'north'}}"));
		map.put("south_" + integer, afn.b("{Name:'minecraft:" + string + "_wall_banner',Properties:{facing:'south'}}"));
		map.put("west_" + integer, afn.b("{Name:'minecraft:" + string + "_wall_banner',Properties:{facing:'west'}}"));
		map.put("east_" + integer, afn.b("{Name:'minecraft:" + string + "_wall_banner',Properties:{facing:'east'}}"));
	}

	public static String a(Dynamic<?> dynamic) {
		return dynamic.get("Name").asString("");
	}

	public static String a(Dynamic<?> dynamic, String string) {
		return dynamic.get("Properties").get(string).asString("");
	}

	public static int a(adm<Dynamic<?>> adm, Dynamic<?> dynamic) {
		int integer3 = adm.a(dynamic);
		if (integer3 == -1) {
			integer3 = adm.c(dynamic);
		}

		return integer3;
	}

	private Dynamic<?> b(Dynamic<?> dynamic) {
		Optional<? extends Dynamic<?>> optional3 = dynamic.get("Level").result();
		return optional3.isPresent() && ((Dynamic)optional3.get()).get("Sections").asStreamOpt().result().isPresent()
			? dynamic.set("Level", new afs.d((Dynamic<?>)optional3.get()).a())
			: dynamic;
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type2 = this.getInputSchema().getType(ajb.c);
		Type<?> type3 = this.getOutputSchema().getType(ajb.c);
		return this.writeFixAndRead("ChunkPalettedStorageFix", type2, type3, this::b);
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

	static {
		c.set(2);
		c.set(3);
		c.set(110);
		c.set(140);
		c.set(144);
		c.set(25);
		c.set(86);
		c.set(26);
		c.set(176);
		c.set(177);
		c.set(175);
		c.set(64);
		c.set(71);
		c.set(193);
		c.set(194);
		c.set(195);
		c.set(196);
		c.set(197);
		b.set(54);
		b.set(146);
		b.set(25);
		b.set(26);
		b.set(51);
		b.set(53);
		b.set(67);
		b.set(108);
		b.set(109);
		b.set(114);
		b.set(128);
		b.set(134);
		b.set(135);
		b.set(136);
		b.set(156);
		b.set(163);
		b.set(164);
		b.set(180);
		b.set(203);
		b.set(55);
		b.set(85);
		b.set(113);
		b.set(188);
		b.set(189);
		b.set(190);
		b.set(191);
		b.set(192);
		b.set(93);
		b.set(94);
		b.set(101);
		b.set(102);
		b.set(160);
		b.set(106);
		b.set(107);
		b.set(183);
		b.set(184);
		b.set(185);
		b.set(186);
		b.set(187);
		b.set(132);
		b.set(139);
		b.set(199);
	}

	static class a {
		private final byte[] a;

		public a() {
			this.a = new byte[2048];
		}

		public a(byte[] arr) {
			this.a = arr;
			if (arr.length != 2048) {
				throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + arr.length);
			}
		}

		public int a(int integer1, int integer2, int integer3) {
			int integer5 = this.b(integer2 << 8 | integer3 << 4 | integer1);
			return this.a(integer2 << 8 | integer3 << 4 | integer1) ? this.a[integer5] & 15 : this.a[integer5] >> 4 & 15;
		}

		private boolean a(int integer) {
			return (integer & 1) == 0;
		}

		private int b(int integer) {
			return integer >> 1;
		}
	}

	public static enum b {
		DOWN(afs.b.b.NEGATIVE, afs.b.a.Y),
		UP(afs.b.b.POSITIVE, afs.b.a.Y),
		NORTH(afs.b.b.NEGATIVE, afs.b.a.Z),
		SOUTH(afs.b.b.POSITIVE, afs.b.a.Z),
		WEST(afs.b.b.NEGATIVE, afs.b.a.X),
		EAST(afs.b.b.POSITIVE, afs.b.a.X);

		private final afs.b.a g;
		private final afs.b.b h;

		private b(afs.b.b b, afs.b.a a) {
			this.g = a;
			this.h = b;
		}

		public afs.b.b a() {
			return this.h;
		}

		public afs.b.a b() {
			return this.g;
		}

		public static enum a {
			X,
			Y,
			Z;
		}

		public static enum b {
			POSITIVE(1),
			NEGATIVE(-1);

			private final int c;

			private b(int integer3) {
				this.c = integer3;
			}

			public int a() {
				return this.c;
			}
		}
	}

	static class c {
		private final adm<Dynamic<?>> b = new adm<>(32);
		private final List<Dynamic<?>> c;
		private final Dynamic<?> d;
		private final boolean e;
		private final Int2ObjectMap<IntList> f = new Int2ObjectLinkedOpenHashMap<>();
		private final IntList g = new IntArrayList();
		public final int a;
		private final Set<Dynamic<?>> h = Sets.newIdentityHashSet();
		private final int[] i = new int[4096];

		public c(Dynamic<?> dynamic) {
			this.c = Lists.<Dynamic<?>>newArrayList();
			this.d = dynamic;
			this.a = dynamic.get("Y").asInt(0);
			this.e = dynamic.get("Blocks").result().isPresent();
		}

		public Dynamic<?> a(int integer) {
			if (integer >= 0 && integer <= 4095) {
				Dynamic<?> dynamic3 = this.b.a(this.i[integer]);
				return dynamic3 == null ? afs.u : dynamic3;
			} else {
				return afs.u;
			}
		}

		public void a(int integer, Dynamic<?> dynamic) {
			if (this.h.add(dynamic)) {
				this.c.add("%%FILTER_ME%%".equals(afs.a(dynamic)) ? afs.u : dynamic);
			}

			this.i[integer] = afs.a(this.b, dynamic);
		}

		public int b(int integer) {
			if (!this.e) {
				return integer;
			} else {
				ByteBuffer byteBuffer3 = (ByteBuffer)this.d.get("Blocks").asByteBufferOpt().result().get();
				afs.a a4 = (afs.a)this.d.get("Data").asByteBufferOpt().map(byteBuffer -> new afs.a(DataFixUtils.toArray(byteBuffer))).result().orElseGet(afs.a::new);
				afs.a a5 = (afs.a)this.d.get("Add").asByteBufferOpt().map(byteBuffer -> new afs.a(DataFixUtils.toArray(byteBuffer))).result().orElseGet(afs.a::new);
				this.h.add(afs.u);
				afs.a(this.b, afs.u);
				this.c.add(afs.u);

				for (int integer6 = 0; integer6 < 4096; integer6++) {
					int integer7 = integer6 & 15;
					int integer8 = integer6 >> 8 & 15;
					int integer9 = integer6 >> 4 & 15;
					int integer10 = a5.a(integer7, integer8, integer9) << 12 | (byteBuffer3.get(integer6) & 255) << 4 | a4.a(integer7, integer8, integer9);
					if (afs.c.get(integer10 >> 4)) {
						this.a(integer10 >> 4, integer6);
					}

					if (afs.b.get(integer10 >> 4)) {
						int integer11 = afs.a(integer7 == 0, integer7 == 15, integer9 == 0, integer9 == 15);
						if (integer11 == 0) {
							this.g.add(integer6);
						} else {
							integer |= integer11;
						}
					}

					this.a(integer6, afn.b(integer10));
				}

				return integer;
			}
		}

		private void a(int integer1, int integer2) {
			IntList intList4 = this.f.get(integer1);
			if (intList4 == null) {
				intList4 = new IntArrayList();
				this.f.put(integer1, intList4);
			}

			intList4.add(integer2);
		}

		public Dynamic<?> a() {
			Dynamic<?> dynamic2 = this.d;
			if (!this.e) {
				return dynamic2;
			} else {
				dynamic2 = dynamic2.set("Palette", dynamic2.createList(this.c.stream()));
				int integer3 = Math.max(4, DataFixUtils.ceillog2(this.h.size()));
				aeq aeq4 = new aeq(integer3, 4096);

				for (int integer5 = 0; integer5 < this.i.length; integer5++) {
					aeq4.a(integer5, this.i[integer5]);
				}

				dynamic2 = dynamic2.set("BlockStates", dynamic2.createLongList(Arrays.stream(aeq4.a())));
				dynamic2 = dynamic2.remove("Blocks");
				dynamic2 = dynamic2.remove("Data");
				return dynamic2.remove("Add");
			}
		}
	}

	static final class d {
		private int a;
		private final afs.c[] b = new afs.c[16];
		private final Dynamic<?> c;
		private final int d;
		private final int e;
		private final Int2ObjectMap<Dynamic<?>> f = new Int2ObjectLinkedOpenHashMap<>(16);

		public d(Dynamic<?> dynamic) {
			this.c = dynamic;
			this.d = dynamic.get("xPos").asInt(0) << 4;
			this.e = dynamic.get("zPos").asInt(0) << 4;
			dynamic.get("TileEntities").asStreamOpt().result().ifPresent(stream -> stream.forEach(dynamicx -> {
					int integer3 = dynamicx.get("x").asInt(0) - this.d & 15;
					int integer4 = dynamicx.get("y").asInt(0);
					int integer5 = dynamicx.get("z").asInt(0) - this.e & 15;
					int integer6 = integer4 << 8 | integer5 << 4 | integer3;
					if (this.f.put(integer6, dynamicx) != null) {
						afs.a.warn("In chunk: {}x{} found a duplicate block entity at position: [{}, {}, {}]", this.d, this.e, integer3, integer4, integer5);
					}
				}));
			boolean boolean3 = dynamic.get("convertedFromAlphaFormat").asBoolean(false);
			dynamic.get("Sections").asStreamOpt().result().ifPresent(stream -> stream.forEach(dynamicx -> {
					afs.c c3 = new afs.c(dynamicx);
					this.a = c3.b(this.a);
					this.b[c3.a] = c3;
				}));

			for (afs.c c7 : this.b) {
				if (c7 != null) {
					for (java.util.Map.Entry<Integer, IntList> entry9 : c7.f.entrySet()) {
						int integer10 = c7.a << 12;
						switch (entry9.getKey()) {
							case 2:
								for (int integer12 : (IntList)entry9.getValue()) {
									integer12 |= integer10;
									Dynamic<?> dynamic13 = this.a(integer12);
									if ("minecraft:grass_block".equals(afs.a(dynamic13))) {
										String string14 = afs.a(this.a(a(integer12, afs.b.UP)));
										if ("minecraft:snow".equals(string14) || "minecraft:snow_layer".equals(string14)) {
											this.a(integer12, afs.f);
										}
									}
								}
								break;
							case 3:
								for (int integer12xxxxxxxxx : (IntList)entry9.getValue()) {
									integer12xxxxxxxxx |= integer10;
									Dynamic<?> dynamic13 = this.a(integer12xxxxxxxxx);
									if ("minecraft:podzol".equals(afs.a(dynamic13))) {
										String string14 = afs.a(this.a(a(integer12xxxxxxxxx, afs.b.UP)));
										if ("minecraft:snow".equals(string14) || "minecraft:snow_layer".equals(string14)) {
											this.a(integer12xxxxxxxxx, afs.e);
										}
									}
								}
								break;
							case 25:
								for (int integer12xxxxx : (IntList)entry9.getValue()) {
									integer12xxxxx |= integer10;
									Dynamic<?> dynamic13 = this.c(integer12xxxxx);
									if (dynamic13 != null) {
										String string14 = Boolean.toString(dynamic13.get("powered").asBoolean(false)) + (byte)Math.min(Math.max(dynamic13.get("note").asInt(0), 0), 24);
										this.a(integer12xxxxx, (Dynamic<?>)afs.q.getOrDefault(string14, afs.q.get("false0")));
									}
								}
								break;
							case 26:
								for (int integer12xxxx : (IntList)entry9.getValue()) {
									integer12xxxx |= integer10;
									Dynamic<?> dynamic13 = this.b(integer12xxxx);
									Dynamic<?> dynamic14 = this.a(integer12xxxx);
									if (dynamic13 != null) {
										int integer15 = dynamic13.get("color").asInt(0);
										if (integer15 != 14 && integer15 >= 0 && integer15 < 16) {
											String string16 = afs.a(dynamic14, "facing") + afs.a(dynamic14, "occupied") + afs.a(dynamic14, "part") + integer15;
											if (afs.s.containsKey(string16)) {
												this.a(integer12xxxx, (Dynamic<?>)afs.s.get(string16));
											}
										}
									}
								}
								break;
							case 64:
							case 71:
							case 193:
							case 194:
							case 195:
							case 196:
							case 197:
								for (int integer12xxx : (IntList)entry9.getValue()) {
									integer12xxx |= integer10;
									Dynamic<?> dynamic13 = this.a(integer12xxx);
									if (afs.a(dynamic13).endsWith("_door")) {
										Dynamic<?> dynamic14 = this.a(integer12xxx);
										if ("lower".equals(afs.a(dynamic14, "half"))) {
											int integer15 = a(integer12xxx, afs.b.UP);
											Dynamic<?> dynamic16 = this.a(integer15);
											String string17 = afs.a(dynamic14);
											if (string17.equals(afs.a(dynamic16))) {
												String string18 = afs.a(dynamic14, "facing");
												String string19 = afs.a(dynamic14, "open");
												String string20 = boolean3 ? "left" : afs.a(dynamic16, "hinge");
												String string21 = boolean3 ? "false" : afs.a(dynamic16, "powered");
												this.a(integer12xxx, (Dynamic<?>)afs.p.get(string17 + string18 + "lower" + string20 + string19 + string21));
												this.a(integer15, (Dynamic<?>)afs.p.get(string17 + string18 + "upper" + string20 + string19 + string21));
											}
										}
									}
								}
								break;
							case 86:
								for (int integer12xxxxxxxx : (IntList)entry9.getValue()) {
									integer12xxxxxxxx |= integer10;
									Dynamic<?> dynamic13 = this.a(integer12xxxxxxxx);
									if ("minecraft:carved_pumpkin".equals(afs.a(dynamic13))) {
										String string14 = afs.a(this.a(a(integer12xxxxxxxx, afs.b.DOWN)));
										if ("minecraft:grass_block".equals(string14) || "minecraft:dirt".equals(string14)) {
											this.a(integer12xxxxxxxx, afs.d);
										}
									}
								}
								break;
							case 110:
								for (int integer12xxxxxxx : (IntList)entry9.getValue()) {
									integer12xxxxxxx |= integer10;
									Dynamic<?> dynamic13 = this.a(integer12xxxxxxx);
									if ("minecraft:mycelium".equals(afs.a(dynamic13))) {
										String string14 = afs.a(this.a(a(integer12xxxxxxx, afs.b.UP)));
										if ("minecraft:snow".equals(string14) || "minecraft:snow_layer".equals(string14)) {
											this.a(integer12xxxxxxx, afs.g);
										}
									}
								}
								break;
							case 140:
								for (int integer12xx : (IntList)entry9.getValue()) {
									integer12xx |= integer10;
									Dynamic<?> dynamic13 = this.c(integer12xx);
									if (dynamic13 != null) {
										String string14 = dynamic13.get("Item").asString("") + dynamic13.get("Data").asInt(0);
										this.a(integer12xx, (Dynamic<?>)afs.n.getOrDefault(string14, afs.n.get("minecraft:air0")));
									}
								}
								break;
							case 144:
								for (int integer12xxxxxx : (IntList)entry9.getValue()) {
									integer12xxxxxx |= integer10;
									Dynamic<?> dynamic13 = this.b(integer12xxxxxx);
									if (dynamic13 != null) {
										String string14 = String.valueOf(dynamic13.get("SkullType").asInt(0));
										String string15 = afs.a(this.a(integer12xxxxxx), "facing");
										String string16;
										if (!"up".equals(string15) && !"down".equals(string15)) {
											string16 = string14 + string15;
										} else {
											string16 = string14 + String.valueOf(dynamic13.get("Rot").asInt(0));
										}

										dynamic13.remove("SkullType");
										dynamic13.remove("facing");
										dynamic13.remove("Rot");
										this.a(integer12xxxxxx, (Dynamic<?>)afs.o.getOrDefault(string16, afs.o.get("0north")));
									}
								}
								break;
							case 175:
								for (int integer12x : (IntList)entry9.getValue()) {
									integer12x |= integer10;
									Dynamic<?> dynamic13 = this.a(integer12x);
									if ("upper".equals(afs.a(dynamic13, "half"))) {
										Dynamic<?> dynamic14 = this.a(a(integer12x, afs.b.DOWN));
										String string15 = afs.a(dynamic14);
										if ("minecraft:sunflower".equals(string15)) {
											this.a(integer12x, afs.h);
										} else if ("minecraft:lilac".equals(string15)) {
											this.a(integer12x, afs.i);
										} else if ("minecraft:tall_grass".equals(string15)) {
											this.a(integer12x, afs.j);
										} else if ("minecraft:large_fern".equals(string15)) {
											this.a(integer12x, afs.k);
										} else if ("minecraft:rose_bush".equals(string15)) {
											this.a(integer12x, afs.l);
										} else if ("minecraft:peony".equals(string15)) {
											this.a(integer12x, afs.m);
										}
									}
								}
								break;
							case 176:
							case 177:
								for (int integer12xxxxxxxxxx : (IntList)entry9.getValue()) {
									integer12xxxxxxxxxx |= integer10;
									Dynamic<?> dynamic13 = this.b(integer12xxxxxxxxxx);
									Dynamic<?> dynamic14 = this.a(integer12xxxxxxxxxx);
									if (dynamic13 != null) {
										int integer15 = dynamic13.get("Base").asInt(0);
										if (integer15 != 15 && integer15 >= 0 && integer15 < 16) {
											String string16 = afs.a(dynamic14, entry9.getKey() == 176 ? "rotation" : "facing") + "_" + integer15;
											if (afs.t.containsKey(string16)) {
												this.a(integer12xxxxxxxxxx, (Dynamic<?>)afs.t.get(string16));
											}
										}
									}
								}
						}
					}
				}
			}
		}

		@Nullable
		private Dynamic<?> b(int integer) {
			return this.f.get(integer);
		}

		@Nullable
		private Dynamic<?> c(int integer) {
			return this.f.remove(integer);
		}

		public static int a(int integer, afs.b b) {
			switch (b.b()) {
				case X:
					int integer3 = (integer & 15) + b.a().a();
					return integer3 >= 0 && integer3 <= 15 ? integer & -16 | integer3 : -1;
				case Y:
					int integer4 = (integer >> 8) + b.a().a();
					return integer4 >= 0 && integer4 <= 255 ? integer & 0xFF | integer4 << 8 : -1;
				case Z:
					int integer5 = (integer >> 4 & 15) + b.a().a();
					return integer5 >= 0 && integer5 <= 15 ? integer & -241 | integer5 << 4 : -1;
				default:
					return -1;
			}
		}

		private void a(int integer, Dynamic<?> dynamic) {
			if (integer >= 0 && integer <= 65535) {
				afs.c c4 = this.d(integer);
				if (c4 != null) {
					c4.a(integer & 4095, dynamic);
				}
			}
		}

		@Nullable
		private afs.c d(int integer) {
			int integer3 = integer >> 12;
			return integer3 < this.b.length ? this.b[integer3] : null;
		}

		public Dynamic<?> a(int integer) {
			if (integer >= 0 && integer <= 65535) {
				afs.c c3 = this.d(integer);
				return c3 == null ? afs.u : c3.a(integer & 4095);
			} else {
				return afs.u;
			}
		}

		public Dynamic<?> a() {
			Dynamic<?> dynamic2 = this.c;
			if (this.f.isEmpty()) {
				dynamic2 = dynamic2.remove("TileEntities");
			} else {
				dynamic2 = dynamic2.set("TileEntities", dynamic2.createList(this.f.values().stream()));
			}

			Dynamic<?> dynamic3 = dynamic2.emptyMap();
			List<Dynamic<?>> list4 = Lists.<Dynamic<?>>newArrayList();

			for (afs.c c8 : this.b) {
				if (c8 != null) {
					list4.add(c8.a());
					dynamic3 = dynamic3.set(String.valueOf(c8.a), dynamic3.createIntList(Arrays.stream(c8.g.toIntArray())));
				}
			}

			Dynamic<?> dynamic5 = dynamic2.emptyMap();
			dynamic5 = dynamic5.set("Sides", dynamic5.createByte((byte)this.a));
			dynamic5 = dynamic5.set("Indices", dynamic3);
			return dynamic2.set("UpgradeData", dynamic5).set("Sections", dynamic5.createList(list4.stream()));
		}
	}
}
