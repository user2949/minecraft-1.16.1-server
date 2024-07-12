import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class aar<T extends aap> implements AutoCloseable {
	private final Set<aat> a;
	private Map<String, T> b = ImmutableMap.of();
	private List<T> c = ImmutableList.of();
	private final aap.a<T> d;

	public aar(aap.a<T> a, aat... arr) {
		this.d = a;
		this.a = ImmutableSet.copyOf(arr);
	}

	public void a() {
		List<String> list2 = (List<String>)this.c.stream().map(aap::e).collect(ImmutableList.toImmutableList());
		this.close();
		this.b = this.g();
		this.c = this.b(list2);
	}

	private Map<String, T> g() {
		Map<String, T> map2 = Maps.newTreeMap();

		for (aat aat4 : this.a) {
			aat4.a(aap -> {
				aap var10000 = (aap)map2.put(aap.e(), aap);
			}, this.d);
		}

		return ImmutableMap.copyOf(map2);
	}

	public void a(Collection<String> collection) {
		this.c = this.b(collection);
	}

	private List<T> b(Collection<String> collection) {
		List<T> list3 = (List<T>)this.c(collection).collect(Collectors.toList());

		for (T aap5 : this.b.values()) {
			if (aap5.f() && !list3.contains(aap5)) {
				aap5.h().a(list3, aap5, Functions.identity(), false);
			}
		}

		return ImmutableList.copyOf(list3);
	}

	private Stream<T> c(Collection<String> collection) {
		return collection.stream().map(this.b::get).filter(Objects::nonNull);
	}

	public Collection<String> b() {
		return this.b.keySet();
	}

	public Collection<T> c() {
		return this.b.values();
	}

	public Collection<String> d() {
		return (Collection<String>)this.c.stream().map(aap::e).collect(ImmutableSet.toImmutableSet());
	}

	public Collection<T> e() {
		return this.c;
	}

	@Nullable
	public T a(String string) {
		return (T)this.b.get(string);
	}

	public void close() {
		this.b.values().forEach(aap::close);
	}

	public boolean b(String string) {
		return this.b.containsKey(string);
	}

	public List<aae> f() {
		return (List<aae>)this.c.stream().map(aap::d).collect(ImmutableList.toImmutableList());
	}
}
