import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class vw {
	private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType((object1, object2) -> new ne("commands.fill.toobig", object1, object2));
	private static final ec b = new ec(bvs.a.n(), Collections.emptySet(), null);
	private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("commands.fill.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("fill")
				.requires(cz -> cz.c(2))
				.then(
					da.a("from", eh.a())
						.then(
							da.a("to", eh.a())
								.then(
									da.a("block", ee.a())
										.executes(
											commandContext -> a(
													commandContext.getSource(), new ctd(eh.a(commandContext, "from"), eh.a(commandContext, "to")), ee.a(commandContext, "block"), vw.a.REPLACE, null
												)
										)
										.then(
											da.a("replace")
												.executes(
													commandContext -> a(
															commandContext.getSource(), new ctd(eh.a(commandContext, "from"), eh.a(commandContext, "to")), ee.a(commandContext, "block"), vw.a.REPLACE, null
														)
												)
												.then(
													da.a("filter", ed.a())
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	new ctd(eh.a(commandContext, "from"), eh.a(commandContext, "to")),
																	ee.a(commandContext, "block"),
																	vw.a.REPLACE,
																	ed.a(commandContext, "filter")
																)
														)
												)
										)
										.then(
											da.a("keep")
												.executes(
													commandContext -> a(
															commandContext.getSource(),
															new ctd(eh.a(commandContext, "from"), eh.a(commandContext, "to")),
															ee.a(commandContext, "block"),
															vw.a.REPLACE,
															cfn -> cfn.c().w(cfn.d())
														)
												)
										)
										.then(
											da.a("outline")
												.executes(
													commandContext -> a(
															commandContext.getSource(), new ctd(eh.a(commandContext, "from"), eh.a(commandContext, "to")), ee.a(commandContext, "block"), vw.a.OUTLINE, null
														)
												)
										)
										.then(
											da.a("hollow")
												.executes(
													commandContext -> a(
															commandContext.getSource(), new ctd(eh.a(commandContext, "from"), eh.a(commandContext, "to")), ee.a(commandContext, "block"), vw.a.HOLLOW, null
														)
												)
										)
										.then(
											da.a("destroy")
												.executes(
													commandContext -> a(
															commandContext.getSource(), new ctd(eh.a(commandContext, "from"), eh.a(commandContext, "to")), ee.a(commandContext, "block"), vw.a.DESTROY, null
														)
												)
										)
								)
						)
				)
		);
	}

	private static int a(cz cz, ctd ctd, ec ec, vw.a a, @Nullable Predicate<cfn> predicate) throws CommandSyntaxException {
		int integer6 = ctd.d() * ctd.e() * ctd.f();
		if (integer6 > 32768) {
			throw vw.a.create(32768, integer6);
		} else {
			List<fu> list7 = Lists.<fu>newArrayList();
			zd zd8 = cz.e();
			int integer9 = 0;

			for (fu fu11 : fu.b(ctd.a, ctd.b, ctd.c, ctd.d, ctd.e, ctd.f)) {
				if (predicate == null || predicate.test(new cfn(zd8, fu11, true))) {
					ec ec12 = a.e.filter(ctd, fu11, ec, zd8);
					if (ec12 != null) {
						cdl cdl13 = zd8.c(fu11);
						amx.a(cdl13);
						if (ec12.a(zd8, fu11, 2)) {
							list7.add(fu11.h());
							integer9++;
						}
					}
				}
			}

			for (fu fu11x : list7) {
				bvr bvr12 = zd8.d_(fu11x).b();
				zd8.a(fu11x, bvr12);
			}

			if (integer9 == 0) {
				throw c.create();
			} else {
				cz.a(new ne("commands.fill.success", integer9), true);
				return integer9;
			}
		}
	}

	static enum a {
		REPLACE((ctd, fu, ec, zd) -> ec),
		OUTLINE((ctd, fu, ec, zd) -> fu.u() != ctd.a && fu.u() != ctd.d && fu.v() != ctd.b && fu.v() != ctd.e && fu.w() != ctd.c && fu.w() != ctd.f ? null : ec),
		HOLLOW((ctd, fu, ec, zd) -> fu.u() != ctd.a && fu.u() != ctd.d && fu.v() != ctd.b && fu.v() != ctd.e && fu.w() != ctd.c && fu.w() != ctd.f ? vw.b : ec),
		DESTROY((ctd, fu, ec, zd) -> {
			zd.b(fu, true);
			return ec;
		});

		public final xb.a e;

		private a(xb.a a) {
			this.e = a;
		}
	}
}
