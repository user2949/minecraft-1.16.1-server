import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongList;
import java.util.function.LongPredicate;

public abstract class cwm {
	private final int a;
	private final LongLinkedOpenHashSet[] b;
	private final Long2ByteMap c;
	private int d;
	private volatile boolean e;

	protected cwm(int integer1, int integer2, int integer3) {
		if (integer1 >= 254) {
			throw new IllegalArgumentException("Level count must be < 254.");
		} else {
			this.a = integer1;
			this.b = new LongLinkedOpenHashSet[integer1];

			for (int integer5 = 0; integer5 < integer1; integer5++) {
				this.b[integer5] = new LongLinkedOpenHashSet(integer2, 0.5F) {
					@Override
					protected void rehash(int integer) {
						if (integer > integer2) {
							super.rehash(integer);
						}
					}
				};
			}

			this.c = new Long2ByteOpenHashMap(integer3, 0.5F) {
				@Override
				protected void rehash(int integer) {
					if (integer > integer3) {
						super.rehash(integer);
					}
				}
			};
			this.c.defaultReturnValue((byte)-1);
			this.d = integer1;
		}
	}

	private int a(int integer1, int integer2) {
		int integer4 = integer1;
		if (integer1 > integer2) {
			integer4 = integer2;
		}

		if (integer4 > this.a - 1) {
			integer4 = this.a - 1;
		}

		return integer4;
	}

	private void a(int integer) {
		int integer3 = this.d;
		this.d = integer;

		for (int integer4 = integer3 + 1; integer4 < integer; integer4++) {
			if (!this.b[integer4].isEmpty()) {
				this.d = integer4;
				break;
			}
		}
	}

	protected void e(long long1) {
		int integer4 = this.c.get(long1) & 255;
		if (integer4 != 255) {
			int integer5 = this.c(long1);
			int integer6 = this.a(integer5, integer4);
			this.a(long1, integer6, this.a, true);
			this.e = this.d < this.a;
		}
	}

	public void a(LongPredicate longPredicate) {
		LongList longList3 = new LongArrayList();
		this.c.keySet().forEach(long3 -> {
			if (longPredicate.test(long3)) {
				longList3.add(long3);
			}
		});
		longList3.forEach(this::e);
	}

	private void a(long long1, int integer2, int integer3, boolean boolean4) {
		if (boolean4) {
			this.c.remove(long1);
		}

		this.b[integer2].remove(long1);
		if (this.b[integer2].isEmpty() && this.d == integer2) {
			this.a(integer3);
		}
	}

	private void a(long long1, int integer2, int integer3) {
		this.c.put(long1, (byte)integer2);
		this.b[integer3].add(long1);
		if (this.d > integer3) {
			this.d = integer3;
		}
	}

	protected void f(long long1) {
		this.a(long1, long1, this.a - 1, false);
	}

	protected void a(long long1, long long2, int integer, boolean boolean4) {
		this.a(long1, long2, integer, this.c(long2), this.c.get(long2) & 255, boolean4);
		this.e = this.d < this.a;
	}

	private void a(long long1, long long2, int integer3, int integer4, int integer5, boolean boolean6) {
		if (!this.a(long2)) {
			integer3 = aec.a(integer3, 0, this.a - 1);
			integer4 = aec.a(integer4, 0, this.a - 1);
			boolean boolean10;
			if (integer5 == 255) {
				boolean10 = true;
				integer5 = integer4;
			} else {
				boolean10 = false;
			}

			int integer11;
			if (boolean6) {
				integer11 = Math.min(integer5, integer3);
			} else {
				integer11 = aec.a(this.a(long2, long1, integer3), 0, this.a - 1);
			}

			int integer12 = this.a(integer4, integer5);
			if (integer4 != integer11) {
				int integer13 = this.a(integer4, integer11);
				if (integer12 != integer13 && !boolean10) {
					this.a(long2, integer12, integer13, false);
				}

				this.a(long2, integer11, integer13);
			} else if (!boolean10) {
				this.a(long2, integer12, this.a, true);
			}
		}
	}

	protected final void b(long long1, long long2, int integer, boolean boolean4) {
		int integer8 = this.c.get(long2) & 255;
		int integer9 = aec.a(this.b(long1, long2, integer), 0, this.a - 1);
		if (boolean4) {
			this.a(long1, long2, integer9, this.c(long2), integer8, true);
		} else {
			int integer10;
			boolean boolean11;
			if (integer8 == 255) {
				boolean11 = true;
				integer10 = aec.a(this.c(long2), 0, this.a - 1);
			} else {
				integer10 = integer8;
				boolean11 = false;
			}

			if (integer9 == integer10) {
				this.a(long1, long2, this.a - 1, boolean11 ? integer10 : this.c(long2), integer8, false);
			}
		}
	}

	protected final boolean b() {
		return this.e;
	}

	protected final int b(int integer) {
		if (this.d >= this.a) {
			return integer;
		} else {
			while (this.d < this.a && integer > 0) {
				integer--;
				LongLinkedOpenHashSet longLinkedOpenHashSet3 = this.b[this.d];
				long long4 = longLinkedOpenHashSet3.removeFirstLong();
				int integer6 = aec.a(this.c(long4), 0, this.a - 1);
				if (longLinkedOpenHashSet3.isEmpty()) {
					this.a(this.a);
				}

				int integer7 = this.c.remove(long4) & 255;
				if (integer7 < integer6) {
					this.a(long4, integer7);
					this.a(long4, integer7, true);
				} else if (integer7 > integer6) {
					this.a(long4, integer7, this.a(this.a - 1, integer7));
					this.a(long4, this.a - 1);
					this.a(long4, integer6, false);
				}
			}

			this.e = this.d < this.a;
			return integer;
		}
	}

	public int c() {
		return this.c.size();
	}

	protected abstract boolean a(long long1);

	protected abstract int a(long long1, long long2, int integer);

	protected abstract void a(long long1, int integer, boolean boolean3);

	protected abstract int c(long long1);

	protected abstract void a(long long1, int integer);

	protected abstract int b(long long1, long long2, int integer);
}
