import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class cqb extends cqd {
	public static final Codec<cqb> a = RecordCodecBuilder.create(
		instance -> instance.group(cqd.e.listOf().fieldOf("elements").forGetter(cqb -> cqb.b), d()).apply(instance, cqb::new)
	);
	private final List<cqd> b;

	@Deprecated
	public cqb(List<cqd> list) {
		this(list, cqf.a.RIGID);
	}

	public cqb(List<cqd> list, cqf.a a) {
		super(a);
		if (list.isEmpty()) {
			throw new IllegalArgumentException("Elements are empty");
		} else {
			this.b = list;
			this.b(a);
		}
	}

	@Override
	public List<cve.c> a(cva cva, fu fu, cap cap, Random random) {
		return ((cqd)this.b.get(0)).a(cva, fu, cap, random);
	}

	@Override
	public ctd a(cva cva, fu fu, cap cap) {
		ctd ctd5 = ctd.a();

		for (cqd cqd7 : this.b) {
			ctd ctd8 = cqd7.a(cva, fu, cap);
			ctd5.c(ctd8);
		}

		return ctd5;
	}

	@Override
	public boolean a(cva cva, bqu bqu, bqq bqq, cha cha, fu fu5, fu fu6, cap cap, ctd ctd, Random random, boolean boolean10) {
		for (cqd cqd13 : this.b) {
			if (!cqd13.a(cva, bqu, bqq, cha, fu5, fu6, cap, ctd, random, boolean10)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public cqe<?> a() {
		return cqe.b;
	}

	@Override
	public cqd a(cqf.a a) {
		super.a(a);
		this.b(a);
		return this;
	}

	public String toString() {
		return "List[" + (String)this.b.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
	}

	private void b(cqf.a a) {
		this.b.forEach(cqd -> cqd.a(a));
	}
}
