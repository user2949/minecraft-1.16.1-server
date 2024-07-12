import java.util.Optional;

public enum bpo implements bpu {
	INSTANCE;

	@Override
	public Optional<Float> a(bpt bpt, bpg bpg, fu fu, cfj cfj, cxa cxa) {
		return cfj.g() && cxa.c() ? Optional.empty() : Optional.of(Math.max(cfj.b().f(), cxa.i()));
	}

	@Override
	public boolean a(bpt bpt, bpg bpg, fu fu, cfj cfj, float float5) {
		return true;
	}
}
