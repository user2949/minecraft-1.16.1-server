import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;

public class de implements ArgumentType<le> {
	private static final Collection<String> a = Arrays.asList("{}", "{foo=bar}");

	private de() {
	}

	public static de a() {
		return new de();
	}

	public static <S> le a(CommandContext<S> commandContext, String string) {
		return commandContext.getArgument(string, le.class);
	}

	public le parse(StringReader stringReader) throws CommandSyntaxException {
		return new lv(stringReader).f();
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}
}
