import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class cjy extends ckt<coa> {
	public cjy(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		bph bph8 = new bph(fu);
		List<Integer> list9 = (List<Integer>)IntStream.rangeClosed(bph8.d(), bph8.f()).boxed().collect(Collectors.toList());
		Collections.shuffle(list9, random);
		List<Integer> list10 = (List<Integer>)IntStream.rangeClosed(bph8.e(), bph8.g()).boxed().collect(Collectors.toList());
		Collections.shuffle(list10, random);
		fu.a a11 = new fu.a();

		for (Integer integer13 : list9) {
			for (Integer integer15 : list10) {
				a11.d(integer13, 0, integer15);
				fu fu16 = bqu.a(cio.a.MOTION_BLOCKING_NO_LEAVES, a11);
				if (bqu.w(fu16) || bqu.d_(fu16).k(bqu, fu16).b()) {
					bqu.a(fu16, bvs.bR.n(), 2);
					cef.a(bqu, random, fu16, dao.b);
					cfj cfj17 = bvs.bL.n();

					for (fz fz19 : fz.c.HORIZONTAL) {
						fu fu20 = fu16.a(fz19);
						if (cfj17.a(bqu, fu20)) {
							bqu.a(fu20, cfj17, 2);
						}
					}

					return true;
				}
			}
		}

		return false;
	}
}
