package io.netty.util;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DefaultAttributeMap implements AttributeMap {
	private static final AtomicReferenceFieldUpdater<DefaultAttributeMap, AtomicReferenceArray> updater = AtomicReferenceFieldUpdater.newUpdater(
		DefaultAttributeMap.class, AtomicReferenceArray.class, "attributes"
	);
	private static final int BUCKET_SIZE = 4;
	private static final int MASK = 3;
	private volatile AtomicReferenceArray<DefaultAttributeMap.DefaultAttribute<?>> attributes;

	@Override
	public <T> Attribute<T> attr(AttributeKey<T> key) {
		if (key == null) {
			throw new NullPointerException("key");
		} else {
			AtomicReferenceArray<DefaultAttributeMap.DefaultAttribute<?>> attributes = this.attributes;
			if (attributes == null) {
				attributes = new AtomicReferenceArray(4);
				if (!updater.compareAndSet(this, null, attributes)) {
					attributes = this.attributes;
				}
			}

			int i = index(key);
			DefaultAttributeMap.DefaultAttribute<?> head = (DefaultAttributeMap.DefaultAttribute<?>)attributes.get(i);
			if (head == null) {
				head = new DefaultAttributeMap.DefaultAttribute();
				DefaultAttributeMap.DefaultAttribute<T> attr = new DefaultAttributeMap.DefaultAttribute<>(head, key);
				head.next = attr;
				attr.prev = head;
				if (attributes.compareAndSet(i, null, head)) {
					return attr;
				}

				head = (DefaultAttributeMap.DefaultAttribute<?>)attributes.get(i);
			}

			synchronized (head) {
				DefaultAttributeMap.DefaultAttribute<?> curr = head;

				while (true) {
					DefaultAttributeMap.DefaultAttribute<?> next = curr.next;
					if (next == null) {
						DefaultAttributeMap.DefaultAttribute<T> attr = new DefaultAttributeMap.DefaultAttribute<>(head, key);
						curr.next = attr;
						attr.prev = curr;
						return attr;
					}

					if (next.key == key && !next.removed) {
						return (Attribute<T>)next;
					}

					curr = next;
				}
			}
		}
	}

	@Override
	public <T> boolean hasAttr(AttributeKey<T> key) {
		if (key == null) {
			throw new NullPointerException("key");
		} else {
			AtomicReferenceArray<DefaultAttributeMap.DefaultAttribute<?>> attributes = this.attributes;
			if (attributes == null) {
				return false;
			} else {
				int i = index(key);
				DefaultAttributeMap.DefaultAttribute<?> head = (DefaultAttributeMap.DefaultAttribute<?>)attributes.get(i);
				if (head == null) {
					return false;
				} else {
					synchronized (head) {
						for (DefaultAttributeMap.DefaultAttribute<?> curr = head.next; curr != null; curr = curr.next) {
							if (curr.key == key && !curr.removed) {
								return true;
							}
						}

						return false;
					}
				}
			}
		}
	}

	private static int index(AttributeKey<?> key) {
		return key.id() & 3;
	}

	private static final class DefaultAttribute<T> extends AtomicReference<T> implements Attribute<T> {
		private static final long serialVersionUID = -2661411462200283011L;
		private final DefaultAttributeMap.DefaultAttribute<?> head;
		private final AttributeKey<T> key;
		private DefaultAttributeMap.DefaultAttribute<?> prev;
		private DefaultAttributeMap.DefaultAttribute<?> next;
		private volatile boolean removed;

		DefaultAttribute(DefaultAttributeMap.DefaultAttribute<?> head, AttributeKey<T> key) {
			this.head = head;
			this.key = key;
		}

		DefaultAttribute() {
			this.head = this;
			this.key = null;
		}

		@Override
		public AttributeKey<T> key() {
			return this.key;
		}

		@Override
		public T setIfAbsent(T value) {
			while (!this.compareAndSet(null, value)) {
				T old = this.get();
				if (old != null) {
					return old;
				}
			}

			return null;
		}

		@Override
		public T getAndRemove() {
			this.removed = true;
			T oldValue = this.getAndSet(null);
			this.remove0();
			return oldValue;
		}

		@Override
		public void remove() {
			this.removed = true;
			this.set(null);
			this.remove0();
		}

		private void remove0() {
			synchronized (this.head) {
				if (this.prev != null) {
					this.prev.next = this.next;
					if (this.next != null) {
						this.next.prev = this.prev;
					}

					this.prev = null;
					this.next = null;
				}
			}
		}
	}
}
