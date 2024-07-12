import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

public class cjo {
	public static void a() {
	}

	static {
		ImmutableList<cvc> immutableList1 = ImmutableList.of(
			new cux(
				ImmutableList.of(
					new cuu(new cuv(bvs.nu, 0.4F), cue.b, bvs.nv.n()),
					new cuu(new cuv(bvs.np, 0.01F), cue.b, bvs.nv.n()),
					new cuu(new cuv(bvs.nu, 1.0E-4F), cue.b, bvs.a.n()),
					new cuu(new cuv(bvs.np, 1.0E-4F), cue.b, bvs.a.n()),
					new cuu(new cuv(bvs.bE, 0.3F), cue.b, bvs.nv.n()),
					cjt.a
				)
			)
		);
		ImmutableList<cvc> immutableList2 = ImmutableList.of(
			new cux(
				ImmutableList.of(
					new cuu(new cuv(bvs.nu, 0.3F), cue.b, bvs.nv.n()),
					new cuu(new cuv(bvs.np, 1.0E-4F), cue.b, bvs.a.n()),
					new cuu(new cuv(bvs.bE, 0.3F), cue.b, bvs.nv.n()),
					cjt.a
				)
			)
		);
		ImmutableList<cvc> immutableList3 = ImmutableList.of(
			new cux(ImmutableList.of(new cuu(new cuv(bvs.nw, 0.5F), cue.b, bvs.a.n()), new cuu(new cuv(bvs.bE, 0.6F), cue.b, bvs.nv.n()), cjt.a))
		);
		ImmutableList<cvc> immutableList4 = ImmutableList.of(
			new cux(ImmutableList.of(new cuu(new cuv(bvs.nu, 0.3F), cue.b, bvs.nv.n()), new cuu(new cuv(bvs.np, 1.0E-4F), cue.b, bvs.a.n())))
		);
		cpz.a
			.a(
				new cqf(
					new uh("bastion/bridge/start"),
					new uh("empty"),
					ImmutableList.of(Pair.of(new cqc("bastion/bridge/starting_pieces/entrance_base", immutableList2), 1)),
					cqf.a.RIGID
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("bastion/bridge/starting_pieces"),
					new uh("empty"),
					ImmutableList.of(
						Pair.of(new cqc("bastion/bridge/starting_pieces/entrance", immutableList3), 1),
						Pair.of(new cqc("bastion/bridge/starting_pieces/entrance_face", immutableList2), 1)
					),
					cqf.a.RIGID
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("bastion/bridge/bridge_pieces"),
					new uh("empty"),
					ImmutableList.of(Pair.of(new cqc("bastion/bridge/bridge_pieces/bridge", immutableList4), 1)),
					cqf.a.RIGID
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("bastion/bridge/legs"),
					new uh("empty"),
					ImmutableList.of(Pair.of(new cqc("bastion/bridge/legs/leg_0", immutableList2), 1), Pair.of(new cqc("bastion/bridge/legs/leg_1", immutableList2), 1)),
					cqf.a.RIGID
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("bastion/bridge/walls"),
					new uh("empty"),
					ImmutableList.of(
						Pair.of(new cqc("bastion/bridge/walls/wall_base_0", immutableList1), 1), Pair.of(new cqc("bastion/bridge/walls/wall_base_1", immutableList1), 1)
					),
					cqf.a.RIGID
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("bastion/bridge/ramparts"),
					new uh("empty"),
					ImmutableList.of(
						Pair.of(new cqc("bastion/bridge/ramparts/rampart_0", immutableList1), 1), Pair.of(new cqc("bastion/bridge/ramparts/rampart_1", immutableList1), 1)
					),
					cqf.a.RIGID
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("bastion/bridge/rampart_plates"),
					new uh("empty"),
					ImmutableList.of(Pair.of(new cqc("bastion/bridge/rampart_plates/plate_0", immutableList1), 1)),
					cqf.a.RIGID
				)
			);
		cpz.a
			.a(
				new cqf(
					new uh("bastion/bridge/connectors"),
					new uh("empty"),
					ImmutableList.of(
						Pair.of(new cqc("bastion/bridge/connectors/back_bridge_top", immutableList2), 1),
						Pair.of(new cqc("bastion/bridge/connectors/back_bridge_bottom", immutableList2), 1)
					),
					cqf.a.RIGID
				)
			);
	}
}
