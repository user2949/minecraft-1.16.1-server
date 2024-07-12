import javax.annotation.Nullable;

public class azh extends ayj {
	private static final tq<Integer> c = tt.a(azh.class, ts.b);
	private static final uh[] d = new uh[]{new uh("textures/entity/fish/tropical_a.png"), new uh("textures/entity/fish/tropical_b.png")};
	private static final uh[] bv = new uh[]{
		new uh("textures/entity/fish/tropical_a_pattern_1.png"),
		new uh("textures/entity/fish/tropical_a_pattern_2.png"),
		new uh("textures/entity/fish/tropical_a_pattern_3.png"),
		new uh("textures/entity/fish/tropical_a_pattern_4.png"),
		new uh("textures/entity/fish/tropical_a_pattern_5.png"),
		new uh("textures/entity/fish/tropical_a_pattern_6.png")
	};
	private static final uh[] bw = new uh[]{
		new uh("textures/entity/fish/tropical_b_pattern_1.png"),
		new uh("textures/entity/fish/tropical_b_pattern_2.png"),
		new uh("textures/entity/fish/tropical_b_pattern_3.png"),
		new uh("textures/entity/fish/tropical_b_pattern_4.png"),
		new uh("textures/entity/fish/tropical_b_pattern_5.png"),
		new uh("textures/entity/fish/tropical_b_pattern_6.png")
	};
	public static final int[] b = new int[]{
		a(azh.a.STRIPEY, bje.ORANGE, bje.GRAY),
		a(azh.a.FLOPPER, bje.GRAY, bje.GRAY),
		a(azh.a.FLOPPER, bje.GRAY, bje.BLUE),
		a(azh.a.CLAYFISH, bje.WHITE, bje.GRAY),
		a(azh.a.SUNSTREAK, bje.BLUE, bje.GRAY),
		a(azh.a.KOB, bje.ORANGE, bje.WHITE),
		a(azh.a.SPOTTY, bje.PINK, bje.LIGHT_BLUE),
		a(azh.a.BLOCKFISH, bje.PURPLE, bje.YELLOW),
		a(azh.a.CLAYFISH, bje.WHITE, bje.RED),
		a(azh.a.SPOTTY, bje.WHITE, bje.YELLOW),
		a(azh.a.GLITTER, bje.WHITE, bje.GRAY),
		a(azh.a.CLAYFISH, bje.WHITE, bje.ORANGE),
		a(azh.a.DASHER, bje.CYAN, bje.PINK),
		a(azh.a.BRINELY, bje.LIME, bje.LIGHT_BLUE),
		a(azh.a.BETTY, bje.RED, bje.WHITE),
		a(azh.a.SNOOPER, bje.GRAY, bje.RED),
		a(azh.a.BLOCKFISH, bje.RED, bje.WHITE),
		a(azh.a.FLOPPER, bje.WHITE, bje.YELLOW),
		a(azh.a.KOB, bje.RED, bje.WHITE),
		a(azh.a.SUNSTREAK, bje.GRAY, bje.WHITE),
		a(azh.a.DASHER, bje.CYAN, bje.YELLOW),
		a(azh.a.FLOPPER, bje.YELLOW, bje.YELLOW)
	};
	private boolean bx = true;

	private static int a(azh.a a, bje bje2, bje bje3) {
		return a.a() & 0xFF | (a.b() & 0xFF) << 8 | (bje2.b() & 0xFF) << 16 | (bje3.b() & 0xFF) << 24;
	}

	public azh(aoq<? extends azh> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(c, 0);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("Variant", this.eV());
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.v(le.h("Variant"));
	}

	public void v(int integer) {
		this.S.b(c, integer);
	}

	@Override
	public boolean c(int integer) {
		return !this.bx;
	}

	public int eV() {
		return this.S.a(c);
	}

	@Override
	protected void k(bki bki) {
		super.k(bki);
		le le3 = bki.p();
		le3.b("BucketVariantTag", this.eV());
	}

	@Override
	protected bki eL() {
		return new bki(bkk.lX);
	}

	@Override
	protected ack I() {
		return acl.pi;
	}

	@Override
	protected ack dp() {
		return acl.pj;
	}

	@Override
	protected ack e(anw anw) {
		return acl.pl;
	}

	@Override
	protected ack eN() {
		return acl.pk;
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		apo = super.a(bqc, ane, apb, apo, le);
		if (le != null && le.c("BucketVariantTag", 3)) {
			this.v(le.h("BucketVariantTag"));
			return apo;
		} else {
			int integer7;
			int integer8;
			int integer9;
			int integer10;
			if (apo instanceof azh.b) {
				azh.b b11 = (azh.b)apo;
				integer7 = b11.b;
				integer8 = b11.c;
				integer9 = b11.d;
				integer10 = b11.e;
			} else if ((double)this.J.nextFloat() < 0.9) {
				int integer11 = v.a(b, this.J);
				integer7 = integer11 & 0xFF;
				integer8 = (integer11 & 0xFF00) >> 8;
				integer9 = (integer11 & 0xFF0000) >> 16;
				integer10 = (integer11 & 0xFF000000) >> 24;
				apo = new azh.b(this, integer7, integer8, integer9, integer10);
			} else {
				this.bx = false;
				integer7 = this.J.nextInt(2);
				integer8 = this.J.nextInt(6);
				integer9 = this.J.nextInt(15);
				integer10 = this.J.nextInt(15);
			}

			this.v(integer7 | integer8 << 8 | integer9 << 16 | integer10 << 24);
			return apo;
		}
	}

	static enum a {
		KOB(0, 0),
		SUNSTREAK(0, 1),
		SNOOPER(0, 2),
		DASHER(0, 3),
		BRINELY(0, 4),
		SPOTTY(0, 5),
		FLOPPER(1, 0),
		STRIPEY(1, 1),
		GLITTER(1, 2),
		BLOCKFISH(1, 3),
		BETTY(1, 4),
		CLAYFISH(1, 5);

		private final int m;
		private final int n;
		private static final azh.a[] o = values();

		private a(int integer3, int integer4) {
			this.m = integer3;
			this.n = integer4;
		}

		public int a() {
			return this.m;
		}

		public int b() {
			return this.n;
		}
	}

	static class b extends ayj.a {
		private final int b;
		private final int c;
		private final int d;
		private final int e;

		private b(azh azh, int integer2, int integer3, int integer4, int integer5) {
			super(azh);
			this.b = integer2;
			this.c = integer3;
			this.d = integer4;
			this.e = integer5;
		}
	}
}
