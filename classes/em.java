import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

public class em implements ArgumentType<EnumSet<fz.a>> {
	private static final Collection<String> a = Arrays.asList("xyz", "x");
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("arguments.swizzle.invalid"));

	public static em a() {
		return new em();
	}

	public static EnumSet<fz.a> a(CommandContext<cz> commandContext, String string) {
		return commandContext.getArgument(string, EnumSet.class);
	}

	public EnumSet<fz.a> parse(StringReader stringReader) throws CommandSyntaxException {
		EnumSet<fz.a> enumSet3 = EnumSet.noneOf(fz.a.class);

		while (stringReader.canRead() && stringReader.peek() != ' ') {
			char character4 = stringReader.read();
			fz.a a5;
			switch (character4) {
				case 'x':
					a5 = fz.a.X;
					break;
				case 'y':
					a5 = fz.a.Y;
					break;
				case 'z':
					a5 = fz.a.Z;
					break;
				default:
					throw b.create();
			}

			if (enumSet3.contains(a5)) {
				throw b.create();
			}

			enumSet3.add(a5);
		}

		return enumSet3;
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}
}
