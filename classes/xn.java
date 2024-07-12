import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;

public class xn {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.teleport.invalidPosition"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		LiteralCommandNode<cz> literalCommandNode2 = commandDispatcher.register(
			da.a("teleport")
				.requires(cz -> cz.c(2))
				.then(
					da.a("targets", dh.b())
						.then(
							da.a("location", eo.a())
								.executes(
									commandContext -> a(
											commandContext.getSource(), dh.b(commandContext, "targets"), commandContext.getSource().e(), eo.b(commandContext, "location"), null, null
										)
								)
								.then(
									da.a("rotation", el.a())
										.executes(
											commandContext -> a(
													commandContext.getSource(),
													dh.b(commandContext, "targets"),
													commandContext.getSource().e(),
													eo.b(commandContext, "location"),
													el.a(commandContext, "rotation"),
													null
												)
										)
								)
								.then(
									da.a("facing")
										.then(
											da.a("entity")
												.then(
													da.a("facingEntity", dh.a())
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	dh.b(commandContext, "targets"),
																	commandContext.getSource().e(),
																	eo.b(commandContext, "location"),
																	null,
																	new xn.a(dh.a(commandContext, "facingEntity"), dg.a.FEET)
																)
														)
														.then(
															da.a("facingAnchor", dg.a())
																.executes(
																	commandContext -> a(
																			commandContext.getSource(),
																			dh.b(commandContext, "targets"),
																			commandContext.getSource().e(),
																			eo.b(commandContext, "location"),
																			null,
																			new xn.a(dh.a(commandContext, "facingEntity"), dg.a(commandContext, "facingAnchor"))
																		)
																)
														)
												)
										)
										.then(
											da.a("facingLocation", eo.a())
												.executes(
													commandContext -> a(
															commandContext.getSource(),
															dh.b(commandContext, "targets"),
															commandContext.getSource().e(),
															eo.b(commandContext, "location"),
															null,
															new xn.a(eo.a(commandContext, "facingLocation"))
														)
												)
										)
								)
						)
						.then(
							da.a("destination", dh.a())
								.executes(commandContext -> a(commandContext.getSource(), dh.b(commandContext, "targets"), dh.a(commandContext, "destination")))
						)
				)
				.then(
					da.a("location", eo.a())
						.executes(
							commandContext -> a(
									commandContext.getSource(),
									Collections.singleton(commandContext.getSource().g()),
									commandContext.getSource().e(),
									eo.b(commandContext, "location"),
									eq.d(),
									null
								)
						)
				)
				.then(
					da.a("destination", dh.a())
						.executes(commandContext -> a(commandContext.getSource(), Collections.singleton(commandContext.getSource().g()), dh.a(commandContext, "destination")))
				)
		);
		commandDispatcher.register(da.a("tp").requires(cz -> cz.c(2)).redirect(literalCommandNode2));
	}

	private static int a(cz cz, Collection<? extends aom> collection, aom aom) throws CommandSyntaxException {
		for (aom aom5 : collection) {
			a(cz, aom5, (zd)aom.l, aom.cC(), aom.cD(), aom.cG(), EnumSet.noneOf(pk.a.class), aom.p, aom.q, null);
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.teleport.success.entity.single", ((aom)collection.iterator().next()).d(), aom.d()), true);
		} else {
			cz.a(new ne("commands.teleport.success.entity.multiple", collection.size(), aom.d()), true);
		}

		return collection.size();
	}

	private static int a(cz cz, Collection<? extends aom> collection, zd zd, ej ej4, @Nullable ej ej5, @Nullable xn.a a) throws CommandSyntaxException {
		dem dem7 = ej4.a(cz);
		del del8 = ej5 == null ? null : ej5.b(cz);
		Set<pk.a> set9 = EnumSet.noneOf(pk.a.class);
		if (ej4.a()) {
			set9.add(pk.a.X);
		}

		if (ej4.b()) {
			set9.add(pk.a.Y);
		}

		if (ej4.c()) {
			set9.add(pk.a.Z);
		}

		if (ej5 == null) {
			set9.add(pk.a.X_ROT);
			set9.add(pk.a.Y_ROT);
		} else {
			if (ej5.a()) {
				set9.add(pk.a.X_ROT);
			}

			if (ej5.b()) {
				set9.add(pk.a.Y_ROT);
			}
		}

		for (aom aom11 : collection) {
			if (ej5 == null) {
				a(cz, aom11, zd, dem7.b, dem7.c, dem7.d, set9, aom11.p, aom11.q, a);
			} else {
				a(cz, aom11, zd, dem7.b, dem7.c, dem7.d, set9, del8.j, del8.i, a);
			}
		}

		if (collection.size() == 1) {
			cz.a(new ne("commands.teleport.success.location.single", ((aom)collection.iterator().next()).d(), dem7.b, dem7.c, dem7.d), true);
		} else {
			cz.a(new ne("commands.teleport.success.location.multiple", collection.size(), dem7.b, dem7.c, dem7.d), true);
		}

		return collection.size();
	}

	private static void a(cz cz, aom aom, zd zd, double double4, double double5, double double6, Set<pk.a> set, float float8, float float9, @Nullable xn.a a) throws CommandSyntaxException {
		fu fu14 = new fu(double4, double5, double6);
		if (!bqb.k(fu14)) {
			throw xn.a.create();
		} else {
			if (aom instanceof ze) {
				bph bph15 = new bph(new fu(double4, double5, double6));
				zd.i().a(zi.g, bph15, 1, aom.V());
				aom.l();
				if (((ze)aom).el()) {
					((ze)aom).a(true, true);
				}

				if (zd == aom.l) {
					((ze)aom).b.a(double4, double5, double6, float8, float9, set);
				} else {
					((ze)aom).a(zd, double4, double5, double6, float8, float9);
				}

				aom.k(float8);
			} else {
				float float15 = aec.g(float8);
				float float16 = aec.g(float9);
				float16 = aec.a(float16, -90.0F, 90.0F);
				if (zd == aom.l) {
					aom.b(double4, double5, double6, float15, float16);
					aom.k(float15);
				} else {
					aom.T();
					aom aom17 = aom;
					aom = aom.U().a(zd);
					if (aom == null) {
						return;
					}

					aom.v(aom17);
					aom.b(double4, double5, double6, float15, float16);
					aom.k(float15);
					zd.e(aom);
					aom17.y = true;
				}
			}

			if (a != null) {
				a.a(cz, aom);
			}

			if (!(aom instanceof aoy) || !((aoy)aom).ee()) {
				aom.e(aom.cB().d(1.0, 0.0, 1.0));
				aom.c(true);
			}

			if (aom instanceof apg) {
				((apg)aom).x().o();
			}
		}
	}

	static class a {
		private final dem a;
		private final aom b;
		private final dg.a c;

		public a(aom aom, dg.a a) {
			this.b = aom;
			this.c = a;
			this.a = a.a(aom);
		}

		public a(dem dem) {
			this.b = null;
			this.a = dem;
			this.c = null;
		}

		public void a(cz cz, aom aom) {
			if (this.b != null) {
				if (aom instanceof ze) {
					((ze)aom).a(cz.k(), this.b, this.c);
				} else {
					aom.a(cz.k(), this.a);
				}
			} else {
				aom.a(cz.k(), this.a);
			}
		}
	}
}
