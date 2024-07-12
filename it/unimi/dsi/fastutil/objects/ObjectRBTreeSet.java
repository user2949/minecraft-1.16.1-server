package it.unimi.dsi.fastutil.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.SortedSet;

public class ObjectRBTreeSet<K> extends AbstractObjectSortedSet<K> implements Serializable, Cloneable, ObjectSortedSet<K> {
	protected transient ObjectRBTreeSet.Entry<K> tree;
	protected int count;
	protected transient ObjectRBTreeSet.Entry<K> firstEntry;
	protected transient ObjectRBTreeSet.Entry<K> lastEntry;
	protected Comparator<? super K> storedComparator;
	protected transient Comparator<? super K> actualComparator;
	private static final long serialVersionUID = -7046029254386353130L;
	private transient boolean[] dirPath;
	private transient ObjectRBTreeSet.Entry<K>[] nodePath;

	public ObjectRBTreeSet() {
		this.allocatePaths();
		this.tree = null;
		this.count = 0;
	}

	private void setActualComparator() {
		this.actualComparator = this.storedComparator;
	}

	public ObjectRBTreeSet(Comparator<? super K> c) {
		this();
		this.storedComparator = c;
		this.setActualComparator();
	}

	public ObjectRBTreeSet(Collection<? extends K> c) {
		this();
		this.addAll(c);
	}

	public ObjectRBTreeSet(SortedSet<K> s) {
		this(s.comparator());
		this.addAll(s);
	}

	public ObjectRBTreeSet(ObjectCollection<? extends K> c) {
		this();
		this.addAll(c);
	}

	public ObjectRBTreeSet(ObjectSortedSet<K> s) {
		this(s.comparator());
		this.addAll(s);
	}

	public ObjectRBTreeSet(Iterator<? extends K> i) {
		this.allocatePaths();

		while (i.hasNext()) {
			this.add((K)i.next());
		}
	}

	public ObjectRBTreeSet(K[] a, int offset, int length, Comparator<? super K> c) {
		this(c);
		ObjectArrays.ensureOffsetLength(a, offset, length);

		for (int i = 0; i < length; i++) {
			this.add(a[offset + i]);
		}
	}

	public ObjectRBTreeSet(K[] a, int offset, int length) {
		this(a, offset, length, null);
	}

	public ObjectRBTreeSet(K[] a) {
		this();
		int i = a.length;

		while (i-- != 0) {
			this.add(a[i]);
		}
	}

	public ObjectRBTreeSet(K[] a, Comparator<? super K> c) {
		this(c);
		int i = a.length;

		while (i-- != 0) {
			this.add(a[i]);
		}
	}

