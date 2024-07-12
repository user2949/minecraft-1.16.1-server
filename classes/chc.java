import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import javax.annotation.Nullable;

public class chc {
	private static final EnumSet<cio.a> n = EnumSet.of(cio.a.OCEAN_FLOOR_WG, cio.a.WORLD_SURFACE_WG);
	private static final EnumSet<cio.a> o = EnumSet.of(cio.a.OCEAN_FLOOR, cio.a.WORLD_SURFACE, cio.a.MOTION_BLOCKING, cio.a.MOTION_BLOCKING_NO_LEAVES);
	private static final chc.c p = (chc, zd, cva, zg, function, cgy) -> {
		if (cgy instanceof chr && !cgy.k().b(chc)) {
			((chr)cgy).a(chc);
		}

		return CompletableFuture.completedFuture(Either.left(cgy));
	};
	public static final chc a = a("empty", null, -1, n, chc.a.PROTOCHUNK, (zd, cha, list, cgy) -> {
	});
	public static final chc b = a("structure_starts", a, 0, n, chc.a.PROTOCHUNK, (chc, zd, cha, cva, zg, function, list, cgy) -> {
		if (!cgy.k().b(chc)) {
			if (zd.l().aV().z().c()) {
				cha.a(zd.a(), cgy, cva, zd.B());
			}

			if (cgy instanceof chr) {
				((chr)cgy).a(chc);
			}
		}

		return CompletableFuture.completedFuture(Either.left(cgy));
	});
	public static final chc c = a("structure_references", b, 8, n, chc.a.PROTOCHUNK, (zd, cha, list, cgy) -> {
		zj zj5 = new zj(zd, list);
		cha.a(zj5, zd.a().a(zj5), cgy);
	});
	public static final chc d = a("biomes", c, 0, n, chc.a.PROTOCHUNK, (zd, cha, list, cgy) -> cha.a(cgy));
	public static final chc e = a("noise", d, 8, n, chc.a.PROTOCHUNK, (zd, cha, list, cgy) -> {
		zj zj5 = new zj(zd, list);
		cha.b(zj5, zd.a().a(zj5), cgy);
	});
	public static final chc f = a("surface", e, 0, n, chc.a.PROTOCHUNK, (zd, cha, list, cgy) -> cha.a(new zj(zd, list), cgy));
	public static final chc g = a("carvers", f, 0, n, chc.a.PROTOCHUNK, (zd, cha, list, cgy) -> cha.a(zd.B(), zd.d(), cgy, cin.a.AIR));
	public static final chc h = a("liquid_carvers", g, 0, o, chc.a.PROTOCHUNK, (zd, cha, list, cgy) -> cha.a(zd.B(), zd.d(), cgy, cin.a.LIQUID));
	public static final chc i = a("features", h, 8, o, chc.a.PROTOCHUNK, (chc, zd, cha, cva, zg, function, list, cgy) -> {
		chr chr9 = (chr)cgy;
		chr9.a(zg);
		if (!cgy.k().b(chc)) {
			cio.a(cgy, EnumSet.of(cio.a.MOTION_BLOCKING, cio.a.MOTION_BLOCKING_NO_LEAVES, cio.a.OCEAN_FLOOR, cio.a.WORLD_SURFACE));
			zj zj10 = new zj(zd, list);
			cha.a(zj10, zd.a().a(zj10));
			chr9.a(chc);
		}

		return CompletableFuture.completedFuture(Either.left(cgy));
	});
	public static final chc j = a(
		"light", i, 1, o, chc.a.PROTOCHUNK, (chc, zd, cha, cva, zg, function, list, cgy) -> a(chc, zg, cgy), (chc, zd, cva, zg, function, cgy) -> a(chc, zg, cgy)
	);
	public static final chc k = a("spawn", j, 0, o, chc.a.PROTOCHUNK, (zd, cha, list, cgy) -> cha.a(new zj(zd, list)));
	public static final chc l = a("heightmaps", k, 0, o, chc.a.PROTOCHUNK, (zd, cha, list, cgy) -> {
	});
	public static final chc m = a(
		"full",
		l,
		0,
		o,
		chc.a.LEVELCHUNK,
		(chc, zd, cha, cva, zg, function, list, cgy) -> (CompletableFuture<Either<cgy, yo.a>>)function.apply(cgy),
		(chc, zd, cva, zg, function, cgy) -> (CompletableFuture<Either<cgy, yo.a>>)function.apply(cgy)
	);
	private static final List<chc> q = ImmutableList.of(m, i, h, b, b, b, b, b, b, b, b);
	private static final IntList r = v.a(new IntArrayList(a().size()), intArrayList -> {
		int integer2 = 0;

		for (int integer3 = a().size() - 1; integer3 >= 0; integer3--) {
			while (integer2 + 1 < q.size() && integer3 <= ((chc)q.get(integer2 + 1)).c()) {
				integer2++;
			}

			intArrayList.add(0, integer2);
		}
	});
	private final String s;
	private final int t;
	private final chc u;
	private final chc.b v;
	private final chc.c w;
	private final int x;
	private final chc.a y;
	private final EnumSet<cio.a> z;

