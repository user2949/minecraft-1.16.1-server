public class aoa extends aoe {
	protected aoa(aof aof, int integer) {
		super(aof, integer);
	}

	@Override
	public void a(aoy aoy, apu apu, int integer) {
		aoy.p(aoy.dS() - (float)(4 * (integer + 1)));
		super.a(aoy, apu, integer);
	}

	@Override
	public void b(aoy aoy, apu apu, int integer) {
		aoy.p(aoy.dS() + (float)(4 * (integer + 1)));
		super.b(aoy, apu, integer);
	}
}
