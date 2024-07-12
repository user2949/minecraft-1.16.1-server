import java.util.Random;

public class ctf extends ctu {
	private final boolean[] e = new boolean[4];

	public ctf(Random random, int integer2, int integer3) {
		super(cmm.N, random, integer2, 64, integer3, 21, 15, 21);
	}

	public ctf(cva cva, le le) {
		super(cmm.N, le);
		this.e[0] = le.q("hasPlacedChest0");
		this.e[1] = le.q("hasPlacedChest1");
		this.e[2] = le.q("hasPlacedChest2");
		this.e[3] = le.q("hasPlacedChest3");
	}

	@Override
	protected void a(le le) {
		super.a(le);
		le.a("hasPlacedChest0", this.e[0]);
		le.a("hasPlacedChest1", this.e[1]);
		le.a("hasPlacedChest2", this.e[2]);
		le.a("hasPlacedChest3", this.e[3]);
	}

	@Override
	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
		this.a(bqu, ctd, 0, -4, 0, this.a - 1, 0, this.c - 1, bvs.at.n(), bvs.at.n(), false);

		for (int integer9 = 1; integer9 <= 9; integer9++) {
			this.a(bqu, ctd, integer9, integer9, integer9, this.a - 1 - integer9, integer9, this.c - 1 - integer9, bvs.at.n(), bvs.at.n(), false);
			this.a(bqu, ctd, integer9 + 1, integer9, integer9 + 1, this.a - 2 - integer9, integer9, this.c - 2 - integer9, bvs.a.n(), bvs.a.n(), false);
		}

		for (int integer9 = 0; integer9 < this.a; integer9++) {
			for (int integer10 = 0; integer10 < this.c; integer10++) {
				int integer11 = -5;
				this.b(bqu, bvs.at.n(), integer9, -5, integer10, ctd);
			}
		}

