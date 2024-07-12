import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class cpw extends cqd {
	public static final Codec<cpw> a = Codec.unit((Supplier<cpw>)(() -> cpw.b));
	public static final cpw b = new cpw();

	private cpw() {
		super(cqf.a.TERRAIN_MATCHING);
	}

	@Override
	public List<cve.c> a(cva cva, fu fu, cap cap, Random random) {
		return Collections.emptyList();
	}

	@Override
	public ctd a(cva cva, fu fu, cap cap) {
		return ctd.a();
	}

	@Override
	public boolean a(cva cva, bqu bqu, bqq bqq, cha cha, fu fu5, fu fu6, cap cap, ctd ctd, Random random, boolean boolean10) {
		return true;
	}

	@Override
	public cqe<?> a() {
		return cqe.d;
	}

	public String toString() {
		return "Empty";
	}
}
