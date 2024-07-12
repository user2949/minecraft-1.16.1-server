import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;

public class dl implements ArgumentType<dl.a> {
	private static final Collection<String> a = Arrays.asList("Hello world!", "foo", "@e", "Hello @p :)");

	public static dl a() {
		return new dl();
	}

	public static mr a(CommandContext<cz> commandContext, String string) throws CommandSyntaxException {
		return commandContext.<dl.a>getArgument(string, dl.a.class).a(commandContext.getSource(), commandContext.getSource().c(2));
	}

	public dl.a parse(StringReader stringReader) throws CommandSyntaxException {
		return dl.a.a(stringReader, true);
	}

	@Override
	public Collection<String> getExamples() {
		return a;
	}

	public static class a {
		private final String a;
		private final dl.b[] b;

		public a(String string, dl.b[] arr) {
			this.a = string;
			this.b = arr;
		}

		public mr a(cz cz, boolean boolean2) throws CommandSyntaxException {
			if (this.b.length != 0 && boolean2) {
				mx mx4 = new nd(this.a.substring(0, this.b[0].a()));
				int integer5 = this.b[0].a();

				for (dl.b b9 : this.b) {
					mr mr10 = b9.a(cz);
					if (integer5 < b9.a()) {
						mx4.c(this.a.substring(integer5, b9.a()));
					}

					if (mr10 != null) {
						mx4.a(mr10);
					}

					integer5 = b9.b();
				}

				if (integer5 < this.a.length()) {
					mx4.c(this.a.substring(integer5, this.a.length()));
				}

				return mx4;
			} else {
				return new nd(this.a);
			}
		}

		public static dl.a a(StringReader stringReader, boolean boolean2) throws CommandSyntaxException {
			String string3 = stringReader.getString().substring(stringReader.getCursor(), stringReader.getTotalLength());
			if (!boolean2) {
				stringReader.setCursor(stringReader.getTotalLength());
				return new dl.a(string3, new dl.b[0]);
			} else {
				List<dl.b> list4 = Lists.<dl.b>newArrayList();
				int integer5 = stringReader.getCursor();

				while (true) {
					int integer6;
					ez ez7;
					while (true) {
						if (!stringReader.canRead()) {
							return new dl.a(string3, (dl.b[])list4.toArray(new dl.b[list4.size()]));
						}

						if (stringReader.peek() == '@') {
							integer6 = stringReader.getCursor();

							try {
								fa fa8 = new fa(stringReader);
								ez7 = fa8.t();
								break;
							} catch (CommandSyntaxException var8) {
								if (var8.getType() != fa.d && var8.getType() != fa.b) {
									throw var8;
								}

								stringReader.setCursor(integer6 + 1);
							}
						} else {
							stringReader.skip();
						}
					}

					list4.add(new dl.b(integer6 - integer5, stringReader.getCursor() - integer5, ez7));
				}
			}
		}
	}

	public static class b {
		private final int a;
		private final int b;
		private final ez c;

		public b(int integer1, int integer2, ez ez) {
			this.a = integer1;
			this.b = integer2;
			this.c = ez;
		}

		public int a() {
			return this.a;
		}

		public int b() {
			return this.b;
		}

		@Nullable
		public mr a(cz cz) throws CommandSyntaxException {
			return ez.a(this.c.b(cz));
		}
	}
}
