import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class abh implements aba {
	private static final Logger a = LogManager.getLogger();
	private final Map<String, aaw> b = Maps.<String, aaw>newHashMap();
	private final List<aax> c = Lists.<aax>newArrayList();
	private final List<aax> d = Lists.<aax>newArrayList();
	private final Set<String> e = Sets.<String>newLinkedHashSet();
	private final List<aae> f = Lists.<aae>newArrayList();
	private final aaf g;

	public abh(aaf aaf) {
		this.g = aaf;
	}

	public void a(aae aae) {
		this.f.add(aae);

		for (String string4 : aae.a(this.g)) {
			this.e.add(string4);
			aaw aaw5 = (aaw)this.b.get(string4);
			if (aaw5 == null) {
				aaw5 = new aaw(this.g, string4);
				this.b.put(string4, aaw5);
			}

			aaw5.a(aae);
		}
	}

	@Override
	public abb a(uh uh) throws IOException {
		abc abc3 = (abc)this.b.get(uh.b());
		if (abc3 != null) {
			return abc3.a(uh);
		} else {
			throw new FileNotFoundException(uh.toString());
		}
	}

	@Override
	public List<abb> c(uh uh) throws IOException {
		abc abc3 = (abc)this.b.get(uh.b());
		if (abc3 != null) {
			return abc3.c(uh);
		} else {
			throw new FileNotFoundException(uh.toString());
		}
	}

	@Override
	public Collection<uh> a(uh uh, Predicate<String> predicate) {
		abc abc4 = (abc)this.b.get(uh.b());
		return (Collection<uh>)(abc4 != null ? abc4.a(uh.a(), predicate) : ImmutableSet.<uh>of());
	}

	@Override
	public Collection<uh> a(String string, Predicate<String> predicate) {
		Set<uh> set4 = Sets.<uh>newHashSet();

		for (aaw aaw6 : this.b.values()) {
			set4.addAll(aaw6.a(string, predicate));
		}

		List<uh> list5 = Lists.<uh>newArrayList(set4);
		Collections.sort(list5);
		return list5;
	}

	private void c() {
		this.b.clear();
		this.e.clear();
		this.f.forEach(aae::close);
		this.f.clear();
	}

	@Override
	public void close() {
		this.c();
	}

	@Override
	public void a(aax aax) {
		this.c.add(aax);
		this.d.add(aax);
	}

	protected aaz b(Executor executor1, Executor executor2, List<aax> list, CompletableFuture<ael> completableFuture) {
		aaz aaz6;
		if (a.isDebugEnabled()) {
			aaz6 = new aay(this, Lists.<aax>newArrayList(list), executor1, executor2, completableFuture);
		} else {
			aaz6 = abg.a(this, Lists.<aax>newArrayList(list), executor1, executor2, completableFuture);
		}

		this.d.clear();
		return aaz6;
	}

	@Override
	public aaz a(Executor executor1, Executor executor2, CompletableFuture<ael> completableFuture, List<aae> list) {
		this.c();
		a.info("Reloading ResourceManager: {}", () -> (String)list.stream().map(aae::a).collect(Collectors.joining(", ")));

		for (aae aae7 : list) {
			try {
				this.a(aae7);
			} catch (Exception var8) {
				a.error("Failed to add resource pack {}", aae7.a(), var8);
				return new abh.a(new abh.b(aae7, var8));
			}
		}

		return this.b(executor1, executor2, this.c, completableFuture);
	}

	static class a implements aaz {
		private final abh.b a;
		private final CompletableFuture<ael> b;

		public a(abh.b b) {
			this.a = b;
			this.b = new CompletableFuture();
			this.b.completeExceptionally(b);
		}

		@Override
		public CompletableFuture<ael> a() {
			return this.b;
		}
	}

	public static class b extends RuntimeException {
		private final aae a;

		public b(aae aae, Throwable throwable) {
			super(aae.a(), throwable);
			this.a = aae;
		}
	}
}
