import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;

public class bee {
	public final Int2IntMap a = new Int2IntOpenHashMap();

	public void a(bki bki) {
		if (!bki.f() && !bki.x() && !bki.t()) {
			this.b(bki);
		}
	}

	public void b(bki bki) {
		this.a(bki, 64);
	}

	public void a(bki bki, int integer) {
		if (!bki.a()) {
			int integer4 = c(bki);
			int integer5 = Math.min(integer, bki.E());
			this.b(integer4, integer5);
		}
	}

	public static int c(bki bki) {
		return gl.am.a(bki.b());
	}

	private boolean b(int integer) {
		return this.a.get(integer) > 0;
	}

	private int a(int integer1, int integer2) {
		int integer4 = this.a.get(integer1);
		if (integer4 >= integer2) {
			this.a.put(integer1, integer4 - integer2);
			return integer1;
		} else {
			return 0;
		}
	}

	private void b(int integer1, int integer2) {
		this.a.put(integer1, this.a.get(integer1) + integer2);
	}

	public boolean a(bmu<?> bmu, @Nullable IntList intList) {
		return this.a(bmu, intList, 1);
	}

	public boolean a(bmu<?> bmu, @Nullable IntList intList, int integer) {
		return new bee.a(bmu).a(integer, intList);
	}

	public int b(bmu<?> bmu, @Nullable IntList intList) {
		return this.a(bmu, Integer.MAX_VALUE, intList);
	}

	public int a(bmu<?> bmu, int integer, @Nullable IntList intList) {
		return new bee.a(bmu).b(integer, intList);
	}

	public static bki a(int integer) {
		return integer == 0 ? bki.b : new bki(bke.b(integer));
	}

	public void a() {
		this.a.clear();
	}

	class a {
		private final bmu<?> b;
		private final List<bmr> c = Lists.<bmr>newArrayList();
		private final int d;
		private final int[] e;
		private final int f;
		private final BitSet g;
		private final IntList h = new IntArrayList();

		public a(bmu<?> bmu) {
			this.b = bmu;
			this.c.addAll(bmu.a());
			this.c.removeIf(bmr::d);
			this.d = this.c.size();
			this.e = this.a();
			this.f = this.e.length;
			this.g = new BitSet(this.d + this.f + this.d + this.d * this.f);

			for (int integer4 = 0; integer4 < this.c.size(); integer4++) {
				IntList intList5 = ((bmr)this.c.get(integer4)).b();

				for (int integer6 = 0; integer6 < this.f; integer6++) {
					if (intList5.contains(this.e[integer6])) {
						this.g.set(this.d(true, integer6, integer4));
					}
				}
			}
		}

		public boolean a(int integer, @Nullable IntList intList) {
			if (integer <= 0) {
				return true;
			} else {
				int integer4;
				for (integer4 = 0; this.a(integer); integer4++) {
					bee.this.a(this.e[this.h.getInt(0)], integer);
					int integer5 = this.h.size() - 1;
					this.c(this.h.getInt(integer5));

					for (int integer6 = 0; integer6 < integer5; integer6++) {
						this.c((integer6 & 1) == 0, this.h.get(integer6), this.h.get(integer6 + 1));
					}

					this.h.clear();
					this.g.clear(0, this.d + this.f);
				}

				boolean boolean5 = integer4 == this.d;
				boolean boolean6 = boolean5 && intList != null;
				if (boolean6) {
					intList.clear();
				}

				this.g.clear(0, this.d + this.f + this.d);
				int integer7 = 0;
				List<bmr> list8 = this.b.a();

				for (int integer9 = 0; integer9 < list8.size(); integer9++) {
					if (boolean6 && ((bmr)list8.get(integer9)).d()) {
						intList.add(0);
					} else {
						for (int integer10 = 0; integer10 < this.f; integer10++) {
							if (this.b(false, integer7, integer10)) {
								this.c(true, integer10, integer7);
								bee.this.b(this.e[integer10], integer);
								if (boolean6) {
									intList.add(this.e[integer10]);
								}
							}
						}

						integer7++;
					}
				}

				return boolean5;
			}
		}

