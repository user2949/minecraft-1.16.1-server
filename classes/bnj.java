public class bnj extends bmn {
	public bnj(uh uh) {
		super(uh);
	}

	public boolean a(bgu bgu, bqb bqb) {
		boolean boolean4 = false;
		boolean boolean5 = false;
		boolean boolean6 = false;
		boolean boolean7 = false;

		for (int integer8 = 0; integer8 < bgu.ab_(); integer8++) {
			bki bki9 = bgu.a(integer8);
			if (!bki9.a()) {
				if (bki9.b() == bvs.bC.h() && !boolean6) {
					boolean6 = true;
				} else if (bki9.b() == bvs.bD.h() && !boolean5) {
					boolean5 = true;
				} else if (bki9.b().a(ada.H) && !boolean4) {
					boolean4 = true;
				} else {
					if (bki9.b() != bkk.kC || boolean7) {
						return false;
					}

					boolean7 = true;
				}
			}
		}

		return boolean4 && boolean6 && boolean5 && boolean7;
	}

	public bki a(bgu bgu) {
		bki bki3 = bki.b;

		for (int integer4 = 0; integer4 < bgu.ab_(); integer4++) {
			bki bki5 = bgu.a(integer4);
			if (!bki5.a() && bki5.b().a(ada.H)) {
				bki3 = bki5;
				break;
			}
		}

		bki bki4 = new bki(bkk.qQ, 1);
		if (bki3.b() instanceof bim && ((bim)bki3.b()).e() instanceof bxx) {
			bxx bxx5 = (bxx)((bim)bki3.b()).e();
			aoe aoe6 = bxx5.c();
			bll.a(bki4, aoe6, bxx5.d());
		}

		return bki4;
	}

	@Override
	public bmw<?> ai_() {
		return bmw.n;
	}
}
