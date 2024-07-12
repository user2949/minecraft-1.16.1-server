import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class apt {
	private final aps a;
	private final Map<apv.a, Set<apv>> b = Maps.newEnumMap(apv.a.class);
	private final Map<UUID, apv> c = new Object2ObjectArrayMap<>();
	private final Set<apv> d = new ObjectArraySet<>();
	private double e;
	private boolean f = true;
	private double g;
	private final Consumer<apt> h;

	public apt(aps aps, Consumer<apt> consumer) {
		this.a = aps;
		this.h = consumer;
		this.e = aps.a();
	}

	public aps a() {
		return this.a;
	}

	public double b() {
		return this.e;
	}

	public void a(double double1) {
		if (double1 != this.e) {
			this.e = double1;
			this.d();
		}
	}

	public Set<apv> a(apv.a a) {
		return (Set<apv>)this.b.computeIfAbsent(a, ax -> Sets.newHashSet());
	}

	public Set<apv> c() {
		return ImmutableSet.copyOf(this.c.values());
	}

	@Nullable
	public apv a(UUID uUID) {
		return (apv)this.c.get(uUID);
	}

	public boolean a(apv apv) {
		return this.c.get(apv.a()) != null;
	}

	private void e(apv apv) {
		apv apv3 = (apv)this.c.putIfAbsent(apv.a(), apv);
		if (apv3 != null) {
			throw new IllegalArgumentException("Modifier is already applied on this attribute!");
		} else {
			this.a(apv.c()).add(apv);
			this.d();
		}
	}

	public void b(apv apv) {
		this.e(apv);
	}

	public void c(apv apv) {
		this.e(apv);
		this.d.add(apv);
	}

	protected void d() {
		this.f = true;
		this.h.accept(this);
	}

	public void d(apv apv) {
		this.a(apv.c()).remove(apv);
		this.c.remove(apv.a());
		this.d.remove(apv);
		this.d();
	}

	public void b(UUID uUID) {
		apv apv3 = this.a(uUID);
		if (apv3 != null) {
			this.d(apv3);
		}
	}

	public boolean c(UUID uUID) {
		apv apv3 = this.a(uUID);
		if (apv3 != null && this.d.contains(apv3)) {
			this.d(apv3);
			return true;
		} else {
			return false;
		}
	}

	public double f() {
		if (this.f) {
			this.g = this.h();
			this.f = false;
		}

		return this.g;
	}

	private double h() {
		double double2 = this.b();

		for (apv apv5 : this.b(apv.a.ADDITION)) {
			double2 += apv5.d();
		}

		double double4 = double2;

		for (apv apv7 : this.b(apv.a.MULTIPLY_BASE)) {
			double4 += double2 * apv7.d();
		}

		for (apv apv7 : this.b(apv.a.MULTIPLY_TOTAL)) {
			double4 *= 1.0 + apv7.d();
		}

		return this.a.a(double4);
	}

	private Collection<apv> b(apv.a a) {
		return (Collection<apv>)this.b.getOrDefault(a, Collections.emptySet());
	}

	public void a(apt apt) {
		this.e = apt.e;
		this.c.clear();
		this.c.putAll(apt.c);
		this.d.clear();
		this.d.addAll(apt.d);
		this.b.clear();
		apt.b.forEach((a, set) -> this.a(a).addAll(set));
		this.d();
	}

	public le g() {
		le le2 = new le();
		le2.a("Name", gl.aP.b(this.a).toString());
		le2.a("Base", this.e);
		if (!this.d.isEmpty()) {
			lk lk3 = new lk();

			for (apv apv5 : this.d) {
				lk3.add(apv5.e());
			}

			le2.a("Modifiers", lk3);
		}

		return le2;
	}

	public void a(le le) {
		this.e = le.k("Base");
		if (le.c("Modifiers", 9)) {
			lk lk3 = le.d("Modifiers", 10);

			for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
				apv apv5 = apv.a(lk3.a(integer4));
				if (apv5 != null) {
					this.c.put(apv5.a(), apv5);
					this.a(apv5.c()).add(apv5);
					this.d.add(apv5);
				}
			}
		}

		this.d();
	}
}
