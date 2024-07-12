import java.util.Random;
import javax.annotation.Nullable;

public class bwq extends bvr {
	private final bvr a;

	public bwq(bvr bvr, cfi.c c) {
		super(c);
		this.a = bvr;
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if (!this.a(zd, fu)) {
			zd.a(fu, this.a.n(), 2);
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (!this.a(bqc, fu5)) {
			bqc.G().a(fu5, this, 60 + bqc.v_().nextInt(40));
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	protected boolean a(bpg bpg, fu fu) {
		for (fz fz7 : fz.values()) {
			cxa cxa8 = bpg.b(fu.a(fz7));
			if (cxa8.a(acz.a)) {
				return true;
			}
		}

		return false;
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		if (!this.a(bin.o(), bin.a())) {
			bin.o().G().a(bin.a(), this, 60 + bin.o().v_().nextInt(40));
		}

		return this.n();
	}
}
