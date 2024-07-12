import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import dn.h;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class xx {
	private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ne("commands.data.merge.failed"));
	private static final DynamicCommandExceptionType e = new DynamicCommandExceptionType(object -> new ne("commands.data.get.invalid", object));
	private static final DynamicCommandExceptionType f = new DynamicCommandExceptionType(object -> new ne("commands.data.get.unknown", object));
	private static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(new ne("commands.data.get.multiple"));
	private static final DynamicCommandExceptionType h = new DynamicCommandExceptionType(object -> new ne("commands.data.modify.expected_list", object));
	private static final DynamicCommandExceptionType i = new DynamicCommandExceptionType(object -> new ne("commands.data.modify.expected_object", object));
	private static final DynamicCommandExceptionType j = new DynamicCommandExceptionType(object -> new ne("commands.data.modify.invalid_index", object));
	public static final List<Function<String, xx.c>> a = ImmutableList.of(xy.a, xv.a, xz.a);
	public static final List<xx.c> b = (List<xx.c>)a.stream().map(function -> (xx.c)function.apply("target")).collect(ImmutableList.toImmutableList());
	public static final List<xx.c> c = (List<xx.c>)a.stream().map(function -> (xx.c)function.apply("source")).collect(ImmutableList.toImmutableList());

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		LiteralArgumentBuilder<cz> literalArgumentBuilder2 = da.a("data").requires(cz -> cz.c(2));

		for (xx.c c4 : b) {
			literalArgumentBuilder2.then(
					c4.a(
						da.a("merge"),
						argumentBuilder -> argumentBuilder.then(
								da.a("nbt", de.a()).executes(commandContext -> a(commandContext.getSource(), c4.a(commandContext), de.a(commandContext, "nbt")))
							)
					)
				)
				.then(
					c4.a(
						da.a("get"),
						argumentBuilder -> argumentBuilder.executes(commandContext -> a((cz)commandContext.getSource(), c4.a(commandContext)))
								.then(
									da.a("path", dn.a())
										.executes(commandContext -> b(commandContext.getSource(), c4.a(commandContext), dn.a(commandContext, "path")))
										.then(
											da.a("scale", DoubleArgumentType.doubleArg())
												.executes(
													commandContext -> a(
															commandContext.getSource(), c4.a(commandContext), dn.a(commandContext, "path"), DoubleArgumentType.getDouble(commandContext, "scale")
														)
												)
										)
								)
					)
				)
				.then(
					c4.a(
						da.a("remove"),
						argumentBuilder -> argumentBuilder.then(
								da.a("path", dn.a()).executes(commandContext -> a(commandContext.getSource(), c4.a(commandContext), dn.a(commandContext, "path")))
							)
					)
				)
				.then(
					a(
						(BiConsumer<ArgumentBuilder<cz, ?>, xx.b>)((argumentBuilder, b) -> argumentBuilder.then(
									da.a("insert").then(da.a("index", IntegerArgumentType.integer()).then(b.create((commandContext, le, h, list) -> {
										int integer5 = IntegerArgumentType.getInteger(commandContext, "index");
										return a(integer5, le, h, list);
									})))
								)
								.then(da.a("prepend").then(b.create((commandContext, le, h, list) -> a(0, le, h, list))))
								.then(da.a("append").then(b.create((commandContext, le, h, list) -> a(-1, le, h, list))))
								.then(da.a("set").then(b.create((commandContext, le, h, list) -> h.b(le, Iterables.getLast(list)::c))))
								.then(da.a("merge").then(b.create((commandContext, le, h, list) -> {
									Collection<lu> collection5 = h.a(le, le::new);
									int integer6 = 0;
				
									for (lu lu8 : collection5) {
										if (!(lu8 instanceof le)) {
											throw i.create(lu8);
										}
				
										le le9 = (le)lu8;
										le le10 = le9.g();
				
										for (lu lu12 : list) {
											if (!(lu12 instanceof le)) {
												throw i.create(lu12);
											}
				
											le9.a((le)lu12);
										}
				
										integer6 += le10.equals(le9) ? 0 : 1;
									}
				
									return integer6;
								}))))
					)
				);
		}

		commandDispatcher.register(literalArgumentBuilder2);
	}

	private static int a(int integer, le le, h h, List<lu> list) throws CommandSyntaxException {
		Collection<lu> collection5 = h.a(le, lk::new);
		int integer6 = 0;

		for (lu lu8 : collection5) {
			if (!(lu8 instanceof ld)) {
				throw xx.h.create(lu8);
			}

			boolean boolean9 = false;
			ld<?> ld10 = (ld<?>)lu8;
			int integer11 = integer < 0 ? ld10.size() + integer + 1 : integer;

			for (lu lu13 : list) {
				try {
					if (ld10.b(integer11, lu13.c())) {
						integer11++;
						boolean9 = true;
					}
				} catch (IndexOutOfBoundsException var14) {
					throw j.create(integer11);
				}
			}

			integer6 += boolean9 ? 1 : 0;
		}

		return integer6;
	}

	private static ArgumentBuilder<cz, ?> a(BiConsumer<ArgumentBuilder<cz, ?>, xx.b> biConsumer) {
		LiteralArgumentBuilder<cz> literalArgumentBuilder2 = da.a("modify");

		for (xx.c c4 : b) {
			c4.a(literalArgumentBuilder2, argumentBuilder -> {
				ArgumentBuilder<cz, ?> argumentBuilder4 = da.a("targetPath", dn.a());

				for (xx.c c6 : xx.c) {
					biConsumer.accept(argumentBuilder4, (xx.b)a -> c6.a(da.a("from"), argumentBuilderx -> argumentBuilderx.executes(commandContext -> {
								List<lu> list5 = Collections.singletonList(c6.a(commandContext).a());
								return a(commandContext, c4, a, list5);
							}).then(da.a("sourcePath", dn.a()).executes(commandContext -> {
								xw xw5 = c6.a(commandContext);
								h h6 = dn.a(commandContext, "sourcePath");
								List<lu> list7 = h6.a(xw5.a());
								return a(commandContext, c4, a, list7);
							}))));
				}

				biConsumer.accept(argumentBuilder4, (xx.b)a -> (LiteralArgumentBuilder)da.a("value").then(da.a("value", dp.a()).executes(commandContext -> {
						List<lu> list4 = Collections.singletonList(dp.a(commandContext, "value"));
						return a(commandContext, c4, a, list4);
					})));
				return argumentBuilder.then(argumentBuilder4);
			});
		}

		return literalArgumentBuilder2;
	}

	private static int a(CommandContext<cz> commandContext, xx.c c, xx.a a, List<lu> list) throws CommandSyntaxException {
		xw xw5 = c.a(commandContext);
		h h6 = dn.a(commandContext, "targetPath");
		le le7 = xw5.a();
		int integer8 = a.modify(commandContext, le7, h6, list);
		if (integer8 == 0) {
			throw d.create();
		} else {
			xw5.a(le7);
			commandContext.getSource().a(xw5.b(), true);
			return integer8;
		}
	}

	private static int a(cz cz, xw xw, h h) throws CommandSyntaxException {
		le le4 = xw.a();
		int integer5 = h.c(le4);
		if (integer5 == 0) {
			throw d.create();
		} else {
			xw.a(le4);
			cz.a(xw.b(), true);
			return integer5;
		}
	}

	private static lu a(h h, xw xw) throws CommandSyntaxException {
		Collection<lu> collection3 = h.a(xw.a());
		Iterator<lu> iterator4 = collection3.iterator();
		lu lu5 = (lu)iterator4.next();
		if (iterator4.hasNext()) {
			throw g.create();
		} else {
			return lu5;
		}
	}

	private static int b(cz cz, xw xw, h h) throws CommandSyntaxException {
		lu lu4 = a(h, xw);
		int integer5;
		if (lu4 instanceof lr) {
			integer5 = aec.c(((lr)lu4).i());
		} else if (lu4 instanceof ld) {
			integer5 = ((ld)lu4).size();
		} else if (lu4 instanceof le) {
			integer5 = ((le)lu4).e();
		} else {
			if (!(lu4 instanceof lt)) {
				throw f.create(h.toString());
			}

			integer5 = lu4.f_().length();
		}

		cz.a(xw.a(lu4), false);
		return integer5;
	}

	private static int a(cz cz, xw xw, h h, double double4) throws CommandSyntaxException {
		lu lu6 = a(h, xw);
		if (!(lu6 instanceof lr)) {
			throw e.create(h.toString());
		} else {
			int integer7 = aec.c(((lr)lu6).i() * double4);
			cz.a(xw.a(h, double4, integer7), false);
			return integer7;
		}
	}

	private static int a(cz cz, xw xw) throws CommandSyntaxException {
		cz.a(xw.a((lu)xw.a()), false);
		return 1;
	}

	private static int a(cz cz, xw xw, le le) throws CommandSyntaxException {
		le le4 = xw.a();
		le le5 = le4.g().a(le);
		if (le4.equals(le5)) {
			throw d.create();
		} else {
			xw.a(le5);
			cz.a(xw.b(), true);
			return 1;
		}
	}

	interface a {
		int modify(CommandContext<cz> commandContext, le le, h h, List<lu> list) throws CommandSyntaxException;
	}

	interface b {
		ArgumentBuilder<cz, ?> create(xx.a a);
	}

	public interface c {
		xw a(CommandContext<cz> commandContext) throws CommandSyntaxException;

		ArgumentBuilder<cz, ?> a(ArgumentBuilder<cz, ?> argumentBuilder, Function<ArgumentBuilder<cz, ?>, ArgumentBuilder<cz, ?>> function);
	}
}
