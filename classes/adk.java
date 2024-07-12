import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class adk<T> extends AbstractCollection<T> {
	private final Map<Class<?>, List<T>> a = Maps.<Class<?>, List<T>>newHashMap();
	private final Class<T> b;
	private final List<T> c = Lists.<T>newArrayList();

	public adk(Class<T> class1) {
		this.b = class1;
		this.a.put(class1, this.c);
	}

	public boolean add(T object) {
		boolean boolean3 = false;

		for (Entry<Class<?>, List<T>> entry5 : this.a.entrySet()) {
			if (((Class)entry5.getKey()).isInstance(object)) {
				boolean3 |= ((List)entry5.getValue()).add(object);
			}
		}

		return boolean3;
	}

	public boolean remove(Object object) {
		boolean boolean3 = false;

		for (Entry<Class<?>, List<T>> entry5 : this.a.entrySet()) {
			if (((Class)entry5.getKey()).isInstance(object)) {
				List<T> list6 = (List<T>)entry5.getValue();
				boolean3 |= list6.remove(object);
			}
		}

		return boolean3;
	}

	public boolean contains(Object object) {
		return this.a(object.getClass()).contains(object);
	}

	public <S> Collection<S> a(Class<S> class1) {
		if (!this.b.isAssignableFrom(class1)) {
			throw new IllegalArgumentException("Don't know how to search for " + class1);
		} else {
			List<T> list3 = (List<T>)this.a.computeIfAbsent(class1, class1x -> (List)this.c.stream().filter(class1x::isInstance).collect(Collectors.toList()));
			return Collections.unmodifiableCollection(list3);
		}
	}

	public Iterator<T> iterator() {
		return (Iterator<T>)(this.c.isEmpty() ? Collections.emptyIterator() : Iterators.unmodifiableIterator(this.c.iterator()));
	}

	public List<T> a() {
		return ImmutableList.copyOf(this.c);
	}

	public int size() {
		return this.c.size();
	}
}
