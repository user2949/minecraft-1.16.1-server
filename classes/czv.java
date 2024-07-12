import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class czv extends czq {
	private static final Logger k = LogManager.getLogger();
	public int a;
	public int b;
	public ug<bqb> c;
	public boolean d;
	public boolean e;
	public byte f;
	public byte[] g = new byte[16384];
	public boolean h;
	public final List<czv.a> i = Lists.<czv.a>newArrayList();
	private final Map<bec, czv.a> l = Maps.<bec, czv.a>newHashMap();
	private final Map<String, czr> m = Maps.<String, czr>newHashMap();
	public final Map<String, czs> j = Maps.<String, czs>newLinkedHashMap();
	private final Map<String, czt> n = Maps.<String, czt>newHashMap();

	public czv(String string) {
		super(string);
	}

	public void a(int integer1, int integer2, int integer3, boolean boolean4, boolean boolean5, ug<bqb> ug) {
		this.f = (byte)integer3;
		this.a((double)integer1, (double)integer2, this.f);
		this.c = ug;
		this.d = boolean4;
		this.e = boolean5;
		this.b();
	}

	public void a(double double1, double double2, int integer) {
		int integer7 = 128 * (1 << integer);
		int integer8 = aec.c((double1 + 64.0) / (double)integer7);
		int integer9 = aec.c((double2 + 64.0) / (double)integer7);
		this.a = integer8 * integer7 + integer7 / 2 - 64;
		this.b = integer9 * integer7 + integer7 / 2 - 64;
	}

	@Override
	public void a(le le) {
		this.c = (ug<bqb>)cif.a(new Dynamic<>(lp.a, le.c("dimension")))
			.resultOrPartial(k::error)
			.orElseThrow(() -> new IllegalArgumentException("Invalid map dimension: " + le.c("dimension")));
		this.a = le.h("xCenter");
		this.b = le.h("zCenter");
		this.f = (byte)aec.a(le.f("scale"), 0, 4);
		this.d = !le.c("trackingPosition", 1) || le.q("trackingPosition");
		this.e = le.q("unlimitedTracking");
		this.h = le.q("locked");
		this.g = le.m("colors");
		if (this.g.length != 16384) {
			this.g = new byte[16384];
		}

		lk lk3 = le.d("banners", 10);

		for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
			czr czr5 = czr.a(lk3.a(integer4));
			this.m.put(czr5.f(), czr5);
			this.a(czr5.c(), null, czr5.f(), (double)czr5.a().u(), (double)czr5.a().w(), 180.0, czr5.d());
		}

		lk lk4 = le.d("frames", 10);

		for (int integer5 = 0; integer5 < lk4.size(); integer5++) {
			czt czt6 = czt.a(lk4.a(integer5));
			this.n.put(czt6.e(), czt6);
			this.a(czs.a.FRAME, null, "frame-" + czt6.d(), (double)czt6.b().u(), (double)czt6.b().w(), (double)czt6.c(), null);
		}
	}

	@Override
	public le b(le le) {
		uh.a.encodeStart(lp.a, this.c.a()).resultOrPartial(k::error).ifPresent(lu -> le.a("dimension", lu));
		le.b("xCenter", this.a);
		le.b("zCenter", this.b);
		le.a("scale", this.f);
		le.a("colors", this.g);
		le.a("trackingPosition", this.d);
		le.a("unlimitedTracking", this.e);
		le.a("locked", this.h);
		lk lk3 = new lk();

		for (czr czr5 : this.m.values()) {
			lk3.add(czr5.e());
		}

		le.a("banners", lk3);
		lk lk4 = new lk();

		for (czt czt6 : this.n.values()) {
			lk4.add(czt6.a());
		}

		le.a("frames", lk4);
		return le;
	}

	public void a(czv czv) {
		this.h = true;
		this.a = czv.a;
		this.b = czv.b;
		this.m.putAll(czv.m);
		this.j.putAll(czv.j);
		System.arraycopy(czv.g, 0, this.g, 0, czv.g.length);
		this.b();
	}

	public void a(bec bec, bki bki) {
		if (!this.l.containsKey(bec)) {
			czv.a a4 = new czv.a(bec);
			this.l.put(bec, a4);
			this.i.add(a4);
		}

		if (!bec.bt.h(bki)) {
			this.j.remove(bec.P().getString());
		}

		for (int integer4 = 0; integer4 < this.i.size(); integer4++) {
			czv.a a5 = (czv.a)this.i.get(integer4);
			String string6 = a5.a.P().getString();
			if (!a5.a.y && (a5.a.bt.h(bki) || bki.y())) {
				if (!bki.y() && a5.a.l.W() == this.c && this.d) {
					this.a(czs.a.PLAYER, a5.a.l, string6, a5.a.cC(), a5.a.cG(), (double)a5.a.p, null);
				}
			} else {
				this.l.remove(a5.a);
				this.i.remove(a5);
				this.j.remove(string6);
			}
		}

		if (bki.y() && this.d) {
			bba bba4 = bki.z();
			fu fu5 = bba4.n();
			czt czt6 = (czt)this.n.get(czt.a(fu5));
			if (czt6 != null && bba4.V() != czt6.d() && this.n.containsKey(czt6.e())) {
				this.j.remove("frame-" + czt6.d());
			}

			czt czt7 = new czt(fu5, bba4.bY().d() * 90, bba4.V());
			this.a(czs.a.FRAME, bec.l, "frame-" + bba4.V(), (double)fu5.u(), (double)fu5.w(), (double)(bba4.bY().d() * 90), null);
			this.n.put(czt7.e(), czt7);
		}

		le le4 = bki.o();
		if (le4 != null && le4.c("Decorations", 9)) {
			lk lk5 = le4.d("Decorations", 10);

			for (int integer6 = 0; integer6 < lk5.size(); integer6++) {
				le le7 = lk5.a(integer6);
				if (!this.j.containsKey(le7.l("id"))) {
					this.a(czs.a.a(le7.f("type")), bec.l, le7.l("id"), le7.k("x"), le7.k("z"), le7.k("rot"), null);
				}
			}
		}
	}

	public static void a(bki bki, fu fu, String string, czs.a a) {
		lk lk5;
		if (bki.n() && bki.o().c("Decorations", 9)) {
			lk5 = bki.o().d("Decorations", 10);
		} else {
			lk5 = new lk();
			bki.a("Decorations", lk5);
		}

		le le6 = new le();
		le6.a("type", a.a());
		le6.a("id", string);
		le6.a("x", (double)fu.u());
		le6.a("z", (double)fu.w());
		le6.a("rot", 180.0);
		lk5.add(le6);
		if (a.c()) {
			le le7 = bki.a("display");
			le7.b("MapColor", a.d());
		}
	}

	private void a(czs.a a, @Nullable bqc bqc, String string, double double4, double double5, double double6, @Nullable mr mr) {
		int integer12 = 1 << this.f;
		float float13 = (float)(double4 - (double)this.a) / (float)integer12;
		float float14 = (float)(double5 - (double)this.b) / (float)integer12;
		byte byte15 = (byte)((int)((double)(float13 * 2.0F) + 0.5));
		byte byte16 = (byte)((int)((double)(float14 * 2.0F) + 0.5));
		int integer18 = 63;
		byte byte17;
		if (float13 >= -63.0F && float14 >= -63.0F && float13 <= 63.0F && float14 <= 63.0F) {
			double6 += double6 < 0.0 ? -8.0 : 8.0;
			byte17 = (byte)((int)(double6 * 16.0 / 360.0));
			if (this.c == bqb.h && bqc != null) {
				int integer19 = (int)(bqc.u_().e() / 10L);
				byte17 = (byte)(integer19 * integer19 * 34187121 + integer19 * 121 >> 15 & 15);
			}
		} else {
			if (a != czs.a.PLAYER) {
				this.j.remove(string);
				return;
			}

			int integer19 = 320;
			if (Math.abs(float13) < 320.0F && Math.abs(float14) < 320.0F) {
				a = czs.a.PLAYER_OFF_MAP;
			} else {
				if (!this.e) {
					this.j.remove(string);
					return;
				}

				a = czs.a.PLAYER_OFF_LIMITS;
			}

			byte17 = 0;
			if (float13 <= -63.0F) {
				byte15 = -128;
			}

			if (float14 <= -63.0F) {
				byte16 = -128;
			}

			if (float13 >= 63.0F) {
				byte15 = 127;
			}

			if (float14 >= 63.0F) {
				byte16 = 127;
			}
		}

		this.j.put(string, new czs(a, byte15, byte16, byte17, mr));
	}

	@Nullable
	public ni<?> a(bki bki, bpg bpg, bec bec) {
		czv.a a5 = (czv.a)this.l.get(bec);
		return a5 == null ? null : a5.a(bki);
	}

	public void a(int integer1, int integer2) {
		this.b();

		for (czv.a a5 : this.i) {
			a5.a(integer1, integer2);
		}
	}

	public czv.a a(bec bec) {
		czv.a a3 = (czv.a)this.l.get(bec);
		if (a3 == null) {
			a3 = new czv.a(bec);
			this.l.put(bec, a3);
			this.i.add(a3);
		}

		return a3;
	}

	public void a(bqc bqc, fu fu) {
		double double4 = (double)fu.u() + 0.5;
		double double6 = (double)fu.w() + 0.5;
		int integer8 = 1 << this.f;
		double double9 = (double4 - (double)this.a) / (double)integer8;
		double double11 = (double6 - (double)this.b) / (double)integer8;
		int integer13 = 63;
		boolean boolean14 = false;
		if (double9 >= -63.0 && double11 >= -63.0 && double9 <= 63.0 && double11 <= 63.0) {
			czr czr15 = czr.a(bqc, fu);
			if (czr15 == null) {
				return;
			}

			boolean boolean16 = true;
			if (this.m.containsKey(czr15.f()) && ((czr)this.m.get(czr15.f())).equals(czr15)) {
				this.m.remove(czr15.f());
				this.j.remove(czr15.f());
				boolean16 = false;
				boolean14 = true;
			}

			if (boolean16) {
				this.m.put(czr15.f(), czr15);
				this.a(czr15.c(), bqc, czr15.f(), double4, double6, 180.0, czr15.d());
				boolean14 = true;
			}

			if (boolean14) {
				this.b();
			}
		}
	}

	public void a(bpg bpg, int integer2, int integer3) {
		Iterator<czr> iterator5 = this.m.values().iterator();

		while (iterator5.hasNext()) {
			czr czr6 = (czr)iterator5.next();
			if (czr6.a().u() == integer2 && czr6.a().w() == integer3) {
				czr czr7 = czr.a(bpg, czr6.a());
				if (!czr6.equals(czr7)) {
					iterator5.remove();
					this.j.remove(czr6.f());
				}
			}
		}
	}

	public void a(fu fu, int integer) {
		this.j.remove("frame-" + integer);
		this.n.remove(czt.a(fu));
	}

	public class a {
		public final bec a;
		private boolean d = true;
		private int e;
		private int f;
		private int g = 127;
		private int h = 127;
		private int i;
		public int b;

		public a(bec bec) {
			this.a = bec;
		}

		@Nullable
		public ni<?> a(bki bki) {
			if (this.d) {
				this.d = false;
				return new oy(bko.d(bki), czv.this.f, czv.this.d, czv.this.h, czv.this.j.values(), czv.this.g, this.e, this.f, this.g + 1 - this.e, this.h + 1 - this.f);
			} else {
				return this.i++ % 5 == 0 ? new oy(bko.d(bki), czv.this.f, czv.this.d, czv.this.h, czv.this.j.values(), czv.this.g, 0, 0, 0, 0) : null;
			}
		}

		public void a(int integer1, int integer2) {
			if (this.d) {
				this.e = Math.min(this.e, integer1);
				this.f = Math.min(this.f, integer2);
				this.g = Math.max(this.g, integer1);
				this.h = Math.max(this.h, integer2);
			} else {
				this.d = true;
				this.e = integer1;
				this.f = integer2;
				this.g = integer1;
				this.h = integer2;
			}
		}
	}
}
