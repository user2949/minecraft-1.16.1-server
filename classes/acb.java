import java.util.UUID;
import net.minecraft.server.MinecraftServer;

public class acb implements cy {
	private static final nd b = new nd("Rcon");
	private final StringBuffer c = new StringBuffer();
	private final MinecraftServer d;

	public acb(MinecraftServer minecraftServer) {
		this.d = minecraftServer;
	}

	public void d() {
		this.c.setLength(0);
	}

	public String e() {
		return this.c.toString();
	}

	public cz f() {
		zd zd2 = this.d.D();
		return new cz(this, dem.b(zd2.u()), del.a, zd2, 4, "Rcon", b, this.d, null);
	}

	@Override
	public void a(mr mr, UUID uUID) {
		this.c.append(mr.getString());
	}

	@Override
	public boolean a() {
		return true;
	}

	@Override
	public boolean b() {
		return true;
	}

	@Override
	public boolean S_() {
		return this.d.i();
	}
}
