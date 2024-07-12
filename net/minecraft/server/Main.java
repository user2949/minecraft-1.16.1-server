package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Lifecycle;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	private static final Logger a = LogManager.getLogger();

	public static void main(String[] arr) {
		OptionParser optionParser2 = new OptionParser();
		OptionSpec<Void> optionSpec3 = optionParser2.accepts("nogui");
		OptionSpec<Void> optionSpec4 = optionParser2.accepts("initSettings", "Initializes 'server.properties' and 'eula.txt', then quits");
		OptionSpec<Void> optionSpec5 = optionParser2.accepts("demo");
		OptionSpec<Void> optionSpec6 = optionParser2.accepts("bonusChest");
		OptionSpec<Void> optionSpec7 = optionParser2.accepts("forceUpgrade");
		OptionSpec<Void> optionSpec8 = optionParser2.accepts("eraseCache");
		OptionSpec<Void> optionSpec9 = optionParser2.accepts("safeMode", "Loads level with vanilla datapack only");
		OptionSpec<Void> optionSpec10 = optionParser2.accepts("help").forHelp();
		OptionSpec<String> optionSpec11 = optionParser2.accepts("singleplayer").withRequiredArg();
		OptionSpec<String> optionSpec12 = optionParser2.accepts("universe").withRequiredArg().defaultsTo(".");
		OptionSpec<String> optionSpec13 = optionParser2.accepts("world").withRequiredArg();
		OptionSpec<Integer> optionSpec14 = optionParser2.accepts("port").withRequiredArg().<Integer>ofType(Integer.class).defaultsTo(-1);
		OptionSpec<String> optionSpec15 = optionParser2.accepts("serverId").withRequiredArg();
		OptionSpec<String> optionSpec16 = optionParser2.nonOptions();

		try {
			OptionSet optionSet17 = optionParser2.parse(arr);
			if (optionSet17.has(optionSpec10)) {
				optionParser2.printHelpOn(System.err);
				return;
			}

			j.h();
			uj.a();
			uj.c();
			v.l();
			Path path18 = Paths.get("server.properties");
			yf yf19 = new yf(path18);
			yf19.b();
			Path path20 = Paths.get("eula.txt");
			uo uo21 = new uo(path20);
			if (optionSet17.has(optionSpec4)) {
				a.info("Initialized '{}' and '{}'", path18.toAbsolutePath(), path20.toAbsolutePath());
				return;
			}

			if (!uo21.a()) {
				a.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
				return;
			}

			File file22 = new File(optionSet17.valueOf(optionSpec12));
			YggdrasilAuthenticationService yggdrasilAuthenticationService23 = new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString());
			MinecraftSessionService minecraftSessionService24 = yggdrasilAuthenticationService23.createMinecraftSessionService();
			GameProfileRepository gameProfileRepository25 = yggdrasilAuthenticationService23.createProfileRepository();
			abl abl26 = new abl(gameProfileRepository25, new File(file22, MinecraftServer.b.getName()));
			String string27 = (String)Optional.ofNullable(optionSet17.valueOf(optionSpec13)).orElse(yf19.a().n);
			dae dae28 = dae.a(file22.toPath());
			dae.a a29 = dae28.c(string27);
			MinecraftServer.a(a29);
			bpn bpn30 = a29.e();
			boolean boolean31 = optionSet17.has(optionSpec9);
			if (boolean31) {
				a.warn("Safe mode active, only vanilla datapack will be loaded");
			}

			aar<aap> aar32 = new aar<>(aap::new, new aau(), new aao(a29.a(dac.g).toFile(), aas.c));
			bpn bpn33 = MinecraftServer.a(aar32, bpn30 == null ? bpn.a : bpn30, boolean31);
			CompletableFuture<uw> completableFuture34 = uw.a(aar32.f(), da.a.DEDICATED, yf19.a().G, v.f(), Runnable::run);

			uw uw35;
			try {
				uw35 = (uw)completableFuture34.get();
			} catch (Exception var41) {
				a.warn("Failed to load datapacks, can't proceed with server load. You can either fix your datapacks or reset to vanilla with --safeMode", (Throwable)var41);
				aar32.close();
				return;
			}

			uw35.i();
			gm.a a36 = gm.b();
			ue<lu> ue37 = ue.a(lp.a, uw35.h(), a36);
			dal dal38 = a29.a(ue37, bpn33);
			if (dal38 == null) {
				bqe bqe39;
				cix cix40;
				if (optionSet17.has(optionSpec5)) {
					bqe39 = MinecraftServer.c;
					cix40 = cix.b;
				} else {
					ye ye41 = yf19.a();
					bqe39 = new bqe(ye41.n, ye41.m, ye41.y, ye41.l, false, new bpx(), bpn33);
					cix40 = optionSet17.has(optionSpec6) ? ye41.U.k() : ye41.U;
				}

				dal38 = new daj(bqe39, cix40, Lifecycle.stable());
			}

			if (optionSet17.has(optionSpec7)) {
				a(a29, aep.a(), optionSet17.has(optionSpec8), () -> true, dal38.z().g());
			}

			a29.a(a36, dal38);
			dal dal39 = dal38;
			final yd yd40 = MinecraftServer.a((Function<Thread, yd>)(thread -> {
				yd yd18 = new yd(thread, a36, a29, aar32, uw35, dal39, yf19, aep.a(), minecraftSessionService24, gameProfileRepository25, abl26, zo::new);
				yd18.d(optionSet17.valueOf(optionSpec11));
				yd18.a(optionSet17.valueOf(optionSpec14));
				yd18.c(optionSet17.has(optionSpec5));
				yd18.b(optionSet17.valueOf(optionSpec15));
				boolean boolean19 = !optionSet17.has(optionSpec3) && !optionSet17.valuesOf(optionSpec16).contains("nogui");
				if (boolean19 && !GraphicsEnvironment.isHeadless()) {
					yd18.bb();
				}

				return yd18;
			}));
			Thread thread41 = new Thread("Server Shutdown Thread") {
				public void run() {
					yd40.a(true);
				}
			};
			thread41.setUncaughtExceptionHandler(new m(a));
			Runtime.getRuntime().addShutdownHook(thread41);
		} catch (Exception var42) {
			a.fatal("Failed to start the minecraft server", (Throwable)var42);
		}
	}

	private static void a(dae.a a, DataFixer dataFixer, boolean boolean3, BooleanSupplier booleanSupplier, ImmutableSet<ug<bqb>> immutableSet) {
		Main.a.info("Forcing world upgrade!");
		amu amu6 = new amu(a, dataFixer, immutableSet, boolean3);
		mr mr7 = null;

		while (!amu6.b()) {
			mr mr8 = amu6.h();
			if (mr7 != mr8) {
				mr7 = mr8;
				Main.a.info(amu6.h().getString());
			}

			int integer9 = amu6.e();
			if (integer9 > 0) {
				int integer10 = amu6.f() + amu6.g();
				Main.a.info("{}% completed ({} / {} chunks)...", aec.d((float)integer10 / (float)integer9 * 100.0F), integer10, integer9);
			}

			if (!booleanSupplier.getAsBoolean()) {
				amu6.a();
			} else {
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException var10) {
				}
			}
		}
	}
}
