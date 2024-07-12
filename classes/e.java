import java.util.Arrays;

public enum e {
	P123(0, 1, 2),
	P213(1, 0, 2),
	P132(0, 2, 1),
	P231(1, 2, 0),
	P312(2, 0, 1),
	P321(2, 1, 0);

	private final int[] g;
	private final a h;
	private static final e[][] i = v.a(new e[values().length][values().length], arr -> {
		for (e e5 : values()) {
			for (e e9 : values()) {
				int[] arr10 = new int[3];

				for (int integer11 = 0; integer11 < 3; integer11++) {
					arr10[integer11] = e5.g[e9.g[integer11]];
				}

				e e11 = (e)Arrays.stream(values()).filter(e -> Arrays.equals(e.g, arr10)).findFirst().get();
				arr[e5.ordinal()][e9.ordinal()] = e11;
			}
		}
	});

	private e(int integer3, int integer4, int integer5) {
		this.g = new int[]{integer3, integer4, integer5};
		this.h = new a();
		this.h.a(0, this.a(0), 1.0F);
		this.h.a(1, this.a(1), 1.0F);
		this.h.a(2, this.a(2), 1.0F);
	}

	public e a(e e) {
		return i[this.ordinal()][e.ordinal()];
	}

	public int a(int integer) {
		return this.g[integer];
	}

	public a a() {
		return this.h;
	}
}
