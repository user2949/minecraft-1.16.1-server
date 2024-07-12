import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class aqs extends aqh<bdp> {
	private static final Map<bds, uh> b = v.a(Maps.<bds, uh>newHashMap(), hashMap -> {
		hashMap.put(bds.b, dao.al);
		hashMap.put(bds.c, dao.am);
		hashMap.put(bds.d, dao.an);
		hashMap.put(bds.e, dao.ao);
		hashMap.put(bds.f, dao.ap);
		hashMap.put(bds.g, dao.aq);
		hashMap.put(bds.h, dao.ar);
		hashMap.put(bds.i, dao.as);
		hashMap.put(bds.j, dao.at);
		hashMap.put(bds.k, dao.au);
		hashMap.put(bds.m, dao.av);
		hashMap.put(bds.n, dao.aw);
		hashMap.put(bds.o, dao.ax);
	});
	private int c = 600;
	private boolean d;
	private long e;

	public aqs(int integer) {
		super(ImmutableMap.of(awp.m, awq.REGISTERED, awp.n, awq.REGISTERED, awp.q, awq.REGISTERED, awp.k, awq.VALUE_PRESENT), integer);
	}

	protected boolean a(zd zd, bdp bdp) {
		if (!this.b(bdp)) {
			return false;
		} else if (this.c > 0) {
			this.c--;
			return false;
		} else {
			return true;
		}
	}

	protected void a(zd zd, bdp bdp, long long3) {
		this.d = false;
		this.e = long3;
		bec bec6 = (bec)this.c(bdp).get();
		bdp.cI().a(awp.q, bec6);
		aqi.a(bdp, bec6);
	}

	protected boolean b(zd zd, bdp bdp, long long3) {
		return this.b(bdp) && !this.d;
	}

	protected void d(zd zd, bdp bdp, long long3) {
		bec bec6 = (bec)this.c(bdp).get();
		aqi.a(bdp, bec6);
		if (this.a(bdp, bec6)) {
			if (long3 - this.e > 20L) {
				this.a(bdp, (aoy)bec6);
				this.d = true;
			}
		} else {
			aqi.a(bdp, bec6, 0.5F, 5);
		}
	}

	protected void d(zd zd, bdp bdp, long long3) {
		this.c = a(zd);
		bdp.cI().b(awp.q);
		bdp.cI().b(awp.m);
		bdp.cI().b(awp.n);
	}

	private void a(bdp bdp, aoy aoy) {
		for (bki bki6 : this.a(bdp)) {
			aqi.a(bdp, bki6, aoy.cz());
		}
	}

	private List<bki> a(bdp bdp) {
		if (bdp.x_()) {
			return ImmutableList.of(new bki(bkk.bi));
		} else {
			bds bds3 = bdp.eY().b();
			if (b.containsKey(bds3)) {
				daw daw4 = bdp.l.l().aH().a((uh)b.get(bds3));
				dat.a a5 = new dat.a((zd)bdp.l).a(dda.f, bdp.cA()).a(dda.a, bdp).a(bdp.cX());
				return daw4.a(a5.a(dcz.g));
			} else {
				return ImmutableList.of(new bki(bkk.kV));
			}
		}
	}

	private boolean b(bdp bdp) {
		return this.c(bdp).isPresent();
	}

	private Optional<bec> c(bdp bdp) {
		return bdp.cI().c(awp.k).filter(this::a);
	}

	private boolean a(bec bec) {
		return bec.a(aoi.F);
	}

	private boolean a(bdp bdp, bec bec) {
		fu fu4 = bec.cA();
		fu fu5 = bdp.cA();
		return fu5.a(fu4, 5.0);
	}

	private static int a(zd zd) {
		return 600 + zd.t.nextInt(6001);
	}
}
