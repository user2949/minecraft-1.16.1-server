import bre.f;

public final class bsq extends bre {
	public bsq() {
		super(
			new bre.a()
				.a(cvw.S, new cvx(bvs.cE.n(), bvs.j.n(), bvs.E.n()))
				.a(bre.f.SNOW)
				.a(bre.b.ICY)
				.a(0.425F)
				.b(0.45000002F)
				.c(0.0F)
				.d(0.5F)
				.a(new bri.a().b(4159204).c(329011).a(12638463).a(bqw.b).a())
				.a("snowy_tundra")
		);
		brf.b(this);
		this.a(brf.y);
		brf.d(this);
		brf.f(this);
		brf.h(this);
		this.a(cin.b.SURFACE_STRUCTURES, ckt.o.b(cnr.k).a(csc.b.a(new csf(3))));
		this.a(cin.b.SURFACE_STRUCTURES, ckt.x.b(new cns(2)).a(csc.b.a(new csf(2))));
		brf.i(this);
		brf.j(this);
		brf.n(this);
		brf.H(this);
		brf.W(this);
		brf.Y(this);
		brf.ab(this);
		brf.ac(this);
		brf.ao(this);
		brf.ar(this);
		this.a(apa.CREATURE, new bre.g(aoq.an, 10, 2, 3));
		this.a(apa.CREATURE, new bre.g(aoq.ak, 1, 1, 2));
		this.a(apa.AMBIENT, new bre.g(aoq.d, 10, 8, 8));
		this.a(apa.MONSTER, new bre.g(aoq.aB, 100, 4, 4));
		this.a(apa.MONSTER, new bre.g(aoq.aX, 95, 4, 4));
		this.a(apa.MONSTER, new bre.g(aoq.aZ, 5, 1, 1));
		this.a(apa.MONSTER, new bre.g(aoq.m, 100, 4, 4));
		this.a(apa.MONSTER, new bre.g(aoq.aw, 100, 4, 4));
		this.a(apa.MONSTER, new bre.g(aoq.u, 10, 1, 4));
		this.a(apa.MONSTER, new bre.g(aoq.aR, 5, 1, 1));
		this.a(apa.MONSTER, new bre.g(aoq.au, 20, 4, 4));
		this.a(apa.MONSTER, new bre.g(aoq.aD, 80, 4, 4));
	}

	@Override
	public float f() {
		return 0.07F;
	}
}
