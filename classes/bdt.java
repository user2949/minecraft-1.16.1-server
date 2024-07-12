import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class bdt {
	public static final Map<bds, Int2ObjectMap<bdt.f[]>> a = v.a(
		Maps.<bds, Int2ObjectMap<bdt.f[]>>newHashMap(),
		hashMap -> {
			hashMap.put(
				bds.f,
				a(
					ImmutableMap.of(
						1,
						new bdt.f[]{
							new bdt.b(bkk.kW, 20, 16, 2), new bdt.b(bkk.oY, 26, 16, 2), new bdt.b(bkk.oX, 22, 16, 2), new bdt.b(bkk.qe, 15, 16, 2), new bdt.h(bkk.kX, 1, 6, 16, 1)
						},
						2,
						new bdt.f[]{new bdt.b(bvs.cK, 6, 12, 10), new bdt.h(bkk.pm, 1, 4, 5), new bdt.h(bkk.ke, 1, 4, 16, 5)},
						3,
						new bdt.f[]{new bdt.h(bkk.ne, 3, 18, 10), new bdt.b(bvs.dK, 4, 12, 20)},
						4,
						new bdt.f[]{
							new bdt.h(bvs.cW, 1, 1, 12, 15),
							new bdt.i(aoi.p, 100, 15),
							new bdt.i(aoi.h, 160, 15),
							new bdt.i(aoi.r, 140, 15),
							new bdt.i(aoi.o, 120, 15),
							new bdt.i(aoi.s, 280, 15),
							new bdt.i(aoi.w, 7, 15)
						},
						5,
						new bdt.f[]{new bdt.h(bkk.pc, 3, 3, 30), new bdt.h(bkk.nE, 4, 3, 30)}
					)
				)
			);
			hashMap.put(
				bds.g,
				a(
					ImmutableMap.of(
						1,
						new bdt.f[]{new bdt.b(bkk.kM, 20, 16, 2), new bdt.b(bkk.kh, 10, 16, 2), new bdt.g(bkk.ml, 6, bkk.mp, 6, 16, 1), new bdt.h(bkk.lW, 3, 1, 16, 1)},
						2,
						new bdt.f[]{new bdt.b(bkk.ml, 15, 16, 10), new bdt.g(bkk.mm, 6, bkk.mq, 6, 16, 5), new bdt.h(bkk.rm, 2, 1, 5)},
						3,
						new bdt.f[]{new bdt.b(bkk.mm, 13, 16, 20), new bdt.e(bkk.mi, 3, 3, 10, 0.2F)},
						4,
						new bdt.f[]{new bdt.b(bkk.mn, 6, 12, 30)},
						5,
						new bdt.f[]{
							new bdt.b(bkk.mo, 4, 12, 30),
							new bdt.c(
								1,
								12,
								30,
								ImmutableMap.<bdu, bke>builder()
									.put(bdu.c, bkk.lR)
									.put(bdu.g, bkk.qo)
									.put(bdu.e, bkk.qo)
									.put(bdu.a, bkk.qq)
									.put(bdu.b, bkk.qq)
									.put(bdu.d, bkk.qr)
									.put(bdu.f, bkk.qs)
									.build()
							)
						}
					)
				)
			);
			hashMap.put(
				bds.m,
				a(
					ImmutableMap.of(
						1,
						new bdt.f[]{
							new bdt.b(bvs.aY, 18, 16, 2), new bdt.b(bvs.bk, 18, 16, 2), new bdt.b(bvs.bn, 18, 16, 2), new bdt.b(bvs.bf, 18, 16, 2), new bdt.h(bkk.ng, 2, 1, 1)
						},
						2,
						new bdt.f[]{
							new bdt.b(bkk.mK, 12, 16, 10),
							new bdt.b(bkk.mz, 12, 16, 10),
							new bdt.b(bkk.mJ, 12, 16, 10),
							new bdt.b(bkk.mD, 12, 16, 10),
							new bdt.b(bkk.mB, 12, 16, 10),
							new bdt.h(bvs.aY, 1, 1, 16, 5),
							new bdt.h(bvs.aZ, 1, 1, 16, 5),
							new bdt.h(bvs.ba, 1, 1, 16, 5),
							new bdt.h(bvs.bb, 1, 1, 16, 5),
							new bdt.h(bvs.bc, 1, 1, 16, 5),
							new bdt.h(bvs.bd, 1, 1, 16, 5),
							new bdt.h(bvs.be, 1, 1, 16, 5),
							new bdt.h(bvs.bf, 1, 1, 16, 5),
							new bdt.h(bvs.bg, 1, 1, 16, 5),
							new bdt.h(bvs.bh, 1, 1, 16, 5),
							new bdt.h(bvs.bi, 1, 1, 16, 5),
							new bdt.h(bvs.bj, 1, 1, 16, 5),
							new bdt.h(bvs.bk, 1, 1, 16, 5),
							new bdt.h(bvs.bl, 1, 1, 16, 5),
							new bdt.h(bvs.bm, 1, 1, 16, 5),
							new bdt.h(bvs.bn, 1, 1, 16, 5),
							new bdt.h(bvs.gB, 1, 4, 16, 5),
							new bdt.h(bvs.gC, 1, 4, 16, 5),
							new bdt.h(bvs.gD, 1, 4, 16, 5),
							new bdt.h(bvs.gE, 1, 4, 16, 5),
							new bdt.h(bvs.gF, 1, 4, 16, 5),
							new bdt.h(bvs.gG, 1, 4, 16, 5),
							new bdt.h(bvs.gH, 1, 4, 16, 5),
							new bdt.h(bvs.gI, 1, 4, 16, 5),
							new bdt.h(bvs.gJ, 1, 4, 16, 5),
							new bdt.h(bvs.gK, 1, 4, 16, 5),
							new bdt.h(bvs.gL, 1, 4, 16, 5),
							new bdt.h(bvs.gM, 1, 4, 16, 5),
							new bdt.h(bvs.gN, 1, 4, 16, 5),
							new bdt.h(bvs.gO, 1, 4, 16, 5),
							new bdt.h(bvs.gP, 1, 4, 16, 5),
							new bdt.h(bvs.gQ, 1, 4, 16, 5)
						},
						3,
						new bdt.f[]{
							new bdt.b(bkk.mC, 12, 16, 20),
							new bdt.b(bkk.my, 12, 16, 20),
							new bdt.b(bkk.mF, 12, 16, 20),
							new bdt.b(bkk.ms, 12, 16, 20),
							new bdt.b(bkk.mA, 12, 16, 20),
							new bdt.h(bvs.ax, 3, 1, 12, 10),
							new bdt.h(bvs.aB, 3, 1, 12, 10),
							new bdt.h(bvs.aL, 3, 1, 12, 10),
							new bdt.h(bvs.aM, 3, 1, 12, 10),
							new bdt.h(bvs.aI, 3, 1, 12, 10),
							new bdt.h(bvs.aJ, 3, 1, 12, 10),
							new bdt.h(bvs.aG, 3, 1, 12, 10),
							new bdt.h(bvs.aE, 3, 1, 12, 10),
							new bdt.h(bvs.aK, 3, 1, 12, 10),
							new bdt.h(bvs.aA, 3, 1, 12, 10),
							new bdt.h(bvs.aF, 3, 1, 12, 10),
							new bdt.h(bvs.aC, 3, 1, 12, 10),
							new bdt.h(bvs.az, 3, 1, 12, 10),
							new bdt.h(bvs.ay, 3, 1, 12, 10),
							new bdt.h(bvs.aD, 3, 1, 12, 10),
							new bdt.h(bvs.aH, 3, 1, 12, 10)
						},
						4,
						new bdt.f[]{
							new bdt.b(bkk.mI, 12, 16, 30),
							new bdt.b(bkk.mw, 12, 16, 30),
							new bdt.b(bkk.mH, 12, 16, 30),
							new bdt.b(bkk.mt, 12, 16, 30),
							new bdt.b(bkk.mE, 12, 16, 30),
							new bdt.b(bkk.mx, 12, 16, 30),
							new bdt.h(bkk.pL, 3, 1, 12, 15),
							new bdt.h(bkk.pW, 3, 1, 12, 15),
							new bdt.h(bkk.pO, 3, 1, 12, 15),
							new bdt.h(bkk.pZ, 3, 1, 12, 15),
							new bdt.h(bkk.pR, 3, 1, 12, 15),
							new bdt.h(bkk.pY, 3, 1, 12, 15),
							new bdt.h(bkk.pQ, 3, 1, 12, 15),
							new bdt.h(bkk.pS, 3, 1, 12, 15),
							new bdt.h(bkk.qa, 3, 1, 12, 15),
							new bdt.h(bkk.pV, 3, 1, 12, 15),
							new bdt.h(bkk.pN, 3, 1, 12, 15),
							new bdt.h(bkk.pU, 3, 1, 12, 15),
							new bdt.h(bkk.pX, 3, 1, 12, 15),
							new bdt.h(bkk.pP, 3, 1, 12, 15),
							new bdt.h(bkk.pM, 3, 1, 12, 15),
							new bdt.h(bkk.pT, 3, 1, 12, 15)
						},
						5,
						new bdt.f[]{new bdt.h(bkk.lz, 2, 3, 30)}
					)
				)
			);
			hashMap.put(
				bds.h,
				a(
					ImmutableMap.of(
						1,
						new bdt.f[]{new bdt.b(bkk.kB, 32, 16, 2), new bdt.h(bkk.kg, 1, 16, 1), new bdt.g(bvs.E, 10, bkk.lw, 10, 12, 1)},
						2,
						new bdt.f[]{new bdt.b(bkk.lw, 26, 12, 10), new bdt.h(bkk.kf, 2, 1, 5)},
						3,
						new bdt.f[]{new bdt.b(bkk.kM, 14, 16, 20), new bdt.h(bkk.qP, 3, 1, 10)},
						4,
						new bdt.f[]{new bdt.b(bkk.kN, 24, 16, 30), new bdt.e(bkk.kf, 2, 3, 15)},
						5,
						new bdt.f[]{new bdt.b(bkk.es, 8, 12, 30), new bdt.e(bkk.qP, 3, 3, 15), new bdt.j(bkk.kg, 5, bkk.qk, 5, 2, 12, 30)}
					)
				)
			);
			hashMap.put(
				bds.j,
				a(
					ImmutableMap.<Integer, bdt.f[]>builder()
						.put(1, new bdt.f[]{new bdt.b(bkk.mb, 24, 16, 2), new bdt.d(1), new bdt.h(bvs.bI, 9, 1, 12, 1)})
						.put(2, new bdt.f[]{new bdt.b(bkk.mc, 4, 12, 10), new bdt.d(5), new bdt.h(bkk.rj, 1, 1, 5)})
						.put(3, new bdt.f[]{new bdt.b(bkk.mr, 5, 12, 20), new bdt.d(10), new bdt.h(bkk.az, 1, 4, 10)})
						.put(4, new bdt.f[]{new bdt.b(bkk.oS, 2, 12, 30), new bdt.d(15), new bdt.h(bkk.mj, 5, 1, 15), new bdt.h(bkk.mh, 4, 1, 15)})
						.put(5, new bdt.f[]{new bdt.h(bkk.pH, 20, 1, 30)})
						.build()
				)
			);
			hashMap.put(
				bds.d,
				a(
					ImmutableMap.of(
						1,
						new bdt.f[]{new bdt.b(bkk.mb, 24, 16, 2), new bdt.h(bkk.pb, 7, 1, 1)},
						2,
						new bdt.f[]{new bdt.b(bkk.dP, 11, 16, 10), new bdt.k(13, cml.l, czs.a.MONUMENT, 12, 5)},
						3,
						new bdt.f[]{new bdt.b(bkk.mh, 1, 12, 20), new bdt.k(14, cml.d, czs.a.MANSION, 12, 10)},
						4,
						new bdt.f[]{
							new bdt.h(bkk.oV, 7, 1, 15),
							new bdt.h(bkk.pL, 3, 1, 15),
							new bdt.h(bkk.pW, 3, 1, 15),
							new bdt.h(bkk.pO, 3, 1, 15),
							new bdt.h(bkk.pZ, 3, 1, 15),
							new bdt.h(bkk.pR, 3, 1, 15),
							new bdt.h(bkk.pY, 3, 1, 15),
							new bdt.h(bkk.pQ, 3, 1, 15),
							new bdt.h(bkk.pS, 3, 1, 15),
							new bdt.h(bkk.qa, 3, 1, 15),
							new bdt.h(bkk.pV, 3, 1, 15),
							new bdt.h(bkk.pN, 3, 1, 15),
							new bdt.h(bkk.pU, 3, 1, 15),
							new bdt.h(bkk.pX, 3, 1, 15),
							new bdt.h(bkk.pP, 3, 1, 15),
							new bdt.h(bkk.pM, 3, 1, 15),
							new bdt.h(bkk.pT, 3, 1, 15)
						},
						5,
						new bdt.f[]{new bdt.h(bkk.qW, 8, 1, 30)}
					)
				)
			);
			hashMap.put(
				bds.e,
				a(
					ImmutableMap.of(
						1,
						new bdt.f[]{new bdt.b(bkk.np, 32, 16, 2), new bdt.h(bkk.lP, 1, 2, 1)},
						2,
						new bdt.f[]{new bdt.b(bkk.kl, 3, 12, 10), new bdt.h(bkk.mv, 1, 1, 5)},
						3,
						new bdt.f[]{new bdt.b(bkk.pz, 2, 12, 20), new bdt.h(bvs.cS, 4, 1, 12, 10)},
						4,
						new bdt.f[]{new bdt.b(bkk.jZ, 4, 12, 30), new bdt.b(bkk.nw, 9, 12, 30), new bdt.h(bkk.nq, 5, 1, 15)},
						5,
						new bdt.f[]{new bdt.b(bkk.nu, 22, 12, 30), new bdt.h(bkk.oQ, 3, 1, 30)}
					)
				)
			);
			hashMap.put(
				bds.b,
				a(
					ImmutableMap.of(
						1,
						new bdt.f[]{
							new bdt.b(bkk.kh, 15, 16, 2),
							new bdt.h(new bki(bkk.li), 7, 1, 12, 1, 0.2F),
							new bdt.h(new bki(bkk.lj), 4, 1, 12, 1, 0.2F),
							new bdt.h(new bki(bkk.lg), 5, 1, 12, 1, 0.2F),
							new bdt.h(new bki(bkk.lh), 9, 1, 12, 1, 0.2F)
						},
						2,
						new bdt.f[]{
							new bdt.b(bkk.kk, 4, 12, 10),
							new bdt.h(new bki(bkk.ri), 36, 1, 12, 5, 0.2F),
							new bdt.h(new bki(bkk.lf), 1, 1, 12, 5, 0.2F),
							new bdt.h(new bki(bkk.le), 3, 1, 12, 5, 0.2F)
						},
						3,
						new bdt.f[]{
							new bdt.b(bkk.lM, 1, 12, 20),
							new bdt.b(bkk.kj, 1, 12, 20),
							new bdt.h(new bki(bkk.lc), 1, 1, 12, 10, 0.2F),
							new bdt.h(new bki(bkk.ld), 4, 1, 12, 10, 0.2F),
							new bdt.h(new bki(bkk.qm), 5, 1, 12, 10, 0.2F)
						},
						4,
						new bdt.f[]{new bdt.e(bkk.lm, 14, 3, 15, 0.2F), new bdt.e(bkk.ln, 8, 3, 15, 0.2F)},
						5,
						new bdt.f[]{new bdt.e(bkk.lk, 8, 3, 30, 0.2F), new bdt.e(bkk.ll, 16, 3, 30, 0.2F)}
					)
				)
			);
			hashMap.put(
				bds.o,
				a(
					ImmutableMap.of(
						1,
						new bdt.f[]{new bdt.b(bkk.kh, 15, 16, 2), new bdt.h(new bki(bkk.kc), 3, 1, 12, 1, 0.2F), new bdt.e(bkk.ko, 2, 3, 1)},
						2,
						new bdt.f[]{new bdt.b(bkk.kk, 4, 12, 10), new bdt.h(new bki(bkk.ri), 36, 1, 12, 5, 0.2F)},
						3,
						new bdt.f[]{new bdt.b(bkk.lw, 24, 12, 20)},
						4,
						new bdt.f[]{new bdt.b(bkk.kj, 1, 12, 30), new bdt.e(bkk.kA, 12, 3, 15, 0.2F)},
						5,
						new bdt.f[]{new bdt.e(bkk.kx, 8, 3, 30, 0.2F)}
					)
				)
			);
			hashMap.put(
				bds.n,
				a(
					ImmutableMap.of(
						1,
						new bdt.f[]{
							new bdt.b(bkk.kh, 15, 16, 2),
							new bdt.h(new bki(bkk.kw), 1, 1, 12, 1, 0.2F),
							new bdt.h(new bki(bkk.ku), 1, 1, 12, 1, 0.2F),
							new bdt.h(new bki(bkk.kv), 1, 1, 12, 1, 0.2F),
							new bdt.h(new bki(bkk.kQ), 1, 1, 12, 1, 0.2F)
						},
						2,
						new bdt.f[]{new bdt.b(bkk.kk, 4, 12, 10), new bdt.h(new bki(bkk.ri), 36, 1, 12, 5, 0.2F)},
						3,
						new bdt.f[]{
							new bdt.b(bkk.lw, 30, 12, 20),
							new bdt.e(bkk.kc, 1, 3, 10, 0.2F),
							new bdt.e(bkk.ka, 2, 3, 10, 0.2F),
							new bdt.e(bkk.kb, 3, 3, 10, 0.2F),
							new bdt.h(new bki(bkk.kS), 4, 1, 3, 10, 0.2F)
						},
						4,
						new bdt.f[]{new bdt.b(bkk.kj, 1, 12, 30), new bdt.e(bkk.kA, 12, 3, 15, 0.2F), new bdt.e(bkk.ky, 5, 3, 15, 0.2F)},
						5,
						new bdt.f[]{new bdt.e(bkk.kz, 13, 3, 30, 0.2F)}
					)
				)
			);
			hashMap.put(
				bds.c,
				a(
					ImmutableMap.of(
						1,
						new bdt.f[]{new bdt.b(bkk.nn, 14, 16, 2), new bdt.b(bkk.lx, 7, 16, 2), new bdt.b(bkk.pw, 4, 16, 2), new bdt.h(bkk.py, 1, 1, 1)},
						2,
						new bdt.f[]{new bdt.b(bkk.kh, 15, 16, 2), new bdt.h(bkk.ly, 1, 5, 16, 5), new bdt.h(bkk.no, 1, 8, 16, 5)},
						3,
						new bdt.f[]{new bdt.b(bkk.pJ, 7, 16, 20), new bdt.b(bkk.nl, 10, 16, 20)},
						4,
						new bdt.f[]{new bdt.b(bkk.ma, 10, 12, 30)},
						5,
						new bdt.f[]{new bdt.b(bkk.rl, 10, 12, 30)}
					)
				)
			);
			hashMap.put(
				bds.i,
				a(
					ImmutableMap.of(
						1,
						new bdt.f[]{new bdt.b(bkk.lS, 6, 16, 2), new bdt.a(bkk.la, 3), new bdt.a(bkk.kZ, 7)},
						2,
						new bdt.f[]{new bdt.b(bkk.lw, 26, 12, 10), new bdt.a(bkk.kY, 5, 12, 5), new bdt.a(bkk.lb, 4, 12, 5)},
						3,
						new bdt.f[]{new bdt.b(bkk.pA, 9, 12, 20), new bdt.a(bkk.kZ, 7)},
						4,
						new bdt.f[]{new bdt.b(bkk.jZ, 4, 12, 30), new bdt.a(bkk.pF, 6, 12, 15)},
						5,
						new bdt.f[]{new bdt.h(new bki(bkk.lO), 6, 1, 12, 30, 0.2F), new bdt.a(bkk.kY, 5, 12, 30)}
					)
				)
			);
			hashMap.put(
				bds.k,
				a(
					ImmutableMap.of(
						1,
						new bdt.f[]{new bdt.b(bkk.lZ, 10, 16, 2), new bdt.h(bkk.lY, 1, 10, 16, 1)},
						2,
						new bdt.f[]{new bdt.b(bvs.b, 20, 16, 10), new bdt.h(bvs.dx, 1, 4, 16, 5)},
						3,
						new bdt.f[]{
							new bdt.b(bvs.c, 16, 16, 20),
							new bdt.b(bvs.g, 16, 16, 20),
							new bdt.b(bvs.e, 16, 16, 20),
							new bdt.h(bvs.h, 1, 4, 16, 10),
							new bdt.h(bvs.f, 1, 4, 16, 10),
							new bdt.h(bvs.d, 1, 4, 16, 10)
						},
						4,
						new bdt.f[]{
							new bdt.b(bkk.pr, 12, 12, 30),
							new bdt.h(bvs.fG, 1, 1, 12, 15),
							new bdt.h(bvs.fF, 1, 1, 12, 15),
							new bdt.h(bvs.fQ, 1, 1, 12, 15),
							new bdt.h(bvs.fI, 1, 1, 12, 15),
							new bdt.h(bvs.fM, 1, 1, 12, 15),
							new bdt.h(bvs.fN, 1, 1, 12, 15),
							new bdt.h(bvs.fU, 1, 1, 12, 15),
							new bdt.h(bvs.fT, 1, 1, 12, 15),
							new bdt.h(bvs.fL, 1, 1, 12, 15),
							new bdt.h(bvs.fH, 1, 1, 12, 15),
							new bdt.h(bvs.fK, 1, 1, 12, 15),
							new bdt.h(bvs.fS, 1, 1, 12, 15),
							new bdt.h(bvs.fO, 1, 1, 12, 15),
							new bdt.h(bvs.fP, 1, 1, 12, 15),
							new bdt.h(bvs.fJ, 1, 1, 12, 15),
							new bdt.h(bvs.fR, 1, 1, 12, 15),
							new bdt.h(bvs.jh, 1, 1, 12, 15),
							new bdt.h(bvs.jg, 1, 1, 12, 15),
							new bdt.h(bvs.jr, 1, 1, 12, 15),
							new bdt.h(bvs.jj, 1, 1, 12, 15),
							new bdt.h(bvs.jn, 1, 1, 12, 15),
							new bdt.h(bvs.jo, 1, 1, 12, 15),
							new bdt.h(bvs.jv, 1, 1, 12, 15),
							new bdt.h(bvs.ju, 1, 1, 12, 15),
							new bdt.h(bvs.jm, 1, 1, 12, 15),
							new bdt.h(bvs.ji, 1, 1, 12, 15),
							new bdt.h(bvs.jl, 1, 1, 12, 15),
							new bdt.h(bvs.jt, 1, 1, 12, 15),
							new bdt.h(bvs.jp, 1, 1, 12, 15),
							new bdt.h(bvs.jq, 1, 1, 12, 15),
							new bdt.h(bvs.jk, 1, 1, 12, 15),
							new bdt.h(bvs.js, 1, 1, 12, 15)
						},
						5,
						new bdt.f[]{new bdt.h(bvs.fB, 1, 1, 12, 30), new bdt.h(bvs.fz, 1, 1, 12, 30)}
					)
				)
			);
		}
	);
	public static final Int2ObjectMap<bdt.f[]> b = a(
		ImmutableMap.of(
			1,
			new bdt.f[]{
				new bdt.h(bkk.aP, 2, 1, 5, 1),
				new bdt.h(bkk.md, 4, 1, 5, 1),
				new bdt.h(bkk.dq, 2, 1, 5, 1),
				new bdt.h(bkk.qN, 5, 1, 5, 1),
				new bdt.h(bkk.aM, 1, 1, 12, 1),
				new bdt.h(bkk.bD, 1, 1, 8, 1),
				new bdt.h(bkk.di, 1, 1, 4, 1),
				new bdt.h(bkk.bE, 3, 1, 12, 1),
				new bdt.h(bkk.cX, 3, 1, 8, 1),
				new bdt.h(bkk.bh, 1, 1, 12, 1),
				new bdt.h(bkk.bi, 1, 1, 12, 1),
				new bdt.h(bkk.bj, 1, 1, 8, 1),
				new bdt.h(bkk.bk, 1, 1, 12, 1),
				new bdt.h(bkk.bl, 1, 1, 12, 1),
				new bdt.h(bkk.bm, 1, 1, 12, 1),
				new bdt.h(bkk.bn, 1, 1, 12, 1),
				new bdt.h(bkk.bo, 1, 1, 12, 1),
				new bdt.h(bkk.bp, 1, 1, 12, 1),
				new bdt.h(bkk.bq, 1, 1, 12, 1),
				new bdt.h(bkk.br, 1, 1, 12, 1),
				new bdt.h(bkk.bs, 1, 1, 7, 1),
				new bdt.h(bkk.kV, 1, 1, 12, 1),
				new bdt.h(bkk.qf, 1, 1, 12, 1),
				new bdt.h(bkk.nj, 1, 1, 12, 1),
				new bdt.h(bkk.nk, 1, 1, 12, 1),
				new bdt.h(bkk.B, 5, 1, 8, 1),
				new bdt.h(bkk.z, 5, 1, 8, 1),
				new bdt.h(bkk.C, 5, 1, 8, 1),
				new bdt.h(bkk.A, 5, 1, 8, 1),
				new bdt.h(bkk.x, 5, 1, 8, 1),
				new bdt.h(bkk.y, 5, 1, 8, 1),
				new bdt.h(bkk.ms, 1, 3, 12, 1),
				new bdt.h(bkk.mK, 1, 3, 12, 1),
				new bdt.h(bkk.mH, 1, 3, 12, 1),
				new bdt.h(bkk.mA, 1, 3, 12, 1),
				new bdt.h(bkk.mJ, 1, 3, 12, 1),
				new bdt.h(bkk.mt, 1, 3, 12, 1),
				new bdt.h(bkk.my, 1, 3, 12, 1),
				new bdt.h(bkk.mE, 1, 3, 12, 1),
				new bdt.h(bkk.mC, 1, 3, 12, 1),
				new bdt.h(bkk.mz, 1, 3, 12, 1),
				new bdt.h(bkk.mw, 1, 3, 12, 1),
				new bdt.h(bkk.mD, 1, 3, 12, 1),
				new bdt.h(bkk.mB, 1, 3, 12, 1),
				new bdt.h(bkk.mF, 1, 3, 12, 1),
				new bdt.h(bkk.mI, 1, 3, 12, 1),
				new bdt.h(bkk.mx, 1, 3, 12, 1),
				new bdt.h(bkk.iJ, 3, 1, 8, 1),
				new bdt.h(bkk.iK, 3, 1, 8, 1),
				new bdt.h(bkk.iL, 3, 1, 8, 1),
				new bdt.h(bkk.iM, 3, 1, 8, 1),
				new bdt.h(bkk.iI, 3, 1, 8, 1),
				new bdt.h(bkk.dR, 1, 1, 12, 1),
				new bdt.h(bkk.bu, 1, 1, 12, 1),
				new bdt.h(bkk.bv, 1, 1, 12, 1),
				new bdt.h(bkk.ed, 1, 2, 5, 1),
				new bdt.h(bkk.E, 1, 8, 8, 1),
				new bdt.h(bkk.F, 1, 4, 6, 1)
			},
			2,
			new bdt.f[]{
				new bdt.h(bkk.lX, 5, 1, 4, 1),
				new bdt.h(bkk.lU, 5, 1, 4, 1),
				new bdt.h(bkk.ge, 3, 1, 6, 1),
				new bdt.h(bkk.jh, 6, 1, 6, 1),
				new bdt.h(bkk.kO, 1, 1, 8, 1),
				new bdt.h(bkk.l, 3, 3, 6, 1)
			}
		)
	);

	private static Int2ObjectMap<bdt.f[]> a(ImmutableMap<Integer, bdt.f[]> immutableMap) {
		return new Int2ObjectOpenHashMap<>(immutableMap);
	}

	static class a implements bdt.f {
		private final bke a;
		private final int b;
		private final int c;
		private final int d;

		public a(bke bke, int integer) {
			this(bke, integer, 12, 1);
		}

		public a(bke bke, int integer2, int integer3, int integer4) {
			this.a = bke;
			this.b = integer2;
			this.c = integer3;
			this.d = integer4;
		}

		@Override
		public boz a(aom aom, Random random) {
			bki bki4 = new bki(bkk.oU, this.b);
			bki bki5 = new bki(this.a);
			if (this.a instanceof bjg) {
				List<bjf> list6 = Lists.<bjf>newArrayList();
				list6.add(a(random));
				if (random.nextFloat() > 0.7F) {
					list6.add(a(random));
				}

				if (random.nextFloat() > 0.8F) {
					list6.add(a(random));
				}

				bki5 = bji.a(bki5, list6);
			}

			return new boz(bki4, bki5, this.c, this.d, 0.2F);
		}

		private static bjf a(Random random) {
			return bjf.a(bje.a(random.nextInt(16)));
		}
	}

	static class b implements bdt.f {
		private final bke a;
		private final int b;
		private final int c;
		private final int d;
		private final float e;

		public b(bqa bqa, int integer2, int integer3, int integer4) {
			this.a = bqa.h();
			this.b = integer2;
			this.c = integer3;
			this.d = integer4;
			this.e = 0.05F;
		}

		@Override
		public boz a(aom aom, Random random) {
			bki bki4 = new bki(this.a, this.b);
			return new boz(bki4, new bki(bkk.oU), this.c, this.d, this.e);
		}
	}

	static class c implements bdt.f {
		private final Map<bdu, bke> a;
		private final int b;
		private final int c;
		private final int d;

		public c(int integer1, int integer2, int integer3, Map<bdu, bke> map) {
			gl.aR.e().filter(bdu -> !map.containsKey(bdu)).findAny().ifPresent(bdu -> {
				throw new IllegalStateException("Missing trade for villager type: " + gl.aR.b(bdu));
			});
			this.a = map;
			this.b = integer1;
			this.c = integer2;
			this.d = integer3;
		}

		@Nullable
		@Override
		public boz a(aom aom, Random random) {
			if (aom instanceof bdr) {
				bki bki4 = new bki((bqa)this.a.get(((bdr)aom).eY().a()), this.b);
				return new boz(bki4, new bki(bkk.oU), this.c, this.d, 0.05F);
			} else {
				return null;
			}
		}
	}

	static class d implements bdt.f {
		private final int a;

		public d(int integer) {
			this.a = integer;
		}

		@Override
		public boz a(aom aom, Random random) {
			List<bnw> list4 = (List<bnw>)gl.ak.e().filter(bnw::h).collect(Collectors.toList());
			bnw bnw5 = (bnw)list4.get(random.nextInt(list4.size()));
			int integer6 = aec.a(random, bnw5.e(), bnw5.a());
			bki bki7 = bjm.a(new bnz(bnw5, integer6));
			int integer8 = 2 + random.nextInt(5 + integer6 * 10) + 3 * integer6;
			if (bnw5.b()) {
				integer8 *= 2;
			}

			if (integer8 > 64) {
				integer8 = 64;
			}

			return new boz(new bki(bkk.oU, integer8), new bki(bkk.mc), bki7, 12, this.a, 0.2F);
		}
	}

	static class e implements bdt.f {
		private final bki a;
		private final int b;
		private final int c;
		private final int d;
		private final float e;

		public e(bke bke, int integer2, int integer3, int integer4) {
			this(bke, integer2, integer3, integer4, 0.05F);
		}

		public e(bke bke, int integer2, int integer3, int integer4, float float5) {
			this.a = new bki(bke);
			this.b = integer2;
			this.c = integer3;
			this.d = integer4;
			this.e = float5;
		}

		@Override
		public boz a(aom aom, Random random) {
			int integer4 = 5 + random.nextInt(15);
			bki bki5 = bny.a(random, new bki(this.a.b()), integer4, false);
			int integer6 = Math.min(this.b + integer4, 64);
			bki bki7 = new bki(bkk.oU, integer6);
			return new boz(bki7, bki5, this.c, this.d, this.e);
		}
	}

	public interface f {
		@Nullable
		boz a(aom aom, Random random);
	}

	static class g implements bdt.f {
		private final bki a;
		private final int b;
		private final int c;
		private final bki d;
		private final int e;
		private final int f;
		private final int g;
		private final float h;

		public g(bqa bqa, int integer2, bke bke, int integer4, int integer5, int integer6) {
			this(bqa, integer2, 1, bke, integer4, integer5, integer6);
		}

		public g(bqa bqa, int integer2, int integer3, bke bke, int integer5, int integer6, int integer7) {
			this.a = new bki(bqa);
			this.b = integer2;
			this.c = integer3;
			this.d = new bki(bke);
			this.e = integer5;
			this.f = integer6;
			this.g = integer7;
			this.h = 0.05F;
		}

		@Nullable
		@Override
		public boz a(aom aom, Random random) {
			return new boz(new bki(bkk.oU, this.c), new bki(this.a.b(), this.b), new bki(this.d.b(), this.e), this.f, this.g, this.h);
		}
	}

	static class h implements bdt.f {
		private final bki a;
		private final int b;
		private final int c;
		private final int d;
		private final int e;
		private final float f;

		public h(bvr bvr, int integer2, int integer3, int integer4, int integer5) {
			this(new bki(bvr), integer2, integer3, integer4, integer5);
		}

		public h(bke bke, int integer2, int integer3, int integer4) {
			this(new bki(bke), integer2, integer3, 12, integer4);
		}

		public h(bke bke, int integer2, int integer3, int integer4, int integer5) {
			this(new bki(bke), integer2, integer3, integer4, integer5);
		}

		public h(bki bki, int integer2, int integer3, int integer4, int integer5) {
			this(bki, integer2, integer3, integer4, integer5, 0.05F);
		}

		public h(bki bki, int integer2, int integer3, int integer4, int integer5, float float6) {
			this.a = bki;
			this.b = integer2;
			this.c = integer3;
			this.d = integer4;
			this.e = integer5;
			this.f = float6;
		}

		@Override
		public boz a(aom aom, Random random) {
			return new boz(new bki(bkk.oU, this.b), new bki(this.a.b(), this.c), this.d, this.e, this.f);
		}
	}

	static class i implements bdt.f {
		final aoe a;
		final int b;
		final int c;
		private final float d;

		public i(aoe aoe, int integer2, int integer3) {
			this.a = aoe;
			this.b = integer2;
			this.c = integer3;
			this.d = 0.05F;
		}

		@Nullable
		@Override
		public boz a(aom aom, Random random) {
			bki bki4 = new bki(bkk.qQ, 1);
			bll.a(bki4, this.a, this.b);
			return new boz(new bki(bkk.oU, 1), bki4, 12, this.c, this.d);
		}
	}

	static class j implements bdt.f {
		private final bki a;
		private final int b;
		private final int c;
		private final int d;
		private final int e;
		private final bke f;
		private final int g;
		private final float h;

		public j(bke bke1, int integer2, bke bke3, int integer4, int integer5, int integer6, int integer7) {
			this.a = new bki(bke3);
			this.c = integer5;
			this.d = integer6;
			this.e = integer7;
			this.f = bke1;
			this.g = integer2;
			this.b = integer4;
			this.h = 0.05F;
		}

		@Override
		public boz a(aom aom, Random random) {
			bki bki4 = new bki(bkk.oU, this.c);
			List<bmb> list5 = (List<bmb>)gl.an.e().filter(bmb -> !bmb.a().isEmpty() && bmc.a(bmb)).collect(Collectors.toList());
			bmb bmb6 = (bmb)list5.get(random.nextInt(list5.size()));
			bki bki7 = bmd.a(new bki(this.a.b(), this.b), bmb6);
			return new boz(bki4, new bki(this.f, this.g), bki7, this.d, this.e, this.h);
		}
	}

	static class k implements bdt.f {
		private final int a;
		private final cml<?> b;
		private final czs.a c;
		private final int d;
		private final int e;

		public k(int integer1, cml<?> cml, czs.a a, int integer4, int integer5) {
			this.a = integer1;
			this.b = cml;
			this.c = a;
			this.d = integer4;
			this.e = integer5;
		}

		@Nullable
		@Override
		public boz a(aom aom, Random random) {
			if (!(aom.l instanceof zd)) {
				return null;
			} else {
				zd zd4 = (zd)aom.l;
				fu fu5 = zd4.a(this.b, aom.cA(), 100, true);
				if (fu5 != null) {
					bki bki6 = bko.a(zd4, fu5.u(), fu5.w(), (byte)2, true, true);
					bko.a(zd4, bki6);
					czv.a(bki6, fu5, "+", this.c);
					bki6.a(new ne("filled_map." + this.b.i().toLowerCase(Locale.ROOT)));
					return new boz(new bki(bkk.oU, this.a), new bki(bkk.mh), bki6, this.d, this.e, 0.2F);
				} else {
					return null;
				}
			}
		}
	}
}
