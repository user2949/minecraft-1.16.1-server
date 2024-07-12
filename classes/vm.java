import com.google.common.collect.ImmutableMap;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class vm {
	private static final Logger a = LogManager.getLogger();
	private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ne("commands.debug.notRunning"));
	private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("commands.debug.alreadyRunning"));
	@Nullable
	private static final FileSystemProvider d = (FileSystemProvider)FileSystemProvider.installedProviders()
		.stream()
		.filter(fileSystemProvider -> fileSystemProvider.getScheme().equalsIgnoreCase("jar"))
		.findFirst()
		.orElse(null);

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("debug")
				.requires(cz -> cz.c(3))
				.then(da.a("start").executes(commandContext -> a(commandContext.getSource())))
				.then(da.a("stop").executes(commandContext -> b(commandContext.getSource())))
				.then(da.a("report").executes(commandContext -> c(commandContext.getSource())))
		);
	}

	private static int a(cz cz) throws CommandSyntaxException {
		MinecraftServer minecraftServer2 = cz.j();
		if (minecraftServer2.aQ()) {
			throw c.create();
		} else {
			minecraftServer2.aR();
			cz.a(new ne("commands.debug.started", "Started the debug profiler. Type '/debug stop' to stop it."), true);
			return 0;
		}
	}

	private static int b(cz cz) throws CommandSyntaxException {
		MinecraftServer minecraftServer2 = cz.j();
		if (!minecraftServer2.aQ()) {
			throw b.create();
		} else {
			amh amh3 = minecraftServer2.aS();
			File file4 = new File(minecraftServer2.c("debug"), "profile-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
			amh3.a(file4);
			float float5 = (float)amh3.g() / 1.0E9F;
			float float6 = (float)amh3.f() / float5;
			cz.a(new ne("commands.debug.stopped", String.format(Locale.ROOT, "%.2f", float5), amh3.f(), String.format("%.2f", float6)), true);
			return aec.d(float6);
		}
	}

	private static int c(cz cz) {
		MinecraftServer minecraftServer2 = cz.j();
		String string3 = "debug-report-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());

		try {
			Path path5 = minecraftServer2.c("debug").toPath();
			Files.createDirectories(path5);
			if (!u.d && d != null) {
				Path path4 = path5.resolve(string3 + ".zip");
				FileSystem fileSystem6 = d.newFileSystem(path4, ImmutableMap.of("create", "true"));
				Throwable var6 = null;

				try {
					minecraftServer2.a(fileSystem6.getPath("/"));
				} catch (Throwable var16) {
					var6 = var16;
					throw var16;
				} finally {
					if (fileSystem6 != null) {
						if (var6 != null) {
							try {
								fileSystem6.close();
							} catch (Throwable var15) {
								var6.addSuppressed(var15);
							}
						} else {
							fileSystem6.close();
						}
					}
				}
			} else {
				Path path4 = path5.resolve(string3);
				minecraftServer2.a(path4);
			}

			cz.a(new ne("commands.debug.reportSaved", string3), false);
			return 1;
		} catch (IOException var18) {
			a.error("Failed to save debug dump", (Throwable)var18);
			cz.a(new ne("commands.debug.reportFailed"));
			return 0;
		}
	}
}
