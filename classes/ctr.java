import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;

public class ctr {
	public static void a(cha cha, cva cva, fu fu, List<cty> list, ciy ciy) {
		cpz.a(new uh("pillager_outpost/base_plates"), 7, ctr.a::new, cha, cva, fu, list, ciy, true, true);
	}

	public static void a() {
	}

	static {
		cpz.a.a(new cqf(new uh("pillager_outpost/base_plates"), new uh("empty"), ImmutableList.of(Pair.of(new cqa("pillager_outpost/base_plate"), 1)), cqf.a.RIGID));
		cpz.a
			.a(
				new cqf(
					new uh("pillager_outpost/towers"),
					new uh("empty"),
					ImmutableList.of(
						Pair.of(
							new cqb(ImmutableList.of(new cqa("pillager_outpost/watchtower"), new cqa("pillager_outpost/watchtower_overgrown", ImmutableList.of(new cuk(0.05F))))), 1
						)
					),
					cqf.a.RIGID
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("pillager_outpost/feature_plates"),
					new uh("empty"),
					ImmutableList.of(Pair.of(new cqa("pillager_outpost/feature_plate"), 1)),
					cqf.a.TERRAIN_MATCHING
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("pillager_outpost/features"),
					new uh("empty"),
					ImmutableList.of(
						Pair.of(new cqa("pillager_outpost/feature_cage1"), 1),
						Pair.of(new cqa("pillager_outpost/feature_cage2"), 1),
						Pair.of(new cqa("pillager_outpost/feature_logs"), 1),
						Pair.of(new cqa("pillager_outpost/feature_tent1"), 1),
						Pair.of(new cqa("pillager_outpost/feature_tent2"), 1),
						Pair.of(new cqa("pillager_outpost/feature_targets"), 1),
						Pair.of(cpw.b, 6)
					),
					cqf.a.RIGID
				)
			);
	}

	public static class a extends cts {
		public a(cva cva, cqd cqd, fu fu, int integer, cap cap, ctd ctd) {
			super(cmm.e, cva, cqd, fu, integer, cap, ctd);
		}

		public a(cva cva, le le) {
			super(cva, le, cmm.e);
		}
	}
}
