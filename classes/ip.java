import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ip {
	private static final ip a = new ip(ImmutableList.of());
	private static final Comparator<cgl.a<?>> b = Comparator.comparing(a -> a.a().f());
	private final List<cgl.a<?>> c;

	public ip a(cgl.a<?> a) {
		return new ip(ImmutableList.<cgl.a<?>>builder().addAll(this.c).add(a).build());
	}

	public ip a(ip ip) {
		return new ip(ImmutableList.<cgl.a<?>>builder().addAll(this.c).addAll(ip.c).build());
	}

	private ip(List<cgl.a<?>> list) {
		this.c = list;
	}

	public static ip a() {
		return a;
	}

	public static ip a(cgl.a<?>... arr) {
		return new ip(ImmutableList.copyOf(arr));
	}

	public boolean equals(Object object) {
		return this == object || object instanceof ip && this.c.equals(((ip)object).c);
	}

	public int hashCode() {
		return this.c.hashCode();
	}

	public String b() {
		return (String)this.c.stream().sorted(b).map(cgl.a::toString).collect(Collectors.joining(","));
	}

	public String toString() {
		return this.b();
	}
}
