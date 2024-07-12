import com.google.gson.GsonBuilder;

public class daq {
	public static GsonBuilder a() {
		return new GsonBuilder()
			.registerTypeAdapter(dbb.class, new dbb.a())
			.registerTypeAdapter(dan.class, new dan.a())
			.registerTypeAdapter(dap.class, new dap.a())
			.registerTypeHierarchyAdapter(ddm.class, ddo.a())
			.registerTypeHierarchyAdapter(dat.c.class, new dat.c.a());
	}

	public static GsonBuilder b() {
		return a().registerTypeAdapter(das.class, new das.a()).registerTypeHierarchyAdapter(dbo.class, dbm.a()).registerTypeHierarchyAdapter(dch.class, dcj.a());
	}

	public static GsonBuilder c() {
		return b().registerTypeAdapter(dav.class, new dav.b()).registerTypeAdapter(daw.class, new daw.b());
	}
}
