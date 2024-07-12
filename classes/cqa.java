import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class cqa extends cqc {
	public static final Codec<cqa> a = RecordCodecBuilder.create(instance -> instance.group(c(), b(), d()).apply(instance, cqa::new));

	@Deprecated
	public cqa(String string, List<cvc> list) {
		super(string, list);
	}

	private cqa(Either<uh, cve> either, List<cvc> list, cqf.a a) {
		super(either, list, a);
	}

	@Deprecated
	public cqa(String string) {
		super(string, ImmutableList.of());
	}

	@Override
	protected cvb a(cap cap, ctd ctd, boolean boolean3) {
		cvb cvb5 = super.a(cap, ctd, boolean3);
		cvb5.b(cui.b);
		cvb5.a(cui.d);
		return cvb5;
	}

	@Override
	public cqe<?> a() {
		return cqe.e;
	}

	@Override
	public String toString() {
		return "LegacySingle[" + this.c + "]";
	}
}
