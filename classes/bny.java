import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

public class bny {
	public static int a(bnw bnw, bki bki) {
		if (bki.a()) {
			return 0;
		} else {
			uh uh3 = gl.ak.b(bnw);
			lk lk4 = bki.q();

			for (int integer5 = 0; integer5 < lk4.size(); integer5++) {
				le le6 = lk4.a(integer5);
				uh uh7 = uh.a(le6.l("id"));
				if (uh7 != null && uh7.equals(uh3)) {
					return aec.a(le6.h("lvl"), 0, 255);
				}
			}

			return 0;
		}
	}

	public static Map<bnw, Integer> a(bki bki) {
		lk lk2 = bki.b() == bkk.pp ? bjm.d(bki) : bki.q();
		return a(lk2);
	}

	public static Map<bnw, Integer> a(lk lk) {
		Map<bnw, Integer> map2 = Maps.<bnw, Integer>newLinkedHashMap();

		for (int integer3 = 0; integer3 < lk.size(); integer3++) {
			le le4 = lk.a(integer3);
			gl.ak.b(uh.a(le4.l("id"))).ifPresent(bnw -> {
				Integer var10000 = (Integer)map2.put(bnw, le4.h("lvl"));
			});
		}

		return map2;
	}

	public static void a(Map<bnw, Integer> map, bki bki) {
		lk lk3 = new lk();

		for (Entry<bnw, Integer> entry5 : map.entrySet()) {
			bnw bnw6 = (bnw)entry5.getKey();
			if (bnw6 != null) {
				int integer7 = (Integer)entry5.getValue();
				le le8 = new le();
				le8.a("id", String.valueOf(gl.ak.b(bnw6)));
				le8.a("lvl", (short)integer7);
				lk3.add(le8);
				if (bki.b() == bkk.pp) {
					bjm.a(bki, new bnz(bnw6, integer7));
				}
			}
		}

		if (lk3.isEmpty()) {
			bki.c("Enchantments");
		} else if (bki.b() != bkk.pp) {
			bki.a("Enchantments", lk3);
		}
	}

