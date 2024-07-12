package io.netty.util;

public interface HashingStrategy<T> {
	HashingStrategy JAVA_HASHER = new HashingStrategy() {
		@Override
		public int hashCode(Object obj) {
			return obj != null ? obj.hashCode() : 0;
		}

		@Override
		public boolean equals(Object a, Object b) {
			return a == b || a != null && a.equals(b);
		}
	};

	int hashCode(T object);

	boolean equals(T object1, T object2);
}
