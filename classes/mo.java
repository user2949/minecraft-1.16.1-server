public enum mo {
	CHAT((byte)0, false),
	SYSTEM((byte)1, true),
	GAME_INFO((byte)2, true);

	private final byte d;
	private final boolean e;

	private mo(byte byte3, boolean boolean4) {
		this.d = byte3;
		this.e = boolean4;
	}

	public byte a() {
		return this.d;
	}

	public static mo a(byte byte1) {
		for (mo mo5 : values()) {
			if (byte1 == mo5.d) {
				return mo5;
			}
		}

		return CHAT;
	}
}
