import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;

public class el implements ArgumentType<ej> {
	private static final Collection<String> b = Arrays.asList("0 0", "~ ~", "~-5 ~5");
	public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("argument.rotation.incomplete"));

	public static el a() {
		return new el();
	}

	public static ej a(CommandContext<cz> commandContext, String string) {
		return commandContext.getArgument(string, ej.class);
	}

	public ej parse(StringReader stringReader) throws CommandSyntaxException {
		int integer3 = stringReader.getCursor();
		if (!stringReader.canRead()) {
			throw a.createWithContext(stringReader);
		} else {
			ep ep4 = ep.a(stringReader, false);
			if (stringReader.canRead() && stringReader.peek() == ' ') {
				stringReader.skip();
				ep ep5 = ep.a(stringReader, false);
				return new eq(ep5, ep4, new ep(true, 0.0));
			} else {
				stringReader.setCursor(integer3);
				throw a.createWithContext(stringReader);
			}
		}
	}

	@Override
	public Collection<String> getExamples() {
		return b;
	}
}
