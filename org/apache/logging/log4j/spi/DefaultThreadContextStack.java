package org.apache.logging.log4j.spi;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.ThreadContext.ContextStack;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.apache.logging.log4j.util.StringBuilders;

public class DefaultThreadContextStack implements ThreadContextStack, StringBuilderFormattable {
	private static final long serialVersionUID = 5050501L;
	private static final ThreadLocal<MutableThreadContextStack> STACK = new ThreadLocal();
	private final boolean useStack;

	public DefaultThreadContextStack(boolean useStack) {
		this.useStack = useStack;
	}

	private MutableThreadContextStack getNonNullStackCopy() {
		MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
		return (MutableThreadContextStack)(values == null ? new MutableThreadContextStack() : values.copy());
	}

	public boolean add(String s) {
		if (!this.useStack) {
			return false;
		} else {
			MutableThreadContextStack copy = this.getNonNullStackCopy();
			copy.add(s);
			copy.freeze();
			STACK.set(copy);
			return true;
		}
	}

	public boolean addAll(Collection<? extends String> strings) {
		if (this.useStack && !strings.isEmpty()) {
			MutableThreadContextStack copy = this.getNonNullStackCopy();
			copy.addAll(strings);
			copy.freeze();
			STACK.set(copy);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<String> asList() {
		MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
		return values == null ? Collections.emptyList() : values.asList();
	}

	public void clear() {
		STACK.remove();
	}

	public boolean contains(Object o) {
		MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
		return values != null && values.contains(o);
	}

	public boolean containsAll(Collection<?> objects) {
		if (objects.isEmpty()) {
			return true;
		} else {
			MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
			return values != null && values.containsAll(objects);
		}
	}

	public ThreadContextStack copy() {
		MutableThreadContextStack values = null;
		return (ThreadContextStack)(this.useStack && (values = (MutableThreadContextStack)STACK.get()) != null ? values.copy() : new MutableThreadContextStack());
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else {
			if (obj instanceof DefaultThreadContextStack) {
				DefaultThreadContextStack other = (DefaultThreadContextStack)obj;
				if (this.useStack != other.useStack) {
					return false;
				}
			}

			if (!(obj instanceof ThreadContextStack)) {
				return false;
			} else {
				ThreadContextStack other = (ThreadContextStack)obj;
				MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
				return values == null ? false : values.equals(other);
			}
		}
	}

	@Override
	public int getDepth() {
		MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
		return values == null ? 0 : values.getDepth();
	}

	public int hashCode() {
		MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
		int prime = 31;
		int result = 1;
		return 31 * result + (values == null ? 0 : values.hashCode());
	}

	public boolean isEmpty() {
		MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
		return values == null || values.isEmpty();
	}

	public Iterator<String> iterator() {
		MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
		if (values == null) {
			List<String> empty = Collections.emptyList();
			return empty.iterator();
		} else {
			return values.iterator();
		}
	}

	@Override
	public String peek() {
		MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
		return values != null && values.size() != 0 ? values.peek() : "";
	}

	@Override
	public String pop() {
		if (!this.useStack) {
			return "";
		} else {
			MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
			if (values != null && values.size() != 0) {
				MutableThreadContextStack copy = (MutableThreadContextStack)values.copy();
				String result = copy.pop();
				copy.freeze();
				STACK.set(copy);
				return result;
			} else {
				return "";
			}
		}
	}

	@Override
	public void push(String message) {
		if (this.useStack) {
			this.add(message);
		}
	}

	public boolean remove(Object o) {
		if (!this.useStack) {
			return false;
		} else {
			MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
			if (values != null && values.size() != 0) {
				MutableThreadContextStack copy = (MutableThreadContextStack)values.copy();
				boolean result = copy.remove(o);
				copy.freeze();
				STACK.set(copy);
				return result;
			} else {
				return false;
			}
		}
	}

	public boolean removeAll(Collection<?> objects) {
		if (this.useStack && !objects.isEmpty()) {
			MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
			if (values != null && !values.isEmpty()) {
				MutableThreadContextStack copy = (MutableThreadContextStack)values.copy();
				boolean result = copy.removeAll(objects);
				copy.freeze();
				STACK.set(copy);
				return result;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean retainAll(Collection<?> objects) {
		if (this.useStack && !objects.isEmpty()) {
			MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
			if (values != null && !values.isEmpty()) {
				MutableThreadContextStack copy = (MutableThreadContextStack)values.copy();
				boolean result = copy.retainAll(objects);
				copy.freeze();
				STACK.set(copy);
				return result;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public int size() {
		MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
		return values == null ? 0 : values.size();
	}

	public Object[] toArray() {
		MutableThreadContextStack result = (MutableThreadContextStack)STACK.get();
		return (Object[])(result == null ? new String[0] : result.toArray(new Object[result.size()]));
	}

	public <T> T[] toArray(T[] ts) {
		MutableThreadContextStack result = (MutableThreadContextStack)STACK.get();
		if (result == null) {
			if (ts.length > 0) {
				ts[0] = null;
			}

			return ts;
		} else {
			return (T[])result.toArray(ts);
		}
	}

	public String toString() {
		MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
		return values == null ? "[]" : values.toString();
	}

	@Override
	public void formatTo(StringBuilder buffer) {
		MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
		if (values == null) {
			buffer.append("[]");
		} else {
			StringBuilders.appendValue(buffer, values);
		}
	}

	@Override
	public void trim(int depth) {
		if (depth < 0) {
			throw new IllegalArgumentException("Maximum stack depth cannot be negative");
		} else {
			MutableThreadContextStack values = (MutableThreadContextStack)STACK.get();
			if (values != null) {
				MutableThreadContextStack copy = (MutableThreadContextStack)values.copy();
				copy.trim(depth);
				copy.freeze();
				STACK.set(copy);
			}
		}
	}

	@Override
	public ContextStack getImmutableStackOrNull() {
		return (ContextStack)STACK.get();
	}
}
