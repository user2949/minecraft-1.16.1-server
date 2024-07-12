package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2FloatMap.BasicEntry;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;

public class Short2FloatAVLTreeMap extends AbstractShort2FloatSortedMap implements Serializable, Cloneable {
	protected transient Short2FloatAVLTreeMap.Entry tree;
	protected int count;
	protected transient Short2FloatAVLTreeMap.Entry firstEntry;
	protected transient Short2FloatAVLTreeMap.Entry lastEntry;
	protected transient ObjectSortedSet<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> entries;
	protected transient ShortSortedSet keys;
	protected transient FloatCollection values;
	protected transient boolean modified;
	protected Comparator<? super Short> storedComparator;
	protected transient ShortComparator actualComparator;
	private static final long serialVersionUID = -7046029254386353129L;
	private transient boolean[] dirPath;

	public Short2FloatAVLTreeMap() {
		this.allocatePaths();
		this.tree = null;
		this.count = 0;
	}

	private void setActualComparator() {
		this.actualComparator = ShortComparators.asShortComparator(this.storedComparator);
	}

	public Short2FloatAVLTreeMap(Comparator<? super Short> c) {
		this();
		this.storedComparator = c;
		this.setActualComparator();
	}

	public Short2FloatAVLTreeMap(Map<? extends Short, ? extends Float> m) {
		this();
		this.putAll(m);
	}

