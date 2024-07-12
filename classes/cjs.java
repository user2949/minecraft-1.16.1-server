import com.google.common.collect.ImmutableMap;
import java.util.List;

public class cjs {
	public static final ImmutableMap<String, Integer> a = ImmutableMap.<String, Integer>builder()
		.put("bastion/units/base", 60)
		.put("bastion/hoglin_stable/origin", 60)
		.put("bastion/treasure/starters", 60)
		.put("bastion/bridge/start", 60)
		.build();

	public static void a() {
		cjr.a();
		cjq.a();
		cju.a();
		cjo.a();
		cjt.a();
	}

	public static void a(cha cha, cva cva, fu fu, List<cty> list, ciy ciy, cnx cnx) {
		a();
		cnu cnu7 = cnx.a(ciy);
		cpz.a(cnu7.b, cnu7.c, cjs.a::new, cha, cva, fu, list, ciy, false, false);
	}

	public static class a extends cts {
		public a(cva cva, cqd cqd, fu fu, int integer, cap cap, ctd ctd) {
			super(cmm.af, cva, cqd, fu, integer, cap, ctd);
		}

		public a(cva cva, le le) {
			super(cva, le, cmm.af);
		}
	}
}
