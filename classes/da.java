import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class da {
	private static final Logger a = LogManager.getLogger();
	private final CommandDispatcher<cz> b = new CommandDispatcher<>();

	public da(da.a a) {
		vc.a(this.b);
		vd.a(this.b);
		vu.a(this.b);
		vh.a(this.b);
		vi.a(this.b);
		vj.a(this.b);
		xx.a(this.b);
		vk.a(this.b);
		vm.a(this.b);
		vp.a(this.b);
		vq.a(this.b);
		vr.a(this.b);
		vs.a(this.b);
		vt.a(this.b);
		vv.a(this.b);
		vw.a(this.b);
		vx.a(this.b);
		vy.a(this.b);
		vz.a(this.b);
		wa.a(this.b);
		wb.a(this.b);
		wc.a(this.b);
		wd.a(this.b);
		we.a(this.b);
		wf.a(this.b);
		wh.a(this.b);
		wg.a(this.b);
		wi.a(this.b);
		wj.a(this.b);
		wn.a(this.b);
		wo.a(this.b);
		ws.a(this.b);
		wr.a(this.b);
		wt.a(this.b);
		wx.a(this.b);
		wy.a(this.b);
		wz.a(this.b);
		xa.a(this.b, a != da.a.INTEGRATED);
		xb.a(this.b);
		xd.a(this.b);
		xe.a(this.b);
		xf.a(this.b);
		xg.a(this.b);
		xi.a(this.b);
		xj.a(this.b);
		xk.a(this.b);
		xl.a(this.b);
		xm.a(this.b);
		xn.a(this.b);
		xo.a(this.b);
		xp.a(this.b);
		xq.a(this.b);
		xr.a(this.b);
		xs.a(this.b);
		xu.a(this.b);
		if (u.d) {
			ku.a(this.b);
		}

		if (a.e) {
			ve.a(this.b);
			vf.a(this.b);
			vg.a(this.b);
			vl.a(this.b);
			wk.a(this.b);
			wl.a(this.b);
			wm.a(this.b);
			wu.a(this.b);
			wv.a(this.b);
			ww.a(this.b);
			xc.a(this.b);
			xh.a(this.b);
			xt.a(this.b);
		}

		if (a.d) {
			wp.a(this.b);
		}

		this.b
			.findAmbiguities(
				(commandNode1, commandNode2, commandNode3, collection) -> a.warn(
						"Ambiguity between arguments {} and {} with inputs: {}", this.b.getPath(commandNode2), this.b.getPath(commandNode3), collection
					)
			);
		this.b.setConsumer((commandContext, boolean2, integer) -> commandContext.getSource().a(commandContext, boolean2, integer));
	}

	public int a(cz cz, String string) {
		StringReader stringReader4 = new StringReader(string);
		if (stringReader4.canRead() && stringReader4.peek() == '/') {
			stringReader4.skip();
		}

		cz.j().aO().a(string);

		byte var20;
		try {
			return this.b.execute(stringReader4, cz);
		} catch (cx var13) {
			cz.a(var13.a());
			return 0;
		} catch (CommandSyntaxException var14) {
			cz.a(ms.a(var14.getRawMessage()));
			if (var14.getInput() != null && var14.getCursor() >= 0) {
				int integer6 = Math.min(var14.getInput().length(), var14.getCursor());
				mx mx7 = new nd("").a(i.GRAY).a(nb -> nb.a(new mp(mp.a.SUGGEST_COMMAND, string)));
				if (integer6 > 10) {
					mx7.c("...");
				}

				mx7.c(var14.getInput().substring(Math.max(0, integer6 - 10), integer6));
				if (integer6 < var14.getInput().length()) {
					mr mr8 = new nd(var14.getInput().substring(integer6)).a(new i[]{i.RED, i.UNDERLINE});
					mx7.a(mr8);
				}

				mx7.a(new ne("command.context.here").a(new i[]{i.RED, i.ITALIC}));
				cz.a(mx7);
			}

			return 0;
		} catch (Exception var15) {
			mx mx6 = new nd(var15.getMessage() == null ? var15.getClass().getName() : var15.getMessage());
			if (a.isDebugEnabled()) {
				a.error("Command exception: {}", string, var15);
				StackTraceElement[] arr7 = var15.getStackTrace();

				for (int integer8 = 0; integer8 < Math.min(arr7.length, 3); integer8++) {
					mx6.c("\n\n").c(arr7[integer8].getMethodName()).c("\n ").c(arr7[integer8].getFileName()).c(":").c(String.valueOf(arr7[integer8].getLineNumber()));
				}
			}

			cz.a(new ne("command.failed").a(nb -> nb.a(new mv(mv.a.a, mx6))));
			if (u.d) {
				cz.a(new nd(v.d(var15)));
				a.error("'" + string + "' threw an exception", (Throwable)var15);
			}

			var20 = 0;
		} finally {
			cz.j().aO().c();
		}

		return var20;
	}

	public void a(ze ze) {
		Map<CommandNode<cz>, CommandNode<db>> map3 = Maps.<CommandNode<cz>, CommandNode<db>>newHashMap();
		RootCommandNode<db> rootCommandNode4 = new RootCommandNode<>();
		map3.put(this.b.getRoot(), rootCommandNode4);
		this.a(this.b.getRoot(), rootCommandNode4, ze.cv(), map3);
		ze.b.a(new od(rootCommandNode4));
	}

	private void a(CommandNode<cz> commandNode1, CommandNode<db> commandNode2, cz cz, Map<CommandNode<cz>, CommandNode<db>> map) {
		for (CommandNode<cz> commandNode7 : commandNode1.getChildren()) {
			if (commandNode7.canUse(cz)) {
				ArgumentBuilder<db, ?> argumentBuilder8 = commandNode7.createBuilder();
				argumentBuilder8.requires(db -> true);
				if (argumentBuilder8.getCommand() != null) {
					argumentBuilder8.executes(commandContext -> 0);
				}

				if (argumentBuilder8 instanceof RequiredArgumentBuilder) {
					RequiredArgumentBuilder<db, ?> requiredArgumentBuilder9 = (RequiredArgumentBuilder<db, ?>)argumentBuilder8;
					if (requiredArgumentBuilder9.getSuggestionsProvider() != null) {
						requiredArgumentBuilder9.suggests(fj.b(requiredArgumentBuilder9.getSuggestionsProvider()));
					}
				}

				if (argumentBuilder8.getRedirect() != null) {
					argumentBuilder8.redirect((CommandNode<db>)map.get(argumentBuilder8.getRedirect()));
				}

				CommandNode<db> commandNode9 = argumentBuilder8.build();
				map.put(commandNode7, commandNode9);
				commandNode2.addChild(commandNode9);
				if (!commandNode7.getChildren().isEmpty()) {
					this.a(commandNode7, commandNode9, cz, map);
				}
			}
		}
	}

	public static LiteralArgumentBuilder<cz> a(String string) {
		return LiteralArgumentBuilder.literal(string);
	}

	public static <T> RequiredArgumentBuilder<cz, T> a(String string, ArgumentType<T> argumentType) {
		return RequiredArgumentBuilder.argument(string, argumentType);
	}

	public static Predicate<String> a(da.b b) {
		return string -> {
			try {
				b.parse(new StringReader(string));
				return true;
			} catch (CommandSyntaxException var3) {
				return false;
			}
		};
	}

	public CommandDispatcher<cz> a() {
		return this.b;
	}

	@Nullable
	public static <S> CommandSyntaxException a(ParseResults<S> parseResults) {
		if (!parseResults.getReader().canRead()) {
			return null;
		} else if (parseResults.getExceptions().size() == 1) {
			return (CommandSyntaxException)parseResults.getExceptions().values().iterator().next();
		} else {
			return parseResults.getContext().getRange().isEmpty()
				? CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext(parseResults.getReader())
				: CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(parseResults.getReader());
		}
	}

	public static enum a {
		ALL(true, true),
		DEDICATED(false, true),
		INTEGRATED(true, false);

		private final boolean d;
		private final boolean e;

		private a(boolean boolean3, boolean boolean4) {
			this.d = boolean3;
			this.e = boolean4;
		}
	}

	@FunctionalInterface
	public interface b {
		void parse(StringReader stringReader) throws CommandSyntaxException;
	}
}
