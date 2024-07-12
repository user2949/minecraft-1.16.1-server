package io.netty.util.concurrent;

import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectCleaner;
import io.netty.util.internal.PlatformDependent;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public class FastThreadLocal<V> {
	private static final int variablesToRemoveIndex = InternalThreadLocalMap.nextVariableIndex();
	private final int index = InternalThreadLocalMap.nextVariableIndex();

	public static void removeAll() {
		InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.getIfSet();
		if (threadLocalMap != null) {
			try {
				Object v = threadLocalMap.indexedVariable(variablesToRemoveIndex);
				if (v != null && v != InternalThreadLocalMap.UNSET) {
					Set<FastThreadLocal<?>> variablesToRemove = (Set<FastThreadLocal<?>>)v;
					FastThreadLocal<?>[] variablesToRemoveArray = (FastThreadLocal<?>[])variablesToRemove.toArray(new FastThreadLocal[variablesToRemove.size()]);

					for (FastThreadLocal<?> tlv : variablesToRemoveArray) {
						tlv.remove(threadLocalMap);
					}
				}
			} finally {
				InternalThreadLocalMap.remove();
			}
		}
	}

	public static int size() {
		InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.getIfSet();
		return threadLocalMap == null ? 0 : threadLocalMap.size();
	}

	public static void destroy() {
		InternalThreadLocalMap.destroy();
	}

	private static void addToVariablesToRemove(InternalThreadLocalMap threadLocalMap, FastThreadLocal<?> variable) {
		Object v = threadLocalMap.indexedVariable(variablesToRemoveIndex);
		Set<FastThreadLocal<?>> variablesToRemove;
		if (v != InternalThreadLocalMap.UNSET && v != null) {
			variablesToRemove = (Set<FastThreadLocal<?>>)v;
		} else {
			variablesToRemove = Collections.newSetFromMap(new IdentityHashMap());
			threadLocalMap.setIndexedVariable(variablesToRemoveIndex, variablesToRemove);
		}

		variablesToRemove.add(variable);
	}

	private static void removeFromVariablesToRemove(InternalThreadLocalMap threadLocalMap, FastThreadLocal<?> variable) {
		Object v = threadLocalMap.indexedVariable(variablesToRemoveIndex);
		if (v != InternalThreadLocalMap.UNSET && v != null) {
			Set<FastThreadLocal<?>> variablesToRemove = (Set<FastThreadLocal<?>>)v;
			variablesToRemove.remove(variable);
		}
	}

	public final V get() {
		InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.get();
		Object v = threadLocalMap.indexedVariable(this.index);
		if (v != InternalThreadLocalMap.UNSET) {
			return (V)v;
		} else {
			V value = this.initialize(threadLocalMap);
			this.registerCleaner(threadLocalMap);
			return value;
		}
	}

	private void registerCleaner(InternalThreadLocalMap threadLocalMap) {
		Thread current = Thread.currentThread();
		if (!FastThreadLocalThread.willCleanupFastThreadLocals(current) && !threadLocalMap.isCleanerFlagSet(this.index)) {
			threadLocalMap.setCleanerFlag(this.index);
			ObjectCleaner.register(current, new Runnable() {
				public void run() {
					FastThreadLocal.this.remove(threadLocalMap);
				}
			});
		}
	}

	public final V get(InternalThreadLocalMap threadLocalMap) {
		Object v = threadLocalMap.indexedVariable(this.index);
		return (V)(v != InternalThreadLocalMap.UNSET ? v : this.initialize(threadLocalMap));
	}

	private V initialize(InternalThreadLocalMap threadLocalMap) {
		V v = null;

		try {
			v = this.initialValue();
		} catch (Exception var4) {
			PlatformDependent.throwException(var4);
		}

		threadLocalMap.setIndexedVariable(this.index, v);
		addToVariablesToRemove(threadLocalMap, this);
		return v;
	}

	public final void set(V value) {
		if (value != InternalThreadLocalMap.UNSET) {
			InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.get();
			if (this.setKnownNotUnset(threadLocalMap, value)) {
				this.registerCleaner(threadLocalMap);
			}
		} else {
			this.remove();
		}
	}

	public final void set(InternalThreadLocalMap threadLocalMap, V value) {
		if (value != InternalThreadLocalMap.UNSET) {
			this.setKnownNotUnset(threadLocalMap, value);
		} else {
			this.remove(threadLocalMap);
		}
	}

	private boolean setKnownNotUnset(InternalThreadLocalMap threadLocalMap, V value) {
		if (threadLocalMap.setIndexedVariable(this.index, value)) {
			addToVariablesToRemove(threadLocalMap, this);
			return true;
		} else {
			return false;
		}
	}

	public final boolean isSet() {
		return this.isSet(InternalThreadLocalMap.getIfSet());
	}

	public final boolean isSet(InternalThreadLocalMap threadLocalMap) {
		return threadLocalMap != null && threadLocalMap.isIndexedVariableSet(this.index);
	}

	public final void remove() {
		this.remove(InternalThreadLocalMap.getIfSet());
	}

	public final void remove(InternalThreadLocalMap threadLocalMap) {
		if (threadLocalMap != null) {
			Object v = threadLocalMap.removeIndexedVariable(this.index);
			removeFromVariablesToRemove(threadLocalMap, this);
			if (v != InternalThreadLocalMap.UNSET) {
				try {
					this.onRemoval((V)v);
				} catch (Exception var4) {
					PlatformDependent.throwException(var4);
				}
			}
		}
	}

	protected V initialValue() throws Exception {
		return null;
	}

	protected void onRemoval(V value) throws Exception {
	}
}
