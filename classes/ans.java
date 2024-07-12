public class ans extends anw {
	protected ans() {
		super("badRespawnPoint");
		this.r();
		this.e();
	}

	@Override
	public mr a(aoy aoy) {
		mr mr3 = ms.a((mr)(new ne("death.attack.badRespawnPoint.link")))
			.a(nb -> nb.a(new mp(mp.a.OPEN_URL, "https://bugs.mojang.com/browse/MCPE-28723")).a(new mv(mv.a.a, new nd("MCPE-28723"))));
		return new ne("death.attack.badRespawnPoint.message", aoy.d(), mr3);
	}
}
