import com.mojang.authlib.GameProfile;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class yc extends abp {
	private static final Logger a = LogManager.getLogger();

	public yc(yd yd, gm.a a, dai dai) {
		super(yd, a, dai, yd.g_().J);
		ye ye5 = yd.g_();
		this.a(ye5.I);
		super.a(ye5.T.get());
		this.y();
		this.w();
		this.x();
		this.v();
		this.z();
		this.B();
		this.A();
		if (!this.i().b().exists()) {
			this.C();
		}
	}

	@Override
	public void a(boolean boolean1) {
		super.a(boolean1);
		this.b().j(boolean1);
	}

	@Override
	public void a(GameProfile gameProfile) {
		super.a(gameProfile);
		this.A();
	}

	@Override
	public void b(GameProfile gameProfile) {
		super.b(gameProfile);
		this.A();
	}

	@Override
	public void a() {
		this.B();
	}

	private void v() {
		try {
			this.g().e();
		} catch (IOException var2) {
			a.warn("Failed to save ip banlist: ", (Throwable)var2);
		}
	}

	private void w() {
		try {
			this.f().e();
		} catch (IOException var2) {
			a.warn("Failed to save user banlist: ", (Throwable)var2);
		}
	}

	private void x() {
		try {
			this.g().f();
		} catch (IOException var2) {
			a.warn("Failed to load ip banlist: ", (Throwable)var2);
		}
	}

	private void y() {
		try {
			this.f().f();
		} catch (IOException var2) {
			a.warn("Failed to load user banlist: ", (Throwable)var2);
		}
	}

	private void z() {
		try {
			this.k().f();
		} catch (Exception var2) {
			a.warn("Failed to load operators list: ", (Throwable)var2);
		}
	}

	private void A() {
		try {
			this.k().e();
		} catch (Exception var2) {
			a.warn("Failed to save operators list: ", (Throwable)var2);
		}
	}

	private void B() {
		try {
			this.i().f();
		} catch (Exception var2) {
			a.warn("Failed to load white-list: ", (Throwable)var2);
		}
	}

	private void C() {
		try {
			this.i().e();
		} catch (Exception var2) {
			a.warn("Failed to save white-list: ", (Throwable)var2);
		}
	}

	@Override
	public boolean e(GameProfile gameProfile) {
		return !this.o() || this.h(gameProfile) || this.i().a(gameProfile);
	}

	public yd c() {
		return (yd)super.c();
	}

	@Override
	public boolean f(GameProfile gameProfile) {
		return this.k().b(gameProfile);
	}
}