	private static void a(bny.a a, bki bki) {
		if (!bki.a()) {
			lk lk3 = bki.q();

			for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
				String string5 = lk3.a(integer4).l("id");
				int integer6 = lk3.a(integer4).h("lvl");
				gl.ak.b(uh.a(string5)).ifPresent(bnw -> a.accept(bnw, integer6));
			}
		}
	}

	private static void a(bny.a a, Iterable<bki> iterable) {
		for (bki bki4 : iterable) {
			a(a, bki4);
		}
	}

	public static int a(Iterable<bki> iterable, anw anw) {
		MutableInt mutableInt3 = new MutableInt();
		a((bnw, integer) -> mutableInt3.add(bnw.a(integer, anw)), iterable);
		return mutableInt3.intValue();
	}

	public static float a(bki bki, apc apc) {
		MutableFloat mutableFloat3 = new MutableFloat();
		a((bny.a)((bnw, integer) -> mutableFloat3.add(bnw.a(integer, apc))), bki);
		return mutableFloat3.floatValue();
	}

	public static float a(aoy aoy) {
		int integer2 = a(boa.s, aoy);
		return integer2 > 0 ? bom.e(integer2) : 0.0F;
	}

	public static void a(aoy aoy, aom aom) {
		bny.a a3 = (bnw, integer) -> bnw.b(aoy, aom, integer);
		if (aoy != null) {
			a(a3, aoy.bl());
		}

		if (aom instanceof bec) {
			a(a3, aoy.dC());
		}
	}

	public static void b(aoy aoy, aom aom) {
		bny.a a3 = (bnw, integer) -> bnw.a(aoy, aom, integer);
		if (aoy != null) {
			a(a3, aoy.bl());
		}

		if (aoy instanceof bec) {
			a(a3, aoy.dC());
		}
	}

	public static int a(bnw bnw, aoy aoy) {
		Iterable<bki> iterable3 = bnw.a(aoy).values();
		if (iterable3 == null) {
			return 0;
		} else {
			int integer4 = 0;

			for (bki bki6 : iterable3) {
				int integer7 = a(bnw, bki6);
				if (integer7 > integer4) {
					integer4 = integer7;
				}
			}

			return integer4;
		}
	}

	public static int b(aoy aoy) {
		return a(boa.p, aoy);
	}

	public static int c(aoy aoy) {
		return a(boa.q, aoy);
	}

	public static int d(aoy aoy) {
		return a(boa.f, aoy);
	}

	public static int e(aoy aoy) {
		return a(boa.i, aoy);
	}

	public static int f(aoy aoy) {
		return a(boa.t, aoy);
	}

	public static int b(bki bki) {
		return a(boa.B, bki);
	}

	public static int c(bki bki) {
		return a(boa.C, bki);
	}

	public static int g(aoy aoy) {
		return a(boa.r, aoy);
	}

	public static boolean h(aoy aoy) {
		return a(boa.g, aoy) > 0;
	}

	public static boolean i(aoy aoy) {
		return a(boa.j, aoy) > 0;
	}

	public static boolean j(aoy aoy) {
		return a(boa.l, aoy) > 0;
	}

	public static boolean d(bki bki) {
		return a(boa.k, bki) > 0;
	}

	public static boolean e(bki bki) {
		return a(boa.L, bki) > 0;
	}

	public static int f(bki bki) {
		return a(boa.D, bki);
	}

	public static int g(bki bki) {
		return a(boa.F, bki);
	}

	public static boolean h(bki bki) {
		return a(boa.G, bki) > 0;
	}

	@Nullable
	public static Entry<aor, bki> b(bnw bnw, aoy aoy) {
		return a(bnw, aoy, bki -> true);
	}

	@Nullable
	public static Entry<aor, bki> a(bnw bnw, aoy aoy, Predicate<bki> predicate) {
		Map<aor, bki> map4 = bnw.a(aoy);
		if (map4.isEmpty()) {
			return null;
		} else {
			List<Entry<aor, bki>> list5 = Lists.<Entry<aor, bki>>newArrayList();

			for (Entry<aor, bki> entry7 : map4.entrySet()) {
				bki bki8 = (bki)entry7.getValue();
				if (!bki8.a() && a(bnw, bki8) > 0 && predicate.test(bki8)) {
					list5.add(entry7);
				}
			}

			return list5.isEmpty() ? null : (Entry)list5.get(aoy.cX().nextInt(list5.size()));
		}
	}

	public static int a(Random random, int integer2, int integer3, bki bki) {
		bke bke5 = bki.b();
		int integer6 = bke5.c();
		if (integer6 <= 0) {
			return 0;
		} else {
			if (integer3 > 15) {
				integer3 = 15;
			}

			int integer7 = random.nextInt(8) + 1 + (integer3 >> 1) + random.nextInt(integer3 + 1);
			if (integer2 == 0) {
				return Math.max(integer7 / 3, 1);
			} else {
				return integer2 == 1 ? integer7 * 2 / 3 + 1 : Math.max(integer7, integer3 * 2);
			}
		}
	}

	public static bki a(Random random, bki bki, int integer, boolean boolean4) {
		List<bnz> list5 = b(random, bki, integer, boolean4);
		boolean boolean6 = bki.b() == bkk.mc;
		if (boolean6) {
			bki = new bki(bkk.pp);
		}

		for (bnz bnz8 : list5) {
			if (boolean6) {
				bjm.a(bki, bnz8);
			} else {
				bki.a(bnz8.b, bnz8.c);
			}
		}

		return bki;
	}

	public static List<bnz> b(Random random, bki bki, int integer, boolean boolean4) {
		List<bnz> list5 = Lists.<bnz>newArrayList();
		bke bke6 = bki.b();
		int integer7 = bke6.c();
		if (integer7 <= 0) {
			return list5;
		} else {
			integer += 1 + random.nextInt(integer7 / 4 + 1) + random.nextInt(integer7 / 4 + 1);
			float float8 = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;
			integer = aec.a(Math.round((float)integer + (float)integer * float8), 1, Integer.MAX_VALUE);
			List<bnz> list9 = a(integer, bki, boolean4);
			if (!list9.isEmpty()) {
				list5.add(aen.a(random, list9));

				while (random.nextInt(50) <= integer) {
					a(list9, v.a(list5));
					if (list9.isEmpty()) {
						break;
					}

					list5.add(aen.a(random, list9));
					integer /= 2;
				}
			}

			return list5;
		}
	}

	public static void a(List<bnz> list, bnz bnz) {
		Iterator<bnz> iterator3 = list.iterator();

		while (iterator3.hasNext()) {
			if (!bnz.b.b(((bnz)iterator3.next()).b)) {
				iterator3.remove();
			}
		}
	}

	public static boolean a(Collection<bnw> collection, bnw bnw) {
		for (bnw bnw4 : collection) {
			if (!bnw4.b(bnw)) {
				return false;
			}
		}

		return true;
	}

	public static List<bnz> a(int integer, bki bki, boolean boolean3) {
		List<bnz> list4 = Lists.<bnz>newArrayList();
		bke bke5 = bki.b();
		boolean boolean6 = bki.b() == bkk.mc;

		for (bnw bnw8 : gl.ak) {
			if ((!bnw8.b() || boolean3) && bnw8.i() && (bnw8.b.a(bke5) || boolean6)) {
				for (int integer9 = bnw8.a(); integer9 > bnw8.e() - 1; integer9--) {
					if (integer >= bnw8.a(integer9) && integer <= bnw8.b(integer9)) {
						list4.add(new bnz(bnw8, integer9));
						break;
					}
				}
			}
		}

		return list4;
	}

	@FunctionalInterface
	interface a {
		void accept(bnw bnw, int integer);
	}
}
