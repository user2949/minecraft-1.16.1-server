import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;

public class dd implements ArgumentType<mr> {
	private static final Collection<String> b = Arrays.asList("\"hello world\"", "\"\"", "\"{\"text\":\"hello world\"}", "[\"\"]");
	public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("argument.component.invalid", object));

	private dd() {
	}

	public static mr a(CommandContext<cz> commandContext, String string) {
		return commandContext.getArgument(string, mr.class);
	}

	public static dd a() {
		return new dd();
	}

	public mr parse(StringReader stringReader) throws CommandSyntaxException {
		try {
			mr mr3 = mr.a.a(stringReader);
			if (mr3 == null) {
				throw a.createWithContext(stringReader, "empty");
			} else {
				return mr3;
			}
		} catch (JsonParseException var4) {
			String string4 = var4.getCause() != null ? var4.getCause().getMessage() : var4.getMessage();
			throw a.createWithContext(stringReader, string4);
		}
	}

	@Override
	public Collection<String> getExamples() {
		return b;
	}
}
