package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
import it.unimi.dsi.fastutil.chars.AbstractChar2BooleanMap.BasicEntry;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;

public class Char2BooleanAVLTreeMap extends AbstractChar2BooleanSortedMap implements Serializable, Cloneable {
	protected transient Char2BooleanAVLTreeMap.Entry tree;
	protected int count;
	protected transient Char2BooleanAVLTreeMap.Entry firstEntry;
	protected transient Char2BooleanAVLTreeMap.Entry lastEntry;
	protected transient ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> entries;
	protected transient CharSortedSet keys;
	protected transient BooleanCollection values;
	protected transient boolean modified;
	protected Comparator<? super Character> storedComparator;
	protected transient CharComparator actualComparator;
	private static final long serialVersionUID = -7046029254386353129L;
	private transient boolean[] dirPath;

	public Char2BooleanAVLTreeMap() {
		this.allocatePaths();
		this.tree = null;
		this.count = 0;
	}

	private void setActualComparator() {
		this.actualComparator = CharComparators.asCharComparator(this.storedComparator);
	}

	public Char2BooleanAVLTreeMap(Comparator<? super Character> c) {
		this();
		this.storedComparator = c;
		this.setActualComparator();
	}

	public Char2BooleanAVLTreeMap(Map<? extends Character, ? extends Boolean> m) {
		this();
		this.putAll(m);
	}

