import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;

public class wo {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.playsound.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		RequiredArgumentBuilder<cz, uh> requiredArgumentBuilder2 = da.a("sound", dv.a()).suggests(fj.c);

		for (acm acm6 : acm.values()) {
			requiredArgumentBuilder2.then(a(acm6));
		}

		commandDispatcher.register(da.a("playsound").requires(cz -> cz.c(2)).then(requiredArgumentBuilder2));
	}

	private static LiteralArgumentBuilder<cz> a(acm acm) {
		return da.a(acm.a())
			.then(
				da.a("targets", dh.d())
					.executes(
						commandContext -> a(
								commandContext.getSource(), dh.f(commandContext, "targets"), dv.e(commandContext, "sound"), acm, commandContext.getSource().d(), 1.0F, 1.0F, 0.0F
							)
					)
					.then(
						da.a("pos", eo.a())
							.executes(
								commandContext -> a(
										commandContext.getSource(), dh.f(commandContext, "targets"), dv.e(commandContext, "sound"), acm, eo.a(commandContext, "pos"), 1.0F, 1.0F, 0.0F
									)
							)
							.then(
								da.a("volume", FloatArgumentType.floatArg(0.0F))
									.executes(
										commandContext -> a(
												commandContext.getSource(),
												dh.f(commandContext, "targets"),
												dv.e(commandContext, "sound"),
												acm,
												eo.a(commandContext, "pos"),
												commandContext.<Float>getArgument("volume", Float.class),
												1.0F,
												0.0F
											)
									)
									.then(
										da.a("pitch", FloatArgumentType.floatArg(0.0F, 2.0F))
											.executes(
												commandContext -> a(
														commandContext.getSource(),
														dh.f(commandContext, "targets"),
														dv.e(commandContext, "sound"),
														acm,
														eo.a(commandContext, "pos"),
														commandContext.<Float>getArgument("volume", Float.class),
														commandContext.<Float>getArgument("pitch", Float.class),
														0.0F
													)
											)
											.then(
												da.a("minVolume", FloatArgumentType.floatArg(0.0F, 1.0F))
													.executes(
														commandContext -> a(
																commandContext.getSource(),
																dh.f(commandContext, "targets"),
																dv.e(commandContext, "sound"),
																acm,
																eo.a(commandContext, "pos"),
																commandContext.<Float>getArgument("volume", Float.class),
																commandContext.<Float>getArgument("pitch", Float.class),
																commandContext.<Float>getArgument("minVolume", Float.class)
															)
													)
											)
									)
							)
					)
			);
	}

	private static int a(cz cz, Collection<ze> collection, uh uh, acm acm, dem dem, float float6, float float7, float float8) throws CommandSyntaxException {
		double double9 = Math.pow(float6 > 1.0F ? (double)(float6 * 16.0F) : 16.0, 2.0);
		int integer11 = 0;

		for (ze ze13 : collection) {
			double double14 = dem.b - ze13.cC();
			double double16 = dem.c - ze13.cD();
			double double18 = dem.d - ze13.cG();
			double double20 = double14 * double14 + double16 * double16 + double18 * double18;
			dem dem22 = dem;
			float float23 = float6;
			if (double20 > double9) {
				if (float8 <= 0.0F) {
					continue;
				}

				double double24 = (double)aec.a(double20);
				dem22 = new dem(ze13.cC() + double14 / double24 * 2.0, ze13.cD() + double16 / double24 * 2.0, ze13.cG() + double18 / double24 * 2.0);
				float23 = float8;
			}

			ze13.b.a(new ol(uh, acm, dem22, float23, float7));
			integer11++;
		}

		if (integer11 == 0) {
			throw a.create();
		} else {
			if (collection.size() == 1) {
				cz.a(new ne("commands.playsound.success.single", uh, ((ze)collection.iterator().next()).d()), true);
			} else {
				cz.a(new ne("commands.playsound.success.multiple", uh, collection.size()), true);
			}

			return integer11;
		}
	}
}
