import java.util.BitSet;

public final class dep extends dev {
	private final BitSet d;
	private int e;
	private int f;
	private int g;
	private int h;
	private int i;
	private int j;

	public dep(int integer1, int integer2, int integer3) {
		this(integer1, integer2, integer3, integer1, integer2, integer3, 0, 0, 0);
	}

	public dep(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8, int integer9) {
		super(integer1, integer2, integer3);
		this.d = new BitSet(integer1 * integer2 * integer3);
		this.e = integer4;
		this.f = integer5;
		this.g = integer6;
		this.h = integer7;
		this.i = integer8;
		this.j = integer9;
	}

	public dep(dev dev) {
		super(dev.a, dev.b, dev.c);
		if (dev instanceof dep) {
			this.d = (BitSet)((dep)dev).d.clone();
		} else {
			this.d = new BitSet(this.a * this.b * this.c);

			for (int integer3 = 0; integer3 < this.a; integer3++) {
				for (int integer4 = 0; integer4 < this.b; integer4++) {
					for (int integer5 = 0; integer5 < this.c; integer5++) {
						if (dev.b(integer3, integer4, integer5)) {
							this.d.set(this.a(integer3, integer4, integer5));
						}
					}
				}
			}
		}

		this.e = dev.a(fz.a.X);
		this.f = dev.a(fz.a.Y);
		this.g = dev.a(fz.a.Z);
		this.h = dev.b(fz.a.X);
		this.i = dev.b(fz.a.Y);
		this.j = dev.b(fz.a.Z);
	}

	protected int a(int integer1, int integer2, int integer3) {
		return (integer1 * this.b + integer2) * this.c + integer3;
	}

	@Override
	public boolean b(int integer1, int integer2, int integer3) {
		return this.d.get(this.a(integer1, integer2, integer3));
	}

	@Override
	public void a(int integer1, int integer2, int integer3, boolean boolean4, boolean boolean5) {
		this.d.set(this.a(integer1, integer2, integer3), boolean5);
		if (boolean4 && boolean5) {
			this.e = Math.min(this.e, integer1);
			this.f = Math.min(this.f, integer2);
			this.g = Math.min(this.g, integer3);
			this.h = Math.max(this.h, integer1 + 1);
			this.i = Math.max(this.i, integer2 + 1);
			this.j = Math.max(this.j, integer3 + 1);
		}
	}

	@Override
	public boolean a() {
		return this.d.isEmpty();
	}

	@Override
	public int a(fz.a a) {
		return a.a(this.e, this.f, this.g);
	}

	@Override
	public int b(fz.a a) {
		return a.a(this.h, this.i, this.j);
	}

	@Override
	protected boolean a(int integer1, int integer2, int integer3, int integer4) {
		if (integer3 < 0 || integer4 < 0 || integer1 < 0) {
			return false;
		} else {
			return integer3 < this.a && integer4 < this.b && integer2 <= this.c
				? this.d.nextClearBit(this.a(integer3, integer4, integer1)) >= this.a(integer3, integer4, integer2)
				: false;
		}
	}

	@Override
	protected void a(int integer1, int integer2, int integer3, int integer4, boolean boolean5) {
		this.d.set(this.a(integer3, integer4, integer1), this.a(integer3, integer4, integer2), boolean5);
	}

	static dep a(dev dev1, dev dev2, dey dey3, dey dey4, dey dey5, deq deq) {
		dep dep7 = new dep(dey3.a().size() - 1, dey4.a().size() - 1, dey5.a().size() - 1);
		int[] arr8 = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
		dey3.a((integer8, integer9, integer10) -> {
			boolean[] arr11 = new boolean[]{false};
			boolean boolean12 = dey4.a((integer11, integer12, integer13) -> {
				boolean[] arr14 = new boolean[]{false};
				boolean boolean15 = dey5.a((integer13x, integer14, integer15) -> {
					boolean boolean16 = deq.apply(dev1.c(integer8, integer11, integer13x), dev2.c(integer9, integer12, integer14));
					if (boolean16) {
						dep7.d.set(dep7.a(integer10, integer13, integer15));
						arr8[2] = Math.min(arr8[2], integer15);
						arr8[5] = Math.max(arr8[5], integer15);
						arr14[0] = true;
					}

					return true;
				});
				if (arr14[0]) {
					arr8[1] = Math.min(arr8[1], integer13);
					arr8[4] = Math.max(arr8[4], integer13);
					arr11[0] = true;
				}

				return boolean15;
			});
			if (arr11[0]) {
				arr8[0] = Math.min(arr8[0], integer10);
				arr8[3] = Math.max(arr8[3], integer10);
			}

			return boolean12;
		});
		dep7.e = arr8[0];
		dep7.f = arr8[1];
		dep7.g = arr8[2];
		dep7.h = arr8[3] + 1;
		dep7.i = arr8[4] + 1;
		dep7.j = arr8[5] + 1;
		return dep7;
	}
}
