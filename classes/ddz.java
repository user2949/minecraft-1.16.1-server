import net.minecraft.server.MinecraftServer;

public class ddz implements deb<MinecraftServer> {
	private final uh a;

	public ddz(uh uh) {
		this.a = uh;
	}

	public void a(MinecraftServer minecraftServer, ded<MinecraftServer> ded, long long3) {
		uu uu6 = minecraftServer.az();
		uu6.a(this.a).ifPresent(cw -> uu6.a(cw, uu6.e()));
	}

	public static class a extends deb.a<MinecraftServer, ddz> {
		public a() {
			super(new uh("function"), ddz.class);
		}

		public void a(le le, ddz ddz) {
			le.a("Name", ddz.a.toString());
		}

		public ddz b(le le) {
			uh uh3 = new uh(le.l("Name"));
			return new ddz(uh3);
		}
	}
}
