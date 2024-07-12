import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class bmy extends bmn {
	public bmy(uh uh) {
		super(uh);
	}

	public boolean a(bgu bgu, bqb bqb) {
		List<bki> list4 = Lists.<bki>newArrayList();

		for (int integer5 = 0; integer5 < bgu.ab_(); integer5++) {
			bki bki6 = bgu.a(integer5);
			if (!bki6.a()) {
				list4.add(bki6);
				if (list4.size() > 1) {
					bki bki7 = (bki)list4.get(0);
					if (bki6.b() != bki7.b() || bki7.E() != 1 || bki6.E() != 1 || !bki7.b().k()) {
						return false;
					}
				}
			}
		}

		return list4.size() == 2;
	}

	public bki a(bgu bgu) {
		List<bki> list3 = Lists.<bki>newArrayList();

		for (int integer4 = 0; integer4 < bgu.ab_(); integer4++) {
			bki bki5 = bgu.a(integer4);
			if (!bki5.a()) {
				list3.add(bki5);
				if (list3.size() > 1) {
					bki bki6 = (bki)list3.get(0);
					if (bki5.b() != bki6.b() || bki6.E() != 1 || bki5.E() != 1 || !bki6.b().k()) {
						return bki.b;
					}
				}
			}
		}

		if (list3.size() == 2) {
			bki bki4 = (bki)list3.get(0);
			bki bki5 = (bki)list3.get(1);
			if (bki4.b() == bki5.b() && bki4.E() == 1 && bki5.E() == 1 && bki4.b().k()) {
				bke bke6 = bki4.b();
				int integer7 = bke6.j() - bki4.g();
				int integer8 = bke6.j() - bki5.g();
				int integer9 = integer7 + integer8 + bke6.j() * 5 / 100;
				int integer10 = bke6.j() - integer9;
				if (integer10 < 0) {
					integer10 = 0;
				}

				bki bki11 = new bki(bki4.b());
				bki11.b(integer10);
				Map<bnw, Integer> map12 = Maps.<bnw, Integer>newHashMap();
				Map<bnw, Integer> map13 = bny.a(bki4);
				Map<bnw, Integer> map14 = bny.a(bki5);
				gl.ak.e().filter(bnw::c).forEach(bnw -> {
					int integer5 = Math.max((Integer)map13.getOrDefault(bnw, 0), (Integer)map14.getOrDefault(bnw, 0));
					if (integer5 > 0) {
						map12.put(bnw, integer5);
					}
				});
				if (!map12.isEmpty()) {
					bny.a(map12, bki11);
				}

				return bki11;
			}
		}

		return bki.b;
	}

	@Override
	public bmw<?> ai_() {
		return bmw.o;
	}
}
