import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.MinecraftServer;

public class vp {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		LiteralArgumentBuilder<cz> literalArgumentBuilder2 = da.a("defaultgamemode").requires(cz -> cz.c(2));

		for (bpy bpy6 : bpy.values()) {
			if (bpy6 != bpy.NOT_SET) {
				literalArgumentBuilder2.then(da.a(bpy6.b()).executes(commandContext -> a(commandContext.getSource(), bpy6)));
			}
		}

		commandDispatcher.register(literalArgumentBuilder2);
	}

	private static int a(cz cz, bpy bpy) {
		int integer3 = 0;
		MinecraftServer minecraftServer4 = cz.j();
		minecraftServer4.a(bpy);
		if (minecraftServer4.aj()) {
			for (ze ze6 : minecraftServer4.ac().s()) {
				if (ze6.d.b() != bpy) {
					ze6.a(bpy);
					integer3++;
				}
			}
		}

		cz.a(new ne("commands.defaultgamemode.success", bpy.c()), true);
		return integer3;
	}
}
