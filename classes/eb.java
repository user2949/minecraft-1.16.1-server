import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class eb implements ArgumentType<UUID> {
	public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("argument.uuid.invalid"));
	private static final Collection<String> b = Arrays.asList("dd12be42-52a9-4a91-a8a1-11c01849e498");
	private static final Pattern c = Pattern.compile("^([-A-Fa-f0-9]+)");

	public static UUID a(CommandContext<cz> commandContext, String string) {
		return commandContext.getArgument(string, UUID.class);
	}

	public static eb a() {
		return new eb();
	}

	public UUID parse(StringReader stringReader) throws CommandSyntaxException {
		String string3 = stringReader.getRemaining();
		Matcher matcher4 = c.matcher(string3);
		if (matcher4.find()) {
			String string5 = matcher4.group(1);

			try {
				UUID uUID6 = UUID.fromString(string5);
				stringReader.setCursor(stringReader.getCursor() + string5.length());
				return uUID6;
			} catch (IllegalArgumentException var6) {
			}
		}

		throw a.create();
	}

	@Override
	public Collection<String> getExamples() {
		return b;
	}
}
