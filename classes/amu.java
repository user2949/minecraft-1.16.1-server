import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenCustomHashMap;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class amu {
	private static final Logger a = LogManager.getLogger();
	private static final ThreadFactory b = new ThreadFactoryBuilder().setDaemon(true).build();
	private final ImmutableSet<ug<bqb>> c;
	private final boolean d;
	private final dae.a e;
	private final Thread f;
	private final DataFixer g;
	private volatile boolean h = true;
	private volatile boolean i;
	private volatile float j;
	private volatile int k;
	private volatile int l;
	private volatile int m;
	private final Object2FloatMap<ug<bqb>> n = Object2FloatMaps.synchronize(new Object2FloatOpenCustomHashMap<>(v.k()));
	private volatile mr o = new ne("optimizeWorld.stage.counting");
	private static final Pattern p = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
	private final daa q;

	public amu(dae.a a, DataFixer dataFixer, ImmutableSet<ug<bqb>> immutableSet, boolean boolean4) {
		this.c = immutableSet;
		this.d = boolean4;
		this.g = dataFixer;
		this.e = a;
		this.q = new daa(new File(this.e.a(bqb.g), "data"), dataFixer);
		this.f = b.newThread(this::i);
		this.f.setUncaughtExceptionHandler((thread, throwable) -> {
			a.error("Error upgrading world", throwable);
			this.o = new ne("optimizeWorld.stage.failed");
			this.i = true;
		});
		this.f.start();
	}

	public void a() {
		this.h = false;

		try {
			this.f.join();
		} catch (InterruptedException var2) {
		}
	}

	private void i() {
		this.k = 0;
		Builder<ug<bqb>, ListIterator<bph>> builder2 = ImmutableMap.builder();

		for (ug<bqb> ug4 : this.c) {
			List<bph> list5 = this.b(ug4);
			builder2.put(ug4, list5.listIterator());
			this.k = this.k + list5.size();
		}

		if (this.k == 0) {
			this.i = true;
		} else {
			float float3 = (float)this.k;
			ImmutableMap<ug<bqb>, ListIterator<bph>> immutableMap4 = builder2.build();
			Builder<ug<bqb>, chw> builder5 = ImmutableMap.builder();

			for (ug<bqb> ug7 : this.c) {
				File file8 = this.e.a(ug7);
				builder5.put(ug7, new chw(new File(file8, "region"), this.g, true));
			}

			ImmutableMap<ug<bqb>, chw> immutableMap6 = builder5.build();
			long long7 = v.b();
			this.o = new ne("optimizeWorld.stage.upgrading");

			while (this.h) {
				boolean boolean9 = false;
				float float10 = 0.0F;

				for (ug<bqb> ug12 : this.c) {
					ListIterator<bph> listIterator13 = immutableMap4.get(ug12);
					chw chw14 = immutableMap6.get(ug12);
					if (listIterator13.hasNext()) {
						bph bph15 = (bph)listIterator13.next();
						boolean boolean16 = false;

						try {
							le le17 = chw14.e(bph15);
							if (le17 != null) {
								int integer18 = chw.a(le17);
								le le19 = chw14.a(ug12, () -> this.q, le17);
								le le20 = le19.p("Level");
								bph bph21 = new bph(le20.h("xPos"), le20.h("zPos"));
								if (!bph21.equals(bph15)) {
									a.warn("Chunk {} has invalid position {}", bph15, bph21);
								}

								boolean boolean22 = integer18 < u.a().getWorldVersion();
								if (this.d) {
									boolean22 = boolean22 || le20.e("Heightmaps");
									le20.r("Heightmaps");
									boolean22 = boolean22 || le20.e("isLightOn");
									le20.r("isLightOn");
								}

								if (boolean22) {
									chw14.a(bph15, le19);
									boolean16 = true;
								}
							}
						} catch (s var23) {
							Throwable throwable18 = var23.getCause();
							if (!(throwable18 instanceof IOException)) {
								throw var23;
							}

							a.error("Error upgrading chunk {}", bph15, throwable18);
						} catch (IOException var24) {
							a.error("Error upgrading chunk {}", bph15, var24);
						}

						if (boolean16) {
							this.l++;
						} else {
							this.m++;
						}

						boolean9 = true;
					}

					float float15 = (float)listIterator13.nextIndex() / float3;
					this.n.put(ug12, float15);
					float10 += float15;
				}

				this.j = float10;
				if (!boolean9) {
					this.h = false;
				}
			}

			this.o = new ne("optimizeWorld.stage.finished");

			for (chw chw10 : immutableMap6.values()) {
				try {
					chw10.close();
				} catch (IOException var22) {
					a.error("Error upgrading chunk", (Throwable)var22);
				}
			}

			this.q.a();
			long7 = v.b() - long7;
			a.info("World optimizaton finished after {} ms", long7);
			this.i = true;
		}
	}

	private List<bph> b(ug<bqb> ug) {
		File file3 = this.e.a(ug);
		File file4 = new File(file3, "region");
		File[] arr5 = file4.listFiles((file, string) -> string.endsWith(".mca"));
		if (arr5 == null) {
			return ImmutableList.of();
		} else {
			List<bph> list6 = Lists.<bph>newArrayList();

			for (File file10 : arr5) {
				Matcher matcher11 = p.matcher(file10.getName());
				if (matcher11.matches()) {
					int integer12 = Integer.parseInt(matcher11.group(1)) << 5;
					int integer13 = Integer.parseInt(matcher11.group(2)) << 5;

					try (cia cia14 = new cia(file10, file4, true)) {
						for (int integer16 = 0; integer16 < 32; integer16++) {
							for (int integer17 = 0; integer17 < 32; integer17++) {
								bph bph18 = new bph(integer16 + integer12, integer17 + integer13);
								if (cia14.b(bph18)) {
									list6.add(bph18);
								}
							}
						}
					} catch (Throwable var28) {
					}
				}
			}

			return list6;
		}
	}

	public boolean b() {
		return this.i;
	}

	public int e() {
		return this.k;
	}

	public int f() {
		return this.l;
	}

	public int g() {
		return this.m;
	}

	public mr h() {
		return this.o;
	}
}
