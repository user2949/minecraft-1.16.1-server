import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class cil extends cha {
	public static final cil d = new cil();
	public static final Codec<cil> e = MapCodec.of(Encoder.empty(), Decoder.unit((Supplier<cil>)(() -> d))).stable().codec();
	private static final List<cfj> h = (List<cfj>)StreamSupport.stream(gl.aj.spliterator(), false)
		.flatMap(bvr -> bvr.m().a().stream())
		.collect(Collectors.toList());
	private static final int i = aec.f(aec.c((float)h.size()));
	private static final int j = aec.f((float)h.size() / (float)i);
	protected static final cfj f = bvs.a.n();
	protected static final cfj g = bvs.go.n();

	private cil() {
		super(new bse(brk.c), new ciw(false));
	}

	@Override
	protected Codec<? extends cha> a() {
		return e;
	}

	@Override
	public void a(zj zj, cgy cgy) {
	}

	@Override
	public void a(long long1, brg brg, cgy cgy, cin.a a) {
	}

	@Override
	public void a(zj zj, bqq bqq) {
		fu.a a4 = new fu.a();
		int integer5 = zj.a();
		int integer6 = zj.b();

		for (int integer7 = 0; integer7 < 16; integer7++) {
			for (int integer8 = 0; integer8 < 16; integer8++) {
				int integer9 = (integer5 << 4) + integer7;
				int integer10 = (integer6 << 4) + integer8;
				zj.a(a4.d(integer9, 60, integer10), g, 2);
				cfj cfj11 = b(integer9, integer10);
				if (cfj11 != null) {
					zj.a(a4.d(integer9, 70, integer10), cfj11, 2);
				}
			}
		}
	}

	@Override
	public void b(bqc bqc, bqq bqq, cgy cgy) {
	}

	@Override
	public int a(int integer1, int integer2, cio.a a) {
		return 0;
	}

	@Override
	public bpg a(int integer1, int integer2) {
		return new bqk(new cfj[0]);
	}

	public static cfj b(int integer1, int integer2) {
		cfj cfj3 = f;
		if (integer1 > 0 && integer2 > 0 && integer1 % 2 != 0 && integer2 % 2 != 0) {
			integer1 /= 2;
			integer2 /= 2;
			if (integer1 <= i && integer2 <= j) {
				int integer4 = aec.a(integer1 * i + integer2);
				if (integer4 < h.size()) {
					cfj3 = (cfj)h.get(integer4);
				}
			}
		}

		return cfj3;
	}
}