	private static CompletableFuture<Either<cgy, yo.a>> a(chc chc, zg zg, cgy cgy) {
		boolean boolean4 = a(chc, cgy);
		if (!cgy.k().b(chc)) {
			((chr)cgy).a(chc);
		}

		return zg.a(cgy, boolean4).thenApply(Either::left);
	}

	private static chc a(String string, @Nullable chc chc, int integer, EnumSet<cio.a> enumSet, chc.a a, chc.d d) {
		return a(string, chc, integer, enumSet, a, (chc.b)d);
	}

	private static chc a(String string, @Nullable chc chc, int integer, EnumSet<cio.a> enumSet, chc.a a, chc.b b) {
		return a(string, chc, integer, enumSet, a, b, p);
	}

	private static chc a(String string, @Nullable chc chc, int integer, EnumSet<cio.a> enumSet, chc.a a, chc.b b, chc.c c) {
		return gl.a(gl.aF, string, new chc(string, chc, integer, enumSet, a, b, c));
	}

	public static List<chc> a() {
		List<chc> list1 = Lists.<chc>newArrayList();

		chc chc2;
		for (chc2 = m; chc2.e() != chc2; chc2 = chc2.e()) {
			list1.add(chc2);
		}

		list1.add(chc2);
		Collections.reverse(list1);
		return list1;
	}

	private static boolean a(chc chc, cgy cgy) {
		return cgy.k().b(chc) && cgy.r();
	}

	public static chc a(int integer) {
		if (integer >= q.size()) {
			return a;
		} else {
			return integer < 0 ? m : (chc)q.get(integer);
		}
	}

	public static int b() {
		return q.size();
	}

	public static int a(chc chc) {
		return r.getInt(chc.c());
	}

	chc(String string, @Nullable chc chc, int integer, EnumSet<cio.a> enumSet, chc.a a, chc.b b, chc.c c) {
		this.s = string;
		this.u = chc == null ? this : chc;
		this.v = b;
		this.w = c;
		this.x = integer;
		this.y = a;
		this.z = enumSet;
		this.t = chc == null ? 0 : chc.c() + 1;
	}

	public int c() {
		return this.t;
	}

	public String d() {
		return this.s;
	}

	public chc e() {
		return this.u;
	}

	public CompletableFuture<Either<cgy, yo.a>> a(zd zd, cha cha, cva cva, zg zg, Function<cgy, CompletableFuture<Either<cgy, yo.a>>> function, List<cgy> list) {
		return this.v.doWork(this, zd, cha, cva, zg, function, list, (cgy)list.get(list.size() / 2));
	}

	public CompletableFuture<Either<cgy, yo.a>> a(zd zd, cva cva, zg zg, Function<cgy, CompletableFuture<Either<cgy, yo.a>>> function, cgy cgy) {
		return this.w.doWork(this, zd, cva, zg, function, cgy);
	}

	public int f() {
		return this.x;
	}

	public chc.a g() {
		return this.y;
	}

	public static chc a(String string) {
		return gl.aF.a(uh.a(string));
	}

	public EnumSet<cio.a> h() {
		return this.z;
	}

	public boolean b(chc chc) {
		return this.c() >= chc.c();
	}

	public String toString() {
		return gl.aF.b(this).toString();
	}

	public static enum a {
		PROTOCHUNK,
		LEVELCHUNK;
	}

	interface b {
		CompletableFuture<Either<cgy, yo.a>> doWork(
			chc chc, zd zd, cha cha, cva cva, zg zg, Function<cgy, CompletableFuture<Either<cgy, yo.a>>> function, List<cgy> list, cgy cgy
		);
	}

	interface c {
		CompletableFuture<Either<cgy, yo.a>> doWork(chc chc, zd zd, cva cva, zg zg, Function<cgy, CompletableFuture<Either<cgy, yo.a>>> function, cgy cgy);
	}

	interface d extends chc.b {
		@Override
		default CompletableFuture<Either<cgy, yo.a>> doWork(
			chc chc, zd zd, cha cha, cva cva, zg zg, Function<cgy, CompletableFuture<Either<cgy, yo.a>>> function, List<cgy> list, cgy cgy
		) {
			if (!cgy.k().b(chc)) {
				this.doWork(zd, cha, list, cgy);
				if (cgy instanceof chr) {
					((chr)cgy).a(chc);
				}
			}

			return CompletableFuture.completedFuture(Either.left(cgy));
		}

		void doWork(zd zd, cha cha, List<cgy> list, cgy cgy);
	}
}
