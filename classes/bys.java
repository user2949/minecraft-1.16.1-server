import com.google.common.collect.Maps;
import java.util.Map;

public class bys extends bvr {
	private final bvr a;
	private static final Map<bvr, bvr> b = Maps.<bvr, bvr>newIdentityHashMap();

	public bys(bvr bvr, cfi.c c) {
		super(c);
		this.a = bvr;
		b.put(bvr, this);
	}

	public bvr c() {
		return this.a;
	}

	public static boolean h(cfj cfj) {
		return b.containsKey(cfj.b());
	}

	private void a(bqb bqb, fu fu) {
		bci bci4 = aoq.at.a(bqb);
		bci4.b((double)fu.u() + 0.5, (double)fu.v(), (double)fu.w() + 0.5, 0.0F, 0.0F);
		bqb.c(bci4);
		bci4.G();
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, bki bki) {
		super.a(cfj, bqb, fu, bki);
		if (!bqb.v && bqb.S().b(bpx.f) && bny.a(boa.u, bki) == 0) {
			this.a(bqb, fu);
		}
	}

	@Override
	public void a(bqb bqb, fu fu, bpt bpt) {
		if (!bqb.v) {
			this.a(bqb, fu);
		}
	}

	public static cfj c(bvr bvr) {
		return ((bvr)b.get(bvr)).n();
	}
}
