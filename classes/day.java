import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class day extends abe {
	private static final Logger a = LogManager.getLogger();
	private static final Gson b = daq.a().create();
	private Map<uh, ddm> c = ImmutableMap.of();

	public day() {
		super(b, "predicates");
	}

	@Nullable
	public ddm a(uh uh) {
		return (ddm)this.c.get(uh);
	}

	protected void a(Map<uh, JsonElement> map, abc abc, ami ami) {
		Builder<uh, ddm> builder5 = ImmutableMap.builder();
		map.forEach((uh, jsonElement) -> {
			try {
				if (jsonElement.isJsonArray()) {
					ddm[] arr4 = b.fromJson(jsonElement, ddm[].class);
					builder5.put(uh, new day.a(arr4));
				} else {
					ddm ddm4 = b.fromJson(jsonElement, ddm.class);
					builder5.put(uh, ddm4);
				}
			} catch (Exception var4x) {
				a.error("Couldn't parse loot table {}", uh, var4x);
			}
		});
		Map<uh, ddm> map6 = builder5.build();
		dbe dbe7 = new dbe(dcz.k, map6::get, uh -> null);
		map6.forEach((uh, ddm) -> ddm.a(dbe7.b("{" + uh + "}", uh)));
		dbe7.a().forEach((string1, string2) -> a.warn("Found validation problem in " + string1 + ": " + string2));
		this.c = map6;
	}

	public Set<uh> a() {
		return Collections.unmodifiableSet(this.c.keySet());
	}

	static class a implements ddm {
		private final ddm[] a;
		private final Predicate<dat> b;

		private a(ddm[] arr) {
			this.a = arr;
			this.b = ddo.a(arr);
		}

		public final boolean test(dat dat) {
			return this.b.test(dat);
		}

		@Override
		public void a(dbe dbe) {
			ddm.super.a(dbe);

			for (int integer3 = 0; integer3 < this.a.length; integer3++) {
				this.a[integer3].a(dbe.b(".term[" + integer3 + "]"));
			}
		}

		@Override
		public ddn b() {
			throw new UnsupportedOperationException();
		}
	}
}
