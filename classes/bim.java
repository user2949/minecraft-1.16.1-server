import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class bim extends bke {
	@Deprecated
	private final bvr a;

	public bim(bvr bvr, bke.a a) {
		super(a);
		this.a = bvr;
	}

	@Override
	public ang a(blv blv) {
		ang ang3 = this.a(new bin(blv));
		return !ang3.a() && this.s() ? this.a(blv.e, blv.b, blv.c).a() : ang3;
	}

	public ang a(bin bin) {
		if (!bin.b()) {
			return ang.FAIL;
		} else {
			bin bin3 = this.b(bin);
			if (bin3 == null) {
				return ang.FAIL;
			} else {
				cfj cfj4 = this.c(bin3);
				if (cfj4 == null) {
					return ang.FAIL;
				} else if (!this.a(bin3, cfj4)) {
					return ang.FAIL;
				} else {
					fu fu5 = bin3.a();
					bqb bqb6 = bin3.o();
					bec bec7 = bin3.m();
					bki bki8 = bin3.l();
					cfj cfj9 = bqb6.d_(fu5);
					bvr bvr10 = cfj9.b();
					if (bvr10 == cfj4.b()) {
						cfj9 = this.a(fu5, bqb6, bki8, cfj9);
						this.a(fu5, bqb6, bec7, bki8, cfj9);
						bvr10.a(bqb6, fu5, cfj9, bec7, bki8);
						if (bec7 instanceof ze) {
							aa.y.a((ze)bec7, fu5, bki8);
						}
					}

					cbh cbh11 = cfj9.o();
					bqb6.a(bec7, fu5, this.a(cfj9), acm.BLOCKS, (cbh11.a() + 1.0F) / 2.0F, cbh11.b() * 0.8F);
					if (bec7 == null || !bec7.bJ.d) {
						bki8.g(1);
					}

					return ang.a(bqb6.v);
				}
			}
		}
	}

	protected ack a(cfj cfj) {
		return cfj.o().e();
	}

	@Nullable
	public bin b(bin bin) {
		return bin;
	}

	protected boolean a(fu fu, bqb bqb, @Nullable bec bec, bki bki, cfj cfj) {
		return a(bqb, bec, fu, bki);
	}

	@Nullable
	protected cfj c(bin bin) {
		cfj cfj3 = this.e().a(bin);
		return cfj3 != null && this.b(bin, cfj3) ? cfj3 : null;
	}

	private cfj a(fu fu, bqb bqb, bki bki, cfj cfj) {
		cfj cfj6 = cfj;
		le le7 = bki.o();
		if (le7 != null) {
			le le8 = le7.p("BlockStateTag");
			cfk<bvr, cfj> cfk9 = cfj.b().m();

			for (String string11 : le8.d()) {
				cgl<?> cgl12 = cfk9.a(string11);
				if (cgl12 != null) {
					String string13 = le8.c(string11).f_();
					cfj6 = a(cfj6, cgl12, string13);
				}
			}
		}

		if (cfj6 != cfj) {
			bqb.a(fu, cfj6, 2);
		}

		return cfj6;
	}

	private static <T extends Comparable<T>> cfj a(cfj cfj, cgl<T> cgl, String string) {
		return (cfj)cgl.b(string).map(comparable -> cfj.a(cgl, comparable)).orElse(cfj);
	}

	protected boolean b(bin bin, cfj cfj) {
		bec bec4 = bin.m();
		der der5 = bec4 == null ? der.a() : der.a(bec4);
		return (!this.d() || cfj.a((bqd)bin.o(), bin.a())) && bin.o().a(cfj, bin.a(), der5);
	}

	protected boolean d() {
		return true;
	}

	protected boolean a(bin bin, cfj cfj) {
		return bin.o().a(bin.a(), cfj, 11);
	}

	public static boolean a(bqb bqb, @Nullable bec bec, fu fu, bki bki) {
		MinecraftServer minecraftServer5 = bqb.l();
		if (minecraftServer5 == null) {
			return false;
		} else {
			le le6 = bki.b("BlockEntityTag");
			if (le6 != null) {
				cdl cdl7 = bqb.c(fu);
				if (cdl7 != null) {
					if (!bqb.v && cdl7.t() && (bec == null || !bec.eV())) {
						return false;
					}

					le le8 = cdl7.a(new le());
					le le9 = le8.g();
					le8.a(le6);
					le8.b("x", fu.u());
					le8.b("y", fu.v());
					le8.b("z", fu.w());
					if (!le8.equals(le9)) {
						cdl7.a(bqb.d_(fu), le8);
						cdl7.Z_();
						return true;
					}
				}
			}

			return false;
		}
	}

	@Override
	public String a() {
		return this.e().i();
	}

	@Override
	public void a(biy biy, gi<bki> gi) {
		if (this.a(biy)) {
			this.e().a(biy, gi);
		}
	}

	public bvr e() {
		return this.a;
	}

	public void a(Map<bvr, bke> map, bke bke) {
		map.put(this.e(), bke);
	}
}
