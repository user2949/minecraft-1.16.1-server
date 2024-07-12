import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class bfo {
	private final bfn a;
	private final List<bfo.a> b = Lists.<bfo.a>newArrayList();

	public bfo(bfn bfn) {
		this.a = bfn;
	}

	public bfo a(int integer, bfl bfl) {
		this.b.add(new bfo.a(integer, bfl));
		return this;
	}

	public bfn a() {
		((Set)this.b.stream().map(bfo.a::b).collect(Collectors.toSet())).forEach(this.a::a);
		this.b.forEach(a -> {
			bfl bfl3 = a.b();
			this.a.c(bfl3).forEach(bfp -> bfp.a(a.a(), 0.0F));
			this.a.b(bfl3).a(a.a(), 1.0F);
		});
		return this.a;
	}

	static class a {
		private final int a;
		private final bfl b;

		public a(int integer, bfl bfl) {
			this.a = integer;
			this.b = bfl;
		}

		public int a() {
			return this.a;
		}

		public bfl b() {
			return this.b;
		}
	}
}
