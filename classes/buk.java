import bre.f;
import com.google.common.collect.ImmutableList;

public class buk extends bre {
	protected buk() {
		super(
			new bre.a()
				.a(cvw.ad, cvw.Q)
				.a(bre.f.NONE)
				.a(bre.b.NETHER)
				.a(0.1F)
				.b(0.2F)
				.c(2.0F)
				.d(0.0F)
				.a(
					new bri.a()
						.b(4159204)
						.c(329011)
						.a(1705242)
						.a(new bqx(hh.ao, 0.01428F))
						.a(acl.o)
						.a(new bqw(acl.p, 6000, 8, 2.0))
						.a(new bqv(acl.n, 0.0111))
						.a(acj.a(acl.is))
						.a()
				)
				.a(null)
				.a(ImmutableList.of(new bre.d(0.0F, 0.5F, 0.0F, 0.0F, 0.375F)))
		);
		this.a(brf.o);
		this.a(brf.s);
		this.a(brf.E);
		this.a(cin.a.AIR, a(cjh.b, new cod(0.2F)));
		this.a(cin.b.VEGETAL_DECORATION, ckt.g.b(brf.aL).a(csc.p.a(new cnl(20, 8, 16, 256))));
		brf.ab(this);
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.g.b(brf.aM).a(csc.n.a(new cnl(8, 4, 8, 128))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.e.b(brf.ap).a(csc.A.a(new csf(10))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.e.b(brf.aq).a(csc.A.a(new csf(10))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.p.b(cnr.k).a(csc.I.a(new csf(10))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.p.b(cnr.k).a(csc.n.a(new cnl(10, 0, 0, 128))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.z.b(new coc(coc.a.NETHERRACK, bvs.iJ.n(), 33)).a(csc.B.a(new csf(4))));
		this.a(cin.b.UNDERGROUND_DECORATION, ckt.g.b(brf.aO).a(csc.n.a(new cnl(16, 10, 20, 128))));
		brf.av(this);
		brf.as(this);
		this.a(apa.MONSTER, new bre.g(aoq.u, 1, 4, 4));
		this.a(apa.CREATURE, new bre.g(aoq.aE, 60, 1, 2));
		this.a(aoq.u, 1.0, 0.12);
	}
}
