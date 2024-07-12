import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;

public class dp implements ArgumentType<lu> {
	private static final Collection<String> a = Arrays.asList("0", "0b", "0l", "0.0", "\"foo\"", "{foo=bar}", "[0]");

	private dp() {
	}

	public static dp a() {
		return new dp();
	}

	public static <S> lu a(CommandContext<S> commandContext, String string) {
		return commandContext.getArgument(string, lu.class);
	}

	public lu parse(StringReader stringReader) throws CommandSyntaxException {
		return new lv(stringReader).d();
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}
}
