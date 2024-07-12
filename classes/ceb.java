import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

public class ceb extends cdl {
	private uh a = new uh("empty");
	private uh b = new uh("empty");
	private uh c = new uh("empty");
	private ceb.a g = ceb.a.ROLLABLE;
	private String h = "minecraft:air";

	public ceb(cdm<?> cdm) {
		super(cdm);
	}

	public ceb() {
		this(cdm.E);
	}

	public void a(uh uh) {
		this.a = uh;
	}

	public void b(uh uh) {
		this.b = uh;
	}

	public void c(uh uh) {
		this.c = uh;
	}

	public void a(String string) {
		this.h = string;
	}

	public void a(ceb.a a) {
		this.g = a;
	}

	@Override
	public le a(le le) {
		super.a(le);
		le.a("name", this.a.toString());
		le.a("target", this.b.toString());
		le.a("pool", this.c.toString());
		le.a("final_state", this.h);
		le.a("joint", this.g.a());
		return le;
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.a = new uh(le.l("name"));
		this.b = new uh(le.l("target"));
		this.c = new uh(le.l("pool"));
		this.h = le.l("final_state");
		this.g = (ceb.a)ceb.a.a(le.l("joint")).orElseGet(() -> byu.h(cfj).n().d() ? ceb.a.ALIGNED : ceb.a.ROLLABLE);
	}

	@Nullable
	@Override
	public nv a() {
		return new nv(this.e, 12, this.b());
	}

	@Override
	public le b() {
		return this.a(new le());
	}

	public void a(zd zd, int integer, boolean boolean3) {
		cha cha5 = zd.i().g();
		cva cva6 = zd.r_();
		bqq bqq7 = zd.a();
		Random random8 = zd.v_();
		fu fu9 = this.o();
		List<cts> list10 = Lists.<cts>newArrayList();
		cve cve11 = new cve();
		cve11.a(zd, fu9, new fu(1, 1, 1), false, null);
		cqd cqd12 = new cqc(cve11, ImmutableList.of(), cqf.a.RIGID);
		ceb.b b13 = new ceb.b(cva6, cqd12, fu9, 1, cap.NONE, new ctd(fu9, fu9));
		cpz.a(b13, integer, ceb.b::new, cha5, cva6, list10, random8);

		for (cts cts15 : list10) {
			cts15.a(zd, bqq7, cha5, random8, ctd.b(), fu9, boolean3);
		}
	}

	public static enum a implements aeh {
		ROLLABLE("rollable"),
		ALIGNED("aligned");

		private final String c;

		private a(String string3) {
			this.c = string3;
		}

		@Override
		public String a() {
			return this.c;
		}

		public static Optional<ceb.a> a(String string) {
			return Arrays.stream(values()).filter(a -> a.a().equals(string)).findFirst();
		}
	}

	public static final class b extends cts {
		public b(cva cva, cqd cqd, fu fu, int integer, cap cap, ctd ctd) {
			super(cmm.ag, cva, cqd, fu, integer, cap, ctd);
		}

		public b(cva cva, le le) {
			super(cva, le, cmm.ag);
		}
	}
}
