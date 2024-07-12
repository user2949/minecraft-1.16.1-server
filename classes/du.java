import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;

public interface du<T extends bx<?>> extends ArgumentType<T> {
	static du.b a() {
		return new du.b();
	}

	public static class a implements du<bx.c> {
		private static final Collection<String> a = Arrays.asList("0..5.2", "0", "-5.4", "-100.76..", "..100");

		public bx.c parse(StringReader stringReader) throws CommandSyntaxException {
			return bx.c.a(stringReader);
		}

		@Override
		public Collection<String> getExamples() {
			return a;
		}

		public static class a extends du.c<du.a> {
			public du.a b(mg mg) {
				return new du.a();
			}
		}
	}

	public static class b implements du<bx.d> {
		private static final Collection<String> a = Arrays.asList("0..5", "0", "-5", "-100..", "..100");

		public static bx.d a(CommandContext<cz> commandContext, String string) {
			return commandContext.getArgument(string, bx.d.class);
		}

		public bx.d parse(StringReader stringReader) throws CommandSyntaxException {
			return bx.d.a(stringReader);
		}

		@Override
		public Collection<String> getExamples() {
			return a;
		}

		public static class a extends du.c<du.b> {
			public du.b b(mg mg) {
				return new du.b();
			}
		}
	}

	public abstract static class c<T extends du<?>> implements fg<T> {
		public void a(T du, mg mg) {
		}

		public void a(T du, JsonObject jsonObject) {
		}
	}
}
