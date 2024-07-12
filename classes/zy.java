import net.minecraft.server.MinecraftServer;

public class zy implements tm {
	private static final mr a = new ne("multiplayer.status.request_handled");
	private final MinecraftServer b;
	private final me c;
	private boolean d;

	public zy(MinecraftServer minecraftServer, me me) {
		this.b = minecraftServer;
		this.c = me;
	}

	@Override
	public void a(mr mr) {
	}

	@Override
	public me a() {
		return this.c;
	}

	@Override
	public void a(to to) {
		if (this.d) {
			this.c.a(a);
		} else {
			this.d = true;
			this.c.a(new tk(this.b.aq()));
		}
	}

	@Override
	public void a(tn tn) {
		this.c.a(new tj(tn.b()));
		this.c.a(a);
	}
}
