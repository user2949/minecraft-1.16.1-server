import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

public class adh implements aax {
	private final ade<bvr> a = new ade<>(gl.aj, "tags/blocks", "block");
	private final ade<bke> b = new ade<>(gl.am, "tags/items", "item");
	private final ade<cwz> c = new ade<>(gl.ah, "tags/fluids", "fluid");
	private final ade<aoq<?>> d = new ade<>(gl.al, "tags/entity_types", "entity_type");

	public ade<bvr> a() {
		return this.a;
	}

	public ade<bke> b() {
		return this.b;
	}

	public ade<cwz> d() {
		return this.c;
	}

	public ade<aoq<?>> e() {
		return this.d;
	}

	public void a(mg mg) {
		this.a.a(mg);
		this.b.a(mg);
		this.c.a(mg);
		this.d.a(mg);
	}

	public static adh b(mg mg) {
		adh adh2 = new adh();
		adh2.a().b(mg);
		adh2.b().b(mg);
		adh2.d().b(mg);
		adh2.e().b(mg);
		return adh2;
	}

	@Override
	public CompletableFuture<Void> a(aax.a a, abc abc, ami ami3, ami ami4, Executor executor5, Executor executor6) {
		CompletableFuture<Map<uh, adf.a>> completableFuture8 = this.a.a(abc, executor5);
		CompletableFuture<Map<uh, adf.a>> completableFuture9 = this.b.a(abc, executor5);
		CompletableFuture<Map<uh, adf.a>> completableFuture10 = this.c.a(abc, executor5);
		CompletableFuture<Map<uh, adf.a>> completableFuture11 = this.d.a(abc, executor5);
		return CompletableFuture.allOf(completableFuture8, completableFuture9, completableFuture10, completableFuture11)
			.thenCompose(a::a)
			.thenAcceptAsync(
				void5 -> {
					this.a.a((Map<uh, adf.a>)completableFuture8.join());
					this.b.a((Map<uh, adf.a>)completableFuture9.join());
					this.c.a((Map<uh, adf.a>)completableFuture10.join());
					this.d.a((Map<uh, adf.a>)completableFuture11.join());
					adb.a(this.a, this.b, this.c, this.d);
					Multimap<String, uh> multimap7 = HashMultimap.create();
					multimap7.putAll("blocks", acx.b(this.a));
					multimap7.putAll("items", ada.b(this.b));
					multimap7.putAll("fluids", acz.b(this.c));
					multimap7.putAll("entity_types", acy.b(this.d));
					if (!multimap7.isEmpty()) {
						throw new IllegalStateException(
							"Missing required tags: "
								+ (String)multimap7.entries().stream().map(entry -> (String)entry.getKey() + ":" + entry.getValue()).sorted().collect(Collectors.joining(","))
						);
					}
				},
				executor6
			);
	}

	public void f() {
		acx.a(this.a);
		ada.a(this.b);
		acz.a(this.c);
		acy.a(this.d);
		bvs.a();
	}
}
