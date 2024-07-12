import java.nio.file.Path;
import java.util.function.Function;

public class ju extends jv<bke> {
	private final Function<adf.e<bvr>, adf.a> d;

	public ju(hk hk, jr jr) {
		super(hk, gl.am);
		this.d = jr::b;
	}

	@Override
	protected void b() {
		this.a(acx.a, ada.a);
		this.a(acx.b, ada.b);
		this.a(acx.c, ada.c);
		this.a(acx.d, ada.d);
		this.a(acx.e, ada.e);
		this.a(acx.f, ada.f);
		this.a(acx.g, ada.g);
		this.a(acx.h, ada.h);
		this.a(acx.i, ada.i);
		this.a(acx.j, ada.j);
		this.a(acx.l, ada.k);
		this.a(acx.o, ada.m);
		this.a(acx.p, ada.n);
		this.a(acx.t, ada.r);
		this.a(acx.s, ada.q);
		this.a(acx.u, ada.s);
		this.a(acx.v, ada.t);
		this.a(acx.x, ada.v);
		this.a(acx.w, ada.u);
		this.a(acx.y, ada.w);
		this.a(acx.z, ada.x);
		this.a(acx.q, ada.o);
		this.a(acx.r, ada.p);
		this.a(acx.B, ada.z);
		this.a(acx.D, ada.B);
		this.a(acx.E, ada.C);
		this.a(acx.C, ada.A);
		this.a(acx.F, ada.D);
		this.a(acx.G, ada.E);
		this.a(acx.H, ada.F);
		this.a(acx.n, ada.l);
		this.a(acx.I, ada.G);
		this.a(acx.J, ada.H);
		this.a(acx.K, ada.I);
		this.a(acx.L, ada.J);
		this.a(acx.M, ada.K);
		this.a(acx.N, ada.L);
		this.a(acx.P, ada.O);
		this.a(acx.av, ada.Q);
		this.a(ada.y).a(bkk.pL, bkk.pM, bkk.pN, bkk.pO, bkk.pP, bkk.pQ, bkk.pR, bkk.pS, bkk.pT, bkk.pU, bkk.pV, bkk.pW, bkk.pX, bkk.pY, bkk.pZ, bkk.qa);
		this.a(ada.R).a(bkk.lR, bkk.qo, bkk.qp, bkk.qq, bkk.qr, bkk.qs);
		this.a(ada.S).a(bkk.ml, bkk.mp, bkk.mm, bkk.mq, bkk.mo, bkk.mn);
		this.a(acx.ac, ada.T);
		this.a(ada.V).a(bkk.qy, bkk.qz, bkk.qA, bkk.qB, bkk.qC, bkk.qD, bkk.qE, bkk.qF, bkk.qG, bkk.qH, bkk.qI, bkk.qJ);
		this.a(ada.U).a(ada.V).a(bkk.qK);
		this.a(ada.W).a(bkk.kh, bkk.ki);
		this.a(ada.X).a(bkk.kg, bkk.qk, bkk.qj);
		this.a(ada.Y).a(bkk.oT, bkk.oS);
		this.a(ada.Z).a(bkk.km, bkk.oU, bkk.kj, bkk.kl, bkk.kk);
		this.a(ada.M).a(bkk.dp).a(bkk.rk).a(bkk.rn);
		this.a(ada.N)
			.a(ada.O)
			.a(
				bkk.bG,
				bkk.rD,
				bkk.fg,
				bkk.kl,
				bkk.ri,
				bkk.mj,
				bkk.pc,
				bkk.nE,
				bkk.lA,
				bkk.lB,
				bkk.lo,
				bkk.lp,
				bkk.lq,
				bkk.lr,
				bkk.pD,
				bkk.kE,
				bkk.kG,
				bkk.kF,
				bkk.kH,
				bkk.kT
			);
		this.a(ada.P)
			.a(
				bkk.S,
				bkk.aa,
				bkk.aq,
				bkk.ai,
				bkk.R,
				bkk.Z,
				bkk.ap,
				bkk.ah,
				bkk.v,
				bkk.w,
				bkk.bO,
				bkk.bP,
				bkk.cP,
				bkk.cQ,
				bkk.dg,
				bkk.dh,
				bkk.dy,
				bkk.dz,
				bkk.dY,
				bkk.dZ,
				bkk.ex,
				bkk.ey,
				bkk.eZ,
				bkk.fa,
				bkk.jS,
				bkk.jT,
				bkk.lI,
				bkk.lJ
			);
		this.a(ada.aa).a(bkk.o, bkk.rA);
		this.a(ada.ab).a(bkk.o, bkk.rA);
	}

	protected void a(adf.e<bvr> e1, adf.e<bke> e2) {
		adf.a a4 = this.b(e2);
		adf.a a5 = (adf.a)this.d.apply(e1);
		a5.b().forEach(a4::a);
	}

	@Override
	protected Path a(uh uh) {
		return this.b.b().resolve("data/" + uh.b() + "/tags/items/" + uh.a() + ".json");
	}

	@Override
	public String a() {
		return "Item Tags";
	}
}
