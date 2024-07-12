import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;

public interface cgy extends bpg, chf {
	@Nullable
	cfj a(fu fu, cfj cfj, boolean boolean3);

	void a(fu fu, cdl cdl);

	void a(aom aom);

	@Nullable
	default chk a() {
		chk[] arr2 = this.d();

		for (int integer3 = arr2.length - 1; integer3 >= 0; integer3--) {
			chk chk4 = arr2[integer3];
			if (!chk.a(chk4)) {
				return chk4;
			}
		}

		return null;
	}

	default int b() {
		chk chk2 = this.a();
		return chk2 == null ? 0 : chk2.g();
	}

	Set<fu> c();

	chk[] d();

	Collection<Entry<cio.a, cio>> f();

	void a(cio.a a, long[] arr);

	cio a(cio.a a);

	int a(cio.a a, int integer2, int integer3);

	bph g();

	void a(long long1);

	Map<cml<?>, ctz<?>> h();

	void a(Map<cml<?>, ctz<?>> map);

	default boolean a(int integer1, int integer2) {
		if (integer1 < 0) {
			integer1 = 0;
		}

		if (integer2 >= 256) {
			integer2 = 255;
		}

		for (int integer4 = integer1; integer4 <= integer2; integer4 += 16) {
			if (!chk.a(this.d()[integer4 >> 4])) {
				return false;
			}
		}

		return true;
	}

	@Nullable
	cgz i();

	void a(boolean boolean1);

	boolean j();

	chc k();

	void d(fu fu);

	default void e(fu fu) {
		LogManager.getLogger().warn("Trying to mark a block for PostProcessing @ {}, but this operation is not supported.", fu);
	}

	ShortList[] l();

	default void a(short short1, int integer) {
		a(this.l(), integer).add(short1);
	}

	default void a(le le) {
		LogManager.getLogger().warn("Trying to set a BlockEntity, but this operation is not supported.");
	}

	@Nullable
	le f(fu fu);

	@Nullable
	le i(fu fu);

	Stream<fu> m();

	bqr<bvr> n();

	bqr<cwz> o();

	cht p();

	void b(long long1);

	long q();

	static ShortList a(ShortList[] arr, int integer) {
		if (arr[integer] == null) {
			arr[integer] = new ShortArrayList();
		}

		return arr[integer];
	}

	boolean r();

	void b(boolean boolean1);
}
