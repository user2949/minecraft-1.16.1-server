import com.google.common.collect.Lists;
import java.util.List;

public class bmp extends bmn {
	private static final bmr a = bmr.a(bkk.po);

	public bmp(uh uh) {
		super(uh);
	}

	public boolean a(bgu bgu, bqb bqb) {
		boolean boolean4 = false;
		boolean boolean5 = false;

		for (int integer6 = 0; integer6 < bgu.ab_(); integer6++) {
			bki bki7 = bgu.a(integer6);
			if (!bki7.a()) {
				if (bki7.b() instanceof bjf) {
					boolean4 = true;
				} else {
					if (!a.a(bki7)) {
						return false;
					}

					if (boolean5) {
						return false;
					}

					boolean5 = true;
				}
			}
		}

		return boolean5 && boolean4;
	}

	public bki a(bgu bgu) {
		List<Integer> list3 = Lists.<Integer>newArrayList();
		bki bki4 = null;

		for (int integer5 = 0; integer5 < bgu.ab_(); integer5++) {
			bki bki6 = bgu.a(integer5);
			bke bke7 = bki6.b();
			if (bke7 instanceof bjf) {
				list3.add(((bjf)bke7).d().g());
			} else if (a.a(bki6)) {
				bki4 = bki6.i();
				bki4.e(1);
			}
		}

		if (bki4 != null && !list3.isEmpty()) {
			bki4.a("Explosion").b("FadeColors", list3);
			return bki4;
		} else {
			return bki.b;
		}
	}

	@Override
	public bmw<?> ai_() {
		return bmw.i;
	}
}
