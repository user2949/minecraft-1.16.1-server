import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class cdg extends cdl implements anj, ceo {
	public static final aoe[][] a = new aoe[][]{{aoi.a, aoi.c}, {aoi.k, aoi.h}, {aoi.e}, {aoi.j}};
	private static final Set<aoe> b = (Set<aoe>)Arrays.stream(a).flatMap(Arrays::stream).collect(Collectors.toSet());
	private List<cdg.a> c = Lists.<cdg.a>newArrayList();
	private List<cdg.a> g = Lists.<cdg.a>newArrayList();
	private int h;
	private int i = -1;
	@Nullable
	private aoe j;
	@Nullable
	private aoe k;
	@Nullable
	private mr l;
	private ani m = ani.a;
	private final bgr n = new bgr() {
		@Override
		public int a(int integer) {
			switch (integer) {
				case 0:
					return cdg.this.h;
				case 1:
					return aoe.a(cdg.this.j);
				case 2:
					return aoe.a(cdg.this.k);
				default:
					return 0;
			}
		}

		@Override
		public void a(int integer1, int integer2) {
			switch (integer1) {
				case 0:
					cdg.this.h = integer2;
					break;
				case 1:
					if (!cdg.this.d.v && !cdg.this.c.isEmpty()) {
						cdg.this.a(acl.ax);
					}

					cdg.this.j = cdg.b(integer2);
					break;
				case 2:
					cdg.this.k = cdg.b(integer2);
			}
		}

		@Override
		public int a() {
			return 3;
		}
	};

	public cdg() {
		super(cdm.n);
	}

	@Override
	public void al_() {
		int integer2 = this.e.u();
		int integer3 = this.e.v();
		int integer4 = this.e.w();
		fu fu5;
		if (this.i < integer3) {
			fu5 = this.e;
			this.g = Lists.<cdg.a>newArrayList();
			this.i = fu5.v() - 1;
		} else {
			fu5 = new fu(integer2, this.i + 1, integer4);
		}

		cdg.a a6 = this.g.isEmpty() ? null : (cdg.a)this.g.get(this.g.size() - 1);
		int integer7 = this.d.a(cio.a.WORLD_SURFACE, integer2, integer4);

		for (int integer8 = 0; integer8 < 10 && fu5.v() <= integer7; integer8++) {
			cfj cfj9 = this.d.d_(fu5);
			bvr bvr10 = cfj9.b();
			if (bvr10 instanceof bvk) {
				float[] arr11 = ((bvk)bvr10).a().e();
				if (this.g.size() <= 1) {
					a6 = new cdg.a(arr11);
					this.g.add(a6);
				} else if (a6 != null) {
					if (Arrays.equals(arr11, a6.a)) {
						a6.a();
					} else {
						a6 = new cdg.a(new float[]{(a6.a[0] + arr11[0]) / 2.0F, (a6.a[1] + arr11[1]) / 2.0F, (a6.a[2] + arr11[2]) / 2.0F});
						this.g.add(a6);
					}
				}
			} else {
				if (a6 == null || cfj9.b((bpg)this.d, fu5) >= 15 && bvr10 != bvs.z) {
					this.g.clear();
					this.i = integer7;
					break;
				}

				a6.a();
			}

			fu5 = fu5.b();
			this.i++;
		}

		int integer8 = this.h;
		if (this.d.Q() % 80L == 0L) {
			if (!this.c.isEmpty()) {
				this.a(integer2, integer3, integer4);
			}

			if (this.h > 0 && !this.c.isEmpty()) {
				this.j();
				this.a(acl.av);
			}
		}

		if (this.i >= integer7) {
			this.i = -1;
			boolean boolean9 = integer8 > 0;
			this.c = this.g;
			if (!this.d.v) {
				boolean boolean10 = this.h > 0;
				if (!boolean9 && boolean10) {
					this.a(acl.au);

					for (ze ze12 : this.d
						.a(ze.class, new deg((double)integer2, (double)integer3, (double)integer4, (double)integer2, (double)(integer3 - 4), (double)integer4).c(10.0, 5.0, 10.0))) {
						aa.l.a(ze12, this);
					}
				} else if (boolean9 && !boolean10) {
					this.a(acl.aw);
				}
			}
		}
	}

	private void a(int integer1, int integer2, int integer3) {
		this.h = 0;

		for (int integer5 = 1; integer5 <= 4; this.h = integer5++) {
			int integer6 = integer2 - integer5;
			if (integer6 < 0) {
				break;
			}

			boolean boolean7 = true;

			for (int integer8 = integer1 - integer5; integer8 <= integer1 + integer5 && boolean7; integer8++) {
				for (int integer9 = integer3 - integer5; integer9 <= integer3 + integer5; integer9++) {
					if (!this.d.d_(new fu(integer8, integer6, integer9)).a(acx.ap)) {
						boolean7 = false;
						break;
					}
				}
			}

			if (!boolean7) {
				break;
			}
		}
	}

	@Override
	public void an_() {
		this.a(acl.aw);
		super.an_();
	}

	private void j() {
		if (!this.d.v && this.j != null) {
			double double2 = (double)(this.h * 10 + 10);
			int integer4 = 0;
			if (this.h >= 4 && this.j == this.k) {
				integer4 = 1;
			}

			int integer5 = (9 + this.h * 2) * 20;
			deg deg6 = new deg(this.e).g(double2).b(0.0, (double)this.d.I(), 0.0);
			List<bec> list7 = this.d.a(bec.class, deg6);

			for (bec bec9 : list7) {
				bec9.c(new aog(this.j, integer5, integer4, true, true));
			}

			if (this.h >= 4 && this.j != this.k && this.k != null) {
				for (bec bec9 : list7) {
					bec9.c(new aog(this.k, integer5, 0, true, true));
				}
			}
		}
	}

	public void a(ack ack) {
		this.d.a(null, this.e, ack, acm.BLOCKS, 1.0F, 1.0F);
	}

	public int h() {
		return this.h;
	}

	@Nullable
	@Override
	public nv a() {
		return new nv(this.e, 3, this.b());
	}

	@Override
	public le b() {
		return this.a(new le());
	}

	@Nullable
	private static aoe b(int integer) {
		aoe aoe2 = aoe.a(integer);
		return b.contains(aoe2) ? aoe2 : null;
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.j = b(le.h("Primary"));
		this.k = b(le.h("Secondary"));
		if (le.c("CustomName", 8)) {
			this.l = mr.a.a(le.l("CustomName"));
		}

		this.m = ani.b(le);
	}

	@Override
	public le a(le le) {
		super.a(le);
		le.b("Primary", aoe.a(this.j));
		le.b("Secondary", aoe.a(this.k));
		le.b("Levels", this.h);
		if (this.l != null) {
			le.a("CustomName", mr.a.a(this.l));
		}

		this.m.a(le);
		return le;
	}

	public void a(@Nullable mr mr) {
		this.l = mr;
	}

	@Nullable
	@Override
	public bgi createMenu(int integer, beb beb, bec bec) {
		return cdf.a(bec, this.m, this.d()) ? new bgl(integer, beb, this.n, bgs.a(this.d, this.o())) : null;
	}

	@Override
	public mr d() {
		return (mr)(this.l != null ? this.l : new ne("container.beacon"));
	}

	public static class a {
		private final float[] a;
		private int b;

		public a(float[] arr) {
			this.a = arr;
			this.b = 1;
		}

		protected void a() {
			this.b++;
		}
	}
}
