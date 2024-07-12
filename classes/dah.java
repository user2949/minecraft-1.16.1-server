import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dah {
	private static final Logger a = LogManager.getLogger();

	static boolean a(dae.a a, aed aed) {
		aed.a(0);
		List<File> list3 = Lists.<File>newArrayList();
		List<File> list4 = Lists.<File>newArrayList();
		List<File> list5 = Lists.<File>newArrayList();
		File file6 = a.a(bqb.g);
		File file7 = a.a(bqb.h);
		File file8 = a.a(bqb.i);
		dah.a.info("Scanning folders...");
		a(file6, list3);
		if (file7.exists()) {
			a(file7, list4);
		}

		if (file8.exists()) {
			a(file8, list5);
		}

		int integer9 = list3.size() + list4.size() + list5.size();
		dah.a.info("Total conversion count is {}", integer9);
		gm.a a10 = gm.b();
		ue<lu> ue11 = ue.a(lp.a, abc.a.INSTANCE, a10);
		dal dal12 = a.a(ue11, bpn.a);
		long long13 = dal12 != null ? dal12.z().b() : 0L;
		brh brh15;
		if (dal12 != null && dal12.z().i()) {
			brh15 = new bse(brk.c);
		} else {
			brh15 = new bti(long13, false, false);
		}

		a(new File(file6, "region"), list3, brh15, 0, integer9, aed);
		a(new File(file7, "region"), list4, new bse(brk.j), list3.size(), integer9, aed);
		a(new File(file8, "region"), list5, new bse(brk.k), list3.size() + list4.size(), integer9, aed);
		a(a);
		a.a(a10, dal12);
		return true;
	}

	private static void a(dae.a a) {
		File file2 = a.a(dac.e).toFile();
		if (!file2.exists()) {
			dah.a.warn("Unable to create level.dat_mcr backup");
		} else {
			File file3 = new File(file2.getParent(), "level.dat_mcr");
			if (!file2.renameTo(file3)) {
				dah.a.warn("Unable to create level.dat_mcr backup");
			}
		}
	}

	private static void a(File file, Iterable<File> iterable, brh brh, int integer4, int integer5, aed aed) {
		for (File file8 : iterable) {
			a(file, file8, brh, integer4, integer5, aed);
			integer4++;
			int integer9 = (int)Math.round(100.0 * (double)integer4 / (double)integer5);
			aed.a(integer9);
		}
	}

	private static void a(File file1, File file2, brh brh, int integer4, int integer5, aed aed) {
		String string7 = file2.getName();

		try (
			cia cia8 = new cia(file2, file1, true);
			cia cia10 = new cia(new File(file1, string7.substring(0, string7.length() - ".mcr".length()) + ".mca"), file1, true);
		) {
			for (int integer12 = 0; integer12 < 32; integer12++) {
				for (int integer13 = 0; integer13 < 32; integer13++) {
					bph bph14 = new bph(integer12, integer13);
					if (cia8.d(bph14) && !cia10.d(bph14)) {
						le le15;
						try {
							DataInputStream dataInputStream16 = cia8.a(bph14);
							Throwable a17 = null;

							try {
								if (dataInputStream16 == null) {
									a.warn("Failed to fetch input stream for chunk {}", bph14);
									continue;
								}

								le15 = lo.a(dataInputStream16);
							} catch (Throwable var104) {
								a17 = var104;
								throw var104;
							} finally {
								if (dataInputStream16 != null) {
									if (a17 != null) {
										try {
											dataInputStream16.close();
										} catch (Throwable var101) {
											a17.addSuppressed(var101);
										}
									} else {
										dataInputStream16.close();
									}
								}
							}
						} catch (IOException var106) {
							a.warn("Failed to read data for chunk {}", bph14, var106);
							continue;
						}

						le le16 = le15.p("Level");
						chy.a a17 = chy.a(le16);
						le le18 = new le();
						le le19 = new le();
						le18.a("Level", le19);
						chy.a(a17, le19, brh);
						DataOutputStream dataOutputStream20 = cia10.c(bph14);
						Throwable var20 = null;

						try {
							lo.a(le18, dataOutputStream20);
						} catch (Throwable var102) {
							var20 = var102;
							throw var102;
						} finally {
							if (dataOutputStream20 != null) {
								if (var20 != null) {
									try {
										dataOutputStream20.close();
									} catch (Throwable var100) {
										var20.addSuppressed(var100);
									}
								} else {
									dataOutputStream20.close();
								}
							}
						}
					}
				}

				int integer13x = (int)Math.round(100.0 * (double)(integer4 * 1024) / (double)(integer5 * 1024));
				int integer14 = (int)Math.round(100.0 * (double)((integer12 + 1) * 32 + integer4 * 1024) / (double)(integer5 * 1024));
				if (integer14 > integer13x) {
					aed.a(integer14);
				}
			}
		} catch (IOException var111) {
			a.error("Failed to upgrade region file {}", file2, var111);
		}
	}

	private static void a(File file, Collection<File> collection) {
		File file3 = new File(file, "region");
		File[] arr4 = file3.listFiles((filex, string) -> string.endsWith(".mcr"));
		if (arr4 != null) {
			Collections.addAll(collection, arr4);
		}
	}
}
