import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class jf implements hl {
	private static final Logger b = LogManager.getLogger();
	private static final Gson c = new GsonBuilder().setPrettyPrinting().create();
	private final hk d;

	public jf(hk hk) {
		this.d = hk;
	}

	@Override
	public void a(hm hm) throws IOException {
		Path path3 = this.d.b();
		Set<uh> set4 = Sets.<uh>newHashSet();
		a(je -> {
			if (!set4.add(je.b())) {
				throw new IllegalStateException("Duplicate recipe " + je.b());
			} else {
				a(hm, je.a(), path3.resolve("data/" + je.b().b() + "/recipes/" + je.b().a() + ".json"));
				JsonObject jsonObject5 = je.d();
				if (jsonObject5 != null) {
					b(hm, jsonObject5, path3.resolve("data/" + je.b().b() + "/advancements/" + je.e().a() + ".json"));
				}
			}
		});
		b(hm, w.a.a().a("impossible", new bk.a()).b(), path3.resolve("data/minecraft/advancements/recipes/root.json"));
	}

	private static void a(hm hm, JsonObject jsonObject, Path path) {
		try {
			String string4 = c.toJson((JsonElement)jsonObject);
			String string5 = a.hashUnencodedChars(string4).toString();
			if (!Objects.equals(hm.a(path), string5) || !Files.exists(path, new LinkOption[0])) {
				Files.createDirectories(path.getParent());
				BufferedWriter bufferedWriter6 = Files.newBufferedWriter(path);
				Throwable var6 = null;

				try {
					bufferedWriter6.write(string4);
				} catch (Throwable var16) {
					var6 = var16;
					throw var16;
				} finally {
					if (bufferedWriter6 != null) {
						if (var6 != null) {
							try {
								bufferedWriter6.close();
							} catch (Throwable var15) {
								var6.addSuppressed(var15);
							}
						} else {
							bufferedWriter6.close();
						}
					}
				}
			}

			hm.a(path, string5);
		} catch (IOException var18) {
			b.error("Couldn't save recipe {}", path, var18);
		}
	}

	private static void b(hm hm, JsonObject jsonObject, Path path) {
		try {
			String string4 = c.toJson((JsonElement)jsonObject);
			String string5 = a.hashUnencodedChars(string4).toString();
			if (!Objects.equals(hm.a(path), string5) || !Files.exists(path, new LinkOption[0])) {
				Files.createDirectories(path.getParent());
				BufferedWriter bufferedWriter6 = Files.newBufferedWriter(path);
				Throwable var6 = null;

				try {
					bufferedWriter6.write(string4);
				} catch (Throwable var16) {
					var6 = var16;
					throw var16;
				} finally {
					if (bufferedWriter6 != null) {
						if (var6 != null) {
							try {
								bufferedWriter6.close();
							} catch (Throwable var15) {
								var6.addSuppressed(var15);
							}
						} else {
							bufferedWriter6.close();
						}
					}
				}
			}

			hm.a(path, string5);
		} catch (IOException var18) {
			b.error("Couldn't save recipe advancement {}", path, var18);
		}
	}

	private static void a(Consumer<je> consumer) {
		a(consumer, bvs.r, ada.t);
		b(consumer, bvs.p, ada.s);
		b(consumer, bvs.mC, ada.w);
		a(consumer, bvs.s, ada.q);
		b(consumer, bvs.q, ada.u);
		b(consumer, bvs.n, ada.r);
		b(consumer, bvs.o, ada.v);
		b(consumer, bvs.mD, ada.x);
		a(consumer, bvs.Z, bvs.N);
		a(consumer, bvs.X, bvs.L);
		a(consumer, bvs.aa, bvs.O);
		a(consumer, bvs.Y, bvs.M);
		a(consumer, bvs.V, bvs.J);
		a(consumer, bvs.W, bvs.K);
		a(consumer, bvs.ms, bvs.mq);
		a(consumer, bvs.mj, bvs.mh);
		a(consumer, bvs.af, bvs.S);
		a(consumer, bvs.ad, bvs.Q);
		a(consumer, bvs.ag, bvs.T);
		a(consumer, bvs.ae, bvs.R);
		a(consumer, bvs.ab, bvs.U);
		a(consumer, bvs.ac, bvs.P);
		a(consumer, bvs.mt, bvs.mr);
		a(consumer, bvs.mk, bvs.mi);
		b(consumer, bkk.qr, bvs.r);
		b(consumer, bkk.qp, bvs.p);
		b(consumer, bkk.qs, bvs.s);
		b(consumer, bkk.qq, bvs.q);
		b(consumer, bkk.lR, bvs.n);
		b(consumer, bkk.qo, bvs.o);
		c(consumer, bvs.fa, bvs.r);
		d(consumer, bvs.iu, bvs.r);
		e(consumer, bvs.ip, bvs.r);
		f(consumer, bvs.ik, bvs.r);
		g(consumer, bvs.cw, bvs.r);
		h(consumer, bvs.hO, bvs.r);
		i(consumer, bvs.gl, bvs.r);
		j(consumer, bvs.ds, bvs.r);
		k(consumer, bvs.cc, bvs.r);
		c(consumer, bvs.eY, bvs.p);
		d(consumer, bvs.is, bvs.p);
		e(consumer, bvs.in, bvs.p);
		f(consumer, bvs.ii, bvs.p);
		g(consumer, bvs.cu, bvs.p);
		h(consumer, bvs.hM, bvs.p);
		i(consumer, bvs.ep, bvs.p);
		j(consumer, bvs.dq, bvs.p);
		k(consumer, bvs.cb, bvs.p);
		c(consumer, bvs.mQ, bvs.mC);
		d(consumer, bvs.mS, bvs.mC);
		e(consumer, bvs.mI, bvs.mC);
		f(consumer, bvs.mM, bvs.mC);
		g(consumer, bvs.mG, bvs.mC);
		h(consumer, bvs.mE, bvs.mC);
		i(consumer, bvs.mO, bvs.mC);
		j(consumer, bvs.mK, bvs.mC);
		k(consumer, bvs.mU, bvs.mC);
		c(consumer, bvs.fb, bvs.s);
		d(consumer, bvs.iv, bvs.s);
		e(consumer, bvs.iq, bvs.s);
		f(consumer, bvs.il, bvs.s);
		g(consumer, bvs.cx, bvs.s);
		h(consumer, bvs.hP, bvs.s);
		i(consumer, bvs.gm, bvs.s);
		j(consumer, bvs.dt, bvs.s);
		k(consumer, bvs.ce, bvs.s);
		c(consumer, bvs.eZ, bvs.q);
		d(consumer, bvs.it, bvs.q);
		e(consumer, bvs.io, bvs.q);
		f(consumer, bvs.ij, bvs.q);
		g(consumer, bvs.cv, bvs.q);
		h(consumer, bvs.hN, bvs.q);
		i(consumer, bvs.eq, bvs.q);
		j(consumer, bvs.dr, bvs.q);
		k(consumer, bvs.cd, bvs.q);
		c(consumer, bvs.eW, bvs.n);
		d(consumer, bvs.cf, bvs.n);
		e(consumer, bvs.cJ, bvs.n);
		f(consumer, bvs.dQ, bvs.n);
		g(consumer, bvs.cs, bvs.n);
		h(consumer, bvs.hK, bvs.n);
		i(consumer, bvs.bQ, bvs.n);
		j(consumer, bvs.do, bvs.n);
		k(consumer, bvs.bZ, bvs.n);
		c(consumer, bvs.eX, bvs.o);
		d(consumer, bvs.ir, bvs.o);
		e(consumer, bvs.im, bvs.o);
		f(consumer, bvs.ih, bvs.o);
		g(consumer, bvs.ct, bvs.o);
		h(consumer, bvs.hL, bvs.o);
		i(consumer, bvs.eo, bvs.o);
		j(consumer, bvs.dp, bvs.o);
		k(consumer, bvs.ca, bvs.o);
		c(consumer, bvs.mR, bvs.mD);
		d(consumer, bvs.mT, bvs.mD);
		e(consumer, bvs.mJ, bvs.mD);
		f(consumer, bvs.mN, bvs.mD);
		g(consumer, bvs.mH, bvs.mD);
		h(consumer, bvs.mF, bvs.mD);
		i(consumer, bvs.mP, bvs.mD);
		j(consumer, bvs.mL, bvs.mD);
		k(consumer, bvs.mV, bvs.mD);
		l(consumer, bvs.bn, bkk.mJ);
		m(consumer, bvs.gQ, bvs.bn);
		n(consumer, bvs.gQ, bkk.mJ);
		o(consumer, bkk.nd, bvs.bn);
		p(consumer, bkk.nd, bkk.mJ);
		q(consumer, bkk.qa, bvs.bn);
		l(consumer, bvs.bj, bkk.mH);
		m(consumer, bvs.gM, bvs.bj);
		n(consumer, bvs.gM, bkk.mH);
		o(consumer, bkk.mZ, bvs.bj);
		p(consumer, bkk.mZ, bkk.mH);
		q(consumer, bkk.pW, bvs.bj);
		l(consumer, bvs.bk, bkk.mI);
		m(consumer, bvs.gN, bvs.bk);
		n(consumer, bvs.gN, bkk.mI);
		o(consumer, bkk.na, bvs.bk);
		p(consumer, bkk.na, bkk.mI);
		q(consumer, bkk.pX, bvs.bk);
		l(consumer, bvs.bh, bkk.mx);
		m(consumer, bvs.gK, bvs.bh);
		n(consumer, bvs.gK, bkk.mx);
		o(consumer, bkk.mX, bvs.bh);
		p(consumer, bkk.mX, bkk.mx);
		q(consumer, bkk.pU, bvs.bh);
		l(consumer, bvs.bf, bkk.mz);
		m(consumer, bvs.gI, bvs.bf);
		n(consumer, bvs.gI, bkk.mz);
		o(consumer, bkk.mV, bvs.bf);
		p(consumer, bkk.mV, bkk.mz);
		q(consumer, bkk.pS, bvs.bf);
		l(consumer, bvs.bl, bkk.mt);
		m(consumer, bvs.gO, bvs.bl);
		n(consumer, bvs.gO, bkk.mt);
		o(consumer, bkk.nb, bvs.bl);
		p(consumer, bkk.nb, bkk.mt);
		q(consumer, bkk.pY, bvs.bl);
		l(consumer, bvs.bb, bkk.mD);
		m(consumer, bvs.gE, bvs.bb);
		n(consumer, bvs.gE, bkk.mD);
		o(consumer, bkk.mR, bvs.bb);
		p(consumer, bkk.mR, bkk.mD);
		q(consumer, bkk.pO, bvs.bb);
		l(consumer, bvs.bg, bkk.my);
		m(consumer, bvs.gJ, bvs.bg);
		n(consumer, bvs.gJ, bkk.my);
		o(consumer, bkk.mW, bvs.bg);
		p(consumer, bkk.mW, bkk.my);
		q(consumer, bkk.pT, bvs.bg);
		l(consumer, bvs.bd, bkk.mB);
		m(consumer, bvs.gG, bvs.bd);
		n(consumer, bvs.gG, bkk.mB);
		o(consumer, bkk.mT, bvs.bd);
		p(consumer, bkk.mT, bkk.mB);
		q(consumer, bkk.pQ, bvs.bd);
		l(consumer, bvs.ba, bkk.mE);
		m(consumer, bvs.gD, bvs.ba);
		n(consumer, bvs.gD, bkk.mE);
		o(consumer, bkk.mQ, bvs.ba);
		p(consumer, bkk.mQ, bkk.mE);
		q(consumer, bkk.pN, bvs.ba);
		l(consumer, bvs.aZ, bkk.mF);
		m(consumer, bvs.gC, bvs.aZ);
		n(consumer, bvs.gC, bkk.mF);
		o(consumer, bkk.mP, bvs.aZ);
		p(consumer, bkk.mP, bkk.mF);
		q(consumer, bkk.pM, bvs.aZ);
		l(consumer, bvs.be, bkk.mA);
		m(consumer, bvs.gH, bvs.be);
		n(consumer, bvs.gH, bkk.mA);
		o(consumer, bkk.mU, bvs.be);
		p(consumer, bkk.mU, bkk.mA);
		q(consumer, bkk.pR, bvs.be);
		l(consumer, bvs.bi, bkk.mw);
		m(consumer, bvs.gL, bvs.bi);
		n(consumer, bvs.gL, bkk.mw);
		o(consumer, bkk.mY, bvs.bi);
		p(consumer, bkk.mY, bkk.mw);
		q(consumer, bkk.pV, bvs.bi);
		l(consumer, bvs.bm, bkk.ms);
		m(consumer, bvs.gP, bvs.bm);
		n(consumer, bvs.gP, bkk.ms);
		o(consumer, bkk.nc, bvs.bm);
		p(consumer, bkk.nc, bkk.ms);
		q(consumer, bkk.pZ, bvs.bm);
		m(consumer, bvs.gB, bvs.aY);
		o(consumer, bkk.mO, bvs.aY);
		q(consumer, bkk.pL, bvs.aY);
		l(consumer, bvs.bc, bkk.mC);
		m(consumer, bvs.gF, bvs.bc);
		n(consumer, bvs.gF, bkk.mC);
		o(consumer, bkk.mS, bvs.bc);
		p(consumer, bkk.mS, bkk.mC);
		q(consumer, bkk.pP, bvs.bc);
		r(consumer, bvs.dn, bkk.mJ);
		s(consumer, bvs.gk, bvs.dn);
		t(consumer, bvs.gk, bkk.mJ);
		r(consumer, bvs.dj, bkk.mH);
		s(consumer, bvs.gg, bvs.dj);
		t(consumer, bvs.gg, bkk.mH);
		r(consumer, bvs.dk, bkk.mI);
		s(consumer, bvs.gh, bvs.dk);
		t(consumer, bvs.gh, bkk.mI);
		r(consumer, bvs.dh, bkk.mx);
		s(consumer, bvs.ge, bvs.dh);
		t(consumer, bvs.ge, bkk.mx);
		r(consumer, bvs.df, bkk.mz);
		s(consumer, bvs.gc, bvs.df);
		t(consumer, bvs.gc, bkk.mz);
		r(consumer, bvs.dl, bkk.mt);
		s(consumer, bvs.gi, bvs.dl);
		t(consumer, bvs.gi, bkk.mt);
		r(consumer, bvs.db, bkk.mD);
		s(consumer, bvs.fY, bvs.db);
		t(consumer, bvs.fY, bkk.mD);
		r(consumer, bvs.dg, bkk.my);
		s(consumer, bvs.gd, bvs.dg);
		t(consumer, bvs.gd, bkk.my);
		r(consumer, bvs.dd, bkk.mB);
		s(consumer, bvs.ga, bvs.dd);
		t(consumer, bvs.ga, bkk.mB);
		r(consumer, bvs.da, bkk.mE);
		s(consumer, bvs.fX, bvs.da);
		t(consumer, bvs.fX, bkk.mE);
		r(consumer, bvs.cZ, bkk.mF);
		s(consumer, bvs.fW, bvs.cZ);
		t(consumer, bvs.fW, bkk.mF);
		r(consumer, bvs.de, bkk.mA);
		s(consumer, bvs.gb, bvs.de);
		t(consumer, bvs.gb, bkk.mA);
		r(consumer, bvs.di, bkk.mw);
		s(consumer, bvs.gf, bvs.di);
		t(consumer, bvs.gf, bkk.mw);
		r(consumer, bvs.dm, bkk.ms);
		s(consumer, bvs.gj, bvs.dm);
		t(consumer, bvs.gj, bkk.ms);
		r(consumer, bvs.cY, bkk.mK);
		s(consumer, bvs.fV, bvs.cY);
		t(consumer, bvs.fV, bkk.mK);
		r(consumer, bvs.dc, bkk.mC);
		s(consumer, bvs.fZ, bvs.dc);
		t(consumer, bvs.fZ, bkk.mC);
		u(consumer, bvs.fU, bkk.mJ);
		u(consumer, bvs.fQ, bkk.mH);
		u(consumer, bvs.fR, bkk.mI);
		u(consumer, bvs.fO, bkk.mx);
		u(consumer, bvs.fM, bkk.mz);
		u(consumer, bvs.fS, bkk.mt);
		u(consumer, bvs.fI, bkk.mD);
		u(consumer, bvs.fN, bkk.my);
		u(consumer, bvs.fK, bkk.mB);
		u(consumer, bvs.fH, bkk.mE);
		u(consumer, bvs.fG, bkk.mF);
		u(consumer, bvs.fL, bkk.mA);
		u(consumer, bvs.fP, bkk.mw);
		u(consumer, bvs.fT, bkk.ms);
		u(consumer, bvs.fF, bkk.mK);
		u(consumer, bvs.fJ, bkk.mC);
		v(consumer, bvs.kb, bkk.mJ);
		v(consumer, bvs.jX, bkk.mH);
		v(consumer, bvs.jY, bkk.mI);
		v(consumer, bvs.jV, bkk.mx);
		v(consumer, bvs.jT, bkk.mz);
		v(consumer, bvs.jZ, bkk.mt);
		v(consumer, bvs.jP, bkk.mD);
		v(consumer, bvs.jU, bkk.my);
		v(consumer, bvs.jR, bkk.mB);
		v(consumer, bvs.jO, bkk.mE);
		v(consumer, bvs.jN, bkk.mF);
		v(consumer, bvs.jS, bkk.mA);
		v(consumer, bvs.jW, bkk.mw);
		v(consumer, bvs.ka, bkk.ms);
		v(consumer, bvs.jM, bkk.mK);
		v(consumer, bvs.jQ, bkk.mC);
		jg.a(bvs.fD, 6).a('#', bvs.cz).a('S', bkk.kB).a('X', bkk.kk).a("XSX").a("X#X").a("XSX").a("has_rail", a((bqa)bvs.ch)).a(consumer);
		jh.a(bvs.g, 2).b(bvs.e).b(bvs.m).a("has_stone", a((bqa)bvs.e)).a(consumer);
		jg.a(bvs.fo).a('I', bvs.bF).a('i', bkk.kk).a("III").a(" i ").a("iii").a("has_iron_block", a((bqa)bvs.bF)).a(consumer);
		jg.a(bkk.pB).a('/', bkk.kB).a('_', bvs.hR).a("///").a(" / ").a("/_/").a("has_stone_slab", a((bqa)bvs.hR)).a(consumer);
		jg.a(bkk.kg, 4).a('#', bkk.kB).a('X', bkk.lw).a('Y', bkk.kN).a("X").a("#").a("Y").a("has_feather", a(bkk.kN)).a("has_flint", a(bkk.lw)).a(consumer);
		jg.a(bvs.lS, 1).a('P', ada.b).a('S', ada.i).a("PSP").a("P P").a("PSP").a("has_planks", a(ada.b)).a("has_wood_slab", a(ada.i)).a(consumer);
		jg.a(bvs.es).a('S', bkk.pl).a('G', bvs.ap).a('O', bvs.bK).a("GGG").a("GSG").a("OOO").a("has_nether_star", a(bkk.pl)).a(consumer);
		jg.a(bvs.nd).a('P', ada.b).a('H', bkk.rp).a("PPP").a("HHH").a("PPP").a("has_honeycomb", a(bkk.rp)).a(consumer);
		jh.a(bkk.qg).b(bkk.kC).b(bkk.qe, 6).a("has_beetroot", a(bkk.qe)).a(consumer);
		jh.a(bkk.mJ).b(bkk.mr).a("black_dye").a("has_ink_sac", a(bkk.mr)).a(consumer);
		jh.a(bkk.mJ).b(bvs.bA).a("black_dye").a("has_black_flower", a((bqa)bvs.bA)).a(consumer, "black_dye_from_wither_rose");
		jh.a(bkk.nz, 2).b(bkk.nr).a("has_blaze_rod", a(bkk.nr)).a(consumer);
		jh.a(bkk.mH).b(bkk.mv).a("blue_dye").a("has_lapis_lazuli", a(bkk.mv)).a(consumer);
		jh.a(bkk.mH).b(bvs.bz).a("blue_dye").a("has_blue_flower", a((bqa)bvs.bz)).a(consumer, "blue_dye_from_cornflower");
		jg.a(bvs.kV).a('#', bvs.gT).a("###").a("###").a("###").a("has_packed_ice", a((bqa)bvs.gT)).a(consumer);
		jg.a(bvs.iM).a('X', bkk.mG).a("XXX").a("XXX").a("XXX").a("has_bonemeal", a(bkk.mG)).a(consumer);
		jh.a(bkk.mG, 3).b(bkk.mL).a("bonemeal").a("has_bone", a(bkk.mL)).a(consumer);
		jh.a(bkk.mG, 9).b(bvs.iM).a("bonemeal").a("has_bone_block", a((bqa)bvs.iM)).a(consumer, "bone_meal_from_bone_block");
		jh.a(bkk.mc).b(bkk.mb, 3).b(bkk.lS).a("has_paper", a(bkk.mb)).a(consumer);
		jg.a(bvs.bI).a('#', ada.b).a('X', bkk.mc).a("###").a("XXX").a("###").a("has_book", a(bkk.mc)).a(consumer);
		jg.a(bkk.kf).a('#', bkk.kB).a('X', bkk.kM).a(" #X").a("# X").a(" #X").a("has_string", a(bkk.kM)).a(consumer);
		jg.a(bkk.kC, 4)
			.a('#', ada.b)
			.a("# #")
			.a(" # ")
			.a("has_brown_mushroom", a((bqa)bvs.bC))
			.a("has_red_mushroom", a((bqa)bvs.bD))
			.a("has_mushroom_stew", a(bkk.kD))
			.a(consumer);
		jg.a(bkk.kX).a('#', bkk.kW).a("###").a("has_wheat", a(bkk.kW)).a(consumer);
		jg.a(bvs.ea).a('B', bkk.nr).a('#', bvs.m).a(" B ").a("###").a("has_blaze_rod", a(bkk.nr)).a(consumer);
		jg.a(bvs.bG).a('#', bkk.lY).a("##").a("##").a("has_brick", a(bkk.lY)).a(consumer);
		jg.a(bvs.hW, 6).a('#', bvs.bG).a("###").a("has_brick_block", a((bqa)bvs.bG)).a(consumer);
		jg.a(bvs.dR, 4).a('#', bvs.bG).a("#  ").a("## ").a("###").a("has_brick_block", a((bqa)bvs.bG)).a(consumer);
		jh.a(bkk.mI).b(bkk.mu).a("brown_dye").a("has_cocoa_beans", a(bkk.mu)).a(consumer);
		jg.a(bkk.lK).a('#', bkk.kk).a("# #").a(" # ").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bvs.cW).a('A', bkk.lT).a('B', bkk.mM).a('C', bkk.kW).a('E', bkk.mg).a("AAA").a("BEB").a("CCC").a("has_egg", a(bkk.mg)).a(consumer);
		jg.a(bvs.me).a('L', ada.p).a('S', bkk.kB).a('C', ada.W).a(" S ").a("SCS").a("LLL").a("has_stick", a(bkk.kB)).a("has_coal", a(ada.W)).a(consumer);
		jg.a(bkk.pj).a('#', bkk.mi).a('X', bkk.oX).a("# ").a(" X").a("has_carrot", a(bkk.oX)).a(consumer);
		jg.a(bkk.pk).a('#', bkk.mi).a('X', bkk.bx).a("# ").a(" X").a("has_warped_fungus", a(bkk.bx)).a(consumer);
		jg.a(bvs.eb).a('#', bkk.kk).a("# #").a("# #").a("###").a("has_water_bucket", a(bkk.lL)).a(consumer);
		jg.a(bvs.na).a('#', ada.i).a("# #").a("# #").a("###").a("has_wood_slab", a(ada.i)).a(consumer);
		jg.a(bvs.bR).a('#', ada.b).a("###").a("# #").a("###").a("has_lots_of_items", new bl.a(be.b.a, bx.d.b(10), bx.d.e, bx.d.e, new bo[0])).a(consumer);
		jg.a(bkk.me).a('A', bvs.bR).a('B', bkk.lN).a("A").a("B").a("has_minecart", a(bkk.lN)).a(consumer);
		jg.a(bvs.nG).a('#', bvs.hY).a("#").a("#").a("has_nether_bricks", a((bqa)bvs.dV)).a(consumer);
		jg.a(bvs.fA)
			.a('#', bvs.hZ)
			.a("#")
			.a("#")
			.a("has_chiseled_quartz_block", a((bqa)bvs.fA))
			.a("has_quartz_block", a((bqa)bvs.fz))
			.a("has_quartz_pillar", a((bqa)bvs.fB))
			.a(consumer);
		jg.a(bvs.dx).a('#', bvs.hX).a("#").a("#").a("has_stone_bricks", a(ada.c)).a(consumer);
		jg.a(bvs.cG).a('#', bkk.lZ).a("##").a("##").a("has_clay_ball", a(bkk.lZ)).a(consumer);
		jg.a(bkk.mj).a('#', bkk.kl).a('X', bkk.lP).a(" # ").a("#X#").a(" # ").a("has_redstone", a(bkk.lP)).a(consumer);
		jh.a(bkk.kh, 9).b(bvs.gS).a("has_coal_block", a((bqa)bvs.gS)).a(consumer);
		jg.a(bvs.gS).a('#', bkk.kh).a("###").a("###").a("###").a("has_coal", a(bkk.kh)).a(consumer);
		jg.a(bvs.k, 4).a('D', bvs.j).a('G', bvs.E).a("DG").a("GD").a("has_gravel", a((bqa)bvs.E)).a(consumer);
		jg.a(bvs.hV, 6).a('#', bvs.m).a("###").a("has_cobblestone", a((bqa)bvs.m)).a(consumer);
		jg.a(bvs.et, 6).a('#', bvs.m).a("###").a("###").a("has_cobblestone", a((bqa)bvs.m)).a(consumer);
		jg.a(bvs.fu).a('#', bvs.cz).a('X', bkk.pr).a('I', bvs.b).a(" # ").a("#X#").a("III").a("has_quartz", a(bkk.pr)).a(consumer);
		jg.a(bkk.mh).a('#', bkk.kk).a('X', bkk.lP).a(" # ").a("#X#").a(" # ").a("has_redstone", a(bkk.lP)).a(consumer);
		jg.a(bkk.ne, 8).a('#', bkk.kW).a('X', bkk.mu).a("#X#").a("has_cocoa", a(bkk.mu)).a(consumer);
		jg.a(bvs.bV).a('#', ada.b).a("##").a("##").a("has_planks", a(ada.b)).a(consumer);
		jg.a(bkk.qP)
			.a('~', bkk.kM)
			.a('#', bkk.kB)
			.a('&', bkk.kk)
			.a('$', bvs.el)
			.a("#&#")
			.a("~$~")
			.a(" # ")
			.a("has_string", a(bkk.kM))
			.a("has_stick", a(bkk.kB))
			.a("has_iron_ingot", a(bkk.kk))
			.a("has_tripwire_hook", a((bqa)bvs.el))
			.a(consumer);
		jg.a(bvs.lR).a('#', ada.b).a('@', bkk.kM).a("@@").a("##").a("has_string", a(bkk.kM)).a(consumer);
		jg.a(bvs.hH)
			.a('#', bvs.ia)
			.a("#")
			.a("#")
			.a("has_red_sandstone", a((bqa)bvs.hG))
			.a("has_chiseled_red_sandstone", a((bqa)bvs.hH))
			.a("has_cut_red_sandstone", a((bqa)bvs.hI))
			.a(consumer);
		jg.a(bvs.au).a('#', bvs.hS).a("#").a("#").a("has_stone_slab", a((bqa)bvs.hS)).a(consumer);
		jh.a(bkk.mx, 2).b(bkk.mH).b(bkk.mt).a("has_green_dye", a(bkk.mt)).a("has_blue_dye", a(bkk.mH)).a(consumer);
		jg.a(bvs.gs).a('S', bkk.pu).a('I', bkk.mJ).a("SSS").a("SIS").a("SSS").a("has_prismarine_shard", a(bkk.pu)).a(consumer);
		jg.a(bvs.gt, 4).a('#', bvs.gq).a("#  ").a("## ").a("###").a("has_prismarine", a((bqa)bvs.gq)).a(consumer);
		jg.a(bvs.gu, 4).a('#', bvs.gr).a("#  ").a("## ").a("###").a("has_prismarine_bricks", a((bqa)bvs.gr)).a(consumer);
		jg.a(bvs.gv, 4).a('#', bvs.gs).a("#  ").a("## ").a("###").a("has_dark_prismarine", a((bqa)bvs.gs)).a(consumer);
		jg.a(bvs.fv).a('Q', bkk.pr).a('G', bvs.ap).a('W', bmr.a(ada.i)).a("GGG").a("QQQ").a("WWW").a("has_quartz", a(bkk.pr)).a(consumer);
		jg.a(bvs.aO, 6).a('R', bkk.lP).a('#', bvs.cq).a('X', bkk.kk).a("X X").a("X#X").a("XRX").a("has_rail", a((bqa)bvs.ch)).a(consumer);
		jh.a(bkk.kj, 9).b(bvs.bU).a("has_diamond_block", a((bqa)bvs.bU)).a(consumer);
		jg.a(bkk.kA).a('#', bkk.kB).a('X', bkk.kj).a("XX").a("X#").a(" #").a("has_diamond", a(bkk.kj)).a(consumer);
		jg.a(bvs.bU).a('#', bkk.kj).a("###").a("###").a("###").a("has_diamond", a(bkk.kj)).a(consumer);
		jg.a(bkk.ln).a('X', bkk.kj).a("X X").a("X X").a("has_diamond", a(bkk.kj)).a(consumer);
		jg.a(bkk.ll).a('X', bkk.kj).a("X X").a("XXX").a("XXX").a("has_diamond", a(bkk.kj)).a(consumer);
		jg.a(bkk.lk).a('X', bkk.kj).a("XXX").a("X X").a("has_diamond", a(bkk.kj)).a(consumer);
		jg.a(bkk.kS).a('#', bkk.kB).a('X', bkk.kj).a("XX").a(" #").a(" #").a("has_diamond", a(bkk.kj)).a(consumer);
		jg.a(bkk.lm).a('X', bkk.kj).a("XXX").a("X X").a("X X").a("has_diamond", a(bkk.kj)).a(consumer);
		jg.a(bkk.kz).a('#', bkk.kB).a('X', bkk.kj).a("XXX").a(" # ").a(" # ").a("has_diamond", a(bkk.kj)).a(consumer);
		jg.a(bkk.ky).a('#', bkk.kB).a('X', bkk.kj).a("X").a("#").a("#").a("has_diamond", a(bkk.kj)).a(consumer);
		jg.a(bkk.kx).a('#', bkk.kB).a('X', bkk.kj).a("X").a("X").a("#").a("has_diamond", a(bkk.kj)).a(consumer);
		jg.a(bvs.e, 2).a('Q', bkk.pr).a('C', bvs.m).a("CQ").a("QC").a("has_quartz", a(bkk.pr)).a(consumer);
		jg.a(bvs.as).a('R', bkk.lP).a('#', bvs.m).a('X', bkk.kf).a("###").a("#X#").a("#R#").a("has_bow", a(bkk.kf)).a(consumer);
		jg.a(bvs.fE).a('R', bkk.lP).a('#', bvs.m).a("###").a("# #").a("#R#").a("has_redstone", a(bkk.lP)).a(consumer);
		jh.a(bkk.oU, 9).b(bvs.en).a("has_emerald_block", a((bqa)bvs.en)).a(consumer);
		jg.a(bvs.en).a('#', bkk.oU).a("###").a("###").a("###").a("has_emerald", a(bkk.oU)).a(consumer);
		jg.a(bvs.dZ).a('B', bkk.mc).a('#', bvs.bK).a('D', bkk.kj).a(" B ").a("D#D").a("###").a("has_obsidian", a((bqa)bvs.bK)).a(consumer);
		jg.a(bvs.ek).a('#', bvs.bK).a('E', bkk.nD).a("###").a("#E#").a("###").a("has_ender_eye", a(bkk.nD)).a(consumer);
		jh.a(bkk.nD).b(bkk.nq).b(bkk.nz).a("has_blaze_powder", a(bkk.nz)).a(consumer);
		jg.a(bvs.iC, 4).a('#', bvs.ee).a("##").a("##").a("has_end_stone", a((bqa)bvs.ee)).a(consumer);
		jg.a(bkk.qb).a('T', bkk.ns).a('E', bkk.nD).a('G', bvs.ap).a("GGG").a("GEG").a("GTG").a("has_ender_eye", a(bkk.nD)).a(consumer);
		jg.a(bvs.iw, 4).a('#', bkk.qd).a('/', bkk.nr).a("/").a("#").a("has_chorus_fruit_popped", a(bkk.qd)).a(consumer);
		jh.a(bkk.ny).b(bkk.nx).b(bvs.bC).b(bkk.mM).a("has_spider_eye", a(bkk.nx)).a(consumer);
		jh.a(bkk.oR, 3).b(bkk.kO).b(bkk.nz).a(bmr.a(bkk.kh, bkk.ki)).a("has_blaze_powder", a(bkk.nz)).a(consumer);
		jg.a(bkk.mi).a('#', bkk.kB).a('X', bkk.kM).a("  #").a(" #X").a("# X").a("has_string", a(bkk.kM)).a(consumer);
		jh.a(bkk.kd).b(bkk.kk).b(bkk.lw).a("has_flint", a(bkk.lw)).a("has_obsidian", a((bqa)bvs.bK)).a(consumer);
		jg.a(bvs.ev).a('#', bkk.lY).a("# #").a(" # ").a("has_brick", a(bkk.lY)).a(consumer);
		jg.a(bvs.bY).a('#', ada.ab).a("###").a("# #").a("###").a("has_cobblestone", a(ada.ab)).a(consumer);
		jg.a(bkk.mf).a('A', bvs.bY).a('B', bkk.lN).a("A").a("B").a("has_minecart", a(bkk.lN)).a(consumer);
		jg.a(bkk.nw, 3).a('#', bvs.ap).a("# #").a(" # ").a("has_glass", a((bqa)bvs.ap)).a(consumer);
		jg.a(bvs.dJ, 16).a('#', bvs.ap).a("###").a("###").a("has_glass", a((bqa)bvs.ap)).a(consumer);
		jg.a(bvs.cS).a('#', bkk.mk).a("##").a("##").a("has_glowstone_dust", a(bkk.mk)).a(consumer);
		jg.a(bkk.lA).a('#', bkk.kl).a('X', bkk.ke).a("###").a("#X#").a("###").a("has_gold_ingot", a(bkk.kl)).a(consumer);
		jg.a(bkk.kH).a('#', bkk.kB).a('X', bkk.kl).a("XX").a("X#").a(" #").a("has_gold_ingot", a(bkk.kl)).a(consumer);
		jg.a(bkk.lr).a('X', bkk.kl).a("X X").a("X X").a("has_gold_ingot", a(bkk.kl)).a(consumer);
		jg.a(bkk.pc).a('#', bkk.nt).a('X', bkk.oX).a("###").a("#X#").a("###").a("has_gold_nugget", a(bkk.nt)).a(consumer);
		jg.a(bkk.lp).a('X', bkk.kl).a("X X").a("XXX").a("XXX").a("has_gold_ingot", a(bkk.kl)).a(consumer);
		jg.a(bkk.lo).a('X', bkk.kl).a("XXX").a("X X").a("has_gold_ingot", a(bkk.kl)).a(consumer);
		jg.a(bkk.kT).a('#', bkk.kB).a('X', bkk.kl).a("XX").a(" #").a(" #").a("has_gold_ingot", a(bkk.kl)).a(consumer);
		jg.a(bkk.lq).a('X', bkk.kl).a("XXX").a("X X").a("X X").a("has_gold_ingot", a(bkk.kl)).a(consumer);
		jg.a(bkk.kG).a('#', bkk.kB).a('X', bkk.kl).a("XXX").a(" # ").a(" # ").a("has_gold_ingot", a(bkk.kl)).a(consumer);
		jg.a(bvs.aN, 6).a('R', bkk.lP).a('#', bkk.kB).a('X', bkk.kl).a("X X").a("X#X").a("XRX").a("has_rail", a((bqa)bvs.ch)).a(consumer);
		jg.a(bkk.kF).a('#', bkk.kB).a('X', bkk.kl).a("X").a("#").a("#").a("has_gold_ingot", a(bkk.kl)).a(consumer);
		jg.a(bkk.kE).a('#', bkk.kB).a('X', bkk.kl).a("X").a("X").a("#").a("has_gold_ingot", a(bkk.kl)).a(consumer);
		jg.a(bvs.bE).a('#', bkk.kl).a("###").a("###").a("###").a("has_gold_ingot", a(bkk.kl)).a(consumer);
		jh.a(bkk.kl, 9).b(bvs.bE).a("gold_ingot").a("has_gold_block", a((bqa)bvs.bE)).a(consumer, "gold_ingot_from_gold_block");
		jg.a(bkk.kl).a('#', bkk.nt).a("###").a("###").a("###").b("gold_ingot").a("has_gold_nugget", a(bkk.nt)).a(consumer, "gold_ingot_from_nuggets");
		jh.a(bkk.nt, 9).b(bkk.kl).a("has_gold_ingot", a(bkk.kl)).a(consumer);
		jh.a(bvs.c).b(bvs.e).b(bkk.pr).a("has_quartz", a(bkk.pr)).a(consumer);
		jh.a(bkk.mz, 2).b(bkk.mJ).b(bkk.mK).a("has_white_dye", a(bkk.mK)).a("has_black_dye", a(bkk.mJ)).a(consumer);
		jg.a(bvs.gA).a('#', bkk.kW).a("###").a("###").a("###").a("has_wheat", a(bkk.kW)).a(consumer);
		jg.a(bvs.ft).a('#', bkk.kk).a("##").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jh.a(bkk.rs, 4).b(bkk.rt).b(bkk.nw, 4).a("has_honey_block", a((bqa)bvs.ne)).a(consumer);
		jg.a(bvs.ne, 1).a('S', bkk.rs).a("SS").a("SS").a("has_honey_bottle", a(bkk.rs)).a(consumer);
		jg.a(bvs.nf).a('H', bkk.rp).a("HH").a("HH").a("has_honeycomb", a(bkk.rp)).a(consumer);
		jg.a(bvs.fy).a('C', bvs.bR).a('I', bkk.kk).a("I I").a("ICI").a(" I ").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bkk.pt).a('A', bvs.fy).a('B', bkk.lN).a("A").a("B").a("has_minecart", a(bkk.lN)).a(consumer);
		jg.a(bkk.kc).a('#', bkk.kB).a('X', bkk.kk).a("XX").a("X#").a(" #").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bvs.dH, 16).a('#', bkk.kk).a("###").a("###").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bvs.bF).a('#', bkk.kk).a("###").a("###").a("###").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bkk.lj).a('X', bkk.kk).a("X X").a("X X").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bkk.lh).a('X', bkk.kk).a("X X").a("XXX").a("XXX").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bvs.cr, 3).a('#', bkk.kk).a("##").a("##").a("##").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bkk.lg).a('X', bkk.kk).a("XXX").a("X X").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bkk.kR).a('#', bkk.kB).a('X', bkk.kk).a("XX").a(" #").a(" #").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jh.a(bkk.kk, 9).b(bvs.bF).a("iron_ingot").a("has_iron_block", a((bqa)bvs.bF)).a(consumer, "iron_ingot_from_iron_block");
		jg.a(bkk.kk).a('#', bkk.qv).a("###").a("###").a("###").b("iron_ingot").a("has_iron_nugget", a(bkk.qv)).a(consumer, "iron_ingot_from_nuggets");
		jg.a(bkk.li).a('X', bkk.kk).a("XXX").a("X X").a("X X").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jh.a(bkk.qv, 9).b(bkk.kk).a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bkk.kb).a('#', bkk.kB).a('X', bkk.kk).a("XXX").a(" # ").a(" # ").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bkk.ka).a('#', bkk.kB).a('X', bkk.kk).a("X").a("#").a("#").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bkk.ko).a('#', bkk.kB).a('X', bkk.kk).a("X").a("X").a("#").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bvs.gp).a('#', bkk.kk).a("##").a("##").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bkk.oV).a('#', bkk.kB).a('X', bkk.lS).a("###").a("#X#").a("###").a("has_leather", a(bkk.lS)).a(consumer);
		jg.a(bvs.cI).a('#', ada.b).a('X', bkk.kj).a("###").a("#X#").a("###").a("has_diamond", a(bkk.kj)).a(consumer);
		jg.a(bvs.cg, 3).a('#', bkk.kB).a("# #").a("###").a("# #").a("has_stick", a(bkk.kB)).a(consumer);
		jg.a(bvs.ar).a('#', bkk.mv).a("###").a("###").a("###").a("has_lapis", a(bkk.mv)).a(consumer);
		jh.a(bkk.mv, 9).b(bvs.ar).a("has_lapis_block", a((bqa)bvs.ar)).a(consumer);
		jg.a(bkk.pG, 2).a('~', bkk.kM).a('O', bkk.md).a("~~ ").a("~O ").a("  ~").a("has_slime_ball", a(bkk.md)).a(consumer);
		jg.a(bkk.lS).a('#', bkk.pA).a("##").a("##").a("has_rabbit_hide", a(bkk.pA)).a(consumer);
		jg.a(bkk.lb).a('X', bkk.lS).a("X X").a("X X").a("has_leather", a(bkk.lS)).a(consumer);
		jg.a(bkk.kZ).a('X', bkk.lS).a("X X").a("XXX").a("XXX").a("has_leather", a(bkk.lS)).a(consumer);
		jg.a(bkk.kY).a('X', bkk.lS).a("XXX").a("X X").a("has_leather", a(bkk.lS)).a(consumer);
		jg.a(bkk.la).a('X', bkk.lS).a("XXX").a("X X").a("X X").a("has_leather", a(bkk.lS)).a(consumer);
		jg.a(bkk.pF).a('X', bkk.lS).a("X X").a("XXX").a("X X").a("has_leather", a(bkk.lS)).a(consumer);
		jg.a(bvs.lY).a('S', ada.i).a('B', bvs.bI).a("SSS").a(" B ").a(" S ").a("has_book", a(bkk.mc)).a(consumer);
		jg.a(bvs.cp).a('#', bvs.m).a('X', bkk.kB).a("X").a("#").a("has_cobblestone", a((bqa)bvs.m)).a(consumer);
		jh.a(bkk.mD).b(bvs.br).a("light_blue_dye").a("has_red_flower", a((bqa)bvs.br)).a(consumer, "light_blue_dye_from_blue_orchid");
		jh.a(bkk.mD, 2)
			.b(bkk.mH)
			.b(bkk.mK)
			.a("light_blue_dye")
			.a("has_blue_dye", a(bkk.mH))
			.a("has_white_dye", a(bkk.mK))
			.a(consumer, "light_blue_dye_from_blue_white_dye");
		jh.a(bkk.my).b(bvs.bt).a("light_gray_dye").a("has_red_flower", a((bqa)bvs.bt)).a(consumer, "light_gray_dye_from_azure_bluet");
		jh.a(bkk.my, 2)
			.b(bkk.mz)
			.b(bkk.mK)
			.a("light_gray_dye")
			.a("has_gray_dye", a(bkk.mz))
			.a("has_white_dye", a(bkk.mK))
			.a(consumer, "light_gray_dye_from_gray_white_dye");
		jh.a(bkk.my, 3)
			.b(bkk.mJ)
			.b(bkk.mK, 2)
			.a("light_gray_dye")
			.a("has_white_dye", a(bkk.mK))
			.a("has_black_dye", a(bkk.mJ))
			.a(consumer, "light_gray_dye_from_black_white_dye");
		jh.a(bkk.my).b(bvs.by).a("light_gray_dye").a("has_red_flower", a((bqa)bvs.by)).a(consumer, "light_gray_dye_from_oxeye_daisy");
		jh.a(bkk.my).b(bvs.bw).a("light_gray_dye").a("has_red_flower", a((bqa)bvs.bw)).a(consumer, "light_gray_dye_from_white_tulip");
		jg.a(bvs.fs).a('#', bkk.kl).a("##").a("has_gold_ingot", a(bkk.kl)).a(consumer);
		jh.a(bkk.mB, 2).b(bkk.mt).b(bkk.mK).a("has_green_dye", a(bkk.mt)).a("has_white_dye", a(bkk.mK)).a(consumer);
		jg.a(bvs.cV).a('A', bvs.cU).a('B', bvs.bL).a("A").a("B").a("has_carved_pumpkin", a((bqa)bvs.cU)).a(consumer);
		jh.a(bkk.mE).b(bvs.bs).a("magenta_dye").a("has_red_flower", a((bqa)bvs.bs)).a(consumer, "magenta_dye_from_allium");
		jh.a(bkk.mE, 4)
			.b(bkk.mH)
			.b(bkk.ms, 2)
			.b(bkk.mK)
			.a("magenta_dye")
			.a("has_blue_dye", a(bkk.mH))
			.a("has_rose_red", a(bkk.ms))
			.a("has_white_dye", a(bkk.mK))
			.a(consumer, "magenta_dye_from_blue_red_white_dye");
		jh.a(bkk.mE, 3)
			.b(bkk.mH)
			.b(bkk.ms)
			.b(bkk.mA)
			.a("magenta_dye")
			.a("has_pink_dye", a(bkk.mA))
			.a("has_blue_dye", a(bkk.mH))
			.a("has_red_dye", a(bkk.ms))
			.a(consumer, "magenta_dye_from_blue_red_pink");
		jh.a(bkk.mE, 2).b(bvs.gV).a("magenta_dye").a("has_double_plant", a((bqa)bvs.gV)).a(consumer, "magenta_dye_from_lilac");
		jh.a(bkk.mE, 2)
			.b(bkk.mw)
			.b(bkk.mA)
			.a("magenta_dye")
			.a("has_pink_dye", a(bkk.mA))
			.a("has_purple_dye", a(bkk.mw))
			.a(consumer, "magenta_dye_from_purple_and_pink");
		jg.a(bvs.iJ).a('#', bkk.nA).a("##").a("##").a("has_magma_cream", a(bkk.nA)).a(consumer);
		jh.a(bkk.nA).b(bkk.nz).b(bkk.md).a("has_blaze_powder", a(bkk.nz)).a(consumer);
		jg.a(bkk.pb).a('#', bkk.mb).a('X', bkk.mh).a("###").a("#X#").a("###").a("has_compass", a(bkk.mh)).a(consumer);
		jg.a(bvs.dK).a('M', bkk.nh).a("MMM").a("MMM").a("MMM").a("has_melon", a(bkk.nh)).a(consumer);
		jh.a(bkk.nk).b(bkk.nh).a("has_melon", a(bkk.nh)).a(consumer);
		jg.a(bkk.lN).a('#', bkk.kk).a("# #").a("###").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jh.a(bvs.bJ).b(bvs.m).b(bvs.dP).a("has_vine", a((bqa)bvs.dP)).a(consumer);
		jg.a(bvs.eu, 6).a('#', bvs.bJ).a("###").a("###").a("has_mossy_cobblestone", a((bqa)bvs.bJ)).a(consumer);
		jh.a(bvs.dv).b(bvs.du).b(bvs.dP).a("has_mossy_cobblestone", a((bqa)bvs.bJ)).a(consumer);
		jh.a(bkk.kD)
			.b(bvs.bC)
			.b(bvs.bD)
			.b(bkk.kC)
			.a("has_mushroom_stew", a(bkk.kD))
			.a("has_bowl", a(bkk.kC))
			.a("has_brown_mushroom", a((bqa)bvs.bC))
			.a("has_red_mushroom", a((bqa)bvs.bD))
			.a(consumer);
		jg.a(bvs.dV).a('N', bkk.pq).a("NN").a("NN").a("has_netherbrick", a(bkk.pq)).a(consumer);
		jg.a(bvs.dW, 6).a('#', bvs.dV).a('-', bkk.pq).a("#-#").a("#-#").a("has_nether_brick", a((bqa)bvs.dV)).a(consumer);
		jg.a(bvs.hY, 6).a('#', bvs.dV).a("###").a("has_nether_brick", a((bqa)bvs.dV)).a(consumer);
		jg.a(bvs.dX, 4).a('#', bvs.dV).a("#  ").a("## ").a("###").a("has_nether_brick", a((bqa)bvs.dV)).a(consumer);
		jg.a(bvs.iK).a('#', bkk.nu).a("###").a("###").a("###").a("has_nether_wart", a(bkk.nu)).a(consumer);
		jg.a(bvs.aw).a('#', ada.b).a('X', bkk.lP).a("###").a("#X#").a("###").a("has_redstone", a(bkk.lP)).a(consumer);
		jg.a(bvs.iO).a('Q', bkk.pr).a('R', bkk.lP).a('#', bvs.m).a("###").a("RRQ").a("###").a("has_quartz", a(bkk.pr)).a(consumer);
		jh.a(bkk.mF).b(bvs.bv).a("orange_dye").a("has_red_flower", a((bqa)bvs.bv)).a(consumer, "orange_dye_from_orange_tulip");
		jh.a(bkk.mF, 2).b(bkk.ms).b(bkk.mC).a("orange_dye").a("has_red_dye", a(bkk.ms)).a("has_yellow_dye", a(bkk.mC)).a(consumer, "orange_dye_from_red_yellow");
		jg.a(bkk.lz).a('#', bkk.kB).a('X', bmr.a(ada.a)).a("###").a("#X#").a("###").a("has_wool", a(ada.a)).a(consumer);
		jg.a(bkk.mb, 3).a('#', bvs.cH).a("###").a("has_reeds", a((bqa)bvs.cH)).a(consumer);
		jg.a(bvs.fB, 2)
			.a('#', bvs.fz)
			.a("#")
			.a("#")
			.a("has_chiseled_quartz_block", a((bqa)bvs.fA))
			.a("has_quartz_block", a((bqa)bvs.fz))
			.a("has_quartz_pillar", a((bqa)bvs.fB))
			.a(consumer);
		jh.a(bvs.gT).b(bvs.cD, 9).a("has_ice", a((bqa)bvs.cD)).a(consumer);
		jh.a(bkk.mA, 2).b(bvs.gX).a("pink_dye").a("has_double_plant", a((bqa)bvs.gX)).a(consumer, "pink_dye_from_peony");
		jh.a(bkk.mA).b(bvs.bx).a("pink_dye").a("has_red_flower", a((bqa)bvs.bx)).a(consumer, "pink_dye_from_pink_tulip");
		jh.a(bkk.mA, 2).b(bkk.ms).b(bkk.mK).a("pink_dye").a("has_white_dye", a(bkk.mK)).a("has_red_dye", a(bkk.ms)).a(consumer, "pink_dye_from_red_white_dye");
		jg.a(bvs.aW).a('R', bkk.lP).a('#', bvs.m).a('T', ada.b).a('X', bkk.kk).a("TTT").a("#X#").a("#R#").a("has_redstone", a(bkk.lP)).a(consumer);
		jg.a(bvs.cP, 4).a('S', bvs.cO).a("SS").a("SS").a("has_basalt", a((bqa)bvs.cO)).a(consumer);
		jg.a(bvs.d, 4).a('S', bvs.c).a("SS").a("SS").a("has_stone", a((bqa)bvs.c)).a(consumer);
		jg.a(bvs.f, 4).a('S', bvs.e).a("SS").a("SS").a("has_stone", a((bqa)bvs.e)).a(consumer);
		jg.a(bvs.h, 4).a('S', bvs.g).a("SS").a("SS").a("has_stone", a((bqa)bvs.g)).a(consumer);
		jg.a(bvs.gq).a('S', bkk.pu).a("SS").a("SS").a("has_prismarine_shard", a(bkk.pu)).a(consumer);
		jg.a(bvs.gr).a('S', bkk.pu).a("SSS").a("SSS").a("SSS").a("has_prismarine_shard", a(bkk.pu)).a(consumer);
		jg.a(bvs.gw, 6).a('#', bvs.gq).a("###").a("has_prismarine", a((bqa)bvs.gq)).a(consumer);
		jg.a(bvs.gx, 6).a('#', bvs.gr).a("###").a("has_prismarine_bricks", a((bqa)bvs.gr)).a(consumer);
		jg.a(bvs.gy, 6).a('#', bvs.gs).a("###").a("has_dark_prismarine", a((bqa)bvs.gs)).a(consumer);
		jh.a(bkk.pm).b(bvs.cK).b(bkk.mM).b(bkk.mg).a("has_carved_pumpkin", a((bqa)bvs.cU)).a("has_pumpkin", a((bqa)bvs.cK)).a(consumer);
		jh.a(bkk.nj, 4).b(bvs.cK).a("has_pumpkin", a((bqa)bvs.cK)).a(consumer);
		jh.a(bkk.mw, 2).b(bkk.mH).b(bkk.ms).a("has_blue_dye", a(bkk.mH)).a("has_red_dye", a(bkk.ms)).a(consumer);
		jg.a(bvs.iP).a('#', bvs.bR).a('-', bkk.qu).a("-").a("#").a("-").a("has_shulker_shell", a(bkk.qu)).a(consumer);
		jg.a(bvs.iz, 4).a('F', bkk.qd).a("FF").a("FF").a("has_chorus_fruit_popped", a(bkk.qd)).a(consumer);
		jg.a(bvs.iA).a('#', bvs.ic).a("#").a("#").a("has_purpur_block", a((bqa)bvs.iz)).a(consumer);
		jg.a(bvs.ic, 6).a('#', bmr.a(bvs.iz, bvs.iA)).a("###").a("has_purpur_block", a((bqa)bvs.iz)).a(consumer);
		jg.a(bvs.iB, 4).a('#', bmr.a(bvs.iz, bvs.iA)).a("#  ").a("## ").a("###").a("has_purpur_block", a((bqa)bvs.iz)).a(consumer);
		jg.a(bvs.fz).a('#', bkk.pr).a("##").a("##").a("has_quartz", a(bkk.pr)).a(consumer);
		jg.a(bvs.nI, 4).a('#', bvs.fz).a("##").a("##").a("has_quartz_block", a((bqa)bvs.fz)).a(consumer);
		jg.a(bvs.hZ, 6)
			.a('#', bmr.a(bvs.fA, bvs.fz, bvs.fB))
			.a("###")
			.a("has_chiseled_quartz_block", a((bqa)bvs.fA))
			.a("has_quartz_block", a((bqa)bvs.fz))
			.a("has_quartz_pillar", a((bqa)bvs.fB))
			.a(consumer);
		jg.a(bvs.fC, 4)
			.a('#', bmr.a(bvs.fA, bvs.fz, bvs.fB))
			.a("#  ")
			.a("## ")
			.a("###")
			.a("has_chiseled_quartz_block", a((bqa)bvs.fA))
			.a("has_quartz_block", a((bqa)bvs.fz))
			.a("has_quartz_pillar", a((bqa)bvs.fB))
			.a(consumer);
		jh.a(bkk.py)
			.b(bkk.oZ)
			.b(bkk.px)
			.b(bkk.kC)
			.b(bkk.oX)
			.b(bvs.bC)
			.a("rabbit_stew")
			.a("has_cooked_rabbit", a(bkk.px))
			.a(consumer, "rabbit_stew_from_brown_mushroom");
		jh.a(bkk.py)
			.b(bkk.oZ)
			.b(bkk.px)
			.b(bkk.kC)
			.b(bkk.oX)
			.b(bvs.bD)
			.a("rabbit_stew")
			.a("has_cooked_rabbit", a(bkk.px))
			.a(consumer, "rabbit_stew_from_red_mushroom");
		jg.a(bvs.ch, 16).a('#', bkk.kB).a('X', bkk.kk).a("X X").a("X#X").a("X X").a("has_minecart", a(bkk.lN)).a(consumer);
		jh.a(bkk.lP, 9).b(bvs.fw).a("has_redstone_block", a((bqa)bvs.fw)).a(consumer);
		jg.a(bvs.fw).a('#', bkk.lP).a("###").a("###").a("###").a("has_redstone", a(bkk.lP)).a(consumer);
		jg.a(bvs.eg).a('R', bkk.lP).a('G', bvs.cS).a(" R ").a("RGR").a(" R ").a("has_glowstone", a((bqa)bvs.cS)).a(consumer);
		jg.a(bvs.cz).a('#', bkk.kB).a('X', bkk.lP).a("X").a("#").a("has_redstone", a(bkk.lP)).a(consumer);
		jh.a(bkk.ms).b(bkk.qe).a("red_dye").a("has_beetroot", a(bkk.qe)).a(consumer, "red_dye_from_beetroot");
		jh.a(bkk.ms).b(bvs.bq).a("red_dye").a("has_red_flower", a((bqa)bvs.bq)).a(consumer, "red_dye_from_poppy");
		jh.a(bkk.ms, 2).b(bvs.gW).a("red_dye").a("has_double_plant", a((bqa)bvs.gW)).a(consumer, "red_dye_from_rose_bush");
		jh.a(bkk.ms).b(bvs.bu).a("red_dye").a("has_red_flower", a((bqa)bvs.bu)).a(consumer, "red_dye_from_tulip");
		jg.a(bvs.iL).a('W', bkk.nu).a('N', bkk.pq).a("NW").a("WN").a("has_nether_wart", a(bkk.nu)).a(consumer);
		jg.a(bvs.hG).a('#', bvs.D).a("##").a("##").a("has_sand", a((bqa)bvs.D)).a(consumer);
		jg.a(bvs.ia, 6).a('#', bmr.a(bvs.hG, bvs.hH)).a("###").a("has_red_sandstone", a((bqa)bvs.hG)).a("has_chiseled_red_sandstone", a((bqa)bvs.hH)).a(consumer);
		jg.a(bvs.ib, 6).a('#', bvs.hI).a("###").a("has_cut_red_sandstone", a((bqa)bvs.hI)).a(consumer);
		jg.a(bvs.hJ, 4)
			.a('#', bmr.a(bvs.hG, bvs.hH, bvs.hI))
			.a("#  ")
			.a("## ")
			.a("###")
			.a("has_red_sandstone", a((bqa)bvs.hG))
			.a("has_chiseled_red_sandstone", a((bqa)bvs.hH))
			.a("has_cut_red_sandstone", a((bqa)bvs.hI))
			.a(consumer);
		jg.a(bvs.cX).a('#', bvs.cz).a('X', bkk.lP).a('I', bvs.b).a("#X#").a("III").a("has_redstone_torch", a((bqa)bvs.cz)).a(consumer);
		jg.a(bvs.at).a('#', bvs.C).a("##").a("##").a("has_sand", a((bqa)bvs.C)).a(consumer);
		jg.a(bvs.hS, 6).a('#', bmr.a(bvs.at, bvs.au)).a("###").a("has_sandstone", a((bqa)bvs.at)).a("has_chiseled_sandstone", a((bqa)bvs.au)).a(consumer);
		jg.a(bvs.hT, 6).a('#', bvs.av).a("###").a("has_cut_sandstone", a((bqa)bvs.av)).a(consumer);
		jg.a(bvs.ei, 4)
			.a('#', bmr.a(bvs.at, bvs.au, bvs.av))
			.a("#  ")
			.a("## ")
			.a("###")
			.a("has_sandstone", a((bqa)bvs.at))
			.a("has_chiseled_sandstone", a((bqa)bvs.au))
			.a("has_cut_sandstone", a((bqa)bvs.av))
			.a(consumer);
		jg.a(bvs.gz).a('S', bkk.pu).a('C', bkk.pv).a("SCS").a("CCC").a("SCS").a("has_prismarine_crystals", a(bkk.pv)).a(consumer);
		jg.a(bkk.ng).a('#', bkk.kk).a(" #").a("# ").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bkk.qm).a('W', ada.b).a('o', bkk.kk).a("WoW").a("WWW").a(" W ").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bvs.gn).a('#', bkk.md).a("###").a("###").a("###").a("has_slime_ball", a(bkk.md)).a(consumer);
		jh.a(bkk.md, 9).b(bvs.gn).a("has_slime", a((bqa)bvs.gn)).a(consumer);
		jg.a(bvs.hI, 4).a('#', bvs.hG).a("##").a("##").a("has_red_sandstone", a((bqa)bvs.hG)).a(consumer);
		jg.a(bvs.av, 4).a('#', bvs.at).a("##").a("##").a("has_sandstone", a((bqa)bvs.at)).a(consumer);
		jg.a(bvs.cE).a('#', bkk.lQ).a("##").a("##").a("has_snowball", a(bkk.lQ)).a(consumer);
		jg.a(bvs.cC, 6).a('#', bvs.cE).a("###").a("has_snowball", a(bkk.lQ)).a(consumer);
		jg.a(bvs.mf).a('L', ada.p).a('S', bkk.kB).a('#', ada.Q).a(" S ").a("S#S").a("LLL").a("has_stick", a(bkk.kB)).a("has_soul_sand", a(ada.Q)).a(consumer);
		jg.a(bkk.nE).a('#', bkk.nt).a('X', bkk.nh).a("###").a("#X#").a("###").a("has_melon", a(bkk.nh)).a(consumer);
		jg.a(bkk.qj, 2).a('#', bkk.mk).a('X', bkk.kg).a(" # ").a("#X#").a(" # ").a("has_glowstone_dust", a(bkk.mk)).a(consumer);
		jg.a(bkk.kB, 4).a('#', ada.b).a("#").a("#").b("sticks").a("has_planks", a(ada.b)).a(consumer);
		jg.a(bkk.kB, 1).a('#', bvs.kY).a("#").a("#").b("sticks").a("has_bamboo", a((bqa)bvs.kY)).a(consumer, "stick_from_bamboo_item");
		jg.a(bvs.aP).a('P', bvs.aW).a('S', bkk.md).a("S").a("P").a("has_slime_ball", a(bkk.md)).a(consumer);
		jg.a(bvs.du, 4).a('#', bvs.b).a("##").a("##").a("has_stone", a((bqa)bvs.b)).a(consumer);
		jg.a(bkk.kw).a('#', bkk.kB).a('X', ada.aa).a("XX").a("X#").a(" #").a("has_cobblestone", a(ada.aa)).a(consumer);
		jg.a(bvs.hX, 6).a('#', bvs.du).a("###").a("has_stone_bricks", a(ada.c)).a(consumer);
		jg.a(bvs.dS, 4).a('#', bvs.du).a("#  ").a("## ").a("###").a("has_stone_bricks", a(ada.c)).a(consumer);
		jh.a(bvs.cB).b(bvs.b).a("has_stone", a((bqa)bvs.b)).a(consumer);
		jg.a(bkk.kQ).a('#', bkk.kB).a('X', ada.aa).a("XX").a(" #").a(" #").a("has_cobblestone", a(ada.aa)).a(consumer);
		jg.a(bkk.kv).a('#', bkk.kB).a('X', ada.aa).a("XXX").a(" # ").a(" # ").a("has_cobblestone", a(ada.aa)).a(consumer);
		jg.a(bvs.cq).a('#', bvs.b).a("##").a("has_stone", a((bqa)bvs.b)).a(consumer);
		jg.a(bkk.ku).a('#', bkk.kB).a('X', ada.aa).a("X").a("#").a("#").a("has_cobblestone", a(ada.aa)).a(consumer);
		jg.a(bvs.hQ, 6).a('#', bvs.b).a("###").a("has_stone", a((bqa)bvs.b)).a(consumer);
		jg.a(bvs.hR, 6).a('#', bvs.id).a("###").a("has_smooth_stone", a((bqa)bvs.id)).a(consumer);
		jg.a(bvs.ci, 4).a('#', bvs.m).a("#  ").a("## ").a("###").a("has_cobblestone", a((bqa)bvs.m)).a(consumer);
		jg.a(bkk.kt).a('#', bkk.kB).a('X', ada.aa).a("X").a("X").a("#").a("has_cobblestone", a(ada.aa)).a(consumer);
		jg.a(bvs.aY).a('#', bkk.kM).a("##").a("##").a("has_string", a(bkk.kM)).a(consumer, "white_wool_from_string");
		jh.a(bkk.mM).b(bvs.cH).a("sugar").a("has_reeds", a((bqa)bvs.cH)).a(consumer, "sugar_from_sugar_cane");
		jh.a(bkk.mM, 3).b(bkk.rs).a("sugar").a("has_honey_bottle", a(bkk.rs)).a(consumer, "sugar_from_honey_bottle");
		jg.a(bvs.nb).a('H', bkk.fL).a('R', bkk.lP).a(" R ").a("RHR").a(" R ").a("has_redstone", a(bkk.lP)).a("has_hay_block", a((bqa)bvs.gA)).a(consumer);
		jg.a(bvs.bH).a('#', bmr.a(bvs.C, bvs.D)).a('X', bkk.kO).a("X#X").a("#X#").a("X#X").a("has_gunpowder", a(bkk.kO)).a(consumer);
		jg.a(bkk.ps).a('A', bvs.bH).a('B', bkk.lN).a("A").a("B").a("has_minecart", a(bkk.lN)).a(consumer);
		jg.a(bvs.bL, 4).a('#', bkk.kB).a('X', bmr.a(bkk.kh, bkk.ki)).a("X").a("#").a("has_stone_pickaxe", a(bkk.kv)).a(consumer);
		jg.a(bvs.cQ, 4).a('X', bmr.a(bkk.kh, bkk.ki)).a('#', bkk.kB).a('S', ada.Q).a("X").a("#").a("S").a("has_soul_sand", a(ada.Q)).a(consumer);
		jg.a(bvs.mc).a('#', bkk.cp).a('X', bkk.qv).a("XXX").a("X#X").a("XXX").a("has_iron_nugget", a(bkk.qv)).a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bvs.md).a('#', bkk.dp).a('X', bkk.qv).a("XXX").a("X#X").a("XXX").a("has_soul_torch", a(bkk.dp)).a(consumer);
		jh.a(bvs.fr).b(bvs.bR).b(bvs.el).a("has_tripwire_hook", a((bqa)bvs.el)).a(consumer);
		jg.a(bvs.el, 2).a('#', ada.b).a('S', bkk.kB).a('I', bkk.kk).a("I").a("S").a("#").a("has_string", a(bkk.kM)).a(consumer);
		jg.a(bkk.jY).a('X', bkk.jZ).a("XXX").a("X X").a("has_scute", a(bkk.jZ)).a(consumer);
		jh.a(bkk.kW, 9).b(bvs.gA).a("has_hay_block", a((bqa)bvs.gA)).a(consumer);
		jh.a(bkk.mK).b(bkk.mG).a("white_dye").a("has_bone_meal", a(bkk.mG)).a(consumer);
		jh.a(bkk.mK).b(bvs.bB).a("white_dye").a("has_white_flower", a((bqa)bvs.bB)).a(consumer, "white_dye_from_lily_of_the_valley");
		jg.a(bkk.ks).a('#', bkk.kB).a('X', ada.b).a("XX").a("X#").a(" #").a("has_stick", a(bkk.kB)).a(consumer);
		jg.a(bkk.kP).a('#', bkk.kB).a('X', ada.b).a("XX").a(" #").a(" #").a("has_stick", a(bkk.kB)).a(consumer);
		jg.a(bkk.kr).a('#', bkk.kB).a('X', ada.b).a("XXX").a(" # ").a(" # ").a("has_stick", a(bkk.kB)).a(consumer);
		jg.a(bkk.kq).a('#', bkk.kB).a('X', ada.b).a("X").a("#").a("#").a("has_stick", a(bkk.kB)).a(consumer);
		jg.a(bkk.kp).a('#', bkk.kB).a('X', ada.b).a("X").a("X").a("#").a("has_stick", a(bkk.kB)).a(consumer);
		jh.a(bkk.oS).b(bkk.mc).b(bkk.mr).b(bkk.kN).a("has_book", a(bkk.mc)).a(consumer);
		jh.a(bkk.mC).b(bvs.bp).a("yellow_dye").a("has_yellow_flower", a((bqa)bvs.bp)).a(consumer, "yellow_dye_from_dandelion");
		jh.a(bkk.mC, 2).b(bvs.gU).a("yellow_dye").a("has_double_plant", a((bqa)bvs.gU)).a(consumer, "yellow_dye_from_sunflower");
		jh.a(bkk.ni, 9).b(bvs.ke).a("has_dried_kelp_block", a((bqa)bvs.ke)).a(consumer);
		jh.a(bvs.ke).b(bkk.ni, 9).a("has_dried_kelp", a(bkk.ni)).a(consumer);
		jg.a(bvs.kW).a('#', bkk.qN).a('X', bkk.qO).a("###").a("#X#").a("###").a("has_nautilus_core", a(bkk.qO)).a("has_nautilus_shell", a(bkk.qN)).a(consumer);
		jg.a(bvs.ld, 4).a('#', bvs.d).a("#  ").a("## ").a("###").a("has_polished_granite", a((bqa)bvs.d)).a(consumer);
		jg.a(bvs.le, 4).a('#', bvs.ig).a("#  ").a("## ").a("###").a("has_smooth_red_sandstone", a((bqa)bvs.ig)).a(consumer);
		jg.a(bvs.lf, 4).a('#', bvs.dv).a("#  ").a("## ").a("###").a("has_mossy_stone_bricks", a((bqa)bvs.dv)).a(consumer);
		jg.a(bvs.lg, 4).a('#', bvs.f).a("#  ").a("## ").a("###").a("has_polished_diorite", a((bqa)bvs.f)).a(consumer);
		jg.a(bvs.lh, 4).a('#', bvs.bJ).a("#  ").a("## ").a("###").a("has_mossy_cobblestone", a((bqa)bvs.bJ)).a(consumer);
		jg.a(bvs.li, 4).a('#', bvs.iC).a("#  ").a("## ").a("###").a("has_end_stone_bricks", a((bqa)bvs.iC)).a(consumer);
		jg.a(bvs.lj, 4).a('#', bvs.b).a("#  ").a("## ").a("###").a("has_stone", a((bqa)bvs.b)).a(consumer);
		jg.a(bvs.lk, 4).a('#', bvs.ie).a("#  ").a("## ").a("###").a("has_smooth_sandstone", a((bqa)bvs.ie)).a(consumer);
		jg.a(bvs.ll, 4).a('#', bvs.if).a("#  ").a("## ").a("###").a("has_smooth_quartz", a((bqa)bvs.if)).a(consumer);
		jg.a(bvs.lm, 4).a('#', bvs.c).a("#  ").a("## ").a("###").a("has_granite", a((bqa)bvs.c)).a(consumer);
		jg.a(bvs.ln, 4).a('#', bvs.g).a("#  ").a("## ").a("###").a("has_andesite", a((bqa)bvs.g)).a(consumer);
		jg.a(bvs.lo, 4).a('#', bvs.iL).a("#  ").a("## ").a("###").a("has_red_nether_bricks", a((bqa)bvs.iL)).a(consumer);
		jg.a(bvs.lp, 4).a('#', bvs.h).a("#  ").a("## ").a("###").a("has_polished_andesite", a((bqa)bvs.h)).a(consumer);
		jg.a(bvs.lq, 4).a('#', bvs.e).a("#  ").a("## ").a("###").a("has_diorite", a((bqa)bvs.e)).a(consumer);
		jg.a(bvs.lr, 6).a('#', bvs.d).a("###").a("has_polished_granite", a((bqa)bvs.d)).a(consumer);
		jg.a(bvs.ls, 6).a('#', bvs.ig).a("###").a("has_smooth_red_sandstone", a((bqa)bvs.ig)).a(consumer);
		jg.a(bvs.lt, 6).a('#', bvs.dv).a("###").a("has_mossy_stone_bricks", a((bqa)bvs.dv)).a(consumer);
		jg.a(bvs.lu, 6).a('#', bvs.f).a("###").a("has_polished_diorite", a((bqa)bvs.f)).a(consumer);
		jg.a(bvs.lv, 6).a('#', bvs.bJ).a("###").a("has_mossy_cobblestone", a((bqa)bvs.bJ)).a(consumer);
		jg.a(bvs.lw, 6).a('#', bvs.iC).a("###").a("has_end_stone_bricks", a((bqa)bvs.iC)).a(consumer);
		jg.a(bvs.lx, 6).a('#', bvs.ie).a("###").a("has_smooth_sandstone", a((bqa)bvs.ie)).a(consumer);
		jg.a(bvs.ly, 6).a('#', bvs.if).a("###").a("has_smooth_quartz", a((bqa)bvs.if)).a(consumer);
		jg.a(bvs.lz, 6).a('#', bvs.c).a("###").a("has_granite", a((bqa)bvs.c)).a(consumer);
		jg.a(bvs.lA, 6).a('#', bvs.g).a("###").a("has_andesite", a((bqa)bvs.g)).a(consumer);
		jg.a(bvs.lB, 6).a('#', bvs.iL).a("###").a("has_red_nether_bricks", a((bqa)bvs.iL)).a(consumer);
		jg.a(bvs.lC, 6).a('#', bvs.h).a("###").a("has_polished_andesite", a((bqa)bvs.h)).a(consumer);
		jg.a(bvs.lD, 6).a('#', bvs.e).a("###").a("has_diorite", a((bqa)bvs.e)).a(consumer);
		jg.a(bvs.lE, 6).a('#', bvs.bG).a("###").a("###").a("has_bricks", a((bqa)bvs.bG)).a(consumer);
		jg.a(bvs.lF, 6).a('#', bvs.gq).a("###").a("###").a("has_prismarine", a((bqa)bvs.gq)).a(consumer);
		jg.a(bvs.lG, 6).a('#', bvs.hG).a("###").a("###").a("has_red_sandstone", a((bqa)bvs.hG)).a(consumer);
		jg.a(bvs.lH, 6).a('#', bvs.dv).a("###").a("###").a("has_mossy_stone_bricks", a((bqa)bvs.dv)).a(consumer);
		jg.a(bvs.lI, 6).a('#', bvs.c).a("###").a("###").a("has_granite", a((bqa)bvs.c)).a(consumer);
		jg.a(bvs.lJ, 6).a('#', bvs.du).a("###").a("###").a("has_stone_bricks", a((bqa)bvs.du)).a(consumer);
		jg.a(bvs.lK, 6).a('#', bvs.dV).a("###").a("###").a("has_nether_bricks", a((bqa)bvs.dV)).a(consumer);
		jg.a(bvs.lL, 6).a('#', bvs.g).a("###").a("###").a("has_andesite", a((bqa)bvs.g)).a(consumer);
		jg.a(bvs.lM, 6).a('#', bvs.iL).a("###").a("###").a("has_red_nether_bricks", a((bqa)bvs.iL)).a(consumer);
		jg.a(bvs.lN, 6).a('#', bvs.at).a("###").a("###").a("has_sandstone", a((bqa)bvs.at)).a(consumer);
		jg.a(bvs.lO, 6).a('#', bvs.iC).a("###").a("###").a("has_end_stone_bricks", a((bqa)bvs.iC)).a(consumer);
		jg.a(bvs.lP, 6).a('#', bvs.e).a("###").a("###").a("has_diorite", a((bqa)bvs.e)).a(consumer);
		jh.a(bkk.qT).b(bkk.mb).b(bkk.ph).a("has_creeper_head", a(bkk.ph)).a(consumer);
		jh.a(bkk.qU).b(bkk.mb).b(bkk.pe).a("has_wither_skeleton_skull", a(bkk.pe)).a(consumer);
		jh.a(bkk.qS).b(bkk.mb).b(bvs.by).a("has_oxeye_daisy", a((bqa)bvs.by)).a(consumer);
		jh.a(bkk.qV).b(bkk.mb).b(bkk.lB).a("has_enchanted_golden_apple", a(bkk.lB)).a(consumer);
		jg.a(bvs.lQ, 6).a('~', bkk.kM).a('I', bvs.kY).a("I~I").a("I I").a("I I").a("has_bamboo", a((bqa)bvs.kY)).a(consumer);
		jg.a(bvs.lX).a('I', bkk.kB).a('-', bvs.hQ).a('#', ada.b).a("I-I").a("# #").a("has_stone_slab", a((bqa)bvs.hQ)).a(consumer);
		jg.a(bvs.lU).a('#', bvs.id).a('X', bvs.bY).a('I', bkk.kk).a("III").a("IXI").a("###").a("has_smooth_stone", a((bqa)bvs.id)).a(consumer);
		jg.a(bvs.lT).a('#', ada.p).a('X', bvs.bY).a(" # ").a("#X#").a(" # ").a("has_furnace", a((bqa)bvs.bY)).a(consumer);
		jg.a(bvs.lV).a('#', ada.b).a('@', bkk.mb).a("@@").a("##").a("##").a("has_paper", a(bkk.mb)).a(consumer);
		jg.a(bvs.lZ).a('#', ada.b).a('@', bkk.kk).a("@@").a("##").a("##").a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jg.a(bvs.lW).a('#', ada.b).a('@', bkk.lw).a("@@").a("##").a("##").a("has_flint", a(bkk.lw)).a(consumer);
		jg.a(bvs.ma).a('I', bkk.kk).a('#', bvs.b).a(" I ").a("###").a("has_stone", a((bqa)bvs.b)).a(consumer);
		jg.a(bvs.no).a('S', bkk.dJ).a('#', bkk.km).a("SSS").a("S#S").a("SSS").a("has_netherite_ingot", a(bkk.km)).a(consumer);
		jg.a(bvs.ng).a('#', bkk.km).a("###").a("###").a("###").a("has_netherite_ingot", a(bkk.km)).a(consumer);
		jh.a(bkk.km, 9).b(bvs.ng).a("netherite_ingot").a("has_netherite_block", a((bqa)bvs.ng)).a(consumer, "netherite_ingot_from_netherite_block");
		jh.a(bkk.km).b(bkk.kn, 4).b(bkk.kl, 4).a("netherite_ingot").a("has_netherite_scrap", a(bkk.kn)).a(consumer);
		jg.a(bvs.nj).a('O', bvs.ni).a('G', bvs.cS).a("OOO").a("GGG").a("OOO").a("has_obsidian", a((bqa)bvs.ni)).a(consumer);
		jg.a(bvs.nq, 4).a('#', bvs.np).a("#  ").a("## ").a("###").a("has_blackstone", a((bqa)bvs.np)).a(consumer);
		jg.a(bvs.nB, 4).a('#', bvs.nt).a("#  ").a("## ").a("###").a("has_polished_blackstone", a((bqa)bvs.nt)).a(consumer);
		jg.a(bvs.ny, 4).a('#', bvs.nu).a("#  ").a("## ").a("###").a("has_polished_blackstone_bricks", a((bqa)bvs.nu)).a(consumer);
		jg.a(bvs.ns, 6).a('#', bvs.np).a("###").a("has_blackstone", a((bqa)bvs.np)).a(consumer);
		jg.a(bvs.nC, 6).a('#', bvs.nt).a("###").a("has_polished_blackstone", a((bqa)bvs.nt)).a(consumer);
		jg.a(bvs.nx, 6).a('#', bvs.nu).a("###").a("has_polished_blackstone_bricks", a((bqa)bvs.nu)).a(consumer);
		jg.a(bvs.nt, 4).a('S', bvs.np).a("SS").a("SS").a("has_blackstone", a((bqa)bvs.np)).a(consumer);
		jg.a(bvs.nu, 4).a('#', bvs.nt).a("##").a("##").a("has_polished_blackstone", a((bqa)bvs.nt)).a(consumer);
		jg.a(bvs.nw).a('#', bvs.nC).a("#").a("#").a("has_polished_blackstone", a((bqa)bvs.nt)).a(consumer);
		jg.a(bvs.nr, 6).a('#', bvs.np).a("###").a("###").a("has_blackstone", a((bqa)bvs.np)).a(consumer);
		jg.a(bvs.nF, 6).a('#', bvs.nt).a("###").a("###").a("has_polished_blackstone", a((bqa)bvs.nt)).a(consumer);
		jg.a(bvs.nz, 6).a('#', bvs.nu).a("###").a("###").a("has_polished_blackstone_bricks", a((bqa)bvs.nu)).a(consumer);
		jh.a(bvs.nE).b(bvs.nt).a("has_polished_blackstone", a((bqa)bvs.nt)).a(consumer);
		jg.a(bvs.nD).a('#', bvs.nt).a("##").a("has_polished_blackstone", a((bqa)bvs.nt)).a(consumer);
		jg.a(bvs.dI).a('I', bkk.kk).a('N', bkk.qv).a("N").a("I").a("N").a("has_iron_nugget", a(bkk.qv)).a("has_iron_ingot", a(bkk.kk)).a(consumer);
		jk.a(bmw.c).a(consumer, "armor_dye");
		jk.a(bmw.k).a(consumer, "banner_duplicate");
		jk.a(bmw.d).a(consumer, "book_cloning");
		jk.a(bmw.g).a(consumer, "firework_rocket");
		jk.a(bmw.h).a(consumer, "firework_star");
		jk.a(bmw.i).a(consumer, "firework_star_fade");
		jk.a(bmw.e).a(consumer, "map_cloning");
		jk.a(bmw.f).a(consumer, "map_extending");
		jk.a(bmw.o).a(consumer, "repair_item");
		jk.a(bmw.l).a(consumer, "shield_decoration");
		jk.a(bmw.m).a(consumer, "shulker_box_coloring");
		jk.a(bmw.j).a(consumer, "tipped_arrow");
		jk.a(bmw.n).a(consumer, "suspicious_stew");
		ji.c(bmr.a(bkk.oY), bkk.oZ, 0.35F, 200).a("has_potato", a(bkk.oY)).a(consumer);
		ji.c(bmr.a(bkk.lZ), bkk.lY, 0.3F, 200).a("has_clay_ball", a(bkk.lZ)).a(consumer);
		ji.c(bmr.a(ada.o), bkk.ki, 0.15F, 200).a("has_log", a(ada.o)).a(consumer);
		ji.c(bmr.a(bkk.qc), bkk.qd, 0.1F, 200).a("has_chorus_fruit", a(bkk.qc)).a(consumer);
		ji.c(bmr.a(bvs.H.h()), bkk.kh, 0.1F, 200).a("has_coal_ore", a((bqa)bvs.H)).a(consumer, "coal_from_smelting");
		ji.c(bmr.a(bkk.nl), bkk.nm, 0.35F, 200).a("has_beef", a(bkk.nl)).a(consumer);
		ji.c(bmr.a(bkk.nn), bkk.no, 0.35F, 200).a("has_chicken", a(bkk.nn)).a(consumer);
		ji.c(bmr.a(bkk.ml), bkk.mp, 0.35F, 200).a("has_cod", a(bkk.ml)).a(consumer);
		ji.c(bmr.a(bvs.kc), bkk.ni, 0.1F, 200).a("has_kelp", a((bqa)bvs.kc)).a(consumer, "dried_kelp_from_smelting");
		ji.c(bmr.a(bkk.mm), bkk.mq, 0.35F, 200).a("has_salmon", a(bkk.mm)).a(consumer);
		ji.c(bmr.a(bkk.pJ), bkk.pK, 0.35F, 200).a("has_mutton", a(bkk.pJ)).a(consumer);
		ji.c(bmr.a(bkk.lx), bkk.ly, 0.35F, 200).a("has_porkchop", a(bkk.lx)).a(consumer);
		ji.c(bmr.a(bkk.pw), bkk.px, 0.35F, 200).a("has_rabbit", a(bkk.pw)).a(consumer);
		ji.c(bmr.a(bvs.bT.h()), bkk.kj, 1.0F, 200).a("has_diamond_ore", a((bqa)bvs.bT)).a(consumer, "diamond_from_smelting");
		ji.c(bmr.a(bvs.aq.h()), bkk.mv, 0.2F, 200).a("has_lapis_ore", a((bqa)bvs.aq)).a(consumer, "lapis_from_smelting");
		ji.c(bmr.a(bvs.ej.h()), bkk.oU, 1.0F, 200).a("has_emerald_ore", a((bqa)bvs.ej)).a(consumer, "emerald_from_smelting");
		ji.c(bmr.a(ada.z), bvs.ap.h(), 0.1F, 200).a("has_sand", a(ada.z)).a(consumer);
		ji.c(bmr.a(ada.O), bkk.kl, 1.0F, 200).a("has_gold_ore", a(ada.O)).a(consumer);
		ji.c(bmr.a(bvs.kU.h()), bkk.mB, 0.1F, 200).a("has_sea_pickle", a((bqa)bvs.kU)).a(consumer, "lime_dye_from_smelting");
		ji.c(bmr.a(bvs.cF.h()), bkk.mt, 1.0F, 200).a("has_cactus", a((bqa)bvs.cF)).a(consumer);
		ji.c(bmr.a(bkk.kG, bkk.kF, bkk.kH, bkk.kT, bkk.kE, bkk.lo, bkk.lp, bkk.lq, bkk.lr, bkk.pD), bkk.nt, 0.1F, 200)
			.a("has_golden_pickaxe", a(bkk.kG))
			.a("has_golden_shovel", a(bkk.kF))
			.a("has_golden_axe", a(bkk.kH))
			.a("has_golden_hoe", a(bkk.kT))
			.a("has_golden_sword", a(bkk.kE))
			.a("has_golden_helmet", a(bkk.lo))
			.a("has_golden_chestplate", a(bkk.lp))
			.a("has_golden_leggings", a(bkk.lq))
			.a("has_golden_boots", a(bkk.lr))
			.a("has_golden_horse_armor", a(bkk.pD))
			.a(consumer, "gold_nugget_from_smelting");
		ji.c(bmr.a(bkk.kb, bkk.ka, bkk.kc, bkk.kR, bkk.ko, bkk.lg, bkk.lh, bkk.li, bkk.lj, bkk.pC, bkk.lc, bkk.ld, bkk.le, bkk.lf), bkk.qv, 0.1F, 200)
			.a("has_iron_pickaxe", a(bkk.kb))
			.a("has_iron_shovel", a(bkk.ka))
			.a("has_iron_axe", a(bkk.kc))
			.a("has_iron_hoe", a(bkk.kR))
			.a("has_iron_sword", a(bkk.ko))
			.a("has_iron_helmet", a(bkk.lg))
			.a("has_iron_chestplate", a(bkk.lh))
			.a("has_iron_leggings", a(bkk.li))
			.a("has_iron_boots", a(bkk.lj))
			.a("has_iron_horse_armor", a(bkk.pC))
			.a("has_chainmail_helmet", a(bkk.lc))
			.a("has_chainmail_chestplate", a(bkk.ld))
			.a("has_chainmail_leggings", a(bkk.le))
			.a("has_chainmail_boots", a(bkk.lf))
			.a(consumer, "iron_nugget_from_smelting");
		ji.c(bmr.a(bvs.G.h()), bkk.kk, 0.7F, 200).a("has_iron_ore", a(bvs.G.h())).a(consumer);
		ji.c(bmr.a(bvs.cG), bvs.gR.h(), 0.35F, 200).a("has_clay_block", a((bqa)bvs.cG)).a(consumer);
		ji.c(bmr.a(bvs.cL), bkk.pq, 0.1F, 200).a("has_netherrack", a((bqa)bvs.cL)).a(consumer);
		ji.c(bmr.a(bvs.fx), bkk.pr, 0.2F, 200).a("has_nether_quartz_ore", a((bqa)bvs.fx)).a(consumer);
		ji.c(bmr.a(bvs.cy), bkk.lP, 0.7F, 200).a("has_redstone_ore", a((bqa)bvs.cy)).a(consumer, "redstone_from_smelting");
		ji.c(bmr.a(bvs.ao), bvs.an.h(), 0.15F, 200).a("has_wet_sponge", a((bqa)bvs.ao)).a(consumer);
		ji.c(bmr.a(bvs.m), bvs.b.h(), 0.1F, 200).a("has_cobblestone", a((bqa)bvs.m)).a(consumer);
		ji.c(bmr.a(bvs.b), bvs.id.h(), 0.1F, 200).a("has_stone", a((bqa)bvs.b)).a(consumer);
		ji.c(bmr.a(bvs.at), bvs.ie.h(), 0.1F, 200).a("has_sandstone", a((bqa)bvs.at)).a(consumer);
		ji.c(bmr.a(bvs.hG), bvs.ig.h(), 0.1F, 200).a("has_red_sandstone", a((bqa)bvs.hG)).a(consumer);
		ji.c(bmr.a(bvs.fz), bvs.if.h(), 0.1F, 200).a("has_quartz_block", a((bqa)bvs.fz)).a(consumer);
		ji.c(bmr.a(bvs.du), bvs.dw.h(), 0.1F, 200).a("has_stone_bricks", a((bqa)bvs.du)).a(consumer);
		ji.c(bmr.a(bvs.fU), bvs.jv.h(), 0.1F, 200).a("has_black_terracotta", a((bqa)bvs.fU)).a(consumer);
		ji.c(bmr.a(bvs.fQ), bvs.jr.h(), 0.1F, 200).a("has_blue_terracotta", a((bqa)bvs.fQ)).a(consumer);
		ji.c(bmr.a(bvs.fR), bvs.js.h(), 0.1F, 200).a("has_brown_terracotta", a((bqa)bvs.fR)).a(consumer);
		ji.c(bmr.a(bvs.fO), bvs.jp.h(), 0.1F, 200).a("has_cyan_terracotta", a((bqa)bvs.fO)).a(consumer);
		ji.c(bmr.a(bvs.fM), bvs.jn.h(), 0.1F, 200).a("has_gray_terracotta", a((bqa)bvs.fM)).a(consumer);
		ji.c(bmr.a(bvs.fS), bvs.jt.h(), 0.1F, 200).a("has_green_terracotta", a((bqa)bvs.fS)).a(consumer);
		ji.c(bmr.a(bvs.fI), bvs.jj.h(), 0.1F, 200).a("has_light_blue_terracotta", a((bqa)bvs.fI)).a(consumer);
		ji.c(bmr.a(bvs.fN), bvs.jo.h(), 0.1F, 200).a("has_light_gray_terracotta", a((bqa)bvs.fN)).a(consumer);
		ji.c(bmr.a(bvs.fK), bvs.jl.h(), 0.1F, 200).a("has_lime_terracotta", a((bqa)bvs.fK)).a(consumer);
		ji.c(bmr.a(bvs.fH), bvs.ji.h(), 0.1F, 200).a("has_magenta_terracotta", a((bqa)bvs.fH)).a(consumer);
		ji.c(bmr.a(bvs.fG), bvs.jh.h(), 0.1F, 200).a("has_orange_terracotta", a((bqa)bvs.fG)).a(consumer);
		ji.c(bmr.a(bvs.fL), bvs.jm.h(), 0.1F, 200).a("has_pink_terracotta", a((bqa)bvs.fL)).a(consumer);
		ji.c(bmr.a(bvs.fP), bvs.jq.h(), 0.1F, 200).a("has_purple_terracotta", a((bqa)bvs.fP)).a(consumer);
		ji.c(bmr.a(bvs.fT), bvs.ju.h(), 0.1F, 200).a("has_red_terracotta", a((bqa)bvs.fT)).a(consumer);
		ji.c(bmr.a(bvs.fF), bvs.jg.h(), 0.1F, 200).a("has_white_terracotta", a((bqa)bvs.fF)).a(consumer);
		ji.c(bmr.a(bvs.fJ), bvs.jk.h(), 0.1F, 200).a("has_yellow_terracotta", a((bqa)bvs.fJ)).a(consumer);
		ji.c(bmr.a(bvs.nh), bkk.kn, 2.0F, 200).a("has_ancient_debris", a((bqa)bvs.nh)).a(consumer);
		ji.c(bmr.a(bvs.nu), bvs.nv.h(), 0.1F, 200).a("has_blackstone_bricks", a((bqa)bvs.nu)).a(consumer);
		ji.c(bmr.a(bvs.dV), bvs.nH.h(), 0.1F, 200).a("has_nether_bricks", a((bqa)bvs.dV)).a(consumer);
		ji.b(bmr.a(bvs.G.h()), bkk.kk, 0.7F, 100).a("has_iron_ore", a(bvs.G.h())).a(consumer, "iron_ingot_from_blasting");
		ji.b(bmr.a(ada.O), bkk.kl, 1.0F, 100).a("has_gold_ore", a(ada.O)).a(consumer, "gold_ingot_from_blasting");
		ji.b(bmr.a(bvs.bT.h()), bkk.kj, 1.0F, 100).a("has_diamond_ore", a((bqa)bvs.bT)).a(consumer, "diamond_from_blasting");
		ji.b(bmr.a(bvs.aq.h()), bkk.mv, 0.2F, 100).a("has_lapis_ore", a((bqa)bvs.aq)).a(consumer, "lapis_from_blasting");
		ji.b(bmr.a(bvs.cy), bkk.lP, 0.7F, 100).a("has_redstone_ore", a((bqa)bvs.cy)).a(consumer, "redstone_from_blasting");
		ji.b(bmr.a(bvs.H.h()), bkk.kh, 0.1F, 100).a("has_coal_ore", a((bqa)bvs.H)).a(consumer, "coal_from_blasting");
		ji.b(bmr.a(bvs.ej.h()), bkk.oU, 1.0F, 100).a("has_emerald_ore", a((bqa)bvs.ej)).a(consumer, "emerald_from_blasting");
		ji.b(bmr.a(bvs.fx), bkk.pr, 0.2F, 100).a("has_nether_quartz_ore", a((bqa)bvs.fx)).a(consumer, "quartz_from_blasting");
		ji.b(bmr.a(bkk.kG, bkk.kF, bkk.kH, bkk.kT, bkk.kE, bkk.lo, bkk.lp, bkk.lq, bkk.lr, bkk.pD), bkk.nt, 0.1F, 100)
			.a("has_golden_pickaxe", a(bkk.kG))
			.a("has_golden_shovel", a(bkk.kF))
			.a("has_golden_axe", a(bkk.kH))
			.a("has_golden_hoe", a(bkk.kT))
			.a("has_golden_sword", a(bkk.kE))
			.a("has_golden_helmet", a(bkk.lo))
			.a("has_golden_chestplate", a(bkk.lp))
			.a("has_golden_leggings", a(bkk.lq))
			.a("has_golden_boots", a(bkk.lr))
			.a("has_golden_horse_armor", a(bkk.pD))
			.a(consumer, "gold_nugget_from_blasting");
		ji.b(bmr.a(bkk.kb, bkk.ka, bkk.kc, bkk.kR, bkk.ko, bkk.lg, bkk.lh, bkk.li, bkk.lj, bkk.pC, bkk.lc, bkk.ld, bkk.le, bkk.lf), bkk.qv, 0.1F, 100)
			.a("has_iron_pickaxe", a(bkk.kb))
			.a("has_iron_shovel", a(bkk.ka))
			.a("has_iron_axe", a(bkk.kc))
			.a("has_iron_hoe", a(bkk.kR))
			.a("has_iron_sword", a(bkk.ko))
			.a("has_iron_helmet", a(bkk.lg))
			.a("has_iron_chestplate", a(bkk.lh))
			.a("has_iron_leggings", a(bkk.li))
			.a("has_iron_boots", a(bkk.lj))
			.a("has_iron_horse_armor", a(bkk.pC))
			.a("has_chainmail_helmet", a(bkk.lc))
			.a("has_chainmail_chestplate", a(bkk.ld))
			.a("has_chainmail_leggings", a(bkk.le))
			.a("has_chainmail_boots", a(bkk.lf))
			.a(consumer, "iron_nugget_from_blasting");
		ji.b(bmr.a(bvs.nh), bkk.kn, 2.0F, 100).a("has_ancient_debris", a((bqa)bvs.nh)).a(consumer, "netherite_scrap_from_blasting");
		a(consumer, "smoking", bmw.r, 100);
		a(consumer, "campfire_cooking", bmw.s, 600);
		jj.a(bmr.a(bvs.b), bvs.hQ, 2).a("has_stone", a((bqa)bvs.b)).a(consumer, "stone_slab_from_stone_stonecutting");
		jj.a(bmr.a(bvs.b), bvs.lj).a("has_stone", a((bqa)bvs.b)).a(consumer, "stone_stairs_from_stone_stonecutting");
		jj.a(bmr.a(bvs.b), bvs.du).a("has_stone", a((bqa)bvs.b)).a(consumer, "stone_bricks_from_stone_stonecutting");
		jj.a(bmr.a(bvs.b), bvs.hX, 2).a("has_stone", a((bqa)bvs.b)).a(consumer, "stone_brick_slab_from_stone_stonecutting");
		jj.a(bmr.a(bvs.b), bvs.dS).a("has_stone", a((bqa)bvs.b)).a(consumer, "stone_brick_stairs_from_stone_stonecutting");
		jj.a(bmr.a(bvs.b), bvs.dx).a("has_stone", a((bqa)bvs.b)).a(consumer, "chiseled_stone_bricks_stone_from_stonecutting");
		jj.a(bmr.a(bvs.b), bvs.lJ).a("has_stone", a((bqa)bvs.b)).a(consumer, "stone_brick_walls_from_stone_stonecutting");
		jj.a(bmr.a(bvs.at), bvs.av).a("has_sandstone", a((bqa)bvs.at)).a(consumer, "cut_sandstone_from_sandstone_stonecutting");
		jj.a(bmr.a(bvs.at), bvs.hS, 2).a("has_sandstone", a((bqa)bvs.at)).a(consumer, "sandstone_slab_from_sandstone_stonecutting");
		jj.a(bmr.a(bvs.at), bvs.hT, 2).a("has_sandstone", a((bqa)bvs.at)).a(consumer, "cut_sandstone_slab_from_sandstone_stonecutting");
		jj.a(bmr.a(bvs.av), bvs.hT, 2).a("has_cut_sandstone", a((bqa)bvs.at)).a(consumer, "cut_sandstone_slab_from_cut_sandstone_stonecutting");
		jj.a(bmr.a(bvs.at), bvs.ei).a("has_sandstone", a((bqa)bvs.at)).a(consumer, "sandstone_stairs_from_sandstone_stonecutting");
		jj.a(bmr.a(bvs.at), bvs.lN).a("has_sandstone", a((bqa)bvs.at)).a(consumer, "sandstone_wall_from_sandstone_stonecutting");
		jj.a(bmr.a(bvs.at), bvs.au).a("has_sandstone", a((bqa)bvs.at)).a(consumer, "chiseled_sandstone_from_sandstone_stonecutting");
		jj.a(bmr.a(bvs.hG), bvs.hI).a("has_red_sandstone", a((bqa)bvs.hG)).a(consumer, "cut_red_sandstone_from_red_sandstone_stonecutting");
		jj.a(bmr.a(bvs.hG), bvs.ia, 2).a("has_red_sandstone", a((bqa)bvs.hG)).a(consumer, "red_sandstone_slab_from_red_sandstone_stonecutting");
		jj.a(bmr.a(bvs.hG), bvs.ib, 2).a("has_red_sandstone", a((bqa)bvs.hG)).a(consumer, "cut_red_sandstone_slab_from_red_sandstone_stonecutting");
		jj.a(bmr.a(bvs.hI), bvs.ib, 2).a("has_cut_red_sandstone", a((bqa)bvs.hG)).a(consumer, "cut_red_sandstone_slab_from_cut_red_sandstone_stonecutting");
		jj.a(bmr.a(bvs.hG), bvs.hJ).a("has_red_sandstone", a((bqa)bvs.hG)).a(consumer, "red_sandstone_stairs_from_red_sandstone_stonecutting");
		jj.a(bmr.a(bvs.hG), bvs.lG).a("has_red_sandstone", a((bqa)bvs.hG)).a(consumer, "red_sandstone_wall_from_red_sandstone_stonecutting");
		jj.a(bmr.a(bvs.hG), bvs.hH).a("has_red_sandstone", a((bqa)bvs.hG)).a(consumer, "chiseled_red_sandstone_from_red_sandstone_stonecutting");
		jj.a(bmr.a(bvs.fz), bvs.hZ, 2).a("has_quartz_block", a((bqa)bvs.fz)).a(consumer, "quartz_slab_from_stonecutting");
		jj.a(bmr.a(bvs.fz), bvs.fC).a("has_quartz_block", a((bqa)bvs.fz)).a(consumer, "quartz_stairs_from_quartz_block_stonecutting");
		jj.a(bmr.a(bvs.fz), bvs.fB).a("has_quartz_block", a((bqa)bvs.fz)).a(consumer, "quartz_pillar_from_quartz_block_stonecutting");
		jj.a(bmr.a(bvs.fz), bvs.fA).a("has_quartz_block", a((bqa)bvs.fz)).a(consumer, "chiseled_quartz_block_from_quartz_block_stonecutting");
		jj.a(bmr.a(bvs.fz), bvs.nI).a("has_quartz_block", a((bqa)bvs.fz)).a(consumer, "quartz_bricks_from_quartz_block_stonecutting");
		jj.a(bmr.a(bvs.m), bvs.ci).a("has_cobblestone", a((bqa)bvs.m)).a(consumer, "cobblestone_stairs_from_cobblestone_stonecutting");
		jj.a(bmr.a(bvs.m), bvs.hV, 2).a("has_cobblestone", a((bqa)bvs.m)).a(consumer, "cobblestone_slab_from_cobblestone_stonecutting");
		jj.a(bmr.a(bvs.m), bvs.et).a("has_cobblestone", a((bqa)bvs.m)).a(consumer, "cobblestone_wall_from_cobblestone_stonecutting");
		jj.a(bmr.a(bvs.du), bvs.hX, 2).a("has_stone_bricks", a((bqa)bvs.du)).a(consumer, "stone_brick_slab_from_stone_bricks_stonecutting");
		jj.a(bmr.a(bvs.du), bvs.dS).a("has_stone_bricks", a((bqa)bvs.du)).a(consumer, "stone_brick_stairs_from_stone_bricks_stonecutting");
		jj.a(bmr.a(bvs.du), bvs.lJ).a("has_stone_bricks", a((bqa)bvs.du)).a(consumer, "stone_brick_wall_from_stone_bricks_stonecutting");
		jj.a(bmr.a(bvs.du), bvs.dx).a("has_stone_bricks", a((bqa)bvs.du)).a(consumer, "chiseled_stone_bricks_from_stone_bricks_stonecutting");
		jj.a(bmr.a(bvs.bG), bvs.hW, 2).a("has_bricks", a((bqa)bvs.bG)).a(consumer, "brick_slab_from_bricks_stonecutting");
		jj.a(bmr.a(bvs.bG), bvs.dR).a("has_bricks", a((bqa)bvs.bG)).a(consumer, "brick_stairs_from_bricks_stonecutting");
		jj.a(bmr.a(bvs.bG), bvs.lE).a("has_bricks", a((bqa)bvs.bG)).a(consumer, "brick_wall_from_bricks_stonecutting");
		jj.a(bmr.a(bvs.dV), bvs.hY, 2).a("has_nether_bricks", a((bqa)bvs.dV)).a(consumer, "nether_brick_slab_from_nether_bricks_stonecutting");
		jj.a(bmr.a(bvs.dV), bvs.dX).a("has_nether_bricks", a((bqa)bvs.dV)).a(consumer, "nether_brick_stairs_from_nether_bricks_stonecutting");
		jj.a(bmr.a(bvs.dV), bvs.lK).a("has_nether_bricks", a((bqa)bvs.dV)).a(consumer, "nether_brick_wall_from_nether_bricks_stonecutting");
		jj.a(bmr.a(bvs.dV), bvs.nG).a("has_nether_bricks", a((bqa)bvs.dV)).a(consumer, "chiseled_nether_bricks_from_nether_bricks_stonecutting");
		jj.a(bmr.a(bvs.iL), bvs.lB, 2).a("has_nether_bricks", a((bqa)bvs.iL)).a(consumer, "red_nether_brick_slab_from_red_nether_bricks_stonecutting");
		jj.a(bmr.a(bvs.iL), bvs.lo).a("has_nether_bricks", a((bqa)bvs.iL)).a(consumer, "red_nether_brick_stairs_from_red_nether_bricks_stonecutting");
		jj.a(bmr.a(bvs.iL), bvs.lM).a("has_nether_bricks", a((bqa)bvs.iL)).a(consumer, "red_nether_brick_wall_from_red_nether_bricks_stonecutting");
		jj.a(bmr.a(bvs.iz), bvs.ic, 2).a("has_purpur_block", a((bqa)bvs.iz)).a(consumer, "purpur_slab_from_purpur_block_stonecutting");
		jj.a(bmr.a(bvs.iz), bvs.iB).a("has_purpur_block", a((bqa)bvs.iz)).a(consumer, "purpur_stairs_from_purpur_block_stonecutting");
		jj.a(bmr.a(bvs.iz), bvs.iA).a("has_purpur_block", a((bqa)bvs.iz)).a(consumer, "purpur_pillar_from_purpur_block_stonecutting");
		jj.a(bmr.a(bvs.gq), bvs.gw, 2).a("has_prismarine", a((bqa)bvs.gq)).a(consumer, "prismarine_slab_from_prismarine_stonecutting");
		jj.a(bmr.a(bvs.gq), bvs.gt).a("has_prismarine", a((bqa)bvs.gq)).a(consumer, "prismarine_stairs_from_prismarine_stonecutting");
		jj.a(bmr.a(bvs.gq), bvs.lF).a("has_prismarine", a((bqa)bvs.gq)).a(consumer, "prismarine_wall_from_prismarine_stonecutting");
		jj.a(bmr.a(bvs.gr), bvs.gx, 2).a("has_prismarine_brick", a((bqa)bvs.gr)).a(consumer, "prismarine_brick_slab_from_prismarine_stonecutting");
		jj.a(bmr.a(bvs.gr), bvs.gu).a("has_prismarine_brick", a((bqa)bvs.gr)).a(consumer, "prismarine_brick_stairs_from_prismarine_stonecutting");
		jj.a(bmr.a(bvs.gs), bvs.gy, 2).a("has_dark_prismarine", a((bqa)bvs.gs)).a(consumer, "dark_prismarine_slab_from_dark_prismarine_stonecutting");
		jj.a(bmr.a(bvs.gs), bvs.gv).a("has_dark_prismarine", a((bqa)bvs.gs)).a(consumer, "dark_prismarine_stairs_from_dark_prismarine_stonecutting");
		jj.a(bmr.a(bvs.g), bvs.lA, 2).a("has_andesite", a((bqa)bvs.g)).a(consumer, "andesite_slab_from_andesite_stonecutting");
		jj.a(bmr.a(bvs.g), bvs.ln).a("has_andesite", a((bqa)bvs.g)).a(consumer, "andesite_stairs_from_andesite_stonecutting");
		jj.a(bmr.a(bvs.g), bvs.lL).a("has_andesite", a((bqa)bvs.g)).a(consumer, "andesite_wall_from_andesite_stonecutting");
		jj.a(bmr.a(bvs.g), bvs.h).a("has_andesite", a((bqa)bvs.g)).a(consumer, "polished_andesite_from_andesite_stonecutting");
		jj.a(bmr.a(bvs.g), bvs.lC, 2).a("has_andesite", a((bqa)bvs.g)).a(consumer, "polished_andesite_slab_from_andesite_stonecutting");
		jj.a(bmr.a(bvs.g), bvs.lp).a("has_andesite", a((bqa)bvs.g)).a(consumer, "polished_andesite_stairs_from_andesite_stonecutting");
		jj.a(bmr.a(bvs.h), bvs.lC, 2).a("has_polished_andesite", a((bqa)bvs.h)).a(consumer, "polished_andesite_slab_from_polished_andesite_stonecutting");
		jj.a(bmr.a(bvs.h), bvs.lp).a("has_polished_andesite", a((bqa)bvs.h)).a(consumer, "polished_andesite_stairs_from_polished_andesite_stonecutting");
		jj.a(bmr.a(bvs.cO), bvs.cP).a("has_basalt", a((bqa)bvs.cO)).a(consumer, "polished_basalt_from_basalt_stonecutting");
		jj.a(bmr.a(bvs.c), bvs.lz, 2).a("has_granite", a((bqa)bvs.c)).a(consumer, "granite_slab_from_granite_stonecutting");
		jj.a(bmr.a(bvs.c), bvs.lm).a("has_granite", a((bqa)bvs.c)).a(consumer, "granite_stairs_from_granite_stonecutting");
		jj.a(bmr.a(bvs.c), bvs.lI).a("has_granite", a((bqa)bvs.c)).a(consumer, "granite_wall_from_granite_stonecutting");
		jj.a(bmr.a(bvs.c), bvs.d).a("has_granite", a((bqa)bvs.c)).a(consumer, "polished_granite_from_granite_stonecutting");
		jj.a(bmr.a(bvs.c), bvs.lr, 2).a("has_granite", a((bqa)bvs.c)).a(consumer, "polished_granite_slab_from_granite_stonecutting");
		jj.a(bmr.a(bvs.c), bvs.ld).a("has_granite", a((bqa)bvs.c)).a(consumer, "polished_granite_stairs_from_granite_stonecutting");
		jj.a(bmr.a(bvs.d), bvs.lr, 2).a("has_polished_granite", a((bqa)bvs.d)).a(consumer, "polished_granite_slab_from_polished_granite_stonecutting");
		jj.a(bmr.a(bvs.d), bvs.ld).a("has_polished_granite", a((bqa)bvs.d)).a(consumer, "polished_granite_stairs_from_polished_granite_stonecutting");
		jj.a(bmr.a(bvs.e), bvs.lD, 2).a("has_diorite", a((bqa)bvs.e)).a(consumer, "diorite_slab_from_diorite_stonecutting");
		jj.a(bmr.a(bvs.e), bvs.lq).a("has_diorite", a((bqa)bvs.e)).a(consumer, "diorite_stairs_from_diorite_stonecutting");
		jj.a(bmr.a(bvs.e), bvs.lP).a("has_diorite", a((bqa)bvs.e)).a(consumer, "diorite_wall_from_diorite_stonecutting");
		jj.a(bmr.a(bvs.e), bvs.f).a("has_diorite", a((bqa)bvs.e)).a(consumer, "polished_diorite_from_diorite_stonecutting");
		jj.a(bmr.a(bvs.e), bvs.lu, 2).a("has_diorite", a((bqa)bvs.f)).a(consumer, "polished_diorite_slab_from_diorite_stonecutting");
		jj.a(bmr.a(bvs.e), bvs.lg).a("has_diorite", a((bqa)bvs.f)).a(consumer, "polished_diorite_stairs_from_diorite_stonecutting");
		jj.a(bmr.a(bvs.f), bvs.lu, 2).a("has_polished_diorite", a((bqa)bvs.f)).a(consumer, "polished_diorite_slab_from_polished_diorite_stonecutting");
		jj.a(bmr.a(bvs.f), bvs.lg).a("has_polished_diorite", a((bqa)bvs.f)).a(consumer, "polished_diorite_stairs_from_polished_diorite_stonecutting");
		jj.a(bmr.a(bvs.dv), bvs.lt, 2).a("has_mossy_stone_bricks", a((bqa)bvs.dv)).a(consumer, "mossy_stone_brick_slab_from_mossy_stone_brick_stonecutting");
		jj.a(bmr.a(bvs.dv), bvs.lf).a("has_mossy_stone_bricks", a((bqa)bvs.dv)).a(consumer, "mossy_stone_brick_stairs_from_mossy_stone_brick_stonecutting");
		jj.a(bmr.a(bvs.dv), bvs.lH).a("has_mossy_stone_bricks", a((bqa)bvs.dv)).a(consumer, "mossy_stone_brick_wall_from_mossy_stone_brick_stonecutting");
		jj.a(bmr.a(bvs.bJ), bvs.lv, 2).a("has_mossy_cobblestone", a((bqa)bvs.bJ)).a(consumer, "mossy_cobblestone_slab_from_mossy_cobblestone_stonecutting");
		jj.a(bmr.a(bvs.bJ), bvs.lh).a("has_mossy_cobblestone", a((bqa)bvs.bJ)).a(consumer, "mossy_cobblestone_stairs_from_mossy_cobblestone_stonecutting");
		jj.a(bmr.a(bvs.bJ), bvs.eu).a("has_mossy_cobblestone", a((bqa)bvs.bJ)).a(consumer, "mossy_cobblestone_wall_from_mossy_cobblestone_stonecutting");
		jj.a(bmr.a(bvs.ie), bvs.lx, 2).a("has_smooth_sandstone", a((bqa)bvs.ie)).a(consumer, "smooth_sandstone_slab_from_smooth_sandstone_stonecutting");
		jj.a(bmr.a(bvs.ie), bvs.lk).a("has_mossy_cobblestone", a((bqa)bvs.ie)).a(consumer, "smooth_sandstone_stairs_from_smooth_sandstone_stonecutting");
		jj.a(bmr.a(bvs.ig), bvs.ls, 2).a("has_smooth_red_sandstone", a((bqa)bvs.ig)).a(consumer, "smooth_red_sandstone_slab_from_smooth_red_sandstone_stonecutting");
		jj.a(bmr.a(bvs.ig), bvs.le).a("has_smooth_red_sandstone", a((bqa)bvs.ig)).a(consumer, "smooth_red_sandstone_stairs_from_smooth_red_sandstone_stonecutting");
		jj.a(bmr.a(bvs.if), bvs.ly, 2).a("has_smooth_quartz", a((bqa)bvs.if)).a(consumer, "smooth_quartz_slab_from_smooth_quartz_stonecutting");
		jj.a(bmr.a(bvs.if), bvs.ll).a("has_smooth_quartz", a((bqa)bvs.if)).a(consumer, "smooth_quartz_stairs_from_smooth_quartz_stonecutting");
		jj.a(bmr.a(bvs.iC), bvs.lw, 2).a("has_end_stone_brick", a((bqa)bvs.iC)).a(consumer, "end_stone_brick_slab_from_end_stone_brick_stonecutting");
		jj.a(bmr.a(bvs.iC), bvs.li).a("has_end_stone_brick", a((bqa)bvs.iC)).a(consumer, "end_stone_brick_stairs_from_end_stone_brick_stonecutting");
		jj.a(bmr.a(bvs.iC), bvs.lO).a("has_end_stone_brick", a((bqa)bvs.iC)).a(consumer, "end_stone_brick_wall_from_end_stone_brick_stonecutting");
		jj.a(bmr.a(bvs.ee), bvs.iC).a("has_end_stone", a((bqa)bvs.ee)).a(consumer, "end_stone_bricks_from_end_stone_stonecutting");
		jj.a(bmr.a(bvs.ee), bvs.lw, 2).a("has_end_stone", a((bqa)bvs.ee)).a(consumer, "end_stone_brick_slab_from_end_stone_stonecutting");
		jj.a(bmr.a(bvs.ee), bvs.li).a("has_end_stone", a((bqa)bvs.ee)).a(consumer, "end_stone_brick_stairs_from_end_stone_stonecutting");
		jj.a(bmr.a(bvs.ee), bvs.lO).a("has_end_stone", a((bqa)bvs.ee)).a(consumer, "end_stone_brick_wall_from_end_stone_stonecutting");
		jj.a(bmr.a(bvs.id), bvs.hR, 2).a("has_smooth_stone", a((bqa)bvs.id)).a(consumer, "smooth_stone_slab_from_smooth_stone_stonecutting");
		jj.a(bmr.a(bvs.np), bvs.ns, 2).a("has_blackstone", a((bqa)bvs.np)).a(consumer, "blackstone_slab_from_blackstone_stonecutting");
		jj.a(bmr.a(bvs.np), bvs.nq).a("has_blackstone", a((bqa)bvs.np)).a(consumer, "blackstone_stairs_from_blackstone_stonecutting");
		jj.a(bmr.a(bvs.np), bvs.nr).a("has_blackstone", a((bqa)bvs.np)).a(consumer, "blackstone_wall_from_blackstone_stonecutting");
		jj.a(bmr.a(bvs.np), bvs.nt).a("has_blackstone", a((bqa)bvs.np)).a(consumer, "polished_blackstone_from_blackstone_stonecutting");
		jj.a(bmr.a(bvs.np), bvs.nF).a("has_blackstone", a((bqa)bvs.np)).a(consumer, "polished_blackstone_wall_from_blackstone_stonecutting");
		jj.a(bmr.a(bvs.np), bvs.nC, 2).a("has_blackstone", a((bqa)bvs.np)).a(consumer, "polished_blackstone_slab_from_blackstone_stonecutting");
		jj.a(bmr.a(bvs.np), bvs.nB).a("has_blackstone", a((bqa)bvs.np)).a(consumer, "polished_blackstone_stairs_from_blackstone_stonecutting");
		jj.a(bmr.a(bvs.np), bvs.nw).a("has_blackstone", a((bqa)bvs.np)).a(consumer, "chiseled_polished_blackstone_from_blackstone_stonecutting");
		jj.a(bmr.a(bvs.np), bvs.nu).a("has_blackstone", a((bqa)bvs.np)).a(consumer, "polished_blackstone_bricks_from_blackstone_stonecutting");
		jj.a(bmr.a(bvs.np), bvs.nx, 2).a("has_blackstone", a((bqa)bvs.np)).a(consumer, "polished_blackstone_brick_slab_from_blackstone_stonecutting");
		jj.a(bmr.a(bvs.np), bvs.ny).a("has_blackstone", a((bqa)bvs.np)).a(consumer, "polished_blackstone_brick_stairs_from_blackstone_stonecutting");
		jj.a(bmr.a(bvs.np), bvs.nz).a("has_blackstone", a((bqa)bvs.np)).a(consumer, "polished_blackstone_brick_wall_from_blackstone_stonecutting");
		jj.a(bmr.a(bvs.nt), bvs.nC, 2).a("has_polished_blackstone", a((bqa)bvs.nt)).a(consumer, "polished_blackstone_slab_from_polished_blackstone_stonecutting");
		jj.a(bmr.a(bvs.nt), bvs.nB).a("has_polished_blackstone", a((bqa)bvs.nt)).a(consumer, "polished_blackstone_stairs_from_polished_blackstone_stonecutting");
		jj.a(bmr.a(bvs.nt), bvs.nu).a("has_polished_blackstone", a((bqa)bvs.nt)).a(consumer, "polished_blackstone_bricks_from_polished_blackstone_stonecutting");
		jj.a(bmr.a(bvs.nt), bvs.nF).a("has_polished_blackstone", a((bqa)bvs.nt)).a(consumer, "polished_blackstone_wall_from_polished_blackstone_stonecutting");
		jj.a(bmr.a(bvs.nt), bvs.nx, 2)
			.a("has_polished_blackstone", a((bqa)bvs.nt))
			.a(consumer, "polished_blackstone_brick_slab_from_polished_blackstone_stonecutting");
		jj.a(bmr.a(bvs.nt), bvs.ny)
			.a("has_polished_blackstone", a((bqa)bvs.nt))
			.a(consumer, "polished_blackstone_brick_stairs_from_polished_blackstone_stonecutting");
		jj.a(bmr.a(bvs.nt), bvs.nz).a("has_polished_blackstone", a((bqa)bvs.nt)).a(consumer, "polished_blackstone_brick_wall_from_polished_blackstone_stonecutting");
		jj.a(bmr.a(bvs.nt), bvs.nw).a("has_polished_blackstone", a((bqa)bvs.nt)).a(consumer, "chiseled_polished_blackstone_from_polished_blackstone_stonecutting");
		jj.a(bmr.a(bvs.nu), bvs.nx, 2)
			.a("has_polished_blackstone_bricks", a((bqa)bvs.nu))
			.a(consumer, "polished_blackstone_brick_slab_from_polished_blackstone_bricks_stonecutting");
		jj.a(bmr.a(bvs.nu), bvs.ny)
			.a("has_polished_blackstone_bricks", a((bqa)bvs.nu))
			.a(consumer, "polished_blackstone_brick_stairs_from_polished_blackstone_bricks_stonecutting");
		jj.a(bmr.a(bvs.nu), bvs.nz)
			.a("has_polished_blackstone_bricks", a((bqa)bvs.nu))
			.a(consumer, "polished_blackstone_brick_wall_from_polished_blackstone_bricks_stonecutting");
		a(consumer, bkk.ll, bkk.lt);
		a(consumer, bkk.lm, bkk.lu);
		a(consumer, bkk.lk, bkk.ls);
		a(consumer, bkk.ln, bkk.lv);
		a(consumer, bkk.kx, bkk.kI);
		a(consumer, bkk.kA, bkk.kL);
		a(consumer, bkk.kz, bkk.kK);
		a(consumer, bkk.kS, bkk.kU);
		a(consumer, bkk.ky, bkk.kJ);
	}

	private static void a(Consumer<je> consumer, bke bke2, bke bke3) {
		jl.a(bmr.a(bke2), bmr.a(bkk.km), bke3).a("has_netherite_ingot", a(bkk.km)).a(consumer, gl.am.b(bke3.h()).a() + "_smithing");
	}

	private static void a(Consumer<je> consumer, bqa bqa, adf<bke> adf) {
		jh.a(bqa, 4).a(adf).a("planks").a("has_log", a(adf)).a(consumer);
	}

	private static void b(Consumer<je> consumer, bqa bqa, adf<bke> adf) {
		jh.a(bqa, 4).a(adf).a("planks").a("has_logs", a(adf)).a(consumer);
	}

	private static void a(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jg.a(bqa2, 3).a('#', bqa3).a("##").a("##").b("bark").a("has_log", a(bqa3)).a(consumer);
	}

	private static void b(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jg.a(bqa2).a('#', bqa3).a("# #").a("###").b("boat").a("in_water", a(bvs.A)).a(consumer);
	}

	private static void c(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jh.a(bqa2).b(bqa3).a("wooden_button").a("has_planks", a(bqa3)).a(consumer);
	}

	private static void d(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jg.a(bqa2, 3).a('#', bqa3).a("##").a("##").a("##").b("wooden_door").a("has_planks", a(bqa3)).a(consumer);
	}

	private static void e(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jg.a(bqa2, 3).a('#', bkk.kB).a('W', bqa3).a("W#W").a("W#W").b("wooden_fence").a("has_planks", a(bqa3)).a(consumer);
	}

	private static void f(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jg.a(bqa2).a('#', bkk.kB).a('W', bqa3).a("#W#").a("#W#").b("wooden_fence_gate").a("has_planks", a(bqa3)).a(consumer);
	}

	private static void g(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jg.a(bqa2).a('#', bqa3).a("##").b("wooden_pressure_plate").a("has_planks", a(bqa3)).a(consumer);
	}

	private static void h(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jg.a(bqa2, 6).a('#', bqa3).a("###").b("wooden_slab").a("has_planks", a(bqa3)).a(consumer);
	}

	private static void i(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jg.a(bqa2, 4).a('#', bqa3).a("#  ").a("## ").a("###").b("wooden_stairs").a("has_planks", a(bqa3)).a(consumer);
	}

	private static void j(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jg.a(bqa2, 2).a('#', bqa3).a("###").a("###").b("wooden_trapdoor").a("has_planks", a(bqa3)).a(consumer);
	}

	private static void k(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		String string4 = gl.am.b(bqa3.h()).a();
		jg.a(bqa2, 3).b("sign").a('#', bqa3).a('X', bkk.kB).a("###").a("###").a(" X ").a("has_" + string4, a(bqa3)).a(consumer);
	}

	private static void l(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jh.a(bqa2).b(bqa3).b(bvs.aY).a("wool").a("has_white_wool", a((bqa)bvs.aY)).a(consumer);
	}

	private static void m(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		String string4 = gl.am.b(bqa3.h()).a();
		jg.a(bqa2, 3).a('#', bqa3).a("##").b("carpet").a("has_" + string4, a(bqa3)).a(consumer);
	}

	private static void n(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		String string4 = gl.am.b(bqa2.h()).a();
		String string5 = gl.am.b(bqa3.h()).a();
		jg.a(bqa2, 8)
			.a('#', bvs.gB)
			.a('$', bqa3)
			.a("###")
			.a("#$#")
			.a("###")
			.b("carpet")
			.a("has_white_carpet", a((bqa)bvs.gB))
			.a("has_" + string5, a(bqa3))
			.a(consumer, string4 + "_from_white_carpet");
	}

	private static void o(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		String string4 = gl.am.b(bqa3.h()).a();
		jg.a(bqa2).a('#', bqa3).a('X', ada.b).a("###").a("XXX").b("bed").a("has_" + string4, a(bqa3)).a(consumer);
	}

	private static void p(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		String string4 = gl.am.b(bqa2.h()).a();
		jh.a(bqa2).b(bkk.mO).b(bqa3).a("dyed_bed").a("has_bed", a(bkk.mO)).a(consumer, string4 + "_from_white_bed");
	}

	private static void q(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		String string4 = gl.am.b(bqa3.h()).a();
		jg.a(bqa2).a('#', bqa3).a('|', bkk.kB).a("###").a("###").a(" | ").b("banner").a("has_" + string4, a(bqa3)).a(consumer);
	}

	private static void r(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jg.a(bqa2, 8).a('#', bvs.ap).a('X', bqa3).a("###").a("#X#").a("###").b("stained_glass").a("has_glass", a((bqa)bvs.ap)).a(consumer);
	}

	private static void s(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jg.a(bqa2, 16).a('#', bqa3).a("###").a("###").b("stained_glass_pane").a("has_glass", a(bqa3)).a(consumer);
	}

	private static void t(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		String string4 = gl.am.b(bqa2.h()).a();
		String string5 = gl.am.b(bqa3.h()).a();
		jg.a(bqa2, 8)
			.a('#', bvs.dJ)
			.a('$', bqa3)
			.a("###")
			.a("#$#")
			.a("###")
			.b("stained_glass_pane")
			.a("has_glass_pane", a((bqa)bvs.dJ))
			.a("has_" + string5, a(bqa3))
			.a(consumer, string4 + "_from_glass_pane");
	}

	private static void u(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jg.a(bqa2, 8).a('#', bvs.gR).a('X', bqa3).a("###").a("#X#").a("###").b("stained_terracotta").a("has_terracotta", a((bqa)bvs.gR)).a(consumer);
	}

	private static void v(Consumer<je> consumer, bqa bqa2, bqa bqa3) {
		jh.a(bqa2, 8).b(bqa3).b(bvs.C, 4).b(bvs.E, 4).a("concrete_powder").a("has_sand", a((bqa)bvs.C)).a("has_gravel", a((bqa)bvs.E)).a(consumer);
	}

	private static void a(Consumer<je> consumer, String string, bnd<?> bnd, int integer) {
		ji.a(bmr.a(bkk.nl), bkk.nm, 0.35F, integer, bnd).a("has_beef", a(bkk.nl)).a(consumer, "cooked_beef_from_" + string);
		ji.a(bmr.a(bkk.nn), bkk.no, 0.35F, integer, bnd).a("has_chicken", a(bkk.nn)).a(consumer, "cooked_chicken_from_" + string);
		ji.a(bmr.a(bkk.ml), bkk.mp, 0.35F, integer, bnd).a("has_cod", a(bkk.ml)).a(consumer, "cooked_cod_from_" + string);
		ji.a(bmr.a(bvs.kc), bkk.ni, 0.1F, integer, bnd).a("has_kelp", a((bqa)bvs.kc)).a(consumer, "dried_kelp_from_" + string);
		ji.a(bmr.a(bkk.mm), bkk.mq, 0.35F, integer, bnd).a("has_salmon", a(bkk.mm)).a(consumer, "cooked_salmon_from_" + string);
		ji.a(bmr.a(bkk.pJ), bkk.pK, 0.35F, integer, bnd).a("has_mutton", a(bkk.pJ)).a(consumer, "cooked_mutton_from_" + string);
		ji.a(bmr.a(bkk.lx), bkk.ly, 0.35F, integer, bnd).a("has_porkchop", a(bkk.lx)).a(consumer, "cooked_porkchop_from_" + string);
		ji.a(bmr.a(bkk.oY), bkk.oZ, 0.35F, integer, bnd).a("has_potato", a(bkk.oY)).a(consumer, "baked_potato_from_" + string);
		ji.a(bmr.a(bkk.pw), bkk.px, 0.35F, integer, bnd).a("has_rabbit", a(bkk.pw)).a(consumer, "cooked_rabbit_from_" + string);
	}

	private static ba.a a(bvr bvr) {
		return new ba.a(be.b.a, bvr, ck.a);
	}

	private static bl.a a(bqa bqa) {
		return a(bo.a.a().a(bqa).b());
	}

	private static bl.a a(adf<bke> adf) {
		return a(bo.a.a().a(adf).b());
	}

	private static bl.a a(bo... arr) {
		return new bl.a(be.b.a, bx.d.e, bx.d.e, bx.d.e, arr);
	}

	@Override
	public String a() {
		return "Recipes";
	}
}
