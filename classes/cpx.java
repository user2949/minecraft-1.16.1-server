import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;

public class cpx extends cqd {
	public static final Codec<cpx> a = RecordCodecBuilder.create(
		instance -> instance.group(ckb.b.fieldOf("feature").forGetter(cpx -> cpx.b), d()).apply(instance, cpx::new)
	);
	private final ckb<?, ?> b;
	private final le c;

	@Deprecated
	public cpx(ckb<?, ?> ckb) {
		this(ckb, cqf.a.RIGID);
	}

	private cpx(ckb<?, ?> ckb, cqf.a a) {
		super(a);
		this.b = ckb;
		this.c = this.b();
	}

	private le b() {
		le le2 = new le();
		le2.a("name", "minecraft:bottom");
		le2.a("final_state", "minecraft:air");
		le2.a("pool", "minecraft:empty");
		le2.a("target", "minecraft:empty");
		le2.a("joint", ceb.a.ROLLABLE.a());
		return le2;
	}

	public fu a(cva cva, cap cap) {
		return fu.b;
	}

	@Override
	public List<cve.c> a(cva cva, fu fu, cap cap, Random random) {
		List<cve.c> list6 = Lists.<cve.c>newArrayList();
		list6.add(new cve.c(fu, bvs.mZ.n().a(byu.a, gb.a(fz.DOWN, fz.SOUTH)), this.c));
		return list6;
	}

	@Override
	public ctd a(cva cva, fu fu, cap cap) {
		fu fu5 = this.a(cva, cap);
		return new ctd(fu.u(), fu.v(), fu.w(), fu.u() + fu5.u(), fu.v() + fu5.v(), fu.w() + fu5.w());
	}

	@Override
	public boolean a(cva cva, bqu bqu, bqq bqq, cha cha, fu fu5, fu fu6, cap cap, ctd ctd, Random random, boolean boolean10) {
		return this.b.a(bqu, bqq, cha, random, fu5);
	}

	@Override
	public cqe<?> a() {
		return cqe.c;
	}

	public String toString() {
		return "Feature[" + gl.aq.b(this.b.d) + "]";
	}
}
