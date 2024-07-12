import com.mojang.serialization.Codec;
import java.util.Random;

public class clt extends ckt<cog> {
	public clt(Codec<cog> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, cog cog) {
		cfj cfj8 = cog.b.a(random, fu);
		fu fu9;
		if (cog.l) {
			fu9 = bqu.a(cio.a.WORLD_SURFACE_WG, fu);
		} else {
			fu9 = fu;
		}

		int integer10 = 0;
		fu.a a11 = new fu.a();

		for (int integer12 = 0; integer12 < cog.f; integer12++) {
			a11.a(
				fu9,
				random.nextInt(cog.g + 1) - random.nextInt(cog.g + 1),
				random.nextInt(cog.h + 1) - random.nextInt(cog.h + 1),
				random.nextInt(cog.i + 1) - random.nextInt(cog.i + 1)
			);
			fu fu13 = a11.c();
			cfj cfj14 = bqu.d_(fu13);
			if ((bqu.w(a11) || cog.j && bqu.d_(a11).c().e())
				&& cfj8.a(bqu, a11)
				&& (cog.d.isEmpty() || cog.d.contains(cfj14.b()))
				&& !cog.e.contains(cfj14)
				&& (!cog.m || bqu.b(fu13.f()).a(acz.a) || bqu.b(fu13.g()).a(acz.a) || bqu.b(fu13.d()).a(acz.a) || bqu.b(fu13.e()).a(acz.a))) {
				cog.c.a(bqu, a11, cfj8, random);
				integer10++;
			}
		}

		return integer10 > 0;
	}
}
