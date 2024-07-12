import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class cub extends cty {
	private static final Logger d = LogManager.getLogger();
	protected cve a;
	protected cvb b;
	protected fu c;

	public cub(cmm cmm, int integer) {
		super(cmm, integer);
	}

	public cub(cmm cmm, le le) {
		super(cmm, le);
		this.c = new fu(le.h("TPX"), le.h("TPY"), le.h("TPZ"));
	}

	protected void a(cve cve, fu fu, cvb cvb) {
		this.a = cve;
		this.a(fz.NORTH);
		this.c = fu;
		this.b = cvb;
		this.n = cve.b(cvb, fu);
	}

	@Override
	protected void a(le le) {
		le.b("TPX", this.c.u());
		le.b("TPY", this.c.v());
		le.b("TPZ", this.c.w());
	}

	@Override
	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
		this.b.a(ctd);
		this.n = this.a.b(this.b, this.c);
		if (this.a.a(bqu, this.c, fu, this.b, random, 2)) {
			for (cve.c c11 : this.a.a(this.c, this.b, bvs.mY)) {
				if (c11.c != null) {
					cgq cgq12 = cgq.valueOf(c11.c.l("mode"));
					if (cgq12 == cgq.DATA) {
						this.a(c11.c.l("metadata"), c11.a, bqu, random, ctd);
					}
				}
			}

			for (cve.c c12 : this.a.a(this.c, this.b, bvs.mZ)) {
				if (c12.c != null) {
					String string13 = c12.c.l("final_state");
					ef ef14 = new ef(new StringReader(string13), false);
					cfj cfj15 = bvs.a.n();

					try {
						ef14.a(true);
						cfj cfj16 = ef14.b();
						if (cfj16 != null) {
							cfj15 = cfj16;
						} else {
							d.error("Error while parsing blockstate {} in jigsaw block @ {}", string13, c12.a);
						}
					} catch (CommandSyntaxException var16) {
						d.error("Error while parsing blockstate {} in jigsaw block @ {}", string13, c12.a);
					}

					bqu.a(c12.a, cfj15, 3);
				}
			}
		}

		return true;
	}

	protected abstract void a(String string, fu fu, bqc bqc, Random random, ctd ctd);

	@Override
	public void a(int integer1, int integer2, int integer3) {
		super.a(integer1, integer2, integer3);
		this.c = this.c.b(integer1, integer2, integer3);
	}

	@Override
	public cap ap_() {
		return this.b.d();
	}
}
