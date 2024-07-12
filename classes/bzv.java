import com.google.common.collect.Maps;
import java.util.Map;

public class bzv extends bvr {
	private static final fz[] i = fz.values();
	public static final cga a = cfz.I;
	public static final cga b = cfz.J;
	public static final cga c = cfz.K;
	public static final cga d = cfz.L;
	public static final cga e = cfz.G;
	public static final cga f = cfz.H;
	public static final Map<fz, cga> g = v.a(Maps.newEnumMap(fz.class), enumMap -> {
		enumMap.put(fz.NORTH, a);
		enumMap.put(fz.EAST, b);
		enumMap.put(fz.SOUTH, c);
		enumMap.put(fz.WEST, d);
		enumMap.put(fz.UP, e);
		enumMap.put(fz.DOWN, f);
	});
	protected final dfg[] h;

	protected bzv(float float1, cfi.c c) {
		super(c);
		this.h = this.a(float1);
	}

	private dfg[] a(float float1) {
		float float3 = 0.5F - float1;
		float float4 = 0.5F + float1;
		dfg dfg5 = bvr.a(
			(double)(float3 * 16.0F), (double)(float3 * 16.0F), (double)(float3 * 16.0F), (double)(float4 * 16.0F), (double)(float4 * 16.0F), (double)(float4 * 16.0F)
		);
		dfg[] arr6 = new dfg[i.length];

		for (int integer7 = 0; integer7 < i.length; integer7++) {
			fz fz8 = i[integer7];
			arr6[integer7] = dfd.a(
				0.5 + Math.min((double)(-float1), (double)fz8.i() * 0.5),
				0.5 + Math.min((double)(-float1), (double)fz8.j() * 0.5),
				0.5 + Math.min((double)(-float1), (double)fz8.k() * 0.5),
				0.5 + Math.max((double)float1, (double)fz8.i() * 0.5),
				0.5 + Math.max((double)float1, (double)fz8.j() * 0.5),
				0.5 + Math.max((double)float1, (double)fz8.k() * 0.5)
			);
		}

		dfg[] arr7 = new dfg[64];

		for (int integer8 = 0; integer8 < 64; integer8++) {
			dfg dfg9 = dfg5;

			for (int integer10 = 0; integer10 < i.length; integer10++) {
				if ((integer8 & 1 << integer10) != 0) {
					dfg9 = dfd.a(dfg9, arr6[integer10]);
				}
			}

			arr7[integer8] = dfg9;
		}

		return arr7;
	}

	@Override
	public boolean b(cfj cfj, bpg bpg, fu fu) {
		return false;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return this.h[this.h(cfj)];
	}

	protected int h(cfj cfj) {
		int integer3 = 0;

		for (int integer4 = 0; integer4 < i.length; integer4++) {
			if ((Boolean)cfj.c((cgl)g.get(i[integer4]))) {
				integer3 |= 1 << integer4;
			}
		}

		return integer3;
	}
}
