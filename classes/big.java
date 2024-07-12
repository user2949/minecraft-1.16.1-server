import java.util.Random;

public class big extends bke {
	public big(bke.a a) {
		super(a);
	}

	@Override
	public ang a(blv blv) {
		fz fz3 = blv.i();
		if (fz3 == fz.DOWN) {
			return ang.FAIL;
		} else {
			bqb bqb4 = blv.o();
			bin bin5 = new bin(blv);
			fu fu6 = bin5.a();
			bki bki7 = blv.l();
			bay bay8 = aoq.b.b(bqb4, bki7.o(), null, blv.m(), fu6, apb.SPAWN_EGG, true, true);
			if (bqb4.j(bay8) && bqb4.a(bay8, bay8.cb()).isEmpty()) {
				if (!bqb4.v) {
					float float9 = (float)aec.d((aec.g(blv.h() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					bay8.b(bay8.cC(), bay8.cD(), bay8.cG(), float9, 0.0F);
					this.a(bay8, bqb4.t);
					bqb4.c(bay8);
					bqb4.a(null, bay8.cC(), bay8.cD(), bay8.cG(), acl.V, acm.BLOCKS, 0.75F, 0.8F);
				}

				bki7.g(1);
				return ang.a(bqb4.v);
			} else {
				return ang.FAIL;
			}
		}
	}

	private void a(bay bay, Random random) {
		gn gn4 = bay.r();
		float float6 = random.nextFloat() * 5.0F;
		float float7 = random.nextFloat() * 20.0F - 10.0F;
		gn gn5 = new gn(gn4.b() + float6, gn4.c() + float7, gn4.d());
		bay.a(gn5);
		gn4 = bay.t();
		float6 = random.nextFloat() * 10.0F - 5.0F;
		gn5 = new gn(gn4.b(), gn4.c() + float6, gn4.d());
		bay.b(gn5);
	}
}
