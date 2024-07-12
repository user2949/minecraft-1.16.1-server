import bre.f;
import com.google.common.collect.ImmutableList;

public class brc extends bre {
	protected brc() {
		super(
			new bre.a()
				.a(cvw.af, cvw.R)
				.a(bre.f.NONE)
				.a(bre.b.NETHER)
				.a(0.1F)
				.b(0.2F)
				.c(2.0F)
				.d(0.0F)
				.a(
					new bri.a()
						.b(4159204)
						.c(4341314)
						.a(6840176)
						.a(new bqx(hh.at, 0.118093334F))
						.a(acl.c)
						.a(new bqw(acl.d, 6000, 8, 2.0))
						.a(new bqv(acl.b, 0.0111))
						.a(acj.a(acl.io))
						.a()
				)
				.a(null)
				.a(ImmutableList.of(new bre.d(-0.5F, 0.0F, 0.0F, 0.0F, 0.175F)))
		);
		this.a(brf.E);
		this.a(cin.a.AIR, a(cjh.b, new cod(0.2F)));
		this.a(brf.o);
		this.a(cin.b.SURFACE_STRUCTURES, ckt.Q.b(brf.aV).a(csc.b.a(new csf(40))));
		this.a(cin.b.VEGETAL_DECORATION, ckt.g.b(brf.aL).a(csc.p.a(new cnl(40, 8, 16, 256))));
		this.a(cin.b.SURFACE_STRUCTURES, ckt.P.b(brf.aR).a(csc.b.a(new csf(4))));
		this.a(cin.b.SURFACE_STRUCTURES, ckt.P.b(brf.aS).a(csc.b.a(new csf(2))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.R.b(brf.aT).a(csc.n.a(new cnl(75, 0, 0, 128))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.R.b(brf.aU).a(csc.n.a(new cnl(25, 0, 0, 128))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.g.b(brf.aN).a(csc.n.a(new cnl(16, 4, 8, 128))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.e.b(brf.ap).a(csc.A.a(new csf(10))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.e.b(brf.aq).a(csc.A.a(new csf(10))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.p.b(cnr.k).a(csc.I.a(new csf(10))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.p.b(cnr.k).a(csc.n.a(new cnl(10, 0, 0, 128))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.e.b(brf.at).a(csc.r.a(new cni(0.5F, 0, 0, 128))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.e.b(brf.as).a(csc.r.a(new cni(0.5F, 0, 0, 128))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.z.b(new coc(coc.a.NETHERRACK, bvs.iJ.n(), 33)).a(csc.B.a(new csf(4))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.g.b(brf.aO).a(csc.n.a(new cnl(32, 10, 20, 128))));
		brf.a(this, 20, 32);
		brf.at(this);
		this.a(apa.MONSTER, new bre.g(aoq.D, 40, 1, 1));
		this.a(apa.MONSTER, new bre.g(aoq.S, 100, 2, 5));
		this.a(apa.CREATURE, new bre.g(aoq.aE, 60, 1, 2));
	}
}
