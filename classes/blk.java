import java.util.Map;
import javax.annotation.Nullable;

public class blk extends bim {
	protected final bvr a;

	public blk(bvr bvr1, bvr bvr2, bke.a a) {
		super(bvr1, a);
		this.a = bvr2;
	}

	@Nullable
	@Override
	protected cfj c(bin bin) {
		cfj cfj3 = this.a.a(bin);
		cfj cfj4 = null;
		bqd bqd5 = bin.o();
		fu fu6 = bin.a();

		for (fz fz10 : bin.e()) {
			if (fz10 != fz.UP) {
				cfj cfj11 = fz10 == fz.DOWN ? this.e().a(bin) : cfj3;
				if (cfj11 != null && cfj11.a(bqd5, fu6)) {
					cfj4 = cfj11;
					break;
				}
			}
		}

		return cfj4 != null && bqd5.a(cfj4, fu6, der.a()) ? cfj4 : null;
	}

	@Override
	public void a(Map<bvr, bke> map, bke bke) {
		super.a(map, bke);
		map.put(this.a, bke);
	}
}
