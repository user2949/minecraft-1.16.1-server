package org.apache.logging.log4j;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.spi.CleanableThreadContextMap;
import org.apache.logging.log4j.spi.DefaultThreadContextMap;
import org.apache.logging.log4j.spi.DefaultThreadContextStack;
import org.apache.logging.log4j.spi.NoOpThreadContextMap;
import org.apache.logging.log4j.spi.ReadOnlyThreadContextMap;
import org.apache.logging.log4j.spi.ThreadContextMap;
import org.apache.logging.log4j.spi.ThreadContextMap2;
import org.apache.logging.log4j.spi.ThreadContextMapFactory;
import org.apache.logging.log4j.spi.ThreadContextStack;
import org.apache.logging.log4j.util.PropertiesUtil;

public final class ThreadContext {
	public static final Map<String, String> EMPTY_MAP = Collections.emptyMap();
	public static final ThreadContextStack EMPTY_STACK = new ThreadContext.EmptyThreadContextStack();
	private static final String DISABLE_MAP = "disableThreadContextMap";
	private static final String DISABLE_STACK = "disableThreadContextStack";
	private static final String DISABLE_ALL = "disableThreadContext";
	private static boolean disableAll;
	private static boolean useMap;
	private static boolean useStack;
	private static ThreadContextMap contextMap;
	private static ThreadContextStack contextStack;
	private static ReadOnlyThreadContextMap readOnlyContextMap;

	private ThreadContext() {
	}

	static void init() {
		contextMap = null;
		PropertiesUtil managerProps = PropertiesUtil.getProperties();
		disableAll = managerProps.getBooleanProperty("disableThreadContext");
		useStack = !managerProps.getBooleanProperty("disableThreadContextStack") && !disableAll;
		useMap = !managerProps.getBooleanProperty("disableThreadContextMap") && !disableAll;
		contextStack = new DefaultThreadContextStack(useStack);
		if (!useMap) {
			contextMap = new NoOpThreadContextMap();
		} else {
			contextMap = ThreadContextMapFactory.createThreadContextMap();
		}

		if (contextMap instanceof ReadOnlyThreadContextMap) {
			readOnlyContextMap = (ReadOnlyThreadContextMap)contextMap;
		}
	}

	public static void put(String key, String value) {
		contextMap.put(key, value);
	}

	public static void putAll(Map<String, String> m) {
		if (contextMap instanceof ThreadContextMap2) {
			((ThreadContextMap2)contextMap).putAll(m);
		} else if (contextMap instanceof DefaultThreadContextMap) {
			((DefaultThreadContextMap)contextMap).putAll(m);
		} else {
			for (Entry<String, String> entry : m.entrySet()) {
				contextMap.put((String)entry.getKey(), (String)entry.getValue());
			}
		}
	}

	public static String get(String key) {
		return contextMap.get(key);
	}

	public static void remove(String key) {
		contextMap.remove(key);
	}

	public static void removeAll(Iterable<String> keys) {
		if (contextMap instanceof CleanableThreadContextMap) {
			((CleanableThreadContextMap)contextMap).removeAll(keys);
		} else if (contextMap instanceof DefaultThreadContextMap) {
			((DefaultThreadContextMap)contextMap).removeAll(keys);
		} else {
			for (String key : keys) {
				contextMap.remove(key);
			}
		}
	}

	public static void clearMap() {
		contextMap.clear();
	}

	public static void clearAll() {
		clearMap();
		clearStack();
	}

	public static boolean containsKey(String key) {
		return contextMap.containsKey(key);
	}

	public static Map<String, String> getContext() {
		return contextMap.getCopy();
	}

	public static Map<String, String> getImmutableContext() {
		Map<String, String> map = contextMap.getImmutableMapOrNull();
		return map == null ? EMPTY_MAP : map;
	}

	public static ReadOnlyThreadContextMap getThreadContextMap() {
		return readOnlyContextMap;
	}

	public static boolean isEmpty() {
		return contextMap.isEmpty();
	}

	public static void clearStack() {
		contextStack.clear();
	}

	public static ThreadContext.ContextStack cloneStack() {
		return contextStack.copy();
	}

	public static ThreadContext.ContextStack getImmutableStack() {
		ThreadContext.ContextStack result = contextStack.getImmutableStackOrNull();
		return (ThreadContext.ContextStack)(result == null ? EMPTY_STACK : result);
	}

	public static void setStack(Collection<String> stack) {
		if (!stack.isEmpty() && useStack) {
			contextStack.clear();
			contextStack.addAll(stack);
		}
	}

	public static int getDepth() {
		return contextStack.getDepth();
	}

	public static String pop() {
		return contextStack.pop();
	}

	public static String peek() {
		return contextStack.peek();
	}

	public static void push(String message) {
		contextStack.push(message);
	}

	public static void push(String message, Object... args) {
		contextStack.push(ParameterizedMessage.format(message, args));
	}

	public static void removeStack() {
		contextStack.clear();
	}

	public static void trim(int depth) {
		contextStack.trim(depth);
	}

	static {
		init();
	}

	public interface ContextStack extends Serializable, Collection<String> {
		String pop();

		String peek();

		void push(String string);

		int getDepth();

		List<String> asList();

		void trim(int integer);

		ThreadContext.ContextStack copy();

		ThreadContext.ContextStack getImmutableStackOrNull();
	}

	private static class EmptyIterator<E> implements Iterator<E> {
		private EmptyIterator() {
		}

		public boolean hasNext() {
			return false;
		}

		public E next() {
			throw new NoSuchElementException("This is an empty iterator!");
		}

		public void remove() {
		}
	}

	private static class EmptyThreadContextStack extends AbstractCollection<String> implements ThreadContextStack {
		private static final long serialVersionUID = 1L;
		private static final Iterator<String> EMPTY_ITERATOR = new ThreadContext.EmptyIterator<>();

		private EmptyThreadContextStack() {
		}

		@Override
		public String pop() {
			return null;
		}

		@Override
		public String peek() {
			return null;
		}

		@Override
		public void push(String message) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getDepth() {
			return 0;
		}

		@Override
		public List<String> asList() {
			return Collections.emptyList();
		}

		@Override
		public void trim(int depth) {
		}

		public boolean equals(Object o) {
			return o instanceof Collection && ((Collection)o).isEmpty();
		}

		public int hashCode() {
			return 1;
		}

		@Override
		public ThreadContext.ContextStack copy() {
			return this;
		}

		public <T> T[] toArray(T[] a) {
			throw new UnsupportedOperationException();
		}

		public boolean add(String e) {
			throw new UnsupportedOperationException();
		}

		public boolean containsAll(Collection<?> c) {
			return false;
		}

		public boolean addAll(Collection<? extends String> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public Iterator<String> iterator() {
			return EMPTY_ITERATOR;
		}

		public int size() {
			return 0;
		}

		@Override
		public ThreadContext.ContextStack getImmutableStackOrNull() {
			return this;
		}
	}
}
