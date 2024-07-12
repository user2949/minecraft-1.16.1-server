import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;

public class cmn extends cml<coa> {
	private static final List<bre.g> u = Lists.<bre.g>newArrayList(new bre.g(aoq.aR, 1, 1, 1));
	private static final List<bre.g> v = Lists.<bre.g>newArrayList(new bre.g(aoq.h, 1, 1, 1));

	public cmn(Codec<coa> codec) {
		super(codec);
	}

	@Override
	public cml.a<coa> a() {
		return cmn.a::new;
	}

	@Override
	public List<bre.g> c() {
		return u;
	}

	@Override
	public List<bre.g> j() {
		return v;
	}

	public static class a extends ctz<coa> {
		public a(cml<coa> cml, int integer2, int integer3, ctd ctd, int integer5, long long6) {
			super(cml, integer2, integer3, ctd, integer5, long6);
		}

		public void a(cha cha, cva cva, int integer3, int integer4, bre bre, coa coa) {
			cua cua8 = new cua(this.d, integer3 * 16, integer4 * 16);
			this.b.add(cua8);
			this.b();
		}
	}
}
