import net.minecraft.server.MinecraftServer;

public class zw implements su {
	private static final mr a = new nd("Ignoring status request");
	private final MinecraftServer b;
	private final me c;

	public zw(MinecraftServer minecraftServer, me me) {
		this.b = minecraftServer;
		this.c = me;
	}

	@Override
	public void a(st st) {
		switch (st.b()) {
			case LOGIN:
				this.c.a(mf.LOGIN);
				if (st.c() > u.a().getProtocolVersion()) {
					mr mr3 = new ne("multiplayer.disconnect.outdated_server", u.a().getName());
					this.c.a(new tb(mr3));
					this.c.a(mr3);
				} else if (st.c() < u.a().getProtocolVersion()) {
					mr mr3 = new ne("multiplayer.disconnect.outdated_client", u.a().getName());
					this.c.a(new tb(mr3));
					this.c.a(mr3);
				} else {
					this.c.a(new zx(this.b, this.c));
				}
				break;
			case STATUS:
				if (this.b.ak()) {
					this.c.a(mf.STATUS);
					this.c.a(new zy(this.b, this.c));
				} else {
					this.c.a(a);
				}
				break;
			default:
				throw new UnsupportedOperationException("Invalid intention " + st.b());
		}
	}

	@Override
	public void a(mr mr) {
	}

	@Override
	public me a() {
		return this.c;
	}
}
