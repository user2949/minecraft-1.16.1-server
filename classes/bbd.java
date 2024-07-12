import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class bbd extends baz {
	public bbc e;

	public bbd(aoq<? extends bbd> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bbd(bqb bqb, fu fu, fz fz) {
		super(aoq.ad, bqb, fu);
		List<bbc> list5 = Lists.<bbc>newArrayList();
		int integer6 = 0;

		for (bbc bbc8 : gl.aD) {
			this.e = bbc8;
			this.a(fz);
			if (this.h()) {
				list5.add(bbc8);
				int integer9 = bbc8.a() * bbc8.b();
				if (integer9 > integer6) {
					integer6 = integer9;
				}
			}
		}

		if (!list5.isEmpty()) {
			Iterator<bbc> iterator7 = list5.iterator();

			while (iterator7.hasNext()) {
				bbc bbc8x = (bbc)iterator7.next();
				if (bbc8x.a() * bbc8x.b() < integer6) {
					iterator7.remove();
				}
			}

			this.e = (bbc)list5.get(this.J.nextInt(list5.size()));
		}

		this.a(fz);
	}

	@Override
	public void b(le le) {
		le.a("Motive", gl.aD.b(this.e).toString());
		super.b(le);
	}

	@Override
	public void a(le le) {
		this.e = gl.aD.a(uh.a(le.l("Motive")));
		super.a(le);
	}

	@Override
	public int i() {
		return this.e == null ? 1 : this.e.a();
	}

	@Override
	public int k() {
		return this.e == null ? 1 : this.e.b();
	}

	@Override
	public void a(@Nullable aom aom) {
		if (this.l.S().b(bpx.g)) {
			this.a(acl.jI, 1.0F, 1.0F);
			if (aom instanceof bec) {
				bec bec3 = (bec)aom;
				if (bec3.bJ.d) {
					return;
				}
			}

			this.a(bkk.lz);
		}
	}

	@Override
	public void m() {
		this.a(acl.jJ, 1.0F, 1.0F);
	}

	@Override
	public void b(double double1, double double2, double double3, float float4, float float5) {
		this.d(double1, double2, double3);
	}

	@Override
	public ni<?> O() {
		return new np(this);
	}
}
