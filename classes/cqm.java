import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class cqm {
	public static final Codec<cqm> c = gl.ax.dispatch(cqm::a, cqn::a);

	protected abstract cqn<?> a();

	public abstract void a(bqc bqc, Random random, List<fu> list3, List<fu> list4, Set<fu> set, ctd ctd);

	protected void a(bqh bqh, fu fu, cga cga, Set<fu> set, ctd ctd) {
		this.a(bqh, fu, bvs.dP.n().a(cga, Boolean.valueOf(true)), set, ctd);
	}

	protected void a(bqh bqh, fu fu, cfj cfj, Set<fu> set, ctd ctd) {
		bqh.a(fu, cfj, 19);
		set.add(fu);
		ctd.c(new ctd(fu, fu));
	}
}