	final int compare(K k1, K k2) {
		return this.actualComparator == null ? ((Comparable)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
	}

	private ObjectRBTreeSet.Entry<K> findKey(K k) {
		ObjectRBTreeSet.Entry<K> e = this.tree;

		int cmp;
		while (e != null && (cmp = this.compare(k, e.key)) != 0) {
			e = cmp < 0 ? e.left() : e.right();
		}

		return e;
	}

	final ObjectRBTreeSet.Entry<K> locateKey(K k) {
		ObjectRBTreeSet.Entry<K> e = this.tree;
		ObjectRBTreeSet.Entry<K> last = this.tree;

		int cmp;
		for (cmp = 0; e != null && (cmp = this.compare(k, e.key)) != 0; e = cmp < 0 ? e.left() : e.right()) {
			last = e;
		}

		return cmp == 0 ? e : last;
	}

	private void allocatePaths() {
		this.dirPath = new boolean[64];
		this.nodePath = new ObjectRBTreeSet.Entry[64];
	}

	public boolean add(K k) {
		int maxDepth = 0;
		if (this.tree == null) {
			this.count++;
			this.tree = this.lastEntry = this.firstEntry = new ObjectRBTreeSet.Entry<>(k);
		} else {
			ObjectRBTreeSet.Entry<K> p = this.tree;
			int i = 0;

			label123:
			while (true) {
				int cmp;
				if ((cmp = this.compare(k, p.key)) == 0) {
					while (i-- != 0) {
						this.nodePath[i] = null;
					}

					return false;
				}

				this.nodePath[i] = p;
				if (this.dirPath[i++] = cmp > 0) {
					if (!p.succ()) {
						p = p.right;
						continue;
					}

					this.count++;
					ObjectRBTreeSet.Entry<K> e = new ObjectRBTreeSet.Entry<>(k);
					if (p.right == null) {
						this.lastEntry = e;
					}

					e.left = p;
					e.right = p.right;
					p.right(e);
				} else {
					if (!p.pred()) {
						p = p.left;
						continue;
					}

					this.count++;
					ObjectRBTreeSet.Entry<K> e = new ObjectRBTreeSet.Entry<>(k);
					if (p.left == null) {
						this.firstEntry = e;
					}

					e.right = p;
					e.left = p.left;
					p.left(e);
				}

				maxDepth = i--;

				while (true) {
					if (i <= 0 || this.nodePath[i].black()) {
						break label123;
					}

					if (!this.dirPath[i - 1]) {
						ObjectRBTreeSet.Entry<K> y = this.nodePath[i - 1].right;
						if (this.nodePath[i - 1].succ() || y.black()) {
							if (!this.dirPath[i]) {
								y = this.nodePath[i];
							} else {
								ObjectRBTreeSet.Entry<K> x = this.nodePath[i];
								y = x.right;
								x.right = y.left;
								y.left = x;
								this.nodePath[i - 1].left = y;
								if (y.pred()) {
									y.pred(false);
									x.succ(y);
								}
							}

							ObjectRBTreeSet.Entry<K> xx = this.nodePath[i - 1];
							xx.black(false);
							y.black(true);
							xx.left = y.right;
							y.right = xx;
							if (i < 2) {
								this.tree = y;
							} else if (this.dirPath[i - 2]) {
								this.nodePath[i - 2].right = y;
							} else {
								this.nodePath[i - 2].left = y;
							}

							if (y.succ()) {
								y.succ(false);
								xx.pred(y);
							}
							break label123;
						}

						this.nodePath[i].black(true);
						y.black(true);
						this.nodePath[i - 1].black(false);
						i -= 2;
					} else {
						ObjectRBTreeSet.Entry<K> y = this.nodePath[i - 1].left;
						if (this.nodePath[i - 1].pred() || y.black()) {
							if (this.dirPath[i]) {
								y = this.nodePath[i];
							} else {
								ObjectRBTreeSet.Entry<K> x = this.nodePath[i];
								y = x.left;
								x.left = y.right;
								y.right = x;
								this.nodePath[i - 1].right = y;
								if (y.succ()) {
									y.succ(false);
									x.pred(y);
								}
							}

							ObjectRBTreeSet.Entry<K> xxx = this.nodePath[i - 1];
							xxx.black(false);
							y.black(true);
							xxx.right = y.left;
							y.left = xxx;
							if (i < 2) {
								this.tree = y;
							} else if (this.dirPath[i - 2]) {
								this.nodePath[i - 2].right = y;
							} else {
								this.nodePath[i - 2].left = y;
							}

							if (y.pred()) {
								y.pred(false);
								xxx.succ(y);
							}
							break label123;
						}

						this.nodePath[i].black(true);
						y.black(true);
						this.nodePath[i - 1].black(false);
						i -= 2;
					}
				}
			}
		}

		this.tree.black(true);

		while (maxDepth-- != 0) {
			this.nodePath[maxDepth] = null;
		}

		return true;
	}

	public boolean remove(Object k) {
		if (this.tree == null) {
			return false;
		} else {
			ObjectRBTreeSet.Entry<K> p = this.tree;
			int i = 0;
			K kk = (K)k;

			int cmp;
			while ((cmp = this.compare(kk, p.key)) != 0) {
				this.dirPath[i] = cmp > 0;
				this.nodePath[i] = p;
				if (this.dirPath[i++]) {
					if ((p = p.right()) == null) {
						while (i-- != 0) {
							this.nodePath[i] = null;
						}

						return false;
					}
				} else if ((p = p.left()) == null) {
					while (i-- != 0) {
						this.nodePath[i] = null;
					}

					return false;
				}
			}

			if (p.left == null) {
				this.firstEntry = p.next();
			}

			if (p.right == null) {
				this.lastEntry = p.prev();
			}

			if (p.succ()) {
				if (p.pred()) {
					if (i == 0) {
						this.tree = p.left;
					} else if (this.dirPath[i - 1]) {
						this.nodePath[i - 1].succ(p.right);
					} else {
						this.nodePath[i - 1].pred(p.left);
					}
				} else {
					p.prev().right = p.right;
					if (i == 0) {
						this.tree = p.left;
					} else if (this.dirPath[i - 1]) {
						this.nodePath[i - 1].right = p.left;
					} else {
						this.nodePath[i - 1].left = p.left;
					}
				}
			} else {
				ObjectRBTreeSet.Entry<K> r = p.right;
				if (r.pred()) {
					r.left = p.left;
					r.pred(p.pred());
					if (!r.pred()) {
						r.prev().right = r;
					}

					if (i == 0) {
						this.tree = r;
					} else if (this.dirPath[i - 1]) {
						this.nodePath[i - 1].right = r;
					} else {
						this.nodePath[i - 1].left = r;
					}

					boolean color = r.black();
					r.black(p.black());
					p.black(color);
					this.dirPath[i] = true;
					this.nodePath[i++] = r;
				} else {
					int j = i++;

					while (true) {
						this.dirPath[i] = false;
						this.nodePath[i++] = r;
						ObjectRBTreeSet.Entry<K> s = r.left;
						if (s.pred()) {
							this.dirPath[j] = true;
							this.nodePath[j] = s;
							if (s.succ()) {
								r.pred(s);
							} else {
								r.left = s.right;
							}

							s.left = p.left;
							if (!p.pred()) {
								p.prev().right = s;
								s.pred(false);
							}

							s.right(p.right);
							boolean color = s.black();
							s.black(p.black());
							p.black(color);
							if (j == 0) {
								this.tree = s;
							} else if (this.dirPath[j - 1]) {
								this.nodePath[j - 1].right = s;
							} else {
								this.nodePath[j - 1].left = s;
							}
							break;
						}

						r = s;
					}
				}
			}

			int maxDepth = i;
			if (p.black()) {
				for (; i > 0; i--) {
					if (this.dirPath[i - 1] && !this.nodePath[i - 1].succ() || !this.dirPath[i - 1] && !this.nodePath[i - 1].pred()) {
						ObjectRBTreeSet.Entry<K> x = this.dirPath[i - 1] ? this.nodePath[i - 1].right : this.nodePath[i - 1].left;
						if (!x.black()) {
							x.black(true);
							break;
						}
					}

					if (!this.dirPath[i - 1]) {
						ObjectRBTreeSet.Entry<K> w = this.nodePath[i - 1].right;
						if (!w.black()) {
							w.black(true);
							this.nodePath[i - 1].black(false);
							this.nodePath[i - 1].right = w.left;
							w.left = this.nodePath[i - 1];
							if (i < 2) {
								this.tree = w;
							} else if (this.dirPath[i - 2]) {
								this.nodePath[i - 2].right = w;
							} else {
								this.nodePath[i - 2].left = w;
							}

							this.nodePath[i] = this.nodePath[i - 1];
							this.dirPath[i] = false;
							this.nodePath[i - 1] = w;
							if (maxDepth == i++) {
								maxDepth++;
							}

							w = this.nodePath[i - 1].right;
						}

						if (!w.pred() && !w.left.black() || !w.succ() && !w.right.black()) {
							if (w.succ() || w.right.black()) {
								ObjectRBTreeSet.Entry<K> y = w.left;
								y.black(true);
								w.black(false);
								w.left = y.right;
								y.right = w;
								w = this.nodePath[i - 1].right = y;
								if (w.succ()) {
									w.succ(false);
									w.right.pred(w);
								}
							}

							w.black(this.nodePath[i - 1].black());
							this.nodePath[i - 1].black(true);
							w.right.black(true);
							this.nodePath[i - 1].right = w.left;
							w.left = this.nodePath[i - 1];
							if (i < 2) {
								this.tree = w;
							} else if (this.dirPath[i - 2]) {
								this.nodePath[i - 2].right = w;
							} else {
								this.nodePath[i - 2].left = w;
							}

							if (w.pred()) {
								w.pred(false);
								this.nodePath[i - 1].succ(w);
							}
							break;
						}

						w.black(false);
					} else {
						ObjectRBTreeSet.Entry<K> wx = this.nodePath[i - 1].left;
						if (!wx.black()) {
							wx.black(true);
							this.nodePath[i - 1].black(false);
							this.nodePath[i - 1].left = wx.right;
							wx.right = this.nodePath[i - 1];
							if (i < 2) {
								this.tree = wx;
							} else if (this.dirPath[i - 2]) {
								this.nodePath[i - 2].right = wx;
							} else {
								this.nodePath[i - 2].left = wx;
							}

							this.nodePath[i] = this.nodePath[i - 1];
							this.dirPath[i] = true;
							this.nodePath[i - 1] = wx;
							if (maxDepth == i++) {
								maxDepth++;
							}

							wx = this.nodePath[i - 1].left;
						}

						if (!wx.pred() && !wx.left.black() || !wx.succ() && !wx.right.black()) {
							if (wx.pred() || wx.left.black()) {
								ObjectRBTreeSet.Entry<K> y = wx.right;
								y.black(true);
								wx.black(false);
								wx.right = y.left;
								y.left = wx;
								wx = this.nodePath[i - 1].left = y;
								if (wx.pred()) {
									wx.pred(false);
									wx.left.succ(wx);
								}
							}

							wx.black(this.nodePath[i - 1].black());
							this.nodePath[i - 1].black(true);
							wx.left.black(true);
							this.nodePath[i - 1].left = wx.right;
							wx.right = this.nodePath[i - 1];
							if (i < 2) {
								this.tree = wx;
							} else if (this.dirPath[i - 2]) {
								this.nodePath[i - 2].right = wx;
							} else {
								this.nodePath[i - 2].left = wx;
							}

							if (wx.succ()) {
								wx.succ(false);
								this.nodePath[i - 1].pred(wx);
							}
							break;
						}

						wx.black(false);
					}
				}

				if (this.tree != null) {
					this.tree.black(true);
				}
			}

			this.count--;

			while (maxDepth-- != 0) {
				this.nodePath[maxDepth] = null;
			}

			return true;
		}
	}

	public boolean contains(Object k) {
		return this.findKey((K)k) != null;
	}

	public K get(Object k) {
		ObjectRBTreeSet.Entry<K> entry = this.findKey((K)k);
		return entry == null ? null : entry.key;
	}

	public void clear() {
		this.count = 0;
		this.tree = null;
		this.firstEntry = this.lastEntry = null;
	}

	public int size() {
		return this.count;
	}

	public boolean isEmpty() {
		return this.count == 0;
	}

	public K first() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.firstEntry.key;
		}
	}

	public K last() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.lastEntry.key;
		}
	}

	@Override
	public ObjectBidirectionalIterator<K> iterator() {
		return new ObjectRBTreeSet.SetIterator();
	}

	@Override
	public ObjectBidirectionalIterator<K> iterator(K from) {
		return new ObjectRBTreeSet.SetIterator(from);
	}

	public Comparator<? super K> comparator() {
		return this.actualComparator;
	}

	@Override
	public ObjectSortedSet<K> headSet(K to) {
		return new ObjectRBTreeSet.Subset(null, true, to, false);
	}

	@Override
	public ObjectSortedSet<K> tailSet(K from) {
		return new ObjectRBTreeSet.Subset(from, false, null, true);
	}

	@Override
	public ObjectSortedSet<K> subSet(K from, K to) {
		return new ObjectRBTreeSet.Subset(from, false, to, false);
	}

	public Object clone() {
		ObjectRBTreeSet<K> c;
		try {
			c = (ObjectRBTreeSet<K>)super.clone();
		} catch (CloneNotSupportedException var7) {
			throw new InternalError();
		}

		c.allocatePaths();
		if (this.count == 0) {
			return c;
		} else {
			ObjectRBTreeSet.Entry<K> rp = new ObjectRBTreeSet.Entry<>();
			ObjectRBTreeSet.Entry<K> rq = new ObjectRBTreeSet.Entry<>();
			ObjectRBTreeSet.Entry<K> p = rp;
			rp.left(this.tree);
			ObjectRBTreeSet.Entry<K> q = rq;
			rq.pred(null);

			while (true) {
				if (!p.pred()) {
					ObjectRBTreeSet.Entry<K> e = p.left.clone();
					e.pred(q.left);
					e.succ(q);
					q.left(e);
					p = p.left;
					q = q.left;
				} else {
					while (p.succ()) {
						p = p.right;
						if (p == null) {
							q.right = null;
							c.tree = rq.left;
							c.firstEntry = c.tree;

							while (c.firstEntry.left != null) {
								c.firstEntry = c.firstEntry.left;
							}

							c.lastEntry = c.tree;

							while (c.lastEntry.right != null) {
								c.lastEntry = c.lastEntry.right;
							}

							return c;
						}

						q = q.right;
					}

					p = p.right;
					q = q.right;
				}

				if (!p.succ()) {
					ObjectRBTreeSet.Entry<K> e = p.right.clone();
					e.succ(q.right);
					e.pred(q);
					q.right(e);
				}
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		int n = this.count;
		ObjectRBTreeSet<K>.SetIterator i = new ObjectRBTreeSet.SetIterator();
		s.defaultWriteObject();

		while (n-- != 0) {
			s.writeObject(i.next());
		}
	}

	private ObjectRBTreeSet.Entry<K> readTree(ObjectInputStream s, int n, ObjectRBTreeSet.Entry<K> pred, ObjectRBTreeSet.Entry<K> succ) throws IOException, ClassNotFoundException {
		if (n == 1) {
			ObjectRBTreeSet.Entry<K> top = new ObjectRBTreeSet.Entry<>((K)s.readObject());
			top.pred(pred);
			top.succ(succ);
			top.black(true);
			return top;
		} else if (n == 2) {
			ObjectRBTreeSet.Entry<K> top = new ObjectRBTreeSet.Entry<>((K)s.readObject());
			top.black(true);
			top.right(new ObjectRBTreeSet.Entry<>((K)s.readObject()));
			top.right.pred(top);
			top.pred(pred);
			top.right.succ(succ);
			return top;
		} else {
			int rightN = n / 2;
			int leftN = n - rightN - 1;
			ObjectRBTreeSet.Entry<K> top = new ObjectRBTreeSet.Entry<>();
			top.left(this.readTree(s, leftN, pred, top));
			top.key = (K)s.readObject();
			top.black(true);
			top.right(this.readTree(s, rightN, top, succ));
			if (n + 2 == (n + 2 & -(n + 2))) {
				top.right.black(false);
			}

			return top;
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.setActualComparator();
		this.allocatePaths();
		if (this.count != 0) {
			this.tree = this.readTree(s, this.count, null, null);
			ObjectRBTreeSet.Entry<K> e = this.tree;

			while (e.left() != null) {
				e = e.left();
			}

			this.firstEntry = e;
			e = this.tree;

			while (e.right() != null) {
				e = e.right();
			}

			this.lastEntry = e;
		}
	}

	private static final class Entry<K> implements Cloneable {
		private static final int BLACK_MASK = 1;
		private static final int SUCC_MASK = Integer.MIN_VALUE;
		private static final int PRED_MASK = 1073741824;
		K key;
		ObjectRBTreeSet.Entry<K> left;
		ObjectRBTreeSet.Entry<K> right;
		int info;

		Entry() {
		}

		Entry(K k) {
			this.key = k;
			this.info = -1073741824;
		}

		ObjectRBTreeSet.Entry<K> left() {
			return (this.info & 1073741824) != 0 ? null : this.left;
		}

		ObjectRBTreeSet.Entry<K> right() {
			return (this.info & -2147483648) != 0 ? null : this.right;
		}

		boolean pred() {
			return (this.info & 1073741824) != 0;
		}

		boolean succ() {
			return (this.info & -2147483648) != 0;
		}

		void pred(boolean pred) {
			if (pred) {
				this.info |= 1073741824;
			} else {
				this.info &= -1073741825;
			}
		}

		void succ(boolean succ) {
			if (succ) {
				this.info |= Integer.MIN_VALUE;
			} else {
				this.info &= Integer.MAX_VALUE;
			}
		}

		void pred(ObjectRBTreeSet.Entry<K> pred) {
			this.info |= 1073741824;
			this.left = pred;
		}

		void succ(ObjectRBTreeSet.Entry<K> succ) {
			this.info |= Integer.MIN_VALUE;
			this.right = succ;
		}

		void left(ObjectRBTreeSet.Entry<K> left) {
			this.info &= -1073741825;
			this.left = left;
		}

		void right(ObjectRBTreeSet.Entry<K> right) {
			this.info &= Integer.MAX_VALUE;
			this.right = right;
		}

		boolean black() {
			return (this.info & 1) != 0;
		}

		void black(boolean black) {
			if (black) {
				this.info |= 1;
			} else {
				this.info &= -2;
			}
		}

		ObjectRBTreeSet.Entry<K> next() {
			ObjectRBTreeSet.Entry<K> next = this.right;
			if ((this.info & -2147483648) == 0) {
				while ((next.info & 1073741824) == 0) {
					next = next.left;
				}
			}

			return next;
		}

		ObjectRBTreeSet.Entry<K> prev() {
			ObjectRBTreeSet.Entry<K> prev = this.left;
			if ((this.info & 1073741824) == 0) {
				while ((prev.info & -2147483648) == 0) {
					prev = prev.right;
				}
			}

			return prev;
		}

		public ObjectRBTreeSet.Entry<K> clone() {
			ObjectRBTreeSet.Entry<K> c;
			try {
				c = (ObjectRBTreeSet.Entry<K>)super.clone();
			} catch (CloneNotSupportedException var3) {
				throw new InternalError();
			}

			c.key = this.key;
			c.info = this.info;
			return c;
		}

		public boolean equals(Object o) {
			if (!(o instanceof ObjectRBTreeSet.Entry)) {
				return false;
			} else {
				ObjectRBTreeSet.Entry<?> e = (ObjectRBTreeSet.Entry<?>)o;
				return Objects.equals(this.key, e.key);
			}
		}

		public int hashCode() {
			return this.key.hashCode();
		}

		public String toString() {
			return String.valueOf(this.key);
		}
	}

	private class SetIterator implements ObjectListIterator<K> {
		ObjectRBTreeSet.Entry<K> prev;
		ObjectRBTreeSet.Entry<K> next;
		ObjectRBTreeSet.Entry<K> curr;
		int index = 0;

		SetIterator() {
			this.next = ObjectRBTreeSet.this.firstEntry;
		}

		SetIterator(K k) {
			if ((this.next = ObjectRBTreeSet.this.locateKey(k)) != null) {
				if (ObjectRBTreeSet.this.compare(this.next.key, k) <= 0) {
					this.prev = this.next;
					this.next = this.next.next();
				} else {
					this.prev = this.next.prev();
				}
			}
		}

		public boolean hasNext() {
			return this.next != null;
		}

		@Override
		public boolean hasPrevious() {
			return this.prev != null;
		}

		void updateNext() {
			this.next = this.next.next();
		}

		void updatePrevious() {
			this.prev = this.prev.prev();
		}

		public K next() {
			return this.nextEntry().key;
		}

		@Override
		public K previous() {
			return this.previousEntry().key;
		}

		ObjectRBTreeSet.Entry<K> nextEntry() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.curr = this.prev = this.next;
				this.index++;
				this.updateNext();
				return this.curr;
			}
		}

		ObjectRBTreeSet.Entry<K> previousEntry() {
			if (!this.hasPrevious()) {
				throw new NoSuchElementException();
			} else {
				this.curr = this.next = this.prev;
				this.index--;
				this.updatePrevious();
				return this.curr;
			}
		}

		public int nextIndex() {
			return this.index;
		}

		public int previousIndex() {
			return this.index - 1;
		}

		@Override
		public void remove() {
			if (this.curr == null) {
				throw new IllegalStateException();
			} else {
				if (this.curr == this.prev) {
					this.index--;
				}

				this.next = this.prev = this.curr;
				this.updatePrevious();
				this.updateNext();
				ObjectRBTreeSet.this.remove(this.curr.key);
				this.curr = null;
			}
		}
	}

	private final class Subset extends AbstractObjectSortedSet<K> implements Serializable, ObjectSortedSet<K> {
		private static final long serialVersionUID = -7046029254386353129L;
		K from;
		K to;
		boolean bottom;
		boolean top;

		public Subset(K from, boolean bottom, K to, boolean top) {
			if (!bottom && !top && ObjectRBTreeSet.this.compare(from, to) > 0) {
				throw new IllegalArgumentException("Start element (" + from + ") is larger than end element (" + to + ")");
			} else {
				this.from = from;
				this.bottom = bottom;
				this.to = to;
				this.top = top;
			}
		}

		public void clear() {
			ObjectRBTreeSet<K>.Subset.SubsetIterator i = new ObjectRBTreeSet.Subset.SubsetIterator();

			while (i.hasNext()) {
				i.next();
				i.remove();
			}
		}

		final boolean in(K k) {
			return (this.bottom || ObjectRBTreeSet.this.compare(k, this.from) >= 0) && (this.top || ObjectRBTreeSet.this.compare(k, this.to) < 0);
		}

		public boolean contains(Object k) {
			return this.in((K)k) && ObjectRBTreeSet.this.contains(k);
		}

		public boolean add(K k) {
			if (!this.in(k)) {
				throw new IllegalArgumentException(
					"Element (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"
				);
			} else {
				return ObjectRBTreeSet.this.add(k);
			}
		}

		public boolean remove(Object k) {
			return !this.in((K)k) ? false : ObjectRBTreeSet.this.remove(k);
		}

		public int size() {
			ObjectRBTreeSet<K>.Subset.SubsetIterator i = new ObjectRBTreeSet.Subset.SubsetIterator();
			int n = 0;

			while (i.hasNext()) {
				n++;
				i.next();
			}

			return n;
		}

		public boolean isEmpty() {
			return !new ObjectRBTreeSet.Subset.SubsetIterator().hasNext();
		}

		public Comparator<? super K> comparator() {
			return ObjectRBTreeSet.this.actualComparator;
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return new ObjectRBTreeSet.Subset.SubsetIterator();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return new ObjectRBTreeSet.Subset.SubsetIterator(from);
		}

		@Override
		public ObjectSortedSet<K> headSet(K to) {
			if (this.top) {
				return ObjectRBTreeSet.this.new Subset(this.from, this.bottom, to, false);
			} else {
				return ObjectRBTreeSet.this.compare(to, this.to) < 0 ? ObjectRBTreeSet.this.new Subset(this.from, this.bottom, to, false) : this;
			}
		}

		@Override
		public ObjectSortedSet<K> tailSet(K from) {
			if (this.bottom) {
				return ObjectRBTreeSet.this.new Subset(from, false, this.to, this.top);
			} else {
				return ObjectRBTreeSet.this.compare(from, this.from) > 0 ? ObjectRBTreeSet.this.new Subset(from, false, this.to, this.top) : this;
			}
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			if (this.top && this.bottom) {
				return ObjectRBTreeSet.this.new Subset(from, false, to, false);
			} else {
				if (!this.top) {
					to = ObjectRBTreeSet.this.compare(to, this.to) < 0 ? to : this.to;
				}

				if (!this.bottom) {
					from = ObjectRBTreeSet.this.compare(from, this.from) > 0 ? from : this.from;
				}

				return !this.top && !this.bottom && from == this.from && to == this.to ? this : ObjectRBTreeSet.this.new Subset(from, false, to, false);
			}
		}

		public ObjectRBTreeSet.Entry<K> firstEntry() {
			if (ObjectRBTreeSet.this.tree == null) {
				return null;
			} else {
				ObjectRBTreeSet.Entry<K> e;
				if (this.bottom) {
					e = ObjectRBTreeSet.this.firstEntry;
				} else {
					e = ObjectRBTreeSet.this.locateKey(this.from);
					if (ObjectRBTreeSet.this.compare(e.key, this.from) < 0) {
						e = e.next();
					}
				}

				return e != null && (this.top || ObjectRBTreeSet.this.compare(e.key, this.to) < 0) ? e : null;
			}
		}

		public ObjectRBTreeSet.Entry<K> lastEntry() {
			if (ObjectRBTreeSet.this.tree == null) {
				return null;
			} else {
				ObjectRBTreeSet.Entry<K> e;
				if (this.top) {
					e = ObjectRBTreeSet.this.lastEntry;
				} else {
					e = ObjectRBTreeSet.this.locateKey(this.to);
					if (ObjectRBTreeSet.this.compare(e.key, this.to) >= 0) {
						e = e.prev();
					}
				}

				return e != null && (this.bottom || ObjectRBTreeSet.this.compare(e.key, this.from) >= 0) ? e : null;
			}
		}

		public K first() {
			ObjectRBTreeSet.Entry<K> e = this.firstEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		public K last() {
			ObjectRBTreeSet.Entry<K> e = this.lastEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		private final class SubsetIterator extends ObjectRBTreeSet<K>.SetIterator {
			SubsetIterator() {
				this.next = Subset.this.firstEntry();
			}

			SubsetIterator(K k) {
				this();
				if (this.next != null) {
					if (!Subset.this.bottom && ObjectRBTreeSet.this.compare(k, this.next.key) < 0) {
						this.prev = null;
					} else if (!Subset.this.top && ObjectRBTreeSet.this.compare(k, (this.prev = Subset.this.lastEntry()).key) >= 0) {
						this.next = null;
					} else {
						this.next = ObjectRBTreeSet.this.locateKey(k);
						if (ObjectRBTreeSet.this.compare(this.next.key, k) <= 0) {
							this.prev = this.next;
							this.next = this.next.next();
						} else {
							this.prev = this.next.prev();
						}
					}
				}
			}

			@Override
			void updatePrevious() {
				this.prev = this.prev.prev();
				if (!Subset.this.bottom && this.prev != null && ObjectRBTreeSet.this.compare(this.prev.key, Subset.this.from) < 0) {
					this.prev = null;
				}
			}

			@Override
			void updateNext() {
				this.next = this.next.next();
				if (!Subset.this.top && this.next != null && ObjectRBTreeSet.this.compare(this.next.key, Subset.this.to) >= 0) {
					this.next = null;
				}
			}
		}
	}
}
