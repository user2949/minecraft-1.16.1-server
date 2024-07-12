import com.google.common.base.Joiner;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.longs.LongSet;

public class vx {
	private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType(
		(object1, object2) -> new ne("commands.forceload.toobig", object1, object2)
	);
	private static final Dynamic2CommandExceptionType b = new Dynamic2CommandExceptionType(
		(object1, object2) -> new ne("commands.forceload.query.failure", object1, object2)
	);
	private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("commands.forceload.added.failure"));
	private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ne("commands.forceload.removed.failure"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("forceload")
				.requires(cz -> cz.c(2))
				.then(
					da.a("add")
						.then(
							da.a("from", ei.a())
								.executes(commandContext -> a(commandContext.getSource(), ei.a(commandContext, "from"), ei.a(commandContext, "from"), true))
								.then(da.a("to", ei.a()).executes(commandContext -> a(commandContext.getSource(), ei.a(commandContext, "from"), ei.a(commandContext, "to"), true)))
						)
				)
				.then(
					da.a("remove")
						.then(
							da.a("from", ei.a())
								.executes(commandContext -> a(commandContext.getSource(), ei.a(commandContext, "from"), ei.a(commandContext, "from"), false))
								.then(da.a("to", ei.a()).executes(commandContext -> a(commandContext.getSource(), ei.a(commandContext, "from"), ei.a(commandContext, "to"), false)))
						)
						.then(da.a("all").executes(commandContext -> b(commandContext.getSource())))
				)
				.then(
					da.a("query")
						.executes(commandContext -> a(commandContext.getSource()))
						.then(da.a("pos", ei.a()).executes(commandContext -> a(commandContext.getSource(), ei.a(commandContext, "pos"))))
				)
		);
	}

	private static int a(cz cz, yt yt) throws CommandSyntaxException {
		bph bph3 = new bph(yt.a >> 4, yt.b >> 4);
		zd zd4 = cz.e();
		ug<bqb> ug5 = zd4.W();
		boolean boolean6 = zd4.v().contains(bph3.a());
		if (boolean6) {
			cz.a(new ne("commands.forceload.query.success", bph3, ug5.a()), false);
			return 1;
		} else {
			throw b.create(bph3, ug5.a());
		}
	}

	private static int a(cz cz) {
		zd zd2 = cz.e();
		ug<bqb> ug3 = zd2.W();
		LongSet longSet4 = zd2.v();
		int integer5 = longSet4.size();
		if (integer5 > 0) {
			String string6 = Joiner.on(", ").join(longSet4.stream().sorted().map(bph::new).map(bph::toString).iterator());
			if (integer5 == 1) {
				cz.a(new ne("commands.forceload.list.single", ug3.a(), string6), false);
			} else {
				cz.a(new ne("commands.forceload.list.multiple", integer5, ug3.a(), string6), false);
			}
		} else {
			cz.a(new ne("commands.forceload.added.none", ug3.a()));
		}

		return integer5;
	}

	private static int b(cz cz) {
		zd zd2 = cz.e();
		ug<bqb> ug3 = zd2.W();
		LongSet longSet4 = zd2.v();
		longSet4.forEach(long2 -> zd2.a(bph.a(long2), bph.b(long2), false));
		cz.a(new ne("commands.forceload.removed.all", ug3.a()), true);
		return 0;
	}

	private static int a(cz cz, yt yt2, yt yt3, boolean boolean4) throws CommandSyntaxException {
		int integer5 = Math.min(yt2.a, yt3.a);
		int integer6 = Math.min(yt2.b, yt3.b);
		int integer7 = Math.max(yt2.a, yt3.a);
		int integer8 = Math.max(yt2.b, yt3.b);
		if (integer5 >= -30000000 && integer6 >= -30000000 && integer7 < 30000000 && integer8 < 30000000) {
			int integer9 = integer5 >> 4;
			int integer10 = integer6 >> 4;
			int integer11 = integer7 >> 4;
			int integer12 = integer8 >> 4;
			long long13 = ((long)(integer11 - integer9) + 1L) * ((long)(integer12 - integer10) + 1L);
			if (long13 > 256L) {
				throw a.create(256, long13);
			} else {
				zd zd15 = cz.e();
				ug<bqb> ug16 = zd15.W();
				bph bph17 = null;
				int integer18 = 0;

				for (int integer19 = integer9; integer19 <= integer11; integer19++) {
					for (int integer20 = integer10; integer20 <= integer12; integer20++) {
						boolean boolean21 = zd15.a(integer19, integer20, boolean4);
						if (boolean21) {
							integer18++;
							if (bph17 == null) {
								bph17 = new bph(integer19, integer20);
							}
						}
					}
				}

				if (integer18 == 0) {
					throw (boolean4 ? c : d).create();
				} else {
					if (integer18 == 1) {
						cz.a(new ne("commands.forceload." + (boolean4 ? "added" : "removed") + ".single", bph17, ug16.a()), true);
					} else {
						bph bph19 = new bph(integer9, integer10);
						bph bph20 = new bph(integer11, integer12);
						cz.a(new ne("commands.forceload." + (boolean4 ? "added" : "removed") + ".multiple", integer18, ug16.a(), bph19, bph20), true);
					}

					return integer18;
				}
			}
		} else {
			throw eh.b.create();
		}
	}
}
