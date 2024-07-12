import com.google.common.collect.Lists;
import java.util.List;

public class bmh extends bmn {
	public bmh(uh uh) {
		super(uh);
	}

	public boolean a(bgu bgu, bqb bqb) {
		bki bki4 = bki.b;
		List<bki> list5 = Lists.<bki>newArrayList();

		for (int integer6 = 0; integer6 < bgu.ab_(); integer6++) {
			bki bki7 = bgu.a(integer6);
			if (!bki7.a()) {
				if (bki7.b() instanceof bji) {
					if (!bki4.a()) {
						return false;
					}

					bki4 = bki7;
				} else {
					if (!(bki7.b() instanceof bjf)) {
						return false;
					}

					list5.add(bki7);
				}
			}
		}

		return !bki4.a() && !list5.isEmpty();
	}

	public bki a(bgu bgu) {
		List<bjf> list3 = Lists.<bjf>newArrayList();
		bki bki4 = bki.b;

		for (int integer5 = 0; integer5 < bgu.ab_(); integer5++) {
			bki bki6 = bgu.a(integer5);
			if (!bki6.a()) {
				bke bke7 = bki6.b();
				if (bke7 instanceof bji) {
					if (!bki4.a()) {
						return bki.b;
					}

					bki4 = bki6.i();
				} else {
					if (!(bke7 instanceof bjf)) {
						return bki.b;
					}

					list3.add((bjf)bke7);
				}
			}
		}

		return !bki4.a() && !list3.isEmpty() ? bji.a(bki4, list3) : bki.b;
	}

	@Override
	public bmw<?> ai_() {
		return bmw.c;
	}
}
