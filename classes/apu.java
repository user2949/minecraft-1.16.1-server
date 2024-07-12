import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class apu {
	private static final Logger a = LogManager.getLogger();
	private final Map<aps, apt> b = Maps.<aps, apt>newHashMap();
	private final Set<apt> c = Sets.<apt>newHashSet();
	private final apw d;

	public apu(apw apw) {
		this.d = apw;
	}

	private void a(apt apt) {
		if (apt.a().b()) {
			this.c.add(apt);
		}
	}

	public Set<apt> a() {
		return this.c;
	}

	public Collection<apt> b() {
		return (Collection<apt>)this.b.values().stream().filter(apt -> apt.a().b()).collect(Collectors.toList());
	}

	@Nullable
	public apt a(aps aps) {
		return (apt)this.b.computeIfAbsent(aps, apsx -> this.d.a(this::a, apsx));
	}

	public boolean b(aps aps) {
		return this.b.get(aps) != null || this.d.c(aps);
	}

	public boolean a(aps aps, UUID uUID) {
		apt apt4 = (apt)this.b.get(aps);
		return apt4 != null ? apt4.a(uUID) != null : this.d.b(aps, uUID);
	}

	public double c(aps aps) {
		apt apt3 = (apt)this.b.get(aps);
		return apt3 != null ? apt3.f() : this.d.a(aps);
	}

	public double d(aps aps) {
		apt apt3 = (apt)this.b.get(aps);
		return apt3 != null ? apt3.b() : this.d.b(aps);
	}

	public double b(aps aps, UUID uUID) {
		apt apt4 = (apt)this.b.get(aps);
		return apt4 != null ? apt4.a(uUID).d() : this.d.a(aps, uUID);
	}

	public void a(Multimap<aps, apv> multimap) {
		multimap.asMap().forEach((aps, collection) -> {
			apt apt4 = (apt)this.b.get(aps);
			if (apt4 != null) {
				collection.forEach(apt4::d);
			}
		});
	}

	public void b(Multimap<aps, apv> multimap) {
		multimap.forEach((aps, apv) -> {
			apt apt4 = this.a(aps);
			if (apt4 != null) {
				apt4.d(apv);
				apt4.b(apv);
			}
		});
	}

	public lk c() {
		lk lk2 = new lk();

		for (apt apt4 : this.b.values()) {
			lk2.add(apt4.g());
		}

		return lk2;
	}

	public void a(lk lk) {
		for (int integer3 = 0; integer3 < lk.size(); integer3++) {
			le le4 = lk.a(integer3);
			String string5 = le4.l("Name");
			v.a(gl.aP.b(uh.a(string5)), aps -> {
				apt apt4 = this.a(aps);
				if (apt4 != null) {
					apt4.a(le4);
				}
			}, () -> a.warn("Ignoring unknown attribute '{}'", string5));
		}
	}
}
