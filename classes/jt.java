import java.nio.file.Path;

public class jt extends jv<cwz> {
	public jt(hk hk) {
		super(hk, gl.ah);
	}

	@Override
	protected void b() {
		this.a(acz.a).a(cxb.c, cxb.b);
		this.a(acz.b).a(cxb.e, cxb.d);
	}

	@Override
	protected Path a(uh uh) {
		return this.b.b().resolve("data/" + uh.b() + "/tags/fluids/" + uh.a() + ".json");
	}

	@Override
	public String a() {
		return "Fluid Tags";
	}
}
