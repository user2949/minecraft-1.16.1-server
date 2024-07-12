public class bwf extends bvr {
	public static final cgi a = cfz.ar;
	private static final dfg c = a(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
	protected static final dfg b = dfd.a(
		dfd.b(), dfd.a(a(0.0, 0.0, 4.0, 16.0, 3.0, 12.0), a(4.0, 0.0, 0.0, 12.0, 3.0, 16.0), a(2.0, 0.0, 2.0, 14.0, 3.0, 14.0), c), deq.e
	);

	public bwf(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b;
	}

	@Override
	public dfg a_(cfj cfj, bpg bpg, fu fu) {
		return c;
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		int integer6 = (Integer)cfj.c(a);
		float float7 = (float)fu.v() + (6.0F + (float)(3 * integer6)) / 16.0F;
		if (!bqb.v && aom.bm() && integer6 > 0 && aom.cD() <= (double)float7) {
			aom.ah();
			this.a(bqb, fu, cfj, integer6 - 1);
		}
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		bki bki8 = bec.b(anf);
		if (bki8.a()) {
			return ang.PASS;
		} else {
			int integer9 = (Integer)cfj.c(a);
			bke bke10 = bki8.b();
			if (bke10 == bkk.lL) {
				if (integer9 < 3 && !bqb.v) {
					if (!bec.bJ.d) {
						bec.a(anf, new bki(bkk.lK));
					}

					bec.a(acu.U);
					this.a(bqb, fu, cfj, 3);
					bqb.a(null, fu, acl.bj, acm.BLOCKS, 1.0F, 1.0F);
				}

				return ang.a(bqb.v);
			} else if (bke10 == bkk.lK) {
				if (integer9 == 3 && !bqb.v) {
					if (!bec.bJ.d) {
						bki8.g(1);
						if (bki8.a()) {
							bec.a(anf, new bki(bkk.lL));
						} else if (!bec.bt.e(new bki(bkk.lL))) {
							bec.a(new bki(bkk.lL), false);
						}
					}

					bec.a(acu.V);
					this.a(bqb, fu, cfj, 0);
					bqb.a(null, fu, acl.bm, acm.BLOCKS, 1.0F, 1.0F);
				}

				return ang.a(bqb.v);
			} else if (bke10 == bkk.nw) {
				if (integer9 > 0 && !bqb.v) {
					if (!bec.bJ.d) {
						bki bki11 = bmd.a(new bki(bkk.nv), bme.b);
						bec.a(acu.V);
						bki8.g(1);
						if (bki8.a()) {
							bec.a(anf, bki11);
						} else if (!bec.bt.e(bki11)) {
							bec.a(bki11, false);
						} else if (bec instanceof ze) {
							((ze)bec).a(bec.bv);
						}
					}

					bqb.a(null, fu, acl.bb, acm.BLOCKS, 1.0F, 1.0F);
					this.a(bqb, fu, cfj, integer9 - 1);
				}

				return ang.a(bqb.v);
			} else if (bke10 == bkk.nv && bmd.d(bki8) == bme.b) {
				if (integer9 < 3 && !bqb.v) {
					if (!bec.bJ.d) {
						bki bki11 = new bki(bkk.nw);
						bec.a(acu.V);
						bec.a(anf, bki11);
						if (bec instanceof ze) {
							((ze)bec).a(bec.bv);
						}
					}

					bqb.a(null, fu, acl.ba, acm.BLOCKS, 1.0F, 1.0F);
					this.a(bqb, fu, cfj, integer9 + 1);
				}

				return ang.a(bqb.v);
			} else {
				if (integer9 > 0 && bke10 instanceof bji) {
					bji bji11 = (bji)bke10;
					if (bji11.a(bki8) && !bqb.v) {
						bji11.c(bki8);
						this.a(bqb, fu, cfj, integer9 - 1);
						bec.a(acu.W);
						return ang.SUCCESS;
					}
				}

				if (integer9 > 0 && bke10 instanceof bij) {
					if (cdc.b(bki8) > 0 && !bqb.v) {
						bki bki11 = bki8.i();
						bki11.e(1);
						cdc.c(bki11);
						bec.a(acu.X);
						if (!bec.bJ.d) {
							bki8.g(1);
							this.a(bqb, fu, cfj, integer9 - 1);
						}

						if (bki8.a()) {
							bec.a(anf, bki11);
						} else if (!bec.bt.e(bki11)) {
							bec.a(bki11, false);
						} else if (bec instanceof ze) {
							((ze)bec).a(bec.bv);
						}
					}

					return ang.a(bqb.v);
				} else if (integer9 > 0 && bke10 instanceof bim) {
					bvr bvr11 = ((bim)bke10).e();
					if (bvr11 instanceof cav && !bqb.s_()) {
						bki bki12 = new bki(bvs.iP, 1);
						if (bki8.n()) {
							bki12.c(bki8.o().g());
						}

						bec.a(anf, bki12);
						this.a(bqb, fu, cfj, integer9 - 1);
						bec.a(acu.Y);
						return ang.SUCCESS;
					} else {
						return ang.CONSUME;
					}
				} else {
					return ang.PASS;
				}
			}
		}
	}

	public void a(bqb bqb, fu fu, cfj cfj, int integer) {
		bqb.a(fu, cfj.a(a, Integer.valueOf(aec.a(integer, 0, 3))), 2);
		bqb.c(fu, this);
	}

	@Override
	public void c(bqb bqb, fu fu) {
		if (bqb.t.nextInt(20) == 1) {
			float float4 = bqb.v(fu).b(fu);
			if (!(float4 < 0.15F)) {
				cfj cfj5 = bqb.d_(fu);
				if ((Integer)cfj5.c(a) < 3) {
					bqb.a(fu, cfj5.a(a), 2);
				}
			}
		}
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		return (Integer)cfj.c(a);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bwf.a);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
