import java.nio.file.Path;

public class js extends jv<aoq<?>> {
	public js(hk hk) {
		super(hk, gl.al);
	}

	@Override
	protected void b() {
		this.a(acy.a).a(aoq.au, aoq.aD, aoq.aT);
		this.a(acy.b).a(aoq.w, aoq.aj, aoq.ao, aoq.aP, aoq.J, aoq.aR);
		this.a(acy.c).a(aoq.e);
		this.a(acy.d).a(aoq.c, aoq.aA);
		this.a(acy.e).a(acy.d).a(aoq.az, aoq.N, aoq.ax, aoq.aF, aoq.aJ, aoq.p, aoq.aU);
	}

	@Override
	protected Path a(uh uh) {
		return this.b.b().resolve("data/" + uh.b() + "/tags/entity_types/" + uh.a() + ".json");
	}

	@Override
	public String a() {
		return "Entity Type Tags";
	}
}
