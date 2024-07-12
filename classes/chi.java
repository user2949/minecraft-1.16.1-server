import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.BitSet;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class chi extends chr {
	private final chj a;

	public chi(chj chj) {
		super(chj.g(), cht.a);
		this.a = chj;
	}

	@Nullable
	@Override
	public cdl c(fu fu) {
		return this.a.c(fu);
	}

	@Nullable
	@Override
	public cfj d_(fu fu) {
		return this.a.d_(fu);
	}

	@Override
	public cxa b(fu fu) {
		return this.a.b(fu);
	}

	@Override
	public int H() {
		return this.a.H();
	}

	@Nullable
	@Override
	public cfj a(fu fu, cfj cfj, boolean boolean3) {
		return null;
	}

	@Override
	public void a(fu fu, cdl cdl) {
	}

	@Override
	public void a(aom aom) {
	}

	@Override
	public void a(chc chc) {
	}

	@Override
	public chk[] d() {
		return this.a.d();
	}

	@Nullable
	@Override
	public cwr e() {
		return this.a.e();
	}

	@Override
	public void a(cio.a a, long[] arr) {
	}

	private cio.a c(cio.a a) {
		if (a == cio.a.WORLD_SURFACE_WG) {
			return cio.a.WORLD_SURFACE;
		} else {
			return a == cio.a.OCEAN_FLOOR_WG ? cio.a.OCEAN_FLOOR : a;
		}
	}

	@Override
	public int a(cio.a a, int integer2, int integer3) {
		return this.a.a(this.c(a), integer2, integer3);
	}

	@Override
	public bph g() {
		return this.a.g();
	}

	@Override
	public void a(long long1) {
	}

	@Nullable
	@Override
	public ctz<?> a(cml<?> cml) {
		return this.a.a(cml);
	}

	@Override
	public void a(cml<?> cml, ctz<?> ctz) {
	}

	@Override
	public Map<cml<?>, ctz<?>> h() {
		return this.a.h();
	}

	@Override
	public void a(Map<cml<?>, ctz<?>> map) {
	}

	@Override
	public LongSet b(cml<?> cml) {
		return this.a.b(cml);
	}

	@Override
	public void a(cml<?> cml, long long2) {
	}

	@Override
	public Map<cml<?>, LongSet> v() {
		return this.a.v();
	}

	@Override
	public void b(Map<cml<?>, LongSet> map) {
	}

	@Override
	public cgz i() {
		return this.a.i();
	}

	@Override
	public void a(boolean boolean1) {
	}

	@Override
	public boolean j() {
		return false;
	}

	@Override
	public chc k() {
		return this.a.k();
	}

	@Override
	public void d(fu fu) {
	}

	@Override
	public void e(fu fu) {
	}

	@Override
	public void a(le le) {
	}

	@Nullable
	@Override
	public le f(fu fu) {
		return this.a.f(fu);
	}

	@Nullable
	@Override
	public le i(fu fu) {
		return this.a.i(fu);
	}

	@Override
	public void a(cgz cgz) {
	}

	@Override
	public Stream<fu> m() {
		return this.a.m();
	}

	@Override
	public chs<bvr> n() {
		return new chs<>(bvr -> bvr.n().g(), this.g());
	}

	@Override
	public chs<cwz> o() {
		return new chs<>(cwz -> cwz == cxb.a, this.g());
	}

	@Override
	public BitSet a(cin.a a) {
		throw (UnsupportedOperationException)v.c(new UnsupportedOperationException("Meaningless in this context"));
	}

	@Override
	public BitSet b(cin.a a) {
		throw (UnsupportedOperationException)v.c(new UnsupportedOperationException("Meaningless in this context"));
	}

	public chj u() {
		return this.a;
	}

	@Override
	public boolean r() {
		return this.a.r();
	}

	@Override
	public void b(boolean boolean1) {
		this.a.b(boolean1);
	}
}
