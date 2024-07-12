import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Deque;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class vj {
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.clone.overlap"));
	private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType((object1, object2) -> new ne("commands.clone.toobig", object1, object2));
	private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ne("commands.clone.failed"));
	public static final Predicate<cfn> a = cfn -> !cfn.a().g();

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("clone")
				.requires(cz -> cz.c(2))
				.then(
					da.a("begin", eh.a())
						.then(
							da.a("end", eh.a())
								.then(
									da.a("destination", eh.a())
										.executes(
											commandContext -> a(
													commandContext.getSource(),
													eh.a(commandContext, "begin"),
													eh.a(commandContext, "end"),
													eh.a(commandContext, "destination"),
													cfn -> true,
													vj.b.NORMAL
												)
										)
										.then(
											da.a("replace")
												.executes(
													commandContext -> a(
															commandContext.getSource(),
															eh.a(commandContext, "begin"),
															eh.a(commandContext, "end"),
															eh.a(commandContext, "destination"),
															cfn -> true,
															vj.b.NORMAL
														)
												)
												.then(
													da.a("force")
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	eh.a(commandContext, "begin"),
																	eh.a(commandContext, "end"),
																	eh.a(commandContext, "destination"),
																	cfn -> true,
																	vj.b.FORCE
																)
														)
												)
												.then(
													da.a("move")
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	eh.a(commandContext, "begin"),
																	eh.a(commandContext, "end"),
																	eh.a(commandContext, "destination"),
																	cfn -> true,
																	vj.b.MOVE
																)
														)
												)
												.then(
													da.a("normal")
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	eh.a(commandContext, "begin"),
																	eh.a(commandContext, "end"),
																	eh.a(commandContext, "destination"),
																	cfn -> true,
																	vj.b.NORMAL
																)
														)
												)
										)
										.then(
											da.a("masked")
												.executes(
													commandContext -> a(
															commandContext.getSource(), eh.a(commandContext, "begin"), eh.a(commandContext, "end"), eh.a(commandContext, "destination"), a, vj.b.NORMAL
														)
												)
												.then(
													da.a("force")
														.executes(
															commandContext -> a(
																	commandContext.getSource(), eh.a(commandContext, "begin"), eh.a(commandContext, "end"), eh.a(commandContext, "destination"), a, vj.b.FORCE
																)
														)
												)
												.then(
													da.a("move")
														.executes(
															commandContext -> a(
																	commandContext.getSource(), eh.a(commandContext, "begin"), eh.a(commandContext, "end"), eh.a(commandContext, "destination"), a, vj.b.MOVE
																)
														)
												)
												.then(
													da.a("normal")
														.executes(
															commandContext -> a(
																	commandContext.getSource(), eh.a(commandContext, "begin"), eh.a(commandContext, "end"), eh.a(commandContext, "destination"), a, vj.b.NORMAL
																)
														)
												)
										)
										.then(
											da.a("filtered")
												.then(
													da.a("filter", ed.a())
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	eh.a(commandContext, "begin"),
																	eh.a(commandContext, "end"),
																	eh.a(commandContext, "destination"),
																	ed.a(commandContext, "filter"),
																	vj.b.NORMAL
																)
														)
														.then(
															da.a("force")
																.executes(
																	commandContext -> a(
																			commandContext.getSource(),
																			eh.a(commandContext, "begin"),
																			eh.a(commandContext, "end"),
																			eh.a(commandContext, "destination"),
																			ed.a(commandContext, "filter"),
																			vj.b.FORCE
																		)
																)
														)
														.then(
															da.a("move")
																.executes(
																	commandContext -> a(
																			commandContext.getSource(),
																			eh.a(commandContext, "begin"),
																			eh.a(commandContext, "end"),
																			eh.a(commandContext, "destination"),
																			ed.a(commandContext, "filter"),
																			vj.b.MOVE
																		)
																)
														)
														.then(
															da.a("normal")
																.executes(
																	commandContext -> a(
																			commandContext.getSource(),
																			eh.a(commandContext, "begin"),
																			eh.a(commandContext, "end"),
																			eh.a(commandContext, "destination"),
																			ed.a(commandContext, "filter"),
																			vj.b.NORMAL
																		)
																)
														)
												)
										)
								)
						)
				)
		);
	}

	private static int a(cz cz, fu fu2, fu fu3, fu fu4, Predicate<cfn> predicate, vj.b b) throws CommandSyntaxException {
		ctd ctd7 = new ctd(fu2, fu3);
		fu fu8 = fu4.a(ctd7.c());
		ctd ctd9 = new ctd(fu4, fu8);
		if (!b.a() && ctd9.b(ctd7)) {
			throw vj.b.create();
		} else {
			int integer10 = ctd7.d() * ctd7.e() * ctd7.f();
			if (integer10 > 32768) {
				throw c.create(32768, integer10);
			} else {
				zd zd11 = cz.e();
				if (zd11.a(fu2, fu3) && zd11.a(fu4, fu8)) {
					List<vj.a> list12 = Lists.<vj.a>newArrayList();
					List<vj.a> list13 = Lists.<vj.a>newArrayList();
					List<vj.a> list14 = Lists.<vj.a>newArrayList();
					Deque<fu> deque15 = Lists.<fu>newLinkedList();
					fu fu16 = new fu(ctd9.a - ctd7.a, ctd9.b - ctd7.b, ctd9.c - ctd7.c);

					for (int integer17 = ctd7.c; integer17 <= ctd7.f; integer17++) {
						for (int integer18 = ctd7.b; integer18 <= ctd7.e; integer18++) {
							for (int integer19 = ctd7.a; integer19 <= ctd7.d; integer19++) {
								fu fu20 = new fu(integer19, integer18, integer17);
								fu fu21 = fu20.a(fu16);
								cfn cfn22 = new cfn(zd11, fu20, false);
								cfj cfj23 = cfn22.a();
								if (predicate.test(cfn22)) {
									cdl cdl24 = zd11.c(fu20);
									if (cdl24 != null) {
										le le25 = cdl24.a(new le());
										list13.add(new vj.a(fu21, cfj23, le25));
										deque15.addLast(fu20);
									} else if (!cfj23.i(zd11, fu20) && !cfj23.r(zd11, fu20)) {
										list14.add(new vj.a(fu21, cfj23, null));
										deque15.addFirst(fu20);
									} else {
										list12.add(new vj.a(fu21, cfj23, null));
										deque15.addLast(fu20);
									}
								}
							}
						}
					}

					if (b == vj.b.MOVE) {
						for (fu fu18 : deque15) {
							cdl cdl19 = zd11.c(fu18);
							amx.a(cdl19);
							zd11.a(fu18, bvs.go.n(), 2);
						}

						for (fu fu18 : deque15) {
							zd11.a(fu18, bvs.a.n(), 3);
						}
					}

					List<vj.a> list17 = Lists.<vj.a>newArrayList();
					list17.addAll(list12);
					list17.addAll(list13);
					list17.addAll(list14);
					List<vj.a> list18 = Lists.reverse(list17);

					for (vj.a a20 : list18) {
						cdl cdl21 = zd11.c(a20.a);
						amx.a(cdl21);
						zd11.a(a20.a, bvs.go.n(), 2);
					}

					int integer19x = 0;

					for (vj.a a21 : list17) {
						if (zd11.a(a21.a, a21.b, 2)) {
							integer19x++;
						}
					}

					for (vj.a a21x : list13) {
						cdl cdl22 = zd11.c(a21x.a);
						if (a21x.c != null && cdl22 != null) {
							a21x.c.b("x", a21x.a.u());
							a21x.c.b("y", a21x.a.v());
							a21x.c.b("z", a21x.a.w());
							cdl22.a(a21x.b, a21x.c);
							cdl22.Z_();
						}

						zd11.a(a21x.a, a21x.b, 2);
					}

					for (vj.a a21x : list18) {
						zd11.a(a21x.a, a21x.b.b());
					}

					zd11.j().a(ctd7, fu16);
					if (integer19x == 0) {
						throw d.create();
					} else {
						cz.a(new ne("commands.clone.success", integer19x), true);
						return integer19x;
					}
				} else {
					throw eh.a.create();
				}
			}
		}
	}

	static class a {
		public final fu a;
		public final cfj b;
		@Nullable
		public final le c;

		public a(fu fu, cfj cfj, @Nullable le le) {
			this.a = fu;
			this.b = cfj;
			this.c = le;
		}
	}

	static enum b {
		FORCE(true),
		MOVE(true),
		NORMAL(false);

		private final boolean d;

		private b(boolean boolean3) {
			this.d = boolean3;
		}

		public boolean a() {
			return this.d;
		}
	}
}
