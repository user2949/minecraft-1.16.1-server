import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class dbe {
	private final Multimap<String, String> a;
	private final Supplier<String> b;
	private final dcy c;
	private final Function<uh, ddm> d;
	private final Set<uh> e;
	private final Function<uh, daw> f;
	private final Set<uh> g;
	private String h;

	public dbe(dcy dcy, Function<uh, ddm> function2, Function<uh, daw> function3) {
		this(HashMultimap.create(), () -> "", dcy, function2, ImmutableSet.of(), function3, ImmutableSet.of());
	}

	public dbe(
		Multimap<String, String> multimap, Supplier<String> supplier, dcy dcy, Function<uh, ddm> function4, Set<uh> set5, Function<uh, daw> function6, Set<uh> set7
	) {
		this.a = multimap;
		this.b = supplier;
		this.c = dcy;
		this.d = function4;
		this.e = set5;
		this.f = function6;
		this.g = set7;
	}

	private String b() {
		if (this.h == null) {
			this.h = (String)this.b.get();
		}

		return this.h;
	}

	public void a(String string) {
		this.a.put(this.b(), string);
	}

	public dbe b(String string) {
		return new dbe(this.a, () -> this.b() + string, this.c, this.d, this.e, this.f, this.g);
	}

	public dbe a(String string, uh uh) {
		ImmutableSet<uh> immutableSet4 = ImmutableSet.<uh>builder().addAll(this.g).add(uh).build();
		return new dbe(this.a, () -> this.b() + string, this.c, this.d, this.e, this.f, immutableSet4);
	}

	public dbe b(String string, uh uh) {
		ImmutableSet<uh> immutableSet4 = ImmutableSet.<uh>builder().addAll(this.e).add(uh).build();
		return new dbe(this.a, () -> this.b() + string, this.c, this.d, immutableSet4, this.f, this.g);
	}

	public boolean a(uh uh) {
		return this.g.contains(uh);
	}

	public boolean b(uh uh) {
		return this.e.contains(uh);
	}

	public Multimap<String, String> a() {
		return ImmutableMultimap.copyOf(this.a);
	}

	public void a(dau dau) {
		this.c.a(this, dau);
	}

	@Nullable
	public daw c(uh uh) {
		return (daw)this.f.apply(uh);
	}

	@Nullable
	public ddm d(uh uh) {
		return (ddm)this.d.apply(uh);
	}

	public dbe a(dcy dcy) {
		return new dbe(this.a, this.b, dcy, this.d, this.e, this.f, this.g);
	}
}
