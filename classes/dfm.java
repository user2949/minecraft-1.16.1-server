import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class dfm {
	private final Map<String, dfj> a = Maps.<String, dfj>newHashMap();
	private final Map<dfp, List<dfj>> b = Maps.<dfp, List<dfj>>newHashMap();
	private final Map<String, Map<dfj, dfl>> c = Maps.<String, Map<dfj, dfl>>newHashMap();
	private final dfj[] d = new dfj[19];
	private final Map<String, dfk> e = Maps.<String, dfk>newHashMap();
	private final Map<String, dfk> f = Maps.<String, dfk>newHashMap();
	private static String[] g;

	public dfj c(String string) {
		return (dfj)this.a.get(string);
	}

	@Nullable
	public dfj d(@Nullable String string) {
		return (dfj)this.a.get(string);
	}

	public dfj a(String string, dfp dfp, mr mr, dfp.a a) {
		if (string.length() > 16) {
			throw new IllegalArgumentException("The objective name '" + string + "' is too long!");
		} else if (this.a.containsKey(string)) {
			throw new IllegalArgumentException("An objective with the name '" + string + "' already exists!");
		} else {
			dfj dfj6 = new dfj(this, string, dfp, mr, a);
			((List)this.b.computeIfAbsent(dfp, dfpx -> Lists.newArrayList())).add(dfj6);
			this.a.put(string, dfj6);
			this.a(dfj6);
			return dfj6;
		}
	}

	public final void a(dfp dfp, String string, Consumer<dfl> consumer) {
		((List)this.b.getOrDefault(dfp, Collections.emptyList())).forEach(dfj -> consumer.accept(this.c(string, dfj)));
	}

	public boolean b(String string, dfj dfj) {
		Map<dfj, dfl> map4 = (Map<dfj, dfl>)this.c.get(string);
		if (map4 == null) {
			return false;
		} else {
			dfl dfl5 = (dfl)map4.get(dfj);
			return dfl5 != null;
		}
	}

	public dfl c(String string, dfj dfj) {
		if (string.length() > 40) {
			throw new IllegalArgumentException("The player name '" + string + "' is too long!");
		} else {
			Map<dfj, dfl> map4 = (Map<dfj, dfl>)this.c.computeIfAbsent(string, stringx -> Maps.newHashMap());
			return (dfl)map4.computeIfAbsent(dfj, dfjx -> {
				dfl dfl4 = new dfl(this, dfjx, string);
				dfl4.c(0);
				return dfl4;
			});
		}
	}

	public Collection<dfl> i(dfj dfj) {
		List<dfl> list3 = Lists.<dfl>newArrayList();

		for (Map<dfj, dfl> map5 : this.c.values()) {
			dfl dfl6 = (dfl)map5.get(dfj);
			if (dfl6 != null) {
				list3.add(dfl6);
			}
		}

		list3.sort(dfl.a);
		return list3;
	}

	public Collection<dfj> c() {
		return this.a.values();
	}

	public Collection<String> d() {
		return this.a.keySet();
	}

	public Collection<String> e() {
		return Lists.<String>newArrayList(this.c.keySet());
	}

	public void d(String string, @Nullable dfj dfj) {
		if (dfj == null) {
			Map<dfj, dfl> map4 = (Map<dfj, dfl>)this.c.remove(string);
			if (map4 != null) {
				this.a(string);
			}
		} else {
			Map<dfj, dfl> map4 = (Map<dfj, dfl>)this.c.get(string);
			if (map4 != null) {
				dfl dfl5 = (dfl)map4.remove(dfj);
				if (map4.size() < 1) {
					Map<dfj, dfl> map6 = (Map<dfj, dfl>)this.c.remove(string);
					if (map6 != null) {
						this.a(string);
					}
				} else if (dfl5 != null) {
					this.a(string, dfj);
				}
			}
		}
	}

	public Map<dfj, dfl> e(String string) {
		Map<dfj, dfl> map3 = (Map<dfj, dfl>)this.c.get(string);
		if (map3 == null) {
			map3 = Maps.<dfj, dfl>newHashMap();
		}

		return map3;
	}

	public void j(dfj dfj) {
		this.a.remove(dfj.b());

		for (int integer3 = 0; integer3 < 19; integer3++) {
			if (this.a(integer3) == dfj) {
				this.a(integer3, null);
			}
		}

		List<dfj> list3 = (List<dfj>)this.b.get(dfj.c());
		if (list3 != null) {
			list3.remove(dfj);
		}

		for (Map<dfj, dfl> map5 : this.c.values()) {
			map5.remove(dfj);
		}

		this.c(dfj);
	}

	public void a(int integer, @Nullable dfj dfj) {
		this.d[integer] = dfj;
	}

	@Nullable
	public dfj a(int integer) {
		return this.d[integer];
	}

	public dfk f(String string) {
		return (dfk)this.e.get(string);
	}

	public dfk g(String string) {
		if (string.length() > 16) {
			throw new IllegalArgumentException("The team name '" + string + "' is too long!");
		} else {
			dfk dfk3 = this.f(string);
			if (dfk3 != null) {
				throw new IllegalArgumentException("A team with the name '" + string + "' already exists!");
			} else {
				dfk3 = new dfk(this, string);
				this.e.put(string, dfk3);
				this.a(dfk3);
				return dfk3;
			}
		}
	}

	public void d(dfk dfk) {
		this.e.remove(dfk.b());

		for (String string4 : dfk.g()) {
			this.f.remove(string4);
		}

		this.c(dfk);
	}

	public boolean a(String string, dfk dfk) {
		if (string.length() > 40) {
			throw new IllegalArgumentException("The player name '" + string + "' is too long!");
		} else {
			if (this.i(string) != null) {
				this.h(string);
			}

			this.f.put(string, dfk);
			return dfk.g().add(string);
		}
	}

	public boolean h(String string) {
		dfk dfk3 = this.i(string);
		if (dfk3 != null) {
			this.b(string, dfk3);
			return true;
		} else {
			return false;
		}
	}

	public void b(String string, dfk dfk) {
		if (this.i(string) != dfk) {
			throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + dfk.b() + "'.");
		} else {
			this.f.remove(string);
			dfk.g().remove(string);
		}
	}

	public Collection<String> f() {
		return this.e.keySet();
	}

	public Collection<dfk> g() {
		return this.e.values();
	}

	@Nullable
	public dfk i(String string) {
		return (dfk)this.f.get(string);
	}

	public void a(dfj dfj) {
	}

	public void b(dfj dfj) {
	}

	public void c(dfj dfj) {
	}

	public void a(dfl dfl) {
	}

	public void a(String string) {
	}

	public void a(String string, dfj dfj) {
	}

	public void a(dfk dfk) {
	}

	public void b(dfk dfk) {
	}

	public void c(dfk dfk) {
	}

	public static String b(int integer) {
		switch (integer) {
			case 0:
				return "list";
			case 1:
				return "sidebar";
			case 2:
				return "belowName";
			default:
				if (integer >= 3 && integer <= 18) {
					i i2 = i.a(integer - 3);
					if (i2 != null && i2 != i.RESET) {
						return "sidebar.team." + i2.f();
					}
				}

				return null;
		}
	}

	public static int j(String string) {
		if ("list".equalsIgnoreCase(string)) {
			return 0;
		} else if ("sidebar".equalsIgnoreCase(string)) {
			return 1;
		} else if ("belowName".equalsIgnoreCase(string)) {
			return 2;
		} else {
			if (string.startsWith("sidebar.team.")) {
				String string2 = string.substring("sidebar.team.".length());
				i i3 = i.b(string2);
				if (i3 != null && i3.b() >= 0) {
					return i3.b() + 3;
				}
			}

			return -1;
		}
	}

	public static String[] h() {
		if (g == null) {
			g = new String[19];

			for (int integer1 = 0; integer1 < 19; integer1++) {
				g[integer1] = b(integer1);
			}
		}

		return g;
	}

	public void a(aom aom) {
		if (aom != null && !(aom instanceof bec) && !aom.aU()) {
			String string3 = aom.bS();
			this.d(string3, null);
			this.h(string3);
		}
	}

	protected lk i() {
		lk lk2 = new lk();
		this.c.values().stream().map(Map::values).forEach(collection -> collection.stream().filter(dfl -> dfl.d() != null).forEach(dfl -> {
				le le3 = new le();
				le3.a("Name", dfl.e());
				le3.a("Objective", dfl.d().b());
				le3.b("Score", dfl.b());
				le3.a("Locked", dfl.g());
				lk2.add(le3);
			}));
		return lk2;
	}

	protected void a(lk lk) {
		for (int integer3 = 0; integer3 < lk.size(); integer3++) {
			le le4 = lk.a(integer3);
			dfj dfj5 = this.c(le4.l("Objective"));
			String string6 = le4.l("Name");
			if (string6.length() > 40) {
				string6 = string6.substring(0, 40);
			}

			dfl dfl7 = this.c(string6, dfj5);
			dfl7.c(le4.h("Score"));
			if (le4.e("Locked")) {
				dfl7.a(le4.q("Locked"));
			}
		}
	}
}
