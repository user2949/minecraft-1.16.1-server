import net.minecraft.server.MinecraftServer;

public class dea implements deb<MinecraftServer> {
	private final uh a;

	public dea(uh uh) {
		this.a = uh;
	}

	public void a(MinecraftServer minecraftServer, ded<MinecraftServer> ded, long long3) {
		uu uu6 = minecraftServer.az();
		adf<cw> adf7 = uu6.b(this.a);

		for (cw cw9 : adf7.b()) {
			uu6.a(cw9, uu6.e());
		}
	}

	public static class a extends deb.a<MinecraftServer, dea> {
		public a() {
			super(new uh("function_tag"), dea.class);
		}

		public void a(le le, dea dea) {
			le.a("Name", dea.a.toString());
		}

		public dea b(le le) {
			uh uh3 = new uh(le.l("Name"));
			return new dea(uh3);
		}
	}
}
