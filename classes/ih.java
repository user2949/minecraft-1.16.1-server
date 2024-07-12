import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public class ih {
	private final Consumer<ik> a;
	private final BiConsumer<uh, Supplier<JsonElement>> b;
	private final Consumer<bke> c;

	public ih(Consumer<ik> consumer1, BiConsumer<uh, Supplier<JsonElement>> biConsumer, Consumer<bke> consumer3) {
		this.a = consumer1;
		this.b = biConsumer;
		this.c = consumer3;
	}

	private void a(bvr bvr) {
		this.c.accept(bvr.h());
	}

	private void c(bvr bvr, uh uh) {
		this.b.accept(iv.a(bvr.h()), new iu(uh));
	}

	private void a(bke bke, uh uh) {
		this.b.accept(iv.a(bke), new iu(uh));
	}

	private void a(bke bke) {
		ix.aK.a(iv.a(bke), iy.b(bke), this.b);
	}

	private void b(bvr bvr) {
		bke bke3 = bvr.h();
		if (bke3 != bkk.a) {
			ix.aK.a(iv.a(bke3), iy.B(bvr), this.b);
		}
	}

	private void a(bvr bvr, String string) {
		bke bke4 = bvr.h();
		ix.aK.a(iv.a(bke4), iy.j(iy.a(bvr, string)), this.b);
	}

	private static io b() {
		return io.a(cfz.O).a(fz.EAST, iq.a().a(ir.b, ir.a.R90)).a(fz.SOUTH, iq.a().a(ir.b, ir.a.R180)).a(fz.WEST, iq.a().a(ir.b, ir.a.R270)).a(fz.NORTH, iq.a());
	}

	private static io c() {
		return io.a(cfz.O).a(fz.SOUTH, iq.a()).a(fz.WEST, iq.a().a(ir.b, ir.a.R90)).a(fz.NORTH, iq.a().a(ir.b, ir.a.R180)).a(fz.EAST, iq.a().a(ir.b, ir.a.R270));
	}

	private static io d() {
		return io.a(cfz.O).a(fz.EAST, iq.a()).a(fz.SOUTH, iq.a().a(ir.b, ir.a.R90)).a(fz.WEST, iq.a().a(ir.b, ir.a.R180)).a(fz.NORTH, iq.a().a(ir.b, ir.a.R270));
	}

	private static io e() {
		return io.a(cfz.M)
			.a(fz.DOWN, iq.a().a(ir.a, ir.a.R90))
			.a(fz.UP, iq.a().a(ir.a, ir.a.R270))
			.a(fz.NORTH, iq.a())
			.a(fz.SOUTH, iq.a().a(ir.b, ir.a.R180))
			.a(fz.WEST, iq.a().a(ir.b, ir.a.R270))
			.a(fz.EAST, iq.a().a(ir.b, ir.a.R90));
	}

	private static in d(bvr bvr, uh uh) {
		return in.a(bvr, a(uh));
	}

	private static iq[] a(uh uh) {
		return new iq[]{iq.a().a(ir.c, uh), iq.a().a(ir.c, uh).a(ir.b, ir.a.R90), iq.a().a(ir.c, uh).a(ir.b, ir.a.R180), iq.a().a(ir.c, uh).a(ir.b, ir.a.R270)};
	}

	private static in e(bvr bvr, uh uh2, uh uh3) {
		return in.a(bvr, iq.a().a(ir.c, uh2), iq.a().a(ir.c, uh3), iq.a().a(ir.c, uh2).a(ir.b, ir.a.R180), iq.a().a(ir.c, uh3).a(ir.b, ir.a.R180));
	}

	private static io a(cga cga, uh uh2, uh uh3) {
		return io.a(cga).a(true, iq.a().a(ir.c, uh2)).a(false, iq.a().a(ir.c, uh3));
	}

	private void c(bvr bvr) {
		uh uh3 = ja.a.a(bvr, this.b);
		uh uh4 = ja.b.a(bvr, this.b);
		this.a.accept(e(bvr, uh3, uh4));
	}

	private void d(bvr bvr) {
		uh uh3 = ja.a.a(bvr, this.b);
		this.a.accept(d(bvr, uh3));
	}

	private static ik f(bvr bvr, uh uh2, uh uh3) {
		return in.a(bvr)
			.a(io.a(cfz.w).a(false, iq.a().a(ir.c, uh2)).a(true, iq.a().a(ir.c, uh3)))
			.a(
				io.a(cfz.Q, cfz.O)
					.a(cfv.FLOOR, fz.EAST, iq.a().a(ir.b, ir.a.R90))
					.a(cfv.FLOOR, fz.WEST, iq.a().a(ir.b, ir.a.R270))
					.a(cfv.FLOOR, fz.SOUTH, iq.a().a(ir.b, ir.a.R180))
					.a(cfv.FLOOR, fz.NORTH, iq.a())
					.a(cfv.WALL, fz.EAST, iq.a().a(ir.b, ir.a.R90).a(ir.a, ir.a.R90).a(ir.d, true))
					.a(cfv.WALL, fz.WEST, iq.a().a(ir.b, ir.a.R270).a(ir.a, ir.a.R90).a(ir.d, true))
					.a(cfv.WALL, fz.SOUTH, iq.a().a(ir.b, ir.a.R180).a(ir.a, ir.a.R90).a(ir.d, true))
					.a(cfv.WALL, fz.NORTH, iq.a().a(ir.a, ir.a.R90).a(ir.d, true))
					.a(cfv.CEILING, fz.EAST, iq.a().a(ir.b, ir.a.R270).a(ir.a, ir.a.R180))
					.a(cfv.CEILING, fz.WEST, iq.a().a(ir.b, ir.a.R90).a(ir.a, ir.a.R180))
					.a(cfv.CEILING, fz.SOUTH, iq.a().a(ir.a, ir.a.R180))
					.a(cfv.CEILING, fz.NORTH, iq.a().a(ir.b, ir.a.R180).a(ir.a, ir.a.R180))
			);
	}

	private static io.d<fz, cgf, cge, Boolean> a(io.d<fz, cgf, cge, Boolean> d, cgf cgf, uh uh3, uh uh4) {
		return d.a(fz.EAST, cgf, cge.LEFT, false, iq.a().a(ir.c, uh3))
			.a(fz.SOUTH, cgf, cge.LEFT, false, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R90))
			.a(fz.WEST, cgf, cge.LEFT, false, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R180))
			.a(fz.NORTH, cgf, cge.LEFT, false, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R270))
			.a(fz.EAST, cgf, cge.RIGHT, false, iq.a().a(ir.c, uh4))
			.a(fz.SOUTH, cgf, cge.RIGHT, false, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R90))
			.a(fz.WEST, cgf, cge.RIGHT, false, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R180))
			.a(fz.NORTH, cgf, cge.RIGHT, false, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R270))
			.a(fz.EAST, cgf, cge.LEFT, true, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R90))
			.a(fz.SOUTH, cgf, cge.LEFT, true, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R180))
			.a(fz.WEST, cgf, cge.LEFT, true, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R270))
			.a(fz.NORTH, cgf, cge.LEFT, true, iq.a().a(ir.c, uh4))
			.a(fz.EAST, cgf, cge.RIGHT, true, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R270))
			.a(fz.SOUTH, cgf, cge.RIGHT, true, iq.a().a(ir.c, uh3))
			.a(fz.WEST, cgf, cge.RIGHT, true, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R90))
			.a(fz.NORTH, cgf, cge.RIGHT, true, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R180));
	}

	private static ik b(bvr bvr, uh uh2, uh uh3, uh uh4, uh uh5) {
		return in.a(bvr).a(a(a(io.a(cfz.O, cfz.aa, cfz.aH, cfz.u), cgf.LOWER, uh2, uh3), cgf.UPPER, uh4, uh5));
	}

	private static ik g(bvr bvr, uh uh2, uh uh3) {
		return im.a(bvr)
			.a(iq.a().a(ir.c, uh2))
			.a(il.a().a(cfz.I, true), iq.a().a(ir.c, uh3).a(ir.d, true))
			.a(il.a().a(cfz.J, true), iq.a().a(ir.c, uh3).a(ir.b, ir.a.R90).a(ir.d, true))
			.a(il.a().a(cfz.K, true), iq.a().a(ir.c, uh3).a(ir.b, ir.a.R180).a(ir.d, true))
			.a(il.a().a(cfz.L, true), iq.a().a(ir.c, uh3).a(ir.b, ir.a.R270).a(ir.d, true));
	}

	private static ik d(bvr bvr, uh uh2, uh uh3, uh uh4) {
		return im.a(bvr)
			.a(il.a().a(cfz.G, true), iq.a().a(ir.c, uh2))
			.a(il.a().a(cfz.T, cgr.LOW), iq.a().a(ir.c, uh3).a(ir.d, true))
			.a(il.a().a(cfz.S, cgr.LOW), iq.a().a(ir.c, uh3).a(ir.b, ir.a.R90).a(ir.d, true))
			.a(il.a().a(cfz.U, cgr.LOW), iq.a().a(ir.c, uh3).a(ir.b, ir.a.R180).a(ir.d, true))
			.a(il.a().a(cfz.V, cgr.LOW), iq.a().a(ir.c, uh3).a(ir.b, ir.a.R270).a(ir.d, true))
			.a(il.a().a(cfz.T, cgr.TALL), iq.a().a(ir.c, uh4).a(ir.d, true))
			.a(il.a().a(cfz.S, cgr.TALL), iq.a().a(ir.c, uh4).a(ir.b, ir.a.R90).a(ir.d, true))
			.a(il.a().a(cfz.U, cgr.TALL), iq.a().a(ir.c, uh4).a(ir.b, ir.a.R180).a(ir.d, true))
			.a(il.a().a(cfz.V, cgr.TALL), iq.a().a(ir.c, uh4).a(ir.b, ir.a.R270).a(ir.d, true));
	}

	private static ik c(bvr bvr, uh uh2, uh uh3, uh uh4, uh uh5) {
		return in.a(bvr, iq.a().a(ir.d, true))
			.a(c())
			.a(
				io.a(cfz.q, cfz.u)
					.a(false, false, iq.a().a(ir.c, uh3))
					.a(true, false, iq.a().a(ir.c, uh5))
					.a(false, true, iq.a().a(ir.c, uh2))
					.a(true, true, iq.a().a(ir.c, uh4))
			);
	}

	private static ik e(bvr bvr, uh uh2, uh uh3, uh uh4) {
		return in.a(bvr)
			.a(
				io.a(cfz.O, cfz.ab, cfz.aL)
					.a(fz.EAST, cgh.BOTTOM, cgp.STRAIGHT, iq.a().a(ir.c, uh3))
					.a(fz.WEST, cgh.BOTTOM, cgp.STRAIGHT, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R180).a(ir.d, true))
					.a(fz.SOUTH, cgh.BOTTOM, cgp.STRAIGHT, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R90).a(ir.d, true))
					.a(fz.NORTH, cgh.BOTTOM, cgp.STRAIGHT, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R270).a(ir.d, true))
					.a(fz.EAST, cgh.BOTTOM, cgp.OUTER_RIGHT, iq.a().a(ir.c, uh4))
					.a(fz.WEST, cgh.BOTTOM, cgp.OUTER_RIGHT, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R180).a(ir.d, true))
					.a(fz.SOUTH, cgh.BOTTOM, cgp.OUTER_RIGHT, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R90).a(ir.d, true))
					.a(fz.NORTH, cgh.BOTTOM, cgp.OUTER_RIGHT, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R270).a(ir.d, true))
					.a(fz.EAST, cgh.BOTTOM, cgp.OUTER_LEFT, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R270).a(ir.d, true))
					.a(fz.WEST, cgh.BOTTOM, cgp.OUTER_LEFT, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R90).a(ir.d, true))
					.a(fz.SOUTH, cgh.BOTTOM, cgp.OUTER_LEFT, iq.a().a(ir.c, uh4))
					.a(fz.NORTH, cgh.BOTTOM, cgp.OUTER_LEFT, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R180).a(ir.d, true))
					.a(fz.EAST, cgh.BOTTOM, cgp.INNER_RIGHT, iq.a().a(ir.c, uh2))
					.a(fz.WEST, cgh.BOTTOM, cgp.INNER_RIGHT, iq.a().a(ir.c, uh2).a(ir.b, ir.a.R180).a(ir.d, true))
					.a(fz.SOUTH, cgh.BOTTOM, cgp.INNER_RIGHT, iq.a().a(ir.c, uh2).a(ir.b, ir.a.R90).a(ir.d, true))
					.a(fz.NORTH, cgh.BOTTOM, cgp.INNER_RIGHT, iq.a().a(ir.c, uh2).a(ir.b, ir.a.R270).a(ir.d, true))
					.a(fz.EAST, cgh.BOTTOM, cgp.INNER_LEFT, iq.a().a(ir.c, uh2).a(ir.b, ir.a.R270).a(ir.d, true))
					.a(fz.WEST, cgh.BOTTOM, cgp.INNER_LEFT, iq.a().a(ir.c, uh2).a(ir.b, ir.a.R90).a(ir.d, true))
					.a(fz.SOUTH, cgh.BOTTOM, cgp.INNER_LEFT, iq.a().a(ir.c, uh2))
					.a(fz.NORTH, cgh.BOTTOM, cgp.INNER_LEFT, iq.a().a(ir.c, uh2).a(ir.b, ir.a.R180).a(ir.d, true))
					.a(fz.EAST, cgh.TOP, cgp.STRAIGHT, iq.a().a(ir.c, uh3).a(ir.a, ir.a.R180).a(ir.d, true))
					.a(fz.WEST, cgh.TOP, cgp.STRAIGHT, iq.a().a(ir.c, uh3).a(ir.a, ir.a.R180).a(ir.b, ir.a.R180).a(ir.d, true))
					.a(fz.SOUTH, cgh.TOP, cgp.STRAIGHT, iq.a().a(ir.c, uh3).a(ir.a, ir.a.R180).a(ir.b, ir.a.R90).a(ir.d, true))
					.a(fz.NORTH, cgh.TOP, cgp.STRAIGHT, iq.a().a(ir.c, uh3).a(ir.a, ir.a.R180).a(ir.b, ir.a.R270).a(ir.d, true))
					.a(fz.EAST, cgh.TOP, cgp.OUTER_RIGHT, iq.a().a(ir.c, uh4).a(ir.a, ir.a.R180).a(ir.b, ir.a.R90).a(ir.d, true))
					.a(fz.WEST, cgh.TOP, cgp.OUTER_RIGHT, iq.a().a(ir.c, uh4).a(ir.a, ir.a.R180).a(ir.b, ir.a.R270).a(ir.d, true))
					.a(fz.SOUTH, cgh.TOP, cgp.OUTER_RIGHT, iq.a().a(ir.c, uh4).a(ir.a, ir.a.R180).a(ir.b, ir.a.R180).a(ir.d, true))
					.a(fz.NORTH, cgh.TOP, cgp.OUTER_RIGHT, iq.a().a(ir.c, uh4).a(ir.a, ir.a.R180).a(ir.d, true))
					.a(fz.EAST, cgh.TOP, cgp.OUTER_LEFT, iq.a().a(ir.c, uh4).a(ir.a, ir.a.R180).a(ir.d, true))
					.a(fz.WEST, cgh.TOP, cgp.OUTER_LEFT, iq.a().a(ir.c, uh4).a(ir.a, ir.a.R180).a(ir.b, ir.a.R180).a(ir.d, true))
					.a(fz.SOUTH, cgh.TOP, cgp.OUTER_LEFT, iq.a().a(ir.c, uh4).a(ir.a, ir.a.R180).a(ir.b, ir.a.R90).a(ir.d, true))
					.a(fz.NORTH, cgh.TOP, cgp.OUTER_LEFT, iq.a().a(ir.c, uh4).a(ir.a, ir.a.R180).a(ir.b, ir.a.R270).a(ir.d, true))
					.a(fz.EAST, cgh.TOP, cgp.INNER_RIGHT, iq.a().a(ir.c, uh2).a(ir.a, ir.a.R180).a(ir.b, ir.a.R90).a(ir.d, true))
					.a(fz.WEST, cgh.TOP, cgp.INNER_RIGHT, iq.a().a(ir.c, uh2).a(ir.a, ir.a.R180).a(ir.b, ir.a.R270).a(ir.d, true))
					.a(fz.SOUTH, cgh.TOP, cgp.INNER_RIGHT, iq.a().a(ir.c, uh2).a(ir.a, ir.a.R180).a(ir.b, ir.a.R180).a(ir.d, true))
					.a(fz.NORTH, cgh.TOP, cgp.INNER_RIGHT, iq.a().a(ir.c, uh2).a(ir.a, ir.a.R180).a(ir.d, true))
					.a(fz.EAST, cgh.TOP, cgp.INNER_LEFT, iq.a().a(ir.c, uh2).a(ir.a, ir.a.R180).a(ir.d, true))
					.a(fz.WEST, cgh.TOP, cgp.INNER_LEFT, iq.a().a(ir.c, uh2).a(ir.a, ir.a.R180).a(ir.b, ir.a.R180).a(ir.d, true))
					.a(fz.SOUTH, cgh.TOP, cgp.INNER_LEFT, iq.a().a(ir.c, uh2).a(ir.a, ir.a.R180).a(ir.b, ir.a.R90).a(ir.d, true))
					.a(fz.NORTH, cgh.TOP, cgp.INNER_LEFT, iq.a().a(ir.c, uh2).a(ir.a, ir.a.R180).a(ir.b, ir.a.R270).a(ir.d, true))
			);
	}

	private static ik f(bvr bvr, uh uh2, uh uh3, uh uh4) {
		return in.a(bvr)
			.a(
				io.a(cfz.O, cfz.ab, cfz.u)
					.a(fz.NORTH, cgh.BOTTOM, false, iq.a().a(ir.c, uh3))
					.a(fz.SOUTH, cgh.BOTTOM, false, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R180))
					.a(fz.EAST, cgh.BOTTOM, false, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R90))
					.a(fz.WEST, cgh.BOTTOM, false, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R270))
					.a(fz.NORTH, cgh.TOP, false, iq.a().a(ir.c, uh2))
					.a(fz.SOUTH, cgh.TOP, false, iq.a().a(ir.c, uh2).a(ir.b, ir.a.R180))
					.a(fz.EAST, cgh.TOP, false, iq.a().a(ir.c, uh2).a(ir.b, ir.a.R90))
					.a(fz.WEST, cgh.TOP, false, iq.a().a(ir.c, uh2).a(ir.b, ir.a.R270))
					.a(fz.NORTH, cgh.BOTTOM, true, iq.a().a(ir.c, uh4))
					.a(fz.SOUTH, cgh.BOTTOM, true, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R180))
					.a(fz.EAST, cgh.BOTTOM, true, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R90))
					.a(fz.WEST, cgh.BOTTOM, true, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R270))
					.a(fz.NORTH, cgh.TOP, true, iq.a().a(ir.c, uh4).a(ir.a, ir.a.R180).a(ir.b, ir.a.R180))
					.a(fz.SOUTH, cgh.TOP, true, iq.a().a(ir.c, uh4).a(ir.a, ir.a.R180).a(ir.b, ir.a.R0))
					.a(fz.EAST, cgh.TOP, true, iq.a().a(ir.c, uh4).a(ir.a, ir.a.R180).a(ir.b, ir.a.R270))
					.a(fz.WEST, cgh.TOP, true, iq.a().a(ir.c, uh4).a(ir.a, ir.a.R180).a(ir.b, ir.a.R90))
			);
	}

	private static ik g(bvr bvr, uh uh2, uh uh3, uh uh4) {
		return in.a(bvr)
			.a(
				io.a(cfz.O, cfz.ab, cfz.u)
					.a(fz.NORTH, cgh.BOTTOM, false, iq.a().a(ir.c, uh3))
					.a(fz.SOUTH, cgh.BOTTOM, false, iq.a().a(ir.c, uh3))
					.a(fz.EAST, cgh.BOTTOM, false, iq.a().a(ir.c, uh3))
					.a(fz.WEST, cgh.BOTTOM, false, iq.a().a(ir.c, uh3))
					.a(fz.NORTH, cgh.TOP, false, iq.a().a(ir.c, uh2))
					.a(fz.SOUTH, cgh.TOP, false, iq.a().a(ir.c, uh2))
					.a(fz.EAST, cgh.TOP, false, iq.a().a(ir.c, uh2))
					.a(fz.WEST, cgh.TOP, false, iq.a().a(ir.c, uh2))
					.a(fz.NORTH, cgh.BOTTOM, true, iq.a().a(ir.c, uh4))
					.a(fz.SOUTH, cgh.BOTTOM, true, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R180))
					.a(fz.EAST, cgh.BOTTOM, true, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R90))
					.a(fz.WEST, cgh.BOTTOM, true, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R270))
					.a(fz.NORTH, cgh.TOP, true, iq.a().a(ir.c, uh4))
					.a(fz.SOUTH, cgh.TOP, true, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R180))
					.a(fz.EAST, cgh.TOP, true, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R90))
					.a(fz.WEST, cgh.TOP, true, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R270))
			);
	}

	private static in e(bvr bvr, uh uh) {
		return in.a(bvr, iq.a().a(ir.c, uh));
	}

	private static io f() {
		return io.a(cfz.F).a(fz.a.Y, iq.a()).a(fz.a.Z, iq.a().a(ir.a, ir.a.R90)).a(fz.a.X, iq.a().a(ir.a, ir.a.R90).a(ir.b, ir.a.R90));
	}

	private static ik f(bvr bvr, uh uh) {
		return in.a(bvr, iq.a().a(ir.c, uh)).a(f());
	}

	private void a(bvr bvr, ja.a a) {
		uh uh4 = a.a(bvr, this.b);
		this.a.accept(f(bvr, uh4));
	}

	private void b(bvr bvr, ja.a a) {
		uh uh4 = a.a(bvr, this.b);
		this.a.accept(in.a(bvr, iq.a().a(ir.c, uh4)).a(b()));
	}

	private static ik h(bvr bvr, uh uh2, uh uh3) {
		return in.a(bvr)
			.a(
				io.a(cfz.F)
					.a(fz.a.Y, iq.a().a(ir.c, uh2))
					.a(fz.a.Z, iq.a().a(ir.c, uh3).a(ir.a, ir.a.R90))
					.a(fz.a.X, iq.a().a(ir.c, uh3).a(ir.a, ir.a.R90).a(ir.b, ir.a.R90))
			);
	}

	private void a(bvr bvr, ja.a a2, ja.a a3) {
		uh uh5 = a2.a(bvr, this.b);
		uh uh6 = a3.a(bvr, this.b);
		this.a.accept(h(bvr, uh5, uh6));
	}

	private uh a(bvr bvr, String string, iw iw, Function<uh, iy> function) {
		return iw.a(bvr, string, (iy)function.apply(iy.a(bvr, string)), this.b);
	}

	private static ik i(bvr bvr, uh uh2, uh uh3) {
		return in.a(bvr).a(a(cfz.w, uh3, uh2));
	}

	private static ik h(bvr bvr, uh uh2, uh uh3, uh uh4) {
		return in.a(bvr).a(io.a(cfz.aK).a(cgo.BOTTOM, iq.a().a(ir.c, uh2)).a(cgo.TOP, iq.a().a(ir.c, uh3)).a(cgo.DOUBLE, iq.a().a(ir.c, uh4)));
	}

	private void e(bvr bvr) {
		this.c(bvr, ja.a);
	}

	private void c(bvr bvr, ja.a a) {
		this.a.accept(e(bvr, a.a(bvr, this.b)));
	}

	private void a(bvr bvr, iy iy, iw iw) {
		uh uh5 = iw.a(bvr, iy, this.b);
		this.a.accept(e(bvr, uh5));
	}

	private ih.b a(bvr bvr, ja ja) {
		return new ih.b(ja.b()).a(bvr, ja.a());
	}

	private ih.b d(bvr bvr, ja.a a) {
		ja ja4 = a.get(bvr);
		return new ih.b(ja4.b()).a(bvr, ja4.a());
	}

	private ih.b f(bvr bvr) {
		return this.d(bvr, ja.a);
	}

	private ih.b a(iy iy) {
		return new ih.b(iy);
	}

	private void g(bvr bvr) {
		iy iy3 = iy.p(bvr);
		uh uh4 = ix.o.a(bvr, iy3, this.b);
		uh uh5 = ix.p.a(bvr, iy3, this.b);
		uh uh6 = ix.q.a(bvr, iy3, this.b);
		uh uh7 = ix.r.a(bvr, iy3, this.b);
		this.a(bvr.h());
		this.a.accept(b(bvr, uh4, uh5, uh6, uh7));
	}

	private void h(bvr bvr) {
		iy iy3 = iy.b(bvr);
		uh uh4 = ix.P.a(bvr, iy3, this.b);
		uh uh5 = ix.Q.a(bvr, iy3, this.b);
		uh uh6 = ix.R.a(bvr, iy3, this.b);
		this.a.accept(f(bvr, uh4, uh5, uh6));
		this.c(bvr, uh5);
	}

	private void i(bvr bvr) {
		iy iy3 = iy.b(bvr);
		uh uh4 = ix.M.a(bvr, iy3, this.b);
		uh uh5 = ix.N.a(bvr, iy3, this.b);
		uh uh6 = ix.O.a(bvr, iy3, this.b);
		this.a.accept(g(bvr, uh4, uh5, uh6));
		this.c(bvr, uh5);
	}

	private ih.d j(bvr bvr) {
		return new ih.d(iy.l(bvr));
	}

	private void k(bvr bvr) {
		this.a(bvr, bvr);
	}

	private void a(bvr bvr1, bvr bvr2) {
		this.a.accept(e(bvr1, iv.a(bvr2)));
	}

	private void a(bvr bvr, ih.c c) {
		this.b(bvr);
		this.b(bvr, c);
	}

	private void a(bvr bvr, ih.c c, iy iy) {
		this.b(bvr);
		this.b(bvr, c, iy);
	}

	private void b(bvr bvr, ih.c c) {
		iy iy4 = iy.c(bvr);
		this.b(bvr, c, iy4);
	}

	private void b(bvr bvr, ih.c c, iy iy) {
		uh uh5 = c.a().a(bvr, iy, this.b);
		this.a.accept(e(bvr, uh5));
	}

	private void a(bvr bvr1, bvr bvr2, ih.c c) {
		this.a(bvr1, c);
		iy iy5 = iy.d(bvr1);
		uh uh6 = c.b().a(bvr2, iy5, this.b);
		this.a.accept(e(bvr2, uh6));
	}

	private void b(bvr bvr1, bvr bvr2) {
		ja ja4 = ja.k.get(bvr1);
		uh uh5 = ja4.a(bvr1, this.b);
		this.a.accept(e(bvr1, uh5));
		uh uh6 = ix.ac.a(bvr2, ja4.b(), this.b);
		this.a.accept(in.a(bvr2, iq.a().a(ir.c, uh6)).a(b()));
		this.b(bvr1);
	}

	private void c(bvr bvr1, bvr bvr2) {
		this.a(bvr1.h());
		iy iy4 = iy.g(bvr1);
		iy iy5 = iy.a(bvr1, bvr2);
		uh uh6 = ix.ao.a(bvr2, iy5, this.b);
		this.a
			.accept(
				in.a(bvr2, iq.a().a(ir.c, uh6))
					.a(io.a(cfz.O).a(fz.WEST, iq.a()).a(fz.SOUTH, iq.a().a(ir.b, ir.a.R270)).a(fz.NORTH, iq.a().a(ir.b, ir.a.R90)).a(fz.EAST, iq.a().a(ir.b, ir.a.R180)))
			);
		this.a.accept(in.a(bvr1).a(io.a(cfz.ai).a(integer -> iq.a().a(ir.c, ix.an[integer].a(bvr1, iy4, this.b)))));
	}

	private void a(bvr bvr1, bvr bvr2, bvr bvr3, bvr bvr4, bvr bvr5, bvr bvr6, bvr bvr7, bvr bvr8) {
		this.a(bvr1, ih.c.NOT_TINTED);
		this.a(bvr2, ih.c.NOT_TINTED);
		this.e(bvr3);
		this.e(bvr4);
		this.b(bvr5, bvr7);
		this.b(bvr6, bvr8);
	}

	private void c(bvr bvr, ih.c c) {
		this.a(bvr, "_top");
		uh uh4 = this.a(bvr, "_top", c.a(), iy::c);
		uh uh5 = this.a(bvr, "_bottom", c.a(), iy::c);
		this.j(bvr, uh4, uh5);
	}

	private void g() {
		this.a(bvs.gU, "_front");
		uh uh2 = iv.a(bvs.gU, "_top");
		uh uh3 = this.a(bvs.gU, "_bottom", ih.c.NOT_TINTED.a(), iy::c);
		this.j(bvs.gU, uh2, uh3);
	}

	private void h() {
		uh uh2 = this.a(bvs.aV, "_top", ix.aE, iy::a);
		uh uh3 = this.a(bvs.aV, "_bottom", ix.aE, iy::a);
		this.j(bvs.aV, uh2, uh3);
	}

	private void j(bvr bvr, uh uh2, uh uh3) {
		this.a.accept(in.a(bvr).a(io.a(cfz.aa).a(cgf.LOWER, iq.a().a(ir.c, uh3)).a(cgf.UPPER, iq.a().a(ir.c, uh2))));
	}

	private void l(bvr bvr) {
		iy iy3 = iy.e(bvr);
		iy iy4 = iy.e(iy.a(bvr, "_corner"));
		uh uh5 = ix.W.a(bvr, iy3, this.b);
		uh uh6 = ix.X.a(bvr, iy4, this.b);
		uh uh7 = ix.Y.a(bvr, iy3, this.b);
		uh uh8 = ix.Z.a(bvr, iy3, this.b);
		this.b(bvr);
		this.a
			.accept(
				in.a(bvr)
					.a(
						io.a(cfz.ac)
							.a(cgm.NORTH_SOUTH, iq.a().a(ir.c, uh5))
							.a(cgm.EAST_WEST, iq.a().a(ir.c, uh5).a(ir.b, ir.a.R90))
							.a(cgm.ASCENDING_EAST, iq.a().a(ir.c, uh7).a(ir.b, ir.a.R90))
							.a(cgm.ASCENDING_WEST, iq.a().a(ir.c, uh8).a(ir.b, ir.a.R90))
							.a(cgm.ASCENDING_NORTH, iq.a().a(ir.c, uh7))
							.a(cgm.ASCENDING_SOUTH, iq.a().a(ir.c, uh8))
							.a(cgm.SOUTH_EAST, iq.a().a(ir.c, uh6))
							.a(cgm.SOUTH_WEST, iq.a().a(ir.c, uh6).a(ir.b, ir.a.R90))
							.a(cgm.NORTH_WEST, iq.a().a(ir.c, uh6).a(ir.b, ir.a.R180))
							.a(cgm.NORTH_EAST, iq.a().a(ir.c, uh6).a(ir.b, ir.a.R270))
					)
			);
	}

	private void m(bvr bvr) {
		uh uh3 = this.a(bvr, "", ix.W, iy::e);
		uh uh4 = this.a(bvr, "", ix.Y, iy::e);
		uh uh5 = this.a(bvr, "", ix.Z, iy::e);
		uh uh6 = this.a(bvr, "_on", ix.W, iy::e);
		uh uh7 = this.a(bvr, "_on", ix.Y, iy::e);
		uh uh8 = this.a(bvr, "_on", ix.Z, iy::e);
		io io9 = io.a(cfz.w, cfz.ad).a((boolean7, cgm) -> {
			switch (cgm) {
				case NORTH_SOUTH:
					return iq.a().a(ir.c, boolean7 ? uh6 : uh3);
				case EAST_WEST:
					return iq.a().a(ir.c, boolean7 ? uh6 : uh3).a(ir.b, ir.a.R90);
				case ASCENDING_EAST:
					return iq.a().a(ir.c, boolean7 ? uh7 : uh4).a(ir.b, ir.a.R90);
				case ASCENDING_WEST:
					return iq.a().a(ir.c, boolean7 ? uh8 : uh5).a(ir.b, ir.a.R90);
				case ASCENDING_NORTH:
					return iq.a().a(ir.c, boolean7 ? uh7 : uh4);
				case ASCENDING_SOUTH:
					return iq.a().a(ir.c, boolean7 ? uh8 : uh5);
				default:
					throw new UnsupportedOperationException("Fix you generator!");
			}
		});
		this.b(bvr);
		this.a.accept(in.a(bvr).a(io9));
	}

	private ih.a a(uh uh, bvr bvr) {
		return new ih.a(uh, bvr);
	}

	private ih.a d(bvr bvr1, bvr bvr2) {
		return new ih.a(iv.a(bvr1), bvr2);
	}

	private void a(bvr bvr, bke bke) {
		uh uh4 = ix.F.a(bvr, iy.a(bke), this.b);
		this.a.accept(e(bvr, uh4));
	}

	private void g(bvr bvr, uh uh) {
		uh uh4 = ix.F.a(bvr, iy.h(uh), this.b);
		this.a.accept(e(bvr, uh4));
	}

	private void e(bvr bvr1, bvr bvr2) {
		this.c(bvr1, ja.a);
		uh uh4 = ja.i.get(bvr1).a(bvr2, this.b);
		this.a.accept(e(bvr2, uh4));
	}

	private void a(ja.a a, bvr... arr) {
		for (bvr bvr7 : arr) {
			uh uh8 = a.a(bvr7, this.b);
			this.a.accept(d(bvr7, uh8));
		}
	}

	private void b(ja.a a, bvr... arr) {
		for (bvr bvr7 : arr) {
			uh uh8 = a.a(bvr7, this.b);
			this.a.accept(in.a(bvr7, iq.a().a(ir.c, uh8)).a(c()));
		}
	}

	private void f(bvr bvr1, bvr bvr2) {
		this.e(bvr1);
		iy iy4 = iy.b(bvr1, bvr2);
		uh uh5 = ix.ai.a(bvr2, iy4, this.b);
		uh uh6 = ix.aj.a(bvr2, iy4, this.b);
		uh uh7 = ix.ak.a(bvr2, iy4, this.b);
		uh uh8 = ix.ag.a(bvr2, iy4, this.b);
		uh uh9 = ix.ah.a(bvr2, iy4, this.b);
		bke bke10 = bvr2.h();
		ix.aK.a(iv.a(bke10), iy.B(bvr1), this.b);
		this.a
			.accept(
				im.a(bvr2)
					.a(iq.a().a(ir.c, uh5))
					.a(il.a().a(cfz.I, true), iq.a().a(ir.c, uh6))
					.a(il.a().a(cfz.J, true), iq.a().a(ir.c, uh6).a(ir.b, ir.a.R90))
					.a(il.a().a(cfz.K, true), iq.a().a(ir.c, uh7))
					.a(il.a().a(cfz.L, true), iq.a().a(ir.c, uh7).a(ir.b, ir.a.R90))
					.a(il.a().a(cfz.I, false), iq.a().a(ir.c, uh8))
					.a(il.a().a(cfz.J, false), iq.a().a(ir.c, uh9))
					.a(il.a().a(cfz.K, false), iq.a().a(ir.c, uh9).a(ir.b, ir.a.R90))
					.a(il.a().a(cfz.L, false), iq.a().a(ir.c, uh8).a(ir.b, ir.a.R270))
			);
	}

	private void n(bvr bvr) {
		iy iy3 = iy.v(bvr);
		uh uh4 = ix.al.a(bvr, iy3, this.b);
		uh uh5 = this.a(bvr, "_conditional", ix.al, uh -> iy3.c(iz.i, uh));
		this.a.accept(in.a(bvr).a(a(cfz.c, uh5, uh4)).a(e()));
	}

	private void o(bvr bvr) {
		uh uh3 = ja.m.a(bvr, this.b);
		this.a.accept(e(bvr, uh3).a(c()));
	}

	private List<iq> a(int integer) {
		String string3 = "_age" + integer;
		return (List<iq>)IntStream.range(1, 5).mapToObj(integerx -> iq.a().a(ir.c, iv.a(bvs.kY, integerx + string3))).collect(Collectors.toList());
	}

	private void i() {
		this.a(bvs.kY);
		this.a
			.accept(
				im.a(bvs.kY)
					.a(il.a().a(cfz.ae, 0), this.a(0))
					.a(il.a().a(cfz.ae, 1), this.a(1))
					.a(il.a().a(cfz.aN, cfw.SMALL), iq.a().a(ir.c, iv.a(bvs.kY, "_small_leaves")))
					.a(il.a().a(cfz.aN, cfw.LARGE), iq.a().a(ir.c, iv.a(bvs.kY, "_large_leaves")))
			);
	}

	private io j() {
		return io.a(cfz.M)
			.a(fz.DOWN, iq.a().a(ir.a, ir.a.R180))
			.a(fz.UP, iq.a())
			.a(fz.NORTH, iq.a().a(ir.a, ir.a.R90))
			.a(fz.SOUTH, iq.a().a(ir.a, ir.a.R90).a(ir.b, ir.a.R180))
			.a(fz.WEST, iq.a().a(ir.a, ir.a.R90).a(ir.b, ir.a.R270))
			.a(fz.EAST, iq.a().a(ir.a, ir.a.R90).a(ir.b, ir.a.R90));
	}

	private void k() {
		uh uh2 = iy.a(bvs.lS, "_top_open");
		this.a
			.accept(
				in.a(bvs.lS)
					.a(this.j())
					.a(
						io.a(cfz.u)
							.a(false, iq.a().a(ir.c, ja.e.a(bvs.lS, this.b)))
							.a(true, iq.a().a(ir.c, ja.e.get(bvs.lS).a(iy -> iy.a(iz.f, uh2)).a(bvs.lS, "_open", this.b)))
					)
			);
	}

	private static <T extends Comparable<T>> io a(cgl<T> cgl, T comparable, uh uh3, uh uh4) {
		iq iq5 = iq.a().a(ir.c, uh3);
		iq iq6 = iq.a().a(ir.c, uh4);
		return io.a(cgl).a(comparable4 -> {
			boolean boolean5 = comparable4.compareTo(comparable) >= 0;
			return boolean5 ? iq5 : iq6;
		});
	}

	private void a(bvr bvr, Function<bvr, iy> function) {
		iy iy4 = ((iy)function.apply(bvr)).b(iz.i, iz.c);
		iy iy5 = iy4.c(iz.g, iy.a(bvr, "_front_honey"));
		uh uh6 = ix.j.a(bvr, iy4, this.b);
		uh uh7 = ix.j.a(bvr, "_honey", iy5, this.b);
		this.a.accept(in.a(bvr).a(b()).a(a(cfz.au, 5, uh7, uh6)));
	}

	private void a(bvr bvr, cgl<Integer> cgl, int... arr) {
		if (cgl.a().size() != arr.length) {
			throw new IllegalArgumentException();
		} else {
			Int2ObjectMap<uh> int2ObjectMap5 = new Int2ObjectOpenHashMap<>();
			io io6 = io.a(cgl).a(integer -> {
				int integer6 = arr[integer];
				uh uh7 = int2ObjectMap5.computeIfAbsent(integer6, integer3 -> this.a(bvr, "_stage" + integer6, ix.ap, iy::g));
				return iq.a().a(ir.c, uh7);
			});
			this.a(bvr.h());
			this.a.accept(in.a(bvr).a(io6));
		}
	}

	private void l() {
		uh uh2 = iv.a(bvs.mb, "_floor");
		uh uh3 = iv.a(bvs.mb, "_ceiling");
		uh uh4 = iv.a(bvs.mb, "_wall");
		uh uh5 = iv.a(bvs.mb, "_between_walls");
		this.a(bkk.ri);
		this.a
			.accept(
				in.a(bvs.mb)
					.a(
						io.a(cfz.O, cfz.R)
							.a(fz.NORTH, cfy.FLOOR, iq.a().a(ir.c, uh2))
							.a(fz.SOUTH, cfy.FLOOR, iq.a().a(ir.c, uh2).a(ir.b, ir.a.R180))
							.a(fz.EAST, cfy.FLOOR, iq.a().a(ir.c, uh2).a(ir.b, ir.a.R90))
							.a(fz.WEST, cfy.FLOOR, iq.a().a(ir.c, uh2).a(ir.b, ir.a.R270))
							.a(fz.NORTH, cfy.CEILING, iq.a().a(ir.c, uh3))
							.a(fz.SOUTH, cfy.CEILING, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R180))
							.a(fz.EAST, cfy.CEILING, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R90))
							.a(fz.WEST, cfy.CEILING, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R270))
							.a(fz.NORTH, cfy.SINGLE_WALL, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R270))
							.a(fz.SOUTH, cfy.SINGLE_WALL, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R90))
							.a(fz.EAST, cfy.SINGLE_WALL, iq.a().a(ir.c, uh4))
							.a(fz.WEST, cfy.SINGLE_WALL, iq.a().a(ir.c, uh4).a(ir.b, ir.a.R180))
							.a(fz.SOUTH, cfy.DOUBLE_WALL, iq.a().a(ir.c, uh5).a(ir.b, ir.a.R90))
							.a(fz.NORTH, cfy.DOUBLE_WALL, iq.a().a(ir.c, uh5).a(ir.b, ir.a.R270))
							.a(fz.EAST, cfy.DOUBLE_WALL, iq.a().a(ir.c, uh5))
							.a(fz.WEST, cfy.DOUBLE_WALL, iq.a().a(ir.c, uh5).a(ir.b, ir.a.R180))
					)
			);
	}

	private void m() {
		this.a
			.accept(
				in.a(bvs.lX, iq.a().a(ir.c, iv.a(bvs.lX)))
					.a(
						io.a(cfz.Q, cfz.O)
							.a(cfv.FLOOR, fz.NORTH, iq.a())
							.a(cfv.FLOOR, fz.EAST, iq.a().a(ir.b, ir.a.R90))
							.a(cfv.FLOOR, fz.SOUTH, iq.a().a(ir.b, ir.a.R180))
							.a(cfv.FLOOR, fz.WEST, iq.a().a(ir.b, ir.a.R270))
							.a(cfv.WALL, fz.NORTH, iq.a().a(ir.a, ir.a.R90))
							.a(cfv.WALL, fz.EAST, iq.a().a(ir.a, ir.a.R90).a(ir.b, ir.a.R90))
							.a(cfv.WALL, fz.SOUTH, iq.a().a(ir.a, ir.a.R90).a(ir.b, ir.a.R180))
							.a(cfv.WALL, fz.WEST, iq.a().a(ir.a, ir.a.R90).a(ir.b, ir.a.R270))
							.a(cfv.CEILING, fz.SOUTH, iq.a().a(ir.a, ir.a.R180))
							.a(cfv.CEILING, fz.WEST, iq.a().a(ir.a, ir.a.R180).a(ir.b, ir.a.R90))
							.a(cfv.CEILING, fz.NORTH, iq.a().a(ir.a, ir.a.R180).a(ir.b, ir.a.R180))
							.a(cfv.CEILING, fz.EAST, iq.a().a(ir.a, ir.a.R180).a(ir.b, ir.a.R270))
					)
			);
	}

	private void e(bvr bvr, ja.a a) {
		uh uh4 = a.a(bvr, this.b);
		uh uh5 = iy.a(bvr, "_front_on");
		uh uh6 = a.get(bvr).a(iy -> iy.a(iz.g, uh5)).a(bvr, "_on", this.b);
		this.a.accept(in.a(bvr).a(a(cfz.r, uh6, uh4)).a(b()));
	}

	private void a(bvr... arr) {
		uh uh3 = iv.a("campfire_off");

		for (bvr bvr7 : arr) {
			uh uh8 = ix.aw.a(bvr7, iy.A(bvr7), this.b);
			this.a(bvr7.h());
			this.a.accept(in.a(bvr7).a(a(cfz.r, uh8, uh3)).a(c()));
		}
	}

	private void n() {
		iy iy2 = iy.a(iy.C(bvs.bI), iy.C(bvs.n));
		uh uh3 = ix.e.a(bvs.bI, iy2, this.b);
		this.a.accept(e(bvs.bI, uh3));
	}

	private void o() {
		this.a(bkk.lP);
		this.a
			.accept(
				im.a(bvs.bS)
					.a(
						il.b(
							il.a().a(cfz.X, cgn.NONE).a(cfz.W, cgn.NONE).a(cfz.Y, cgn.NONE).a(cfz.Z, cgn.NONE),
							il.a().a(cfz.X, cgn.SIDE, cgn.UP).a(cfz.W, cgn.SIDE, cgn.UP),
							il.a().a(cfz.W, cgn.SIDE, cgn.UP).a(cfz.Y, cgn.SIDE, cgn.UP),
							il.a().a(cfz.Y, cgn.SIDE, cgn.UP).a(cfz.Z, cgn.SIDE, cgn.UP),
							il.a().a(cfz.Z, cgn.SIDE, cgn.UP).a(cfz.X, cgn.SIDE, cgn.UP)
						),
						iq.a().a(ir.c, iv.a("redstone_dust_dot"))
					)
					.a(il.a().a(cfz.X, cgn.SIDE, cgn.UP), iq.a().a(ir.c, iv.a("redstone_dust_side0")))
					.a(il.a().a(cfz.Y, cgn.SIDE, cgn.UP), iq.a().a(ir.c, iv.a("redstone_dust_side_alt0")))
					.a(il.a().a(cfz.W, cgn.SIDE, cgn.UP), iq.a().a(ir.c, iv.a("redstone_dust_side_alt1")).a(ir.b, ir.a.R270))
					.a(il.a().a(cfz.Z, cgn.SIDE, cgn.UP), iq.a().a(ir.c, iv.a("redstone_dust_side1")).a(ir.b, ir.a.R270))
					.a(il.a().a(cfz.X, cgn.UP), iq.a().a(ir.c, iv.a("redstone_dust_up")))
					.a(il.a().a(cfz.W, cgn.UP), iq.a().a(ir.c, iv.a("redstone_dust_up")).a(ir.b, ir.a.R90))
					.a(il.a().a(cfz.Y, cgn.UP), iq.a().a(ir.c, iv.a("redstone_dust_up")).a(ir.b, ir.a.R180))
					.a(il.a().a(cfz.Z, cgn.UP), iq.a().a(ir.c, iv.a("redstone_dust_up")).a(ir.b, ir.a.R270))
			);
	}

	private void p() {
		this.a(bkk.jV);
		this.a
			.accept(
				in.a(bvs.fu)
					.a(c())
					.a(
						io.a(cfz.aG, cfz.w)
							.a(cgc.COMPARE, false, iq.a().a(ir.c, iv.a(bvs.fu)))
							.a(cgc.COMPARE, true, iq.a().a(ir.c, iv.a(bvs.fu, "_on")))
							.a(cgc.SUBTRACT, false, iq.a().a(ir.c, iv.a(bvs.fu, "_subtract")))
							.a(cgc.SUBTRACT, true, iq.a().a(ir.c, iv.a(bvs.fu, "_on_subtract")))
					)
			);
	}

	private void q() {
		iy iy2 = iy.a(bvs.id);
		iy iy3 = iy.a(iy.a(bvs.hR, "_side"), iy2.a(iz.f));
		uh uh4 = ix.G.a(bvs.hR, iy3, this.b);
		uh uh5 = ix.H.a(bvs.hR, iy3, this.b);
		uh uh6 = ix.e.b(bvs.hR, "_double", iy3, this.b);
		this.a.accept(h(bvs.hR, uh4, uh5, uh6));
		this.a.accept(e(bvs.id, ix.c.a(bvs.id, iy2, this.b)));
	}

	private void r() {
		this.a(bkk.nB);
		this.a
			.accept(
				im.a(bvs.ea)
					.a(iq.a().a(ir.c, iy.C(bvs.ea)))
					.a(il.a().a(cfz.k, true), iq.a().a(ir.c, iy.a(bvs.ea, "_bottle0")))
					.a(il.a().a(cfz.l, true), iq.a().a(ir.c, iy.a(bvs.ea, "_bottle1")))
					.a(il.a().a(cfz.m, true), iq.a().a(ir.c, iy.a(bvs.ea, "_bottle2")))
					.a(il.a().a(cfz.k, false), iq.a().a(ir.c, iy.a(bvs.ea, "_empty0")))
					.a(il.a().a(cfz.l, false), iq.a().a(ir.c, iy.a(bvs.ea, "_empty1")))
					.a(il.a().a(cfz.m, false), iq.a().a(ir.c, iy.a(bvs.ea, "_empty2")))
			);
	}

	private void p(bvr bvr) {
		uh uh3 = ix.aJ.a(bvr, iy.b(bvr), this.b);
		uh uh4 = iv.a("mushroom_block_inside");
		this.a
			.accept(
				im.a(bvr)
					.a(il.a().a(cfz.I, true), iq.a().a(ir.c, uh3))
					.a(il.a().a(cfz.J, true), iq.a().a(ir.c, uh3).a(ir.b, ir.a.R90).a(ir.d, true))
					.a(il.a().a(cfz.K, true), iq.a().a(ir.c, uh3).a(ir.b, ir.a.R180).a(ir.d, true))
					.a(il.a().a(cfz.L, true), iq.a().a(ir.c, uh3).a(ir.b, ir.a.R270).a(ir.d, true))
					.a(il.a().a(cfz.G, true), iq.a().a(ir.c, uh3).a(ir.a, ir.a.R270).a(ir.d, true))
					.a(il.a().a(cfz.H, true), iq.a().a(ir.c, uh3).a(ir.a, ir.a.R90).a(ir.d, true))
					.a(il.a().a(cfz.I, false), iq.a().a(ir.c, uh4))
					.a(il.a().a(cfz.J, false), iq.a().a(ir.c, uh4).a(ir.b, ir.a.R90).a(ir.d, false))
					.a(il.a().a(cfz.K, false), iq.a().a(ir.c, uh4).a(ir.b, ir.a.R180).a(ir.d, false))
					.a(il.a().a(cfz.L, false), iq.a().a(ir.c, uh4).a(ir.b, ir.a.R270).a(ir.d, false))
					.a(il.a().a(cfz.G, false), iq.a().a(ir.c, uh4).a(ir.a, ir.a.R270).a(ir.d, false))
					.a(il.a().a(cfz.H, false), iq.a().a(ir.c, uh4).a(ir.a, ir.a.R90).a(ir.d, false))
			);
		this.c(bvr, ja.a.a(bvr, "_inventory", this.b));
	}

	private void s() {
		this.a(bkk.mN);
		this.a
			.accept(
				in.a(bvs.cW)
					.a(
						io.a(cfz.al)
							.a(0, iq.a().a(ir.c, iv.a(bvs.cW)))
							.a(1, iq.a().a(ir.c, iv.a(bvs.cW, "_slice1")))
							.a(2, iq.a().a(ir.c, iv.a(bvs.cW, "_slice2")))
							.a(3, iq.a().a(ir.c, iv.a(bvs.cW, "_slice3")))
							.a(4, iq.a().a(ir.c, iv.a(bvs.cW, "_slice4")))
							.a(5, iq.a().a(ir.c, iv.a(bvs.cW, "_slice5")))
							.a(6, iq.a().a(ir.c, iv.a(bvs.cW, "_slice6")))
					)
			);
	}

	private void t() {
		iy iy2 = new iy()
			.a(iz.c, iy.a(bvs.lV, "_side3"))
			.a(iz.o, iy.C(bvs.s))
			.a(iz.n, iy.a(bvs.lV, "_top"))
			.a(iz.j, iy.a(bvs.lV, "_side3"))
			.a(iz.l, iy.a(bvs.lV, "_side3"))
			.a(iz.k, iy.a(bvs.lV, "_side1"))
			.a(iz.m, iy.a(bvs.lV, "_side2"));
		this.a.accept(e(bvs.lV, ix.a.a(bvs.lV, iy2, this.b)));
	}

	private void u() {
		iy iy2 = new iy()
			.a(iz.c, iy.a(bvs.lZ, "_front"))
			.a(iz.o, iy.a(bvs.lZ, "_bottom"))
			.a(iz.n, iy.a(bvs.lZ, "_top"))
			.a(iz.j, iy.a(bvs.lZ, "_front"))
			.a(iz.k, iy.a(bvs.lZ, "_front"))
			.a(iz.l, iy.a(bvs.lZ, "_side"))
			.a(iz.m, iy.a(bvs.lZ, "_side"));
		this.a.accept(e(bvs.lZ, ix.a.a(bvs.lZ, iy2, this.b)));
	}

	private void a(bvr bvr1, bvr bvr2, BiFunction<bvr, bvr, iy> biFunction) {
		iy iy5 = (iy)biFunction.apply(bvr1, bvr2);
		this.a.accept(e(bvr1, ix.a.a(bvr1, iy5, this.b)));
	}

	private void v() {
		iy iy2 = iy.j(bvs.cK);
		this.a.accept(e(bvs.cK, iv.a(bvs.cK)));
		this.a(bvs.cU, iy2);
		this.a(bvs.cV, iy2);
	}

	private void a(bvr bvr, iy iy) {
		uh uh4 = ix.i.a(bvr, iy.c(iz.g, iy.C(bvr)), this.b);
		this.a.accept(in.a(bvr, iq.a().a(ir.c, uh4)).a(b()));
	}

	private void w() {
		this.a(bkk.nC);
		this.a
			.accept(
				in.a(bvs.eb)
					.a(
						io.a(cfz.ar)
							.a(0, iq.a().a(ir.c, iv.a(bvs.eb)))
							.a(1, iq.a().a(ir.c, iv.a(bvs.eb, "_level1")))
							.a(2, iq.a().a(ir.c, iv.a(bvs.eb, "_level2")))
							.a(3, iq.a().a(ir.c, iv.a(bvs.eb, "_level3")))
					)
			);
	}

	private void g(bvr bvr1, bvr bvr2) {
		iy iy4 = new iy().a(iz.d, iy.a(bvr2, "_top")).a(iz.i, iy.C(bvr1));
		this.a(bvr1, iy4, ix.e);
	}

	private void x() {
		iy iy2 = iy.b(bvs.iy);
		uh uh3 = ix.ae.a(bvs.iy, iy2, this.b);
		uh uh4 = this.a(bvs.iy, "_dead", ix.ae, uh -> iy2.c(iz.b, uh));
		this.a.accept(in.a(bvs.iy).a(a(cfz.ah, 5, uh4, uh3)));
	}

	private void q(bvr bvr) {
		iy iy3 = new iy().a(iz.f, iy.a(bvs.bY, "_top")).a(iz.i, iy.a(bvs.bY, "_side")).a(iz.g, iy.a(bvr, "_front"));
		iy iy4 = new iy().a(iz.i, iy.a(bvs.bY, "_top")).a(iz.g, iy.a(bvr, "_front_vertical"));
		uh uh5 = ix.i.a(bvr, iy3, this.b);
		uh uh6 = ix.k.a(bvr, iy4, this.b);
		this.a
			.accept(
				in.a(bvr)
					.a(
						io.a(cfz.M)
							.a(fz.DOWN, iq.a().a(ir.c, uh6).a(ir.a, ir.a.R180))
							.a(fz.UP, iq.a().a(ir.c, uh6))
							.a(fz.NORTH, iq.a().a(ir.c, uh5))
							.a(fz.EAST, iq.a().a(ir.c, uh5).a(ir.b, ir.a.R90))
							.a(fz.SOUTH, iq.a().a(ir.c, uh5).a(ir.b, ir.a.R180))
							.a(fz.WEST, iq.a().a(ir.c, uh5).a(ir.b, ir.a.R270))
					)
			);
	}

	private void y() {
		uh uh2 = iv.a(bvs.ed);
		uh uh3 = iv.a(bvs.ed, "_filled");
		this.a.accept(in.a(bvs.ed).a(io.a(cfz.h).a(false, iq.a().a(ir.c, uh2)).a(true, iq.a().a(ir.c, uh3))).a(c()));
	}

	private void z() {
		uh uh2 = iv.a(bvs.ix, "_side");
		uh uh3 = iv.a(bvs.ix, "_noside");
		uh uh4 = iv.a(bvs.ix, "_noside1");
		uh uh5 = iv.a(bvs.ix, "_noside2");
		uh uh6 = iv.a(bvs.ix, "_noside3");
		this.a
			.accept(
				im.a(bvs.ix)
					.a(il.a().a(cfz.I, true), iq.a().a(ir.c, uh2))
					.a(il.a().a(cfz.J, true), iq.a().a(ir.c, uh2).a(ir.b, ir.a.R90).a(ir.d, true))
					.a(il.a().a(cfz.K, true), iq.a().a(ir.c, uh2).a(ir.b, ir.a.R180).a(ir.d, true))
					.a(il.a().a(cfz.L, true), iq.a().a(ir.c, uh2).a(ir.b, ir.a.R270).a(ir.d, true))
					.a(il.a().a(cfz.G, true), iq.a().a(ir.c, uh2).a(ir.a, ir.a.R270).a(ir.d, true))
					.a(il.a().a(cfz.H, true), iq.a().a(ir.c, uh2).a(ir.a, ir.a.R90).a(ir.d, true))
					.a(il.a().a(cfz.I, false), iq.a().a(ir.c, uh3).a(ir.e, 2), iq.a().a(ir.c, uh4), iq.a().a(ir.c, uh5), iq.a().a(ir.c, uh6))
					.a(
						il.a().a(cfz.J, false),
						iq.a().a(ir.c, uh4).a(ir.b, ir.a.R90).a(ir.d, true),
						iq.a().a(ir.c, uh5).a(ir.b, ir.a.R90).a(ir.d, true),
						iq.a().a(ir.c, uh6).a(ir.b, ir.a.R90).a(ir.d, true),
						iq.a().a(ir.c, uh3).a(ir.e, 2).a(ir.b, ir.a.R90).a(ir.d, true)
					)
					.a(
						il.a().a(cfz.K, false),
						iq.a().a(ir.c, uh5).a(ir.b, ir.a.R180).a(ir.d, true),
						iq.a().a(ir.c, uh6).a(ir.b, ir.a.R180).a(ir.d, true),
						iq.a().a(ir.c, uh3).a(ir.e, 2).a(ir.b, ir.a.R180).a(ir.d, true),
						iq.a().a(ir.c, uh4).a(ir.b, ir.a.R180).a(ir.d, true)
					)
					.a(
						il.a().a(cfz.L, false),
						iq.a().a(ir.c, uh6).a(ir.b, ir.a.R270).a(ir.d, true),
						iq.a().a(ir.c, uh3).a(ir.e, 2).a(ir.b, ir.a.R270).a(ir.d, true),
						iq.a().a(ir.c, uh4).a(ir.b, ir.a.R270).a(ir.d, true),
						iq.a().a(ir.c, uh5).a(ir.b, ir.a.R270).a(ir.d, true)
					)
					.a(
						il.a().a(cfz.G, false),
						iq.a().a(ir.c, uh3).a(ir.e, 2).a(ir.a, ir.a.R270).a(ir.d, true),
						iq.a().a(ir.c, uh6).a(ir.a, ir.a.R270).a(ir.d, true),
						iq.a().a(ir.c, uh4).a(ir.a, ir.a.R270).a(ir.d, true),
						iq.a().a(ir.c, uh5).a(ir.a, ir.a.R270).a(ir.d, true)
					)
					.a(
						il.a().a(cfz.H, false),
						iq.a().a(ir.c, uh6).a(ir.a, ir.a.R90).a(ir.d, true),
						iq.a().a(ir.c, uh5).a(ir.a, ir.a.R90).a(ir.d, true),
						iq.a().a(ir.c, uh4).a(ir.a, ir.a.R90).a(ir.d, true),
						iq.a().a(ir.c, uh3).a(ir.e, 2).a(ir.a, ir.a.R90).a(ir.d, true)
					)
			);
	}

	private void A() {
		this.a
			.accept(
				im.a(bvs.na)
					.a(iq.a().a(ir.c, iy.C(bvs.na)))
					.a(il.a().a(cfz.as, 1), iq.a().a(ir.c, iy.a(bvs.na, "_contents1")))
					.a(il.a().a(cfz.as, 2), iq.a().a(ir.c, iy.a(bvs.na, "_contents2")))
					.a(il.a().a(cfz.as, 3), iq.a().a(ir.c, iy.a(bvs.na, "_contents3")))
					.a(il.a().a(cfz.as, 4), iq.a().a(ir.c, iy.a(bvs.na, "_contents4")))
					.a(il.a().a(cfz.as, 5), iq.a().a(ir.c, iy.a(bvs.na, "_contents5")))
					.a(il.a().a(cfz.as, 6), iq.a().a(ir.c, iy.a(bvs.na, "_contents6")))
					.a(il.a().a(cfz.as, 7), iq.a().a(ir.c, iy.a(bvs.na, "_contents7")))
					.a(il.a().a(cfz.as, 8), iq.a().a(ir.c, iy.a(bvs.na, "_contents_ready")))
			);
	}

	private void r(bvr bvr) {
		iy iy3 = new iy().a(iz.e, iy.C(bvs.cL)).a(iz.f, iy.C(bvr)).a(iz.i, iy.a(bvr, "_side"));
		this.a.accept(e(bvr, ix.h.a(bvr, iy3, this.b)));
	}

	private void B() {
		uh uh2 = iy.a(bvs.fv, "_side");
		iy iy3 = new iy().a(iz.f, iy.a(bvs.fv, "_top")).a(iz.i, uh2);
		iy iy4 = new iy().a(iz.f, iy.a(bvs.fv, "_inverted_top")).a(iz.i, uh2);
		this.a
			.accept(
				in.a(bvs.fv).a(io.a(cfz.p).a(false, iq.a().a(ir.c, ix.af.a(bvs.fv, iy3, this.b))).a(true, iq.a().a(ir.c, ix.af.a(iv.a(bvs.fv, "_inverted"), iy4, this.b))))
			);
	}

	private void C() {
		this.a.accept(in.a(bvs.iw, iq.a().a(ir.c, iv.a(bvs.iw))).a(this.j()));
	}

	private void D() {
		iy iy2 = new iy().a(iz.B, iy.C(bvs.j)).a(iz.f, iy.C(bvs.bX));
		iy iy3 = new iy().a(iz.B, iy.C(bvs.j)).a(iz.f, iy.a(bvs.bX, "_moist"));
		uh uh4 = ix.aq.a(bvs.bX, iy2, this.b);
		uh uh5 = ix.aq.a(iy.a(bvs.bX, "_moist"), iy3, this.b);
		this.a.accept(in.a(bvs.bX).a(a(cfz.aw, 7, uh5, uh4)));
	}

	private List<uh> s(bvr bvr) {
		uh uh3 = ix.ar.a(iv.a(bvr, "_floor0"), iy.r(bvr), this.b);
		uh uh4 = ix.ar.a(iv.a(bvr, "_floor1"), iy.s(bvr), this.b);
		return ImmutableList.of(uh3, uh4);
	}

	private List<uh> t(bvr bvr) {
		uh uh3 = ix.as.a(iv.a(bvr, "_side0"), iy.r(bvr), this.b);
		uh uh4 = ix.as.a(iv.a(bvr, "_side1"), iy.s(bvr), this.b);
		uh uh5 = ix.at.a(iv.a(bvr, "_side_alt0"), iy.r(bvr), this.b);
		uh uh6 = ix.at.a(iv.a(bvr, "_side_alt1"), iy.s(bvr), this.b);
		return ImmutableList.of(uh3, uh4, uh5, uh6);
	}

	private List<uh> u(bvr bvr) {
		uh uh3 = ix.au.a(iv.a(bvr, "_up0"), iy.r(bvr), this.b);
		uh uh4 = ix.au.a(iv.a(bvr, "_up1"), iy.s(bvr), this.b);
		uh uh5 = ix.av.a(iv.a(bvr, "_up_alt0"), iy.r(bvr), this.b);
		uh uh6 = ix.av.a(iv.a(bvr, "_up_alt1"), iy.s(bvr), this.b);
		return ImmutableList.of(uh3, uh4, uh5, uh6);
	}

	private static List<iq> a(List<uh> list, UnaryOperator<iq> unaryOperator) {
		return (List<iq>)list.stream().map(uh -> iq.a().a(ir.c, uh)).map(unaryOperator).collect(Collectors.toList());
	}

	private void E() {
		il il2 = il.a().a(cfz.I, false).a(cfz.J, false).a(cfz.K, false).a(cfz.L, false).a(cfz.G, false);
		List<uh> list3 = this.s(bvs.bN);
		List<uh> list4 = this.t(bvs.bN);
		List<uh> list5 = this.u(bvs.bN);
		this.a
			.accept(
				im.a(bvs.bN)
					.a(il2, a(list3, iq -> iq))
					.a(il.b(il.a().a(cfz.I, true), il2), a(list4, iq -> iq))
					.a(il.b(il.a().a(cfz.J, true), il2), a(list4, iq -> iq.a(ir.b, ir.a.R90)))
					.a(il.b(il.a().a(cfz.K, true), il2), a(list4, iq -> iq.a(ir.b, ir.a.R180)))
					.a(il.b(il.a().a(cfz.L, true), il2), a(list4, iq -> iq.a(ir.b, ir.a.R270)))
					.a(il.a().a(cfz.G, true), a(list5, iq -> iq))
			);
	}

	private void F() {
		List<uh> list2 = this.s(bvs.bO);
		List<uh> list3 = this.t(bvs.bO);
		this.a
			.accept(
				im.a(bvs.bO)
					.a(a(list2, iq -> iq))
					.a(a(list3, iq -> iq))
					.a(a(list3, iq -> iq.a(ir.b, ir.a.R90)))
					.a(a(list3, iq -> iq.a(ir.b, ir.a.R180)))
					.a(a(list3, iq -> iq.a(ir.b, ir.a.R270)))
			);
	}

	private void v(bvr bvr) {
		uh uh3 = ja.o.a(bvr, this.b);
		uh uh4 = ja.p.a(bvr, this.b);
		this.a(bvr.h());
		this.a.accept(in.a(bvr).a(a(cfz.j, uh4, uh3)));
	}

	private void G() {
		this.a
			.accept(
				in.a(bvs.iI)
					.a(
						io.a(cfz.ag)
							.a(0, iq.a().a(ir.c, this.a(bvs.iI, "_0", ix.c, iy::b)))
							.a(1, iq.a().a(ir.c, this.a(bvs.iI, "_1", ix.c, iy::b)))
							.a(2, iq.a().a(ir.c, this.a(bvs.iI, "_2", ix.c, iy::b)))
							.a(3, iq.a().a(ir.c, this.a(bvs.iI, "_3", ix.c, iy::b)))
					)
			);
	}

	private void H() {
		uh uh2 = iy.C(bvs.j);
		iy iy3 = new iy().a(iz.e, uh2).b(iz.e, iz.c).a(iz.f, iy.a(bvs.i, "_top")).a(iz.i, iy.a(bvs.i, "_snow"));
		iq iq4 = iq.a().a(ir.c, ix.h.a(bvs.i, "_snow", iy3, this.b));
		this.a(bvs.i, iv.a(bvs.i), iq4);
		uh uh5 = ja.e.get(bvs.dT).a(iy -> iy.a(iz.e, uh2)).a(bvs.dT, this.b);
		this.a(bvs.dT, uh5, iq4);
		uh uh6 = ja.e.get(bvs.l).a(iy -> iy.a(iz.e, uh2)).a(bvs.l, this.b);
		this.a(bvs.l, uh6, iq4);
	}

	private void a(bvr bvr, uh uh, iq iq) {
		List<iq> list5 = Arrays.asList(a(uh));
		this.a.accept(in.a(bvr).a(io.a(cfz.z).a(true, iq).a(false, list5)));
	}

	private void I() {
		this.a(bkk.mu);
		this.a
			.accept(
				in.a(bvs.eh)
					.a(io.a(cfz.af).a(0, iq.a().a(ir.c, iv.a(bvs.eh, "_stage0"))).a(1, iq.a().a(ir.c, iv.a(bvs.eh, "_stage1"))).a(2, iq.a().a(ir.c, iv.a(bvs.eh, "_stage2"))))
					.a(c())
			);
	}

	private void J() {
		this.a.accept(d(bvs.iE, iv.a(bvs.iE)));
	}

	private void h(bvr bvr1, bvr bvr2) {
		iy iy4 = iy.b(bvr2);
		uh uh5 = ix.D.a(bvr1, iy4, this.b);
		uh uh6 = ix.E.a(bvr1, iy4, this.b);
		this.a.accept(in.a(bvr1).a(a(cfz.az, 1, uh6, uh5)));
	}

	private void K() {
		uh uh2 = iv.a(bvs.fy);
		uh uh3 = iv.a(bvs.fy, "_side");
		this.a(bkk.fl);
		this.a
			.accept(
				in.a(bvs.fy)
					.a(
						io.a(cfz.N)
							.a(fz.DOWN, iq.a().a(ir.c, uh2))
							.a(fz.NORTH, iq.a().a(ir.c, uh3))
							.a(fz.EAST, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R90))
							.a(fz.SOUTH, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R180))
							.a(fz.WEST, iq.a().a(ir.c, uh3).a(ir.b, ir.a.R270))
					)
			);
	}

	private void i(bvr bvr1, bvr bvr2) {
		uh uh4 = iv.a(bvr1);
		this.a.accept(in.a(bvr2, iq.a().a(ir.c, uh4)));
		this.c(bvr2, uh4);
	}

	private void L() {
		uh uh2 = iv.a(bvs.dH, "_post_ends");
		uh uh3 = iv.a(bvs.dH, "_post");
		uh uh4 = iv.a(bvs.dH, "_cap");
		uh uh5 = iv.a(bvs.dH, "_cap_alt");
		uh uh6 = iv.a(bvs.dH, "_side");
		uh uh7 = iv.a(bvs.dH, "_side_alt");
		this.a
			.accept(
				im.a(bvs.dH)
					.a(iq.a().a(ir.c, uh2))
					.a(il.a().a(cfz.I, false).a(cfz.J, false).a(cfz.K, false).a(cfz.L, false), iq.a().a(ir.c, uh3))
					.a(il.a().a(cfz.I, true).a(cfz.J, false).a(cfz.K, false).a(cfz.L, false), iq.a().a(ir.c, uh4))
					.a(il.a().a(cfz.I, false).a(cfz.J, true).a(cfz.K, false).a(cfz.L, false), iq.a().a(ir.c, uh4).a(ir.b, ir.a.R90))
					.a(il.a().a(cfz.I, false).a(cfz.J, false).a(cfz.K, true).a(cfz.L, false), iq.a().a(ir.c, uh5))
					.a(il.a().a(cfz.I, false).a(cfz.J, false).a(cfz.K, false).a(cfz.L, true), iq.a().a(ir.c, uh5).a(ir.b, ir.a.R90))
					.a(il.a().a(cfz.I, true), iq.a().a(ir.c, uh6))
					.a(il.a().a(cfz.J, true), iq.a().a(ir.c, uh6).a(ir.b, ir.a.R90))
					.a(il.a().a(cfz.K, true), iq.a().a(ir.c, uh7))
					.a(il.a().a(cfz.L, true), iq.a().a(ir.c, uh7).a(ir.b, ir.a.R90))
			);
		this.b(bvs.dH);
	}

	private void w(bvr bvr) {
		this.a.accept(in.a(bvr, iq.a().a(ir.c, iv.a(bvr))).a(b()));
	}

	private void M() {
		uh uh2 = iv.a(bvs.cp);
		uh uh3 = iv.a(bvs.cp, "_on");
		this.b(bvs.cp);
		this.a
			.accept(
				in.a(bvs.cp)
					.a(a(cfz.w, uh2, uh3))
					.a(
						io.a(cfz.Q, cfz.O)
							.a(cfv.CEILING, fz.NORTH, iq.a().a(ir.a, ir.a.R180).a(ir.b, ir.a.R180))
							.a(cfv.CEILING, fz.EAST, iq.a().a(ir.a, ir.a.R180).a(ir.b, ir.a.R270))
							.a(cfv.CEILING, fz.SOUTH, iq.a().a(ir.a, ir.a.R180))
							.a(cfv.CEILING, fz.WEST, iq.a().a(ir.a, ir.a.R180).a(ir.b, ir.a.R90))
							.a(cfv.FLOOR, fz.NORTH, iq.a())
							.a(cfv.FLOOR, fz.EAST, iq.a().a(ir.b, ir.a.R90))
							.a(cfv.FLOOR, fz.SOUTH, iq.a().a(ir.b, ir.a.R180))
							.a(cfv.FLOOR, fz.WEST, iq.a().a(ir.b, ir.a.R270))
							.a(cfv.WALL, fz.NORTH, iq.a().a(ir.a, ir.a.R90))
							.a(cfv.WALL, fz.EAST, iq.a().a(ir.a, ir.a.R90).a(ir.b, ir.a.R90))
							.a(cfv.WALL, fz.SOUTH, iq.a().a(ir.a, ir.a.R90).a(ir.b, ir.a.R180))
							.a(cfv.WALL, fz.WEST, iq.a().a(ir.a, ir.a.R90).a(ir.b, ir.a.R270))
					)
			);
	}

	private void N() {
		this.b(bvs.dU);
		this.a.accept(d(bvs.dU, iv.a(bvs.dU)));
	}

	private void O() {
		this.a.accept(in.a(bvs.cT).a(io.a(cfz.E).a(fz.a.X, iq.a().a(ir.c, iv.a(bvs.cT, "_ns"))).a(fz.a.Z, iq.a().a(ir.c, iv.a(bvs.cT, "_ew")))));
	}

	private void P() {
		uh uh2 = ja.a.a(bvs.cL, this.b);
		this.a
			.accept(
				in.a(
					bvs.cL,
					iq.a().a(ir.c, uh2),
					iq.a().a(ir.c, uh2).a(ir.a, ir.a.R90),
					iq.a().a(ir.c, uh2).a(ir.a, ir.a.R180),
					iq.a().a(ir.c, uh2).a(ir.a, ir.a.R270),
					iq.a().a(ir.c, uh2).a(ir.b, ir.a.R90),
					iq.a().a(ir.c, uh2).a(ir.b, ir.a.R90).a(ir.a, ir.a.R90),
					iq.a().a(ir.c, uh2).a(ir.b, ir.a.R90).a(ir.a, ir.a.R180),
					iq.a().a(ir.c, uh2).a(ir.b, ir.a.R90).a(ir.a, ir.a.R270),
					iq.a().a(ir.c, uh2).a(ir.b, ir.a.R180),
					iq.a().a(ir.c, uh2).a(ir.b, ir.a.R180).a(ir.a, ir.a.R90),
					iq.a().a(ir.c, uh2).a(ir.b, ir.a.R180).a(ir.a, ir.a.R180),
					iq.a().a(ir.c, uh2).a(ir.b, ir.a.R180).a(ir.a, ir.a.R270),
					iq.a().a(ir.c, uh2).a(ir.b, ir.a.R270),
					iq.a().a(ir.c, uh2).a(ir.b, ir.a.R270).a(ir.a, ir.a.R90),
					iq.a().a(ir.c, uh2).a(ir.b, ir.a.R270).a(ir.a, ir.a.R180),
					iq.a().a(ir.c, uh2).a(ir.b, ir.a.R270).a(ir.a, ir.a.R270)
				)
			);
	}

	private void Q() {
		uh uh2 = iv.a(bvs.iO);
		uh uh3 = iv.a(bvs.iO, "_on");
		this.a.accept(in.a(bvs.iO).a(a(cfz.w, uh3, uh2)).a(e()));
	}

	private void R() {
		iy iy2 = new iy().a(iz.e, iy.a(bvs.aW, "_bottom")).a(iz.i, iy.a(bvs.aW, "_side"));
		uh uh3 = iy.a(bvs.aW, "_top_sticky");
		uh uh4 = iy.a(bvs.aW, "_top");
		iy iy5 = iy2.c(iz.E, uh3);
		iy iy6 = iy2.c(iz.E, uh4);
		uh uh7 = iv.a(bvs.aW, "_base");
		this.a(bvs.aW, uh7, iy6);
		this.a(bvs.aP, uh7, iy5);
		uh uh8 = ix.h.a(bvs.aW, "_inventory", iy2.c(iz.f, uh4), this.b);
		uh uh9 = ix.h.a(bvs.aP, "_inventory", iy2.c(iz.f, uh3), this.b);
		this.c(bvs.aW, uh8);
		this.c(bvs.aP, uh9);
	}

	private void a(bvr bvr, uh uh, iy iy) {
		uh uh5 = ix.aB.a(bvr, iy, this.b);
		this.a.accept(in.a(bvr).a(a(cfz.g, uh, uh5)).a(e()));
	}

	private void S() {
		iy iy2 = new iy().a(iz.F, iy.a(bvs.aW, "_top")).a(iz.i, iy.a(bvs.aW, "_side"));
		iy iy3 = iy2.c(iz.E, iy.a(bvs.aW, "_top_sticky"));
		iy iy4 = iy2.c(iz.E, iy.a(bvs.aW, "_top"));
		this.a
			.accept(
				in.a(bvs.aX)
					.a(
						io.a(cfz.x, cfz.aJ)
							.a(false, cgk.DEFAULT, iq.a().a(ir.c, ix.aC.a(bvs.aW, "_head", iy4, this.b)))
							.a(false, cgk.STICKY, iq.a().a(ir.c, ix.aC.a(bvs.aW, "_head_sticky", iy3, this.b)))
							.a(true, cgk.DEFAULT, iq.a().a(ir.c, ix.aD.a(bvs.aW, "_head_short", iy4, this.b)))
							.a(true, cgk.STICKY, iq.a().a(ir.c, ix.aD.a(bvs.aW, "_head_short_sticky", iy3, this.b)))
					)
					.a(e())
			);
	}

	private void T() {
		uh uh2 = iv.a(bvs.lQ, "_stable");
		uh uh3 = iv.a(bvs.lQ, "_unstable");
		this.c(bvs.lQ, uh2);
		this.a.accept(in.a(bvs.lQ).a(a(cfz.b, uh3, uh2)));
	}

	private void U() {
		uh uh2 = ja.a.a(bvs.eg, this.b);
		uh uh3 = this.a(bvs.eg, "_on", ix.c, iy::b);
		this.a.accept(in.a(bvs.eg).a(a(cfz.r, uh3, uh2)));
	}

	private void j(bvr bvr1, bvr bvr2) {
		iy iy4 = iy.u(bvr1);
		this.a.accept(e(bvr1, ix.az.a(bvr1, iy4, this.b)));
		this.a.accept(in.a(bvr2, iq.a().a(ir.c, ix.aA.a(bvr2, iy4, this.b))).a(d()));
		this.b(bvr1);
		this.a(bvr2);
	}

	private void V() {
		iy iy2 = iy.u(bvs.cz);
		iy iy3 = iy.i(iy.a(bvs.cz, "_off"));
		uh uh4 = ix.az.a(bvs.cz, iy2, this.b);
		uh uh5 = ix.az.a(bvs.cz, "_off", iy3, this.b);
		this.a.accept(in.a(bvs.cz).a(a(cfz.r, uh4, uh5)));
		uh uh6 = ix.aA.a(bvs.cA, iy2, this.b);
		uh uh7 = ix.aA.a(bvs.cA, "_off", iy3, this.b);
		this.a.accept(in.a(bvs.cA).a(a(cfz.r, uh6, uh7)).a(d()));
		this.b(bvs.cz);
		this.a(bvs.cA);
	}

	private void W() {
		this.a(bkk.jU);
		this.a.accept(in.a(bvs.cX).a(io.a(cfz.am, cfz.s, cfz.w).a((integer, boolean2, boolean3) -> {
			StringBuilder stringBuilder4 = new StringBuilder();
			stringBuilder4.append('_').append(integer).append("tick");
			if (boolean3) {
				stringBuilder4.append("_on");
			}

			if (boolean2) {
				stringBuilder4.append("_locked");
			}

			return iq.a().a(ir.c, iy.a(bvs.cX, stringBuilder4.toString()));
		})).a(c()));
	}

	private void X() {
		this.a(bkk.aP);
		this.a
			.accept(
				in.a(bvs.kU)
					.a(
						io.a(cfz.ay, cfz.C)
							.a(1, false, Arrays.asList(a(iv.a("dead_sea_pickle"))))
							.a(2, false, Arrays.asList(a(iv.a("two_dead_sea_pickles"))))
							.a(3, false, Arrays.asList(a(iv.a("three_dead_sea_pickles"))))
							.a(4, false, Arrays.asList(a(iv.a("four_dead_sea_pickles"))))
							.a(1, true, Arrays.asList(a(iv.a("sea_pickle"))))
							.a(2, true, Arrays.asList(a(iv.a("two_sea_pickles"))))
							.a(3, true, Arrays.asList(a(iv.a("three_sea_pickles"))))
							.a(4, true, Arrays.asList(a(iv.a("four_sea_pickles"))))
					)
			);
	}

	private void Y() {
		iy iy2 = iy.a(bvs.cC);
		uh uh3 = ix.c.a(bvs.cE, iy2, this.b);
		this.a.accept(in.a(bvs.cC).a(io.a(cfz.aq).a(integer -> iq.a().a(ir.c, integer < 8 ? iv.a(bvs.cC, "_height" + integer * 2) : uh3))));
		this.c(bvs.cC, iv.a(bvs.cC, "_height2"));
		this.a.accept(e(bvs.cE, uh3));
	}

	private void Z() {
		this.a.accept(in.a(bvs.ma, iq.a().a(ir.c, iv.a(bvs.ma))).a(b()));
	}

	private void aa() {
		uh uh2 = ja.a.a(bvs.mY, this.b);
		this.c(bvs.mY, uh2);
		this.a.accept(in.a(bvs.mY).a(io.a(cfz.aM).a(cgq -> iq.a().a(ir.c, this.a(bvs.mY, "_" + cgq.a(), ix.c, iy::b)))));
	}

	private void ab() {
		this.a(bkk.rl);
		this.a.accept(in.a(bvs.mg).a(io.a(cfz.ag).a(integer -> iq.a().a(ir.c, this.a(bvs.mg, "_stage" + integer, ix.S, iy::c)))));
	}

	private void ac() {
		this.a(bkk.kM);
		this.a
			.accept(
				in.a(bvs.em)
					.a(
						io.a(cfz.a, cfz.J, cfz.I, cfz.K, cfz.L)
							.a(false, false, false, false, false, iq.a().a(ir.c, iv.a(bvs.em, "_ns")))
							.a(false, true, false, false, false, iq.a().a(ir.c, iv.a(bvs.em, "_n")).a(ir.b, ir.a.R90))
							.a(false, false, true, false, false, iq.a().a(ir.c, iv.a(bvs.em, "_n")))
							.a(false, false, false, true, false, iq.a().a(ir.c, iv.a(bvs.em, "_n")).a(ir.b, ir.a.R180))
							.a(false, false, false, false, true, iq.a().a(ir.c, iv.a(bvs.em, "_n")).a(ir.b, ir.a.R270))
							.a(false, true, true, false, false, iq.a().a(ir.c, iv.a(bvs.em, "_ne")))
							.a(false, true, false, true, false, iq.a().a(ir.c, iv.a(bvs.em, "_ne")).a(ir.b, ir.a.R90))
							.a(false, false, false, true, true, iq.a().a(ir.c, iv.a(bvs.em, "_ne")).a(ir.b, ir.a.R180))
							.a(false, false, true, false, true, iq.a().a(ir.c, iv.a(bvs.em, "_ne")).a(ir.b, ir.a.R270))
							.a(false, false, true, true, false, iq.a().a(ir.c, iv.a(bvs.em, "_ns")))
							.a(false, true, false, false, true, iq.a().a(ir.c, iv.a(bvs.em, "_ns")).a(ir.b, ir.a.R90))
							.a(false, true, true, true, false, iq.a().a(ir.c, iv.a(bvs.em, "_nse")))
							.a(false, true, false, true, true, iq.a().a(ir.c, iv.a(bvs.em, "_nse")).a(ir.b, ir.a.R90))
							.a(false, false, true, true, true, iq.a().a(ir.c, iv.a(bvs.em, "_nse")).a(ir.b, ir.a.R180))
							.a(false, true, true, false, true, iq.a().a(ir.c, iv.a(bvs.em, "_nse")).a(ir.b, ir.a.R270))
							.a(false, true, true, true, true, iq.a().a(ir.c, iv.a(bvs.em, "_nsew")))
							.a(true, false, false, false, false, iq.a().a(ir.c, iv.a(bvs.em, "_attached_ns")))
							.a(true, false, true, false, false, iq.a().a(ir.c, iv.a(bvs.em, "_attached_n")))
							.a(true, false, false, true, false, iq.a().a(ir.c, iv.a(bvs.em, "_attached_n")).a(ir.b, ir.a.R180))
							.a(true, true, false, false, false, iq.a().a(ir.c, iv.a(bvs.em, "_attached_n")).a(ir.b, ir.a.R90))
							.a(true, false, false, false, true, iq.a().a(ir.c, iv.a(bvs.em, "_attached_n")).a(ir.b, ir.a.R270))
							.a(true, true, true, false, false, iq.a().a(ir.c, iv.a(bvs.em, "_attached_ne")))
							.a(true, true, false, true, false, iq.a().a(ir.c, iv.a(bvs.em, "_attached_ne")).a(ir.b, ir.a.R90))
							.a(true, false, false, true, true, iq.a().a(ir.c, iv.a(bvs.em, "_attached_ne")).a(ir.b, ir.a.R180))
							.a(true, false, true, false, true, iq.a().a(ir.c, iv.a(bvs.em, "_attached_ne")).a(ir.b, ir.a.R270))
							.a(true, false, true, true, false, iq.a().a(ir.c, iv.a(bvs.em, "_attached_ns")))
							.a(true, true, false, false, true, iq.a().a(ir.c, iv.a(bvs.em, "_attached_ns")).a(ir.b, ir.a.R90))
							.a(true, true, true, true, false, iq.a().a(ir.c, iv.a(bvs.em, "_attached_nse")))
							.a(true, true, false, true, true, iq.a().a(ir.c, iv.a(bvs.em, "_attached_nse")).a(ir.b, ir.a.R90))
							.a(true, false, true, true, true, iq.a().a(ir.c, iv.a(bvs.em, "_attached_nse")).a(ir.b, ir.a.R180))
							.a(true, true, true, false, true, iq.a().a(ir.c, iv.a(bvs.em, "_attached_nse")).a(ir.b, ir.a.R270))
							.a(true, true, true, true, true, iq.a().a(ir.c, iv.a(bvs.em, "_attached_nsew")))
					)
			);
	}

	private void ad() {
		this.b(bvs.el);
		this.a
			.accept(
				in.a(bvs.el).a(io.a(cfz.a, cfz.w).a((boolean1, boolean2) -> iq.a().a(ir.c, iy.a(bvs.el, (boolean1 ? "_attached" : "") + (boolean2 ? "_on" : ""))))).a(b())
			);
	}

	private uh a(int integer, String string, iy iy) {
		switch (integer) {
			case 1:
				return ix.aF.a(iv.a(string + "turtle_egg"), iy, this.b);
			case 2:
				return ix.aG.a(iv.a("two_" + string + "turtle_eggs"), iy, this.b);
			case 3:
				return ix.aH.a(iv.a("three_" + string + "turtle_eggs"), iy, this.b);
			case 4:
				return ix.aI.a(iv.a("four_" + string + "turtle_eggs"), iy, this.b);
			default:
				throw new UnsupportedOperationException();
		}
	}

	private uh a(Integer integer1, Integer integer2) {
		switch (integer2) {
			case 0:
				return this.a(integer1.intValue(), "", iy.b(iy.C(bvs.kf)));
			case 1:
				return this.a(integer1.intValue(), "slightly_cracked_", iy.b(iy.a(bvs.kf, "_slightly_cracked")));
			case 2:
				return this.a(integer1.intValue(), "very_cracked_", iy.b(iy.a(bvs.kf, "_very_cracked")));
			default:
				throw new UnsupportedOperationException();
		}
	}

	private void ae() {
		this.a(bkk.iC);
		this.a.accept(in.a(bvs.kf).a(io.a(cfz.ao, cfz.ap).b((integer1, integer2) -> Arrays.asList(a(this.a(integer1, integer2))))));
	}

	private void af() {
		this.b(bvs.dP);
		this.a
			.accept(
				in.a(bvs.dP)
					.a(
						io.a(cfz.J, cfz.I, cfz.K, cfz.G, cfz.L)
							.a(false, false, false, false, false, iq.a().a(ir.c, iv.a(bvs.dP, "_1")))
							.a(false, false, true, false, false, iq.a().a(ir.c, iv.a(bvs.dP, "_1")))
							.a(false, false, false, false, true, iq.a().a(ir.c, iv.a(bvs.dP, "_1")).a(ir.b, ir.a.R90))
							.a(false, true, false, false, false, iq.a().a(ir.c, iv.a(bvs.dP, "_1")).a(ir.b, ir.a.R180))
							.a(true, false, false, false, false, iq.a().a(ir.c, iv.a(bvs.dP, "_1")).a(ir.b, ir.a.R270))
							.a(true, true, false, false, false, iq.a().a(ir.c, iv.a(bvs.dP, "_2")))
							.a(true, false, true, false, false, iq.a().a(ir.c, iv.a(bvs.dP, "_2")).a(ir.b, ir.a.R90))
							.a(false, false, true, false, true, iq.a().a(ir.c, iv.a(bvs.dP, "_2")).a(ir.b, ir.a.R180))
							.a(false, true, false, false, true, iq.a().a(ir.c, iv.a(bvs.dP, "_2")).a(ir.b, ir.a.R270))
							.a(true, false, false, false, true, iq.a().a(ir.c, iv.a(bvs.dP, "_2_opposite")))
							.a(false, true, true, false, false, iq.a().a(ir.c, iv.a(bvs.dP, "_2_opposite")).a(ir.b, ir.a.R90))
							.a(true, true, true, false, false, iq.a().a(ir.c, iv.a(bvs.dP, "_3")))
							.a(true, false, true, false, true, iq.a().a(ir.c, iv.a(bvs.dP, "_3")).a(ir.b, ir.a.R90))
							.a(false, true, true, false, true, iq.a().a(ir.c, iv.a(bvs.dP, "_3")).a(ir.b, ir.a.R180))
							.a(true, true, false, false, true, iq.a().a(ir.c, iv.a(bvs.dP, "_3")).a(ir.b, ir.a.R270))
							.a(true, true, true, false, true, iq.a().a(ir.c, iv.a(bvs.dP, "_4")))
							.a(false, false, false, true, false, iq.a().a(ir.c, iv.a(bvs.dP, "_u")))
							.a(false, false, true, true, false, iq.a().a(ir.c, iv.a(bvs.dP, "_1u")))
							.a(false, false, false, true, true, iq.a().a(ir.c, iv.a(bvs.dP, "_1u")).a(ir.b, ir.a.R90))
							.a(false, true, false, true, false, iq.a().a(ir.c, iv.a(bvs.dP, "_1u")).a(ir.b, ir.a.R180))
							.a(true, false, false, true, false, iq.a().a(ir.c, iv.a(bvs.dP, "_1u")).a(ir.b, ir.a.R270))
							.a(true, true, false, true, false, iq.a().a(ir.c, iv.a(bvs.dP, "_2u")))
							.a(true, false, true, true, false, iq.a().a(ir.c, iv.a(bvs.dP, "_2u")).a(ir.b, ir.a.R90))
							.a(false, false, true, true, true, iq.a().a(ir.c, iv.a(bvs.dP, "_2u")).a(ir.b, ir.a.R180))
							.a(false, true, false, true, true, iq.a().a(ir.c, iv.a(bvs.dP, "_2u")).a(ir.b, ir.a.R270))
							.a(true, false, false, true, true, iq.a().a(ir.c, iv.a(bvs.dP, "_2u_opposite")))
							.a(false, true, true, true, false, iq.a().a(ir.c, iv.a(bvs.dP, "_2u_opposite")).a(ir.b, ir.a.R90))
							.a(true, true, true, true, false, iq.a().a(ir.c, iv.a(bvs.dP, "_3u")))
							.a(true, false, true, true, true, iq.a().a(ir.c, iv.a(bvs.dP, "_3u")).a(ir.b, ir.a.R90))
							.a(false, true, true, true, true, iq.a().a(ir.c, iv.a(bvs.dP, "_3u")).a(ir.b, ir.a.R180))
							.a(true, true, false, true, true, iq.a().a(ir.c, iv.a(bvs.dP, "_3u")).a(ir.b, ir.a.R270))
							.a(true, true, true, true, true, iq.a().a(ir.c, iv.a(bvs.dP, "_4u")))
					)
			);
	}

	private void ag() {
		this.a.accept(e(bvs.iJ, ix.c.a(bvs.iJ, iy.b(iv.a("magma")), this.b)));
	}

	private void x(bvr bvr) {
		this.c(bvr, ja.l);
		ix.aN.a(iv.a(bvr.h()), iy.q(bvr), this.b);
	}

	private void b(bvr bvr1, bvr bvr2, ih.c c) {
		this.b(bvr1, c);
		this.b(bvr2, c);
	}

	private void k(bvr bvr1, bvr bvr2) {
		ix.aO.a(iv.a(bvr1.h()), iy.q(bvr2), this.b);
	}

	private void ah() {
		uh uh2 = iv.a(bvs.b);
		uh uh3 = iv.a(bvs.b, "_mirrored");
		this.a.accept(e(bvs.dy, uh2, uh3));
		this.c(bvs.dy, uh2);
	}

	private void l(bvr bvr1, bvr bvr2) {
		this.a(bvr1, ih.c.NOT_TINTED);
		iy iy4 = iy.d(iy.a(bvr1, "_pot"));
		uh uh5 = ih.c.NOT_TINTED.b().a(bvr2, iy4, this.b);
		this.a.accept(e(bvr2, uh5));
	}

	private void ai() {
		uh uh2 = iy.a(bvs.nj, "_bottom");
		uh uh3 = iy.a(bvs.nj, "_top_off");
		uh uh4 = iy.a(bvs.nj, "_top");
		uh[] arr5 = new uh[5];

		for (int integer6 = 0; integer6 < 5; integer6++) {
			iy iy7 = new iy().a(iz.e, uh2).a(iz.f, integer6 == 0 ? uh3 : uh4).a(iz.i, iy.a(bvs.nj, "_side" + integer6));
			arr5[integer6] = ix.h.a(bvs.nj, "_" + integer6, iy7, this.b);
		}

		this.a.accept(in.a(bvs.nj).a(io.a(cfz.aC).a(integer -> iq.a().a(ir.c, arr5[integer]))));
		this.a(bkk.rM, arr5[0]);
	}

	private iq a(gb gb, iq iq) {
		switch (gb) {
			case DOWN_NORTH:
				return iq.a(ir.a, ir.a.R90);
			case DOWN_SOUTH:
				return iq.a(ir.a, ir.a.R90).a(ir.b, ir.a.R180);
			case DOWN_WEST:
				return iq.a(ir.a, ir.a.R90).a(ir.b, ir.a.R270);
			case DOWN_EAST:
				return iq.a(ir.a, ir.a.R90).a(ir.b, ir.a.R90);
			case UP_NORTH:
				return iq.a(ir.a, ir.a.R270).a(ir.b, ir.a.R180);
			case UP_SOUTH:
				return iq.a(ir.a, ir.a.R270);
			case UP_WEST:
				return iq.a(ir.a, ir.a.R270).a(ir.b, ir.a.R90);
			case UP_EAST:
				return iq.a(ir.a, ir.a.R270).a(ir.b, ir.a.R270);
			case NORTH_UP:
				return iq;
			case SOUTH_UP:
				return iq.a(ir.b, ir.a.R180);
			case WEST_UP:
				return iq.a(ir.b, ir.a.R270);
			case EAST_UP:
				return iq.a(ir.b, ir.a.R90);
			default:
				throw new UnsupportedOperationException("Rotation " + gb + " can't be expressed with existing x and y values");
		}
	}

	private void aj() {
		uh uh2 = iy.a(bvs.mZ, "_top");
		uh uh3 = iy.a(bvs.mZ, "_bottom");
		uh uh4 = iy.a(bvs.mZ, "_side");
		uh uh5 = iy.a(bvs.mZ, "_lock");
		iy iy6 = new iy().a(iz.o, uh4).a(iz.m, uh4).a(iz.l, uh4).a(iz.c, uh2).a(iz.j, uh2).a(iz.k, uh3).a(iz.n, uh5);
		uh uh7 = ix.b.a(bvs.mZ, iy6, this.b);
		this.a.accept(in.a(bvs.mZ, iq.a().a(ir.c, uh7)).a(io.a(cfz.P).a(gb -> this.a(gb, iq.a()))));
	}

	public void a() {
		this.k(bvs.a);
		this.a(bvs.lb, bvs.a);
		this.a(bvs.la, bvs.a);
		this.k(bvs.es);
		this.k(bvs.cF);
		this.a(bvs.lc, bvs.A);
		this.k(bvs.ef);
		this.k(bvs.ke);
		this.k(bvs.dZ);
		this.k(bvs.ev);
		this.a(bkk.oW);
		this.k(bvs.ne);
		this.k(bvs.A);
		this.k(bvs.B);
		this.k(bvs.gn);
		this.k(bvs.dI);
		this.a(bkk.dO);
		this.k(bvs.kZ);
		this.k(bvs.eT);
		this.a(bvs.go, bkk.fJ);
		this.a(bkk.fJ);
		this.a(bvs.iN, bkk.hn);
		this.a(bkk.hn);
		this.g(bvs.bo, iy.a(bvs.aW, "_side"));
		this.c(bvs.H, ja.a);
		this.c(bvs.gS, ja.a);
		this.c(bvs.bT, ja.a);
		this.c(bvs.bU, ja.a);
		this.c(bvs.ej, ja.a);
		this.c(bvs.en, ja.a);
		this.c(bvs.F, ja.a);
		this.c(bvs.I, ja.a);
		this.c(bvs.bE, ja.a);
		this.c(bvs.G, ja.a);
		this.c(bvs.bF, ja.a);
		this.c(bvs.nh, ja.c);
		this.c(bvs.ng, ja.a);
		this.c(bvs.aq, ja.a);
		this.c(bvs.ar, ja.a);
		this.c(bvs.fx, ja.a);
		this.c(bvs.cy, ja.a);
		this.c(bvs.fw, ja.a);
		this.c(bvs.nA, ja.a);
		this.c(bvs.kV, ja.a);
		this.c(bvs.nG, ja.a);
		this.c(bvs.cG, ja.a);
		this.c(bvs.k, ja.a);
		this.c(bvs.nH, ja.a);
		this.c(bvs.dw, ja.a);
		this.c(bvs.ni, ja.a);
		this.c(bvs.ee, ja.a);
		this.c(bvs.cS, ja.a);
		this.c(bvs.E, ja.a);
		this.c(bvs.nf, ja.a);
		this.c(bvs.cD, ja.a);
		this.c(bvs.cI, ja.f);
		this.c(bvs.no, ja.c);
		this.c(bvs.dK, ja.c);
		this.c(bvs.iK, ja.a);
		this.c(bvs.aw, ja.a);
		this.c(bvs.gT, ja.a);
		this.c(bvs.bK, ja.a);
		this.c(bvs.nI, ja.a);
		this.c(bvs.gz, ja.a);
		this.c(bvs.mw, ja.a);
		this.c(bvs.cM, ja.a);
		this.c(bvs.cN, ja.a);
		this.c(bvs.bP, ja.a);
		this.c(bvs.an, ja.a);
		this.c(bvs.aU, ja.q);
		this.a(bkk.aO);
		this.c(bvs.bH, ja.e);
		this.c(bvs.nb, ja.c);
		this.c(bvs.mn, ja.a);
		this.c(bvs.ao, ja.a);
		this.c(bvs.nv, ja.a);
		this.c(bvs.fA, ja.c.a(iy -> iy.a(iz.i, iy.C(bvs.fA))));
		this.c(bvs.dx, ja.a);
		this.g(bvs.au, bvs.at);
		this.g(bvs.hH, bvs.hG);
		this.c(bvs.nw, ja.a);
		this.h(bvs.fs, bvs.bE);
		this.h(bvs.ft, bvs.bF);
		this.n();
		this.r();
		this.s();
		this.a(bvs.me, bvs.mf);
		this.t();
		this.w();
		this.x();
		this.z();
		this.A();
		this.B();
		this.y();
		this.C();
		this.D();
		this.E();
		this.F();
		this.G();
		this.H();
		this.I();
		this.J();
		this.m();
		this.K();
		this.L();
		this.M();
		this.N();
		this.O();
		this.P();
		this.Q();
		this.R();
		this.S();
		this.T();
		this.V();
		this.U();
		this.W();
		this.X();
		this.u();
		this.Y();
		this.Z();
		this.aa();
		this.ab();
		this.ac();
		this.ad();
		this.ae();
		this.af();
		this.ag();
		this.aj();
		this.w(bvs.cg);
		this.b(bvs.cg);
		this.w(bvs.lY);
		this.j(bvs.bL, bvs.bM);
		this.j(bvs.cQ, bvs.cR);
		this.a(bvs.bV, bvs.n, iy::c);
		this.a(bvs.lW, bvs.p, iy::d);
		this.r(bvs.mu);
		this.r(bvs.ml);
		this.q(bvs.as);
		this.q(bvs.fE);
		this.v(bvs.mc);
		this.v(bvs.md);
		this.a(bvs.cO, ja.c);
		this.a(bvs.cP, ja.c);
		this.a(bvs.iM, ja.c);
		this.d(bvs.j);
		this.d(bvs.C);
		this.d(bvs.D);
		this.c(bvs.z);
		this.a(bvs.gA, ja.c, ja.d);
		this.a(bvs.iA, ja.r, ja.s);
		this.a(bvs.fB, ja.r, ja.s);
		this.b(bvs.lR, ja.h);
		this.v();
		this.a(bvs.nc, iy::w);
		this.a(bvs.nd, iy::y);
		this.a(bvs.iD, cfz.ag, 0, 1, 2, 3);
		this.a(bvs.eU, cfz.ai, 0, 0, 1, 1, 2, 2, 2, 3);
		this.a(bvs.dY, cfz.ag, 0, 1, 1, 2);
		this.a(bvs.eV, cfz.ai, 0, 0, 1, 1, 2, 2, 2, 3);
		this.a(bvs.bW, cfz.ai, 0, 1, 2, 3, 4, 5, 6, 7);
		this.a(iv.a("banner"), bvs.n)
			.a(ix.aP, bvs.ha, bvs.hb, bvs.hc, bvs.hd, bvs.he, bvs.hf, bvs.hg, bvs.hh, bvs.hi, bvs.hj, bvs.hk, bvs.hl, bvs.hm, bvs.hn, bvs.ho, bvs.hp)
			.b(bvs.hq, bvs.hr, bvs.hs, bvs.ht, bvs.hu, bvs.hv, bvs.hw, bvs.hx, bvs.hy, bvs.hz, bvs.hA, bvs.hB, bvs.hC, bvs.hD, bvs.hE, bvs.hF);
		this.a(iv.a("bed"), bvs.n).b(bvs.ax, bvs.ay, bvs.az, bvs.aA, bvs.aB, bvs.aC, bvs.aD, bvs.aE, bvs.aF, bvs.aG, bvs.aH, bvs.aI, bvs.aJ, bvs.aK, bvs.aL, bvs.aM);
		this.k(bvs.ax, bvs.aY);
		this.k(bvs.ay, bvs.aZ);
		this.k(bvs.az, bvs.ba);
		this.k(bvs.aA, bvs.bb);
		this.k(bvs.aB, bvs.bc);
		this.k(bvs.aC, bvs.bd);
		this.k(bvs.aD, bvs.be);
		this.k(bvs.aE, bvs.bf);
		this.k(bvs.aF, bvs.bg);
		this.k(bvs.aG, bvs.bh);
		this.k(bvs.aH, bvs.bi);
		this.k(bvs.aI, bvs.bj);
		this.k(bvs.aJ, bvs.bk);
		this.k(bvs.aK, bvs.bl);
		this.k(bvs.aL, bvs.bm);
		this.k(bvs.aM, bvs.bn);
		this.a(iv.a("skull"), bvs.cM).a(ix.aQ, bvs.fk, bvs.fi, bvs.fg, bvs.fc, bvs.fe).a(bvs.fm).b(bvs.fl, bvs.fn, bvs.fj, bvs.fh, bvs.fd, bvs.ff);
		this.x(bvs.iP);
		this.x(bvs.iQ);
		this.x(bvs.iR);
		this.x(bvs.iS);
		this.x(bvs.iT);
		this.x(bvs.iU);
		this.x(bvs.iV);
		this.x(bvs.iW);
		this.x(bvs.iX);
		this.x(bvs.iY);
		this.x(bvs.iZ);
		this.x(bvs.ja);
		this.x(bvs.jb);
		this.x(bvs.jc);
		this.x(bvs.jd);
		this.x(bvs.je);
		this.x(bvs.jf);
		this.c(bvs.kW, ja.l);
		this.a(bvs.kW);
		this.a(iv.a("chest"), bvs.n).b(bvs.bR, bvs.fr);
		this.a(iv.a("ender_chest"), bvs.bK).b(bvs.ek);
		this.d(bvs.ec, bvs.bK).a(bvs.ec, bvs.iF);
		this.e(bvs.jw);
		this.e(bvs.jx);
		this.e(bvs.jy);
		this.e(bvs.jz);
		this.e(bvs.jA);
		this.e(bvs.jB);
		this.e(bvs.jC);
		this.e(bvs.jD);
		this.e(bvs.jE);
		this.e(bvs.jF);
		this.e(bvs.jG);
		this.e(bvs.jH);
		this.e(bvs.jI);
		this.e(bvs.jJ);
		this.e(bvs.jK);
		this.e(bvs.jL);
		this.a(ja.a, bvs.jM, bvs.jN, bvs.jO, bvs.jP, bvs.jQ, bvs.jR, bvs.jS, bvs.jT, bvs.jU, bvs.jV, bvs.jW, bvs.jX, bvs.jY, bvs.jZ, bvs.ka, bvs.kb);
		this.e(bvs.gR);
		this.e(bvs.fF);
		this.e(bvs.fG);
		this.e(bvs.fH);
		this.e(bvs.fI);
		this.e(bvs.fJ);
		this.e(bvs.fK);
		this.e(bvs.fL);
		this.e(bvs.fM);
		this.e(bvs.fN);
		this.e(bvs.fO);
		this.e(bvs.fP);
		this.e(bvs.fQ);
		this.e(bvs.fR);
		this.e(bvs.fS);
		this.e(bvs.fT);
		this.e(bvs.fU);
		this.f(bvs.ap, bvs.dJ);
		this.f(bvs.cY, bvs.fV);
		this.f(bvs.cZ, bvs.fW);
		this.f(bvs.da, bvs.fX);
		this.f(bvs.db, bvs.fY);
		this.f(bvs.dc, bvs.fZ);
		this.f(bvs.dd, bvs.ga);
		this.f(bvs.de, bvs.gb);
		this.f(bvs.df, bvs.gc);
		this.f(bvs.dg, bvs.gd);
		this.f(bvs.dh, bvs.ge);
		this.f(bvs.di, bvs.gf);
		this.f(bvs.dj, bvs.gg);
		this.f(bvs.dk, bvs.gh);
		this.f(bvs.dl, bvs.gi);
		this.f(bvs.dm, bvs.gj);
		this.f(bvs.dn, bvs.gk);
		this.b(ja.j, bvs.jg, bvs.jh, bvs.ji, bvs.jj, bvs.jk, bvs.jl, bvs.jm, bvs.jn, bvs.jo, bvs.jp, bvs.jq, bvs.jr, bvs.js, bvs.jt, bvs.ju, bvs.jv);
		this.e(bvs.aY, bvs.gB);
		this.e(bvs.aZ, bvs.gC);
		this.e(bvs.ba, bvs.gD);
		this.e(bvs.bb, bvs.gE);
		this.e(bvs.bc, bvs.gF);
		this.e(bvs.bd, bvs.gG);
		this.e(bvs.be, bvs.gH);
		this.e(bvs.bf, bvs.gI);
		this.e(bvs.bg, bvs.gJ);
		this.e(bvs.bh, bvs.gK);
		this.e(bvs.bi, bvs.gL);
		this.e(bvs.bj, bvs.gM);
		this.e(bvs.bk, bvs.gN);
		this.e(bvs.bl, bvs.gO);
		this.e(bvs.bm, bvs.gP);
		this.e(bvs.bn, bvs.gQ);
		this.a(bvs.aS, bvs.eC, ih.c.TINTED);
		this.a(bvs.bp, bvs.eD, ih.c.NOT_TINTED);
		this.a(bvs.bq, bvs.eE, ih.c.NOT_TINTED);
		this.a(bvs.br, bvs.eF, ih.c.NOT_TINTED);
		this.a(bvs.bs, bvs.eG, ih.c.NOT_TINTED);
		this.a(bvs.bt, bvs.eH, ih.c.NOT_TINTED);
		this.a(bvs.bu, bvs.eI, ih.c.NOT_TINTED);
		this.a(bvs.bv, bvs.eJ, ih.c.NOT_TINTED);
		this.a(bvs.bw, bvs.eK, ih.c.NOT_TINTED);
		this.a(bvs.bx, bvs.eL, ih.c.NOT_TINTED);
		this.a(bvs.by, bvs.eM, ih.c.NOT_TINTED);
		this.a(bvs.bz, bvs.eN, ih.c.NOT_TINTED);
		this.a(bvs.bB, bvs.eO, ih.c.NOT_TINTED);
		this.a(bvs.bA, bvs.eP, ih.c.NOT_TINTED);
		this.a(bvs.bD, bvs.eQ, ih.c.NOT_TINTED);
		this.a(bvs.bC, bvs.eR, ih.c.NOT_TINTED);
		this.a(bvs.aT, bvs.eS, ih.c.NOT_TINTED);
		this.p(bvs.dE);
		this.p(bvs.dF);
		this.p(bvs.dG);
		this.a(bvs.aR, ih.c.TINTED);
		this.b(bvs.cH, ih.c.TINTED);
		this.a(bkk.bD);
		this.b(bvs.kc, bvs.kd, ih.c.TINTED);
		this.a(bkk.bE);
		this.a(bvs.kd);
		this.b(bvs.mx, bvs.my, ih.c.NOT_TINTED);
		this.b(bvs.mz, bvs.mA, ih.c.NOT_TINTED);
		this.a(bvs.mx, "_plant");
		this.a(bvs.my);
		this.a(bvs.mz, "_plant");
		this.a(bvs.mA);
		this.a(bvs.kX, ih.c.TINTED, iy.c(iy.a(bvs.kY, "_stage0")));
		this.i();
		this.a(bvs.aQ, ih.c.NOT_TINTED);
		this.c(bvs.gV, ih.c.NOT_TINTED);
		this.c(bvs.gW, ih.c.NOT_TINTED);
		this.c(bvs.gX, ih.c.NOT_TINTED);
		this.c(bvs.gY, ih.c.TINTED);
		this.c(bvs.gZ, ih.c.TINTED);
		this.g();
		this.h();
		this.a(bvs.kv, bvs.kq, bvs.kl, bvs.kg, bvs.kF, bvs.kA, bvs.kP, bvs.kK);
		this.a(bvs.kw, bvs.kr, bvs.km, bvs.kh, bvs.kG, bvs.kB, bvs.kQ, bvs.kL);
		this.a(bvs.kx, bvs.ks, bvs.kn, bvs.ki, bvs.kH, bvs.kC, bvs.kR, bvs.kM);
		this.a(bvs.ky, bvs.kt, bvs.ko, bvs.kj, bvs.kI, bvs.kD, bvs.kS, bvs.kN);
		this.a(bvs.kz, bvs.ku, bvs.kp, bvs.kk, bvs.kJ, bvs.kE, bvs.kT, bvs.kO);
		this.c(bvs.dO, bvs.dM);
		this.c(bvs.dN, bvs.dL);
		this.f(bvs.r).a(bvs.fa).c(bvs.ip).d(bvs.ik).e(bvs.cw).a(bvs.cc, bvs.cm).f(bvs.hO).g(bvs.gl);
		this.g(bvs.iu);
		this.h(bvs.ds);
		this.j(bvs.N).c(bvs.N).a(bvs.Z);
		this.j(bvs.S).c(bvs.S).a(bvs.af);
		this.a(bvs.x, bvs.eA, ih.c.NOT_TINTED);
		this.c(bvs.al, ja.n);
		this.f(bvs.p).a(bvs.eY).c(bvs.in).d(bvs.ii).e(bvs.cu).a(bvs.cb, bvs.cl).f(bvs.hM).g(bvs.ep);
		this.g(bvs.is);
		this.h(bvs.dq);
		this.j(bvs.L).c(bvs.L).a(bvs.X);
		this.j(bvs.Q).c(bvs.Q).a(bvs.ad);
		this.a(bvs.v, bvs.ey, ih.c.NOT_TINTED);
		this.c(bvs.aj, ja.n);
		this.f(bvs.n).a(bvs.eW).c(bvs.cJ).d(bvs.dQ).e(bvs.cs).a(bvs.bZ, bvs.cj).f(bvs.hK).f(bvs.hU).g(bvs.bQ);
		this.g(bvs.cf);
		this.i(bvs.do);
		this.j(bvs.J).c(bvs.J).a(bvs.V);
		this.j(bvs.U).c(bvs.U).a(bvs.ab);
		this.a(bvs.t, bvs.ew, ih.c.NOT_TINTED);
		this.c(bvs.ah, ja.n);
		this.f(bvs.o).a(bvs.eX).c(bvs.im).d(bvs.ih).e(bvs.ct).a(bvs.ca, bvs.ck).f(bvs.hL).g(bvs.eo);
		this.g(bvs.ir);
		this.h(bvs.dp);
		this.j(bvs.K).c(bvs.K).a(bvs.W);
		this.j(bvs.P).c(bvs.P).a(bvs.ac);
		this.a(bvs.u, bvs.ex, ih.c.NOT_TINTED);
		this.c(bvs.ai, ja.n);
		this.f(bvs.s).a(bvs.fb).c(bvs.iq).d(bvs.il).e(bvs.cx).a(bvs.ce, bvs.co).f(bvs.hP).g(bvs.gm);
		this.g(bvs.iv);
		this.i(bvs.dt);
		this.j(bvs.O).c(bvs.O).a(bvs.aa);
		this.j(bvs.T).c(bvs.T).a(bvs.ag);
		this.a(bvs.y, bvs.eB, ih.c.NOT_TINTED);
		this.c(bvs.am, ja.n);
		this.f(bvs.q).a(bvs.eZ).c(bvs.io).d(bvs.ij).e(bvs.cv).a(bvs.cd, bvs.cn).f(bvs.hN).g(bvs.eq);
		this.g(bvs.it);
		this.h(bvs.dr);
		this.j(bvs.M).c(bvs.M).a(bvs.Y);
		this.j(bvs.R).c(bvs.R).a(bvs.ae);
		this.a(bvs.w, bvs.ez, ih.c.NOT_TINTED);
		this.c(bvs.ak, ja.n);
		this.f(bvs.mC).a(bvs.mQ).c(bvs.mI).d(bvs.mM).e(bvs.mG).a(bvs.mU, bvs.mW).f(bvs.mE).g(bvs.mO);
		this.g(bvs.mS);
		this.h(bvs.mK);
		this.j(bvs.mq).b(bvs.mq).a(bvs.ms);
		this.j(bvs.mr).b(bvs.mr).a(bvs.mt);
		this.a(bvs.mv, bvs.nk, ih.c.NOT_TINTED);
		this.l(bvs.mB, bvs.nm);
		this.f(bvs.mD).a(bvs.mR).c(bvs.mJ).d(bvs.mN).e(bvs.mH).a(bvs.mV, bvs.mX).f(bvs.mF).g(bvs.mP);
		this.g(bvs.mT);
		this.h(bvs.mL);
		this.j(bvs.mh).b(bvs.mh).a(bvs.mj);
		this.j(bvs.mi).b(bvs.mi).a(bvs.mk);
		this.a(bvs.mm, bvs.nl, ih.c.NOT_TINTED);
		this.l(bvs.mo, bvs.nn);
		this.b(bvs.mp, ih.c.NOT_TINTED);
		this.a(bkk.bA);
		this.a(iy.a(bvs.b)).a(iy -> {
			uh uh3 = ix.c.a(bvs.b, iy, this.b);
			uh uh4 = ix.d.a(bvs.b, iy, this.b);
			this.a.accept(e(bvs.b, uh3, uh4));
			return uh3;
		}).f(bvs.hQ).e(bvs.cq).a(bvs.cB).g(bvs.lj);
		this.g(bvs.cr);
		this.i(bvs.gp);
		this.f(bvs.du).b(bvs.lJ).g(bvs.dS).f(bvs.hX);
		this.f(bvs.dv).b(bvs.lH).g(bvs.lf).f(bvs.lt);
		this.f(bvs.m).b(bvs.et).g(bvs.ci).f(bvs.hV);
		this.f(bvs.bJ).b(bvs.eu).g(bvs.lh).f(bvs.lv);
		this.f(bvs.gq).b(bvs.lF).g(bvs.gt).f(bvs.gw);
		this.f(bvs.gr).g(bvs.gu).f(bvs.gx);
		this.f(bvs.gs).g(bvs.gv).f(bvs.gy);
		this.d(bvs.at, ja.t).b(bvs.lN).g(bvs.ei).f(bvs.hS);
		this.a(bvs.ie, ja.a(iy.a(bvs.at, "_top"))).f(bvs.lx).g(bvs.lk);
		this.a(bvs.av, ja.c.get(bvs.at).a(iy -> iy.a(iz.i, iy.C(bvs.av)))).f(bvs.hT);
		this.d(bvs.hG, ja.t).b(bvs.lG).g(bvs.hJ).f(bvs.ia);
		this.a(bvs.ig, ja.a(iy.a(bvs.hG, "_top"))).f(bvs.ls).g(bvs.le);
		this.a(bvs.hI, ja.c.get(bvs.hG).a(iy -> iy.a(iz.i, iy.C(bvs.hI)))).f(bvs.ib);
		this.f(bvs.bG).b(bvs.lE).g(bvs.dR).f(bvs.hW);
		this.f(bvs.dV).c(bvs.dW).b(bvs.lK).g(bvs.dX).f(bvs.hY);
		this.f(bvs.iz).g(bvs.iB).f(bvs.ic);
		this.f(bvs.e).b(bvs.lP).g(bvs.lq).f(bvs.lD);
		this.f(bvs.f).g(bvs.lg).f(bvs.lu);
		this.f(bvs.c).b(bvs.lI).g(bvs.lm).f(bvs.lz);
		this.f(bvs.d).g(bvs.ld).f(bvs.lr);
		this.f(bvs.g).b(bvs.lL).g(bvs.ln).f(bvs.lA);
		this.f(bvs.h).g(bvs.lp).f(bvs.lC);
		this.f(bvs.iC).b(bvs.lO).g(bvs.li).f(bvs.lw);
		this.d(bvs.fz, ja.c).g(bvs.fC).f(bvs.hZ);
		this.a(bvs.if, ja.a(iy.a(bvs.fz, "_bottom"))).g(bvs.ll).f(bvs.ly);
		this.f(bvs.iL).f(bvs.lB).g(bvs.lo).b(bvs.lM);
		this.d(bvs.np, ja.u).b(bvs.nr).g(bvs.nq).f(bvs.ns);
		this.f(bvs.nu).b(bvs.nz).g(bvs.ny).f(bvs.nx);
		this.f(bvs.nt).b(bvs.nF).e(bvs.nD).a(bvs.nE).g(bvs.nB).f(bvs.nC);
		this.q();
		this.l(bvs.ch);
		this.m(bvs.aN);
		this.m(bvs.aO);
		this.m(bvs.fD);
		this.p();
		this.n(bvs.er);
		this.n(bvs.iG);
		this.n(bvs.iH);
		this.o(bvs.fo);
		this.o(bvs.fp);
		this.o(bvs.fq);
		this.k();
		this.l();
		this.e(bvs.bY, ja.g);
		this.e(bvs.lU, ja.g);
		this.e(bvs.lT, ja.h);
		this.o();
		this.ai();
		this.i(bvs.dx, bvs.dD);
		this.i(bvs.m, bvs.dz);
		this.i(bvs.dw, bvs.dC);
		this.i(bvs.dv, bvs.dB);
		this.ah();
		this.i(bvs.du, bvs.dA);
		blh.f().forEach(blh -> this.a(blh, iv.b("template_spawn_egg")));
	}

	class a {
		private final uh b;

		public a(uh uh, bvr bvr) {
			this.b = ix.F.a(uh, iy.q(bvr), ih.this.b);
		}

		public ih.a a(bvr... arr) {
			for (bvr bvr6 : arr) {
				ih.this.a.accept(ih.e(bvr6, this.b));
			}

			return this;
		}

		public ih.a b(bvr... arr) {
			for (bvr bvr6 : arr) {
				ih.this.a(bvr6);
			}

			return this.a(arr);
		}

		public ih.a a(iw iw, bvr... arr) {
			for (bvr bvr7 : arr) {
				iw.a(iv.a(bvr7.h()), iy.q(bvr7), ih.this.b);
			}

			return this.a(arr);
		}
	}

	class b {
		private final iy b;
		@Nullable
		private uh c;

		public b(iy iy) {
			this.b = iy;
		}

		public ih.b a(bvr bvr, iw iw) {
			this.c = iw.a(bvr, this.b, ih.this.b);
			ih.this.a.accept(ih.e(bvr, this.c));
			return this;
		}

		public ih.b a(Function<iy, uh> function) {
			this.c = (uh)function.apply(this.b);
			return this;
		}

		public ih.b a(bvr bvr) {
			uh uh3 = ix.l.a(bvr, this.b, ih.this.b);
			uh uh4 = ix.m.a(bvr, this.b, ih.this.b);
			ih.this.a.accept(ih.f(bvr, uh3, uh4));
			uh uh5 = ix.n.a(bvr, this.b, ih.this.b);
			ih.this.c(bvr, uh5);
			return this;
		}

		public ih.b b(bvr bvr) {
			uh uh3 = ix.v.a(bvr, this.b, ih.this.b);
			uh uh4 = ix.w.a(bvr, this.b, ih.this.b);
			uh uh5 = ix.x.a(bvr, this.b, ih.this.b);
			ih.this.a.accept(ih.d(bvr, uh3, uh4, uh5));
			uh uh6 = ix.y.a(bvr, this.b, ih.this.b);
			ih.this.c(bvr, uh6);
			return this;
		}

		public ih.b c(bvr bvr) {
			uh uh3 = ix.s.a(bvr, this.b, ih.this.b);
			uh uh4 = ix.t.a(bvr, this.b, ih.this.b);
			ih.this.a.accept(ih.g(bvr, uh3, uh4));
			uh uh5 = ix.u.a(bvr, this.b, ih.this.b);
			ih.this.c(bvr, uh5);
			return this;
		}

		public ih.b d(bvr bvr) {
			uh uh3 = ix.A.a(bvr, this.b, ih.this.b);
			uh uh4 = ix.z.a(bvr, this.b, ih.this.b);
			uh uh5 = ix.C.a(bvr, this.b, ih.this.b);
			uh uh6 = ix.B.a(bvr, this.b, ih.this.b);
			ih.this.a.accept(ih.c(bvr, uh3, uh4, uh5, uh6));
			return this;
		}

		public ih.b e(bvr bvr) {
			uh uh3 = ix.D.a(bvr, this.b, ih.this.b);
			uh uh4 = ix.E.a(bvr, this.b, ih.this.b);
			ih.this.a.accept(ih.i(bvr, uh3, uh4));
			return this;
		}

		public ih.b a(bvr bvr1, bvr bvr2) {
			uh uh4 = ix.F.a(bvr1, this.b, ih.this.b);
			ih.this.a.accept(ih.e(bvr1, uh4));
			ih.this.a.accept(ih.e(bvr2, uh4));
			ih.this.a(bvr1.h());
			ih.this.a(bvr2);
			return this;
		}

		public ih.b f(bvr bvr) {
			if (this.c == null) {
				throw new IllegalStateException("Full block not generated yet");
			} else {
				uh uh3 = ix.G.a(bvr, this.b, ih.this.b);
				uh uh4 = ix.H.a(bvr, this.b, ih.this.b);
				ih.this.a.accept(ih.h(bvr, uh3, uh4, this.c));
				return this;
			}
		}

		public ih.b g(bvr bvr) {
			uh uh3 = ix.K.a(bvr, this.b, ih.this.b);
			uh uh4 = ix.J.a(bvr, this.b, ih.this.b);
			uh uh5 = ix.L.a(bvr, this.b, ih.this.b);
			ih.this.a.accept(ih.e(bvr, uh3, uh4, uh5));
			return this;
		}
	}

	static enum c {
		TINTED,
		NOT_TINTED;

		public iw a() {
			return this == TINTED ? ix.T : ix.S;
		}

		public iw b() {
			return this == TINTED ? ix.V : ix.U;
		}
	}

	class d {
		private final iy b;

		public d(iy iy) {
			this.b = iy;
		}

		public ih.d a(bvr bvr) {
			iy iy3 = this.b.c(iz.d, this.b.a(iz.i));
			uh uh4 = ix.e.a(bvr, iy3, ih.this.b);
			ih.this.a.accept(ih.f(bvr, uh4));
			return this;
		}

		public ih.d b(bvr bvr) {
			uh uh3 = ix.e.a(bvr, this.b, ih.this.b);
			ih.this.a.accept(ih.f(bvr, uh3));
			return this;
		}

		public ih.d c(bvr bvr) {
			uh uh3 = ix.e.a(bvr, this.b, ih.this.b);
			uh uh4 = ix.f.a(bvr, this.b, ih.this.b);
			ih.this.a.accept(ih.h(bvr, uh3, uh4));
			return this;
		}
	}
}