	public Char2BooleanAVLTreeMap(SortedMap<Character, Boolean> m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Char2BooleanAVLTreeMap(Char2BooleanMap m) {
		this();
		this.putAll(m);
	}

	public Char2BooleanAVLTreeMap(Char2BooleanSortedMap m) {
		this(m.comparator());
		this.putAll(m);
	}

	public Char2BooleanAVLTreeMap(char[] k, boolean[] v, Comparator<? super Character> c) {
		this(c);
		if (k.length != v.length) {
			throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
		} else {
			for (int i = 0; i < k.length; i++) {
				this.put(k[i], v[i]);
			}
		}
	}

	public Char2BooleanAVLTreeMap(char[] k, boolean[] v) {
		this(k, v, null);
	}

	final int compare(char k1, char k2) {
		return this.actualComparator == null ? Character.compare(k1, k2) : this.actualComparator.compare(k1, k2);
	}

	final Char2BooleanAVLTreeMap.Entry findKey(char k) {
		Char2BooleanAVLTreeMap.Entry e = this.tree;

		int cmp;
		while (e != null && (cmp = this.compare(k, e.key)) != 0) {
			e = cmp < 0 ? e.left() : e.right();
		}

		return e;
	}

	final Char2BooleanAVLTreeMap.Entry locateKey(char k) {
		Char2BooleanAVLTreeMap.Entry e = this.tree;
		Char2BooleanAVLTreeMap.Entry last = this.tree;

		int cmp;
		for (cmp = 0; e != null && (cmp = this.compare(k, e.key)) != 0; e = cmp < 0 ? e.left() : e.right()) {
			last = e;
		}

		return cmp == 0 ? e : last;
	}

	private void allocatePaths() {
		this.dirPath = new boolean[48];
	}

	@Override
	public boolean put(char k, boolean v) {
		Char2BooleanAVLTreeMap.Entry e = this.add(k);
		boolean oldValue = e.value;
		e.value = v;
		return oldValue;
	}

	private Char2BooleanAVLTreeMap.Entry add(char k) {
		this.modified = false;
		Char2BooleanAVLTreeMap.Entry e = null;
		if (this.tree == null) {
			this.count++;
			e = this.tree = this.lastEntry = this.firstEntry = new Char2BooleanAVLTreeMap.Entry(k, this.defRetValue);
			this.modified = true;
		} else {
			Char2BooleanAVLTreeMap.Entry p = this.tree;
			Char2BooleanAVLTreeMap.Entry q = null;
			Char2BooleanAVLTreeMap.Entry y = this.tree;
			Char2BooleanAVLTreeMap.Entry z = null;
			Char2BooleanAVLTreeMap.Entry w = null;
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
						e = new Char2BooleanAVLTreeMap.Entry(k, this.defRetValue);
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
						e = new Char2BooleanAVLTreeMap.Entry(k, this.defRetValue);
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
				Char2BooleanAVLTreeMap.Entry x = y.left;
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

				Char2BooleanAVLTreeMap.Entry x = y.right;
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

	private Char2BooleanAVLTreeMap.Entry parent(Char2BooleanAVLTreeMap.Entry e) {
		if (e == this.tree) {
			return null;
		} else {
			Char2BooleanAVLTreeMap.Entry y = e;

			Char2BooleanAVLTreeMap.Entry x;
			for (x = e; !y.succ(); y = y.right) {
				if (x.pred()) {
					Char2BooleanAVLTreeMap.Entry p = x.left;
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

			Char2BooleanAVLTreeMap.Entry p = y.right;
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
	public boolean remove(char k) {
		this.modified = false;
		if (this.tree == null) {
			return this.defRetValue;
		} else {
			Char2BooleanAVLTreeMap.Entry p = this.tree;
			Char2BooleanAVLTreeMap.Entry q = null;
			boolean dir = false;
			char kk = k;

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
				Char2BooleanAVLTreeMap.Entry r = p.right;
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
						Char2BooleanAVLTreeMap.Entry s = r.left;
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
				Char2BooleanAVLTreeMap.Entry y = q;
				q = this.parent(q);
				if (!dir) {
					dir = q != null && q.left != y;
					y.incBalance();
					if (y.balance() == 1) {
						break;
					}

					if (y.balance() == 2) {
						Char2BooleanAVLTreeMap.Entry x = y.right;

						assert x != null;

						if (x.balance() == -1) {
							assert x.balance() == -1;

							Char2BooleanAVLTreeMap.Entry w = x.left;
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
						Char2BooleanAVLTreeMap.Entry xx = y.left;

						assert xx != null;

						if (xx.balance() == 1) {
							assert xx.balance() == 1;

							Char2BooleanAVLTreeMap.Entry wx = xx.right;
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
	public boolean containsValue(boolean v) {
		Char2BooleanAVLTreeMap.ValueIterator i = new Char2BooleanAVLTreeMap.ValueIterator();
		int j = this.count;

		while (j-- != 0) {
			boolean ev = i.nextBoolean();
			if (ev == v) {
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
	public boolean containsKey(char k) {
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
	public boolean get(char k) {
		Char2BooleanAVLTreeMap.Entry e = this.findKey(k);
		return e == null ? this.defRetValue : e.value;
	}

	@Override
	public char firstCharKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.firstEntry.key;
		}
	}

	@Override
	public char lastCharKey() {
		if (this.tree == null) {
			throw new NoSuchElementException();
		} else {
			return this.lastEntry.key;
		}
	}

	@Override
	public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> char2BooleanEntrySet() {
		if (this.entries == null) {
			this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry>() {
				final Comparator<? super it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> comparator = (x, y) -> Char2BooleanAVLTreeMap.this.actualComparator
						.compare(x.getCharKey(), y.getCharKey());

				public Comparator<? super it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> comparator() {
					return this.comparator;
				}

				@Override
				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> iterator() {
					return Char2BooleanAVLTreeMap.this.new EntryIterator();
				}

				public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> iterator(it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry from) {
					return Char2BooleanAVLTreeMap.this.new EntryIterator(from.getCharKey());
				}

				public boolean contains(Object o) {
					if (!(o instanceof java.util.Map.Entry)) {
						return false;
					} else {
						java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
						if (e.getKey() == null || !(e.getKey() instanceof Character)) {
							return false;
						} else if (e.getValue() != null && e.getValue() instanceof Boolean) {
							Char2BooleanAVLTreeMap.Entry f = Char2BooleanAVLTreeMap.this.findKey((Character)e.getKey());
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
						if (e.getKey() == null || !(e.getKey() instanceof Character)) {
							return false;
						} else if (e.getValue() != null && e.getValue() instanceof Boolean) {
							Char2BooleanAVLTreeMap.Entry f = Char2BooleanAVLTreeMap.this.findKey((Character)e.getKey());
							if (f != null && f.getBooleanValue() == (Boolean)e.getValue()) {
								Char2BooleanAVLTreeMap.this.remove(f.key);
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
					return Char2BooleanAVLTreeMap.this.count;
				}

				public void clear() {
					Char2BooleanAVLTreeMap.this.clear();
				}

				public it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry first() {
					return Char2BooleanAVLTreeMap.this.firstEntry;
				}

				public it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry last() {
					return Char2BooleanAVLTreeMap.this.lastEntry;
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> subSet(
					it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry from, it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry to
				) {
					return Char2BooleanAVLTreeMap.this.subMap(from.getCharKey(), to.getCharKey()).char2BooleanEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> headSet(it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry to) {
					return Char2BooleanAVLTreeMap.this.headMap(to.getCharKey()).char2BooleanEntrySet();
				}

				public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> tailSet(it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry from) {
					return Char2BooleanAVLTreeMap.this.tailMap(from.getCharKey()).char2BooleanEntrySet();
				}
			};
		}

		return this.entries;
	}

	@Override
	public CharSortedSet keySet() {
		if (this.keys == null) {
			this.keys = new Char2BooleanAVLTreeMap.KeySet();
		}

		return this.keys;
	}

	@Override
	public BooleanCollection values() {
		if (this.values == null) {
			this.values = new AbstractBooleanCollection() {
				@Override
				public BooleanIterator iterator() {
					return Char2BooleanAVLTreeMap.this.new ValueIterator();
				}

				@Override
				public boolean contains(boolean k) {
					return Char2BooleanAVLTreeMap.this.containsValue(k);
				}

				public int size() {
					return Char2BooleanAVLTreeMap.this.count;
				}

				public void clear() {
					Char2BooleanAVLTreeMap.this.clear();
				}
			};
		}

		return this.values;
	}

	@Override
	public CharComparator comparator() {
		return this.actualComparator;
	}

	@Override
	public Char2BooleanSortedMap headMap(char to) {
		return new Char2BooleanAVLTreeMap.Submap('\u0000', true, to, false);
	}

	@Override
	public Char2BooleanSortedMap tailMap(char from) {
		return new Char2BooleanAVLTreeMap.Submap(from, false, '\u0000', true);
	}

	@Override
	public Char2BooleanSortedMap subMap(char from, char to) {
		return new Char2BooleanAVLTreeMap.Submap(from, false, to, false);
	}

	public Char2BooleanAVLTreeMap clone() {
		Char2BooleanAVLTreeMap c;
		try {
			c = (Char2BooleanAVLTreeMap)super.clone();
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
			Char2BooleanAVLTreeMap.Entry rp = new Char2BooleanAVLTreeMap.Entry();
			Char2BooleanAVLTreeMap.Entry rq = new Char2BooleanAVLTreeMap.Entry();
			Char2BooleanAVLTreeMap.Entry p = rp;
			rp.left(this.tree);
			Char2BooleanAVLTreeMap.Entry q = rq;
			rq.pred(null);

			while (true) {
				if (!p.pred()) {
					Char2BooleanAVLTreeMap.Entry e = p.left.clone();
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
					Char2BooleanAVLTreeMap.Entry e = p.right.clone();
					e.succ(q.right);
					e.pred(q);
					q.right(e);
				}
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		int n = this.count;
		Char2BooleanAVLTreeMap.EntryIterator i = new Char2BooleanAVLTreeMap.EntryIterator();
		s.defaultWriteObject();

		while (n-- != 0) {
			Char2BooleanAVLTreeMap.Entry e = i.nextEntry();
			s.writeChar(e.key);
			s.writeBoolean(e.value);
		}
	}

	private Char2BooleanAVLTreeMap.Entry readTree(ObjectInputStream s, int n, Char2BooleanAVLTreeMap.Entry pred, Char2BooleanAVLTreeMap.Entry succ) throws IOException, ClassNotFoundException {
		if (n == 1) {
			Char2BooleanAVLTreeMap.Entry top = new Char2BooleanAVLTreeMap.Entry(s.readChar(), s.readBoolean());
			top.pred(pred);
			top.succ(succ);
			return top;
		} else if (n == 2) {
			Char2BooleanAVLTreeMap.Entry top = new Char2BooleanAVLTreeMap.Entry(s.readChar(), s.readBoolean());
			top.right(new Char2BooleanAVLTreeMap.Entry(s.readChar(), s.readBoolean()));
			top.right.pred(top);
			top.balance(1);
			top.pred(pred);
			top.right.succ(succ);
			return top;
		} else {
			int rightN = n / 2;
			int leftN = n - rightN - 1;
			Char2BooleanAVLTreeMap.Entry top = new Char2BooleanAVLTreeMap.Entry();
			top.left(this.readTree(s, leftN, pred, top));
			top.key = s.readChar();
			top.value = s.readBoolean();
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
			Char2BooleanAVLTreeMap.Entry e = this.tree;

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
		Char2BooleanAVLTreeMap.Entry left;
		Char2BooleanAVLTreeMap.Entry right;
		int info;

		Entry() {
			super('\u0000', false);
		}

		Entry(char k, boolean v) {
			super(k, v);
			this.info = -1073741824;
		}

		Char2BooleanAVLTreeMap.Entry left() {
			return (this.info & 1073741824) != 0 ? null : this.left;
		}

		Char2BooleanAVLTreeMap.Entry right() {
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

		void pred(Char2BooleanAVLTreeMap.Entry pred) {
			this.info |= 1073741824;
			this.left = pred;
		}

		void succ(Char2BooleanAVLTreeMap.Entry succ) {
			this.info |= Integer.MIN_VALUE;
			this.right = succ;
		}

		void left(Char2BooleanAVLTreeMap.Entry left) {
			this.info &= -1073741825;
			this.left = left;
		}

		void right(Char2BooleanAVLTreeMap.Entry right) {
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

		Char2BooleanAVLTreeMap.Entry next() {
			Char2BooleanAVLTreeMap.Entry next = this.right;
			if ((this.info & -2147483648) == 0) {
				while ((next.info & 1073741824) == 0) {
					next = next.left;
				}
			}

			return next;
		}

		Char2BooleanAVLTreeMap.Entry prev() {
			Char2BooleanAVLTreeMap.Entry prev = this.left;
			if ((this.info & 1073741824) == 0) {
				while ((prev.info & -2147483648) == 0) {
					prev = prev.right;
				}
			}

			return prev;
		}

		@Override
		public boolean setValue(boolean value) {
			boolean oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public Char2BooleanAVLTreeMap.Entry clone() {
			Char2BooleanAVLTreeMap.Entry c;
			try {
				c = (Char2BooleanAVLTreeMap.Entry)super.clone();
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
				java.util.Map.Entry<Character, Boolean> e = (java.util.Map.Entry<Character, Boolean>)o;
				return this.key == (Character)e.getKey() && this.value == (Boolean)e.getValue();
			}
		}

		@Override
		public int hashCode() {
			return this.key ^ (this.value ? 1231 : 1237);
		}

		@Override
		public String toString() {
			return this.key + "=>" + this.value;
		}
	}

	private class EntryIterator extends Char2BooleanAVLTreeMap.TreeIterator implements ObjectListIterator<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> {
		EntryIterator() {
		}

		EntryIterator(char k) {
			super(k);
		}

		public it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry next() {
			return this.nextEntry();
		}

		public it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry previous() {
			return this.previousEntry();
		}

		public void set(it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry ok) {
			throw new UnsupportedOperationException();
		}

		public void add(it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry ok) {
			throw new UnsupportedOperationException();
		}
	}

	private final class KeyIterator extends Char2BooleanAVLTreeMap.TreeIterator implements CharListIterator {
		public KeyIterator() {
		}

		public KeyIterator(char k) {
			super(k);
		}

		@Override
		public char nextChar() {
			return this.nextEntry().key;
		}

		@Override
		public char previousChar() {
			return this.previousEntry().key;
		}
	}

	private class KeySet extends it.unimi.dsi.fastutil.chars.AbstractChar2BooleanSortedMap.KeySet {
		private KeySet() {
			super(Char2BooleanAVLTreeMap.this);
		}

		@Override
		public CharBidirectionalIterator iterator() {
			return Char2BooleanAVLTreeMap.this.new KeyIterator();
		}

		@Override
		public CharBidirectionalIterator iterator(char from) {
			return Char2BooleanAVLTreeMap.this.new KeyIterator(from);
		}
	}

	private final class Submap extends AbstractChar2BooleanSortedMap implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		char from;
		char to;
		boolean bottom;
		boolean top;
		protected transient ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> entries;
		protected transient CharSortedSet keys;
		protected transient BooleanCollection values;

		public Submap(char from, boolean bottom, char to, boolean top) {
			if (!bottom && !top && Char2BooleanAVLTreeMap.this.compare(from, to) > 0) {
				throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")");
			} else {
				this.from = from;
				this.bottom = bottom;
				this.to = to;
				this.top = top;
				this.defRetValue = Char2BooleanAVLTreeMap.this.defRetValue;
			}
		}

		@Override
		public void clear() {
			Char2BooleanAVLTreeMap.Submap.SubmapIterator i = new Char2BooleanAVLTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				i.nextEntry();
				i.remove();
			}
		}

		final boolean in(char k) {
			return (this.bottom || Char2BooleanAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Char2BooleanAVLTreeMap.this.compare(k, this.to) < 0);
		}

		@Override
		public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> char2BooleanEntrySet() {
			if (this.entries == null) {
				this.entries = new AbstractObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry>() {
					@Override
					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> iterator() {
						return Submap.this.new SubmapEntryIterator();
					}

					public ObjectBidirectionalIterator<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> iterator(it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry from) {
						return Submap.this.new SubmapEntryIterator(from.getCharKey());
					}

					public Comparator<? super it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> comparator() {
						return Char2BooleanAVLTreeMap.this.char2BooleanEntrySet().comparator();
					}

					public boolean contains(Object o) {
						if (!(o instanceof java.util.Map.Entry)) {
							return false;
						} else {
							java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>)o;
							if (e.getKey() == null || !(e.getKey() instanceof Character)) {
								return false;
							} else if (e.getValue() != null && e.getValue() instanceof Boolean) {
								Char2BooleanAVLTreeMap.Entry f = Char2BooleanAVLTreeMap.this.findKey((Character)e.getKey());
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
							if (e.getKey() == null || !(e.getKey() instanceof Character)) {
								return false;
							} else if (e.getValue() != null && e.getValue() instanceof Boolean) {
								Char2BooleanAVLTreeMap.Entry f = Char2BooleanAVLTreeMap.this.findKey((Character)e.getKey());
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

					public it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry first() {
						return Submap.this.firstEntry();
					}

					public it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry last() {
						return Submap.this.lastEntry();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> subSet(
						it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry from, it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry to
					) {
						return Submap.this.subMap(from.getCharKey(), to.getCharKey()).char2BooleanEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> headSet(it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry to) {
						return Submap.this.headMap(to.getCharKey()).char2BooleanEntrySet();
					}

					public ObjectSortedSet<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> tailSet(it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry from) {
						return Submap.this.tailMap(from.getCharKey()).char2BooleanEntrySet();
					}
				};
			}

			return this.entries;
		}

		@Override
		public CharSortedSet keySet() {
			if (this.keys == null) {
				this.keys = new Char2BooleanAVLTreeMap.Submap.KeySet();
			}

			return this.keys;
		}

		@Override
		public BooleanCollection values() {
			if (this.values == null) {
				this.values = new AbstractBooleanCollection() {
					@Override
					public BooleanIterator iterator() {
						return Submap.this.new SubmapValueIterator();
					}

					@Override
					public boolean contains(boolean k) {
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
		public boolean containsKey(char k) {
			return this.in(k) && Char2BooleanAVLTreeMap.this.containsKey(k);
		}

		@Override
		public boolean containsValue(boolean v) {
			Char2BooleanAVLTreeMap.Submap.SubmapIterator i = new Char2BooleanAVLTreeMap.Submap.SubmapIterator();

			while (i.hasNext()) {
				boolean ev = i.nextEntry().value;
				if (ev == v) {
					return true;
				}
			}

			return false;
		}

		@Override
		public boolean get(char k) {
			Char2BooleanAVLTreeMap.Entry e;
			return this.in(k) && (e = Char2BooleanAVLTreeMap.this.findKey(k)) != null ? e.value : this.defRetValue;
		}

		@Override
		public boolean put(char k, boolean v) {
			Char2BooleanAVLTreeMap.this.modified = false;
			if (!this.in(k)) {
				throw new IllegalArgumentException(
					"Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"
				);
			} else {
				boolean oldValue = Char2BooleanAVLTreeMap.this.put(k, v);
				return Char2BooleanAVLTreeMap.this.modified ? this.defRetValue : oldValue;
			}
		}

		@Override
		public boolean remove(char k) {
			Char2BooleanAVLTreeMap.this.modified = false;
			if (!this.in(k)) {
				return this.defRetValue;
			} else {
				boolean oldValue = Char2BooleanAVLTreeMap.this.remove(k);
				return Char2BooleanAVLTreeMap.this.modified ? oldValue : this.defRetValue;
			}
		}

		@Override
		public int size() {
			Char2BooleanAVLTreeMap.Submap.SubmapIterator i = new Char2BooleanAVLTreeMap.Submap.SubmapIterator();
			int n = 0;

			while (i.hasNext()) {
				n++;
				i.nextEntry();
			}

			return n;
		}

		@Override
		public boolean isEmpty() {
			return !new Char2BooleanAVLTreeMap.Submap.SubmapIterator().hasNext();
		}

		@Override
		public CharComparator comparator() {
			return Char2BooleanAVLTreeMap.this.actualComparator;
		}

		@Override
		public Char2BooleanSortedMap headMap(char to) {
			if (this.top) {
				return Char2BooleanAVLTreeMap.this.new Submap(this.from, this.bottom, to, false);
			} else {
				return Char2BooleanAVLTreeMap.this.compare(to, this.to) < 0 ? Char2BooleanAVLTreeMap.this.new Submap(this.from, this.bottom, to, false) : this;
			}
		}

		@Override
		public Char2BooleanSortedMap tailMap(char from) {
			if (this.bottom) {
				return Char2BooleanAVLTreeMap.this.new Submap(from, false, this.to, this.top);
			} else {
				return Char2BooleanAVLTreeMap.this.compare(from, this.from) > 0 ? Char2BooleanAVLTreeMap.this.new Submap(from, false, this.to, this.top) : this;
			}
		}

		@Override
		public Char2BooleanSortedMap subMap(char from, char to) {
			if (this.top && this.bottom) {
				return Char2BooleanAVLTreeMap.this.new Submap(from, false, to, false);
			} else {
				if (!this.top) {
					to = Char2BooleanAVLTreeMap.this.compare(to, this.to) < 0 ? to : this.to;
				}

				if (!this.bottom) {
					from = Char2BooleanAVLTreeMap.this.compare(from, this.from) > 0 ? from : this.from;
				}

				return !this.top && !this.bottom && from == this.from && to == this.to ? this : Char2BooleanAVLTreeMap.this.new Submap(from, false, to, false);
			}
		}

		public Char2BooleanAVLTreeMap.Entry firstEntry() {
			if (Char2BooleanAVLTreeMap.this.tree == null) {
				return null;
			} else {
				Char2BooleanAVLTreeMap.Entry e;
				if (this.bottom) {
					e = Char2BooleanAVLTreeMap.this.firstEntry;
				} else {
					e = Char2BooleanAVLTreeMap.this.locateKey(this.from);
					if (Char2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0) {
						e = e.next();
					}
				}

				return e != null && (this.top || Char2BooleanAVLTreeMap.this.compare(e.key, this.to) < 0) ? e : null;
			}
		}

		public Char2BooleanAVLTreeMap.Entry lastEntry() {
			if (Char2BooleanAVLTreeMap.this.tree == null) {
				return null;
			} else {
				Char2BooleanAVLTreeMap.Entry e;
				if (this.top) {
					e = Char2BooleanAVLTreeMap.this.lastEntry;
				} else {
					e = Char2BooleanAVLTreeMap.this.locateKey(this.to);
					if (Char2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0) {
						e = e.prev();
					}
				}

				return e != null && (this.bottom || Char2BooleanAVLTreeMap.this.compare(e.key, this.from) >= 0) ? e : null;
			}
		}

		@Override
		public char firstCharKey() {
			Char2BooleanAVLTreeMap.Entry e = this.firstEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		@Override
		public char lastCharKey() {
			Char2BooleanAVLTreeMap.Entry e = this.lastEntry();
			if (e == null) {
				throw new NoSuchElementException();
			} else {
				return e.key;
			}
		}

		private class KeySet extends it.unimi.dsi.fastutil.chars.AbstractChar2BooleanSortedMap.KeySet {
			private KeySet() {
				super(Submap.this);
			}

			@Override
			public CharBidirectionalIterator iterator() {
				return Submap.this.new SubmapKeyIterator();
			}

			@Override
			public CharBidirectionalIterator iterator(char from) {
				return Submap.this.new SubmapKeyIterator(from);
			}
		}

		private class SubmapEntryIterator
			extends Char2BooleanAVLTreeMap.Submap.SubmapIterator
			implements ObjectListIterator<it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry> {
			SubmapEntryIterator() {
			}

			SubmapEntryIterator(char k) {
				super(k);
			}

			public it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry next() {
				return this.nextEntry();
			}

			public it.unimi.dsi.fastutil.chars.Char2BooleanMap.Entry previous() {
				return this.previousEntry();
			}
		}

		private class SubmapIterator extends Char2BooleanAVLTreeMap.TreeIterator {
			SubmapIterator() {
				this.next = Submap.this.firstEntry();
			}

			SubmapIterator(char k) {
				this();
				if (this.next != null) {
					if (!Submap.this.bottom && Char2BooleanAVLTreeMap.this.compare(k, this.next.key) < 0) {
						this.prev = null;
					} else if (!Submap.this.top && Char2BooleanAVLTreeMap.this.compare(k, (this.prev = Submap.this.lastEntry()).key) >= 0) {
						this.next = null;
					} else {
						this.next = Char2BooleanAVLTreeMap.this.locateKey(k);
						if (Char2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0) {
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
				if (!Submap.this.bottom && this.prev != null && Char2BooleanAVLTreeMap.this.compare(this.prev.key, Submap.this.from) < 0) {
					this.prev = null;
				}
			}

			@Override
			void updateNext() {
				this.next = this.next.next();
				if (!Submap.this.top && this.next != null && Char2BooleanAVLTreeMap.this.compare(this.next.key, Submap.this.to) >= 0) {
					this.next = null;
				}
			}
		}

		private final class SubmapKeyIterator extends Char2BooleanAVLTreeMap.Submap.SubmapIterator implements CharListIterator {
			public SubmapKeyIterator() {
			}

			public SubmapKeyIterator(char from) {
				super(from);
			}

			@Override
			public char nextChar() {
				return this.nextEntry().key;
			}

			@Override
			public char previousChar() {
				return this.previousEntry().key;
			}
		}

		private final class SubmapValueIterator extends Char2BooleanAVLTreeMap.Submap.SubmapIterator implements BooleanListIterator {
			private SubmapValueIterator() {
			}

			@Override
			public boolean nextBoolean() {
				return this.nextEntry().value;
			}

			@Override
			public boolean previousBoolean() {
				return this.previousEntry().value;
			}
		}
	}

	private class TreeIterator {
		Char2BooleanAVLTreeMap.Entry prev;
		Char2BooleanAVLTreeMap.Entry next;
		Char2BooleanAVLTreeMap.Entry curr;
		int index = 0;

		TreeIterator() {
			this.next = Char2BooleanAVLTreeMap.this.firstEntry;
		}

		TreeIterator(char k) {
			if ((this.next = Char2BooleanAVLTreeMap.this.locateKey(k)) != null) {
				if (Char2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0) {
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

		Char2BooleanAVLTreeMap.Entry nextEntry() {
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

		Char2BooleanAVLTreeMap.Entry previousEntry() {
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
				Char2BooleanAVLTreeMap.this.remove(this.curr.key);
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

	private final class ValueIterator extends Char2BooleanAVLTreeMap.TreeIterator implements BooleanListIterator {
		private ValueIterator() {
		}

		@Override
		public boolean nextBoolean() {
			return this.nextEntry().value;
		}

		@Override
		public boolean previousBoolean() {
			return this.previousEntry().value;
		}
	}
}
