import java.util.UUID;
import net.minecraft.server.MinecraftServer;

public interface dak extends dam {
	@Override
	String f();

	void a(boolean boolean1);

	int k();

	void f(int integer);

	void e(int integer);

	int i();

	@Override
	default void a(k k) {
		dam.super.a(k);
		k.a("Level name", this::f);
		k.a("Level game mode", (l<String>)(() -> String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", this.l().b(), this.l().a(), this.m(), this.n())));
		k.a("Level weather", (l<String>)(() -> String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", this.k(), this.j(), this.i(), this.h())));
	}

	int g();

	void a(int integer);

	int u();

	void g(int integer);

	int v();

	void h(int integer);

	void a(UUID uUID);

	bpy l();

	void a(cgw.c c);

	cgw.c q();

	boolean o();

	void c(boolean boolean1);

	boolean n();

	void a(bpy bpy);

	ded<MinecraftServer> t();

	void a(long long1);

	void b(long long1);
}
