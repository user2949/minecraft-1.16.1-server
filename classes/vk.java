import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class vk {
	private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("commands.datapack.unknown", object));
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("commands.datapack.enable.failed", object));
	private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(object -> new ne("commands.datapack.disable.failed", object));
	private static final SuggestionProvider<cz> d = (commandContext, suggestionsBuilder) -> db.b(
			commandContext.getSource().j().aA().d().stream().map(StringArgumentType::escapeIfRequired), suggestionsBuilder
		);
	private static final SuggestionProvider<cz> e = (commandContext, suggestionsBuilder) -> {
		aar<?> aar3 = commandContext.getSource().j().aA();
		Collection<String> collection4 = aar3.d();
		return db.b(aar3.b().stream().filter(string -> !collection4.contains(string)).map(StringArgumentType::escapeIfRequired), suggestionsBuilder);
	};

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("datapack")
				.requires(cz -> cz.c(2))
				.then(
					da.a("enable")
						.then(
							da.a("name", StringArgumentType.string())
								.suggests(e)
								.executes(commandContext -> a(commandContext.getSource(), a(commandContext, "name", true), (list, aap) -> aap.h().a(list, aap, aapx -> aapx, false)))
								.then(
									da.a("after")
										.then(
											da.a("existing", StringArgumentType.string())
												.suggests(d)
												.executes(
													commandContext -> a(
															commandContext.getSource(),
															a(commandContext, "name", true),
															(list, aap) -> list.add(list.indexOf(a(commandContext, "existing", false)) + 1, aap)
														)
												)
										)
								)
								.then(
									da.a("before")
										.then(
											da.a("existing", StringArgumentType.string())
												.suggests(d)
												.executes(
													commandContext -> a(
															commandContext.getSource(), a(commandContext, "name", true), (list, aap) -> list.add(list.indexOf(a(commandContext, "existing", false)), aap)
														)
												)
										)
								)
								.then(da.a("last").executes(commandContext -> a(commandContext.getSource(), a(commandContext, "name", true), List::add)))
								.then(da.a("first").executes(commandContext -> a(commandContext.getSource(), a(commandContext, "name", true), (list, aap) -> list.add(0, aap))))
						)
				)
				.then(
					da.a("disable")
						.then(da.a("name", StringArgumentType.string()).suggests(d).executes(commandContext -> a(commandContext.getSource(), a(commandContext, "name", false))))
				)
				.then(
					da.a("list")
						.executes(commandContext -> a(commandContext.getSource()))
						.then(da.a("available").executes(commandContext -> b(commandContext.getSource())))
						.then(da.a("enabled").executes(commandContext -> c(commandContext.getSource())))
				)
		);
	}

	private static int a(cz cz, aap aap, vk.a a) throws CommandSyntaxException {
		aar<?> aar4 = cz.j().aA();
		List<aap> list5 = Lists.<aap>newArrayList((Iterable<? extends aap>)aar4.e());
		a.apply(list5, aap);
		cz.a(new ne("commands.datapack.modify.enable", aap.a(true)), true);
		ws.a((Collection<String>)list5.stream().map(aap::e).collect(Collectors.toList()), cz);
		return list5.size();
	}

	private static int a(cz cz, aap aap) {
		aar<?> aar3 = cz.j().aA();
		List<aap> list4 = Lists.<aap>newArrayList((Iterable<? extends aap>)aar3.e());
		list4.remove(aap);
		cz.a(new ne("commands.datapack.modify.disable", aap.a(true)), true);
		ws.a((Collection<String>)list4.stream().map(aap::e).collect(Collectors.toList()), cz);
		return list4.size();
	}

	private static int a(cz cz) {
		return c(cz) + b(cz);
	}

	private static int b(cz cz) {
		aar<?> aar2 = cz.j().aA();
		aar2.a();
		Collection<? extends aap> collection3 = (Collection<? extends aap>)aar2.e();
		Collection<? extends aap> collection4 = (Collection<? extends aap>)aar2.c();
		List<aap> list5 = (List<aap>)collection4.stream().filter(aap -> !collection3.contains(aap)).collect(Collectors.toList());
		if (list5.isEmpty()) {
			cz.a(new ne("commands.datapack.list.available.none"), false);
		} else {
			cz.a(new ne("commands.datapack.list.available.success", list5.size(), ms.b(list5, aap -> aap.a(false))), false);
		}

		return list5.size();
	}

	private static int c(cz cz) {
		aar<?> aar2 = cz.j().aA();
		aar2.a();
		Collection<? extends aap> collection3 = (Collection<? extends aap>)aar2.e();
		if (collection3.isEmpty()) {
			cz.a(new ne("commands.datapack.list.enabled.none"), false);
		} else {
			cz.a(new ne("commands.datapack.list.enabled.success", collection3.size(), ms.b(collection3, aap -> aap.a(true))), false);
		}

		return collection3.size();
	}

	private static aap a(CommandContext<cz> commandContext, String string, boolean boolean3) throws CommandSyntaxException {
		String string4 = StringArgumentType.getString(commandContext, string);
		aar<?> aar5 = commandContext.getSource().j().aA();
		aap aap6 = aar5.a(string4);
		if (aap6 == null) {
			throw a.create(string4);
		} else {
			boolean boolean7 = aar5.e().contains(aap6);
			if (boolean3 && boolean7) {
				throw b.create(string4);
			} else if (!boolean3 && !boolean7) {
				throw c.create(string4);
			} else {
				return aap6;
			}
		}
	}

	interface a {
		void apply(List<aap> list, aap aap) throws CommandSyntaxException;
	}
}
