import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class aso extends aqh<bdp> {
	private final awp<List<gc>> b;
	private final awp<gc> c;
	private final float d;
	private final int e;
	private final int f;
	private long g;
	@Nullable
	private gc h;

	public aso(awp<List<gc>> awp1, float float2, int integer3, int integer4, awp<gc> awp5) {
		super(ImmutableMap.of(awp.m, awq.REGISTERED, awp1, awq.VALUE_PRESENT, awp5, awq.VALUE_PRESENT));
		this.b = awp1;
		this.d = float2;
		this.e = integer3;
		this.f = integer4;
		this.c = awp5;
	}

	protected boolean a(zd zd, bdp bdp) {
		Optional<List<gc>> optional4 = bdp.cI().c(this.b);
		Optional<gc> optional5 = bdp.cI().c(this.c);
		if (optional4.isPresent() && optional5.isPresent()) {
			List<gc> list6 = (List<gc>)optional4.get();
			if (!list6.isEmpty()) {
				this.h = (gc)list6.get(zd.v_().nextInt(list6.size()));
				return this.h != null && zd.W() == this.h.a() && ((gc)optional5.get()).b().a(bdp.cz(), (double)this.f);
			}
		}

		return false;
	}

	protected void a(zd zd, bdp bdp, long long3) {
		if (long3 > this.g && this.h != null) {
			bdp.cI().a(awp.m, new awr(this.h.b(), this.d, this.e));
			this.g = long3 + 100L;
		}
	}
}
