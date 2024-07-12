import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import java.util.Collections;

public class vz {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		LiteralArgumentBuilder<cz> literalArgumentBuilder2 = da.a("gamemode").requires(cz -> cz.c(2));

		for (bpy bpy6 : bpy.values()) {
			if (bpy6 != bpy.NOT_SET) {
				literalArgumentBuilder2.then(
					da.a(bpy6.b())
						.executes(commandContext -> a(commandContext, Collections.singleton(commandContext.getSource().h()), bpy6))
						.then(da.a("target", dh.d()).executes(commandContext -> a(commandContext, dh.f(commandContext, "target"), bpy6)))
				);
			}
		}

		commandDispatcher.register(literalArgumentBuilder2);
	}

	private static void a(cz cz, ze ze, bpy bpy) {
		mr mr4 = new ne("gameMode." + bpy.b());
		if (cz.f() == ze) {
			cz.a(new ne("commands.gamemode.success.self", mr4), true);
		} else {
			if (cz.e().S().b(bpx.n)) {
				ze.a(new ne("gameMode.changed", mr4), v.b);
			}

			cz.a(new ne("commands.gamemode.success.other", ze.d(), mr4), true);
		}
	}

	private static int a(CommandContext<cz> commandContext, Collection<ze> collection, bpy bpy) {
		int integer4 = 0;

		for (ze ze6 : collection) {
			if (ze6.d.b() != bpy) {
				ze6.a(bpy);
				a(commandContext.getSource(), ze6, bpy);
				integer4++;
			}
		}

		return integer4;
	}
}
