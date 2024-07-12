import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class es implements ArgumentType<es.a> {
	private static final Collection<String> a = Arrays.asList("foo", "foo:bar", "#foo");
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("arguments.function.tag.unknown", object));
	private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(object -> new ne("arguments.function.unknown", object));

	public static es a() {
		return new es();
	}

	public es.a parse(StringReader stringReader) throws CommandSyntaxException {
		if (stringReader.canRead() && stringReader.peek() == '#') {
			stringReader.skip();
			final uh uh3 = uh.a(stringReader);
			return new es.a() {
				@Override
				public Collection<cw> a(CommandContext<cz> commandContext) throws CommandSyntaxException {
					adf<cw> adf3 = es.d(commandContext, uh3);
					return adf3.b();
				}

				@Override
				public Pair<uh, Either<cw, adf<cw>>> b(CommandContext<cz> commandContext) throws CommandSyntaxException {
					return Pair.of(uh3, Either.right(es.d(commandContext, uh3)));
				}
			};
		} else {
			final uh uh3 = uh.a(stringReader);
			return new es.a() {
				@Override
				public Collection<cw> a(CommandContext<cz> commandContext) throws CommandSyntaxException {
					return Collections.singleton(es.c(commandContext, uh3));
				}

				@Override
				public Pair<uh, Either<cw, adf<cw>>> b(CommandContext<cz> commandContext) throws CommandSyntaxException {
					return Pair.of(uh3, Either.left(es.c(commandContext, uh3)));
				}
			};
		}
	}

	private static cw c(CommandContext<cz> commandContext, uh uh) throws CommandSyntaxException {
		return (cw)commandContext.getSource().j().az().a(uh).orElseThrow(() -> c.create(uh.toString()));
	}

	private static adf<cw> d(CommandContext<cz> commandContext, uh uh) throws CommandSyntaxException {
		adf<cw> adf3 = commandContext.getSource().j().az().b(uh);
		if (adf3 == null) {
			throw b.create(uh.toString());
		} else {
			return adf3;
		}
	}

	public static Collection<cw> a(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return commandContext.<es.a>getArgument(string, es.a.class).a(commandContext);
	}

	public static Pair<uh, Either<cw, adf<cw>>> b(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return commandContext.<es.a>getArgument(string, es.a.class).b(commandContext);
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}

	public interface a {
		Collection<cw> a(CommandContext<cz> commandContext) throws CommandSyntaxException;

		Pair<uh, Either<cw, adf<cw>>> b(CommandContext<cz> commandContext) throws CommandSyntaxException;
	}
}
