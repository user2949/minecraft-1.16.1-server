public enum bnx {
	ARMOR {
		@Override
		public boolean a(bke bke) {
			return bke instanceof bid;
		}
	},
	ARMOR_FEET {
		@Override
		public boolean a(bke bke) {
			return bke instanceof bid && ((bid)bke).b() == aor.FEET;
		}
	},
	ARMOR_LEGS {
		@Override
		public boolean a(bke bke) {
			return bke instanceof bid && ((bid)bke).b() == aor.LEGS;
		}
	},
	ARMOR_CHEST {
		@Override
		public boolean a(bke bke) {
			return bke instanceof bid && ((bid)bke).b() == aor.CHEST;
		}
	},
	ARMOR_HEAD {
		@Override
		public boolean a(bke bke) {
			return bke instanceof bid && ((bid)bke).b() == aor.HEAD;
		}
	},
	WEAPON {
		@Override
		public boolean a(bke bke) {
			return bke instanceof blm;
		}
	},
	DIGGER {
		@Override
		public boolean a(bke bke) {
			return bke instanceof bjb;
		}
	},
	FISHING_ROD {
		@Override
		public boolean a(bke bke) {
			return bke instanceof bjw;
		}
	},
	TRIDENT {
		@Override
		public boolean a(bke bke) {
			return bke instanceof blt;
		}
	},
	BREAKABLE {
		@Override
		public boolean a(bke bke) {
			return bke.k();
		}
	},
	BOW {
		@Override
		public boolean a(bke bke) {
			return bke instanceof bis;
		}
	},
	WEARABLE {
		@Override
		public boolean a(bke bke) {
			return bke instanceof bly || bvr.a(bke) instanceof bly;
		}
	},
	CROSSBOW {
		@Override
		public boolean a(bke bke) {
			return bke instanceof biz;
		}
	},
	VANISHABLE {
		@Override
		public boolean a(bke bke) {
			return bke instanceof blw || bvr.a(bke) instanceof blw || BREAKABLE.a(bke);
		}
	};

	private bnx() {
	}

	public abstract boolean a(bke bke);
}
