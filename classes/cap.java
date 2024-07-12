import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum cap {
	NONE(c.IDENTITY),
	CLOCKWISE_90(c.ROT_90_Y_NEG),
	CLOCKWISE_180(c.ROT_180_FACE_XZ),
	COUNTERCLOCKWISE_90(c.ROT_90_Y_POS);

	private final c e;

	private cap(c c) {
		this.e = c;
	}

	public cap a(cap cap) {
		switch (cap) {
			case CLOCKWISE_180:
				switch (this) {
					case NONE:
						return CLOCKWISE_180;
					case CLOCKWISE_90:
						return COUNTERCLOCKWISE_90;
					case CLOCKWISE_180:
						return NONE;
					case COUNTERCLOCKWISE_90:
						return CLOCKWISE_90;
				}
			case COUNTERCLOCKWISE_90:
				switch (this) {
					case NONE:
						return COUNTERCLOCKWISE_90;
					case CLOCKWISE_90:
						return NONE;
					case CLOCKWISE_180:
						return CLOCKWISE_90;
					case COUNTERCLOCKWISE_90:
						return CLOCKWISE_180;
				}
			case CLOCKWISE_90:
				switch (this) {
					case NONE:
						return CLOCKWISE_90;
					case CLOCKWISE_90:
						return CLOCKWISE_180;
					case CLOCKWISE_180:
						return COUNTERCLOCKWISE_90;
					case COUNTERCLOCKWISE_90:
						return NONE;
				}
			default:
				return this;
		}
	}

	public c a() {
		return this.e;
	}

	public fz a(fz fz) {
		if (fz.n() == fz.a.Y) {
			return fz;
		} else {
			switch (this) {
				case CLOCKWISE_90:
					return fz.g();
				case CLOCKWISE_180:
					return fz.f();
				case COUNTERCLOCKWISE_90:
					return fz.h();
				default:
					return fz;
			}
		}
	}

	public int a(int integer1, int integer2) {
		switch (this) {
			case CLOCKWISE_90:
				return (integer1 + integer2 / 4) % integer2;
			case CLOCKWISE_180:
				return (integer1 + integer2 / 2) % integer2;
			case COUNTERCLOCKWISE_90:
				return (integer1 + integer2 * 3 / 4) % integer2;
			default:
				return integer1;
		}
	}

	public static cap a(Random random) {
		return v.a(values(), random);
	}

	public static List<cap> b(Random random) {
		List<cap> list2 = Lists.<cap>newArrayList(values());
		Collections.shuffle(list2, random);
		return list2;
	}
}
