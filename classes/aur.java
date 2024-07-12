import javax.annotation.Nullable;

public class aur extends avf {
	public aur(apg apg, double double2, boolean boolean3) {
		super(apg, double2, 10, boolean3);
	}

	@Override
	public boolean a() {
		zd zd2 = (zd)this.a.l;
		fu fu3 = this.a.cA();
		return zd2.b_(fu3) ? false : super.a();
	}

	@Nullable
	@Override
	protected dem g() {
		zd zd2 = (zd)this.a.l;
		fu fu3 = this.a.cA();
		go go4 = go.a(fu3);
		go go5 = aqi.a(zd2, go4, 2);
		return go5 != go4 ? axu.b(this.a, 10, 7, dem.c(go5.q())) : null;
	}
}
