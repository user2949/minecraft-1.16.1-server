import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class aqk extends aqh<bdp> {
	@Nullable
	private bfh b;

	public aqk(int integer1, int integer2) {
		super(ImmutableMap.of(), integer1, integer2);
	}

	protected boolean a(zd zd, bdp bdp) {
		fu fu4 = bdp.cA();
		this.b = zd.c_(fu4);
		return this.b != null && this.b.e() && arj.a(zd, bdp, fu4);
	}

	protected boolean b(zd zd, bdp bdp, long long3) {
		return this.b != null && !this.b.d();
	}

	protected void c(zd zd, bdp bdp, long long3) {
		this.b = null;
		bdp.cI().a(zd.R(), zd.Q());
	}

	protected void c(zd zd, bdp bdp, long long3) {
		Random random6 = bdp.cX();
		if (random6.nextInt(100) == 0) {
			bdp.eS();
		}

		if (random6.nextInt(200) == 0 && arj.a(zd, bdp, bdp.cA())) {
			bje bje7 = v.a(bje.values(), random6);
			int integer8 = random6.nextInt(3);
			bki bki9 = this.a(bje7, integer8);
			ben ben10 = new ben(bdp.l, bdp, bdp.cC(), bdp.cF(), bdp.cG(), bki9);
			bdp.l.c(ben10);
		}
	}

	private bki a(bje bje, int integer) {
		bki bki4 = new bki(bkk.pn, 1);
		bki bki5 = new bki(bkk.po);
		le le6 = bki5.a("Explosion");
		List<Integer> list7 = Lists.<Integer>newArrayList();
		list7.add(bje.g());
		le6.b("Colors", list7);
		le6.a("Type", (byte)bjt.a.BURST.a());
		le le8 = bki4.a("Fireworks");
		lk lk9 = new lk();
		le le10 = bki5.b("Explosion");
		if (le10 != null) {
			lk9.add(le10);
		}

		le8.a("Flight", (byte)integer);
		if (!lk9.isEmpty()) {
			le8.a("Explosions", lk9);
		}

		return bki4;
	}
}
