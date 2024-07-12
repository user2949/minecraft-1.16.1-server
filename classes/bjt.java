import java.util.Arrays;
import java.util.Comparator;

public class bjt extends bke {
	public bjt(bke.a a) {
		super(a);
	}

	@Override
	public ang a(blv blv) {
		bqb bqb3 = blv.o();
		if (!bqb3.v) {
			bki bki4 = blv.l();
			dem dem5 = blv.j();
			fz fz6 = blv.i();
			ben ben7 = new ben(bqb3, blv.m(), dem5.b + (double)fz6.i() * 0.15, dem5.c + (double)fz6.j() * 0.15, dem5.d + (double)fz6.k() * 0.15, bki4);
			bqb3.c(ben7);
			bki4.g(1);
		}

		return ang.a(bqb3.v);
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		if (bec.ee()) {
			bki bki5 = bec.b(anf);
			if (!bqb.v) {
				bqb.c(new ben(bqb, bki5, bec));
				if (!bec.bJ.d) {
					bki5.g(1);
				}
			}

			return anh.a(bec.b(anf), bqb.s_());
		} else {
			return anh.c(bec.b(anf));
		}
	}

	public static enum a {
		SMALL_BALL(0, "small_ball"),
		LARGE_BALL(1, "large_ball"),
		STAR(2, "star"),
		CREEPER(3, "creeper"),
		BURST(4, "burst");

		private static final bjt.a[] f = (bjt.a[])Arrays.stream(values()).sorted(Comparator.comparingInt(a -> a.g)).toArray(bjt.a[]::new);
		private final int g;
		private final String h;

		private a(int integer3, String string4) {
			this.g = integer3;
			this.h = string4;
		}

		public int a() {
			return this.g;
		}
	}
}
