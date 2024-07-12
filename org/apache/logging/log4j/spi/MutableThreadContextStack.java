package org.apache.logging.log4j.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.ThreadContext.ContextStack;
import org.apache.logging.log4j.util.StringBuilderFormattable;

public class MutableThreadContextStack implements ThreadContextStack, StringBuilderFormattable {
	private static final long serialVersionUID = 50505011L;
	private final List<String> list;
	private boolean frozen;

	public MutableThreadContextStack() {
		this(new ArrayList());
	}

	public MutableThreadContextStack(List<String> list) {
		this.list = new ArrayList(list);
	}

	private MutableThreadContextStack(MutableThreadContextStack stack) {
		this.list = new ArrayList(stack.list);
	}

	private void checkInvariants() {
		if (this.frozen) {
			throw new UnsupportedOperationException("context stack has been frozen");
		}
	}

	@Override
	public String pop() {
		this.checkInvariants();
		if (this.list.isEmpty()) {
			return null;
		} else {
			int last = this.list.size() - 1;
			return (String)this.list.remove(last);
		}
	}

	@Override
	public String peek() {
		if (this.list.isEmpty()) {
			return null;
		} else {
			int last = this.list.size() - 1;
			return (String)this.list.get(last);
		}
	}

	@Override
	public void push(String message) {
		this.checkInvariants();
		this.list.add(message);
	}

	@Override
	public int getDepth() {
		return this.list.size();
	}

	@Override
	public List<String> asList() {
		return this.list;
	}

	@Override
	public void trim(int depth) {
		this.checkInvariants();
		if (depth < 0) {
			throw new IllegalArgumentException("Maximum stack depth cannot be negative");
		} else if (this.list != null) {
			List<String> copy = new ArrayList(this.list.size());
			int count = Math.min(depth, this.list.size());

			for (int i = 0; i < count; i++) {
				copy.add(this.list.get(i));
			}

			this.list.clear();
			this.list.addAll(copy);
		}
	}

	public ThreadContextStack copy() {
		return new MutableThreadContextStack(this);
	}

	public void clear() {
		this.checkInvariants();
		this.list.clear();
	}

	public int size() {
		return this.list.size();
	}

	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	public boolean contains(Object o) {
		return this.list.contains(o);
	}

	public Iterator<String> iterator() {
		return this.list.iterator();
	}

	public Object[] toArray() {
		return this.list.toArray();
	}

	public <T> T[] toArray(T[] ts) {
		return (T[])this.list.toArray(ts);
	}

	public boolean add(String s) {
		this.checkInvariants();
		return this.list.add(s);
	}

	public boolean remove(Object o) {
		this.checkInvariants();
		return this.list.remove(o);
	}

	public boolean containsAll(Collection<?> objects) {
		return this.list.containsAll(objects);
	}

	public boolean addAll(Collection<? extends String> strings) {
		this.checkInvariants();
		return this.list.addAll(strings);
	}

	public boolean removeAll(Collection<?> objects) {
		this.checkInvariants();
		return this.list.removeAll(objects);
	}

	public boolean retainAll(Collection<?> objects) {
		this.checkInvariants();
		return this.list.retainAll(objects);
	}

	public String toString() {
		return String.valueOf(this.list);
	}

	@Override
	public void formatTo(StringBuilder buffer) {
		buffer.append('[');

		for (int i = 0; i < this.list.size(); i++) {
			if (i > 0) {
				buffer.append(',').append(' ');
			}

			buffer.append((String)this.list.get(i));
		}

		buffer.append(']');
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		return 31 * result + (this.list == null ? 0 : this.list.hashCode());
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (!(obj instanceof ThreadContextStack)) {
			return false;
		} else {
			ThreadContextStack other = (ThreadContextStack)obj;
			List<String> otherAsList = other.asList();
			if (this.list == null) {
				if (otherAsList != null) {
					return false;
				}
			} else if (!this.list.equals(otherAsList)) {
				return false;
			}

			return true;
		}
	}

	@Override
	public ContextStack getImmutableStackOrNull() {
		return this.copy();
	}

	public void freeze() {
		this.frozen = true;
	}

	public boolean isFrozen() {
		return this.frozen;
	}
}
