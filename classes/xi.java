import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import javax.annotation.Nullable;

public class xi {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		RequiredArgumentBuilder<cz, ez> requiredArgumentBuilder2 = da.a("targets", dh.d())
			.executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), null, null))
			.then(
				da.a("*")
					.then(
						da.a("sound", dv.a())
							.suggests(fj.c)
							.executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), null, dv.e(commandContext, "sound")))
					)
			);

		for (acm acm6 : acm.values()) {
			requiredArgumentBuilder2.then(
				da.a(acm6.a())
					.executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), acm6, null))
					.then(
						da.a("sound", dv.a())
							.suggests(fj.c)
							.executes(commandContext -> a(commandContext.getSource(), dh.f(commandContext, "targets"), acm6, dv.e(commandContext, "sound")))
					)
			);
		}

		commandDispatcher.register(da.a("stopsound").requires(cz -> cz.c(2)).then(requiredArgumentBuilder2));
	}

	private static int a(cz cz, Collection<ze> collection, @Nullable acm acm, @Nullable uh uh) {
		qn qn5 = new qn(uh, acm);

		for (ze ze7 : collection) {
			ze7.b.a(qn5);
		}

		if (acm != null) {
			if (uh != null) {
				cz.a(new ne("commands.stopsound.success.source.sound", uh, acm.a()), true);
			} else {
				cz.a(new ne("commands.stopsound.success.source.any", acm.a()), true);
			}
		} else if (uh != null) {
			cz.a(new ne("commands.stopsound.success.sourceless.sound", uh), true);
		} else {
			cz.a(new ne("commands.stopsound.success.sourceless.any"), true);
		}

		return collection.size();
	}
}
