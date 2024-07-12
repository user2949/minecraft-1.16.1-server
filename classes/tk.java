import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;

public class tk implements ni<ti> {
	private static final Gson a = new GsonBuilder()
		.registerTypeAdapter(tl.c.class, new tl.c.a())
		.registerTypeAdapter(tl.a.class, new tl.a.a())
		.registerTypeAdapter(tl.class, new tl.b())
		.registerTypeHierarchyAdapter(mr.class, new mr.a())
		.registerTypeHierarchyAdapter(nb.class, new nb.a())
		.registerTypeAdapterFactory(new aeb())
		.create();
	private tl b;

	public tk() {
	}

	public tk(tl tl) {
		this.b = tl;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.b = adt.a(a, mg.e(32767), tl.class);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(a.toJson(this.b));
	}

	public void a(ti ti) {
		ti.a(this);
	}
}
