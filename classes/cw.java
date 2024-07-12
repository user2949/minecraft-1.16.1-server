import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class cw {
	private final cw.c[] a;
	private final uh b;

	public cw(uh uh, cw.c[] arr) {
		this.b = uh;
		this.a = arr;
	}

	public uh a() {
		return this.b;
	}

	public cw.c[] b() {
		return this.a;
	}

	public static cw a(uh uh, CommandDispatcher<cz> commandDispatcher, cz cz, List<String> list) {
		List<cw.c> list5 = Lists.<cw.c>newArrayListWithCapacity(list.size());

		for (int integer6 = 0; integer6 < list.size(); integer6++) {
			int integer7 = integer6 + 1;
			String string8 = ((String)list.get(integer6)).trim();
			StringReader stringReader9 = new StringReader(string8);
			if (stringReader9.canRead() && stringReader9.peek() != '#') {
				if (stringReader9.peek() == '/') {
					stringReader9.skip();
					if (stringReader9.peek() == '/') {
						throw new IllegalArgumentException(
							"Unknown or invalid command '" + string8 + "' on line " + integer7 + " (if you intended to make a comment, use '#' not '//')"
						);
					}

					String string10 = stringReader9.readUnquotedString();
					throw new IllegalArgumentException(
						"Unknown or invalid command '" + string8 + "' on line " + integer7 + " (did you mean '" + string10 + "'? Do not use a preceding forwards slash.)"
					);
				}

				try {
					ParseResults<cz> parseResults10 = commandDispatcher.parse(stringReader9, cz);
					if (parseResults10.getReader().canRead()) {
						throw da.a(parseResults10);
					}

					list5.add(new cw.b(parseResults10));
				} catch (CommandSyntaxException var10) {
					throw new IllegalArgumentException("Whilst parsing command on line " + integer7 + ": " + var10.getMessage());
				}
			}
		}

		return new cw(uh, (cw.c[])list5.toArray(new cw.c[0]));
	}

	public static class a {
		public static final cw.a a = new cw.a((uh)null);
		@Nullable
		private final uh b;
		private boolean c;
		private Optional<cw> d = Optional.empty();

		public a(@Nullable uh uh) {
			this.b = uh;
		}

		public a(cw cw) {
			this.c = true;
			this.b = null;
			this.d = Optional.of(cw);
		}

		public Optional<cw> a(uu uu) {
			if (!this.c) {
				if (this.b != null) {
					this.d = uu.a(this.b);
				}

				this.c = true;
			}

			return this.d;
		}

		@Nullable
		public uh a() {
			return (uh)this.d.map(cw -> cw.b).orElse(this.b);
		}
	}

	public static class b implements cw.c {
		private final ParseResults<cz> a;

		public b(ParseResults<cz> parseResults) {
			this.a = parseResults;
		}

		@Override
		public void a(uu uu, cz cz, ArrayDeque<uu.a> arrayDeque, int integer) throws CommandSyntaxException {
			uu.c().execute(new ParseResults<>(this.a.getContext().withSource(cz), this.a.getReader(), this.a.getExceptions()));
		}

		public String toString() {
			return this.a.getReader().getString();
		}
	}

	public interface c {
		void a(uu uu, cz cz, ArrayDeque<uu.a> arrayDeque, int integer) throws CommandSyntaxException;
	}

	public static class d implements cw.c {
		private final cw.a a;

		public d(cw cw) {
			this.a = new cw.a(cw);
		}

		@Override
		public void a(uu uu, cz cz, ArrayDeque<uu.a> arrayDeque, int integer) {
			this.a.a(uu).ifPresent(cw -> {
				cw.c[] arr6 = cw.b();
				int integer7 = integer - arrayDeque.size();
				int integer8 = Math.min(arr6.length, integer7);

				for (int integer9 = integer8 - 1; integer9 >= 0; integer9--) {
					arrayDeque.addFirst(new uu.a(uu, cz, arr6[integer9]));
				}
			});
		}

		public String toString() {
			return "function " + this.a.a();
		}
	}
}
