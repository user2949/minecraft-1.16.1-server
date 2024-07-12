import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.DynamicLike;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bpx {
	private static final Logger H = LogManager.getLogger();
	private static final Map<bpx.e<?>, bpx.f<?>> I = Maps.newTreeMap(Comparator.comparing(e -> e.a));
	public static final bpx.e<bpx.a> a = a("doFireTick", bpx.b.UPDATES, bpx.a.b(true));
	public static final bpx.e<bpx.a> b = a("mobGriefing", bpx.b.MOBS, bpx.a.b(true));
	public static final bpx.e<bpx.a> c = a("keepInventory", bpx.b.PLAYER, bpx.a.b(false));
	public static final bpx.e<bpx.a> d = a("doMobSpawning", bpx.b.SPAWNING, bpx.a.b(true));
	public static final bpx.e<bpx.a> e = a("doMobLoot", bpx.b.DROPS, bpx.a.b(true));
	public static final bpx.e<bpx.a> f = a("doTileDrops", bpx.b.DROPS, bpx.a.b(true));
	public static final bpx.e<bpx.a> g = a("doEntityDrops", bpx.b.DROPS, bpx.a.b(true));
	public static final bpx.e<bpx.a> h = a("commandBlockOutput", bpx.b.CHAT, bpx.a.b(true));
	public static final bpx.e<bpx.a> i = a("naturalRegeneration", bpx.b.PLAYER, bpx.a.b(true));
	public static final bpx.e<bpx.a> j = a("doDaylightCycle", bpx.b.UPDATES, bpx.a.b(true));
	public static final bpx.e<bpx.a> k = a("logAdminCommands", bpx.b.CHAT, bpx.a.b(true));
	public static final bpx.e<bpx.a> l = a("showDeathMessages", bpx.b.CHAT, bpx.a.b(true));
	public static final bpx.e<bpx.d> m = a("randomTickSpeed", bpx.b.UPDATES, bpx.d.b(3));
	public static final bpx.e<bpx.a> n = a("sendCommandFeedback", bpx.b.CHAT, bpx.a.b(true));
	public static final bpx.e<bpx.a> o = a("reducedDebugInfo", bpx.b.MISC, bpx.a.b(false, (minecraftServer, a) -> {
		byte byte3 = (byte)(a.a() ? 22 : 23);

		for (ze ze5 : minecraftServer.ac().s()) {
			ze5.b.a(new on(ze5, byte3));
		}
	}));
	public static final bpx.e<bpx.a> p = a("spectatorsGenerateChunks", bpx.b.PLAYER, bpx.a.b(true));
	public static final bpx.e<bpx.d> q = a("spawnRadius", bpx.b.PLAYER, bpx.d.b(10));
	public static final bpx.e<bpx.a> r = a("disableElytraMovementCheck", bpx.b.PLAYER, bpx.a.b(false));
	public static final bpx.e<bpx.d> s = a("maxEntityCramming", bpx.b.MOBS, bpx.d.b(24));
	public static final bpx.e<bpx.a> t = a("doWeatherCycle", bpx.b.UPDATES, bpx.a.b(true));
	public static final bpx.e<bpx.a> u = a("doLimitedCrafting", bpx.b.PLAYER, bpx.a.b(false));
	public static final bpx.e<bpx.d> v = a("maxCommandChainLength", bpx.b.MISC, bpx.d.b(65536));
	public static final bpx.e<bpx.a> w = a("announceAdvancements", bpx.b.CHAT, bpx.a.b(true));
	public static final bpx.e<bpx.a> x = a("disableRaids", bpx.b.MOBS, bpx.a.b(false));
	public static final bpx.e<bpx.a> y = a("doInsomnia", bpx.b.SPAWNING, bpx.a.b(true));
	public static final bpx.e<bpx.a> z = a("doImmediateRespawn", bpx.b.PLAYER, bpx.a.b(false, (minecraftServer, a) -> {
		for (ze ze4 : minecraftServer.ac().s()) {
			ze4.b.a(new oq(oq.l, a.a() ? 1.0F : 0.0F));
		}
	}));
	public static final bpx.e<bpx.a> A = a("drowningDamage", bpx.b.PLAYER, bpx.a.b(true));
	public static final bpx.e<bpx.a> B = a("fallDamage", bpx.b.PLAYER, bpx.a.b(true));
	public static final bpx.e<bpx.a> C = a("fireDamage", bpx.b.PLAYER, bpx.a.b(true));
	public static final bpx.e<bpx.a> D = a("doPatrolSpawning", bpx.b.SPAWNING, bpx.a.b(true));
	public static final bpx.e<bpx.a> E = a("doTraderSpawning", bpx.b.SPAWNING, bpx.a.b(true));
	public static final bpx.e<bpx.a> F = a("forgiveDeadPlayers", bpx.b.MOBS, bpx.a.b(true));
	public static final bpx.e<bpx.a> G = a("universalAnger", bpx.b.MOBS, bpx.a.b(false));
	private final Map<bpx.e<?>, bpx.g<?>> J;

	private static <T extends bpx.g<T>> bpx.e<T> a(String string, bpx.b b, bpx.f<T> f) {
		bpx.e<T> e4 = new bpx.e<>(string, b);
		bpx.f<?> f5 = (bpx.f<?>)I.put(e4, f);
		if (f5 != null) {
			throw new IllegalStateException("Duplicate game rule registration for " + string);
		} else {
			return e4;
		}
	}

	public bpx(DynamicLike<?> dynamicLike) {
		this();
		this.a(dynamicLike);
	}

	public bpx() {
		this.J = (Map<bpx.e<?>, bpx.g<?>>)I.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, entry -> ((bpx.f)entry.getValue()).a()));
	}

	private bpx(Map<bpx.e<?>, bpx.g<?>> map) {
		this.J = map;
	}

	public <T extends bpx.g<T>> T a(bpx.e<T> e) {
		return (T)this.J.get(e);
	}

	public le a() {
		le le2 = new le();
		this.J.forEach((e, g) -> le2.a(e.a, g.b()));
		return le2;
	}

	private void a(DynamicLike<?> dynamicLike) {
		this.J.forEach((e, g) -> dynamicLike.get(e.a).asString().result().ifPresent(g::a));
	}

	public bpx b() {
		return new bpx(
			(Map<bpx.e<?>, bpx.g<?>>)this.J.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, entry -> ((bpx.g)entry.getValue()).f()))
		);
	}

	public static void a(bpx.c c) {
		I.forEach((e, f) -> a(c, e, f));
	}

	private static <T extends bpx.g<T>> void a(bpx.c c, bpx.e<?> e, bpx.f<?> f) {
		c.a(e, f);
		f.a(c, e);
	}

	public boolean b(bpx.e<bpx.a> e) {
		return this.a(e).a();
	}

	public int c(bpx.e<bpx.d> e) {
		return this.a(e).a();
	}

	public static class a extends bpx.g<bpx.a> {
		private boolean b;

		private static bpx.f<bpx.a> b(boolean boolean1, BiConsumer<MinecraftServer, bpx.a> biConsumer) {
			return new bpx.f<>(BoolArgumentType::bool, f -> new bpx.a(f, boolean1), biConsumer, bpx.c::b);
		}

		private static bpx.f<bpx.a> b(boolean boolean1) {
			return b(boolean1, (minecraftServer, a) -> {
			});
		}

		public a(bpx.f<bpx.a> f, boolean boolean2) {
			super(f);
			this.b = boolean2;
		}

		@Override
		protected void a(CommandContext<cz> commandContext, String string) {
			this.b = BoolArgumentType.getBool(commandContext, string);
		}

		public boolean a() {
			return this.b;
		}

		public void a(boolean boolean1, @Nullable MinecraftServer minecraftServer) {
			this.b = boolean1;
			this.a(minecraftServer);
		}

		@Override
		public String b() {
			return Boolean.toString(this.b);
		}

		@Override
		protected void a(String string) {
			this.b = Boolean.parseBoolean(string);
		}

		@Override
		public int c() {
			return this.b ? 1 : 0;
		}

		protected bpx.a g() {
			return this;
		}

		protected bpx.a f() {
			return new bpx.a(this.a, this.b);
		}
	}

	public static enum b {
		PLAYER("gamerule.category.player"),
		MOBS("gamerule.category.mobs"),
		SPAWNING("gamerule.category.spawning"),
		DROPS("gamerule.category.drops"),
		UPDATES("gamerule.category.updates"),
		CHAT("gamerule.category.chat"),
		MISC("gamerule.category.misc");

		private final String h;

		private b(String string3) {
			this.h = string3;
		}
	}

	public interface c {
		default <T extends bpx.g<T>> void a(bpx.e<T> e, bpx.f<T> f) {
		}

		default void b(bpx.e<bpx.a> e, bpx.f<bpx.a> f) {
		}

		default void c(bpx.e<bpx.d> e, bpx.f<bpx.d> f) {
		}
	}

	public static class d extends bpx.g<bpx.d> {
		private int b;

		private static bpx.f<bpx.d> a(int integer, BiConsumer<MinecraftServer, bpx.d> biConsumer) {
			return new bpx.f<>(IntegerArgumentType::integer, f -> new bpx.d(f, integer), biConsumer, bpx.c::c);
		}

		private static bpx.f<bpx.d> b(int integer) {
			return a(integer, (minecraftServer, d) -> {
			});
		}

		public d(bpx.f<bpx.d> f, int integer) {
			super(f);
			this.b = integer;
		}

		@Override
		protected void a(CommandContext<cz> commandContext, String string) {
			this.b = IntegerArgumentType.getInteger(commandContext, string);
		}

		public int a() {
			return this.b;
		}

		@Override
		public String b() {
			return Integer.toString(this.b);
		}

		@Override
		protected void a(String string) {
			this.b = c(string);
		}

		private static int c(String string) {
			if (!string.isEmpty()) {
				try {
					return Integer.parseInt(string);
				} catch (NumberFormatException var2) {
					bpx.H.warn("Failed to parse integer {}", string);
				}
			}

			return 0;
		}

		@Override
		public int c() {
			return this.b;
		}

		protected bpx.d g() {
			return this;
		}

		protected bpx.d f() {
			return new bpx.d(this.a, this.b);
		}
	}

	public static final class e<T extends bpx.g<T>> {
		private final String a;
		private final bpx.b b;

		public e(String string, bpx.b b) {
			this.a = string;
			this.b = b;
		}

		public String toString() {
			return this.a;
		}

		public boolean equals(Object object) {
			return this == object ? true : object instanceof bpx.e && ((bpx.e)object).a.equals(this.a);
		}

		public int hashCode() {
			return this.a.hashCode();
		}

		public String a() {
			return this.a;
		}

		public String b() {
			return "gamerule." + this.a;
		}
	}

	public static class f<T extends bpx.g<T>> {
		private final Supplier<ArgumentType<?>> a;
		private final Function<bpx.f<T>, T> b;
		private final BiConsumer<MinecraftServer, T> c;
		private final bpx.h<T> d;

		private f(Supplier<ArgumentType<?>> supplier, Function<bpx.f<T>, T> function, BiConsumer<MinecraftServer, T> biConsumer, bpx.h<T> h) {
			this.a = supplier;
			this.b = function;
			this.c = biConsumer;
			this.d = h;
		}

		public RequiredArgumentBuilder<cz, ?> a(String string) {
			return da.a(string, (ArgumentType<T>)this.a.get());
		}

		public T a() {
			return (T)this.b.apply(this);
		}

		public void a(bpx.c c, bpx.e<T> e) {
			this.d.call(c, e, this);
		}
	}

	public abstract static class g<T extends bpx.g<T>> {
		protected final bpx.f<T> a;

		public g(bpx.f<T> f) {
			this.a = f;
		}

		protected abstract void a(CommandContext<cz> commandContext, String string);

		public void b(CommandContext<cz> commandContext, String string) {
			this.a(commandContext, string);
			this.a(commandContext.getSource().j());
		}

		protected void a(@Nullable MinecraftServer minecraftServer) {
			if (minecraftServer != null) {
				this.a.c.accept(minecraftServer, this.g());
			}
		}

		protected abstract void a(String string);

		public abstract String b();

		public String toString() {
			return this.b();
		}

		public abstract int c();

		protected abstract T g();

		protected abstract T f();
	}

	interface h<T extends bpx.g<T>> {
		void call(bpx.c c, bpx.e<T> e, bpx.f<T> f);
	}
}
