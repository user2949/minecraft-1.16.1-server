import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

public class ev {
	public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("argument.item.tag.disallowed"));
	public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("argument.item.id.invalid", object));
	private static final BiFunction<SuggestionsBuilder, adg<bke>, CompletableFuture<Suggestions>> c = (suggestionsBuilder, adg) -> suggestionsBuilder.buildFuture();
	private final StringReader d;
	private final boolean e;
	private final Map<cgl<?>, Comparable<?>> f = Maps.<cgl<?>, Comparable<?>>newHashMap();
	private bke g;
	@Nullable
	private le h;
	private uh i = new uh("");
	private int j;
	private BiFunction<SuggestionsBuilder, adg<bke>, CompletableFuture<Suggestions>> k = c;

	public ev(StringReader stringReader, boolean boolean2) {
		this.d = stringReader;
		this.e = boolean2;
	}

	public bke b() {
		return this.g;
	}

	@Nullable
	public le c() {
		return this.h;
	}

	public uh d() {
		return this.i;
	}

	public void e() throws CommandSyntaxException {
		int integer2 = this.d.getCursor();
		uh uh3 = uh.a(this.d);
		this.g = (bke)gl.am.b(uh3).orElseThrow(() -> {
			this.d.setCursor(integer2);
			return b.createWithContext(this.d, uh3.toString());
		});
	}

	public void f() throws CommandSyntaxException {
		if (!this.e) {
			throw a.create();
		} else {
			this.k = this::c;
			this.d.expect('#');
			this.j = this.d.getCursor();
			this.i = uh.a(this.d);
		}
	}

	public void g() throws CommandSyntaxException {
		this.h = new lv(this.d).f();
	}

	public ev h() throws CommandSyntaxException {
		this.k = this::d;
		if (this.d.canRead() && this.d.peek() == '#') {
			this.f();
		} else {
			this.e();
			this.k = this::b;
		}

		if (this.d.canRead() && this.d.peek() == '{') {
			this.k = c;
			this.g();
		}

		return this;
	}

	private CompletableFuture<Suggestions> b(SuggestionsBuilder suggestionsBuilder, adg<bke> adg) {
		if (suggestionsBuilder.getRemaining().isEmpty()) {
			suggestionsBuilder.suggest(String.valueOf('{'));
		}

		return suggestionsBuilder.buildFuture();
	}

	private CompletableFuture<Suggestions> c(SuggestionsBuilder suggestionsBuilder, adg<bke> adg) {
		return db.a(adg.a(), suggestionsBuilder.createOffset(this.j));
	}

	private CompletableFuture<Suggestions> d(SuggestionsBuilder suggestionsBuilder, adg<bke> adg) {
		if (this.e) {
			db.a(adg.a(), suggestionsBuilder, String.valueOf('#'));
		}

		return db.a(gl.am.b(), suggestionsBuilder);
	}

	public CompletableFuture<Suggestions> a(SuggestionsBuilder suggestionsBuilder, adg<bke> adg) {
		return (CompletableFuture<Suggestions>)this.k.apply(suggestionsBuilder.createOffset(this.d.getCursor()), adg);
	}
}
