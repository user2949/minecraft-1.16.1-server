import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.yggdrasil.ProfileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class abo {
	private static final Logger e = LogManager.getLogger();
	public static final File a = new File("banned-ips.txt");
	public static final File b = new File("banned-players.txt");
	public static final File c = new File("ops.txt");
	public static final File d = new File("white-list.txt");

	static List<String> a(File file, Map<String, String[]> map) throws IOException {
		List<String> list3 = Files.readLines(file, StandardCharsets.UTF_8);

		for (String string5 : list3) {
			string5 = string5.trim();
			if (!string5.startsWith("#") && string5.length() >= 1) {
				String[] arr6 = string5.split("\\|");
				map.put(arr6[0].toLowerCase(Locale.ROOT), arr6);
			}
		}

		return list3;
	}

	private static void a(MinecraftServer minecraftServer, Collection<String> collection, ProfileLookupCallback profileLookupCallback) {
		String[] arr4 = (String[])collection.stream().filter(string -> !aei.b(string)).toArray(String[]::new);
		if (minecraftServer.T()) {
			minecraftServer.ao().findProfilesByNames(arr4, Agent.MINECRAFT, profileLookupCallback);
		} else {
			for (String string8 : arr4) {
				UUID uUID9 = bec.a(new GameProfile(null, string8));
				GameProfile gameProfile10 = new GameProfile(uUID9, string8);
				profileLookupCallback.onProfileLookupSucceeded(gameProfile10);
			}
		}
	}

	public static boolean a(MinecraftServer minecraftServer) {
		final abu abu2 = new abu(abp.b);
		if (b.exists() && b.isFile()) {
			if (abu2.b().exists()) {
				try {
					abu2.f();
				} catch (IOException var6) {
					e.warn("Could not load existing file {}", abu2.b().getName(), var6);
				}
			}

			try {
				final Map<String, String[]> map3 = Maps.<String, String[]>newHashMap();
				a(b, map3);
				ProfileLookupCallback profileLookupCallback4 = new ProfileLookupCallback() {
					@Override
					public void onProfileLookupSucceeded(GameProfile gameProfile) {
						minecraftServer.ap().a(gameProfile);
						String[] arr3 = (String[])map3.get(gameProfile.getName().toLowerCase(Locale.ROOT));
						if (arr3 == null) {
							abo.e.warn("Could not convert user banlist entry for {}", gameProfile.getName());
							throw new abo.a("Profile not in the conversionlist");
						} else {
							Date date4 = arr3.length > 1 ? abo.b(arr3[1], null) : null;
							String string5 = arr3.length > 2 ? arr3[2] : null;
							Date date6 = arr3.length > 3 ? abo.b(arr3[3], null) : null;
							String string7 = arr3.length > 4 ? arr3[4] : null;
							abu2.a(new abv(gameProfile, date4, string5, date6, string7));
						}
					}

					@Override
					public void onProfileLookupFailed(GameProfile gameProfile, Exception exception) {
						abo.e.warn("Could not lookup user banlist entry for {}", gameProfile.getName(), exception);
						if (!(exception instanceof ProfileNotFoundException)) {
							throw new abo.a("Could not request user " + gameProfile.getName() + " from backend systems", exception);
						}
					}
				};
				a(minecraftServer, map3.keySet(), profileLookupCallback4);
				abu2.e();
				c(b);
				return true;
			} catch (IOException var4) {
				e.warn("Could not read old user banlist to convert it!", (Throwable)var4);
				return false;
			} catch (abo.a var5) {
				e.error("Conversion failed, please try again later", (Throwable)var5);
				return false;
			}
		} else {
			return true;
		}
	}

	public static boolean b(MinecraftServer minecraftServer) {
		abm abm2 = new abm(abp.c);
		if (a.exists() && a.isFile()) {
			if (abm2.b().exists()) {
				try {
					abm2.f();
				} catch (IOException var11) {
					e.warn("Could not load existing file {}", abm2.b().getName(), var11);
				}
			}

			try {
				Map<String, String[]> map3 = Maps.<String, String[]>newHashMap();
				a(a, map3);

				for (String string5 : map3.keySet()) {
					String[] arr6 = (String[])map3.get(string5);
					Date date7 = arr6.length > 1 ? b(arr6[1], null) : null;
					String string8 = arr6.length > 2 ? arr6[2] : null;
					Date date9 = arr6.length > 3 ? b(arr6[3], null) : null;
					String string10 = arr6.length > 4 ? arr6[4] : null;
					abm2.a(new abn(string5, date7, string8, date9, string10));
				}

				abm2.e();
				c(a);
				return true;
			} catch (IOException var10) {
				e.warn("Could not parse old ip banlist to convert it!", (Throwable)var10);
				return false;
			}
		} else {
			return true;
		}
	}

	public static boolean c(MinecraftServer minecraftServer) {
		final abq abq2 = new abq(abp.d);
		if (c.exists() && c.isFile()) {
			if (abq2.b().exists()) {
				try {
					abq2.f();
				} catch (IOException var6) {
					e.warn("Could not load existing file {}", abq2.b().getName(), var6);
				}
			}

			try {
				List<String> list3 = Files.readLines(c, StandardCharsets.UTF_8);
				ProfileLookupCallback profileLookupCallback4 = new ProfileLookupCallback() {
					@Override
					public void onProfileLookupSucceeded(GameProfile gameProfile) {
						minecraftServer.ap().a(gameProfile);
						abq2.a(new abr(gameProfile, minecraftServer.g(), false));
					}

					@Override
					public void onProfileLookupFailed(GameProfile gameProfile, Exception exception) {
						abo.e.warn("Could not lookup oplist entry for {}", gameProfile.getName(), exception);
						if (!(exception instanceof ProfileNotFoundException)) {
							throw new abo.a("Could not request user " + gameProfile.getName() + " from backend systems", exception);
						}
					}
				};
				a(minecraftServer, list3, profileLookupCallback4);
				abq2.e();
				c(c);
				return true;
			} catch (IOException var4) {
				e.warn("Could not read old oplist to convert it!", (Throwable)var4);
				return false;
			} catch (abo.a var5) {
				e.error("Conversion failed, please try again later", (Throwable)var5);
				return false;
			}
		} else {
			return true;
		}
	}

	public static boolean d(MinecraftServer minecraftServer) {
		final abw abw2 = new abw(abp.e);
		if (d.exists() && d.isFile()) {
			if (abw2.b().exists()) {
				try {
					abw2.f();
				} catch (IOException var6) {
					e.warn("Could not load existing file {}", abw2.b().getName(), var6);
				}
			}

			try {
				List<String> list3 = Files.readLines(d, StandardCharsets.UTF_8);
				ProfileLookupCallback profileLookupCallback4 = new ProfileLookupCallback() {
					@Override
					public void onProfileLookupSucceeded(GameProfile gameProfile) {
						minecraftServer.ap().a(gameProfile);
						abw2.a(new abx(gameProfile));
					}

					@Override
					public void onProfileLookupFailed(GameProfile gameProfile, Exception exception) {
						abo.e.warn("Could not lookup user whitelist entry for {}", gameProfile.getName(), exception);
						if (!(exception instanceof ProfileNotFoundException)) {
							throw new abo.a("Could not request user " + gameProfile.getName() + " from backend systems", exception);
						}
					}
				};
				a(minecraftServer, list3, profileLookupCallback4);
				abw2.e();
				c(d);
				return true;
			} catch (IOException var4) {
				e.warn("Could not read old whitelist to convert it!", (Throwable)var4);
				return false;
			} catch (abo.a var5) {
				e.error("Conversion failed, please try again later", (Throwable)var5);
				return false;
			}
		} else {
			return true;
		}
	}

	@Nullable
	public static UUID a(MinecraftServer minecraftServer, String string) {
		if (!aei.b(string) && string.length() <= 16) {
			GameProfile gameProfile3 = minecraftServer.ap().a(string);
			if (gameProfile3 != null && gameProfile3.getId() != null) {
				return gameProfile3.getId();
			} else if (!minecraftServer.N() && minecraftServer.T()) {
				final List<GameProfile> list4 = Lists.<GameProfile>newArrayList();
				ProfileLookupCallback profileLookupCallback5 = new ProfileLookupCallback() {
					@Override
					public void onProfileLookupSucceeded(GameProfile gameProfile) {
						minecraftServer.ap().a(gameProfile);
						list4.add(gameProfile);
					}

					@Override
					public void onProfileLookupFailed(GameProfile gameProfile, Exception exception) {
						abo.e.warn("Could not lookup user whitelist entry for {}", gameProfile.getName(), exception);
					}
				};
				a(minecraftServer, Lists.<String>newArrayList(string), profileLookupCallback5);
				return !list4.isEmpty() && ((GameProfile)list4.get(0)).getId() != null ? ((GameProfile)list4.get(0)).getId() : null;
			} else {
				return bec.a(new GameProfile(null, string));
			}
		} else {
			try {
				return UUID.fromString(string);
			} catch (IllegalArgumentException var5) {
				return null;
			}
		}
	}

	public static boolean a(yd yd) {
		final File file2 = g(yd);
		final File file3 = new File(file2.getParentFile(), "playerdata");
		final File file4 = new File(file2.getParentFile(), "unknownplayers");
		if (file2.exists() && file2.isDirectory()) {
			File[] arr5 = file2.listFiles();
			List<String> list6 = Lists.<String>newArrayList();

			for (File file10 : arr5) {
				String string11 = file10.getName();
				if (string11.toLowerCase(Locale.ROOT).endsWith(".dat")) {
					String string12 = string11.substring(0, string11.length() - ".dat".length());
					if (!string12.isEmpty()) {
						list6.add(string12);
					}
				}
			}

			try {
				final String[] arr7 = (String[])list6.toArray(new String[list6.size()]);
				ProfileLookupCallback profileLookupCallback8 = new ProfileLookupCallback() {
					@Override
					public void onProfileLookupSucceeded(GameProfile gameProfile) {
						yd.ap().a(gameProfile);
						UUID uUID3 = gameProfile.getId();
						if (uUID3 == null) {
							throw new abo.a("Missing UUID for user profile " + gameProfile.getName());
						} else {
							this.a(file3, this.a(gameProfile), uUID3.toString());
						}
					}

					@Override
					public void onProfileLookupFailed(GameProfile gameProfile, Exception exception) {
						abo.e.warn("Could not lookup user uuid for {}", gameProfile.getName(), exception);
						if (exception instanceof ProfileNotFoundException) {
							String string4 = this.a(gameProfile);
							this.a(file4, string4, string4);
						} else {
							throw new abo.a("Could not request user " + gameProfile.getName() + " from backend systems", exception);
						}
					}

					private void a(File file, String string2, String string3) {
						File file5 = new File(file2, string2 + ".dat");
						File file6 = new File(file, string3 + ".dat");
						abo.b(file);
						if (!file5.renameTo(file6)) {
							throw new abo.a("Could not convert file for " + string2);
						}
					}

					private String a(GameProfile gameProfile) {
						String string3 = null;

						for (String string7 : arr7) {
							if (string7 != null && string7.equalsIgnoreCase(gameProfile.getName())) {
								string3 = string7;
								break;
							}
						}

						if (string3 == null) {
							throw new abo.a("Could not find the filename for " + gameProfile.getName() + " anymore");
						} else {
							return string3;
						}
					}
				};
				a(yd, Lists.<String>newArrayList(arr7), profileLookupCallback8);
				return true;
			} catch (abo.a var12) {
				e.error("Conversion failed, please try again later", (Throwable)var12);
				return false;
			}
		} else {
			return true;
		}
	}

	private static void b(File file) {
		if (file.exists()) {
			if (!file.isDirectory()) {
				throw new abo.a("Can't create directory " + file.getName() + " in world save directory.");
			}
		} else if (!file.mkdirs()) {
			throw new abo.a("Can't create directory " + file.getName() + " in world save directory.");
		}
	}

	public static boolean e(MinecraftServer minecraftServer) {
		boolean boolean2 = b();
		return boolean2 && f(minecraftServer);
	}

	private static boolean b() {
		boolean boolean1 = false;
		if (b.exists() && b.isFile()) {
			boolean1 = true;
		}

		boolean boolean2 = false;
		if (a.exists() && a.isFile()) {
			boolean2 = true;
		}

		boolean boolean3 = false;
		if (c.exists() && c.isFile()) {
			boolean3 = true;
		}

		boolean boolean4 = false;
		if (d.exists() && d.isFile()) {
			boolean4 = true;
		}

		if (!boolean1 && !boolean2 && !boolean3 && !boolean4) {
			return true;
		} else {
			e.warn("**** FAILED TO START THE SERVER AFTER ACCOUNT CONVERSION!");
			e.warn("** please remove the following files and restart the server:");
			if (boolean1) {
				e.warn("* {}", b.getName());
			}

			if (boolean2) {
				e.warn("* {}", a.getName());
			}

			if (boolean3) {
				e.warn("* {}", c.getName());
			}

			if (boolean4) {
				e.warn("* {}", d.getName());
			}

			return false;
		}
	}

	private static boolean f(MinecraftServer minecraftServer) {
		File file2 = g(minecraftServer);
		if (!file2.exists() || !file2.isDirectory() || file2.list().length <= 0 && file2.delete()) {
			return true;
		} else {
			e.warn("**** DETECTED OLD PLAYER DIRECTORY IN THE WORLD SAVE");
			e.warn("**** THIS USUALLY HAPPENS WHEN THE AUTOMATIC CONVERSION FAILED IN SOME WAY");
			e.warn("** please restart the server and if the problem persists, remove the directory '{}'", file2.getPath());
			return false;
		}
	}

	private static File g(MinecraftServer minecraftServer) {
		return minecraftServer.a(dac.d).toFile();
	}

	private static void c(File file) {
		File file2 = new File(file.getName() + ".converted");
		file.renameTo(file2);
	}

	private static Date b(String string, Date date) {
		Date date3;
		try {
			date3 = abk.a.parse(string);
		} catch (ParseException var4) {
			date3 = date;
		}

		return date3;
	}

	static class a extends RuntimeException {
		private a(String string, Throwable throwable) {
			super(string, throwable);
		}

		private a(String string) {
			super(string);
		}
	}
}
