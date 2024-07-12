import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;

public class bww extends bvr implements cax {
	public static final cga a = bzv.a;
	public static final cga b = bzv.b;
	public static final cga c = bzv.c;
	public static final cga d = bzv.d;
	public static final cga e = cfz.C;
	protected static final Map<fz, cga> f = (Map<fz, cga>)bzv.g.entrySet().stream().filter(entry -> ((fz)entry.getKey()).n().d()).collect(v.a());
	protected final dfg[] g;
	protected final dfg[] h;
	private final Object2IntMap<cfj> i = new Object2IntOpenHashMap<>();

	protected bww(float float1, float float2, float float3, float float4, float float5, cfi.c c) {
		super(c);
		this.g = this.a(float1, float2, float5, 0.0F, float5);
		this.h = this.a(float1, float2, float3, 0.0F, float4);

		for (cfj cfj9 : this.n.a()) {
			this.g(cfj9);
		}
	}

	protected dfg[] a(float float1, float float2, float float3, float float4, float float5) {
		float float7 = 8.0F - float1;
		float float8 = 8.0F + float1;
		float float9 = 8.0F - float2;
		float float10 = 8.0F + float2;
		dfg dfg11 = bvr.a((double)float7, 0.0, (double)float7, (double)float8, (double)float3, (double)float8);
		dfg dfg12 = bvr.a((double)float9, (double)float4, 0.0, (double)float10, (double)float5, (double)float10);
		dfg dfg13 = bvr.a((double)float9, (double)float4, (double)float9, (double)float10, (double)float5, 16.0);
		dfg dfg14 = bvr.a(0.0, (double)float4, (double)float9, (double)float10, (double)float5, (double)float10);
		dfg dfg15 = bvr.a((double)float9, (double)float4, (double)float9, 16.0, (double)float5, (double)float10);
		dfg dfg16 = dfd.a(dfg12, dfg15);
		dfg dfg17 = dfd.a(dfg13, dfg14);
		dfg[] arr18 = new dfg[]{
			dfd.a(),
			dfg13,
			dfg14,
			dfg17,
			dfg12,
			dfd.a(dfg13, dfg12),
			dfd.a(dfg14, dfg12),
			dfd.a(dfg17, dfg12),
			dfg15,
			dfd.a(dfg13, dfg15),
			dfd.a(dfg14, dfg15),
			dfd.a(dfg17, dfg15),
			dfg16,
			dfd.a(dfg13, dfg16),
			dfd.a(dfg14, dfg16),
			dfd.a(dfg17, dfg16)
		};

		for (int integer19 = 0; integer19 < 16; integer19++) {
			arr18[integer19] = dfd.a(dfg11, arr18[integer19]);
		}

		return arr18;
	}

	@Override
	public boolean b(cfj cfj, bpg bpg, fu fu) {
		return !(Boolean)cfj.c(e);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return this.h[this.g(cfj)];
	}

	@Override
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		return this.g[this.g(cfj)];
	}

	private static int a(fz fz) {
		return 1 << fz.d();
	}

	protected int g(cfj cfj) {
		return this.i.computeIntIfAbsent(cfj, cfjx -> {
			int integer2 = 0;
			if ((Boolean)cfjx.c(a)) {
				integer2 |= a(fz.NORTH);
			}

			if ((Boolean)cfjx.c(b)) {
				integer2 |= a(fz.EAST);
			}

			if ((Boolean)cfjx.c(c)) {
				integer2 |= a(fz.SOUTH);
			}

			if ((Boolean)cfjx.c(d)) {
				integer2 |= a(fz.WEST);
			}

			return integer2;
		});
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(e) ? cxb.c.a(false) : super.d(cfj);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		switch (cap) {
			case CLOCKWISE_180:
				return cfj.a(a, cfj.c(c)).a(b, cfj.c(d)).a(c, cfj.c(a)).a(d, cfj.c(b));
			case COUNTERCLOCKWISE_90:
				return cfj.a(a, cfj.c(b)).a(b, cfj.c(c)).a(c, cfj.c(d)).a(d, cfj.c(a));
			case CLOCKWISE_90:
				return cfj.a(a, cfj.c(d)).a(b, cfj.c(a)).a(c, cfj.c(b)).a(d, cfj.c(c));
			default:
				return cfj;
		}
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		switch (bzj) {
			case LEFT_RIGHT:
				return cfj.a(a, cfj.c(c)).a(c, cfj.c(a));
			case FRONT_BACK:
				return cfj.a(b, cfj.c(d)).a(d, cfj.c(b));
			default:
				return super.a(cfj, bzj);
		}
	}
}
