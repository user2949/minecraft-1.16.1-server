import com.google.common.collect.Lists;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class cts extends cty {
	private static final Logger d = LogManager.getLogger();
	protected final cqd a;
	protected fu b;
	private final int e;
	protected final cap c;
	private final List<cpy> f = Lists.<cpy>newArrayList();
	private final cva g;

	public cts(cmm cmm, cva cva, cqd cqd, fu fu, int integer, cap cap, ctd ctd) {
		super(cmm, 0);
		this.g = cva;
		this.a = cqd;
		this.b = fu;
		this.e = integer;
		this.c = cap;
		this.n = ctd;
	}

	public cts(cva cva, le le, cmm cmm) {
		super(cmm, le);
		this.g = cva;
		this.b = new fu(le.h("PosX"), le.h("PosY"), le.h("PosZ"));
		this.e = le.h("ground_level_delta");
		this.a = (cqd)cqd.e.parse(lp.a, le.p("pool_element")).resultOrPartial(d::error).orElse(cpw.b);
		this.c = cap.valueOf(le.l("rotation"));
		this.n = this.a.a(cva, this.b, this.c);
		lk lk5 = le.d("junctions", 10);
		this.f.clear();
		lk5.forEach(lu -> this.f.add(cpy.a(new Dynamic<>(lp.a, lu))));
	}

	@Override
	protected void a(le le) {
		le.b("PosX", this.b.u());
		le.b("PosY", this.b.v());
		le.b("PosZ", this.b.w());
		le.b("ground_level_delta", this.e);
		cqd.e.encodeStart(lp.a, this.a).resultOrPartial(d::error).ifPresent(lu -> le.a("pool_element", lu));
		le.a("rotation", this.c.name());
		lk lk3 = new lk();

		for (cpy cpy5 : this.f) {
			lk3.add(cpy5.a(lp.a).getValue());
		}

		le.a("junctions", lk3);
	}

	@Override
	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
		return this.a(bqu, bqq, cha, random, ctd, fu, false);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, fu fu, boolean boolean7) {
		return this.a.a(this.g, bqu, bqq, cha, this.b, fu, this.c, ctd, random, boolean7);
	}

	@Override
	public void a(int integer1, int integer2, int integer3) {
		super.a(integer1, integer2, integer3);
		this.b = this.b.b(integer1, integer2, integer3);
	}

	@Override
	public cap ap_() {
		return this.c;
	}

	public String toString() {
		return String.format("<%s | %s | %s | %s>", this.getClass().getSimpleName(), this.b, this.c, this.a);
	}

	public cqd b() {
		return this.a;
	}

	public fu c() {
		return this.b;
	}

	public int d() {
		return this.e;
	}

	public void a(cpy cpy) {
		this.f.add(cpy);
	}

	public List<cpy> e() {
		return this.f;
	}
}
