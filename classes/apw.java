import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class apw {
	private final Map<aps, apt> a;

	public apw(Map<aps, apt> map) {
		this.a = ImmutableMap.copyOf(map);
	}

	private apt d(aps aps) {
		apt apt3 = (apt)this.a.get(aps);
		if (apt3 == null) {
			throw new IllegalArgumentException("Can't find attribute " + gl.aP.b(aps));
		} else {
			return apt3;
		}
	}

	public double a(aps aps) {
		return this.d(aps).f();
	}

	public double b(aps aps) {
		return this.d(aps).b();
	}

	public double a(aps aps, UUID uUID) {
		apv apv4 = this.d(aps).a(uUID);
		if (apv4 == null) {
			throw new IllegalArgumentException("Can't find modifier " + uUID + " on attribute " + gl.aP.b(aps));
		} else {
			return apv4.d();
		}
	}

	@Nullable
	public apt a(Consumer<apt> consumer, aps aps) {
		apt apt4 = (apt)this.a.get(aps);
		if (apt4 == null) {
			return null;
		} else {
			apt apt5 = new apt(aps, consumer);
			apt5.a(apt4);
			return apt5;
		}
	}

	public static apw.a a() {
		return new apw.a();
	}

	public boolean c(aps aps) {
		return this.a.containsKey(aps);
	}

	public boolean b(aps aps, UUID uUID) {
		apt apt4 = (apt)this.a.get(aps);
		return apt4 != null && apt4.a(uUID) != null;
	}

	public static class a {
		private final Map<aps, apt> a = Maps.<aps, apt>newHashMap();
		private boolean b;

		private apt b(aps aps) {
			apt apt3 = new apt(aps, apt -> {
				if (this.b) {
					throw new UnsupportedOperationException("Tried to change value for default attribute instance: " + gl.aP.b(aps));
				}
			});
			this.a.put(aps, apt3);
			return apt3;
		}

		public apw.a a(aps aps) {
			this.b(aps);
			return this;
		}

		public apw.a a(aps aps, double double2) {
			apt apt5 = this.b(aps);
			apt5.a(double2);
			return this;
		}

		public apw a() {
			this.b = true;
			return new apw(this.a);
		}
	}
}
