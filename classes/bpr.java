import java.util.Optional;

class bpr implements bpu {
	private final aom a;

	bpr(aom aom) {
		this.a = aom;
	}

	@Override
	public Optional<Float> a(bpt bpt, bpg bpg, fu fu, cfj cfj, cxa cxa) {
		return bpo.INSTANCE.a(bpt, bpg, fu, cfj, cxa).map(float6 -> this.a.a(bpt, bpg, fu, cfj, cxa, float6));
	}

	@Override
	public boolean a(bpt bpt, bpg bpg, fu fu, cfj cfj, float float5) {
		return this.a.a(bpt, bpg, fu, cfj, float5);
	}
}
