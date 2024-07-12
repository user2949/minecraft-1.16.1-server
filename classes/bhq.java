import java.util.Collections;
import javax.annotation.Nullable;

public interface bhq {
	void a(@Nullable bmu<?> bmu);

	@Nullable
	bmu<?> am_();

	default void b(bec bec) {
		bmu<?> bmu3 = this.am_();
		if (bmu3 != null && !bmu3.ah_()) {
			bec.a(Collections.singleton(bmu3));
			this.a(null);
		}
	}

	default boolean a(bqb bqb, ze ze, bmu<?> bmu) {
		if (!bmu.ah_() && bqb.S().b(bpx.u) && !ze.B().b(bmu)) {
			return false;
		} else {
			this.a(bmu);
			return true;
		}
	}
}
