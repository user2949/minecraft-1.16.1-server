import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;

public class dv implements ArgumentType<uh> {
	private static final Collection<String> a = Arrays.asList("foo", "foo:bar", "012");
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("advancement.advancementNotFound", object));
	private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(object -> new ne("recipe.notFound", object));
	private static final DynamicCommandExceptionType d = new DynamicCommandExceptionType(object -> new ne("predicate.unknown", object));
	private static final DynamicCommandExceptionType e = new DynamicCommandExceptionType(object -> new ne("attribute.unknown", object));

	public static dv a() {
		return new dv();
	}

	public static w a(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		uh uh3 = commandContext.getArgument(string, uh.class);
		w w4 = commandContext.getSource().j().ay().a(uh3);
		if (w4 == null) {
			throw b.create(uh3);
		} else {
			return w4;
		}
	}

	public static bmu<?> b(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		bmv bmv3 = commandContext.getSource().j().aD();
		uh uh4 = commandContext.getArgument(string, uh.class);
		return (bmu<?>)bmv3.a(uh4).orElseThrow(() -> c.create(uh4));
	}

	public static ddm c(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		uh uh3 = commandContext.getArgument(string, uh.class);
		day day4 = commandContext.getSource().j().aI();
		ddm ddm5 = day4.a(uh3);
		if (ddm5 == null) {
			throw d.create(uh3);
		} else {
			return ddm5;
		}
	}

	public static aps d(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		uh uh3 = commandContext.getArgument(string, uh.class);
		return (aps)gl.aP.b(uh3).orElseThrow(() -> e.create(uh3));
	}

	public static uh e(CommandContext<cz> commandContext, String string) {
		return commandContext.getArgument(string, uh.class);
	}

	public uh parse(StringReader stringReader) throws CommandSyntaxException {
		return uh.a(stringReader);
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}
}
