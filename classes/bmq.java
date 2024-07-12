import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class bmq extends bmn {
	private static final bmr a = bmr.a(bkk.oR, bkk.kN, bkk.nt, bkk.pd, bkk.pe, bkk.ph, bkk.pf, bkk.pi, bkk.pg);
	private static final bmr b = bmr.a(bkk.kj);
	private static final bmr c = bmr.a(bkk.mk);
	private static final Map<bke, bjt.a> d = v.a(Maps.<bke, bjt.a>newHashMap(), hashMap -> {
		hashMap.put(bkk.oR, bjt.a.LARGE_BALL);
		hashMap.put(bkk.kN, bjt.a.BURST);
		hashMap.put(bkk.nt, bjt.a.STAR);
		hashMap.put(bkk.pd, bjt.a.CREEPER);
		hashMap.put(bkk.pe, bjt.a.CREEPER);
		hashMap.put(bkk.ph, bjt.a.CREEPER);
		hashMap.put(bkk.pf, bjt.a.CREEPER);
		hashMap.put(bkk.pi, bjt.a.CREEPER);
		hashMap.put(bkk.pg, bjt.a.CREEPER);
	});
	private static final bmr e = bmr.a(bkk.kO);

	public bmq(uh uh) {
		super(uh);
	}

	public boolean a(bgu bgu, bqb bqb) {
		boolean boolean4 = false;
		boolean boolean5 = false;
		boolean boolean6 = false;
		boolean boolean7 = false;
		boolean boolean8 = false;

		for (int integer9 = 0; integer9 < bgu.ab_(); integer9++) {
			bki bki10 = bgu.a(integer9);
			if (!bki10.a()) {
				if (a.a(bki10)) {
					if (boolean6) {
						return false;
					}

					boolean6 = true;
				} else if (c.a(bki10)) {
					if (boolean8) {
						return false;
					}

					boolean8 = true;
				} else if (b.a(bki10)) {
					if (boolean7) {
						return false;
					}

					boolean7 = true;
				} else if (e.a(bki10)) {
					if (boolean4) {
						return false;
					}

					boolean4 = true;
				} else {
					if (!(bki10.b() instanceof bjf)) {
						return false;
					}

					boolean5 = true;
				}
			}
		}

		return boolean4 && boolean5;
	}

	public bki a(bgu bgu) {
		bki bki3 = new bki(bkk.po);
		le le4 = bki3.a("Explosion");
		bjt.a a5 = bjt.a.SMALL_BALL;
		List<Integer> list6 = Lists.<Integer>newArrayList();

		for (int integer7 = 0; integer7 < bgu.ab_(); integer7++) {
			bki bki8 = bgu.a(integer7);
			if (!bki8.a()) {
				if (a.a(bki8)) {
					a5 = (bjt.a)d.get(bki8.b());
				} else if (c.a(bki8)) {
					le4.a("Flicker", true);
				} else if (b.a(bki8)) {
					le4.a("Trail", true);
				} else if (bki8.b() instanceof bjf) {
					list6.add(((bjf)bki8.b()).d().g());
				}
			}
		}

		le4.b("Colors", list6);
		le4.a("Type", (byte)a5.a());
		return bki3;
	}

	@Override
	public bki c() {
		return new bki(bkk.po);
	}

	@Override
	public bmw<?> ai_() {
		return bmw.h;
	}
}
