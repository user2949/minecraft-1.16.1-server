import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class cio {
	private static final Predicate<cfj> a = cfj -> !cfj.g();
	private static final Predicate<cfj> b = cfj -> cfj.c().c();
	private final adj c = new adj(9, 256);
	private final Predicate<cfj> d;
	private final cgy e;

	public cio(cgy cgy, cio.a a) {
		this.d = a.e();
		this.e = cgy;
	}

	public static void a(cgy cgy, Set<cio.a> set) {
		int integer3 = set.size();
		ObjectList<cio> objectList4 = new ObjectArrayList<>(integer3);
		ObjectListIterator<cio> objectListIterator5 = objectList4.iterator();
		int integer6 = cgy.b() + 16;
		fu.a a7 = new fu.a();

		for (int integer8 = 0; integer8 < 16; integer8++) {
			for (int integer9 = 0; integer9 < 16; integer9++) {
				for (cio.a a11 : set) {
					objectList4.add(cgy.a(a11));
				}

				for (int integer10 = integer6 - 1; integer10 >= 0; integer10--) {
					a7.d(integer8, integer10, integer9);
					cfj cfj11 = cgy.d_(a7);
					if (!cfj11.a(bvs.a)) {
						while (objectListIterator5.hasNext()) {
							cio cio12 = (cio)objectListIterator5.next();
							if (cio12.d.test(cfj11)) {
								cio12.a(integer8, integer9, integer10 + 1);
								objectListIterator5.remove();
							}
						}

						if (objectList4.isEmpty()) {
							break;
						}

						objectListIterator5.back(integer3);
					}
				}
			}
		}
	}

	public boolean a(int integer1, int integer2, int integer3, cfj cfj) {
		int integer6 = this.a(integer1, integer3);
		if (integer2 <= integer6 - 2) {
			return false;
		} else {
			if (this.d.test(cfj)) {
				if (integer2 >= integer6) {
					this.a(integer1, integer3, integer2 + 1);
					return true;
				}
			} else if (integer6 - 1 == integer2) {
				fu.a a7 = new fu.a();

				for (int integer8 = integer2 - 1; integer8 >= 0; integer8--) {
					a7.d(integer1, integer8, integer3);
					if (this.d.test(this.e.d_(a7))) {
						this.a(integer1, integer3, integer8 + 1);
						return true;
					}
				}

				this.a(integer1, integer3, 0);
				return true;
			}

			return false;
		}
	}

	public int a(int integer1, int integer2) {
		return this.a(c(integer1, integer2));
	}

	private int a(int integer) {
		return this.c.a(integer);
	}

	private void a(int integer1, int integer2, int integer3) {
		this.c.b(c(integer1, integer2), integer3);
	}

	public void a(long[] arr) {
		System.arraycopy(arr, 0, this.c.a(), 0, arr.length);
	}

	public long[] a() {
		return this.c.a();
	}

	private static int c(int integer1, int integer2) {
		return integer1 + integer2 * 16;
	}

	public static enum a implements aeh {
		WORLD_SURFACE_WG("WORLD_SURFACE_WG", cio.b.WORLDGEN, cio.a),
		WORLD_SURFACE("WORLD_SURFACE", cio.b.CLIENT, cio.a),
		OCEAN_FLOOR_WG("OCEAN_FLOOR_WG", cio.b.WORLDGEN, cio.b),
		OCEAN_FLOOR("OCEAN_FLOOR", cio.b.LIVE_WORLD, cio.b),
		MOTION_BLOCKING("MOTION_BLOCKING", cio.b.CLIENT, cfj -> cfj.c().c() || !cfj.m().c()),
		MOTION_BLOCKING_NO_LEAVES("MOTION_BLOCKING_NO_LEAVES", cio.b.LIVE_WORLD, cfj -> (cfj.c().c() || !cfj.m().c()) && !(cfj.b() instanceof bza));

		public static final Codec<cio.a> g = aeh.a(cio.a::values, cio.a::a);
		private final String h;
		private final cio.b i;
		private final Predicate<cfj> j;
		private static final Map<String, cio.a> k = v.a(Maps.<String, cio.a>newHashMap(), hashMap -> {
			for (cio.a a5 : values()) {
				hashMap.put(a5.h, a5);
			}
		});

		private a(String string3, cio.b b, Predicate<cfj> predicate) {
			this.h = string3;
			this.i = b;
			this.j = predicate;
		}

		public String b() {
			return this.h;
		}

		public boolean c() {
			return this.i == cio.b.CLIENT;
		}

		@Nullable
		public static cio.a a(String string) {
			return (cio.a)k.get(string);
		}

		public Predicate<cfj> e() {
			return this.j;
		}

		@Override
		public String a() {
			return this.h;
		}
	}

	public static enum b {
		WORLDGEN,
		LIVE_WORLD,
		CLIENT;
	}
}
