import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

public class cqc extends cqd {
	private static final Codec<Either<uh, cve>> a = Codec.of(cqc::a, uh.a.map(Either::left));
	public static final Codec<cqc> b = RecordCodecBuilder.create(instance -> instance.group(c(), b(), d()).apply(instance, cqc::new));
	protected final Either<uh, cve> c;
	protected final ImmutableList<cvc> d;

	private static <T> DataResult<T> a(Either<uh, cve> either, DynamicOps<T> dynamicOps, T object) {
		Optional<uh> optional4 = either.left();
		return !optional4.isPresent() ? DataResult.error("Can not serialize a runtime pool element") : uh.a.encode((uh)optional4.get(), dynamicOps, object);
	}

	protected static <E extends cqc> RecordCodecBuilder<E, List<cvc>> b() {
		return cvd.j.listOf().fieldOf("processors").forGetter(cqc -> cqc.d);
	}

	protected static <E extends cqc> RecordCodecBuilder<E, Either<uh, cve>> c() {
		return a.fieldOf("location").forGetter(cqc -> cqc.c);
	}

	@Deprecated
	public cqc(String string, List<cvc> list) {
		this(Either.left(new uh(string)), list, cqf.a.RIGID);
	}

	protected cqc(Either<uh, cve> either, List<cvc> list, cqf.a a) {
		super(a);
		this.c = either;
		this.d = ImmutableList.copyOf(list);
	}

	public cqc(cve cve, List<cvc> list, cqf.a a) {
		this(Either.right(cve), list, a);
	}

	@Deprecated
	public cqc(String string) {
		this(string, ImmutableList.of());
	}

	private cve a(cva cva) {
		return this.c.map(cva::a, Function.identity());
	}

	public List<cve.c> a(cva cva, fu fu, cap cap, boolean boolean4) {
		cve cve6 = this.a(cva);
		List<cve.c> list7 = cve6.a(fu, new cvb().a(cap), bvs.mY, boolean4);
		List<cve.c> list8 = Lists.<cve.c>newArrayList();

		for (cve.c c10 : list7) {
			if (c10.c != null) {
				cgq cgq11 = cgq.valueOf(c10.c.l("mode"));
				if (cgq11 == cgq.DATA) {
					list8.add(c10);
				}
			}
		}

		return list8;
	}

	@Override
	public List<cve.c> a(cva cva, fu fu, cap cap, Random random) {
		cve cve6 = this.a(cva);
		List<cve.c> list7 = cve6.a(fu, new cvb().a(cap), bvs.mZ, true);
		Collections.shuffle(list7, random);
		return list7;
	}

	@Override
	public ctd a(cva cva, fu fu, cap cap) {
		cve cve5 = this.a(cva);
		return cve5.b(new cvb().a(cap), fu);
	}

	@Override
	public boolean a(cva cva, bqu bqu, bqq bqq, cha cha, fu fu5, fu fu6, cap cap, ctd ctd, Random random, boolean boolean10) {
		cve cve12 = this.a(cva);
		cvb cvb13 = this.a(cap, ctd, boolean10);
		if (!cve12.a(bqu, fu5, fu6, cvb13, random, 18)) {
			return false;
		} else {
			for (cve.c c16 : cve.a(bqu, fu5, fu6, cvb13, this.a(cva, fu5, cap, false))) {
				this.a(bqu, c16, fu5, cap, random, ctd);
			}

			return true;
		}
	}

	protected cvb a(cap cap, ctd ctd, boolean boolean3) {
		cvb cvb5 = new cvb();
		cvb5.a(ctd);
		cvb5.a(cap);
		cvb5.c(true);
		cvb5.a(false);
		cvb5.a(cui.b);
		cvb5.d(true);
		if (!boolean3) {
			cvb5.a(cun.b);
		}

		this.d.forEach(cvb5::a);
		this.e().c().forEach(cvb5::a);
		return cvb5;
	}

	@Override
	public cqe<?> a() {
		return cqe.a;
	}

	public String toString() {
		return "Single[" + this.c + "]";
	}
}
