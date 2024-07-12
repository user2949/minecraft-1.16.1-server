public enum cgj implements aeh {
	HARP("harp", acl.jv),
	BASEDRUM("basedrum", acl.jp),
	SNARE("snare", acl.jy),
	HAT("hat", acl.jw),
	BASS("bass", acl.jq),
	FLUTE("flute", acl.jt),
	BELL("bell", acl.jr),
	GUITAR("guitar", acl.ju),
	CHIME("chime", acl.js),
	XYLOPHONE("xylophone", acl.jz),
	IRON_XYLOPHONE("iron_xylophone", acl.jA),
	COW_BELL("cow_bell", acl.jB),
	DIDGERIDOO("didgeridoo", acl.jC),
	BIT("bit", acl.jD),
	BANJO("banjo", acl.jE),
	PLING("pling", acl.jx);

	private final String q;
	private final ack r;

	private cgj(String string3, ack ack) {
		this.q = string3;
		this.r = ack;
	}

	@Override
	public String a() {
		return this.q;
	}

	public ack b() {
		return this.r;
	}

	public static cgj a(cfj cfj) {
		if (cfj.a(bvs.cG)) {
			return FLUTE;
		} else if (cfj.a(bvs.bE)) {
			return BELL;
		} else if (cfj.a(acx.a)) {
			return GUITAR;
		} else if (cfj.a(bvs.gT)) {
			return CHIME;
		} else if (cfj.a(bvs.iM)) {
			return XYLOPHONE;
		} else if (cfj.a(bvs.bF)) {
			return IRON_XYLOPHONE;
		} else if (cfj.a(bvs.cM)) {
			return COW_BELL;
		} else if (cfj.a(bvs.cK)) {
			return DIDGERIDOO;
		} else if (cfj.a(bvs.en)) {
			return BIT;
		} else if (cfj.a(bvs.gA)) {
			return BANJO;
		} else if (cfj.a(bvs.cS)) {
			return PLING;
		} else {
			cxd cxd2 = cfj.c();
			if (cxd2 == cxd.H) {
				return BASEDRUM;
			} else if (cxd2 == cxd.u) {
				return SNARE;
			} else if (cxd2 == cxd.E) {
				return HAT;
			} else {
				return cxd2 != cxd.x && cxd2 != cxd.y ? HARP : BASS;
			}
		}
	}
}
