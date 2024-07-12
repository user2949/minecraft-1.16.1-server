import java.util.Optional;
import java.util.stream.IntStream;

public class ix {
	public static final iw a = a("cube", iz.c, iz.j, iz.k, iz.l, iz.m, iz.n, iz.o);
	public static final iw b = a("cube_directional", iz.c, iz.j, iz.k, iz.l, iz.m, iz.n, iz.o);
	public static final iw c = a("cube_all", iz.a);
	public static final iw d = a("cube_mirrored_all", "_mirrored", iz.a);
	public static final iw e = a("cube_column", iz.d, iz.i);
	public static final iw f = a("cube_column_horizontal", "_horizontal", iz.d, iz.i);
	public static final iw g = a("cube_top", iz.f, iz.i);
	public static final iw h = a("cube_bottom_top", iz.f, iz.e, iz.i);
	public static final iw i = a("orientable", iz.f, iz.g, iz.i);
	public static final iw j = a("orientable_with_bottom", iz.f, iz.e, iz.i, iz.g);
	public static final iw k = a("orientable_vertical", "_vertical", iz.g, iz.i);
	public static final iw l = a("button", iz.b);
	public static final iw m = a("button_pressed", "_pressed", iz.b);
	public static final iw n = a("button_inventory", "_inventory", iz.b);
	public static final iw o = a("door_bottom", "_bottom", iz.f, iz.e);
	public static final iw p = a("door_bottom_rh", "_bottom_hinge", iz.f, iz.e);
	public static final iw q = a("door_top", "_top", iz.f, iz.e);
	public static final iw r = a("door_top_rh", "_top_hinge", iz.f, iz.e);
	public static final iw s = a("fence_post", "_post", iz.b);
	public static final iw t = a("fence_side", "_side", iz.b);
	public static final iw u = a("fence_inventory", "_inventory", iz.b);
	public static final iw v = a("template_wall_post", "_post", iz.r);
	public static final iw w = a("template_wall_side", "_side", iz.r);
	public static final iw x = a("template_wall_side_tall", "_side_tall", iz.r);
	public static final iw y = a("wall_inventory", "_inventory", iz.r);
	public static final iw z = a("template_fence_gate", iz.b);
	public static final iw A = a("template_fence_gate_open", "_open", iz.b);
	public static final iw B = a("template_fence_gate_wall", "_wall", iz.b);
	public static final iw C = a("template_fence_gate_wall_open", "_wall_open", iz.b);
	public static final iw D = a("pressure_plate_up", iz.b);
	public static final iw E = a("pressure_plate_down", "_down", iz.b);
	public static final iw F = a(iz.c);
	public static final iw G = a("slab", iz.e, iz.f, iz.i);
	public static final iw H = a("slab_top", "_top", iz.e, iz.f, iz.i);
	public static final iw I = a("leaves", iz.a);
	public static final iw J = a("stairs", iz.e, iz.f, iz.i);
	public static final iw K = a("inner_stairs", "_inner", iz.e, iz.f, iz.i);
	public static final iw L = a("outer_stairs", "_outer", iz.e, iz.f, iz.i);
	public static final iw M = a("template_trapdoor_top", "_top", iz.b);
	public static final iw N = a("template_trapdoor_bottom", "_bottom", iz.b);
	public static final iw O = a("template_trapdoor_open", "_open", iz.b);
	public static final iw P = a("template_orientable_trapdoor_top", "_top", iz.b);
	public static final iw Q = a("template_orientable_trapdoor_bottom", "_bottom", iz.b);
	public static final iw R = a("template_orientable_trapdoor_open", "_open", iz.b);
	public static final iw S = a("cross", iz.p);
	public static final iw T = a("tinted_cross", iz.p);
	public static final iw U = a("flower_pot_cross", iz.q);
	public static final iw V = a("tinted_flower_pot_cross", iz.q);
	public static final iw W = a("rail_flat", iz.s);
	public static final iw X = a("rail_curved", "_corner", iz.s);
	public static final iw Y = a("template_rail_raised_ne", "_raised_ne", iz.s);
	public static final iw Z = a("template_rail_raised_sw", "_raised_sw", iz.s);
	public static final iw aa = a("carpet", iz.t);
	public static final iw ab = a("coral_fan", iz.x);
	public static final iw ac = a("coral_wall_fan", iz.x);
	public static final iw ad = a("template_glazed_terracotta", iz.u);
	public static final iw ae = a("template_chorus_flower", iz.b);
	public static final iw af = a("template_daylight_detector", iz.f, iz.i);
	public static final iw ag = a("template_glass_pane_noside", "_noside", iz.v);
	public static final iw ah = a("template_glass_pane_noside_alt", "_noside_alt", iz.v);
	public static final iw ai = a("template_glass_pane_post", "_post", iz.v, iz.w);
	public static final iw aj = a("template_glass_pane_side", "_side", iz.v, iz.w);
	public static final iw ak = a("template_glass_pane_side_alt", "_side_alt", iz.v, iz.w);
	public static final iw al = a("template_command_block", iz.g, iz.h, iz.i);
	public static final iw am = a("template_anvil", iz.f);
	public static final iw[] an = (iw[])IntStream.range(0, 8).mapToObj(integer -> a("stem_growth" + integer, "_stage" + integer, iz.y)).toArray(iw[]::new);
	public static final iw ao = a("stem_fruit", iz.y, iz.z);
	public static final iw ap = a("crop", iz.A);
	public static final iw aq = a("template_farmland", iz.B, iz.f);
	public static final iw ar = a("template_fire_floor", iz.C);
	public static final iw as = a("template_fire_side", iz.C);
	public static final iw at = a("template_fire_side_alt", iz.C);
	public static final iw au = a("template_fire_up", iz.C);
	public static final iw av = a("template_fire_up_alt", iz.C);
	public static final iw aw = a("template_campfire", iz.C, iz.I);
	public static final iw ax = a("template_lantern", iz.D);
	public static final iw ay = a("template_hanging_lantern", "_hanging", iz.D);
	public static final iw az = a("template_torch", iz.G);
	public static final iw aA = a("template_torch_wall", iz.G);
	public static final iw aB = a("template_piston", iz.E, iz.e, iz.i);
	public static final iw aC = a("template_piston_head", iz.E, iz.i, iz.F);
	public static final iw aD = a("template_piston_head_short", iz.E, iz.i, iz.F);
	public static final iw aE = a("template_seagrass", iz.b);
	public static final iw aF = a("template_turtle_egg", iz.a);
	public static final iw aG = a("template_two_turtle_eggs", iz.a);
	public static final iw aH = a("template_three_turtle_eggs", iz.a);
	public static final iw aI = a("template_four_turtle_eggs", iz.a);
	public static final iw aJ = a("template_single_face", iz.b);
	public static final iw aK = b("generated", iz.H);
	public static final iw aL = b("handheld", iz.H);
	public static final iw aM = b("handheld_rod", iz.H);
	public static final iw aN = b("template_shulker_box", iz.c);
	public static final iw aO = b("template_bed", iz.c);
	public static final iw aP = b("template_banner");
	public static final iw aQ = b("template_skull");

	private static iw a(iz... arr) {
		return new iw(Optional.empty(), Optional.empty(), arr);
	}

	private static iw a(String string, iz... arr) {
		return new iw(Optional.of(new uh("minecraft", "block/" + string)), Optional.empty(), arr);
	}

	private static iw b(String string, iz... arr) {
		return new iw(Optional.of(new uh("minecraft", "item/" + string)), Optional.empty(), arr);
	}

	private static iw a(String string1, String string2, iz... arr) {
		return new iw(Optional.of(new uh("minecraft", "block/" + string1)), Optional.of(string2), arr);
	}
}
