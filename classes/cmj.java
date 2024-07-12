import com.mojang.serialization.Codec;
import java.util.Random;

public class cmj extends ckt<cor> {
	public cmj(Codec<cor> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cor cor) {
		if (!cor.f.contains(bqu.d_(fu.b()).b())) {
			return false;
		} else if (cor.c && !cor.f.contains(bqu.d_(fu.c()).b())) {
			return false;
		} else {
			cfj cfj8 = bqu.d_(fu);
			if (!cfj8.g() && !cor.f.contains(cfj8.b())) {
				return false;
			} else {
				int integer9 = 0;
				int integer10 = 0;
				if (cor.f.contains(bqu.d_(fu.f()).b())) {
					integer10++;
				}

				if (cor.f.contains(bqu.d_(fu.g()).b())) {
					integer10++;
				}

				if (cor.f.contains(bqu.d_(fu.d()).b())) {
					integer10++;
				}

				if (cor.f.contains(bqu.d_(fu.e()).b())) {
					integer10++;
				}

				if (cor.f.contains(bqu.d_(fu.c()).b())) {
					integer10++;
				}

				int integer11 = 0;
				if (bqu.w(fu.f())) {
					integer11++;
				}

				if (bqu.w(fu.g())) {
					integer11++;
				}

				if (bqu.w(fu.d())) {
					integer11++;
				}

				if (bqu.w(fu.e())) {
					integer11++;
				}

				if (bqu.w(fu.c())) {
					integer11++;
				}

				if (integer10 == cor.d && integer11 == cor.e) {
					bqu.a(fu, cor.b.g(), 2);
					bqu.F().a(fu, cor.b.a(), 0);
					integer9++;
				}

				return integer9 > 0;
			}
		}
	}
}
