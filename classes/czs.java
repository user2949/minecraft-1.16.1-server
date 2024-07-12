import java.util.Objects;
import javax.annotation.Nullable;

public class czs {
	private final czs.a a;
	private byte b;
	private byte c;
	private byte d;
	private final mr e;

	public czs(czs.a a, byte byte2, byte byte3, byte byte4, @Nullable mr mr) {
		this.a = a;
		this.b = byte2;
		this.c = byte3;
		this.d = byte4;
		this.e = mr;
	}

	public czs.a b() {
		return this.a;
	}

	public byte c() {
		return this.b;
	}

	public byte d() {
		return this.c;
	}

	public byte e() {
		return this.d;
	}

	@Nullable
	public mr g() {
		return this.e;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof czs)) {
			return false;
		} else {
			czs czs3 = (czs)object;
			if (this.a != czs3.a) {
				return false;
			} else if (this.d != czs3.d) {
				return false;
			} else if (this.b != czs3.b) {
				return false;
			} else {
				return this.c != czs3.c ? false : Objects.equals(this.e, czs3.e);
			}
		}
	}

	public int hashCode() {
		int integer2 = this.a.a();
		integer2 = 31 * integer2 + this.b;
		integer2 = 31 * integer2 + this.c;
		integer2 = 31 * integer2 + this.d;
		return 31 * integer2 + Objects.hashCode(this.e);
	}

	public static enum a {
		PLAYER(false),
		FRAME(true),
		RED_MARKER(false),
		BLUE_MARKER(false),
		TARGET_X(true),
		TARGET_POINT(true),
		PLAYER_OFF_MAP(false),
		PLAYER_OFF_LIMITS(false),
		MANSION(true, 5393476),
		MONUMENT(true, 3830373),
		BANNER_WHITE(true),
		BANNER_ORANGE(true),
		BANNER_MAGENTA(true),
		BANNER_LIGHT_BLUE(true),
		BANNER_YELLOW(true),
		BANNER_LIME(true),
		BANNER_PINK(true),
		BANNER_GRAY(true),
		BANNER_LIGHT_GRAY(true),
		BANNER_CYAN(true),
		BANNER_PURPLE(true),
		BANNER_BLUE(true),
		BANNER_BROWN(true),
		BANNER_GREEN(true),
		BANNER_RED(true),
		BANNER_BLACK(true),
		RED_X(true);

		private final byte B = (byte)this.ordinal();
		private final boolean C;
		private final int D;

		private a(boolean boolean3) {
			this(boolean3, -1);
		}

		private a(boolean boolean3, int integer4) {
			this.C = boolean3;
			this.D = integer4;
		}

		public byte a() {
			return this.B;
		}

		public boolean c() {
			return this.D >= 0;
		}

		public int d() {
			return this.D;
		}

		public static czs.a a(byte byte1) {
			return values()[aec.a(byte1, 0, values().length - 1)];
		}
	}
}