		cfj cfj9 = bvs.ei.n().a(cbn.a, fz.NORTH);
		cfj cfj10 = bvs.ei.n().a(cbn.a, fz.SOUTH);
		cfj cfj11 = bvs.ei.n().a(cbn.a, fz.EAST);
		cfj cfj12 = bvs.ei.n().a(cbn.a, fz.WEST);
		this.a(bqu, ctd, 0, 0, 0, 4, 9, 4, bvs.at.n(), bvs.a.n(), false);
		this.a(bqu, ctd, 1, 10, 1, 3, 10, 3, bvs.at.n(), bvs.at.n(), false);
		this.a(bqu, cfj9, 2, 10, 0, ctd);
		this.a(bqu, cfj10, 2, 10, 4, ctd);
		this.a(bqu, cfj11, 0, 10, 2, ctd);
		this.a(bqu, cfj12, 4, 10, 2, ctd);
		this.a(bqu, ctd, this.a - 5, 0, 0, this.a - 1, 9, 4, bvs.at.n(), bvs.a.n(), false);
		this.a(bqu, ctd, this.a - 4, 10, 1, this.a - 2, 10, 3, bvs.at.n(), bvs.at.n(), false);
		this.a(bqu, cfj9, this.a - 3, 10, 0, ctd);
		this.a(bqu, cfj10, this.a - 3, 10, 4, ctd);
		this.a(bqu, cfj11, this.a - 5, 10, 2, ctd);
		this.a(bqu, cfj12, this.a - 1, 10, 2, ctd);
		this.a(bqu, ctd, 8, 0, 0, 12, 4, 4, bvs.at.n(), bvs.a.n(), false);
		this.a(bqu, ctd, 9, 1, 0, 11, 3, 4, bvs.a.n(), bvs.a.n(), false);
		this.a(bqu, bvs.av.n(), 9, 1, 1, ctd);
		this.a(bqu, bvs.av.n(), 9, 2, 1, ctd);
		this.a(bqu, bvs.av.n(), 9, 3, 1, ctd);
		this.a(bqu, bvs.av.n(), 10, 3, 1, ctd);
		this.a(bqu, bvs.av.n(), 11, 3, 1, ctd);
		this.a(bqu, bvs.av.n(), 11, 2, 1, ctd);
		this.a(bqu, bvs.av.n(), 11, 1, 1, ctd);
		this.a(bqu, ctd, 4, 1, 1, 8, 3, 3, bvs.at.n(), bvs.a.n(), false);
		this.a(bqu, ctd, 4, 1, 2, 8, 2, 2, bvs.a.n(), bvs.a.n(), false);
		this.a(bqu, ctd, 12, 1, 1, 16, 3, 3, bvs.at.n(), bvs.a.n(), false);
		this.a(bqu, ctd, 12, 1, 2, 16, 2, 2, bvs.a.n(), bvs.a.n(), false);
		this.a(bqu, ctd, 5, 4, 5, this.a - 6, 4, this.c - 6, bvs.at.n(), bvs.at.n(), false);
		this.a(bqu, ctd, 9, 4, 9, 11, 4, 11, bvs.a.n(), bvs.a.n(), false);
		this.a(bqu, ctd, 8, 1, 8, 8, 3, 8, bvs.av.n(), bvs.av.n(), false);
		this.a(bqu, ctd, 12, 1, 8, 12, 3, 8, bvs.av.n(), bvs.av.n(), false);
		this.a(bqu, ctd, 8, 1, 12, 8, 3, 12, bvs.av.n(), bvs.av.n(), false);
		this.a(bqu, ctd, 12, 1, 12, 12, 3, 12, bvs.av.n(), bvs.av.n(), false);
		this.a(bqu, ctd, 1, 1, 5, 4, 4, 11, bvs.at.n(), bvs.at.n(), false);
		this.a(bqu, ctd, this.a - 5, 1, 5, this.a - 2, 4, 11, bvs.at.n(), bvs.at.n(), false);
		this.a(bqu, ctd, 6, 7, 9, 6, 7, 11, bvs.at.n(), bvs.at.n(), false);
		this.a(bqu, ctd, this.a - 7, 7, 9, this.a - 7, 7, 11, bvs.at.n(), bvs.at.n(), false);
		this.a(bqu, ctd, 5, 5, 9, 5, 7, 11, bvs.av.n(), bvs.av.n(), false);
		this.a(bqu, ctd, this.a - 6, 5, 9, this.a - 6, 7, 11, bvs.av.n(), bvs.av.n(), false);
		this.a(bqu, bvs.a.n(), 5, 5, 10, ctd);
		this.a(bqu, bvs.a.n(), 5, 6, 10, ctd);
		this.a(bqu, bvs.a.n(), 6, 6, 10, ctd);
		this.a(bqu, bvs.a.n(), this.a - 6, 5, 10, ctd);
		this.a(bqu, bvs.a.n(), this.a - 6, 6, 10, ctd);
		this.a(bqu, bvs.a.n(), this.a - 7, 6, 10, ctd);
		this.a(bqu, ctd, 2, 4, 4, 2, 6, 4, bvs.a.n(), bvs.a.n(), false);
		this.a(bqu, ctd, this.a - 3, 4, 4, this.a - 3, 6, 4, bvs.a.n(), bvs.a.n(), false);
		this.a(bqu, cfj9, 2, 4, 5, ctd);
		this.a(bqu, cfj9, 2, 3, 4, ctd);
		this.a(bqu, cfj9, this.a - 3, 4, 5, ctd);
		this.a(bqu, cfj9, this.a - 3, 3, 4, ctd);
		this.a(bqu, ctd, 1, 1, 3, 2, 2, 3, bvs.at.n(), bvs.at.n(), false);
		this.a(bqu, ctd, this.a - 3, 1, 3, this.a - 2, 2, 3, bvs.at.n(), bvs.at.n(), false);
		this.a(bqu, bvs.at.n(), 1, 1, 2, ctd);
		this.a(bqu, bvs.at.n(), this.a - 2, 1, 2, ctd);
		this.a(bqu, bvs.hS.n(), 1, 2, 2, ctd);
		this.a(bqu, bvs.hS.n(), this.a - 2, 2, 2, ctd);
		this.a(bqu, cfj12, 2, 1, 2, ctd);
		this.a(bqu, cfj11, this.a - 3, 1, 2, ctd);
		this.a(bqu, ctd, 4, 3, 5, 4, 3, 17, bvs.at.n(), bvs.at.n(), false);
		this.a(bqu, ctd, this.a - 5, 3, 5, this.a - 5, 3, 17, bvs.at.n(), bvs.at.n(), false);
		this.a(bqu, ctd, 3, 1, 5, 4, 2, 16, bvs.a.n(), bvs.a.n(), false);
		this.a(bqu, ctd, this.a - 6, 1, 5, this.a - 5, 2, 16, bvs.a.n(), bvs.a.n(), false);

