import javax.annotation.Nullable;

public class avw extends avf {
	protected final float h;

	public avw(apg apg, double double2) {
		this(apg, double2, 0.001F);
	}

	public avw(apg apg, double double2, float float3) {
		super(apg, double2);
		this.h = float3;
	}

	@Nullable
	@Override
	protected dem g() {
		if (this.a.aD()) {
			dem dem2 = axu.b(this.a, 15, 7);
			return dem2 == null ? super.g() : dem2;
		} else {
			return this.a.cX().nextFloat() >= this.h ? axu.b(this.a, 10, 7) : super.g();
		}
	}
}
