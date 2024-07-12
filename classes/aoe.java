import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class aoe {
	private final Map<aps, apv> a = Maps.<aps, apv>newHashMap();
	private final aof b;
	private final int c;
	@Nullable
	private String d;

	@Nullable
	public static aoe a(int integer) {
		return gl.ai.a(integer);
	}

	public static int a(aoe aoe) {
		return gl.ai.a(aoe);
	}

	protected aoe(aof aof, int integer) {
		this.b = aof;
		this.c = integer;
	}

	public void a(aoy aoy, int integer) {
		if (this == aoi.j) {
			if (aoy.dj() < aoy.dw()) {
				aoy.b(1.0F);
			}
		} else if (this == aoi.s) {
			if (aoy.dj() > 1.0F) {
				aoy.a(anw.o, 1.0F);
			}
		} else if (this == aoi.t) {
			aoy.a(anw.p, 1.0F);
		} else if (this == aoi.q && aoy instanceof bec) {
			((bec)aoy).q(0.005F * (float)(integer + 1));
		} else if (this == aoi.w && aoy instanceof bec) {
			if (!aoy.l.v) {
				((bec)aoy).eH().a(integer + 1, 1.0F);
			}
		} else if ((this != aoi.f || aoy.di()) && (this != aoi.g || !aoy.di())) {
			if (this == aoi.g && !aoy.di() || this == aoi.f && aoy.di()) {
				aoy.a(anw.o, (float)(6 << integer));
			}
		} else {
			aoy.b((float)Math.max(4 << integer, 0));
		}
	}

	public void a(@Nullable aom aom1, @Nullable aom aom2, aoy aoy, int integer, double double5) {
		if ((this != aoi.f || aoy.di()) && (this != aoi.g || !aoy.di())) {
			if (this == aoi.g && !aoy.di() || this == aoi.f && aoy.di()) {
				int integer8 = (int)(double5 * (double)(6 << integer) + 0.5);
				if (aom1 == null) {
					aoy.a(anw.o, (float)integer8);
				} else {
					aoy.a(anw.c(aom1, aom2), (float)integer8);
				}
			} else {
				this.a(aoy, integer);
			}
		} else {
			int integer8 = (int)(double5 * (double)(4 << integer) + 0.5);
			aoy.b((float)integer8);
		}
	}

	public boolean a(int integer1, int integer2) {
		if (this == aoi.j) {
			int integer4 = 50 >> integer2;
			return integer4 > 0 ? integer1 % integer4 == 0 : true;
		} else if (this == aoi.s) {
			int integer4 = 25 >> integer2;
			return integer4 > 0 ? integer1 % integer4 == 0 : true;
		} else if (this == aoi.t) {
			int integer4 = 40 >> integer2;
			return integer4 > 0 ? integer1 % integer4 == 0 : true;
		} else {
			return this == aoi.q;
		}
	}

	public boolean a() {
		return false;
	}

	protected String b() {
		if (this.d == null) {
			this.d = v.a("effect", gl.ai.b(this));
		}

		return this.d;
	}

	public String c() {
		return this.b();
	}

	public mr d() {
		return new ne(this.c());
	}

	public int f() {
		return this.c;
	}

	public aoe a(aps aps, String string, double double3, apv.a a) {
		apv apv7 = new apv(UUID.fromString(string), this::c, double3, a);
		this.a.put(aps, apv7);
		return this;
	}

	public void a(aoy aoy, apu apu, int integer) {
		for (Entry<aps, apv> entry6 : this.a.entrySet()) {
			apt apt7 = apu.a((aps)entry6.getKey());
			if (apt7 != null) {
				apt7.d((apv)entry6.getValue());
			}
		}
	}

	public void b(aoy aoy, apu apu, int integer) {
		for (Entry<aps, apv> entry6 : this.a.entrySet()) {
			apt apt7 = apu.a((aps)entry6.getKey());
			if (apt7 != null) {
				apv apv8 = (apv)entry6.getValue();
				apt7.d(apv8);
				apt7.c(new apv(apv8.a(), this.c() + " " + integer, this.a(integer, apv8), apv8.c()));
			}
		}
	}

	public double a(int integer, apv apv) {
		return apv.d() * (double)(integer + 1);
	}
}