		private int[] a() {
			IntCollection intCollection2 = new IntAVLTreeSet();

			for (bmr bmr4 : this.c) {
				intCollection2.addAll(bmr4.b());
			}

			IntIterator intIterator3 = intCollection2.iterator();

			while (intIterator3.hasNext()) {
				if (!bee.this.b(intIterator3.nextInt())) {
					intIterator3.remove();
				}
			}

			return intCollection2.toIntArray();
		}

		private boolean a(int integer) {
			int integer3 = this.f;

			for (int integer4 = 0; integer4 < integer3; integer4++) {
				if (bee.this.a.get(this.e[integer4]) >= integer) {
					this.a(false, integer4);

					while (!this.h.isEmpty()) {
						int integer5 = this.h.size();
						boolean boolean6 = (integer5 & 1) == 1;
						int integer7 = this.h.getInt(integer5 - 1);
						if (!boolean6 && !this.b(integer7)) {
							break;
						}

						int integer8 = boolean6 ? this.d : integer3;
						int integer9 = 0;

						while (true) {
							if (integer9 < integer8) {
								if (this.b(boolean6, integer9) || !this.a(boolean6, integer7, integer9) || !this.b(boolean6, integer7, integer9)) {
									integer9++;
									continue;
								}

								this.a(boolean6, integer9);
							}

							integer9 = this.h.size();
							if (integer9 == integer5) {
								this.h.removeInt(integer9 - 1);
							}
							break;
						}
					}

					if (!this.h.isEmpty()) {
						return true;
					}
				}
			}

			return false;
		}

		private boolean b(int integer) {
			return this.g.get(this.d(integer));
		}

		private void c(int integer) {
			this.g.set(this.d(integer));
		}

		private int d(int integer) {
			return this.d + this.f + integer;
		}

		private boolean a(boolean boolean1, int integer2, int integer3) {
			return this.g.get(this.d(boolean1, integer2, integer3));
		}

		private boolean b(boolean boolean1, int integer2, int integer3) {
			return boolean1 != this.g.get(1 + this.d(boolean1, integer2, integer3));
		}

		private void c(boolean boolean1, int integer2, int integer3) {
			this.g.flip(1 + this.d(boolean1, integer2, integer3));
		}

		private int d(boolean boolean1, int integer2, int integer3) {
			int integer5 = boolean1 ? integer2 * this.d + integer3 : integer3 * this.d + integer2;
			return this.d + this.f + this.d + 2 * integer5;
		}

		private void a(boolean boolean1, int integer) {
			this.g.set(this.c(boolean1, integer));
			this.h.add(integer);
		}

		private boolean b(boolean boolean1, int integer) {
			return this.g.get(this.c(boolean1, integer));
		}

		private int c(boolean boolean1, int integer) {
			return (boolean1 ? 0 : this.d) + integer;
		}

		public int b(int integer, @Nullable IntList intList) {
			int integer4 = 0;
			int integer5 = Math.min(integer, this.b()) + 1;

			while (true) {
				int integer6 = (integer4 + integer5) / 2;
				if (this.a(integer6, null)) {
					if (integer5 - integer4 <= 1) {
						if (integer6 > 0) {
							this.a(integer6, intList);
						}

						return integer6;
					}

					integer4 = integer6;
				} else {
					integer5 = integer6;
				}
			}
		}

		private int b() {
			int integer2 = Integer.MAX_VALUE;

			for (bmr bmr4 : this.c) {
				int integer5 = 0;

				for (int integer7 : bmr4.b()) {
					integer5 = Math.max(integer5, bee.this.a.get(integer7));
				}

				if (integer2 > 0) {
					integer2 = Math.min(integer2, integer5);
				}
			}

			return integer2;
		}
	}
}
