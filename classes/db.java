import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface db {
	Collection<String> l();

	default Collection<String> r() {
		return Collections.emptyList();
	}

	Collection<String> m();

	Collection<uh> n();

	Stream<uh> o();

	CompletableFuture<Suggestions> a(CommandContext<db> commandContext, SuggestionsBuilder suggestionsBuilder);

	default Collection<db.a> s() {
		return Collections.singleton(db.a.b);
	}

	default Collection<db.a> t() {
		return Collections.singleton(db.a.b);
	}

	Set<ug<bqb>> p();

	boolean c(int integer);

	static <T> void a(Iterable<T> iterable, String string, Function<T, uh> function, Consumer<T> consumer) {
		boolean boolean5 = string.indexOf(58) > -1;

		for (T object7 : iterable) {
			uh uh8 = (uh)function.apply(object7);
			if (boolean5) {
				String string9 = uh8.toString();
				if (a(string, string9)) {
					consumer.accept(object7);
				}
			} else if (a(string, uh8.b()) || uh8.b().equals("minecraft") && a(string, uh8.a())) {
				consumer.accept(object7);
			}
		}
	}

	static <T> void a(Iterable<T> iterable, String string2, String string3, Function<T, uh> function, Consumer<T> consumer) {
		if (string2.isEmpty()) {
			iterable.forEach(consumer);
		} else {
			String string6 = Strings.commonPrefix(string2, string3);
			if (!string6.isEmpty()) {
				String string7 = string2.substring(string6.length());
				a(iterable, string7, function, consumer);
			}
		}
	}

	static CompletableFuture<Suggestions> a(Iterable<uh> iterable, SuggestionsBuilder suggestionsBuilder, String string) {
		String string4 = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
		a(iterable, string4, string, uh -> uh, uh -> suggestionsBuilder.suggest(string + uh));
		return suggestionsBuilder.buildFuture();
	}

	static CompletableFuture<Suggestions> a(Iterable<uh> iterable, SuggestionsBuilder suggestionsBuilder) {
		String string3 = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
		a(iterable, string3, uh -> uh, uh -> suggestionsBuilder.suggest(uh.toString()));
		return suggestionsBuilder.buildFuture();
	}

	static <T> CompletableFuture<Suggestions> a(
		Iterable<T> iterable, SuggestionsBuilder suggestionsBuilder, Function<T, uh> function3, Function<T, Message> function4
	) {
		String string5 = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
		a(iterable, string5, function3, object -> suggestionsBuilder.suggest(((uh)function3.apply(object)).toString(), (Message)function4.apply(object)));
		return suggestionsBuilder.buildFuture();
	}

	static CompletableFuture<Suggestions> a(Stream<uh> stream, SuggestionsBuilder suggestionsBuilder) {
		return a(stream::iterator, suggestionsBuilder);
	}

	static <T> CompletableFuture<Suggestions> a(Stream<T> stream, SuggestionsBuilder suggestionsBuilder, Function<T, uh> function3, Function<T, Message> function4) {
		return a(stream::iterator, suggestionsBuilder, function3, function4);
	}

	static CompletableFuture<Suggestions> a(String string, Collection<db.a> collection, SuggestionsBuilder suggestionsBuilder, Predicate<String> predicate) {
		List<String> list5 = Lists.<String>newArrayList();
		if (Strings.isNullOrEmpty(string)) {
			for (db.a a7 : collection) {
				String string8 = a7.c + " " + a7.d + " " + a7.e;
				if (predicate.test(string8)) {
					list5.add(a7.c);
					list5.add(a7.c + " " + a7.d);
					list5.add(string8);
				}
			}
		} else {
			String[] arr6 = string.split(" ");
			if (arr6.length == 1) {
				for (db.a a8 : collection) {
					String string9 = arr6[0] + " " + a8.d + " " + a8.e;
					if (predicate.test(string9)) {
						list5.add(arr6[0] + " " + a8.d);
						list5.add(string9);
					}
				}
			} else if (arr6.length == 2) {
				for (db.a a8x : collection) {
					String string9 = arr6[0] + " " + arr6[1] + " " + a8x.e;
					if (predicate.test(string9)) {
						list5.add(string9);
					}
				}
			}
		}

		return b(list5, suggestionsBuilder);
	}

	static CompletableFuture<Suggestions> b(String string, Collection<db.a> collection, SuggestionsBuilder suggestionsBuilder, Predicate<String> predicate) {
		List<String> list5 = Lists.<String>newArrayList();
		if (Strings.isNullOrEmpty(string)) {
			for (db.a a7 : collection) {
				String string8 = a7.c + " " + a7.e;
				if (predicate.test(string8)) {
					list5.add(a7.c);
					list5.add(string8);
				}
			}
		} else {
			String[] arr6 = string.split(" ");
			if (arr6.length == 1) {
				for (db.a a8 : collection) {
					String string9 = arr6[0] + " " + a8.e;
					if (predicate.test(string9)) {
						list5.add(string9);
					}
				}
			}
		}

		return b(list5, suggestionsBuilder);
	}

	static CompletableFuture<Suggestions> b(Iterable<String> iterable, SuggestionsBuilder suggestionsBuilder) {
		String string3 = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);

		for (String string5 : iterable) {
			if (a(string3, string5.toLowerCase(Locale.ROOT))) {
				suggestionsBuilder.suggest(string5);
			}
		}

		return suggestionsBuilder.buildFuture();
	}

	static CompletableFuture<Suggestions> b(Stream<String> stream, SuggestionsBuilder suggestionsBuilder) {
		String string3 = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
		stream.filter(string2 -> a(string3, string2.toLowerCase(Locale.ROOT))).forEach(suggestionsBuilder::suggest);
		return suggestionsBuilder.buildFuture();
	}

	static CompletableFuture<Suggestions> a(String[] arr, SuggestionsBuilder suggestionsBuilder) {
		String string3 = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);

		for (String string7 : arr) {
			if (a(string3, string7.toLowerCase(Locale.ROOT))) {
				suggestionsBuilder.suggest(string7);
			}
		}

		return suggestionsBuilder.buildFuture();
	}

	static boolean a(String string1, String string2) {
		for (int integer3 = 0; !string2.startsWith(string1, integer3); integer3++) {
			integer3 = string2.indexOf(95, integer3);
			if (integer3 < 0) {
				return false;
			}
		}

		return true;
	}

	public static class a {
		public static final db.a a = new db.a("^", "^", "^");
		public static final db.a b = new db.a("~", "~", "~");
		public final String c;
		public final String d;
		public final String e;

		public a(String string1, String string2, String string3) {
			this.c = string1;
			this.d = string2;
			this.e = string3;
		}
	}
}
