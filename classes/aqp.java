import java.util.List;
import java.util.Optional;

public class aqp implements arn {
	private final aom a;
	private final boolean b;

	public aqp(aom aom, boolean boolean2) {
		this.a = aom;
		this.b = boolean2;
	}

	@Override
	public dem a() {
		return this.b ? this.a.cz().b(0.0, (double)this.a.cd(), 0.0) : this.a.cz();
	}

	@Override
	public fu b() {
		return this.a.cA();
	}

	@Override
	public boolean a(aoy aoy) {
		if (!(this.a instanceof aoy)) {
			return true;
		} else {
			Optional<List<aoy>> optional3 = aoy.cI().c(awp.h);
			return this.a.aU() && optional3.isPresent() && ((List)optional3.get()).contains(this.a);
		}
	}

	public String toString() {
		return "EntityTracker for " + this.a;
	}
}
