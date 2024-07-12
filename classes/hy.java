import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class hy implements Consumer<BiConsumer<uh, daw.a>> {
	private static final ddm.a a = ddt.a(bo.a.a().a(new az(boa.u, bx.d.b(1))));
	private static final ddm.a b = a.a();
	private static final ddm.a c = ddt.a(bo.a.a().a(bkk.ng));
	private static final ddm.a d = c.a(a);
	private static final ddm.a e = d.a();
	private static final Set<bke> f = (Set<bke>)Stream.of(
			bvs.ef,
			bvs.es,
			bvs.kW,
			bvs.fc,
			bvs.fe,
			bvs.fi,
			bvs.fg,
			bvs.fk,
			bvs.fm,
			bvs.iP,
			bvs.jf,
			bvs.jb,
			bvs.jc,
			bvs.iZ,
			bvs.iX,
			bvs.jd,
			bvs.iT,
			bvs.iY,
			bvs.iV,
			bvs.iS,
			bvs.iR,
			bvs.iW,
			bvs.ja,
			bvs.je,
			bvs.iQ,
			bvs.iU
		)
		.map(bqa::h)
		.collect(ImmutableSet.toImmutableSet());
	private static final float[] g = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
	private static final float[] h = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
	private final Map<uh, daw.a> i = Maps.<uh, daw.a>newHashMap();

	private static <T> T a(bqa bqa, dce<T> dce) {
		return !f.contains(bqa.h()) ? dce.b(dbw.c()) : dce.c();
	}

	private static <T> T a(bqa bqa, ddf<T> ddf) {
		return !f.contains(bqa.h()) ? ddf.b(ddi.c()) : ddf.c();
	}

	private static daw.a a(bqa bqa) {
		return daw.b().a(a(bqa, dav.a().a(dap.a(1)).a(dbl.a(bqa))));
	}

	private static daw.a a(bvr bvr, ddm.a a, dbo.a<?> a) {
		return daw.b().a(dav.a().a(dap.a(1)).a(((dbq.a)dbl.a(bvr).a(a)).a(a)));
	}

	private static daw.a a(bvr bvr, dbo.a<?> a) {
		return a(bvr, hy.a, a);
	}

	private static daw.a b(bvr bvr, dbo.a<?> a) {
		return a(bvr, c, a);
	}

	private static daw.a c(bvr bvr, dbo.a<?> a) {
		return a(bvr, d, a);
	}

	private static daw.a b(bvr bvr, bqa bqa) {
		return a(bvr, (dbo.a<?>)a((bqa)bvr, dbl.a(bqa)));
	}

	private static daw.a a(bqa bqa, daz daz) {
		return daw.b().a(dav.a().a(dap.a(1)).a(a(bqa, dbl.a(bqa).a(dco.a(daz)))));
	}

	private static daw.a a(bvr bvr, bqa bqa, daz daz) {
		return a(bvr, a((bqa)bvr, dbl.a(bqa).a(dco.a(daz))));
	}

	private static daw.a b(bqa bqa) {
		return daw.b().a(dav.a().a(a).a(dap.a(1)).a(dbl.a(bqa)));
	}

	private static daw.a c(bqa bqa) {
		return daw.b().a(a(bvs.ev, dav.a().a(dap.a(1)).a(dbl.a(bvs.ev)))).a(a(bqa, dav.a().a(dap.a(1)).a(dbl.a(bqa))));
	}

	private static daw.a e(bvr bvr) {
		return daw.b().a(dav.a().a(dap.a(1)).a(a((bqa)bvr, dbl.a(bvr).a(dco.a(dap.a(2)).a(ddl.a(bvr).a(ck.a.a().a(caz.a, cgo.DOUBLE)))))));
	}

	private static <T extends Comparable<T> & aeh> daw.a a(bvr bvr, cgl<T> cgl, T comparable) {
		return daw.b().a(a(bvr, dav.a().a(dap.a(1)).a(dbl.a(bvr).a(ddl.a(bvr).a(ck.a.a().a(cgl, comparable))))));
	}

	private static daw.a f(bvr bvr) {
		return daw.b().a(a(bvr, dav.a().a(dap.a(1)).a(dbl.a(bvr).a(dby.a(dby.a.BLOCK_ENTITY)))));
	}

	private static daw.a g(bvr bvr) {
		return daw.b()
			.a(
				a(
					bvr,
					dav.a()
						.a(dap.a(1))
						.a(
							dbl.a(bvr)
								.a(dby.a(dby.a.BLOCK_ENTITY))
								.a(
									dbz.a(dbz.c.BLOCK_ENTITY)
										.a("Lock", "BlockEntityTag.Lock")
										.a("LootTable", "BlockEntityTag.LootTable")
										.a("LootTableSeed", "BlockEntityTag.LootTableSeed")
								)
								.a(dcm.c().a(dbi.a(cav.b)))
						)
				)
			);
	}

	private static daw.a h(bvr bvr) {
		return daw.b()
			.a(a(bvr, dav.a().a(dap.a(1)).a(dbl.a(bvr).a(dby.a(dby.a.BLOCK_ENTITY)).a(dbz.a(dbz.c.BLOCK_ENTITY).a("Patterns", "BlockEntityTag.Patterns")))));
	}

	private static daw.a i(bvr bvr) {
		return daw.b().a(dav.a().a(a).a(dap.a(1)).a(dbl.a(bvr).a(dbz.a(dbz.c.BLOCK_ENTITY).a("Bees", "BlockEntityTag.Bees")).a(dbx.a(bvr).a(bvn.b))));
	}

	private static daw.a j(bvr bvr) {
		return daw.b()
			.a(dav.a().a(dap.a(1)).a(((dbq.a)dbl.a(bvr).a(a)).a(dbz.a(dbz.c.BLOCK_ENTITY).a("Bees", "BlockEntityTag.Bees")).a(dbx.a(bvr).a(bvn.b)).a(dbl.a(bvr))));
	}

	private static daw.a a(bvr bvr, bke bke) {
		return a(bvr, a((bqa)bvr, dbl.a(bke).a(dbv.a(boa.w))));
	}

	private static daw.a c(bvr bvr, bqa bqa) {
		return a(bvr, a((bqa)bvr, dbl.a(bqa).a(dco.a(dbb.a(-6.0F, 2.0F))).a(dcf.a(das.a(0)))));
	}

	private static daw.a k(bvr bvr) {
		return b(bvr, a((bqa)bvr, ((dbq.a)dbl.a(bkk.kV).a(ddr.a(0.125F))).a(dbv.a(boa.w, 2))));
	}

	private static daw.a b(bvr bvr, bke bke) {
		return daw.b()
			.a(
				a(
					bvr,
					dav.a()
						.a(dap.a(1))
						.a(
							dbl.a(bke)
								.a(dco.a(dan.a(3, 0.06666667F)).a(ddl.a(bvr).a(ck.a.a().a(cbp.a, 0))))
								.a(dco.a(dan.a(3, 0.13333334F)).a(ddl.a(bvr).a(ck.a.a().a(cbp.a, 1))))
								.a(dco.a(dan.a(3, 0.2F)).a(ddl.a(bvr).a(ck.a.a().a(cbp.a, 2))))
								.a(dco.a(dan.a(3, 0.26666668F)).a(ddl.a(bvr).a(ck.a.a().a(cbp.a, 3))))
								.a(dco.a(dan.a(3, 0.33333334F)).a(ddl.a(bvr).a(ck.a.a().a(cbp.a, 4))))
								.a(dco.a(dan.a(3, 0.4F)).a(ddl.a(bvr).a(ck.a.a().a(cbp.a, 5))))
								.a(dco.a(dan.a(3, 0.46666667F)).a(ddl.a(bvr).a(ck.a.a().a(cbp.a, 6))))
								.a(dco.a(dan.a(3, 0.53333336F)).a(ddl.a(bvr).a(ck.a.a().a(cbp.a, 7))))
						)
				)
			);
	}

	private static daw.a c(bvr bvr, bke bke) {
		return daw.b().a(a(bvr, dav.a().a(dap.a(1)).a(dbl.a(bke).a(dco.a(dan.a(3, 0.53333336F))))));
	}

	private static daw.a d(bqa bqa) {
		return daw.b().a(dav.a().a(dap.a(1)).a(c).a(dbl.a(bqa)));
	}

	private static daw.a a(bvr bvr1, bvr bvr2, float... arr) {
		return c(bvr1, ((dbq.a)a((bqa)bvr1, dbl.a(bvr2))).a(ddd.a(boa.w, arr)))
			.a(dav.a().a(dap.a(1)).a(e).a(a((bqa)bvr1, dbl.a(bkk.kB).a(dco.a(dbb.a(1.0F, 2.0F)))).a(ddd.a(boa.w, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
	}

	private static daw.a b(bvr bvr1, bvr bvr2, float... arr) {
		return a(bvr1, bvr2, arr)
			.a(dav.a().a(dap.a(1)).a(e).a(((dbq.a)a((bqa)bvr1, dbl.a(bkk.ke))).a(ddd.a(boa.w, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
	}

	private static daw.a a(bvr bvr, bke bke2, bke bke3, ddm.a a) {
		return a((bqa)bvr, daw.b().a(dav.a().a(((dbq.a)dbl.a(bke2).a(a)).a(dbl.a(bke3)))).a(dav.a().a(a).a(dbl.a(bke3).a(dbv.a(boa.w, 0.5714286F, 3)))));
	}

	private static daw.a l(bvr bvr) {
		return daw.b().a(dav.a().a(c).a(dbl.a(bvr).a(dco.a(dap.a(2)))));
	}

	private static daw.a b(bvr bvr1, bvr bvr2) {
		dbo.a<?> a3 = ((dbq.a)dbl.a(bvr2).a(dco.a(dap.a(2))).a(c)).a(((dbq.a)a((bqa)bvr1, dbl.a(bkk.kV))).a(ddr.a(0.125F)));
		return daw.b()
			.a(
				dav.a()
					.a(a3)
					.a(ddl.a(bvr1).a(ck.a.a().a(bxg.a, cgf.LOWER)))
					.a(ddk.a(bu.a.a().a(al.a.a().a(bvr1).a(ck.a.a().a(bxg.a, cgf.UPPER).b()).b()), new fu(0, 1, 0)))
			)
			.a(
				dav.a()
					.a(a3)
					.a(ddl.a(bvr1).a(ck.a.a().a(bxg.a, cgf.UPPER)))
					.a(ddk.a(bu.a.a().a(al.a.a().a(bvr1).a(ck.a.a().a(bxg.a, cgf.LOWER).b()).b()), new fu(0, -1, 0)))
			);
	}

	public static daw.a a() {
		return daw.b();
	}

	public void accept(BiConsumer<uh, daw.a> biConsumer) {
		this.d(bvs.c);
		this.d(bvs.d);
		this.d(bvs.e);
		this.d(bvs.f);
		this.d(bvs.g);
		this.d(bvs.h);
		this.d(bvs.j);
		this.d(bvs.k);
		this.d(bvs.m);
		this.d(bvs.n);
		this.d(bvs.o);
		this.d(bvs.p);
		this.d(bvs.q);
		this.d(bvs.r);
		this.d(bvs.s);
		this.d(bvs.t);
		this.d(bvs.u);
		this.d(bvs.v);
		this.d(bvs.w);
		this.d(bvs.x);
		this.d(bvs.y);
		this.d(bvs.C);
		this.d(bvs.D);
		this.d(bvs.F);
		this.d(bvs.G);
		this.d(bvs.J);
		this.d(bvs.K);
		this.d(bvs.L);
		this.d(bvs.M);
		this.d(bvs.N);
		this.d(bvs.O);
		this.d(bvs.P);
		this.d(bvs.Q);
		this.d(bvs.R);
		this.d(bvs.S);
		this.d(bvs.T);
		this.d(bvs.U);
		this.d(bvs.mi);
		this.d(bvs.mr);
		this.d(bvs.V);
		this.d(bvs.W);
		this.d(bvs.X);
		this.d(bvs.Y);
		this.d(bvs.Z);
		this.d(bvs.aa);
		this.d(bvs.ab);
		this.d(bvs.ac);
		this.d(bvs.ad);
		this.d(bvs.ae);
		this.d(bvs.af);
		this.d(bvs.ag);
		this.d(bvs.mt);
		this.d(bvs.mk);
		this.d(bvs.an);
		this.d(bvs.ao);
		this.d(bvs.ar);
		this.d(bvs.at);
		this.d(bvs.au);
		this.d(bvs.av);
		this.d(bvs.aw);
		this.d(bvs.aN);
		this.d(bvs.aO);
		this.d(bvs.aP);
		this.d(bvs.aW);
		this.d(bvs.aY);
		this.d(bvs.aZ);
		this.d(bvs.ba);
		this.d(bvs.bb);
		this.d(bvs.bc);
		this.d(bvs.bd);
		this.d(bvs.be);
		this.d(bvs.bf);
		this.d(bvs.bg);
		this.d(bvs.bh);
		this.d(bvs.bi);
		this.d(bvs.bj);
		this.d(bvs.bk);
		this.d(bvs.bl);
		this.d(bvs.bm);
		this.d(bvs.bn);
		this.d(bvs.bp);
		this.d(bvs.bq);
		this.d(bvs.br);
		this.d(bvs.bs);
		this.d(bvs.bt);
		this.d(bvs.bu);
		this.d(bvs.bv);
		this.d(bvs.bw);
		this.d(bvs.bx);
		this.d(bvs.by);
		this.d(bvs.bz);
		this.d(bvs.bA);
		this.d(bvs.bB);
		this.d(bvs.bC);
		this.d(bvs.bD);
		this.d(bvs.bE);
		this.d(bvs.bF);
		this.d(bvs.bG);
		this.d(bvs.bJ);
		this.d(bvs.bK);
		this.d(bvs.ni);
		this.d(bvs.bL);
		this.d(bvs.bQ);
		this.d(bvs.bS);
		this.d(bvs.bU);
		this.d(bvs.bV);
		this.d(bvs.bZ);
		this.d(bvs.ca);
		this.d(bvs.cb);
		this.d(bvs.cc);
		this.d(bvs.cd);
		this.d(bvs.ce);
		this.d(bvs.cg);
		this.d(bvs.ch);
		this.d(bvs.ci);
		this.d(bvs.cp);
		this.d(bvs.cq);
		this.d(bvs.cs);
		this.d(bvs.ct);
		this.d(bvs.cu);
		this.d(bvs.cv);
		this.d(bvs.cw);
		this.d(bvs.cx);
		this.d(bvs.cz);
		this.d(bvs.cB);
		this.d(bvs.cF);
		this.d(bvs.cH);
		this.d(bvs.cI);
		this.d(bvs.cJ);
		this.d(bvs.cK);
		this.d(bvs.cL);
		this.d(bvs.cM);
		this.d(bvs.cN);
		this.d(bvs.cO);
		this.d(bvs.cP);
		this.d(bvs.cQ);
		this.d(bvs.cU);
		this.d(bvs.cV);
		this.d(bvs.cX);
		this.d(bvs.do);
		this.d(bvs.dp);
		this.d(bvs.dq);
		this.d(bvs.dr);
		this.d(bvs.ds);
		this.d(bvs.dt);
		this.d(bvs.du);
		this.d(bvs.dv);
		this.d(bvs.dw);
		this.d(bvs.dx);
		this.d(bvs.dH);
		this.d(bvs.dQ);
		this.d(bvs.dR);
		this.d(bvs.dS);
		this.d(bvs.dU);
		this.d(bvs.dV);
		this.d(bvs.dW);
		this.d(bvs.dX);
		this.d(bvs.eb);
		this.d(bvs.ee);
		this.d(bvs.eg);
		this.d(bvs.ei);
		this.d(bvs.el);
		this.d(bvs.en);
		this.d(bvs.eo);
		this.d(bvs.ep);
		this.d(bvs.eq);
		this.d(bvs.et);
		this.d(bvs.eu);
		this.d(bvs.ev);
		this.d(bvs.eW);
		this.d(bvs.eX);
		this.d(bvs.eY);
		this.d(bvs.eZ);
		this.d(bvs.fa);
		this.d(bvs.fb);
		this.d(bvs.fc);
		this.d(bvs.fe);
		this.d(bvs.fg);
		this.d(bvs.fk);
		this.d(bvs.fm);
		this.d(bvs.fo);
		this.d(bvs.fp);
		this.d(bvs.fq);
		this.d(bvs.fs);
		this.d(bvs.ft);
		this.d(bvs.fu);
		this.d(bvs.fv);
		this.d(bvs.fw);
		this.d(bvs.fz);
		this.d(bvs.fA);
		this.d(bvs.fB);
		this.d(bvs.fC);
		this.d(bvs.fD);
		this.d(bvs.fF);
		this.d(bvs.fG);
		this.d(bvs.fH);
		this.d(bvs.fI);
		this.d(bvs.fJ);
		this.d(bvs.fK);
		this.d(bvs.fL);
		this.d(bvs.fM);
		this.d(bvs.fN);
		this.d(bvs.fO);
		this.d(bvs.fP);
		this.d(bvs.fQ);
		this.d(bvs.fR);
		this.d(bvs.fS);
		this.d(bvs.fT);
		this.d(bvs.fU);
		this.d(bvs.gl);
		this.d(bvs.gm);
		this.d(bvs.gn);
		this.d(bvs.gp);
		this.d(bvs.gq);
		this.d(bvs.gr);
		this.d(bvs.gs);
		this.d(bvs.gt);
		this.d(bvs.gu);
		this.d(bvs.gv);
		this.d(bvs.gA);
		this.d(bvs.gB);
		this.d(bvs.gC);
		this.d(bvs.gD);
		this.d(bvs.gE);
		this.d(bvs.gF);
		this.d(bvs.gG);
		this.d(bvs.gH);
		this.d(bvs.gI);
		this.d(bvs.gJ);
		this.d(bvs.gK);
		this.d(bvs.gL);
		this.d(bvs.gM);
		this.d(bvs.gN);
		this.d(bvs.gO);
		this.d(bvs.gP);
		this.d(bvs.gQ);
		this.d(bvs.gR);
		this.d(bvs.gS);
		this.d(bvs.hG);
		this.d(bvs.hH);
		this.d(bvs.hI);
		this.d(bvs.hJ);
		this.d(bvs.id);
		this.d(bvs.ie);
		this.d(bvs.if);
		this.d(bvs.ig);
		this.d(bvs.ih);
		this.d(bvs.ii);
		this.d(bvs.ij);
		this.d(bvs.ik);
		this.d(bvs.il);
		this.d(bvs.im);
		this.d(bvs.in);
		this.d(bvs.io);
		this.d(bvs.ip);
		this.d(bvs.iq);
		this.d(bvs.iw);
		this.d(bvs.iz);
		this.d(bvs.iA);
		this.d(bvs.iB);
		this.d(bvs.iC);
		this.d(bvs.iJ);
		this.d(bvs.iK);
		this.d(bvs.iL);
		this.d(bvs.iM);
		this.d(bvs.iO);
		this.d(bvs.nb);
		this.d(bvs.jg);
		this.d(bvs.jh);
		this.d(bvs.ji);
		this.d(bvs.jj);
		this.d(bvs.jk);
		this.d(bvs.jl);
		this.d(bvs.jm);
		this.d(bvs.jn);
		this.d(bvs.jo);
		this.d(bvs.jp);
		this.d(bvs.jq);
		this.d(bvs.jr);
		this.d(bvs.js);
		this.d(bvs.jt);
		this.d(bvs.ju);
		this.d(bvs.jv);
		this.d(bvs.jw);
		this.d(bvs.jx);
		this.d(bvs.jy);
		this.d(bvs.jz);
		this.d(bvs.jA);
		this.d(bvs.jB);
		this.d(bvs.jC);
		this.d(bvs.jD);
		this.d(bvs.jE);
		this.d(bvs.jF);
		this.d(bvs.jG);
		this.d(bvs.jH);
		this.d(bvs.jI);
		this.d(bvs.jJ);
		this.d(bvs.jK);
		this.d(bvs.jL);
		this.d(bvs.jM);
		this.d(bvs.jN);
		this.d(bvs.jO);
		this.d(bvs.jP);
		this.d(bvs.jQ);
		this.d(bvs.jR);
		this.d(bvs.jS);
		this.d(bvs.jT);
		this.d(bvs.jU);
		this.d(bvs.jV);
		this.d(bvs.jW);
		this.d(bvs.jX);
		this.d(bvs.jY);
		this.d(bvs.jZ);
		this.d(bvs.ka);
		this.d(bvs.kb);
		this.d(bvs.kc);
		this.d(bvs.ke);
		this.d(bvs.kg);
		this.d(bvs.kh);
		this.d(bvs.ki);
		this.d(bvs.kj);
		this.d(bvs.kk);
		this.d(bvs.kW);
		this.d(bvs.ef);
		this.d(bvs.kY);
		this.d(bvs.ld);
		this.d(bvs.le);
		this.d(bvs.lf);
		this.d(bvs.lg);
		this.d(bvs.lh);
		this.d(bvs.li);
		this.d(bvs.lj);
		this.d(bvs.lk);
		this.d(bvs.ll);
		this.d(bvs.lm);
		this.d(bvs.ln);
		this.d(bvs.lo);
		this.d(bvs.lp);
		this.d(bvs.lq);
		this.d(bvs.lE);
		this.d(bvs.lF);
		this.d(bvs.lG);
		this.d(bvs.lH);
		this.d(bvs.lI);
		this.d(bvs.lJ);
		this.d(bvs.lK);
		this.d(bvs.lL);
		this.d(bvs.lM);
		this.d(bvs.lN);
		this.d(bvs.lO);
		this.d(bvs.lP);
		this.d(bvs.lR);
		this.d(bvs.lQ);
		this.d(bvs.ne);
		this.d(bvs.nf);
		this.d(bvs.nj);
		this.d(bvs.no);
		this.d(bvs.mh);
		this.d(bvs.mj);
		this.d(bvs.mm);
		this.d(bvs.mn);
		this.d(bvs.mo);
		this.d(bvs.mq);
		this.d(bvs.ms);
		this.d(bvs.mv);
		this.d(bvs.mw);
		this.d(bvs.mB);
		this.d(bvs.mC);
		this.d(bvs.mD);
		this.d(bvs.mH);
		this.d(bvs.mJ);
		this.d(bvs.mL);
		this.d(bvs.mN);
		this.d(bvs.mP);
		this.d(bvs.mR);
		this.d(bvs.mV);
		this.d(bvs.mG);
		this.d(bvs.mI);
		this.d(bvs.mK);
		this.d(bvs.mM);
		this.d(bvs.mO);
		this.d(bvs.mQ);
		this.d(bvs.mU);
		this.d(bvs.ng);
		this.d(bvs.nh);
		this.d(bvs.np);
		this.d(bvs.nu);
		this.d(bvs.ny);
		this.d(bvs.nq);
		this.d(bvs.nr);
		this.d(bvs.nz);
		this.d(bvs.nw);
		this.d(bvs.nv);
		this.d(bvs.nt);
		this.d(bvs.nB);
		this.d(bvs.nD);
		this.d(bvs.nE);
		this.d(bvs.nF);
		this.d(bvs.nG);
		this.d(bvs.nH);
		this.d(bvs.nI);
		this.d(bvs.dI);
		this.a(bvs.bX, (bqa)bvs.j);
		this.a(bvs.em, (bqa)bkk.kM);
		this.a(bvs.iE, (bqa)bvs.j);
		this.a(bvs.kd, (bqa)bvs.kc);
		this.a(bvs.kX, (bqa)bvs.kY);
		this.a(bvs.b, bvr -> b(bvr, (bqa)bvs.m));
		this.a(bvs.i, bvr -> b(bvr, (bqa)bvs.j));
		this.a(bvs.l, bvr -> b(bvr, (bqa)bvs.j));
		this.a(bvs.dT, bvr -> b(bvr, (bqa)bvs.j));
		this.a(bvs.kl, bvr -> b(bvr, (bqa)bvs.kg));
		this.a(bvs.km, bvr -> b(bvr, (bqa)bvs.kh));
		this.a(bvs.kn, bvr -> b(bvr, (bqa)bvs.ki));
		this.a(bvs.ko, bvr -> b(bvr, (bqa)bvs.kj));
		this.a(bvs.kp, bvr -> b(bvr, (bqa)bvs.kk));
		this.a(bvs.mu, bvr -> b(bvr, (bqa)bvs.cL));
		this.a(bvs.ml, bvr -> b(bvr, (bqa)bvs.cL));
		this.a(bvs.bI, bvr -> a(bvr, bkk.mc, dap.a(3)));
		this.a(bvs.cG, bvr -> a(bvr, bkk.lZ, dap.a(4)));
		this.a(bvs.ek, bvr -> a(bvr, bvs.bK, dap.a(8)));
		this.a(bvs.cE, bvr -> a(bvr, bkk.lQ, dap.a(4)));
		this.a(bvs.ix, a(bkk.qc, dbb.a(0.0F, 1.0F)));
		this.b(bvs.ew);
		this.b(bvs.ex);
		this.b(bvs.ey);
		this.b(bvs.ez);
		this.b(bvs.eA);
		this.b(bvs.eB);
		this.b(bvs.eC);
		this.b(bvs.eD);
		this.b(bvs.eE);
		this.b(bvs.eF);
		this.b(bvs.eG);
		this.b(bvs.eH);
		this.b(bvs.eI);
		this.b(bvs.eJ);
		this.b(bvs.eK);
		this.b(bvs.eL);
		this.b(bvs.eM);
		this.b(bvs.eN);
		this.b(bvs.eO);
		this.b(bvs.eP);
		this.b(bvs.eQ);
		this.b(bvs.eR);
		this.b(bvs.eS);
		this.b(bvs.eT);
		this.b(bvs.kZ);
		this.b(bvs.nk);
		this.b(bvs.nl);
		this.b(bvs.nm);
		this.b(bvs.nn);
		this.a(bvs.hO, hy::e);
		this.a(bvs.hM, hy::e);
		this.a(bvs.hW, hy::e);
		this.a(bvs.hV, hy::e);
		this.a(bvs.hP, hy::e);
		this.a(bvs.gy, hy::e);
		this.a(bvs.hN, hy::e);
		this.a(bvs.hY, hy::e);
		this.a(bvs.hK, hy::e);
		this.a(bvs.hU, hy::e);
		this.a(bvs.gx, hy::e);
		this.a(bvs.gw, hy::e);
		this.a(bvs.ic, hy::e);
		this.a(bvs.hZ, hy::e);
		this.a(bvs.ia, hy::e);
		this.a(bvs.hS, hy::e);
		this.a(bvs.ib, hy::e);
		this.a(bvs.hT, hy::e);
		this.a(bvs.hL, hy::e);
		this.a(bvs.hX, hy::e);
		this.a(bvs.hQ, hy::e);
		this.a(bvs.hR, hy::e);
		this.a(bvs.lr, hy::e);
		this.a(bvs.ls, hy::e);
		this.a(bvs.lt, hy::e);
		this.a(bvs.lu, hy::e);
		this.a(bvs.lv, hy::e);
		this.a(bvs.lw, hy::e);
		this.a(bvs.lx, hy::e);
		this.a(bvs.ly, hy::e);
		this.a(bvs.lz, hy::e);
		this.a(bvs.lA, hy::e);
		this.a(bvs.lB, hy::e);
		this.a(bvs.lC, hy::e);
		this.a(bvs.lD, hy::e);
		this.a(bvs.mE, hy::e);
		this.a(bvs.mF, hy::e);
		this.a(bvs.ns, hy::e);
		this.a(bvs.nx, hy::e);
		this.a(bvs.nC, hy::e);
		this.a(bvs.iu, hy::a);
		this.a(bvs.is, hy::a);
		this.a(bvs.iv, hy::a);
		this.a(bvs.cr, hy::a);
		this.a(bvs.it, hy::a);
		this.a(bvs.cf, hy::a);
		this.a(bvs.ir, hy::a);
		this.a(bvs.mT, hy::a);
		this.a(bvs.mS, hy::a);
		this.a(bvs.aM, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.aI, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.aJ, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.aG, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.aE, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.aK, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.aA, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.aF, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.aC, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.az, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.aH, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.ay, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.aD, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.aL, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.ax, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.aB, bvr -> a(bvr, bvm.a, cfx.HEAD));
		this.a(bvs.gV, bvr -> a(bvr, bxg.a, cgf.LOWER));
		this.a(bvs.gU, bvr -> a(bvr, bxg.a, cgf.LOWER));
		this.a(bvs.gX, bvr -> a(bvr, bxg.a, cgf.LOWER));
		this.a(bvs.gW, bvr -> a(bvr, bxg.a, cgf.LOWER));
		this.a(bvs.bH, daw.b().a(a(bvs.bH, dav.a().a(dap.a(1)).a(dbl.a(bvs.bH).a(ddl.a(bvs.bH).a(ck.a.a().a(ccb.a, false)))))));
		this.a(bvs.eh, bvr -> daw.b().a(dav.a().a(dap.a(1)).a(a((bqa)bvr, dbl.a(bkk.mu).a(dco.a(dap.a(3)).a(ddl.a(bvr).a(ck.a.a().a(bwk.a, 2))))))));
		this.a(
			bvs.kU,
			bvr -> daw.b()
					.a(
						dav.a()
							.a(dap.a(1))
							.a(
								a(
									(bqa)bvs.kU,
									dbl.a(bvr)
										.a(dco.a(dap.a(2)).a(ddl.a(bvr).a(ck.a.a().a(cat.a, 2))))
										.a(dco.a(dap.a(3)).a(ddl.a(bvr).a(ck.a.a().a(cat.a, 3))))
										.a(dco.a(dap.a(4)).a(ddl.a(bvr).a(ck.a.a().a(cat.a, 4))))
								)
							)
					)
		);
		this.a(bvs.na, bvr -> daw.b().a(dav.a().a((dbo.a<?>)a((bqa)bvr, dbl.a(bkk.qY)))).a(dav.a().a(dbl.a(bkk.mG)).a(ddl.a(bvr).a(ck.a.a().a(bwn.a, 8)))));
		this.a(bvs.es, hy::f);
		this.a(bvs.ea, hy::f);
		this.a(bvs.bR, hy::f);
		this.a(bvs.as, hy::f);
		this.a(bvs.fE, hy::f);
		this.a(bvs.dZ, hy::f);
		this.a(bvs.bY, hy::f);
		this.a(bvs.fy, hy::f);
		this.a(bvs.fr, hy::f);
		this.a(bvs.lT, hy::f);
		this.a(bvs.lU, hy::f);
		this.a(bvs.lS, hy::f);
		this.a(bvs.lV, hy::f);
		this.a(bvs.lW, hy::f);
		this.a(bvs.lX, hy::f);
		this.a(bvs.lY, hy::f);
		this.a(bvs.lZ, hy::f);
		this.a(bvs.ma, hy::f);
		this.a(bvs.mb, hy::a);
		this.a(bvs.mc, hy::a);
		this.a(bvs.md, hy::a);
		this.a(bvs.iP, hy::g);
		this.a(bvs.jf, hy::g);
		this.a(bvs.jb, hy::g);
		this.a(bvs.jc, hy::g);
		this.a(bvs.iZ, hy::g);
		this.a(bvs.iX, hy::g);
		this.a(bvs.jd, hy::g);
		this.a(bvs.iT, hy::g);
		this.a(bvs.iY, hy::g);
		this.a(bvs.iV, hy::g);
		this.a(bvs.iS, hy::g);
		this.a(bvs.iR, hy::g);
		this.a(bvs.iW, hy::g);
		this.a(bvs.ja, hy::g);
		this.a(bvs.je, hy::g);
		this.a(bvs.iQ, hy::g);
		this.a(bvs.iU, hy::g);
		this.a(bvs.hp, hy::h);
		this.a(bvs.hl, hy::h);
		this.a(bvs.hm, hy::h);
		this.a(bvs.hj, hy::h);
		this.a(bvs.hh, hy::h);
		this.a(bvs.hn, hy::h);
		this.a(bvs.hd, hy::h);
		this.a(bvs.hi, hy::h);
		this.a(bvs.hf, hy::h);
		this.a(bvs.hc, hy::h);
		this.a(bvs.hb, hy::h);
		this.a(bvs.hg, hy::h);
		this.a(bvs.hk, hy::h);
		this.a(bvs.ho, hy::h);
		this.a(bvs.ha, hy::h);
		this.a(bvs.he, hy::h);
		this.a(bvs.fi, bvr -> daw.b().a(a(bvr, dav.a().a(dap.a(1)).a(dbl.a(bvr).a(dbz.a(dbz.c.BLOCK_ENTITY).a("SkullOwner", "SkullOwner"))))));
		this.a(bvs.nc, hy::i);
		this.a(bvs.nd, hy::j);
		this.a(bvs.aj, bvr -> a(bvr, bvs.v, g));
		this.a(bvs.al, bvr -> a(bvr, bvs.x, g));
		this.a(bvs.ak, bvr -> a(bvr, bvs.w, h));
		this.a(bvs.ai, bvr -> a(bvr, bvs.u, g));
		this.a(bvs.ah, bvr -> b(bvr, bvs.t, g));
		this.a(bvs.am, bvr -> b(bvr, bvs.y, g));
		ddm.a a3 = ddl.a(bvs.iD).a(ck.a.a().a(bvo.a, 3));
		this.a(bvs.iD, a(bvs.iD, bkk.qe, bkk.qf, a3));
		ddm.a a4 = ddl.a(bvs.bW).a(ck.a.a().a(bwv.b, 7));
		this.a(bvs.bW, a(bvs.bW, bkk.kW, bkk.kV, a4));
		ddm.a a5 = ddl.a(bvs.eU).a(ck.a.a().a(bwc.b, 7));
		this.a(bvs.eU, a((bqa)bvs.eU, daw.b().a(dav.a().a(dbl.a(bkk.oX))).a(dav.a().a(a5).a(dbl.a(bkk.oX).a(dbv.a(boa.w, 0.5714286F, 3))))));
		ddm.a a6 = ddl.a(bvs.eV).a(ck.a.a().a(bzy.b, 7));
		this.a(
			bvs.eV,
			a(
				(bqa)bvs.eV,
				daw.b().a(dav.a().a(dbl.a(bkk.oY))).a(dav.a().a(a6).a(dbl.a(bkk.oY).a(dbv.a(boa.w, 0.5714286F, 3)))).a(dav.a().a(a6).a(dbl.a(bkk.pa).a(ddr.a(0.02F))))
			)
		);
		this.a(
			bvs.mg,
			bvr -> a(
					(bqa)bvr,
					daw.b()
						.a(dav.a().a(ddl.a(bvs.mg).a(ck.a.a().a(cbw.a, 3))).a(dbl.a(bkk.rl)).a(dco.a(dbb.a(2.0F, 3.0F))).a(dbv.b(boa.w)))
						.a(dav.a().a(ddl.a(bvs.mg).a(ck.a.a().a(cbw.a, 2))).a(dbl.a(bkk.rl)).a(dco.a(dbb.a(1.0F, 2.0F))).a(dbv.b(boa.w)))
				)
		);
		this.a(bvs.dE, bvr -> c(bvr, (bqa)bvs.bC));
		this.a(bvs.dF, bvr -> c(bvr, (bqa)bvs.bD));
		this.a(bvs.H, bvr -> a(bvr, bkk.kh));
		this.a(bvs.ej, bvr -> a(bvr, bkk.oU));
		this.a(bvs.fx, bvr -> a(bvr, bkk.pr));
		this.a(bvs.bT, bvr -> a(bvr, bkk.kj));
		this.a(bvs.I, bvr -> a(bvr, a((bqa)bvr, dbl.a(bkk.nt).a(dco.a(dbb.a(2.0F, 6.0F))).a(dbv.a(boa.w)))));
		this.a(bvs.aq, bvr -> a(bvr, a((bqa)bvr, dbl.a(bkk.mv).a(dco.a(dbb.a(4.0F, 9.0F))).a(dbv.a(boa.w)))));
		this.a(bvs.aQ, bvr -> c(bvr, (dbo.a<?>)a((bqa)bvr, dbl.a(bkk.kM))));
		this.a(bvs.aT, bvr -> b(bvr, a((bqa)bvr, dbl.a(bkk.kB).a(dco.a(dbb.a(0.0F, 2.0F))))));
		this.a(bvs.mp, hy::d);
		this.a(bvs.aU, hy::d);
		this.a(bvs.dP, hy::d);
		this.a(bvs.aV, l(bvs.aU));
		this.a(bvs.gZ, bvr -> b(bvr, bvs.aS));
		this.a(bvs.gY, bvr -> b(bvr, bvs.aR));
		this.a(bvs.dO, bvr -> b(bvr, bkk.nk));
		this.a(bvs.dM, bvr -> c(bvr, bkk.nk));
		this.a(bvs.dN, bvr -> b(bvr, bkk.nj));
		this.a(bvs.dL, bvr -> c(bvr, bkk.nj));
		this.a(bvs.iy, bvr -> daw.b().a(dav.a().a(dap.a(1)).a(((dbq.a)a((bqa)bvr, dbl.a(bvr))).a(ddp.a(dat.c.THIS)))));
		this.a(bvs.aS, hy::k);
		this.a(bvs.aR, hy::k);
		this.a(bvs.cS, bvr -> a(bvr, a((bqa)bvr, dbl.a(bkk.mk).a(dco.a(dbb.a(2.0F, 4.0F))).a(dbv.b(boa.w)).a(dcf.a(das.a(1, 4))))));
		this.a(bvs.dK, bvr -> a(bvr, a((bqa)bvr, dbl.a(bkk.nh).a(dco.a(dbb.a(3.0F, 7.0F))).a(dbv.b(boa.w)).a(dcf.a(das.b(9))))));
		this.a(bvs.cy, bvr -> a(bvr, a((bqa)bvr, dbl.a(bkk.lP).a(dco.a(dbb.a(4.0F, 5.0F))).a(dbv.b(boa.w)))));
		this.a(bvs.gz, bvr -> a(bvr, a((bqa)bvr, dbl.a(bkk.pv).a(dco.a(dbb.a(2.0F, 3.0F))).a(dbv.b(boa.w)).a(dcf.a(das.a(1, 5))))));
		this.a(
			bvs.dY,
			bvr -> daw.b()
					.a(
						a(
							bvr,
							dav.a()
								.a(dap.a(1))
								.a(dbl.a(bkk.nu).a(dco.a(dbb.a(2.0F, 4.0F)).a(ddl.a(bvr).a(ck.a.a().a(bzp.a, 3)))).a(dbv.b(boa.w).a(ddl.a(bvr).a(ck.a.a().a(bzp.a, 3)))))
						)
					)
		);
		this.a(
			bvs.cC,
			bvr -> daw.b()
					.a(
						dav.a()
							.a(ddp.a(dat.c.THIS))
							.a(
								dbf.a(
									dbf.a(
											dbl.a(bkk.lQ).a(ddl.a(bvr).a(ck.a.a().a(cbd.a, 1))),
											((dbq.a)dbl.a(bkk.lQ).a(ddl.a(bvr).a(ck.a.a().a(cbd.a, 2)))).a(dco.a(dap.a(2))),
											((dbq.a)dbl.a(bkk.lQ).a(ddl.a(bvr).a(ck.a.a().a(cbd.a, 3)))).a(dco.a(dap.a(3))),
											((dbq.a)dbl.a(bkk.lQ).a(ddl.a(bvr).a(ck.a.a().a(cbd.a, 4)))).a(dco.a(dap.a(4))),
											((dbq.a)dbl.a(bkk.lQ).a(ddl.a(bvr).a(ck.a.a().a(cbd.a, 5)))).a(dco.a(dap.a(5))),
											((dbq.a)dbl.a(bkk.lQ).a(ddl.a(bvr).a(ck.a.a().a(cbd.a, 6)))).a(dco.a(dap.a(6))),
											((dbq.a)dbl.a(bkk.lQ).a(ddl.a(bvr).a(ck.a.a().a(cbd.a, 7)))).a(dco.a(dap.a(7))),
											dbl.a(bkk.lQ).a(dco.a(dap.a(8)))
										)
										.a(b),
									dbf.a(
										dbl.a(bvs.cC).a(ddl.a(bvr).a(ck.a.a().a(cbd.a, 1))),
										dbl.a(bvs.cC).a(dco.a(dap.a(2))).a(ddl.a(bvr).a(ck.a.a().a(cbd.a, 2))),
										dbl.a(bvs.cC).a(dco.a(dap.a(3))).a(ddl.a(bvr).a(ck.a.a().a(cbd.a, 3))),
										dbl.a(bvs.cC).a(dco.a(dap.a(4))).a(ddl.a(bvr).a(ck.a.a().a(cbd.a, 4))),
										dbl.a(bvs.cC).a(dco.a(dap.a(5))).a(ddl.a(bvr).a(ck.a.a().a(cbd.a, 5))),
										dbl.a(bvs.cC).a(dco.a(dap.a(6))).a(ddl.a(bvr).a(ck.a.a().a(cbd.a, 6))),
										dbl.a(bvs.cC).a(dco.a(dap.a(7))).a(ddl.a(bvr).a(ck.a.a().a(cbd.a, 7))),
										dbl.a(bvs.cE)
									)
								)
							)
					)
		);
		this.a(bvs.E, bvr -> a(bvr, a((bqa)bvr, ((dbq.a)dbl.a(bkk.lw).a(ddd.a(boa.w, 0.1F, 0.14285715F, 0.25F, 1.0F))).a(dbl.a(bvr)))));
		this.a(bvs.me, bvr -> a(bvr, a((bqa)bvr, dbl.a(bkk.ki).a(dco.a(dap.a(2))))));
		this.a(bvs.nA, bvr -> a(bvr, a((bqa)bvr, ((dbq.a)dbl.a(bkk.nt).a(dco.a(dbb.a(2.0F, 5.0F))).a(ddd.a(boa.w, 0.1F, 0.14285715F, 0.25F, 1.0F))).a(dbl.a(bvr)))));
		this.a(bvs.mf, bvr -> a(bvr, a((bqa)bvr, dbl.a(bkk.dm).a(dco.a(dap.a(1))))));
		this.c(bvs.ap);
		this.c(bvs.cY);
		this.c(bvs.cZ);
		this.c(bvs.da);
		this.c(bvs.db);
		this.c(bvs.dc);
		this.c(bvs.dd);
		this.c(bvs.de);
		this.c(bvs.df);
		this.c(bvs.dg);
		this.c(bvs.dh);
		this.c(bvs.di);
		this.c(bvs.dj);
		this.c(bvs.dk);
		this.c(bvs.dl);
		this.c(bvs.dm);
		this.c(bvs.dn);
		this.c(bvs.dJ);
		this.c(bvs.fV);
		this.c(bvs.fW);
		this.c(bvs.fX);
		this.c(bvs.fY);
		this.c(bvs.fZ);
		this.c(bvs.ga);
		this.c(bvs.gb);
		this.c(bvs.gc);
		this.c(bvs.gd);
		this.c(bvs.ge);
		this.c(bvs.gf);
		this.c(bvs.gg);
		this.c(bvs.gh);
		this.c(bvs.gi);
		this.c(bvs.gj);
		this.c(bvs.gk);
		this.c(bvs.cD);
		this.c(bvs.gT);
		this.c(bvs.kV);
		this.c(bvs.kf);
		this.c(bvs.dG);
		this.c(bvs.kq);
		this.c(bvs.kr);
		this.c(bvs.ks);
		this.c(bvs.kt);
		this.c(bvs.ku);
		this.c(bvs.kv);
		this.c(bvs.kw);
		this.c(bvs.kx);
		this.c(bvs.ky);
		this.c(bvs.kz);
		this.c(bvs.kA);
		this.c(bvs.kB);
		this.c(bvs.kC);
		this.c(bvs.kD);
		this.c(bvs.kE);
		this.c(bvs.kF);
		this.c(bvs.kG);
		this.c(bvs.kH);
		this.c(bvs.kI);
		this.c(bvs.kJ);
		this.a(bvs.dy, bvs.b);
		this.a(bvs.dz, bvs.m);
		this.a(bvs.dA, bvs.du);
		this.a(bvs.dB, bvs.dv);
		this.a(bvs.dC, bvs.dw);
		this.a(bvs.dD, bvs.dx);
		this.c(bvs.mx, bvs.my);
		this.c(bvs.mz, bvs.mA);
		this.a(bvs.cW, a());
		this.a(bvs.iI, a());
		this.a(bvs.bP, a());
		this.a(bvs.bN, a());
		this.a(bvs.bO, a());
		this.a(bvs.cT, a());
		Set<uh> set7 = Sets.<uh>newHashSet();

		for (bvr bvr9 : gl.aj) {
			uh uh10 = bvr9.r();
			if (uh10 != dao.a && set7.add(uh10)) {
				daw.a a11 = (daw.a)this.i.remove(uh10);
				if (a11 == null) {
					throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", uh10, gl.aj.b(bvr9)));
				}

				biConsumer.accept(uh10, a11);
			}
		}

		if (!this.i.isEmpty()) {
			throw new IllegalStateException("Created block loot tables for non-blocks: " + this.i.keySet());
		}
	}

	private void c(bvr bvr1, bvr bvr2) {
		daw.a a4 = c(bvr1, dbl.a(bvr1).a(ddd.a(boa.w, 0.33F, 0.55F, 0.77F, 1.0F)));
		this.a(bvr1, a4);
		this.a(bvr2, a4);
	}

	public static daw.a a(bvr bvr) {
		return a(bvr, bxe.e, cgf.LOWER);
	}

	public void b(bvr bvr) {
		this.a(bvr, bvrx -> c((bqa)((bxy)bvrx).c()));
	}

	public void a(bvr bvr1, bvr bvr2) {
		this.a(bvr1, b((bqa)bvr2));
	}

	public void a(bvr bvr, bqa bqa) {
		this.a(bvr, a(bqa));
	}

	public void c(bvr bvr) {
		this.a(bvr, bvr);
	}

	public void d(bvr bvr) {
		this.a(bvr, (bqa)bvr);
	}

	private void a(bvr bvr, Function<bvr, daw.a> function) {
		this.a(bvr, (daw.a)function.apply(bvr));
	}

	private void a(bvr bvr, daw.a a) {
		this.i.put(bvr.r(), a);
	}
}
