import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public enum c implements aeh {
	IDENTITY("identity", e.P123, false, false, false),
	ROT_180_FACE_XY("rot_180_face_xy", e.P123, true, true, false),
	ROT_180_FACE_XZ("rot_180_face_xz", e.P123, true, false, true),
	ROT_180_FACE_YZ("rot_180_face_yz", e.P123, false, true, true),
	ROT_120_NNN("rot_120_nnn", e.P231, false, false, false),
	ROT_120_NNP("rot_120_nnp", e.P312, true, false, true),
	ROT_120_NPN("rot_120_npn", e.P312, false, true, true),
	ROT_120_NPP("rot_120_npp", e.P231, true, false, true),
	ROT_120_PNN("rot_120_pnn", e.P312, true, true, false),
	ROT_120_PNP("rot_120_pnp", e.P231, true, true, false),
	ROT_120_PPN("rot_120_ppn", e.P231, false, true, true),
	ROT_120_PPP("rot_120_ppp", e.P312, false, false, false),
	ROT_180_EDGE_XY_NEG("rot_180_edge_xy_neg", e.P213, true, true, true),
	ROT_180_EDGE_XY_POS("rot_180_edge_xy_pos", e.P213, false, false, true),
	ROT_180_EDGE_XZ_NEG("rot_180_edge_xz_neg", e.P321, true, true, true),
	ROT_180_EDGE_XZ_POS("rot_180_edge_xz_pos", e.P321, false, true, false),
	ROT_180_EDGE_YZ_NEG("rot_180_edge_yz_neg", e.P132, true, true, true),
	ROT_180_EDGE_YZ_POS("rot_180_edge_yz_pos", e.P132, true, false, false),
	ROT_90_X_NEG("rot_90_x_neg", e.P132, false, false, true),
	ROT_90_X_POS("rot_90_x_pos", e.P132, false, true, false),
	ROT_90_Y_NEG("rot_90_y_neg", e.P321, true, false, false),
	ROT_90_Y_POS("rot_90_y_pos", e.P321, false, false, true),
	ROT_90_Z_NEG("rot_90_z_neg", e.P213, false, true, false),
	ROT_90_Z_POS("rot_90_z_pos", e.P213, true, false, false),
	INVERSION("inversion", e.P123, true, true, true),
	INVERT_X("invert_x", e.P123, true, false, false),
	INVERT_Y("invert_y", e.P123, false, true, false),
	INVERT_Z("invert_z", e.P123, false, false, true),
	ROT_60_REF_NNN("rot_60_ref_nnn", e.P312, true, true, true),
	ROT_60_REF_NNP("rot_60_ref_nnp", e.P231, true, false, false),
	ROT_60_REF_NPN("rot_60_ref_npn", e.P231, false, false, true),
	ROT_60_REF_NPP("rot_60_ref_npp", e.P312, false, false, true),
	ROT_60_REF_PNN("rot_60_ref_pnn", e.P231, false, true, false),
	ROT_60_REF_PNP("rot_60_ref_pnp", e.P312, true, false, false),
	ROT_60_REF_PPN("rot_60_ref_ppn", e.P312, false, true, false),
	ROT_60_REF_PPP("rot_60_ref_ppp", e.P231, true, true, true),
	SWAP_XY("swap_xy", e.P213, false, false, false),
	SWAP_YZ("swap_yz", e.P132, false, false, false),
	SWAP_XZ("swap_xz", e.P321, false, false, false),
	SWAP_NEG_XY("swap_neg_xy", e.P213, true, true, false),
	SWAP_NEG_YZ("swap_neg_yz", e.P132, false, true, true),
	SWAP_NEG_XZ("swap_neg_xz", e.P321, true, false, true),
	ROT_90_REF_X_NEG("rot_90_ref_x_neg", e.P132, true, false, true),
	ROT_90_REF_X_POS("rot_90_ref_x_pos", e.P132, true, true, false),
	ROT_90_REF_Y_NEG("rot_90_ref_y_neg", e.P321, true, true, false),
	ROT_90_REF_Y_POS("rot_90_ref_y_pos", e.P321, false, true, true),
	ROT_90_REF_Z_NEG("rot_90_ref_z_neg", e.P213, false, true, true),
	ROT_90_REF_Z_POS("rot_90_ref_z_pos", e.P213, true, false, true);

	private final a W;
	private final String X;
	@Nullable
	private Map<fz, fz> Y;
	private final boolean Z;
	private final boolean aa;
	private final boolean ab;
	private final e ac;
	private static final c[][] ad = v.a(new c[values().length][values().length], arr -> {
		Map<Pair<e, BooleanList>, c> map2 = (Map<Pair<e, BooleanList>, c>)Arrays.stream(values()).collect(Collectors.toMap(c -> Pair.of(c.ac, c.b()), c -> c));

		for (c c6 : values()) {
			for (c c10 : values()) {
				BooleanList booleanList11 = c6.b();
				BooleanList booleanList12 = c10.b();
				e e13 = c10.ac.a(c6.ac);
				BooleanArrayList booleanArrayList14 = new BooleanArrayList(3);

				for (int integer15 = 0; integer15 < 3; integer15++) {
					booleanArrayList14.add(booleanList11.getBoolean(integer15) ^ booleanList12.getBoolean(c6.ac.a(integer15)));
				}

				arr[c6.ordinal()][c10.ordinal()] = (c)map2.get(Pair.of(e13, booleanArrayList14));
			}
		}
	});
	private static final c[] ae = (c[])Arrays.stream(values())
		.map(c -> (c)Arrays.stream(values()).filter(c2 -> c.a(c2) == IDENTITY).findAny().get())
		.toArray(c[]::new);

	private c(String string3, e e, boolean boolean5, boolean boolean6, boolean boolean7) {
		this.X = string3;
		this.Z = boolean5;
		this.aa = boolean6;
		this.ab = boolean7;
		this.ac = e;
		this.W = new a();
		this.W.a = boolean5 ? -1.0F : 1.0F;
		this.W.e = boolean6 ? -1.0F : 1.0F;
		this.W.i = boolean7 ? -1.0F : 1.0F;
		this.W.b(e.a());
	}

	private BooleanList b() {
		return new BooleanArrayList(new boolean[]{this.Z, this.aa, this.ab});
	}

	public c a(c c) {
		return ad[this.ordinal()][c.ordinal()];
	}

	public String toString() {
		return this.X;
	}

	@Override
	public String a() {
		return this.X;
	}

	public fz a(fz fz) {
		if (this.Y == null) {
			this.Y = Maps.newEnumMap(fz.class);

			for (fz fz6 : fz.values()) {
				fz.a a7 = fz6.n();
				fz.b b8 = fz6.e();
				fz.a a9 = fz.a.values()[this.ac.a(a7.ordinal())];
				fz.b b10 = this.a(a9) ? b8.c() : b8;
				fz fz11 = fz.a(a9, b10);
				this.Y.put(fz6, fz11);
			}
		}

		return (fz)this.Y.get(fz);
	}

	public boolean a(fz.a a) {
		switch (a) {
			case X:
				return this.Z;
			case Y:
				return this.aa;
			case Z:
			default:
				return this.ab;
		}
	}

	public gb a(gb gb) {
		return gb.a(this.a(gb.b()), this.a(gb.c()));
	}
}