		for (int integer13 = 5; integer13 <= 17; integer13 += 2) {
			this.a(bqu, bvs.av.n(), 4, 1, integer13, ctd);
			this.a(bqu, bvs.au.n(), 4, 2, integer13, ctd);
			this.a(bqu, bvs.av.n(), this.a - 5, 1, integer13, ctd);
			this.a(bqu, bvs.au.n(), this.a - 5, 2, integer13, ctd);
		}

		this.a(bqu, bvs.fG.n(), 10, 0, 7, ctd);
		this.a(bqu, bvs.fG.n(), 10, 0, 8, ctd);
		this.a(bqu, bvs.fG.n(), 9, 0, 9, ctd);
		this.a(bqu, bvs.fG.n(), 11, 0, 9, ctd);
		this.a(bqu, bvs.fG.n(), 8, 0, 10, ctd);
		this.a(bqu, bvs.fG.n(), 12, 0, 10, ctd);
		this.a(bqu, bvs.fG.n(), 7, 0, 10, ctd);
		this.a(bqu, bvs.fG.n(), 13, 0, 10, ctd);
		this.a(bqu, bvs.fG.n(), 9, 0, 11, ctd);
		this.a(bqu, bvs.fG.n(), 11, 0, 11, ctd);
		this.a(bqu, bvs.fG.n(), 10, 0, 12, ctd);
		this.a(bqu, bvs.fG.n(), 10, 0, 13, ctd);
		this.a(bqu, bvs.fQ.n(), 10, 0, 10, ctd);

		for (int integer13 = 0; integer13 <= this.a - 1; integer13 += this.a - 1) {
			this.a(bqu, bvs.av.n(), integer13, 2, 1, ctd);
			this.a(bqu, bvs.fG.n(), integer13, 2, 2, ctd);
			this.a(bqu, bvs.av.n(), integer13, 2, 3, ctd);
			this.a(bqu, bvs.av.n(), integer13, 3, 1, ctd);
			this.a(bqu, bvs.fG.n(), integer13, 3, 2, ctd);
			this.a(bqu, bvs.av.n(), integer13, 3, 3, ctd);
			this.a(bqu, bvs.fG.n(), integer13, 4, 1, ctd);
			this.a(bqu, bvs.au.n(), integer13, 4, 2, ctd);
			this.a(bqu, bvs.fG.n(), integer13, 4, 3, ctd);
			this.a(bqu, bvs.av.n(), integer13, 5, 1, ctd);
			this.a(bqu, bvs.fG.n(), integer13, 5, 2, ctd);
			this.a(bqu, bvs.av.n(), integer13, 5, 3, ctd);
			this.a(bqu, bvs.fG.n(), integer13, 6, 1, ctd);
			this.a(bqu, bvs.au.n(), integer13, 6, 2, ctd);
			this.a(bqu, bvs.fG.n(), integer13, 6, 3, ctd);
			this.a(bqu, bvs.fG.n(), integer13, 7, 1, ctd);
			this.a(bqu, bvs.fG.n(), integer13, 7, 2, ctd);
			this.a(bqu, bvs.fG.n(), integer13, 7, 3, ctd);
			this.a(bqu, bvs.av.n(), integer13, 8, 1, ctd);
			this.a(bqu, bvs.av.n(), integer13, 8, 2, ctd);
			this.a(bqu, bvs.av.n(), integer13, 8, 3, ctd);
		}

