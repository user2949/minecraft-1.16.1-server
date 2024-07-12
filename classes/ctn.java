import java.util.List;
import java.util.Random;

public class ctn {
	private static final uh[] a = new uh[]{
		new uh("nether_fossils/fossil_1"),
		new uh("nether_fossils/fossil_2"),
		new uh("nether_fossils/fossil_3"),
		new uh("nether_fossils/fossil_4"),
		new uh("nether_fossils/fossil_5"),
		new uh("nether_fossils/fossil_6"),
		new uh("nether_fossils/fossil_7"),
		new uh("nether_fossils/fossil_8"),
		new uh("nether_fossils/fossil_9"),
		new uh("nether_fossils/fossil_10"),
		new uh("nether_fossils/fossil_11"),
		new uh("nether_fossils/fossil_12"),
		new uh("nether_fossils/fossil_13"),
		new uh("nether_fossils/fossil_14")
	};

	public static void a(cva cva, List<cty> list, Random random, fu fu) {
		cap cap5 = cap.a(random);
		list.add(new ctn.a(cva, v.a(a, random), fu, cap5));
	}

	public static class a extends cub {
		private final uh d;
		private final cap e;

		public a(cva cva, uh uh, fu fu, cap cap) {
			super(cmm.ae, 0);
			this.d = uh;
			this.c = fu;
			this.e = cap;
			this.a(cva);
		}

		public a(cva cva, le le) {
			super(cmm.ae, le);
			this.d = new uh(le.l("Template"));
			this.e = cap.valueOf(le.l("Rot"));
			this.a(cva);
		}

		private void a(cva cva) {
			cve cve3 = cva.a(this.d);
			cvb cvb4 = new cvb().a(this.e).a(bzj.NONE).a(cui.d);
			this.a(cve3, this.c, cvb4);
		}

		@Override
		protected void a(le le) {
			super.a(le);
			le.a("Template", this.d.toString());
			le.a("Rot", this.e.name());
		}

		@Override
		protected void a(String string, fu fu, bqc bqc, Random random, ctd ctd) {
		}

		@Override
		public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
			ctd.c(this.a.b(this.b, this.c));
			return super.a(bqu, bqq, cha, random, ctd, bph, fu);
		}
	}
}
