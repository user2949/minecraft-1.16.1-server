import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;

public class di implements ArgumentType<uh> {
	private static final Collection<String> b = Arrays.asList("minecraft:pig", "cow");
	public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("entity.notFound", object));

	public static di a() {
		return new di();
	}

	public static uh a(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return a(commandContext.getArgument(string, uh.class));
	}

	private static uh a(uh uh) throws CommandSyntaxException {
		gl.al.b(uh).filter(aoq::b).orElseThrow(() -> a.create(uh));
		return uh;
	}

	public uh parse(StringReader stringReader) throws CommandSyntaxException {
		return a(uh.a(stringReader));
	}

	@Override
	public Collection<String> getExamples() {
		return b;
	}
}
