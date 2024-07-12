import com.mojang.datafixers.DataFixUtils;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class afn {
	private static final Logger a = LogManager.getLogger();
	private static final Dynamic<?>[] b = new Dynamic[4096];
	private static final Dynamic<?>[] c = new Dynamic[256];
	private static final Object2IntMap<Dynamic<?>> d = DataFixUtils.make(
		new Object2IntOpenHashMap<>(), object2IntOpenHashMap -> object2IntOpenHashMap.defaultReturnValue(-1)
	);
	private static final Object2IntMap<String> e = DataFixUtils.make(
		new Object2IntOpenHashMap<>(), object2IntOpenHashMap -> object2IntOpenHashMap.defaultReturnValue(-1)
	);

	private static void a(int integer, String string, String... arr) {
		Dynamic<?> dynamic4 = b(string);
		b[integer] = dynamic4;
		int integer5 = integer >> 4;
		if (c[integer5] == null) {
			c[integer5] = dynamic4;
		}

		for (String string9 : arr) {
			Dynamic<?> dynamic10 = b(string9);
			String string11 = dynamic10.get("Name").asString("");
			e.putIfAbsent(string11, integer);
			d.put(dynamic10, integer);
		}
	}

	private static void a() {
		for (int integer1 = 0; integer1 < b.length; integer1++) {
			if (b[integer1] == null) {
				b[integer1] = c[integer1 >> 4];
			}
		}
	}

	public static Dynamic<?> a(Dynamic<?> dynamic) {
		int integer2 = d.getInt(dynamic);
		if (integer2 >= 0 && integer2 < b.length) {
			Dynamic<?> dynamic3 = b[integer2];
			return dynamic3 == null ? dynamic : dynamic3;
		} else {
			return dynamic;
		}
	}

	public static String a(String string) {
		int integer2 = e.getInt(string);
		if (integer2 >= 0 && integer2 < b.length) {
			Dynamic<?> dynamic3 = b[integer2];
			return dynamic3 == null ? string : dynamic3.get("Name").asString("");
		} else {
			return string;
		}
	}

	public static String a(int integer) {
		if (integer >= 0 && integer < b.length) {
			Dynamic<?> dynamic2 = b[integer];
			return dynamic2 == null ? "minecraft:air" : dynamic2.get("Name").asString("");
		} else {
			return "minecraft:air";
		}
	}

	public static Dynamic<?> b(String string) {
		try {
			return new Dynamic<>(lp.a, lv.a(string.replace('\'', '"')));
		} catch (Exception var2) {
			a.error("Parsing {}", string, var2);
			throw new RuntimeException(var2);
		}
	}

	public static Dynamic<?> b(int integer) {
		Dynamic<?> dynamic2 = null;
		if (integer >= 0 && integer < b.length) {
			dynamic2 = b[integer];
		}

		return dynamic2 == null ? b[0] : dynamic2;
	}

	static {
		d.defaultReturnValue(-1);
		a(0, "{Name:'minecraft:air'}", "{Name:'minecraft:air'}");
		a(16, "{Name:'minecraft:stone'}", "{Name:'minecraft:stone',Properties:{variant:'stone'}}");
		a(17, "{Name:'minecraft:granite'}", "{Name:'minecraft:stone',Properties:{variant:'granite'}}");
		a(18, "{Name:'minecraft:polished_granite'}", "{Name:'minecraft:stone',Properties:{variant:'smooth_granite'}}");
		a(19, "{Name:'minecraft:diorite'}", "{Name:'minecraft:stone',Properties:{variant:'diorite'}}");
		a(20, "{Name:'minecraft:polished_diorite'}", "{Name:'minecraft:stone',Properties:{variant:'smooth_diorite'}}");
		a(21, "{Name:'minecraft:andesite'}", "{Name:'minecraft:stone',Properties:{variant:'andesite'}}");
		a(22, "{Name:'minecraft:polished_andesite'}", "{Name:'minecraft:stone',Properties:{variant:'smooth_andesite'}}");
		a(
			32,
			"{Name:'minecraft:grass_block',Properties:{snowy:'false'}}",
			"{Name:'minecraft:grass',Properties:{snowy:'false'}}",
			"{Name:'minecraft:grass',Properties:{snowy:'true'}}"
		);
		a(
			48,
			"{Name:'minecraft:dirt'}",
			"{Name:'minecraft:dirt',Properties:{snowy:'false',variant:'dirt'}}",
			"{Name:'minecraft:dirt',Properties:{snowy:'true',variant:'dirt'}}"
		);
		a(
			49,
			"{Name:'minecraft:coarse_dirt'}",
			"{Name:'minecraft:dirt',Properties:{snowy:'false',variant:'coarse_dirt'}}",
			"{Name:'minecraft:dirt',Properties:{snowy:'true',variant:'coarse_dirt'}}"
		);
		a(
			50,
			"{Name:'minecraft:podzol',Properties:{snowy:'false'}}",
			"{Name:'minecraft:dirt',Properties:{snowy:'false',variant:'podzol'}}",
			"{Name:'minecraft:dirt',Properties:{snowy:'true',variant:'podzol'}}"
		);
		a(64, "{Name:'minecraft:cobblestone'}", "{Name:'minecraft:cobblestone'}");
		a(80, "{Name:'minecraft:oak_planks'}", "{Name:'minecraft:planks',Properties:{variant:'oak'}}");
		a(81, "{Name:'minecraft:spruce_planks'}", "{Name:'minecraft:planks',Properties:{variant:'spruce'}}");
		a(82, "{Name:'minecraft:birch_planks'}", "{Name:'minecraft:planks',Properties:{variant:'birch'}}");
		a(83, "{Name:'minecraft:jungle_planks'}", "{Name:'minecraft:planks',Properties:{variant:'jungle'}}");
		a(84, "{Name:'minecraft:acacia_planks'}", "{Name:'minecraft:planks',Properties:{variant:'acacia'}}");
		a(85, "{Name:'minecraft:dark_oak_planks'}", "{Name:'minecraft:planks',Properties:{variant:'dark_oak'}}");
		a(96, "{Name:'minecraft:oak_sapling',Properties:{stage:'0'}}", "{Name:'minecraft:sapling',Properties:{stage:'0',type:'oak'}}");
		a(97, "{Name:'minecraft:spruce_sapling',Properties:{stage:'0'}}", "{Name:'minecraft:sapling',Properties:{stage:'0',type:'spruce'}}");
		a(98, "{Name:'minecraft:birch_sapling',Properties:{stage:'0'}}", "{Name:'minecraft:sapling',Properties:{stage:'0',type:'birch'}}");
		a(99, "{Name:'minecraft:jungle_sapling',Properties:{stage:'0'}}", "{Name:'minecraft:sapling',Properties:{stage:'0',type:'jungle'}}");
		a(100, "{Name:'minecraft:acacia_sapling',Properties:{stage:'0'}}", "{Name:'minecraft:sapling',Properties:{stage:'0',type:'acacia'}}");
		a(101, "{Name:'minecraft:dark_oak_sapling',Properties:{stage:'0'}}", "{Name:'minecraft:sapling',Properties:{stage:'0',type:'dark_oak'}}");
		a(104, "{Name:'minecraft:oak_sapling',Properties:{stage:'1'}}", "{Name:'minecraft:sapling',Properties:{stage:'1',type:'oak'}}");
		a(105, "{Name:'minecraft:spruce_sapling',Properties:{stage:'1'}}", "{Name:'minecraft:sapling',Properties:{stage:'1',type:'spruce'}}");
		a(106, "{Name:'minecraft:birch_sapling',Properties:{stage:'1'}}", "{Name:'minecraft:sapling',Properties:{stage:'1',type:'birch'}}");
		a(107, "{Name:'minecraft:jungle_sapling',Properties:{stage:'1'}}", "{Name:'minecraft:sapling',Properties:{stage:'1',type:'jungle'}}");
		a(108, "{Name:'minecraft:acacia_sapling',Properties:{stage:'1'}}", "{Name:'minecraft:sapling',Properties:{stage:'1',type:'acacia'}}");
		a(109, "{Name:'minecraft:dark_oak_sapling',Properties:{stage:'1'}}", "{Name:'minecraft:sapling',Properties:{stage:'1',type:'dark_oak'}}");
		a(112, "{Name:'minecraft:bedrock'}", "{Name:'minecraft:bedrock'}");
		a(128, "{Name:'minecraft:water',Properties:{level:'0'}}", "{Name:'minecraft:flowing_water',Properties:{level:'0'}}");
		a(129, "{Name:'minecraft:water',Properties:{level:'1'}}", "{Name:'minecraft:flowing_water',Properties:{level:'1'}}");
		a(130, "{Name:'minecraft:water',Properties:{level:'2'}}", "{Name:'minecraft:flowing_water',Properties:{level:'2'}}");
		a(131, "{Name:'minecraft:water',Properties:{level:'3'}}", "{Name:'minecraft:flowing_water',Properties:{level:'3'}}");
		a(132, "{Name:'minecraft:water',Properties:{level:'4'}}", "{Name:'minecraft:flowing_water',Properties:{level:'4'}}");
		a(133, "{Name:'minecraft:water',Properties:{level:'5'}}", "{Name:'minecraft:flowing_water',Properties:{level:'5'}}");
		a(134, "{Name:'minecraft:water',Properties:{level:'6'}}", "{Name:'minecraft:flowing_water',Properties:{level:'6'}}");
		a(135, "{Name:'minecraft:water',Properties:{level:'7'}}", "{Name:'minecraft:flowing_water',Properties:{level:'7'}}");
		a(136, "{Name:'minecraft:water',Properties:{level:'8'}}", "{Name:'minecraft:flowing_water',Properties:{level:'8'}}");
		a(137, "{Name:'minecraft:water',Properties:{level:'9'}}", "{Name:'minecraft:flowing_water',Properties:{level:'9'}}");
		a(138, "{Name:'minecraft:water',Properties:{level:'10'}}", "{Name:'minecraft:flowing_water',Properties:{level:'10'}}");
		a(139, "{Name:'minecraft:water',Properties:{level:'11'}}", "{Name:'minecraft:flowing_water',Properties:{level:'11'}}");
		a(140, "{Name:'minecraft:water',Properties:{level:'12'}}", "{Name:'minecraft:flowing_water',Properties:{level:'12'}}");
		a(141, "{Name:'minecraft:water',Properties:{level:'13'}}", "{Name:'minecraft:flowing_water',Properties:{level:'13'}}");
		a(142, "{Name:'minecraft:water',Properties:{level:'14'}}", "{Name:'minecraft:flowing_water',Properties:{level:'14'}}");
		a(143, "{Name:'minecraft:water',Properties:{level:'15'}}", "{Name:'minecraft:flowing_water',Properties:{level:'15'}}");
		a(144, "{Name:'minecraft:water',Properties:{level:'0'}}", "{Name:'minecraft:water',Properties:{level:'0'}}");
		a(145, "{Name:'minecraft:water',Properties:{level:'1'}}", "{Name:'minecraft:water',Properties:{level:'1'}}");
		a(146, "{Name:'minecraft:water',Properties:{level:'2'}}", "{Name:'minecraft:water',Properties:{level:'2'}}");
		a(147, "{Name:'minecraft:water',Properties:{level:'3'}}", "{Name:'minecraft:water',Properties:{level:'3'}}");
		a(148, "{Name:'minecraft:water',Properties:{level:'4'}}", "{Name:'minecraft:water',Properties:{level:'4'}}");
		a(149, "{Name:'minecraft:water',Properties:{level:'5'}}", "{Name:'minecraft:water',Properties:{level:'5'}}");
		a(150, "{Name:'minecraft:water',Properties:{level:'6'}}", "{Name:'minecraft:water',Properties:{level:'6'}}");
		a(151, "{Name:'minecraft:water',Properties:{level:'7'}}", "{Name:'minecraft:water',Properties:{level:'7'}}");
		a(152, "{Name:'minecraft:water',Properties:{level:'8'}}", "{Name:'minecraft:water',Properties:{level:'8'}}");
		a(153, "{Name:'minecraft:water',Properties:{level:'9'}}", "{Name:'minecraft:water',Properties:{level:'9'}}");
		a(154, "{Name:'minecraft:water',Properties:{level:'10'}}", "{Name:'minecraft:water',Properties:{level:'10'}}");
		a(155, "{Name:'minecraft:water',Properties:{level:'11'}}", "{Name:'minecraft:water',Properties:{level:'11'}}");
		a(156, "{Name:'minecraft:water',Properties:{level:'12'}}", "{Name:'minecraft:water',Properties:{level:'12'}}");
		a(157, "{Name:'minecraft:water',Properties:{level:'13'}}", "{Name:'minecraft:water',Properties:{level:'13'}}");
		a(158, "{Name:'minecraft:water',Properties:{level:'14'}}", "{Name:'minecraft:water',Properties:{level:'14'}}");
		a(159, "{Name:'minecraft:water',Properties:{level:'15'}}", "{Name:'minecraft:water',Properties:{level:'15'}}");
		a(160, "{Name:'minecraft:lava',Properties:{level:'0'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'0'}}");
		a(161, "{Name:'minecraft:lava',Properties:{level:'1'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'1'}}");
		a(162, "{Name:'minecraft:lava',Properties:{level:'2'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'2'}}");
		a(163, "{Name:'minecraft:lava',Properties:{level:'3'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'3'}}");
		a(164, "{Name:'minecraft:lava',Properties:{level:'4'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'4'}}");
		a(165, "{Name:'minecraft:lava',Properties:{level:'5'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'5'}}");
		a(166, "{Name:'minecraft:lava',Properties:{level:'6'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'6'}}");
		a(167, "{Name:'minecraft:lava',Properties:{level:'7'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'7'}}");
		a(168, "{Name:'minecraft:lava',Properties:{level:'8'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'8'}}");
		a(169, "{Name:'minecraft:lava',Properties:{level:'9'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'9'}}");
		a(170, "{Name:'minecraft:lava',Properties:{level:'10'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'10'}}");
		a(171, "{Name:'minecraft:lava',Properties:{level:'11'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'11'}}");
		a(172, "{Name:'minecraft:lava',Properties:{level:'12'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'12'}}");
		a(173, "{Name:'minecraft:lava',Properties:{level:'13'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'13'}}");
		a(174, "{Name:'minecraft:lava',Properties:{level:'14'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'14'}}");
		a(175, "{Name:'minecraft:lava',Properties:{level:'15'}}", "{Name:'minecraft:flowing_lava',Properties:{level:'15'}}");
		a(176, "{Name:'minecraft:lava',Properties:{level:'0'}}", "{Name:'minecraft:lava',Properties:{level:'0'}}");
		a(177, "{Name:'minecraft:lava',Properties:{level:'1'}}", "{Name:'minecraft:lava',Properties:{level:'1'}}");
		a(178, "{Name:'minecraft:lava',Properties:{level:'2'}}", "{Name:'minecraft:lava',Properties:{level:'2'}}");
		a(179, "{Name:'minecraft:lava',Properties:{level:'3'}}", "{Name:'minecraft:lava',Properties:{level:'3'}}");
		a(180, "{Name:'minecraft:lava',Properties:{level:'4'}}", "{Name:'minecraft:lava',Properties:{level:'4'}}");
		a(181, "{Name:'minecraft:lava',Properties:{level:'5'}}", "{Name:'minecraft:lava',Properties:{level:'5'}}");
		a(182, "{Name:'minecraft:lava',Properties:{level:'6'}}", "{Name:'minecraft:lava',Properties:{level:'6'}}");
		a(183, "{Name:'minecraft:lava',Properties:{level:'7'}}", "{Name:'minecraft:lava',Properties:{level:'7'}}");
		a(184, "{Name:'minecraft:lava',Properties:{level:'8'}}", "{Name:'minecraft:lava',Properties:{level:'8'}}");
		a(185, "{Name:'minecraft:lava',Properties:{level:'9'}}", "{Name:'minecraft:lava',Properties:{level:'9'}}");
		a(186, "{Name:'minecraft:lava',Properties:{level:'10'}}", "{Name:'minecraft:lava',Properties:{level:'10'}}");
		a(187, "{Name:'minecraft:lava',Properties:{level:'11'}}", "{Name:'minecraft:lava',Properties:{level:'11'}}");
		a(188, "{Name:'minecraft:lava',Properties:{level:'12'}}", "{Name:'minecraft:lava',Properties:{level:'12'}}");
		a(189, "{Name:'minecraft:lava',Properties:{level:'13'}}", "{Name:'minecraft:lava',Properties:{level:'13'}}");
		a(190, "{Name:'minecraft:lava',Properties:{level:'14'}}", "{Name:'minecraft:lava',Properties:{level:'14'}}");
		a(191, "{Name:'minecraft:lava',Properties:{level:'15'}}", "{Name:'minecraft:lava',Properties:{level:'15'}}");
		a(192, "{Name:'minecraft:sand'}", "{Name:'minecraft:sand',Properties:{variant:'sand'}}");
		a(193, "{Name:'minecraft:red_sand'}", "{Name:'minecraft:sand',Properties:{variant:'red_sand'}}");
		a(208, "{Name:'minecraft:gravel'}", "{Name:'minecraft:gravel'}");
		a(224, "{Name:'minecraft:gold_ore'}", "{Name:'minecraft:gold_ore'}");
		a(240, "{Name:'minecraft:iron_ore'}", "{Name:'minecraft:iron_ore'}");
		a(256, "{Name:'minecraft:coal_ore'}", "{Name:'minecraft:coal_ore'}");
		a(272, "{Name:'minecraft:oak_log',Properties:{axis:'y'}}", "{Name:'minecraft:log',Properties:{axis:'y',variant:'oak'}}");
		a(273, "{Name:'minecraft:spruce_log',Properties:{axis:'y'}}", "{Name:'minecraft:log',Properties:{axis:'y',variant:'spruce'}}");
		a(274, "{Name:'minecraft:birch_log',Properties:{axis:'y'}}", "{Name:'minecraft:log',Properties:{axis:'y',variant:'birch'}}");
		a(275, "{Name:'minecraft:jungle_log',Properties:{axis:'y'}}", "{Name:'minecraft:log',Properties:{axis:'y',variant:'jungle'}}");
		a(276, "{Name:'minecraft:oak_log',Properties:{axis:'x'}}", "{Name:'minecraft:log',Properties:{axis:'x',variant:'oak'}}");
		a(277, "{Name:'minecraft:spruce_log',Properties:{axis:'x'}}", "{Name:'minecraft:log',Properties:{axis:'x',variant:'spruce'}}");
		a(278, "{Name:'minecraft:birch_log',Properties:{axis:'x'}}", "{Name:'minecraft:log',Properties:{axis:'x',variant:'birch'}}");
		a(279, "{Name:'minecraft:jungle_log',Properties:{axis:'x'}}", "{Name:'minecraft:log',Properties:{axis:'x',variant:'jungle'}}");
		a(280, "{Name:'minecraft:oak_log',Properties:{axis:'z'}}", "{Name:'minecraft:log',Properties:{axis:'z',variant:'oak'}}");
		a(281, "{Name:'minecraft:spruce_log',Properties:{axis:'z'}}", "{Name:'minecraft:log',Properties:{axis:'z',variant:'spruce'}}");
		a(282, "{Name:'minecraft:birch_log',Properties:{axis:'z'}}", "{Name:'minecraft:log',Properties:{axis:'z',variant:'birch'}}");
		a(283, "{Name:'minecraft:jungle_log',Properties:{axis:'z'}}", "{Name:'minecraft:log',Properties:{axis:'z',variant:'jungle'}}");
		a(284, "{Name:'minecraft:oak_bark'}", "{Name:'minecraft:log',Properties:{axis:'none',variant:'oak'}}");
		a(285, "{Name:'minecraft:spruce_bark'}", "{Name:'minecraft:log',Properties:{axis:'none',variant:'spruce'}}");
		a(286, "{Name:'minecraft:birch_bark'}", "{Name:'minecraft:log',Properties:{axis:'none',variant:'birch'}}");
		a(287, "{Name:'minecraft:jungle_bark'}", "{Name:'minecraft:log',Properties:{axis:'none',variant:'jungle'}}");
		a(
			288,
			"{Name:'minecraft:oak_leaves',Properties:{check_decay:'false',decayable:'true'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'false',decayable:'true',variant:'oak'}}"
		);
		a(
			289,
			"{Name:'minecraft:spruce_leaves',Properties:{check_decay:'false',decayable:'true'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'false',decayable:'true',variant:'spruce'}}"
		);
		a(
			290,
			"{Name:'minecraft:birch_leaves',Properties:{check_decay:'false',decayable:'true'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'false',decayable:'true',variant:'birch'}}"
		);
		a(
			291,
			"{Name:'minecraft:jungle_leaves',Properties:{check_decay:'false',decayable:'true'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'false',decayable:'true',variant:'jungle'}}"
		);
		a(
			292,
			"{Name:'minecraft:oak_leaves',Properties:{check_decay:'false',decayable:'false'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'false',decayable:'false',variant:'oak'}}"
		);
		a(
			293,
			"{Name:'minecraft:spruce_leaves',Properties:{check_decay:'false',decayable:'false'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'false',decayable:'false',variant:'spruce'}}"
		);
		a(
			294,
			"{Name:'minecraft:birch_leaves',Properties:{check_decay:'false',decayable:'false'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'false',decayable:'false',variant:'birch'}}"
		);
		a(
			295,
			"{Name:'minecraft:jungle_leaves',Properties:{check_decay:'false',decayable:'false'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'false',decayable:'false',variant:'jungle'}}"
		);
		a(
			296,
			"{Name:'minecraft:oak_leaves',Properties:{check_decay:'true',decayable:'true'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'true',decayable:'true',variant:'oak'}}"
		);
		a(
			297,
			"{Name:'minecraft:spruce_leaves',Properties:{check_decay:'true',decayable:'true'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'true',decayable:'true',variant:'spruce'}}"
		);
		a(
			298,
			"{Name:'minecraft:birch_leaves',Properties:{check_decay:'true',decayable:'true'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'true',decayable:'true',variant:'birch'}}"
		);
		a(
			299,
			"{Name:'minecraft:jungle_leaves',Properties:{check_decay:'true',decayable:'true'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'true',decayable:'true',variant:'jungle'}}"
		);
		a(
			300,
			"{Name:'minecraft:oak_leaves',Properties:{check_decay:'true',decayable:'false'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'true',decayable:'false',variant:'oak'}}"
		);
		a(
			301,
			"{Name:'minecraft:spruce_leaves',Properties:{check_decay:'true',decayable:'false'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'true',decayable:'false',variant:'spruce'}}"
		);
		a(
			302,
			"{Name:'minecraft:birch_leaves',Properties:{check_decay:'true',decayable:'false'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'true',decayable:'false',variant:'birch'}}"
		);
		a(
			303,
			"{Name:'minecraft:jungle_leaves',Properties:{check_decay:'true',decayable:'false'}}",
			"{Name:'minecraft:leaves',Properties:{check_decay:'true',decayable:'false',variant:'jungle'}}"
		);
		a(304, "{Name:'minecraft:sponge'}", "{Name:'minecraft:sponge',Properties:{wet:'false'}}");
		a(305, "{Name:'minecraft:wet_sponge'}", "{Name:'minecraft:sponge',Properties:{wet:'true'}}");
		a(320, "{Name:'minecraft:glass'}", "{Name:'minecraft:glass'}");
		a(336, "{Name:'minecraft:lapis_ore'}", "{Name:'minecraft:lapis_ore'}");
		a(352, "{Name:'minecraft:lapis_block'}", "{Name:'minecraft:lapis_block'}");
		a(
			368,
			"{Name:'minecraft:dispenser',Properties:{facing:'down',triggered:'false'}}",
			"{Name:'minecraft:dispenser',Properties:{facing:'down',triggered:'false'}}"
		);
		a(369, "{Name:'minecraft:dispenser',Properties:{facing:'up',triggered:'false'}}", "{Name:'minecraft:dispenser',Properties:{facing:'up',triggered:'false'}}");
		a(
			370,
			"{Name:'minecraft:dispenser',Properties:{facing:'north',triggered:'false'}}",
			"{Name:'minecraft:dispenser',Properties:{facing:'north',triggered:'false'}}"
		);
		a(
			371,
			"{Name:'minecraft:dispenser',Properties:{facing:'south',triggered:'false'}}",
			"{Name:'minecraft:dispenser',Properties:{facing:'south',triggered:'false'}}"
		);
		a(
			372,
			"{Name:'minecraft:dispenser',Properties:{facing:'west',triggered:'false'}}",
			"{Name:'minecraft:dispenser',Properties:{facing:'west',triggered:'false'}}"
		);
		a(
			373,
			"{Name:'minecraft:dispenser',Properties:{facing:'east',triggered:'false'}}",
			"{Name:'minecraft:dispenser',Properties:{facing:'east',triggered:'false'}}"
		);
		a(376, "{Name:'minecraft:dispenser',Properties:{facing:'down',triggered:'true'}}", "{Name:'minecraft:dispenser',Properties:{facing:'down',triggered:'true'}}");
		a(377, "{Name:'minecraft:dispenser',Properties:{facing:'up',triggered:'true'}}", "{Name:'minecraft:dispenser',Properties:{facing:'up',triggered:'true'}}");
		a(
			378,
			"{Name:'minecraft:dispenser',Properties:{facing:'north',triggered:'true'}}",
			"{Name:'minecraft:dispenser',Properties:{facing:'north',triggered:'true'}}"
		);
		a(
			379,
			"{Name:'minecraft:dispenser',Properties:{facing:'south',triggered:'true'}}",
			"{Name:'minecraft:dispenser',Properties:{facing:'south',triggered:'true'}}"
		);
		a(380, "{Name:'minecraft:dispenser',Properties:{facing:'west',triggered:'true'}}", "{Name:'minecraft:dispenser',Properties:{facing:'west',triggered:'true'}}");
		a(381, "{Name:'minecraft:dispenser',Properties:{facing:'east',triggered:'true'}}", "{Name:'minecraft:dispenser',Properties:{facing:'east',triggered:'true'}}");
		a(384, "{Name:'minecraft:sandstone'}", "{Name:'minecraft:sandstone',Properties:{type:'sandstone'}}");
		a(385, "{Name:'minecraft:chiseled_sandstone'}", "{Name:'minecraft:sandstone',Properties:{type:'chiseled_sandstone'}}");
		a(386, "{Name:'minecraft:cut_sandstone'}", "{Name:'minecraft:sandstone',Properties:{type:'smooth_sandstone'}}");
		a(400, "{Name:'minecraft:note_block'}", "{Name:'minecraft:noteblock'}");
		a(
			416,
			"{Name:'minecraft:red_bed',Properties:{facing:'south',occupied:'false',part:'foot'}}",
			"{Name:'minecraft:bed',Properties:{facing:'south',occupied:'false',part:'foot'}}",
			"{Name:'minecraft:bed',Properties:{facing:'south',occupied:'true',part:'foot'}}"
		);
		a(
			417,
			"{Name:'minecraft:red_bed',Properties:{facing:'west',occupied:'false',part:'foot'}}",
			"{Name:'minecraft:bed',Properties:{facing:'west',occupied:'false',part:'foot'}}",
			"{Name:'minecraft:bed',Properties:{facing:'west',occupied:'true',part:'foot'}}"
		);
		a(
			418,
			"{Name:'minecraft:red_bed',Properties:{facing:'north',occupied:'false',part:'foot'}}",
			"{Name:'minecraft:bed',Properties:{facing:'north',occupied:'false',part:'foot'}}",
			"{Name:'minecraft:bed',Properties:{facing:'north',occupied:'true',part:'foot'}}"
		);
		a(
			419,
			"{Name:'minecraft:red_bed',Properties:{facing:'east',occupied:'false',part:'foot'}}",
			"{Name:'minecraft:bed',Properties:{facing:'east',occupied:'false',part:'foot'}}",
			"{Name:'minecraft:bed',Properties:{facing:'east',occupied:'true',part:'foot'}}"
		);
		a(
			424,
			"{Name:'minecraft:red_bed',Properties:{facing:'south',occupied:'false',part:'head'}}",
			"{Name:'minecraft:bed',Properties:{facing:'south',occupied:'false',part:'head'}}"
		);
		a(
			425,
			"{Name:'minecraft:red_bed',Properties:{facing:'west',occupied:'false',part:'head'}}",
			"{Name:'minecraft:bed',Properties:{facing:'west',occupied:'false',part:'head'}}"
		);
		a(
			426,
			"{Name:'minecraft:red_bed',Properties:{facing:'north',occupied:'false',part:'head'}}",
			"{Name:'minecraft:bed',Properties:{facing:'north',occupied:'false',part:'head'}}"
		);
		a(
			427,
			"{Name:'minecraft:red_bed',Properties:{facing:'east',occupied:'false',part:'head'}}",
			"{Name:'minecraft:bed',Properties:{facing:'east',occupied:'false',part:'head'}}"
		);
		a(
			428,
			"{Name:'minecraft:red_bed',Properties:{facing:'south',occupied:'true',part:'head'}}",
			"{Name:'minecraft:bed',Properties:{facing:'south',occupied:'true',part:'head'}}"
		);
		a(
			429,
			"{Name:'minecraft:red_bed',Properties:{facing:'west',occupied:'true',part:'head'}}",
			"{Name:'minecraft:bed',Properties:{facing:'west',occupied:'true',part:'head'}}"
		);
		a(
			430,
			"{Name:'minecraft:red_bed',Properties:{facing:'north',occupied:'true',part:'head'}}",
			"{Name:'minecraft:bed',Properties:{facing:'north',occupied:'true',part:'head'}}"
		);
		a(
			431,
			"{Name:'minecraft:red_bed',Properties:{facing:'east',occupied:'true',part:'head'}}",
			"{Name:'minecraft:bed',Properties:{facing:'east',occupied:'true',part:'head'}}"
		);
		a(
			432,
			"{Name:'minecraft:powered_rail',Properties:{powered:'false',shape:'north_south'}}",
			"{Name:'minecraft:golden_rail',Properties:{powered:'false',shape:'north_south'}}"
		);
		a(
			433,
			"{Name:'minecraft:powered_rail',Properties:{powered:'false',shape:'east_west'}}",
			"{Name:'minecraft:golden_rail',Properties:{powered:'false',shape:'east_west'}}"
		);
		a(
			434,
			"{Name:'minecraft:powered_rail',Properties:{powered:'false',shape:'ascending_east'}}",
			"{Name:'minecraft:golden_rail',Properties:{powered:'false',shape:'ascending_east'}}"
		);
		a(
			435,
			"{Name:'minecraft:powered_rail',Properties:{powered:'false',shape:'ascending_west'}}",
			"{Name:'minecraft:golden_rail',Properties:{powered:'false',shape:'ascending_west'}}"
		);
		a(
			436,
			"{Name:'minecraft:powered_rail',Properties:{powered:'false',shape:'ascending_north'}}",
			"{Name:'minecraft:golden_rail',Properties:{powered:'false',shape:'ascending_north'}}"
		);
		a(
			437,
			"{Name:'minecraft:powered_rail',Properties:{powered:'false',shape:'ascending_south'}}",
			"{Name:'minecraft:golden_rail',Properties:{powered:'false',shape:'ascending_south'}}"
		);
		a(
			440,
			"{Name:'minecraft:powered_rail',Properties:{powered:'true',shape:'north_south'}}",
			"{Name:'minecraft:golden_rail',Properties:{powered:'true',shape:'north_south'}}"
		);
		a(
			441,
			"{Name:'minecraft:powered_rail',Properties:{powered:'true',shape:'east_west'}}",
			"{Name:'minecraft:golden_rail',Properties:{powered:'true',shape:'east_west'}}"
		);
		a(
			442,
			"{Name:'minecraft:powered_rail',Properties:{powered:'true',shape:'ascending_east'}}",
			"{Name:'minecraft:golden_rail',Properties:{powered:'true',shape:'ascending_east'}}"
		);
		a(
			443,
			"{Name:'minecraft:powered_rail',Properties:{powered:'true',shape:'ascending_west'}}",
			"{Name:'minecraft:golden_rail',Properties:{powered:'true',shape:'ascending_west'}}"
		);
		a(
			444,
			"{Name:'minecraft:powered_rail',Properties:{powered:'true',shape:'ascending_north'}}",
			"{Name:'minecraft:golden_rail',Properties:{powered:'true',shape:'ascending_north'}}"
		);
		a(
			445,
			"{Name:'minecraft:powered_rail',Properties:{powered:'true',shape:'ascending_south'}}",
			"{Name:'minecraft:golden_rail',Properties:{powered:'true',shape:'ascending_south'}}"
		);
		a(
			448,
			"{Name:'minecraft:detector_rail',Properties:{powered:'false',shape:'north_south'}}",
			"{Name:'minecraft:detector_rail',Properties:{powered:'false',shape:'north_south'}}"
		);
		a(
			449,
			"{Name:'minecraft:detector_rail',Properties:{powered:'false',shape:'east_west'}}",
			"{Name:'minecraft:detector_rail',Properties:{powered:'false',shape:'east_west'}}"
		);
		a(
			450,
			"{Name:'minecraft:detector_rail',Properties:{powered:'false',shape:'ascending_east'}}",
			"{Name:'minecraft:detector_rail',Properties:{powered:'false',shape:'ascending_east'}}"
		);
		a(
			451,
			"{Name:'minecraft:detector_rail',Properties:{powered:'false',shape:'ascending_west'}}",
			"{Name:'minecraft:detector_rail',Properties:{powered:'false',shape:'ascending_west'}}"
		);
		a(
			452,
			"{Name:'minecraft:detector_rail',Properties:{powered:'false',shape:'ascending_north'}}",
			"{Name:'minecraft:detector_rail',Properties:{powered:'false',shape:'ascending_north'}}"
		);
		a(
			453,
			"{Name:'minecraft:detector_rail',Properties:{powered:'false',shape:'ascending_south'}}",
			"{Name:'minecraft:detector_rail',Properties:{powered:'false',shape:'ascending_south'}}"
		);
		a(
			456,
			"{Name:'minecraft:detector_rail',Properties:{powered:'true',shape:'north_south'}}",
			"{Name:'minecraft:detector_rail',Properties:{powered:'true',shape:'north_south'}}"
		);
		a(
			457,
			"{Name:'minecraft:detector_rail',Properties:{powered:'true',shape:'east_west'}}",
			"{Name:'minecraft:detector_rail',Properties:{powered:'true',shape:'east_west'}}"
		);
		a(
			458,
			"{Name:'minecraft:detector_rail',Properties:{powered:'true',shape:'ascending_east'}}",
			"{Name:'minecraft:detector_rail',Properties:{powered:'true',shape:'ascending_east'}}"
		);
		a(
			459,
			"{Name:'minecraft:detector_rail',Properties:{powered:'true',shape:'ascending_west'}}",
			"{Name:'minecraft:detector_rail',Properties:{powered:'true',shape:'ascending_west'}}"
		);
		a(
			460,
			"{Name:'minecraft:detector_rail',Properties:{powered:'true',shape:'ascending_north'}}",
			"{Name:'minecraft:detector_rail',Properties:{powered:'true',shape:'ascending_north'}}"
		);
		a(
			461,
			"{Name:'minecraft:detector_rail',Properties:{powered:'true',shape:'ascending_south'}}",
			"{Name:'minecraft:detector_rail',Properties:{powered:'true',shape:'ascending_south'}}"
		);
		a(
			464,
			"{Name:'minecraft:sticky_piston',Properties:{extended:'false',facing:'down'}}",
			"{Name:'minecraft:sticky_piston',Properties:{extended:'false',facing:'down'}}"
		);
		a(
			465,
			"{Name:'minecraft:sticky_piston',Properties:{extended:'false',facing:'up'}}",
			"{Name:'minecraft:sticky_piston',Properties:{extended:'false',facing:'up'}}"
		);
		a(
			466,
			"{Name:'minecraft:sticky_piston',Properties:{extended:'false',facing:'north'}}",
			"{Name:'minecraft:sticky_piston',Properties:{extended:'false',facing:'north'}}"
		);
		a(
			467,
			"{Name:'minecraft:sticky_piston',Properties:{extended:'false',facing:'south'}}",
			"{Name:'minecraft:sticky_piston',Properties:{extended:'false',facing:'south'}}"
		);
		a(
			468,
			"{Name:'minecraft:sticky_piston',Properties:{extended:'false',facing:'west'}}",
			"{Name:'minecraft:sticky_piston',Properties:{extended:'false',facing:'west'}}"
		);
		a(
			469,
			"{Name:'minecraft:sticky_piston',Properties:{extended:'false',facing:'east'}}",
			"{Name:'minecraft:sticky_piston',Properties:{extended:'false',facing:'east'}}"
		);
		a(
			472,
			"{Name:'minecraft:sticky_piston',Properties:{extended:'true',facing:'down'}}",
			"{Name:'minecraft:sticky_piston',Properties:{extended:'true',facing:'down'}}"
		);
		a(
			473,
			"{Name:'minecraft:sticky_piston',Properties:{extended:'true',facing:'up'}}",
			"{Name:'minecraft:sticky_piston',Properties:{extended:'true',facing:'up'}}"
		);
		a(
			474,
			"{Name:'minecraft:sticky_piston',Properties:{extended:'true',facing:'north'}}",
			"{Name:'minecraft:sticky_piston',Properties:{extended:'true',facing:'north'}}"
		);
		a(
			475,
			"{Name:'minecraft:sticky_piston',Properties:{extended:'true',facing:'south'}}",
			"{Name:'minecraft:sticky_piston',Properties:{extended:'true',facing:'south'}}"
		);
		a(
			476,
			"{Name:'minecraft:sticky_piston',Properties:{extended:'true',facing:'west'}}",
			"{Name:'minecraft:sticky_piston',Properties:{extended:'true',facing:'west'}}"
		);
		a(
			477,
			"{Name:'minecraft:sticky_piston',Properties:{extended:'true',facing:'east'}}",
			"{Name:'minecraft:sticky_piston',Properties:{extended:'true',facing:'east'}}"
		);
		a(480, "{Name:'minecraft:cobweb'}", "{Name:'minecraft:web'}");
		a(496, "{Name:'minecraft:dead_bush'}", "{Name:'minecraft:tallgrass',Properties:{type:'dead_bush'}}");
		a(497, "{Name:'minecraft:grass'}", "{Name:'minecraft:tallgrass',Properties:{type:'tall_grass'}}");
		a(498, "{Name:'minecraft:fern'}", "{Name:'minecraft:tallgrass',Properties:{type:'fern'}}");
		a(512, "{Name:'minecraft:dead_bush'}", "{Name:'minecraft:deadbush'}");
		a(528, "{Name:'minecraft:piston',Properties:{extended:'false',facing:'down'}}", "{Name:'minecraft:piston',Properties:{extended:'false',facing:'down'}}");
		a(529, "{Name:'minecraft:piston',Properties:{extended:'false',facing:'up'}}", "{Name:'minecraft:piston',Properties:{extended:'false',facing:'up'}}");
		a(530, "{Name:'minecraft:piston',Properties:{extended:'false',facing:'north'}}", "{Name:'minecraft:piston',Properties:{extended:'false',facing:'north'}}");
		a(531, "{Name:'minecraft:piston',Properties:{extended:'false',facing:'south'}}", "{Name:'minecraft:piston',Properties:{extended:'false',facing:'south'}}");
		a(532, "{Name:'minecraft:piston',Properties:{extended:'false',facing:'west'}}", "{Name:'minecraft:piston',Properties:{extended:'false',facing:'west'}}");
		a(533, "{Name:'minecraft:piston',Properties:{extended:'false',facing:'east'}}", "{Name:'minecraft:piston',Properties:{extended:'false',facing:'east'}}");
		a(536, "{Name:'minecraft:piston',Properties:{extended:'true',facing:'down'}}", "{Name:'minecraft:piston',Properties:{extended:'true',facing:'down'}}");
		a(537, "{Name:'minecraft:piston',Properties:{extended:'true',facing:'up'}}", "{Name:'minecraft:piston',Properties:{extended:'true',facing:'up'}}");
		a(538, "{Name:'minecraft:piston',Properties:{extended:'true',facing:'north'}}", "{Name:'minecraft:piston',Properties:{extended:'true',facing:'north'}}");
		a(539, "{Name:'minecraft:piston',Properties:{extended:'true',facing:'south'}}", "{Name:'minecraft:piston',Properties:{extended:'true',facing:'south'}}");
		a(540, "{Name:'minecraft:piston',Properties:{extended:'true',facing:'west'}}", "{Name:'minecraft:piston',Properties:{extended:'true',facing:'west'}}");
		a(541, "{Name:'minecraft:piston',Properties:{extended:'true',facing:'east'}}", "{Name:'minecraft:piston',Properties:{extended:'true',facing:'east'}}");
		a(
			544,
			"{Name:'minecraft:piston_head',Properties:{facing:'down',short:'false',type:'normal'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'down',short:'false',type:'normal'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'down',short:'true',type:'normal'}}"
		);
		a(
			545,
			"{Name:'minecraft:piston_head',Properties:{facing:'up',short:'false',type:'normal'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'up',short:'false',type:'normal'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'up',short:'true',type:'normal'}}"
		);
		a(
			546,
			"{Name:'minecraft:piston_head',Properties:{facing:'north',short:'false',type:'normal'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'north',short:'false',type:'normal'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'north',short:'true',type:'normal'}}"
		);
		a(
			547,
			"{Name:'minecraft:piston_head',Properties:{facing:'south',short:'false',type:'normal'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'south',short:'false',type:'normal'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'south',short:'true',type:'normal'}}"
		);
		a(
			548,
			"{Name:'minecraft:piston_head',Properties:{facing:'west',short:'false',type:'normal'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'west',short:'false',type:'normal'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'west',short:'true',type:'normal'}}"
		);
		a(
			549,
			"{Name:'minecraft:piston_head',Properties:{facing:'east',short:'false',type:'normal'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'east',short:'false',type:'normal'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'east',short:'true',type:'normal'}}"
		);
		a(
			552,
			"{Name:'minecraft:piston_head',Properties:{facing:'down',short:'false',type:'sticky'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'down',short:'false',type:'sticky'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'down',short:'true',type:'sticky'}}"
		);
		a(
			553,
			"{Name:'minecraft:piston_head',Properties:{facing:'up',short:'false',type:'sticky'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'up',short:'false',type:'sticky'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'up',short:'true',type:'sticky'}}"
		);
		a(
			554,
			"{Name:'minecraft:piston_head',Properties:{facing:'north',short:'false',type:'sticky'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'north',short:'false',type:'sticky'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'north',short:'true',type:'sticky'}}"
		);
		a(
			555,
			"{Name:'minecraft:piston_head',Properties:{facing:'south',short:'false',type:'sticky'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'south',short:'false',type:'sticky'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'south',short:'true',type:'sticky'}}"
		);
		a(
			556,
			"{Name:'minecraft:piston_head',Properties:{facing:'west',short:'false',type:'sticky'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'west',short:'false',type:'sticky'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'west',short:'true',type:'sticky'}}"
		);
		a(
			557,
			"{Name:'minecraft:piston_head',Properties:{facing:'east',short:'false',type:'sticky'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'east',short:'false',type:'sticky'}}",
			"{Name:'minecraft:piston_head',Properties:{facing:'east',short:'true',type:'sticky'}}"
		);
		a(560, "{Name:'minecraft:white_wool'}", "{Name:'minecraft:wool',Properties:{color:'white'}}");
		a(561, "{Name:'minecraft:orange_wool'}", "{Name:'minecraft:wool',Properties:{color:'orange'}}");
		a(562, "{Name:'minecraft:magenta_wool'}", "{Name:'minecraft:wool',Properties:{color:'magenta'}}");
		a(563, "{Name:'minecraft:light_blue_wool'}", "{Name:'minecraft:wool',Properties:{color:'light_blue'}}");
		a(564, "{Name:'minecraft:yellow_wool'}", "{Name:'minecraft:wool',Properties:{color:'yellow'}}");
		a(565, "{Name:'minecraft:lime_wool'}", "{Name:'minecraft:wool',Properties:{color:'lime'}}");
		a(566, "{Name:'minecraft:pink_wool'}", "{Name:'minecraft:wool',Properties:{color:'pink'}}");
		a(567, "{Name:'minecraft:gray_wool'}", "{Name:'minecraft:wool',Properties:{color:'gray'}}");
		a(568, "{Name:'minecraft:light_gray_wool'}", "{Name:'minecraft:wool',Properties:{color:'silver'}}");
		a(569, "{Name:'minecraft:cyan_wool'}", "{Name:'minecraft:wool',Properties:{color:'cyan'}}");
		a(570, "{Name:'minecraft:purple_wool'}", "{Name:'minecraft:wool',Properties:{color:'purple'}}");
		a(571, "{Name:'minecraft:blue_wool'}", "{Name:'minecraft:wool',Properties:{color:'blue'}}");
		a(572, "{Name:'minecraft:brown_wool'}", "{Name:'minecraft:wool',Properties:{color:'brown'}}");
		a(573, "{Name:'minecraft:green_wool'}", "{Name:'minecraft:wool',Properties:{color:'green'}}");
		a(574, "{Name:'minecraft:red_wool'}", "{Name:'minecraft:wool',Properties:{color:'red'}}");
		a(575, "{Name:'minecraft:black_wool'}", "{Name:'minecraft:wool',Properties:{color:'black'}}");
		a(
			576,
			"{Name:'minecraft:moving_piston',Properties:{facing:'down',type:'normal'}}",
			"{Name:'minecraft:piston_extension',Properties:{facing:'down',type:'normal'}}"
		);
		a(
			577, "{Name:'minecraft:moving_piston',Properties:{facing:'up',type:'normal'}}", "{Name:'minecraft:piston_extension',Properties:{facing:'up',type:'normal'}}"
		);
		a(
			578,
			"{Name:'minecraft:moving_piston',Properties:{facing:'north',type:'normal'}}",
			"{Name:'minecraft:piston_extension',Properties:{facing:'north',type:'normal'}}"
		);
		a(
			579,
			"{Name:'minecraft:moving_piston',Properties:{facing:'south',type:'normal'}}",
			"{Name:'minecraft:piston_extension',Properties:{facing:'south',type:'normal'}}"
		);
		a(
			580,
			"{Name:'minecraft:moving_piston',Properties:{facing:'west',type:'normal'}}",
			"{Name:'minecraft:piston_extension',Properties:{facing:'west',type:'normal'}}"
		);
		a(
			581,
			"{Name:'minecraft:moving_piston',Properties:{facing:'east',type:'normal'}}",
			"{Name:'minecraft:piston_extension',Properties:{facing:'east',type:'normal'}}"
		);
		a(
			584,
			"{Name:'minecraft:moving_piston',Properties:{facing:'down',type:'sticky'}}",
			"{Name:'minecraft:piston_extension',Properties:{facing:'down',type:'sticky'}}"
		);
		a(
			585, "{Name:'minecraft:moving_piston',Properties:{facing:'up',type:'sticky'}}", "{Name:'minecraft:piston_extension',Properties:{facing:'up',type:'sticky'}}"
		);
		a(
			586,
			"{Name:'minecraft:moving_piston',Properties:{facing:'north',type:'sticky'}}",
			"{Name:'minecraft:piston_extension',Properties:{facing:'north',type:'sticky'}}"
		);
		a(
			587,
			"{Name:'minecraft:moving_piston',Properties:{facing:'south',type:'sticky'}}",
			"{Name:'minecraft:piston_extension',Properties:{facing:'south',type:'sticky'}}"
		);
		a(
			588,
			"{Name:'minecraft:moving_piston',Properties:{facing:'west',type:'sticky'}}",
			"{Name:'minecraft:piston_extension',Properties:{facing:'west',type:'sticky'}}"
		);
		a(
			589,
			"{Name:'minecraft:moving_piston',Properties:{facing:'east',type:'sticky'}}",
			"{Name:'minecraft:piston_extension',Properties:{facing:'east',type:'sticky'}}"
		);
		a(592, "{Name:'minecraft:dandelion'}", "{Name:'minecraft:yellow_flower',Properties:{type:'dandelion'}}");
		a(608, "{Name:'minecraft:poppy'}", "{Name:'minecraft:red_flower',Properties:{type:'poppy'}}");
		a(609, "{Name:'minecraft:blue_orchid'}", "{Name:'minecraft:red_flower',Properties:{type:'blue_orchid'}}");
		a(610, "{Name:'minecraft:allium'}", "{Name:'minecraft:red_flower',Properties:{type:'allium'}}");
		a(611, "{Name:'minecraft:azure_bluet'}", "{Name:'minecraft:red_flower',Properties:{type:'houstonia'}}");
		a(612, "{Name:'minecraft:red_tulip'}", "{Name:'minecraft:red_flower',Properties:{type:'red_tulip'}}");
		a(613, "{Name:'minecraft:orange_tulip'}", "{Name:'minecraft:red_flower',Properties:{type:'orange_tulip'}}");
		a(614, "{Name:'minecraft:white_tulip'}", "{Name:'minecraft:red_flower',Properties:{type:'white_tulip'}}");
		a(615, "{Name:'minecraft:pink_tulip'}", "{Name:'minecraft:red_flower',Properties:{type:'pink_tulip'}}");
		a(616, "{Name:'minecraft:oxeye_daisy'}", "{Name:'minecraft:red_flower',Properties:{type:'oxeye_daisy'}}");
		a(624, "{Name:'minecraft:brown_mushroom'}", "{Name:'minecraft:brown_mushroom'}");
		a(640, "{Name:'minecraft:red_mushroom'}", "{Name:'minecraft:red_mushroom'}");
		a(656, "{Name:'minecraft:gold_block'}", "{Name:'minecraft:gold_block'}");
		a(672, "{Name:'minecraft:iron_block'}", "{Name:'minecraft:iron_block'}");
		a(688, "{Name:'minecraft:stone_slab',Properties:{type:'double'}}", "{Name:'minecraft:double_stone_slab',Properties:{seamless:'false',variant:'stone'}}");
		a(
			689,
			"{Name:'minecraft:sandstone_slab',Properties:{type:'double'}}",
			"{Name:'minecraft:double_stone_slab',Properties:{seamless:'false',variant:'sandstone'}}"
		);
		a(
			690,
			"{Name:'minecraft:petrified_oak_slab',Properties:{type:'double'}}",
			"{Name:'minecraft:double_stone_slab',Properties:{seamless:'false',variant:'wood_old'}}"
		);
		a(
			691,
			"{Name:'minecraft:cobblestone_slab',Properties:{type:'double'}}",
			"{Name:'minecraft:double_stone_slab',Properties:{seamless:'false',variant:'cobblestone'}}"
		);
		a(692, "{Name:'minecraft:brick_slab',Properties:{type:'double'}}", "{Name:'minecraft:double_stone_slab',Properties:{seamless:'false',variant:'brick'}}");
		a(
			693,
			"{Name:'minecraft:stone_brick_slab',Properties:{type:'double'}}",
			"{Name:'minecraft:double_stone_slab',Properties:{seamless:'false',variant:'stone_brick'}}"
		);
		a(
			694,
			"{Name:'minecraft:nether_brick_slab',Properties:{type:'double'}}",
			"{Name:'minecraft:double_stone_slab',Properties:{seamless:'false',variant:'nether_brick'}}"
		);
		a(695, "{Name:'minecraft:quartz_slab',Properties:{type:'double'}}", "{Name:'minecraft:double_stone_slab',Properties:{seamless:'false',variant:'quartz'}}");
		a(696, "{Name:'minecraft:smooth_stone'}", "{Name:'minecraft:double_stone_slab',Properties:{seamless:'true',variant:'stone'}}");
		a(697, "{Name:'minecraft:smooth_sandstone'}", "{Name:'minecraft:double_stone_slab',Properties:{seamless:'true',variant:'sandstone'}}");
		a(
			698,
			"{Name:'minecraft:petrified_oak_slab',Properties:{type:'double'}}",
			"{Name:'minecraft:double_stone_slab',Properties:{seamless:'true',variant:'wood_old'}}"
		);
		a(
			699,
			"{Name:'minecraft:cobblestone_slab',Properties:{type:'double'}}",
			"{Name:'minecraft:double_stone_slab',Properties:{seamless:'true',variant:'cobblestone'}}"
		);
		a(700, "{Name:'minecraft:brick_slab',Properties:{type:'double'}}", "{Name:'minecraft:double_stone_slab',Properties:{seamless:'true',variant:'brick'}}");
		a(
			701,
			"{Name:'minecraft:stone_brick_slab',Properties:{type:'double'}}",
			"{Name:'minecraft:double_stone_slab',Properties:{seamless:'true',variant:'stone_brick'}}"
		);
		a(
			702,
			"{Name:'minecraft:nether_brick_slab',Properties:{type:'double'}}",
			"{Name:'minecraft:double_stone_slab',Properties:{seamless:'true',variant:'nether_brick'}}"
		);
		a(703, "{Name:'minecraft:smooth_quartz'}", "{Name:'minecraft:double_stone_slab',Properties:{seamless:'true',variant:'quartz'}}");
		a(704, "{Name:'minecraft:stone_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:stone_slab',Properties:{half:'bottom',variant:'stone'}}");
		a(705, "{Name:'minecraft:sandstone_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:stone_slab',Properties:{half:'bottom',variant:'sandstone'}}");
		a(706, "{Name:'minecraft:petrified_oak_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:stone_slab',Properties:{half:'bottom',variant:'wood_old'}}");
		a(707, "{Name:'minecraft:cobblestone_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:stone_slab',Properties:{half:'bottom',variant:'cobblestone'}}");
		a(708, "{Name:'minecraft:brick_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:stone_slab',Properties:{half:'bottom',variant:'brick'}}");
		a(709, "{Name:'minecraft:stone_brick_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:stone_slab',Properties:{half:'bottom',variant:'stone_brick'}}");
		a(710, "{Name:'minecraft:nether_brick_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:stone_slab',Properties:{half:'bottom',variant:'nether_brick'}}");
		a(711, "{Name:'minecraft:quartz_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:stone_slab',Properties:{half:'bottom',variant:'quartz'}}");
		a(712, "{Name:'minecraft:stone_slab',Properties:{type:'top'}}", "{Name:'minecraft:stone_slab',Properties:{half:'top',variant:'stone'}}");
		a(713, "{Name:'minecraft:sandstone_slab',Properties:{type:'top'}}", "{Name:'minecraft:stone_slab',Properties:{half:'top',variant:'sandstone'}}");
		a(714, "{Name:'minecraft:petrified_oak_slab',Properties:{type:'top'}}", "{Name:'minecraft:stone_slab',Properties:{half:'top',variant:'wood_old'}}");
		a(715, "{Name:'minecraft:cobblestone_slab',Properties:{type:'top'}}", "{Name:'minecraft:stone_slab',Properties:{half:'top',variant:'cobblestone'}}");
		a(716, "{Name:'minecraft:brick_slab',Properties:{type:'top'}}", "{Name:'minecraft:stone_slab',Properties:{half:'top',variant:'brick'}}");
		a(717, "{Name:'minecraft:stone_brick_slab',Properties:{type:'top'}}", "{Name:'minecraft:stone_slab',Properties:{half:'top',variant:'stone_brick'}}");
		a(718, "{Name:'minecraft:nether_brick_slab',Properties:{type:'top'}}", "{Name:'minecraft:stone_slab',Properties:{half:'top',variant:'nether_brick'}}");
		a(719, "{Name:'minecraft:quartz_slab',Properties:{type:'top'}}", "{Name:'minecraft:stone_slab',Properties:{half:'top',variant:'quartz'}}");
		a(720, "{Name:'minecraft:bricks'}", "{Name:'minecraft:brick_block'}");
		a(736, "{Name:'minecraft:tnt',Properties:{unstable:'false'}}", "{Name:'minecraft:tnt',Properties:{explode:'false'}}");
		a(737, "{Name:'minecraft:tnt',Properties:{unstable:'true'}}", "{Name:'minecraft:tnt',Properties:{explode:'true'}}");
		a(752, "{Name:'minecraft:bookshelf'}", "{Name:'minecraft:bookshelf'}");
		a(768, "{Name:'minecraft:mossy_cobblestone'}", "{Name:'minecraft:mossy_cobblestone'}");
		a(784, "{Name:'minecraft:obsidian'}", "{Name:'minecraft:obsidian'}");
		a(801, "{Name:'minecraft:wall_torch',Properties:{facing:'east'}}", "{Name:'minecraft:torch',Properties:{facing:'east'}}");
		a(802, "{Name:'minecraft:wall_torch',Properties:{facing:'west'}}", "{Name:'minecraft:torch',Properties:{facing:'west'}}");
		a(803, "{Name:'minecraft:wall_torch',Properties:{facing:'south'}}", "{Name:'minecraft:torch',Properties:{facing:'south'}}");
		a(804, "{Name:'minecraft:wall_torch',Properties:{facing:'north'}}", "{Name:'minecraft:torch',Properties:{facing:'north'}}");
		a(805, "{Name:'minecraft:torch'}", "{Name:'minecraft:torch',Properties:{facing:'up'}}");
		a(
			816,
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'0',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			817,
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'1',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			818,
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'2',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			819,
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'3',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			820,
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'4',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			821,
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'5',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			822,
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'6',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			823,
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'7',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			824,
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'8',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			825,
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'9',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			826,
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'10',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			827,
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'11',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			828,
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'12',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			829,
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'13',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			830,
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'14',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			831,
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:fire',Properties:{age:'15',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(832, "{Name:'minecraft:mob_spawner'}", "{Name:'minecraft:mob_spawner'}");
		a(
			848,
			"{Name:'minecraft:oak_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}"
		);
		a(
			849,
			"{Name:'minecraft:oak_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}"
		);
		a(
			850,
			"{Name:'minecraft:oak_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}"
		);
		a(
			851,
			"{Name:'minecraft:oak_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}"
		);
		a(
			852,
			"{Name:'minecraft:oak_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'east',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'east',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'east',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'east',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}"
		);
		a(
			853,
			"{Name:'minecraft:oak_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'west',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'west',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'west',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'west',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}"
		);
		a(
			854,
			"{Name:'minecraft:oak_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'south',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'south',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'south',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'south',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}"
		);
		a(
			855,
			"{Name:'minecraft:oak_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'north',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'north',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'north',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'north',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:oak_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}"
		);
		a(866, "{Name:'minecraft:chest',Properties:{facing:'north',type:'single'}}", "{Name:'minecraft:chest',Properties:{facing:'north'}}");
		a(867, "{Name:'minecraft:chest',Properties:{facing:'south',type:'single'}}", "{Name:'minecraft:chest',Properties:{facing:'south'}}");
		a(868, "{Name:'minecraft:chest',Properties:{facing:'west',type:'single'}}", "{Name:'minecraft:chest',Properties:{facing:'west'}}");
		a(869, "{Name:'minecraft:chest',Properties:{facing:'east',type:'single'}}", "{Name:'minecraft:chest',Properties:{facing:'east'}}");
		a(
			880,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'0',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'0',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'0',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'0',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'0',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'0',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'0',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'0',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'0',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'0',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'0',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'0',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'0',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'0',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'0',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'0',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'0',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'0',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'0',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'0',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'0',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'0',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'0',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'0',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'0',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'0',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'0',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'0',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'0',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'0',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'0',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'0',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'0',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'0',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'0',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'0',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'0',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'0',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'0',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'0',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'0',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'0',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'0',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'0',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'0',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'0',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'0',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'0',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'0',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'0',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'0',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'0',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'0',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'0',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'0',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'0',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'0',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'0',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'0',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'0',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'0',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'0',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'0',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'0',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'0',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'0',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'0',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'0',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'0',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'0',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'0',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'0',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'0',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'0',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'0',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'0',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'0',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'0',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'0',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'0',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'0',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'0',south:'up',west:'up'}}"
		);
		a(
			881,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'1',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'1',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'1',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'1',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'1',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'1',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'1',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'1',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'1',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'1',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'1',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'1',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'1',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'1',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'1',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'1',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'1',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'1',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'1',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'1',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'1',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'1',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'1',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'1',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'1',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'1',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'1',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'1',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'1',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'1',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'1',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'1',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'1',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'1',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'1',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'1',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'1',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'1',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'1',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'1',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'1',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'1',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'1',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'1',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'1',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'1',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'1',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'1',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'1',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'1',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'1',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'1',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'1',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'1',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'1',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'1',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'1',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'1',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'1',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'1',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'1',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'1',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'1',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'1',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'1',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'1',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'1',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'1',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'1',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'1',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'1',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'1',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'1',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'1',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'1',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'1',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'1',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'1',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'1',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'1',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'1',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'1',south:'up',west:'up'}}"
		);
		a(
			882,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'2',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'2',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'2',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'2',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'2',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'2',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'2',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'2',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'2',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'2',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'2',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'2',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'2',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'2',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'2',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'2',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'2',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'2',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'2',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'2',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'2',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'2',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'2',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'2',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'2',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'2',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'2',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'2',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'2',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'2',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'2',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'2',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'2',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'2',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'2',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'2',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'2',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'2',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'2',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'2',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'2',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'2',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'2',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'2',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'2',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'2',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'2',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'2',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'2',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'2',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'2',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'2',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'2',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'2',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'2',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'2',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'2',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'2',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'2',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'2',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'2',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'2',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'2',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'2',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'2',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'2',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'2',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'2',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'2',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'2',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'2',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'2',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'2',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'2',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'2',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'2',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'2',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'2',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'2',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'2',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'2',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'2',south:'up',west:'up'}}"
		);
		a(
			883,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'3',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'3',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'3',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'3',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'3',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'3',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'3',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'3',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'3',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'3',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'3',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'3',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'3',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'3',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'3',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'3',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'3',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'3',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'3',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'3',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'3',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'3',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'3',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'3',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'3',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'3',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'3',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'3',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'3',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'3',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'3',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'3',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'3',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'3',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'3',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'3',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'3',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'3',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'3',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'3',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'3',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'3',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'3',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'3',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'3',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'3',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'3',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'3',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'3',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'3',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'3',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'3',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'3',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'3',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'3',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'3',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'3',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'3',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'3',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'3',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'3',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'3',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'3',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'3',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'3',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'3',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'3',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'3',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'3',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'3',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'3',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'3',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'3',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'3',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'3',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'3',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'3',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'3',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'3',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'3',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'3',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'3',south:'up',west:'up'}}"
		);
		a(
			884,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'4',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'4',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'4',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'4',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'4',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'4',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'4',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'4',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'4',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'4',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'4',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'4',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'4',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'4',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'4',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'4',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'4',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'4',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'4',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'4',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'4',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'4',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'4',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'4',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'4',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'4',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'4',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'4',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'4',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'4',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'4',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'4',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'4',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'4',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'4',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'4',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'4',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'4',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'4',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'4',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'4',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'4',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'4',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'4',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'4',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'4',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'4',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'4',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'4',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'4',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'4',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'4',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'4',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'4',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'4',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'4',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'4',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'4',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'4',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'4',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'4',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'4',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'4',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'4',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'4',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'4',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'4',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'4',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'4',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'4',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'4',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'4',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'4',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'4',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'4',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'4',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'4',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'4',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'4',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'4',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'4',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'4',south:'up',west:'up'}}"
		);
		a(
			885,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'5',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'5',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'5',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'5',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'5',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'5',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'5',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'5',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'5',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'5',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'5',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'5',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'5',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'5',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'5',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'5',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'5',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'5',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'5',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'5',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'5',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'5',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'5',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'5',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'5',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'5',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'5',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'5',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'5',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'5',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'5',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'5',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'5',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'5',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'5',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'5',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'5',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'5',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'5',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'5',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'5',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'5',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'5',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'5',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'5',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'5',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'5',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'5',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'5',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'5',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'5',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'5',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'5',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'5',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'5',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'5',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'5',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'5',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'5',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'5',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'5',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'5',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'5',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'5',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'5',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'5',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'5',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'5',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'5',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'5',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'5',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'5',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'5',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'5',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'5',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'5',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'5',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'5',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'5',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'5',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'5',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'5',south:'up',west:'up'}}"
		);
		a(
			886,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'6',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'6',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'6',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'6',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'6',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'6',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'6',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'6',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'6',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'6',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'6',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'6',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'6',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'6',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'6',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'6',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'6',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'6',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'6',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'6',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'6',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'6',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'6',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'6',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'6',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'6',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'6',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'6',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'6',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'6',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'6',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'6',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'6',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'6',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'6',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'6',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'6',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'6',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'6',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'6',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'6',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'6',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'6',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'6',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'6',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'6',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'6',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'6',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'6',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'6',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'6',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'6',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'6',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'6',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'6',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'6',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'6',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'6',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'6',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'6',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'6',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'6',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'6',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'6',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'6',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'6',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'6',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'6',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'6',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'6',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'6',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'6',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'6',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'6',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'6',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'6',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'6',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'6',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'6',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'6',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'6',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'6',south:'up',west:'up'}}"
		);
		a(
			887,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'7',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'7',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'7',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'7',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'7',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'7',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'7',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'7',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'7',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'7',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'7',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'7',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'7',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'7',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'7',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'7',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'7',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'7',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'7',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'7',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'7',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'7',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'7',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'7',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'7',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'7',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'7',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'7',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'7',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'7',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'7',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'7',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'7',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'7',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'7',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'7',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'7',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'7',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'7',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'7',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'7',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'7',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'7',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'7',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'7',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'7',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'7',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'7',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'7',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'7',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'7',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'7',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'7',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'7',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'7',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'7',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'7',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'7',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'7',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'7',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'7',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'7',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'7',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'7',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'7',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'7',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'7',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'7',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'7',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'7',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'7',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'7',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'7',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'7',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'7',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'7',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'7',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'7',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'7',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'7',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'7',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'7',south:'up',west:'up'}}"
		);
		a(
			888,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'8',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'8',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'8',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'8',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'8',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'8',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'8',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'8',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'8',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'8',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'8',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'8',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'8',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'8',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'8',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'8',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'8',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'8',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'8',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'8',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'8',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'8',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'8',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'8',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'8',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'8',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'8',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'8',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'8',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'8',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'8',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'8',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'8',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'8',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'8',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'8',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'8',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'8',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'8',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'8',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'8',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'8',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'8',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'8',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'8',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'8',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'8',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'8',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'8',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'8',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'8',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'8',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'8',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'8',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'8',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'8',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'8',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'8',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'8',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'8',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'8',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'8',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'8',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'8',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'8',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'8',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'8',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'8',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'8',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'8',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'8',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'8',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'8',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'8',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'8',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'8',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'8',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'8',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'8',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'8',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'8',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'8',south:'up',west:'up'}}"
		);
		a(
			889,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'9',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'9',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'9',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'9',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'9',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'9',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'9',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'9',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'9',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'9',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'9',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'9',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'9',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'9',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'9',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'9',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'9',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'9',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'9',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'9',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'9',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'9',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'9',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'9',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'9',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'9',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'9',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'9',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'9',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'9',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'9',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'9',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'9',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'9',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'9',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'9',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'9',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'9',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'9',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'9',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'9',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'9',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'9',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'9',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'9',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'9',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'9',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'9',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'9',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'9',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'9',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'9',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'9',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'9',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'9',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'9',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'9',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'9',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'9',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'9',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'9',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'9',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'9',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'9',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'9',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'9',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'9',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'9',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'9',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'9',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'9',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'9',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'9',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'9',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'9',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'9',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'9',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'9',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'9',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'9',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'9',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'9',south:'up',west:'up'}}"
		);
		a(
			890,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'10',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'10',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'10',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'10',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'10',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'10',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'10',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'10',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'10',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'10',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'10',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'10',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'10',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'10',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'10',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'10',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'10',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'10',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'10',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'10',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'10',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'10',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'10',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'10',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'10',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'10',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'10',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'10',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'10',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'10',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'10',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'10',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'10',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'10',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'10',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'10',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'10',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'10',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'10',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'10',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'10',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'10',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'10',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'10',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'10',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'10',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'10',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'10',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'10',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'10',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'10',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'10',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'10',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'10',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'10',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'10',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'10',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'10',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'10',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'10',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'10',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'10',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'10',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'10',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'10',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'10',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'10',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'10',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'10',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'10',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'10',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'10',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'10',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'10',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'10',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'10',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'10',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'10',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'10',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'10',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'10',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'10',south:'up',west:'up'}}"
		);
		a(
			891,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'11',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'11',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'11',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'11',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'11',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'11',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'11',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'11',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'11',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'11',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'11',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'11',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'11',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'11',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'11',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'11',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'11',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'11',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'11',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'11',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'11',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'11',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'11',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'11',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'11',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'11',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'11',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'11',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'11',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'11',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'11',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'11',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'11',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'11',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'11',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'11',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'11',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'11',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'11',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'11',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'11',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'11',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'11',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'11',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'11',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'11',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'11',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'11',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'11',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'11',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'11',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'11',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'11',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'11',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'11',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'11',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'11',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'11',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'11',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'11',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'11',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'11',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'11',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'11',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'11',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'11',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'11',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'11',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'11',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'11',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'11',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'11',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'11',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'11',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'11',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'11',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'11',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'11',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'11',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'11',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'11',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'11',south:'up',west:'up'}}"
		);
		a(
			892,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'12',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'12',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'12',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'12',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'12',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'12',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'12',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'12',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'12',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'12',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'12',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'12',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'12',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'12',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'12',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'12',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'12',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'12',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'12',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'12',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'12',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'12',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'12',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'12',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'12',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'12',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'12',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'12',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'12',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'12',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'12',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'12',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'12',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'12',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'12',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'12',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'12',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'12',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'12',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'12',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'12',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'12',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'12',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'12',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'12',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'12',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'12',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'12',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'12',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'12',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'12',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'12',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'12',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'12',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'12',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'12',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'12',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'12',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'12',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'12',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'12',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'12',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'12',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'12',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'12',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'12',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'12',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'12',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'12',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'12',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'12',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'12',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'12',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'12',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'12',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'12',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'12',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'12',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'12',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'12',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'12',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'12',south:'up',west:'up'}}"
		);
		a(
			893,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'13',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'13',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'13',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'13',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'13',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'13',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'13',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'13',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'13',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'13',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'13',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'13',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'13',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'13',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'13',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'13',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'13',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'13',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'13',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'13',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'13',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'13',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'13',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'13',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'13',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'13',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'13',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'13',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'13',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'13',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'13',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'13',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'13',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'13',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'13',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'13',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'13',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'13',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'13',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'13',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'13',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'13',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'13',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'13',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'13',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'13',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'13',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'13',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'13',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'13',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'13',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'13',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'13',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'13',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'13',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'13',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'13',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'13',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'13',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'13',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'13',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'13',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'13',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'13',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'13',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'13',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'13',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'13',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'13',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'13',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'13',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'13',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'13',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'13',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'13',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'13',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'13',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'13',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'13',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'13',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'13',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'13',south:'up',west:'up'}}"
		);
		a(
			894,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'14',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'14',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'14',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'14',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'14',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'14',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'14',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'14',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'14',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'14',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'14',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'14',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'14',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'14',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'14',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'14',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'14',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'14',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'14',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'14',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'14',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'14',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'14',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'14',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'14',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'14',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'14',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'14',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'14',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'14',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'14',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'14',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'14',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'14',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'14',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'14',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'14',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'14',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'14',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'14',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'14',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'14',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'14',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'14',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'14',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'14',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'14',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'14',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'14',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'14',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'14',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'14',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'14',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'14',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'14',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'14',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'14',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'14',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'14',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'14',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'14',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'14',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'14',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'14',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'14',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'14',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'14',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'14',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'14',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'14',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'14',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'14',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'14',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'14',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'14',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'14',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'14',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'14',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'14',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'14',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'14',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'14',south:'up',west:'up'}}"
		);
		a(
			895,
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'15',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'15',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'15',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'15',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'15',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'15',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'15',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'15',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'15',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'none',power:'15',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'15',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'15',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'15',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'15',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'15',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'15',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'15',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'15',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'side',power:'15',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'15',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'15',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'15',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'15',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'15',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'15',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'15',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'15',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'none',north:'up',power:'15',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'15',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'15',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'15',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'15',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'15',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'15',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'15',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'15',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'none',power:'15',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'15',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'15',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'15',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'15',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'15',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'15',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'15',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'15',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'side',power:'15',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'15',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'15',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'15',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'15',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'15',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'15',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'15',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'15',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'side',north:'up',power:'15',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'15',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'15',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'15',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'15',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'15',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'15',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'15',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'15',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'none',power:'15',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'15',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'15',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'15',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'15',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'15',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'15',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'15',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'15',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'side',power:'15',south:'up',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'15',south:'none',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'15',south:'none',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'15',south:'none',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'15',south:'side',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'15',south:'side',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'15',south:'side',west:'up'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'15',south:'up',west:'none'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'15',south:'up',west:'side'}}",
			"{Name:'minecraft:redstone_wire',Properties:{east:'up',north:'up',power:'15',south:'up',west:'up'}}"
		);
		a(896, "{Name:'minecraft:diamond_ore'}", "{Name:'minecraft:diamond_ore'}");
		a(912, "{Name:'minecraft:diamond_block'}", "{Name:'minecraft:diamond_block'}");
		a(928, "{Name:'minecraft:crafting_table'}", "{Name:'minecraft:crafting_table'}");
		a(944, "{Name:'minecraft:wheat',Properties:{age:'0'}}", "{Name:'minecraft:wheat',Properties:{age:'0'}}");
		a(945, "{Name:'minecraft:wheat',Properties:{age:'1'}}", "{Name:'minecraft:wheat',Properties:{age:'1'}}");
		a(946, "{Name:'minecraft:wheat',Properties:{age:'2'}}", "{Name:'minecraft:wheat',Properties:{age:'2'}}");
		a(947, "{Name:'minecraft:wheat',Properties:{age:'3'}}", "{Name:'minecraft:wheat',Properties:{age:'3'}}");
		a(948, "{Name:'minecraft:wheat',Properties:{age:'4'}}", "{Name:'minecraft:wheat',Properties:{age:'4'}}");
		a(949, "{Name:'minecraft:wheat',Properties:{age:'5'}}", "{Name:'minecraft:wheat',Properties:{age:'5'}}");
		a(950, "{Name:'minecraft:wheat',Properties:{age:'6'}}", "{Name:'minecraft:wheat',Properties:{age:'6'}}");
		a(951, "{Name:'minecraft:wheat',Properties:{age:'7'}}", "{Name:'minecraft:wheat',Properties:{age:'7'}}");
		a(960, "{Name:'minecraft:farmland',Properties:{moisture:'0'}}", "{Name:'minecraft:farmland',Properties:{moisture:'0'}}");
		a(961, "{Name:'minecraft:farmland',Properties:{moisture:'1'}}", "{Name:'minecraft:farmland',Properties:{moisture:'1'}}");
		a(962, "{Name:'minecraft:farmland',Properties:{moisture:'2'}}", "{Name:'minecraft:farmland',Properties:{moisture:'2'}}");
		a(963, "{Name:'minecraft:farmland',Properties:{moisture:'3'}}", "{Name:'minecraft:farmland',Properties:{moisture:'3'}}");
		a(964, "{Name:'minecraft:farmland',Properties:{moisture:'4'}}", "{Name:'minecraft:farmland',Properties:{moisture:'4'}}");
		a(965, "{Name:'minecraft:farmland',Properties:{moisture:'5'}}", "{Name:'minecraft:farmland',Properties:{moisture:'5'}}");
		a(966, "{Name:'minecraft:farmland',Properties:{moisture:'6'}}", "{Name:'minecraft:farmland',Properties:{moisture:'6'}}");
		a(967, "{Name:'minecraft:farmland',Properties:{moisture:'7'}}", "{Name:'minecraft:farmland',Properties:{moisture:'7'}}");
		a(978, "{Name:'minecraft:furnace',Properties:{facing:'north',lit:'false'}}", "{Name:'minecraft:furnace',Properties:{facing:'north'}}");
		a(979, "{Name:'minecraft:furnace',Properties:{facing:'south',lit:'false'}}", "{Name:'minecraft:furnace',Properties:{facing:'south'}}");
		a(980, "{Name:'minecraft:furnace',Properties:{facing:'west',lit:'false'}}", "{Name:'minecraft:furnace',Properties:{facing:'west'}}");
		a(981, "{Name:'minecraft:furnace',Properties:{facing:'east',lit:'false'}}", "{Name:'minecraft:furnace',Properties:{facing:'east'}}");
		a(994, "{Name:'minecraft:furnace',Properties:{facing:'north',lit:'true'}}", "{Name:'minecraft:lit_furnace',Properties:{facing:'north'}}");
		a(995, "{Name:'minecraft:furnace',Properties:{facing:'south',lit:'true'}}", "{Name:'minecraft:lit_furnace',Properties:{facing:'south'}}");
		a(996, "{Name:'minecraft:furnace',Properties:{facing:'west',lit:'true'}}", "{Name:'minecraft:lit_furnace',Properties:{facing:'west'}}");
		a(997, "{Name:'minecraft:furnace',Properties:{facing:'east',lit:'true'}}", "{Name:'minecraft:lit_furnace',Properties:{facing:'east'}}");
		a(1008, "{Name:'minecraft:sign',Properties:{rotation:'0'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'0'}}");
		a(1009, "{Name:'minecraft:sign',Properties:{rotation:'1'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'1'}}");
		a(1010, "{Name:'minecraft:sign',Properties:{rotation:'2'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'2'}}");
		a(1011, "{Name:'minecraft:sign',Properties:{rotation:'3'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'3'}}");
		a(1012, "{Name:'minecraft:sign',Properties:{rotation:'4'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'4'}}");
		a(1013, "{Name:'minecraft:sign',Properties:{rotation:'5'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'5'}}");
		a(1014, "{Name:'minecraft:sign',Properties:{rotation:'6'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'6'}}");
		a(1015, "{Name:'minecraft:sign',Properties:{rotation:'7'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'7'}}");
		a(1016, "{Name:'minecraft:sign',Properties:{rotation:'8'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'8'}}");
		a(1017, "{Name:'minecraft:sign',Properties:{rotation:'9'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'9'}}");
		a(1018, "{Name:'minecraft:sign',Properties:{rotation:'10'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'10'}}");
		a(1019, "{Name:'minecraft:sign',Properties:{rotation:'11'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'11'}}");
		a(1020, "{Name:'minecraft:sign',Properties:{rotation:'12'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'12'}}");
		a(1021, "{Name:'minecraft:sign',Properties:{rotation:'13'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'13'}}");
		a(1022, "{Name:'minecraft:sign',Properties:{rotation:'14'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'14'}}");
		a(1023, "{Name:'minecraft:sign',Properties:{rotation:'15'}}", "{Name:'minecraft:standing_sign',Properties:{rotation:'15'}}");
		a(
			1024,
			"{Name:'minecraft:oak_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			1025,
			"{Name:'minecraft:oak_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			1026,
			"{Name:'minecraft:oak_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			1027,
			"{Name:'minecraft:oak_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			1028,
			"{Name:'minecraft:oak_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			1029,
			"{Name:'minecraft:oak_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			1030,
			"{Name:'minecraft:oak_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			1031,
			"{Name:'minecraft:oak_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			1032,
			"{Name:'minecraft:oak_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}"
		);
		a(
			1033,
			"{Name:'minecraft:oak_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}"
		);
		a(
			1034,
			"{Name:'minecraft:oak_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}"
		);
		a(
			1035,
			"{Name:'minecraft:oak_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:wooden_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}"
		);
		a(1036, "{Name:'minecraft:oak_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}");
		a(1037, "{Name:'minecraft:oak_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}");
		a(1038, "{Name:'minecraft:oak_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}");
		a(1039, "{Name:'minecraft:oak_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}");
		a(1042, "{Name:'minecraft:ladder',Properties:{facing:'north'}}", "{Name:'minecraft:ladder',Properties:{facing:'north'}}");
		a(1043, "{Name:'minecraft:ladder',Properties:{facing:'south'}}", "{Name:'minecraft:ladder',Properties:{facing:'south'}}");
		a(1044, "{Name:'minecraft:ladder',Properties:{facing:'west'}}", "{Name:'minecraft:ladder',Properties:{facing:'west'}}");
		a(1045, "{Name:'minecraft:ladder',Properties:{facing:'east'}}", "{Name:'minecraft:ladder',Properties:{facing:'east'}}");
		a(1056, "{Name:'minecraft:rail',Properties:{shape:'north_south'}}", "{Name:'minecraft:rail',Properties:{shape:'north_south'}}");
		a(1057, "{Name:'minecraft:rail',Properties:{shape:'east_west'}}", "{Name:'minecraft:rail',Properties:{shape:'east_west'}}");
		a(1058, "{Name:'minecraft:rail',Properties:{shape:'ascending_east'}}", "{Name:'minecraft:rail',Properties:{shape:'ascending_east'}}");
		a(1059, "{Name:'minecraft:rail',Properties:{shape:'ascending_west'}}", "{Name:'minecraft:rail',Properties:{shape:'ascending_west'}}");
		a(1060, "{Name:'minecraft:rail',Properties:{shape:'ascending_north'}}", "{Name:'minecraft:rail',Properties:{shape:'ascending_north'}}");
		a(1061, "{Name:'minecraft:rail',Properties:{shape:'ascending_south'}}", "{Name:'minecraft:rail',Properties:{shape:'ascending_south'}}");
		a(1062, "{Name:'minecraft:rail',Properties:{shape:'south_east'}}", "{Name:'minecraft:rail',Properties:{shape:'south_east'}}");
		a(1063, "{Name:'minecraft:rail',Properties:{shape:'south_west'}}", "{Name:'minecraft:rail',Properties:{shape:'south_west'}}");
		a(1064, "{Name:'minecraft:rail',Properties:{shape:'north_west'}}", "{Name:'minecraft:rail',Properties:{shape:'north_west'}}");
		a(1065, "{Name:'minecraft:rail',Properties:{shape:'north_east'}}", "{Name:'minecraft:rail',Properties:{shape:'north_east'}}");
		a(
			1072,
			"{Name:'minecraft:cobblestone_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}"
		);
		a(
			1073,
			"{Name:'minecraft:cobblestone_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}"
		);
		a(
			1074,
			"{Name:'minecraft:cobblestone_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}"
		);
		a(
			1075,
			"{Name:'minecraft:cobblestone_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}"
		);
		a(
			1076,
			"{Name:'minecraft:cobblestone_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'east',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'east',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'east',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'east',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}"
		);
		a(
			1077,
			"{Name:'minecraft:cobblestone_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'west',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'west',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'west',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'west',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}"
		);
		a(
			1078,
			"{Name:'minecraft:cobblestone_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'south',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'south',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'south',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'south',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}"
		);
		a(
			1079,
			"{Name:'minecraft:cobblestone_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'north',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'north',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'north',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'north',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:stone_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}"
		);
		a(1090, "{Name:'minecraft:wall_sign',Properties:{facing:'north'}}", "{Name:'minecraft:wall_sign',Properties:{facing:'north'}}");
		a(1091, "{Name:'minecraft:wall_sign',Properties:{facing:'south'}}", "{Name:'minecraft:wall_sign',Properties:{facing:'south'}}");
		a(1092, "{Name:'minecraft:wall_sign',Properties:{facing:'west'}}", "{Name:'minecraft:wall_sign',Properties:{facing:'west'}}");
		a(1093, "{Name:'minecraft:wall_sign',Properties:{facing:'east'}}", "{Name:'minecraft:wall_sign',Properties:{facing:'east'}}");
		a(
			1104,
			"{Name:'minecraft:lever',Properties:{face:'ceiling',facing:'west',powered:'false'}}",
			"{Name:'minecraft:lever',Properties:{facing:'down_x',powered:'false'}}"
		);
		a(
			1105,
			"{Name:'minecraft:lever',Properties:{face:'wall',facing:'east',powered:'false'}}",
			"{Name:'minecraft:lever',Properties:{facing:'east',powered:'false'}}"
		);
		a(
			1106,
			"{Name:'minecraft:lever',Properties:{face:'wall',facing:'west',powered:'false'}}",
			"{Name:'minecraft:lever',Properties:{facing:'west',powered:'false'}}"
		);
		a(
			1107,
			"{Name:'minecraft:lever',Properties:{face:'wall',facing:'south',powered:'false'}}",
			"{Name:'minecraft:lever',Properties:{facing:'south',powered:'false'}}"
		);
		a(
			1108,
			"{Name:'minecraft:lever',Properties:{face:'wall',facing:'north',powered:'false'}}",
			"{Name:'minecraft:lever',Properties:{facing:'north',powered:'false'}}"
		);
		a(
			1109,
			"{Name:'minecraft:lever',Properties:{face:'floor',facing:'north',powered:'false'}}",
			"{Name:'minecraft:lever',Properties:{facing:'up_z',powered:'false'}}"
		);
		a(
			1110,
			"{Name:'minecraft:lever',Properties:{face:'floor',facing:'west',powered:'false'}}",
			"{Name:'minecraft:lever',Properties:{facing:'up_x',powered:'false'}}"
		);
		a(
			1111,
			"{Name:'minecraft:lever',Properties:{face:'ceiling',facing:'north',powered:'false'}}",
			"{Name:'minecraft:lever',Properties:{facing:'down_z',powered:'false'}}"
		);
		a(
			1112,
			"{Name:'minecraft:lever',Properties:{face:'ceiling',facing:'west',powered:'true'}}",
			"{Name:'minecraft:lever',Properties:{facing:'down_x',powered:'true'}}"
		);
		a(
			1113, "{Name:'minecraft:lever',Properties:{face:'wall',facing:'east',powered:'true'}}", "{Name:'minecraft:lever',Properties:{facing:'east',powered:'true'}}"
		);
		a(
			1114, "{Name:'minecraft:lever',Properties:{face:'wall',facing:'west',powered:'true'}}", "{Name:'minecraft:lever',Properties:{facing:'west',powered:'true'}}"
		);
		a(
			1115,
			"{Name:'minecraft:lever',Properties:{face:'wall',facing:'south',powered:'true'}}",
			"{Name:'minecraft:lever',Properties:{facing:'south',powered:'true'}}"
		);
		a(
			1116,
			"{Name:'minecraft:lever',Properties:{face:'wall',facing:'north',powered:'true'}}",
			"{Name:'minecraft:lever',Properties:{facing:'north',powered:'true'}}"
		);
		a(
			1117,
			"{Name:'minecraft:lever',Properties:{face:'floor',facing:'north',powered:'true'}}",
			"{Name:'minecraft:lever',Properties:{facing:'up_z',powered:'true'}}"
		);
		a(
			1118,
			"{Name:'minecraft:lever',Properties:{face:'floor',facing:'west',powered:'true'}}",
			"{Name:'minecraft:lever',Properties:{facing:'up_x',powered:'true'}}"
		);
		a(
			1119,
			"{Name:'minecraft:lever',Properties:{face:'ceiling',facing:'north',powered:'true'}}",
			"{Name:'minecraft:lever',Properties:{facing:'down_z',powered:'true'}}"
		);
		a(1120, "{Name:'minecraft:stone_pressure_plate',Properties:{powered:'false'}}", "{Name:'minecraft:stone_pressure_plate',Properties:{powered:'false'}}");
		a(1121, "{Name:'minecraft:stone_pressure_plate',Properties:{powered:'true'}}", "{Name:'minecraft:stone_pressure_plate',Properties:{powered:'true'}}");
		a(
			1136,
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			1137,
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			1138,
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			1139,
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			1140,
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			1141,
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			1142,
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			1143,
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			1144,
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}"
		);
		a(
			1145,
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}"
		);
		a(
			1146,
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}"
		);
		a(
			1147,
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:iron_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}"
		);
		a(1148, "{Name:'minecraft:iron_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}");
		a(1149, "{Name:'minecraft:iron_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}");
		a(1150, "{Name:'minecraft:iron_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}");
		a(1151, "{Name:'minecraft:iron_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}");
		a(1152, "{Name:'minecraft:oak_pressure_plate',Properties:{powered:'false'}}", "{Name:'minecraft:wooden_pressure_plate',Properties:{powered:'false'}}");
		a(1153, "{Name:'minecraft:oak_pressure_plate',Properties:{powered:'true'}}", "{Name:'minecraft:wooden_pressure_plate',Properties:{powered:'true'}}");
		a(1168, "{Name:'minecraft:redstone_ore',Properties:{lit:'false'}}", "{Name:'minecraft:redstone_ore'}");
		a(1184, "{Name:'minecraft:redstone_ore',Properties:{lit:'true'}}", "{Name:'minecraft:lit_redstone_ore'}");
		a(1201, "{Name:'minecraft:redstone_wall_torch',Properties:{facing:'east',lit:'false'}}", "{Name:'minecraft:unlit_redstone_torch',Properties:{facing:'east'}}");
		a(1202, "{Name:'minecraft:redstone_wall_torch',Properties:{facing:'west',lit:'false'}}", "{Name:'minecraft:unlit_redstone_torch',Properties:{facing:'west'}}");
		a(
			1203,
			"{Name:'minecraft:redstone_wall_torch',Properties:{facing:'south',lit:'false'}}",
			"{Name:'minecraft:unlit_redstone_torch',Properties:{facing:'south'}}"
		);
		a(
			1204,
			"{Name:'minecraft:redstone_wall_torch',Properties:{facing:'north',lit:'false'}}",
			"{Name:'minecraft:unlit_redstone_torch',Properties:{facing:'north'}}"
		);
		a(1205, "{Name:'minecraft:redstone_torch',Properties:{lit:'false'}}", "{Name:'minecraft:unlit_redstone_torch',Properties:{facing:'up'}}");
		a(1217, "{Name:'minecraft:redstone_wall_torch',Properties:{facing:'east',lit:'true'}}", "{Name:'minecraft:redstone_torch',Properties:{facing:'east'}}");
		a(1218, "{Name:'minecraft:redstone_wall_torch',Properties:{facing:'west',lit:'true'}}", "{Name:'minecraft:redstone_torch',Properties:{facing:'west'}}");
		a(1219, "{Name:'minecraft:redstone_wall_torch',Properties:{facing:'south',lit:'true'}}", "{Name:'minecraft:redstone_torch',Properties:{facing:'south'}}");
		a(1220, "{Name:'minecraft:redstone_wall_torch',Properties:{facing:'north',lit:'true'}}", "{Name:'minecraft:redstone_torch',Properties:{facing:'north'}}");
		a(1221, "{Name:'minecraft:redstone_torch',Properties:{lit:'true'}}", "{Name:'minecraft:redstone_torch',Properties:{facing:'up'}}");
		a(
			1232,
			"{Name:'minecraft:stone_button',Properties:{face:'ceiling',facing:'north',powered:'false'}}",
			"{Name:'minecraft:stone_button',Properties:{facing:'down',powered:'false'}}"
		);
		a(
			1233,
			"{Name:'minecraft:stone_button',Properties:{face:'wall',facing:'east',powered:'false'}}",
			"{Name:'minecraft:stone_button',Properties:{facing:'east',powered:'false'}}"
		);
		a(
			1234,
			"{Name:'minecraft:stone_button',Properties:{face:'wall',facing:'west',powered:'false'}}",
			"{Name:'minecraft:stone_button',Properties:{facing:'west',powered:'false'}}"
		);
		a(
			1235,
			"{Name:'minecraft:stone_button',Properties:{face:'wall',facing:'south',powered:'false'}}",
			"{Name:'minecraft:stone_button',Properties:{facing:'south',powered:'false'}}"
		);
		a(
			1236,
			"{Name:'minecraft:stone_button',Properties:{face:'wall',facing:'north',powered:'false'}}",
			"{Name:'minecraft:stone_button',Properties:{facing:'north',powered:'false'}}"
		);
		a(
			1237,
			"{Name:'minecraft:stone_button',Properties:{face:'floor',facing:'north',powered:'false'}}",
			"{Name:'minecraft:stone_button',Properties:{facing:'up',powered:'false'}}"
		);
		a(
			1240,
			"{Name:'minecraft:stone_button',Properties:{face:'ceiling',facing:'north',powered:'true'}}",
			"{Name:'minecraft:stone_button',Properties:{facing:'down',powered:'true'}}"
		);
		a(
			1241,
			"{Name:'minecraft:stone_button',Properties:{face:'wall',facing:'east',powered:'true'}}",
			"{Name:'minecraft:stone_button',Properties:{facing:'east',powered:'true'}}"
		);
		a(
			1242,
			"{Name:'minecraft:stone_button',Properties:{face:'wall',facing:'west',powered:'true'}}",
			"{Name:'minecraft:stone_button',Properties:{facing:'west',powered:'true'}}"
		);
		a(
			1243,
			"{Name:'minecraft:stone_button',Properties:{face:'wall',facing:'south',powered:'true'}}",
			"{Name:'minecraft:stone_button',Properties:{facing:'south',powered:'true'}}"
		);
		a(
			1244,
			"{Name:'minecraft:stone_button',Properties:{face:'wall',facing:'north',powered:'true'}}",
			"{Name:'minecraft:stone_button',Properties:{facing:'north',powered:'true'}}"
		);
		a(
			1245,
			"{Name:'minecraft:stone_button',Properties:{face:'floor',facing:'north',powered:'true'}}",
			"{Name:'minecraft:stone_button',Properties:{facing:'up',powered:'true'}}"
		);
		a(1248, "{Name:'minecraft:snow',Properties:{layers:'1'}}", "{Name:'minecraft:snow_layer',Properties:{layers:'1'}}");
		a(1249, "{Name:'minecraft:snow',Properties:{layers:'2'}}", "{Name:'minecraft:snow_layer',Properties:{layers:'2'}}");
		a(1250, "{Name:'minecraft:snow',Properties:{layers:'3'}}", "{Name:'minecraft:snow_layer',Properties:{layers:'3'}}");
		a(1251, "{Name:'minecraft:snow',Properties:{layers:'4'}}", "{Name:'minecraft:snow_layer',Properties:{layers:'4'}}");
		a(1252, "{Name:'minecraft:snow',Properties:{layers:'5'}}", "{Name:'minecraft:snow_layer',Properties:{layers:'5'}}");
		a(1253, "{Name:'minecraft:snow',Properties:{layers:'6'}}", "{Name:'minecraft:snow_layer',Properties:{layers:'6'}}");
		a(1254, "{Name:'minecraft:snow',Properties:{layers:'7'}}", "{Name:'minecraft:snow_layer',Properties:{layers:'7'}}");
		a(1255, "{Name:'minecraft:snow',Properties:{layers:'8'}}", "{Name:'minecraft:snow_layer',Properties:{layers:'8'}}");
		a(1264, "{Name:'minecraft:ice'}", "{Name:'minecraft:ice'}");
		a(1280, "{Name:'minecraft:snow_block'}", "{Name:'minecraft:snow'}");
		a(1296, "{Name:'minecraft:cactus',Properties:{age:'0'}}", "{Name:'minecraft:cactus',Properties:{age:'0'}}");
		a(1297, "{Name:'minecraft:cactus',Properties:{age:'1'}}", "{Name:'minecraft:cactus',Properties:{age:'1'}}");
		a(1298, "{Name:'minecraft:cactus',Properties:{age:'2'}}", "{Name:'minecraft:cactus',Properties:{age:'2'}}");
		a(1299, "{Name:'minecraft:cactus',Properties:{age:'3'}}", "{Name:'minecraft:cactus',Properties:{age:'3'}}");
		a(1300, "{Name:'minecraft:cactus',Properties:{age:'4'}}", "{Name:'minecraft:cactus',Properties:{age:'4'}}");
		a(1301, "{Name:'minecraft:cactus',Properties:{age:'5'}}", "{Name:'minecraft:cactus',Properties:{age:'5'}}");
		a(1302, "{Name:'minecraft:cactus',Properties:{age:'6'}}", "{Name:'minecraft:cactus',Properties:{age:'6'}}");
		a(1303, "{Name:'minecraft:cactus',Properties:{age:'7'}}", "{Name:'minecraft:cactus',Properties:{age:'7'}}");
		a(1304, "{Name:'minecraft:cactus',Properties:{age:'8'}}", "{Name:'minecraft:cactus',Properties:{age:'8'}}");
		a(1305, "{Name:'minecraft:cactus',Properties:{age:'9'}}", "{Name:'minecraft:cactus',Properties:{age:'9'}}");
		a(1306, "{Name:'minecraft:cactus',Properties:{age:'10'}}", "{Name:'minecraft:cactus',Properties:{age:'10'}}");
		a(1307, "{Name:'minecraft:cactus',Properties:{age:'11'}}", "{Name:'minecraft:cactus',Properties:{age:'11'}}");
		a(1308, "{Name:'minecraft:cactus',Properties:{age:'12'}}", "{Name:'minecraft:cactus',Properties:{age:'12'}}");
		a(1309, "{Name:'minecraft:cactus',Properties:{age:'13'}}", "{Name:'minecraft:cactus',Properties:{age:'13'}}");
		a(1310, "{Name:'minecraft:cactus',Properties:{age:'14'}}", "{Name:'minecraft:cactus',Properties:{age:'14'}}");
		a(1311, "{Name:'minecraft:cactus',Properties:{age:'15'}}", "{Name:'minecraft:cactus',Properties:{age:'15'}}");
		a(1312, "{Name:'minecraft:clay'}", "{Name:'minecraft:clay'}");
		a(1328, "{Name:'minecraft:sugar_cane',Properties:{age:'0'}}", "{Name:'minecraft:reeds',Properties:{age:'0'}}");
		a(1329, "{Name:'minecraft:sugar_cane',Properties:{age:'1'}}", "{Name:'minecraft:reeds',Properties:{age:'1'}}");
		a(1330, "{Name:'minecraft:sugar_cane',Properties:{age:'2'}}", "{Name:'minecraft:reeds',Properties:{age:'2'}}");
		a(1331, "{Name:'minecraft:sugar_cane',Properties:{age:'3'}}", "{Name:'minecraft:reeds',Properties:{age:'3'}}");
		a(1332, "{Name:'minecraft:sugar_cane',Properties:{age:'4'}}", "{Name:'minecraft:reeds',Properties:{age:'4'}}");
		a(1333, "{Name:'minecraft:sugar_cane',Properties:{age:'5'}}", "{Name:'minecraft:reeds',Properties:{age:'5'}}");
		a(1334, "{Name:'minecraft:sugar_cane',Properties:{age:'6'}}", "{Name:'minecraft:reeds',Properties:{age:'6'}}");
		a(1335, "{Name:'minecraft:sugar_cane',Properties:{age:'7'}}", "{Name:'minecraft:reeds',Properties:{age:'7'}}");
		a(1336, "{Name:'minecraft:sugar_cane',Properties:{age:'8'}}", "{Name:'minecraft:reeds',Properties:{age:'8'}}");
		a(1337, "{Name:'minecraft:sugar_cane',Properties:{age:'9'}}", "{Name:'minecraft:reeds',Properties:{age:'9'}}");
		a(1338, "{Name:'minecraft:sugar_cane',Properties:{age:'10'}}", "{Name:'minecraft:reeds',Properties:{age:'10'}}");
		a(1339, "{Name:'minecraft:sugar_cane',Properties:{age:'11'}}", "{Name:'minecraft:reeds',Properties:{age:'11'}}");
		a(1340, "{Name:'minecraft:sugar_cane',Properties:{age:'12'}}", "{Name:'minecraft:reeds',Properties:{age:'12'}}");
		a(1341, "{Name:'minecraft:sugar_cane',Properties:{age:'13'}}", "{Name:'minecraft:reeds',Properties:{age:'13'}}");
		a(1342, "{Name:'minecraft:sugar_cane',Properties:{age:'14'}}", "{Name:'minecraft:reeds',Properties:{age:'14'}}");
		a(1343, "{Name:'minecraft:sugar_cane',Properties:{age:'15'}}", "{Name:'minecraft:reeds',Properties:{age:'15'}}");
		a(1344, "{Name:'minecraft:jukebox',Properties:{has_record:'false'}}", "{Name:'minecraft:jukebox',Properties:{has_record:'false'}}");
		a(1345, "{Name:'minecraft:jukebox',Properties:{has_record:'true'}}", "{Name:'minecraft:jukebox',Properties:{has_record:'true'}}");
		a(
			1360,
			"{Name:'minecraft:oak_fence',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:fence',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:fence',Properties:{east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:fence',Properties:{east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:fence',Properties:{east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:fence',Properties:{east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:fence',Properties:{east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:fence',Properties:{east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:fence',Properties:{east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:fence',Properties:{east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:fence',Properties:{east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:fence',Properties:{east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:fence',Properties:{east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:fence',Properties:{east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:fence',Properties:{east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:fence',Properties:{east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:fence',Properties:{east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(1376, "{Name:'minecraft:carved_pumpkin',Properties:{facing:'south'}}", "{Name:'minecraft:pumpkin',Properties:{facing:'south'}}");
		a(1377, "{Name:'minecraft:carved_pumpkin',Properties:{facing:'west'}}", "{Name:'minecraft:pumpkin',Properties:{facing:'west'}}");
		a(1378, "{Name:'minecraft:carved_pumpkin',Properties:{facing:'north'}}", "{Name:'minecraft:pumpkin',Properties:{facing:'north'}}");
		a(1379, "{Name:'minecraft:carved_pumpkin',Properties:{facing:'east'}}", "{Name:'minecraft:pumpkin',Properties:{facing:'east'}}");
		a(1392, "{Name:'minecraft:netherrack'}", "{Name:'minecraft:netherrack'}");
		a(1408, "{Name:'minecraft:soul_sand'}", "{Name:'minecraft:soul_sand'}");
		a(1424, "{Name:'minecraft:glowstone'}", "{Name:'minecraft:glowstone'}");
		a(1441, "{Name:'minecraft:portal',Properties:{axis:'x'}}", "{Name:'minecraft:portal',Properties:{axis:'x'}}");
		a(1442, "{Name:'minecraft:portal',Properties:{axis:'z'}}", "{Name:'minecraft:portal',Properties:{axis:'z'}}");
		a(1456, "{Name:'minecraft:jack_o_lantern',Properties:{facing:'south'}}", "{Name:'minecraft:lit_pumpkin',Properties:{facing:'south'}}");
		a(1457, "{Name:'minecraft:jack_o_lantern',Properties:{facing:'west'}}", "{Name:'minecraft:lit_pumpkin',Properties:{facing:'west'}}");
		a(1458, "{Name:'minecraft:jack_o_lantern',Properties:{facing:'north'}}", "{Name:'minecraft:lit_pumpkin',Properties:{facing:'north'}}");
		a(1459, "{Name:'minecraft:jack_o_lantern',Properties:{facing:'east'}}", "{Name:'minecraft:lit_pumpkin',Properties:{facing:'east'}}");
		a(1472, "{Name:'minecraft:cake',Properties:{bites:'0'}}", "{Name:'minecraft:cake',Properties:{bites:'0'}}");
		a(1473, "{Name:'minecraft:cake',Properties:{bites:'1'}}", "{Name:'minecraft:cake',Properties:{bites:'1'}}");
		a(1474, "{Name:'minecraft:cake',Properties:{bites:'2'}}", "{Name:'minecraft:cake',Properties:{bites:'2'}}");
		a(1475, "{Name:'minecraft:cake',Properties:{bites:'3'}}", "{Name:'minecraft:cake',Properties:{bites:'3'}}");
		a(1476, "{Name:'minecraft:cake',Properties:{bites:'4'}}", "{Name:'minecraft:cake',Properties:{bites:'4'}}");
		a(1477, "{Name:'minecraft:cake',Properties:{bites:'5'}}", "{Name:'minecraft:cake',Properties:{bites:'5'}}");
		a(1478, "{Name:'minecraft:cake',Properties:{bites:'6'}}", "{Name:'minecraft:cake',Properties:{bites:'6'}}");
		a(
			1488,
			"{Name:'minecraft:repeater',Properties:{delay:'1',facing:'south',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'1',facing:'south',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'1',facing:'south',locked:'true'}}"
		);
		a(
			1489,
			"{Name:'minecraft:repeater',Properties:{delay:'1',facing:'west',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'1',facing:'west',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'1',facing:'west',locked:'true'}}"
		);
		a(
			1490,
			"{Name:'minecraft:repeater',Properties:{delay:'1',facing:'north',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'1',facing:'north',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'1',facing:'north',locked:'true'}}"
		);
		a(
			1491,
			"{Name:'minecraft:repeater',Properties:{delay:'1',facing:'east',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'1',facing:'east',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'1',facing:'east',locked:'true'}}"
		);
		a(
			1492,
			"{Name:'minecraft:repeater',Properties:{delay:'2',facing:'south',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'2',facing:'south',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'2',facing:'south',locked:'true'}}"
		);
		a(
			1493,
			"{Name:'minecraft:repeater',Properties:{delay:'2',facing:'west',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'2',facing:'west',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'2',facing:'west',locked:'true'}}"
		);
		a(
			1494,
			"{Name:'minecraft:repeater',Properties:{delay:'2',facing:'north',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'2',facing:'north',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'2',facing:'north',locked:'true'}}"
		);
		a(
			1495,
			"{Name:'minecraft:repeater',Properties:{delay:'2',facing:'east',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'2',facing:'east',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'2',facing:'east',locked:'true'}}"
		);
		a(
			1496,
			"{Name:'minecraft:repeater',Properties:{delay:'3',facing:'south',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'3',facing:'south',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'3',facing:'south',locked:'true'}}"
		);
		a(
			1497,
			"{Name:'minecraft:repeater',Properties:{delay:'3',facing:'west',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'3',facing:'west',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'3',facing:'west',locked:'true'}}"
		);
		a(
			1498,
			"{Name:'minecraft:repeater',Properties:{delay:'3',facing:'north',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'3',facing:'north',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'3',facing:'north',locked:'true'}}"
		);
		a(
			1499,
			"{Name:'minecraft:repeater',Properties:{delay:'3',facing:'east',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'3',facing:'east',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'3',facing:'east',locked:'true'}}"
		);
		a(
			1500,
			"{Name:'minecraft:repeater',Properties:{delay:'4',facing:'south',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'4',facing:'south',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'4',facing:'south',locked:'true'}}"
		);
		a(
			1501,
			"{Name:'minecraft:repeater',Properties:{delay:'4',facing:'west',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'4',facing:'west',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'4',facing:'west',locked:'true'}}"
		);
		a(
			1502,
			"{Name:'minecraft:repeater',Properties:{delay:'4',facing:'north',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'4',facing:'north',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'4',facing:'north',locked:'true'}}"
		);
		a(
			1503,
			"{Name:'minecraft:repeater',Properties:{delay:'4',facing:'east',locked:'false',powered:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'4',facing:'east',locked:'false'}}",
			"{Name:'minecraft:unpowered_repeater',Properties:{delay:'4',facing:'east',locked:'true'}}"
		);
		a(
			1504,
			"{Name:'minecraft:repeater',Properties:{delay:'1',facing:'south',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'1',facing:'south',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'1',facing:'south',locked:'true'}}"
		);
		a(
			1505,
			"{Name:'minecraft:repeater',Properties:{delay:'1',facing:'west',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'1',facing:'west',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'1',facing:'west',locked:'true'}}"
		);
		a(
			1506,
			"{Name:'minecraft:repeater',Properties:{delay:'1',facing:'north',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'1',facing:'north',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'1',facing:'north',locked:'true'}}"
		);
		a(
			1507,
			"{Name:'minecraft:repeater',Properties:{delay:'1',facing:'east',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'1',facing:'east',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'1',facing:'east',locked:'true'}}"
		);
		a(
			1508,
			"{Name:'minecraft:repeater',Properties:{delay:'2',facing:'south',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'2',facing:'south',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'2',facing:'south',locked:'true'}}"
		);
		a(
			1509,
			"{Name:'minecraft:repeater',Properties:{delay:'2',facing:'west',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'2',facing:'west',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'2',facing:'west',locked:'true'}}"
		);
		a(
			1510,
			"{Name:'minecraft:repeater',Properties:{delay:'2',facing:'north',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'2',facing:'north',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'2',facing:'north',locked:'true'}}"
		);
		a(
			1511,
			"{Name:'minecraft:repeater',Properties:{delay:'2',facing:'east',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'2',facing:'east',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'2',facing:'east',locked:'true'}}"
		);
		a(
			1512,
			"{Name:'minecraft:repeater',Properties:{delay:'3',facing:'south',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'3',facing:'south',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'3',facing:'south',locked:'true'}}"
		);
		a(
			1513,
			"{Name:'minecraft:repeater',Properties:{delay:'3',facing:'west',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'3',facing:'west',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'3',facing:'west',locked:'true'}}"
		);
		a(
			1514,
			"{Name:'minecraft:repeater',Properties:{delay:'3',facing:'north',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'3',facing:'north',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'3',facing:'north',locked:'true'}}"
		);
		a(
			1515,
			"{Name:'minecraft:repeater',Properties:{delay:'3',facing:'east',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'3',facing:'east',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'3',facing:'east',locked:'true'}}"
		);
		a(
			1516,
			"{Name:'minecraft:repeater',Properties:{delay:'4',facing:'south',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'4',facing:'south',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'4',facing:'south',locked:'true'}}"
		);
		a(
			1517,
			"{Name:'minecraft:repeater',Properties:{delay:'4',facing:'west',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'4',facing:'west',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'4',facing:'west',locked:'true'}}"
		);
		a(
			1518,
			"{Name:'minecraft:repeater',Properties:{delay:'4',facing:'north',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'4',facing:'north',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'4',facing:'north',locked:'true'}}"
		);
		a(
			1519,
			"{Name:'minecraft:repeater',Properties:{delay:'4',facing:'east',locked:'false',powered:'true'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'4',facing:'east',locked:'false'}}",
			"{Name:'minecraft:powered_repeater',Properties:{delay:'4',facing:'east',locked:'true'}}"
		);
		a(1520, "{Name:'minecraft:white_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'white'}}");
		a(1521, "{Name:'minecraft:orange_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'orange'}}");
		a(1522, "{Name:'minecraft:magenta_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'magenta'}}");
		a(1523, "{Name:'minecraft:light_blue_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'light_blue'}}");
		a(1524, "{Name:'minecraft:yellow_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'yellow'}}");
		a(1525, "{Name:'minecraft:lime_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'lime'}}");
		a(1526, "{Name:'minecraft:pink_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'pink'}}");
		a(1527, "{Name:'minecraft:gray_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'gray'}}");
		a(1528, "{Name:'minecraft:light_gray_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'silver'}}");
		a(1529, "{Name:'minecraft:cyan_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'cyan'}}");
		a(1530, "{Name:'minecraft:purple_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'purple'}}");
		a(1531, "{Name:'minecraft:blue_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'blue'}}");
		a(1532, "{Name:'minecraft:brown_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'brown'}}");
		a(1533, "{Name:'minecraft:green_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'green'}}");
		a(1534, "{Name:'minecraft:red_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'red'}}");
		a(1535, "{Name:'minecraft:black_stained_glass'}", "{Name:'minecraft:stained_glass',Properties:{color:'black'}}");
		a(
			1536,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'north',half:'bottom',open:'false'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'north',half:'bottom',open:'false'}}"
		);
		a(
			1537,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'south',half:'bottom',open:'false'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'south',half:'bottom',open:'false'}}"
		);
		a(
			1538,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'west',half:'bottom',open:'false'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'west',half:'bottom',open:'false'}}"
		);
		a(
			1539,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'east',half:'bottom',open:'false'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'east',half:'bottom',open:'false'}}"
		);
		a(
			1540,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'north',half:'bottom',open:'true'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'north',half:'bottom',open:'true'}}"
		);
		a(
			1541,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'south',half:'bottom',open:'true'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'south',half:'bottom',open:'true'}}"
		);
		a(
			1542,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'west',half:'bottom',open:'true'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'west',half:'bottom',open:'true'}}"
		);
		a(
			1543,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'east',half:'bottom',open:'true'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'east',half:'bottom',open:'true'}}"
		);
		a(
			1544,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'north',half:'top',open:'false'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'north',half:'top',open:'false'}}"
		);
		a(
			1545,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'south',half:'top',open:'false'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'south',half:'top',open:'false'}}"
		);
		a(
			1546,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'west',half:'top',open:'false'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'west',half:'top',open:'false'}}"
		);
		a(
			1547,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'east',half:'top',open:'false'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'east',half:'top',open:'false'}}"
		);
		a(
			1548,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'north',half:'top',open:'true'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'north',half:'top',open:'true'}}"
		);
		a(
			1549,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'south',half:'top',open:'true'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'south',half:'top',open:'true'}}"
		);
		a(
			1550,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'west',half:'top',open:'true'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'west',half:'top',open:'true'}}"
		);
		a(
			1551,
			"{Name:'minecraft:oak_trapdoor',Properties:{facing:'east',half:'top',open:'true'}}",
			"{Name:'minecraft:trapdoor',Properties:{facing:'east',half:'top',open:'true'}}"
		);
		a(1552, "{Name:'minecraft:infested_stone'}", "{Name:'minecraft:monster_egg',Properties:{variant:'stone'}}");
		a(1553, "{Name:'minecraft:infested_cobblestone'}", "{Name:'minecraft:monster_egg',Properties:{variant:'cobblestone'}}");
		a(1554, "{Name:'minecraft:infested_stone_bricks'}", "{Name:'minecraft:monster_egg',Properties:{variant:'stone_brick'}}");
		a(1555, "{Name:'minecraft:infested_mossy_stone_bricks'}", "{Name:'minecraft:monster_egg',Properties:{variant:'mossy_brick'}}");
		a(1556, "{Name:'minecraft:infested_cracked_stone_bricks'}", "{Name:'minecraft:monster_egg',Properties:{variant:'cracked_brick'}}");
		a(1557, "{Name:'minecraft:infested_chiseled_stone_bricks'}", "{Name:'minecraft:monster_egg',Properties:{variant:'chiseled_brick'}}");
		a(1568, "{Name:'minecraft:stone_bricks'}", "{Name:'minecraft:stonebrick',Properties:{variant:'stonebrick'}}");
		a(1569, "{Name:'minecraft:mossy_stone_bricks'}", "{Name:'minecraft:stonebrick',Properties:{variant:'mossy_stonebrick'}}");
		a(1570, "{Name:'minecraft:cracked_stone_bricks'}", "{Name:'minecraft:stonebrick',Properties:{variant:'cracked_stonebrick'}}");
		a(1571, "{Name:'minecraft:chiseled_stone_bricks'}", "{Name:'minecraft:stonebrick',Properties:{variant:'chiseled_stonebrick'}}");
		a(
			1584,
			"{Name:'minecraft:brown_mushroom_block',Properties:{north:'false',east:'false',south:'false',west:'false',up:'false',down:'false'}}",
			"{Name:'minecraft:brown_mushroom_block',Properties:{variant:'all_inside'}}"
		);
		a(
			1585,
			"{Name:'minecraft:brown_mushroom_block',Properties:{north:'true',east:'false',south:'false',west:'true',up:'true',down:'false'}}",
			"{Name:'minecraft:brown_mushroom_block',Properties:{variant:'north_west'}}"
		);
		a(
			1586,
			"{Name:'minecraft:brown_mushroom_block',Properties:{north:'true',east:'false',south:'false',west:'false',up:'true',down:'false'}}",
			"{Name:'minecraft:brown_mushroom_block',Properties:{variant:'north'}}"
		);
		a(
			1587,
			"{Name:'minecraft:brown_mushroom_block',Properties:{north:'true',east:'true',south:'false',west:'false',up:'true',down:'false'}}",
			"{Name:'minecraft:brown_mushroom_block',Properties:{variant:'north_east'}}"
		);
		a(
			1588,
			"{Name:'minecraft:brown_mushroom_block',Properties:{north:'false',east:'false',south:'false',west:'true',up:'true',down:'false'}}",
			"{Name:'minecraft:brown_mushroom_block',Properties:{variant:'west'}}"
		);
		a(
			1589,
			"{Name:'minecraft:brown_mushroom_block',Properties:{north:'false',east:'false',south:'false',west:'false',up:'true',down:'false'}}",
			"{Name:'minecraft:brown_mushroom_block',Properties:{variant:'center'}}"
		);
		a(
			1590,
			"{Name:'minecraft:brown_mushroom_block',Properties:{north:'false',east:'true',south:'false',west:'false',up:'true',down:'false'}}",
			"{Name:'minecraft:brown_mushroom_block',Properties:{variant:'east'}}"
		);
		a(
			1591,
			"{Name:'minecraft:brown_mushroom_block',Properties:{north:'false',east:'false',south:'true',west:'true',up:'true',down:'false'}}",
			"{Name:'minecraft:brown_mushroom_block',Properties:{variant:'south_west'}}"
		);
		a(
			1592,
			"{Name:'minecraft:brown_mushroom_block',Properties:{north:'false',east:'false',south:'true',west:'false',up:'true',down:'false'}}",
			"{Name:'minecraft:brown_mushroom_block',Properties:{variant:'south'}}"
		);
		a(
			1593,
			"{Name:'minecraft:brown_mushroom_block',Properties:{north:'false',east:'true',south:'true',west:'false',up:'true',down:'false'}}",
			"{Name:'minecraft:brown_mushroom_block',Properties:{variant:'south_east'}}"
		);
		a(
			1594,
			"{Name:'minecraft:mushroom_stem',Properties:{north:'true',east:'true',south:'true',west:'true',up:'false',down:'false'}}",
			"{Name:'minecraft:brown_mushroom_block',Properties:{variant:'stem'}}"
		);
		a(1595, "{Name:'minecraft:brown_mushroom_block',Properties:{north:'false',east:'false',south:'false',west:'false',up:'false',down:'false'}}");
		a(1596, "{Name:'minecraft:brown_mushroom_block',Properties:{north:'false',east:'false',south:'false',west:'false',up:'false',down:'false'}}");
		a(1597, "{Name:'minecraft:brown_mushroom_block',Properties:{north:'false',east:'false',south:'false',west:'false',up:'false',down:'false'}}");
		a(
			1598,
			"{Name:'minecraft:brown_mushroom_block',Properties:{north:'true',east:'true',south:'true',west:'true',up:'true',down:'true'}}",
			"{Name:'minecraft:brown_mushroom_block',Properties:{variant:'all_outside'}}"
		);
		a(
			1599,
			"{Name:'minecraft:mushroom_stem',Properties:{north:'true',east:'true',south:'true',west:'true',up:'true',down:'true'}}",
			"{Name:'minecraft:brown_mushroom_block',Properties:{variant:'all_stem'}}"
		);
		a(
			1600,
			"{Name:'minecraft:red_mushroom_block',Properties:{north:'false',east:'false',south:'false',west:'false',up:'false',down:'false'}}",
			"{Name:'minecraft:red_mushroom_block',Properties:{variant:'all_inside'}}"
		);
		a(
			1601,
			"{Name:'minecraft:red_mushroom_block',Properties:{north:'true',east:'false',south:'false',west:'true',up:'true',down:'false'}}",
			"{Name:'minecraft:red_mushroom_block',Properties:{variant:'north_west'}}"
		);
		a(
			1602,
			"{Name:'minecraft:red_mushroom_block',Properties:{north:'true',east:'false',south:'false',west:'false',up:'true',down:'false'}}",
			"{Name:'minecraft:red_mushroom_block',Properties:{variant:'north'}}"
		);
		a(
			1603,
			"{Name:'minecraft:red_mushroom_block',Properties:{north:'true',east:'true',south:'false',west:'false',up:'true',down:'false'}}",
			"{Name:'minecraft:red_mushroom_block',Properties:{variant:'north_east'}}"
		);
		a(
			1604,
			"{Name:'minecraft:red_mushroom_block',Properties:{north:'false',east:'false',south:'false',west:'true',up:'true',down:'false'}}",
			"{Name:'minecraft:red_mushroom_block',Properties:{variant:'west'}}"
		);
		a(
			1605,
			"{Name:'minecraft:red_mushroom_block',Properties:{north:'false',east:'false',south:'false',west:'false',up:'true',down:'false'}}",
			"{Name:'minecraft:red_mushroom_block',Properties:{variant:'center'}}"
		);
		a(
			1606,
			"{Name:'minecraft:red_mushroom_block',Properties:{north:'false',east:'true',south:'false',west:'false',up:'true',down:'false'}}",
			"{Name:'minecraft:red_mushroom_block',Properties:{variant:'east'}}"
		);
		a(
			1607,
			"{Name:'minecraft:red_mushroom_block',Properties:{north:'false',east:'false',south:'true',west:'true',up:'true',down:'false'}}",
			"{Name:'minecraft:red_mushroom_block',Properties:{variant:'south_west'}}"
		);
		a(
			1608,
			"{Name:'minecraft:red_mushroom_block',Properties:{north:'false',east:'false',south:'true',west:'false',up:'true',down:'false'}}",
			"{Name:'minecraft:red_mushroom_block',Properties:{variant:'south'}}"
		);
		a(
			1609,
			"{Name:'minecraft:red_mushroom_block',Properties:{north:'false',east:'true',south:'true',west:'false',up:'true',down:'false'}}",
			"{Name:'minecraft:red_mushroom_block',Properties:{variant:'south_east'}}"
		);
		a(
			1610,
			"{Name:'minecraft:mushroom_stem',Properties:{north:'true',east:'true',south:'true',west:'true',up:'false',down:'false'}}",
			"{Name:'minecraft:red_mushroom_block',Properties:{variant:'stem'}}"
		);
		a(1611, "{Name:'minecraft:red_mushroom_block',Properties:{north:'false',east:'false',south:'false',west:'false',up:'false',down:'false'}}");
		a(1612, "{Name:'minecraft:red_mushroom_block',Properties:{north:'false',east:'false',south:'false',west:'false',up:'false',down:'false'}}");
		a(1613, "{Name:'minecraft:red_mushroom_block',Properties:{north:'false',east:'false',south:'false',west:'false',up:'false',down:'false'}}");
		a(
			1614,
			"{Name:'minecraft:red_mushroom_block',Properties:{north:'true',east:'true',south:'true',west:'true',up:'true',down:'true'}}",
			"{Name:'minecraft:red_mushroom_block',Properties:{variant:'all_outside'}}"
		);
		a(
			1615,
			"{Name:'minecraft:mushroom_stem',Properties:{north:'true',east:'true',south:'true',west:'true',up:'true',down:'true'}}",
			"{Name:'minecraft:red_mushroom_block',Properties:{variant:'all_stem'}}"
		);
		a(
			1616,
			"{Name:'minecraft:iron_bars',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:iron_bars',Properties:{east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			1632,
			"{Name:'minecraft:glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:glass_pane',Properties:{east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(1648, "{Name:'minecraft:melon_block'}", "{Name:'minecraft:melon_block'}");
		a(
			1664,
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'0'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'0',facing:'east'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'0',facing:'north'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'0',facing:'south'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'0',facing:'up'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'0',facing:'west'}}"
		);
		a(
			1665,
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'1'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'1',facing:'east'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'1',facing:'north'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'1',facing:'south'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'1',facing:'up'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'1',facing:'west'}}"
		);
		a(
			1666,
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'2'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'2',facing:'east'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'2',facing:'north'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'2',facing:'south'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'2',facing:'up'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'2',facing:'west'}}"
		);
		a(
			1667,
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'3'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'3',facing:'east'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'3',facing:'north'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'3',facing:'south'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'3',facing:'up'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'3',facing:'west'}}"
		);
		a(
			1668,
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'4'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'4',facing:'east'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'4',facing:'north'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'4',facing:'south'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'4',facing:'up'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'4',facing:'west'}}"
		);
		a(
			1669,
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'5'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'5',facing:'east'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'5',facing:'north'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'5',facing:'south'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'5',facing:'up'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'5',facing:'west'}}"
		);
		a(
			1670,
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'6'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'6',facing:'east'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'6',facing:'north'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'6',facing:'south'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'6',facing:'up'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'6',facing:'west'}}"
		);
		a(
			1671,
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'7'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'7',facing:'east'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'7',facing:'north'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'7',facing:'south'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'7',facing:'up'}}",
			"{Name:'minecraft:pumpkin_stem',Properties:{age:'7',facing:'west'}}"
		);
		a(
			1680,
			"{Name:'minecraft:melon_stem',Properties:{age:'0'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'0',facing:'east'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'0',facing:'north'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'0',facing:'south'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'0',facing:'up'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'0',facing:'west'}}"
		);
		a(
			1681,
			"{Name:'minecraft:melon_stem',Properties:{age:'1'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'1',facing:'east'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'1',facing:'north'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'1',facing:'south'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'1',facing:'up'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'1',facing:'west'}}"
		);
		a(
			1682,
			"{Name:'minecraft:melon_stem',Properties:{age:'2'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'2',facing:'east'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'2',facing:'north'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'2',facing:'south'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'2',facing:'up'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'2',facing:'west'}}"
		);
		a(
			1683,
			"{Name:'minecraft:melon_stem',Properties:{age:'3'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'3',facing:'east'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'3',facing:'north'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'3',facing:'south'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'3',facing:'up'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'3',facing:'west'}}"
		);
		a(
			1684,
			"{Name:'minecraft:melon_stem',Properties:{age:'4'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'4',facing:'east'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'4',facing:'north'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'4',facing:'south'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'4',facing:'up'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'4',facing:'west'}}"
		);
		a(
			1685,
			"{Name:'minecraft:melon_stem',Properties:{age:'5'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'5',facing:'east'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'5',facing:'north'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'5',facing:'south'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'5',facing:'up'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'5',facing:'west'}}"
		);
		a(
			1686,
			"{Name:'minecraft:melon_stem',Properties:{age:'6'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'6',facing:'east'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'6',facing:'north'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'6',facing:'south'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'6',facing:'up'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'6',facing:'west'}}"
		);
		a(
			1687,
			"{Name:'minecraft:melon_stem',Properties:{age:'7'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'7',facing:'east'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'7',facing:'north'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'7',facing:'south'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'7',facing:'up'}}",
			"{Name:'minecraft:melon_stem',Properties:{age:'7',facing:'west'}}"
		);
		a(
			1696,
			"{Name:'minecraft:vine',Properties:{east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'false',south:'false',up:'true',west:'false'}}"
		);
		a(
			1697,
			"{Name:'minecraft:vine',Properties:{east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'false',south:'true',up:'true',west:'false'}}"
		);
		a(
			1698,
			"{Name:'minecraft:vine',Properties:{east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'false',south:'false',up:'true',west:'true'}}"
		);
		a(
			1699,
			"{Name:'minecraft:vine',Properties:{east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'false',south:'true',up:'true',west:'true'}}"
		);
		a(
			1700,
			"{Name:'minecraft:vine',Properties:{east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'true',south:'false',up:'true',west:'false'}}"
		);
		a(
			1701,
			"{Name:'minecraft:vine',Properties:{east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'true',south:'true',up:'true',west:'false'}}"
		);
		a(
			1702,
			"{Name:'minecraft:vine',Properties:{east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'true',south:'false',up:'true',west:'true'}}"
		);
		a(
			1703,
			"{Name:'minecraft:vine',Properties:{east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'false',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			1704,
			"{Name:'minecraft:vine',Properties:{east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'false',south:'false',up:'true',west:'false'}}"
		);
		a(
			1705,
			"{Name:'minecraft:vine',Properties:{east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'false',south:'true',up:'true',west:'false'}}"
		);
		a(
			1706,
			"{Name:'minecraft:vine',Properties:{east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'false',south:'false',up:'true',west:'true'}}"
		);
		a(
			1707,
			"{Name:'minecraft:vine',Properties:{east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'false',south:'true',up:'true',west:'true'}}"
		);
		a(
			1708,
			"{Name:'minecraft:vine',Properties:{east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'true',south:'false',up:'true',west:'false'}}"
		);
		a(
			1709,
			"{Name:'minecraft:vine',Properties:{east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'true',south:'true',up:'true',west:'false'}}"
		);
		a(
			1710,
			"{Name:'minecraft:vine',Properties:{east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'true',south:'false',up:'true',west:'true'}}"
		);
		a(
			1711,
			"{Name:'minecraft:vine',Properties:{east:'true',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:vine',Properties:{east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(
			1712,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'south',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			1713,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'west',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			1714,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'north',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			1715,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'east',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			1716,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'south',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			1717,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'west',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			1718,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'north',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			1719,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'east',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			1720,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'south',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			1721,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'west',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			1722,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'north',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			1723,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'east',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			1724,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'south',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			1725,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'west',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			1726,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'north',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			1727,
			"{Name:'minecraft:oak_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:fence_gate',Properties:{facing:'east',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			1728,
			"{Name:'minecraft:brick_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}"
		);
		a(
			1729,
			"{Name:'minecraft:brick_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}"
		);
		a(
			1730,
			"{Name:'minecraft:brick_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}"
		);
		a(
			1731,
			"{Name:'minecraft:brick_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}"
		);
		a(
			1732,
			"{Name:'minecraft:brick_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'east',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'east',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'east',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'east',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}"
		);
		a(
			1733,
			"{Name:'minecraft:brick_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'west',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'west',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'west',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'west',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}"
		);
		a(
			1734,
			"{Name:'minecraft:brick_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'south',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'south',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'south',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'south',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}"
		);
		a(
			1735,
			"{Name:'minecraft:brick_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'north',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'north',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'north',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'north',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:brick_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}"
		);
		a(
			1744,
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}"
		);
		a(
			1745,
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}"
		);
		a(
			1746,
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}"
		);
		a(
			1747,
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}"
		);
		a(
			1748,
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'east',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'east',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'east',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'east',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}"
		);
		a(
			1749,
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'west',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'west',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'west',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'west',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}"
		);
		a(
			1750,
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'south',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'south',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'south',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'south',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}"
		);
		a(
			1751,
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'north',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'north',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'north',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'north',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:stone_brick_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}"
		);
		a(
			1760,
			"{Name:'minecraft:mycelium',Properties:{snowy:'false'}}",
			"{Name:'minecraft:mycelium',Properties:{snowy:'false'}}",
			"{Name:'minecraft:mycelium',Properties:{snowy:'true'}}"
		);
		a(1776, "{Name:'minecraft:lily_pad'}", "{Name:'minecraft:waterlily'}");
		a(1792, "{Name:'minecraft:nether_bricks'}", "{Name:'minecraft:nether_brick'}");
		a(
			1808,
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:nether_brick_fence',Properties:{east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			1824,
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}"
		);
		a(
			1825,
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}"
		);
		a(
			1826,
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}"
		);
		a(
			1827,
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}"
		);
		a(
			1828,
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'east',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'east',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'east',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'east',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}"
		);
		a(
			1829,
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'west',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'west',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'west',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'west',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}"
		);
		a(
			1830,
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'south',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'south',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'south',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'south',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}"
		);
		a(
			1831,
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'north',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'north',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'north',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'north',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:nether_brick_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}"
		);
		a(1840, "{Name:'minecraft:nether_wart',Properties:{age:'0'}}", "{Name:'minecraft:nether_wart',Properties:{age:'0'}}");
		a(1841, "{Name:'minecraft:nether_wart',Properties:{age:'1'}}", "{Name:'minecraft:nether_wart',Properties:{age:'1'}}");
		a(1842, "{Name:'minecraft:nether_wart',Properties:{age:'2'}}", "{Name:'minecraft:nether_wart',Properties:{age:'2'}}");
		a(1843, "{Name:'minecraft:nether_wart',Properties:{age:'3'}}", "{Name:'minecraft:nether_wart',Properties:{age:'3'}}");
		a(1856, "{Name:'minecraft:enchanting_table'}", "{Name:'minecraft:enchanting_table'}");
		a(
			1872,
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'false',has_bottle_1:'false',has_bottle_2:'false'}}",
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'false',has_bottle_1:'false',has_bottle_2:'false'}}"
		);
		a(
			1873,
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'true',has_bottle_1:'false',has_bottle_2:'false'}}",
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'true',has_bottle_1:'false',has_bottle_2:'false'}}"
		);
		a(
			1874,
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'false',has_bottle_1:'true',has_bottle_2:'false'}}",
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'false',has_bottle_1:'true',has_bottle_2:'false'}}"
		);
		a(
			1875,
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'true',has_bottle_1:'true',has_bottle_2:'false'}}",
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'true',has_bottle_1:'true',has_bottle_2:'false'}}"
		);
		a(
			1876,
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'false',has_bottle_1:'false',has_bottle_2:'true'}}",
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'false',has_bottle_1:'false',has_bottle_2:'true'}}"
		);
		a(
			1877,
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'true',has_bottle_1:'false',has_bottle_2:'true'}}",
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'true',has_bottle_1:'false',has_bottle_2:'true'}}"
		);
		a(
			1878,
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'false',has_bottle_1:'true',has_bottle_2:'true'}}",
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'false',has_bottle_1:'true',has_bottle_2:'true'}}"
		);
		a(
			1879,
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'true',has_bottle_1:'true',has_bottle_2:'true'}}",
			"{Name:'minecraft:brewing_stand',Properties:{has_bottle_0:'true',has_bottle_1:'true',has_bottle_2:'true'}}"
		);
		a(1888, "{Name:'minecraft:cauldron',Properties:{level:'0'}}", "{Name:'minecraft:cauldron',Properties:{level:'0'}}");
		a(1889, "{Name:'minecraft:cauldron',Properties:{level:'1'}}", "{Name:'minecraft:cauldron',Properties:{level:'1'}}");
		a(1890, "{Name:'minecraft:cauldron',Properties:{level:'2'}}", "{Name:'minecraft:cauldron',Properties:{level:'2'}}");
		a(1891, "{Name:'minecraft:cauldron',Properties:{level:'3'}}", "{Name:'minecraft:cauldron',Properties:{level:'3'}}");
		a(1904, "{Name:'minecraft:end_portal'}", "{Name:'minecraft:end_portal'}");
		a(
			1920,
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'false',facing:'south'}}",
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'false',facing:'south'}}"
		);
		a(
			1921,
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'false',facing:'west'}}",
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'false',facing:'west'}}"
		);
		a(
			1922,
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'false',facing:'north'}}",
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'false',facing:'north'}}"
		);
		a(
			1923,
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'false',facing:'east'}}",
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'false',facing:'east'}}"
		);
		a(
			1924,
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'true',facing:'south'}}",
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'true',facing:'south'}}"
		);
		a(
			1925,
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'true',facing:'west'}}",
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'true',facing:'west'}}"
		);
		a(
			1926,
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'true',facing:'north'}}",
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'true',facing:'north'}}"
		);
		a(
			1927,
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'true',facing:'east'}}",
			"{Name:'minecraft:end_portal_frame',Properties:{eye:'true',facing:'east'}}"
		);
		a(1936, "{Name:'minecraft:end_stone'}", "{Name:'minecraft:end_stone'}");
		a(1952, "{Name:'minecraft:dragon_egg'}", "{Name:'minecraft:dragon_egg'}");
		a(1968, "{Name:'minecraft:redstone_lamp',Properties:{lit:'false'}}", "{Name:'minecraft:redstone_lamp'}");
		a(1984, "{Name:'minecraft:redstone_lamp',Properties:{lit:'true'}}", "{Name:'minecraft:lit_redstone_lamp'}");
		a(2000, "{Name:'minecraft:oak_slab',Properties:{type:'double'}}", "{Name:'minecraft:double_wooden_slab',Properties:{variant:'oak'}}");
		a(2001, "{Name:'minecraft:spruce_slab',Properties:{type:'double'}}", "{Name:'minecraft:double_wooden_slab',Properties:{variant:'spruce'}}");
		a(2002, "{Name:'minecraft:birch_slab',Properties:{type:'double'}}", "{Name:'minecraft:double_wooden_slab',Properties:{variant:'birch'}}");
		a(2003, "{Name:'minecraft:jungle_slab',Properties:{type:'double'}}", "{Name:'minecraft:double_wooden_slab',Properties:{variant:'jungle'}}");
		a(2004, "{Name:'minecraft:acacia_slab',Properties:{type:'double'}}", "{Name:'minecraft:double_wooden_slab',Properties:{variant:'acacia'}}");
		a(2005, "{Name:'minecraft:dark_oak_slab',Properties:{type:'double'}}", "{Name:'minecraft:double_wooden_slab',Properties:{variant:'dark_oak'}}");
		a(2016, "{Name:'minecraft:oak_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:wooden_slab',Properties:{half:'bottom',variant:'oak'}}");
		a(2017, "{Name:'minecraft:spruce_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:wooden_slab',Properties:{half:'bottom',variant:'spruce'}}");
		a(2018, "{Name:'minecraft:birch_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:wooden_slab',Properties:{half:'bottom',variant:'birch'}}");
		a(2019, "{Name:'minecraft:jungle_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:wooden_slab',Properties:{half:'bottom',variant:'jungle'}}");
		a(2020, "{Name:'minecraft:acacia_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:wooden_slab',Properties:{half:'bottom',variant:'acacia'}}");
		a(2021, "{Name:'minecraft:dark_oak_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:wooden_slab',Properties:{half:'bottom',variant:'dark_oak'}}");
		a(2024, "{Name:'minecraft:oak_slab',Properties:{type:'top'}}", "{Name:'minecraft:wooden_slab',Properties:{half:'top',variant:'oak'}}");
		a(2025, "{Name:'minecraft:spruce_slab',Properties:{type:'top'}}", "{Name:'minecraft:wooden_slab',Properties:{half:'top',variant:'spruce'}}");
		a(2026, "{Name:'minecraft:birch_slab',Properties:{type:'top'}}", "{Name:'minecraft:wooden_slab',Properties:{half:'top',variant:'birch'}}");
		a(2027, "{Name:'minecraft:jungle_slab',Properties:{type:'top'}}", "{Name:'minecraft:wooden_slab',Properties:{half:'top',variant:'jungle'}}");
		a(2028, "{Name:'minecraft:acacia_slab',Properties:{type:'top'}}", "{Name:'minecraft:wooden_slab',Properties:{half:'top',variant:'acacia'}}");
		a(2029, "{Name:'minecraft:dark_oak_slab',Properties:{type:'top'}}", "{Name:'minecraft:wooden_slab',Properties:{half:'top',variant:'dark_oak'}}");
		a(2032, "{Name:'minecraft:cocoa',Properties:{age:'0',facing:'south'}}", "{Name:'minecraft:cocoa',Properties:{age:'0',facing:'south'}}");
		a(2033, "{Name:'minecraft:cocoa',Properties:{age:'0',facing:'west'}}", "{Name:'minecraft:cocoa',Properties:{age:'0',facing:'west'}}");
		a(2034, "{Name:'minecraft:cocoa',Properties:{age:'0',facing:'north'}}", "{Name:'minecraft:cocoa',Properties:{age:'0',facing:'north'}}");
		a(2035, "{Name:'minecraft:cocoa',Properties:{age:'0',facing:'east'}}", "{Name:'minecraft:cocoa',Properties:{age:'0',facing:'east'}}");
		a(2036, "{Name:'minecraft:cocoa',Properties:{age:'1',facing:'south'}}", "{Name:'minecraft:cocoa',Properties:{age:'1',facing:'south'}}");
		a(2037, "{Name:'minecraft:cocoa',Properties:{age:'1',facing:'west'}}", "{Name:'minecraft:cocoa',Properties:{age:'1',facing:'west'}}");
		a(2038, "{Name:'minecraft:cocoa',Properties:{age:'1',facing:'north'}}", "{Name:'minecraft:cocoa',Properties:{age:'1',facing:'north'}}");
		a(2039, "{Name:'minecraft:cocoa',Properties:{age:'1',facing:'east'}}", "{Name:'minecraft:cocoa',Properties:{age:'1',facing:'east'}}");
		a(2040, "{Name:'minecraft:cocoa',Properties:{age:'2',facing:'south'}}", "{Name:'minecraft:cocoa',Properties:{age:'2',facing:'south'}}");
		a(2041, "{Name:'minecraft:cocoa',Properties:{age:'2',facing:'west'}}", "{Name:'minecraft:cocoa',Properties:{age:'2',facing:'west'}}");
		a(2042, "{Name:'minecraft:cocoa',Properties:{age:'2',facing:'north'}}", "{Name:'minecraft:cocoa',Properties:{age:'2',facing:'north'}}");
		a(2043, "{Name:'minecraft:cocoa',Properties:{age:'2',facing:'east'}}", "{Name:'minecraft:cocoa',Properties:{age:'2',facing:'east'}}");
		a(
			2048,
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}"
		);
		a(
			2049,
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}"
		);
		a(
			2050,
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}"
		);
		a(
			2051,
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}"
		);
		a(
			2052,
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'east',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'east',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'east',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'east',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}"
		);
		a(
			2053,
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'west',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'west',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'west',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'west',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}"
		);
		a(
			2054,
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'south',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'south',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'south',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'south',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}"
		);
		a(
			2055,
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'north',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'north',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'north',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'north',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:sandstone_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}"
		);
		a(2064, "{Name:'minecraft:emerald_ore'}", "{Name:'minecraft:emerald_ore'}");
		a(2082, "{Name:'minecraft:ender_chest',Properties:{facing:'north'}}", "{Name:'minecraft:ender_chest',Properties:{facing:'north'}}");
		a(2083, "{Name:'minecraft:ender_chest',Properties:{facing:'south'}}", "{Name:'minecraft:ender_chest',Properties:{facing:'south'}}");
		a(2084, "{Name:'minecraft:ender_chest',Properties:{facing:'west'}}", "{Name:'minecraft:ender_chest',Properties:{facing:'west'}}");
		a(2085, "{Name:'minecraft:ender_chest',Properties:{facing:'east'}}", "{Name:'minecraft:ender_chest',Properties:{facing:'east'}}");
		a(
			2096,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'south',powered:'false'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'south',powered:'false'}}"
		);
		a(
			2097,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'west',powered:'false'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'west',powered:'false'}}"
		);
		a(
			2098,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'north',powered:'false'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'north',powered:'false'}}"
		);
		a(
			2099,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'east',powered:'false'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'east',powered:'false'}}"
		);
		a(
			2100,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'south',powered:'false'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'south',powered:'false'}}"
		);
		a(
			2101,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'west',powered:'false'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'west',powered:'false'}}"
		);
		a(
			2102,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'north',powered:'false'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'north',powered:'false'}}"
		);
		a(
			2103,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'east',powered:'false'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'east',powered:'false'}}"
		);
		a(
			2104,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'south',powered:'true'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'south',powered:'true'}}"
		);
		a(
			2105,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'west',powered:'true'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'west',powered:'true'}}"
		);
		a(
			2106,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'north',powered:'true'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'north',powered:'true'}}"
		);
		a(
			2107,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'east',powered:'true'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'false',facing:'east',powered:'true'}}"
		);
		a(
			2108,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'south',powered:'true'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'south',powered:'true'}}"
		);
		a(
			2109,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'west',powered:'true'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'west',powered:'true'}}"
		);
		a(
			2110,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'north',powered:'true'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'north',powered:'true'}}"
		);
		a(
			2111,
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'east',powered:'true'}}",
			"{Name:'minecraft:tripwire_hook',Properties:{attached:'true',facing:'east',powered:'true'}}"
		);
		a(
			2112,
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'false',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'false',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'false',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'false',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'false',powered:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'true',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'true',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'true',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'true',powered:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'false',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'false',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'false',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'false',powered:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'true',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'true',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'true',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'true',powered:'false',south:'true',west:'true'}}"
		);
		a(
			2113,
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'false',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'false',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'false',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'false',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'false',powered:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'true',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'true',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'true',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'true',powered:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'false',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'false',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'false',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'false',powered:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'true',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'true',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'true',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'true',north:'true',powered:'true',south:'true',west:'true'}}"
		);
		a(2114, "{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'false',powered:'false',south:'false',west:'false'}}");
		a(2115, "{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'false',east:'false',north:'false',powered:'true',south:'false',west:'false'}}");
		a(
			2116,
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'false',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'false',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'false',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'false',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'false',powered:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'true',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'true',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'true',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'true',powered:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'false',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'false',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'false',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'false',powered:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'true',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'true',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'true',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'true',powered:'false',south:'true',west:'true'}}"
		);
		a(
			2117,
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'false',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'false',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'false',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'false',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'false',powered:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'true',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'true',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'true',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'true',powered:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'false',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'false',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'false',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'false',powered:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'true',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'true',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'true',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'true',north:'true',powered:'true',south:'true',west:'true'}}"
		);
		a(2118, "{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'false',powered:'false',south:'false',west:'false'}}");
		a(2119, "{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'false',east:'false',north:'false',powered:'true',south:'false',west:'false'}}");
		a(
			2120,
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'false',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'false',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'false',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'false',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'false',powered:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'true',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'true',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'true',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'true',powered:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'false',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'false',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'false',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'false',powered:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'true',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'true',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'true',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'true',powered:'false',south:'true',west:'true'}}"
		);
		a(
			2121,
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'false',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'false',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'false',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'false',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'false',powered:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'true',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'true',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'true',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'true',powered:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'false',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'false',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'false',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'false',powered:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'true',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'true',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'true',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'true',north:'true',powered:'true',south:'true',west:'true'}}"
		);
		a(2122, "{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'false',powered:'false',south:'false',west:'false'}}");
		a(2123, "{Name:'minecraft:tripwire',Properties:{attached:'false',disarmed:'true',east:'false',north:'false',powered:'true',south:'false',west:'false'}}");
		a(
			2124,
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'false',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'false',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'false',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'false',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'false',powered:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'true',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'true',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'true',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'true',powered:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'false',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'false',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'false',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'false',powered:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'true',powered:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'true',powered:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'true',powered:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'true',powered:'false',south:'true',west:'true'}}"
		);
		a(
			2125,
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'false',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'false',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'false',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'false',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'false',powered:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'true',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'true',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'true',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'true',powered:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'false',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'false',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'false',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'false',powered:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'true',powered:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'true',powered:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'true',powered:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'true',north:'true',powered:'true',south:'true',west:'true'}}"
		);
		a(2126, "{Name:'minecraft:tripwire',Properties:{attached:'true',disarmed:'true',east:'false',north:'false',powered:'false',south:'false',west:'false'}}");
		a(2128, "{Name:'minecraft:emerald_block'}", "{Name:'minecraft:emerald_block'}");
		a(
			2144,
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}"
		);
		a(
			2145,
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}"
		);
		a(
			2146,
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}"
		);
		a(
			2147,
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}"
		);
		a(
			2148,
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'east',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'east',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'east',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'east',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}"
		);
		a(
			2149,
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'west',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'west',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'west',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'west',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}"
		);
		a(
			2150,
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'south',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'south',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'south',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'south',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}"
		);
		a(
			2151,
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'north',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'north',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'north',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'north',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:spruce_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}"
		);
		a(
			2160,
			"{Name:'minecraft:birch_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}"
		);
		a(
			2161,
			"{Name:'minecraft:birch_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}"
		);
		a(
			2162,
			"{Name:'minecraft:birch_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}"
		);
		a(
			2163,
			"{Name:'minecraft:birch_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}"
		);
		a(
			2164,
			"{Name:'minecraft:birch_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'east',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'east',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'east',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'east',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}"
		);
		a(
			2165,
			"{Name:'minecraft:birch_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'west',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'west',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'west',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'west',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}"
		);
		a(
			2166,
			"{Name:'minecraft:birch_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'south',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'south',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'south',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'south',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}"
		);
		a(
			2167,
			"{Name:'minecraft:birch_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'north',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'north',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'north',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'north',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:birch_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}"
		);
		a(
			2176,
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}"
		);
		a(
			2177,
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}"
		);
		a(
			2178,
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}"
		);
		a(
			2179,
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}"
		);
		a(
			2180,
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'east',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'east',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'east',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'east',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}"
		);
		a(
			2181,
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'west',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'west',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'west',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'west',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}"
		);
		a(
			2182,
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'south',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'south',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'south',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'south',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}"
		);
		a(
			2183,
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'north',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'north',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'north',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'north',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:jungle_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}"
		);
		a(
			2192,
			"{Name:'minecraft:command_block',Properties:{conditional:'false',facing:'down'}}",
			"{Name:'minecraft:command_block',Properties:{conditional:'false',facing:'down'}}"
		);
		a(
			2193,
			"{Name:'minecraft:command_block',Properties:{conditional:'false',facing:'up'}}",
			"{Name:'minecraft:command_block',Properties:{conditional:'false',facing:'up'}}"
		);
		a(
			2194,
			"{Name:'minecraft:command_block',Properties:{conditional:'false',facing:'north'}}",
			"{Name:'minecraft:command_block',Properties:{conditional:'false',facing:'north'}}"
		);
		a(
			2195,
			"{Name:'minecraft:command_block',Properties:{conditional:'false',facing:'south'}}",
			"{Name:'minecraft:command_block',Properties:{conditional:'false',facing:'south'}}"
		);
		a(
			2196,
			"{Name:'minecraft:command_block',Properties:{conditional:'false',facing:'west'}}",
			"{Name:'minecraft:command_block',Properties:{conditional:'false',facing:'west'}}"
		);
		a(
			2197,
			"{Name:'minecraft:command_block',Properties:{conditional:'false',facing:'east'}}",
			"{Name:'minecraft:command_block',Properties:{conditional:'false',facing:'east'}}"
		);
		a(
			2200,
			"{Name:'minecraft:command_block',Properties:{conditional:'true',facing:'down'}}",
			"{Name:'minecraft:command_block',Properties:{conditional:'true',facing:'down'}}"
		);
		a(
			2201,
			"{Name:'minecraft:command_block',Properties:{conditional:'true',facing:'up'}}",
			"{Name:'minecraft:command_block',Properties:{conditional:'true',facing:'up'}}"
		);
		a(
			2202,
			"{Name:'minecraft:command_block',Properties:{conditional:'true',facing:'north'}}",
			"{Name:'minecraft:command_block',Properties:{conditional:'true',facing:'north'}}"
		);
		a(
			2203,
			"{Name:'minecraft:command_block',Properties:{conditional:'true',facing:'south'}}",
			"{Name:'minecraft:command_block',Properties:{conditional:'true',facing:'south'}}"
		);
		a(
			2204,
			"{Name:'minecraft:command_block',Properties:{conditional:'true',facing:'west'}}",
			"{Name:'minecraft:command_block',Properties:{conditional:'true',facing:'west'}}"
		);
		a(
			2205,
			"{Name:'minecraft:command_block',Properties:{conditional:'true',facing:'east'}}",
			"{Name:'minecraft:command_block',Properties:{conditional:'true',facing:'east'}}"
		);
		a(2208, "{Name:'minecraft:beacon'}", "{Name:'minecraft:beacon'}");
		a(
			2224,
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'false',up:'false',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'false',up:'false',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'false',up:'true',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'false',up:'true',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'true',up:'false',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'true',up:'false',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'true',up:'true',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'true',up:'true',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'false',up:'false',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'false',up:'false',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'false',up:'true',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'false',up:'true',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'true',up:'false',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'true',up:'false',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'true',up:'true',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'true',up:'true',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'false',up:'false',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'false',up:'false',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'false',up:'true',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'false',up:'true',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'true',up:'false',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'true',up:'false',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'true',up:'true',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'true',up:'true',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'false',up:'false',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'false',up:'false',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'false',up:'true',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'false',up:'true',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'true',up:'false',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'true',up:'false',variant:'cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'true',up:'true',variant:'cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'true',up:'true',variant:'cobblestone',west:'true'}}"
		);
		a(
			2225,
			"{Name:'minecraft:mossy_cobblestone_wall',Properties:{east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'false',up:'false',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'false',up:'false',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'false',up:'true',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'false',up:'true',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'true',up:'false',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'true',up:'false',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'true',up:'true',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'false',south:'true',up:'true',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'false',up:'false',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'false',up:'false',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'false',up:'true',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'false',up:'true',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'true',up:'false',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'true',up:'false',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'true',up:'true',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'false',north:'true',south:'true',up:'true',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'false',up:'false',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'false',up:'false',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'false',up:'true',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'false',up:'true',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'true',up:'false',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'true',up:'false',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'true',up:'true',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'false',south:'true',up:'true',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'false',up:'false',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'false',up:'false',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'false',up:'true',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'false',up:'true',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'true',up:'false',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'true',up:'false',variant:'mossy_cobblestone',west:'true'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'true',up:'true',variant:'mossy_cobblestone',west:'false'}}",
			"{Name:'minecraft:cobblestone_wall',Properties:{east:'true',north:'true',south:'true',up:'true',variant:'mossy_cobblestone',west:'true'}}"
		);
		a(
			2240,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'0'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'0'}}"
		);
		a(
			2241,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'1'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'1'}}"
		);
		a(
			2242,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'2'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'2'}}"
		);
		a(
			2243,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'3'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'3'}}"
		);
		a(
			2244,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'4'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'4'}}"
		);
		a(
			2245,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'5'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'5'}}"
		);
		a(
			2246,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'6'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'6'}}"
		);
		a(
			2247,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'7'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'7'}}"
		);
		a(
			2248,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'8'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'8'}}"
		);
		a(
			2249,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'9'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'9'}}"
		);
		a(
			2250,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'10'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'10'}}"
		);
		a(
			2251,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'11'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'11'}}"
		);
		a(
			2252,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'12'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'12'}}"
		);
		a(
			2253,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'13'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'13'}}"
		);
		a(
			2254,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'14'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'14'}}"
		);
		a(
			2255,
			"{Name:'minecraft:potted_cactus'}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'acacia_sapling',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'allium',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'birch_sapling',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'blue_orchid',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'cactus',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dandelion',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dark_oak_sapling',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'dead_bush',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'empty',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'fern',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'houstonia',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'jungle_sapling',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_brown',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'mushroom_red',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oak_sapling',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'orange_tulip',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'oxeye_daisy',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'pink_tulip',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'red_tulip',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'rose',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'spruce_sapling',legacy_data:'15'}}",
			"{Name:'minecraft:flower_pot',Properties:{contents:'white_tulip',legacy_data:'15'}}"
		);
		a(2256, "{Name:'minecraft:carrots',Properties:{age:'0'}}", "{Name:'minecraft:carrots',Properties:{age:'0'}}");
		a(2257, "{Name:'minecraft:carrots',Properties:{age:'1'}}", "{Name:'minecraft:carrots',Properties:{age:'1'}}");
		a(2258, "{Name:'minecraft:carrots',Properties:{age:'2'}}", "{Name:'minecraft:carrots',Properties:{age:'2'}}");
		a(2259, "{Name:'minecraft:carrots',Properties:{age:'3'}}", "{Name:'minecraft:carrots',Properties:{age:'3'}}");
		a(2260, "{Name:'minecraft:carrots',Properties:{age:'4'}}", "{Name:'minecraft:carrots',Properties:{age:'4'}}");
		a(2261, "{Name:'minecraft:carrots',Properties:{age:'5'}}", "{Name:'minecraft:carrots',Properties:{age:'5'}}");
		a(2262, "{Name:'minecraft:carrots',Properties:{age:'6'}}", "{Name:'minecraft:carrots',Properties:{age:'6'}}");
		a(2263, "{Name:'minecraft:carrots',Properties:{age:'7'}}", "{Name:'minecraft:carrots',Properties:{age:'7'}}");
		a(2272, "{Name:'minecraft:potatoes',Properties:{age:'0'}}", "{Name:'minecraft:potatoes',Properties:{age:'0'}}");
		a(2273, "{Name:'minecraft:potatoes',Properties:{age:'1'}}", "{Name:'minecraft:potatoes',Properties:{age:'1'}}");
		a(2274, "{Name:'minecraft:potatoes',Properties:{age:'2'}}", "{Name:'minecraft:potatoes',Properties:{age:'2'}}");
		a(2275, "{Name:'minecraft:potatoes',Properties:{age:'3'}}", "{Name:'minecraft:potatoes',Properties:{age:'3'}}");
		a(2276, "{Name:'minecraft:potatoes',Properties:{age:'4'}}", "{Name:'minecraft:potatoes',Properties:{age:'4'}}");
		a(2277, "{Name:'minecraft:potatoes',Properties:{age:'5'}}", "{Name:'minecraft:potatoes',Properties:{age:'5'}}");
		a(2278, "{Name:'minecraft:potatoes',Properties:{age:'6'}}", "{Name:'minecraft:potatoes',Properties:{age:'6'}}");
		a(2279, "{Name:'minecraft:potatoes',Properties:{age:'7'}}", "{Name:'minecraft:potatoes',Properties:{age:'7'}}");
		a(
			2288,
			"{Name:'minecraft:oak_button',Properties:{face:'ceiling',facing:'north',powered:'false'}}",
			"{Name:'minecraft:wooden_button',Properties:{facing:'down',powered:'false'}}"
		);
		a(
			2289,
			"{Name:'minecraft:oak_button',Properties:{face:'wall',facing:'east',powered:'false'}}",
			"{Name:'minecraft:wooden_button',Properties:{facing:'east',powered:'false'}}"
		);
		a(
			2290,
			"{Name:'minecraft:oak_button',Properties:{face:'wall',facing:'west',powered:'false'}}",
			"{Name:'minecraft:wooden_button',Properties:{facing:'west',powered:'false'}}"
		);
		a(
			2291,
			"{Name:'minecraft:oak_button',Properties:{face:'wall',facing:'south',powered:'false'}}",
			"{Name:'minecraft:wooden_button',Properties:{facing:'south',powered:'false'}}"
		);
		a(
			2292,
			"{Name:'minecraft:oak_button',Properties:{face:'wall',facing:'north',powered:'false'}}",
			"{Name:'minecraft:wooden_button',Properties:{facing:'north',powered:'false'}}"
		);
		a(
			2293,
			"{Name:'minecraft:oak_button',Properties:{face:'floor',facing:'north',powered:'false'}}",
			"{Name:'minecraft:wooden_button',Properties:{facing:'up',powered:'false'}}"
		);
		a(
			2296,
			"{Name:'minecraft:oak_button',Properties:{face:'ceiling',facing:'north',powered:'true'}}",
			"{Name:'minecraft:wooden_button',Properties:{facing:'down',powered:'true'}}"
		);
		a(
			2297,
			"{Name:'minecraft:oak_button',Properties:{face:'wall',facing:'east',powered:'true'}}",
			"{Name:'minecraft:wooden_button',Properties:{facing:'east',powered:'true'}}"
		);
		a(
			2298,
			"{Name:'minecraft:oak_button',Properties:{face:'wall',facing:'west',powered:'true'}}",
			"{Name:'minecraft:wooden_button',Properties:{facing:'west',powered:'true'}}"
		);
		a(
			2299,
			"{Name:'minecraft:oak_button',Properties:{face:'wall',facing:'south',powered:'true'}}",
			"{Name:'minecraft:wooden_button',Properties:{facing:'south',powered:'true'}}"
		);
		a(
			2300,
			"{Name:'minecraft:oak_button',Properties:{face:'wall',facing:'north',powered:'true'}}",
			"{Name:'minecraft:wooden_button',Properties:{facing:'north',powered:'true'}}"
		);
		a(
			2301,
			"{Name:'minecraft:oak_button',Properties:{face:'floor',facing:'north',powered:'true'}}",
			"{Name:'minecraft:wooden_button',Properties:{facing:'up',powered:'true'}}"
		);
		a(2304, "{Name:'%%FILTER_ME%%',Properties:{facing:'down',nodrop:'false'}}", "{Name:'minecraft:skull',Properties:{facing:'down',nodrop:'false'}}");
		a(2305, "{Name:'%%FILTER_ME%%',Properties:{facing:'up',nodrop:'false'}}", "{Name:'minecraft:skull',Properties:{facing:'up',nodrop:'false'}}");
		a(2306, "{Name:'%%FILTER_ME%%',Properties:{facing:'north',nodrop:'false'}}", "{Name:'minecraft:skull',Properties:{facing:'north',nodrop:'false'}}");
		a(2307, "{Name:'%%FILTER_ME%%',Properties:{facing:'south',nodrop:'false'}}", "{Name:'minecraft:skull',Properties:{facing:'south',nodrop:'false'}}");
		a(2308, "{Name:'%%FILTER_ME%%',Properties:{facing:'west',nodrop:'false'}}", "{Name:'minecraft:skull',Properties:{facing:'west',nodrop:'false'}}");
		a(2309, "{Name:'%%FILTER_ME%%',Properties:{facing:'east',nodrop:'false'}}", "{Name:'minecraft:skull',Properties:{facing:'east',nodrop:'false'}}");
		a(2312, "{Name:'%%FILTER_ME%%',Properties:{facing:'down',nodrop:'true'}}", "{Name:'minecraft:skull',Properties:{facing:'down',nodrop:'true'}}");
		a(2313, "{Name:'%%FILTER_ME%%',Properties:{facing:'up',nodrop:'true'}}", "{Name:'minecraft:skull',Properties:{facing:'up',nodrop:'true'}}");
		a(2314, "{Name:'%%FILTER_ME%%',Properties:{facing:'north',nodrop:'true'}}", "{Name:'minecraft:skull',Properties:{facing:'north',nodrop:'true'}}");
		a(2315, "{Name:'%%FILTER_ME%%',Properties:{facing:'south',nodrop:'true'}}", "{Name:'minecraft:skull',Properties:{facing:'south',nodrop:'true'}}");
		a(2316, "{Name:'%%FILTER_ME%%',Properties:{facing:'west',nodrop:'true'}}", "{Name:'minecraft:skull',Properties:{facing:'west',nodrop:'true'}}");
		a(2317, "{Name:'%%FILTER_ME%%',Properties:{facing:'east',nodrop:'true'}}", "{Name:'minecraft:skull',Properties:{facing:'east',nodrop:'true'}}");
		a(2320, "{Name:'minecraft:anvil',Properties:{facing:'south'}}", "{Name:'minecraft:anvil',Properties:{damage:'0',facing:'south'}}");
		a(2321, "{Name:'minecraft:anvil',Properties:{facing:'west'}}", "{Name:'minecraft:anvil',Properties:{damage:'0',facing:'west'}}");
		a(2322, "{Name:'minecraft:anvil',Properties:{facing:'north'}}", "{Name:'minecraft:anvil',Properties:{damage:'0',facing:'north'}}");
		a(2323, "{Name:'minecraft:anvil',Properties:{facing:'east'}}", "{Name:'minecraft:anvil',Properties:{damage:'0',facing:'east'}}");
		a(2324, "{Name:'minecraft:chipped_anvil',Properties:{facing:'south'}}", "{Name:'minecraft:anvil',Properties:{damage:'1',facing:'south'}}");
		a(2325, "{Name:'minecraft:chipped_anvil',Properties:{facing:'west'}}", "{Name:'minecraft:anvil',Properties:{damage:'1',facing:'west'}}");
		a(2326, "{Name:'minecraft:chipped_anvil',Properties:{facing:'north'}}", "{Name:'minecraft:anvil',Properties:{damage:'1',facing:'north'}}");
		a(2327, "{Name:'minecraft:chipped_anvil',Properties:{facing:'east'}}", "{Name:'minecraft:anvil',Properties:{damage:'1',facing:'east'}}");
		a(2328, "{Name:'minecraft:damaged_anvil',Properties:{facing:'south'}}", "{Name:'minecraft:anvil',Properties:{damage:'2',facing:'south'}}");
		a(2329, "{Name:'minecraft:damaged_anvil',Properties:{facing:'west'}}", "{Name:'minecraft:anvil',Properties:{damage:'2',facing:'west'}}");
		a(2330, "{Name:'minecraft:damaged_anvil',Properties:{facing:'north'}}", "{Name:'minecraft:anvil',Properties:{damage:'2',facing:'north'}}");
		a(2331, "{Name:'minecraft:damaged_anvil',Properties:{facing:'east'}}", "{Name:'minecraft:anvil',Properties:{damage:'2',facing:'east'}}");
		a(2338, "{Name:'minecraft:trapped_chest',Properties:{facing:'north',type:'single'}}", "{Name:'minecraft:trapped_chest',Properties:{facing:'north'}}");
		a(2339, "{Name:'minecraft:trapped_chest',Properties:{facing:'south',type:'single'}}", "{Name:'minecraft:trapped_chest',Properties:{facing:'south'}}");
		a(2340, "{Name:'minecraft:trapped_chest',Properties:{facing:'west',type:'single'}}", "{Name:'minecraft:trapped_chest',Properties:{facing:'west'}}");
		a(2341, "{Name:'minecraft:trapped_chest',Properties:{facing:'east',type:'single'}}", "{Name:'minecraft:trapped_chest',Properties:{facing:'east'}}");
		a(2352, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'0'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'0'}}");
		a(2353, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'1'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'1'}}");
		a(2354, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'2'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'2'}}");
		a(2355, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'3'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'3'}}");
		a(2356, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'4'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'4'}}");
		a(2357, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'5'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'5'}}");
		a(2358, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'6'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'6'}}");
		a(2359, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'7'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'7'}}");
		a(2360, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'8'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'8'}}");
		a(2361, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'9'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'9'}}");
		a(
			2362, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'10'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'10'}}"
		);
		a(
			2363, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'11'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'11'}}"
		);
		a(
			2364, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'12'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'12'}}"
		);
		a(
			2365, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'13'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'13'}}"
		);
		a(
			2366, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'14'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'14'}}"
		);
		a(
			2367, "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'15'}}", "{Name:'minecraft:light_weighted_pressure_plate',Properties:{power:'15'}}"
		);
		a(2368, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'0'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'0'}}");
		a(2369, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'1'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'1'}}");
		a(2370, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'2'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'2'}}");
		a(2371, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'3'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'3'}}");
		a(2372, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'4'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'4'}}");
		a(2373, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'5'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'5'}}");
		a(2374, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'6'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'6'}}");
		a(2375, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'7'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'7'}}");
		a(2376, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'8'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'8'}}");
		a(2377, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'9'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'9'}}");
		a(
			2378, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'10'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'10'}}"
		);
		a(
			2379, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'11'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'11'}}"
		);
		a(
			2380, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'12'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'12'}}"
		);
		a(
			2381, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'13'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'13'}}"
		);
		a(
			2382, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'14'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'14'}}"
		);
		a(
			2383, "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'15'}}", "{Name:'minecraft:heavy_weighted_pressure_plate',Properties:{power:'15'}}"
		);
		a(
			2384,
			"{Name:'minecraft:comparator',Properties:{facing:'south',mode:'compare',powered:'false'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'south',mode:'compare',powered:'false'}}"
		);
		a(
			2385,
			"{Name:'minecraft:comparator',Properties:{facing:'west',mode:'compare',powered:'false'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'west',mode:'compare',powered:'false'}}"
		);
		a(
			2386,
			"{Name:'minecraft:comparator',Properties:{facing:'north',mode:'compare',powered:'false'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'north',mode:'compare',powered:'false'}}"
		);
		a(
			2387,
			"{Name:'minecraft:comparator',Properties:{facing:'east',mode:'compare',powered:'false'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'east',mode:'compare',powered:'false'}}"
		);
		a(
			2388,
			"{Name:'minecraft:comparator',Properties:{facing:'south',mode:'subtract',powered:'false'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'south',mode:'subtract',powered:'false'}}"
		);
		a(
			2389,
			"{Name:'minecraft:comparator',Properties:{facing:'west',mode:'subtract',powered:'false'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'west',mode:'subtract',powered:'false'}}"
		);
		a(
			2390,
			"{Name:'minecraft:comparator',Properties:{facing:'north',mode:'subtract',powered:'false'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'north',mode:'subtract',powered:'false'}}"
		);
		a(
			2391,
			"{Name:'minecraft:comparator',Properties:{facing:'east',mode:'subtract',powered:'false'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'east',mode:'subtract',powered:'false'}}"
		);
		a(
			2392,
			"{Name:'minecraft:comparator',Properties:{facing:'south',mode:'compare',powered:'true'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'south',mode:'compare',powered:'true'}}"
		);
		a(
			2393,
			"{Name:'minecraft:comparator',Properties:{facing:'west',mode:'compare',powered:'true'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'west',mode:'compare',powered:'true'}}"
		);
		a(
			2394,
			"{Name:'minecraft:comparator',Properties:{facing:'north',mode:'compare',powered:'true'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'north',mode:'compare',powered:'true'}}"
		);
		a(
			2395,
			"{Name:'minecraft:comparator',Properties:{facing:'east',mode:'compare',powered:'true'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'east',mode:'compare',powered:'true'}}"
		);
		a(
			2396,
			"{Name:'minecraft:comparator',Properties:{facing:'south',mode:'subtract',powered:'true'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'south',mode:'subtract',powered:'true'}}"
		);
		a(
			2397,
			"{Name:'minecraft:comparator',Properties:{facing:'west',mode:'subtract',powered:'true'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'west',mode:'subtract',powered:'true'}}"
		);
		a(
			2398,
			"{Name:'minecraft:comparator',Properties:{facing:'north',mode:'subtract',powered:'true'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'north',mode:'subtract',powered:'true'}}"
		);
		a(
			2399,
			"{Name:'minecraft:comparator',Properties:{facing:'east',mode:'subtract',powered:'true'}}",
			"{Name:'minecraft:unpowered_comparator',Properties:{facing:'east',mode:'subtract',powered:'true'}}"
		);
		a(
			2400,
			"{Name:'minecraft:comparator',Properties:{facing:'south',mode:'compare',powered:'false'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'south',mode:'compare',powered:'false'}}"
		);
		a(
			2401,
			"{Name:'minecraft:comparator',Properties:{facing:'west',mode:'compare',powered:'false'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'west',mode:'compare',powered:'false'}}"
		);
		a(
			2402,
			"{Name:'minecraft:comparator',Properties:{facing:'north',mode:'compare',powered:'false'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'north',mode:'compare',powered:'false'}}"
		);
		a(
			2403,
			"{Name:'minecraft:comparator',Properties:{facing:'east',mode:'compare',powered:'false'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'east',mode:'compare',powered:'false'}}"
		);
		a(
			2404,
			"{Name:'minecraft:comparator',Properties:{facing:'south',mode:'subtract',powered:'false'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'south',mode:'subtract',powered:'false'}}"
		);
		a(
			2405,
			"{Name:'minecraft:comparator',Properties:{facing:'west',mode:'subtract',powered:'false'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'west',mode:'subtract',powered:'false'}}"
		);
		a(
			2406,
			"{Name:'minecraft:comparator',Properties:{facing:'north',mode:'subtract',powered:'false'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'north',mode:'subtract',powered:'false'}}"
		);
		a(
			2407,
			"{Name:'minecraft:comparator',Properties:{facing:'east',mode:'subtract',powered:'false'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'east',mode:'subtract',powered:'false'}}"
		);
		a(
			2408,
			"{Name:'minecraft:comparator',Properties:{facing:'south',mode:'compare',powered:'true'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'south',mode:'compare',powered:'true'}}"
		);
		a(
			2409,
			"{Name:'minecraft:comparator',Properties:{facing:'west',mode:'compare',powered:'true'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'west',mode:'compare',powered:'true'}}"
		);
		a(
			2410,
			"{Name:'minecraft:comparator',Properties:{facing:'north',mode:'compare',powered:'true'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'north',mode:'compare',powered:'true'}}"
		);
		a(
			2411,
			"{Name:'minecraft:comparator',Properties:{facing:'east',mode:'compare',powered:'true'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'east',mode:'compare',powered:'true'}}"
		);
		a(
			2412,
			"{Name:'minecraft:comparator',Properties:{facing:'south',mode:'subtract',powered:'true'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'south',mode:'subtract',powered:'true'}}"
		);
		a(
			2413,
			"{Name:'minecraft:comparator',Properties:{facing:'west',mode:'subtract',powered:'true'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'west',mode:'subtract',powered:'true'}}"
		);
		a(
			2414,
			"{Name:'minecraft:comparator',Properties:{facing:'north',mode:'subtract',powered:'true'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'north',mode:'subtract',powered:'true'}}"
		);
		a(
			2415,
			"{Name:'minecraft:comparator',Properties:{facing:'east',mode:'subtract',powered:'true'}}",
			"{Name:'minecraft:powered_comparator',Properties:{facing:'east',mode:'subtract',powered:'true'}}"
		);
		a(2416, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'0'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'0'}}");
		a(2417, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'1'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'1'}}");
		a(2418, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'2'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'2'}}");
		a(2419, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'3'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'3'}}");
		a(2420, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'4'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'4'}}");
		a(2421, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'5'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'5'}}");
		a(2422, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'6'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'6'}}");
		a(2423, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'7'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'7'}}");
		a(2424, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'8'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'8'}}");
		a(2425, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'9'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'9'}}");
		a(2426, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'10'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'10'}}");
		a(2427, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'11'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'11'}}");
		a(2428, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'12'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'12'}}");
		a(2429, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'13'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'13'}}");
		a(2430, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'14'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'14'}}");
		a(2431, "{Name:'minecraft:daylight_detector',Properties:{inverted:'false',power:'15'}}", "{Name:'minecraft:daylight_detector',Properties:{power:'15'}}");
		a(2432, "{Name:'minecraft:redstone_block'}", "{Name:'minecraft:redstone_block'}");
		a(2448, "{Name:'minecraft:nether_quartz_ore'}", "{Name:'minecraft:quartz_ore'}");
		a(2464, "{Name:'minecraft:hopper',Properties:{enabled:'true',facing:'down'}}", "{Name:'minecraft:hopper',Properties:{enabled:'true',facing:'down'}}");
		a(2466, "{Name:'minecraft:hopper',Properties:{enabled:'true',facing:'north'}}", "{Name:'minecraft:hopper',Properties:{enabled:'true',facing:'north'}}");
		a(2467, "{Name:'minecraft:hopper',Properties:{enabled:'true',facing:'south'}}", "{Name:'minecraft:hopper',Properties:{enabled:'true',facing:'south'}}");
		a(2468, "{Name:'minecraft:hopper',Properties:{enabled:'true',facing:'west'}}", "{Name:'minecraft:hopper',Properties:{enabled:'true',facing:'west'}}");
		a(2469, "{Name:'minecraft:hopper',Properties:{enabled:'true',facing:'east'}}", "{Name:'minecraft:hopper',Properties:{enabled:'true',facing:'east'}}");
		a(2472, "{Name:'minecraft:hopper',Properties:{enabled:'false',facing:'down'}}", "{Name:'minecraft:hopper',Properties:{enabled:'false',facing:'down'}}");
		a(2474, "{Name:'minecraft:hopper',Properties:{enabled:'false',facing:'north'}}", "{Name:'minecraft:hopper',Properties:{enabled:'false',facing:'north'}}");
		a(2475, "{Name:'minecraft:hopper',Properties:{enabled:'false',facing:'south'}}", "{Name:'minecraft:hopper',Properties:{enabled:'false',facing:'south'}}");
		a(2476, "{Name:'minecraft:hopper',Properties:{enabled:'false',facing:'west'}}", "{Name:'minecraft:hopper',Properties:{enabled:'false',facing:'west'}}");
		a(2477, "{Name:'minecraft:hopper',Properties:{enabled:'false',facing:'east'}}", "{Name:'minecraft:hopper',Properties:{enabled:'false',facing:'east'}}");
		a(2480, "{Name:'minecraft:quartz_block'}", "{Name:'minecraft:quartz_block',Properties:{variant:'default'}}");
		a(2481, "{Name:'minecraft:chiseled_quartz_block'}", "{Name:'minecraft:quartz_block',Properties:{variant:'chiseled'}}");
		a(2482, "{Name:'minecraft:quartz_pillar',Properties:{axis:'y'}}", "{Name:'minecraft:quartz_block',Properties:{variant:'lines_y'}}");
		a(2483, "{Name:'minecraft:quartz_pillar',Properties:{axis:'x'}}", "{Name:'minecraft:quartz_block',Properties:{variant:'lines_x'}}");
		a(2484, "{Name:'minecraft:quartz_pillar',Properties:{axis:'z'}}", "{Name:'minecraft:quartz_block',Properties:{variant:'lines_z'}}");
		a(
			2496,
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}"
		);
		a(
			2497,
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}"
		);
		a(
			2498,
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}"
		);
		a(
			2499,
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}"
		);
		a(
			2500,
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'east',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'east',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'east',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'east',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}"
		);
		a(
			2501,
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'west',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'west',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'west',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'west',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}"
		);
		a(
			2502,
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'south',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'south',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'south',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'south',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}"
		);
		a(
			2503,
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'north',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'north',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'north',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'north',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:quartz_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}"
		);
		a(
			2512,
			"{Name:'minecraft:activator_rail',Properties:{powered:'false',shape:'north_south'}}",
			"{Name:'minecraft:activator_rail',Properties:{powered:'false',shape:'north_south'}}"
		);
		a(
			2513,
			"{Name:'minecraft:activator_rail',Properties:{powered:'false',shape:'east_west'}}",
			"{Name:'minecraft:activator_rail',Properties:{powered:'false',shape:'east_west'}}"
		);
		a(
			2514,
			"{Name:'minecraft:activator_rail',Properties:{powered:'false',shape:'ascending_east'}}",
			"{Name:'minecraft:activator_rail',Properties:{powered:'false',shape:'ascending_east'}}"
		);
		a(
			2515,
			"{Name:'minecraft:activator_rail',Properties:{powered:'false',shape:'ascending_west'}}",
			"{Name:'minecraft:activator_rail',Properties:{powered:'false',shape:'ascending_west'}}"
		);
		a(
			2516,
			"{Name:'minecraft:activator_rail',Properties:{powered:'false',shape:'ascending_north'}}",
			"{Name:'minecraft:activator_rail',Properties:{powered:'false',shape:'ascending_north'}}"
		);
		a(
			2517,
			"{Name:'minecraft:activator_rail',Properties:{powered:'false',shape:'ascending_south'}}",
			"{Name:'minecraft:activator_rail',Properties:{powered:'false',shape:'ascending_south'}}"
		);
		a(
			2520,
			"{Name:'minecraft:activator_rail',Properties:{powered:'true',shape:'north_south'}}",
			"{Name:'minecraft:activator_rail',Properties:{powered:'true',shape:'north_south'}}"
		);
		a(
			2521,
			"{Name:'minecraft:activator_rail',Properties:{powered:'true',shape:'east_west'}}",
			"{Name:'minecraft:activator_rail',Properties:{powered:'true',shape:'east_west'}}"
		);
		a(
			2522,
			"{Name:'minecraft:activator_rail',Properties:{powered:'true',shape:'ascending_east'}}",
			"{Name:'minecraft:activator_rail',Properties:{powered:'true',shape:'ascending_east'}}"
		);
		a(
			2523,
			"{Name:'minecraft:activator_rail',Properties:{powered:'true',shape:'ascending_west'}}",
			"{Name:'minecraft:activator_rail',Properties:{powered:'true',shape:'ascending_west'}}"
		);
		a(
			2524,
			"{Name:'minecraft:activator_rail',Properties:{powered:'true',shape:'ascending_north'}}",
			"{Name:'minecraft:activator_rail',Properties:{powered:'true',shape:'ascending_north'}}"
		);
		a(
			2525,
			"{Name:'minecraft:activator_rail',Properties:{powered:'true',shape:'ascending_south'}}",
			"{Name:'minecraft:activator_rail',Properties:{powered:'true',shape:'ascending_south'}}"
		);
		a(2528, "{Name:'minecraft:dropper',Properties:{facing:'down',triggered:'false'}}", "{Name:'minecraft:dropper',Properties:{facing:'down',triggered:'false'}}");
		a(2529, "{Name:'minecraft:dropper',Properties:{facing:'up',triggered:'false'}}", "{Name:'minecraft:dropper',Properties:{facing:'up',triggered:'false'}}");
		a(
			2530, "{Name:'minecraft:dropper',Properties:{facing:'north',triggered:'false'}}", "{Name:'minecraft:dropper',Properties:{facing:'north',triggered:'false'}}"
		);
		a(
			2531, "{Name:'minecraft:dropper',Properties:{facing:'south',triggered:'false'}}", "{Name:'minecraft:dropper',Properties:{facing:'south',triggered:'false'}}"
		);
		a(2532, "{Name:'minecraft:dropper',Properties:{facing:'west',triggered:'false'}}", "{Name:'minecraft:dropper',Properties:{facing:'west',triggered:'false'}}");
		a(2533, "{Name:'minecraft:dropper',Properties:{facing:'east',triggered:'false'}}", "{Name:'minecraft:dropper',Properties:{facing:'east',triggered:'false'}}");
		a(2536, "{Name:'minecraft:dropper',Properties:{facing:'down',triggered:'true'}}", "{Name:'minecraft:dropper',Properties:{facing:'down',triggered:'true'}}");
		a(2537, "{Name:'minecraft:dropper',Properties:{facing:'up',triggered:'true'}}", "{Name:'minecraft:dropper',Properties:{facing:'up',triggered:'true'}}");
		a(2538, "{Name:'minecraft:dropper',Properties:{facing:'north',triggered:'true'}}", "{Name:'minecraft:dropper',Properties:{facing:'north',triggered:'true'}}");
		a(2539, "{Name:'minecraft:dropper',Properties:{facing:'south',triggered:'true'}}", "{Name:'minecraft:dropper',Properties:{facing:'south',triggered:'true'}}");
		a(2540, "{Name:'minecraft:dropper',Properties:{facing:'west',triggered:'true'}}", "{Name:'minecraft:dropper',Properties:{facing:'west',triggered:'true'}}");
		a(2541, "{Name:'minecraft:dropper',Properties:{facing:'east',triggered:'true'}}", "{Name:'minecraft:dropper',Properties:{facing:'east',triggered:'true'}}");
		a(2544, "{Name:'minecraft:white_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'white'}}");
		a(2545, "{Name:'minecraft:orange_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'orange'}}");
		a(2546, "{Name:'minecraft:magenta_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'magenta'}}");
		a(2547, "{Name:'minecraft:light_blue_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'light_blue'}}");
		a(2548, "{Name:'minecraft:yellow_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'yellow'}}");
		a(2549, "{Name:'minecraft:lime_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'lime'}}");
		a(2550, "{Name:'minecraft:pink_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'pink'}}");
		a(2551, "{Name:'minecraft:gray_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'gray'}}");
		a(2552, "{Name:'minecraft:light_gray_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'silver'}}");
		a(2553, "{Name:'minecraft:cyan_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'cyan'}}");
		a(2554, "{Name:'minecraft:purple_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'purple'}}");
		a(2555, "{Name:'minecraft:blue_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'blue'}}");
		a(2556, "{Name:'minecraft:brown_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'brown'}}");
		a(2557, "{Name:'minecraft:green_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'green'}}");
		a(2558, "{Name:'minecraft:red_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'red'}}");
		a(2559, "{Name:'minecraft:black_terracotta'}", "{Name:'minecraft:stained_hardened_clay',Properties:{color:'black'}}");
		a(
			2560,
			"{Name:'minecraft:white_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'white',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2561,
			"{Name:'minecraft:orange_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'orange',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2562,
			"{Name:'minecraft:magenta_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'magenta',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2563,
			"{Name:'minecraft:light_blue_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'light_blue',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2564,
			"{Name:'minecraft:yellow_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'yellow',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2565,
			"{Name:'minecraft:lime_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'lime',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2566,
			"{Name:'minecraft:pink_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'pink',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2567,
			"{Name:'minecraft:gray_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'gray',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2568,
			"{Name:'minecraft:light_gray_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'silver',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2569,
			"{Name:'minecraft:cyan_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'cyan',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2570,
			"{Name:'minecraft:purple_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'purple',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2571,
			"{Name:'minecraft:blue_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'blue',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2572,
			"{Name:'minecraft:brown_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'brown',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2573,
			"{Name:'minecraft:green_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'green',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2574,
			"{Name:'minecraft:red_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'red',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2575,
			"{Name:'minecraft:black_stained_glass_pane',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:stained_glass_pane',Properties:{color:'black',east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			2576,
			"{Name:'minecraft:acacia_leaves',Properties:{check_decay:'false',decayable:'true'}}",
			"{Name:'minecraft:leaves2',Properties:{check_decay:'false',decayable:'true',variant:'acacia'}}"
		);
		a(
			2577,
			"{Name:'minecraft:dark_oak_leaves',Properties:{check_decay:'false',decayable:'true'}}",
			"{Name:'minecraft:leaves2',Properties:{check_decay:'false',decayable:'true',variant:'dark_oak'}}"
		);
		a(
			2580,
			"{Name:'minecraft:acacia_leaves',Properties:{check_decay:'false',decayable:'false'}}",
			"{Name:'minecraft:leaves2',Properties:{check_decay:'false',decayable:'false',variant:'acacia'}}"
		);
		a(
			2581,
			"{Name:'minecraft:dark_oak_leaves',Properties:{check_decay:'false',decayable:'false'}}",
			"{Name:'minecraft:leaves2',Properties:{check_decay:'false',decayable:'false',variant:'dark_oak'}}"
		);
		a(
			2584,
			"{Name:'minecraft:acacia_leaves',Properties:{check_decay:'true',decayable:'true'}}",
			"{Name:'minecraft:leaves2',Properties:{check_decay:'true',decayable:'true',variant:'acacia'}}"
		);
		a(
			2585,
			"{Name:'minecraft:dark_oak_leaves',Properties:{check_decay:'true',decayable:'true'}}",
			"{Name:'minecraft:leaves2',Properties:{check_decay:'true',decayable:'true',variant:'dark_oak'}}"
		);
		a(
			2588,
			"{Name:'minecraft:acacia_leaves',Properties:{check_decay:'true',decayable:'false'}}",
			"{Name:'minecraft:leaves2',Properties:{check_decay:'true',decayable:'false',variant:'acacia'}}"
		);
		a(
			2589,
			"{Name:'minecraft:dark_oak_leaves',Properties:{check_decay:'true',decayable:'false'}}",
			"{Name:'minecraft:leaves2',Properties:{check_decay:'true',decayable:'false',variant:'dark_oak'}}"
		);
		a(2592, "{Name:'minecraft:acacia_log',Properties:{axis:'y'}}", "{Name:'minecraft:log2',Properties:{axis:'y',variant:'acacia'}}");
		a(2593, "{Name:'minecraft:dark_oak_log',Properties:{axis:'y'}}", "{Name:'minecraft:log2',Properties:{axis:'y',variant:'dark_oak'}}");
		a(2596, "{Name:'minecraft:acacia_log',Properties:{axis:'x'}}", "{Name:'minecraft:log2',Properties:{axis:'x',variant:'acacia'}}");
		a(2597, "{Name:'minecraft:dark_oak_log',Properties:{axis:'x'}}", "{Name:'minecraft:log2',Properties:{axis:'x',variant:'dark_oak'}}");
		a(2600, "{Name:'minecraft:acacia_log',Properties:{axis:'z'}}", "{Name:'minecraft:log2',Properties:{axis:'z',variant:'acacia'}}");
		a(2601, "{Name:'minecraft:dark_oak_log',Properties:{axis:'z'}}", "{Name:'minecraft:log2',Properties:{axis:'z',variant:'dark_oak'}}");
		a(2604, "{Name:'minecraft:acacia_bark'}", "{Name:'minecraft:log2',Properties:{axis:'none',variant:'acacia'}}");
		a(2605, "{Name:'minecraft:dark_oak_bark'}", "{Name:'minecraft:log2',Properties:{axis:'none',variant:'dark_oak'}}");
		a(
			2608,
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}"
		);
		a(
			2609,
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}"
		);
		a(
			2610,
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}"
		);
		a(
			2611,
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}"
		);
		a(
			2612,
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'east',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'east',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'east',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'east',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}"
		);
		a(
			2613,
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'west',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'west',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'west',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'west',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}"
		);
		a(
			2614,
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'south',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'south',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'south',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'south',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}"
		);
		a(
			2615,
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'north',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'north',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'north',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'north',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:acacia_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}"
		);
		a(
			2624,
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}"
		);
		a(
			2625,
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}"
		);
		a(
			2626,
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}"
		);
		a(
			2627,
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}"
		);
		a(
			2628,
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'east',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'east',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'east',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'east',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}"
		);
		a(
			2629,
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'west',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'west',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'west',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'west',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}"
		);
		a(
			2630,
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'south',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'south',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'south',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'south',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}"
		);
		a(
			2631,
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'north',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'north',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'north',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'north',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:dark_oak_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}"
		);
		a(2640, "{Name:'minecraft:slime_block'}", "{Name:'minecraft:slime'}");
		a(2656, "{Name:'minecraft:barrier'}", "{Name:'minecraft:barrier'}");
		a(
			2672,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'north',half:'bottom',open:'false'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'north',half:'bottom',open:'false'}}"
		);
		a(
			2673,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'south',half:'bottom',open:'false'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'south',half:'bottom',open:'false'}}"
		);
		a(
			2674,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'west',half:'bottom',open:'false'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'west',half:'bottom',open:'false'}}"
		);
		a(
			2675,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'east',half:'bottom',open:'false'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'east',half:'bottom',open:'false'}}"
		);
		a(
			2676,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'north',half:'bottom',open:'true'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'north',half:'bottom',open:'true'}}"
		);
		a(
			2677,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'south',half:'bottom',open:'true'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'south',half:'bottom',open:'true'}}"
		);
		a(
			2678,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'west',half:'bottom',open:'true'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'west',half:'bottom',open:'true'}}"
		);
		a(
			2679,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'east',half:'bottom',open:'true'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'east',half:'bottom',open:'true'}}"
		);
		a(
			2680,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'north',half:'top',open:'false'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'north',half:'top',open:'false'}}"
		);
		a(
			2681,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'south',half:'top',open:'false'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'south',half:'top',open:'false'}}"
		);
		a(
			2682,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'west',half:'top',open:'false'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'west',half:'top',open:'false'}}"
		);
		a(
			2683,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'east',half:'top',open:'false'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'east',half:'top',open:'false'}}"
		);
		a(
			2684,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'north',half:'top',open:'true'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'north',half:'top',open:'true'}}"
		);
		a(
			2685,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'south',half:'top',open:'true'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'south',half:'top',open:'true'}}"
		);
		a(
			2686,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'west',half:'top',open:'true'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'west',half:'top',open:'true'}}"
		);
		a(
			2687,
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'east',half:'top',open:'true'}}",
			"{Name:'minecraft:iron_trapdoor',Properties:{facing:'east',half:'top',open:'true'}}"
		);
		a(2688, "{Name:'minecraft:prismarine'}", "{Name:'minecraft:prismarine',Properties:{variant:'prismarine'}}");
		a(2689, "{Name:'minecraft:prismarine_bricks'}", "{Name:'minecraft:prismarine',Properties:{variant:'prismarine_bricks'}}");
		a(2690, "{Name:'minecraft:dark_prismarine'}", "{Name:'minecraft:prismarine',Properties:{variant:'dark_prismarine'}}");
		a(2704, "{Name:'minecraft:sea_lantern'}", "{Name:'minecraft:sea_lantern'}");
		a(2720, "{Name:'minecraft:hay_block',Properties:{axis:'y'}}", "{Name:'minecraft:hay_block',Properties:{axis:'y'}}");
		a(2724, "{Name:'minecraft:hay_block',Properties:{axis:'x'}}", "{Name:'minecraft:hay_block',Properties:{axis:'x'}}");
		a(2728, "{Name:'minecraft:hay_block',Properties:{axis:'z'}}", "{Name:'minecraft:hay_block',Properties:{axis:'z'}}");
		a(2736, "{Name:'minecraft:white_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'white'}}");
		a(2737, "{Name:'minecraft:orange_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'orange'}}");
		a(2738, "{Name:'minecraft:magenta_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'magenta'}}");
		a(2739, "{Name:'minecraft:light_blue_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'light_blue'}}");
		a(2740, "{Name:'minecraft:yellow_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'yellow'}}");
		a(2741, "{Name:'minecraft:lime_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'lime'}}");
		a(2742, "{Name:'minecraft:pink_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'pink'}}");
		a(2743, "{Name:'minecraft:gray_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'gray'}}");
		a(2744, "{Name:'minecraft:light_gray_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'silver'}}");
		a(2745, "{Name:'minecraft:cyan_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'cyan'}}");
		a(2746, "{Name:'minecraft:purple_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'purple'}}");
		a(2747, "{Name:'minecraft:blue_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'blue'}}");
		a(2748, "{Name:'minecraft:brown_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'brown'}}");
		a(2749, "{Name:'minecraft:green_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'green'}}");
		a(2750, "{Name:'minecraft:red_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'red'}}");
		a(2751, "{Name:'minecraft:black_carpet'}", "{Name:'minecraft:carpet',Properties:{color:'black'}}");
		a(2752, "{Name:'minecraft:terracotta'}", "{Name:'minecraft:hardened_clay'}");
		a(2768, "{Name:'minecraft:coal_block'}", "{Name:'minecraft:coal_block'}");
		a(2784, "{Name:'minecraft:packed_ice'}", "{Name:'minecraft:packed_ice'}");
		a(
			2800,
			"{Name:'minecraft:sunflower',Properties:{half:'lower'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'east',half:'lower',variant:'sunflower'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'north',half:'lower',variant:'sunflower'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'south',half:'lower',variant:'sunflower'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'west',half:'lower',variant:'sunflower'}}"
		);
		a(
			2801,
			"{Name:'minecraft:lilac',Properties:{half:'lower'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'east',half:'lower',variant:'syringa'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'north',half:'lower',variant:'syringa'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'south',half:'lower',variant:'syringa'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'west',half:'lower',variant:'syringa'}}"
		);
		a(
			2802,
			"{Name:'minecraft:tall_grass',Properties:{half:'lower'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'east',half:'lower',variant:'double_grass'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'north',half:'lower',variant:'double_grass'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'south',half:'lower',variant:'double_grass'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'west',half:'lower',variant:'double_grass'}}"
		);
		a(
			2803,
			"{Name:'minecraft:large_fern',Properties:{half:'lower'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'east',half:'lower',variant:'double_fern'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'north',half:'lower',variant:'double_fern'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'south',half:'lower',variant:'double_fern'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'west',half:'lower',variant:'double_fern'}}"
		);
		a(
			2804,
			"{Name:'minecraft:rose_bush',Properties:{half:'lower'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'east',half:'lower',variant:'double_rose'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'north',half:'lower',variant:'double_rose'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'south',half:'lower',variant:'double_rose'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'west',half:'lower',variant:'double_rose'}}"
		);
		a(
			2805,
			"{Name:'minecraft:peony',Properties:{half:'lower'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'east',half:'lower',variant:'paeonia'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'north',half:'lower',variant:'paeonia'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'south',half:'lower',variant:'paeonia'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'west',half:'lower',variant:'paeonia'}}"
		);
		a(
			2808,
			"{Name:'minecraft:peony',Properties:{half:'upper'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'south',half:'upper',variant:'double_fern'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'south',half:'upper',variant:'double_grass'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'south',half:'upper',variant:'double_rose'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'south',half:'upper',variant:'paeonia'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'south',half:'upper',variant:'sunflower'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'south',half:'upper',variant:'syringa'}}"
		);
		a(
			2809,
			"{Name:'minecraft:peony',Properties:{half:'upper'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'west',half:'upper',variant:'double_fern'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'west',half:'upper',variant:'double_grass'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'west',half:'upper',variant:'double_rose'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'west',half:'upper',variant:'paeonia'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'west',half:'upper',variant:'sunflower'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'west',half:'upper',variant:'syringa'}}"
		);
		a(
			2810,
			"{Name:'minecraft:peony',Properties:{half:'upper'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'north',half:'upper',variant:'double_fern'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'north',half:'upper',variant:'double_grass'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'north',half:'upper',variant:'double_rose'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'north',half:'upper',variant:'paeonia'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'north',half:'upper',variant:'sunflower'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'north',half:'upper',variant:'syringa'}}"
		);
		a(
			2811,
			"{Name:'minecraft:peony',Properties:{half:'upper'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'east',half:'upper',variant:'double_fern'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'east',half:'upper',variant:'double_grass'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'east',half:'upper',variant:'double_rose'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'east',half:'upper',variant:'paeonia'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'east',half:'upper',variant:'sunflower'}}",
			"{Name:'minecraft:double_plant',Properties:{facing:'east',half:'upper',variant:'syringa'}}"
		);
		a(2816, "{Name:'minecraft:white_banner',Properties:{rotation:'0'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'0'}}");
		a(2817, "{Name:'minecraft:white_banner',Properties:{rotation:'1'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'1'}}");
		a(2818, "{Name:'minecraft:white_banner',Properties:{rotation:'2'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'2'}}");
		a(2819, "{Name:'minecraft:white_banner',Properties:{rotation:'3'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'3'}}");
		a(2820, "{Name:'minecraft:white_banner',Properties:{rotation:'4'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'4'}}");
		a(2821, "{Name:'minecraft:white_banner',Properties:{rotation:'5'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'5'}}");
		a(2822, "{Name:'minecraft:white_banner',Properties:{rotation:'6'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'6'}}");
		a(2823, "{Name:'minecraft:white_banner',Properties:{rotation:'7'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'7'}}");
		a(2824, "{Name:'minecraft:white_banner',Properties:{rotation:'8'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'8'}}");
		a(2825, "{Name:'minecraft:white_banner',Properties:{rotation:'9'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'9'}}");
		a(2826, "{Name:'minecraft:white_banner',Properties:{rotation:'10'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'10'}}");
		a(2827, "{Name:'minecraft:white_banner',Properties:{rotation:'11'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'11'}}");
		a(2828, "{Name:'minecraft:white_banner',Properties:{rotation:'12'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'12'}}");
		a(2829, "{Name:'minecraft:white_banner',Properties:{rotation:'13'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'13'}}");
		a(2830, "{Name:'minecraft:white_banner',Properties:{rotation:'14'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'14'}}");
		a(2831, "{Name:'minecraft:white_banner',Properties:{rotation:'15'}}", "{Name:'minecraft:standing_banner',Properties:{rotation:'15'}}");
		a(2834, "{Name:'minecraft:white_wall_banner',Properties:{facing:'north'}}", "{Name:'minecraft:wall_banner',Properties:{facing:'north'}}");
		a(2835, "{Name:'minecraft:white_wall_banner',Properties:{facing:'south'}}", "{Name:'minecraft:wall_banner',Properties:{facing:'south'}}");
		a(2836, "{Name:'minecraft:white_wall_banner',Properties:{facing:'west'}}", "{Name:'minecraft:wall_banner',Properties:{facing:'west'}}");
		a(2837, "{Name:'minecraft:white_wall_banner',Properties:{facing:'east'}}", "{Name:'minecraft:wall_banner',Properties:{facing:'east'}}");
		a(2848, "{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'0'}}", "{Name:'minecraft:daylight_detector_inverted',Properties:{power:'0'}}");
		a(2849, "{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'1'}}", "{Name:'minecraft:daylight_detector_inverted',Properties:{power:'1'}}");
		a(2850, "{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'2'}}", "{Name:'minecraft:daylight_detector_inverted',Properties:{power:'2'}}");
		a(2851, "{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'3'}}", "{Name:'minecraft:daylight_detector_inverted',Properties:{power:'3'}}");
		a(2852, "{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'4'}}", "{Name:'minecraft:daylight_detector_inverted',Properties:{power:'4'}}");
		a(2853, "{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'5'}}", "{Name:'minecraft:daylight_detector_inverted',Properties:{power:'5'}}");
		a(2854, "{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'6'}}", "{Name:'minecraft:daylight_detector_inverted',Properties:{power:'6'}}");
		a(2855, "{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'7'}}", "{Name:'minecraft:daylight_detector_inverted',Properties:{power:'7'}}");
		a(2856, "{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'8'}}", "{Name:'minecraft:daylight_detector_inverted',Properties:{power:'8'}}");
		a(2857, "{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'9'}}", "{Name:'minecraft:daylight_detector_inverted',Properties:{power:'9'}}");
		a(
			2858,
			"{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'10'}}",
			"{Name:'minecraft:daylight_detector_inverted',Properties:{power:'10'}}"
		);
		a(
			2859,
			"{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'11'}}",
			"{Name:'minecraft:daylight_detector_inverted',Properties:{power:'11'}}"
		);
		a(
			2860,
			"{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'12'}}",
			"{Name:'minecraft:daylight_detector_inverted',Properties:{power:'12'}}"
		);
		a(
			2861,
			"{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'13'}}",
			"{Name:'minecraft:daylight_detector_inverted',Properties:{power:'13'}}"
		);
		a(
			2862,
			"{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'14'}}",
			"{Name:'minecraft:daylight_detector_inverted',Properties:{power:'14'}}"
		);
		a(
			2863,
			"{Name:'minecraft:daylight_detector',Properties:{inverted:'true',power:'15'}}",
			"{Name:'minecraft:daylight_detector_inverted',Properties:{power:'15'}}"
		);
		a(2864, "{Name:'minecraft:red_sandstone'}", "{Name:'minecraft:red_sandstone',Properties:{type:'red_sandstone'}}");
		a(2865, "{Name:'minecraft:chiseled_red_sandstone'}", "{Name:'minecraft:red_sandstone',Properties:{type:'chiseled_red_sandstone'}}");
		a(2866, "{Name:'minecraft:cut_red_sandstone'}", "{Name:'minecraft:red_sandstone',Properties:{type:'smooth_red_sandstone'}}");
		a(
			2880,
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}"
		);
		a(
			2881,
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}"
		);
		a(
			2882,
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}"
		);
		a(
			2883,
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}"
		);
		a(
			2884,
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'east',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'east',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'east',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'east',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}"
		);
		a(
			2885,
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'west',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'west',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'west',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'west',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}"
		);
		a(
			2886,
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'south',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'south',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'south',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'south',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}"
		);
		a(
			2887,
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'north',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'north',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'north',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'north',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:red_sandstone_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}"
		);
		a(
			2896,
			"{Name:'minecraft:red_sandstone_slab',Properties:{type:'double'}}",
			"{Name:'minecraft:double_stone_slab2',Properties:{seamless:'false',variant:'red_sandstone'}}"
		);
		a(2904, "{Name:'minecraft:smooth_red_sandstone'}", "{Name:'minecraft:double_stone_slab2',Properties:{seamless:'true',variant:'red_sandstone'}}");
		a(
			2912,
			"{Name:'minecraft:red_sandstone_slab',Properties:{type:'bottom'}}",
			"{Name:'minecraft:stone_slab2',Properties:{half:'bottom',variant:'red_sandstone'}}"
		);
		a(2920, "{Name:'minecraft:red_sandstone_slab',Properties:{type:'top'}}", "{Name:'minecraft:stone_slab2',Properties:{half:'top',variant:'red_sandstone'}}");
		a(
			2928,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'south',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2929,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'west',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2930,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'north',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2931,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'east',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2932,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'south',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2933,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'west',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2934,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'north',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2935,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'east',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2936,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'south',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2937,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'west',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2938,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'north',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2939,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'east',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2940,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'south',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2941,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'west',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2942,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'north',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2943,
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_fence_gate',Properties:{facing:'east',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2944,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'south',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2945,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'west',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2946,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'north',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2947,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'east',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2948,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'south',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2949,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'west',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2950,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'north',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2951,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'east',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2952,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'south',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2953,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'west',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2954,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'north',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2955,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'east',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2956,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'south',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2957,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'west',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2958,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'north',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2959,
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_fence_gate',Properties:{facing:'east',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2960,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'south',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2961,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'west',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2962,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'north',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2963,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'east',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2964,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'south',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2965,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'west',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2966,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'north',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2967,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'east',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2968,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'south',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2969,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'west',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2970,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'north',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2971,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'east',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2972,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'south',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2973,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'west',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2974,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'north',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2975,
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_fence_gate',Properties:{facing:'east',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2976,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'south',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2977,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'west',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2978,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'north',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2979,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'east',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2980,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'south',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2981,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'west',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2982,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'north',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2983,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'east',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2984,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'south',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2985,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'west',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2986,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'north',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2987,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'east',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			2988,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'south',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2989,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'west',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2990,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'north',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2991,
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_fence_gate',Properties:{facing:'east',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			2992,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'south',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2993,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'west',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2994,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'north',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2995,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'east',in_wall:'true',open:'false',powered:'false'}}"
		);
		a(
			2996,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'south',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2997,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'west',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2998,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'north',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			2999,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'east',in_wall:'true',open:'true',powered:'false'}}"
		);
		a(
			3000,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'south',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'south',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			3001,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'west',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'west',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			3002,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'north',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'north',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			3003,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'east',in_wall:'false',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'east',in_wall:'true',open:'false',powered:'true'}}"
		);
		a(
			3004,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'south',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'south',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			3005,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'west',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'west',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			3006,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'north',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'north',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			3007,
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'east',in_wall:'false',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_fence_gate',Properties:{facing:'east',in_wall:'true',open:'true',powered:'true'}}"
		);
		a(
			3008,
			"{Name:'minecraft:spruce_fence',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:spruce_fence',Properties:{east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			3024,
			"{Name:'minecraft:birch_fence',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:birch_fence',Properties:{east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			3040,
			"{Name:'minecraft:jungle_fence',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:jungle_fence',Properties:{east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			3056,
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:dark_oak_fence',Properties:{east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			3072,
			"{Name:'minecraft:acacia_fence',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'false',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'false',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'false',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'false',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'false',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'false',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'false',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'false',north:'true',south:'true',west:'true'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'true',north:'false',south:'false',west:'false'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'true',north:'false',south:'false',west:'true'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'true',north:'false',south:'true',west:'false'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'true',north:'false',south:'true',west:'true'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'true',north:'true',south:'false',west:'false'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'true',north:'true',south:'false',west:'true'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'true',north:'true',south:'true',west:'false'}}",
			"{Name:'minecraft:acacia_fence',Properties:{east:'true',north:'true',south:'true',west:'true'}}"
		);
		a(
			3088,
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3089,
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3090,
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3091,
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3092,
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3093,
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3094,
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3095,
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3096,
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}"
		);
		a(
			3097,
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}"
		);
		a(
			3098,
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}"
		);
		a(
			3099,
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:spruce_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3104,
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3105,
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3106,
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3107,
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3108,
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3109,
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3110,
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3111,
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3112,
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}"
		);
		a(
			3113,
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}"
		);
		a(
			3114,
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}"
		);
		a(
			3115,
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:birch_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3120,
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3121,
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3122,
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3123,
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3124,
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3125,
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3126,
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3127,
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3128,
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}"
		);
		a(
			3129,
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}"
		);
		a(
			3130,
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}"
		);
		a(
			3131,
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:jungle_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3136,
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3137,
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3138,
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3139,
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3140,
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3141,
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3142,
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3143,
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3144,
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}"
		);
		a(
			3145,
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}"
		);
		a(
			3146,
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}"
		);
		a(
			3147,
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:acacia_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3152,
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3153,
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3154,
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3155,
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}"
		);
		a(
			3156,
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3157,
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3158,
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3159,
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}"
		);
		a(
			3160,
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}"
		);
		a(
			3161,
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}"
		);
		a(
			3162,
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}"
		);
		a(
			3163,
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}",
			"{Name:'minecraft:dark_oak_door',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}"
		);
		a(3168, "{Name:'minecraft:end_rod',Properties:{facing:'down'}}", "{Name:'minecraft:end_rod',Properties:{facing:'down'}}");
		a(3169, "{Name:'minecraft:end_rod',Properties:{facing:'up'}}", "{Name:'minecraft:end_rod',Properties:{facing:'up'}}");
		a(3170, "{Name:'minecraft:end_rod',Properties:{facing:'north'}}", "{Name:'minecraft:end_rod',Properties:{facing:'north'}}");
		a(3171, "{Name:'minecraft:end_rod',Properties:{facing:'south'}}", "{Name:'minecraft:end_rod',Properties:{facing:'south'}}");
		a(3172, "{Name:'minecraft:end_rod',Properties:{facing:'west'}}", "{Name:'minecraft:end_rod',Properties:{facing:'west'}}");
		a(3173, "{Name:'minecraft:end_rod',Properties:{facing:'east'}}", "{Name:'minecraft:end_rod',Properties:{facing:'east'}}");
		a(
			3184,
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'false',east:'true',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'false',north:'true',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'false',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'false',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'false',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'false',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'false',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'false',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'false',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'false',south:'true',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'true',south:'false',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'true',south:'false',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'true',south:'false',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'true',south:'false',up:'true',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'true',south:'true',up:'false',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'true',south:'true',up:'false',west:'true'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'true',south:'true',up:'true',west:'false'}}",
			"{Name:'minecraft:chorus_plant',Properties:{down:'true',east:'true',north:'true',south:'true',up:'true',west:'true'}}"
		);
		a(3200, "{Name:'minecraft:chorus_flower',Properties:{age:'0'}}", "{Name:'minecraft:chorus_flower',Properties:{age:'0'}}");
		a(3201, "{Name:'minecraft:chorus_flower',Properties:{age:'1'}}", "{Name:'minecraft:chorus_flower',Properties:{age:'1'}}");
		a(3202, "{Name:'minecraft:chorus_flower',Properties:{age:'2'}}", "{Name:'minecraft:chorus_flower',Properties:{age:'2'}}");
		a(3203, "{Name:'minecraft:chorus_flower',Properties:{age:'3'}}", "{Name:'minecraft:chorus_flower',Properties:{age:'3'}}");
		a(3204, "{Name:'minecraft:chorus_flower',Properties:{age:'4'}}", "{Name:'minecraft:chorus_flower',Properties:{age:'4'}}");
		a(3205, "{Name:'minecraft:chorus_flower',Properties:{age:'5'}}", "{Name:'minecraft:chorus_flower',Properties:{age:'5'}}");
		a(3216, "{Name:'minecraft:purpur_block'}", "{Name:'minecraft:purpur_block'}");
		a(3232, "{Name:'minecraft:purpur_pillar',Properties:{axis:'y'}}", "{Name:'minecraft:purpur_pillar',Properties:{axis:'y'}}");
		a(3236, "{Name:'minecraft:purpur_pillar',Properties:{axis:'x'}}", "{Name:'minecraft:purpur_pillar',Properties:{axis:'x'}}");
		a(3240, "{Name:'minecraft:purpur_pillar',Properties:{axis:'z'}}", "{Name:'minecraft:purpur_pillar',Properties:{axis:'z'}}");
		a(
			3248,
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'east',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'east',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'east',half:'bottom',shape:'straight'}}"
		);
		a(
			3249,
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'west',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'west',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'west',half:'bottom',shape:'straight'}}"
		);
		a(
			3250,
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'south',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'south',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'south',half:'bottom',shape:'straight'}}"
		);
		a(
			3251,
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'north',half:'bottom',shape:'inner_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'north',half:'bottom',shape:'outer_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'north',half:'bottom',shape:'straight'}}"
		);
		a(
			3252,
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'east',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'east',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'east',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'east',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'east',half:'top',shape:'straight'}}"
		);
		a(
			3253,
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'west',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'west',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'west',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'west',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'west',half:'top',shape:'straight'}}"
		);
		a(
			3254,
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'south',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'south',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'south',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'south',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'south',half:'top',shape:'straight'}}"
		);
		a(
			3255,
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'north',half:'top',shape:'inner_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'north',half:'top',shape:'inner_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'north',half:'top',shape:'outer_left'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'north',half:'top',shape:'outer_right'}}",
			"{Name:'minecraft:purpur_stairs',Properties:{facing:'north',half:'top',shape:'straight'}}"
		);
		a(3264, "{Name:'minecraft:purpur_slab',Properties:{type:'double'}}", "{Name:'minecraft:purpur_double_slab',Properties:{variant:'default'}}");
		a(3280, "{Name:'minecraft:purpur_slab',Properties:{type:'bottom'}}", "{Name:'minecraft:purpur_slab',Properties:{half:'bottom',variant:'default'}}");
		a(3288, "{Name:'minecraft:purpur_slab',Properties:{type:'top'}}", "{Name:'minecraft:purpur_slab',Properties:{half:'top',variant:'default'}}");
		a(3296, "{Name:'minecraft:end_stone_bricks'}", "{Name:'minecraft:end_bricks'}");
		a(3312, "{Name:'minecraft:beetroots',Properties:{age:'0'}}", "{Name:'minecraft:beetroots',Properties:{age:'0'}}");
		a(3313, "{Name:'minecraft:beetroots',Properties:{age:'1'}}", "{Name:'minecraft:beetroots',Properties:{age:'1'}}");
		a(3314, "{Name:'minecraft:beetroots',Properties:{age:'2'}}", "{Name:'minecraft:beetroots',Properties:{age:'2'}}");
		a(3315, "{Name:'minecraft:beetroots',Properties:{age:'3'}}", "{Name:'minecraft:beetroots',Properties:{age:'3'}}");
		a(3328, "{Name:'minecraft:grass_path'}", "{Name:'minecraft:grass_path'}");
		a(3344, "{Name:'minecraft:end_gateway'}", "{Name:'minecraft:end_gateway'}");
		a(
			3360,
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'false',facing:'down'}}",
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'false',facing:'down'}}"
		);
		a(
			3361,
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'false',facing:'up'}}",
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'false',facing:'up'}}"
		);
		a(
			3362,
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'false',facing:'north'}}",
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'false',facing:'north'}}"
		);
		a(
			3363,
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'false',facing:'south'}}",
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'false',facing:'south'}}"
		);
		a(
			3364,
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'false',facing:'west'}}",
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'false',facing:'west'}}"
		);
		a(
			3365,
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'false',facing:'east'}}",
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'false',facing:'east'}}"
		);
		a(
			3368,
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'true',facing:'down'}}",
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'true',facing:'down'}}"
		);
		a(
			3369,
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'true',facing:'up'}}",
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'true',facing:'up'}}"
		);
		a(
			3370,
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'true',facing:'north'}}",
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'true',facing:'north'}}"
		);
		a(
			3371,
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'true',facing:'south'}}",
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'true',facing:'south'}}"
		);
		a(
			3372,
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'true',facing:'west'}}",
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'true',facing:'west'}}"
		);
		a(
			3373,
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'true',facing:'east'}}",
			"{Name:'minecraft:repeating_command_block',Properties:{conditional:'true',facing:'east'}}"
		);
		a(
			3376,
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'false',facing:'down'}}",
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'false',facing:'down'}}"
		);
		a(
			3377,
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'false',facing:'up'}}",
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'false',facing:'up'}}"
		);
		a(
			3378,
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'false',facing:'north'}}",
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'false',facing:'north'}}"
		);
		a(
			3379,
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'false',facing:'south'}}",
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'false',facing:'south'}}"
		);
		a(
			3380,
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'false',facing:'west'}}",
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'false',facing:'west'}}"
		);
		a(
			3381,
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'false',facing:'east'}}",
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'false',facing:'east'}}"
		);
		a(
			3384,
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'true',facing:'down'}}",
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'true',facing:'down'}}"
		);
		a(
			3385,
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'true',facing:'up'}}",
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'true',facing:'up'}}"
		);
		a(
			3386,
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'true',facing:'north'}}",
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'true',facing:'north'}}"
		);
		a(
			3387,
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'true',facing:'south'}}",
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'true',facing:'south'}}"
		);
		a(
			3388,
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'true',facing:'west'}}",
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'true',facing:'west'}}"
		);
		a(
			3389,
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'true',facing:'east'}}",
			"{Name:'minecraft:chain_command_block',Properties:{conditional:'true',facing:'east'}}"
		);
		a(3392, "{Name:'minecraft:frosted_ice',Properties:{age:'0'}}", "{Name:'minecraft:frosted_ice',Properties:{age:'0'}}");
		a(3393, "{Name:'minecraft:frosted_ice',Properties:{age:'1'}}", "{Name:'minecraft:frosted_ice',Properties:{age:'1'}}");
		a(3394, "{Name:'minecraft:frosted_ice',Properties:{age:'2'}}", "{Name:'minecraft:frosted_ice',Properties:{age:'2'}}");
		a(3395, "{Name:'minecraft:frosted_ice',Properties:{age:'3'}}", "{Name:'minecraft:frosted_ice',Properties:{age:'3'}}");
		a(3408, "{Name:'minecraft:magma_block'}", "{Name:'minecraft:magma'}");
		a(3424, "{Name:'minecraft:nether_wart_block'}", "{Name:'minecraft:nether_wart_block'}");
		a(3440, "{Name:'minecraft:red_nether_bricks'}", "{Name:'minecraft:red_nether_brick'}");
		a(3456, "{Name:'minecraft:bone_block',Properties:{axis:'y'}}", "{Name:'minecraft:bone_block',Properties:{axis:'y'}}");
		a(3460, "{Name:'minecraft:bone_block',Properties:{axis:'x'}}", "{Name:'minecraft:bone_block',Properties:{axis:'x'}}");
		a(3464, "{Name:'minecraft:bone_block',Properties:{axis:'z'}}", "{Name:'minecraft:bone_block',Properties:{axis:'z'}}");
		a(3472, "{Name:'minecraft:structure_void'}", "{Name:'minecraft:structure_void'}");
		a(3488, "{Name:'minecraft:observer',Properties:{facing:'down',powered:'false'}}", "{Name:'minecraft:observer',Properties:{facing:'down',powered:'false'}}");
		a(3489, "{Name:'minecraft:observer',Properties:{facing:'up',powered:'false'}}", "{Name:'minecraft:observer',Properties:{facing:'up',powered:'false'}}");
		a(3490, "{Name:'minecraft:observer',Properties:{facing:'north',powered:'false'}}", "{Name:'minecraft:observer',Properties:{facing:'north',powered:'false'}}");
		a(3491, "{Name:'minecraft:observer',Properties:{facing:'south',powered:'false'}}", "{Name:'minecraft:observer',Properties:{facing:'south',powered:'false'}}");
		a(3492, "{Name:'minecraft:observer',Properties:{facing:'west',powered:'false'}}", "{Name:'minecraft:observer',Properties:{facing:'west',powered:'false'}}");
		a(3493, "{Name:'minecraft:observer',Properties:{facing:'east',powered:'false'}}", "{Name:'minecraft:observer',Properties:{facing:'east',powered:'false'}}");
		a(3496, "{Name:'minecraft:observer',Properties:{facing:'down',powered:'true'}}", "{Name:'minecraft:observer',Properties:{facing:'down',powered:'true'}}");
		a(3497, "{Name:'minecraft:observer',Properties:{facing:'up',powered:'true'}}", "{Name:'minecraft:observer',Properties:{facing:'up',powered:'true'}}");
		a(3498, "{Name:'minecraft:observer',Properties:{facing:'north',powered:'true'}}", "{Name:'minecraft:observer',Properties:{facing:'north',powered:'true'}}");
		a(3499, "{Name:'minecraft:observer',Properties:{facing:'south',powered:'true'}}", "{Name:'minecraft:observer',Properties:{facing:'south',powered:'true'}}");
		a(3500, "{Name:'minecraft:observer',Properties:{facing:'west',powered:'true'}}", "{Name:'minecraft:observer',Properties:{facing:'west',powered:'true'}}");
		a(3501, "{Name:'minecraft:observer',Properties:{facing:'east',powered:'true'}}", "{Name:'minecraft:observer',Properties:{facing:'east',powered:'true'}}");
		a(3504, "{Name:'minecraft:white_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:white_shulker_box',Properties:{facing:'down'}}");
		a(3505, "{Name:'minecraft:white_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:white_shulker_box',Properties:{facing:'up'}}");
		a(3506, "{Name:'minecraft:white_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:white_shulker_box',Properties:{facing:'north'}}");
		a(3507, "{Name:'minecraft:white_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:white_shulker_box',Properties:{facing:'south'}}");
		a(3508, "{Name:'minecraft:white_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:white_shulker_box',Properties:{facing:'west'}}");
		a(3509, "{Name:'minecraft:white_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:white_shulker_box',Properties:{facing:'east'}}");
		a(3520, "{Name:'minecraft:orange_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:orange_shulker_box',Properties:{facing:'down'}}");
		a(3521, "{Name:'minecraft:orange_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:orange_shulker_box',Properties:{facing:'up'}}");
		a(3522, "{Name:'minecraft:orange_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:orange_shulker_box',Properties:{facing:'north'}}");
		a(3523, "{Name:'minecraft:orange_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:orange_shulker_box',Properties:{facing:'south'}}");
		a(3524, "{Name:'minecraft:orange_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:orange_shulker_box',Properties:{facing:'west'}}");
		a(3525, "{Name:'minecraft:orange_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:orange_shulker_box',Properties:{facing:'east'}}");
		a(3536, "{Name:'minecraft:magenta_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:magenta_shulker_box',Properties:{facing:'down'}}");
		a(3537, "{Name:'minecraft:magenta_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:magenta_shulker_box',Properties:{facing:'up'}}");
		a(3538, "{Name:'minecraft:magenta_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:magenta_shulker_box',Properties:{facing:'north'}}");
		a(3539, "{Name:'minecraft:magenta_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:magenta_shulker_box',Properties:{facing:'south'}}");
		a(3540, "{Name:'minecraft:magenta_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:magenta_shulker_box',Properties:{facing:'west'}}");
		a(3541, "{Name:'minecraft:magenta_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:magenta_shulker_box',Properties:{facing:'east'}}");
		a(3552, "{Name:'minecraft:light_blue_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:light_blue_shulker_box',Properties:{facing:'down'}}");
		a(3553, "{Name:'minecraft:light_blue_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:light_blue_shulker_box',Properties:{facing:'up'}}");
		a(3554, "{Name:'minecraft:light_blue_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:light_blue_shulker_box',Properties:{facing:'north'}}");
		a(3555, "{Name:'minecraft:light_blue_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:light_blue_shulker_box',Properties:{facing:'south'}}");
		a(3556, "{Name:'minecraft:light_blue_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:light_blue_shulker_box',Properties:{facing:'west'}}");
		a(3557, "{Name:'minecraft:light_blue_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:light_blue_shulker_box',Properties:{facing:'east'}}");
		a(3568, "{Name:'minecraft:yellow_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:yellow_shulker_box',Properties:{facing:'down'}}");
		a(3569, "{Name:'minecraft:yellow_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:yellow_shulker_box',Properties:{facing:'up'}}");
		a(3570, "{Name:'minecraft:yellow_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:yellow_shulker_box',Properties:{facing:'north'}}");
		a(3571, "{Name:'minecraft:yellow_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:yellow_shulker_box',Properties:{facing:'south'}}");
		a(3572, "{Name:'minecraft:yellow_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:yellow_shulker_box',Properties:{facing:'west'}}");
		a(3573, "{Name:'minecraft:yellow_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:yellow_shulker_box',Properties:{facing:'east'}}");
		a(3584, "{Name:'minecraft:lime_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:lime_shulker_box',Properties:{facing:'down'}}");
		a(3585, "{Name:'minecraft:lime_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:lime_shulker_box',Properties:{facing:'up'}}");
		a(3586, "{Name:'minecraft:lime_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:lime_shulker_box',Properties:{facing:'north'}}");
		a(3587, "{Name:'minecraft:lime_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:lime_shulker_box',Properties:{facing:'south'}}");
		a(3588, "{Name:'minecraft:lime_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:lime_shulker_box',Properties:{facing:'west'}}");
		a(3589, "{Name:'minecraft:lime_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:lime_shulker_box',Properties:{facing:'east'}}");
		a(3600, "{Name:'minecraft:pink_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:pink_shulker_box',Properties:{facing:'down'}}");
		a(3601, "{Name:'minecraft:pink_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:pink_shulker_box',Properties:{facing:'up'}}");
		a(3602, "{Name:'minecraft:pink_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:pink_shulker_box',Properties:{facing:'north'}}");
		a(3603, "{Name:'minecraft:pink_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:pink_shulker_box',Properties:{facing:'south'}}");
		a(3604, "{Name:'minecraft:pink_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:pink_shulker_box',Properties:{facing:'west'}}");
		a(3605, "{Name:'minecraft:pink_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:pink_shulker_box',Properties:{facing:'east'}}");
		a(3616, "{Name:'minecraft:gray_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:gray_shulker_box',Properties:{facing:'down'}}");
		a(3617, "{Name:'minecraft:gray_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:gray_shulker_box',Properties:{facing:'up'}}");
		a(3618, "{Name:'minecraft:gray_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:gray_shulker_box',Properties:{facing:'north'}}");
		a(3619, "{Name:'minecraft:gray_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:gray_shulker_box',Properties:{facing:'south'}}");
		a(3620, "{Name:'minecraft:gray_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:gray_shulker_box',Properties:{facing:'west'}}");
		a(3621, "{Name:'minecraft:gray_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:gray_shulker_box',Properties:{facing:'east'}}");
		a(3632, "{Name:'minecraft:light_gray_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:silver_shulker_box',Properties:{facing:'down'}}");
		a(3633, "{Name:'minecraft:light_gray_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:silver_shulker_box',Properties:{facing:'up'}}");
		a(3634, "{Name:'minecraft:light_gray_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:silver_shulker_box',Properties:{facing:'north'}}");
		a(3635, "{Name:'minecraft:light_gray_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:silver_shulker_box',Properties:{facing:'south'}}");
		a(3636, "{Name:'minecraft:light_gray_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:silver_shulker_box',Properties:{facing:'west'}}");
		a(3637, "{Name:'minecraft:light_gray_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:silver_shulker_box',Properties:{facing:'east'}}");
		a(3648, "{Name:'minecraft:cyan_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:cyan_shulker_box',Properties:{facing:'down'}}");
		a(3649, "{Name:'minecraft:cyan_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:cyan_shulker_box',Properties:{facing:'up'}}");
		a(3650, "{Name:'minecraft:cyan_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:cyan_shulker_box',Properties:{facing:'north'}}");
		a(3651, "{Name:'minecraft:cyan_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:cyan_shulker_box',Properties:{facing:'south'}}");
		a(3652, "{Name:'minecraft:cyan_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:cyan_shulker_box',Properties:{facing:'west'}}");
		a(3653, "{Name:'minecraft:cyan_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:cyan_shulker_box',Properties:{facing:'east'}}");
		a(3664, "{Name:'minecraft:purple_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:purple_shulker_box',Properties:{facing:'down'}}");
		a(3665, "{Name:'minecraft:purple_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:purple_shulker_box',Properties:{facing:'up'}}");
		a(3666, "{Name:'minecraft:purple_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:purple_shulker_box',Properties:{facing:'north'}}");
		a(3667, "{Name:'minecraft:purple_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:purple_shulker_box',Properties:{facing:'south'}}");
		a(3668, "{Name:'minecraft:purple_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:purple_shulker_box',Properties:{facing:'west'}}");
		a(3669, "{Name:'minecraft:purple_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:purple_shulker_box',Properties:{facing:'east'}}");
		a(3680, "{Name:'minecraft:blue_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:blue_shulker_box',Properties:{facing:'down'}}");
		a(3681, "{Name:'minecraft:blue_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:blue_shulker_box',Properties:{facing:'up'}}");
		a(3682, "{Name:'minecraft:blue_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:blue_shulker_box',Properties:{facing:'north'}}");
		a(3683, "{Name:'minecraft:blue_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:blue_shulker_box',Properties:{facing:'south'}}");
		a(3684, "{Name:'minecraft:blue_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:blue_shulker_box',Properties:{facing:'west'}}");
		a(3685, "{Name:'minecraft:blue_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:blue_shulker_box',Properties:{facing:'east'}}");
		a(3696, "{Name:'minecraft:brown_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:brown_shulker_box',Properties:{facing:'down'}}");
		a(3697, "{Name:'minecraft:brown_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:brown_shulker_box',Properties:{facing:'up'}}");
		a(3698, "{Name:'minecraft:brown_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:brown_shulker_box',Properties:{facing:'north'}}");
		a(3699, "{Name:'minecraft:brown_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:brown_shulker_box',Properties:{facing:'south'}}");
		a(3700, "{Name:'minecraft:brown_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:brown_shulker_box',Properties:{facing:'west'}}");
		a(3701, "{Name:'minecraft:brown_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:brown_shulker_box',Properties:{facing:'east'}}");
		a(3712, "{Name:'minecraft:green_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:green_shulker_box',Properties:{facing:'down'}}");
		a(3713, "{Name:'minecraft:green_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:green_shulker_box',Properties:{facing:'up'}}");
		a(3714, "{Name:'minecraft:green_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:green_shulker_box',Properties:{facing:'north'}}");
		a(3715, "{Name:'minecraft:green_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:green_shulker_box',Properties:{facing:'south'}}");
		a(3716, "{Name:'minecraft:green_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:green_shulker_box',Properties:{facing:'west'}}");
		a(3717, "{Name:'minecraft:green_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:green_shulker_box',Properties:{facing:'east'}}");
		a(3728, "{Name:'minecraft:red_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:red_shulker_box',Properties:{facing:'down'}}");
		a(3729, "{Name:'minecraft:red_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:red_shulker_box',Properties:{facing:'up'}}");
		a(3730, "{Name:'minecraft:red_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:red_shulker_box',Properties:{facing:'north'}}");
		a(3731, "{Name:'minecraft:red_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:red_shulker_box',Properties:{facing:'south'}}");
		a(3732, "{Name:'minecraft:red_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:red_shulker_box',Properties:{facing:'west'}}");
		a(3733, "{Name:'minecraft:red_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:red_shulker_box',Properties:{facing:'east'}}");
		a(3744, "{Name:'minecraft:black_shulker_box',Properties:{facing:'down'}}", "{Name:'minecraft:black_shulker_box',Properties:{facing:'down'}}");
		a(3745, "{Name:'minecraft:black_shulker_box',Properties:{facing:'up'}}", "{Name:'minecraft:black_shulker_box',Properties:{facing:'up'}}");
		a(3746, "{Name:'minecraft:black_shulker_box',Properties:{facing:'north'}}", "{Name:'minecraft:black_shulker_box',Properties:{facing:'north'}}");
		a(3747, "{Name:'minecraft:black_shulker_box',Properties:{facing:'south'}}", "{Name:'minecraft:black_shulker_box',Properties:{facing:'south'}}");
		a(3748, "{Name:'minecraft:black_shulker_box',Properties:{facing:'west'}}", "{Name:'minecraft:black_shulker_box',Properties:{facing:'west'}}");
		a(3749, "{Name:'minecraft:black_shulker_box',Properties:{facing:'east'}}", "{Name:'minecraft:black_shulker_box',Properties:{facing:'east'}}");
		a(3760, "{Name:'minecraft:white_glazed_terracotta',Properties:{facing:'south'}}", "{Name:'minecraft:white_glazed_terracotta',Properties:{facing:'south'}}");
		a(3761, "{Name:'minecraft:white_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:white_glazed_terracotta',Properties:{facing:'west'}}");
		a(3762, "{Name:'minecraft:white_glazed_terracotta',Properties:{facing:'north'}}", "{Name:'minecraft:white_glazed_terracotta',Properties:{facing:'north'}}");
		a(3763, "{Name:'minecraft:white_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:white_glazed_terracotta',Properties:{facing:'east'}}");
		a(3776, "{Name:'minecraft:orange_glazed_terracotta',Properties:{facing:'south'}}", "{Name:'minecraft:orange_glazed_terracotta',Properties:{facing:'south'}}");
		a(3777, "{Name:'minecraft:orange_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:orange_glazed_terracotta',Properties:{facing:'west'}}");
		a(3778, "{Name:'minecraft:orange_glazed_terracotta',Properties:{facing:'north'}}", "{Name:'minecraft:orange_glazed_terracotta',Properties:{facing:'north'}}");
		a(3779, "{Name:'minecraft:orange_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:orange_glazed_terracotta',Properties:{facing:'east'}}");
		a(
			3792, "{Name:'minecraft:magenta_glazed_terracotta',Properties:{facing:'south'}}", "{Name:'minecraft:magenta_glazed_terracotta',Properties:{facing:'south'}}"
		);
		a(3793, "{Name:'minecraft:magenta_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:magenta_glazed_terracotta',Properties:{facing:'west'}}");
		a(
			3794, "{Name:'minecraft:magenta_glazed_terracotta',Properties:{facing:'north'}}", "{Name:'minecraft:magenta_glazed_terracotta',Properties:{facing:'north'}}"
		);
		a(3795, "{Name:'minecraft:magenta_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:magenta_glazed_terracotta',Properties:{facing:'east'}}");
		a(
			3808,
			"{Name:'minecraft:light_blue_glazed_terracotta',Properties:{facing:'south'}}",
			"{Name:'minecraft:light_blue_glazed_terracotta',Properties:{facing:'south'}}"
		);
		a(
			3809,
			"{Name:'minecraft:light_blue_glazed_terracotta',Properties:{facing:'west'}}",
			"{Name:'minecraft:light_blue_glazed_terracotta',Properties:{facing:'west'}}"
		);
		a(
			3810,
			"{Name:'minecraft:light_blue_glazed_terracotta',Properties:{facing:'north'}}",
			"{Name:'minecraft:light_blue_glazed_terracotta',Properties:{facing:'north'}}"
		);
		a(
			3811,
			"{Name:'minecraft:light_blue_glazed_terracotta',Properties:{facing:'east'}}",
			"{Name:'minecraft:light_blue_glazed_terracotta',Properties:{facing:'east'}}"
		);
		a(3824, "{Name:'minecraft:yellow_glazed_terracotta',Properties:{facing:'south'}}", "{Name:'minecraft:yellow_glazed_terracotta',Properties:{facing:'south'}}");
		a(3825, "{Name:'minecraft:yellow_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:yellow_glazed_terracotta',Properties:{facing:'west'}}");
		a(3826, "{Name:'minecraft:yellow_glazed_terracotta',Properties:{facing:'north'}}", "{Name:'minecraft:yellow_glazed_terracotta',Properties:{facing:'north'}}");
		a(3827, "{Name:'minecraft:yellow_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:yellow_glazed_terracotta',Properties:{facing:'east'}}");
		a(3840, "{Name:'minecraft:lime_glazed_terracotta',Properties:{facing:'south'}}", "{Name:'minecraft:lime_glazed_terracotta',Properties:{facing:'south'}}");
		a(3841, "{Name:'minecraft:lime_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:lime_glazed_terracotta',Properties:{facing:'west'}}");
		a(3842, "{Name:'minecraft:lime_glazed_terracotta',Properties:{facing:'north'}}", "{Name:'minecraft:lime_glazed_terracotta',Properties:{facing:'north'}}");
		a(3843, "{Name:'minecraft:lime_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:lime_glazed_terracotta',Properties:{facing:'east'}}");
		a(3856, "{Name:'minecraft:pink_glazed_terracotta',Properties:{facing:'south'}}", "{Name:'minecraft:pink_glazed_terracotta',Properties:{facing:'south'}}");
		a(3857, "{Name:'minecraft:pink_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:pink_glazed_terracotta',Properties:{facing:'west'}}");
		a(3858, "{Name:'minecraft:pink_glazed_terracotta',Properties:{facing:'north'}}", "{Name:'minecraft:pink_glazed_terracotta',Properties:{facing:'north'}}");
		a(3859, "{Name:'minecraft:pink_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:pink_glazed_terracotta',Properties:{facing:'east'}}");
		a(3872, "{Name:'minecraft:gray_glazed_terracotta',Properties:{facing:'south'}}", "{Name:'minecraft:gray_glazed_terracotta',Properties:{facing:'south'}}");
		a(3873, "{Name:'minecraft:gray_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:gray_glazed_terracotta',Properties:{facing:'west'}}");
		a(3874, "{Name:'minecraft:gray_glazed_terracotta',Properties:{facing:'north'}}", "{Name:'minecraft:gray_glazed_terracotta',Properties:{facing:'north'}}");
		a(3875, "{Name:'minecraft:gray_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:gray_glazed_terracotta',Properties:{facing:'east'}}");
		a(
			3888,
			"{Name:'minecraft:light_gray_glazed_terracotta',Properties:{facing:'south'}}",
			"{Name:'minecraft:silver_glazed_terracotta',Properties:{facing:'south'}}"
		);
		a(
			3889, "{Name:'minecraft:light_gray_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:silver_glazed_terracotta',Properties:{facing:'west'}}"
		);
		a(
			3890,
			"{Name:'minecraft:light_gray_glazed_terracotta',Properties:{facing:'north'}}",
			"{Name:'minecraft:silver_glazed_terracotta',Properties:{facing:'north'}}"
		);
		a(
			3891, "{Name:'minecraft:light_gray_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:silver_glazed_terracotta',Properties:{facing:'east'}}"
		);
		a(3904, "{Name:'minecraft:cyan_glazed_terracotta',Properties:{facing:'south'}}", "{Name:'minecraft:cyan_glazed_terracotta',Properties:{facing:'south'}}");
		a(3905, "{Name:'minecraft:cyan_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:cyan_glazed_terracotta',Properties:{facing:'west'}}");
		a(3906, "{Name:'minecraft:cyan_glazed_terracotta',Properties:{facing:'north'}}", "{Name:'minecraft:cyan_glazed_terracotta',Properties:{facing:'north'}}");
		a(3907, "{Name:'minecraft:cyan_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:cyan_glazed_terracotta',Properties:{facing:'east'}}");
		a(3920, "{Name:'minecraft:purple_glazed_terracotta',Properties:{facing:'south'}}", "{Name:'minecraft:purple_glazed_terracotta',Properties:{facing:'south'}}");
		a(3921, "{Name:'minecraft:purple_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:purple_glazed_terracotta',Properties:{facing:'west'}}");
		a(3922, "{Name:'minecraft:purple_glazed_terracotta',Properties:{facing:'north'}}", "{Name:'minecraft:purple_glazed_terracotta',Properties:{facing:'north'}}");
		a(3923, "{Name:'minecraft:purple_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:purple_glazed_terracotta',Properties:{facing:'east'}}");
		a(3936, "{Name:'minecraft:blue_glazed_terracotta',Properties:{facing:'south'}}", "{Name:'minecraft:blue_glazed_terracotta',Properties:{facing:'south'}}");
		a(3937, "{Name:'minecraft:blue_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:blue_glazed_terracotta',Properties:{facing:'west'}}");
		a(3938, "{Name:'minecraft:blue_glazed_terracotta',Properties:{facing:'north'}}", "{Name:'minecraft:blue_glazed_terracotta',Properties:{facing:'north'}}");
		a(3939, "{Name:'minecraft:blue_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:blue_glazed_terracotta',Properties:{facing:'east'}}");
		a(3952, "{Name:'minecraft:brown_glazed_terracotta',Properties:{facing:'south'}}", "{Name:'minecraft:brown_glazed_terracotta',Properties:{facing:'south'}}");
		a(3953, "{Name:'minecraft:brown_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:brown_glazed_terracotta',Properties:{facing:'west'}}");
		a(3954, "{Name:'minecraft:brown_glazed_terracotta',Properties:{facing:'north'}}", "{Name:'minecraft:brown_glazed_terracotta',Properties:{facing:'north'}}");
		a(3955, "{Name:'minecraft:brown_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:brown_glazed_terracotta',Properties:{facing:'east'}}");
		a(3968, "{Name:'minecraft:green_glazed_terracotta',Properties:{facing:'south'}}", "{Name:'minecraft:green_glazed_terracotta',Properties:{facing:'south'}}");
		a(3969, "{Name:'minecraft:green_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:green_glazed_terracotta',Properties:{facing:'west'}}");
		a(3970, "{Name:'minecraft:green_glazed_terracotta',Properties:{facing:'north'}}", "{Name:'minecraft:green_glazed_terracotta',Properties:{facing:'north'}}");
		a(3971, "{Name:'minecraft:green_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:green_glazed_terracotta',Properties:{facing:'east'}}");
		a(3984, "{Name:'minecraft:red_glazed_terracotta',Properties:{facing:'south'}}", "{Name:'minecraft:red_glazed_terracotta',Properties:{facing:'south'}}");
		a(3985, "{Name:'minecraft:red_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:red_glazed_terracotta',Properties:{facing:'west'}}");
		a(3986, "{Name:'minecraft:red_glazed_terracotta',Properties:{facing:'north'}}", "{Name:'minecraft:red_glazed_terracotta',Properties:{facing:'north'}}");
		a(3987, "{Name:'minecraft:red_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:red_glazed_terracotta',Properties:{facing:'east'}}");
		a(4000, "{Name:'minecraft:black_glazed_terracotta',Properties:{facing:'south'}}", "{Name:'minecraft:black_glazed_terracotta',Properties:{facing:'south'}}");
		a(4001, "{Name:'minecraft:black_glazed_terracotta',Properties:{facing:'west'}}", "{Name:'minecraft:black_glazed_terracotta',Properties:{facing:'west'}}");
		a(4002, "{Name:'minecraft:black_glazed_terracotta',Properties:{facing:'north'}}", "{Name:'minecraft:black_glazed_terracotta',Properties:{facing:'north'}}");
		a(4003, "{Name:'minecraft:black_glazed_terracotta',Properties:{facing:'east'}}", "{Name:'minecraft:black_glazed_terracotta',Properties:{facing:'east'}}");
		a(4016, "{Name:'minecraft:white_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'white'}}");
		a(4017, "{Name:'minecraft:orange_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'orange'}}");
		a(4018, "{Name:'minecraft:magenta_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'magenta'}}");
		a(4019, "{Name:'minecraft:light_blue_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'light_blue'}}");
		a(4020, "{Name:'minecraft:yellow_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'yellow'}}");
		a(4021, "{Name:'minecraft:lime_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'lime'}}");
		a(4022, "{Name:'minecraft:pink_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'pink'}}");
		a(4023, "{Name:'minecraft:gray_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'gray'}}");
		a(4024, "{Name:'minecraft:light_gray_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'silver'}}");
		a(4025, "{Name:'minecraft:cyan_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'cyan'}}");
		a(4026, "{Name:'minecraft:purple_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'purple'}}");
		a(4027, "{Name:'minecraft:blue_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'blue'}}");
		a(4028, "{Name:'minecraft:brown_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'brown'}}");
		a(4029, "{Name:'minecraft:green_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'green'}}");
		a(4030, "{Name:'minecraft:red_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'red'}}");
		a(4031, "{Name:'minecraft:black_concrete'}", "{Name:'minecraft:concrete',Properties:{color:'black'}}");
		a(4032, "{Name:'minecraft:white_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'white'}}");
		a(4033, "{Name:'minecraft:orange_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'orange'}}");
		a(4034, "{Name:'minecraft:magenta_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'magenta'}}");
		a(4035, "{Name:'minecraft:light_blue_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'light_blue'}}");
		a(4036, "{Name:'minecraft:yellow_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'yellow'}}");
		a(4037, "{Name:'minecraft:lime_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'lime'}}");
		a(4038, "{Name:'minecraft:pink_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'pink'}}");
		a(4039, "{Name:'minecraft:gray_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'gray'}}");
		a(4040, "{Name:'minecraft:light_gray_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'silver'}}");
		a(4041, "{Name:'minecraft:cyan_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'cyan'}}");
		a(4042, "{Name:'minecraft:purple_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'purple'}}");
		a(4043, "{Name:'minecraft:blue_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'blue'}}");
		a(4044, "{Name:'minecraft:brown_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'brown'}}");
		a(4045, "{Name:'minecraft:green_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'green'}}");
		a(4046, "{Name:'minecraft:red_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'red'}}");
		a(4047, "{Name:'minecraft:black_concrete_powder'}", "{Name:'minecraft:concrete_powder',Properties:{color:'black'}}");
		a(4080, "{Name:'minecraft:structure_block',Properties:{mode:'save'}}", "{Name:'minecraft:structure_block',Properties:{mode:'save'}}");
		a(4081, "{Name:'minecraft:structure_block',Properties:{mode:'load'}}", "{Name:'minecraft:structure_block',Properties:{mode:'load'}}");
		a(4082, "{Name:'minecraft:structure_block',Properties:{mode:'corner'}}", "{Name:'minecraft:structure_block',Properties:{mode:'corner'}}");
		a(4083, "{Name:'minecraft:structure_block',Properties:{mode:'data'}}", "{Name:'minecraft:structure_block',Properties:{mode:'data'}}");
		a();
	}
}
