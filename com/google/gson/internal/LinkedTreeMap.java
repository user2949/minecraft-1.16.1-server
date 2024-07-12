package com.google.gson.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;

public final class LinkedTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {
	private static final Comparator<Comparable> NATURAL_ORDER = new Comparator<Comparable>() {
		public int compare(Comparable a, Comparable b) {
			return a.compareTo(b);
		}
	};
	Comparator<? super K> comparator;
	LinkedTreeMap.Node<K, V> root;
	int size = 0;
	int modCount = 0;
	final LinkedTreeMap.Node<K, V> header = new LinkedTreeMap.Node<>();
	private LinkedTreeMap<K, V>.EntrySet entrySet;
	private LinkedTreeMap<K, V>.KeySet keySet;

	public LinkedTreeMap() {
		this(NATURAL_ORDER);
	}

	public LinkedTreeMap(Comparator<? super K> comparator) {
		this.comparator = comparator != null ? comparator : NATURAL_ORDER;
	}

	public int size() {
		return this.size;
	}

	public V get(Object key) {
		LinkedTreeMap.Node<K, V> node = this.findByObject(key);
		return node != null ? node.value : null;
	}

	public boolean containsKey(Object key) {
		return this.findByObject(key) != null;
	}

	public V put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("key == null");
		} else {
			LinkedTreeMap.Node<K, V> created = this.find(key, true);
			V result = created.value;
			created.value = value;
			return result;
		}
	}

	public void clear() {
		this.root = null;
		this.size = 0;
		this.modCount++;
		LinkedTreeMap.Node<K, V> header = this.header;
		header.next = header.prev = header;
	}

	public V remove(Object key) {
		LinkedTreeMap.Node<K, V> node = this.removeInternalByKey(key);
		return node != null ? node.value : null;
	}

	LinkedTreeMap.Node<K, V> find(K key, boolean create) {
		Comparator<? super K> comparator = this.comparator;
		LinkedTreeMap.Node<K, V> nearest = this.root;
		int comparison = 0;
		if (nearest != null) {
			Comparable<Object> comparableKey = comparator == NATURAL_ORDER ? (Comparable)key : null;

			while (true) {
				comparison = comparableKey != null ? comparableKey.compareTo(nearest.key) : comparator.compare(key, nearest.key);
				if (comparison == 0) {
					return nearest;
				}

				LinkedTreeMap.Node<K, V> child = comparison < 0 ? nearest.left : nearest.right;
				if (child == null) {
					break;
				}

				nearest = child;
			}
		}

		if (!create) {
			return null;
		} else {
			LinkedTreeMap.Node<K, V> header = this.header;
			LinkedTreeMap.Node<K, V> created;
			if (nearest == null) {
				if (comparator == NATURAL_ORDER && !(key instanceof Comparable)) {
					throw new ClassCastException(key.getClass().getName() + " is not Comparable");
				}

				created = new LinkedTreeMap.Node<>(nearest, key, header, header.prev);
				this.root = created;
			} else {
				created = new LinkedTreeMap.Node<>(nearest, key, header, header.prev);
				if (comparison < 0) {
					nearest.left = created;
				} else {
					nearest.right = created;
				}

				this.rebalance(nearest, true);
			}

			this.size++;
			this.modCount++;
			return created;
		}
	}

	LinkedTreeMap.Node<K, V> findByObject(Object key) {
		try {
			return key != null ? this.find((K)key, false) : null;
		} catch (ClassCastException var3) {
			return null;
		}
	}

	LinkedTreeMap.Node<K, V> findByEntry(Entry<?, ?> entry) {
		LinkedTreeMap.Node<K, V> mine = this.findByObject(entry.getKey());
		boolean valuesEqual = mine != null && this.equal(mine.value, entry.getValue());
		return valuesEqual ? mine : null;
	}

	private boolean equal(Object a, Object b) {
		return a == b || a != null && a.equals(b);
	}

	void removeInternal(LinkedTreeMap.Node<K, V> node, boolean unlink) {
		if (unlink) {
			node.prev.next = node.next;
			node.next.prev = node.prev;
		}

		LinkedTreeMap.Node<K, V> left = node.left;
		LinkedTreeMap.Node<K, V> right = node.right;
		LinkedTreeMap.Node<K, V> originalParent = node.parent;
		if (left != null && right != null) {
			LinkedTreeMap.Node<K, V> adjacent = left.height > right.height ? left.last() : right.first();
			this.removeInternal(adjacent, false);
			int leftHeight = 0;
			left = node.left;
			if (left != null) {
				leftHeight = left.height;
				adjacent.left = left;
				left.parent = adjacent;
				node.left = null;
			}

			int rightHeight = 0;
			right = node.right;
			if (right != null) {
				rightHeight = right.height;
				adjacent.right = right;
				right.parent = adjacent;
				node.right = null;
			}

			adjacent.height = Math.max(leftHeight, rightHeight) + 1;
			this.replaceInParent(node, adjacent);
		} else {
			if (left != null) {
				this.replaceInParent(node, left);
				node.left = null;
			} else if (right != null) {
				this.replaceInParent(node, right);
				node.right = null;
			} else {
				this.replaceInParent(node, null);
			}

			this.rebalance(originalParent, false);
			this.size--;
			this.modCount++;
		}
	}

	LinkedTreeMap.Node<K, V> removeInternalByKey(Object key) {
		LinkedTreeMap.Node<K, V> node = this.findByObject(key);
		if (node != null) {
			this.removeInternal(node, true);
		}

		return node;
	}

	private void replaceInParent(LinkedTreeMap.Node<K, V> node, LinkedTreeMap.Node<K, V> replacement) {
		LinkedTreeMap.Node<K, V> parent = node.parent;
		node.parent = null;
		if (replacement != null) {
			replacement.parent = parent;
		}

		if (parent != null) {
			if (parent.left == node) {
				parent.left = replacement;
			} else {
				assert parent.right == node;

				parent.right = replacement;
			}
		} else {
			this.root = replacement;
		}
	}

	private void rebalance(LinkedTreeMap.Node<K, V> unbalanced, boolean insert) {
		for (LinkedTreeMap.Node<K, V> node = unbalanced; node != null; node = node.parent) {
			LinkedTreeMap.Node<K, V> left = node.left;
			LinkedTreeMap.Node<K, V> right = node.right;
			int leftHeight = left != null ? left.height : 0;
			int rightHeight = right != null ? right.height : 0;
			int delta = leftHeight - rightHeight;
			if (delta == -2) {
				LinkedTreeMap.Node<K, V> rightLeft = right.left;
				LinkedTreeMap.Node<K, V> rightRight = right.right;
				int rightRightHeight = rightRight != null ? rightRight.height : 0;
				int rightLeftHeight = rightLeft != null ? rightLeft.height : 0;
				int rightDelta = rightLeftHeight - rightRightHeight;
				if (rightDelta != -1 && (rightDelta != 0 || insert)) {
					assert rightDelta == 1;

					this.rotateRight(right);
					this.rotateLeft(node);
				} else {
					this.rotateLeft(node);
				}

				if (insert) {
					break;
				}
			} else if (delta == 2) {
				LinkedTreeMap.Node<K, V> leftLeft = left.left;
				LinkedTreeMap.Node<K, V> leftRight = left.right;
				int leftRightHeight = leftRight != null ? leftRight.height : 0;
				int leftLeftHeight = leftLeft != null ? leftLeft.height : 0;
				int leftDelta = leftLeftHeight - leftRightHeight;
				if (leftDelta != 1 && (leftDelta != 0 || insert)) {
					assert leftDelta == -1;

					this.rotateLeft(left);
					this.rotateRight(node);
				} else {
					this.rotateRight(node);
				}

				if (insert) {
					break;
				}
			} else if (delta == 0) {
				node.height = leftHeight + 1;
				if (insert) {
					break;
				}
			} else {
				assert delta == -1 || delta == 1;

				node.height = Math.max(leftHeight, rightHeight) + 1;
				if (!insert) {
					break;
				}
			}
		}
	}

	private void rotateLeft(LinkedTreeMap.Node<K, V> root) {
		LinkedTreeMap.Node<K, V> left = root.left;
		LinkedTreeMap.Node<K, V> pivot = root.right;
		LinkedTreeMap.Node<K, V> pivotLeft = pivot.left;
		LinkedTreeMap.Node<K, V> pivotRight = pivot.right;
		root.right = pivotLeft;
		if (pivotLeft != null) {
			pivotLeft.parent = root;
		}

		this.replaceInParent(root, pivot);
		pivot.left = root;
		root.parent = pivot;
		root.height = Math.max(left != null ? left.height : 0, pivotLeft != null ? pivotLeft.height : 0) + 1;
		pivot.height = Math.max(root.height, pivotRight != null ? pivotRight.height : 0) + 1;
	}

	private void rotateRight(LinkedTreeMap.Node<K, V> root) {
		LinkedTreeMap.Node<K, V> pivot = root.left;
		LinkedTreeMap.Node<K, V> right = root.right;
		LinkedTreeMap.Node<K, V> pivotLeft = pivot.left;
		LinkedTreeMap.Node<K, V> pivotRight = pivot.right;
		root.left = pivotRight;
		if (pivotRight != null) {
			pivotRight.parent = root;
		}

		this.replaceInParent(root, pivot);
		pivot.right = root;
		root.parent = pivot;
		root.height = Math.max(right != null ? right.height : 0, pivotRight != null ? pivotRight.height : 0) + 1;
		pivot.height = Math.max(root.height, pivotLeft != null ? pivotLeft.height : 0) + 1;
	}

	public Set<Entry<K, V>> entrySet() {
		LinkedTreeMap<K, V>.EntrySet result = this.entrySet;
		return result != null ? result : (this.entrySet = new LinkedTreeMap.EntrySet());
	}

	public Set<K> keySet() {
		LinkedTreeMap<K, V>.KeySet result = this.keySet;
		return result != null ? result : (this.keySet = new LinkedTreeMap.KeySet());
	}

	private Object writeReplace() throws ObjectStreamException {
		return new LinkedHashMap(this);
	}

	class EntrySet extends AbstractSet<Entry<K, V>> {
		public int size() {
			return LinkedTreeMap.this.size;
		}

		public Iterator<Entry<K, V>> iterator() {
			return new LinkedTreeMap<K, V>.LinkedTreeMapIterator<Entry<K, V>>() {
				public Entry<K, V> next() {
					return this.nextNode();
				}
			};
		}

		public boolean contains(Object o) {
			return o instanceof Entry && LinkedTreeMap.this.findByEntry((Entry<?, ?>)o) != null;
		}

		public boolean remove(Object o) {
			if (!(o instanceof Entry)) {
				return false;
			} else {
				LinkedTreeMap.Node<K, V> node = LinkedTreeMap.this.findByEntry((Entry<?, ?>)o);
				if (node == null) {
					return false;
				} else {
					LinkedTreeMap.this.removeInternal(node, true);
					return true;
				}
			}
		}

		public void clear() {
			LinkedTreeMap.this.clear();
		}
	}

	final class KeySet extends AbstractSet<K> {
		public int size() {
			return LinkedTreeMap.this.size;
		}

		public Iterator<K> iterator() {
			return new LinkedTreeMap<K, V>.LinkedTreeMapIterator<K>() {
				public K next() {
					return this.nextNode().key;
				}
			};
		}

		public boolean contains(Object o) {
			return LinkedTreeMap.this.containsKey(o);
		}

		public boolean remove(Object key) {
			return LinkedTreeMap.this.removeInternalByKey(key) != null;
		}

		public void clear() {
			LinkedTreeMap.this.clear();
		}
	}

	private abstract class LinkedTreeMapIterator<T> implements Iterator<T> {
		LinkedTreeMap.Node<K, V> next;
		LinkedTreeMap.Node<K, V> lastReturned;
		int expectedModCount;

		LinkedTreeMapIterator() {
			this.next = LinkedTreeMap.this.header.next;
			this.lastReturned = null;
			this.expectedModCount = LinkedTreeMap.this.modCount;
		}

		public final boolean hasNext() {
			return this.next != LinkedTreeMap.this.header;
		}

		final LinkedTreeMap.Node<K, V> nextNode() {
			LinkedTreeMap.Node<K, V> e = this.next;
			if (e == LinkedTreeMap.this.header) {
				throw new NoSuchElementException();
			} else if (LinkedTreeMap.this.modCount != this.expectedModCount) {
				throw new ConcurrentModificationException();
			} else {
				this.next = e.next;
				return this.lastReturned = e;
			}
		}

		public final void remove() {
			if (this.lastReturned == null) {
				throw new IllegalStateException();
			} else {
				LinkedTreeMap.this.removeInternal(this.lastReturned, true);
				this.lastReturned = null;
				this.expectedModCount = LinkedTreeMap.this.modCount;
			}
		}
	}

	static final class Node<K, V> implements Entry<K, V> {
		LinkedTreeMap.Node<K, V> parent;
		LinkedTreeMap.Node<K, V> left;
		LinkedTreeMap.Node<K, V> right;
		LinkedTreeMap.Node<K, V> next;
		LinkedTreeMap.Node<K, V> prev;
		final K key;
		V value;
		int height;

		Node() {
			this.key = null;
			this.next = this.prev = this;
		}

		Node(LinkedTreeMap.Node<K, V> parent, K key, LinkedTreeMap.Node<K, V> next, LinkedTreeMap.Node<K, V> prev) {
			this.parent = parent;
			this.key = key;
			this.height = 1;
			this.next = next;
			this.prev = prev;
			prev.next = this;
			next.prev = this;
		}

		public K getKey() {
			return this.key;
		}

		public V getValue() {
			return this.value;
		}

		public V setValue(V value) {
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public boolean equals(Object o) {
			if (!(o instanceof Entry)) {
				return false;
			} else {
				Entry other = (Entry)o;
				return (this.key == null ? other.getKey() == null : this.key.equals(other.getKey()))
					&& (this.value == null ? other.getValue() == null : this.value.equals(other.getValue()));
			}
		}

		public int hashCode() {
			return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
		}

		public String toString() {
			return this.key + "=" + this.value;
		}

		public LinkedTreeMap.Node<K, V> first() {
			LinkedTreeMap.Node<K, V> node = this;

			for (LinkedTreeMap.Node<K, V> child = this.left; child != null; child = child.left) {
				node = child;
			}

			return node;
		}

		public LinkedTreeMap.Node<K, V> last() {
			LinkedTreeMap.Node<K, V> node = this;

			for (LinkedTreeMap.Node<K, V> child = this.right; child != null; child = child.right) {
				node = child;
			}

			return node;
		}
	}
}
