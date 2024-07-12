import javax.annotation.Nullable;

public class bjz extends bim {
	public bjz(bvr bvr, bke.a a) {
		super(bvr, a);
	}

	@Nullable
	@Override
	protected cfj c(bin bin) {
		bec bec3 = bin.m();
		return bec3 != null && !bec3.eV() ? null : super.c(bin);
	}
}
