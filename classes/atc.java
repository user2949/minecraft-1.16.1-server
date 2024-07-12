import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;

public class atc extends atd {
	private static final List<bke> b = ImmutableList.of(bkk.kV, bkk.qf);

	@Override
	protected void a(zd zd, bdp bdp) {
		Optional<gc> optional4 = bdp.cI().c(awp.c);
		if (optional4.isPresent()) {
			gc gc5 = (gc)optional4.get();
			cfj cfj6 = zd.d_(gc5.b());
			if (cfj6.a(bvs.na)) {
				this.a(bdp);
				this.a(zd, bdp, gc5, cfj6);
			}
		}
	}

	private void a(zd zd, bdp bdp, gc gc, cfj cfj) {
		if ((Integer)cfj.c(bwn.a) == 8) {
			cfj = bwn.d(cfj, zd, gc.b());
		}

		int integer6 = 20;
		int integer7 = 10;
		int[] arr8 = new int[b.size()];
		anm anm9 = bdp.eU();
		int integer10 = anm9.ab_();

		for (int integer11 = integer10 - 1; integer11 >= 0 && integer6 > 0; integer11--) {
			bki bki12 = anm9.a(integer11);
			int integer13 = b.indexOf(bki12.b());
			if (integer13 != -1) {
				int integer14 = bki12.E();
				int integer15 = arr8[integer13] + integer14;
				arr8[integer13] = integer15;
				int integer16 = Math.min(Math.min(integer15 - 10, integer6), integer14);
				if (integer16 > 0) {
					integer6 -= integer16;

					for (int integer17 = 0; integer17 < integer16; integer17++) {
						cfj = bwn.a(cfj, zd, bki12, gc.b());
						if ((Integer)cfj.c(bwn.a) == 7) {
							return;
						}
					}
				}
			}
		}
	}

	private void a(bdp bdp) {
		anm anm3 = bdp.eU();
		if (anm3.a(bkk.kX) <= 36) {
			int integer4 = anm3.a(bkk.kW);
			int integer5 = 3;
			int integer6 = 3;
			int integer7 = Math.min(3, integer4 / 3);
			if (integer7 != 0) {
				int integer8 = integer7 * 3;
				anm3.a(bkk.kW, integer8);
				bki bki9 = anm3.a(new bki(bkk.kX, integer7));
				if (!bki9.a()) {
					bdp.a(bki9, 0.5F);
				}
			}
		}
	}
}
