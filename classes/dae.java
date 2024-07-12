import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dae {
	private static final Logger a = LogManager.getLogger();
	private static final DateTimeFormatter b = new DateTimeFormatterBuilder()
		.appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
		.appendLiteral('-')
		.appendValue(ChronoField.MONTH_OF_YEAR, 2)
		.appendLiteral('-')
		.appendValue(ChronoField.DAY_OF_MONTH, 2)
		.appendLiteral('_')
		.appendValue(ChronoField.HOUR_OF_DAY, 2)
		.appendLiteral('-')
		.appendValue(ChronoField.MINUTE_OF_HOUR, 2)
		.appendLiteral('-')
		.appendValue(ChronoField.SECOND_OF_MINUTE, 2)
		.toFormatter();
	private static final ImmutableList<String> c = ImmutableList.of(
		"RandomSeed", "generatorName", "generatorOptions", "generatorVersion", "legacy_custom_options", "MapFeatures", "BonusChest"
	);
	private final Path d;
	private final Path e;
	private final DataFixer f;

	public dae(Path path1, Path path2, DataFixer dataFixer) {
		this.f = dataFixer;

		try {
			Files.createDirectories(Files.exists(path1, new LinkOption[0]) ? path1.toRealPath() : path1);
		} catch (IOException var5) {
			throw new RuntimeException(var5);
		}

		this.d = path1;
		this.e = path2;
	}

	public static dae a(Path path) {
		return new dae(path, path.resolve("../backups"), aep.a());
	}

	private static Pair<cix, Lifecycle> a(Dynamic<?> dynamic, DataFixer dataFixer, int integer) {
		Dynamic<?> dynamic4 = dynamic.get("WorldGenSettings").orElseEmptyMap();

		for (String string6 : c) {
			Optional<? extends Dynamic<?>> optional7 = dynamic.get(string6).result();
			if (optional7.isPresent()) {
				dynamic4 = dynamic4.set(string6, (Dynamic<?>)optional7.get());
			}
		}

		Dynamic<?> dynamic5 = dataFixer.update(ajb.y, dynamic4, integer, u.a().getWorldVersion());
		DataResult<cix> dataResult6 = cix.a.parse(dynamic5);
		return Pair.of((cix)dataResult6.resultOrPartial(v.a("WorldGenSettings: ", a::error)).orElseGet(cix::a), dataResult6.lifecycle());
	}

	private static bpn a(Dynamic<?> dynamic) {
		return (bpn)bpn.b.parse(dynamic).resultOrPartial(a::error).orElse(bpn.a);
	}

	private int g() {
		return 19133;
	}

	@Nullable
	private <T> T a(File file, BiFunction<File, DataFixer, T> biFunction) {
		if (!file.exists()) {
			return null;
		} else {
			File file4 = new File(file, "level.dat");
			if (file4.exists()) {
				T object5 = (T)biFunction.apply(file4, this.f);
				if (object5 != null) {
					return object5;
				}
			}

			file4 = new File(file, "level.dat_old");
			return (T)(file4.exists() ? biFunction.apply(file4, this.f) : null);
		}
	}

	@Nullable
	private static bpn b(File file, DataFixer dataFixer) {
		try {
			le le3 = lo.a(new FileInputStream(file));
			le le4 = le3.p("Data");
			le4.r("Player");
			int integer5 = le4.c("DataVersion", 99) ? le4.h("DataVersion") : -1;
			Dynamic<lu> dynamic6 = dataFixer.update(aeo.LEVEL.a(), new Dynamic<>(lp.a, le4), integer5, u.a().getWorldVersion());
			return (bpn)dynamic6.get("DataPacks").result().map(dae::a).orElse(bpn.a);
		} catch (Exception var6) {
			a.error("Exception reading {}", file, var6);
			return null;
		}
	}

	private static BiFunction<File, DataFixer, daj> b(DynamicOps<lu> dynamicOps, bpn bpn) {
		return (file, dataFixer) -> {
			try {
				le le5 = lo.a(new FileInputStream(file));
				le le6 = le5.p("Data");
				le le7 = le6.c("Player", 10) ? le6.p("Player") : null;
				le6.r("Player");
				int integer8 = le6.c("DataVersion", 99) ? le6.h("DataVersion") : -1;
				Dynamic<lu> dynamic9 = dataFixer.update(aeo.LEVEL.a(), new Dynamic<>(dynamicOps, le6), integer8, u.a().getWorldVersion());
				Pair<cix, Lifecycle> pair10 = a(dynamic9, dataFixer, integer8);
				dag dag11 = dag.a(dynamic9);
				bqe bqe12 = bqe.a(dynamic9, bpn);
				return daj.a(dynamic9, dataFixer, integer8, le7, bqe12, dag11, pair10.getFirst(), pair10.getSecond());
			} catch (Exception var12) {
				a.error("Exception reading {}", file, var12);
				return null;
			}
		};
	}

	private BiFunction<File, DataFixer, daf> a(File file, boolean boolean2) {
		return (file3, dataFixer) -> {
			try {
				le le6 = lo.a(new FileInputStream(file3));
				le le7 = le6.p("Data");
				le7.r("Player");
				int integer8 = le7.c("DataVersion", 99) ? le7.h("DataVersion") : -1;
				Dynamic<lu> dynamic9 = dataFixer.update(aeo.LEVEL.a(), new Dynamic<>(lp.a, le7), integer8, u.a().getWorldVersion());
				dag dag10 = dag.a(dynamic9);
				int integer11 = dag10.a();
				if (integer11 != 19132 && integer11 != 19133) {
					return null;
				} else {
					boolean boolean12 = integer11 != this.g();
					File file13 = new File(file, "icon.png");
					bpn bpn14 = (bpn)dynamic9.get("DataPacks").result().map(dae::a).orElse(bpn.a);
					bqe bqe15 = bqe.a(dynamic9, bpn14);
					return new daf(bqe15, dag10, file.getName(), boolean12, boolean2, file13);
				}
			} catch (Exception var15) {
				a.error("Exception reading {}", file3, var15);
				return null;
			}
		};
	}

	public dae.a c(String string) throws IOException {
		return new dae.a(string);
	}

	public class a implements AutoCloseable {
		private final adp b;
		private final Path c;
		private final String d;
		private final Map<dac, Path> e = Maps.<dac, Path>newHashMap();

		public a(String string) throws IOException {
			this.d = string;
			this.c = dae.this.d.resolve(string);
			this.b = adp.a(this.c);
		}

		public String a() {
			return this.d;
		}

		public Path a(dac dac) {
			return (Path)this.e.computeIfAbsent(dac, dacx -> this.c.resolve(dacx.a()));
		}

		public File a(ug<bqb> ug) {
			return cif.a(ug, this.c.toFile());
		}

		private void i() {
			if (!this.b.a()) {
				throw new IllegalStateException("Lock is no longer valid");
			}
		}

		public dai b() {
			this.i();
			return new dai(this, dae.this.f);
		}

		public boolean c() {
			daf daf2 = this.d();
			return daf2 != null && daf2.k().a() != dae.this.g();
		}

		public boolean a(aed aed) {
			this.i();
			return dah.a(this, aed);
		}

		@Nullable
		public daf d() {
			this.i();
			return dae.this.a(this.c.toFile(), dae.this.a(this.c.toFile(), false));
		}

		@Nullable
		public dal a(DynamicOps<lu> dynamicOps, bpn bpn) {
			this.i();
			return dae.this.a(this.c.toFile(), dae.b(dynamicOps, bpn));
		}

		@Nullable
		public bpn e() {
			this.i();
			return dae.this.a(this.c.toFile(), (file, dataFixer) -> dae.b(file, dataFixer));
		}

		public void a(gm gm, dal dal) {
			this.a(gm, dal, null);
		}

		public void a(gm gm, dal dal, @Nullable le le) {
			File file5 = this.c.toFile();
			le le6 = dal.a(gm, le);
			le le7 = new le();
			le7.a("Data", le6);

			try {
				File file8 = File.createTempFile("level", ".dat", file5);
				lo.a(le7, new FileOutputStream(file8));
				File file9 = new File(file5, "level.dat_old");
				File file10 = new File(file5, "level.dat");
				v.a(file10, file8, file9);
			} catch (Exception var10) {
				dae.a.error("Failed to save level {}", file5, var10);
			}
		}

		public File f() {
			this.i();
			return this.c.resolve("icon.png").toFile();
		}

		public void close() throws IOException {
			this.b.close();
		}
	}
}