		for (int integer13 = 2; integer13 <= this.a - 3; integer13 += this.a - 3 - 2) {
			this.a(bqu, bvs.av.n(), integer13 - 1, 2, 0, ctd);
			this.a(bqu, bvs.fG.n(), integer13, 2, 0, ctd);
			this.a(bqu, bvs.av.n(), integer13 + 1, 2, 0, ctd);
			this.a(bqu, bvs.av.n(), integer13 - 1, 3, 0, ctd);
			this.a(bqu, bvs.fG.n(), integer13, 3, 0, ctd);
			this.a(bqu, bvs.av.n(), integer13 + 1, 3, 0, ctd);
			this.a(bqu, bvs.fG.n(), integer13 - 1, 4, 0, ctd);
			this.a(bqu, bvs.au.n(), integer13, 4, 0, ctd);
			this.a(bqu, bvs.fG.n(), integer13 + 1, 4, 0, ctd);
			this.a(bqu, bvs.av.n(), integer13 - 1, 5, 0, ctd);
			this.a(bqu, bvs.fG.n(), integer13, 5, 0, ctd);
			this.a(bqu, bvs.av.n(), integer13 + 1, 5, 0, ctd);
			this.a(bqu, bvs.fG.n(), integer13 - 1, 6, 0, ctd);
			this.a(bqu, bvs.au.n(), integer13, 6, 0, ctd);
			this.a(bqu, bvs.fG.n(), integer13 + 1, 6, 0, ctd);
			this.a(bqu, bvs.fG.n(), integer13 - 1, 7, 0, ctd);
			this.a(bqu, bvs.fG.n(), integer13, 7, 0, ctd);
			this.a(bqu, bvs.fG.n(), integer13 + 1, 7, 0, ctd);
			this.a(bqu, bvs.av.n(), integer13 - 1, 8, 0, ctd);
			this.a(bqu, bvs.av.n(), integer13, 8, 0, ctd);
			this.a(bqu, bvs.av.n(), integer13 + 1, 8, 0, ctd);
		}

		this.a(bqu, ctd, 8, 4, 0, 12, 6, 0, bvs.av.n(), bvs.av.n(), false);
		this.a(bqu, bvs.a.n(), 8, 6, 0, ctd);
		this.a(bqu, bvs.a.n(), 12, 6, 0, ctd);
		this.a(bqu, bvs.fG.n(), 9, 5, 0, ctd);
		this.a(bqu, bvs.au.n(), 10, 5, 0, ctd);
		this.a(bqu, bvs.fG.n(), 11, 5, 0, ctd);
		this.a(bqu, ctd, 8, -14, 8, 12, -11, 12, bvs.av.n(), bvs.av.n(), false);
		this.a(bqu, ctd, 8, -10, 8, 12, -10, 12, bvs.au.n(), bvs.au.n(), false);
		this.a(bqu, ctd, 8, -9, 8, 12, -9, 12, bvs.av.n(), bvs.av.n(), false);
		this.a(bqu, ctd, 8, -8, 8, 12, -1, 12, bvs.at.n(), bvs.at.n(), false);
		this.a(bqu, ctd, 9, -11, 9, 11, -1, 11, bvs.a.n(), bvs.a.n(), false);
		this.a(bqu, bvs.cq.n(), 10, -11, 10, ctd);
		this.a(bqu, ctd, 9, -13, 9, 11, -13, 11, bvs.bH.n(), bvs.a.n(), false);
		this.a(bqu, bvs.a.n(), 8, -11, 10, ctd);
		this.a(bqu, bvs.a.n(), 8, -10, 10, ctd);
		this.a(bqu, bvs.au.n(), 7, -10, 10, ctd);
		this.a(bqu, bvs.av.n(), 7, -11, 10, ctd);
		this.a(bqu, bvs.a.n(), 12, -11, 10, ctd);
		this.a(bqu, bvs.a.n(), 12, -10, 10, ctd);
		this.a(bqu, bvs.au.n(), 13, -10, 10, ctd);
		this.a(bqu, bvs.av.n(), 13, -11, 10, ctd);
		this.a(bqu, bvs.a.n(), 10, -11, 8, ctd);
		this.a(bqu, bvs.a.n(), 10, -10, 8, ctd);
		this.a(bqu, bvs.au.n(), 10, -10, 7, ctd);
		this.a(bqu, bvs.av.n(), 10, -11, 7, ctd);
		this.a(bqu, bvs.a.n(), 10, -11, 12, ctd);
		this.a(bqu, bvs.a.n(), 10, -10, 12, ctd);
		this.a(bqu, bvs.au.n(), 10, -10, 13, ctd);
		this.a(bqu, bvs.av.n(), 10, -11, 13, ctd);

		for (fz fz14 : fz.c.HORIZONTAL) {
			if (!this.e[fz14.d()]) {
				int integer15 = fz14.i() * 2;
				int integer16 = fz14.k() * 2;
				this.e[fz14.d()] = this.a(bqu, ctd, random, 10 + integer15, -11, 10 + integer16, dao.z);
			}
		}

		return true;
	}
}
