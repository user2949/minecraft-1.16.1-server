import java.util.Map;
import java.util.function.BiConsumer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bgk extends bhg {
	private static final Logger g = LogManager.getLogger();
	private int h;
	private String i;
	private final bgw j = bgw.a();

	public bgk(int integer, beb beb) {
		this(integer, beb, bgs.a);
	}

	public bgk(int integer, beb beb, bgs bgs) {
		super(bhk.h, integer, beb, bgs);
		this.a(this.j);
	}

	@Override
	protected boolean a(cfj cfj) {
		return cfj.a(acx.F);
	}

	@Override
	protected boolean b(bec bec, boolean boolean2) {
		return (bec.bJ.d || bec.bK >= this.j.b()) && this.j.b() > 0;
	}

	@Override
	protected bki a(bec bec, bki bki) {
		if (!bec.bJ.d) {
			bec.c(-this.j.b());
		}

		this.d.a(0, bki.b);
		if (this.h > 0) {
			bki bki4 = this.d.a(1);
			if (!bki4.a() && bki4.E() > this.h) {
				bki4.g(this.h);
				this.d.a(1, bki4);
			} else {
				this.d.a(1, bki.b);
			}
		} else {
			this.d.a(1, bki.b);
		}

		this.j.a(0);
		this.e.a((BiConsumer<bqb, fu>)((bqb, fu) -> {
			cfj cfj4 = bqb.d_(fu);
			if (!bec.bJ.d && cfj4.a(acx.F) && bec.cX().nextFloat() < 0.12F) {
				cfj cfj5 = buv.c(cfj4);
				if (cfj5 == null) {
					bqb.a(fu, false);
					bqb.c(1029, fu, 0);
				} else {
					bqb.a(fu, cfj5, 2);
					bqb.c(1030, fu, 0);
				}
			} else {
				bqb.c(1030, fu, 0);
			}
		}));
		return bki;
	}

	@Override
	public void e() {
		bki bki2 = this.d.a(0);
		this.j.a(1);
		int integer3 = 0;
		int integer4 = 0;
		int integer5 = 0;
		if (bki2.a()) {
			this.c.a(0, bki.b);
			this.j.a(0);
		} else {
			bki bki6 = bki2.i();
			bki bki7 = this.d.a(1);
			Map<bnw, Integer> map8 = bny.a(bki6);
			integer4 += bki2.B() + (bki7.a() ? 0 : bki7.B());
			this.h = 0;
			if (!bki7.a()) {
				boolean boolean9 = bki7.b() == bkk.pp && !bjm.d(bki7).isEmpty();
				if (bki6.e() && bki6.b().a(bki2, bki7)) {
					int integer10 = Math.min(bki6.g(), bki6.h() / 4);
					if (integer10 <= 0) {
						this.c.a(0, bki.b);
						this.j.a(0);
						return;
					}

					int integer11;
					for (integer11 = 0; integer10 > 0 && integer11 < bki7.E(); integer11++) {
						int integer12 = bki6.g() - integer10;
						bki6.b(integer12);
						integer3++;
						integer10 = Math.min(bki6.g(), bki6.h() / 4);
					}

					this.h = integer11;
				} else {
					if (!boolean9 && (bki6.b() != bki7.b() || !bki6.e())) {
						this.c.a(0, bki.b);
						this.j.a(0);
						return;
					}

					if (bki6.e() && !boolean9) {
						int integer10x = bki2.h() - bki2.g();
						int integer11 = bki7.h() - bki7.g();
						int integer12 = integer11 + bki6.h() * 12 / 100;
						int integer13 = integer10x + integer12;
						int integer14 = bki6.h() - integer13;
						if (integer14 < 0) {
							integer14 = 0;
						}

						if (integer14 < bki6.g()) {
							bki6.b(integer14);
							integer3 += 2;
						}
					}

					Map<bnw, Integer> map10 = bny.a(bki7);
					boolean boolean11 = false;
					boolean boolean12 = false;

					for (bnw bnw14 : map10.keySet()) {
						if (bnw14 != null) {
							int integer15 = (Integer)map8.getOrDefault(bnw14, 0);
							int integer16 = (Integer)map10.get(bnw14);
							integer16 = integer15 == integer16 ? integer16 + 1 : Math.max(integer16, integer15);
							boolean boolean17 = bnw14.a(bki2);
							if (this.f.bJ.d || bki2.b() == bkk.pp) {
								boolean17 = true;
							}

							for (bnw bnw19 : map8.keySet()) {
								if (bnw19 != bnw14 && !bnw14.b(bnw19)) {
									boolean17 = false;
									integer3++;
								}
							}

							if (!boolean17) {
								boolean12 = true;
							} else {
								boolean11 = true;
								if (integer16 > bnw14.a()) {
									integer16 = bnw14.a();
								}

								map8.put(bnw14, integer16);
								int integer18 = 0;
								switch (bnw14.d()) {
									case COMMON:
										integer18 = 1;
										break;
									case UNCOMMON:
										integer18 = 2;
										break;
									case RARE:
										integer18 = 4;
										break;
									case VERY_RARE:
										integer18 = 8;
								}

								if (boolean9) {
									integer18 = Math.max(1, integer18 / 2);
								}

								integer3 += integer18 * integer16;
								if (bki2.E() > 1) {
									integer3 = 40;
								}
							}
						}
					}

					if (boolean12 && !boolean11) {
						this.c.a(0, bki.b);
						this.j.a(0);
						return;
					}
				}
			}

			if (StringUtils.isBlank(this.i)) {
				if (bki2.t()) {
					integer5 = 1;
					integer3 += integer5;
					bki6.s();
				}
			} else if (!this.i.equals(bki2.r().getString())) {
				integer5 = 1;
				integer3 += integer5;
				bki6.a(new nd(this.i));
			}

			this.j.a(integer4 + integer3);
			if (integer3 <= 0) {
				bki6 = bki.b;
			}

			if (integer5 == integer3 && integer5 > 0 && this.j.b() >= 40) {
				this.j.a(39);
			}

			if (this.j.b() >= 40 && !this.f.bJ.d) {
				bki6 = bki.b;
			}

			if (!bki6.a()) {
				int integer9 = bki6.B();
				if (!bki7.a() && integer9 < bki7.B()) {
					integer9 = bki7.B();
				}

				if (integer5 != integer3 || integer5 == 0) {
					integer9 = d(integer9);
				}

				bki6.c(integer9);
				bny.a(map8, bki6);
			}

			this.c.a(0, bki6);
			this.c();
		}
	}

	public static int d(int integer) {
		return integer * 2 + 1;
	}

	public void a(String string) {
		this.i = string;
		if (this.a(2).f()) {
			bki bki3 = this.a(2).e();
			if (StringUtils.isBlank(string)) {
				bki3.s();
			} else {
				bki3.a(new nd(this.i));
			}
		}

		this.e();
	}
}