	public Short2FloatAVLTreeMap(SortedMap<Short, Float> m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Short2FloatAVLTreeMap(Short2FloatMap m) {
		this();
		this.putAll(m);
	}

	public Short2FloatAVLTreeMap(Short2FloatSortedMap m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Short2FloatAVLTreeMap(short[] k, float[] v, Comparator<? super Short> c) {
		this(c);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Short2FloatAVLTreeMap(short[] k, float[] v) {
		this(k, v, null);
	}

	final int compare(short k1, short k2) {
		return this.actualComparator == null ? Short.compare(k1, k2) : this.actualComparator.compare(k1, k2);
	}

	final Short2FloatAVLTreeMap.Entry findKey(short k) {
		Short2FloatAVLTreeMap.Entry e = this.tree;

		int cmp;
		while (e != null && (cmp = this.compare(k, e.key)) != 0) {
			e = cmp < 0 ? e.left() : e.right();
		}

		return e;
	}

	final Short2FloatAVLTreeMap.Entry locateKey(short k) {
		Short2FloatAVLTreeMap.Entry e = this.tree;
		Short2FloatAVLTreeMap.Entry last = this.tree;

		int cmp;
		for (cmp = 0; e != null && (cmp = this.compare(k, e.key)) != 0; e = cmp < 0 ? e.left() : e.right()) {
			last = e;
		}

		return cmp == 0 ? e : last;
	}

	private void allocatePaths() {
		this.dirPath = new boolean[48];
	}

	public float addTo(short k, float incr) {
		Short2FloatAVLTreeMap.Entry e = this.add(k);
		float oldValue = e.value;
		e.value += incr;
		return oldValue;
	}

	@Override
	public float put(short k, float v) {
		Short2FloatAVLTreeMap.Entry e = this.add(k);
		float oldValue = e.value;
		e.value = v;
		return oldValue;
	}

	private Short2FloatAVLTreeMap.Entry add(short k) {
		this.modified = false;
		Short2FloatAVLTreeMap.Entry e = null;
		if (this.tree == null) {
			this.count++;
			e = this.tree = this.lastEntry = this.firstEntry = new Short2FloatAVLTreeMap.Entry(k, this.defRetValue);
			this.modified = true;
		} else {
			Short2FloatAVLTreeMap.Entry p = this.tree;
			Short2FloatAVLTreeMap.Entry q = null;
			Short2FloatAVLTreeMap.Entry y = this.tree;
			Short2FloatAVLTreeMap.Entry z = null;
			Short2FloatAVLTreeMap.Entry w = null;
			int i = 0;

			while (true) {
				int cmp;
				if ((cmp = this.compare(k, p.key)) == 0) {
					return p;
				}

				if (p.balance() != 0) {
					i = 0;
					z = q;
					y = p;
				}

				if (this.dirPath[i++] = cmp > 0) {
					if (p.succ()) {
						this.count++;
						e = new Short2FloatAVLTreeMap.Entry(k, this.defRetValue);
						this.modified = true;
						if (p.right == null) {
							this.lastEntry = e;
						}

						e.left = p;
						e.right = p.right;
						p.right(e);
						break;
					}

					q = p;
					p = p.right;
				} else {
					if (p.pred()) {
						this.count++;
						e = new Short2FloatAVLTreeMap.Entry(k, this.defRetValue);
						this.modified = true;
						if (p.left == null) {
							this.firstEntry = e;
						}

						e.right = p;
						e.left = p.left;
						p.left(e);
						break;
					}

					q = p;
					p = p.left;
				}
			}

			p = y;

			for (int var14 = 0; p != e; p = this.dirPath[var14++] ? p.right : p.left) {
				if (this.dirPath[var14]) {
					p.incBalance();
				} else {
					p.decBalance();
				}
			}

			if (y.balance() == -2) {
				Short2FloatAVLTreeMap.Entry x = y.left;
				if (x.balance() == -1) {
					w = x;
					if (x.succ()) {
						x.succ(false);
						y.pred(x);
					} else {
						y.left = x.right;
					}

					x.right = y;
					x.balance(0);
					y.balance(0);
				} else {
					assert x.balance() == 1;

					w = x.right;
					x.right = w.left;
					w.left = x;
					y.left = w.right;
					w.right = y;
					if (w.balance() == -1) {
						x.balance(0);
						y.balance(1);
					} else if (w.balance() == 0) {
						x.balance(0);
						y.balance(0);
					} else {
						x.balance(-1);
						y.balance(0);
					}

					w.balance(0);
					if (w.pred()) {
						x.succ(w);
						w.pred(false);
					}

					if (w.succ()) {
						y.pred(w);
						w.succ(false);
					}
				}
			} else {
				if (y.balance() != 2) {
					return e;
				}

				Short2FloatAVLTreeMap.Entry x = y.right;
				if (x.balance() == 1) {
					w = x;
					if (x.pred()) {
						x.pred(false);
						y.succ(x);
					} else {
						y.right = x.left;
					}

					x.left = y;
					x.balance(0);
					y.balance(0);
				} else {
					assert x.balance() == -1;

					w = x.left;
					x.left = w.right;
					w.right = x;
					y.right = w.left;
					w.left = y;
					if (w.balance() == 1) {
						x.balance(0);
						y.balance(-1);
					} else if (w.balance() == 0) {
						x.balance(0);
						y.balance(0);
					} else {
						x.balance(1);
						y.balance(0);
					}

					w.balance(0);
					if (w.pred()) {
						y.succ(w);
						w.pred(false);
					}

					if (w.succ()) {
						x.pred(w);
						w.succ(false);
					}
				}
			}

			if (z == null) {
				this.tree = w;
			} else if (z.left == y) {
				z.left = w;
			} else {
				z.right = w;
			}
		}

		return e;
	}

	private Short2FloatAVLTreeMap.Entry parent(Short2FloatAVLTreeMap.Entry e) {
		if (e == this.tree) {
			return null;
		} else {
			Short2FloatAVLTreeMap.Entry y = e;

			Short2FloatAVLTreeMap.Entry x;
			for (x = e; !y.succ(); y = y.right) {
				if (x.pred()) {
					Short2FloatAVLTreeMap.Entry p = x.left;
					if (p == null || p.right != e) {
						while (!y.succ()) {
							y = y.right;
						}

						p = y.right;
					}

					return p;
				}

				x = x.left;
			}

			Short2FloatAVLTreeMap.Entry p = y.right;
			if (p == null || p.left != e) {
				while (!x.pred()) {
					x = x.left;
				}

				p = x.left;
			}

			return p;
		}
	}

	@Override
	public float remove(short k) {
		this.modified = false;
		if (this.tree == null) {
			return this.defRetValue;
		} else {
			Short2FloatAVLTreeMap.Entry p = this.tree;
			Short2FloatAVLTreeMap.Entry q = null;
			boolean dir = false;
			short kk = k;

			int cmp;
			while ((cmp = this.compare(kk, p.key)) != 0) {
				if (dir = cmp > 0) {
					q = p;
					if ((p = p.right()) == null) {
						return this.defRetValue;
					}
				} else {
					q = p;
					if ((p = p.left()) == null) {
						return this.defRetValue;
					}
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
					if (q != null) {
						if (dir) {
							q.succ(p.right);
						} else {
							q.pred(p.left);
						}
					} else {
						this.tree = dir ? p.right : p.left;
					}
				} else {
					p.prev().right = p.right;
					if (q != null) {
						if (dir) {
							q.right = p.left;
						} else {
							q.left = p.left;
						}
					} else {
						this.tree = p.left;
					}
				}
			} else {
				Short2FloatAVLTreeMap.Entry r = p.right;
				if (r.pred()) {
					r.left = p.left;
					r.pred(p.pred());
					if (!r.pred()) {
						r.prev().right = r;
					}

					if (q != null) {
						if (dir) {
							q.right = r;
						} else {
							q.left = r;
						}
					} else {
						this.tree = r;
					}

					r.balance(p.balance());
					q = r;
					dir = true;
				} else {
					while (true) {
						Short2FloatAVLTreeMap.Entry s = r.left;
						if (s.pred()) {
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

							s.right = p.right;
							s.succ(false);
							if (q != null) {
								if (dir) {
									q.right = s;
								} else {
									q.left = s;
								}
							} else {
								this.tree = s;
							}

							s.balance(p.balance());
							q = r;
							dir = false;
							break;
						}

						r = s;
					}
				}
			}

			while (q != null) {
				Short2FloatAVLTreeMap.Entry y = q;
				q = this.parent(q);
				if (!dir) {
					dir = q != null && q.left != y;
					y.incBalance();
					if (y.balance() == 1) {
						break;
					}

					if (y.balance() == 2) {
						Short2FloatAVLTreeMap.Entry x = y.right;

						assert x != null;

						if (x.balance() == -1) {
							assert x.balance() == -1;

							Short2FloatAVLTreeMap.Entry w = x.left;
							x.left = w.right;
							w.right = x;
							y.right = w.left;
							w.left = y;
							if (w.balance() == 1) {
								x.balance(0);
								y.balance(-1);
							} else if (w.balance() == 0) {
								x.balance(0);
								y.balance(0);
							} else {
								assert w.balance() == -1;

								x.balance(1);
								y.balance(0);
							}

							w.balance(0);
							if (w.pred()) {
								y.succ(w);
								w.pred(false);
							}

							if (w.succ()) {
								x.pred(w);
								w.succ(false);
							}

							if (q != null) {
								if (dir) {
									q.right = w;
								} else {
									q.left = w;
								}
							} else {
								this.tree = w;
							}
						} else {
							if (q != null) {
								if (dir) {
									q.right = x;
								} else {
									q.left = x;
								}
							} else {
								this.tree = x;
							}

							if (x.balance() == 0) {
								y.right = x.left;
								x.left = y;
								x.balance(-1);
								y.balance(1);
								break;
							}

							assert x.balance() == 1;

							if (x.pred()) {
								y.succ(true);
								x.pred(false);
							} else {
								y.right = x.left;
							}

							x.left = y;
							y.balance(0);
							x.balance(0);
						}
					}
				} else {
					dir = q != null && q.left != y;
					y.decBalance();
					if (y.balance() == -1) {
						break;
					}

					if (y.balance() == -2) {
						Short2FloatAVLTreeMap.Entry xx = y.left;

						assert xx != null;

						if (xx.balance() == 1) {
							assert xx.balance() == 1;

							Short2FloatAVLTreeMap.Entry wx = xx.right;
							xx.right = wx.left;
							wx.left = xx;
							y.left = wx.right;
							wx.right = y;
							if (wx.balance() == -1) {
								xx.balance(0);
								y.balance(1);
							} else if (wx.balance() == 0) {
								xx.balance(0);
								y.balance(0);
							} else {
								assert wx.balance() == 1;

								xx.balance(-1);
								y.balance(0);
							}

							wx.balance(0);
							if (wx.pred()) {
								xx.succ(wx);
								wx.pred(false);
							}

							if (wx.succ()) {
								y.pred(wx);
								wx.succ(false);
							}

							if (q != null) {
								if (dir) {
									q.right = wx;
								} else {
									q.left = wx;
								}
							} else {
								this.tree = wx;
							}
						} else {
							if (q != null) {
								if (dir) {
									q.right = xx;
								} else {
									q.left = xx;
								}
							} else {
								this.tree = xx;
							}

							if (xx.balance() == 0) {
								y.left = xx.right;
								xx.right = y;
								xx.balance(1);
								y.balance(-1);
								break;
							}

							assert xx.balance() == -1;

							if (xx.succ()) {
								y.pred(true);
								xx.succ(false);
							} else {
								y.left = xx.right;
							}

							xx.right = y;
							y.balance(0);
							xx.balance(0);
						}
					}
				}
			}

			this.modified = true;
			this.count--;
			return p.value;
		}
	}

	@Override
	public boolean containsValue(float v) {
		Short2FloatAVLTreeMap.ValueIterator i = new Short2FloatAVLTreeMap.ValueIterator();
		int j = this.count;

		while (j-- != 0) {
			float ev = i.nextFloat();
			if (Float.floatToIntBits(ev) == Float.floatToIntBits(v)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void clear() {
		this.count = 0;
		this.tree = null;
		this.entries = null;
		this.values = null;
		this.keys = null;
		this.firstEntry = this.lastEntry = null;
	}

	@Override
	public boolean containsKey(short k) {
		return this.findKey(k) != null;
	}

	@Override
	public int size() {
		return this.count;
	}

	@Override
	public boolean isEmpty() {
		return this.count == 0;
	}

	@Override
	public float get(short k) {
		Short2FloatAVLTreeMap.Entry e = this.findKey(k);
		return e == null ? this.defRetValue : e.value;
	}

	@Override
	public short firstShortKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.firstEntry.key;
		}
	}

	@Override
	public short lastShortKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.lastEntry.key;
		}
	}

	@Override
	public ObjectSortedSet<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> short2FloatEntrySet() {
		if (this.entries == null) {
			this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry>() {
				final Comparator<? super it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> comparator = (x, y) -> Short2FloatAVLTreeMap.this.actualComparator
						.compare(x.getShortKey(), y.getShortKey());

				public Comparator<? super it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> comparator() {
					return this.comparator;
				}

				@Override
				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> iterator() {
					return Short2FloatAVLTreeMap.this.new EntryIterator();
				}

				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> iterator(it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry from) {
					return Short2FloatAVLTreeMap.this.new EntryIterator(from.getShortKey());
				}

				public boolean contains(Object o) {
					if (!(o instanceof java.util.Map.Entry)) {
						return false;
					} else {
						java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
						if (e.getKey() == null || !(e.getKey() instanceof Short)) {
							return false;
						} else if (e.getValue() != null && e.getValue() instanceof Float) {
							Short2FloatAVLTreeMap.Entry f = Short2FloatAVLTreeMap.this.findKey((Short)e.getKey());
							return e.equals(f);
						} else {
							return false;
						}
					}
				}

				public boolean remove(Object o) {
					if (!(o instanceof java.util.Map.Entry)) {
						return false;
					} else {
						java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
						if (e.getKey() == null || !(e.getKey() instanceof Short)) {
							return false;
						} else if (e.getValue() != null && e.getValue() instanceof Float) {
							Short2FloatAVLTreeMap.Entry f = Short2FloatAVLTreeMap.this.findKey((Short)e.getKey());
							if (f != null && Float.floatToIntBits(f.getFloatValue()) == Float.floatToIntBits((Float)e.getValue())) {
								Short2FloatAVLTreeMap.this.remove(f.key);
								return true;
							} else {
								return false;
							}
						} else {
							return false;
						}
					}
				}

				public int size() {
					return Short2FloatAVLTreeMap.this.count;
				}

				public void clear() {
					Short2FloatAVLTreeMap.this.clear();
				}

				public it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry first() {
					return Short2FloatAVLTreeMap.this.firstEntry;
				}

				public it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry last() {
					return Short2FloatAVLTreeMap.this.lastEntry;
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> subSet(
					it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry from, it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry to
				) {
					return Short2FloatAVLTreeMap.this.subMap(from.getShortKey(), to.getShortKey()).short2FloatEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> headSet(it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry to) {
					return Short2FloatAVLTreeMap.this.headMap(to.getShortKey()).short2FloatEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> tailSet(it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry from) {
					return Short2FloatAVLTreeMap.this.tailMap(from.getShortKey()).short2FloatEntrySet();
				}
			};
		}

		return this.entries;
	}

	@Override
	public ShortSortedSet keySet() {
		if (this.keys == null) {
			this.keys = new Short2FloatAVLTreeMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public FloatCollection values() {
		if (this.values == null) {
			this.values = new AbstractFloatCollection() {
				@Override
				public FloatIterator iterator() {
					return Short2FloatAVLTreeMap.this.new ValueIterator();
				}

				@Override
				public boolean contains(float k) {
					return Short2FloatAVLTreeMap.this.containsValue(k);
				}

				public int size() {
					return Short2FloatAVLTreeMap.this.count;
				}

				public void clear() {
					Short2FloatAVLTreeMap.this.clear();
				}
			};
		}

		return this.values;
	}

	@Override
	public ShortComparator comparator() {
		return this.actualComparator;
	}

	@Override
	public Short2FloatSortedMap headMap(short to) {
		return new Short2FloatAVLTreeMap.Submap((short)0, true, to, false);
	}

	@Override
	public Short2FloatSortedMap tailMap(short from) {
		return new Short2FloatAVLTreeMap.Submap(from, false, (short)0, true);
	}

	@Override
	public Short2FloatSortedMap subMap(short from, short to) {
		return new Short2FloatAVLTreeMap.Submap(from, false, to, false);
	}

	public Short2FloatAVLTreeMap clone() {
		Short2FloatAVLTreeMap c;
		try {
			c = (Short2FloatAVLTreeMap)super.clone();
		} catch (CloneNotSupportedException var7) {
			throw new InternalError();
		}

		c.keys = null;
		c.values = null;
		c.entries = null;
		c.allocatePaths();
		if (this.count == 0) {
			return c;
		} else {
			Short2FloatAVLTreeMap.Entry rp = new Short2FloatAVLTreeMap.Entry();
			Short2FloatAVLTreeMap.Entry rq = new Short2FloatAVLTreeMap.Entry();
			Short2FloatAVLTreeMap.Entry p = rp;
			rp.left(this.tree);
			Short2FloatAVLTreeMap.Entry q = rq;
			rq.pred(null);

			while (true) {
				if (!p.pred()) {
					Short2FloatAVLTreeMap.Entry e = p.left.clone();
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
					Short2FloatAVLTreeMap.Entry e = p.right.clone();
					e.succ(q.right);
					e.pred(q);
					q.right(e);
				}
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		int n = this.count;
		Short2FloatAVLTreeMap.EntryIterator i = new Short2FloatAVLTreeMap.EntryIterator();
		s.defaultWriteObject();

		while (n-- != 0) {
			Short2FloatAVLTreeMap.Entry e = i.nextEntry();
			s.writeShort(e.key);
			s.writeFloat(e.value);
		}
	}

	private Short2FloatAVLTreeMap.Entry readTree(ObjectInputStream s, int n, Short2FloatAVLTreeMap.Entry pred, Short2FloatAVLTreeMap.Entry succ) throws IOException, ClassNotFoundException {
		if (n == 1) {
			Short2FloatAVLTreeMap.Entry top = new Short2FloatAVLTreeMap.Entry(s.readShort(), s.readFloat());
			top.pred(pred);
			top.succ(succ);
			return top;
		} else if (n == 2) {
			Short2FloatAVLTreeMap.Entry top = new Short2FloatAVLTreeMap.Entry(s.readShort(), s.readFloat());
			top.right(new Short2FloatAVLTreeMap.Entry(s.readShort(), s.readFloat()));
			top.right.pred(top);
			top.balance(1);
			top.pred(pred);
			top.right.succ(succ);
			return top;
		} else {
			int rightN = n / 2;
			int leftN = n - rightN - 1;
			Short2FloatAVLTreeMap.Entry top = new Short2FloatAVLTreeMap.Entry();
			top.left(this.readTree(s, leftN, pred, top));
			top.key = s.readShort();
			top.value = s.readFloat();
			top.right(this.readTree(s, rightN, top, succ));
			if (n == (n & -n)) {
				top.balance(1);
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
			Short2FloatAVLTreeMap.Entry e = this.tree;

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

	private static final class Entry extends BasicEntry implements Cloneable {
		private static final int SUCC_MASK = Integer.MIN_VALUE;
		private static final int PRED_MASK = 1073741824;
		private static final int BALANCE_MASK = 255;
		Short2FloatAVLTreeMap.Entry left;
		Short2FloatAVLTreeMap.Entry right;
		int info;

		Entry() {
			super((short)0, 0.0F);
		}

		Entry(short k, float v) {
			super(k, v);
			this.info = -1073741824;
		}

		Short2FloatAVLTreeMap.Entry left() {
			return (this.info & 1073741824) != 0 ? null : this.left;
		}

		Short2FloatAVLTreeMap.Entry right() {
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

		void pred(Short2FloatAVLTreeMap.Entry pred) {
			this.info |= 1073741824;
			this.left = pred;
		}

		void succ(Short2FloatAVLTreeMap.Entry succ) {
			this.info |= Integer.MIN_VALUE;
			this.right = succ;
		}

		void left(Short2FloatAVLTreeMap.Entry left) {
			this.info &= -1073741825;
			this.left = left;
		}

		void right(Short2FloatAVLTreeMap.Entry right) {
			this.info &= Integer.MAX_VALUE;
			this.right = right;
		}

		int balance() {
			return (byte)this.info;
		}

		void balance(int level) {
			this.info &= -256;
			this.info |= level & 0xFF;
		}

		void incBalance() {
			this.info = this.info & -256 | (byte)this.info + 1 & 0xFF;
		}

		protected void decBalance() {
			this.info = this.info & -256 | (byte)this.info - 1 & 0xFF;
		}

		Short2FloatAVLTreeMap.Entry next() {
			Short2FloatAVLTreeMap.Entry next = this.right;
			if ((this.info & -2147483648) == 0) {
				while ((next.info & 1073741824) == 0) {
					next = next.left;
				}
			}

			return next;
		}

		Short2FloatAVLTreeMap.Entry prev() {
			Short2FloatAVLTreeMap.Entry prev = this.left;
			if ((this.info & 1073741824) == 0) {
				while ((prev.info & -2147483648) == 0) {
					prev = prev.right;
				}
			}

			return prev;
		}

		@Override
		public float setValue(float value) {
			float oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public Short2FloatAVLTreeMap.Entry clone() {
			Short2FloatAVLTreeMap.Entry c;
			try {
				c = (Short2FloatAVLTreeMap.Entry)super.clone();
			} catch (CloneNotSupportedException var3) {
				throw new InternalError();
			}

			c.key = this.key;
			c.value = this.value;
			c.info = this.info;
			return c;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry<Short, Float> e = (java.util.Map.Entry<Short, Float>)o;
				return this.key == (Short)e.getKey() && Float.floatToIntBits(this.value) == Float.floatToIntBits((Float)e.getValue());
			}
		}

		@Override
		public int hashCode() {
			return this.key ^ HashCommon.float2int(this.value);
		}

		@Override
		public String toString() {
			return this.key + "=>" + this.value;
		}
	}

	private class EntryIterator extends Short2FloatAVLTreeMap.TreeIterator implements ObjectListIterator<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> {
		EntryIterator() {
		}

		EntryIterator(short k) {
			super(k);
		}

		public it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry next() {
			return this.nextEntry();
		}

		public it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry previous() {
			return this.previousEntry();
		}

		public void set(it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry ok) {
			throw new UnsupportedOperationException();
		}

		public void add(it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry ok) {
			throw new UnsupportedOperationException();
		}
	}

	private final class KeyIterator extends Short2FloatAVLTreeMap.TreeIterator implements ShortListIterator {
		public KeyIterator() {
		}

		public KeyIterator(short k) {
			super(k);
		}

		@Override
		public short nextShort() {
			return this.nextEntry().key;
		}

		@Override
		public short previousShort() {
			return this.previousEntry().key;
		}
	}

	private class KeySet extends it.unimi.dsi.fastutil.shorts.AbstractShort2FloatSortedMap.KeySet {
		private KeySet() {
			super(Short2FloatAVLTreeMap.this);
		}

		@Override
		public ShortBidirectionalIterator iterator() {
			return Short2FloatAVLTreeMap.this.new KeyIterator();
		}

		@Override
		public ShortBidirectionalIterator iterator(short from) {
			return Short2FloatAVLTreeMap.this.new KeyIterator(from);
		}
	}

	private final class Submap extends AbstractShort2FloatSortedMap implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		short from;
		short to;
		boolean bottom;
		boolean top;
		protected transient ObjectSortedSet<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> entries;
		protected transient ShortSortedSet keys;
		protected transient FloatCollection values;

		public Submap(short from, boolean bottom, short to, boolean top) {
			if (!bottom && !top && Short2FloatAVLTreeMap.this.compare(from, to) > 0) {
				throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")");
			} else {
				this.from = from;
				this.bottom = bottom;
				this.to = to;
				this.top = top;
				this.defRetValue = Short2FloatAVLTreeMap.this.defRetValue;
			}
		}

		@Override
		public void clear() {
			Short2FloatAVLTreeMap.Submap.SubmapIterator i = new Short2FloatAVLTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				i.nextEntry();
				i.remove();
			}
		}

		final boolean in(short k) {
			return (this.bottom || Short2FloatAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Short2FloatAVLTreeMap.this.compare(k, this.to) < 0);
		}

		@Override
		public ObjectSortedSet<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> short2FloatEntrySet() {
			if (this.entries == null) {
				this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry>() {
					@Override
					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> iterator() {
						return Submap.this.new SubmapEntryIterator();
					}

					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> iterator(it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry from) {
						return Submap.this.new SubmapEntryIterator(from.getShortKey());
					}

					public Comparator<? super it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> comparator() {
						return Short2FloatAVLTreeMap.this.short2FloatEntrySet().comparator();
					}

					public boolean contains(Object o) {
						if (!(o instanceof java.util.Map.Entry)) {
							return false;
						} else {
							java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
							if (e.getKey() == null || !(e.getKey() instanceof Short)) {
								return false;
							} else if (e.getValue() != null && e.getValue() instanceof Float) {
								Short2FloatAVLTreeMap.Entry f = Short2FloatAVLTreeMap.this.findKey((Short)e.getKey());
								return f != null && Submap.this.in(f.key) && e.equals(f);
							} else {
								return false;
							}
						}
					}

					public boolean remove(Object o) {
						if (!(o instanceof java.util.Map.Entry)) {
							return false;
						} else {
							java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
							if (e.getKey() == null || !(e.getKey() instanceof Short)) {
								return false;
							} else if (e.getValue() != null && e.getValue() instanceof Float) {
								Short2FloatAVLTreeMap.Entry f = Short2FloatAVLTreeMap.this.findKey((Short)e.getKey());
								if (f != null && Submap.this.in(f.key)) {
									Submap.this.remove(f.key);
								}

								return f != null;
							} else {
								return false;
							}
						}
					}

					public int size() {
						int c = 0;
						Iterator<?> i = this.iterator();

						while (i.hasNext()) {
							c++;
							i.next();
						}

						return c;
					}

					public boolean isEmpty() {
						return !Submap.this.new SubmapIterator().hasNext();
					}

					public void clear() {
						Submap.this.clear();
					}

					public it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry first() {
						return Submap.this.firstEntry();
					}

					public it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry last() {
						return Submap.this.lastEntry();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> subSet(
						it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry from, it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry to
					) {
						return Submap.this.subMap(from.getShortKey(), to.getShortKey()).short2FloatEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> headSet(it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry to) {
						return Submap.this.headMap(to.getShortKey()).short2FloatEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> tailSet(it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry from) {
						return Submap.this.tailMap(from.getShortKey()).short2FloatEntrySet();
					}
				};
			}

			return this.entries;
		}

		@Override
		public ShortSortedSet keySet() {
			if (this.keys == null) {
				this.keys = new Short2FloatAVLTreeMap.Submap.KeySet();
			}

			return this.keys;
		}

		@Override
		public FloatCollection values() {
			if (this.values == null) {
				this.values = new AbstractFloatCollection() {
					@Override
					public FloatIterator iterator() {
						return Submap.this.new SubmapValueIterator();
					}

					@Override
					public boolean contains(float k) {
						return Submap.this.containsValue(k);
					}

					public int size() {
						return Submap.this.size();
					}

					public void clear() {
						Submap.this.clear();
					}
				};
			}

			return this.values;
		}

		@Override
		public boolean containsKey(short k) {
			return this.in(k) && Short2FloatAVLTreeMap.this.containsKey(k);
		}

		@Override
		public boolean containsValue(float v) {
			Short2FloatAVLTreeMap.Submap.SubmapIterator i = new Short2FloatAVLTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				float ev = i.nextEntry().value;
				if (Float.floatToIntBits(ev) == Float.floatToIntBits(v)) {
					return true;
				}
			}

			return false;
		}

		@Override
		public float get(short k) {
			Short2FloatAVLTreeMap.Entry e;
			return this.in(k) && (e = Short2FloatAVLTreeMap.this.findKey(k)) != null ? e.value : this.defRetValue;
		}

		@Override
		public float put(short k, float v) {
			Short2FloatAVLTreeMap.this.modified = false;
			if (!this.in(k)) {
				throw new IllegalArgumentException(
					"Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"
				);
			} else {
				float oldValue = Short2FloatAVLTreeMap.this.put(k, v);
				return Short2FloatAVLTreeMap.this.modified ? this.defRetValue : oldValue;
			}
		}

		@Override
		public float remove(short k) {
			Short2FloatAVLTreeMap.this.modified = false;
			if (!this.in(k)) {
				return this.defRetValue;
			} else {
				float oldValue = Short2FloatAVLTreeMap.this.remove(k);
				return Short2FloatAVLTreeMap.this.modified ? oldValue : this.defRetValue;
			}
		}

		@Override
		public int size() {
			Short2FloatAVLTreeMap.Submap.SubmapIterator i = new Short2FloatAVLTreeMap.Submap.SubmapIterator();
			int n = 0;

			while (i.hasNext()) {
				n++;
				i.nextEntry();
			}

			return n;
		}

		@Override
		public boolean isEmpty() {
			return !new Short2FloatAVLTreeMap.Submap.SubmapIterator().hasNext();
		}

		@Override
		public ShortComparator comparator() {
			return Short2FloatAVLTreeMap.this.actualComparator;
		}

		@Override
		public Short2FloatSortedMap headMap(short to) {
			if (this.top) {
				return Short2FloatAVLTreeMap.this.new Submap(this.from, this.bottom, to, false);
			} else {
				return Short2FloatAVLTreeMap.this.compare(to, this.to) < 0 ? Short2FloatAVLTreeMap.this.new Submap(this.from, this.bottom, to, false) : this;
			}
		}

		@Override
		public Short2FloatSortedMap tailMap(short from) {
			if (this.bottom) {
				return Short2FloatAVLTreeMap.this.new Submap(from, false, this.to, this.top);
			} else {
				return Short2FloatAVLTreeMap.this.compare(from, this.from) > 0 ? Short2FloatAVLTreeMap.this.new Submap(from, false, this.to, this.top) : this;
			}
		}

		@Override
		public Short2FloatSortedMap subMap(short from, short to) {
			if (this.top && this.bottom) {
				return Short2FloatAVLTreeMap.this.new Submap(from, false, to, false);
			} else {
				if (!this.top) {
					to = Short2FloatAVLTreeMap.this.compare(to, this.to) < 0 ? to : this.to;
				}

				if (!this.bottom) {
					from = Short2FloatAVLTreeMap.this.compare(from, this.from) > 0 ? from : this.from;
				}

				return !this.top && !this.bottom && from == this.from && to == this.to ? this : Short2FloatAVLTreeMap.this.new Submap(from, false, to, false);
			}
		}

		public Short2FloatAVLTreeMap.Entry firstEntry() {
			if (Short2FloatAVLTreeMap.this.tree == null) {
				return null;
			} else {
				Short2FloatAVLTreeMap.Entry e;
				if (this.bottom) {
					e = Short2FloatAVLTreeMap.this.firstEntry;
				} else {
					e = Short2FloatAVLTreeMap.this.locateKey(this.from);
					if (Short2FloatAVLTreeMap.this.compare(e.key, this.from) < 0) {
						e = e.next();
					}
				}

				return e != null && (this.top || Short2FloatAVLTreeMap.this.compare(e.key, this.to) < 0) ? e : null;
			}
		}

		public Short2FloatAVLTreeMap.Entry lastEntry() {
			if (Short2FloatAVLTreeMap.this.tree == null) {
				return null;
			} else {
				Short2FloatAVLTreeMap.Entry e;
				if (this.top) {
					e = Short2FloatAVLTreeMap.this.lastEntry;
				} else {
					e = Short2FloatAVLTreeMap.this.locateKey(this.to);
					if (Short2FloatAVLTreeMap.this.compare(e.key, this.to) >= 0) {
						e = e.prev();
					}
				}

				return e != null && (this.bottom || Short2FloatAVLTreeMap.this.compare(e.key, this.from) >= 0) ? e : null;
			}
		}

		@Override
		public short firstShortKey() {
			Short2FloatAVLTreeMap.Entry e = this.firstEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		@Override
		public short lastShortKey() {
			Short2FloatAVLTreeMap.Entry e = this.lastEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		private class KeySet extends it.unimi.dsi.fastutil.shorts.AbstractShort2FloatSortedMap.KeySet {
			private KeySet() {
				super(Submap.this);
			}

			@Override
			public ShortBidirectionalIterator iterator() {
				return Submap.this.new SubmapKeyIterator();
			}

			@Override
			public ShortBidirectionalIterator iterator(short from) {
				return Submap.this.new SubmapKeyIterator(from);
			}
		}

		private class SubmapEntryIterator
			extends Short2FloatAVLTreeMap.Submap.SubmapIterator
			implements ObjectListIterator<it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry> {
			SubmapEntryIterator() {
			}

			SubmapEntryIterator(short k) {
				super(k);
			}

			public it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry next() {
				return this.nextEntry();
			}

			public it.unimi.dsi.fastutil.shorts.Short2FloatMap.Entry previous() {
				return this.previousEntry();
			}
		}

		private class SubmapIterator extends Short2FloatAVLTreeMap.TreeIterator {
			SubmapIterator() {
				this.next = Submap.this.firstEntry();
			}

			SubmapIterator(short k) {
				this();
				if (this.next != null) {
					if (!Submap.this.bottom && Short2FloatAVLTreeMap.this.compare(k, this.next.key) < 0) {
						this.prev = null;
					} else if (!Submap.this.top && Short2FloatAVLTreeMap.this.compare(k, (this.prev = Submap.this.lastEntry()).key) >= 0) {
						this.next = null;
					} else {
						this.next = Short2FloatAVLTreeMap.this.locateKey(k);
						if (Short2FloatAVLTreeMap.this.compare(this.next.key, k) <= 0) {
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
				if (!Submap.this.bottom && this.prev != null && Short2FloatAVLTreeMap.this.compare(this.prev.key, Submap.this.from) < 0) {
					this.prev = null;
				}
			}

			@Override
			void updateNext() {
				this.next = this.next.next();
				if (!Submap.this.top && this.next != null && Short2FloatAVLTreeMap.this.compare(this.next.key, Submap.this.to) >= 0) {
					this.next = null;
				}
			}
		}

		private final class SubmapKeyIterator extends Short2FloatAVLTreeMap.Submap.SubmapIterator implements ShortListIterator {
			public SubmapKeyIterator() {
			}

			public SubmapKeyIterator(short from) {
				super(from);
			}

			@Override
			public short nextShort() {
				return this.nextEntry().key;
			}

			@Override
			public short previousShort() {
				return this.previousEntry().key;
			}
		}

		private final class SubmapValueIterator extends Short2FloatAVLTreeMap.Submap.SubmapIterator implements FloatListIterator {
			private SubmapValueIterator() {
			}

			@Override
			public float nextFloat() {
				return this.nextEntry().value;
			}

			@Override
			public float previousFloat() {
				return this.previousEntry().value;
			}
		}
	}

	private class TreeIterator {
		Short2FloatAVLTreeMap.Entry prev;
		Short2FloatAVLTreeMap.Entry next;
		Short2FloatAVLTreeMap.Entry curr;
		int index = 0;

		TreeIterator() {
			this.next = Short2FloatAVLTreeMap.this.firstEntry;
		}

		TreeIterator(short k) {
			if ((this.next = Short2FloatAVLTreeMap.this.locateKey(k)) != null) {
				if (Short2FloatAVLTreeMap.this.compare(this.next.key, k) <= 0) {
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

		public boolean hasPrevious() {
			return this.prev != null;
		}

		void updateNext() {
			this.next = this.next.next();
		}

		Short2FloatAVLTreeMap.Entry nextEntry() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.curr = this.prev = this.next;
				this.index++;
				this.updateNext();
				return this.curr;
			}
		}

		void updatePrevious() {
			this.prev = this.prev.prev();
		}

		Short2FloatAVLTreeMap.Entry previousEntry() {
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
				Short2FloatAVLTreeMap.this.remove(this.curr.key);
				this.curr = null;
			}
		}

		public int skip(int n) {
			int i = n;

			while (i-- != 0 && this.hasNext()) {
				this.nextEntry();
			}

			return n - i - 1;
		}

		public int back(int n) {
			int i = n;

			while (i-- != 0 && this.hasPrevious()) {
				this.previousEntry();
			}

			return n - i - 1;
		}
	}

	private final class ValueIterator extends Short2FloatAVLTreeMap.TreeIterator implements FloatListIterator {
		private ValueIterator() {
		}

		@Override
		public float nextFloat() {
			return this.nextEntry().value;
		}

		@Override
		public float previousFloat() {
			return this.previousEntry().value;
		}
	}
}
