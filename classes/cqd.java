import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public abstract class cqd {
	public static final Codec<cqd> e = gl.aL.dispatch("element_type", cqd::a, cqe::codec);
	@Nullable
	private volatile cqf.a a;

	protected static <E extends cqd> RecordCodecBuilder<E, cqf.a> d() {
		return cqf.a.c.fieldOf("projection").forGetter(cqd::e);
	}

	protected cqd(cqf.a a) {
		this.a = a;
	}

	public abstract List<cve.c> a(cva cva, fu fu, cap cap, Random random);

	public abstract ctd a(cva cva, fu fu, cap cap);

	public abstract boolean a(cva cva, bqu bqu, bqq bqq, cha cha, fu fu5, fu fu6, cap cap, ctd ctd, Random random, boolean boolean10);

	public abstract cqe<?> a();

	public void a(bqc bqc, cve.c c, fu fu, cap cap, Random random, ctd ctd) {
	}

	public cqd a(cqf.a a) {
		this.a = a;
		return this;
	}

	public cqf.a e() {
		cqf.a a2 = this.a;
		if (a2 == null) {
			throw new IllegalStateException();
		} else {
			return a2;
		}
	}

	public int f() {
		return 1;
	}
}
