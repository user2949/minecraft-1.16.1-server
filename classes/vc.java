import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;

public class vc {
	private static final SuggestionProvider<cz> a = (commandContext, suggestionsBuilder) -> {
		Collection<w> collection3 = commandContext.getSource().j().ay().a();
		return db.a(collection3.stream().map(w::h), suggestionsBuilder);
	};

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("advancement")
				.requires(cz -> cz.c(2))
				.then(
					da.a("grant")
						.then(
							da.a("targets", dh.d())
								.then(
									da.a("only")
										.then(
											da.a("advancement", dv.a())
												.suggests(a)
												.executes(
													commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), vc.a.GRANT, a(dv.a(commandContext, "advancement"), vc.b.ONLY))
												)
												.then(
													da.a("criterion", StringArgumentType.greedyString())
														.suggests((commandContext, suggestionsBuilder) -> db.b(dv.a(commandContext, "advancement").f().keySet(), suggestionsBuilder))
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	dh.f(commandContext, "targets"),
																	vc.a.GRANT,
																	dv.a(commandContext, "advancement"),
																	StringArgumentType.getString(commandContext, "criterion")
																)
														)
												)
										)
								)
								.then(
									da.a("from")
										.then(
											da.a("advancement", dv.a())
												.suggests(a)
												.executes(
													commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), vc.a.GRANT, a(dv.a(commandContext, "advancement"), vc.b.FROM))
												)
										)
								)
								.then(
									da.a("until")
										.then(
											da.a("advancement", dv.a())
												.suggests(a)
												.executes(
													commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), vc.a.GRANT, a(dv.a(commandContext, "advancement"), vc.b.UNTIL))
												)
										)
								)
								.then(
									da.a("through")
										.then(
											da.a("advancement", dv.a())
												.suggests(a)
												.executes(
													commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), vc.a.GRANT, a(dv.a(commandContext, "advancement"), vc.b.THROUGH))
												)
										)
								)
								.then(
									da.a("everything")
										.executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), vc.a.GRANT, commandContext.getSource().j().ay().a()))
								)
						)
				)
				.then(
					da.a("revoke")
						.then(
							da.a("targets", dh.d())
								.then(
									da.a("only")
										.then(
											da.a("advancement", dv.a())
												.suggests(a)
												.executes(
													commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), vc.a.REVOKE, a(dv.a(commandContext, "advancement"), vc.b.ONLY))
												)
												.then(
													da.a("criterion", StringArgumentType.greedyString())
														.suggests((commandContext, suggestionsBuilder) -> db.b(dv.a(commandContext, "advancement").f().keySet(), suggestionsBuilder))
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	dh.f(commandContext, "targets"),
																	vc.a.REVOKE,
																	dv.a(commandContext, "advancement"),
																	StringArgumentType.getString(commandContext, "criterion")
																)
														)
												)
										)
								)
								.then(
									da.a("from")
										.then(
											da.a("advancement", dv.a())
												.suggests(a)
												.executes(
													commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), vc.a.REVOKE, a(dv.a(commandContext, "advancement"), vc.b.FROM))
												)
										)
								)
								.then(
									da.a("until")
										.then(
											da.a("advancement", dv.a())
												.suggests(a)
												.executes(
													commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), vc.a.REVOKE, a(dv.a(commandContext, "advancement"), vc.b.UNTIL))
												)
										)
								)
								.then(
									da.a("through")
										.then(
											da.a("advancement", dv.a())
												.suggests(a)
												.executes(
													commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), vc.a.REVOKE, a(dv.a(commandContext, "advancement"), vc.b.THROUGH))
												)
										)
								)
								.then(
									da.a("everything")
										.executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), vc.a.REVOKE, commandContext.getSource().j().ay().a()))
								)
						)
				)
		);
	}

	private static int a(cz cz, Collection<ze> collection2, vc.a a, Collection<w> collection4) {
		int integer5 = 0;

		for (ze ze7 : collection2) {
			integer5 += a.a(ze7, collection4);
		}

		if (integer5 == 0) {
			if (collection4.size() == 1) {
				if (collection2.size() == 1) {
					throw new cx(new ne(a.a() + ".one.to.one.failure", ((w)collection4.iterator().next()).j(), ((ze)collection2.iterator().next()).d()));
				} else {
					throw new cx(new ne(a.a() + ".one.to.many.failure", ((w)collection4.iterator().next()).j(), collection2.size()));
				}
			} else if (collection2.size() == 1) {
				throw new cx(new ne(a.a() + ".many.to.one.failure", collection4.size(), ((ze)collection2.iterator().next()).d()));
			} else {
				throw new cx(new ne(a.a() + ".many.to.many.failure", collection4.size(), collection2.size()));
			}
		} else {
			if (collection4.size() == 1) {
				if (collection2.size() == 1) {
					cz.a(new ne(a.a() + ".one.to.one.success", ((w)collection4.iterator().next()).j(), ((ze)collection2.iterator().next()).d()), true);
				} else {
					cz.a(new ne(a.a() + ".one.to.many.success", ((w)collection4.iterator().next()).j(), collection2.size()), true);
				}
			} else if (collection2.size() == 1) {
				cz.a(new ne(a.a() + ".many.to.one.success", collection4.size(), ((ze)collection2.iterator().next()).d()), true);
			} else {
				cz.a(new ne(a.a() + ".many.to.many.success", collection4.size(), collection2.size()), true);
			}

			return integer5;
		}
	}

	private static int a(cz cz, Collection<ze> collection, vc.a a, w w, String string) {
		int integer6 = 0;
		if (!w.f().containsKey(string)) {
			throw new cx(new ne("commands.advancement.criterionNotFound", w.j(), string));
		} else {
			for (ze ze8 : collection) {
				if (a.a(ze8, w, string)) {
					integer6++;
				}
			}

			if (integer6 == 0) {
				if (collection.size() == 1) {
					throw new cx(new ne(a.a() + ".criterion.to.one.failure", string, w.j(), ((ze)collection.iterator().next()).d()));
				} else {
					throw new cx(new ne(a.a() + ".criterion.to.many.failure", string, w.j(), collection.size()));
				}
			} else {
				if (collection.size() == 1) {
					cz.a(new ne(a.a() + ".criterion.to.one.success", string, w.j(), ((ze)collection.iterator().next()).d()), true);
				} else {
					cz.a(new ne(a.a() + ".criterion.to.many.success", string, w.j(), collection.size()), true);
				}

				return integer6;
			}
		}
	}

	private static List<w> a(w w, vc.b b) {
		List<w> list3 = Lists.<w>newArrayList();
		if (b.f) {
			for (w w4 = w.b(); w4 != null; w4 = w4.b()) {
				list3.add(w4);
			}
		}

		list3.add(w);
		if (b.g) {
			a(w, list3);
		}

		return list3;
	}

	private static void a(w w, List<w> list) {
		for (w w4 : w.e()) {
			list.add(w4);
			a(w4, list);
		}
	}

	static enum a {
		GRANT("grant") {
			@Override
			protected boolean a(ze ze, w w) {
				y y4 = ze.J().b(w);
				if (y4.a()) {
					return false;
				} else {
					for (String string6 : y4.e()) {
						ze.J().a(w, string6);
					}

					return true;
				}
			}

			@Override
			protected boolean a(ze ze, w w, String string) {
				return ze.J().a(w, string);
			}
		},
		REVOKE("revoke") {
			@Override
			protected boolean a(ze ze, w w) {
				y y4 = ze.J().b(w);
				if (!y4.b()) {
					return false;
				} else {
					for (String string6 : y4.f()) {
						ze.J().b(w, string6);
					}

					return true;
				}
			}

			@Override
			protected boolean a(ze ze, w w, String string) {
				return ze.J().b(w, string);
			}
		};

		private final String c;

		private a(String string3) {
			this.c = "commands.advancement." + string3;
		}

		public int a(ze ze, Iterable<w> iterable) {
			int integer4 = 0;

			for (w w6 : iterable) {
				if (this.a(ze, w6)) {
					integer4++;
				}
			}

			return integer4;
		}

		protected abstract boolean a(ze ze, w w);

		protected abstract boolean a(ze ze, w w, String string);

		protected String a() {
			return this.c;
		}
	}

	static enum b {
		ONLY(false, false),
		THROUGH(true, true),
		FROM(false, true),
		UNTIL(true, false),
		EVERYTHING(true, true);

		private final boolean f;
		private final boolean g;

		private b(boolean boolean3, boolean boolean4) {
			this.f = boolean3;
			this.g = boolean4;
		}
	}
}
