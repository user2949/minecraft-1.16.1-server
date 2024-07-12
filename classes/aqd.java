import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.server.MinecraftServer;

public class aqd extends aqh<bdp> {
	public aqd() {
		super(ImmutableMap.of(awp.d, awq.VALUE_PRESENT));
	}

	protected boolean a(zd zd, bdp bdp) {
		fu fu4 = ((gc)bdp.cI().c(awp.d).get()).b();
		return fu4.a(bdp.cz(), 2.0) || bdp.eZ();
	}

	protected void a(zd zd, bdp bdp, long long3) {
		gc gc6 = (gc)bdp.cI().c(awp.d).get();
		bdp.cI().b(awp.d);
		bdp.cI().a(awp.c, gc6);
		if (bdp.eY().b() == bds.a) {
			MinecraftServer minecraftServer7 = zd.l();
			Optional.ofNullable(minecraftServer7.a(gc6.a()))
				.flatMap(zdx -> zdx.x().c(gc6.b()))
				.flatMap(ayc -> gl.aS.e().filter(bds -> bds.b() == ayc).findFirst())
				.ifPresent(bds -> {
					bdp.a(bdp.eY().a(bds));
					bdp.b(zd);
				});
		}
	}
}
