import com.mojang.serialization.Codec;
import java.util.Random;

public class ckq extends ckt<cnq> {
	public ckq(Codec<cnq> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cnq cnq) {
		for (fu fu9 : fu.a(fu.b(-1, -2, -1), fu.b(1, 2, 1))) {
			boolean boolean10 = fu9.u() == fu.u();
			boolean boolean11 = fu9.v() == fu.v();
			boolean boolean12 = fu9.w() == fu.w();
			boolean boolean13 = Math.abs(fu9.v() - fu.v()) == 2;
			if (boolean10 && boolean11 && boolean12) {
				fu fu14 = fu9.h();
				this.a(bqu, fu14, bvs.iF.n());
				cnq.b().ifPresent(fu4 -> {
					cdl cdl5 = bqu.c(fu14);
					if (cdl5 instanceof cem) {
						cem cem6 = (cem)cdl5;
						cem6.a(fu4, cnq.c());
						cdl5.Z_();
					}
				});
			} else if (boolean11) {
				this.a(bqu, fu9, bvs.a.n());
			} else if (boolean13 && boolean10 && boolean12) {
				this.a(bqu, fu9, bvs.z.n());
			} else if ((boolean10 || boolean12) && !boolean13) {
				this.a(bqu, fu9, bvs.z.n());
			} else {
				this.a(bqu, fu9, bvs.a.n());
			}
		}

		return true;
	}
}
