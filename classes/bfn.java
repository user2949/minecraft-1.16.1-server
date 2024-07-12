import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class bfn {
	public static final bfn a = a("empty").a(0, bfl.b).a();
	public static final bfn b = a("simple").a(5000, bfl.c).a(11000, bfl.e).a();
	public static final bfn c = a("villager_baby").a(10, bfl.b).a(3000, bfl.d).a(6000, bfl.b).a(10000, bfl.d).a(12000, bfl.e).a();
	public static final bfn d = a("villager_default").a(10, bfl.b).a(2000, bfl.c).a(9000, bfl.f).a(11000, bfl.b).a(12000, bfl.e).a();
	private final Map<bfl, bfp> e = Maps.<bfl, bfp>newHashMap();

	protected static bfo a(String string) {
		bfn bfn2 = gl.a(gl.aW, string, new bfn());
		return new bfo(bfn2);
	}

	protected void a(bfl bfl) {
		if (!this.e.containsKey(bfl)) {
			this.e.put(bfl, new bfp());
		}
	}

	protected bfp b(bfl bfl) {
		return (bfp)this.e.get(bfl);
	}

	protected List<bfp> c(bfl bfl) {
		return (List<bfp>)this.e.entrySet().stream().filter(entry -> entry.getKey() != bfl).map(Entry::getValue).collect(Collectors.toList());
	}

	public bfl a(int integer) {
		return (bfl)this.e.entrySet().stream().max(Comparator.comparingDouble(entry -> (double)((bfp)entry.getValue()).a(integer))).map(Entry::getKey).orElse(bfl.b);
	}
}
